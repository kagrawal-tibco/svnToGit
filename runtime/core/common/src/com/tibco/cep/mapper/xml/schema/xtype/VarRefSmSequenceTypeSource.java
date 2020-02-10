package com.tibco.cep.mapper.xml.schema.xtype;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.schema.SmSequenceTypeSource;

/**
 * Copied from com.tibco.xml.schema.xtype.VariableRefSequenceTypeSource, as that class does not implement
 * the getXPath method, which is needed for drawing lines in the xml mapper (for coercions only?)
 * @author rhollom
 *
 */
public class VarRefSmSequenceTypeSource implements SmSequenceTypeSource
{
    private final ExpandedName mVarName;

    public VarRefSmSequenceTypeSource(ExpandedName varName)
    {
        mVarName = varName;
    }

    public String getXPath(NamespaceToPrefixResolver resolver)
    {
    	return "$" + mVarName;
    }
}