package com.tibco.cep.studio.ui.editors.concepts;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.SharedStateMachineElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.navigator.NavigatorPlugin;

/**
 * 
 * @author sasahoo
 * 
 */
public class StateMachineAssociationLabelProvider extends LabelProvider implements ITableLabelProvider {

	private String projectName;
	
	private String conceptFilePath;
	
	private String libRef;

	public StateMachineAssociationLabelProvider(String projectName,
			                                    String conceptFilePath,
			                                    String libRef) {
		this(projectName, conceptFilePath);
		this.libRef = libRef;
	}

	public StateMachineAssociationLabelProvider(String projectName,
			                                    String conceptFilePath) {
		this.projectName = projectName;
		this.conceptFilePath = conceptFilePath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang
	 * .Object, int)
	 */
	public String getColumnText(Object obj, int index) {
		return obj.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang
	 * .Object, int)
	 */
	public Image getColumnImage(Object obj, int index) {
		StateMachine stateMachine = null;
		if (libRef != null) {
			int idx = conceptFilePath.lastIndexOf(".");
			boolean hasLeadingSlash = conceptFilePath.length() > 0
					&& conceptFilePath.charAt(0) == '/';
			if (idx != -1) {
				conceptFilePath = hasLeadingSlash ? conceptFilePath.substring(0, idx) : "/" + conceptFilePath.substring(0, idx);
			} else {
				conceptFilePath = hasLeadingSlash ? conceptFilePath : "/" + conceptFilePath;
			}
			DesignerProject project = IndexUtils.getIndex(projectName);
			DesignerProject refProject = IndexUtils.getReferencedDesignerProject(project, libRef);
			//Fix for opening editor from Worklist Editor
			if (refProject == null) {
				return NavigatorPlugin.getDefault().getImage("icons/sm_association.png");
			}
			//Referenced project can be null because this code path may
			//not always be traversed during project library call.
			if (refProject != null) {
				EList<EntityElement> entityElements = refProject.getEntityElements();
				for (int i = 0; i < entityElements.size(); i++) {
					EntityElement entityElement = entityElements.get(i);
					if (entityElement instanceof SharedEntityElement) {
						SharedEntityElement sharedElement = (SharedEntityElement) entityElement;
						if (sharedElement instanceof SharedStateMachineElement) {
							SharedStateMachineElement sharedStateMachineElement = (SharedStateMachineElement) sharedElement;
							StateMachine shstateMachine = (StateMachine) sharedStateMachineElement
									.getSharedEntity();
							if (shstateMachine.getFullPath().equals(obj.toString())) {
								stateMachine = shstateMachine;
								break;
							}
						}
					}
				}
			}
		} else {
			stateMachine = (StateMachine) IndexUtils.getEntity(projectName, obj.toString(), ELEMENT_TYPES.STATE_MACHINE);
		}
		if (stateMachine == null) {
			return new DecorationOverlayIcon(NavigatorPlugin.getDefault()
					.getImage("icons/sm_association.png"), NavigatorPlugin
					.getImageDescriptor("icons/error_marker.png"),
					IDecoration.BOTTOM_LEFT).createImage();
		}
		if (stateMachine != null
				&& !stateMachine.getOwnerConceptPath().equals(conceptFilePath)) {
			return new DecorationOverlayIcon(NavigatorPlugin.getDefault()
					.getImage("icons/sm_association.png"), NavigatorPlugin
					.getImageDescriptor("icons/error_marker.png"),
					IDecoration.BOTTOM_LEFT).createImage();
		}
		if (stateMachine != null && stateMachine.isMain()) {
			return new DecorationOverlayIcon(NavigatorPlugin.getDefault()
					.getImage("icons/sm_association.png"), NavigatorPlugin
					.getImageDescriptor("icons/main_overlay.png"),
					IDecoration.TOP_LEFT).createImage();

		}
		return NavigatorPlugin.getDefault().getImage("icons/sm_association.png");
	}
}