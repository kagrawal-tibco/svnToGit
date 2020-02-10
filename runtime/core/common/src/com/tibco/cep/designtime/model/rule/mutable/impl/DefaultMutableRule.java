/**
 * User: ishaan
 * Date: Apr 22, 2004
 * Time: 12:22:43 PM
 */
package com.tibco.cep.designtime.model.rule.mutable.impl;


import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleSet;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


public class DefaultMutableRule extends AbstractMutableCompilable implements MutableRule {


    public static final ExpandedName PRIORITY_NAME = ExpandedName.makeName("priority");
    public static final ExpandedName TEST_INTERVAL_NAME = ExpandedName.makeName("testInterval");
    public static final ExpandedName START_TIME_NAME = ExpandedName.makeName("startTime");
    public static final ExpandedName DOES_REQUEUE_NAME = ExpandedName.makeName("doesRequeue");
    public static final ExpandedName MAX_RULES_NAME = ExpandedName.makeName("maxRules");
    public static final ExpandedName FORWARD_CHAIN_NAME = ExpandedName.makeName("fwdChain");
    public static final ExpandedName BACKWARD_CHAIN_NAME = ExpandedName.makeName("bwdChain");
    public static final ExpandedName DECLARATIONS_NAME = ExpandedName.makeName("declarations");
    public static final ExpandedName IDENTIFIER_NAME = ExpandedName.makeName("identifier");
    public static final ExpandedName ENTITY_NAME = ExpandedName.makeName("entity");
    public static final ExpandedName CONDITION_NAME = ExpandedName.makeName("condition");
    public static final ExpandedName ACTION_NAME = ExpandedName.makeName("action");
    public static final ExpandedName REQUEUE_VARS_NAME = ExpandedName.makeName("requeueVars");
    public static final ExpandedName REQUEUE_VAR_NAME = ExpandedName.makeName("requeueVar");
    public static final ExpandedName ISFUNCTION_NAME = ExpandedName.makeName("isAFunction");
    public static final ExpandedName ISACONDITIONFUNCTION_NAME = ExpandedName.makeName("isAConditionFunction");
    public static final ExpandedName RULECOMPILATIONSTATUS_NAME = ExpandedName.makeName("compilationStatus");
    public static final ExpandedName TEMPLATE_NAME = ExpandedName.makeName("template");
    public static final ExpandedName AUTHOR_NAME = ExpandedName.makeName("author");

    public static final String INTERNAL_REPRESENTATION_SEPARATOR_STRING = ":";

    protected DefaultMutableRuleSet m_ruleSet;
    protected int m_priority;
    protected long m_testInterval;
    protected long m_startTime;
    protected boolean m_doesRequeue;
    protected int m_maxRules;
    protected boolean m_doesForwardChain;
    protected boolean m_doesBackwardChain;
    protected String m_condition;
    protected String m_action;
    protected Set m_requeueVars;
    protected boolean m_isFunction;
    protected boolean m_isConditionFunction;
    protected XiNode m_template;
    protected String m_author;


    public DefaultMutableRule(DefaultMutableOntology ontology, DefaultMutableRuleSet ruleSet, String name, boolean isFunction) {
        super(ontology, null, name);
        m_ruleSet = ruleSet;
        m_priority = 5;
        m_testInterval = -1;
        m_startTime = -1;
        m_doesRequeue = false;
        m_maxRules = 1;
        m_doesForwardChain = true;
        m_doesBackwardChain = false;
        m_condition = "";
        m_action = "";
        m_requeueVars = new LinkedHashSet();
        m_isFunction = isFunction;
        m_template = null;
        m_author = "";
    }


    public RuleSet getRuleSet() {
        return m_ruleSet;
    }


    public String getFullPath() {
        return m_ruleSet.getFullPath() + Folder.FOLDER_SEPARATOR_CHAR + getName();
    }


    public void delete() {
//        /** Remove the Rule from its participants */
//        final Collection symbols = this.m_decls.values();
//        for (Iterator it = symbols.iterator(); it.hasNext();) {
//            final Symbol symbol = (Symbol) it.next();
//            removeFromParticipant(symbol.getType());
//        }
        if (m_ontology != null) {
            m_ontology.notifyEntityDeleted(this);
        }
        m_ruleSet.removeDefaultRule(getName());
    }


