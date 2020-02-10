package com.tibco.cep.bpmn.debug;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetExtension;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.IEditorStatusLine;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.debug.ProcessDebugModel;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessOntologyAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.IGraphEditor;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.debug.core.model.DebuggerConstants;
import com.tibco.cep.studio.debug.core.process.IGraphBreakpoint;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpointInfo;
import com.tibco.cep.studio.debug.core.process.ProcessBreakpoint;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

public class ProcessToggleBreakpointTargetAdapter extends AbstractGraphToggleBreakpointAdapter implements IToggleBreakpointsTargetExtension {
	public static String[] validExtensions = new String[] { CommonIndexUtils.PROCESS_EXTENSION };
	/**
	 * The status line to use to display errors
	 */
	private IEditorStatusLine fStatusLine;

	@Override
	public boolean canToggleBreakpoints(IWorkbenchPart part, ISelection selection) {
		IGraphEditor graphEditor = getEditor(part);
		fStatusLine = (IEditorStatusLine) part.getAdapter(IEditorStatusLine.class);
		if (selection.isEmpty()) {
			return false;
		}
		if (graphEditor != null) {
			IGraphSelection graphSelection = (IGraphSelection) selection;
			TSEObject graphObject = graphSelection.getGraphObject();

			IResource resource = (IResource) graphEditor.getEditorInput().getAdapter(IResource.class);
			EObject index = null;
			String elementPath = null;
			if (resource != null) {
				elementPath = IndexUtils.getFullPath(resource);
				index = BpmnCorePlugin.getDefault().getBpmnModelManager().getBpmnIndex(resource.getProject());
			} else {
				IStorage storage = (IStorage) graphEditor.getEditorInput().getAdapter(IStorage.class);
				IProject project = (IProject) storage.getAdapter(IProject.class);
				index = StudioCorePlugin.getDesignerModelManager().getIndex(project);
				elementPath = storage.getFullPath().removeFileExtension().makeAbsolute().toPortableString();
			}
			if (index == null) {
				return false;
			}

			ProcessOntologyAdapter ontology = new ProcessOntologyAdapter(index);
			Entity entity = ontology.getEntity(elementPath);
			if (entity != null && entity instanceof ProcessModel) {
				if (graphObject != null) {

					TSEGraph fTSEGraph = null;
					TSENode fTSENode = null;
					@SuppressWarnings("unused")
					TSEEdge fTSEEdge = null;

					if (graphObject instanceof TSEGraph) {
						fTSEGraph = (TSEGraph) graphObject;
						if (fTSEGraph.getParent() instanceof TSENode)
							fTSENode = (TSENode) fTSEGraph.getParent();
						return false;
					}
					if (graphObject instanceof TSEEdge) {
						fTSEEdge = (TSEEdge) graphObject;
						return false;
					}
					/**
					 * get the node data
					 */
					
					if (graphObject instanceof TSENode) {
						
						fTSENode = (TSENode) graphObject;
						
						// Remove breakpoint from start and end node of SUbprocess
						EObject eobjP = BpmnModelUtils.getFlowElementContainer((EObject)fTSENode.getUserObject()) ;
						EObjectWrapper<EClass, EObject> userObjWrapper = 
								EObjectWrapper.wrap( (EObject)fTSENode.getUserObject() );
						EList<EObject> listAttribute = null ;
						if ( userObjWrapper != null && userObjWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS ) ) {
						 	listAttribute = userObjWrapper
										.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
						}
						
						if ( eobjP != null && BpmnModelClass.SUB_PROCESS.isSuperTypeOf( eobjP.eClass() ) && userObjWrapper != null 
								&& ( userObjWrapper.isInstanceOf( BpmnModelClass.START_EVENT ) ||
										 userObjWrapper.isInstanceOf( BpmnModelClass.END_EVENT ) )
								&& listAttribute != null
								&& listAttribute.size() == 0 ) {
							
							report( "BreakPoint cannot be set at given position" , true ) ;
							return false ;
						}
						
						EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
						if (BpmnModelClass.ACTIVITY.isSuperTypeOf(nodeType) 
								|| BpmnModelClass.SUB_PROCESS.isSuperTypeOf(nodeType) 
								|| BpmnModelClass.TASK.isSuperTypeOf(nodeType) 
								|| BpmnModelClass.EVENT.isSuperTypeOf(nodeType)
								|| BpmnModelClass.GATEWAY.isSuperTypeOf(nodeType)) {
							report("TOGGLE OK", true);
							return true;
						} else {
							return false;
						}

					}
				}
				else
					return false;

			}

		}
		return false;

	}

	@Override
	public void toggleBreakpoints(final IWorkbenchPart part, final ISelection selection) throws CoreException {
		Job job = new Job("Toggle Line Breakpoint") { //$NON-NLS-1$
			protected IStatus run(IProgressMonitor monitor) {
				try {
					IGraphEditor graphEditor = getEditor(part);
					if (graphEditor != null) {
						IResource resource = (IResource) graphEditor.getEditorInput().getAdapter(IResource.class);
						String typeName = null;
						if (resource == null) {
							IStorage storage = (IStorage) graphEditor.getEditorInput().getAdapter(IStorage.class);
							if (storage != null) {
								resource = (IResource) storage.getAdapter(IProject.class);
								typeName = getTypeName(storage);
							} else {
								return Status.CANCEL_STATUS;
							}
						} else {
							typeName = getTypeName(resource);
						}
						IGraphSelection textSelection = (IGraphSelection) selection;
						TSEObject graphObject = textSelection.getGraphObject();
						if (graphObject != null) {

							TSEGraph fTSEGraph = null;
							@SuppressWarnings("unused")
							TSENode fTSENode = null;
							@SuppressWarnings("unused")
							TSEEdge fTSEEdge = null;

							if (graphObject instanceof TSEGraph) {
								fTSEGraph = (TSEGraph) graphObject;
								if (fTSEGraph.getParent() instanceof TSENode)
									fTSENode = (TSENode) fTSEGraph.getParent();
							}
							if (graphObject instanceof TSEEdge) {
								fTSEEdge = (TSEEdge) graphObject;
							}
							/**
							 * get the node data
							 */
							if (graphObject instanceof TSENode) {
								fTSENode = (TSENode) graphObject;
								
								EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
										.wrap((EObject) fTSENode.getUserObject());
								EList<EObject> listAttribute = null;
								if (userObjWrapper != null && userObjWrapper
										.containsAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS)) {
									listAttribute = userObjWrapper
											.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
								}
								EObject eobjP = BpmnModelUtils.getFlowElementContainer((EObject)fTSENode.getUserObject()) ;
								if (eobjP != null
										&& BpmnModelClass.SUB_PROCESS
												.isSuperTypeOf(eobjP.eClass()) && userObjWrapper!= null 
										&& (userObjWrapper
												.isInstanceOf(BpmnModelClass.START_EVENT) || userObjWrapper
												.isInstanceOf(BpmnModelClass.END_EVENT))
										&& listAttribute != null
										&& listAttribute.size() == 0) {
									report("BreakPoint cannot be set at given position ", true );
									return Status.CANCEL_STATUS;
								} 
								IProcessBreakpointInfo graphInfo = ProcessBreakpointUtils.getProcessBreakpointInfo(graphObject);

								IGraphBreakpoint existingBreakpoint = ProcessDebugModel.graphBreakpointExists(typeName,graphInfo );
								if (existingBreakpoint != null) {
									DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(existingBreakpoint, true);
									return Status.OK_STATUS;
								}
								Map<String,Object> attributes = new HashMap<String,Object>(10);
								attributes.put(DebuggerConstants.BREAKPOINT_TYPE, DebuggerConstants.BREAKPOINT_PROCESS);
								ProcessBreakpointUtils.addBreakpointAttributesWithMemberDetails(attributes, typeName, graphInfo);
								ProcessDebugModel.createProcessBreakpoint(resource, typeName, graphInfo, attributes);
								//new BreakpointLocationVerifierJob(document, breakpoint, graphObject, true, typeName, resource, graphEditor).schedule();

							}

						}
					}
				} catch (CoreException ce) {
					return ce.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.schedule();

	}

	public String getTypeName(IResource resource) {
		IPath resourcePath = resource.getFullPath().removeFileExtension().removeFirstSegments(1).makeAbsolute();
		return resourcePath.toString();
	}

	public String getTypeName(IStorage storage) {
		return storage.getFullPath().removeFileExtension().makeAbsolute().toPortableString();
	}

	private IGraphEditor getEditor(IWorkbenchPart part) {
		if (part instanceof IEditorPart) {
			IEditorPart editorPart = (IEditorPart) part;
			IResource resource = (IResource) editorPart.getEditorInput().getAdapter(IResource.class);
			if (resource != null) {
				String extension = resource.getFileExtension();
				if (extension != null) {
					if (Arrays.binarySearch(validExtensions, extension) >= 0) {
						return (IGraphEditor) editorPart.getAdapter(IGraphEditor.class);
					}
				}
			} else {
				IStorage storage = (IStorage) editorPart.getEditorInput().getAdapter(IStorage.class);
				if (storage != null) {
					String extension = storage.getFullPath().getFileExtension();
					if (Arrays.binarySearch(validExtensions, extension) >= 0) {
						return (IGraphEditor) editorPart.getAdapter(IGraphEditor.class);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Reports any status to the current active workbench shell
	 * 
	 * @param message
	 *            the message to display
	 */
	protected void report(final String message, final boolean autoClear) {
		StudioDebugUIPlugin.getStandardDisplay().asyncExec(new Runnable() {
			public void run() {
				if (fStatusLine != null) {
					fStatusLine.setMessage(true, message, null);
				}
				if (message != null && StudioDebugUIPlugin.getActiveWorkbenchShell() != null) {
					// Display.getCurrent().beep();
				}
			}
		});
		if (autoClear) {
			Job clearStatusJob = new Job("Clear status line") {

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					report("", false);
					return Status.OK_STATUS;
				}
			};
			clearStatusJob.schedule(30000);
		}
	}

}
