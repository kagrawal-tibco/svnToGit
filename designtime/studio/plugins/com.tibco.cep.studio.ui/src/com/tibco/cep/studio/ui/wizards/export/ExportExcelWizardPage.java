/**
 * 
 */
package com.tibco.cep.studio.ui.wizards.export;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.ui.filter.ExtensionOnlyBasedFileInclusionFilter;
import com.tibco.cep.studio.ui.filter.ProjectDependenciesExclusionFilter;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * @author aathalye
 *
 */
public abstract class ExportExcelWizardPage<E extends ExportExcelWizard<E, L>, L extends ILabelDecorator> extends WizardPage {
	
	/**
	 * The location for the Excel File
	 */
	private Text fileLocationText;
	
	/**
	 * The selection for decision table
	 */
	private IStructuredSelection treeselection;
	
	private TreeViewer projectViewer = null;
	
    private List<ViewerFilter> viewerFilters = null;
    
    protected L labelDecorator;
    
    private INavigatorContentService navigatorService = null;
    
    protected Text resourceNameText; 
    
    protected boolean isTestData=false;
    
    /**
     * The resource type to import e.g: Decision Table/Domain Model
     */
    protected String resourceType;
    protected String pathWithExtension;
    
    IFile selectedFile;
    
    
    /**
     * File extension of resource to export
     */
    protected String resourceFileExtension;
    protected List<String> resourceFileExtensionsforTestData=new ArrayList<String>();
	
	public ExportExcelWizardPage(String pageName, 
			                     IWorkbenchWindow window,
			                     IStructuredSelection treeselection) {
		super(pageName);
		setTitle(Messages.getString("Export.Excel.Page"));
		this.treeselection = treeselection;
	}
	
		
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		setPageComplete(false);

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		createLabel(container, Messages.getString("Export.Excel.Select.File"));

		Composite container1 = new Composite(container, SWT.NONE);
		container1.setLayout(new GridLayout(2, false));
		container1.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		fileLocationText = createText(container1);
		GridData data_fileLocationText = new GridData(GridData.FILL_HORIZONTAL);
		fileLocationText.setLayoutData(data_fileLocationText);
		
		Button browseButton = new Button(container1, SWT.NULL);
		browseButton.setText("Browse...");
		
