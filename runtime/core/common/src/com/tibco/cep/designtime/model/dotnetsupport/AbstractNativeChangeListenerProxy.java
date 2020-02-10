package com.tibco.cep.designtime.model.dotnetsupport;


/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 8, 2006
 * Time: 9:20:08 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractNativeChangeListenerProxy {


    //address of the .NET delegate taht entityChagned should call
    public long gcHandle;


    AbstractNativeChangeListenerProxy(long gcHandle) {
        this.gcHandle = gcHandle;
    }


    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof NativeEntityChangeListenerProxy)) {
            return false;
        }
        return equals(this.gcHandle, ((NativeEntityChangeListenerProxy) o).gcHandle);
    }


    native private static boolean equals(long thisGcHandleAddr, long otherGcHandleAddr);


    public int hashCode() {
        return hashCode(gcHandle);
    }


    native private static int hashCode(long gcHandleAddr);


    public void finalize() {
        System.out.println("AbstractChangeListenerProxy finalizer " + gcHandle);
        finalize(gcHandle);
    }


    native private static void finalize(long gcHandle);
}
