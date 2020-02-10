package com.tibco.cep.analytics.terr.io;

import java.util.List;
import java.util.UUID;

/*
* Author: Ashwin Jayaprakash / Date: 11/28/12 / Time: 4:42 PM
*/
public class DummyTerrInternals implements TerrInternals {
    @Override
    public void loadModel(String modelFilePath) throws Exception {
    }

    @Override
    public void invokeModel(String modelName,
                            List<String> doubleFieldNames,
                            List<String> intFieldNames,
                            List<String> stringFieldNames,
                            List<String> booleanFieldNames
    ) throws Exception {
        Thread.sleep(1000);

        String[] stringOuts = TerrCall.getOutStrings();
        for (int i = 0; i < stringOuts.length; i++) {
            stringOuts[i] = UUID.randomUUID().toString();
        }
    }
}
