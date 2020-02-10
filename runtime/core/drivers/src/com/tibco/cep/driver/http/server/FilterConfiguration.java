package com.tibco.cep.driver.http.server;

import java.util.Map;
import java.util.Set;

/**
 * User: nicolas
 * Date: 9/4/13
 * Time: 1:03 PM
 */
public interface FilterConfiguration {

    String getClassName();

    String getName();

    Map<String, String> getParameters();

    Set<String> getUrlPatterns();

}