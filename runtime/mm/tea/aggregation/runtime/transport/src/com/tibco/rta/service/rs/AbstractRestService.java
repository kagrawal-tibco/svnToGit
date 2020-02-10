package com.tibco.rta.service.rs;

import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 6/5/14
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRestService {

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_TRANSPORT_REST.getCategory());
}
