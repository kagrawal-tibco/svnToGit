package com.tibco.cep.kernel.core.rete;

import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.kernel.concurrent.Guard;

/*
* Author: Ashwin Jayaprakash Date: Dec 5, 2008 Time: 5:02:57 PM
*/
public class DefaultGuard extends ReentrantLock implements Guard {
    /**
     * Fair lock.
     */
    public DefaultGuard() {
        super(true);
    }
}
