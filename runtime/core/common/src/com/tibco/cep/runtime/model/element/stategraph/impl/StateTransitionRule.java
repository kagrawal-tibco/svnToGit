package com.tibco.cep.runtime.model.element.stategraph.impl;


import com.tibco.cep.kernel.helper.BitSet;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.StateGuardCondition;
import com.tibco.cep.kernel.model.rule.impl.EquivalentCondition;
import com.tibco.cep.kernel.model.rule.impl.IdentifierImpl;
import com.tibco.cep.kernel.model.rule.impl.RuleImpl;
import com.tibco.cep.kernel.model.rule.impl.StateIdentifierImpl;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineRule;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 10, 2006
 * Time: 8:51:29 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class StateTransitionRule extends RuleImpl implements Action, StateMachineRule {

    public StateTransitionRule(
            String _name,
            String uri) {
        super(_name, uri);
        m_actions = new Action[] {this};
    }

    public StateTransitionRule(
            String _name,
            String uri,
            int _priority) {
        super(_name, uri, _priority);
        m_actions = new Action[] {this};
    }

    protected void setIdentifierDependencyBitArray(DependencyIndex[][] dependencyIndices) {
//        System.err.println("Setting Identifier Bit Array " + this.getDescription());
        super.setIdentifierDependencyBitArray(dependencyIndices);
//            ((StateIdentifierImpl) this.getIdentifiers()[0]).setDependencyBitArray_Child(childDependencies);
        StateGuardCondition guardCondition=null;

        for(int x = 0; x < m_conditions.length; x++) {
            if(m_conditions[x] instanceof StateGuardCondition) {
                guardCondition= (StateGuardCondition) m_conditions[x];
                Identifier [] idrs = m_conditions[x].getIdentifiers();
                for (int i=0; i < idrs.length; i++) {
                    ((StateIdentifierImpl) idrs[i]).setRule(this);
                }
            }
        }

        StateIdentifierImpl guardIdentifier= (StateIdentifierImpl) guardCondition.getIdentifiers()[0];
        guardIdentifier.setRule(this);

        for (int j=0; j < m_identifiers.length; j++) {
            IdentifierImpl idx = (IdentifierImpl) m_identifiers[j];
            if (!(idx instanceof StateIdentifierImpl)) {
                if (idx.equals(guardIdentifier)) {
                    guardIdentifier.setDependencyBitArray(idx.getDependencyBitArray());
                    m_identifiers[j]=guardIdentifier;
                }
            }
        }

        for (int j=0; j < m_identifiers.length; j++) {
            if (m_identifiers[j] instanceof StateIdentifierImpl) {
                IdentifierImpl idx = (IdentifierImpl) m_identifiers[j];
                for(int x = 0; x < m_conditions.length; x++) {
                    setGuardIdentifier(m_conditions[x].getIdentifiers(), m_identifiers[j]);
                    if(m_conditions[x] instanceof EquivalentCondition) {
                        setGuardIdentifier(((EquivalentCondition)m_conditions[x]).getLeftIdentifiers(), m_identifiers[j]);
                        setGuardIdentifier(((EquivalentCondition)m_conditions[x]).getRightIdentifiers(), m_identifiers[j]);
                    }
                }
            }
        }
        setIdentifierDependencyBitArray_Child(dependencyIndices);
    }

    protected void setIdrChildBitArray(Identifier[] idrs, int [] bitMap) {
        for(int y = 0; y < idrs.length; y++) {
            if(idrs[y] instanceof StateIdentifierImpl) {
                ((StateIdentifierImpl)idrs[y]).setDependencyBitArray_Child(bitMap); 
            }
        }
    }

    protected void setIdentifierDependencyBitArray_Child(DependencyIndex[][] dependencyIndices) {
        for(int i = 0; i < dependencyIndices.length; i++) {
            boolean isStateIdentifier=false;

            DependencyIndex[] propertyIndex = dependencyIndices[i];
            if (propertyIndex == null) {
                continue;
            }
            int maxIdx = -1;
            for(int j = 0; j < propertyIndex.length; j++) {
                if (propertyIndex[j] instanceof ChildDependencyIndex) {
                    isStateIdentifier=true;
                    if (maxIdx < propertyIndex[j].position)
                        maxIdx = propertyIndex[j].position;
                }
            }
            if (!isStateIdentifier) {
                continue;
            }
            
            int[] dependencyBitArray = new int[maxIdx/32 + 1];
            for(int k = 0; k < propertyIndex.length; k++) {
                if (propertyIndex[k] instanceof ChildDependencyIndex) {
                    BitSet.setBit(dependencyBitArray, propertyIndex[k].position);
                }
            }
            for (int j=0; j < m_identifiers.length; j++) {
                if (m_identifiers[j] instanceof StateIdentifierImpl) {
                    ((StateIdentifierImpl) m_identifiers[j]).setDependencyBitArray_Child(dependencyBitArray);
                }
            }

            //todo- nl, put this in code gen instead
            for(int x = 0; x < m_conditions.length; x++) {
                setIdrChildBitArray(m_conditions[x].getIdentifiers(), dependencyBitArray);
                if(m_conditions[x] instanceof EquivalentCondition) {
                    setIdrChildBitArray(((EquivalentCondition)m_conditions[x]).getLeftIdentifiers(), dependencyBitArray);
                    setIdrChildBitArray(((EquivalentCondition)m_conditions[x]).getRightIdentifiers(), dependencyBitArray);
                }
            }
        }
    }

    protected void setGuardIdentifier(Identifier[] idrs, Identifier master) {
        for(int y = 0; y < idrs.length; y++) {
            if(idrs[y].equals(master)) {
                idrs[y]=master;
                //((IdentifierImpl)idrs[y]).setDependencyBitArray(master.getDependencyBitArray());
            }
        }
    }

    public void execute(Object[] objects) {
        RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getLogger(this.getClass()).log(
                Level.DEBUG, "Transition rule [%s] fired on %s", getName(), objects[0]);
     action(objects);
     transition(objects);
        // Should be overridden by the generated child class
        // Cancel the timer, if any
    }

    protected abstract void  action(Object[] objects);
    protected abstract void  transition(Object[] objects);

    public Rule getRule() {
        return this;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
