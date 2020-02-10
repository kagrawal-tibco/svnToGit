package com.tibco.cep.studio.ui.validation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.ui.StudioUIPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioJavaSourceChangeListener implements IResourceChangeListener {

	private Map<IResource, ArrayList<String>> resourceMessagesMap =  new LinkedHashMap<IResource, ArrayList<String>>();
	private Map<IResource, ArrayList<Integer>> resourceLineNoMap =  new LinkedHashMap<IResource, ArrayList<Integer>>();
	private final static String APT_ANNOTATION_PROBLEM_TYPE = "org.eclipse.jdt.apt.pluggable.core.compileProblem";
	public static final String JAVA_SOURCE_ANNOTATION_PROBLEM_TYPE = StudioCorePlugin.PLUGIN_ID + ".studioJavaSourceAnnotationProblem";

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		//		resourceMessagesMap.clear();
		IResourceDelta delta = event.getDelta();
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE){}
		if (event.getType() == IResourceChangeEvent.PRE_DELETE){}
		if (event.getType() == IResourceChangeEvent.POST_CHANGE){
			IResource resource = delta.getResource();
			if (resource instanceof IWorkspaceRoot) {
				IResourceDelta[] affectedChildren = delta.getAffectedChildren();
				for (IResourceDelta resourceDelta : affectedChildren) {
					processDelta(resourceDelta);
				}
			}
		}

		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				for (IResource resource : resourceMessagesMap.keySet()) {
					try {
						resource.deleteMarkers(JAVA_SOURCE_ANNOTATION_PROBLEM_TYPE, false, IResource.DEPTH_ZERO);
						ArrayList<String> msgList = resourceMessagesMap.get(resource);
						ArrayList<Integer> lineNoList = resourceLineNoMap.get(resource);
						if (msgList != null && lineNoList != null) {
							for (int l = 0; l< msgList.size() ;  l++) {
								String message = msgList.get(l);
								int lineNo = lineNoList.get(l);
								if (!message.isEmpty() && lineNo != -1) {
									DefaultResourceValidator validator = new DefaultResourceValidator();
									validator.reportProblem(resource, message, lineNo, IMarker.SEVERITY_ERROR,
											JAVA_SOURCE_ANNOTATION_PROBLEM_TYPE, false);
								}
							}
						}
						
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						StudioUIPlugin.log(e);
					}	
				}
				resourceMessagesMap.clear();
				resourceLineNoMap.clear();
			}

		});
	}

	/**
	 * @param delta
	 */
	private void processDelta(IResourceDelta delta) {
		IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
			/* (non-Javadoc)
			 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
			 */
			public boolean visit(IResourceDelta delta) {
				IResource resource = delta.getResource();
				if (delta.getKind() == IResourceDelta.CHANGED){
					if (resource.getType() == IResource.FILE 
							&& resource.getFileExtension().equals(CommonIndexUtils.JAVA_EXTENSION)) {
						if (delta.getFlags() == IResourceDelta.MARKERS) {
							IMarkerDelta[] markerDeltas = delta.getMarkerDeltas();
							for (IMarkerDelta markerDelta :  markerDeltas ) {
								String type = markerDelta.getType();
								try {
									if (type.equals(APT_ANNOTATION_PROBLEM_TYPE) && markerDelta.getMarker().exists()) {
										Map<String, Object> map = markerDelta.getMarker().getAttributes();
										String message = map.get(IMarker.MESSAGE).toString();
										int lineNo = (Integer)map.get(IMarker.LINE_NUMBER);
										if (!resourceMessagesMap.containsKey(resource)) {
											ArrayList<String> list = new ArrayList<String>();
											resourceMessagesMap.put(resource, list);
											resourceMessagesMap.get(resource).add(message);
										} else {
											resourceMessagesMap.get(resource).add(message);
										}
										if (!resourceLineNoMap.containsKey(resource)) {
											ArrayList<Integer> list = new ArrayList<Integer>();
											resourceLineNoMap.put(resource, list);
											resourceLineNoMap.get(resource).add(lineNo);
										} else {
											resourceLineNoMap.get(resource).add(lineNo);
										}
									}  
									if (type.equals(APT_ANNOTATION_PROBLEM_TYPE) && !markerDelta.getMarker().exists()) {
										if (!resourceMessagesMap.containsKey(resource)) {
											ArrayList<String> list = new ArrayList<String>();
											resourceMessagesMap.put(resource, list);
											resourceMessagesMap.get(resource).add("");
										} else {
											resourceMessagesMap.get(resource).add("");
										}
										if (!resourceLineNoMap.containsKey(resource)) {
											ArrayList<Integer> list = new ArrayList<Integer>();
											resourceLineNoMap.put(resource, list);
											resourceLineNoMap.get(resource).add(-1);
										} else {
											resourceLineNoMap.get(resource).add(-1);
										}
									}
								} catch (CoreException e) {
									e.printStackTrace();
									StudioUIPlugin.log(e);
								}

							}
						}	
					}
				}
				return true;
			}
		};
		try {
			delta.accept(visitor);
		} catch (CoreException e) {
			e.printStackTrace();
			StudioUIPlugin.log(e);
		}
	}

}