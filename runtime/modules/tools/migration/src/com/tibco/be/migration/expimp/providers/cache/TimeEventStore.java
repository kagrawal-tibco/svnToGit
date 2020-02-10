package com.tibco.be.migration.expimp.providers.cache;

import java.util.Properties;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 21, 2008
 * Time: 11:07:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimeEventStore extends EventStore {
    public TimeEventStore(RuleServiceProvider rsp, String masterCacheName, Entity entityClass, Properties _logger) throws Exception {
        super(rsp, masterCacheName, entityClass, _logger);
    }

    public TimeEventStore(RuleServiceProvider rsp, String masterCacheName, Class implClass, Properties _logger) throws Exception {
        super(rsp, masterCacheName, implClass, _logger);
    }


}