    public void setRuleSet(RuleSet ruleSet) throws ModelException {
        if (ruleSet == null) {
            return;
        }
        if (ruleSet.equals(m_ruleSet)) {
            return;
        }

        String oldRuleSetPath = (m_ruleSet != null) ? m_ruleSet.getFullPath() : "";

        /** Add the rule to the new RuleSet */
        DefaultMutableRuleSet drs = (DefaultMutableRuleSet) ruleSet;
        drs.addDefaultRule(this);

        /** Remove it from the old RuleSet */
        if (m_ruleSet != null) {
            m_ruleSet.removeDefaultRule(getName());
        }

        /** Set our internal pointer to the new RuleSet */
        setDefaultRuleSet(drs);

        String newRuleSetPath = m_ruleSet.getFullPath();
        pathChanged(oldRuleSetPath, m_name, newRuleSetPath, m_name);

        notifyListeners();
        notifyOntologyOnChange();
    }


    public static final String NAME_CONFLICT_KEY = "DefaultRule.setName.nameConflict";


    public void setName(String name, boolean renameOnConflict) throws ModelException {
        BEModelBundle bundle = BEModelBundle.getBundle();

        if (name == null || name.length() == 0) {
            String msg = bundle.getString(AbstractMutableEntity.EMPTY_NAME_KEY);
            throw new ModelException(msg);
        }
        if (name.equals(m_name)) {
            return;
        }

        String oldName = m_name;

        if (m_ruleSet != null) {
            if (m_ruleSet.getRule(name) != null) {
                String msg = bundle.formatString(NAME_CONFLICT_KEY, name, m_ruleSet.getFullPath());
                throw new ModelException(msg);
            }

            m_ruleSet.removeDefaultRule(m_name);
        }

        m_name = name;

        if (m_ruleSet != null) {
            String ruleSetPath = m_ruleSet.getFullPath();

            m_ruleSet.addDefaultRule(this);
            pathChanged(ruleSetPath, oldName, ruleSetPath, m_name);
        }

        if (m_ontology != null) {
            m_ontology.notifyEntityRenamed(this, oldName);
        }
        notifyListeners();
        notifyOntologyOnChange();
    }


    /**
     * Notifies participants that the name has changed.
     */
    public void pathChanged(String oldRuleSetPath, String oldName, String newRuleSetPath, String newName) {
        if (m_ontology == null) {
            return;
        }
        if (oldName == null) {
            return;
        }
        if (newName == null) {
            newName = "";
        }

//        for (Iterator it = this.m_decls.values().iterator(); it.hasNext();) {
//            final Symbol symbol = (Symbol) it.next();
//            final MutableRuleParticipant rp = (MutableRuleParticipant) m_ontology.getEntity(symbol.getType());
//            if (null != rp) {
//                rp.removeRule(oldRuleSetPath, oldName);
//                rp.addRule(newRuleSetPath, newName);
//            }//if
//        }//for
    }


    public void setFolderPath(String fullPath) throws ModelException {
    }


    public void setFolder(MutableFolder folder) throws ModelException {
    }


    public int getPriority() {
        return m_priority;
    }


    public void setPriority(int priority) {
        if (priority == m_priority) {
            return;
        }

        if (priority < MIN_PRIORITY) {
            m_priority = MIN_PRIORITY;
        } else if (priority > MAX_PRIORITY) {
            m_priority = MAX_PRIORITY;
        } else {
            m_priority = priority;
        }

        notifyListeners();
        notifyOntologyOnChange();
    }


    public XiNode getTemplate() {
        return m_template;
    }


    public long getTestInterval() {
        return m_testInterval;
    }


    public void setTestInterval(long intervalInMilliseconds) {
        if (m_testInterval == intervalInMilliseconds) {
            return;
        }
        m_testInterval = intervalInMilliseconds;
        notifyListeners();
        notifyOntologyOnChange();
    }


    public long getStartTime() {
        return m_startTime;
    }


    public void setStartTime(long startTimeInMilliseconds) {
        if (m_startTime == startTimeInMilliseconds) {
            return;
        }

        m_startTime = startTimeInMilliseconds;
        notifyListeners();
        notifyOntologyOnChange();
    }


//    /**
//     * Removes the DefaultRule from the AbstractRuleParticipant at the provided path.
//     *
//     * @param participantPath
//     */
//    public void removeFromParticipant(String participantPath) {
//        if (m_ontology == null) {
//            return;
//        }
//
//        MutableRuleParticipant rp = (MutableRuleParticipant) m_ontology.getEntity(participantPath);
//        if (rp == null) {
//            return;
//        }
//
//        String ruleSetPath = m_ruleSet.getFullPath();
//        rp.removeRule(ruleSetPath, m_name);
//    }
//
//
//    /**
//     * Adds the DefaultRule to the AbstractRuleParticipant at the provided path.
//     *
//     * @param participantPath
//     */
//    public void addToParticipant(String participantPath) {
//        if ((m_ontology == null) || (m_ruleSet == null)) {
//            return;
//        }
//
//        MutableRuleParticipant rp = (MutableRuleParticipant) m_ontology.getEntity(participantPath);
//        if (rp == null) {
//            return;
//        }
//
//        String ruleSetPath = m_ruleSet.getFullPath();
//        rp.addRule(ruleSetPath, m_name);
//    }


