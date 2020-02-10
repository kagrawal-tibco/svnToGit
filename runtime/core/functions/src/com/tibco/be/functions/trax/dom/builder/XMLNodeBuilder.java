package com.tibco.be.functions.trax.dom.builder;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface XMLNodeBuilder {

	public Node build(Document document, Object object, String rootTagName);

}
