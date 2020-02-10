package com.tibco.cep.runtime.session;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA. User: apuneet Date: Feb 25, 2009 Time: 4:30:55 PM To change this
 * template use File | Settings | File Templates.
 */
public interface JobGroupManager {
    int getMaxJobsInaGroup();

    void execute(Collection<? extends Runnable> jobs) throws InterruptedException;
}