    public String getEntityPathForIdentifier(String identifier) {
        final Symbol symbol = this.m_decls.getSymbol(identifier);
        if (null == symbol) {
            return null;
        }
        return symbol.getType();
    }


    public void addDeclaration(String identifier, String entityPath) {
        this.m_decls.put(identifier, entityPath);
    }


    public boolean deleteDeclaration(String identifier) {
        return (null != this.m_decls.remove(identifier));
    }


    public Symbols getDeclarations() {
        return this.getScope();
    }


    public void setDeclarations(Symbols declarations) {
        this.setScope(declarations);
    }


    public String getConditionText() {
        return m_condition;
    }


    public void setConditionText(String text) {
        if (text == null) {
            text = "";
        }
        m_condition = text;
        notifyListeners();
        notifyOntologyOnChange();
    }


    public String getActionText() {
        return m_action;
    }


    public void setActionText(String text) {
        if (text == null) {
            text = "";
        }
        m_action = text;
        notifyListeners();
        notifyOntologyOnChange();
    }


    public void setTemplate(XiNode template) {
        m_template = template;
        notifyListeners();
        notifyOntologyOnChange();
    }


    public String getAuthor() {
        return m_author;
    }


    public void setAuthor(String author) {
        if (author == null) {
            author = "";
        }
        m_author = author;
    }


    public boolean doesRequeue() {
        return m_doesRequeue;
    }


    public void setRequeue(boolean doesRequeue) {
        m_doesRequeue = doesRequeue;
        notifyListeners();
        notifyOntologyOnChange();
    }


    public int getRequeueCount() {
        return m_maxRules;
    }


    public void setRequeueCount(int maxRules) {
        m_maxRules = maxRules;
        notifyListeners();
        notifyOntologyOnChange();
    }


    public boolean usesForwardChaining() {
        return m_doesForwardChain;
    }


    public boolean usesBackwardChaining() {
        return m_doesBackwardChain;
    }


    public void setChainingPolicy(boolean forwardChain, boolean backwardChain) {
        if (forwardChain == m_doesForwardChain && backwardChain == m_doesBackwardChain) {
            return;
        }
        m_doesForwardChain = forwardChain;
        m_doesBackwardChain = backwardChain;
        notifyListeners();
        notifyOntologyOnChange();
    }


    public void setRequeueIdentifiers(Set identifierNames) {
        if (identifierNames == null) {
            identifierNames = new LinkedHashSet();
        }
        m_requeueVars = identifierNames;
    }


    public Set getRequeueIdentifiers() {
        return m_requeueVars;
    }


    /**
     * Start methods used by default implementation **
     */
    public void setDefaultRuleSet(DefaultMutableRuleSet ruleSet) {
        m_ruleSet = ruleSet;
        if (m_ruleSet != null) {
            this.setOntology((MutableOntology) m_ruleSet.getOntology());
        } else {
            this.setOntology(null);
        }
    }


