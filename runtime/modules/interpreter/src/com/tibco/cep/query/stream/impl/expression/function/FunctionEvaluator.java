package com.tibco.cep.query.stream.impl.expression.function;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

import java.io.Serializable;
import java.lang.reflect.Method;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 15, 2008
* Time: 3:17:10 PM
*/
public abstract class FunctionEvaluator implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator[] argEvaluators;
    protected transient Class functionClass;
    protected transient Object invocationTarget;
    protected String functionClassName;
    protected String functionName;
    protected Class[] functionArgClasses;
    protected transient Method functionMethod;

    // Required by serializer.
    public FunctionEvaluator(){        
    }

    public FunctionEvaluator(
            Class functionClass,
            String functionName,
            Class[] functionArgClasses,
            ExpressionEvaluator[] argEvaluators) {
        this.functionArgClasses = functionArgClasses;
        this.functionName = functionName;
        this.argEvaluators = argEvaluators;
        this.functionClass = functionClass;
        this.functionClassName = functionClass.getName();
        this.initializeInvocationtarget();
    }

    protected void initializeInvocationtarget() {
        try {
            if(this.invocationTarget == null) {
                this.invocationTarget = getFunctionClass().newInstance();
            }
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        boolean initDone = true;
        if(this.invocationTarget == null) {
            initDone = false;
        }
        if(initDone == false) {
            this.initializeInvocationtarget();
        }
        final Object[] args = new Object[this.argEvaluators.length];
        int i = 0;
        for (ExpressionEvaluator argEvaluator : this.argEvaluators) {
            args[i++] = argEvaluator == null ? null : argEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        }
        try {
            return this.getMethod().invoke(this.invocationTarget, args);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (Boolean) this.evaluate(globalContext, queryContext, aliasAndTuples);
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return ((Number) this.evaluate(globalContext, queryContext, aliasAndTuples)).doubleValue();
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return ((Number) this.evaluate(globalContext, queryContext, aliasAndTuples)).floatValue();
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return ((Number) this.evaluate(globalContext, queryContext, aliasAndTuples)).intValue();
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return ((Number) this.evaluate(globalContext, queryContext, aliasAndTuples)).longValue();
    }

    
    protected Method getMethod() {
        if (null == this.functionMethod) {
            try {
                this.functionMethod = getFunctionClass().getMethod(this.functionName, this.functionArgClasses);
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return this.functionMethod;
    }

    protected Class<?> getFunctionClass() {
        if(this.functionClass == null) {
            try {
                this.functionClass = Class.forName(functionClassName, true, RuleServiceProviderManager.getInstance().getDefaultProvider().getClassLoader());
            } catch(ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return this.functionClass;
    }

}
