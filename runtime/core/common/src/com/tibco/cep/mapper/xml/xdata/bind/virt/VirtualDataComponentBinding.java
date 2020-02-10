package com.tibco.cep.mapper.xml.xdata.bind.virt;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Base class for {@link com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualElementBinding} and {@link com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualAttributeBinding}.
 */
public interface VirtualDataComponentBinding
{
    public static final ExpandedName INLINE_TEXT_ATTR = ExpandedName.makeName("text");
    public static final ExpandedName INLINE_VALUE_OF_ATTR = ExpandedName.makeName("value-of");
    public static final ExpandedName COPY_MODE_ATTR = ExpandedName.makeName("copy-mode");

    void setHasInlineFormula(boolean inlineFormula);
    boolean getHasInlineFormula();
    /**
     * Sets if the inline is either xsl:text (true) or xsl:value-of (false)
     */
    void setInlineIsText(boolean inlineIsText);

    /**
     * Gets if the inline is either xsl:text (true) or xsl:value-of (false).<br>
     * Not applicable if not {@link #getHasInlineFormula}.
     */
    boolean getInlineIsText();

    /**
     * A derived property to see if inline is possible.<br>
     * For elements, this is always true, for attributes, only true when it is an explicit binding.
     * @return
     */
    boolean getCanHaveInline();

    /**
     * Gets if the copy mode of this component.
     * @return The copy mode, never null.
     */
    public VirtualDataComponentCopyMode getCopyMode();

    /**
     * Sets the copy mode of this component.
     * @param copyMode
     */
    public void setCopyMode(VirtualDataComponentCopyMode copyMode);
}

