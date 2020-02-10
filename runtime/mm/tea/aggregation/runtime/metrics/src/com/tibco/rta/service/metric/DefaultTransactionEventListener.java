package com.tibco.rta.service.metric;

import com.tibco.rta.common.service.session.ServerSessionRegistry;
import com.tibco.rta.common.service.TransactionEvent;
import com.tibco.rta.common.service.TransactionEventListener;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/2/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultTransactionEventListener implements TransactionEventListener {

    private DefaultTransactionEventListener() {}

    public static DefaultTransactionEventListener INSTANCE = new DefaultTransactionEventListener();

    @Override
    public void onTransactionEvent(TransactionEvent context) {
        try {
            ServerSessionRegistry.INSTANCE.invoke(context);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
