package com.tibco.cep.bpmn.ui.graph.properties;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;


/**
 * 
 * @author majha
 *
 */
public class AssociationEdgeGeneralPropertySection extends AbstractEdgeGeneralPropertySection {


	public AssociationEdgeGeneralPropertySection() {
		super();
	}
	
	@Override
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		// TODO Auto-generated method stub
		super.createControls(parent, tabbedPropertySheetPage);
		nameText.setEditable(false);
	}

	@Override
	String getLabelAttribute() {
		// TODO Auto-generated method stub
		return BpmnMetaModelExtensionConstants.E_ATTR_LABEL;
	}
	
}