    public XiNode toXiNode(XiFactory factory) {
        XiNode root = super.toXiNode(factory, "rule");
        root.removeAttribute(GUID_NAME);
        
        /** Added two attributes for storing if the rule is a function and if yes, what type */
        root.setAttributeStringValue(ISFUNCTION_NAME, String.valueOf(m_isFunction));
        root.setAttributeStringValue(ISACONDITIONFUNCTION_NAME, String.valueOf(m_isConditionFunction));
        root.setAttributeStringValue(RULECOMPILATIONSTATUS_NAME, String.valueOf(getCompilationStatus()));

        XiNode priority = root.appendElement(PRIORITY_NAME);
        priority.setStringValue(String.valueOf(m_priority));

        XiNode testInterval = root.appendElement(TEST_INTERVAL_NAME);
        testInterval.setStringValue(String.valueOf(m_testInterval));

        XiNode startTime = root.appendElement(START_TIME_NAME);
        startTime.setStringValue(String.valueOf(m_startTime));

        XiNode doesRequeue = root.appendElement(DOES_REQUEUE_NAME);
        doesRequeue.setStringValue(String.valueOf(m_doesRequeue));

        XiNode maxRules = root.appendElement(MAX_RULES_NAME);
        maxRules.setStringValue(String.valueOf(m_maxRules));

        XiNode fwdChain = root.appendElement(FORWARD_CHAIN_NAME);
        fwdChain.setStringValue(String.valueOf(m_doesForwardChain));

        XiNode bwdChain = root.appendElement(BACKWARD_CHAIN_NAME);
        bwdChain.setStringValue(String.valueOf(m_doesBackwardChain));

        root.appendChild(this.scopeToXiNode(factory));

        XiNode condition = root.appendElement(CONDITION_NAME);
        condition.setStringValue(m_condition);

        XiNode action = root.appendElement(ACTION_NAME);
        action.setStringValue(m_action);

        XiNode rqVarsNode = root.appendElement(REQUEUE_VARS_NAME);
        Iterator rqVarIt = m_requeueVars.iterator();
        while (rqVarIt.hasNext()) {
            String rqVar = (String) rqVarIt.next();
            XiNode rqVarNode = rqVarsNode.appendElement(REQUEUE_VAR_NAME);
            rqVarNode.setStringValue(rqVar);
        }

        XiNode templateNode = root.appendElement(TEMPLATE_NAME);
        if (m_template != null) {
            templateNode.appendChild(m_template.copy());
        }

        XiNode authorNode = root.appendElement(AUTHOR_NAME);
        authorNode.setStringValue(m_author);

        return root;
    }


    public static DefaultMutableRule createDefaultRuleFromNode(XiNode root, DefaultMutableRuleSet ruleSet) throws ModelException {
        DefaultMutableRule dr = null;

        String name = root.getAttributeStringValue(NAME_NAME);
        String description = root.getAttributeStringValue(DESCRIPTION_NAME);
        String isFunction = root.getAttributeStringValue(ISFUNCTION_NAME);

        boolean isAFunction = false;
        boolean isAConditionFunction = false;

        if (isFunction != null) {
            isAFunction = Boolean.valueOf(isFunction).booleanValue();
        } else {
            isAFunction = false;
        }
        if (isAFunction) {
            String tmp = root.getAttributeStringValue(ISACONDITIONFUNCTION_NAME);
            System.out.println("restore: tmp==" + tmp);
            if (tmp != null) {
                isAConditionFunction = Boolean.valueOf(tmp).booleanValue();
                System.out.println("isAConditionFunction==" + isAConditionFunction);
            } else {
                isAConditionFunction = true;// By default, all functions are condition functions
            }
        }

        DefaultMutableOntology o = (DefaultMutableOntology) ((ruleSet != null) ? ruleSet.getOntology() : null);

        dr = new DefaultMutableRule(o, ruleSet, name, isAFunction);
        dr.setDescription(description);
        dr.setFunctionTypeAsCondition(isAConditionFunction);

        XiNode priorityNode = XiChild.getChild(root, PRIORITY_NAME);
        String priorityStr = priorityNode.getStringValue();
        int priority = Integer.parseInt(priorityStr);
        dr.setPriority(priority);

        XiNode testIntervalNode = XiChild.getChild(root, TEST_INTERVAL_NAME);
        String testIntervalStr = testIntervalNode.getStringValue();
        long testInterval = Long.parseLong(testIntervalStr);
        dr.setTestInterval(testInterval);

        XiNode startTimeNode = XiChild.getChild(root, START_TIME_NAME);
        String startTimeStr = startTimeNode.getStringValue();
        long startTime = Long.parseLong(startTimeStr);
        dr.setStartTime(startTime);

        XiNode doesRequeueNode = XiChild.getChild(root, DOES_REQUEUE_NAME);
        String doesRequeueStr = doesRequeueNode.getStringValue();
        boolean doesRequeue = Boolean.valueOf(doesRequeueStr).booleanValue();
        dr.setRequeue(doesRequeue);

        XiNode maxRulesNode = XiChild.getChild(root, MAX_RULES_NAME);
        String maxRulesStr = maxRulesNode.getStringValue();
        int maxRules = Integer.parseInt(maxRulesStr);
        dr.setRequeueCount(maxRules);

        XiNode fwdChainNode = XiChild.getChild(root, FORWARD_CHAIN_NAME);
        String fwdChainStr = fwdChainNode.getStringValue();
        boolean fwdChain = Boolean.valueOf(fwdChainStr).booleanValue();

        XiNode bwdChainNode = XiChild.getChild(root, BACKWARD_CHAIN_NAME);
        String bwdChainStr = bwdChainNode.getStringValue();
        boolean bwdChain = Boolean.valueOf(bwdChainStr).booleanValue();
        dr.setChainingPolicy(fwdChain, bwdChain);

        dr.loadScope(root, true);

        XiNode conditionNode = XiChild.getChild(root, CONDITION_NAME);
        String conditionNodeStr = conditionNode.getStringValue();
        dr.setConditionText(conditionNodeStr);

        XiNode actionNode = XiChild.getChild(root, ACTION_NAME);
        String actionNodeStr = actionNode.getStringValue();
        dr.setActionText(actionNodeStr);

        Set identifiers = new LinkedHashSet();
        XiNode rqVarsNode = XiChild.getChild(root, REQUEUE_VARS_NAME);
        if (rqVarsNode != null) {
            Iterator rqVarIt = rqVarsNode.getChildren();
            while (rqVarIt.hasNext()) {
                XiNode rqVarNode = (XiNode) rqVarIt.next();
                String rqVar = rqVarNode.getStringValue();
                identifiers.add(rqVar);
            }

            dr.setRequeueIdentifiers(identifiers);
        }

        if (ruleSet != null) {
            ruleSet.removeDefaultRule(dr.getName());
            ruleSet.addDefaultRule(dr);
        }
        XiNode compStatus = root.getAttribute(RULECOMPILATIONSTATUS_NAME);
        if (compStatus != null) {
            dr.setCompilationStatus(Integer.parseInt(compStatus.getStringValue()));
        }

        XiNode templateNode = XiChild.getChild(root, TEMPLATE_NAME);
        if (templateNode != null) {
            XiNode template = templateNode.getFirstChild();
            if (template != null) {
                dr.setTemplate(template.copy());
            }
        }

        XiNode authorNode = XiChild.getChild(root, AUTHOR_NAME);
        if (authorNode != null) {
            String author = authorNode.getStringValue();
            if (author != null) {
                dr.setAuthor(author);
            }
        }

        return dr;
    }


//    public void setOntology(MutableOntology ontology) {
//        super.setOntology(ontology);
//        if (getCompilationStatus() == -1) {//PENDING COMPILATION
//            try {
////                ArrayList errList = new ArrayList(); //TODO - could be a goodplace to hold all the errors.
////                m_compilationStatus = RuleChecker.compileRule(this, errList);
//            }
//            catch (Exception e) {
//                setCompilationStatus(1);  //SOME ERRORS
//            }
//        }
//    }


