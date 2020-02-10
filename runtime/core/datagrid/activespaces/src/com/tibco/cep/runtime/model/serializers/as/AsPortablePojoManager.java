package com.tibco.cep.runtime.model.serializers.as;

import com.tibco.cep.runtime.model.pojo.exim.PortablePojo;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoManager;

/*
* Author: Ashwin Jayaprakash / Date: 12/13/11 / Time: 4:03 PM
*/
public class AsPortablePojoManager implements PortablePojoManager {
//    @Override
//    public PortablePojo findPojoByIdForRead(long id) {
//        return null;
//    }
//
//    @Override
//    public PortablePojo findPojoByIdForUpdate(long id) {
//        return null;
//    }
//
//    @Override
//    public PortablePojo findPojoByExtIdForRead(String extId) {
//        return null;
//    }
//
//    @Override
//    public PortablePojo findPojoByExtIdForUpdate(String extId) {
//        return null;
//    }

    @Override
    public PortablePojo createPojo(long id, String extId, int typeId) {
        return new AsPortablePojo(id, extId, typeId);
    }

    @Override
    public PortablePojo[] reset() {
        return null;
    }
}
