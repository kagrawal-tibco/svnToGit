package com.tibco.jxpath;

import javax.xml.transform.TransformerException;

import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 2:43 PM
*/

/**
 * Expression is designed to be a Re-entrant object.
 */
public interface Expression {

    /**
     * todo decide the return parameter. Re-entrant method and should thread safe.
     * @param context
     * @throws TransformerException
     */
    XObject eval (XPathContext context) throws TransformerException;
}
