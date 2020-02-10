package com.tibco.cep.driver.tibrv.serializer;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TimeZone;

import com.tibco.be.util.XiSupport;
import com.tibco.tibrv.TibrvDate;
import com.tibco.tibrv.TibrvIPAddr;
import com.tibco.tibrv.TibrvIPPort;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.TimeZoneSupport;
import com.tibco.xml.data.primitive.values.XsBase64Binary;
import com.tibco.xml.data.primitive.values.XsBoolean;
import com.tibco.xml.data.primitive.values.XsByte;
import com.tibco.xml.data.primitive.values.XsDate;
import com.tibco.xml.data.primitive.values.XsDateTime;
import com.tibco.xml.data.primitive.values.XsDay;
import com.tibco.xml.data.primitive.values.XsDayTimeDuration;
import com.tibco.xml.data.primitive.values.XsDecimal;
import com.tibco.xml.data.primitive.values.XsDouble;
import com.tibco.xml.data.primitive.values.XsFloat;
import com.tibco.xml.data.primitive.values.XsHexBinary;
import com.tibco.xml.data.primitive.values.XsInt;
import com.tibco.xml.data.primitive.values.XsLong;
import com.tibco.xml.data.primitive.values.XsMonth;
import com.tibco.xml.data.primitive.values.XsMonthDay;
import com.tibco.xml.data.primitive.values.XsNegativeInteger;
import com.tibco.xml.data.primitive.values.XsNonNegativeInteger;
import com.tibco.xml.data.primitive.values.XsNonPositiveInteger;
import com.tibco.xml.data.primitive.values.XsPositiveInteger;
import com.tibco.xml.data.primitive.values.XsShort;
import com.tibco.xml.data.primitive.values.XsString;
import com.tibco.xml.data.primitive.values.XsTime;
import com.tibco.xml.data.primitive.values.XsUnsignedByte;
import com.tibco.xml.data.primitive.values.XsUnsignedInt;
import com.tibco.xml.data.primitive.values.XsUnsignedLong;
import com.tibco.xml.data.primitive.values.XsUnsignedShort;
import com.tibco.xml.data.primitive.values.XsYear;
import com.tibco.xml.data.primitive.values.XsYearMonth;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmAttributeGroup;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.flavor.XSDL20001024;
import com.tibco.xml.schema.flavor.XSDLConstants;

public class RV2XSDDataTypeConverter {

    public static void putField (
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration
    )
    {
        RV2XSDDataTypeConverter.putField (
            parentNode,
            elementToAdd,
            data,
            rvFieldType,
            modelGroup,
            bRawMode,
            bNeedOutputFiltration,
            false,
            null
        );
    }


