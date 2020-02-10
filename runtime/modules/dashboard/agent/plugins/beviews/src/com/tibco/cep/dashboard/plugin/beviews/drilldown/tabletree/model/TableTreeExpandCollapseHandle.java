package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.ExpandCollapseHandle;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.ExpandCollapseHandleConfigurator;

/**
 * @author rajesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableTreeExpandCollapseHandle extends ExpandCollapseHandle{

    private TableTree table;

    /**
     * @return
     */
    public String getIDPrefix()
    {
        return "tableTree_";
    }

    /**
     * @return
     */
    public String getFrameId()
    {
        return table.getFrameId();
    }

    /**
     * @param prefix
     * @return
     */
    public String computeId(String prefix)
    {
        return table.computeId(prefix) + TableTree.ID_SEPARATOR + subPath;
    }

    /**
     * @return
     */
    public String getRowIndexParams()
    {
        return "&" + TableTreeConstants.KEY_ROW_INDEX + "="
        + rowIndex
        + "&" + TableTreeConstants.KEY_ROW_PATH + "="
        + subPath;
    }

    /**
     * 
     */
    public TableTreeExpandCollapseHandle(
        TableTree table,
        ExpandCollapseHandleConfigurator configurator) {

        super(configurator);
        this.table = table;
        table.setExpandCollapseHandle(this);
    }
}
