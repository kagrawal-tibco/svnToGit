package com.tibco.cep.analytics.terr.io;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/*
* Author: Ashwin Jayaprakash / Date: 11/27/12 / Time: 11:20 AM
*/
public class TerrCallTest {
    @Test
    public void testBasic() {
        TerrCall.setInternals(new DummyTerrInternals());

        String modelPath = "/user/analytics/prediction.R";
        try {
            TerrCall.load(modelPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String modelFunction = "tempPredict";

        //----------

        Call call1 = new Call("Field1", 100.0, "Field2", 34.0, "Field3", "sfo");

        System.out.println(call1);

        try {
            TerrCall.call(modelFunction, call1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\t" + call1.getOutput());

        //-----------

        call1 = new Call("Field1", 100.0, "Field2", 34.0, "Field3", "sfo");
        Call call2 = new Call("Field1", 155.0, "Field2", 12.0, "Field3", "sjc");
        BatchCall batchCall = new BatchCall(modelFunction, call1, call2);

        System.out.println(batchCall);

        try {
            TerrCall.call(batchCall);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\t" + call1.getOutput());
        System.out.println("\t" + call2.getOutput());
    }

    @Test
    public void testAsync() throws ExecutionException, InterruptedException {
        TerrCall.setInternals(new DummyTerrInternals());

        String modelPath1 = "/user/analytics/regression.R";
        try {
            TerrCall.load(modelPath1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String modelFunction1 = "predictNextN";

        String modelPath2 = "/user/analytics/classification.R";
        try {
            TerrCall.load(modelPath2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String modelFunction2 = "classify";

        //----------

        ConcurrentTerrCall.start();

        Future<Call> f1 = ConcurrentTerrCall.call(modelFunction1, new Call("Field1", 155.0, "Field2", 12.0, "Field3", "one"));
        Future<Call> f2 = ConcurrentTerrCall.call(modelFunction2, new Call("Field1", 155.0, "Field2", 12.0, "Field3", "two"));
        Future<Call> f3 = ConcurrentTerrCall.call(modelFunction1, new Call("Field1", 155.0, "Field2", 12.0, "Field3", "three"));
        Future<Call> f4 = ConcurrentTerrCall.call(modelFunction2, new Call("Field1", 155.0, "Field2", 12.0, "Field3", "four"));
        Future<Call> f5 = ConcurrentTerrCall.call(modelFunction2, new Call("Field1", 155.0, "Field2", 12.0, "Field3", "five"));
        Future<Call> f6 = ConcurrentTerrCall.call(modelFunction2, new Call("Field1", 155.0, "Field2", 12.0, "Field3", "six"));
        Future<Call> f7 = ConcurrentTerrCall.call(modelFunction1, new Call("Field1", 155.0, "Field2", 12.0, "Field3", "seven"));

        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());
        System.out.println(f4.get());
        System.out.println(f5.get());
        System.out.println(f6.get());
        System.out.println(f7.get());

        ConcurrentTerrCall.stop();
    }
}
