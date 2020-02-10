package com.tibco.cep.bpmn.ui.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.bpmn.ui.BERuleSelector;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;


/**
 * 
 * @author majha
 *
 */
public class AttachedBERuleResourcePanel {
	public final static Color COLOR_RED = Display.getDefault().getSystemColor(SWT.COLOR_RED);
	public final static Color COLOR_BLACK = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	
	private AttachedBEResourceChangeListener listener;
	private Composite parent;
	private IProject project;
	private List<String> selectedRules;
	private TableViewer rulesTable;
	private Button ruleResourceButton;
	private RuleSelectionWidgetListener ruleTreeListener;

	public AttachedBERuleResourcePanel(AttachedBEResourceChangeListener listener, IProject project , Composite parent){
		this.listener = listener;
		this.parent = parent;
		this.project = project;

		ruleTreeListener = new RuleSelectionWidgetListener();
		createRuleResourcePanel();
	}
	
	
	protected void createRuleResourcePanel() {
		GridData gd = new GridData(/* GridData.FILL_HORIZONTAL */);
//		gd.widthHint = 562;
		parent.setLayoutData(gd);
		this.rulesTable = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		rulesTable.setContentProvider(new ArrayContentProvider());
		rulesTable.setLabelProvider(new RulesLabelProvider());
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalSpan = 3;
		gridData.widthHint = 562;
		rulesTable.getTable().setLayoutData(gridData);
		
		createResourceButton(parent);
		
		addListener();
	}


	private void createResourceButton(Composite parent){
		ruleResourceButton = new Button(parent, SWT.FLAT);
		GridData gd = new GridData(SWT.END, SWT.BEGINNING, false, false);
		ruleResourceButton.setText(BpmnMessages.getString("browse_label")); //$NON-NLS-1$
		ruleResourceButton.setLayoutData(gd);
	}
	
	

	
	public void selectionChanged(BpmnPaletteGroupItem paletteInfo) {
		selectedRules = new ArrayList<String>();
		String attachedResource = paletteInfo.getAttachedResource();
		if(attachedResource != null && !attachedResource.trim().isEmpty()){
			String[] split = attachedResource.split(",");
			for (String string : split) {
				selectedRules.add(string);
			}
		}
		
		Object[] array = selectedRules.toArray();
		rulesTable.setInput(array);
	}
	

	private void addListener() {
		ruleResourceButton.addSelectionListener(this.ruleTreeListener);
	}
	
	public void resourceBrowse() {
		
		BERuleSelector selectionDialog = getRuleSeletionDialog();
		int status = selectionDialog.open();
		if (status == StudioResourceSelectionDialog.OK) {
			Set<Object> checkedElements = selectionDialog.getCheckedElements();
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
			
			StringBuilder changedResource = new StringBuilder();
			for (String string : paths) {
				changedResource.append(string+",");
			}
			String string = changedResource.toString();
			if(string.endsWith(","))
				string = string.substring(0, string.length() -1);
			listener.resourceChanged(string);
		}
	}
	
	protected BERuleSelector getRuleSeletionDialog(){
		
		String projectName = project.getName();

		return new BERuleSelector(Display.getDefault().getActiveShell(), projectName,selectedRules, 
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
						project.getName(), (String)element);
				if(desEle == null)
					return COLOR_RED;
			}
			return COLOR_BLACK;
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
