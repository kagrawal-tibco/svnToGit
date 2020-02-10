package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.HashMap;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * A simple structure holding the resolvers used in a transform.
 */
public class DefaultStylesheetResolver implements StylesheetResolver {

    public static final DefaultStylesheetResolver INSTANCE = new DefaultStylesheetResolver();
    private HashMap mNameToTemplates = new HashMap();
    private StylesheetResolver mChainTo;
    private boolean mLocked;

    static {
        INSTANCE.lock();
    }

    public DefaultStylesheetResolver() {
    }

    public StylesheetBinding getStylesheetByLocation(String location)
    {
        if (mChainTo!=null) {
            return mChainTo.getStylesheetByLocation(location);
        }
        return null;
    }

    public TemplateBinding getTemplateByMatch(SmSequenceType match, ExpandedName mode)
    {
        if (mChainTo!=null) {
            return mChainTo.getTemplateByMatch(match,mode);
        }
        return null;
    }

    public TemplateSignatureReport getTemplateByName(ExpandedName name)
    {
        TemplateSignatureReport tsr = (TemplateSignatureReport) mNameToTemplates.get(name);
        if (tsr==null) {
            if (mChainTo!=null) {
                return mChainTo.getTemplateByName(name);
            }
        }
        return tsr;
    }

    public void addTemplate(TemplateSignatureReport tsr) {
        if (mLocked) {
            throw new IllegalStateException();
        }
        String n = tsr.getTemplate().getTemplateName();
        if (n!=null) {
            mNameToTemplates.put(ExpandedName.makeName(n),tsr);
        }
    }

    public void setChainTo(StylesheetResolver sr) {
        if (mLocked) {
            throw new IllegalStateException();
        }
        mChainTo = sr;
    }

    public void lock() {
        mLocked = true;
    }
}

