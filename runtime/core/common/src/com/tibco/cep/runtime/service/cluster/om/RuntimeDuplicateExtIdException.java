/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Aug 31, 2009
 * Time: 7:02:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuntimeDuplicateExtIdException extends RuntimeException
{
    RuntimeDuplicateExtIdException(DuplicateExtIdException parent) {
        super(parent);
    }
    
    DuplicateExtIdException getParent() {
        return (DuplicateExtIdException)getCause();
    }
}
