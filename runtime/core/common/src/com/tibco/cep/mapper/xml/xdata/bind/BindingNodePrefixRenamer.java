package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.Map;

import com.tibco.cep.mapper.xml.nsutils.NodePrefixRenamer;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.appl.ApplicationObjectXiNode;

/**
 * A prefix rename for bindings.
 */
public class BindingNodePrefixRenamer implements NodePrefixRenamer
{
    public static final NodePrefixRenamer INSTANCE = new BindingNodePrefixRenamer();

    private BindingNodePrefixRenamer()
    {
    }

    public void renameLocalPrefixUsages(XiNode node, Map oldToNewPrefixMap)
    {
        ApplicationObjectXiNode apn = (ApplicationObjectXiNode) node;
        Binding b = (Binding) apn.getApplicationObject();
        b.renamePrefixUsages(oldToNewPrefixMap);
    }
}
