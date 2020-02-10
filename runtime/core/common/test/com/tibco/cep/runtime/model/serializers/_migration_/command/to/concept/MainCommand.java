package com.tibco.cep.runtime.model.serializers._migration_.command.to.concept;

import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.serializers._migration_.ConversionScratchpad;
import com.tibco.cep.runtime.model.serializers._migration_.command.ConceptConstants;
import com.tibco.cep.runtime.model.serializers._migration_.command.Command;
import com.tibco.cep.runtime.service.om.coherence.cluster.CacheCluster;
import com.tibco.cep.runtime.service.om.coherence.cluster.MetadataCache;
import com.tibco.cep.runtime.session.ServiceLocator;

/*
* Author: Ashwin Jayaprakash Date: Jan 19, 2009 Time: 1:34:28 PM
*/
public class MainCommand implements Command {
    protected Class<? extends ConceptImpl> conceptClass;

    public MainCommand() {
    }

    public void execute(ConversionScratchpad scratchpad) throws Exception {
        if (conceptClass == null) {
            ServiceLocator locator = scratchpad.getLocator();
            CacheCluster cacheCluster = locator.locateCacheCluster();
            MetadataCache metadataCache = cacheCluster.getMetadataCache();

            int typeId = scratchpad.removeIntermediateDatum(ConceptConstants.KEY_TYPE_ID);
            conceptClass = (Class<? extends ConceptImpl>) metadataCache.getClass(typeId);
        }

        ConceptImpl concept = conceptClass.newInstance();

        int version = scratchpad.removeIntermediateDatum(ConceptConstants.KEY_VERSION);
        concept.setVersion(version);

        boolean isDeleted = scratchpad.removeIntermediateDatum(ConceptConstants.KEY_IS_DELETED);
        if (isDeleted) {
            concept.markDeleted();
        }

        long id = scratchpad.removeIntermediateDatum(ConceptConstants.KEY_ID);
        concept.setId(id);

        String extId = scratchpad.removeIntermediateDatum(ConceptConstants.KEY_EXT_ID);
        concept.setExtId(extId);

        ConceptOrReference parentRef =
                scratchpad.removeIntermediateDatum(ConceptConstants.KEY_PARENT_ID);
        concept.setParentReference(parentRef);

        scratchpad.setToObject(concept);
    }
}
