package com.tibco.cep.bpmn.runtime.templates;

/*
* Author: Suresh Subramani / Date: 8/4/12 / Time: 6:35 PM
*/
public interface VersionedProcessName extends ProcessName {

    /**
     * Return version
     * @return
     */
    int getVersion();
}
