package com.tibco.cep.container.standalone.hawk.methods.util;


import com.tibco.be.util.NVPair;
import com.tibco.cep.runtime.session.RuleServiceProvider;


/**
 * Command that can be invoked with {@link ExecuteMethod}.
 */
public interface Command {


    /**
     * Invokes the command with the given <code>String</code> arguments.
     *
     * @param rsp         the <code>RuleServiceProvider</code> in which the command is invoked.
     * @param inputParams a <code>String</code> containing optional parameters.
     * @return array of NVPair</code>.
     * @throws Exception
     */
    public NVPair[] invoke(RuleServiceProvider rsp, String inputParams) throws Exception;

}
