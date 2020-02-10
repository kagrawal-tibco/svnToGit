package com.tibco.cep.studio.dashboard.ui.wizards.rolepreference;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalView;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.wizards.DashboardEntityFileCreationWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class RolePreferenceEntityFileCreationWizardPage extends DashboardEntityFileCreationWizard {

	protected Text txtView;
	protected Button btnBrowse;

	protected LocalView selectedLocalView;

	protected String roleName;

	public RolePreferenceEntityFileCreationWizardPage(String pageName, IStructuredSelection selection, String type, String typeName) {
		super(pageName, selection, type, typeName);
	}

	@Override
	public void createTypeDescControl(Composite parent) {
		super.createTypeDescControl(parent);
		createLabel(parent, "Role");
		final Text txtRole = createText(parent);
		txtRole.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				roleName = txtRole.getText();
			}
		});

		createLabel(parent, "View");

		Composite control = (Composite) super.getControl();

		Composite viewBrowserComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;

		viewBrowserComposite.setLayout(layout);
		viewBrowserComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

		txtView = new Text(viewBrowserComposite, SWT.BORDER);
		txtView.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		txtView.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}

		});

		btnBrowse = new Button(viewBrowserComposite, SWT.NONE);
		btnBrowse.setText(Messages.getString("Browse"));
		btnBrowse.addSelectionListener(new AbstractSelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				IResource resource = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
				if (getProject() == null) {
					if(resource != null) {
						if (resource instanceof IProject) {
							setProject((IProject) resource);
						} else {
							setProject(resource.getProject());
						}
					}
				}
				if(resource != null) {
					StudioResourceSelectionDialog selectionDialog = new StudioResourceSelectionDialog(getShell(), getProject().getName(), null, ELEMENT_TYPES.VIEW);
					int btnClicked = selectionDialog.open();
					if (btnClicked == StudioResourceSelectionDialog.OK) {
						IFile firstResult = (IFile) selectionDialog.getFirstResult();
						String path = firstResult.getProjectRelativePath().toString();
						path = path.replace(".view", "");
						txtView.setText("/" + path);
					}
				}
			}
		});

		if (getContainerFullPath() != null) {
			IResource resource = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
			if (resource instanceof IProject) {
				setProject((IProject) resource);
			} else {
				setProject(resource.getProject());
			}
			LocalView viewToUse = getViewToUse();
			if (viewToUse != null) {
				txtView.setText(viewToUse.getPropertyValue(LocalView.PROP_KEY_FOLDER)+viewToUse.getName());
			}
		}

		setControl(control);
	}

	@SuppressWarnings("rawtypes")
	private LocalView getViewToUse() {
		if (_selection != null) {
			Iterator selectionIterator = _selection.iterator();
			while (selectionIterator.hasNext()) {
				Object object = (Object) selectionIterator.next();
				if (object instanceof IResource) {
					DesignerElement element = IndexUtils.getElement((IResource) object);
					if (element != null && element.getElementType().compareTo(ELEMENT_TYPES.VIEW) == 0) {
						return (LocalView) LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(getProject()), ((EntityElement)element).getEntity());
					}
				}
			}
		}
		List<LocalElement> views = LocalECoreFactory.getInstance(getProject()).getChildren(BEViewsElementNames.VIEW);
		if (views.size() == 1) {
			return (LocalView) views.get(0);
		}
		return null;
	}

	public String getRoleName() {
		return roleName;
	}

	public LocalView getSelectedView() {
		return selectedLocalView;
	}

	@SuppressWarnings("rawtypes")
	public List<LocalElement> getSelectedComponents(){
		if (_selection != null) {
			List<LocalElement> components = new LinkedList<LocalElement>();
			Iterator iterator = _selection.iterator();
			while (iterator.hasNext()) {
				Object object = (Object) iterator.next();
				if (object instanceof IResource) {
					IResource resource = (IResource) object;
					//is the selection within the current project
					if (resource.getProject().equals(getProject()) == true) {
						//yes, it is, is it a series color
						DesignerElement element = IndexUtils.getElement(resource);
						if (element != null && element instanceof EntityElement) {
							EntityElement entityElement = (EntityElement) element;
							if (element.getElementType().compareTo(ELEMENT_TYPES.CHART_COMPONENT) == 0) {
								components.add(LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(getProject()), entityElement.getEntity()));
							}
							else if (element.getElementType().compareTo(ELEMENT_TYPES.TEXT_COMPONENT) == 0) {
								components.add(LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(getProject()), entityElement.getEntity()));
							}
						}
					}
				}
			}
			return components;
		}
		return Collections.emptyList();
	}


	@Override
	protected boolean validatePage() {
		boolean validatePage = super.validatePage();
		if (validatePage == true) {
			selectedLocalView = null;
			String viewPath = txtView.getText();
			if (viewPath != null && viewPath.trim().length() != 0) {
				EntityElement viewElement = (EntityElement) IndexUtils.getElement(getProject().getName(), viewPath, ELEMENT_TYPES.VIEW);
				if (viewElement != null) {
					Entity view = viewElement.getEntity();
					if (view != null) {
						selectedLocalView = (LocalView) LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(getProject()), view);
					}
				}
			}
			if (selectedLocalView == null) {
				setErrorMessage("Select View");
				return false;
			}
			setPageComplete(true);
			return true;
		}
		return validatePage;
	}
}
