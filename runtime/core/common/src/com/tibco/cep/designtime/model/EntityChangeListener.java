package com.tibco.cep.designtime.model;

import com.tibco.cep.designtime.model.mutable.MutableEntity;


/**
 * A generic interface for listening to changes on any kind of Entity.
 *
 * @author ishaan
 * @version Mar 30, 2004 10:49:18 AM
 */
public interface EntityChangeListener {


    public void entityChanged(MutableEntity entity);


    public void entityAdded(MutableEntity entity);


    public void entityRemoved(MutableEntity entity);


    public void entityRenamed(MutableEntity entity, String oldName);


    public void entityMoved(MutableEntity entity, String oldPath);
}
