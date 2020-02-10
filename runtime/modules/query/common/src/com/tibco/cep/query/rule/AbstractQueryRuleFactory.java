package com.tibco.cep.query.rule;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import javassist.ClassPool;
import javassist.LoaderClassPath;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.service.QueryServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/*
 * User: pdhar
 * Date: Jul 8, 2007
 * Time: 1:15:12 PM
 */

public abstract class AbstractQueryRuleFactory
        implements QueryRuleFactory {


    protected static HashMap<String, QueryRule> queryMap = new HashMap<String, QueryRule>();
    protected ClassPool pool;
    protected QueryServiceProvider qsp;

    static String templatePath = null;
    static StringTemplateGroup templateGroup = null;
    private Logger logger;


    public AbstractQueryRuleFactory(QueryServiceProvider qsp) throws Exception {
        this.qsp = qsp;
        this.logger = qsp.getLogger(this.getClass());
        final RuleServiceProvider rsp = qsp.getProjectContext().getRuleServiceProvider();
        pool = ClassPool.getDefault();
        LoaderClassPath classpath = new LoaderClassPath(rsp.getClassLoader());
        pool.appendClassPath(classpath);
        pool.appendSystemPath();        
        pool.appendClassPath(new BEClassPath(rsp));
        if(templatePath == null) {
            InputStream is = getClass().getClassLoader().getResourceAsStream(getTemplatePath());
            InputStreamReader reader = new InputStreamReader(is);
            templateGroup = new StringTemplateGroup(reader);
        }
    }


    protected abstract String getTemplatePath();


    /**
     * @return ClassPool
     */
    public ClassPool getClassPool() {
        return pool;
    }


    public RuleServiceProvider getRuleServiceProvider() {
        return this.qsp.getProjectContext().getRuleServiceProvider();
    }


    public Logger getLogger() {
        return this.logger;
    }


    protected StringTemplate getTemplate(String templateName) throws Exception {
        StringTemplate temp = templateGroup.getInstanceOf(templateName);
        if(temp == null)
            throw new Exception("Template "+ templateName+" not found");
        return temp;
    }


}
