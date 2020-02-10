package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author rajesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JSFunction {

    private boolean bReturn = true;

    private String name;

    private ArrayList listParams;

    /**
     * 
     */
    public JSFunction(String name) {
        this.name = name;
    }
    
    public void addStringParam(String value)
    {
        if (listParams == null)
        {
            listParams = new ArrayList();
        }
        value = "'" + escape(value) + "'"; 
        listParams.add(value);
    }
    
    public void addNonStringParam(String value)
    {
        if (listParams == null)
        {
            listParams = new ArrayList();
        }
        listParams.add(escape(value));
    }

    /**
     * @param value
     * @return
     */
    private String escape(String value) {
        return value;
    }

    public void setReturn(boolean bReturn)
    {
        this.bReturn = bReturn;
    }
    
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        if (bReturn)
        {
            buffer.append("return ");
        }
        buffer.append(name);
        buffer.append("(");
        boolean bFirst = true;
        for (Iterator itParams = listParams.iterator(); itParams.hasNext();) {
            String param = (String) itParams.next();
            if (!bFirst)
            {
                buffer.append(", ");
            }
            buffer.append(param);
            bFirst = false;
        }
        buffer.append(");");
        return buffer.toString();
    }

    /**
     * @param rowIndex
     */
    public void addStringParam(int param) {
        addStringParam(String.valueOf(param));
    }

    public void addNonStringParam(int param)
    {
        addNonStringParam(String.valueOf(param));
    }
}
