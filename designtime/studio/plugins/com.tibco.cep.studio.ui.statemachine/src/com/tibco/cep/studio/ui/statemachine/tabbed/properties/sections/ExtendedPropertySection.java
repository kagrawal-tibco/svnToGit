package com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.ui.forms.components.StateMachineExtendedPropTreeViewer;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tibco.cep.studio.ui.statemachine.tabbed.properties.StateMachinePropertySheetPage;

/**
 * 
 * @author sasahoo
 *
 */
public class ExtendedPropertySection extends AbstractStateMachinePropertySection{

//	private ExtendedPropertiesPanel exPanel;
	public Composite  exSwtPanel;
	private StateMachineExtendedPropTreeViewer extndProp;
	private ISelectionChangedListener fSelectionListener;
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		
		StateMachinePropertySheetPage tempPage=(StateMachinePropertySheetPage)tabbedPropertySheetPage;
		this.editor = (StateMachineEditor) tempPage.getEditor();
		GridData gd = new GridData(GridData.FILL_BOTH);
    	gd.widthHint = 500;
    	gd.heightHint = 200;
    	parent.setLayoutData(gd);
    	 gd = new GridData(GridData.FILL_BOTH);
     	gd.widthHint = 500;
     	gd.heightHint = 200;
 	    editor.getControl().setLayoutData(gd);
		
		super.createControls(parent, tabbedPropertySheetPage);
		Composite sectionClient = getWidgetFactory().createComposite(parent);
		extndProp= new StateMachineExtendedPropTreeViewer(editor) {
			@Override
			public void modify(Object element, String property, Object value) {
				super.modify(element, property, value);
				ExtendedPropertySection.this.editor.enableEdit(true);
			}
		};
		extndProp.setIsStateMachineEditor(true);
		extndProp.createHierarchicalTree(sectionClient);
		extndProp.getM_treeViewer().addSelectionChangedListener(getSelectionListener());
//		exSwtPanel.pack();
	}
	
	@Override
	public void dispose() {
		if (extndProp != null && extndProp.getM_treeViewer() != null) {
			extndProp.getM_treeViewer().removeSelectionChangedListener(getSelectionListener());
		}

		super.dispose();
		if (exSwtPanel != null) {
//			exSwtPanel.setEntity(null);
			exSwtPanel = null;
		}
	}

	private ISelectionChangedListener getSelectionListener() {
		if (fSelectionListener == null) {
			fSelectionListener = new ISelectionChangedListener() {
				
				@Override
				public void selectionChanged(SelectionChangedEvent arg0) {
					if (arg0.getSelection() != null && arg0.getSelection().isEmpty()) {
						editor.enableEdit(true);
					} else {
						editor.enableEdit(false);
					}
				}
			};
		}
				
		return fSelectionListener;
	}

	/**
	 * Default extended Properties
	 * @param entity
	 */
	protected void setDefaultExtendedProperties(Entity entity){
//		try{
//			if(entity.getExtendedProperties()!=null &&! (entity.getExtendedProperties().getProperties().isEmpty())){
////				exPanel.setEntity(entity);
//				EditorUtils.setExtendedPropertiesPanelData(entity.getExtendedProperties(), null);
//			}else{
//				if(entity instanceof StateMachine){
//					entity.setExtendedProperties(EditorUtils.getDefaultEntityPropertiesMap());
//				}else{
//					entity.setExtendedProperties(EditorUtils.getDefaultStateMachinePropertiesMap());
//				}
////				exPanel.setEntity(entity);
//				EditorUtils.setExtendedPropertiesPanelData(entity.getExtendedProperties(), null);
//				editor.modified(); //No Editor Dirty for default Extended Property
//			}
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		extndProp.changeEntity((Entity)eObject);
	}
}