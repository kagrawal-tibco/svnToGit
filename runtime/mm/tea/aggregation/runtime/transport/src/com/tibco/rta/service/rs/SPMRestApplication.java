package com.tibco.rta.service.rs;

import com.tibco.rta.service.rs.util.QueryStateProvider;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/2/14
 * Time: 11:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class SPMRestApplication extends Application {

    private Set<Object> singletons = new HashSet<Object>();

    private Set<Class<?>> classes = new HashSet<Class<?>>();

    public SPMRestApplication() {
        singletons.add(new QueryStateProvider());
        classes.add(RestAdminService.class);
        classes.add(RestMetricService.class);
        classes.add(RestQueryService.class);
        classes.add(RestRuleService.class);
        classes.add(RestSessionService.class);
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
