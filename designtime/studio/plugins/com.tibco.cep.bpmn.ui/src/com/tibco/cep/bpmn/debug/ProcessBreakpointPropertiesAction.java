package com.tibco.cep.bpmn.debug;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.texteditor.IUpdate;

import com.tibco.cep.bpmn.core.debug.ProcessDebugModel;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.editor.IGraphEditor;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tibco.cep.studio.debug.core.process.IGraphBreakpoint;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpoint;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpointInfo;
import com.tibco.cep.studio.debug.core.process.ProcessBreakpoint;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

/**
 * 
 * @author sasahoo
 *
 */
public class ProcessBreakpointPropertiesAction extends Action implements IUpdate {

	private IBreakpoint fBreakpoint;
	private IGraphEditor fEditor;

	public IGraphEditor getEditor() {
		return fEditor;
	}

	public ProcessBreakpointPropertiesAction(IGraphEditor editor) {
		this.fEditor = editor;
		setText(BpmnMessages.getString("processBreakPointPropAct_label")); 
	}

	public void run() {
		fBreakpoint = getBreakpoint();
		if (fBreakpoint != null) {
			PropertyDialogAction action = new PropertyDialogAction(getEditor()
					.getEditorSite(), new ISelectionProvider() {
				public void addSelectionChangedListener(
						ISelectionChangedListener listener) {
				}

				public ISelection getSelection() {
					if (getBreakpoint()!= null) {
						return new StructuredSelection(fBreakpoint);	
					}
					return new StructuredSelection();
				}

				public void removeSelectionChangedListener(
						ISelectionChangedListener listener) {
				}

				public void setSelection(ISelection selection) {
				}
			});
			action.run();
		}
	}
	
	public void update() {
		fBreakpoint = null;
		IBreakpoint breakpoint = getBreakpoint();
		if (breakpoint != null && (breakpoint instanceof IProcessBreakpoint)) {
			fBreakpoint = breakpoint;
		}
		setEnabled(fBreakpoint != null);
	}	
	
	/**
	 * @return breakpoint associated with activity in the Process or <code>null</code>
	 */
	protected IBreakpoint getBreakpoint() {
		try {
			if (fEditor != null) {
				IResource resource = (IResource) fEditor.getEditorInput().getAdapter(IResource.class);
				String typeName = null;
				if (resource == null) {
					IStorage storage = (IStorage) fEditor.getEditorInput().getAdapter(IStorage.class);
					if (storage != null) {
						resource = (IResource) storage.getAdapter(IProject.class);
						typeName = getTypeName(storage);
					} else {
						return null;
					}
				} else {
					typeName = getTypeName(resource);
				}
				IGraphSelection selection = (IGraphSelection) fEditor.getSite().getSelectionProvider().getSelection();
				TSEObject graphObject = selection.getGraphObject();
				if (graphObject != null) {
					TSEGraph fTSEGraph = null;
					@SuppressWarnings("unused")
					TSENode fTSENode = null;
					@SuppressWarnings("unused")
					TSEEdge fTSEEdge = null;
					if (graphObject instanceof TSEGraph) {
						fTSEGraph = (TSEGraph) graphObject;
						if (fTSEGraph.getParent() instanceof TSENode) {
							fTSENode = (TSENode) fTSEGraph.getParent();
						}
					}
					if (graphObject instanceof TSEEdge) {
						fTSEEdge = (TSEEdge) graphObject;
					}
					if (graphObject instanceof TSENode) {
						fTSENode = (TSENode) graphObject;
						IProcessBreakpointInfo graphInfo = ProcessBreakpointUtils.getProcessBreakpointInfo(graphObject);
						IGraphBreakpoint breakpoint = ProcessBreakpoint.graphBreakpointExists(typeName,graphInfo );
						//TODO: extract info for START and END location
						return breakpoint;
					}
				}
			}
		} catch (CoreException ce) {
			ce.printStackTrace();
		}
		return null;
	}
	
	public String getTypeName(IStorage storage) {
		return storage.getFullPath().removeFileExtension().makeAbsolute().toPortableString();
	}
	public String getTypeName(IResource resource) {
		IPath resourcePath = resource.getFullPath().removeFileExtension().removeFirstSegments(1).makeAbsolute();
		return resourcePath.toString();
	}

}