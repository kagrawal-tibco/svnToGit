package com.tibco.cep.mapper.xml.xdata.bind;

import org.xml.sax.SAXException;

import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;

/**
 * An 'extended' error for template reports; i.e. for virtual xslt elements.
 */
public interface TemplateReportExtendedError
{
    /**
     * Indicates if the error knows how to fix itself.
     * @param onReport The report this error was added on.
     * @return True if it can fix the error.
     */
    boolean canFix(TemplateReport onReport);
    void fix(TemplateReport report);
    String formatMessage(TemplateReport report);

    /**
     * Indicates if the error is a {@link com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChange#CATEGORY_ERROR}, etc.
     */
    int getCategory();

    void formatFragment(XmlContentHandler handler, NamespaceToPrefixResolver resolver) throws SAXException;
    void registerNamespaces(NamespaceContextRegistry nsContextRegistry);
}

