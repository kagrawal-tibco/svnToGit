package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.StateVisualization;
import com.tibco.cep.designtime.core.model.beviewsconfig.Visualization;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.BEViewsURIHandler;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;

public class StateMachineComponentStateRefIDReplacer {

	IProject sourceProject;

	IResource sourceStateMachineRes;

	IProject targetProject;

	StateMachineComponent targetStateMachineComponent;

	IResource targetStateMachineRes;


	public StateMachineComponentStateRefIDReplacer(IProject sourceProject, IProject targetProject, StateMachineComponent targetStateMachineComponent, String stateMachinePath) {
		super();
		this.sourceProject = sourceProject;
		this.sourceStateMachineRes = this.sourceProject.findMember(stateMachinePath);
		if (this.sourceStateMachineRes == null) {
			throw new IllegalArgumentException("could not find "+stateMachinePath+" under "+sourceProject.getName());
		}
		this.targetProject = targetProject;
		this.targetStateMachineComponent = targetStateMachineComponent;
		this.targetStateMachineRes = this.targetProject.getFile(stateMachinePath);
	}

	public boolean update(){
		if (targetStateMachineRes.exists() == true) {
			updateRefIds();
			return true;
		}
		else {
			//the target state machine has not need updated/created yet, we can process the state machine later
			targetProject.getWorkspace().addResourceChangeListener(new TargetStateMachineChangeListener(), IResourceChangeEvent.POST_CHANGE);
			return false;
		}
	}

	protected void updateRefIds() {
		//load the source state machine
		StateMachine sourceStateMachine = (StateMachine) IndexUtils.loadEObject(ResourceHelper.getLocationURI(sourceStateMachineRes));
		//load the target state machine
		StateMachine targetStateMachine = (StateMachine) IndexUtils.loadEObject(ResourceHelper.getLocationURI(targetStateMachineRes));
		//generate the id to name map from source state machine
		Map<String,String> sourceIdToNameMap = new HashMap<String, String>();
		populateIDToNameEntitiesMap(sourceStateMachine.getStateEntities(), sourceIdToNameMap);
		//generate the name to id map from target state machine
		Map<String,String> targetNameToIdMap = new HashMap<String, String>();
		populateNameToIDEntitiesMap(targetStateMachine.getStateEntities(), targetNameToIdMap);
		//traverse the incoming State Model Component and update the stateref id
		for (Visualization visualization : targetStateMachineComponent.getVisualization()) {
			StateVisualization stateVisualization = (StateVisualization) visualization;
			String sourceStateRefID = stateVisualization.getStateRefID();
			if (StringUtil.isEmpty(sourceStateRefID) == false){
				String sourceStateNameWithPath = sourceIdToNameMap.get(sourceStateRefID);
				if (sourceStateNameWithPath != null) {
					String targetStateRefID = targetNameToIdMap.get(sourceStateNameWithPath);
					if (targetStateRefID != null) {
						stateVisualization.setStateRefID(targetStateRefID);
					}
				}
			}
		}
	}

	private void populateIDToNameEntitiesMap(List<StateEntity> stateEntities, Map<String,String> idToNameMap) {
		for (StateEntity stateEntity : stateEntities) {
			idToNameMap.put(stateEntity.getGUID(), IndexUtils.getStateEntityFullPath(stateEntity));
			if (stateEntity instanceof StateComposite){
				populateIDToNameEntitiesMap(((StateComposite) stateEntity).getStateEntities(), idToNameMap);
			}
		}
	}

	private void populateNameToIDEntitiesMap(List<StateEntity> stateEntities, Map<String,String> nameToIdMap) {
		for (StateEntity stateEntity : stateEntities) {
			nameToIdMap.put(IndexUtils.getStateEntityFullPath(stateEntity),stateEntity.getGUID());
			if (stateEntity instanceof StateComposite){
				populateNameToIDEntitiesMap(((StateComposite) stateEntity).getStateEntities(), nameToIdMap);
			}
		}
	}


	class TargetStateMachineChangeListener implements IResourceChangeListener {

		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta targetStateMachineResDelta = event.getDelta().findMember(targetStateMachineRes.getFullPath());
			if (targetStateMachineResDelta != null) {
				final String projectRelativePath = targetStateMachineComponent.getFullPath()+"."+IndexUtils.getFileExtension(targetStateMachineComponent);
				final String workspaceRelativePath = targetStateMachineComponent.getOwnerProjectName() + projectRelativePath;
				final IResource resource = targetProject.findMember(projectRelativePath);
				try {
					WorkspaceJob job = new WorkspaceJob(workspaceRelativePath+" updater") {

						@Override
						public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {

							try {
								monitor.subTask("Updating State Reference IDs in "+workspaceRelativePath);
								//update the ref ids in the target State Model Component
								updateRefIds();
								//save the target State Model Component
								DashboardResourceUtils.persistEntity(targetStateMachineComponent, DashboardResourceUtils.getCurrentProjectBaseURI(targetProject), targetProject, new BEViewsURIHandler(), null);
								//refresh the resource containing the target State Model Component in a new workspace job (since refreshLocal has scheduling rule which is @ directory level)
								new WorkspaceJob(workspaceRelativePath+" refresher") {

									@Override
									public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
										resource.refreshLocal(IResource.DEPTH_ZERO, null);
										return Status.OK_STATUS;
									}
								}.schedule();
								return Status.OK_STATUS;
							} catch (IOException e) {
								IStatus status = new Status(IStatus.ERROR, DashboardStateMachineComponentPlugin.PLUGIN_ID, "could not update "+workspaceRelativePath,e);
								throw new CoreException(status);
							}
						}

					};

					job.setRule(targetProject.getWorkspace().getRuleFactory().modifyRule(resource));
					job.schedule();

				} finally  {
					targetProject.getWorkspace().removeResourceChangeListener(this);
				}
			}
		}
	}

}