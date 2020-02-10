package com.tibco.cep.container.standalone.hawk.methods.wm;


import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.runtime.session.RuleSession;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetSessionsMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;
    protected int m_processId;
    protected String m_hostName;


    public GetSessionsMethod(HawkRuleAdministrator hma) {
        super("getSessions", "Retrieves session names.", AmiConstants.METHOD_TYPE_INFO, "Line");
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        return args;
    }


    public AmiParameterList getReturns() {
        final AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line number.", 0));
        values.addElement(new AmiParameter("Session", "Name of the Session.", ""));
        return values;
    }//getReturns


    void fillInOneReturnsEntry(AmiParameterList values, int row, RuleSession session, String name) {
        values.addElement(new AmiParameter("Line", "Line number.", row));
        values.addElement(new AmiParameter("Session", "Name of the Session.", name));
    }//fillInOneReturnsEntry


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            final RuleSession[] sessions = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSessions();
            for (int i = 0; i < sessions.length; i++) {
                final RuleSession session = sessions[i];
                this.fillInOneReturnsEntry(values, i, session, session.getName());
            }//if
            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke


}//class


