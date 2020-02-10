package com.tibco.cep.bpmn.ui.editor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import com.tibco.cep.bpmn.core.debug.GraphPosition;
import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultProcessIndex;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpoint;
import com.tibco.cep.studio.debug.core.process.ProcessBreakpointInfo;
import com.tibco.xml.data.primitive.ExpandedName;

public class GraphResourceMarkerAnnotationModel extends AbstractMarkerAnnotationModel implements IAnnotationModel {
	
	class ResourceChangeListener implements IResourceChangeListener {
		/*
		 * @see IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
		 */
		public void resourceChanged(IResourceChangeEvent e) {
			IResourceDelta delta= e.getDelta();
			if (delta != null && fResource != null) {
				IResourceDelta child= delta.findMember(fResource.getFullPath());
				if (child != null)
					update(child.getMarkerDeltas());
			}
		}
	}
	
	/** The workspace. */
	private IWorkspace fWorkspace;
	/** The resource. */
	private IResource fResource;
	/** The resource change listener. */
	private IResourceChangeListener fResourceChangeListener= new ResourceChangeListener();
	private IGraphEditorInput fEditorInput;

	public GraphResourceMarkerAnnotationModel(IGraphEditorInput editorInput) {
		fEditorInput = editorInput;
		fResource= fEditorInput.getFile();
		fWorkspace= fEditorInput.getFile().getWorkspace();
	}

