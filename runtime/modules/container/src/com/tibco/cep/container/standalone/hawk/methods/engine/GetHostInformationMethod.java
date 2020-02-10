package com.tibco.cep.container.standalone.hawk.methods.engine;


import java.util.Enumeration;
import java.util.Properties;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.cep_commonVersion;
import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.runtime.session.RuleServiceProvider;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 14, 2004
 * Time: 3:34:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetHostInformationMethod extends AmiMethod {


    public static final String PROPERTY_NAME_APPLICATION_NAME = "Application Name";
    public static final String PROPERTY_NAME_APPLICATION_INSTANCE = "Application Instance";
    public static final String PROPERTY_NAME_APPLICATION_STATE = "Application State";

    protected HawkRuleAdministrator m_hma;
    protected Properties m_hostInfoProps;


    public GetHostInformationMethod(HawkRuleAdministrator hma) {
        super("getHostInformation", "Gets host information properties.", AmiConstants.METHOD_TYPE_INFO);
        this.m_hma = hma;
        this.m_hostInfoProps = new Properties();
        this.m_hostInfoProps.put(PROPERTY_NAME_APPLICATION_NAME, cep_commonVersion.getComponent());
        final RuleServiceProvider rsp = this.m_hma.getServiceProvider();
        if (null != rsp) {
            this.m_hostInfoProps.put(PROPERTY_NAME_APPLICATION_INSTANCE, m_hma.getServiceProvider().getName());
        }
        this.m_hostInfoProps.put(PROPERTY_NAME_APPLICATION_STATE, m_hma.getApplicationState());
    }//constr


    public AmiParameterList getArguments() {
        AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Name", "Name of host information property to get (optional).", ""));
        return args;
    }


    public AmiParameterList getReturns() {
        AmiParameterList returns = new AmiParameterList();
        fillInOneReturnsEntry(returns, "", "");
        return returns;
    }


    public String[] getIndexName() {
        return new String[]{"Name"};
    }


    /**
     * Fills in either the sample values (if wf is null)
     * or the actual values (from wf, if wf != null)
     * by adding AmiParameters to the given list.
     */
    protected void fillInOneReturnsEntry(AmiParameterList returns, String name, String value) {
        returns.addElement(new AmiParameter("Name", "Property Name", name));
        returns.addElement(new AmiParameter("Value", "Property Value", value));
    }


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        AmiParameterList values = null;
        try {
            m_hostInfoProps.put(PROPERTY_NAME_APPLICATION_STATE, m_hma.getApplicationState());
            values = new AmiParameterList();
            final AmiParameter param = (AmiParameter) inParams.elementAt(0);

            final String name = (String) param.getValue();
            // eventually, when SDK 4.1 is available, might be able
            // to call the SDK to get (some of) this information.
            if (name == null || name.length() == 0) {
                Enumeration keys = m_hostInfoProps.keys();
                while (keys.hasMoreElements()) {
                    String key = (String) keys.nextElement();
                    String value = (String) m_hostInfoProps.get(key);
//                    if (debug()) debug("calling fillInOneReturnsEntry with key=" + key + " and value=" + value);
                    fillInOneReturnsEntry(values, key, value);
                }//while
            } else if(m_hostInfoProps.get(name) != null) {
                    fillInOneReturnsEntry(values, name, (String) m_hostInfoProps.get(name));
            }//else
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch

        return values;
    }//onInvoke

}//class

