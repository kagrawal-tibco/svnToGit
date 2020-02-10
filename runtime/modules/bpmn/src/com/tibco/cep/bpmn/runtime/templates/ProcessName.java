package com.tibco.cep.bpmn.runtime.templates;

import com.tibco.xml.data.primitive.ExpandedName;

/*
* Author: Suresh Subramani / Date: 8/4/12 / Time: 5:33 PM
*/
public interface ProcessName {

    /**
     * Return the user defined name for this process
     * @return
     */
    String getSimpleName();

    /**
     * Return modeled fully Qualified Name
      * @return
     */
    String getModeledFQN();

    /**
     * Return the VersionedName converting the "/" => "." and any "." to $ for subprocess.
     * @return
     */
    String getName();
    /**
     * Return the Java Class Name for this process
     * @return
     */
    String getJavaClassName();

    /**
     * Return the EntityDao that holds the JobInstances in the Cache
     * @return
     */
    String getEntityDaoName();

    /**
     * Return the BackingStore Name for this process
     * @return
     */
    String getBackingStoreName();

    /**
     * Return the ExpandedName for this Process.
     * @return
     */
    ExpandedName getExpandedName();






}
