package com.tibco.cep.query.utils;

import com.tibco.cep.query.model.QueryContext;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 14, 2008
 * Time: 8:08:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class SourceTreeAdaptor extends BaseObjectTreeAdaptor {
    public SourceTreeAdaptor() {
        super();
    }

    public String getText(ObjectTreeNode t) {
        if(t instanceof QueryContext) {
            return ((QueryContext)t).getSourceString();
        } else
            return super.getText(t);
    }
}
