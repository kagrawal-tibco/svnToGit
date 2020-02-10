package com.tibco.cep.studio.ui.diagrams.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.AbstractNavigatorNode;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * 
 * @author ggrigore
 *
 */
public class GenerateProjectDiagramActionDelegate implements IWorkbenchWindowActionDelegate, IObjectActionDelegate {

	private ISelection _selection;
	private IStructuredSelection strSelection;
	private IProject project;	
	private IWorkbenchPage page;
	private IWorkbenchWindow window;

	
	private class ResourceCollector implements IResourceVisitor {

		private List<IFile> fCollectedResources = new ArrayList<IFile>();
		
		@Override
		public boolean visit(IResource resource) throws CoreException {
			if (!(resource instanceof IFile)) {
				return true;
			}
			IFile file = (IFile) resource;
			if (!fCollectedResources.contains(file)) {
				fCollectedResources.add(file);
			}
			return false;
		}
		
		public List<IFile> getCollectedResources() {
			return fCollectedResources;
		}

	}

	public GenerateProjectDiagramActionDelegate() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {

		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			if (_selection != null && _selection instanceof IStructuredSelection) {
				strSelection = (IStructuredSelection)_selection;
				if (!(strSelection.getFirstElement() instanceof IProject)) {
					if (!StudioResourceUtils.isStudioProject(strSelection)) {
						return;
					}
					project = StudioResourceUtils.getCurrentProject(strSelection);
				} else {
					project = (IProject)strSelection.getFirstElement();
				}
				ProgressMonitorDialog dialog = new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
				dialog.run(true, true, new IRunnableWithProgress(){ 
					public void run(IProgressMonitor monitor) { 
					monitor.beginTask("Creating project diagram for selected entities...", IProgressMonitor.UNKNOWN); 
					monitor.worked(20);
						if(project != null && project.getProject().isOpen()){
							Object[] array = strSelection.toArray();
							ResourceCollector collector = new ResourceCollector();
							for (Object object : array) {
								if (object instanceof IResource) {
									try {
										((IResource)object).accept(collector);
									} catch (CoreException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						List<IFile> collectedResources = collector.getCollectedResources();
						final List<EObject> elements = new ArrayList<EObject>();
						final List<IFile> processFiles = new ArrayList<IFile>();
						monitor.worked(40);
						for (int i = 0; i < collectedResources.size(); i++) {
							IFile file = collectedResources.get(i);
							try {
								if(file.getFileExtension()!=null)
								{
								if (file.getFileExtension().equals("beprocess")) {
									processFiles.add(file);
								} else {
									EObject element = IndexUtils.getElement(file);
								   if (element != null && !elements.contains(element)) {
										elements.add(element);
									}
								}
								}
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
						final Object[] invocationContext = new Object[]{elements, processFiles};
						monitor.worked(60);
						String projectFileName = project.getName() + Messages.getString("PV_extension");
						final IFile projectViewFile = project.getFile(projectFileName);
						if (projectViewFile.exists()) {
							Display.getDefault().asyncExec(new Runnable() {
								@Override
								public void run() {
									EditorUtils.refreshDiagramEditor(page, projectViewFile, invocationContext);
								}
							});
						} else {
							File mfile = new File(project.getLocation().toOSString() + File.separator + project.getName() + Messages.getString("PV_extension"));
							//No separate check for existence is needed as the API does that
							try {
								mfile.createNewFile();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}//creating the file
							final IFile file = project.getFile(projectFileName);
							 Display.getDefault().asyncExec(new Runnable() {
									@Override
									public void run() {
										EditorUtils.openDiagramEditor(page, file, invocationContext);
									}
								});
						}
					} else 
						return;
					monitor.done();
					}
				});
			} else
				return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		_selection = selection;
		if (!(_selection.isEmpty()) && _selection instanceof IStructuredSelection) {
			strSelection = (IStructuredSelection)_selection;
			action.setEnabled(true);
			if(strSelection.getFirstElement() instanceof AbstractNavigatorNode){
				action.setEnabled(false);
				return;
			}
			if (!(strSelection.getFirstElement() instanceof IProject)) {
				project = StudioResourceUtils.getCurrentProject(strSelection);
			} else {
				project = (IProject)strSelection.getFirstElement();
			}
			if(project != null && project.isOpen()){
				action.setEnabled(true);
			}else{
				action.setEnabled(false);
			}
		} else if (_selection.isEmpty()) {
			action.setEnabled(false);
		}
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unused")
	private List<DesignerElement> getAllEntities() {
		
		ELEMENT_TYPES [] types = {ELEMENT_TYPES.CONCEPT,
				ELEMENT_TYPES.SIMPLE_EVENT,
				ELEMENT_TYPES.TIME_EVENT,
				ELEMENT_TYPES.SCORECARD,
				ELEMENT_TYPES.STATE_MACHINE,
				ELEMENT_TYPES.CHANNEL,
				ELEMENT_TYPES.DESTINATION,
				ELEMENT_TYPES.RULE,
				ELEMENT_TYPES.RULE_SET,
				ELEMENT_TYPES.RULE_FUNCTION };
		
		return IndexUtils.getAllElements(project.getName(), types);
	}
}
