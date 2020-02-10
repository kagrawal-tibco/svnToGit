/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.service.cluster.om;

import java.util.Map;

import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.om.api.ComparisonResult;
import com.tibco.cep.runtime.service.om.api.Invocable;

/*
* Author: Ashwin Jayaprakash Date: Jan 22, 2009 Time: 4:53:22 PM
*/

public class EventLivenessChecker implements Invocable {

	private static final long serialVersionUID = -4496298690563196503L;

	public EventLivenessChecker() {
    }

    @Override
    public Object invoke(Map.Entry entry) {
        Object value = entry.getValue();
        return checkIfAlive(value);
    }

    /**
     * @param value
     * @return {@link ComparisonResult}
     *         based on whether the entry is available and still
     *         alive}.
     */
    protected ComparisonResult checkIfAlive(Object value) {
        if (value == null) {
            return ComparisonResult.VALUE_NOT_PRESENT;
        } else if (value instanceof ObjectTable.Tuple) {
        	ObjectTable.Tuple tuple = (ObjectTable.Tuple) value;
        	if (tuple.isDeleted()) {
                return ComparisonResult.VALUE_NOT_PRESENT;
            }
        	return ComparisonResult.SAME_VERSION;
        }
        return ComparisonResult.NOT_VERSIONED;
    }
}