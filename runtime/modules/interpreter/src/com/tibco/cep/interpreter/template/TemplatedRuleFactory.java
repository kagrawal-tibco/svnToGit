package com.tibco.cep.interpreter.template;

import com.tibco.cep.interpreter.template.evaluator.RuleTemplateEvaluatorDescriptorFactory;
import com.tibco.cep.query.model.impl.ProjectContextImpl;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 * User: nprade
 * Date: 3/5/12
 * Time: 2:13 PM
 */
public class TemplatedRuleFactory {


    private RuleServiceProvider rsp;
    private RuleTemplateEvaluatorDescriptorFactory rtEvaluatorDescriptorFactory;


    public TemplatedRuleFactory(
            RuleServiceProvider rsp)
            throws Exception {

        this.rsp = rsp;
        final ProjectContextImpl projectContext = new ProjectContextImpl(rsp, false);
        this.rtEvaluatorDescriptorFactory = new RuleTemplateEvaluatorDescriptorFactory(projectContext);
    }


    public TemplatedRule newTemplatedRule(
            RuleTemplateInstance config)
            throws Exception {

        String url = config.getImplementsPath();
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        final ExpandedName xName = ExpandedName.makeName(
                TypeManager.DEFAULT_BE_NAMESPACE_URI + url,
                url.substring(url.lastIndexOf('/') + 1));

        final TypeManager.TypeDescriptor descriptor = ((BEClassLoader) rsp.getClassLoader()).getTypeDescriptor(xName);

        if (null == descriptor) {
            throw new IllegalArgumentException(
                    "Cannot find RuleTemplate <" + url + "> when trying to load: " + config.getId());
        }

        final TemplatedRule rule = (TemplatedRule) descriptor.getImplClass().newInstance();
        rule.initialize(this.rtEvaluatorDescriptorFactory);
        rule.configure(config);

        return rule;
    }


    @SuppressWarnings("UnusedDeclaration")
    public TemplatedRule newTemplatedRule(
            String configID)
            throws Exception {

        final RuleTemplateInstance config = TemplateConfigRegistry.getInstance().getTemplateConfig(configID);
        if (null == config) {
            throw new IllegalArgumentException();
        }

        return this.newTemplatedRule(config);
    }


}