    public static void putField (
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        boolean bIsAttribute = false;

        if (modelGroup instanceof SmAttributeGroup)
        {
            bIsAttribute = true;
        }
        switch (rvFieldType)
        {
            case TibrvMsg.STRING:
                {
                    RV2XSDDataTypeConverter.handleStringType (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.BOOL:
                {
                    RV2XSDDataTypeConverter.handleBooleanType (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.I8:
                {
                    RV2XSDDataTypeConverter.handleI8Type(
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.I16:
                {
                    RV2XSDDataTypeConverter.handleI16Type (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.I32:
                {
                    RV2XSDDataTypeConverter.handleI32Type (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.I64:
                {
                    RV2XSDDataTypeConverter.handleI64Type (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.U8:
                {
                    RV2XSDDataTypeConverter.handleU8Type (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.U16:
                {
                    RV2XSDDataTypeConverter.handleU16Type (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.U32:
                {
                    RV2XSDDataTypeConverter.handleU32Type (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.U64:
                {
                    RV2XSDDataTypeConverter.handleU64Type (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.F32:
                {
                    RV2XSDDataTypeConverter.handleF32Type (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.F64:
                {
                    RV2XSDDataTypeConverter.handleF64Type (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );

                    break;
                }
            case TibrvMsg.OPAQUE:
                {
                    RV2XSDDataTypeConverter.handleOpaqueType (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.DATETIME:
                {
                    RV2XSDDataTypeConverter.handleDateTimeType (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.IPPORT16:
                {
                    RV2XSDDataTypeConverter.handleIPPort16Type (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            case TibrvMsg.IPADDR32:
                {
                    RV2XSDDataTypeConverter.handleIPPort32Type (
                        bIsAttribute,
                        parentNode,
                        elementToAdd,
                        data,
                        rvFieldType,
                        modelGroup,
                        bRawMode,
                        bNeedOutputFiltration,
                        bTopLevelDirectPut,
                        _typeNeeded
                    );
                    break;
                }
            default:
                {
                    //setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsString(data.toString()), XsString.NAME, bRawMode);
                    String resultValue = (new String((byte[])(data))).toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
        }
    }

    private static SmType getTypeFromModel (ExpandedName elementToAdd, SmModelGroup modelGroup)
    {
        SmType smTypeNeeded = null;
        Iterator itParticles = modelGroup.getParticles();
        for (; itParticles.hasNext();)
        {
            SmParticle particle = (SmParticle) itParticles.next();
            SmParticleTerm particleTerm = particle.getTerm();
            if (modelGroup instanceof SmAttributeGroup)
            {
                //System.out.println ("elementToAdd = " + elementToAdd.toString());
                //System.out.println ("particleTerm expandedName = " + particleTerm.getExpandedName().toString());
                if (elementToAdd.equals (particleTerm.getExpandedName()))
                {
                    if (particleTerm instanceof SmAttribute)
                    {
                        smTypeNeeded = ((SmAttribute)(particleTerm)).getType();
                    }
                    break;
                }
            }
            else
            {
                if (elementToAdd.equals (particleTerm.getExpandedName()))
                {
                    if (particleTerm instanceof SmElement)
                    {
                        smTypeNeeded = ((SmElement)(particleTerm)).getType();
                    }
                    break;
                }
            }
        }
        return smTypeNeeded;
    }

    private static void setAttributeOrElement (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        XmlTypedValue resultValue,
        ExpandedName typeName, // e.g. For xsi:type="xsd:string", typeName will hold the xsd:string component
        boolean bRawMode
    )
    {
        if (bIsAttribute)
        {
            parentNode.setAttributeTypedValue(elementToAdd, resultValue);
        }
        else
        {
            if (bRawMode)
            {
                XiNode nodeToAdd = XiSupport.getXiFactory().createElement(elementToAdd);
                nodeToAdd.setAttributeTypedValue(
                    XSDLConstants.ENAME_ATTR_TYPE,
                    typeName/*getNameFromXmlTypedValue(resultValue)*/
                );
                nodeToAdd.setTypedValue(resultValue);
                parentNode.appendChild(nodeToAdd);
            }
            else
            {
                parentNode.appendElement(elementToAdd, resultValue);
            }
        }
    }

    private static void setAttributeOrElementWithString (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        String resultValue
    )
    {
        if (bIsAttribute)
        {
            parentNode.setAttributeStringValue(elementToAdd, resultValue);
        }
        else
        {
            parentNode.appendElement(elementToAdd, resultValue);
        }
    }

    protected static void handleStringType (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            String resultValue = ((String)(data));
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsString(resultValue), XsString.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    String resultValue = ((String)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsString(resultValue), XsString.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        String resultValue = ((String)(data));
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsString(resultValue), XsString.NAME, bRawMode);
                        return;
                    }
                }
                else if (typeNeeded.equals(XSDL.TIME) || ((typeNeeded.equals(XSDL20001024.TIME))))
                {
                    String dateValue = ((String)(data));
                    XsTime xsTime = null;
                    try {
                        xsTime = XsTime.compile(dateValue);
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsTime, XsTime.NAME, bRawMode);
                    }
                    catch (XmlAtomicValueParseException e0)
                    {                        
                    	((Exception)e0).printStackTrace();
                    	
                    }
                }
                else if (typeNeeded.equals(XSDL.YEAR_MONTH) /*|| typeNeeded.equals(XSDL20001024.YEAR_MONTH))*/)
                {
                    try
                    {
                        String strValue = ((String)(data));
                        XsYearMonth xsYearMonth = XsYearMonth.compile(strValue);
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsYearMonth, XsYearMonth.NAME, bRawMode);
                    }
                    catch (XmlAtomicValueParseException e0)
                    {
                        // We need to handle this in a better way
                    	((Exception)e0).printStackTrace();
                    }
                }
                else if (typeNeeded.equals(XSDL.YEAR) || typeNeeded.equals(XSDL20001024.YEAR))
                {
                    try
                    {
                        String strValue = ((String)(data));
                        XsYear xsYear = XsYear.compile(strValue);
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsYear, XsYear.NAME, bRawMode);
                    }
                    catch (XmlAtomicValueParseException e0)
                    {
                        // We need to handle this in a better way
                    	((Exception)e0).printStackTrace();
                    }
                }
                else if (typeNeeded.equals(XSDL.MONTH_DAY) /* || typeNeeded.equals(XSDL20001024.MONTH_DAY)*/)
                {
                    try
                    {
                        String strValue = ((String)(data));
                        XsMonthDay xsMonthDay = XsMonthDay.compile(strValue);
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsMonthDay, XsMonthDay.NAME, bRawMode);
                    }
                    catch (XmlAtomicValueParseException e0)
                    {
                        // We need to handle this in a better way
                    	((Exception)e0).printStackTrace();
                    }
                }
                else if (typeNeeded.equals(XSDL.DAY) /* || typeNeeded.equals(XSDL20001024.DAY)*/)
                {
                    try
                    {
                        String strValue = ((String)(data));
                        XsDay xsDay = XsDay.compile(strValue);
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsDay, XsDay.NAME, bRawMode);
                    }
                    catch (XmlAtomicValueParseException e0)
                    {
                        // We need to handle this in a better way
                    	((Exception)e0).printStackTrace();
                    }
                }
                else if (typeNeeded.equals(XSDL.MONTH) || typeNeeded.equals(XSDL20001024.MONTH))
                {
                    try
                    {
                        String strValue = ((String)(data));
                        XsMonth xsMonth = XsMonth.compile(strValue);
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsMonth, XsMonth.NAME, bRawMode);
                    }
                    catch (XmlAtomicValueParseException e0)
                    {
                        // We need to handle this in a better way
                    	((Exception)e0).printStackTrace();
                    }
                }
                else if (typeNeeded.equals(XSDL.STRING) || typeNeeded.equals(XSDL20001024.STRING))
                {
                    String resultValue = ((String)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsString(resultValue), XsString.NAME, bRawMode);
                }
                else
                {
                    String resultValue = ((String)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleBooleanType (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            Boolean resultValue = ((Boolean)(data));
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsBoolean(resultValue.booleanValue()), XsBoolean.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    Boolean resultValue = ((Boolean)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsBoolean(resultValue.booleanValue()), XsBoolean.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        Boolean resultValue = ((Boolean)(data));
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsBoolean(resultValue.booleanValue()), XsBoolean.NAME, bRawMode);
                        return;
                    }
                }
                else if (typeNeeded.equals(XSDL.BOOLEAN) || typeNeeded.equals(XSDL20001024.BOOLEAN))
                {
                    Boolean resultValue = ((Boolean)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsBoolean(resultValue.booleanValue()), XsBoolean.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleI8Type (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            Byte resultValue = ((Byte)(data));
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsByte(resultValue.byteValue()), XsByte.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    Byte resultValue = ((Byte)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsByte(resultValue.byteValue()), XsByte.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        Byte resultValue = ((Byte)(data));
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsByte(resultValue.byteValue()), XsByte.NAME, bRawMode);
                        return;
                    }
                }
                else if (typeNeeded.equals(XSDL.BYTE) || typeNeeded.equals(XSDL20001024.BYTE))
                {
                    Byte resultValue = ((Byte)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsByte(resultValue.byteValue()), XsByte.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleI16Type (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            Short resultValue = ((Short)(data));
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsShort(resultValue.shortValue()), XsShort.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    Short resultValue = ((Short)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsShort(resultValue.shortValue()), XsShort.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        Short resultValue = ((Short)(data));
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsShort(resultValue.shortValue()), XsShort.NAME, bRawMode);
                        return;
                    }
                }
                else if (typeNeeded.equals(XSDL.SHORT) || typeNeeded.equals(XSDL20001024.SHORT))
                {
                    Short resultValue = ((Short)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsShort(resultValue.shortValue()), XsShort.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleI32Type (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            Integer resultValue = ((Integer)(data));
            XsInt xsInt = new XsInt(resultValue);
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsInt, XsInt.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    Integer resultValue = ((Integer)(data));
                    XsInt xsInt = new XsInt(resultValue);
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsInt, XsInt.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        Integer resultValue = ((Integer)(data));
                        XsInt xsInt = new XsInt(resultValue);
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsInt, XsInt.NAME, bRawMode);
                        return;
                    }
                }
                else if (typeNeeded.equals(XSDL.INT) || typeNeeded.equals(XSDL20001024.INT))
                {
                    Integer resultValue = ((Integer)(data));
                    XsInt xsInt = new XsInt(resultValue);
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsInt, XsInt.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleI64Type (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            Long resultValue = ((Long)(data));
            XsLong xsLong = new XsLong(resultValue);
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsLong, XsLong.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    Long resultValue = ((Long)(data));
                    XsLong xsLong = new XsLong(resultValue);
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsLong, XsLong.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        Long resultValue = ((Long)(data));
                        XsLong xsLong = new XsLong(resultValue);
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsLong, XsLong.NAME, bRawMode);
                        return;
                    }
                }
                else if (typeNeeded.equals(XSDL.POSITIVE_INTEGER) || typeNeeded.equals(XSDL20001024.POSITIVE_INTEGER))
                {
                    Long resultValue = ((Long)(data));
                    XsPositiveInteger xsPositiveInteger = new XsPositiveInteger(resultValue.intValue());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsPositiveInteger, XsPositiveInteger.NAME, bRawMode);
                }
                else if (typeNeeded.equals(XSDL.NON_NEGATIVE_INTEGER) || typeNeeded.equals(XSDL20001024.NON_NEGATIVE_INTEGER))
                {
                    Long resultValue = ((Long)(data));
                    XsNonNegativeInteger xsNonNegativeInteger = new XsNonNegativeInteger(resultValue.intValue());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsNonNegativeInteger, XsNonNegativeInteger.NAME, bRawMode);
                }
                else if (typeNeeded.equals(XSDL.NON_POSITIVE_INTEGER) || typeNeeded.equals(XSDL20001024.NON_POSITIVE_INTEGER))
                {
                    Long resultValue = ((Long)(data));
                    XsNonPositiveInteger xsNonPositiveInteger = new XsNonPositiveInteger(resultValue.intValue());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsNonPositiveInteger, XsNonPositiveInteger.NAME, bRawMode);
                }
                else if (typeNeeded.equals(XSDL.NEGATIVE_INTEGER) || typeNeeded.equals(XSDL20001024.NEGATIVE_INTEGER))
                {
                    Long resultValue = ((Long)(data));
                    XsNegativeInteger xsNegativeInteger = new XsNegativeInteger(resultValue.intValue());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsNegativeInteger, XsNegativeInteger.NAME, bRawMode);
                }
                else if (typeNeeded.equals(XSDL.LONG) || typeNeeded.equals(XSDL20001024.LONG))
                {
                    Long resultValue = ((Long)(data));
                    XsLong xsLong = new XsLong(resultValue);
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsLong, XsLong.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleU8Type (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            Short resultValue = ((Short)(data));
            XsUnsignedByte xsUnsignedByte = new XsUnsignedByte(resultValue.shortValue());
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedByte, XsUnsignedByte.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    Short resultValue = ((Short)(data));
                    XsUnsignedByte xsUnsignedByte = new XsUnsignedByte(resultValue.shortValue());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedByte, XsUnsignedByte.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        Short resultValue = ((Short)(data));
                        XsUnsignedByte xsUnsignedByte = new XsUnsignedByte(resultValue.shortValue());
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedByte, XsUnsignedByte.NAME, bRawMode);
                        return;
                    }
                }
                else if (typeNeeded.equals(XSDL.UNSIGNED_BYTE) || typeNeeded.equals(XSDL20001024.UNSIGNED_BYTE))
                {
                    Short resultValue = ((Short)(data));
                    XsUnsignedByte xsUnsignedByte = new XsUnsignedByte(resultValue.shortValue());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedByte, XsUnsignedByte.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleU16Type (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            Integer resultValue = ((Integer)(data));
            XsUnsignedShort xsUnsignedShort = new XsUnsignedShort(resultValue.intValue());
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedShort, XsUnsignedShort.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    Integer resultValue = ((Integer)(data));
                    XsUnsignedShort xsUnsignedShort = new XsUnsignedShort(resultValue.intValue());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedShort, XsUnsignedShort.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        Integer resultValue = ((Integer)(data));
                        XsUnsignedShort xsUnsignedShort = new XsUnsignedShort(resultValue.intValue());
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedShort, XsUnsignedShort.NAME, bRawMode);
                        return;
                    }
                }
                else if (typeNeeded.equals(XSDL.UNSIGNED_SHORT) || typeNeeded.equals(XSDL20001024.UNSIGNED_SHORT))
                {
                    Integer resultValue = ((Integer)(data));
                    XsUnsignedShort xsUnsignedShort = new XsUnsignedShort(resultValue.intValue());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedShort, XsUnsignedShort.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleU32Type (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            Long resultValue = ((Long)(data));
            XsUnsignedInt xsUnsignedInt = new XsUnsignedInt(resultValue.longValue());
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedInt, XsUnsignedInt.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    Long resultValue = ((Long)(data));
                    XsUnsignedInt xsUnsignedInt = new XsUnsignedInt(resultValue.longValue());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedInt, XsUnsignedInt.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        Long resultValue = ((Long)(data));
                        XsUnsignedInt xsUnsignedInt = new XsUnsignedInt(resultValue.longValue());
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedInt, XsUnsignedInt.NAME, bRawMode);
                        return;
                    }
                }
                else if (typeNeeded.equals(XSDL.UNSIGNED_INT) || typeNeeded.equals(XSDL20001024.UNSIGNED_INT))
                {
                    Long resultValue = ((Long)(data));
                    XsUnsignedInt xsUnsignedInt = new XsUnsignedInt(resultValue.longValue());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedInt, XsUnsignedInt.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleU64Type (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            Long resultValue = ((Long)(data));
            XsUnsignedLong xsUnsignedLong = new XsUnsignedLong(resultValue.longValue());
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedLong, XsUnsignedLong.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    Long resultValue = ((Long)(data));
                    XsUnsignedLong xsUnsignedLong = new XsUnsignedLong(resultValue.longValue());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedLong, XsUnsignedLong.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        Long resultValue = ((Long)(data));
                        XsUnsignedLong xsUnsignedLong = new XsUnsignedLong(resultValue.longValue());
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedLong, XsUnsignedLong.NAME, bRawMode);
                        return;
                    }
                }
                else if (typeNeeded.equals(XSDL.UNSIGNED_LONG) || typeNeeded.equals(XSDL20001024.UNSIGNED_LONG))
                {
                    Long resultValue = ((Long)(data));
                    XsUnsignedLong xsUnsignedLong = new XsUnsignedLong(resultValue.longValue());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedLong, XsUnsignedLong.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleF32Type (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            Float resultValue = ((Float)(data));
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsFloat(resultValue.floatValue()), XsFloat.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    Float resultValue = ((Float)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsFloat(resultValue.floatValue()), XsFloat.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded != null) typeNeeded = SmSupport.getNativeType(typeNeeded);
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        Float resultValue = ((Float)(data));
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsFloat(resultValue.floatValue()), XsFloat.NAME, bRawMode);
                        return;
                    }
                }
                else if (typeNeeded.equals(XSDL.FLOAT) || typeNeeded.equals(XSDL20001024.FLOAT))
                {
                    Float resultValue = ((Float)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsFloat(resultValue.floatValue()), XsFloat.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleF64Type (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            Double resultValue = ((Double)(data));
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsDouble(resultValue.doubleValue()), XsDouble.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    Double resultValue = ((Double)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsDouble(resultValue.doubleValue()), XsDouble.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        Double resultValue = ((Double)(data));
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsDouble(resultValue.doubleValue()), XsDouble.NAME, bRawMode);
                        return;
                    }
                }
                else if (
                    (typeNeeded.equals(XSDL.DECIMAL)) ||
                    (typeNeeded.equals(XSDL20001024.DECIMAL))
                )
                {
                    Double resultValue = ((Double)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsDecimal(resultValue.doubleValue()), XsDecimal.NAME, bRawMode);
                }
                else if (typeNeeded.equals(XSDL.DOUBLE) || typeNeeded.equals(XSDL20001024.DOUBLE))
                {
                    Double resultValue = ((Double)(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, new XsDouble(resultValue.doubleValue()), XsDouble.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleOpaqueType (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            XsBase64Binary base64Binary = new XsBase64Binary ((byte[])(data));
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, base64Binary, XsBase64Binary.NAME, bRawMode);
        }
        else
        {
            if ((modelGroup == null) && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    XsBase64Binary base64Binary = new XsBase64Binary ((byte[])(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, base64Binary, XsBase64Binary.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        XsBase64Binary base64Binary = new XsBase64Binary ((byte[])(data));
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, base64Binary, XsBase64Binary.NAME, bRawMode);
                        return;
                    }
                }
                else if (typeNeeded.equals(XSDL.BASE64_BINARY) || typeNeeded.equals(XSDL.ANY_TYPE))
                {
                    XsBase64Binary base64Binary = new XsBase64Binary ((byte[])(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, base64Binary, XsBase64Binary.NAME, bRawMode);
                }
                else if (typeNeeded.equals(XSDL.HEX_BINARY) /* || typeNeeded.equals(XSDL20001024.HEX_BINARY) */)
                {
                    XsHexBinary hexBinary = new XsHexBinary ((byte[])(data));
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, hexBinary, XsHexBinary.NAME, bRawMode);
                }
                else
                {
                    String resultValue = (new String((byte[])(data))).toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleDateTimeType (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            TibrvDate rvDate = ((TibrvDate)(data));
            XsDayTimeDuration offset = TimeZoneSupport.getDayTimeDuration(TimeZone.getDefault());
            long timeInMilliSeconds = rvDate.getTime();
            BigDecimal decimalTime = new BigDecimal(new Long(timeInMilliSeconds).toString());
            XsDateTime dateTime = new XsDateTime(decimalTime, offset);
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, dateTime, XsDateTime.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    TibrvDate rvDate = ((TibrvDate)(data));
                    XsDayTimeDuration offset = TimeZoneSupport.getDayTimeDuration(TimeZone.getDefault());
                    long timeInMilliSeconds = rvDate.getTime();
                    BigDecimal decimalTime = new BigDecimal(new Long(timeInMilliSeconds).toString());
                    XsDateTime dateTime = new XsDateTime(decimalTime, offset);
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, dateTime, XsDateTime.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                typeNeeded = SmSupport.getNativeType(typeNeeded);
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        TibrvDate rvDate = ((TibrvDate)(data));
                        XsDayTimeDuration offset = TimeZoneSupport.getDayTimeDuration(TimeZone.getDefault());
                        long timeInMilliSeconds = rvDate.getTime();
                        BigDecimal decimalTime = new BigDecimal(new Long(timeInMilliSeconds).toString());
                        XsDateTime dateTime = new XsDateTime(decimalTime, offset);
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, dateTime, XsDateTime.NAME, bRawMode);
                        return;
                    }
                }
                else if (typeNeeded.equals(XSDL.DATETIME))
                {
                    TibrvDate rvDate = ((TibrvDate)(data));
                    XsDayTimeDuration offset = TimeZoneSupport.getDayTimeDuration(TimeZone.getDefault());
                    long timeInMilliSeconds = rvDate.getTime();
                    BigDecimal decimalTime = new BigDecimal(new Long(timeInMilliSeconds).toString());
                    XsDateTime dateTime = new XsDateTime(decimalTime, offset);
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, dateTime, XsDateTime.NAME, bRawMode);
                }
                else if (typeNeeded.equals(XSDL.DATE) || typeNeeded.equals(XSDL20001024.DATE))
                {
                    //((TibrvDate)(data)).gety
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime((TibrvDate)(data));
                    int nYear = calendar.get(Calendar.YEAR);
                    int nMonth = calendar.get(Calendar.MONDAY);   nMonth = nMonth + 1;
                    int nDay = calendar.get(Calendar.DAY_OF_MONTH);
                    XsDate xsDate = new XsDate (nYear, nMonth, nDay);
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsDate, XsDate.NAME, bRawMode);
                }
                else if (typeNeeded.equals(XSDL.ANY_TYPE))
                {
                    TibrvDate rvDate = ((TibrvDate)(data));
                    XsDayTimeDuration offset = TimeZoneSupport.getDayTimeDuration(TimeZone.getDefault());
                    long timeInMilliSeconds = rvDate.getTime();
                    BigDecimal decimalTime = new BigDecimal(new Long(timeInMilliSeconds).toString());
                    XsDateTime dateTime = new XsDateTime(decimalTime, offset);
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, dateTime, XsDateTime.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleIPPort16Type (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            TibrvIPPort ipPort = ((TibrvIPPort)(data));
            int nPort = ipPort.getPort();
            XsUnsignedShort xsUnsignedShort = new XsUnsignedShort(nPort);
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedShort, XsUnsignedShort.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    TibrvIPPort ipPort = ((TibrvIPPort)(data));
                    int nPort = ipPort.getPort();
                    XsUnsignedShort xsUnsignedShort = new XsUnsignedShort(nPort);
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedShort, XsUnsignedShort.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        TibrvIPPort ipPort = ((TibrvIPPort)(data));
                        int nPort = ipPort.getPort();
                        XsUnsignedShort xsUnsignedShort = new XsUnsignedShort(nPort);
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedShort, XsUnsignedShort.NAME, bRawMode);
                        return;
                    }
                }
                else if (
                    (typeNeeded.equals(XSDL.UNSIGNED_SHORT)) ||
                     XSDL.UNSIGNED_SHORT.equals (SmSupport.getNativeType(typeNeeded)) ||
                     typeNeeded.equals(XSDL.ANY_TYPE)
                )
                {
                    TibrvIPPort ipPort = ((TibrvIPPort)(data));
                    int nPort = ipPort.getPort();
                    XsUnsignedShort xsUnsignedShort = new XsUnsignedShort(nPort);
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsUnsignedShort, XsUnsignedShort.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }

    protected static void handleIPPort32Type (
        boolean bIsAttribute,
        XiNode parentNode,
        ExpandedName elementToAdd,
        Object data,
        short rvFieldType,
        SmModelGroup modelGroup,
        boolean bRawMode,
        boolean bNeedOutputFiltration,
        boolean bTopLevelDirectPut,
        SmType _typeNeeded
    )
    {
        if (bRawMode)
        {
            TibrvIPAddr ipAddr = ((TibrvIPAddr)(data));
            XsString xsString = new XsString(ipAddr.getAsString());
            RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsString, XsString.NAME, bRawMode);
        }
        else
        {
            if (modelGroup == null && !bTopLevelDirectPut)
            {
                if (bNeedOutputFiltration)
                {
                    return;
                }
                else
                {
                    TibrvIPAddr ipAddr = ((TibrvIPAddr)(data));
                    XsString xsString = new XsString(ipAddr.getAsString());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsString, XsString.NAME, bRawMode);
                    return;
                }
            }
            else
            {
                SmType typeNeeded = null;
                if ((modelGroup == null) && bTopLevelDirectPut)
                {
                    typeNeeded = _typeNeeded;
                }
                else
                {
                    typeNeeded = RV2XSDDataTypeConverter.getTypeFromModel (elementToAdd, modelGroup);
                }
                if (typeNeeded == null)
                {
                    if (bNeedOutputFiltration)
                    {
                        return;
                    }
                    else
                    {
                        TibrvIPAddr ipAddr = ((TibrvIPAddr)(data));
                        XsString xsString = new XsString(ipAddr.getAsString());
                        RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsString, XsString.NAME, bRawMode);
                        return;
                    }
                }
                else if (
                    typeNeeded.equals(XSDL.STRING) ||
                    XSDL.STRING.equals(SmSupport.getNativeType(typeNeeded)) ||
                    (typeNeeded.equals(XSDL.ANY_TYPE))
                ) // This needs fix ornativedecendent or something of that nature
                {
                    TibrvIPAddr ipAddr = ((TibrvIPAddr)(data));
                    XsString xsString = new XsString(ipAddr.getAsString());
                    RV2XSDDataTypeConverter.setAttributeOrElement(bIsAttribute, parentNode, elementToAdd, xsString, XsString.NAME, bRawMode);
                }
                else
                {
                    String resultValue = data.toString();
                    RV2XSDDataTypeConverter.setAttributeOrElementWithString (bIsAttribute, parentNode, elementToAdd, resultValue);
                }
            }
        }
    }
}
