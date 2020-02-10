package com.tibco.cep.container.standalone.hawk.methods.engine;


import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Dec 8, 2006
 * Time: 3:40:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetMemoryUsageMethod extends AmiMethod {


    protected HawkRuleAdministrator hma;


    public GetMemoryUsageMethod(HawkRuleAdministrator hma) {
        super("getMemoryUsage", "Gets engine memory usage information.", AmiConstants.METHOD_TYPE_INFO);
        this.hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        return null;
    }


    public AmiParameterList getReturns() {
        final AmiParameterList returns = new AmiParameterList();
        returns.addElement(new AmiParameter("Max", "Maximum memory size of the JVM, in bytes.", 0L));
        returns.addElement(new AmiParameter("Free", "Estimate of the free memory available to the JVM, in bytes.", 0L));
        returns.addElement(new AmiParameter("Used", "Estimate of the memory used in the JVM, in bytes.", 0L));
        returns.addElement(new AmiParameter("PercentUsed", "Estimate of the percentage of max memory used.", 0L));
        return returns;
    }//getReturns


    void fillInOneReturnsEntry(AmiParameterList values) {
        final long max = Runtime.getRuntime().maxMemory();
        final long free = max - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory();
        final long used = max - free;
        values.addElement(new AmiParameter("Max", "Maximum memory size of the JVM, in bytes.", max));
        values.addElement(new AmiParameter("Free", "Estimate of the free memory available to the JVM, in bytes.", free));
        values.addElement(new AmiParameter("Used", "Estimate of the memory used in the JVM, in bytes.", used));
        values.addElement(new AmiParameter("PercentUsed", "Estimate of the percentage of max memory used.",
                ((long) (100 * used / max))));
    }


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            this.fillInOneReturnsEntry(values);
            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke

}//class

