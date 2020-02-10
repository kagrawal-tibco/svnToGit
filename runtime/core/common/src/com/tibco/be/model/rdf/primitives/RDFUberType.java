package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 5, 2004
 * Time: 5:51:40 PM
 * To change this template use Options | File Templates.
 */
public interface RDFUberType {


    boolean isA(RDFUberType anotherType);

    String getName();

    boolean isReference();

    SmType getXSDLTerm();

    String getRuntimeTerm();


}
