package com.tibco.cep.designtime.model.mutable.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.model.MutationContext;
import com.tibco.cep.designtime.model.MutationObservable;
import com.tibco.cep.designtime.model.MutationObservableContainer;
import com.tibco.cep.designtime.model.MutationObserver;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 6, 2006
 * Time: 4:22:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultMutableLinkedHashMap extends LinkedHashMap implements MutationObservableContainer {


    private MutationObservable mutationObservable;
    private MutationObserver mutationObserver;


    public DefaultMutableLinkedHashMap() {
        super();
        this._initialize();
    }


    public DefaultMutableLinkedHashMap(int initialCapacity) {
        super(initialCapacity);
        this._initialize();
    }


    public DefaultMutableLinkedHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this._initialize();
    }


    public DefaultMutableLinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
        this._initialize();
    }


    public DefaultMutableLinkedHashMap(Map m) {
        super(m);
        this._initialize();
        for (Iterator it = this.values().iterator(); it.hasNext();) {
            final Object value = it.next();
            this.mutationObserver.startObserving(value);
        }
    }


    private void _initialize() {
        this.mutationObservable = new DefaultMutationObservable(this);
        this.mutationObserver = new DefaultMutationNotificationTransmitter(this.mutationObservable);
    }


    public void clear() {
        final List keys = new ArrayList(this.keySet());
        for (Iterator it = keys.iterator(); it.hasNext();) {
            this.remove(it.next()); // Takes care of all notifications.
        }
    }


    protected void clearWithoutNotification() {
        for (Iterator it = this.values().iterator(); it.hasNext();) {
            this.mutationObserver.stopObserving(it.next());
        }
        super.clear();
    }


    public Object clone() {
        return new DefaultMutableLinkedHashMap(this);
    }


    public MutationObservable getMutationObservable() {
        return this.mutationObservable;
    }


    protected MutationObserver getMutationObserver() {
        return this.mutationObserver;
    }


    public Object put(Object key, Object value) {
        final Object oldValue = this.get(key);
        if (value == oldValue) {
            return oldValue;
        }
        if (null != oldValue) {
            this.remove(key);
        }
        super.put(key, value);
        this.mutationObserver.startObserving(value);
        this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.ADD, value));
        return oldValue;
    }


    protected Object putWithoutNotification(Object key, Object value) {
        final Object oldValue = this.get(key);
        if (null != oldValue) {
            this.mutationObserver.stopObserving(oldValue);
        }
        this.mutationObserver.startObserving(value);
        return super.put(key, value);
    }


    public void putAll(Map map) {
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            final Map.Entry entry = (Map.Entry) it.next();
            final Object key = entry.getKey();
            final Object value = entry.getValue();
            this.put(key, value);
        }
    }


    protected void putAllWithoutNotification(Map map) {
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            final Map.Entry entry = (Map.Entry) it.next();
            final Object key = entry.getKey();
            final Object value = entry.getValue();
            this.putWithoutNotification(key, value);
        }
    }


    public Object remove(Object key) {
        final Object value = this.removeWithoutNotification(key);
        this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.DELETE, value));
        return value;
    }


    protected Object removeWithoutNotification(Object key) {
        final Object value = super.remove(key);
        this.mutationObserver.stopObserving(value);
        return value;
    }


}
