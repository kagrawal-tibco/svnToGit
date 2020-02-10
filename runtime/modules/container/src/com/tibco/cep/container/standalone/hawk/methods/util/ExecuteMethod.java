package com.tibco.cep.container.standalone.hawk.methods.util;


import java.util.HashMap;
import java.util.Map;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.be.util.NVPair;
import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;


/**
 * Utility method for executing special commands.
 *
 * @version 2.0
 * @since 2.0
 */
public class ExecuteMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;
    protected Map commands;


    public ExecuteMethod(HawkRuleAdministrator hma) {
        super("execute", "Runs a special command.", AmiConstants.METHOD_TYPE_ACTION_INFO, "Line");
        this.m_hma = hma;
        this.commands = new HashMap();
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Command", "The special command to execute", ""));
        args.addElement(new AmiParameter("Parameters", "Parameters (optional)", ""));
        return args;
    }//getArguments


    public AmiParameterList getReturns() {
        final AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line Number.", 0));
        values.addElement(new AmiParameter("Name", "Name.", ""));
        values.addElement(new AmiParameter("Value", "Value.", ""));
        return values;
    }//getReturns


    protected AmiParameterList makeReturnEntries(NVPair[] outputs) throws Exception {
        final AmiParameterList values = new AmiParameterList();
        if (null != outputs) {
            for (int i = 0; i < outputs.length; i++) {
                values.addElement(new AmiParameter("Line", "Line Number", i));
                values.addElement(new AmiParameter("Name", "Name.", outputs[i].name.toString()));
                values.addElement(new AmiParameter("Value", "Value", outputs[i].value.toString()));
            }
        }
        return values;
    }//makeReturnEntries


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final String commandName = inParams.getString(0);
            final Command command = (Command) this.commands.get(commandName);
            if (null == command) {
                throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Command not found: " + commandName);
            }


            final NVPair[] result = command.invoke(this.m_hma.getServiceProvider(), inParams.getString(1));

            return this.makeReturnEntries(result);
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke


    public void register(String commandName, Command command) {
        this.commands.put(commandName, command);
    }


    public void unregister(String commandName) {
        this.commands.remove(commandName);
    }


}//class

