package com.tibco.be.util;

import java.util.HashMap;

import com.tibco.xml.schema.SmParticleTerm;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Apr 20, 2005
 * Time: 1:00:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class SmModel2ExpandedName {
    public HashMap mapModelGroup2Name_ExpandedName = new HashMap();
    public HashMap mapModelGroup2Name_ExpandedName_ForAttributes = new HashMap();
    public SmParticleTerm smParticleTerm = null;

    public SmModel2ExpandedName(SmParticleTerm term) {
        smParticleTerm =term;
    }
}
