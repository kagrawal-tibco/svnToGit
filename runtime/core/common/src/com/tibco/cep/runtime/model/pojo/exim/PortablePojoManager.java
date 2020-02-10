package com.tibco.cep.runtime.model.pojo.exim;

/*
* Author: Ashwin Jayaprakash / Date: 12/12/11 / Time: 2:03 PM
*/
public interface PortablePojoManager {
//    PortablePojo findPojoByIdForRead(long id);
//
//    PortablePojo findPojoByIdForUpdate(long id);
//
//    PortablePojo findPojoByExtIdForRead(String extId);
//
//    PortablePojo findPojoByExtIdForUpdate(String extId);

    /**
     * @param id
     * @param extId
     * @param typeId
     * @return
     */
    PortablePojo createPojo(long id, String extId, int typeId);

    /**
     * @return Can be null.
     */
    PortablePojo[] reset();
}
