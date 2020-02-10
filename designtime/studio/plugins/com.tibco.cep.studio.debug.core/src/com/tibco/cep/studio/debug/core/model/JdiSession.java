package com.tibco.cep.studio.debug.core.model;

import com.sun.jdi.VirtualMachine;

/*
@author ssailapp
@date Jul 21, 2009
*/

public interface JdiSession extends JdiEventListener {

    JdiEventListener getJdiEventListener();

    DebugThreadsCache getThreadsCache();

    boolean isConnected();

    /**
     * returns the virtual machine
     */
    VirtualMachine getVM();

}
