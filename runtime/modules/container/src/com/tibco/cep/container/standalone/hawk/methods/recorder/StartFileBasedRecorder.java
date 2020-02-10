package com.tibco.cep.container.standalone.hawk.methods.recorder;

import java.util.StringTokenizer;

import com.tibco.be.util.NVPair;
import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.container.standalone.hawk.methods.util.Command;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.util.FileBasedRecorder;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2007
 * Time: 3:44:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class StartFileBasedRecorder implements Command {

    private HawkRuleAdministrator ruleAdmin;


    public StartFileBasedRecorder(HawkRuleAdministrator ruleAdministrator) {
        this.ruleAdmin = ruleAdministrator;
    }


    public NVPair[] invoke(RuleServiceProvider rsp, String inputParams) throws Exception {

        if ((null == inputParams) || "".equals(inputParams.trim())) {
            throw new Exception("Invalid Input!");
        }
        else {
            String[] params = tokenize(inputParams, null);
            if(params == null || params.length != 3) {
                throw new Exception("Invalid Input Argument!  Should be - SessionName Directory Mode");
            }
            String sessionName = params[0];
            String directory   = params[1];
            String mode        = params[2];
            final RuleSessionImpl s = (RuleSessionImpl) this.ruleAdmin.getServiceProvider().getRuleRuntime().getRuleSession(sessionName);
            if (null == s) {
                throw new Exception("Invalid session name: " + sessionName);
            }
            FileBasedRecorder.start(s, directory, mode);
            NVPair ret = new NVPair( sessionName, "Started recording, outputDir=" + directory + ", mode="+ mode);
            return new NVPair[] { ret};
        }
    }

    public static String[] tokenize(String str, String delimiters) {
        if(str == null) return null;
        StringTokenizer st = null;
        if(delimiters == null) {
            st = new StringTokenizer(str);
        }
        else {
            st = new StringTokenizer(str, delimiters);
        }
        String[] retArr = new String[st.countTokens()];
        for(int i = 0; st.hasMoreTokens(); i++) {
            retArr[i] = st.nextToken();
        }
        return retArr;
    }


}
