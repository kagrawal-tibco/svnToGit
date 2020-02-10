package com.tibco.be.functions.xinodeassist;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.xml.datamodel.XiNode;

/*
* Author: Ashwin Jayaprakash Date: Aug 21, 2008 Time: 11:13:27 PM
*/
public interface XiNodeAssistant {
    void filterAndFill(Concept concept, XiNode node);
}
