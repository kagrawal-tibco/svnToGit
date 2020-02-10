package com.tibco.cep.loadbalancer.impl.server.core;

import static com.tibco.cep.util.Helper.$logger;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.common.resource.DefaultId;
import com.tibco.cep.impl.service.DefaultThreadFactory;
import com.tibco.cep.loadbalancer.impl.transport.MembershipChangeMessage;
import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.DistributionKey;
import com.tibco.cep.loadbalancer.message.MessageHandle;
import com.tibco.cep.loadbalancer.message.DistributableMessage.KnownHeader;
import com.tibco.cep.loadbalancer.server.ServerAdmin;
import com.tibco.cep.loadbalancer.server.core.DistributionTable;
import com.tibco.cep.loadbalancer.server.core.Kernel;
import com.tibco.cep.loadbalancer.server.core.KernelMBean;
import com.tibco.cep.loadbalancer.server.core.LoadBalancer;
import com.tibco.cep.loadbalancer.server.core.LoadBalancerException;
import com.tibco.cep.loadbalancer.server.core.LoadBalancerMBean;
import com.tibco.cep.loadbalancer.server.core.Member;
import com.tibco.cep.loadbalancer.server.core.sink.Sink;
import com.tibco.cep.loadbalancer.server.core.sink.SinkStatus;
import com.tibco.cep.loadbalancer.util.Helper;
import com.tibco.cep.util.annotation.Copy;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Mar 17, 2010 / Time: 3:30:21 PM
*/

@LogCategory("loadbalancer.core.server.kernel")
public class DefaultKernel implements Kernel {
    protected ReentrantLock lock;

    protected HashMap<Id, LoadBalancer> sourceIdAndLoadBalancers;

    protected HashMap<Id, Member> members;

    protected HashMap<Id, Member> suspectedMembers;

    protected boolean running;

    protected Id id;

    protected Logger logger;

    protected ResourceProvider resourceProvider;
    
    protected ExecutorService pendingExecutor;

    public DefaultKernel() {
        this.lock = new ReentrantLock();
        this.sourceIdAndLoadBalancers = new HashMap<Id, LoadBalancer>();
        this.members = new HashMap<Id, Member>();
        this.suspectedMembers = new HashMap<Id, Member>();
		this.pendingExecutor = Executors
				.newSingleThreadExecutor(new DefaultThreadFactory(
						"LB pending ack message delivery thread"));
    }

    @Override
    public void setId(Id id) {
        this.id = id;
    }

