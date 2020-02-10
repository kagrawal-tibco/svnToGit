package com.tibco.cep.studio.core.repo.emf.providers.adapters;


import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.DestinationConfig;
import com.tibco.be.util.config.cdd.DestinationsConfig;
import com.tibco.cep.designtime.core.model.archive.InputDestination;

/*
* User: Nicolas Prade
* Date: Sep 9, 2009
* Time: 2:53:35 PM
*/


public class DestinationsAdapter {


    private ClusterConfig clusterConfig;
    private DestinationsConfig destinationsConfig;
    private InternalEObject ieo;
    private int id;


    public DestinationsAdapter(
            ClusterConfig clusterConfig,
            DestinationsConfig destinationsConfig,
            InternalEObject ieo,
            int id) {
        this.clusterConfig = clusterConfig;
        this.destinationsConfig = destinationsConfig;
        this.ieo = ieo;
        this.id = id;
    }


    protected InputDestination getInputDestination(
            DestinationConfig destinationConfig) {
        return null;//todo how to create InputDestinationImpl?
//
//                BEArchiveResourceProvider.BEArchive.InputDestinationConfigImpl(
//                destinationConfig.getUri().getUri(),
//                destinationConfig.getPreprocessor(),
//                destinationConfig.getThreadCount(),
//                destinationConfig.getQueueSize(),
//                0);  //todo weight and thread model?
    }


    protected EList<InputDestination> getInputDestinations(
            DestinationsConfig destinationsConfig) {

        final EList<InputDestination> inputDestinationConfigs =
                new EObjectContainmentEList<InputDestination>(InputDestination.class, this.ieo, this.id);

        for (final DestinationConfig cfg : destinationsConfig.getAllDestinations()) {
            inputDestinationConfigs.add(this.getInputDestination((DestinationConfig) cfg));
        }
        
        return inputDestinationConfigs;
    }


    public EList<InputDestination> getInputDestinations() {
        return this.getInputDestinations(this.destinationsConfig);
    }


}
