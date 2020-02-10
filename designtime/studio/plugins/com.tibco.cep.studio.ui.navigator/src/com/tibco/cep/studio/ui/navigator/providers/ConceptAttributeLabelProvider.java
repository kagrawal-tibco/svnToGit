package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.navigator.NavigatorPlugin;
import com.tibco.cep.studio.ui.navigator.model.PropertyNode;
import com.tibco.cep.studio.ui.navigator.model.StateMachineAssociationNode;
import com.tibco.cep.studio.ui.util.StudioUIUtils;


public class ConceptAttributeLabelProvider extends EntityLabelProvider {

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if (element instanceof PropertyNode) {
			PropertyNode propNode = (PropertyNode) element;
			return StudioUIUtils.getPropertyImage(propNode.getType());
		}
		if (element instanceof StateMachineAssociationNode) {
			StateMachineAssociationNode smAssociationNode = (StateMachineAssociationNode) element;
			StateMachine stateMachine = (StateMachine)IndexUtils.getEntity(smAssociationNode.getOwnerProjectName(), smAssociationNode.getStateMachine(), ELEMENT_TYPES.STATE_MACHINE);
			if(stateMachine == null){
				return new DecorationOverlayIcon(NavigatorPlugin.getDefault().getImage("icons/sm_association.png"), 
						NavigatorPlugin.getImageDescriptor("icons/error_marker.png"),IDecoration.BOTTOM_LEFT).createImage();
			}
			if(stateMachine != null && stateMachine.isMain()){
				return new DecorationOverlayIcon(NavigatorPlugin.getDefault().getImage("icons/sm_association.png"), 
						NavigatorPlugin.getImageDescriptor("icons/main_overlay.png"),IDecoration.TOP_LEFT).createImage();
			}
			return NavigatorPlugin.getDefault().getImage("icons/sm_association.png");
		}
		return super.getImage(element);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element instanceof PropertyNode) {
			PropertyNode propNode = (PropertyNode) element;
			if(propNode.getType() == PROPERTY_TYPES.CONCEPT 
					|| propNode.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE){
				PropertyDefinition propertyDefinition = (PropertyDefinition)propNode.getEntity();
				return ((PropertyNode)element).getName()+ " ["+propertyDefinition.getConceptTypePath()+"]";
			}
			return ((PropertyNode)element).getName();
		}
		if (element instanceof StudioNavigatorNode) {
			return ((StudioNavigatorNode)element).getName();
		}
		if (element instanceof StateMachineAssociationNode) {
			return ((StateMachineAssociationNode)element).getStateMachine();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider#getDescription(java.lang.Object)
	 */
	@Override
	public String getDescription(Object anElement) {
		if (anElement instanceof PropertyNode) {
			PropertyNode propNode = (PropertyNode) anElement;
			PropertyDefinition propertyDefinition = (PropertyDefinition)propNode.getEntity();
			Concept concept = (Concept)propertyDefinition.eContainer();
			String name = concept.getFullPath()+"." +IndexUtils.getFileExtension(concept)+"/"+propNode.getName();
			return name;
		}
		if (anElement instanceof StateMachineAssociationNode) {
			StateMachineAssociationNode smAssociationNode = (StateMachineAssociationNode) anElement;
			return smAssociationNode.getStateMachine();
		}
		return null;
	}
}
