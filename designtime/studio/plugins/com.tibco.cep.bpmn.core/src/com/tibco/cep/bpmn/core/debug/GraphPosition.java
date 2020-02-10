package com.tibco.cep.bpmn.core.debug;

import org.eclipse.jface.text.Position;

import com.tibco.cep.studio.debug.core.process.IProcessBreakpointInfo;

public class GraphPosition extends Position implements IGraphResourcePosition{
	
	

	private IProcessBreakpointInfo nodeInfo;
	
	
	public static GraphPosition fromBreakPointInfo(IProcessBreakpointInfo info) {
		return new GraphPosition(info);
	}

	public GraphPosition(IProcessBreakpointInfo bpInfo) {
		super(bpInfo.getUniqueId(), bpInfo.getUniqueId());
		this.nodeInfo = bpInfo;

	}
	
	@Override
	public IProcessBreakpointInfo getNodeInfo() {
		return nodeInfo;
	}
	
	@Override
	public int getLineNumber() {
		return nodeInfo.getUniqueId();
	}
	
	@Override
	public String getResourceName() {
		return nodeInfo.getProcessUri();
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GraphPosition [" + (nodeInfo != null ? "nodeInfo=" + nodeInfo + ", " : "")
				+ (getNodeInfo() != null ? "getNodeInfo()=" + getNodeInfo() + ", " : "") + "hashCode()=" + hashCode() + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nodeInfo == null) ? 0 : nodeInfo.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof GraphPosition)) {
			return false;
		}
		GraphPosition other = (GraphPosition) obj;
		if (nodeInfo == null) {
			if (other.nodeInfo != null) {
				return false;
			}
		} else if (!nodeInfo.equals(other.nodeInfo)) {
			return false;
		}
		return true;
	}

	

}
