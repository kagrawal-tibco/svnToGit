package com.tibco.cep.runtime.management.impl.adapter.util;

import com.tangosol.util.Filter;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.FQNameFinder;

/*
* Author: Ashwin Jayaprakash Date: Mar 2, 2009 Time: 3:42:33 PM
*/
public class CoherenceFQNameFilterFactory {
    public Filter createChildFinder(FQName parentFQN) {
        ChildFilter filter = new ChildFilter();
        filter.setParentFQN(parentFQN);

        return filter;
    }

    public Filter createParentFinder(FQName childFQN) {
        ParentFilter filter = new ParentFilter();
        filter.setChildFQN(childFQN);

        return filter;
    }

    //------------

    protected static class ChildFilter extends FQNameFinder.ChildFinder implements Filter {
        public boolean evaluate(Object o) {
            FQName fqn = (FQName) o;

            return matches(fqn);
        }
    }

    protected static class ParentFilter extends FQNameFinder.ParentFinder implements Filter {
        public boolean evaluate(Object o) {
            FQName fqn = (FQName) o;

            return matches(fqn);
        }
    }
}
