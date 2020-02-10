package com.tibco.cep.modules;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 28, 2007
 * Time: 4:44:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RTFactory {
    void initialize(ModuleManager mgr) throws Exception;
    void start(int mode) throws Exception;
    void stop();
}
