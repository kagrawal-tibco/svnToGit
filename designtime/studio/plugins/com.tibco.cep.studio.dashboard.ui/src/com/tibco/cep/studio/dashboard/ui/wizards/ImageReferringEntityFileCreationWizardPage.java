package com.tibco.cep.studio.dashboard.ui.wizards;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalHeader;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynImageURLType;
import com.tibco.cep.studio.dashboard.ui.dialogs.ImageSelectionDialog;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

class ImageReferringEntityFileCreationWizardPage extends DashboardEntityFileCreationWizard {

	protected Text txtImageURL;
	protected Button btnBrowse;

	protected IFile selectedImageResource;

	ImageReferringEntityFileCreationWizardPage(String pageName, IStructuredSelection selection, String type, String typeName) {
		super(pageName, selection, type, typeName);
	}

	@Override
	public void createTypeDescControl(Composite parent) {
		super.createTypeDescControl(parent);
		createLabel(parent, "Image");

		Composite control = (Composite) super.getControl();

		Composite imageBrowserComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;

		imageBrowserComposite.setLayout(layout);
		imageBrowserComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

		txtImageURL = new Text(imageBrowserComposite, SWT.BORDER);
		txtImageURL.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		txtImageURL.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}

		});

		btnBrowse = new Button(imageBrowserComposite, SWT.NONE);
		btnBrowse.setText(Messages.getString("Browse"));
		btnBrowse.addSelectionListener(new AbstractSelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				IResource resource = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
				if (getProject() == null) {
					// if(resource == null) {return;}
					if (resource != null) {
						if (resource instanceof IProject) {
							setProject((IProject) resource);
						} else {
							setProject(resource.getProject());
						}
					}
				}
				if (resource != null) {
					SynProperty property = ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.HEADER, LocalHeader.PROP_KEY_HEADER_IMAGE);
					String[] allowedImageTypes = ((SynImageURLType) property.getTypeDefinition()).getAllowedImageTypes();
					ImageSelectionDialog dialog = new ImageSelectionDialog(getShell(), getProject().getName(), allowedImageTypes);
					int status = dialog.open();
					if (status == ImageSelectionDialog.OK) {
						IResource imageResource = (IResource) dialog.getFirstResult();
						txtImageURL.setText("/" + imageResource.getProjectRelativePath().toString()); //$NON-NLS-1$
					}
				}
				validatePage();
			}

		});

		if (getContainerFullPath() != null) {
			IResource resource = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
			if (resource instanceof IProject) {
				setProject((IProject) resource);
			} else {
				setProject(resource.getProject());
			}
			IResource imageToUse = getImageToUse();
			if (imageToUse != null) {
				txtImageURL.setText("/" + imageToUse.getProjectRelativePath().toString()); //$NON-NLS-1$
			}
		}

		setControl(control);
	}

	@SuppressWarnings("rawtypes")
	private IResource getImageToUse() {
		if (_selection != null && _selection.isEmpty() == false) {
			Iterator iterator = _selection.iterator();
			while (iterator.hasNext()) {
				Object selectedObject = (Object) iterator.next();
				if (selectedObject instanceof IFile) {
					SynProperty property = ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.HEADER, LocalHeader.PROP_KEY_HEADER_IMAGE);
					String[] allowedImageTypes = ((SynImageURLType) property.getTypeDefinition()).getAllowedImageTypes();
					for (String imageType : allowedImageTypes) {
						if (imageType.equalsIgnoreCase(((IFile) selectedObject).getFileExtension()) == true) {
							return (IResource) selectedObject;
						}
					}
				}
			}
		}
		return null;
	}

	IFile getSelectedImageResource() {
		return selectedImageResource;
	}

	@Override
	protected boolean validatePage() {
		boolean validatePage = super.validatePage();
		if (validatePage == true) {
			selectedImageResource = null;
			String imagePath = txtImageURL.getText();
			if (imagePath != null && imagePath.trim().length() != 0) {
				IResource member = getProject().findMember(imagePath);
				if (member != null && member instanceof IFile) {
					SynProperty property = ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.HEADER, LocalHeader.PROP_KEY_HEADER_IMAGE);
					String[] allowedImageTypes = ((SynImageURLType) property.getTypeDefinition()).getAllowedImageTypes();
					for (String imageType : allowedImageTypes) {
						if (imageType.equalsIgnoreCase(((IFile) member).getFileExtension()) == true) {
							selectedImageResource = (IFile) member;
							break;
						}
					}
				}
				if (selectedImageResource == null) {
					setErrorMessage("Select image");
					return false;
				}
			}
			setPageComplete(true);
			return true;
		}
		return validatePage;
	}

}