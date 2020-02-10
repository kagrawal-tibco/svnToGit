package com.tibco.cep.kernel.model.rule.impl;

import com.tibco.cep.kernel.helper.BitSet;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.InvalidRuleException;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.model.rule.StateGuardCondition;
import com.tibco.cep.kernel.service.ResourceManager;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 4:07:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleImpl implements Rule {

    protected int     m_priority;
    int     m_id;
    String  m_name;
    boolean m_active;
    RuleFunction m_rank = null;
    protected String  uri;

    protected Identifier[] m_identifiers;
    protected Condition[]  m_conditions;
    protected Action[]     m_actions;
    protected String       m_loadTime = null;

    static int currentRuleId = 0;
    synchronized public int getNextRuleId() {
        int retId = ++currentRuleId;
        return retId;
    }

    public RuleImpl(
            String _name,
            String uri) {
        this(_name, uri, DEFAULT_PRIORITY);
    }

    public RuleImpl(
            String _name,
            String uri,
            int _priority) {
        m_id          = getNextRuleId();
        m_name        = _name;
        m_priority    = _priority;
        m_active      = false;
        this.uri = uri;
    }

    protected void setIdentifierDependencyBitArray(DependencyIndex[][] dependencyIndices) {
        for(int i = 0; i < dependencyIndices.length; i++) {
            DependencyIndex[] propertyIndex = dependencyIndices[i];
            if(propertyIndex == null) {
                ((IdentifierImpl)m_identifiers[i]).setDependencyBitArray(null);
            }
            else {
                int maxIdx = -1;
                for(int j = 0; j < propertyIndex.length; j++) {
                    if (!(propertyIndex[j] instanceof ChildDependencyIndex)) {
                        if (maxIdx < propertyIndex[j].position)
                            maxIdx = propertyIndex[j].position;
                    }
                }
                int[] dependencyBitArray = new int[(maxIdx/32)+1];
                for(int k = 0; k < propertyIndex.length; k++) {
                    if (!(propertyIndex[k] instanceof ChildDependencyIndex)) {
                        BitSet.setBit(dependencyBitArray, propertyIndex[k].position);
                    }
                }
                ((IdentifierImpl)m_identifiers[i]).setDependencyBitArray(dependencyBitArray);

                for(int x = 0; x < m_conditions.length; x++) {
                    if (!(m_conditions[x] instanceof StateGuardCondition)) {
                        setIdrBitArray(m_conditions[x].getIdentifiers(), m_identifiers[i]);
                        if(m_conditions[x] instanceof EquivalentCondition) {
                            setIdrBitArray(((EquivalentCondition)m_conditions[x]).getLeftIdentifiers(), m_identifiers[i]);
                            setIdrBitArray(((EquivalentCondition)m_conditions[x]).getRightIdentifiers(), m_identifiers[i]);
                        }
                    }
                }
            }
        }
    }


    protected void setIdrBitArray(Identifier[] idrs, Identifier master) {
        for(int y = 0; y < idrs.length; y++) {
            if(idrs[y].equals(master)) {
                ((IdentifierImpl)idrs[y]).setDependencyBitArray(master.getDependencyBitArray());
            }
        }
    }


    public int getId() {
        return m_id;
    }

    public String getName() {
        if(m_name == null) {
            return this.getClass().getName();
        }
        else {
            return m_name;
        }
    }

    public String getUri() {
        return this.uri;
    }

    public boolean isActive() {
        return m_active;
    }

    public void activate() {
        m_active = true;
    }

    public void deactivate() {
        m_active = false;
    }

    public void setPriority(int priority) {
        m_priority = priority;
    }

    public int getPriority() {
        return m_priority;
    }

    public void setIdentifiers(Identifier[] identifiers) {
        m_identifiers = identifiers;
    }

    public Identifier[] getIdentifiers() {
        return m_identifiers;
    }

    public void setConditions(Condition[] conditions) {
        m_conditions = conditions;
    }

    public Condition[] getConditions() {
        return m_conditions;
    }

    public void setActions(Action[] actions) {
        m_actions = actions;
    }

    public Action[] getActions() {
        return m_actions;
    }

    public String getLoadTime() {
        return m_loadTime;
    }

    public void setLoadTime(String loadTime) {
        m_loadTime = loadTime;
    }


    public void validate() throws InvalidRuleException
    {
        if ( m_identifiers == null || m_identifiers.length == 0 ) {
            throw new InvalidRuleException(ResourceManager.formatString("rule.invalid.noIdentifier", m_name));
        }
        if ( m_conditions == null) { // can be empty condition
            throw new InvalidRuleException(ResourceManager.formatString("rule.invalid.noCondition", m_name));
        }
        if ( m_actions == null || m_actions.length == 0 ) {
            throw new InvalidRuleException(ResourceManager.formatString("rule.invalid.noAction", m_name));
        }
    }

    public String getDescription() {
        String str = getClass().getName();
        if(str.startsWith("be.gen")) {
            return str.substring("be.gen".length() +1);
        }
        return str;
    }

    public boolean forwardChain() {
        return true;
    }
    
    public RuleFunction getRank() {
        return m_rank;
    }

    public void setRank(RuleFunction rf) {
        m_rank = rf;
    }


    static public class DependencyIndex {
        public int position;

        public DependencyIndex(int p) {
            position = p;
        }

        public DependencyIndex(int p, Class childIdentifier) {
            position = p;
        }

        @Override
        public int hashCode() {
            return position;
        }


        @Override
        public boolean equals(Object obj) {
            return (null != obj)
                    && getClass().equals(obj.getClass())
                    && (position == ((DependencyIndex) obj).position);
        }
    }

    static public class ChildDependencyIndex extends DependencyIndex{
        public ChildDependencyIndex(int p) {
            super(p);
        }

    }

}
