package com.tibco.cep.repo.hotdeploy;

/**
 * Created by IntelliJ IDEA.
 * User: hzhang
 * Date: Jul 18, 2006
 * Time: 1:04:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HotDeployer {
    void start() throws Exception;
    void stop();
}
