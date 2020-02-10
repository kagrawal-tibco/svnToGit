package com.tibco.cep.mapper.xml.xdata.bind.fix;

import java.util.ArrayList;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.DataComponentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportErrorFormatter;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * A binding change that either deletes or renames the item.
 */
public class UnexpectedOutputBindingFixerChange extends BindingFixerChange {
    /**
     * Constructor for when this is unexpected....
     * @param report
     */
    public UnexpectedOutputBindingFixerChange(TemplateReport report) {
        super(report,true);

        if (report.getMoveToPreceding()==null && report.getRenameTo()==null) {
            if (!(report.getBinding() instanceof MarkerBinding)) {
                // Except for marker comments, by default, don't delete it:
                setDoApply(false);
            }
        }
    }

    public int getCategory()
    {
        // This is an error, for sure.
        return CATEGORY_ERROR;
    }

    public String computeMessage() {
        TemplateReport report = getTemplateReport();
        if (report.getMoveToPreceding()!=null)
        {
            Binding mt = report.getMoveToPreceding().getBinding();
            String ln;
            if (mt instanceof MarkerBinding)
            {
                SmSequenceType t = ((MarkerBinding)mt).getMarkerType();
                ln = SmSequenceTypeSupport.getDisplayName(t);
            }
            else
            {
                ln = mt.getName().getLocalName();
            }
            if (report.getMoveToPrecedingIndex()!=-1)
            {
                return ResourceBundleManager.getMessage(MessageCode.MOVE_TO_BEFORE,ln);
            }
            else
            {
                return ResourceBundleManager.getMessage(MessageCode.MOVE_TO,ln);
            }
        } else {
            if (report.getRenameTo()!=null)
            {
                if (isNamespaceRename())
                {
                    return ResourceBundleManager.getMessage(MessageCode.RENAME_NAMESPACE,report.getRenameTo().getNamespaceURI());
                }
                else
                {
                    return ResourceBundleManager.getMessage(MessageCode.RENAME,report.getRenameTo().getLocalName());
                }
            }
            else
            {
                return TemplateReportErrorFormatter.formatErrorIgnoreFormula(report,"mapping");
            }
        }
    }

    /**
     * Indicates if this is a namespace change renaming.
     */
    public boolean isNamespaceRename()
    {
        TemplateReport report = getTemplateReport();
        if (report.getRenameTo()!=null)
        {
            return report.getRenameTo().getLocalName().equals(report.getBinding().getName().getLocalName());
        }
        return false;
    }

    /**
     * Indicates if this is move change.
     */
    public boolean isMove()
    {
        TemplateReport report = getTemplateReport();
        if (report.getMoveToPreceding()!=null)
        {
            return true;
        }
        return false;
    }

    public final void performMove(ArrayList moveList) {
        // delete, move, or rename it:

        // (Only do move here)
        if (getTemplateReport().getMoveToPreceding()!=null) {
            // move it:
            // (These are done in a separate pass because they require ordering)
            moveList.add(getTemplateReport());
        }
    }

    public void performNonMove()
    {
        // delete or rename here:
        if (getTemplateReport().getMoveToPreceding()==null) {
            if (getTemplateReport().getRenameTo()!=null) {
                // rename it:
                Binding br = getTemplateReport().getBinding();
                ((DataComponentBinding)br).setLiteralName(getTemplateReport().getRenameTo());
            } else {
                // delete it:
                Binding b = getTemplateReport().getBinding();
                Binding p = b.getParent();
                int ioc = p.getIndexOfChild(b);
                p.removeChild(ioc);
            }
        }
    }
}
