package com.tibco.cep.service;

import java.util.Map;

import com.tibco.cep.util.annotation.ThreadSafe;

/*
* Author: Ashwin Jayaprakash / Date: Jun 3, 2010 / Time: 5:16:08 PM
*/
public interface ScriptingService extends Service {
    @ThreadSafe
    Object evaluate(String script, Map<String, Object> params);
}
