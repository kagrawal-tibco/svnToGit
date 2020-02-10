/**
 * @author ishaan
 * @version Jun 8, 2004, 2:52:14 PM
 */
package com.tibco.cep.designtime.model.element;


import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.impl.DefaultMutableOntology;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.test.DumpSchemaModel;


public class DefaultConceptSmElementTest {


    public static void main(String args[]) throws ModelException {

        DefaultMutableOntology o = new DefaultMutableOntology();
        MutableConcept concept = o.createConcept("/", "concept", null, false);
        concept.createPropertyDefinition("bool", PropertyDefinition.PROPERTY_TYPE_BOOLEAN, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 1, false, "");
        concept.createPropertyDefinition("int", PropertyDefinition.PROPERTY_TYPE_INTEGER, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 1, false, "");
        concept.createPropertyDefinition("str", PropertyDefinition.PROPERTY_TYPE_STRING, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 1, false, "");
        concept.createPropertyDefinition("real", PropertyDefinition.PROPERTY_TYPE_REAL, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 1, false, "");

        SmElement element = concept.toSmElement();
//        XSDWriter xsdwriter = new XSDWriter();
//
//        StringWriter sw = new StringWriter();
//        try {
//            xsdwriter.write(element.getSchema(), sw, null);
//        }
//        catch (IOException e) {
//        }
//        System.out.println(sw.toString());
        DumpSchemaModel.dumpSchema(element.getSchema());
    }
}
