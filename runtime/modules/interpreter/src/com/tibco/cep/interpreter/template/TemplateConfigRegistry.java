package com.tibco.cep.interpreter.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;


/**
 * User: nprade
 * Date: 3/5/12
 * Time: 1:00 PM
 */
public class TemplateConfigRegistry {


    private static class LazySingletonHolder {
        public static final TemplateConfigRegistry SINGLETON = new TemplateConfigRegistry();
    }


    final ConcurrentHashMap<String, RuleTemplateInstance> idToConfig =
            new ConcurrentHashMap<String, RuleTemplateInstance>();

    //Modified by Anand on 07/30/12 to fix BE-16407 : converted from private to protected
    //see com.tibco.cep.runtime.service.cluster.deploy.util.RuleTemplateInstanceDeployTool.doDeploy(RuleTemplateDeployer, String, File, boolean)
    //see com.tibco.cep.runtime.service.cluster.deploy.util.RuleTemplateInstanceDeployTool.doUndeploy(RuleTemplateDeployer, String, File, boolean)
    protected TemplateConfigRegistry() {
    }

    public void addTemplateConfig(RuleTemplateInstance config) {
        this.idToConfig.put(config.getId(), config);
    }


    public static TemplateConfigRegistry getInstance() {
        return LazySingletonHolder.SINGLETON;
    }


    public RuleTemplateInstance getTemplateConfig(String id) {
        return this.idToConfig.get(id);
    }


    public Collection<RuleTemplateInstance> getTemplateConfigs() {
        return this.idToConfig.values();
    }

    public RuleTemplateInstance removeTemplateConfig(String id) {
        return this.idToConfig.remove(id);
    }
    
    public Collection<String> getTemplateURIs() {
    	List<String> uriList = new ArrayList<String>();
    	for (String uri : this.idToConfig.keySet()) {
    		uriList.add("RT"+uri);
    	}
    	return uriList;
    }

}