	@Override
	protected IMarker[] retrieveMarkers() throws CoreException {
		return fResource.findMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO);
	}

	@Override
	protected void deleteMarkers(final IMarker[] markers) throws CoreException {
		fWorkspace.run(new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				for (int i= 0; i < markers.length; ++i) {
					markers[i].delete();
				}
			}
		}, null, IWorkspace.AVOID_UPDATE, null);
	}



	@Override
	protected boolean isAcceptable(IMarker marker) {
		return marker != null && fResource.equals(marker.getResource());
	}
	
	protected void update(IMarkerDelta[] markerDeltas) {

		if (markerDeltas.length ==  0)
			return;

		if (markerDeltas.length == 1) {
			IMarkerDelta delta= markerDeltas[0];
			switch (delta.getKind()) {
				case IResourceDelta.ADDED :
					addMarkerAnnotation(delta.getMarker());
					break;
				case IResourceDelta.REMOVED :
					removeMarkerAnnotation(delta.getMarker());
					break;
				case IResourceDelta.CHANGED :
					modifyMarkerAnnotation(delta.getMarker());
					break;
			}
		} else
			batchedUpdate(markerDeltas);

		fireModelChanged();
	}
	
	private void batchedUpdate(IMarkerDelta[] markerDeltas) {
		HashSet<Object> removedMarkers= new HashSet<Object>(markerDeltas.length);
		HashSet<Object> modifiedMarkers= new HashSet<Object>(markerDeltas.length);

		for (int i= 0; i < markerDeltas.length; i++) {
			IMarkerDelta delta= markerDeltas[i];
			switch (delta.getKind()) {
				case IResourceDelta.ADDED:
					addMarkerAnnotation(delta.getMarker());
					break;
				case IResourceDelta.REMOVED:
					removedMarkers.add(delta.getMarker());
					break;
				case IResourceDelta.CHANGED:
					modifiedMarkers.add(delta.getMarker());
					break;
				}
		}

		if (modifiedMarkers.isEmpty() && removedMarkers.isEmpty())
			return;

		Iterator<?> e= getAnnotationIterator(false);
		while (e.hasNext()) {
			Object o= e.next();
			if (o instanceof MarkerAnnotation) {
				MarkerAnnotation a= (MarkerAnnotation)o;
				IMarker marker= a.getMarker();

				if (removedMarkers.remove(marker))
					removeAnnotation(a, false);

				if (modifiedMarkers.remove(marker)) {
					Position p= createPositionFromMarker(marker);
					if (p != null) {
						a.update();
						modifyAnnotationPosition(a, p, false);
					}
				}

				if (modifiedMarkers.isEmpty() && removedMarkers.isEmpty())
					return;

			}
		}

		Iterator<?> iter= modifiedMarkers.iterator();
		while (iter.hasNext())
			addMarkerAnnotation((IMarker)iter.next());
	}
	
	@Override
	protected void listenToMarkerChanges(boolean listen) {
		if (listen)
			fWorkspace.addResourceChangeListener(fResourceChangeListener);
		else
			fWorkspace.removeResourceChangeListener(fResourceChangeListener);
	}
	
	protected IResource getResource() {
		return fResource;
	}
	
	@Override
	protected Position createPositionFromMarker(IMarker marker) {
		try {
			if (marker.exists()) {
				if(marker.getType().equals(IProcessBreakpoint.PROCESS_BREAKPOINT_MARKER_TYPE)) {
					
					String nodeId = marker.getAttribute(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_ID, null);
					String attribute = marker.getAttribute(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_LOCATION, null);
					if(attribute != null){
						final IProcessBreakpoint.TASK_BREAKPOINT_LOCATION nodeLocation = IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.valueOf(attribute);
						String resourceUri = marker.getAttribute(IProcessBreakpoint.PROCESS_BREAKPOINT_PROCESS_URI, null);
						String nodeTaskType = marker.getAttribute(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_TASK_TYPE, null);
						int uniqueId = marker.getAttribute(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_UNIQUE_ID, -1);
						if(nodeId != null) {
							if(uniqueId < 0) {
								return null;
							}
							ProcessBreakpointInfo bpInfo = new ProcessBreakpointInfo(resourceUri,nodeId,nodeTaskType,nodeLocation,uniqueId);
							return GraphPosition.fromBreakPointInfo(bpInfo);
							
						}
					}
				} else if(marker.getType().equals(IResourceValidator.VALIDATION_MARKER_TYPE)) {
					IResource res = marker.getResource();
					if(res.getFileExtension().equals(CommonIndexUtils.PROCESS_EXTENSION)) {
						EObject index = BpmnModelCache.getInstance().getIndex(res.getProject().getName());
						DefaultBpmnIndex di = new DefaultBpmnIndex(index);
						String processURI = res.getFullPath().removeFileExtension().removeFirstSegments(1).makeAbsolute().toPortableString();
						EObject process = di.getProcessByPath(processURI);
						DefaultProcessIndex dpi = new DefaultProcessIndex(process);
						Collection<EObject> flowNodes = dpi.getAllFlowNodes();
					
						
						String nodeId = marker.getAttribute(IMarker.LOCATION, null);
						if(nodeId != null) {
							EObject eObj = dpi.getElementById(nodeId);
							if(eObj != null){
								ExpandedName en = BpmnMetaModel.getInstance().getExpandedName(eObj.eClass());
								int uniqueId = marker.getAttribute(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_UNIQUE_ID, -1);
								ProcessBreakpointInfo bpInfo = new ProcessBreakpointInfo(processURI,nodeId,en.toString(),IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.START,uniqueId);
								return GraphPosition.fromBreakPointInfo(bpInfo);
							}
						}
//						String nodeId = marker.getAttribute(IMarker., null);
						
						//TODO: Manish - return a position with index 
						// for error/warning markers get the node task type from attribute
						// you need to save the expanded name type when creating the marker
						// nodeLocation is the breakpoint loc which can be constant - start
						// numnodes is the total number of flownodes in the graph at time the validation 
						// runs on process resource change,if the process changes again the earlier markers 
						// should get removed and new markers with new numnodes get created.
					}
					
				}
				
			}
		} catch (CoreException e) {
			BpmnUIPlugin.log(e);
		}
		return null;
	}
	
	@Override
	protected MarkerAnnotation createMarkerAnnotation(IMarker marker) {
		// TODO Auto-generated method stub
		return super.createMarkerAnnotation(marker);
	}
	
	@Override
	public void addAnnotation(Annotation annotation, Position position) {
		// TODO Auto-generated method stub
		super.addAnnotation(annotation, position);
	}
	
	public void removeAnnotation(Annotation annotation) {
		super.removeAnnotation(annotation);
		if (fEditorInput instanceof BpmnEditorInput) {
			BpmnEditor graphEditor = ((BpmnEditorInput)fEditorInput).getGraphEditor();
			if (graphEditor != null) {
				BpmnDiagramManager bpmnGraphDiagramManager = graphEditor.getBpmnGraphDiagramManager();
				if (bpmnGraphDiagramManager != null) {
					bpmnGraphDiagramManager.updateAnnotations();
				}
			}
		}
	}
	
	protected void modifyAnnotationPosition(Annotation annotation, Position position, boolean fireModelChanged) {
		if (position == null) {
			removeAnnotation(annotation, fireModelChanged);
		} else {
			Position p= (Position) getAnnotationMap().get(annotation);
			if (p != null) {

				if (!position.equals(p)) {
					fDocument.removePosition(p);
					p.setOffset(position.getOffset());
					p.setLength(position.getLength());
					try {
						fDocument.addPosition(p);
					} catch (BadLocationException e) {
						// ignore invalid position
					}
				}
				synchronized (getLockObject()) {
					getAnnotationModelEvent().annotationChanged(annotation);
				}
				if (fireModelChanged)
					fireModelChanged();

			} else {
				try {
					addAnnotation(annotation, position, fireModelChanged);
				} catch (BadLocationException x) {
					// ignore invalid position
				}
			}
		}
	}

	

}
