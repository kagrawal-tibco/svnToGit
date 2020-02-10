package com.tibco.cep.mapper.codegen;

import com.tibco.xml.datamodel.XiNode;

public class XiNodeVariableType extends VariableType {

	public XiNodeVariableType() {
		super(XiNode.class.getCanonicalName(), false, false, false, false, false);
	}

}
