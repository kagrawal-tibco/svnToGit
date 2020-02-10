package com.tibco.cep.runtime.service.ft.spi;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Oct 4, 2006
 * Time: 4:09:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FTNodeControl {

    void init();

    boolean preActivate(NodeInfo thisNode, NodeInfo[] allNodes);

    boolean preDeactivate(NodeInfo thisNode, NodeInfo[] allNodes);

    void postActivate(NodeInfo thisNode);

    void postDeactivate(NodeInfo thisNode);

    void shutdown();

}
