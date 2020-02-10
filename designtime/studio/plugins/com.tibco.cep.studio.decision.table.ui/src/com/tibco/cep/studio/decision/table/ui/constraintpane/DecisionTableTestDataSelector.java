package com.tibco.cep.studio.decision.table.ui.constraintpane;

import static com.tibco.cep.studio.ui.editors.EditorsUIPlugin.PLUGIN_ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * @author vdhumal
 *
 */
public class DecisionTableTestDataSelector extends AbstractResourceElementSelector implements ISelectionStatusValidator {

	private String currentProjectName;
	private com.tibco.cep.decision.table.model.dtmodel.Table decisionTable;
	private Map<String, List<String>> selectedTestDataFiles;
	
	private Table argumentResourceTable;
	private Tree treeWidget;
	private EntityTestDataFilter entityTestDataFilter;
	
	public DecisionTableTestDataSelector(Shell parent,
            String currentProjectName,
            com.tibco.cep.decision.table.model.dtmodel.Table decisionTable) {
		super(parent);
		setTitle("Check TestData Coverage");
		this.setAllowMultiple(false);
		this.currentProjectName = currentProjectName;
		this.decisionTable = decisionTable;
		this.selectedTestDataFiles = new HashMap<String, List<String>>();
		setValidator(this);
		
	}

	@Override
	    protected Control createDialogArea(Composite parent) {
		// create a composite with standard margins and spacing
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(composite);
	
		Label label = new Label(composite, SWT.NONE);
		label.setText("Select Test Data");
		
		argumentResourceTable = new Table(composite, SWT.BORDER | SWT.SINGLE);
		argumentResourceTable.setLayout(new GridLayout());
		argumentResourceTable.addSelectionListener(new SelectionAdapter(){
	
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					resetWidget();
					List<String> argumentResources = new ArrayList<String>(); 
					TableItem[]  items = argumentResourceTable.getSelection();
					for (int i = 0; i < items.length; i++) {
						String text = items[0].getText();
						argumentResources.add(text);
					}	
					resetTestDataViewer(argumentResources);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}});
	
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		data.heightHint = 80;
		argumentResourceTable.setLayoutData(data);
		argumentResourceTable.setFont(new Font(Display.getDefault(), "Tahoma", 10, SWT.NORMAL));
		TableColumn typeColumn = new TableColumn(argumentResourceTable, SWT.NULL);
		typeColumn.setWidth(convertWidthInCharsToPixels(fWidth));
		
		updateArgumentResourceTable(decisionTable.getArgument());
	
		// Create a message and tree view area
		// messageLabel = createMessageArea(composite);
		createTreeViewer(composite);

		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		data.heightHint = convertHeightInCharsToPixels(fHeight);
	
		treeWidget = treeViewer.getTree();
		treeWidget.setLayoutData(data);
		treeWidget.setFont(parent.getFont());
	
		// If the tree is empty then disable the controls
		if (treeIsEmpty) {
			//messageLabel.setEnabled(false);
			treeWidget.setEnabled(false);
			updateStatus(new Status(Status.OK, PLUGIN_ID, Status.OK, emptyListMessage, null));
		}
		
		return composite;
	}	

	/* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
    public IStatus validate(Object[] selection) {
    	if (selection != null) {
        	Map<String, String> testDataFilesEntityMap = entityTestDataFilter.getTestDataFilesEntityMap();
    		for (int i = 0; i < selection.length; i++) {
	    		if (selection[i] instanceof IFile) {
	    			String testDataFilePath = ((IFile) selection[i]).getProjectRelativePath().toString();
	    			String entityPath = testDataFilesEntityMap.get(testDataFilePath);
	    			List<String> testDataFilesList = selectedTestDataFiles.get(entityPath);
	    			if (testDataFilesList == null) {
	    				testDataFilesList = new ArrayList<String>();
		    			selectedTestDataFiles.put(entityPath, testDataFilesList);
	    			}
	    			testDataFilesList.add(testDataFilePath);
//	    			resource = true;
	    		}
    		}
			return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, /*statusMessage*/null, null);
    	}
    	return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID,
        				  Status.ERROR, Messages.getString("resource_Selector_Error_Message"), null);
    }

	/**
	 * @return
	 */
	public Map<String, List<String>> getSelectedTestDataFiles() {
		return selectedTestDataFiles;
	}
    
	/**
	 * @param extension
	 */
	private void resetTestDataViewer(List<String> argumentResources){
		treeWidget.setEnabled(true);
		Set<String> extensions = new HashSet<String>();
		extensions.add("concepttestdata");
		extensions.add("eventtestdata");
		for(ViewerFilter filter: treeViewer.getFilters()){
			treeViewer.removeFilter(filter);
		}
		treeViewer.addFilter(new StudioProjectsOnly(currentProjectName));
				
		treeViewer.addFilter(new OnlyFileInclusionFilter(extensions));
		
		entityTestDataFilter = new EntityTestDataFilter(extensions, argumentResources);
		treeViewer.addFilter(entityTestDataFilter);
			
		treeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
		treeViewer.refresh();
		treeIsEmpty = (treeViewer.getTree().getItemCount() == 0);
		
		updateStatus(new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID,
				  Status.ERROR, Messages.getString("resource_Selector_Error_Message"), null));
	}
	
	/**
	 * 
	 */
	private void resetWidget(){
		treeViewer.setInput(null);
		treeViewer.refresh();
		treeWidget.setEnabled(false);
		getOkButton().setEnabled(false);
	}

	/**
	 * @param argumentResources
	 */
	private void updateArgumentResourceTable(EList<Argument> argumentResources){
		TableItem tableItem = null;
		Iterator<Argument> argResourcesItr = argumentResources.iterator();
		while (argResourcesItr.hasNext()) {
			Argument argumentResource = argResourcesItr.next();
			if (ResourceType.CONCEPT.equals(argumentResource.getProperty().getResourceType())
					|| ResourceType.EVENT.equals(argumentResource.getProperty().getResourceType())) {
				tableItem = new TableItem(argumentResourceTable, SWT.NONE);
				tableItem.setText(argumentResource.getProperty().getPath());
			}	
		}
	}
		
}