    protected String toInternalRepresentation() {
        return m_ruleSet.getFullPath() + INTERNAL_REPRESENTATION_SEPARATOR_STRING + m_name;
    }


    protected static DefaultMutableRule parseInternalRepresentation(Ontology ontology, String rep) {
//        if (rep == null || rep.equals("")) {
//            return null;
//        }
//        DefaultMutableRule dr = null;
//
//        String[] parts = rep.split(INTERNAL_REPRESENTATION_SEPARATOR_STRING);
//        if (parts == null || parts.length != 2) {
//            return null;
//        }
//
//        RuleSet ruleSet = ontology.getRuleSet(parts[0]);
//        if (ruleSet == null) {
//            return null;
//        }
//
//        dr = (DefaultMutableRule) ruleSet.getRule(parts[1]);
//        return dr;
    	return null;
    }


    /**
     * @return boolean is a rulefunction
     */
    public boolean isFunction() {
        return m_isFunction;
    }


    /**
     * @return boolean, true if this is a function that can be used in the Condition
     */
    public boolean isConditionFunction() {
        return m_isConditionFunction;
    }


    /**
     * @param isFunctionACondition
     */
    public void setFunctionTypeAsCondition(boolean isFunctionACondition) {
        this.m_isConditionFunction = isFunctionACondition;
        this.notifyListeners();
        this.notifyOntologyOnChange();
    }


    public boolean isEmptyRule() {
        boolean isEmpty = true;
        if (((null != this.m_action) && (this.m_action.trim().length() > 0))
                || ((null != this.m_condition) && (this.m_condition.trim().length() > 0))) {
            return false;
        }

        return isEmpty;

    }


    public String toString() {
        return getFullPath();
    }


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Rule#getDeclLineOffset()
	 */
	@Override
	public CodeBlock getDeclCodeBlock() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Rule#getSource()
	 */
	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Rule#getThenOffset()
	 */
	@Override
	public CodeBlock getThenCodeBlock() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Rule#getWhenLineOffset()
	 */
	@Override
	public CodeBlock getWhenCodeBlock() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public RuleFunction getRank() {
		return null;
	}


	@Override
	public String getRankPath() {
		return "";
	}

    /*** End methods used by default implementation ***/
    
    
}
