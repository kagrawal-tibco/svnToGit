package com.tibco.cep.mapper.xml.xdata.bind;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.xpath.Coercion;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;

/**
 * Performs analysis of coercions & adds to the report.
 */
public final class CoercionChecker
{
    public static void checkCoercions(CoercionSet set, ExprContext initialContext, TemplateReport addToReport)
    {

        int ct = set.getCount();
        ExprContext at = initialContext;
        for (int i=0;i<ct;i++)
        {
            Coercion c = set.get(i);
            ErrorMessage em = c.checkApplyTo(at,new TextRange(0,0)); // text range doesn't matter here.
            if (em!=null)
            {
                // Just add first error, if any:
                addToReport.addExtendedError(new CoercionExtendedError(em));
                addToReport.setIsRecursivelyErrorFree(false);
                break;
            }
        }
    }

    static class CoercionExtendedError implements TemplateReportExtendedError
    {
        private ErrorMessage m_message;

        public CoercionExtendedError(ErrorMessage em)
        {
            m_message = em;
        }

        public boolean canFix(TemplateReport onReport)
        {
            return false;
        }

        public void fix(TemplateReport report)
        {
        }

        public void formatFragment(XmlContentHandler handler, NamespaceToPrefixResolver resolver) throws SAXException
        {
        }

        public String formatMessage(TemplateReport report)
        {
            String msg = ResourceBundleManager.getMessage(MessageCode.COERCION_ERROR,m_message.getMessage());
            return msg;
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_ERROR;
        }

        public void registerNamespaces(NamespaceContextRegistry nsContextRegistry)
        {
        }

        /**
         * For diagnostic/debugging purposes only.
         */
        public String toString()
        {
            return "Coercion error:" + m_message.getMessage();
        }
    }
}

