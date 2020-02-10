package com.tibco.cep.kernel.core.rete;

import com.tibco.cep.kernel.helper.IdentifierUtil;
import com.tibco.cep.kernel.helper.MatchedList;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.service.ResourceManager;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 2:16:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeLink {
    int[]        m_convert;
    Identifier[] m_parentIdentifer;
    Identifier[] m_childIdentifer;
    int          m_numIdentifier;

    Node      m_parent;
    Node      m_child;
    boolean   m_childRight;

    public boolean m_active;

//    private Handle[] convertedHandles;
//    private Object[] convertedObjects;

    protected NodeLink(Node parentNode, Node childNode, boolean childRight) throws SetupException {
        m_parent     = parentNode;
        m_child      = childNode;
        m_childRight = childRight;

        m_parentIdentifer = parentNode.getIdentifiers();
        if(childNode instanceof JoinNode) {
            if(childRight) {
                m_childIdentifer = ((JoinNode)childNode).getRightIdentifiers();
            }
            else {
                m_childIdentifer = ((JoinNode)childNode).getLeftIdentifiers();
            }
        }
        else {
            m_childIdentifer  = childNode.getIdentifiers();
        }

        setConvert();
        m_parent.addNodeLink(this);
        m_active = true;
    }

    protected NodeLink(Node parentNode, Node childNode) throws SetupException {
        this(parentNode, childNode, false);
        if (childNode instanceof JoinNode) {
            throw new SetupException(ResourceManager.getString("rete.nodelink.childIsJoinNode"));
        }
    }

    protected NodeLink(Node childNode, boolean childRight) {
        m_parent = null;
        m_child  = childNode;
        m_childRight = childRight;
        m_convert    = null;

        m_parentIdentifer = null;
        m_childIdentifer  = childNode.getIdentifiers();
        m_active = true;
    }

    public Node getLinkToNode() {
        return m_child;
    }

    public boolean isLinkToRight() {
        return m_childRight;
    }


    private void setConvert() throws SetupException {
        if(m_parentIdentifer.length != m_childIdentifer.length) {
            throw new SetupException(ResourceManager.formatString("rete.nodelink.identifierMismatch",
                    IdentifierUtil.toString(m_parentIdentifer),
                    IdentifierUtil.toString(m_childIdentifer), m_parent.getRule().getName()));
        }
        m_numIdentifier = m_parentIdentifer.length;
        m_convert = new int[m_numIdentifier];
        for(int i = 0; i < m_numIdentifier; i++) {
            boolean found = false;
            for(int j = 0; j < m_numIdentifier; j++) {
                if (m_parentIdentifer[i].equals(m_childIdentifer[j])) {
                    found = true;
                    m_convert[i] = j;
                    break;
                }
            }
            if (found == false) {
                throw new SetupException(ResourceManager.formatString("rete.nodelink.identifierMismatch",
                        IdentifierUtil.toString(m_parentIdentifer),
                        IdentifierUtil.toString(m_childIdentifer), m_parent.getRule().getName()));
            }
        }
        //test if really need conversion
        boolean need = false;
        for(int i = 0; i < m_numIdentifier; i++) {
            if(m_convert[i] != i) {
                need = true;
                break;
            }
        }
        if (!need) {
            m_convert = null;
        }
//        else {
//            convertedHandles = new Handle[m_numIdentifier];
//            convertedObjects = new Object[m_numIdentifier];
//        }
    }

    public void propagateObjects(Handle[] handles, Object[] objects) {
        if (m_convert != null) {
            Handle [] convertedHandles = new Handle[m_numIdentifier];
            Object [] convertedObjects = new Object[m_numIdentifier];

            for(int i = 0; i < m_numIdentifier; i++) {
                convertedHandles[m_convert[i]] = handles[i];
                convertedObjects[m_convert[i]] = objects[i];
            }
            m_child.assertObjects(convertedHandles, convertedObjects, m_childRight);
        }
        else {
            m_child.assertObjects(handles, objects, m_childRight);
        }
    }

    public MatchedList findMatch(MatchedList resultList) {
        if (m_convert != null) {
            for(int k=0; k < resultList.length(); k++) {
                Object[] orgObjs = resultList.getRow(k);
                if(orgObjs != null) {
                    Object[] cvtObjs = new Object[m_numIdentifier];
                    for(int i = 0; i < m_numIdentifier; i++) {
                        cvtObjs[m_convert[i]] = orgObjs[i];
                    }
                    resultList.chgRow(k, cvtObjs);
                }
            }
        }
        return m_child.findMatch(resultList, m_childRight);
    }

    public String toString() {
        String nextNode;
        if(m_child instanceof JoinNode) {
            if (m_childRight) {
                nextNode = m_child.m_nodeId + "R";
            }
            else {
                nextNode = m_child.m_nodeId + "L";
            }
        }
        else {
            nextNode = Integer.toString(m_child.m_nodeId);
        }
        return "link (" + nextNode + ")";
    }


}
