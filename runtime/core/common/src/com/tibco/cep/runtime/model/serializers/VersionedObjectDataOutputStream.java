package com.tibco.cep.runtime.model.serializers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/*
* Author: Ashwin Jayaprakash Date: Jan 7, 2009 Time: 4:40:08 PM
*/
public class VersionedObjectDataOutputStream extends DataOutputStream
        implements VersionAwareDataOutput {
    ByteArrayOutputStream baos;

    int versionStartPosition;

    public VersionedObjectDataOutputStream(ByteArrayOutputStream baos) throws IOException {
        super(baos);

        this.baos = baos;
        this.versionStartPosition = -1;
    }

    public int getVersionStartPosition() {
        return versionStartPosition;
    }

    public void markBeforeVersionWrite() {
        if (versionStartPosition < 0) {
            try {
                flush();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }

            versionStartPosition = baos.size();
        }
    }

    @Override
    public void close() throws IOException {
        super.close();

        baos = null;
    }
}
