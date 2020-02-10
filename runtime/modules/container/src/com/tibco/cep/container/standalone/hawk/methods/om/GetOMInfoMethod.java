package com.tibco.cep.container.standalone.hawk.methods.om;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.runtime.session.RuleSession;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetOMInfoMethod extends AmiMethod {


    protected static final String OMCLASS_NAME = "omClass";
    protected static final Map OMCLASS_VALUE_TO_LABEL = new HashMap();


    static {
        OMCLASS_VALUE_TO_LABEL.put("bdb", "Persistence");
        OMCLASS_VALUE_TO_LABEL.put("tangosol", "Cache");
        OMCLASS_VALUE_TO_LABEL.put("com.tibco.cep.runtime.service.cluster.om.DefaultDistributedCacheBasedStore", "Cache");
        OMCLASS_VALUE_TO_LABEL.put("false", "In memory");
    }


    protected HawkRuleAdministrator m_hma;
    protected int m_processId;
    protected String m_hostName;


    public GetOMInfoMethod(HawkRuleAdministrator hma) {
        super("getOMInfo", "Retrieves Object Store information of a Session.", AmiConstants.METHOD_TYPE_INFO, "Line");
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        return args;
    }


    public AmiParameterList getReturns() {
        final AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line number.", 0));
        values.addElement(new AmiParameter("Session", "Name of the Session", ""));
        values.addElement(new AmiParameter("Property", "Property name.", ""));
        values.addElement(new AmiParameter("Value", "Property value.", ""));
        return values;
    }//getReturns


    void fillInSessionReturnEntries(AmiParameterList values, int line, String session, String propName, String value) {
        values.addElement(new AmiParameter("Line", "Line number.", line));
        values.addElement(new AmiParameter("Session", "Name of the Session", session));
        values.addElement(new AmiParameter("Property", "Property name.", propName));
        values.addElement(new AmiParameter("Value", "Property value.", value));
    }//fillInOneReturnsEntry


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            String sessionName = inParams.getString(0);

            RuleSession[] sessions;
            if ((null == sessionName) || "".equals(sessionName.trim())) {
                sessions = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSessions();
            } else {
                final RuleSession s = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSession(sessionName);
                if (null == s) {
                    throw new Exception("Invalid session name: " + sessionName);
                }
                sessions = new RuleSession[]{s};
            }

            int line = 0;
            for (int i = 0; i < sessions.length; i++) {
                final RuleSession session = sessions[i];
                final ObjectManager om = session.getObjectManager();

                final Properties props = session.getConfig().getCacheConfig();
                sessionName = session.getName();
                for (Iterator it = props.entrySet().iterator(); it.hasNext(); line++) {
                    final Map.Entry entry = (Map.Entry) it.next();
                    final String name = (String) entry.getKey();
                    if (entry.getValue() != null && !(entry.getValue() instanceof String)) {
                    	continue;
                    }
                    String value = (String) entry.getValue();
                    if (OMCLASS_NAME.equals(name)) {
                        value = (String) OMCLASS_VALUE_TO_LABEL.get(value);
                    }
                    this.fillInSessionReturnEntries(values, line, sessionName, name, value);
                }//for
            }//for

            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke

}//class

