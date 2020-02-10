package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.GMPActivationListener;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.engine.DefaultGroupMember;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.cluster.GMPClusterStates;
import com.tibco.rta.service.cluster.GMPMessagePublisher;
import com.tibco.rta.service.cluster.GroupMember;
import com.tibco.rta.service.cluster.GroupMemberStates;
import com.tibco.rta.service.cluster.GroupMembershipCluster;
import com.tibco.rta.service.cluster.GroupMembershipListener;
import com.tibco.rta.service.cluster.GroupMembershipService;
import com.tibco.rta.service.cluster.QuorumBasedGMPCluster;
import com.tibco.rta.service.cluster.event.GMPEventRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 12:16 PM
 * Default implementation of GMP using JMS.
 */
public class QuorumBasedGroupMembershipService extends AbstractStartStopServiceImpl implements GroupMembershipService {

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_CLUSTER.getCategory());

    /**
     * The subject to subscribe to for forming group.
     */
    protected String membershipSubject;

    /**
     * The cluster used by this service.
     */
    protected QuorumBasedGMPCluster cluster;

    /**
     * Message publisher for GMP messages.
     */
    protected GMPJMSMessagePublisher messagePublisher;

    /**
     * Message publisher for GMP messages.
     */
    protected GMPJMSMessageProcessor messageProcessor;

    /**
     * How to elect primary from contending nodes.
     */
    protected GreatestTokenLeaderElectionStrategy leaderElectionStrategy;

    /**
     * General main lock
     */
    protected final ReentrantLock mainLock = new ReentrantLock();

    /**
     * Quorum condition.
     */
    private final Condition quorumCondition = mainLock.newCondition();

    /**
     * Heartbeat sender for this member joining this cluster.
     */
    protected ScheduledExecutorService heartbeatSender;

    /**
     * Heartbeat scanner for all members joining this cluster.
     */
    protected ScheduledExecutorService heartbeatScanner;

    /**
     * Boolean indicating whether heartbeats are being sent by this service or not.
     */
    protected volatile boolean heartbeatStopped = true;

    /**
     * Leader election
     */
    protected ExecutorService leaderElectorExecutorService = Executors.newSingleThreadExecutor(new ThreadFactory() {

        static final String NAME_PREFIX = "Leader-Elector";

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, NAME_PREFIX);
        }
    });


    /**
     * Notify interested parties about GMP events. This is meant to be used by external parties.
     */
    protected List<GMPActivationListener> activationListeners = new ArrayList<GMPActivationListener>();

    public QuorumBasedGroupMembershipService() {

        this.cluster = new QuorumBasedGMPCluster();

        GMPEventRegistry.INSTANCE.registerLifecycleListener(new DefaultGMPLifecycleEventListener());
    }

    public GMPMessagePublisher getMessagePublisher() {
        return messagePublisher;
    }

    @Override
    public void init(Properties configuration) throws Exception {
        super.init(configuration);

        membershipSubject = (String) ConfigProperty.GMP_SUBJECT.getValue(configuration);

        messagePublisher = new GMPJMSMessagePublisher();

        messageProcessor = new GMPJMSMessageProcessor();
        //Initialize cluster
        cluster.init(configuration);

        leaderElectionStrategy = new GreatestTokenLeaderElectionStrategy();
    }

    @Override
    public String getMembershipSubject() {
        return membershipSubject;
    }


    @Override
    public void subscribe() throws Exception {
        GMPSubscriptionMessage subscriptionMessage = new GMPSubscriptionMessage();
        //Send member joined notification.
        subscriptionMessage.setOriginatingMemberUID(cluster.getHostMember().getUID());

        messagePublisher.publishSubscriptionMessage(subscriptionMessage);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <G extends GroupMember> G getOrAddMember(String memberUID) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            for (GroupMember groupMember : cluster.getAllGroupMembers()) {
                if (groupMember.getUID().equals(memberUID)) {
                    return (G) groupMember;
                }
            }
            GroupMember newMember = new DefaultGroupMember(memberUID);
            //Not found. Need to add
            cluster.addMember(newMember);
            return (G) newMember;
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    @Override
    public <G extends GroupMember> G electPrimary() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            //Wait only if current state is quorum not reached.
            //If it is in any other state do not wait.
            while (!cluster.ensureQuorum() && cluster.isQuorumIncomplete()) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Waiting for quorum with current cluster state [%s]", cluster.getCurrentState());
                }
                //Wait for quorum condition to be met.
                quorumCondition.await();
            }

            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Beginning election process");
            }
            //if you reach here condition is met and this will be met only in one engine
            //by virtue of topic.
            GroupMember electedMember = leaderElectionStrategy.electLeader();

            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Elected member [%s]", electedMember);
            }
            /**
             * Each member in the cluster will perform the election process.
             * So potentially everyone could send leader election message.
             * To avoid this, only the member who is the leader should send it.
             */
            if (electedMember != null && cluster.getHostMember().equals(electedMember)) {

                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Elected member [%s] sending election notification", electedMember);
                }
                //Set its state to leader
                cluster.setMemberState(cluster.getHostMember(), GroupMemberStates.LEADER);
                //Set it as primary.
                cluster.setPrimaryMember(electedMember);
                //Transition to leader elected state.
                cluster.setCurrentState(GMPClusterStates.LEADER_ELECTED);
                //Only then should a notification be sent. Else do nothing.
                //Send notification
                GMPLeaderElectedMessage leaderElectedMessage = new GMPLeaderElectedMessage();

                leaderElectedMessage.setOriginatingMemberUID(electedMember.getUID());

                messagePublisher.publishElectionCompletedMessage(leaderElectedMessage);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
        return null;
    }


    @Override
    public <L extends GroupMembershipListener> void addMembershipListener(L listener) {
        cluster.addMembershipListener(listener);
    }


    /**
     * Signal waiters that quorum is completed.
     */
    public <G extends GroupMember> void signalQuorumComplete(G... groupMembers) {
        //Start heartbeat scanner
        //TODO take initial delay as config param.
        startMemberLivenessScanner();
    }


    @Override
    public <M extends GMPActivationListener> void addActivationListener(M messageReceivedListener) {
        activationListeners.add(messageReceivedListener);
    }

    @Override
    public void signalPrimary() {
        for (GMPActivationListener activationListener : activationListeners) {
            activationListener.onActivate();
        }
    }

    /**
     * Publish notification for quorum completion.
     * Also signal waiters for quorum to complete.
     *
     * @throws Exception
     */
    public void publishQuorumNotificationMessage() throws Exception {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            GMPQuorumNotificationMessage quorumNotificationMessage = new GMPQuorumNotificationMessage();

            String[] memberIds = new String[cluster.getAllGroupMembers().size()];

            int loop = 0;
            for (GroupMember groupMember : cluster.getAllGroupMembers()) {
                memberIds[loop++] = groupMember.getUID();
            }
            quorumNotificationMessage.setQuorumMemberIds(memberIds);

            messagePublisher.publishQuorumNotificationMessage(quorumNotificationMessage);
            //Signal waiters
            quorumCondition.signalAll();
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <G extends GroupMember> List<G> getAllMembers() {
        return cluster.getAllGroupMembers();
    }


    @Override
    @SuppressWarnings("unchecked")
    public <C extends GroupMembershipCluster> C getCluster() {
        return (C) cluster;
    }

    @Override
	public synchronized void start() throws Exception {
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO,
					"Starting QuorumBasedGroupMembership service..");
		}
		// Initialize publisher
		messagePublisher.init(configuration, "MessagePublisher");
		// Initialize processor
		messageProcessor.init(configuration, "MessageProcessor");
		super.start();
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO,
					"Starting QuorumBasedGroupMembership service Complete.");
		}
	}

    @Override
    public synchronized void stop() throws Exception {
    	
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping GroupMembership service..");
        }

    	try {
			if (heartbeatScanner != null) {
				heartbeatScanner.shutdownNow();
			}
		} catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
		}
    	
    	try {
			if (heartbeatSender != null) {
				heartbeatSender.shutdownNow();
			}
		} catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
		}
    	
    	try {
    		if (leaderElectorExecutorService != null) {
    			leaderElectorExecutorService.shutdownNow();
    		}
    	} catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
		}
    	
    	try {
			if (messagePublisher != null) {
				messagePublisher.stop();
			}
		} catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
		}
    	
    	try {
			if (messageProcessor != null) {
				messageProcessor.stop();
			}
		} catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
		}
    	
        super.stop();
        
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping GroupMembership service Complete.");
        }
    }


    @Override
    public void startMemberLivenessScanner() {
        //There will be a call here on every quorum complete notification.
        //Hence this arrangement precludes multiple scanners to start.
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            if (heartbeatScanner == null) {

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Starting liveness scanner");
                }
                //Only then start
                heartbeatScanner = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

                    private static final String NAME_PREFIX = "Heartbeat-Scanner-Thread";

                    @Override
                    public Thread newThread(Runnable runnable) {
                        return new Thread(runnable, NAME_PREFIX);
                    }
                });
                long heartbeatScannerInterval = Long.parseLong((String) ConfigProperty.GMP_MEMBER_HB_SCAN_INTERVAL.getValue(configuration));

                long hbThreshold = Long.parseLong((String) ConfigProperty.GMP_MEMBER_HB_THRESHOLD.getValue(configuration));

                long leaderAbsenceThreshold = Long.parseLong((String) ConfigProperty.GMP_LEADER_ABSENCE_THRESHOLD.getValue(configuration));
                //Start heartbeat scanner
                heartbeatScanner.scheduleWithFixedDelay(new GroupMemberHeartbeatScanner(leaderAbsenceThreshold, hbThreshold), 0, heartbeatScannerInterval, TimeUnit.MILLISECONDS);
            }
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    @Override
    public void startMemberHeartbeats() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            if (heartbeatStopped) {

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Starting heartbeats");
                }

                heartbeatSender = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

                    private static final String NAME_PREFIX = "Heartbeat-Sender-Thread";

                    @Override
                    public Thread newThread(Runnable runnable) {
                        return new Thread(runnable, NAME_PREFIX);
                    }
                });
                long heartbeatSendInterval = Long.parseLong((String) ConfigProperty.GMP_MEMBER_HB_INTERVAL.getValue(configuration));
                //Start publishing heartbeat messages
                heartbeatSender.scheduleWithFixedDelay(new GroupMemberHeartbeatSender(), 0, heartbeatSendInterval, TimeUnit.MILLISECONDS);

                heartbeatStopped = false;
            }
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    @Override
    public void stopMemberHeartbeats() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            heartbeatSender.shutdownNow();

            heartbeatStopped = true;
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    @Override
    public void stopMemberLivenessScanner() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            if (heartbeatScanner != null) {

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Stopped member liveness scanner");
                }
                heartbeatScanner.shutdownNow();
                //Set it to null
                heartbeatScanner = null;
            }
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    @Override
    public void startElection() {
        //Start election in a separate thread so as to not block EMS dispatchers.
        leaderElectorExecutorService.submit(new Elector(this));
    }


    private class Elector implements Runnable {

        private GroupMembershipService groupMembershipService;

        private Elector(GroupMembershipService groupMembershipService) {
            this.groupMembershipService = groupMembershipService;
        }

        @Override
        public void run() {
            groupMembershipService.electPrimary();
        }
    }


	@Override
	public void signalNetworkDisconnect() {
        for (GMPActivationListener activationListener : activationListeners) {
            activationListener.onDeactivate();
        }		
	}
}
