package com.tibco.cep.designtime.model;

import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

public interface XiSerializable {
	
	public abstract XiNode toXiNode(XiFactory factory);

}
