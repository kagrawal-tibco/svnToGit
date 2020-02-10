package com.tibco.cep.impl.service;

import static com.tibco.cep.util.Helper.$logger;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.JexlBuilder;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.service.ScriptingService;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Jun 3, 2010 / Time: 5:16:22 PM
*/

@LogCategory("base.service.scripting")
public class DefaultScriptingService extends AbstractService implements ScriptingService {
    protected final ReentrantLock lock;

    protected transient JxltEngine jxltEngine;

    protected Logger logger;

    public DefaultScriptingService(ResourceProvider resourceProvider, Id resourceId) {
        super(resourceProvider, resourceId);

        this.lock = new ReentrantLock();
    }

    @Override
    public void start() throws LifecycleException {
        super.start();

        logger = $logger(resourceProvider, getClass());
        JexlEngine engine = new JexlBuilder().cache(10).silent(false).strict(true).debug(false).create();
        jxltEngine = engine.createJxltEngine();
        logger.log(Level.INFO, "Started " + getClass().getSimpleName());
        
    }

    public Object evaluate(String script, Map<String, Object> params) {
        lock.lock();
        try {
            Object result = null;

            JxltEngine.Expression expr = jxltEngine.createExpression(script);
            MapContext context = new MapContext(params);

            result = expr.evaluate(context);

            return result;
        }
        catch (RuntimeException e) {
            logger.log(Level.SEVERE, "Error occurred while evaluating script" +
                    " [" + script + "] with params " + params, e);

            throw e;
        }
        catch (Error e) {
            logger.log(Level.SEVERE, "Error occurred while evaluating script" +
                    " [" + script + "] with params " + params, e);

            throw e;
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void stop() throws LifecycleException {
        lock.lock();
        try {
        	jxltEngine = null;
        }
        finally {
            lock.unlock();
        }

        super.stop();

        logger.log(Level.INFO, "Stopped " + getClass().getSimpleName());
    }

    @Override
    public DefaultScriptingService recover(ResourceProvider resourceProvider, Object... params)
            throws RecoveryException {
        super.recover(resourceProvider, params);

        return this;
    }
}
