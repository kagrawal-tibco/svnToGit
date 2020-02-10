package com.tibco.cep.common.resource;

import java.io.Serializable;

/*
* Author: Ashwin Jayaprakash Date: Jul 8, 2009 Time: 11:50:24 AM
*/

public interface Id extends Serializable {
    /**
     * @return Canonical form.
     */
    String getAsString();
}
