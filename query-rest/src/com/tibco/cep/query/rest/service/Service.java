package com.tibco.cep.query.rest.service;

import org.apache.catalina.LifecycleException;

import javax.servlet.ServletException;

/**
 * Created with IntelliJ IDEA.
 * User: pgowrish
 * Date: 2/21/14
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Service {

    public void start() throws ServletException, InterruptedException, LifecycleException;
    public void stop();
}
