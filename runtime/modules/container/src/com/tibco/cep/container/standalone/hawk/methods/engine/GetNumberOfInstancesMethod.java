package com.tibco.cep.container.standalone.hawk.methods.engine;

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
 * User: nleong
 * Date: Apr 18, 2005
 * Time: 9:48:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetNumberOfInstancesMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public GetNumberOfInstancesMethod(HawkRuleAdministrator hma) {
        super("getNumberOfInstances", "Get the total number of instances existing in a Session.", AmiConstants.METHOD_TYPE_INFO, "Line");
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        return args;
    }//getArguments


    public AmiParameterList getReturns() {
        final AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line number.", 0));
        values.addElement(new AmiParameter("Session", "Name of the Session.", ""));
        values.addElement(new AmiParameter("Number", "Total Number of Instances", 0));
        return values;
    }//getReturns


    void fillInOneReturnsEntry(AmiParameterList values, int row, String name, int number) {
        values.addElement(new AmiParameter("Line", "Line number.", row));
        values.addElement(new AmiParameter("Session", "Name of the Session.", name));
        values.addElement(new AmiParameter("Number", "Total Number of Instances", number));
    }//fillInOneReturnsEntry


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            final String name = inParams.getString(0);
            if ((name == null) || (name.trim().length() == 0)) {
                final RuleSession[] sessions = m_hma.getServiceProvider().getRuleRuntime().getRuleSessions();
                for(int i = 0; i < sessions.length; i++) {
                    this.fillInOneReturnsEntry(values, i, sessions[i].getName(),
                            sessions[i].getObjectManager().numOfElement());
                }
            } else {
                final RuleSession session = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSession(name);
                if (null == session) {
                    throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Cannot find session named: "+name);
                }
                this.fillInOneReturnsEntry(values, 0, session.getName(), session.getObjectManager().numOfElement());
            }
            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke

}//class

