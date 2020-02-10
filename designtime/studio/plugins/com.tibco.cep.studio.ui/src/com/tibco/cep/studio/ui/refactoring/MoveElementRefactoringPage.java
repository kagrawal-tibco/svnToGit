package com.tibco.cep.studio.ui.refactoring;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.MoveRefactoring;
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.model.WorkbenchViewerComparator;

import com.tibco.cep.studio.core.refactoring.EntityMoveProcessor;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class MoveElementRefactoringPage extends UserInputWizardPage {

	private TreeViewer fDestinationField;
	private String fProjectName;

	protected MoveElementRefactoringPage(String name, String projectName) {
		super(name);
		this.fProjectName = projectName;
	}

	@Override
	protected Refactoring getRefactoring() {
		return super.getRefactoring();
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		
		Composite composite= new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setFont(parent.getFont());

		Label label= new Label(composite, SWT.NONE);
		RefactoringProcessor processor = ((ProcessorBasedRefactoring)getRefactoring()).getProcessor();
		IResource resourceToMove = (IResource) processor.getAdapter(IResource.class);
		
		label.setText("Choose destination for '"+resourceToMove.getName()+"'");
		label.setLayoutData(new GridData());
		
		fDestinationField= new TreeViewer(composite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData gd= new GridData(GridData.FILL, GridData.FILL, true, true, 2, 1);
		gd.widthHint= convertWidthInCharsToPixels(40);
		gd.heightHint= convertHeightInCharsToPixels(15);
		fDestinationField.getTree().setLayoutData(gd);
		fDestinationField.setLabelProvider(new WorkbenchLabelProvider());
		fDestinationField.setContentProvider(new BaseWorkbenchContentProvider());
		fDestinationField.setComparator(new WorkbenchViewerComparator());
		fDestinationField.setInput(ResourcesPlugin.getWorkspace().getRoot());
		fDestinationField.addFilter(new ViewerFilter() {
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (element instanceof IProject) {
					IProject project= (IProject) element;
					if (!project.getName().equals(fProjectName)) {
						return false;
					}
					return project.isAccessible();
				} else if (element instanceof IFolder) {
					if (((IFolder)element).getName().equals("defaultVars") ) {
						return false;
					}
					return true;
				}
				return false;
			}
		});
		fDestinationField.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				validatePage();
			}
		});
		fDestinationField.setSelection(new StructuredSelection(resourceToMove));
		setPageComplete(false);
		setControl(composite);
	}
	
	@Override
	public IWizardPage getNextPage() {

		IStructuredSelection selection= (IStructuredSelection) fDestinationField.getSelection();
		Object firstElement= selection.getFirstElement();
		if (firstElement instanceof IContainer) {
			EntityMoveProcessor processor = (EntityMoveProcessor) ((ProcessorBasedRefactoring)getRefactoring()).getProcessor();
			String resourceName = processor.getResource().getName();
			IPath path = new Path(resourceName);
			if (((IContainer) firstElement).getFile(path).exists()) {
				if (!MessageDialog.openQuestion(getShell(), "Resource exists", "Resource '"+resourceName+"' already exists in this folder.  Do you want to overwrite?")) {
					return this;
				}
			}
		}
		return super.getNextPage();
	}

	private final void validatePage() {
		RefactoringStatus status = new RefactoringStatus();
		
		IStructuredSelection selection= (IStructuredSelection) fDestinationField.getSelection();
		Object firstElement= selection.getFirstElement();
		if (firstElement instanceof IContainer) {
//			status= fRefactoringProcessor.validateDestination((IContainer) firstElement);
			IContainer container = (IContainer) firstElement;
			IPath path = container.getFullPath();
			EntityMoveProcessor processor = (EntityMoveProcessor) ((ProcessorBasedRefactoring)getRefactoring()).getProcessor();
			if (processor.getResource().getParent().equals(firstElement)) {
				status.addFatalError("Source and target locations are the same.  Please select a different location");
			} if (container.getName().equals("defaultVars")) {
				status.addFatalError("Resource can't be moved to the \"defaultVars\" location.  Please select a different location");
			} else {
				//Validation for Bad Folder
				if(!StudioUIUtils.isValidContainer(path)){
					String problemMessage = com.tibco.cep.studio.core.util.Messages.getString("Resource.folder.bad", path);
					status.addFatalError(problemMessage+ ". Please select a different location");
				}else{
					path = path.removeFirstSegments(1); // remove project
					setNewPath(path.toString());
				}
			}
		} else {
			status= new RefactoringStatus();
			status.addError("Select an resource.");
		}
		setPageComplete(status);
	}
	
	private void setNewPath(String path) {
		ProcessorBasedRefactoring refactoring = (ProcessorBasedRefactoring) getRefactoring();
		((EntityMoveProcessor) refactoring.getProcessor()).setNewPath(path);
	}
	
	private void setUpdateReferences(boolean update) {
		MoveRefactoring refactoring = (MoveRefactoring) getRefactoring();
		((EntityMoveProcessor) refactoring.getProcessor()).setUpdateReferences(update);
	}
	
}
