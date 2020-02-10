package com.tibco.cep.designtime.model.mutable.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.designtime.model.MutationContext;
import com.tibco.cep.designtime.model.MutationObservable;
import com.tibco.cep.designtime.model.MutationObservableContainer;
import com.tibco.cep.designtime.model.MutationObserver;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 6, 2006
 * Time: 7:56:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultMutableArrayList extends ArrayList implements MutationObservableContainer {


    private MutationObservable mutationObservable;
    private MutationObserver mutationObserver;


    private void _initialize() {
        this.mutationObservable = new DefaultMutationObservable(this);
        this.mutationObserver = new DefaultMutationNotificationTransmitter(this.mutationObservable);
    }


    public void add(int i, Object object) {
        this.mutationObserver.startObserving(object);
        super.add(i, object);
        this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.ADD, object));
    }


    public boolean add(Object object) {
        if (super.add(object)) {
            this.mutationObserver.startObserving(object);
            this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.ADD, object));
            return true;
        }
        return false;
    }


    public boolean addAll(Collection collection) {
        if (super.addAll(collection)) {
            for (Iterator it = collection.iterator(); it.hasNext();) {
                final Object object = it.next();
                this.mutationObserver.startObserving(object);
                this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.ADD, object));
            }
            return true;
        }
        return false;
    }


    public boolean addAll(int i, Collection collection) {
        if (super.addAll(i, collection)) {
            for (Iterator it = collection.iterator(); it.hasNext();) {
                final Object object = it.next();
                this.mutationObserver.startObserving(object);
                this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.ADD, object));
            }
            return true;
        }
        return false;
    }


    public DefaultMutableArrayList() {
        super();
        this._initialize();
    }


    public DefaultMutableArrayList(Collection collection) {
        super(collection);
        this._initialize();
        for (Iterator it = this.iterator(); it.hasNext();) {
            this.mutationObserver.startObserving(it.next());
        }
    }


    public DefaultMutableArrayList(int i) {
        super(i);
        this._initialize();
    }


    public void clear() {
        for (Iterator it = new ArrayList(this).iterator(); it.hasNext();) {
            this.remove(it.next());
        }
    }


    public Object clone() {
        return new DefaultMutableArrayList(this);
    }


    public MutationObservable getMutationObservable() {
        return this.mutationObservable;
    }


    public Object remove(int i) {
        final Object object = super.remove(i);
        if (null != object) {
            this.mutationObserver.stopObserving(object);
            this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.DELETE, object));
        }
        return object;
    }


    public boolean remove(Object object) {
        if (super.remove(object)) {
            this.mutationObserver.stopObserving(object);
            this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.DELETE, object));
            return true;
        }
        return false;
    }


    public boolean removeAll(Collection collection) {
        boolean changed = false;
        for (Iterator it = this.iterator(); it.hasNext();) {
            final Object object = it.next();
            if (collection.contains(object)) {
                it.remove();
                changed = true;
            }
        }
        return changed;
    }


    protected void removeRange(int fromIndex, int toIndex) {
        this.removeAll(this.subList(fromIndex, toIndex));
    }


    public boolean retainAll(Collection collection) {
        boolean changed = false;
        for (Iterator it = this.iterator(); it.hasNext();) {
            final Object object = it.next();
            if (!collection.contains(object)) {
                this.mutationObserver.stopObserving(object);
                it.remove();
                changed = true;
            }
        }
        return changed;
    }


    public List subList(int fromIndex, int toIndex) {
        final DefaultMutableArrayList subList = new DefaultMutableArrayList(super.subList(fromIndex, toIndex));
        this.mutationObserver.startObserving(subList);
        return subList;
    }
}
