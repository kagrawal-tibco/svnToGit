package com.tibco.cep.driver.tibrv.serializer;

import java.util.HashMap;

import com.tibco.be.util.SmModel2ExpandedName;
import com.tibco.be.util.SmModel2ExpandedNameCache;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.driver.tibrv.TibRvConstants;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvMsgField;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.values.XsBoolean;
import com.tibco.xml.data.primitive.values.XsUnsignedShort;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmAttributeGroup;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.util.NameMangler;

public class RVDeSerializer {

    public static final ExpandedName BASEHOLDER_EXPANDED_NAME = ExpandedName.makeName("_payload_");




    public static XiNode deSerialize(SmElement ele, TibrvMsg rvMsg, boolean isPayload) {
        if (ele == null) return RVDeSerializer.blankDocument();
        if (rvMsg == null) return RVDeSerializer.blankDocument();

        SmModel2ExpandedName cache = SmModel2ExpandedNameCache.buildCache(ele,isPayload);
        return RVDeSerializer.deSerialize(cache.mapModelGroup2Name_ExpandedName,
                cache.mapModelGroup2Name_ExpandedName_ForAttributes,
                cache.smParticleTerm,
                rvMsg,
                true,
                true);



    }

    public static XiNode blankDocument() {

        XiFactory xiFactory = XiSupport.getXiFactory();

        XiNode baseElementNode = xiFactory.createElement(RVDeSerializer.BASEHOLDER_EXPANDED_NAME);

        return baseElementNode;

    }


    public static XiNode blankDocument(
            ExpandedName xname) {
        return XiSupport.getXiFactory().createElement(xname);
    }


    public static XiNode deSerialize (
        HashMap mapModelGroup2Name_ExpandedName,
        HashMap mapModelGroup2Name_ExpandedName_ForAttributes,
        SmParticleTerm smParticleTerm,
        TibrvMsg rvMsg,
        boolean bNeedOutputFiltration,
        boolean bNeedXMLCompliantFieldNames
    )
    {
        final XiNode baseElementNode = RVDeSerializer.blankDocument(smParticleTerm.getExpandedName());
        RVDeSerializer.subDeSerialize(baseElementNode, mapModelGroup2Name_ExpandedName, mapModelGroup2Name_ExpandedName_ForAttributes, smParticleTerm, rvMsg, false, bNeedOutputFiltration, bNeedXMLCompliantFieldNames);
        return baseElementNode;
    }

