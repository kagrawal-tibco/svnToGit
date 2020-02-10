package com.tibco.cep.kernel.core.rete;

import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.helper.IdentifierUtil;
import com.tibco.cep.kernel.helper.MatchedList;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.rule.Rule;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 2:17:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleNode extends Node {

    Rule    m_rule;
    boolean m_singleIdentifier;
    ReteWM  m_wm;

    public RuleNode(ReteNetwork network, Rule rule) {
        super(rule, rule.getIdentifiers(), network);
        m_rule = rule;
        m_wm   = (ReteWM) network.getWorkingMemory();
    }

    MatchedList findMatch(MatchedList resultList, boolean right) {
        return resultList;
    }

    public void assertObjects(Handle[] handles, Object[] objects, boolean right) {
//        if(m_rule.isActive() && !m_wm.loadingObjects  && m_wm.activeModeOn) {
        if(m_rule.isActive()  && m_wm.activeModeOn) {
            m_wm.getResolver().put(m_rule, handles);
        }
    }

    public String toString() {
        return "\t[RuleNode id("+m_nodeId+"): " + Format.BRK +
                "\t\tIdentifier           = " + IdentifierUtil.toString(this.m_identifiers) + " ;" + Format.BRK +
                "\t\tRule                 = " + m_rule.getClass().getName() + "]";
    }

}
