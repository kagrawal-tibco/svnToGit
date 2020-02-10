package com.tibco.cep.studio.dashboard.ui.wizards.datasource;

import java.util.Iterator;
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
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.wizards.DashboardEntityFileCreationWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class DataSourceEntityFileCreationWizardPage extends DashboardEntityFileCreationWizard {

	protected Text txtMetric;
	protected Button btnBrowse;

	protected LocalMetric selectedLocalMetric;

	public DataSourceEntityFileCreationWizardPage(String pageName, IStructuredSelection selection, String type, String typeName) {
		super(pageName, selection, type, typeName);
	}

	@Override
	public void createTypeDescControl(Composite parent) {
		super.createTypeDescControl(parent);
		createLabel(parent, "Metric");

		Composite control = (Composite) super.getControl();

		Composite metricBrowserComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;

		metricBrowserComposite.setLayout(layout);
		metricBrowserComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

		txtMetric = new Text(metricBrowserComposite, SWT.BORDER);
		txtMetric.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		txtMetric.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}

		});

		btnBrowse = new Button(metricBrowserComposite, SWT.NONE);
		btnBrowse.setText(Messages.getString("Browse"));
		btnBrowse.addSelectionListener(new AbstractSelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				IResource resource = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
				if (getProject() == null) {
//					if(resource == null) {return;}
					if(resource != null) {
						if (resource instanceof IProject) {
							setProject((IProject) resource);
						} else {
							setProject(resource.getProject());
						}
					}
				}
				if(resource!= null) {
					StudioResourceSelectionDialog selectionDialog = new StudioResourceSelectionDialog(getShell(), getProject().getName(), null, ELEMENT_TYPES.METRIC);
					int btnClicked = selectionDialog.open();
					if (btnClicked == StudioResourceSelectionDialog.OK) {
						IFile firstResult = (IFile) selectionDialog.getFirstResult();
						String path = firstResult.getProjectRelativePath().toString();
						path = path.replace(".metric", "");
						txtMetric.setText("/" + path);
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
			LocalMetric metricToUse = getMetricToUse();
			if (metricToUse != null) {
				txtMetric.setText(metricToUse.getPropertyValue(LocalMetric.PROP_KEY_FOLDER)+metricToUse.getName());
			}
		}

		setControl(control);
	}

	@SuppressWarnings("rawtypes")
	private LocalMetric getMetricToUse() {
		if (_selection != null && _selection.isEmpty() == false) {
			Iterator selectionIterator = _selection.iterator();
			while (selectionIterator.hasNext()) {
				Object selectedObject = (Object) selectionIterator.next();
				if (selectedObject instanceof IResource) {
					DesignerElement element = IndexUtils.getElement((IResource) selectedObject);
					if (element != null && element.getElementType().compareTo(ELEMENT_TYPES.METRIC) == 0) {
						return (LocalMetric) LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(getProject()), ((EntityElement)element).getEntity());
					}
				}
			}
		}
		List<LocalElement> metrics = LocalECoreFactory.getInstance(getProject()).getChildren(BEViewsElementNames.METRIC);
		if (metrics.isEmpty() == false) {
			return (LocalMetric) metrics.get(0);
		}
		return null;
	}

	public LocalMetric getSelectedMetric() {
		return selectedLocalMetric;
	}


	@Override
	protected boolean validatePage() {
		boolean validatePage = super.validatePage();
		if (validatePage == true) {
			selectedLocalMetric = null;
			String metricPath = txtMetric.getText();
			if (metricPath != null && metricPath.trim().length() != 0) {
				EntityElement metricElement = (EntityElement) IndexUtils.getElement(getProject().getName(), metricPath, ELEMENT_TYPES.METRIC);
				if (metricElement != null) {
					Entity metric = metricElement.getEntity();
					if (metric != null) {
						selectedLocalMetric = (LocalMetric) LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(getProject()), metric);
					}
				}
			}
			if (selectedLocalMetric == null) {
				setErrorMessage("Select metric");
				return false;
			}
			setPageComplete(true);
			return true;
		}
		return validatePage;
	}
}
