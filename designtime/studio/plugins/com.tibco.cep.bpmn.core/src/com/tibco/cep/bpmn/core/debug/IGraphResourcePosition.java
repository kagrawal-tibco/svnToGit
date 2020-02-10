package com.tibco.cep.bpmn.core.debug;

import com.tibco.cep.studio.debug.core.model.IResourcePosition;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpointInfo;

public interface IGraphResourcePosition extends IResourcePosition {

	public abstract IProcessBreakpointInfo getNodeInfo();

}
