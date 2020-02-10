package com.tibco.cep.mapper.xml.xdata.bind;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * A query interface for getting stylesheets & templates.
 */
public interface StylesheetResolver
{
    /**
     * Retrieves a parsed stylesheet.
     * @param locationURI The VFile URI of the stylesheet (relative URIs not cool)
     * @return The parsed stylesheet or null, if location not known.
     */
    StylesheetBinding getStylesheetByLocation(String locationURI);

    TemplateBinding getTemplateByMatch(SmSequenceType match, ExpandedName mode);

    /**
     * WCETODO Clarify this --- maybe should return array of templates...
     * @param name
     * @return
     */
    TemplateSignatureReport getTemplateByName(ExpandedName name);
}

