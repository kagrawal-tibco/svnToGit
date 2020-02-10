package com.tibco.cep.driver.ancillary.api;

import com.tibco.cep.runtime.service.ManagedResource;

/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 1:09:12 PM
*/
public interface Session extends ManagedResource {
    /**
     * Available after the init method.
     *
     * @return
     */
    Reader getReader();

    /**
     * Available after the init method.
     *
     * @return
     */
    Writer getWriter();
}

