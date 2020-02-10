package com.tibco.cep.bpmn.runtime.agent;

import java.util.Collection;

import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;

/*
* Author: Suresh Subramani / Date: 2/11/12 / Time: 6:42 PM
*/
public class ProcessChangeListManager {


    RtcStatusVisitor visitor;


    public ProcessChangeListManager() {
        visitor = new RtcStatusVisitor();
    }

    public void traverseConceptGraphForChanges(Concept concept, ObjectManager objManager, RtcOperationList opList, boolean isComplete, boolean checkDuplicates, Collection<Element> retractedElements) throws Exception {

        if (concept == null) return;

        for(Element el:retractedElements) {
        	el.delete();
        }
        for(Element el:retractedElements) {
        	if(el instanceof Concept){
        		Concept c = (Concept) el;
        		visitor.visit(c, objManager, opList, isComplete,checkDuplicates);
        		if(objManager.handleExists(el)){
        			 BaseHandle baseHandle = (BaseHandle) objManager.getElementHandle(c.getId());
                     baseHandle.setRetracted();
        		}
        	}
        }
        
        visitor.visit(concept, objManager, opList, isComplete,checkDuplicates);


        Property[] allProperties = concept.getProperties();
        for (int i = 0; i < allProperties.length; i++) {
            Property property = allProperties[i];

            if (property instanceof PropertyAtomContainedConcept) {
                Concept containedConcept = ((PropertyAtomContainedConcept)property).getContainedConcept();
                traverseConceptGraphForChanges(containedConcept, objManager, opList, isComplete,checkDuplicates,retractedElements);
            }
            else if (property instanceof PropertyAtomConceptReference) {
                Concept referenceConcept = ((PropertyAtomConceptReference) property).getConcept();
                if (referenceConcept instanceof JobContext) continue;
                if ((referenceConcept == null) || (referenceConcept.getId() == concept.getId())) continue; //BPMN - AbstractConcept has parent property which is raw

                traverseConceptGraphForChanges(referenceConcept, objManager, opList, false,checkDuplicates,retractedElements);
            }
            else if (property instanceof PropertyArrayConceptReference) {
                PropertyArrayConceptReference pacr = (PropertyArrayConceptReference) property;
                for (int k=0; k < pacr.length(); k++) {
                    Concept refConcept = ((PropertyAtomConceptReference) pacr.get(k)).getConcept();
                    if (refConcept instanceof JobContext) continue;
                    if ((refConcept == null) || (refConcept.getId() == concept.getId())) continue; //BPMN - AbstractConcept has parent property which is raw
                    traverseConceptGraphForChanges(refConcept, objManager, opList, false,checkDuplicates,retractedElements);
                }
            }
            else if (property instanceof PropertyArrayContainedConcept) {
                PropertyArrayContainedConcept pacr = (PropertyArrayContainedConcept) property;
                for (int k=0; k < pacr.length(); k++) {
                    Concept containedConcept = ((PropertyAtomContainedConcept) pacr.get(k)).getContainedConcept();
                    traverseConceptGraphForChanges(containedConcept, objManager, opList, isComplete,checkDuplicates,retractedElements);
                }

            }
        }
    }

    public void traverseConceptGraphForChanges(Concept concept, Collection<Entity> assertedList, Collection<Entity> loadedList) throws Exception {

        if (concept == null) return;

        visitor.visit(concept, assertedList, loadedList);


        Property[] allProperties = concept.getProperties();
        for (int i = 0; i < allProperties.length; i++) {
            Property property = allProperties[i];

            if (property instanceof PropertyAtomContainedConcept) {
                Concept containedConcept = ((PropertyAtomContainedConcept)property).getContainedConcept();
                traverseConceptGraphForChanges(containedConcept, assertedList, loadedList);
            }
            else if (property instanceof PropertyAtomConceptReference) {
                Concept referenceConcept = ((PropertyAtomConceptReference) property).getConcept();

                if (referenceConcept instanceof JobContext) continue;
                if ((referenceConcept == null) || (referenceConcept.getId() == concept.getId())) continue; //BPMN - AbstractConcept has parent property which is raw

                traverseConceptGraphForChanges(referenceConcept, assertedList, loadedList);
            }
            else if (property instanceof PropertyArrayConceptReference) {
                PropertyArrayConceptReference pacr = (PropertyArrayConceptReference) property;
                for (int k=0; k < pacr.length(); k++) {
                    Concept refConcept = ((PropertyAtomConceptReference) pacr.get(k)).getConcept();
                    if (refConcept instanceof JobContext) continue;
                    if ((refConcept == null) || (refConcept.getId() == concept.getId())) continue; //BPMN - AbstractConcept has parent property which is raw
                    traverseConceptGraphForChanges(refConcept, assertedList, loadedList);
                }
            }
            else if (property instanceof PropertyArrayContainedConcept) {
                PropertyArrayContainedConcept pacr = (PropertyArrayContainedConcept) property;
                for (int k=0; k < pacr.length(); k++) {
                    Concept containedConcept = ((PropertyAtomContainedConcept) pacr.get(k)).getContainedConcept();
                    traverseConceptGraphForChanges(containedConcept, assertedList, loadedList);
                }

            }
        }
    }



    class RtcStatusVisitor {

        public void visit(Concept concept, ObjectManager objManager, RtcOperationList opList, boolean isComplete, boolean checkDuplicates) throws Exception
        {

            ConceptImpl ceptImpl = (ConceptImpl) concept;
            DistributedCacheBasedStore dcStore = (DistributedCacheBasedStore) objManager;

            //First check if it is deleted
            if (ceptImpl == null) return;

            BaseHandle baseHandle = null; 
            baseHandle = (BaseHandle) dcStore.getAddElementHandle(ceptImpl, false);
//            if(checkDuplicates) {
//            } else {
//            	if ((concept.getExtId() != null) && (concept.getExtId().length() > 0)) {
//            		baseHandle = dcStore.getNewElementExtHandle(ceptImpl, null, true);
//            	} else {
//            		baseHandle = dcStore.getNewElementHandle(ceptImpl, null, true);
//            	}
//            }
            baseHandle.rtcClearAll();
            
            if (isComplete || ceptImpl.isDeleted()) {

                opList.setRtcDeleted(baseHandle);
            }
            else if (ceptImpl.getVersion() == 0) {

                opList.setRtcAsserted(baseHandle);
            }
            else if (ceptImpl.getPersistenceModified()) {

                opList.setRtcModified(baseHandle);
            }
        }

        public void visit(Concept concept, Collection<Entity> assertedList, Collection<Entity> loadedList) throws Exception
        {

            ConceptImpl ceptImpl = (ConceptImpl) concept;

            //First check if it is deleted
            if (ceptImpl == null) return;
            if (ceptImpl.isDeleted() || ceptImpl.isDeleted()) return;

            assertedList.add(ceptImpl);
//            if (ceptImpl.getVersion() == 0) {
//                assertedList.add(ceptImpl);
//            }
//            else {
//                loadedList.add(ceptImpl);
//            }


        }
    }
}

