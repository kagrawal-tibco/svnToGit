package com.tibco.be.model.functions;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 2, 2004
 * Time: 4:35:27 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Predicate {

    /**
     *
     * @return
     */
    ExpandedName getName();

    /**
     *
     * @return
     */
    Class getReturnClass();


    /**
     *
     * @return
     */
    Class [] getThrownExceptions();


    /**
     *
     * @return
     */
    Class [] getArguments();


    /**
     *
     * @return
     */
    String code();

    /**
     *
     * @return
     */
    String inline();

    /**
     *
     * @return
     */
    boolean isValidInCondition();


    /**
     *
     * @return
     */
    boolean isValidInAction();

    /**
     *
     * @return
     */
    boolean isValidInQuery();
    
    /**
     * @return
     */
    FunctionDomain[] getFunctionDomains();

    /**
     *
     * @return
     */
    boolean isValidInBUI();

    /**
     *
     * @return
     */
    boolean isTimeSensitive();


    /**
     *
     * @return
     */
    boolean requiresAsync();
    /**
     *
     * @return
     */
    String getDocumentation();

    /**
     *
     * @return
     * @throws Exception
     */
    String template() ;

    /**
     *
     * @return
     */
    boolean doesModify();

    /**
     * 
     * @return
     */
    String signature();
    
    /**
     * @return
     */
    String signatureFormat();

    /**
     * @return
     */
    boolean reevaluate();
    
    /**
     * @return
     */
    boolean requiresAssert();

    boolean isVarargs();
    
    //lie to studio that the function is not varargs but tell codegen that it is 
    boolean isVarargsCodegen();
    //getArgs returns studio's view of the args, this is the actual type of the varargs argument declared by codegen
    Class getVarargsCodegenArgType();

}
