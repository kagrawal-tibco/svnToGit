package com.tibco.cep.kernel.core.rete;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map.Entry;

/*
* Author: Ashwin Jayaprakash / Date: 10/24/11 / Time: 3:28 PM
*/
public abstract class BeTransaction {
    private static final int REAL_TXN_RETRIES = 6;

    private static final int FAKE_TXN_RETRIES = 1;

    private static final int REAL_TXN_BACKOFF_MILLIS = 5;

    protected static final Logger logger = LogManagerFactory.getLogManager().getLogger(BeTransaction.class);

    protected static final ThreadLocal<HashMap<Object, Object>> TXN_OBJECTS =
            new ThreadLocal<HashMap<Object, Object>>();

    protected static BeTransactionHook optionalTxnHook = initTransactionHook();

    private static int numTxnRetries = FAKE_TXN_RETRIES;

    protected String identifier;

    protected int attempt;

    static BeTransactionHook initTransactionHook() {
        try {
            Class k = Class.forName("com.tibco.cep.runtime.model.element.impl.ManagedObjectManager");
            Method m = k.getMethod("getManagedObjectSpi");

            numTxnRetries = REAL_TXN_RETRIES;

            return (BeTransactionHook) m.invoke(null);
        }
        catch (Throwable t) {
            logger.log(Level.WARN, "Support for BE Transaction not available.");
            logger.log(Level.DEBUG, t, "Exception is.");
        }

        numTxnRetries = FAKE_TXN_RETRIES;

        return null;
    }

    protected BeTransaction(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static void addToTxn(Object id, Object o) {
        HashMap<Object, Object> map = TXN_OBJECTS.get();
        if (map == null) {
            return;
        }

        map.put(id, o);
    }

    public static Object getFromTxn(Object id) {
        HashMap<Object, Object> map = TXN_OBJECTS.get();
        if (map == null) {
            return null;
        }

        return map.get(id);
    }

    public static Object removeFromTxn(Object id) {
        HashMap<Object, Object> map = TXN_OBJECTS.get();
        if (map == null) {
            return null;
        }

        return map.remove(id);
    }

    public final void execute() {
        Throwable lastErr = null;

        for (int i = 0; i < numTxnRetries; i++) {
            try {
                if (i > 0) {
                    try {
                        Thread.sleep(REAL_TXN_BACKOFF_MILLIS);
                    }
                    catch (InterruptedException e) {
                    }
                }

                run();

                return;
            }
            catch (Throwable t) {
                if (numTxnRetries == REAL_TXN_RETRIES && logger.isEnabledFor(Level.DEBUG)) {
                    logger.log(Level.DEBUG, t, "Error in transaction [" + getIdentifier() + "] attempt [" + attempt + "]");
                }

                lastErr = t;
            }
        }

        if (numTxnRetries == REAL_TXN_RETRIES) {
            throw new RuntimeException(
                    "Transaction [" + getIdentifier() + "] failed even after [" + attempt + "] attempts. Giving up", lastErr);
        }

        if (lastErr instanceof RuntimeException) {
            throw (RuntimeException) lastErr;
        }

        throw new RuntimeException(lastErr);
    }

    protected final void run() {
        attempt++;

        if (numTxnRetries == REAL_TXN_RETRIES && logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG, "Starting transaction [" + getIdentifier() + "] attempt [" + attempt + "]");
        }

        HashMap<Object, Object> map = TXN_OBJECTS.get();
        if (map == null) {
            map = new HashMap<Object, Object>();
            TXN_OBJECTS.set(map);
        }

        BeTransaction outerMostTxn = (BeTransaction) map.get(BeTransaction.class);
        if (outerMostTxn != null) {
            doTxnWork();

            return;
        }
        else {
            map.put(BeTransaction.class, this);
        }

        boolean success = false;
        if (optionalTxnHook == null || optionalTxnHook.isComposable()) {
            try {
                if (optionalTxnHook != null) {
                    optionalTxnHook.begin();
                }

                doTxnWork();

                if (optionalTxnHook != null) {
                    optionalTxnHook.commit();
                }
                success = true;
            }
            finally {
                try {
                    if (!success && optionalTxnHook != null) {
                        optionalTxnHook.rollback();
                    }
                }
                finally {
                    map.clear();
                }
            }
        }
        else {
            try {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        doTxnWork();
                    }
                };

                optionalTxnHook.txnWork(r);
                success = true;
            }
            finally {
                if (!success && logger.isEnabledFor(Level.DEBUG)) {
                    StringBuilder sb = new StringBuilder("'Transaction' error. Contents of transaction:\n");
                    for (Entry<Object, Object> entry : map.entrySet()) {
                        sb.append("    ").append(entry.getKey()).append('=').append(entry.getValue()).append('\n');
                    }

                    logger.log(Level.DEBUG, sb.toString());
                }

                map.clear();
            }
        }
    }

    /**
     * @param ruleSession Can be null.
     */
    protected void cleanup(Object ruleSession) {
        if (optionalTxnHook != null) {
            BeTransaction outerMostTxn = (BeTransaction) TXN_OBJECTS.get().get(BeTransaction.class);
            if (outerMostTxn != this) {
                return;
            }

            optionalTxnHook.cleanupTxnWorkArea(ruleSession);
        }
    }

    protected abstract void doTxnWork();
}
