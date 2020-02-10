package com.tibco.cep.designtime.model.service.channel.mutable.impl;


import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableConcept;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Jul 26, 2004
 * Time: 11:25:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class MutableDestinationConcept extends DefaultMutableConcept {


    public MutableDestinationConcept(DefaultMutableOntology ontology, DefaultMutableFolder pack, String name, String superConceptPath) {
        super(DriverConfig.ONTOLOGY, pack, name, superConceptPath);
    }//constr


    public MutableDestinationConcept(DefaultMutableFolder pack, String name, String superConceptPath) {
        this(DriverConfig.ONTOLOGY, pack, name, superConceptPath);
    }//constr


}//class
