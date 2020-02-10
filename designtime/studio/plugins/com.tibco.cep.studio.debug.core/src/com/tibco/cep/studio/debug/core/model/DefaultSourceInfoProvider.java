/**
 * 
 */
package com.tibco.cep.studio.debug.core.model;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;

import com.sun.jdi.Location;
import com.sun.jdi.NativeMethodException;
import com.tibco.cep.studio.debug.core.model.impl.IMappedResourcePosition;
import com.tibco.cep.studio.debug.smap.SourceMapper;

/**
 * @author pdhar
 *
 */
public class DefaultSourceInfoProvider implements ISourceInfoProvider {

	/**
	 * 
	 */
	public DefaultSourceInfoProvider() {
	}


	@Override
	public IPath getSourcePath(RuleDebugStackFrame frame) throws DebugException {
		try {
//			Location location = (Location) frame.getAdapter(Location.class);
			IDebugTarget target = frame.getDebugTarget();
			String javaSourceName = frame.getDeclaringTypeName();
			SourceMapper mapper = ((AbstractDebugTarget)target).getSourceMapper();
			String resourceName =  mapper.getEntityName(javaSourceName);
			if(resourceName == null) {
				return null ;//new Path(javaSourceName);
			} 
			return ((AbstractDebugTarget)target).getEntityResourcePath(resourceName);
		} catch (NativeMethodException e) {
		} catch (RuntimeException e) {
			frame.targetRequestFailed(MessageFormat.format(
					"{0} occurred retrieving source path debug attribute.",
					new Object[] { e.toString() }), e);
		}
		return null;
	}



	@Override
	public IResourcePosition getResourcePosition(RuleDebugStackFrame frame) throws DebugException {
		Location location = (Location) frame.getAdapter(Location.class);
		IDebugTarget target = frame.getDebugTarget();
		String javaSourceName = frame.getDeclaringTypeName();
		SourceMapper mapper = ((AbstractDebugTarget)target).getSourceMapper();
		String resourceName = mapper.getEntityName(javaSourceName);
		if(resourceName != null) {
			IMappedResourcePosition pos = mapper.getBEPosition(javaSourceName, location.lineNumber());
			if(pos != null) {
				return pos;
			}
		}
		return null;
	}

}
