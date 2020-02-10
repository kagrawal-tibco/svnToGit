package com.tibco.cep.pattern.matcher;

import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.pattern.matcher.master.AdvancedDriverOwner;
import com.tibco.cep.pattern.matcher.master.DriverCallback;
import com.tibco.cep.pattern.matcher.master.DriverCallbackCreator;
import com.tibco.cep.pattern.util.callback.AbstractDriverCallback;

/*
* Author: Ashwin Jayaprakash Date: Jul 27, 2009 Time: 4:16:26 PM
*/
public class SimpleDriverCallback extends AbstractDriverCallback {
    public static final DriverCallbackCreator CREATOR = new SimpleDriverCallbackCreator();

    public SimpleDriverCallback() {
    }

    public SimpleDriverCallback recover(ResourceProvider resourceProvider, Object... params)
            throws RecoveryException {
        super.recover(resourceProvider, params);

        return this;
    }

    //----------

    protected static class SimpleDriverCallbackCreator implements DriverCallbackCreator {
        public DriverCallback create(ResourceProvider resourceProvider,
                                     AdvancedDriverOwner driverOwner) {
            return new SimpleDriverCallback();
        }

        public SimpleDriverCallbackCreator recover(ResourceProvider resourceProvider,
                                                   Object... params) throws RecoveryException {
            return this;
        }
    }
}
