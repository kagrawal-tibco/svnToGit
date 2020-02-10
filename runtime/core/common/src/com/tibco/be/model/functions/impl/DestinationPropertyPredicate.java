package com.tibco.be.model.functions.impl;


import java.util.Arrays;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.functions.Predicate;
import com.tibco.cep.designtime.model.service.channel.Destination;
import com.tibco.xml.data.primitive.ExpandedName;
import static com.tibco.be.model.functions.FunctionDomain.*;


/**
 * @author ishaan
 * @version Jan 7, 2005, 7:15:21 PM
 */
public class DestinationPropertyPredicate implements Predicate {
    protected ExpandedName m_name;
    protected Destination m_destination;
    protected FunctionDomain[] fndomains = new FunctionDomain[]{ACTION,CONDITION};

    public DestinationPropertyPredicate(
            Destination destination,
            ExpandedName propName) {
        m_destination = destination;
        m_name = propName;
    }

    public Destination getDestination() {
        return m_destination;
    }


    public String signature() {
        return code();
    }
    
    @Override
    public String signatureFormat() {
    	return code();
    }

    public ExpandedName getName() {
        return m_name;
    }

    public Class getReturnClass() {
        return java.lang.String.class;
    }

    public Class[] getThrownExceptions() {
        return new Class[0];
    }

    public Class[] getArguments() {
        return new Class[0];
    }

    public String code() {
        return toString();
    }

    public String inline() {
        return code();
    }

    public boolean isValidInCondition() {
    	return Arrays.asList(fndomains).contains(CONDITION);
//        return true;
    }

    public boolean isValidInAction() {
    	return Arrays.asList(fndomains).contains(ACTION);
//        return true;
    }

    
    public boolean isValidInBUI() {
    	return Arrays.asList(fndomains).contains(ACTION);
//        return true;
    }


    public boolean isValidInQuery() {
    	return Arrays.asList(fndomains).contains(QUERY);
//        return false;
    }

    public boolean isTimeSensitive() {
        return false;
    }

    public boolean requiresAsync() {
        return false;
    }

    public boolean reevaluate() {
        return false;
    }

    public String getDocumentation() {
        return code();
    }

    public String template() {
        return code();
    }

    public boolean doesModify() {
        return false;
    }

    public String toString() {
        return '"' + this.m_name.localName + '"';
    }

    public boolean requiresAssert() { return false; }

    public boolean isVarargs() { return false; }
    public boolean isVarargsCodegen() { return isVarargs(); }
    public Class getVarargsCodegenArgType() {
    	Class[] args = getArguments();
    	if(args != null && args.length > 0) return args[args.length - 1];
    	else return null;
    }

	@Override
	public FunctionDomain[] getFunctionDomains() {
		return fndomains;
	}
    
    
}
