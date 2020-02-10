package com.tibco.cep.kernel.core.rete;

import com.tibco.cep.kernel.helper.IdentifierUtil;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.impl.ParentChildCondition;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 4:37:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParentChildJoinNode extends JoinNode {

    int m_parentIndex;
    int m_childIndex;

    public ParentChildJoinNode(Rule rule, ReteNetwork network, Identifier[] containsParentIdrs, Identifier[] containsChildIdrs, Condition condition, boolean isConcurrent) throws SetupException {
        super(rule, network, containsParentIdrs, containsChildIdrs, condition, isConcurrent);
        if(!(condition instanceof ParentChildCondition)) {
            throw new RuntimeException("Program Error: Condition passed in is not a ParentChildCondition");
        }
        Identifier parent = ((ParentChildCondition)condition).getParentIdentifier();
        Identifier child  = ((ParentChildCondition)condition).getChildIdentifier();

        m_parentIndex = IdentifierUtil.getIndex(containsParentIdrs, parent);
        m_childIndex  = IdentifierUtil.getIndex(containsChildIdrs, child);
    }

    /*
    public void setParentChildLookup(ParentChildLookup lookup) {
        m_parentChildLookup = lookup;
    }

    public ParentChildCondition getParentChildCondition() {
        return (ParentChildCondition) m_condition;
    }

    static public boolean containsParentIdentifier(Identifier[] set, Condition condition) {
        Identifier parent = ((ParentChildCondition)condition).getParentIdentifier();
        if((IdentifierImpl.getIndex(set, parent) != -1)) {
            return true;
        }
        else {
            return false;
        }
    }

    static public boolean containsChildIdentifier(Identifier[] set, Condition condition) {
        Identifier child  = ((ParentChildCondition)condition).getChildIdentifier();
        if((IdentifierImpl.getIndex(set, child) != -1)) {
            return true;
        }
        else {
            return false;
        }
    }

    private void propagateMatchChildren(int childKey, Object[] joinObjs) {
        Iterator ite;
        if(rightTable instanceof ObjectTable) {
            ite = ((ObjectTable)rightTable).objectIterator(childKey);
        }
        else {
            ite = ((TupleTable)rightTable).keyIterator(childKey);
        }
        while(ite.hasNext()) {
            joinRightObjects((Object[])ite.next(), joinObjs);
            try {
                if(m_condition.eval(joinObjs)) {
                    propagateObjects(joinObjs);
                }
            }
            catch(RuntimeException ex) {
                String errMsg = Helper.resources.formatString("rule.condition.exception", m_condition.getRule().getName(), m_condition);
                Logger.traceError(errMsg,ex);
                continue;
            }
        }
    }

    synchronized public void assertFromLeft(Object[] leftObjs) {
        Concept parent = (Concept) leftObjs[m_parentIndex];
        if(m_saveLeftTuple) {
            if(leftTable instanceof ObjectTable) {
                ((ObjectTable)leftTable).add(leftObjs[0]);
            }
            else {
                TupleRow row = new TupleRowBasic(leftObjs, parent.hashCode());
                ((TupleTable)leftTable).add(row);
            }
        }
        //check the lookup table
        if (m_parentChildLookup != null) {
            long[] childKeys = m_parentChildLookup.addParent(((ParentChildCondition)m_condition).getProperty(parent));
            if(childKeys != null) {
                Object[] joinObjs = new Object[numTotalIdentifier];
                joinLeftObjects(leftObjs, joinObjs);
                for(int i = 0; i < childKeys.length; i++) {
                    propagateMatchChildren(URIImpl.hashCode(childKeys[i]), joinObjs);
                }
            }
        }
        else {  //use parent property
            Property p = ((ParentChildCondition)m_condition).getProperty(parent);
            if(p instanceof PropertyAtomConcept) {
                Concept child = ((PropertyAtomConcept)p).getConcept();
                if(child != null) {
                    Object[] joinObjs = new Object[numTotalIdentifier];
                    joinLeftObjects(leftObjs, joinObjs);
                    propagateMatchChildren(child.hashCode(), joinObjs);
                }
            }
            else if (p instanceof PropertyArrayConcept) {
                PropertyAtom[] arr = ((PropertyArrayConcept)p).toArray();
                if(arr != null) {
                    Object[] joinObjs = new Object[numTotalIdentifier];
                    joinLeftObjects(leftObjs, joinObjs);
                    for(int i = 0; i < arr.length; i++) {
                        Concept child = ((PropertyAtomConcept)arr[i]).getConcept();
                        if(child != null) {
                            propagateMatchChildren(child.hashCode(), joinObjs);
                        }
                    }
                }
            }
        }
    }

    private void propagateMatchParent(int parentKey, Object[] joinObjs) {
        Iterator ite;
        if(leftTable instanceof ObjectTable) {
            ite = ((ObjectTable)leftTable).objectIterator(parentKey);
        }
        else {
            ite = ((TupleTable)leftTable).keyIterator(parentKey);
        }
        while(ite.hasNext()) {
            joinLeftObjects((Object[])ite.next(), joinObjs);
            try {
                if(m_condition.eval(joinObjs)) {
                    propagateObjects(joinObjs);
                }
            }
            catch(RuntimeException ex) {
                String errMsg = Helper.resources.formatString("rule.condition.exception", m_condition.getRule().getName(), m_condition);
                Logger.traceError(errMsg,ex);
                continue;
            }
        }
    }


    synchronized public void assertFromRight(Object[] rightObjs) {
        Concept child = (Concept) rightObjs[m_childIndex];
        if(m_saveRightTuple) {
            if(rightTable instanceof ObjectTable) {
                ((ObjectTable)rightTable).add(rightObjs[0]);
            }
            else {
                TupleRow row = new TupleRowBasic(rightObjs, child.hashCode());
                ((TupleTable)rightTable).add(row);
            }
        }
        if (m_parentChildLookup != null) {
            long[] parentKeys = m_parentChildLookup.getChildParentSet(child.getId());
            if(parentKeys != null) {
                Object[] joinObjs = new Object[numTotalIdentifier];
                joinRightObjects(rightObjs, joinObjs);
                for(int i = 0; i < parentKeys.length; i++) {
                    propagateMatchParent(URIImpl.hashCode(parentKeys[i]), joinObjs);
                }
            }
        }
        else {
            Object[] joinObjs = new Object[numTotalIdentifier];
            joinRightObjects(rightObjs, joinObjs);

            Iterator ite = leftTable.iterator();
            while(ite.hasNext()) {
                joinLeftObjects((Object[])ite.next(), joinObjs);
                try {
                    if(m_condition.eval(joinObjs)) {
                        propagateObjects(joinObjs);
                    }
                }
                catch(RuntimeException ex) {
                    String errMsg = ex.getLocalizedMessage();
                    Logger.traceError(errMsg,ex);
                    continue;
                }
            }
        }
    }

    public String toString() {
        String joinIndexForLeft  = new String();
        String joinIndexForRight = new String();

        for(int i = 0; i < m_joinIndexForLeft.length; i++) {
            joinIndexForLeft += m_joinIndexForLeft[i] + " ";
        }

        for(int i = 0;  i < m_joinIndexForRight.length; i++) {
            joinIndexForRight += m_joinIndexForRight[i] + " ";
        }

        String linkTo = new String();
        if(m_nextNodeLink.getLinkToNode() instanceof JoinNode) {
            if(m_nextNodeLink.isLinkToRight()) {
                linkTo = m_nextNodeLink.getLinkToNode().getNodeId() + "R";
            }
            else {
                linkTo = m_nextNodeLink.getLinkToNode().getNodeId() + "L";
            }
        }
        else {
            linkTo = ""+ m_nextNodeLink.getLinkToNode().getNodeId();
        }
        String tableIds = "" + leftTable.getTableId() + "L," + rightTable.getTableId() + "R";

        return "\t[ParentChildJoinNode("+m_nodeId+") link("+linkTo+") tables(" + tableIds + "): \n" +
                "\t\tLeft(P) Identifier   = " + IdentifierImpl.toString(m_leftIdentifiers) + ";\n" +
                "\t\tParent Left Index    = " + m_parentIndex + ";\n" +
                "\t\tRight(c) Identifier  = " + IdentifierImpl.toString(m_rightIdentifiers) + ";\n" +
                "\t\tChild Right Index    = " + m_childIndex + ";\n" +
                "\t\tOut Identifier       = " + IdentifierImpl.toString(m_identifiers) + ";\n" +
                "\t\tCondition Identifier = " + IdentifierImpl.toString(m_condition.getIdentifiers())+ ";\n" +
                "\t\tJoin Left Index      = " + joinIndexForLeft + ";\n" +
                "\t\tJoin Right Index     = " + joinIndexForRight + ";\n" +
                "\t\tCondition            = " + m_condition + "]";
    }
    */
}
