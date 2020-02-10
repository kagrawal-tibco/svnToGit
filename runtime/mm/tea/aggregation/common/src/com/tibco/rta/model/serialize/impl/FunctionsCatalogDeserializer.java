package com.tibco.rta.model.serialize.impl;

import com.tibco.rta.model.DataType;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.FunctionDescriptor;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.UndefinedSchemaElementException;
import com.tibco.rta.model.impl.MetricFunctionDescriptorImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.Map;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_CATEGORY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DATATYPE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DESCRIPTION;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_ID_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_IMPL_CLASS;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_NAME_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_ORDINAL;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FUNCTION_CONTEXT;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FUNCTION_DESCRIPTOR;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FUNCTION_DESCRIPTORS;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FUNCTION_PARAM;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FUNCTION_PARAMS;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.IS_MULTI_VALUED;

public class FunctionsCatalogDeserializer extends CatalogDeserializer<MetricFunctionDescriptor> {

	// private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger("spm.core");

	@Override
	public Map<String, MetricFunctionDescriptor> deserialize() throws Exception {
		// look for all *.function.catalog files or jars containing .function.catalog files in the classpath

		String pattern = ".*?\\.function\\.catalog";
        return getCatalogFromClasspath(pattern);
	}

	@Override
	public FunctionDescriptor deserializeCatalogElement(InputSource in) throws Exception {

		Document rootDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
		Element rootElement = rootDocument.getDocumentElement();
		if (ELEM_FUNCTION_DESCRIPTOR.equals(rootElement.getNodeName())) {
			Element funcDescriptor = rootElement;
			String funcName = funcDescriptor.getAttribute(ATTR_NAME_NAME);

			String category = funcDescriptor.getAttribute(ATTR_CATEGORY);
			String strMultivalued = funcDescriptor.getAttribute(IS_MULTI_VALUED);
			boolean multiValued = false;
			if (strMultivalued != null && strMultivalued.equalsIgnoreCase("true")) {
				multiValued = true;
			}
			String implClassName = funcDescriptor.getAttribute(ATTR_IMPL_CLASS);
			String strDataType = funcDescriptor.getAttribute(ATTR_DATATYPE_NAME);
			DataType dataType = DataType.getByLiteral(strDataType);
			if (null == dataType) {
				throw new UndefinedSchemaElementException(String.format("Function '%s' has invalid datatype '%s'", funcName, strDataType));
			}
			String desc = funcDescriptor.getAttribute(ATTR_DESCRIPTION);

			MetricFunctionDescriptorImpl function = new MetricFunctionDescriptorImpl(funcName, category, multiValued, implClassName, dataType, desc);
			deserializeFunctionParams(function, funcDescriptor);
			deserializeFunctionContext(function, funcDescriptor);
			return function;

		}
		return null;
	}

	@Override
	public void deserializeCatalogElements(InputStream in, Map<String, MetricFunctionDescriptor> functionDescriptors) throws Exception {
		Document rootDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
		Element rootElement = rootDocument.getDocumentElement();

		if (ELEM_FUNCTION_DESCRIPTORS.equals(rootElement.getNodeName())) {
			NodeList funcDescList = rootElement.getElementsByTagName(ELEM_FUNCTION_DESCRIPTOR);
			for (int i = 0; i < funcDescList.getLength(); i++) {
				Element funcDescriptor = (Element) funcDescList.item(i);

				String funcName = funcDescriptor.getAttribute(ATTR_NAME_NAME);
				// Check if the function (with same name) is already present in the map
				if (functionDescriptors.containsKey(funcName)) {
                    // Skip to next function definition in the catalog
					continue;
				}
				String category = funcDescriptor.getAttribute(ATTR_CATEGORY);
				String strMultivalued = funcDescriptor.getAttribute(IS_MULTI_VALUED);
				boolean multiValued = false;
				if (strMultivalued != null && strMultivalued.equalsIgnoreCase("true")) {
					multiValued = true;
				}
				String implClassName = funcDescriptor.getAttribute(ATTR_IMPL_CLASS);
				String strDataType = funcDescriptor.getAttribute(ATTR_DATATYPE_NAME);
				DataType dataType = DataType.getByLiteral(strDataType);
				if (null == dataType) {
					throw new UndefinedSchemaElementException(String.format("Function '%s' has invalid datatype '%s'", funcName, strDataType));
				}
				String desc = funcDescriptor.getAttribute(ATTR_DESCRIPTION);

				MetricFunctionDescriptorImpl function = new MetricFunctionDescriptorImpl(funcName, category, multiValued, implClassName, dataType, desc);
				deserializeFunctionParams(function, funcDescriptor);
				deserializeFunctionContext(function, funcDescriptor);
                functionDescriptors.put(function.getName(), function);
			}
		}
	}