		browseButton.addSelectionListener(new SelectionListener(){

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Brows Button is clicked				
				FileDialog fileDialog = new FileDialog(parent.getShell());		
				String[] extensions = { "*.xls"};
				//fileDialog.setText(Messages.DT_ImportExcel_Action_Dialog_Title);
				fileDialog.setFilterExtensions(extensions);
				if (fileLocationText != null) {
					fileDialog.setFileName(fileLocationText.getText());
					String fileName = fileDialog.open();
					if (fileName != null && !fileName.endsWith(".xls")) {
						fileName = fileName + ".xls";
					}
					if(fileName!=null)
						fileLocationText.setText(fileName);
				} else {
					String fullFilename = fileDialog.open();
					if (fullFilename != null) {
						if (!fullFilename.endsWith(".xls")) {
							fullFilename = fullFilename + ".xls";
						}
						if (fullFilename != null) {
							fileLocationText.setText(fullFilename);
						}
					}
				}
			}
			
		});
		
		fileLocationText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}

		});
		
		createLabel(container, Messages.getString("Export.Excel.Select.Resource", resourceType));
		Composite treeViewerContainer = new Composite(container, SWT.NULL);
		treeViewerContainer.setLayout(new FillLayout());

		GridData gData = new GridData(GridData.FILL_BOTH);
		gData.heightHint = 200;
		treeViewerContainer.setLayoutData(gData);
		IProject project = StudioResourceUtils.getCurrentProject(treeselection);
		if (project != null) {
		    addFilter(new ProjectDependenciesExclusionFilter(project.getName()));
		    Set<String> extensions = new HashSet<String>();
		    if(resourceFileExtension!=null){
		    	extensions.add(resourceFileExtension);
		    }else{
		    	extensions.addAll(resourceFileExtensionsforTestData);
		    }
//		    if(isTestData){
//	        	for(String str:resourceFileExtensionsforTestData){
//	        		extensions.add(str);
//	        	}
//	        }
	        addFilter(new ExtensionOnlyBasedFileInclusionFilter(extensions));
	        createTreeViewer(treeViewerContainer);
			projectViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
			
			resourceNameText = createText(container);
			resourceNameText.setEnabled(false);
			resourceNameText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					setPageComplete(validatePage());
	
				}
			});
			addResourceSelector();
			setControl(container);
			
			if (treeselection != null) {
				projectViewer.setSelection(treeselection);
				projectViewer.expandToLevel(treeselection.getFirstElement(), 2);
				treeViewerContainer.setEnabled(true);
			}
			
			setPageComplete(validatePage());
		}
	}
	
	protected Label createLabel(Composite container, String label) {
		return createLabel(container, label, 0);
	}

	protected Label createLabel(Composite container, String labelstr, int indent) {
		Label label = new Label(container, SWT.NONE);
		GridData gData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gData.horizontalIndent = indent;
		label.setLayoutData(gData);
		label.setText(labelstr);
		return label;
	}

	protected Text createText(Composite container) {
		Text text = new Text(container, SWT.BORDER);
		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
		gData.widthHint = 150;
		text.setLayoutData(gData);
		return text;
	}
	
	protected void addResourceSelector() {
		projectViewer.addSelectionChangedListener(new ISelectionChangedListener() {

             

			public void selectionChanged(
                     SelectionChangedEvent event) {
            	 ISelection selection = event.getSelection();
                 if (selection instanceof TreeSelection) {
                	 TreeSelection treeSelection = (TreeSelection)selection;
                	 
                	 //Get first element
                	 Object selected = treeSelection.getFirstElement();
                	 if (selected instanceof IFile) {
                		 selectedFile = (IFile)selected;
                		 
	                	 if (selectedFile.getFileExtension().equals(resourceFileExtension)||resourceFileExtensionsforTestData.contains(selectedFile.getFileExtension().toString())) {
	                		 IPath relativePath = selectedFile.getFullPath().removeFirstSegments(1);
	                		 pathWithExtension=relativePath.toString();
	                		 relativePath = relativePath.removeFileExtension();
	                		 String resourcePath = "/" + relativePath.toString();
	                		 resourceNameText.setText(resourcePath);
	                	 } 
	                	 
	                	 
                	 } else if (selected instanceof SharedEntityElement) {
                		 String fileName = ((SharedEntityElement) selected).getFileName();
                		 int idx = fileName.lastIndexOf('.');
                		 if (idx > 0) {
                			 String ext = fileName.substring(idx+1);
    	                	 if (ext.equals(resourceFileExtension)) {
    	                		 String resourcePath = ((SharedEntityElement) selected).getSharedEntity().getFullPath();
    	                		 resourceNameText.setText(resourcePath);
    	                	 } 
                		 }
                	 }else {
                		 resourceNameText.setText("");
                	 }
                 }
             }
         });
	}
	
	 /**
     * @param composite
     * @return
     */
    private TreeViewer createTreeViewer(Composite composite) {
        if (projectViewer == null) {
            projectViewer = new CommonViewer(null , composite, SWT.BORDER | SWT.SINGLE);
            NavigatorContentServiceFactory fact = NavigatorContentServiceFactory.INSTANCE;
            navigatorService = fact.createContentService("com.tibco.cep.studio.projectexplorer.view", projectViewer);
            if (navigatorService != null) {
                INavigatorActivationService service = navigatorService.getActivationService();
                if (service != null) {
                    service.deactivateExtensions(new String[] { "org.eclipse.jdt.java.ui.javaContent" }, false);
                }
                projectViewer.setContentProvider(navigatorService.createCommonContentProvider());
                projectViewer.setLabelProvider(new DecoratingLabelProvider(navigatorService.createCommonLabelProvider(), 
                		                       labelDecorator));
                if (viewerFilters != null) {
                    for (ViewerFilter filter : viewerFilters) {
                        projectViewer.addFilter(filter);
                    }
                }
            }
        }
        return projectViewer;
    }
	
	 /**
     * @param filter
     */
    public void addFilter(ViewerFilter filter) {
        if (viewerFilters == null) {
            viewerFilters = new ArrayList<ViewerFilter>();
        }
        viewerFilters.add(filter);

    }
    
    private boolean validatePage() {
		boolean ret = false;
		boolean fname = false;
		setErrorMessage(null);
		setMessage(null);
		String fullFileName = fileLocationText.getText();
		String resourcePath = "";
		
		if (resourceNameText.getText() != null)
			resourcePath = resourceNameText.getText();

		if (fullFileName.length() > 0) {
			if (fullFileName.endsWith(".xls")) {
				fname = true;
			} else {
				fname = false;
			}
		}
		
		boolean tmp = (resourcePath.length() == 0) ? false : true;

		if (!fname && !tmp) {
			setErrorMessage("Enter Excel File Path, and resource to export");
			ret = false;
		}
		if (fname && !tmp) {
			setErrorMessage("Select resource To Export");
			ret = false;
		}
		
		if (!fname && tmp) {
			setErrorMessage("Enter Excel File Path");
			ret = false;
		}
		if (fname && tmp) {
			ret = true;
		}
		if (ret)
			setErrorMessage(null);
		return ret;
	}
    
    public String getExportFilePath() {
    	return fileLocationText.getText();
    }
    
    public String getResourcePath() {
    	return resourceNameText.getText();
    }
}
