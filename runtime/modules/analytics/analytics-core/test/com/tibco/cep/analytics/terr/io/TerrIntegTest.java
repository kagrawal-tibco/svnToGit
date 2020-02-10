package com.tibco.cep.analytics.terr.io;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: dtsai
 * Date: 11/8/12
 * Time: 1:42 PM
 */
public class TerrIntegTest {
    @Test
    public void testRandomForestAirQuality() {
        double err = 0.1;

        TerrCall.setInternals(new DefaultTerrInternals());

        String modelPath = getClass().getResource("resources/airQualityScoreFun.R").getPath();
        try {
            TerrCall.load(modelPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String modelFunction = "predictOzone";

        //----------

        // SolarR, Wind, Temp, Month, Day
        Call call = new Call("SolarR", 190.0, "Wind", 7.4, "Temp", 67.0, "Month", 5.0, "Day", 1.0);

        try {
            TerrCall.call(modelFunction, call);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(call);
        if (Math.abs(Double.parseDouble(call.getOutput().toString()) - 35.1) / 35.1 > err) {
            Assert.fail("Result should be 35.1 +- 10% error.");
        }

        //-----------

        Call call1 = new Call(
                "SolarR", 118.0,
                "Wind", 8.0,
                "Temp", 72.0,
                "Month", 5.0,
                "Day", 2.0
        );
        Call call2 = new Call(
                "Wind", 12.6,
                "Temp", 74.0,
                "SolarR", 149.0,
                "Day", 3.0,
                "Month", 5.0
        );

        BatchCall batchCall = new BatchCall(modelFunction, call1, call2);

        try {
            TerrCall.call(batchCall);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(batchCall);
        if (Math.abs(Double.parseDouble(call1.getOutput().toString()) - 30.9) / 30.9 > err) {
            Assert.fail("Result should be 30.9 +- 10% error.");
        }
        if (Math.abs(Double.parseDouble(call2.getOutput().toString()) - 17.4) / 17.4 > err) {
            Assert.fail("Result should be 17.4 +- 10% error.");
        }
    }

    @Test
    public void testRandomForestCarAcceptability() {
        /*
         Car Evaluation Data Set http://archive.ics.uci.edu/ml/datasets/Car+Evaluation

        Class Values:

            unacc, acc, good, vgood

        Attributes:

            buying: vhigh, high, med, low.
            maint: vhigh, high, med, low.
            doors: 2, 3, 4, 5more.
            persons: 2, 4, more.
            lug_boot: small, med, big.
            safety: low, med, high.

         */

        String modelPath = getClass().getResource("resources/carAcceptabilityScoreFun.R").getPath();
        try {
            TerrCall.load(modelPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String modelFunction = "predictAcceptability";

        //----------

        Call call = new Call(
                "buying", "vhigh",
                "maint", "vhigh",
                "doors", "2",
                "persons", "2",
                "lug_boot", "small",
                "safety", "low"
        ); // unacc

        try {
            TerrCall.call(modelFunction, call);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(call);

        assertEquals(call.getOutput(), "unacc");

        //-----------

        Call call1 = new Call(
                "persons", "more",
                "lug_boot", "med",
                "doors", "2",
                "buying", "low",
                "safety", "med",
                "maint", "low"
        ); // acc

        Call call2 = new Call(
                "lug_boot", "big",
                "persons", "more",
                "safety", "high",
                "maint", "low",
                "doors", "5more",
                "buying", "low"
        ); // vgood

        BatchCall batchCall = new BatchCall(modelFunction, call1, call2);

        try {
            TerrCall.call(batchCall);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(batchCall);

        assertEquals(call1.getOutput(), "acc");
        assertEquals(call2.getOutput(), "vgood");
    }

    @Test
    public void testAsync() throws ExecutionException, InterruptedException {
        double err = 0.1;

        String modelPath1 = getClass().getResource("resources/airQualityScoreFun.R").getPath();
        try {
            TerrCall.load(modelPath1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String modelFunction1 = "predictOzone";

        String modelPath2 = getClass().getResource("resources/carAcceptabilityScoreFun.R").getPath();
        try {
            TerrCall.load(modelPath2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String modelFunction2 = "predictAcceptability";

        //----------

        ConcurrentTerrCall.start();

        Future<Call> f1 = ConcurrentTerrCall.call(modelFunction1, new Call(
                "SolarR", 118.0,
                "Wind", 8.0,
                "Temp", 72.0,
                "Month", 5.0,
                "Day", 2.0
        ));

        Future<Call> f2 = ConcurrentTerrCall.call(modelFunction2, new Call(
                "buying", "low",
                "maint", "low",
                "doors", "4",
                "persons", "2",
                "lug_boot", "med",
                "safety", "low"
        ));//unacc
        Future<Call> f3 = ConcurrentTerrCall.call(modelFunction1, new Call(
                "SolarR", 313.0,
                "Wind", 11.5,
                "Temp", 62.0,
                "Month", 5.0,
                "Day", 4.0
        ));
        Future<Call> f4 = ConcurrentTerrCall.call(modelFunction2, new Call(
                "buying", "low",
                "maint", "low",
                "doors", "3",
                "persons", "more",
                "lug_boot", "small",
                "safety", "high"
        ));//good
        Future<Call> f5 = ConcurrentTerrCall.call(modelFunction2, new Call(
                "buying", "high",
                "maint", "high",
                "doors", "3",
                "persons", "4",
                "lug_boot", "med",
                "safety", "high"
        ));
        Future<Call> f6 = ConcurrentTerrCall.call(modelFunction2, new Call(
                "buying", "low",
                "maint", "low",
                "doors", "5more",
                "persons", "4",
                "lug_boot", "big",
                "safety", "high"
        ));//vgood
        Future<Call> f7 = ConcurrentTerrCall.call(modelFunction1, new Call(
                "SolarR", 149.0,
                "Wind", 12.6,
                "Temp", 74.0,
                "Month", 5.0,
                "Day", 3.0
        ));

        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());
        System.out.println(f4.get());
        System.out.println(f5.get());
        System.out.println(f6.get());
        System.out.println(f7.get());

        if (Math.abs(Double.parseDouble(f1.get().getOutput().toString()) - 30.9) / 30.9 > err) {
            Assert.fail("Result should be 30.9 +- 10% error.");
        }
        if (Math.abs(Double.parseDouble(f3.get().getOutput().toString()) - 20.7) / 20.7 > err) {
            Assert.fail("Result should be 20.7 +- 10% error.");
        }
        if (Math.abs(Double.parseDouble(f7.get().getOutput().toString()) - 17.4) / 17.4 > err) {
            Assert.fail("Result should be 17.4 +- 10% error.");
        }

        assertEquals(f2.get().getOutput(), "unacc");
        assertEquals(f4.get().getOutput(), "good");
        assertEquals(f5.get().getOutput(), "acc");
        assertEquals(f6.get().getOutput(), "vgood");

        ConcurrentTerrCall.stop();
    }

    @Test
    public void testInflammationAsync() throws ExecutionException, InterruptedException {

        String modelPath1 = getClass().getResource("resources/InflammationOfUrinaryBadder.R").getPath();
        try {
            TerrCall.load(modelPath1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String modelFunction1 = "PredictInflammationOfUrinaryBadder";

        String modelPath2 = getClass().getResource("resources/NephritisOfRenalPelvisOrigin.R").getPath();
        try {
            TerrCall.load(modelPath2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String modelFunction2 = "PredictNephritisOfRenalPelvisOrigin";

        //----------
        ConcurrentTerrCall.start();

        Future<Call> f1a = ConcurrentTerrCall.call(modelFunction1, new Call(
                "TemperatureOfPatient", 35.5,
                "OccurrenceOfNausea", true,
                "LumbarPain", false,
                "UrinePushing", false,
                "MicturitionPains", false,
                "BurningOfUrethra", false
        ));
        Future<Call> f1b = ConcurrentTerrCall.call(modelFunction2, new Call(
                "TemperatureOfPatient", 35.5,
                "OccurrenceOfNausea", true,
                "LumbarPain", false,
                "UrinePushing", false,
                "MicturitionPains", false,
                "BurningOfUrethra", false
        ));

        Future<Call> f2a = ConcurrentTerrCall.call(modelFunction1, new Call(
                "TemperatureOfPatient", 38.0,
                "OccurrenceOfNausea", false,
                "LumbarPain", true,
                "UrinePushing", true,
                "MicturitionPains", false,
                "BurningOfUrethra", true
        ));
        Future<Call> f2b = ConcurrentTerrCall.call(modelFunction2, new Call(
                "TemperatureOfPatient", 38.0,
                "OccurrenceOfNausea", false,
                "LumbarPain", true,
                "UrinePushing", true,
                "MicturitionPains", false,
                "BurningOfUrethra", true
        ));

        Future<Call> f3a = ConcurrentTerrCall.call(modelFunction1, new Call(
                "TemperatureOfPatient", 37.5,
                "OccurrenceOfNausea", false,
                "LumbarPain", false,
                "UrinePushing", true,
                "MicturitionPains", false,
                "BurningOfUrethra", false
        ));
        Future<Call> f3b = ConcurrentTerrCall.call(modelFunction2, new Call(
                "TemperatureOfPatient", 37.5,
                "OccurrenceOfNausea", false,
                "LumbarPain", false,
                "UrinePushing", true,
                "MicturitionPains", false,
                "BurningOfUrethra", false
        ));


        System.out.println(f1a.get());
        System.out.println(f1b.get());

        System.out.println(f2a.get());
        System.out.println(f2b.get());

        System.out.println(f3a.get());
        System.out.println(f3b.get());

        assertEquals(f1a.get().getOutput(), "FALSE");
        assertEquals(f1b.get().getOutput(), "FALSE");

        assertEquals(f2a.get().getOutput(), "FALSE");
        assertEquals(f2b.get().getOutput(), "TRUE");

        assertEquals(f3a.get().getOutput(), "TRUE");
        assertEquals(f3b.get().getOutput(), "FALSE");

        ConcurrentTerrCall.stop();
    }
}
