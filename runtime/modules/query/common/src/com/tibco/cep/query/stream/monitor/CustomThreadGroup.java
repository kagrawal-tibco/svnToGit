package com.tibco.cep.query.stream.monitor;

import java.util.Collection;
import java.util.HashMap;

import com.tibco.cep.query.stream.core.Registry;

/*
* Author: Ashwin Jayaprakash Date: Apr 4, 2008 Time: 5:08:59 PM
*/

/**
 * Thread-safe!
 */
public class CustomThreadGroup extends ThreadGroup {
    private HashMap<String, CustomThreadGroup> children;

    protected CustomThreadGroup(String name) {
        super(name);

        this.children = new HashMap<String, CustomThreadGroup>();
    }

    protected CustomThreadGroup(CustomThreadGroup parent, String name) {
        super(parent, name);

        parent.addChild(name, this);

        this.children = new HashMap<String, CustomThreadGroup>();
    }

    protected synchronized void addChild(String name, CustomThreadGroup child) {
        children.put(name, child);
    }

    protected synchronized void removeChild(CustomThreadGroup child) {
        children.remove(child.getName());
    }

    public synchronized CustomThreadGroup[] listChildren() {
        int size = children.size();

        return children.values().toArray(new CustomThreadGroup[size]);
    }

    protected synchronized Collection<CustomThreadGroup> getChildren() {
        return children.values();
    }

    protected synchronized CustomThreadGroup getChild(String name) {
        return children.get(name);
    }

    private CustomThreadGroup fetchParent() {
        return (CustomThreadGroup) getParent();
    }

    /**
     * Calls {@link ThreadGroup#destroy()} in the end if successful.
     *
     * @return <code>true</code> if there were no children and the operation succeeded. If there
     *         were children, then <code>false</code>.
     */
    public synchronized boolean discard() {
        if (children.isEmpty() == false) {
            return false;
        }

        //--------------

        CustomThreadGroup parent = fetchParent();
        if (parent != null) {
            parent.removeChild(this);
        }

        children.clear();

        destroy();

        return true;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        String msg = "Uncaught error from Thread Id: ";

        if (t instanceof KnownResource) {
            ResourceId id = ((KnownResource) t).getResourceId();

            msg = msg + id.generateSequenceToParentString();
        }
        else {

            //todo Parent

            msg = msg + t.getThreadGroup() + t.getId() + "-" + t.getName();
        }

        Logger logger = Registry.getInstance().getComponent(Logger.class);
        logger.log(Logger.LogLevel.ERROR, msg, e);
    }
}
