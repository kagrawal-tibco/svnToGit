//package com.tibco.cep.bpmn.ui.graph.properties;
//
//import java.awt.BorderLayout;
//import java.awt.Container;
//import java.util.Map;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.ui.forms.widgets.TableWrapData;
//import org.eclipse.ui.forms.widgets.TableWrapLayout;
//import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
//
//
///**
// * 
// * @author sasahoo
// *
// */
//public class ExtendedPropertySection extends AbstractBpmnPropertySection{
//
//	private ExtendedPropertiesPanel exPanel;
//
//	
//	public ExtendedPropertySection() {
//		super();
//	}
//	
//	/**
//	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
//	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
//	 */
//	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
//		super.createControls(parent, tabbedPropertySheetPage);
//		TableWrapLayout layout = new TableWrapLayout();
//		layout.topMargin = 0;
//    	parent.setLayout(layout);
//		Composite sectionClient = getWidgetFactory().createComposite(parent, SWT.EMBEDDED);
//		TableWrapData td = new TableWrapData(TableWrapData.FILL);
//		td.grabVertical = true;
//		td.grabHorizontal = true;
//		td.heightHint = 210;
//		sectionClient.setLayoutData(td);
//		Container panel = getSwingContainer(sectionClient);
//		exPanel = new ExtendedPropertiesPanel(panel);
//		panel.add(exPanel, BorderLayout.CENTER);
//	}
//	
//	/*
//	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
//	 */
//	public void refresh() {
//	}
//
//	@Override
//	protected void updatePropertySection(Map<String, Object> updateList) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}