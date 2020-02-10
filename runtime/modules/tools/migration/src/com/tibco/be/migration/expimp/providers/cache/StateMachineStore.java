package com.tibco.be.migration.expimp.providers.cache;

import java.util.Properties;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 22, 2008
 * Time: 10:02:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class StateMachineStore extends ConceptStore {
    protected StateMachine stateMachine;

    public StateMachineStore(RuleServiceProvider rsp, StateMachine sm, String masterCacheName, Properties cacheConfig) throws Exception {
        super(rsp, masterCacheName, sm.getOwnerConcept(), cacheConfig);
        this.stateMachine = sm;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public String getStateMachineClassName() {
        return ModelNameUtil.modelPathToGeneratedClassName(stateMachine.getOwnerConcept().getFullPath()) + "$" + stateMachine.getName();
    }

    public Class getImplClass() {
        ExpandedName uri = ExpandedName.makeName(TypeManager.DEFAULT_BE_NAMESPACE_URI + stateMachine.getOwnerConcept().getFullPath() + "/"+stateMachine.getName(),stateMachine.getName());
        TypeManager.TypeDescriptor smtd = mrsp.getTypeManager().getTypeDescriptor(uri);
        return smtd.getImplClass();
    }

    public String getCacheName() {
        return getMasterCacheName()+ "." + getStateMachineClassName();
    }

}
