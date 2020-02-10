package com.tibco.cep.studio.ui.wizards.export;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.cep.studio.ui.util.Messages;

public class ProjectLibraryExportDependencyPage extends WizardPage {

	private Table fDependentResources;

	protected ProjectLibraryExportDependencyPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		setTitle(Messages.getString("project.library.export.page.title"));
		setMessage(Messages.getString("project.library.export.dependency.description"));
		getShell().setText("Export");

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		fDependentResources = new Table(composite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		TableColumn resourcesCol = new TableColumn(fDependentResources, SWT.BORDER);
		resourcesCol.setText("Resource");
		resourcesCol.setWidth(500);
		fDependentResources.setLayoutData(new GridData(GridData.FILL_BOTH));
		setControl(composite);
	}

	@Override
	public boolean canFlipToNextPage() {
		return false;
	}

	@Override
	public boolean isPageComplete() {
		return true;
	}

	@SuppressWarnings("rawtypes")
	public void setDependentResources(java.util.List selectedResources, java.util.List<IResource> dependentResources) {
		fDependentResources.removeAll();
		for (IResource resource : dependentResources) {
			if (!selectedResources.contains(resource)) {
				TableItem item = new TableItem(fDependentResources, SWT.NULL);
				item.setText(resource.getFullPath().toString());
				Image i = WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider().getImage(resource);
				item.setImage(i);
			}
		}
	}

	public int getFilteredDependentResourcesSize() {
		return fDependentResources == null ? -1 : fDependentResources.getItemCount();
	}
	
}
