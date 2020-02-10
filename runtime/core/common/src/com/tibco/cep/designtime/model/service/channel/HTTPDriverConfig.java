package com.tibco.cep.designtime.model.service.channel;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 31/8/11
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HTTPDriverConfig extends DriverConfig {

    public List<WebApplicationDescriptor> getWebApplicationDescriptors();
}
