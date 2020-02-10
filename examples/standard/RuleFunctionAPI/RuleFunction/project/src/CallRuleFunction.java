import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Ameya
 * Date: 11/20/14
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallRuleFunction {
    RuleSession ruleSession;
    RuleServiceProvider provider;
    //String mannersData;

    CallRuleFunction(String repoUrl, String traFile, String cddFile, String procUnit) throws Exception {
        Properties env = new Properties();
        env.put("tibco.repourl", repoUrl);
        env.put("be.bootstrap.property.file", traFile);
        env.put("com.tibco.be.config.path", cddFile);
        env.put("com.tibco.be.config.unit.id", procUnit);

        provider = RuleServiceProviderManager.getInstance().newProvider("RuleFunction", env);
        RuleServiceProviderManager.getInstance().setDefaultProvider(provider);
        provider.configure(RuleServiceProvider.MODE_API);

    }

    public void invokeFunction(String functionName, Object [] obj, boolean synchronize) {
        String sessionName = ((RuleServiceProviderImpl) provider).getRuleSessionService().getRuleSessions()[0].getName();  //"inference-class"
        ruleSession = provider.getRuleRuntime().getRuleSession(sessionName);
        ruleSession.invokeFunction(functionName, obj, synchronize);

    }

    public static void main(String[] args) throws Exception {

        String traFile = args[0] ;
        String cddFile = args[1];
        String procUnit = args[2];
        String repourl = args[3];
        String functionName = args[4];
        Object [] obj = new Object[args.length - 4];
        for(int i=5, k=0; i < args.length; i++, k++)
            obj[k] = Integer.parseInt(args[i]);

        CallRuleFunction function = new CallRuleFunction(repourl, traFile, cddFile, procUnit);
        function.invokeFunction(functionName, obj, true);

    }
}
