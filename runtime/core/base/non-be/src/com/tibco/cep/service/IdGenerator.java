package com.tibco.cep.service;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.Recoverable;

/*
* Author: Ashwin Jayaprakash Date: Jun 30, 2009 Time: 11:33:27 AM
*/
public interface IdGenerator extends Recoverable<IdGenerator> {
    Id generateNew();
}
