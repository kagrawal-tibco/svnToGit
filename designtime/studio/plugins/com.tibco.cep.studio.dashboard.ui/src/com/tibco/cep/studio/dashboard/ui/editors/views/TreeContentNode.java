package com.tibco.cep.studio.dashboard.ui.editors.views;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class TreeContentNode {
	
	private Object data;
	
	private TreeContentNode parent;
	
	private boolean particle;
	
	private boolean topLevelElement;
	
	private String type;

	public TreeContentNode(TreeContentNode parent, LocalElement data) {
		super();
		this.data = data;
		this.parent = parent;
		this.type = data.getElementType();
		this.particle = false;
		this.topLevelElement = BEViewsElementNames.isTopLevelElement(type);
	}
	
	public TreeContentNode(TreeContentNode parent, LocalParticle data) {
		super();
		this.data = data;
		this.parent = parent;
		this.type = data.getName();
		this.particle = true;
		this.topLevelElement = false;
	}
	
	public String getName() {
		if (particle == true) {
			return ((LocalParticle)data).getName();
		}
		if (BEViewsElementNames.getChartOrTextComponentTypes().contains(type) == true) {
			return ((LocalElement)data).getScopeName();
		}
		return ((LocalElement)data).getName();
	}
	
	public Object getData() {
		return data;
	}
	
	public TreeContentNode getParent() {
		return parent;
	}
	
	public boolean isParticle() {
		return particle;
	}
	
	public boolean isElement() {
		return !particle;
	}

	public boolean isTopLevelElement() {
		return topLevelElement;
	}
	
	public String getType(){
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TreeContentNode other = (TreeContentNode) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!parent.equals(other.parent)) {
			return false;
		}
		return true;
	}
	
}
