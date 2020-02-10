package com.tibco.cep.decision.table.wizard;

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
import org.eclipse.jface.viewers.ILabelProviderListener;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

import com.tibco.cep.studio.ui.filter.ExtensionOnlyBasedFileInclusionFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * 
 * @author smarathe
 *
 */
public class GenerateDTClassWizardPage extends WizardPage {

	DirectoryDialog dialog;
	Label outputDirectoryLabel, extendedClassPathLabel, earPathLabel;
	Text outputDirectoryText, extendedClassPathText, earPathText;
	Button overwriteExistingClassesButton, outputDirectoryBrowseButton, extendedClassPathBrowseButton, addExtendedClassPathButton, earPathBrowseButton;
	RowLayout rowLayout;
	RowData labelLayoutData, textLayoutData, buttonLayoutData;
	Composite outputDirectoryComposite, overwriteExistingClassesComposite, extendedClassPathComposite, earPathComposite;
	private IStructuredSelection treeselection;
	
	private TreeViewer projectViewer = null;
	
    private List<ViewerFilter> viewerFilters = null;
    
    private INavigatorContentService navigatorService = null;
    
    protected Text resourceNameText; 
	
	protected GenerateDTClassWizardPage(String pageName, IStructuredSelection selection) {
		super(pageName);
		setPageComplete(false);
		treeselection = selection;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		createTreeViewerControls(composite);
		createOutputDirectoryControls(composite);
		createOverwriteExistingClassesControls(composite);
		createExtendedClassPath(composite);
		createEARPathControls(composite);
		setControl(composite);
	}
	

	
	private void createTreeViewerControls(Composite parent) {
		Composite treeViewerContainer = new Composite(parent, SWT.NULL);
		treeViewerContainer.setLayout(new FillLayout());

		GridData gData = new GridData(GridData.FILL_BOTH);
		gData.heightHint = 200;
		treeViewerContainer.setLayoutData(gData);
		IProject project = StudioResourceUtils.getCurrentProject(treeselection);
		if (project != null) {
		    addFilter(new StudioProjectsOnly(project.getName()));
		    Set<String> extensions = new HashSet<String>();
	        extensions.add("rulefunctionimpl");
	        addFilter(new ExtensionOnlyBasedFileInclusionFilter(extensions));
		    createTreeViewer(treeViewerContainer);
			projectViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
			
			resourceNameText = new Text(parent, SWT.BORDER);
			resourceNameText.setEnabled(false);
			resourceNameText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					setPageComplete(validatePage());
	
				}
			});
			addResourceSelector();
			setControl(parent);
			
			if (treeselection != null) {
				projectViewer.setSelection(treeselection);
				projectViewer.expandToLevel(treeselection.getFirstElement(), 2);
				treeViewerContainer.setEnabled(true);
			}
			
			setPageComplete(validatePage());
		}
		
	}
	
	  private boolean validatePage() {
			setErrorMessage(null);
			setMessage(null);
			if(resourceNameText.getText() == null || resourceNameText.getText().equals("")) {
				setErrorMessage("Select Decision Table");
				return false;
			}
			
			if(earPathText == null || earPathText.getText().equals("")) {
				return false;
			}
		return true;
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
                		 IFile file = (IFile)selected;
	                	 if (file.getFileExtension().equals("rulefunctionimpl")) {
	                		 IPath relativePath = file.getFullPath().removeFirstSegments(1);
	                		 relativePath = relativePath.removeFileExtension();
	                		 String resourcePath = "/" + relativePath.toString();
	                		 resourceNameText.setText(resourcePath);
	                	 } 
                	 } else {
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
            projectViewer = new TreeViewer(composite, SWT.BORDER | SWT.SINGLE);
            NavigatorContentServiceFactory fact = NavigatorContentServiceFactory.INSTANCE;
            navigatorService = fact.createContentService("com.tibco.cep.studio.projectexplorer.view", projectViewer);
            if (navigatorService != null) {
                INavigatorActivationService service = navigatorService.getActivationService();
                if (service != null) {
                    service.deactivateExtensions(new String[] { "org.eclipse.jdt.java.ui.javaContent" }, false);
                }
                projectViewer.setContentProvider(navigatorService.createCommonContentProvider());
                projectViewer.setLabelProvider(new DecoratingLabelProvider(navigatorService.createCommonLabelProvider(), 
                		                       new ILabelDecorator() {

												@Override
												public Image decorateImage(
														Image image,
														Object element) {
													// TODO Auto-generated method stub
													return null;
												}

												@Override
												public String decorateText(
														String text,
														Object element) {
													return text;
												}

												@Override
												public void addListener(
														ILabelProviderListener listener) {
													// TODO Auto-generated method stub
													
												}

												@Override
												public void dispose() {
													// TODO Auto-generated method stub
													
												}

												@Override
												public boolean isLabelProperty(
														Object element,
														String property) {
													// TODO Auto-generated method stub
													return false;
												}

												@Override
												public void removeListener(
														ILabelProviderListener listener) {
													// TODO Auto-generated method stub
													
												}
                	
                }));
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


	private void createEARPathControls(Composite parent) {
		earPathComposite = new Composite(parent, SWT.NONE);
		earPathComposite.setLayout(new GridLayout(3, false));
		earPathLabel = new Label(earPathComposite, SWT.NONE);
		earPathLabel.setText("EAR File Path  ");
		earPathLabel.setData(labelLayoutData);
		earPathText = new Text(earPathComposite, SWT.BORDER | SWT.SINGLE);
		earPathText.setData(textLayoutData);
		earPathText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
				
			}
			
		});
		earPathBrowseButton = new Button(earPathComposite, SWT.PUSH);
		earPathBrowseButton.setText("Browse...");
		earPathBrowseButton.setData(buttonLayoutData);
		earPathBrowseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(getShell());
				dlg.setFilterExtensions(new String[] {"*.EAR"});
				String dir = dlg.open();
				if (dir != null) {
					earPathText.setText(dir);
				}

			}
			
		});
		
	}


	private void createExtendedClassPath(Composite parent) {
		extendedClassPathComposite = new Composite(parent, SWT.NONE);
		extendedClassPathComposite.setLayout(new GridLayout(3, false));
		extendedClassPathLabel = new Label(extendedClassPathComposite, SWT.NONE);
		extendedClassPathLabel.setText("Jar to be added in classpath  ");
		extendedClassPathLabel.setData(labelLayoutData);
		extendedClassPathText = new Text(extendedClassPathComposite, SWT.BORDER | SWT.SINGLE);
		extendedClassPathText.setData(textLayoutData);
		extendedClassPathBrowseButton = new Button(extendedClassPathComposite, SWT.PUSH);
		extendedClassPathBrowseButton.setText("Browse...");
		extendedClassPathBrowseButton.setData(buttonLayoutData);
		extendedClassPathBrowseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(getShell());
				dlg.setFilterExtensions(new String[] {".jar"});
				String dir = dlg.open();
				if (dir != null) {
					extendedClassPathLabel.setText(extendedClassPathText.getText()+";"+dir);
				}

			}
			
		});
		
	}

	private void createOverwriteExistingClassesControls(Composite parent) {
		overwriteExistingClassesComposite = new Composite(parent, SWT.NONE);
		overwriteExistingClassesComposite.setLayout(new GridLayout(1, false));
		overwriteExistingClassesButton = new Button(parent, SWT.CHECK);
		overwriteExistingClassesButton.setText("Overwrite Existing Classes");
		overwriteExistingClassesButton.setData(buttonLayoutData);
	}

	private void createOutputDirectoryControls(Composite parent) {
		outputDirectoryComposite = new Composite(parent, SWT.NONE);
		outputDirectoryComposite.setLayout(new GridLayout(3, false));
		outputDirectoryLabel = new Label(outputDirectoryComposite, SWT.NONE);
		outputDirectoryLabel.setText("Output Directory  ");
		outputDirectoryLabel.setData(labelLayoutData);
		outputDirectoryText = new Text(outputDirectoryComposite, SWT.BORDER | SWT.SINGLE);
		outputDirectoryText.setData(textLayoutData);
		outputDirectoryBrowseButton = new Button(outputDirectoryComposite, SWT.PUSH);
		outputDirectoryBrowseButton.setText("Browse...");
		outputDirectoryBrowseButton.setData(buttonLayoutData);
		outputDirectoryBrowseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(getShell());
				String dir = dlg.open();
				if (dir != null) {
					outputDirectoryText.setText(dir);
				}

			}
			
		});
		
	}

	
	
	public String getOutputDirectory() {
		return outputDirectoryText.getText();
	}
	
	public String getOverWriteExistingClasses() {
		return overwriteExistingClassesButton.getSelection()?"true":"false";
	}
	
	public String getExtendedClasspath() {
		return extendedClassPathText.getText();
	}
	
	public String getEARSPath() {
		return earPathText.getText();
	}
	
	public String getDTPath() {
		return resourceNameText.getText();
	}

}