    private static void subDeSerialize (
        XiNode parentNode,
        HashMap mapModelGroup2Name_ExpandedName,
        HashMap mapModelGroup2Name_ExpandedName_ForAttributes,
        SmParticleTerm smParticleTerm,
        TibrvMsg rvMsg,
        boolean bIs_Hat_Data_Hat_Found,
        boolean bNeedOutputFiltration,
        boolean bNeedXMLCompliantFieldNames
    )
    {
        try
        {
            if (rvMsg.get("^data^") != null) {
                rvMsg = (TibrvMsg) rvMsg.get("^data^");
                bIs_Hat_Data_Hat_Found = true;
            }
        }
        catch (TibrvException ex0)
        {
            // We need to handle this in a better way
        }

        boolean blindMode = false;
        if (smParticleTerm == null)
        {
            blindMode = true;
        }
        int nCount = rvMsg.getNumFields();

        SmType smType = null;
        SmModelGroup modelGroup = null;
        SmAttributeGroup attributeGroup = null;

        HashMap mapName2ExpandedName = null;
        HashMap mapName2ExpandedName_ForAttributes = null;
        if (!blindMode)
        {
            if (smParticleTerm instanceof SmModelGroup)
            {
                modelGroup = (SmModelGroup)smParticleTerm;
                mapName2ExpandedName = (HashMap) mapModelGroup2Name_ExpandedName.get(modelGroup);
                int ii = 0;
            }
            else if (smParticleTerm instanceof SmElement)
            {
                smType = ((SmElement)smParticleTerm).getType();

                if (SmSupport.isTextOnlyContent(smType))
                {
                    try
                    {
                        RVDeSerializer.handleTopLevelPrimitiveDeSerialization(parentNode, rvMsg, (SmElement)smParticleTerm, mapModelGroup2Name_ExpandedName, mapModelGroup2Name_ExpandedName_ForAttributes, bNeedOutputFiltration, bNeedXMLCompliantFieldNames);
                    }
                    catch (TibrvException ex0)
                    {}
                    return;
                }
                modelGroup = smType.getContentModel();
                attributeGroup = smType.getAttributeModel();
                mapName2ExpandedName = (HashMap) mapModelGroup2Name_ExpandedName.get(modelGroup);
                mapName2ExpandedName_ForAttributes = (HashMap) mapModelGroup2Name_ExpandedName_ForAttributes.get(attributeGroup);
            }
        }

        for (int i = 0; i < nCount; ++i)
        {
            try
            {
                TibrvMsgField msgField = rvMsg.getFieldByIndex(i);
                String fieldName = msgField.name;

                /*
                RVMSG_STRING   7  ^class^  "Class0"
                RVMSG_INT      4  ^idx^    1
                */
                if (bIs_Hat_Data_Hat_Found)
                {
                    if (fieldName != null)
                    {
                        if (("^class^".equals (fieldName)) || ("^idx^").equals (fieldName))
                        {
                            continue;
                        }
                    }
                }

                boolean bIsAttribute = false;

                if ((fieldName != null) && (fieldName.startsWith("@")))
                {
                    bIsAttribute = true;
                    fieldName = fieldName.substring(1);
                }

                fieldName = RVDeSerializer.doNameManglingIfNeeded(bNeedXMLCompliantFieldNames, fieldName);

                XiFactory xiFactory = XiSupport.getXiFactory();
                XiNode childNode = null;

                ExpandedName resultExpandedName = null;

                if (!bIsAttribute)
                {
                    if (!blindMode)
                    {
                        resultExpandedName = (ExpandedName) mapName2ExpandedName.get(fieldName);
                    }
                }
                else
                {
                    if (!blindMode)
                    {
                        resultExpandedName = (ExpandedName) mapName2ExpandedName_ForAttributes.get(fieldName);
                    }
                }
                if (resultExpandedName == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        continue;
                    }
                    else
                    {
                        // field is not present in the schema
                        resultExpandedName = ExpandedName.makeName(fieldName);
                    }
                }


                switch (msgField.type)
                {
                    /*
                    case TibrvMsg.STRING:
                    {
                        String resultData = (String) (msgField.data);
                        XiChild.appendString(parentNode, resultExpandedName, resultData);
                        break;
                    }
                    case TibrvMsg.I16:
                    {
                        break;
                    }
                    */
                    case TibrvMsg.MSG:
                    {
                        TibrvMsg resultData = (TibrvMsg) msgField.data;

                        childNode = xiFactory.createElement(resultExpandedName);
                        parentNode.appendChild(childNode);

                        SmElement childElement = null;
                        if (!blindMode)
                        {
                            SmParticle smParticle = SmSupport.getParticleInContext(smType, resultExpandedName.getNamespaceURI(), resultExpandedName.getLocalName());
                            if (smParticle == null)
                            {}
                            else
                            {
                                SmParticleTerm particleTerm = smParticle.getTerm();
                                if (particleTerm instanceof SmElement)
                                {
                                    childElement = (SmElement) particleTerm;
                                }
                            }
                        }
                        /*
                        else
                        {
                            childElement = null;
                        }
                        */

                        if (
                            ((childElement != null) && (childElement.isNillable())) &&
                            ((resultData != null) && (resultData.getNumFields() <= 0))
                        )
                        {
                            childNode.setAttributeTypedValue(XSDL.ENAME_ATTR_NIL, XsBoolean.create(true));
                            break;
                        }

                        RVDeSerializer.subDeSerialize(childNode, mapModelGroup2Name_ExpandedName, mapModelGroup2Name_ExpandedName_ForAttributes,childElement, resultData, bIs_Hat_Data_Hat_Found, bNeedOutputFiltration, bNeedXMLCompliantFieldNames);
                        break;
                    }
                    default:
                    {
                        if (!bIsAttribute)
                        {
                            RV2XSDDataTypeConverter.putField(parentNode, resultExpandedName, msgField.data, msgField.type, modelGroup, false, bNeedOutputFiltration);
                        }
                        else
                        {
                            RV2XSDDataTypeConverter.putField(parentNode, resultExpandedName, msgField.data, msgField.type, attributeGroup, false, bNeedOutputFiltration);
                        }
                        break;
                    }
                }
            }
            catch (Exception ex0)
            {
                ex0.printStackTrace();
            }
        }
    }

    public static XiNode deSerializeRaw (
        TibrvMsg rvMsg
    )
    {
        XiFactory xiFactory = XiSupport.getXiFactory();
        XiNode documentNode = xiFactory.createDocument();
        XiNode baseElementNode = xiFactory.createElement(RVDeSerializer.BASEHOLDER_EXPANDED_NAME);
        documentNode.appendChild(baseElementNode);

        RVDeSerializer.subDeSerializeRaw (baseElementNode, rvMsg);
        //printXiNode(documentNode);
        return documentNode;
    }

    public static void subDeSerializeRaw (XiNode parentNode, TibrvMsg rvMsg)
    {
        int nCount = rvMsg.getNumFields();

        for (int i = 0; i < nCount; ++i)
        {
            try
            {
                TibrvMsgField msgField = rvMsg.getFieldByIndex(i);

                int id = msgField.id;
                String name = msgField.name;

                switch (msgField.type)
                {
                    /*
                    case TibrvMsg.STRING:
                    {
                        String resultValue = ((String)(msgField.data));
                        XiNode fieldNode = createSimpleField (id, name);
                        XiChild.appendString(fieldNode, TibrvMsgXmlContentHandler.SIMPLE_EXPANDED_NAME, resultValue);
                        parentNode.appendChild(fieldNode);

                        break;
                    }
                    case TibrvMsg.I16:
                    {
                        break;
                    }
                    */
                    case TibrvMsg.MSG:
                    {
                        TibrvMsg resultValue = (TibrvMsg)(msgField.data);
                        XiNode fieldNode = RVDeSerializer.createMsgField (id, name);

                        XiNode msgNode = XiChild.getChild(fieldNode, TibRvConstants.MSG_EXPANDED_NAME);
                        RVDeSerializer.subDeSerializeRaw (msgNode, resultValue);

                        parentNode.appendChild(fieldNode);
                        break;
                    }
                    default:
                    {
                        XiNode fieldNode = RVDeSerializer.createSimpleField (id, name);
                        RV2XSDDataTypeConverter.putField(fieldNode, TibRvConstants.SIMPLE_EXPANDED_NAME, msgField.data, msgField.type, null, true, false);
                        parentNode.appendChild(fieldNode);
                        break;
                    }
                }
            }
            catch (Exception ex0)
            {
                ex0.printStackTrace();
            }
        }
    }

    private static XiNode createSimpleField (int id, String name)
    {
        XiFactory xiFactory =XiSupport.getXiFactory();
        XiNode fieldNode = xiFactory.createElement(TibRvConstants.FIELD_EXPANDED_NAME);
        //XiChild.appendInt(fieldNode, TibRvConstants.ID_EXPANDED_NAME, id);

        XiChild.appendString(fieldNode, TibRvConstants.NAME_EXPANDED_NAME, name);

        XsUnsignedShort xsUnsignedShortId = new XsUnsignedShort(id);
        fieldNode.appendElement(TibRvConstants.ID_EXPANDED_NAME, xsUnsignedShortId);

        return fieldNode;
    }

    private static XiNode createMsgField (int id, String name)
    {
        XiNode fieldNode = RVDeSerializer.createSimpleField(id, name);

        XiFactory xiFactory = XiSupport.getXiFactory();
        XiNode msgNode = xiFactory.createElement(TibRvConstants.MSG_EXPANDED_NAME);
        fieldNode.appendChild(msgNode);

        return fieldNode;
    }

    /*
    protected static void printXiNode (XiNode node)
    {
        // Write an XiNode to output : Starts
        StringWriter stringWriter = new StringWriter();
        XiSerializer.serialize(node, stringWriter);
        String result = stringWriter.toString();
        System.out.println ("Result = " + result);
        // Ends

    }
    */

    private static void handleTopLevelPrimitiveDeSerialization(XiNode parentNode, TibrvMsg rvMsg, SmElement smElement, HashMap mapModelGroup2Name_ExpandedName, HashMap mapModelGroup2Name_ExpandedName_ForAttributes, boolean bNeedOutputFiltration, boolean bNeedXMLCompliantFieldNames) throws TibrvException
    {
        TibrvMsgField msgField = rvMsg.getFieldByIndex(0);
        String fieldName = msgField.name;

        boolean bIsAttribute = false;
        //HashMap mapName2ExpandedName_ForAttributes = null;
        HashMap mapName2ExpandedName = null;

        if ((fieldName != null) && (fieldName.startsWith("@")))
        {
            bIsAttribute = true;
            fieldName = fieldName.substring(1);
            // We need to handle these cases completely
        }
        else
        {
            mapName2ExpandedName = (HashMap) mapModelGroup2Name_ExpandedName.get(smElement);
        }

        XiFactory xiFactory = XiSupport.getXiFactory();
        XiNode childNode = null;

        ExpandedName resultExpandedName = null;

        fieldName = RVDeSerializer.doNameManglingIfNeeded(bNeedXMLCompliantFieldNames, fieldName);

        if (!bIsAttribute)
        {
            resultExpandedName = (ExpandedName) mapName2ExpandedName.get(fieldName);
        }
        else
        {
            // We need to handle these cases completely
            //resultExpandedName = (ExpandedName) mapName2ExpandedName_ForAttributes.get(fieldName);
        }
        if (resultExpandedName == null)
        {
            // field is not present in the schema
            if (bNeedOutputFiltration) return;
            resultExpandedName = ExpandedName.makeName(fieldName);
        }

        if (!bIsAttribute)
        {
            com.tibco.cep.driver.tibrv.serializer.RV2XSDDataTypeConverter.putField(parentNode, resultExpandedName, msgField.data, msgField.type, /*modelGroup*/null, false, bNeedOutputFiltration, true, smElement.getType());
        }
    }

    private static String doNameManglingIfNeeded (final boolean bNeedXMLCompliantFieldNames, String fieldName)
    {
        if (!bNeedXMLCompliantFieldNames)
        {
            fieldName = NameMangler.mangle(fieldName);
        }
        return fieldName;
    }


}
