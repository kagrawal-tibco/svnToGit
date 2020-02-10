package com.tibco.cep.designtime.model.rule;


import com.tibco.cep.designtime.model.Entity;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 26, 2006
 * Time: 12:37:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Compilable extends Entity, ScopeContainer {


    /**
     * Returns the type of the RuleFunction.
     *
     * @return Returns the name of an RDFUberType corresponding to the return type of the RuleFunction, or an Entity path.
     *         A return value of null implies that the function does not return a value.
     */
    String getReturnType();


    String getConditionText();


    String getActionText();


    int getCompilationStatus();
    
    /**
     * @since 4.0
     * @author pdhar
     *
     */
    public interface CodeBlock {
    	/**
    	 * returns the starting line offset of the first statement in the block
    	 * i.e ConceptA@id = ConceptB@id;
    	 * @since 4.0
    	 * @return
    	 */
    	int getStart();
    	/**
    	 * returns the ending line offset of the last statement in the block
    	 * i.e ConceptA@id = ConceptB@id;
    	 * @since 4.0
    	 * @return
    	 */
    	int getEnd();

    }
}
