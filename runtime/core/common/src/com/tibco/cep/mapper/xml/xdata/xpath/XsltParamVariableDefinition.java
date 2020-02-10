package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.cep.mapper.xml.xdata.NamespaceMapper;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;

public class XsltParamVariableDefinition extends VariableDefinition
{
    public final static int NONE = 0;
    private final static int BOTTOM = NONE;
    public final static int MATCH = 1;
    public final static int SELECT = 2;
    public final static int ELEMENT =3;
    public final static int TYPE_COMMENT = 4;

    private final static int TOP = TYPE_COMMENT;

    protected String mDefault;
    protected int mFormat = NONE;
    protected SmSequenceType mOrigType;


    public XsltParamVariableDefinition(ExpandedName varName, SmSequenceType type, String def, int format)
    {
        this(varName, type, type, false, def, format);
    }

    public XsltParamVariableDefinition(ExpandedName varName, SmSequenceType type, SmSequenceType origType, boolean isGlobal, String def, int format)
    {
        super(varName, type, isGlobal);
        if (format < BOTTOM || format > TOP)
        {
            throw new RuntimeException("Format type field is not a valid enumeration value!");
        }

        mDefault = def;
        mOrigType = origType;
        if (varName.equals("match"))
        {
            mFormat = MATCH;
        }
        else if (def == null)
        {
            mFormat = TYPE_COMMENT;
        }
        else
        {
            mFormat = format;
        }
    }

    public XsltParamVariableDefinition(ExpandedName varName, SmSequenceType type, String def)
    {
        this(varName, type, def, SELECT);
    }

    public XsltParamVariableDefinition(ExpandedName varName, SmSequenceType type, int format)
    {
        this(varName, type, null, format);
    }

    public String getDefault()
    {
        return mDefault;
    }

    public int getFormat()
    {
        return mFormat;
    }

    public boolean hasChanged()
    {
        if (mOrigType == mType)
        {
            return false;
        }
        if (mOrigType == null || mType == null)
        {
            return true;
        }
        throw new RuntimeException();
        /*
        if (mOrigType.getOldTypeCode() == mType.getOldTypeCode())
        {
            return false;
        }
        return true;*/
    }

    public String getTypeStr(NamespaceMapper ns)
            throws NamespaceToPrefixResolver.NamespaceNotFoundException
    {
        StringBuffer typeStr = new StringBuffer();
        SmType smType = mType.getElementOverrideType();

        if (smType == null)
        {
            // LAMb: This bit of code should be removed when SDK is fixed.
            throw new RuntimeException();/*
            switch (mType.getOldTypeCode())
            {
                case XType.OLD_TYPE_CODE_BOOLEAN:
                    return "xsd:boolean";

                case XType.OLD_TYPE_CODE_NUMBER:
                    return "xsd:decimal";

                case XType.OLD_TYPE_CODE_STRING:
                    return "xsd:string";

                case XType.OLD_TYPE_CODE_ANY:
                    return null;
            }*/
        }

        String uri = getTypeNamespace();
        String prefix = ns.getPrefixForNamespaceURI(uri);

        if (prefix != null)
        {
            typeStr.append(prefix);
            typeStr.append(':');
        }
        typeStr.append(getTypeName());

        return typeStr.toString();
    }

    public final String getTypeName()
    {
        /*if (mType instanceof ParticleTermXType)
        {
            SmParticleTerm term = ((ParticleTermXType)mType).getParticleTerm();
            return term.getName();
        }
        return mType.getType().getName();*/
        throw new RuntimeException();
    }

    public final String getTypeNamespace()
    {
        throw new RuntimeException();/*
        if (mType instanceof ParticleTermXType)
        {
            SmParticleTerm term = ((ParticleTermXType)mType).getParticleTerm();
            return term.getNamespace();
        }
        return null;*/
    }
}
