package com.tibco.cep.runtime.service.ft;

import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Sep 29, 2006
 * Time: 6:05:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FTNodeManager {

    public void start();

    public FTNode getNextPriorityNode();

    public FTNode getCurrentNode();

    public void activate(FTNode node);

    public void deactivate(FTNode node);

    public void addNode(FTNode node);

    public void removeNode(FTNode node, String uuid);

    public int getNodeCount();

    public FTNode[] getAllNodes();

    public boolean isPrimary(FTNode node);

    public FTNode getPrimaryNode();

    public String getCurrentNodeName();

    public FTNode shutdown();

    public FTNode shutdown(FTNode node);

     public FTNode getLastModifierNode();

    public FTNode getLastChangedNode();

    public String getClusterName();

    public Logger getLogger();

}
