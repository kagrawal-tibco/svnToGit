package com.tibco.cep.mapper.xml.xdata.bind.fix;

import java.util.ArrayList;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.xml.schema.SmSequenceType;

/**
 * A dialog which shows errors & allows selective choosing of which elements to fix.
 */
public class MissingOutputBindingFixerChange extends BindingFixerChange
{
    private final SmSequenceType mMissingType;
    private final boolean mIsPreceding;
    private final int mPrecedingIndex;

    public MissingOutputBindingFixerChange(TemplateReport report, SmSequenceType missingType, boolean isPreceding, int precedingIndex) {
        super(report,true);

        mIsPreceding = isPreceding;
        mMissingType = missingType;
        mPrecedingIndex = precedingIndex;
    }

    public String computeMessage()
    {
        String typeAsString = mMissingType.toString(); //WCETODO fixme --- use no namespaced version of this.
        if (mIsPreceding) {
            return ResourceBundleManager.getMessage(MessageCode.EXPECTED_OUTPUT_BEFORE, typeAsString);
        } else {
            return ResourceBundleManager.getMessage(MessageCode.EXPECTED_OUTPUT_AT_END, typeAsString);
        }
    }

    public boolean isPreceding()
    {
        return mIsPreceding;
    }

    public int getCategory()
    {
        // Always consider it an error; however in certain circumstances (depending on validation config), this may
        // be not generated at all for optional things.
        return CATEGORY_ERROR;
    }

    public void performMove(ArrayList moveList)
    {
        if (mIsPreceding)
        {
            MarkerBinding marker = new MarkerBinding(mMissingType);
            // Because of moves, must put in move list:
            MarkerAddition ma = new MarkerAddition();
            ma.m_on  = getTemplateReport();
            ma.m_precedingIndex = mPrecedingIndex;
            ma.m_marker = marker;
            moveList.add(ma);
            /*
            Binding p = b.getParent();
            int ioc = p.getIndexOfChild(b);
            p.addChild(ioc,marker);*/
        }
    }

    public void performNonMove()
    {
        if (!mIsPreceding)
        {
            Binding b = getTemplateReport().getBinding();
            MarkerBinding marker = new MarkerBinding(mMissingType);
            b.addChild(marker);
        }
    }
}
