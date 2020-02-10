package com.tibco.cep.runtime.model;

import java.lang.reflect.Constructor;
import java.util.Properties;

import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 29, 2006
 * Time: 7:12:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityFactory {
    

    public static TypeManager createTypeManager(RuleServiceProvider provider, Properties env ) throws Exception {
        String clazzName = env.getProperty("com.tibco.cep.entity.factory", BEClassLoader.class.getName());
        Class factoryClass = Class.forName(clazzName);

        Constructor constructor = factoryClass.getConstructor(new Class[] {RuleServiceProvider.class, Properties.class});
        TypeManager manager = (TypeManager) constructor.newInstance( new Object[] {provider, env});
        return manager;
    }


}