	private void deserializeFunctionContext(MetricFunctionDescriptorImpl function, Element funcDescriptor) throws UndefinedSchemaElementException,DuplicateSchemaElementException {
		NodeList fContext = funcDescriptor.getElementsByTagName(ELEM_FUNCTION_CONTEXT);
		if (fContext.getLength() > 0) {
			Element fContextElem = (Element) fContext.item(0);
			NodeList paramsList = fContextElem.getElementsByTagName(ELEM_FUNCTION_PARAM);
			for (int i = 0; i < paramsList.getLength(); i++) {
				Element paramElem = (Element) paramsList.item(i);
				String paramId = paramElem.getAttribute(ATTR_ID_NAME);
				String paramDataType = paramElem.getAttribute(ATTR_DATATYPE_NAME);
				DataType dataType = DataType.getByLiteral(paramDataType);
				if (null == dataType) {
					throw new UndefinedSchemaElementException(String.format("Parameter %s has invalid datatype '%s'", paramId, paramDataType));
				}
				String ordinal = paramElem.getAttribute(ATTR_ORDINAL);
				int paramIndex = Integer.parseInt(ordinal);
				String paramDesc = paramElem.getAttribute(ATTR_DESCRIPTION);
				MetricFunctionDescriptorImpl.FunctionParamImpl param = new MetricFunctionDescriptorImpl.FunctionParamImpl();
				param.setName(paramId);
				param.setDataType(dataType);
				param.setIndex(paramIndex);
				param.setDescription(paramDesc);

				function.addFunctionContext(param);
			}
		}
	}

	private void deserializeFunctionParams(MetricFunctionDescriptorImpl function, Element funcDescriptor) throws UndefinedSchemaElementException,DuplicateSchemaElementException {
		NodeList fContext = funcDescriptor.getElementsByTagName(ELEM_FUNCTION_PARAMS);
		if (fContext.getLength() > 0) {
			Element fContextElem = (Element) fContext.item(0);
			NodeList paramsList = fContextElem.getElementsByTagName(ELEM_FUNCTION_PARAM);
			for (int i = 0; i < paramsList.getLength(); i++) {
				Element paramElem = (Element) paramsList.item(i);
				String paramId = paramElem.getAttribute(ATTR_ID_NAME);
				String paramDataType = paramElem.getAttribute(ATTR_DATATYPE_NAME);
				DataType dataType = DataType.getByLiteral(paramDataType);
				if (null == dataType) {
					throw new UndefinedSchemaElementException(String.format("Parameter %s has invalid datatype '%s'", paramId, paramDataType));
				}
				String ordinal = paramElem.getAttribute(ATTR_ORDINAL);
				int paramIndex = Integer.parseInt(ordinal);
				String paramDesc = paramElem.getAttribute(ATTR_DESCRIPTION);

				MetricFunctionDescriptorImpl.FunctionParamImpl param = new MetricFunctionDescriptorImpl.FunctionParamImpl();
				param.setName(paramId);
				param.setDataType(dataType);
				param.setIndex(paramIndex);
				param.setDescription(paramDesc);
				function.addFunctionParam(param);
			}
		}
	}
}
