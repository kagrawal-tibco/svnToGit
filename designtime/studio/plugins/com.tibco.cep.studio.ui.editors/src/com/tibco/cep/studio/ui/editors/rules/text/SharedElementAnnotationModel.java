package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;

public class SharedElementAnnotationModel extends AbstractMarkerAnnotationModel implements IResourceChangeListener {
	public static final String RULEBREAKPOINT_MARKER_TYPE = "com.tibco.cep.studio.debug.core.RuleBreakpointMarker";
	public static final String TYPE_NAME = "com.tibco.cep.studio.debug.core.typeName";
	/**
	 * Debug model identifier breakpoint marker attribute (value <code>"org.eclipse.debug.core.id"</code>).
	 * The attribute is a <code>String</code> corresponding to the
	 * identifier of the debug model a breakpoint is associated with.
	 */
	public static final String ID= "org.eclipse.debug.core.id"; //$NON-NLS-1$
	
	JarEntryEditorInput fEditorInput;
	protected IWorkspace fWorkspace;
	protected IResource fMarkerResource;
	protected boolean fChangesApplied;
	
	
	public SharedElementAnnotationModel(JarEntryEditorInput editorInput) {
		this.fEditorInput = editorInput;
		fWorkspace = ResourcesPlugin.getWorkspace();
		fMarkerResource = fWorkspace.getRoot().getProject(editorInput.getProjectName());
	}

	@Override
	protected void deleteMarkers(IMarker[] markers) throws CoreException {
		// TODO Auto-generated method stub

	}
	
	protected boolean isAffected(IMarkerDelta markerDelta) {
		try {
			if (markerDelta == null) return false;

			String typeName = (String)markerDelta.getAttribute(TYPE_NAME);
			if (typeName == null) return false;
			
			return fEditorInput.getStorage().getFullPath().makeAbsolute().removeFileExtension().equals(new Path(typeName));
		} catch (CoreException x) {
			EditorsUIPlugin.log("SharedElementAnnotationModel.isAffected",x);
			return false;
		}
	}
	
	/**
	 * @see AbstractMarkerAnnotationModel#createMarkerAnnotation(IMarker)
	 */
	protected MarkerAnnotation createMarkerAnnotation(IMarker marker) {
		return new MarkerAnnotation(marker);
	}

	@Override
	protected boolean isAcceptable(IMarker marker) {
		try {
			return marker.getType().equals(RULEBREAKPOINT_MARKER_TYPE);
		} catch (CoreException e) {
			return false;
		}
	}

	@Override
	protected void listenToMarkerChanges(boolean listen) {
		if (listen)
			fWorkspace.addResourceChangeListener(this);
		else
			fWorkspace.removeResourceChangeListener(this);

	}

	@Override
	protected IMarker[] retrieveMarkers() throws CoreException {
		if (fMarkerResource != null) {
			IMarker[] workspaceMarkers= fWorkspace.getRoot().findMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO);
			IMarker[] resourceMarkers= fMarkerResource.findMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO);
			int workspaceMarkersLength= workspaceMarkers.length;
			if (workspaceMarkersLength == 0)
				return resourceMarkers;
			
			int resourceMarkersLength= resourceMarkers.length;
			if (resourceMarkersLength == 0)
				return workspaceMarkers;
			
			IMarker[] result= new IMarker[resourceMarkersLength + workspaceMarkersLength];
			System.arraycopy(resourceMarkers, 0, result, 0, resourceMarkersLength);
			System.arraycopy(workspaceMarkers, 0, result, resourceMarkersLength, workspaceMarkersLength);
			return result;
		}
		return null;
	}
	
	private void checkDeltas(IMarkerDelta[] markerDeltas) throws CoreException {
		for (int i= 0; i < markerDeltas.length; i++) {
			if (isAffected(markerDeltas[i])) {
				IMarker marker= markerDeltas[i].getMarker();
				switch (markerDeltas[i].getKind()) {
					case IResourceDelta.ADDED :
						addMarkerAnnotation(marker);
						fChangesApplied= true;
						break;
					case IResourceDelta.REMOVED :
						removeMarkerAnnotation(marker);
						fChangesApplied= true;
						break;
					case IResourceDelta.CHANGED:
						modifyMarkerAnnotation(marker);
						fChangesApplied= true;
						break;
				}
			}
		}
	}
	
	/**
	 * @see IResourceChangeListener#resourceChanged
	 */
	public void resourceChanged(IResourceChangeEvent e) {
		try {
			IMarkerDelta[] deltas= e.findMarkerDeltas(null, true);
			if (deltas != null) {
				fChangesApplied= false;
				checkDeltas(deltas);
				if (fChangesApplied)
					fireModelChanged();
			}
		} catch (CoreException x) {
			EditorsUIPlugin.log(x);
		}
	}

}
