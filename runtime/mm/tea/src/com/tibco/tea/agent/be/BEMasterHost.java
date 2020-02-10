package com.tibco.tea.agent.be;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEMasterHostManagementService;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.management.util.MasterHostConvertor;
import com.tibco.cep.bemm.model.BE;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.BEImpl;
import com.tibco.tea.agent.annotations.Customize;
import com.tibco.tea.agent.annotations.TeaOperation;
import com.tibco.tea.agent.annotations.TeaParam;
import com.tibco.tea.agent.annotations.TeaRequires;
import com.tibco.tea.agent.api.MethodType;
import com.tibco.tea.agent.api.TeaObject;
import com.tibco.tea.agent.api.TeaPrincipal;
import com.tibco.tea.agent.api.WithConfig;
import com.tibco.tea.agent.api.WithStatus;
import com.tibco.tea.agent.be.permission.Permission;
import com.tibco.tea.agent.be.provider.BEMasterHostProvider;
import com.tibco.tea.agent.be.provider.ObjectCacheProvider;
import com.tibco.tea.agent.be.util.BETeaAgentProps;
import com.tibco.tea.agent.internal.types.ClientType;
import com.tibco.tea.agent.support.TeaException;
import com.tibco.tea.agent.types.AgentObjectStatus;

/**
 * This class is used to represent host of the Business Events application.
 * 
 * @author dijadhav
 *
 */
