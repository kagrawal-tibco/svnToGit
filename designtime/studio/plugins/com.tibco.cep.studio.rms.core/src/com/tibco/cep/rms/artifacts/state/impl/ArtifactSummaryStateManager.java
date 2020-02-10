/**
 * 
 */
package com.tibco.cep.rms.artifacts.state.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tibco.cep.rms.artifacts.state.IArtifactSummaryState;
import com.tibco.cep.rms.artifacts.state.IArtifactSummaryStateChangeListener;
import com.tibco.cep.rms.artifacts.state.StateContext;
import com.tibco.cep.rms.artifacts.state.SummaryStateChangeEvent;
import com.tibco.cep.rms.artifacts.state.transitions.IArtifactStateTransition;
import com.tibco.cep.rms.artifacts.state.transitions.impl.ArtifactCommitStateTransition;
import com.tibco.cep.rms.artifacts.state.transitions.impl.LocalCreationStateTransition;
import com.tibco.cep.rms.artifacts.state.transitions.impl.LocalModificationStateTransition;
import com.tibco.cep.rms.artifacts.state.transitions.impl.LocalRemovalStateTransition;
import com.tibco.cep.rms.artifacts.state.transitions.impl.RemoteCreationStateTransition;
import com.tibco.cep.rms.artifacts.state.transitions.impl.RemoteModificationStateTransition;
import com.tibco.cep.rms.artifacts.state.transitions.impl.RemoteRemovalStateTransition;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactChanger;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RMSArtifactsSummaryManager;

/**
 * @author aathalye
 *
 */
public class ArtifactSummaryStateManager implements
		IArtifactSummaryStateChangeListener {
	
	private List<IArtifactStateTransition> allTransitions = new ArrayList<IArtifactStateTransition>();
	
	public ArtifactSummaryStateManager() {
		allTransitions.add(new LocalCreationStateTransition(this));
		allTransitions.add(new LocalModificationStateTransition(this));
		allTransitions.add(new LocalRemovalStateTransition(this));
		allTransitions.add(new RemoteCreationStateTransition(this));
		allTransitions.add(new RemoteModificationStateTransition(this));
		allTransitions.add(new RemoteRemovalStateTransition(this));
		allTransitions.add(new ArtifactCommitStateTransition(this));
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.IArtifactSummaryStateChangeListener#stateChanged()
	 */
	
	public void stateChanged(SummaryStateChangeEvent stateChangeEvent) {
		//Check each transition
		for (IArtifactStateTransition transition : allTransitions) {
			transition.effectTransition(stateChangeEvent);
			//Get to state
			IArtifactSummaryState toState = transition.getToState();
			if (toState != null) {
				Artifact artifact = stateChangeEvent.getArtifact();
				Date updateTime = artifact != null ? artifact.getUpdateTime() : null;
				String commitVersion = artifact != null ? artifact.getCommittedVersion() : null;
				StateContext stateContext = 
					new StateContext(stateChangeEvent.getProject(), 
							         stateChangeEvent.getResourcePath(), 
							         stateChangeEvent.getContainerPath(),
							         stateChangeEvent.getExtension(),
							         updateTime,
							         commitVersion);
				transition.executeActions(stateContext);
				break;
			}
		}
	}
	
	/**
	 * TODO When state persistence is added fetch the current state
	 * @param projectName
	 * @param artifactPath
	 * @return
	 */
	public ArtifactSummaryEntry getArtifactEntry(String projectName, 
			                                     String artifactPath,
			                                     ArtifactChanger artifactChanger) {
		return getArtifactEntry(projectName, artifactPath, artifactChanger, false);
	}
	
	/**
	 * TODO When state persistence is added fetch the current state
	 * @param projectName
	 * @param artifactPath
	 * @return
	 */
	public ArtifactSummaryEntry getArtifactEntry(String projectName, 
			                                     String artifactPath,
			                                     ArtifactChanger artifactChanger,
			                                     boolean commitStatus) {
		RMSArtifactsSummaryManager artifactsSummaryManager = RMSArtifactsSummaryManager.getInstance();
		//Check if this artifact exists
		ArtifactSummaryEntry summaryEntry = 
			artifactsSummaryManager.getSummaryEntry(projectName, artifactPath, artifactChanger, commitStatus);
		return summaryEntry;
	}
}
