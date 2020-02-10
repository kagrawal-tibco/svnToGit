package com.tibco.cep.repo.provider;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.xml.datamodel.XiNode;


/*
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 18, 2006
 * Time: 11:56:09 AM
 */


public interface OntologyResourceProvider<P extends Project>
        extends ResourceProvider {


    public Ontology getOntology();

    public int deserializeResource(String uri, XiNode entityNode, P project)
            throws Exception;

}
