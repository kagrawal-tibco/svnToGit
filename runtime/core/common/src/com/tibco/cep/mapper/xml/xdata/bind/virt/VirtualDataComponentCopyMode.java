package com.tibco.cep.mapper.xml.xdata.bind.virt;



/**
 * A type-safe enum describing the modes of copy in {@link VirtualElementBinding} and {@link VirtualAttributeBinding}.
 */
public final class VirtualDataComponentCopyMode
{
    private String m_displayName;

    /**
     * Indicates a straight element/value-of; no condition, checks or otherwise.<br>
     * This is the default.
     */
    public static final VirtualDataComponentCopyMode REQUIRED_TO_REQUIRED = new VirtualDataComponentCopyMode("required-to-required");

    /**
     * Indicates a straight element/value-of; no condition, checks or otherwise.<br>
     * This is the default.
     */
    public static final VirtualDataComponentCopyMode OPTIONAL_TO_OPTIONAL = new VirtualDataComponentCopyMode("optional-to-optional");
    public static final VirtualDataComponentCopyMode NIL_TO_NIL = new VirtualDataComponentCopyMode("nil-to-nil");
    public static final VirtualDataComponentCopyMode OPTIONALNIL_TO_OPTIONALNIL = new VirtualDataComponentCopyMode("optionalnil-to-optionalnil");
    public static final VirtualDataComponentCopyMode OPTIONAL_TO_NIL = new VirtualDataComponentCopyMode("optional-to-nil");
    public static final VirtualDataComponentCopyMode NIL_TO_OPTIONAL = new VirtualDataComponentCopyMode("nil-to-optional");

    private VirtualDataComponentCopyMode(String displayName)
    {
        m_displayName = displayName;
    }

    /**
     * @return true iff {@link #OPTIONAL_TO_OPTIONAL} or {@link #OPTIONALNIL_TO_OPTIONALNIL}.
     */
    public boolean isInputAndOutputOptional()
    {
        return this==OPTIONAL_TO_OPTIONAL || this==OPTIONALNIL_TO_OPTIONALNIL;
    }

    /**
     * @return true iff {@link #OPTIONAL_TO_OPTIONAL} or {@link #OPTIONALNIL_TO_OPTIONALNIL} or {@link #NIL_TO_OPTIONAL}.
     */
    public boolean isOutputOptional()
    {
        return this==OPTIONAL_TO_OPTIONAL || this==OPTIONALNIL_TO_OPTIONALNIL || this==NIL_TO_OPTIONAL;
    }

    /**
     * @return true iff {@link #OPTIONALNIL_TO_OPTIONALNIL} or {@link #OPTIONAL_TO_NIL} or {@link #NIL_TO_NIL}.
     */
    public boolean isOutputNil()
    {
        return this==OPTIONALNIL_TO_OPTIONALNIL || this==OPTIONAL_TO_NIL || this==NIL_TO_NIL;
    }

    /**
     * @return true iff {@link #OPTIONALNIL_TO_OPTIONALNIL} or {@link #NIL_TO_OPTIONAL} or {@link #NIL_TO_NIL}.
     */
    public boolean isInputNil()
    {
        return this==OPTIONALNIL_TO_OPTIONALNIL || this==NIL_TO_OPTIONAL || this==NIL_TO_NIL;
    }

    /**
     * @return true iff {@link #NIL_TO_NIL} or {@link #OPTIONALNIL_TO_OPTIONALNIL}.
     */
    public boolean isInputAndOutputNil()
    {
        return this==NIL_TO_NIL || this==OPTIONALNIL_TO_OPTIONALNIL;
    }

    /**
     * @return true iff {@link #OPTIONAL_TO_OPTIONAL} or {@link #OPTIONALNIL_TO_OPTIONALNIL} or {@link #OPTIONAL_TO_NIL}.
     */
    public boolean isInputOptional()
    {
        return this==OPTIONAL_TO_OPTIONAL || this==OPTIONAL_TO_NIL || this==OPTIONALNIL_TO_OPTIONALNIL;
    }

    /**
     * @return For {@link #NIL_TO_OPTIONAL} or {@link #NIL_TO_NIL}, returns {@link #REQUIRED_TO_REQUIRED}, for {@link #OPTIONALNIL_TO_OPTIONALNIL} returns {@link #OPTIONAL_TO_OPTIONAL},
     * otherwise returns the same mode.
     */
    public VirtualDataComponentCopyMode getWithNonNilInput()
    {
        if (this==NIL_TO_OPTIONAL || this==NIL_TO_NIL)
        {
            return REQUIRED_TO_REQUIRED;
        }
        if (this==OPTIONALNIL_TO_OPTIONALNIL)
        {
            return OPTIONALNIL_TO_OPTIONALNIL;
        }
        return this;
    }

    /**
     * @return For {@link #NIL_TO_OPTIONAL} or {@link #OPTIONAL_TO_OPTIONAL}, returns {@link #REQUIRED_TO_REQUIRED}, for {@link #OPTIONALNIL_TO_OPTIONALNIL} returns {@link #NIL_TO_NIL},
     * otherwise returns the same mode.
     */
    public VirtualDataComponentCopyMode getWithNoIf()
    {
        if (this==NIL_TO_OPTIONAL || this==OPTIONAL_TO_OPTIONAL)
        {
            return REQUIRED_TO_REQUIRED;
        }
        if (this==OPTIONALNIL_TO_OPTIONALNIL)
        {
            return NIL_TO_NIL;
        }
        return this;
    }

    /**
     * @return For {@link #OPTIONALNIL_TO_OPTIONALNIL} returns {@link #OPTIONAL_TO_OPTIONAL},
     * otherwise returns {@link #REQUIRED_TO_REQUIRED}.
     */
    public VirtualDataComponentCopyMode getWithNonNilOutput()
    {
        if (this==OPTIONALNIL_TO_OPTIONALNIL)
        {
            return OPTIONAL_TO_OPTIONAL;
        }
        return REQUIRED_TO_REQUIRED;
    }

    /**
     * @return For {@link #OPTIONAL_TO_OPTIONAL} or {@link #OPTIONALNIL_TO_OPTIONALNIL}, returns {@link #OPTIONALNIL_TO_OPTIONALNIL}, for {@link #OPTIONALNIL_TO_OPTIONALNIL} returns {@link #OPTIONAL_TO_OPTIONAL},
     * otherwise returns the {@link #NIL_TO_NIL}
     */
    public VirtualDataComponentCopyMode getWithCopyNil()
    {
        if (this==OPTIONAL_TO_OPTIONAL || this==OPTIONALNIL_TO_OPTIONALNIL)
        {
            return OPTIONALNIL_TO_OPTIONALNIL;
        }
        return VirtualDataComponentCopyMode.NIL_TO_NIL;
    }

    /**
     * Formats the name of the mode, for debugging/diagnostic purposes only; not for end-user display.
     * @return The name, never null.
     */
    public String toString()
    {
        return m_displayName;
    }
}

