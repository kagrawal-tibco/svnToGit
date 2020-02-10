package com.tibco.cep.studio.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.dialog.StudioProjectSelector;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;
import com.tibco.cep.studio.ui.util.DisplayModelControl;
import com.tibco.cep.studio.ui.util.Messages;


/*
@author ssailapp
@date Sep 21, 2009 2:30:13 PM
 */

public class NewDisplayModelNamePage extends WizardPage {

	private Text entityText;
	private Button entityBrowseButton;
	private Combo languageCombo;
	private Combo countryCombo;
	private String[] isoLanguages;
	private String[] isoCountries;
	private IStructuredSelection selection;
	private Text projectText;
	private Button projectBrowseButton;
	
    public NewDisplayModelNamePage(IStructuredSelection selection) {
        super("NewDisplayModelFileWizardPage");
        this.selection = selection;
        setTitle(Messages.getString("new.displaymodel.wizard.title"));
        setDescription(Messages.getString("new.displaymodel.wizard.desc"));
        setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/cdd_48x48.gif"));
    }

    protected boolean validatePage() {
    	boolean valid = true;
    	setMessage(null);
    	setErrorMessage(null);
    	valid &= checkProjectName();
    	if (!valid) {
    		setPageComplete(valid);
    		return false;
    	}
    	valid &= checkTargetEntity();
    	if (!valid) {
    		setPageComplete(valid);
    		return false;
    	}
    	valid &= checkDuplicateResource();
    	setPageComplete(valid);
    	return valid;
    }
    
	private boolean checkDuplicateResource() {
		IPath fullPath = getFullPath();
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(fullPath);
		if (file.exists()) {
			setErrorMessage("Display Model already exists");
			return false;
		}
		return true;
	}

	private boolean checkTargetEntity() {
		String entityName = entityText.getText().trim();
		if (entityName.trim().length() == 0) {
			setMessage("Enter the target entity");
			return false;
		}
		String projName = projectText.getText().trim();
		DesignerElement element = IndexUtils.getElement(projName, entityName);
		if (element == null || element instanceof Folder) {
			setErrorMessage("Target entity does not exist");
			return false;
		}
		return true;
	}

	private boolean checkProjectName() {
		String projName = projectText.getText().trim();
		if (projName.length() == 0) {
			setMessage("Enter the project name");
			return false;
		}
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
		if (!project.exists()) {
			setErrorMessage("Project does not exist");
			return false;
		}
		return true;
	}

	public IPath getFullPath() {
		String projName = projectText.getText().trim();
		String fileName = getFileName();
		return new Path(projName).append(fileName);
	}

	protected Label createLabel(Composite container, String labelstr) {
		Label label = new Label(container, SWT.NONE);
		GridData gData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
//		gData.horizontalSpan = 2;
		label.setLayoutData(gData);
		label.setText(labelstr);
		return label;
	}

