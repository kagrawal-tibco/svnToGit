package com.tibco.be.rms.repo;

import java.io.InputStream;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.repo.provider.impl.OntologyResourceProviderImpl;
import com.tibco.objectrepo.vfile.VFileStream;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Sep 15, 2010
 * Time: 10:54:16 AM
 * <!--
 * Add Description of the class here
 * -->
 */
public class ManagedOntologyResourceProvider extends OntologyResourceProviderImpl<ManagedEMFProject> {

    public ManagedOntologyResourceProvider(ManagedEMFProject _project) {
        super(_project);
    }


    @Override
    public int deserializeResource(String URI, InputStream is, Project project, VFileStream stream) throws Exception {
        return ResourceProvider.SUCCESS_CONTINUE;
    }

    @Override
    public void init() throws Exception {
        this.ontology = new ManagedOntology(project);
    }

    @Override
    public boolean supportsResource(String streamName) {
        return false;
    }

    @Override
    public Ontology getOntology() {
        return ontology;
    }
}
