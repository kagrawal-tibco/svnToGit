/**
 * 
 */
package com.tibco.cep.studio.core.validation.dt;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;

/**
 * @author aathalye
 *
 */
public interface IMarkerErrorRecorder<M extends IMarkableEntity> extends RuleErrorRecorder {
	
	static final String MARKER_TRS_ATTR = "com.tibco.cep.decision.table.trs";
	
	static final String MARKER_TRV_ATTR = "com.tibco.cep.decision.table.trv";
	
	/**
	 * Return existing markers for a {@link IResource}
	 * @return
	 */
	IMarker[] getExistingMarkers();
	
	void clearError(M markableEntity);
}
