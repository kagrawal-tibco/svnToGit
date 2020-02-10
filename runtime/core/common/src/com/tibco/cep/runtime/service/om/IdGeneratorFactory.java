package com.tibco.cep.runtime.service.om;

import java.lang.reflect.Constructor;
import java.util.Properties;

import com.tibco.cep.kernel.service.IdGenerator;
import com.tibco.cep.kernel.service.impl.DefaultIdGenerator;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;
/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 26, 2006
 * Time: 1:49:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class IdGeneratorFactory {
    public static final String IDGENERATOR_CLASS = "com.tibco.cep.kernel.service.idgenerator.class";
    public static IdGenerator createIdGenerator(String name, RuleServiceProvider rsp) throws Exception {

        Properties props = rsp.getProperties();
        String idGeneratorClass = props.getProperty(IDGENERATOR_CLASS);
        if(idGeneratorClass == null || idGeneratorClass.equals(DefaultIdGenerator.class.getName()))
            return new DefaultIdGenerator();
        else {
            Class clazz = Class.forName(idGeneratorClass);
            Constructor constructor = clazz.getConstructor(new Class[] {Properties.class, Logger.class, String.class});
            return (IdGenerator) constructor.newInstance(new Object[] {name, rsp});

        }
    }
}
