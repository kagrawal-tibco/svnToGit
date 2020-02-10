package com.tibco.cep.runtime.session;


public interface RuleAdministrator {


    byte STATE_UNINITIALIZED = -1;
    byte STATE_PROJECT_INITIALIZED = 1;
    byte STATE_RUNNING = 15;


    void init() throws Exception;


    void start() throws Exception;


    void stop();


    void shutdown();


    void updateState(byte state);
}
