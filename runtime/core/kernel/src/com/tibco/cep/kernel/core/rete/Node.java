package com.tibco.cep.kernel.core.rete;

import com.tibco.cep.kernel.helper.MatchedList;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.ResourceManager;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 2:16:40 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class Node {
    protected Identifier[] m_identifiers;
    protected NodeLink     m_nextNodeLink;
    protected int          m_nodeId;
    protected ReteNetwork  m_rete;
    protected ReteWM       m_wm;
    protected Rule         m_rule;
    final protected Logger m_logger;

    boolean  loadStopHere = false;

    static int currentNodeId = 0;
    synchronized public int getNextId() {
        int retId = currentNodeId++;
        return retId;
    }

    public Node(Rule rule, Identifier[] identifiers, ReteNetwork retenetwork) {
        m_nextNodeLink = null;
        m_identifiers = identifiers;
        m_nodeId = getNextId();
        m_rete = retenetwork;
        m_rule = rule;
        m_wm = (ReteWM) m_rete.getWorkingMemory();
        m_logger = m_wm.getLogManager().getLogger(Node.class);
    }

    public Rule getRule() {
        return m_rule;
    }

    public WorkingMemory getWorkingMemory() {
        return m_rete.getWorkingMemory();
    }

    public ReteNetwork getReteNetwork() {
        return m_rete;
    }

    public Identifier[] getIdentifiers() {
        return m_identifiers;
    }

    public int getNodeId() {
        return m_nodeId;
    }

    abstract void assertObjects(Handle[] handles, Object[] objects, boolean right);

    abstract MatchedList findMatch(MatchedList resultList, boolean right);   //arrayList should perform better than Set

    void clear() {
        m_nextNodeLink.getLinkToNode().clear();
    }

    public void propagateObjects(Handle[] handles, Object[] objects) {
        m_nextNodeLink.propagateObjects(handles, objects);
    }

    protected void addNodeLink(NodeLink nodelink) throws SetupException {
        if (m_nextNodeLink == null) {
            m_nextNodeLink = nodelink;
        }
        else {
            throw new SetupException(ResourceManager.getString("rete.node.nodeLinkAlreadySet"));
        }
    }

}
