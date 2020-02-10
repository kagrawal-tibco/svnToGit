package com.tibco.cep.bpmn.core.debug;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugException;

import com.sun.jdi.NativeMethodException;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.debug.core.model.IResourcePosition;
import com.tibco.cep.studio.debug.core.model.ISourceInfoProvider;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpointInfo;

public class ProcessSourceInfoProvider implements ISourceInfoProvider {

	
	@Override
	public IPath getSourcePath(RuleDebugStackFrame frame) throws DebugException {
		try {
//			IDebugTarget target = frame.getDebugTarget();
			IProcessBreakpointInfo pbInfo = ProcessDebugModel.getProcessBreakPointInfo(frame);
			if(pbInfo != null) {
				String uri = pbInfo.getProcessUri();
				IPath p = new Path(uri);
				if(p.getFileExtension() == null) {
					p = p.addFileExtension(CommonIndexUtils.PROCESS_EXTENSION);
				}
				return p;
			}
			return null;
		} catch (NativeMethodException e) {
			frame.targetRequestFailed(MessageFormat.format("{0} occurred retrieving source path debug attribute.", new Object[] { e.toString() }), e);
		} catch (RuntimeException e) {
			frame.targetRequestFailed(MessageFormat.format("{0} occurred retrieving source path debug attribute.", new Object[] { e.toString() }), e);
		} 
//		catch (AbsentInformationException e) {
//			frame.targetRequestFailed(MessageFormat.format("{0} occurred retrieving source path debug attribute.", new Object[] { e.toString() }), e);
//		}
		return null;
	}
	
		

	@Override
	public IResourcePosition getResourcePosition(RuleDebugStackFrame frame) throws DebugException {
		try {
			IProcessBreakpointInfo pbInfo = ProcessDebugModel.getProcessBreakPointInfo(frame);
			if(pbInfo != null) {
				String uri = pbInfo.getProcessUri();
				IPath p = new Path(uri);
				if(p.getFileExtension() == null) {
					p = p.addFileExtension(CommonIndexUtils.PROCESS_EXTENSION);
				}
//				IResourcePosition pos = new ResourcePosition(pbInfo.getIndex(), p.toPortableString());
				IResourcePosition pos = GraphPosition.fromBreakPointInfo(pbInfo);
				return pos;
			}
			return null;
		} catch (NativeMethodException e) {
			frame.targetRequestFailed(MessageFormat.format("{0} occurred retrieving source path debug attribute.", new Object[] { e.toString() }), e);
		} catch (RuntimeException e) {
			frame.targetRequestFailed(MessageFormat.format("{0} occurred retrieving source path debug attribute.", new Object[] { e.toString() }), e);
		} 
//		catch (AbsentInformationException e) {
//			frame.targetRequestFailed(MessageFormat.format("{0} occurred retrieving source path debug attribute.", new Object[] { e.toString() }), e);
//		}
		return null;
	}

}
