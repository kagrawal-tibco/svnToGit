package com.tibco.cep.designtime.model;

import junit.framework.TestCase;
import com.tibco.cep.designtime.model.MutableOntology;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.MutableConcept;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;


public abstract class AbstractSampleCodeTest extends TestCase
{
    public AbstractSampleCodeTest(String name){
        super(name);
    }

    private MutableOntology ontology;

    /**
     * Creates an instance of a default ontology for use in the tests
     */


    public void setUp() {
        ontology = createOntology();
    }

    protected abstract MutableOntology createOntology();

    public Ontology testSampleSetOfObjects()throws ModelException {
        final MutableFolder folder = ((MutableFolder) (ontology.getRootFolder())).createSubFolder("folder", true);

        MutableConcept customer = ontology.createConcept(folder, "customer", null, true);

        MutableConcept soldToCustomer = ontology.createConcept(folder, "SoldToCustomer", customer, true);
        Concept customerAddress = ontology.createConcept(folder, "customer address", null, true);

        PropertyDefinition addressLineOne= customer.createPropertyDefinition("Address Line One", PropertyDefinition.PROPERTY_TYPE_STRING, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 10, false, "555 Any Street");
        PropertyDefinition addressLineTwo= customer.createPropertyDefinition( "Address Line Two", PropertyDefinition.PROPERTY_TYPE_STRING, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 10, false, "Apartment 5");


        String conceptTypePath= customerAddress.getFullPath();
        PropertyDefinition customerFirstName= customer.createPropertyDefinition("FirstNameProperty", PropertyDefinition.PROPERTY_TYPE_STRING, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 10, false, "J.");
        PropertyDefinition customerLastName= customer.createPropertyDefinition("LastNameProperty", PropertyDefinition.PROPERTY_TYPE_STRING, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 10, false, "Doe");
        PropertyDefinition customerIdNumber= customer.createPropertyDefinition("IdNumber", PropertyDefinition.PROPERTY_TYPE_INTEGER, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 1, false, "5555555");
        PropertyDefinition adressProperty = customer.createPropertyDefinition("AddressProperty", PropertyDefinition.PROPERTY_TYPE_CONCEPT, conceptTypePath, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 10, false, "defaultValue");

        PropertyDefinition amountSold= soldToCustomer.createPropertyDefinition("AmountSold", PropertyDefinition.PROPERTY_TYPE_INTEGER, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 10, false, "1000");
        PropertyDefinition multipleOrders= soldToCustomer.createPropertyDefinition("MultipleOrders", PropertyDefinition.PROPERTY_TYPE_BOOLEAN, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 10, false, "false");
        PropertyDefinition customerScore= soldToCustomer.createPropertyDefinition("CustomerScore", PropertyDefinition.PROPERTY_TYPE_REAL, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 10, false, "3.00");

        return ontology;
    }









}
