package com.tibco.cep.functions.channel.as;

import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.remote.Invocable;
import com.tibco.as.space.remote.MemberInvocable;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

public class InvocableAdapter implements Invocable, MemberInvocable {

    public static final String INVOCABLE_CODE_NAME = "invocable$codeName";
    public static final String INVOCABLE_CODE_TYPE = "invocable$codeType";
    public static final String INVOCABLE_TARGET_TYPE = "invocable$targetType";
    public  static final String JAVA_CLASS = "java";
    public  static final String RULE_FUNCTION = "rule_function";

    private String codeName;

    private String codeType;

    private String targetType;

    private RuleSession ruleSession;
    public InvocableAdapter() {

    }

    @Override
    public Tuple invoke(Space space, Tuple keyTuple, Tuple contexTuple) {
        codeName = contexTuple.getString(INVOCABLE_CODE_NAME);
        codeType = String.valueOf(contexTuple.getString(INVOCABLE_CODE_TYPE));
        targetType = String.valueOf(contexTuple.getString(INVOCABLE_TARGET_TYPE));
        Tuple context = Tuple.create();
        context.putAll(contexTuple);
        context.remove(INVOCABLE_CODE_NAME);
        context.remove(INVOCABLE_CODE_TYPE);
        context.remove(INVOCABLE_TARGET_TYPE);
        try {
            if (codeType.equals(JAVA_CLASS)){
                Class<Invocable> clz = (Class<Invocable>) Class.forName(codeName);
                Invocable invocable = clz.newInstance();
                return invocable.invoke(space, keyTuple, context);
            }

            // Rulefunctions signature:  Tuple Fn(Tuple context)
            if (codeType.equals(RULE_FUNCTION)) {
                // TODO: Suresh which session will be used when the invocable is called from AS thread.
                ruleSession = RuleSessionManager.getCurrentRuleSession();
                if (ruleSession != null) {
                    return (Tuple) ruleSession.invokeFunction(codeName, new Object[]{context}, true);
                } else {
                    // outside a session
                    final RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
                    BEClassLoader cldr = (BEClassLoader) rsp.getClassLoader();
                    RuleFunction rfn = cldr.getRuleFunctionInstance(codeName);
                    return (Tuple) rfn.invoke(new Object[]{context});
                }
            }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Tuple invoke(Space space, Tuple contextTuple) {
        codeName = contextTuple.getString(INVOCABLE_CODE_NAME);
        codeType = String.valueOf(contextTuple.getString(INVOCABLE_CODE_TYPE));
        targetType = String.valueOf(contextTuple.getString(INVOCABLE_TARGET_TYPE));
        Tuple context = Tuple.create();
        context.putAll(contextTuple);
        context.remove(INVOCABLE_CODE_NAME);
        context.remove(INVOCABLE_CODE_TYPE);
        context.remove(INVOCABLE_TARGET_TYPE);
        try {
            if (codeType.equals(JAVA_CLASS)){
                Class<MemberInvocable> clz = (Class<MemberInvocable>) Class.forName(codeName);
                MemberInvocable invocable = clz.newInstance();
                return invocable.invoke(space, context);
            }

            // Rulefunctions signature:  Tuple Fn(Tuple context)
            if (codeType.equals(RULE_FUNCTION)) {
                // TODO: Suresh which session will be used when the invocable is called from AS thread.
                ruleSession = RuleSessionManager.getCurrentRuleSession();
                if (ruleSession != null) {
                    return (Tuple) ruleSession.invokeFunction(codeName, new Object[]{context}, true);
                } else {
                    // outside a session
                    final RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
                    BEClassLoader cldr = (BEClassLoader) rsp.getClassLoader();
                    RuleFunction rfn = cldr.getRuleFunctionInstance(codeName);
                    return (Tuple) rfn.invoke(new Object[]{context});
                }
            }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }
}
