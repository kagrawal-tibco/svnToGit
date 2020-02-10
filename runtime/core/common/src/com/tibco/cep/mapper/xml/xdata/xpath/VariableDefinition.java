package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.cep.mapper.xml.schema.xtype.VarRefSmSequenceTypeSource;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSequenceTypeContext;

public class VariableDefinition
{
    protected final ExpandedName mName;
    protected final SmSequenceType mType;
    protected final boolean mIsGlobal;
    private final Object mDeclarationContext;
    private SmSequenceType mCachedLocatedType;
    private boolean isArray;
    private String smTypestr;
    public VariableDefinition(ExpandedName varName, SmSequenceType type) {
        this(varName,type,true);
    }

    /**
     * @param isGlobal is this a global or locally defined variable (default is global in other constructors)
     */
    public VariableDefinition(ExpandedName varName, SmSequenceType type, boolean isGlobal) {
        if (varName.getLocalName().charAt(0) == '$')
        {
            throw new IllegalArgumentException("Variable name can not start with $: " + varName);
        }
        if (type==null)
        {
            throw new NullPointerException();
        }
        mName = varName;
        mType = type;
        mIsGlobal = isGlobal;
        mDeclarationContext = null;
    }

    public VariableDefinition(ExpandedName varName, SmSequenceType type, boolean isGlobal, Object declarationContext) {
        if (varName.getLocalName().charAt(0) == '$') {
            throw new IllegalArgumentException("Variable name can not start with $: " + varName);
        }
        mName = varName;
        mType = type;
        if (type==null)
        {
            throw new NullPointerException();
        }
        mIsGlobal = isGlobal;
        mDeclarationContext = declarationContext;
    }

    public ExpandedName getName() {
        return mName;
    }

    public Object getDeclarationContext()
    {
        return mDeclarationContext;
    }

    /**
     * Returns the element for the type, if it has one (null otherwise)
     */
    public SmElement getElement() {
        SmParticleTerm pt = getType().getParticleTerm();
        if (pt instanceof SmElement) {
            return (SmElement) pt;
        }
        return null;
    }

    public SmSequenceType getType() {
        return mType;
    }

    public SmSequenceType getLocatedType()
    {
        synchronized (this)
        {
            if (mCachedLocatedType==null)
            {
               SmSequenceType t = getType();
               SmSequenceTypeContext ctx = t.getContext();

                mCachedLocatedType = getType().createWithContext(
                        ctx.createWithSource(new VarRefSmSequenceTypeSource(getName())));
            }
            return mCachedLocatedType;
        }
    }

    /**
     * Is this a locally or globally defined variable (useful info for visual display mostly)
     */
    public boolean isGlobal() {
        return mIsGlobal;
    }

	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

	public String getSmTypestr() {
		return smTypestr;
	}

	public void setSmTypestr(String smType) {
		this.smTypestr = smType;
	}

	
}
