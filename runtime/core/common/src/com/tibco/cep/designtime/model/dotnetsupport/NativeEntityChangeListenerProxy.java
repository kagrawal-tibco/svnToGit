package com.tibco.cep.designtime.model.dotnetsupport;


import com.tibco.cep.designtime.model.EntityChangeListener;
import com.tibco.cep.designtime.model.mutable.MutableEntity;


/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Feb 3, 2006
 * Time: 6:15:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class NativeEntityChangeListenerProxy extends AbstractNativeChangeListenerProxy implements EntityChangeListener {


    public NativeEntityChangeListenerProxy(long gcHandle) {
        super(gcHandle);
    }


    native public void entityChanged(MutableEntity entity);


    native public void entityAdded(MutableEntity entity);


    native public void entityRemoved(MutableEntity entity);


    native public void entityRenamed(MutableEntity entity, String oldName);


    native public void entityMoved(MutableEntity entity, String oldPath);
}