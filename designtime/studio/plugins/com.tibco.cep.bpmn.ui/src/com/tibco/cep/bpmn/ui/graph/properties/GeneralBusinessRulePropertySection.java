package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BERuleSelector;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;

/**
 * 
 * @author majha
 *
 */
public class GeneralBusinessRulePropertySection extends GeneralTaskPropertySection {

	private TableViewer rulesTable;
	private Button ruleResourceButton;
	private Button ruleRemoveButton;
	private List<String> selectedRules;
	private RuleSelectionWidgetListener ruleTreeListener;
	
	public GeneralBusinessRulePropertySection() {
		super();
		ruleTreeListener = new RuleSelectionWidgetListener();
	}
	
	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
		super.setInput(part, selection);
		
	}
	
	@Override
	protected void createResourceProperty() {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		// Add Event
		getWidgetFactory().createLabel(composite, BpmnMessages.getString("businessRuleProp_Resource_label"), SWT.NONE);
		
		Composite ruleNamesComposite = getWidgetFactory().createComposite(composite);
		ruleNamesComposite.setLayout(new GridLayout(2,false));
		gd = new GridData(/* GridData.FILL_HORIZONTAL */);
//		gd.widthHint = 562;
		gd.verticalSpan = 3;
		ruleNamesComposite.setLayoutData(gd);
		this.rulesTable = new TableViewer(ruleNamesComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		rulesTable.setContentProvider(new ArrayContentProvider());
		rulesTable.setLabelProvider(new RulesLabelProvider());
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL, GridData.FILL_VERTICAL, true, false);
//		gridData.verticalSpan = 3;
		gridData.heightHint = 120;
		gridData.widthHint = 550;
		rulesTable.getTable().setLayoutData(gridData);
	
		Composite buttonComposite = getWidgetFactory().createComposite(ruleNamesComposite);
		buttonComposite.setLayout(new GridLayout());
		buttonComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		createResourceButton(buttonComposite);
		createRemoveButton(buttonComposite);
		
		
		rulesTable.getTable().addListener(SWT.MouseHover, new Listener(){
			
			//final Cursor handcur=new Cursor(rulesTable.getTable().getFont().getDevice(),SWT.CURSOR_HAND);
			@Override
			public void handleEvent(Event event) {
					  //rulesTable.getTable().setToolTipText("double click to open in new editor");
					//  rulesTable.getTable().setCursor(handcur);
			}
		});
		

		rulesTable.getTable().addListener(SWT.MouseDown,new Listener(){

			@Override
			public void handleEvent(Event event) {
				String ext=CommonIndexUtils.RULE_EXTENSION;
				isInsideTableViewer(rulesTable,event,true,ext);
			}
		
		});
	}
	private void createResourceButton(Composite parent){
		ruleResourceButton = new Button(parent, SWT.FLAT);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		ruleResourceButton.setText(BpmnMessages.getString("browse_button_label")); //$NON-NLS-1$
		ruleResourceButton.setLayoutData(gd);
	}
	
	private void createRemoveButton(Composite parent){
		ruleRemoveButton = new Button(parent, SWT.FLAT);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		ruleRemoveButton.setText(BpmnMessages.getString("remove_label")); //$NON-NLS-1$
		ruleRemoveButton.setLayoutData(gd);
		ruleRemoveButton.setEnabled(false);
		ruleRemoveButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection tableSelection = rulesTable.getSelection();
				if (tableSelection instanceof StructuredSelection) {
					List list = ((StructuredSelection) tableSelection).toList();
					selectedRules.removeAll(list);
				}
				Map<String, Object> updateList = new HashMap<String, Object>();
				updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_RULES, selectedRules);
				updatePropertySection(updateList);
				rulesTable.setInput(selectedRules.toArray());
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		rulesTable.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ruleRemoveButton.setEnabled(!rulesTable.getSelection().isEmpty());
			}
		});
	}
	

	protected void refreshResouceWidget(EObjectWrapper<EClass, EObject> userObjWrapper){
		selectedRules = new ArrayList<String>();
		List<EObject> listAttribute = null;
		String attrName = getAttrNameForTaskSelection();
		if (userObjWrapper != null && attrName != null) {
			if (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper, attrName)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(userObjWrapper);
				if (valueWrapper != null) {
					if (valueWrapper.getAttribute(attrName) instanceof List)
						listAttribute = valueWrapper.getListAttribute(attrName);
				}
			}
		}
		if (listAttribute != null) {
			for (Object object : listAttribute) {
				if (object!= null && !object.toString().trim().isEmpty()) {
					selectedRules.add(object.toString());
				}
			}
		}
		
		Object[] array = selectedRules.toArray();
		rulesTable.setInput(array);
	}
	

	
	
	protected void addListenerToResourceWidget(){
		if (isResourcePropertyVisible()) {
			ruleResourceButton.addSelectionListener(this.ruleTreeListener);
		}
	}
	
	protected void removeListenerFromResourceWidget(){
		if (isResourcePropertyVisible()) {
			ruleResourceButton.removeSelectionListener(this.ruleTreeListener);
		}
	}
		
	
	public void resourceBrowse() {
		
		BERuleSelector selectionDialog = getRuleSeletionDialog();
		int status = selectionDialog.open();
		if (status == StudioResourceSelectionDialog.OK) {
			Set<Object> checkedElements = selectionDialog.getCheckedElements();
			if(checkedElements != null){
				List<String> paths = new ArrayList<String>();
				for (Object object : checkedElements) {
					if(object instanceof IFile ) {
						IResource res = (IFile) object;
						paths.add(IndexUtils.getFullPath(res));
					} else if (object instanceof SharedEntityElement) {
						paths.add(((SharedEntityElement) object).getSharedEntity().getFullPath());
					}	
				}
				
				
				selectedRules = paths;
				Object[] array = selectedRules.toArray();
				rulesTable.setInput(array);
				
				Map<String, Object> updateList = new HashMap<String, Object>();
				updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_RULES, selectedRules);
				updatePropertySection(updateList);
			}
		}
	}
	
	protected BERuleSelector getRuleSeletionDialog(){
		Object input = getProject();
		
		String project = ((IProject)input).getName();

		return new BERuleSelector(Display.getDefault().getActiveShell(), project,selectedRules, 
					new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE });
	}
	
	
	
	public class RuleSelectionWidgetListener extends SelectionAdapter {


    	@Override
    	public void widgetSelected(SelectionEvent e) {
    			resourceBrowse();
    	}
    }
	
	
	private class RulesLabelProvider extends LabelProvider implements IColorProvider {

		public Color getBackground(Object element) {
			return null;
		}

		public Color getForeground(Object element) {
			if(element instanceof String){
				DesignerElement desEle = IndexUtils.getElement(
						fProject.getName(), (String)element);
				if(desEle == null)
					return COLOR_RED;
			}
			return Display.getDefault().getSystemColor(
					SWT.COLOR_BLACK);
		}



		@Override
		public String getText(Object element) {
			if (element instanceof String) {
				return (String)element;
			}
			
			return "";
		}
	}
	

}