    public void setResourceProvider(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;

        this.logger = $logger(resourceProvider, getClass());
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public void addLoadBalancer(LoadBalancer loadBalancer) throws LifecycleException {
        lock.lock();
        try {
            LoadBalancer oldLB = sourceIdAndLoadBalancers.get(loadBalancer.getSourceId());
            if (oldLB != null) {
                throw new LifecycleException(String.format("Attempt to add a loadbalancer that connects to" +
                        " source [%s] did not succeed as another loadbalancer instance" +
                        " [%s] has already been registered.", loadBalancer.getSourceId(), oldLB));
            }

            loadBalancer.setResourceProvider(resourceProvider);

            loadBalancer.start();

            sourceIdAndLoadBalancers.put(loadBalancer.getSourceId(), loadBalancer);

            registerLoadBalancerMBean(loadBalancer);

            logger.log(Level.FINE,
                    String.format("Added Source [%s] and LoadBalancer [%s]", loadBalancer.getSourceId(),
                            loadBalancer.getId()));
        }
        finally {
            lock.unlock();
        }
    }

    private void registerKernelMBean() {
        try {
            MBeanServer platform = ManagementFactory.getPlatformMBeanServer();
            StandardMBean standardMBean = new StandardMBean(this, KernelMBean.class);
            ObjectName on = new ObjectName(Helper.JMX_ROOT_NAME + ",router=" + getId());
            platform.registerMBean(standardMBean, on);
        }
        catch (Exception e) {
            logger.log(Level.WARNING, String.format("Error occurred while registering MBeans in Kernel [%s]", getId()), e);
        }
    }

    private void registerLoadBalancerMBean(LoadBalancer loadBalancer) {
        try {
            MBeanServer platform = ManagementFactory.getPlatformMBeanServer();
            StandardMBean standardMBean = new StandardMBean(loadBalancer, LoadBalancerMBean.class);
            ObjectName on = new ObjectName(Helper.JMX_ROOT_NAME + ",router=" + getId() + ",lb=" + loadBalancer.getId());
            if (platform.isRegistered(on) == false) {
                platform.registerMBean(standardMBean, on);
            }
        }
        catch (Exception e) {
            logger.log(Level.WARNING,
                    String.format("Error occurred while registering LoadBalancer [%s] MBean in Kernel [%s]",
                            loadBalancer.getId(), getId()), e);
        }
    }

    @Copy
    @Override
    public Collection<? extends Id> getSourceIds() {
        lock.lock();
        try {
            return new LinkedList<Id>(sourceIdAndLoadBalancers.keySet());
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void removeLoadBalancer(Id sourceId) throws LifecycleException {
        lock.lock();
        try {
            LoadBalancer loadBalancer = sourceIdAndLoadBalancers.remove(sourceId);
            if (loadBalancer != null) {
                loadBalancer.stop();
            }

            logger.log(Level.FINE, String.format("Removed Source [%s]", sourceId));
        }
        finally {
            lock.unlock();
        }
    }

    @Copy
    @Override
    public Map<? extends Id, ? extends LoadBalancer> getSourceIdAndLoadBalancers() {
        lock.lock();
        try {
            return new HashMap<Id, LoadBalancer>(sourceIdAndLoadBalancers);
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void addMember(Member member) {
        lock.lock();
        try {
            if (members.containsKey(member.getId())) {
                logger.log(Level.FINE,
                        String.format("Member [%s] has already been registered. Ignoring call to add.",
                                member.getId()));

                return;
            }

            //-----------------

            logger.log(Level.FINE, String.format("Adding Member [%s]", member.getId()));

            if (running) {
                try {
                    member.start();
                }
                catch (Exception e) {
                    /*
                    Changed from WARNING to FINE because when an LB starts up there could be
                    old/dead member entries in the LB control cache.
                    */
                    logger.log(Level.FINE,
                            String.format("Error occurred while connecting to Member [%s]", member.getId()), e);

                    return;
                }
            }

            members.put(member.getId(), member);

            //-----------------

            ServerAdmin serverAdmin = resourceProvider.fetchResource(ServerAdmin.class);
            Id serverId = serverAdmin.getResourceId();
            Id uniqueId = new DefaultId(serverId, System.currentTimeMillis());
            MembershipChangeMessage membershipChangeMessage =
                    new MembershipChangeMessage(uniqueId, serverId, member.getId(), true);

            for (Sink sink : member.getEndpoints()) {
                DistributionKey[] distributionKeys = sink.getDistributionStrategy().getBootstrapKeys();

                Id sourceId = sink.getSourceId();
                LoadBalancer loadBalancer = sourceIdAndLoadBalancers.get(sourceId);

                if (loadBalancer == null) {
                    logger.log(Level.FINE,
                            String.format("Member is referring to a Source [%s] that is invalid/not present", sourceId));

                    continue;
                }

                //-----------------

                //Before adding the member, inform all the sinks that this LB is connected to.
                loadBalancer.sendToAll(membershipChangeMessage);

                //-----------------

                DistributionTable distributionTable = loadBalancer.getDistributionTable();
                distributionTable.putMember(member, distributionKeys);

                logger.log(Level.FINE, String.format("Connected Source [%s] -> Member [%s] -> Sink [%s]",
                        sourceId, member.getId(), sink.getId()));
            }

            logger.log(Level.FINE, String.format("Added Member [%s]", member.getId()));
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void memberIsSuspect(Id memberId) {
        lock.lock();
        try {
            Member member = members.get(memberId);

            if (member != null) {
                logger.log(Level.FINE, String.format("Member [%s] is suspect", memberId));

                removeMember(memberId);

                suspectedMembers.put(memberId, member);
            }
            else {
                logger.log(Level.FINE,
                        String.format("Member [%s] was not registered to even become suspect. Ignoring call.",
                                memberId));
            }
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void memberIsOk(Id memberId) {
        lock.lock();
        try {
            Member member = suspectedMembers.remove(memberId);

            if (member != null) {
                logger.log(Level.FINE, String.format("Member [%s] is not suspect anymore", memberId));

                addMember(member);
            }
            else {
                logger.log(Level.FINE,
                        String.format("Member [%s] was not in the suspect list. Ignoring call.", memberId));
            }
        }
        finally {
            lock.unlock();
        }
    }

    @Copy
    @Override
    public Collection<? extends Member> getMembers() {
        lock.lock();
        try {
            return new LinkedList<Member>(members.values());
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void removeMember(Id memberId) {
        lock.lock();
        try {
        	List<PendingMessage> pendingMessages = new ArrayList<PendingMessage>();
            Member member = members.get(memberId);

            if (member == null) {
                member = suspectedMembers.remove(memberId);

                if (member == null) {
                    logger.log(Level.FINE,
                            String.format("Member [%s] is not registered. Ignoring call to remove.", memberId));
                }
                else {
                    logger.log(Level.FINE,
                            String.format("Member [%s] was in the suspect list.", memberId));
                }

                return;
            }

            //---------------

            ServerAdmin serverAdmin = resourceProvider.fetchResource(ServerAdmin.class);
            Id serverId = serverAdmin.getResourceId();
            Id uniqueId = new DefaultId(serverId, System.currentTimeMillis());
            MembershipChangeMessage membershipChangeMessage =
                    new MembershipChangeMessage(uniqueId, serverId, member.getId(), false);

            for (Sink sink : member.getEndpoints()) {
                DistributionKey[] distributionKeys = sink.getDistributionStrategy().getBootstrapKeys();

                Id sourceId = sink.getSourceId();
                LoadBalancer loadBalancer = sourceIdAndLoadBalancers.get(sourceId);

                if (loadBalancer == null) {
                    logger.log(Level.FINE, String.format(
                            "Member [%s] -> Sink [%s] is referring to a Source [%s] that is invalid/not present",
                            member.getId(), sink.getId(), sourceId));

                    continue;
                }
                
                SinkStatus sinkStatus = sink.getSinkStatus();
				if(sinkStatus != null) {
					Collection<MessageHandle> pendingMessagesCollection = sinkStatus.getPendingMessages();
					if(pendingMessagesCollection != null && pendingMessagesCollection.size() > 0) {
		                for(MessageHandle messageHandle : pendingMessagesCollection) {
		                	pendingMessages.add(new PendingMessage(messageHandle.getDistributableMessage(), sourceId));
		                }
					}
                }

                //-----------------

                DistributionTable distributionTable = loadBalancer.getDistributionTable();
                distributionTable.removeMember(member, distributionKeys);

                //-----------------

                //Inform all the sinks that this LB is not connected to this member anymore.
                loadBalancer.sendToAll(membershipChangeMessage);

                logger.log(Level.FINE, String.format("Disconnected Source [%s] -> Member [%s] -> Sink [%s]",
                        sourceId, member.getId(), sink.getId()));
            }

            members.remove(memberId);

            if (running) {
                try {
                    member.stop();
                }
                catch (Exception e) {
                    logger.log(Level.FINE,
                            String.format("Error occurred while stopping Member [%s]", member.getId()), e);
                }
            }

            logger.log(Level.FINE, String.format("Removed Member [%s]", member.getId()));
            
            if(pendingMessages.size() > 0 && !this.pendingExecutor.isShutdown()) {
				this.logger
						.info(String
								.format("Resending [%s] pending ack messages that were earlier sent to departed member [%s]",
										pendingMessages.size(), memberId));
            	this.pendingExecutor.submit(new PendingMessageRunnable(pendingMessages, this));
            }
        }
        finally {
            lock.unlock();
        }
    }

    //-------------

    @Override
    public void start() throws LifecycleException {
        lock.lock();
        try {
            if (running) {
                return;
            }

            //-------------

            logger.log(Level.INFO, String.format("Starting Kernel [%s]", getId()));

            for (Member member : members.values()) {
                member.start();
            }

            for (LoadBalancer loadBalancer : sourceIdAndLoadBalancers.values()) {
                loadBalancer.start();
            }

            running = true;

            //-------------

            registerKernelMBean();

            //-------------

            logger.log(Level.INFO, String.format("Started Kernel [%s]", getId()));
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void printReport() {
        lock.lock();
        try {
            logger.log(Level.WARNING, String.format("[Kernel report of [%s]]", getId()));

            for (LoadBalancer loadBalancer : sourceIdAndLoadBalancers.values()) {
                loadBalancer.printReport();
            }
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void stop() throws LifecycleException {
        lock.lock();
        try {
            logger.log(Level.INFO, String.format("Stopping Kernel [%s]", getId()));
            
            pendingExecutor.shutdown();

            for (LoadBalancer loadBalancer : sourceIdAndLoadBalancers.values()) {
                loadBalancer.stop();
            }

            //-------------

            HashSet<Id> copyMemberIds = new HashSet<Id>(members.keySet());

            for (Id memberId : copyMemberIds) {
                removeMember(memberId);
            }

            copyMemberIds.clear();

            //-------------

            HashMap<Id, LoadBalancer> copySourceIdAndLBs = new HashMap<Id, LoadBalancer>(sourceIdAndLoadBalancers);

            for (LoadBalancer loadBalancer : copySourceIdAndLBs.values()) {
                removeLoadBalancer(loadBalancer.getSourceId());
            }

            copySourceIdAndLBs.clear();

            //-------------

            logger.log(Level.INFO, String.format("Stopped Kernel [%s]", getId()));
        }
        finally {
            lock.unlock();
        }
    }
    
    class PendingMessageRunnable implements Runnable {
    	private final List<PendingMessage> pendingMessages; 
    	private final Kernel kernel;

		public PendingMessageRunnable(List<PendingMessage> pendingMessages,
				Kernel kernel) {
			this.pendingMessages = pendingMessages;
			this.kernel = kernel;
		}

		@Override
		public void run() {
			for (PendingMessage pendingMessage : this.pendingMessages) {
				LoadBalancer loadBalancer = this.kernel
						.getSourceIdAndLoadBalancers().get(
								pendingMessage.getSourceId());
				if (loadBalancer != null) {
					try {
						loadBalancer.send(pendingMessage
								.getDistributableMessage());
					} catch (LoadBalancerException e) {
						DefaultKernel.this.logger
								.warning(String
										.format("LoadBalancer [%s] could not deliver the message [%s] for source [%s]",
												loadBalancer.getId(),
												pendingMessage
														.getDistributableMessage()
														.getHeaderValue(
																KnownHeader.__content_id__),
												pendingMessage.getSourceId()));
					}
				}
			}
		}
    }
    
    class PendingMessage {
    	private final DistributableMessage distributableMessage;
    	private final Id sourceId;
    	
		public PendingMessage(DistributableMessage distributableMessage,
				Id sourceId) {
			this.distributableMessage = distributableMessage;
			this.sourceId = sourceId;
		}

		public DistributableMessage getDistributableMessage() {
			return distributableMessage;
		}

		public Id getSourceId() {
			return sourceId;
		}
    	
    }
}
