package com.tibco.cep.container.standalone.hawk.methods.channels;


import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetSessionInputDestinations extends AmiMethod {


    protected HawkRuleAdministrator m_hma;
    protected int m_processId;
    protected String m_hostName;


    public GetSessionInputDestinations(HawkRuleAdministrator hma) {
        super("getSessionInputDestinations", "Retrieves destinations enabled for input.", AmiConstants.METHOD_TYPE_INFO, "Line");
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session.", ""));
        return args;
    }


    public AmiParameterList getReturns() {
        final AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line number.", 0));
        values.addElement(new AmiParameter("Destination", "Destination URI.", ""));
        values.addElement(new AmiParameter("Preprocessor", "Destination preprocessor URI.", ""));
        return values;
    }//getReturns


    void fillInOneReturnsEntry(AmiParameterList values, int row, RuleSessionConfig.InputDestinationConfig dest) {
        values.addElement(new AmiParameter("Line", "Line number.", row));
        values.addElement(new AmiParameter("Destination", "Destination URI.", dest.getURI()));
        final RuleFunction preprocessor = dest.getPreprocessor();
        String preprocessorUri = "";
        if (null != preprocessor) {
            preprocessorUri = preprocessor.getSignature();
        }
        values.addElement(new AmiParameter("Preprocessor", "Destination preprocessor URI.", preprocessorUri));
    }//fillInOneReturnsEntry


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            final String sessionName = inParams.getString(0);
            if ((null == sessionName) || (sessionName.trim().length() == 0)) {
                throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Invalid Session name: " + sessionName);
            } else {
                final RuleSession session = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSession(sessionName);
                if (null == session) {
                    throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Invalid session name: " + sessionName);
                }
                final RuleSessionConfig.InputDestinationConfig[] destinations = session.getConfig().getInputDestinations();
                for (int i=0; i<destinations.length; i++) {
                    this.fillInOneReturnsEntry(values, i, destinations[i]);
                }
            }
            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke

    
}//class



