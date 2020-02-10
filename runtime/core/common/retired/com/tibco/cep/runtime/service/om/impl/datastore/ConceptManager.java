package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.impl.*;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayConceptReferenceSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayContainedConceptSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomConceptReferenceSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomContainedConceptSimple;
import com.tibco.cep.runtime.service.om.exception.OMException;
import com.tibco.cep.util.ResourceManager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Aug 3, 2006
 * Time: 1:06:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConceptManager  {

    DataStore ds = null;
    PersistentStore om;
    HashSet newconceptslist;
    HashSet deletedconceptslist;
    HashSet towriteconcepts;
    HashSet todeleteconcepts;

    public ConceptManager(PersistentStore _om) {
        om = _om;
    }

    public void init() throws Exception {
        newconceptslist = new HashSet();
        deletedconceptslist = new HashSet();
        ds = om.dbFactory().createConceptDataStore(om.omConfig, om.defaultSerializer);
        ds.init();
    }

    public void start() throws Exception {

    }

    public void shutdown() throws Exception {
        if(ds != null) {
            ds.close();
            ds = null;
        }
    }

    public void stop() throws Exception {
    }

    public void addConcept(Concept c) {
        if(newconceptslist.add(c))
            om.incrementOutstandingOps();
    }

    public void removeConcept(Concept c) {
        if(!newconceptslist.remove(c)) {
            deletedconceptslist.add(c);
            om.incrementOutstandingOps();
        }
    }

    public void prepareCheckpoint() {
        towriteconcepts = newconceptslist;
        todeleteconcepts = deletedconceptslist;
        if(om.m_logger.isEnabledFor(Level.INFO)) {
            if(towriteconcepts.size() > 0) om.m_logger.log(Level.INFO,"Num of instances to write = " + towriteconcepts.size());
            if(todeleteconcepts.size() > 0) om.m_logger.log(Level.INFO,"Num of instances to delete = " + todeleteconcepts.size());
        }
        newconceptslist = new HashSet();
        deletedconceptslist = new HashSet();
    }

    public void doCheckpoint(DBTransaction txn) throws OMException {
        Iterator it = todeleteconcepts.iterator();
        DefaultSerializer.SerializeEntityWrapper serializeWrapper = new DefaultSerializer.SerializeEntityWrapper();
        while(it.hasNext()) {
            Concept c = (Concept) it.next();
            if(om.immediateDeletePolicy) {
                ds.delete(txn, new Long(c.getId()));
            } else {
                serializeWrapper.entity = c;
                serializeWrapper.checkpointTime = om.checkpointTime;
                serializeWrapper.wasDeleted = true;
                ds.modify(txn, new Long(c.getId()), serializeWrapper);
                serializeWrapper.entity = null;
            }
        }
        todeleteconcepts = null;

        it = towriteconcepts.iterator();
        while(it.hasNext()) {
            Concept c = (Concept) it.next();
            serializeWrapper.entity = c;
            serializeWrapper.checkpointTime = om.checkpointTime;
            serializeWrapper.wasDeleted = false;
            ds.add(txn, new Long(c.getId()),serializeWrapper);
            serializeWrapper.entity = null;
        }
        towriteconcepts = null;
    }

     public void recover() throws OMException {
         om.m_logger.log(Level.INFO,ResourceManager.getInstance().getMessage("be.om.concepts.recovering"));
         final LinkedList recoveredInstances = new LinkedList();

        // Phase - 1 : create handles for all objects in the cache
         ds.readAll(new DataStore.ReadCallback() {
            public void readObj(Object obj) throws Exception {
                //om.session.loadObject(obj);
                om.session.getRuleServiceProvider().getIdGenerator().setMinEntityId(((Concept)obj).getId());
                ConceptImpl c = (ConceptImpl) obj;
                Handle h = om.addObjectHandle(c);
                c.setHandle(h);
                recoveredInstances.add(c);
                if(recoveredInstances.size()%1000 == 0) {
                    om.m_logger.log(Level.INFO,ResourceManager.getInstance().formatMessage("be.om.instance.read",new Long(recoveredInstances.size())));
                }
            }
        });

        // Phase - 2: Load and Resolve references
        long numReloaded = 0;
        Iterator iter = recoveredInstances.iterator();
        try {
            while (iter.hasNext()) {
                ConceptImpl instance = (ConceptImpl) iter.next();
                om.session.loadObject(instance);
                //set parent of contained instance
                Property.PropertyContainedConcept containedprops[] = instance.getContainedConceptProperties();
                Property.PropertyConceptReference referenceprops[] = instance.getConceptReferenceProperties();
                if (containedprops != null) {
                    for (int i = 0; i < containedprops.length; i++) {
                        if (containedprops[i] instanceof PropertyAtomContainedConceptSimple) {
                            long id = ((PropertyAtomContainedConceptSimple)containedprops[i]).getContainedConceptId();
                            if(id != 0) ((ConceptImpl)om.getElement(id)).forBDBRecoveryOnlySetParent(instance);
                        }
                        else if(containedprops[i] instanceof PropertyAtomContainedConceptImpl) {
                            long id = ((PropertyAtomContainedConceptImpl)containedprops[i]).getContainedConceptId();
                            if(id != 0) ((ConceptImpl)om.getElement(id)).forBDBRecoveryOnlySetParent(instance);
                        }
                        else if (containedprops[i] instanceof PropertyArrayContainedConceptSimple) {
                            PropertyArrayContainedConceptSimple pArr = (PropertyArrayContainedConceptSimple) containedprops[i];
                            for (int ii = 0; ii < pArr.length(); ii++) {
                                PropertyAtomContainedConceptSimple pa = (PropertyAtomContainedConceptSimple) pArr.get(ii);
                                long id = pa.getContainedConceptId();
                                if(id != 0) ((ConceptImpl)om.getElement(id)).forBDBRecoveryOnlySetParent(instance);
                            }
                        }
                        else if (containedprops[i] instanceof PropertyArrayContainedConceptImpl) {
                            PropertyArrayContainedConceptImpl pArr = (PropertyArrayContainedConceptImpl) containedprops[i];
                            for (int ii = 0; ii < pArr.length(); ii++) {
                                PropertyAtomContainedConceptImpl pa = (PropertyAtomContainedConceptImpl) pArr.get(ii);
                                long id = pa.getContainedConceptId();
                                if(id != 0) ((ConceptImpl)om.getElement(id)).forBDBRecoveryOnlySetParent(instance);
                            }
                        }
                        else
                            throw new OMException("Unknown ContainedConceptProperty type = " + containedprops[i]); // Should never occur.
                    }
                }
                //set the reverse reference
                if (referenceprops != null)
                    for (int i = 0; i < referenceprops.length; i++) {
                        if(referenceprops[i] instanceof PropertyAtomConceptReferenceSimple) {
                            long id = ((PropertyAtomConceptReferenceSimple)referenceprops[i]).getConceptId();
                            if(id != 0 && referenceprops[i].maintainReverseRef())  ((ConceptImpl)om.getElement(id)).forBDBRecoveryOnlySetReverseRef(referenceprops[i], instance);
                        }
                        else if(referenceprops[i] instanceof PropertyAtomConceptReferenceImpl) {
                            long id = ((PropertyAtomConceptReferenceImpl)referenceprops[i]).getConceptId();
                            if(id != 0 && referenceprops[i].maintainReverseRef()) ((ConceptImpl)om.getElement(id)).forBDBRecoveryOnlySetReverseRef(referenceprops[i], instance);
                        }
                        else if(referenceprops[i] instanceof PropertyArrayConceptReferenceSimple) {
                            PropertyArrayConceptReferenceSimple pArr = (PropertyArrayConceptReferenceSimple) referenceprops[i];
                            if(pArr.maintainReverseRef()) {
                            for (int ii = 0; ii < pArr.length(); ii++) {
                                PropertyAtomConceptReferenceSimple pa = (PropertyAtomConceptReferenceSimple) pArr.get(ii);
                                long id = pa.getConceptId();
                                if(id != 0) ((ConceptImpl)om.getElement(id)).forBDBRecoveryOnlySetReverseRef(pArr, instance);
                            }
                        }
                        }
                        else if(referenceprops[i] instanceof PropertyArrayConceptReferenceImpl) {
                            PropertyArrayConceptReferenceImpl pArr = (PropertyArrayConceptReferenceImpl) referenceprops[i];
                            if(pArr.maintainReverseRef()) {
                            for (int ii = 0; ii < pArr.length(); ii++) {
                                PropertyAtomConceptReferenceImpl pa = (PropertyAtomConceptReferenceImpl) pArr.get(ii);
                                long id = pa.getConceptId();
                                if(id != 0) ((ConceptImpl)om.getElement(id)).forBDBRecoveryOnlySetReverseRef(pArr, instance);
                            }
                        }
                        }
                        else
                            throw new OMException("Unknown ConceptReferenceProperty type = " + referenceprops[i]); // Should never occur.
                    }
                    numReloaded++;
                    if(numReloaded%1000 == 0) {
                        om.m_logger.log(Level.INFO,ResourceManager.getInstance().formatMessage("be.om.instance.loaded",new Long(numReloaded)));
                    }
                }
            } catch( Exception e) {
                e.printStackTrace();
                throw new OMException(e);
            }

         om.m_logger.log(Level.INFO,ResourceManager.getInstance().formatMessage("be.om.instance.recovered", new Long(numReloaded)));

//         try {
//             initNamedInstances();
//         } catch (Exception e) {
//             e.printStackTrace();
//             throw new OMException(e);
//         }
    }

    /* Create instances of newly created scorecards */
    /*
    private void initNamedInstances() throws Exception {

        TypeManager typeManager = om.session.getRuleServiceProvider().getTypeManager();
        Collection namedInstanceDescriptors = typeManager.getTypeDescriptors(TypeManager.TYPE_NAMEDINSTANCE);
        Iterator ite = namedInstanceDescriptors.iterator();
        while(ite.hasNext()) {
            Class namedInstance = ((TypeManager.TypeDescriptor) ite.next()).getImplClass();
            Method getStaticIdMethod = namedInstance.getMethod("getStaticId", null);
            Long staticId = (Long) getStaticIdMethod.invoke(null, null);
            if(staticId.longValue() == 0) { // Not found. Create a new instance.
                long id = om.session.getRuleServiceProvider().getIdGenerator().nextEntityId();
                Constructor cons = namedInstance.getConstructor(new Class[] {long.class});
                Concept inst = (Concept) cons.newInstance(new Object[] {new Long(id)});
                addInstance(inst);
                om.session.loadObject(inst);
            }
            else {
                Concept inst = (Concept) om.getElement(staticId.longValue());
                if(inst == null) {
                    Constructor cons = namedInstance.getConstructor(new Class[] {long.class});
                    inst = (Concept) cons.newInstance(new Object[] {staticId});
                    addInstance(inst);
                    om.session.loadObject(inst);
                }
            }
        }
    } */
}
