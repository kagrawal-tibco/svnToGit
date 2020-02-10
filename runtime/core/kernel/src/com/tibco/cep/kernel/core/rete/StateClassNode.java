package com.tibco.cep.kernel.core.rete;

import com.tibco.cep.kernel.model.entity.StateMachineElement;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.rule.Rule;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 31, 2009
 * Time: 10:28:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class StateClassNode extends ClassNode{
    public int [][]    ruleIndices;

    public StateClassNode(ReteNetwork reteNetwork, Class cl) {
        super(reteNetwork, cl);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void setUpTable(int index, Rule rule) {
        if (ruleIndices == null) {
            ruleIndices  = new int[index+1][];
        } else if (index >= ruleIndices.length){
            int [][] rtmp=ruleIndices;
            ruleIndices  = new int[index+1][];
            System.arraycopy(rtmp, 0, ruleIndices,0,rtmp.length);
        }
        if (ruleIndices[index] == null) {
            ruleIndices[index] = new int[] {rule.getId()};
            return;
        } else {
            int[] tmp = ruleIndices[index];
            ruleIndices[index] = new int[tmp.length+1];
            System.arraycopy(tmp,0,ruleIndices[index],0,tmp.length);
            ruleIndices[index][tmp.length]=rule.getId();
        }
        return;
    }
    /**
     * @param rule
     */
//    protected void addRule(Rule rule) {
//    }
//
//    protected void removeRule(Rule rule) {
//        if(identifierIndexTable == null) {
//            identifierIndexTable = new int[rule.getId() + 1][];
//        }
//        else if ((rule).getId() >= identifierIndexTable.length) {
//            int[][] oldTable = identifierIndexTable;
//            identifierIndexTable = new int[(rule).getId() + 1][];
//            System.arraycopy(oldTable, 0, identifierIndexTable, 0, oldTable.length);
//        }
//        identifierIndexTable[(rule).getId()] = null;
//        for(int i = 0; i < subNodes.size(); i++) {
//            ((ClassNode)subNodes.get(i)).removeRule(rule);
//        }
//    }

    protected void stateChanged(StateMachineElement sm, int index, Handle ownerConcept) {
        // Get the next rules
        if (ruleIndices != null) {
            if (index < ruleIndices.length) {
                int[] rules= ruleIndices[index];
                if ((rules != null) && (rules.length > 0)) {
                    ClassNode cn=m_rete.getClassNode(ownerConcept.getTypeInfo().getType());
                    if (cn != null) {
                        cn.evaluateRules(ownerConcept, rules);
                    }
                }
            }
        }
    }

    protected boolean stateChangedHasRules(StateMachineElement sm, int index) {
        // Get the next rules
        if (ruleIndices != null) {
            if (index < ruleIndices.length) {
                int[] rules= ruleIndices[index];
                if ((rules != null) && (rules.length > 0)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
