package com.tibco.rta.model.serialize.impl;

import com.tibco.rta.model.DataType;
import com.tibco.rta.model.FunctionDescriptor;
import com.tibco.rta.model.UndefinedSchemaElementException;
import com.tibco.rta.model.impl.FunctionDescriptorImpl;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.impl.ActionFunctionDescriptorImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.Map;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_CATEGORY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DATATYPE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DEFAULT_VALUE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DESCRIPTION;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_ID_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_IMPL_CLASS;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_NAME_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ACTION_DESCRIPTOR;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ACTION_DESCRIPTORS;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ACTION_PARAM;

public class ActionsCatalogDeserializer extends CatalogDeserializer<ActionFunctionDescriptor> {

//    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger("spm.core");

    @Override
    public Map<String, ActionFunctionDescriptor> deserialize() throws Exception {
        // look for all *.action.catalog files or jars containing .action.catalog files in the classpath
        String pattern = ".*?\\.action\\.catalog";
        return getCatalogFromClasspath(pattern);
    }

    @Override
    protected void deserializeCatalogElements(InputStream in, Map<String, ActionFunctionDescriptor> actionDescriptors) throws Exception {
        Document rootDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
        Element rootElement = rootDocument.getDocumentElement();

        if (ELEM_ACTION_DESCRIPTORS.equals(rootElement.getNodeName())) {
            NodeList actionDescList = rootElement.getElementsByTagName(ELEM_ACTION_DESCRIPTOR);
            for (int i = 0; i < actionDescList.getLength(); i++) {
                Element actionDescriptor = (Element) actionDescList.item(i);

                String actionName = actionDescriptor.getAttribute(ATTR_NAME_NAME);
                // Check if the function (with same name) is already present in the map
                if (actionDescriptors.containsKey(actionName)) {
                    continue;    // Skip to next function definition in the catalog
                }
                String category = actionDescriptor.getAttribute(ATTR_CATEGORY);
                String strDataType = actionDescriptor.getAttribute(ATTR_DATATYPE_NAME);
                DataType dataType = DataType.getByLiteral(strDataType);
                if (null == dataType) {
                    dataType = DataType.STRING;
                }
                String implClassName = actionDescriptor.getAttribute(ATTR_IMPL_CLASS);
                String desc = actionDescriptor.getAttribute(ATTR_DESCRIPTION);

                ActionFunctionDescriptorImpl action =
                        new ActionFunctionDescriptorImpl(actionName, category, implClassName, dataType, desc);
                deserializeActionParams(action, actionDescriptor);
                actionDescriptors.put(action.getName(), action);
            }
        }
    }

    private void deserializeActionParams(ActionFunctionDescriptorImpl action, Element actionDescriptor)
            throws Exception {
        NodeList paramsList = actionDescriptor.getElementsByTagName(ELEM_ACTION_PARAM);
        for (int i = 0; i < paramsList.getLength(); i++) {
            Element paramElem = (Element) paramsList.item(i);
            String paramId = paramElem.getAttribute(ATTR_ID_NAME);
            String paramDataType = paramElem.getAttribute(ATTR_DATATYPE_NAME);
            DataType dataType = DataType.getByLiteral(paramDataType);
            if (null == dataType) {
                throw new UndefinedSchemaElementException(String.format("Parameter %s has invalid datatype '%s'", paramId, paramDataType));
            }
            String ordinal = paramElem.getAttribute("ordinal");
            int paramIndex = Integer.parseInt(ordinal);
            String paramDesc = paramElem.getAttribute("description");

            ActionFunctionDescriptorImpl.FunctionParamImpl param = new ActionFunctionDescriptorImpl.FunctionParamImpl();
            param.setName(paramId);
            param.setDataType(dataType);
            param.setIndex(paramIndex);
            param.setDescription(paramDesc);
            action.addFunctionParam(param);

            Object defaultValue = paramElem.getAttribute(ATTR_DEFAULT_VALUE);

            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
            paramValue.setName(paramId);
            paramValue.setDataType(dataType);
            paramValue.setIndex(paramIndex);
            paramValue.setDescription(paramDesc);

            //TODO put as String for now. Deserialize to appropriate value based on datatype
            paramValue.setValue((String) defaultValue);

            action.addFunctionParamValue(paramValue);
        }
    }


    @Override
    protected FunctionDescriptor deserializeCatalogElement(InputSource is) throws Exception {
        Document rootDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        Element rootElement = rootDocument.getDocumentElement();

        if (ELEM_ACTION_DESCRIPTOR.equals(rootElement.getNodeName())) {
            Element funcDescriptor = rootElement;
            String funcName = funcDescriptor.getAttribute(ATTR_NAME_NAME);

            String category = funcDescriptor.getAttribute(ATTR_CATEGORY);

            String implClassName = funcDescriptor.getAttribute(ATTR_IMPL_CLASS);
            String strDataType = funcDescriptor.getAttribute(ATTR_DATATYPE_NAME);
            DataType dataType = DataType.getByLiteral(strDataType);
            if (null == dataType) {
                dataType = DataType.BOOLEAN;
            }
            String desc = funcDescriptor.getAttribute(ATTR_DESCRIPTION);

            ActionFunctionDescriptorImpl function = new ActionFunctionDescriptorImpl(funcName, category, implClassName, dataType, desc);
            deserializeActionParams(function, funcDescriptor);
            return function;
        }
        return null;
    }
}
