package com.tibco.cep.query.stream.monitor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.query.stream.core.Component;

/*
* Author: Ashwin Jayaprakash Date: Apr 4, 2008 Time: 5:08:59 PM
*/

/**
 * Provides a default {@link ThreadGroup} via {@link #getRootThreadGroup()}.
 */
public class ThreadCentral implements Component {
    protected final ResourceId resourceId;

    protected final ReentrantLock lock;

    protected final CustomThreadGroup rootThreadGroup;

    public ThreadCentral() {
        this.resourceId = new ResourceId(ThreadCentral.class.getName());
        this.lock = new ReentrantLock();

        String s = resourceId.getId();
        this.rootThreadGroup = new CustomThreadGroup(s);
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public void init(Properties properties) throws Exception {
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    public void discard() throws Exception {
        if (rootThreadGroup.discard()) {
            resourceId.discard();
        }
    }

    //----------

    public CustomThreadGroup createOrGetThreadGroup(ResourceId id) {
        LinkedList<String> levels = new LinkedList<String>();

        ResourceId node = id;
        while (node != null) {
            levels.add(node.getId());

            node = node.getParent();
        }

        Collections.reverse(levels);

        //-------------

        CustomThreadGroup tg = rootThreadGroup;

        lock.lock();
        try {
            for (String level : levels) {
                CustomThreadGroup temp = tg.getChild(level);

                if (temp == null) {
                    temp = new CustomThreadGroup(tg, level);
                }

                tg.addChild(level, temp);

                tg = temp;
            }
        }
        finally {
            lock.unlock();
        }

        return tg;
    }

    public CustomThreadGroup getRootThreadGroup() {
        return rootThreadGroup;
    }
}
