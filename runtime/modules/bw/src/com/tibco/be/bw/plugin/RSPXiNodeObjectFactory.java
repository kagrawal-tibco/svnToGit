package com.tibco.be.bw.plugin;

import com.tibco.pe.load.ReadOnlyXiNodeObjectFactory;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 19, 2007
 * Time: 11:47:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class RSPXiNodeObjectFactory extends ReadOnlyXiNodeObjectFactory {
    private static final ExpandedName name = ExpandedName.makeName("BWSharedResource");

    public RSPXiNodeObjectFactory()
    {
        super (name);
    }
}


