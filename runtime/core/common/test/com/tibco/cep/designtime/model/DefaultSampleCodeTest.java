package com.tibco.cep.designtime.model;


import com.tibco.cep.designtime.model.impl.DefaultMutableOntology;


public class DefaultSampleCodeTest extends AbstractSampleCodeTest
{
    public DefaultSampleCodeTest (String name) {
        super(name);
    }

    public MutableOntology createOntology() {
        return new DefaultMutableOntology();
    }


    public Ontology testSampleSetOfObjects() throws ModelException {
        return super.testSampleSetOfObjects();    //To change body of overridden methods use File | Settings | File Templates.
    }
}