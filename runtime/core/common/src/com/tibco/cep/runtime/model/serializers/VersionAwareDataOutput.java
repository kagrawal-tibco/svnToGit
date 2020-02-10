package com.tibco.cep.runtime.model.serializers;

import java.io.DataOutput;

/*
* Author: Ashwin Jayaprakash Date: Jan 7, 2009 Time: 6:13:36 PM
*/
public interface VersionAwareDataOutput extends DataOutput {
    int getVersionStartPosition();

    void markBeforeVersionWrite();
}
