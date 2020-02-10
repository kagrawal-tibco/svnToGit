/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 30/6/2010
 */

package com.tibco.cep.runtime.service.cluster.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
* Author: Ashwin Jayaprakash Date: Jan 7, 2009 Time: 2:08:20 PM
*/
public class SingleValueLiteReusableMap<K, V> extends SingleValueLiteMap<K, V> {
    protected ReusableEntrySet reusableEntrySet;

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        if (reusableEntrySet == null) {
            reusableEntrySet = new ReusableEntrySet();
        }

        return reusableEntrySet;
    }

    protected class ReusableEntrySet extends MyEntrySet {
        protected MyEntryIterator reusableEntryIterator;

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            if (reusableEntryIterator == null) {
                reusableEntryIterator = new MyEntryIterator();
            }
            else {
                reusableEntryIterator.reset();
            }

            return reusableEntryIterator;
        }
    }
}