	public String getFileName() {
    	DisplayModelControl control = new DisplayModelControl();
    	String entityName = entityText.getText().trim();
    	String lang = "";
    	if (languageCombo.getSelectionIndex() >= 0) {
    		lang = isoLanguages[languageCombo.getSelectionIndex()];
    	}
    	String country = "";
    	if (countryCombo.getSelectionIndex() >= 0) {
    		country = isoCountries[countryCombo.getSelectionIndex()];
    	}
    	Locale l = new Locale(lang, country);
    	String bundleName = control.toBundleName(entityName, l);
    	return control.toResourceName(bundleName, "display");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite childContainer = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		childContainer.setLayout(layout);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		createLabel(childContainer, Messages.getString("new.displaymodel.wizard.project"));
		
		projectText = new Text(childContainer, SWT.BORDER);
		projectText.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		projectText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});

		Button projectBrowseButton = new Button(childContainer, SWT.NONE);
		projectBrowseButton.setText(Messages.getString("Browse"));
		projectBrowseButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseProjects();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		createLabel(childContainer, Messages.getString("new.displaymodel.wizard.select"));

		entityText = new Text(childContainer, SWT.BORDER);
		entityText.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		entityText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});

		Button entityBrowseButton = new Button(childContainer, SWT.NONE);
		entityBrowseButton.setText(Messages.getString("Browse"));
		entityBrowseButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseEntities();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		createLabel(childContainer, Messages.getString("new.displaymodel.wizard.language"));
		languageCombo = new Combo(childContainer, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		languageCombo.setLayoutData(gd);
		isoLanguages = Locale.getISOLanguages();
		String[] langInput = new String[isoLanguages.length];
		for (int i=0; i<isoLanguages.length; i++) {
			String lang = isoLanguages[i];
			String language = new Locale(lang).getDisplayLanguage();
			langInput[i] = lang + " ("+language+")";
		}
		languageCombo.setItems(langInput);
		languageCombo.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});

		createLabel(childContainer, Messages.getString("new.displaymodel.wizard.country"));
		countryCombo = new Combo(childContainer, SWT.DROP_DOWN | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		countryCombo.setLayoutData(gd);
		isoCountries = Locale.getISOCountries();
		String[] countryInput = new String[isoCountries.length];
		for (int i=0; i<isoCountries.length; i++) {
			String co = isoCountries[i];
			String country = new Locale("", co).getDisplayCountry();
			countryInput[i] = co + " ("+country+")";
		}
		countryCombo.setItems(countryInput);
		countryCombo.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		
		if (this.selection instanceof StructuredSelection) {
			Object element = this.selection.getFirstElement();
			if (element instanceof IResource) {
				projectText.setText(((IResource) element).getProject().getName());
				if (element instanceof IFile) {
					DesignerElement el = IndexUtils.getElement((IResource) element);
					if (el instanceof EntityElement) {
						entityText.setText(((EntityElement) el).getEntity().getFullPath());
					}
				}
			}
		}
		
		validatePage();
		setControl(container);
	}

    protected void browseProjects() {
		String projName = projectText.getText();
		if (projName.isEmpty()) {
			projName = null;
		}
		StudioProjectSelector dialog = new StudioProjectSelector(getShell(),projName, false);
		if (dialog.open() == IStatus.OK) {
			Object[] result = dialog.getResult();
			if (result != null && result.length > 0) {
				Object obj = result[0];
				if (obj instanceof IProject) {
					IProject proj = (IProject) obj;
					projectText.setText(proj.getName());
				} else {
					return;
				}
			}
		}
	}

	protected void browseEntities() {
		try {
			String projName = projectText.getText();
			if (projName.isEmpty()) {
				return;
			}
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
			if (!project.isAccessible()) {
				return;
			}
			StudioResourceSelectionDialog dialog = new StudioResourceSelectionDialog(getShell(), project.getName(),entityText.getText(), new ELEMENT_TYPES[] { ELEMENT_TYPES.CONCEPT, ELEMENT_TYPES.SIMPLE_EVENT });
			dialog.setInput(project);
			if (dialog.open() == IStatus.OK) {
				Object[] result = dialog.getResult();
				if (result != null && result.length > 0) {
					Object obj = result[0];
					if (obj instanceof IFile) {
						IFile file = (IFile) obj;
						String fullPath = IndexUtils.getFullPath(file);
						entityText.setText(fullPath);
					} else if (obj instanceof SharedEntityElement) {
						Entity sharedEntity = ((SharedEntityElement) obj).getSharedEntity();
						if(sharedEntity != null){
							entityText.setText(sharedEntity.getFullPath());
						}
					} else{
						return;
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setVisible(boolean visible) {
        getControl().setVisible(visible);
    }
    
    public boolean canFlipToNextPage() {
       	return false;
    }

	public IFile createNewFile() {
		IFile newFile = null;
		final IPath newFilePath = getFullPath();
		final IFile newFileHandle = ResourcesPlugin.getWorkspace().getRoot().getFile(newFilePath);

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				CreateFileOperation op = new CreateFileOperation(newFileHandle,
						null, null,
						"New File");
				try {
					PlatformUI.getWorkbench().getOperationSupport()
							.getOperationHistory().execute(
									op,
									monitor,
									WorkspaceUndoUtil
											.getUIInfoAdapter(getShell()));
				} catch (final ExecutionException e) {
					getContainer().getShell().getDisplay().syncExec(
							new Runnable() {
								public void run() {
									if (e.getCause() instanceof CoreException) {
										ErrorDialog
												.openError(
														getContainer()
																.getShell(),"Creation Problems",
														null,  ((CoreException) e
																.getCause())
																.getStatus());
									} else {
										MessageDialog.openError(getContainer().getShell(),
														"Creation Problems",
														"Internal error: " +
													    e.getCause().getMessage());
									}
								}
							});
				}
			}
		};
		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			MessageDialog.openError(getContainer().getShell(),
							"Creation problems",
							"Internal error:" +
							e.getTargetException().getMessage());

			return null;
		}

		newFile = newFileHandle;

		return newFile;
	}

}