package com.tibco.cep.container.standalone.hawk.methods.wm;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetTotalNumberRulesFiredMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;
    protected int m_processId;
    protected String m_hostName;


    public GetTotalNumberRulesFiredMethod(HawkRuleAdministrator hma) {
        super("getTotalNumberRulesFired", "Retrieves the total number of rules fired.", AmiConstants.METHOD_TYPE_INFO, "Line");
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        return args;
    }//getArguments


    public AmiParameterList getReturns() {
        final AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line Number.", 0));
        values.addElement(new AmiParameter("Session", "Name of the Session.", ""));
        values.addElement(new AmiParameter("Number of Rules Fired", "Total number of rules fired since the last reset.", ""+0L));
        return values;
    }//getReturns


    void fillInOneReturnsEntry(AmiParameterList values, int row, long numRulesFired, String wmName) {
        values.addElement(new AmiParameter("Line", "Line Number.", row));
        values.addElement(new AmiParameter("Session", "Name of the Session.", wmName));
        values.addElement(new AmiParameter("Number of Rules Fired", "Total number of rules fired since the last reset.", "" + numRulesFired));
    }//fillInOneReturnsEntry


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            final String name = inParams.getString(0);
            if ((null == name) || "".equals(name.trim())) {
                final RuleSession[] sessions = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSessions();
                for(int i=0; i<sessions.length; i++) {
                    final WorkingMemory wm = ((RuleSessionImpl)sessions[i]).getWorkingMemory();
                    this.fillInOneReturnsEntry(values, i, wm.getTotalNumberRulesFired(), wm.getName());
                }
            } else {
                final WorkingMemory wm = ((RuleSessionImpl)
                        this.m_hma.getServiceProvider().getRuleRuntime().getRuleSession(name)).getWorkingMemory();
                this.fillInOneReturnsEntry(values, 0, wm.getTotalNumberRulesFired(), wm.getName());
            }
            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke

}//class