public class BEMasterHost implements TeaObject, WithStatus, WithConfig<MasterHost> {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEMasterHost.class);

	@JsonIgnore
	private BEMasterHostManagementService masterHostManagementService;
	private MasterHost masterHost;

	/**
	 * Parameterized Constructor.
	 * 
	 * @param masterHost
	 *            -Master host instance.
	 * 
	 */
	public BEMasterHost(MasterHost masterHost) {
		this.masterHost = masterHost;
	}

	@Override
	public String getDescription() {
		return "Business Events Master Host";
	}

	@Override
	public String getName() {
		return masterHost.getHostName();
	}

	@Override
	public String getKey() {
		return masterHost.getKey();
	}

	@Override
	public AgentObjectStatus getStatus() {
		AgentObjectStatus status = new AgentObjectStatus();
		status.setState(masterHost.getStatus());
		return status;
	}

	/**
	 * @return the host
	 */
	@Override
	public MasterHost getConfig() {
		return this.masterHost;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void sethost(MasterHost masterHost) {
		this.masterHost = masterHost;
	}

	/**
	 * @param hostService
	 *            the hostService to set
	 */
	@JsonIgnore
	public void setHostService(BEMasterHostManagementService hostService) {
		this.masterHostManagementService = hostService;
	}

	/**
	 * Register Master host instance
	 */
	public void registerWithObjectProvider() {
		BEMasterHostProvider applicationHostProvider = (BEMasterHostProvider) ObjectCacheProvider.getInstance()
				.getProvider(Constants.BE_MASTER_HOST);
		if (null == applicationHostProvider.getInstance(this.getKey())) {
			applicationHostProvider.add(this.getKey(), this);
		}
	}

	@TeaOperation(name = "getHostInfo", description = "Get the master host info", methodType = MethodType.READ)
	public MasterHost getHostInfo() {
		try {
			masterHostManagementService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return masterHost;
		} finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Delete Host")
	@TeaOperation(name = "deleteHost", description = "Delete the host ", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.DELETE_HOST_PERMISSION)
	public String deleteHost(TeaPrincipal teaPrincipal) {
		try {
			masterHostManagementService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			List<String> applicationHosts = masterHostManagementService.getApplicationHostToBeDeleted(masterHost);
			String message = masterHostManagementService.deleteMasterHost(masterHost, loggedInUser);
			ObjectCacheProvider.getInstance().getProvider(Constants.BE_MASTER_HOST).remove(this.getKey());
			for (String applicationHostKey : applicationHosts) {
				ObjectCacheProvider.getInstance().getProvider(Constants.BE_APPLICATION_HOST).remove(applicationHostKey);
			}

			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Edit Host")
	@TeaOperation(name = "edit", description = "Adds a new  host", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_HOST_PERMISSION)
	public String edit(
			@Customize(value = "label:Master Host") @TeaParam(name = "masterHost", alias = "masterHost") com.tibco.tea.agent.be.ui.model.MasterHost masterHost,
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version",defaultValue="0") Long version,
			TeaPrincipal teaPrincipal) throws Exception {
		try {
			masterHostManagementService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			masterHostManagementService.getLockManager().checkVersion(this.masterHost, version);
			String loggedInUser = teaPrincipal.getName();
			String message = masterHostManagementService.editMasterHost(this.masterHost, masterHost.getHostName(),
					masterHost.getHostIp(), masterHost.getOs(),
					MasterHostConvertor.convertBEUIModelToBEServiceModel(masterHost.getBE()), masterHost.getUserName(),
					masterHost.getPassword(), masterHost.getSshPort(), masterHost.getDeploymentPath(), loggedInUser);
			masterHostManagementService.getLockManager().incrementVersion(this.masterHost);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}
	
	@Customize(value = "label:Edit Host Cli")
	@TeaOperation(name = "editCli", description = "Adds a new application host", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.UPDATE_HOST_PERMISSION)
	public String editCli(
			@Customize(value = "label:Host Name") @TeaParam(name = "hostName", alias = "hostName") String hostName,
			@Customize(value = "label:IP Address") @TeaParam(name = "ipAddress", alias = "ipAddress") String ipAddress,
			@Customize(value = "label:Operating System") @TeaParam(name = "hostOS", alias = "hostOS") String hostOS,
			@Customize(value = "label:BE Home") @TeaParam(name = "beHome", alias = "beHome") String beHome,
			@Customize(value = "label:BE TRA ") @TeaParam(name = "beTra", alias = "beTra") String beTra,
			@Customize(value = "label:User Name") @TeaParam(name = "userName", alias = "userName") String userName,
			@Customize(value = "label:Password;private:true") @TeaParam(name = "password", alias = "password") String password,
			@Customize(value = "label:SSH Port") @TeaParam(name = "sshPort", alias = "sshPort") int sshPort,
			@Customize(value = "label:Deployment Path") @TeaParam(name = "deploymentPath", alias = "deploymentPath") String deploymentPath,
			@Customize(value = "label:Version") @TeaParam(name = "version", alias = "version",defaultValue="0") Long version,
			TeaPrincipal teaPrincipal) throws Exception {
		try {
			masterHostManagementService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			masterHostManagementService.getLockManager().checkVersion(this.masterHost, version);
			
			String loggedInUser = teaPrincipal.getName();
			
			List<com.tibco.cep.bemm.model.BE> beList = new ArrayList<com.tibco.cep.bemm.model.BE>();
			
			if(masterHost.getBE()!=null){
				for(com.tibco.cep.bemm.model.BE be : masterHost.getBE()){
					if(be.getBeHome().equals(beHome)){
						com.tibco.cep.bemm.model.BE beServiceModel = new BEImpl();
						beServiceModel.setBeHome(beHome);
						beServiceModel.setBeTra(beTra);
						beList.add(beServiceModel);
					}else{
						beList.add(be);
					}
				}
			}
			
			String message = masterHostManagementService.editMasterHost(masterHost, hostName, ipAddress, hostOS, beList,
					 userName, password, sshPort, deploymentPath, loggedInUser);
			masterHostManagementService.getLockManager().incrementVersion(this.masterHost);
			return message;
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		}finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
		
	}

	@Customize(value = "label:Instances Running")
	@TeaOperation(name = "isInstancesRunning", description = "Check instances running on the this master host or not.", methodType = MethodType.READ)
	public boolean isInstancesRunning(TeaPrincipal teaPrincipal) throws Exception {
		try {
			String loggedInUser = teaPrincipal.getName();
			masterHostManagementService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			return masterHostManagementService.isInstancesDeployed(masterHost);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	@Customize(value = "label:Host Instances")
	@TeaOperation(name = "getHostInstances", description = "Get the instances deployed on the host", methodType = MethodType.READ)
	public List<ServiceInstance> getHostInstances(TeaPrincipal teaPrincipal) throws Exception {
		try {
			masterHostManagementService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return masterHostManagementService.getHostServiceInstance(masterHost);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}

	/**
	 * Log error message
	 * 
	 * @param ex
	 *            - Exception
	 * @return TeaException
	 */
	protected TeaException logErrorMessage(Exception ex) {
		String message = ex.getMessage();
		if (ex instanceof Throwable) {
			if (ex instanceof RuntimeException) {
				message = "Unknown Error";
				LOGGER.log(Level.ERROR, ex, ex.getMessage());
			} else {
				LOGGER.log(Level.ERROR, ex.getMessage());
			}
		}
		return new TeaException(message);
	}

	@Customize(value = "label:Start")
	@TeaOperation(name = "start", description = "Start passed service insatnce of  BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.START_PU_INSTANCE_PERMISSION)
	public String start(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {

		try {
			masterHostManagementService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return masterHostManagementService.start(masterHost, instances, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}

	}

	@Customize(value = "label:Stop")
	@TeaOperation(name = "stop", description = "Stop", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.STOP_PU_INSTANCE_PERMISSION)
	public String stop(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		try {
			masterHostManagementService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return masterHostManagementService.stop(masterHost, instances, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}

	}

	@Customize(value = "label:Kill")
	@TeaOperation(name = "kill", description = "Kill passed service insatnce of  BE Application", methodType = MethodType.UPDATE)
	@TeaRequires(Permission.KILL_INSTANCE_PERMISSION)
	public String kill(@TeaParam(name = "instances", alias = "instances") List<String> instances,
			TeaPrincipal teaPrincipal) {
		try {
			masterHostManagementService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return masterHostManagementService.kill(masterHost, instances, loggedInUser);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}

	}

	@Customize(value = "label:Upload External Jars")
	@TeaOperation(name = "uploadExternalJars", description = "Upload the external jars to selected be home", methodType = MethodType.UPDATE)
	public String uploadExternalJars(@TeaParam(name = "jarFiles", alias = "jarFiles") DataSource jarFiles,
			@TeaParam(name = "beId", alias = "beId") String beId, TeaPrincipal teaPrincipal) {
		try {
			masterHostManagementService.getLockManager().acquireWriteLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			String loggedInUser = teaPrincipal.getName();
			return masterHostManagementService.uploadExternalJars(masterHost, jarFiles, beId);
		} catch (Exception ex) {
			throw logErrorMessage(ex);
		} finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}

	}
	
	//API is especially designed for CLI, although can be used by WEBUI too.
	@TeaOperation(name = "getBeId", description = "Get the be home service id", methodType = MethodType.READ)
	public String getBeId(@TeaParam(name = "beHome", alias = "beHome") String beHome, TeaPrincipal teaPrincipal) {
		try {
			masterHostManagementService.getLockManager().acquireReadLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
			for(BE be : masterHost.getBE()){
				if(be.getBeHome().equalsIgnoreCase(beHome)){
					return be.getId();
				}
			}
			return null;
		} finally {
			masterHostManagementService.getLockManager().releaseLock(BETeaAgentProps.GLOBAL_LOCK_KEY);
		}
	}
	
}
