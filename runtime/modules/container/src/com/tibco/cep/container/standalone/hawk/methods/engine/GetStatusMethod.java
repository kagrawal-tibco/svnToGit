package com.tibco.cep.container.standalone.hawk.methods.engine;

import java.net.InetAddress;
import java.net.UnknownHostException;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.cep_commonVersion;
import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetStatusMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;
    protected int m_processId;
    protected String m_hostName;


    public GetStatusMethod(HawkRuleAdministrator hma) {
        super("getStatus", "Retrieves basic status information about the engine.", AmiConstants.METHOD_TYPE_INFO);

        m_hma = hma;

        try {
            m_hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            m_hostName = "";
        }//catch

        getProcessID();
    }//constr


    public AmiParameterList getArguments() {
        return null;
    }


    public AmiParameterList getReturns() {
        final AmiParameterList returns = new AmiParameterList();
        returns.addElement(new AmiParameter("Instance ID", " Instance ID of the application.", ""));
        returns.addElement(new AmiParameter("Application Name", "Name of the application.", ""));
        returns.addElement(new AmiParameter("Uptime", "Time elapsed since startup.", 0));
        returns.addElement(new AmiParameter("Process ID", "Process ID of the application.", 0));
        returns.addElement(new AmiParameter("Host", "Name of host machine on which this application is running.", ""));
        return returns;
    }//getReturns


    void fillInOneReturnsEntry(AmiParameterList values) {
        values.addElement(new AmiParameter("Instance ID", " Instance ID of the application.", m_hma.getServiceProvider().getName()));
        values.addElement(new AmiParameter("Application Name", "Name of the application.", cep_commonVersion.getComponent()));
        values.addElement(new AmiParameter("Uptime", "Time elapsed since startup.", getUptime()));
//        values.addElement(new AmiParameter("New Errors", "Number of errors since the last call to this method.", getNbNewErrors()));
//        values.addElement(new AmiParameter("Total Errors", "Total number of errors since startup.", getTotalNbErrors()));
        values.addElement(new AmiParameter("Process ID", "Process ID of the application.", getProcessID()));
        values.addElement(new AmiParameter("Host", "Name of host machine on which this application is running.", m_hostName));
    }


    //todo: Implement getNbNewErrors
    public int getNbNewErrors() {
        return 0;
    }


    //todo: Implement getTotalNbErrors
    public int getTotalNbErrors() {
        return 0;
    }


    public int getProcessID() {
        if (0 == m_processId) {
            final String processIdStr = System.getProperty("application.processid");
            if ((null != processIdStr) && !"".equals(processIdStr)) {
                m_processId = new Integer(processIdStr).intValue();
            }//if
        }//if

        return m_processId;
    }//getProcessID


    /**
     * Returns the time elapsed since startup.
     *
     * @return an int (instead of long) because its an int in the SDK getStatus method.
     */
    public int getUptime() {
        return (int) (System.currentTimeMillis() - m_hma.getStartTime());
    }//getUptime


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        AmiParameterList values = null;
        try {
            values = new AmiParameterList();
            fillInOneReturnsEntry(values);
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
        return values;
    }//onInvoke

}//class

