package com.tibco.cep.analytics.pmml.io;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomDoubleSimple;
import org.dmg.pmml.FieldName;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: dtsai
 * Date: 2/26/13
 * Time: 2:45 PM
 */
public class PmmlFunctionsTest {
    @Test
    public void testBasicRandomForestAirQuality() {
        double err = 0.1;

        String modelPath = getClass().getResource("resources/airQualityPMML.xml").getPath();

        final ConcurrentPmmlCall pmmlResource = new ConcurrentPmmlCall();

        try {
            pmmlResource.loadModel("airQuality", modelPath);
            Map<FieldName, Object> parameters = new LinkedHashMap<FieldName, Object>();

            // Ozone;Solar.R;Wind;Temp;Month;Day;JPMML
            // 41;190;7.4;67;5;1;37.149166666666666
            parameters.put(new FieldName("Ozone"), 41);
            parameters.put(new FieldName("SolarR"), 190);
            parameters.put(new FieldName("Wind"), 7.4);
            parameters.put(new FieldName("Temp"), 67);
            parameters.put(new FieldName("Month"), 5);
            parameters.put(new FieldName("Day"), 1);

            Object result = pmmlResource.callModel("airQuality", parameters);
            if (Math.abs(Double.parseDouble(result.toString()) - 37.149166666666666) / 37.149166666666666 > err) {
                Assert.fail("Result should be 37.149166666666666 +- 10% error.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRandomForestAirQuality() {
        double err = 0.1;

        String modelPath = getClass().getResource("resources/airQualityPMML.xml").getPath();

        final ConcurrentPmmlCall pmmlResource = new ConcurrentPmmlCall();
        final PmmlFunctions fun = new PmmlFunctions();

        try {
            PmmlFunctions.loadModel("airQuality", modelPath);
            assertEquals(PmmlFunctions.containsModel("airQuality"), true);
            assertEquals(PmmlFunctions.containsModel("nonExist"), false);
            PmmlFunctions.removeModel("airQuality");
            assertEquals(PmmlFunctions.containsModel("airQuality"), false);

            PmmlFunctions.loadModel("airQuality", modelPath);

            Map<FieldName, Object> parameters = new LinkedHashMap<>();


            Object result = PmmlFunctions.callParsModel("airQuality",
                    "Ozone", 41,
                    "SolarR", 190,
                    "Wind", 7.4,
                    "Temp", 67,
                    "Month", 5,
                    "Day", 1
            );

            if (Math.abs(Double.parseDouble(result.toString()) - 37.149166666666666) / 37.149166666666666 > err) {
                Assert.fail("Result should be 37.149166666666666 +- 10% error.");
            }

            Concept cept = new ConceptImpl() {
                private Property[] properties;

                @Override
                public Property[] getProperties() {
                    return properties;
                }

                public Concept constructor() {
                    properties = new Property[6];
                    properties[0] = new PropertyAtomDoubleSimple(null, 41) {
                        @Override
                        public String getName() {
                            return "Ozone";
                        }
                    };
                    properties[1] = new PropertyAtomDoubleSimple(null, 190) {
                        @Override
                        public String getName() {
                            return "SolarR";
                        }
                    };
                    properties[2] = new PropertyAtomDoubleSimple(null, 7.4) {
                        @Override
                        public String getName() {
                            return "Wind";
                        }
                    };
                    properties[3] = new PropertyAtomDoubleSimple(null, 67) {
                        @Override
                        public String getName() {
                            return "Temp";
                        }
                    };
                    properties[4] = new PropertyAtomDoubleSimple(null, 5) {
                        @Override
                        public String getName() {
                            return "Month";
                        }
                    };
                    properties[5] = new PropertyAtomDoubleSimple(null, 1) {
                        @Override
                        public String getName() {
                            return "Day";
                        }
                    };
                    return this;
                }
            }.constructor();

            Object result2 = PmmlFunctions.callParsModel("airQuality", cept);

            if (Math.abs(Double.parseDouble(result2.toString()) - 37.149166666666666) / 37.149166666666666 > err) {
                Assert.fail("Result should be 37.149166666666666 +- 10% error.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
