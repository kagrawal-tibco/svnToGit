/**
 * 
 */
package com.tibco.cep.studio.rms.client.builder.impl;

import java.io.StringWriter;

import com.tibco.cep.studio.rms.client.IRequestBuilderInput;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;

/**
 * @author aathalye
 *
 */
public abstract class AbstractRequestBuilderInput implements IRequestBuilderInput {

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.client.IRequestBuilderInput#buildRequest()
	 */
	@Override
	public Object buildRequest() throws Exception {
		XiNode xmlNode = buildXMLPart();
		StringWriter stringWriter = new StringWriter();
		DefaultXmlContentSerializer handler = 
				new DefaultXmlContentSerializer(stringWriter, "UTF-8");
		//Serialize
		xmlNode.serialize(handler);
		return stringWriter.toString();
	}
	
	protected abstract XiNode buildXMLPart();
}
