package com.tibco.cep.designtime.model.rule;


import java.util.Set;

import com.tibco.be.util.shared.RuleConstants;
import com.tibco.xml.datamodel.XiNode;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 10:50:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Rule extends Compilable, RulesetEntry {


    /**
     * Constants used for the set*() methods.
     */
    int MIN_PRIORITY = RuleConstants.MIN_PRIORITY;
    int MAX_PRIORITY = RuleConstants.MAX_PRIORITY;
    long NO_START_TIME = -1;
    long NO_TEST_INTERVAL = -1;
    int MIN_MAX_RULES = 1;


    /**
     * Returns the priority of this Rule.
     *
     * @return An int p, such that MIN_PRIORITY <= p <= MAX_PRIORITY.
     */
    int getPriority();


    long getTestInterval();


    long getStartTime();


    Symbols getDeclarations();


    String getEntityPathForIdentifier(String identifier);


    boolean doesRequeue();


    int getRequeueCount();


    boolean usesForwardChaining();


    boolean usesBackwardChaining();


    RuleSet getRuleSet();


    Set getRequeueIdentifiers();


    /**
     * @return
     */
    boolean isFunction();


    /**
     * @return
     */
    boolean isConditionFunction();


    int getCompilationStatus();


    boolean isEmptyRule();


    XiNode getTemplate();


    String getAuthor();
    
    /**
     * Returns the line offset of the declare block
     * @since 4.0
     * @return
     */
	public CodeBlock getDeclCodeBlock();
	
	/**
	 * Returns the line offset of the when block
	 * @since 4.0
	 * @return
	 */
	public CodeBlock getWhenCodeBlock();
	
	/**
	 * Returns the line offset of the then block
	 * @since 4.0
	 * @return
	 */
	public CodeBlock getThenCodeBlock();
	
	/**
	 * Returns the source representation of the Rule as shown in editor
	 * @return
	 */
	public String getSource();
	
	/**
	 * Returns the rank of this rule
	 * @since 4.0
	 * @return
	 */
	public RuleFunction getRank();
	
	/**
	 * Returns the path to the rank of this rule
	 * @since 4.0
	 * @return
	 */
	public String getRankPath();
	
}
