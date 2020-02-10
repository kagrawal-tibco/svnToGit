package com.tibco.cep.mapper.xml.xdata.bind;

import com.tibco.cep.mapper.xml.xdata.bind.virt.BindingVirtualizer;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * A few helper methods that set-up a template to be analyzed (virtualize, optionally reparent), etc.
 */
public final class TemplateReportGenerationUtils
{
    public static TemplateEditorConfiguration createConfiguration(TemplateEditorConfiguration tec, NamespaceContextRegistry namespaces, CancelChecker cancelChecker)
    {
        Binding b = tec.getBinding();
        StylesheetBinding sb = new StylesheetBinding(BindingElementInfo.EMPTY_INFO); // for holding namespaces only.

        sb.setElementInfo(getElementInfoForStylesheet(namespaces));

        // Add the template to a 'fake' stylesheet which holds the ns decls.
        if (b.getParent()!=null)
        {
            // just in case.
            b = b.cloneDeep();
        }
        sb.addChild(b);

        // Before we virtualize, must have all namespaces in context (done above w/ stylesheet)
        Binding virtualized = BindingVirtualizer.INSTANCE.virtualize(b,cancelChecker);
        if (cancelChecker.hasBeenCancelled())
        {
            return null;
        }
        sb.removeChild(0);
        TemplateEditorConfiguration tec2 = (TemplateEditorConfiguration) tec.clone();
        tec2.setBinding((TemplateBinding) virtualized);

        sb.addChild(virtualized);
        return tec2;
    }

    /**
     * Since the binding display can (and should) use namespaces from the process definition namespaces, this sets
     * them up for use in the editor.
     */
    private static BindingElementInfo getElementInfoForStylesheet(NamespaceContextRegistry nm)
    {
        return new BindingElementInfo(nm);
    }
}

