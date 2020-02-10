package com.tibco.cep.query.stream.core;

import java.util.Collection;
import java.util.LinkedHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 15, 2007 Time: 10:53:34 AM
 */

public final class Registry {
    protected static Registry singleton;

    protected final LinkedHashMap<Class<? extends Component>, Component> registeredComponents;

    protected Registry() {
        this.registeredComponents = new LinkedHashMap<Class<? extends Component>, Component>();
    }

    public static Registry getInstance() {
        if (singleton == null) {
            singleton = new Registry();
        }

        return singleton;
    }

    // ----------

    public void register(Class<? extends Component> clazz, Component component) {
        registeredComponents.put(clazz, component);
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        Component component = registeredComponents.get(clazz);
        if (component != null) {
            return clazz.cast(component);
        }

        return null;
    }

    /**
     * @return Same order in which the Components were registered.
     */
    public Collection<? extends Component> getComponents() {
        return registeredComponents.values();
    }

    public void unregister(Class<? extends Component> clazz) {
        registeredComponents.remove(clazz);
    }
}
