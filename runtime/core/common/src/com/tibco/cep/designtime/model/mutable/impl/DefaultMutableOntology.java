/**
 * User: ishaan
 * Date: Mar 26, 2004
 * Time: 2:48:29 PM
 */
package com.tibco.cep.designtime.model.mutable.impl;


import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.util.UniqueNamer;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.EntityChangeListener;
import com.tibco.cep.designtime.model.EntityInputSource;
import com.tibco.cep.designtime.model.EntityView;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.XiSerializable;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.ConceptView;
import com.tibco.cep.designtime.model.element.Instance;
import com.tibco.cep.designtime.model.element.InstanceView;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutableConcept;
import com.tibco.cep.designtime.model.element.mutable.MutableConceptView;
import com.tibco.cep.designtime.model.element.mutable.MutableInstance;
import com.tibco.cep.designtime.model.element.mutable.MutableInstanceView;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyInstance;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableConcept;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableConceptLink;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableConceptReference;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableConceptView;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableInstance;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableInstanceReference;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableMutableInstanceView;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableProcess;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutablePropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutablePropertyInstance;
import com.tibco.cep.designtime.model.element.stategraph.StandaloneStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStandaloneStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateAnnotationLink;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateComposite;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateEnd;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateEntity;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateGroupBox;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStatePseudo;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateRectangle;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateRoundedRectangle;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateSimple;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateStart;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateSubmachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateTextLabel;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateTransition;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateVertex;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.mutable.MutableEvent;
import com.tibco.cep.designtime.model.event.mutable.impl.DefaultMutableEvent;
import com.tibco.cep.designtime.model.mutable.MutableEntity;
import com.tibco.cep.designtime.model.mutable.MutableEntityView;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.registry.ElementTypes;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleSet;
import com.tibco.cep.designtime.model.rule.RuleTemplate;
import com.tibco.cep.designtime.model.rule.StandaloneRule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleFunction;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleSet;
import com.tibco.cep.designtime.model.rule.mutable.MutableStandaloneRule;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRule;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRuleFunction;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRuleSet;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableStandaloneRule;
import com.tibco.cep.designtime.model.service.calendar.Calendar;
import com.tibco.cep.designtime.model.service.calendar.mutable.MutableCalendar;
import com.tibco.cep.designtime.model.service.calendar.mutable.impl.DefaultMutableCalendar;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableChannel;
import com.tibco.cep.designtime.model.service.channel.mutable.impl.DefaultMutableChannel;
import com.tibco.cep.repo.Project;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;


public class DefaultMutableOntology implements MutableOntology {


    protected DefaultMutableFolder m_rootFolder;
    protected LinkedHashMap m_entities;
    public static final XiFactory FACTORY = XiFactoryFactory.newInstance();
    protected ArrayList m_entityChangeListeners = new ArrayList();

    protected static final int CONCEPT_VIEW_FLAG = 0;
    protected static final int INSTANCE_VIEW_FLAG = 1;
    protected static MutableStateMachine mostRecentStateMachine = null;
    protected static DefaultMutableConcept mostRecentConcept = null;
    protected HashMap<String, Entity> m_aliases;
	protected Project m_project;
	protected Date lastModifiedDate;
	protected Date lastPersistedDate;


    public DefaultMutableOntology() {
        m_entities = new LinkedHashMap();
        m_aliases = new HashMap<String, Entity>();
        m_rootFolder = new DefaultMutableFolder(this, null, "" + DefaultMutableFolder.FOLDER_SEPARATOR_CHAR);
    }
    
    public DefaultMutableOntology(Project project) {
    	this();
    	m_project = project;
    }


    public Collection getEntities() {
        return m_entities.values();
    }


    public MutableConcept createConcept(String pack, String name, String superConceptPath, boolean autoNameOnConflict) throws ModelException {
        return createConcept(pack, name, superConceptPath, autoNameOnConflict, false);
    }


    public MutableConcept createConcept(String pack, String name, String superConceptPath, boolean autoNameOnConflict, boolean isScorecard) throws ModelException {
        DefaultMutableFolder defFolder = (DefaultMutableFolder) createFolder(pack, false);
        DefaultMutableConcept defSuper = (DefaultMutableConcept) getConcept(superConceptPath);

        name = getNameForEntity(defFolder, name, autoNameOnConflict);

        /** The Concept doesn't exist, so create it */
        DefaultMutableConcept concept = new DefaultMutableConcept(this, defFolder, name, "", isScorecard);

        /** Call this method to see if a loop is caused by the inheritance */
        if (defSuper != null) {
            concept.setSuperConcept(superConceptPath);
        }

        /** Register the new Concept with its Package Folder */
        defFolder.m_entities.put(name, concept);

        String fullPath = concept.getFullPath();

        /** Register the new Concept with its super MutableConcept */
        if (defSuper != null) {
            defSuper.addSubConcept(fullPath);
        }

        /**
         * Register the new Concept with its Ontology
         * The key used is concept's full path followed by its name.
         */
        m_entities.put(fullPath, concept);
        notifyEntityAdded(concept);

        /** See if we need to re-register the Concept */
        registerConcept(concept);

        return concept;
    }


//    public MutableDBConcept createDBConcept(String pack, String name, boolean autoNameOnConflict) throws ModelException {
//        DefaultMutableFolder defFolder = (DefaultMutableFolder) createFolder(pack, false);
//
//        name = getNameForEntity(defFolder, name, autoNameOnConflict);
//
//        /** The Concept doesn't exist, so create it */
//        DefaultMutableDBConcept concept = new DefaultMutableDBConcept(this, defFolder, name, "");
//
//        /** See if we need to re-register the Concept */
//        registerConcept(concept);
//
//        /** Register the new Concept with its Package Folder */
//        defFolder.m_entities.put(name, concept);
//
//        String fullPath = concept.getFullPath();
//
//        /**
//         * Register the new Concept with its Ontology
//         * The key used is concept's full path followed by its name.
//         */
//        m_entities.put(fullPath, concept);
//        notifyEntityAdded(concept);
//        return concept;
//    }


    public Collection getRuleParticipants() {
        ArrayList rps = new ArrayList();

        Collection c = getConcepts();
        rps.addAll(c);

        c = getEvents();
        rps.addAll(c);

        return rps;
    }


    public void registerStateMachine(DefaultMutableStateMachine sm) {

    }


    /**
     * Finds any entities referencing this Concept's path and establishes a link to them
     * within this Concept's data model.
     *
     * @param concept
     */
    public void registerConcept(DefaultMutableConcept concept) {
        concept.clearReferringViewPaths();
        concept.clearReferringConceptPaths();
        concept.clearSubConceptPaths();

        final String conceptPath = concept.getFullPath();

        // Checks amongst Concepts
        for (Iterator conceptIt = this.getConcepts().iterator(); conceptIt.hasNext();) {
            final DefaultMutableConcept dc = (DefaultMutableConcept) conceptIt.next();
            if (conceptPath.equals(dc.getSuperConceptPath())) {
                concept.addSubConcept(dc.getFullPath());
            }
        }

        // Checks amongst Instances
        for (Iterator instanceIt = getInstances().iterator(); instanceIt.hasNext();) {
            final DefaultMutableInstance di = (DefaultMutableInstance) instanceIt.next();
            final String instancePath = di.getFullPath();
            final String ownerPath = di.getConcept().getFullPath();
            if (conceptPath.equals(ownerPath)) {
                concept.getInstances().add(instancePath);
            }
        }

        /** Check among Property Definitions */
        Collection defs = getPropertyDefinitions();
        Iterator defIt = defs.iterator();
        while (defIt.hasNext()) {
            DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) defIt.next();
            if ((dpd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) || (dpd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT))
            {
                String conceptTypePath = dpd.getConceptTypePath();

                Concept owner = dpd.getOwner();
                String ownerPath = owner.getFullPath();

                /** The PropertyDefinition references the Concept being registered */
                if (conceptPath.equals(conceptTypePath)) {
                    /** Register its owner as a referrer to the Concept being registered */
                    concept.addReferringProperty(ownerPath, dpd.getName());
                }

                if (concept.equals(owner)) {
                    DefaultMutableConcept propConcept = (DefaultMutableConcept) dpd.getConceptType();
                    if (propConcept != null) {
                        propConcept.addReferringProperty(ownerPath, dpd.getName());
                    }
                }

            }
        }

        /** Check among Views */
        Collection views = getConceptViews();
        Iterator viewIt = views.iterator();
        while (viewIt.hasNext()) {
            DefaultMutableConceptView dcv = (DefaultMutableConceptView) viewIt.next();
            if (dcv.containsReferenceTo(conceptPath)) {
                concept.addView(dcv.getFullPath());
            }
        }

//        registerSMRuleParticipants(concept);

//        /** Check among Rules and RuleFunctions */
//        registerRuleParticipant(concept);
    }


//    public void registerSMRuleParticipants(DefaultMutableConcept concept) {
//        String dcPath = concept.getFullPath();
//        Collection rules = concept.getRules();
//        Iterator ruleIt = rules.iterator();
//        while (ruleIt.hasNext()) {
//            Rule rule = (Rule) ruleIt.next();
//            final Symbols symbols = rule.getDeclarations();
//            if ((null != symbols) && !symbols.isEmpty()) {
//                for (Iterator it = symbols.values().iterator(); it.hasNext();) {
//                    final Symbol symbol = (Symbol) it.next();
//                    final String path = symbol.getType();
//                    final AbstractMutableRuleParticipant arp = (AbstractMutableRuleParticipant) getEntity(path);
//                    if (null != arp) {
//                        arp.addRule(dcPath, rule.getName());
//                    }//if
//                }//for
//            }//if
//        }//while
//    }


    public void registerEvent(DefaultMutableEvent event) {
        event.clearSubEventPaths();
        String eventPath = event.getFullPath();
        String registerSuperPath = event.getSuperEventPath();

        /** Check among Events */
        Collection events = getEvents();
        Iterator it = events.iterator();
        while (it.hasNext()) {
            DefaultMutableEvent de = (DefaultMutableEvent) it.next();
            String dePath = de.getFullPath();
            String superPath = de.getSuperEventPath();
            if (eventPath.equals(superPath)) {
                String subEventPath = de.getFullPath();
                event.addSubEvent(subEventPath);
            }

            /* We are a subevent of de */
            else if (dePath.equals(registerSuperPath)) {
                de.addSubEvent(eventPath);
            }
        }

//        registerRuleParticipant(event);
    }


//    protected void registerRuleParticipant(AbstractMutableRuleParticipant arp) {
//        if (arp == null) {
//            return;
//        }
//        arp.clearReferringRules();
//
//        String path = arp.getFullPath();
//
//        Collection concepts = getConcepts();
//        Iterator conceptIt = concepts.iterator();
//        while (conceptIt.hasNext()) {
//            DefaultMutableConcept dc = (DefaultMutableConcept) conceptIt.next();
//            addRulesToParticipant(dc, arp);
//        }
//
//        Collection events = getEvents();
//        Iterator eventIt = events.iterator();
//        while (eventIt.hasNext()) {
//            DefaultMutableEvent de = (DefaultMutableEvent) eventIt.next();
//            addRulesToParticipant(de, arp);
//        }
//
//        Collection ruleSets = getRuleSets();
//        Iterator ruleSetIt = ruleSets.iterator();
//        while (ruleSetIt.hasNext()) {
//            DefaultMutableRuleSet drs = (DefaultMutableRuleSet) ruleSetIt.next();
//            addRulesToParticipant(drs, arp);
//        }
//
//        Collection ruleFns = getRuleFunctions();
//        Iterator ruleFnIt = ruleFns.iterator();
//        while (ruleFnIt.hasNext()) {
//            DefaultMutableRuleFunction dfr = (DefaultMutableRuleFunction) ruleFnIt.next();
//            for (Iterator it = dfr.getArguments().values().iterator(); it.hasNext();) {
//                final Symbol symbol = (Symbol) it.next();
//                if (path.equals(symbol.getType())) {
//                    arp.addRule(path, path);
//                    continue; // don't need to add again for return type
//                }
//            }
//
//            String returnType = dfr.getReturnType();
//            if (path.equals(returnType)) {
//                arp.addRule(path, path);
//            }
//        }
//    }


//    private void addRulesToParticipant(RuleSet rs, AbstractMutableRuleParticipant arp) {
//        String path = arp.getFullPath();
//
//        String rsPath = rs.getFullPath();
//        Collection rules = rs.getRules();
//        Iterator ruleIt = rules.iterator();
//        while (ruleIt.hasNext()) {
//            DefaultMutableRule rule = (DefaultMutableRule) ruleIt.next();
//            final Symbols decls = rule.getDeclarations();
//            for (Iterator it = decls.values().iterator(); it.hasNext();) {
//                final Symbol symbol = (Symbol) it.next();
//                if (symbol.getType().equals(path)) {
//                    arp.addRule(rsPath, rule.getName());
//                    break;
//                }//if
//            }//for
//        }//while
//    }


    public MutableConcept createConcept(MutableFolder pack, String name, Concept superConcept, boolean autoNameOnConflict) throws ModelException {
        if (pack == null) {
            throw new ModelException("bad.createConcept");
        }

        /** Can't create a Concept at this Folder level since one with the same name already exists */
        String superPath = (superConcept != null) ? superConcept.getFullPath() : "";
        return createConcept(pack.toString(), name, superPath, autoNameOnConflict);
    }


    public ConceptView getConceptView(String path) {
        Entity e = getEntity(path);
        return (e instanceof ConceptView) ? ((ConceptView) e) : null;
    }


    public MutableConceptView createConceptView(String folder, String name, boolean autoNameOnConflict) throws ModelException {
        return (MutableConceptView) createEntityView(folder, name, autoNameOnConflict, CONCEPT_VIEW_FLAG);
    }


    public void setPrimaryDomain(MutableInstance instance, MutableFolder domain) throws ModelException {
        DefaultMutableInstance di = (DefaultMutableInstance) instance;
        DefaultMutableFolder df = (DefaultMutableFolder) domain;

        String key = df.toString() + di.m_name;
        Entity entity = df.getEntity(instance.getName(), false);

        /** Make sure df doesn't already contain a different Instance */
        if (entity != null && !instance.equals(entity)) {
            throw new ModelException("bad.setPrimaryDomain");
        }

        /** Make sure df isn't a secondary Domain of di */
        if (di.getSecondaryDomains().contains(df.getFullPath())) {
            throw new ModelException("bad.setPrimaryDomain");
        }

        /** Register the Instance under the proper Domain */
        DefaultMutableFolder oldDomain = (DefaultMutableFolder) di.getFolder();
        String oldKey = oldDomain.toString() + di.m_name;

        oldDomain.m_entities.remove(di.getName());
        df.m_entities.put(di.getName(), di);

        /** Rehash the Instance with the appropriate key */
        m_entities.remove(oldKey);
        m_entities.put(key, di);

        di.m_folder = df.getFullPath();
    }


    public void addSecondaryDomain(MutableInstance instance, String domainPath) throws ModelException {
        if (instance == null || ModelUtils.IsEmptyString(domainPath)) {
            return;
        }

        DefaultMutableInstance di = (DefaultMutableInstance) instance;
        DefaultMutableFolder df = (DefaultMutableFolder) getFolder(domainPath);

        /** Make sure the Instance isn't already associated with the Domain */
        if (domainPath.equals(di.m_folder) || di.getSecondaryDomains().contains(domainPath)) {
            return;
        }

        /** Register with the secondary Domain */
        di.getSecondaryDomains().add(domainPath);
        df.m_entities.put(di.getName(), di);

        di.notifyListeners();
    }


    public void removeSecondaryDomain(MutableInstance instance, String domain) {
        if (instance == null || domain == null) {
            return;
        }

        DefaultMutableInstance di = (DefaultMutableInstance) instance;
        DefaultMutableFolder df = (DefaultMutableFolder) getFolder(domain);

        /** Unregister ths Instance from the Domain */
        di.getSecondaryDomains().remove(domain);
        df.m_entities.remove(di.getName());

        di.notifyListeners();
    }


    public MutableInstance createInstance(String domainPath, String conceptPath, String name, boolean autoNameOnConflict) throws ModelException {
        DefaultMutableFolder df = (DefaultMutableFolder) createFolder(domainPath, false);
        DefaultMutableConcept dc = (DefaultMutableConcept) getConcept(conceptPath);

        name = getNameForEntity(df, name, autoNameOnConflict);

        DefaultMutableInstance di = new DefaultMutableInstance(this, name, df, conceptPath);

        /** Register the Instance with the ontology */
        String key = df.getFullPath() + name;
        m_entities.put(key, di);

        /** Register the Instance with its Concept */
        if (dc != null) {
            dc.getInstances().add(di.getFullPath());
        }

        /** Register the Instance with its Domain */
        df.m_entities.put(name, di);

        /** Create PropertyInstances appropriately */
        if (dc != null) {
            Iterator it = dc.getPropertyDefinitions(false).iterator();
            while (it.hasNext()) {
                DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) it.next();
                if (!dpd.isArray()) {
                    di.createPropertyInstance(dpd.getName());
                }
            }
        }

        return di;
    }


    public MutableInstance createInstance(MutableFolder domain, Concept concept, String name, boolean autoNameOnConflict) throws ModelException {
        if (domain == null || concept == null || name == null || name.length() == 0) {
            throw new ModelException("bad.createInstance");
        }

        return createInstance(domain.toString(), concept.getFullPath(), name, autoNameOnConflict);
    }


    public void setConcept(MutableInstance instance, MutableConcept concept) throws ModelException {
        // TODO - remove (no usage)
        if ((null != instance) && (null != concept)) {
            instance.setConcept(concept);
        }
    }


    public void setValue(MutablePropertyInstance instance, String value) throws ModelException {
        // TODO - remove (no usage)
        if (null != instance) {
            instance.setValue(value);
        }
    }


    public InstanceView getInstanceView(String path) {
        Entity e = getEntity(path);
        return (e instanceof InstanceView) ? ((InstanceView) e) : null;
    }


    public MutableInstanceView createInstanceView(String folder, String name, boolean autoNameOnConflict) throws ModelException {
        return (MutableInstanceView) createEntityView(folder, name, autoNameOnConflict, INSTANCE_VIEW_FLAG);
    }


    public MutableRuleSet createRuleSet(String folderPath, String name, boolean renameOnConflict) throws ModelException {
        final MutableFolder folder = (MutableFolder) this.createFolder(folderPath, false);
        return createRuleSet(folder, name, renameOnConflict);
    }


    public MutableRuleSet createRuleSet(MutableFolder folder, String name, boolean renameOnConflict) throws ModelException {
        if (folder == null) {
            throw new ModelException("bad.createRuleSet");
        }

        DefaultMutableFolder defFolder = (DefaultMutableFolder) folder;

        /** Rename if necessary, or throw an Exception */
        name = getNameForEntity(defFolder, name, renameOnConflict);

        DefaultMutableRuleSet drs = new DefaultMutableRuleSet(this, defFolder, name);

        /** Register the RuleSet with its Folder, and with the Ontology */
        defFolder.m_entities.put(name, drs);
        m_entities.put(drs.getFullPath(), drs);
        notifyEntityAdded(drs);
        return drs;
    }


    public MutableEntity createStandaloneRule(
            String folderPath,
            String name,
            boolean renameOnConflict)
            throws ModelException {
        final MutableFolder folder = this.createFolder(folderPath, false);
        return createStandaloneRule(folder, name, renameOnConflict);
    }


    public MutableStandaloneRule createStandaloneRule(
            MutableFolder folder,
            String name,
            boolean renameOnConflict)
            throws ModelException {

        if (folder == null) {
            throw new ModelException("bad.createStandaloneRule");
        }
        final DefaultMutableFolder defFolder = (DefaultMutableFolder) folder;
        name = this.getNameForEntity(defFolder, name, renameOnConflict);

        final MutableStandaloneRule msr = new DefaultMutableStandaloneRule(this, defFolder, name);

        // Registers with the Folder and the Ontology.
        defFolder.m_entities.put(name, msr);
        this.m_entities.put(msr.getFullPath(), msr);
        this.notifyEntityAdded(msr);
        return msr;
    }


    public void setSuperConcept(MutableConcept child, String parentPath) throws ModelException {
        // TODO - remove (no usage)
        if (null != child) {
            child.setSuperConcept(parentPath);
        }
    }


    public void deletePropertyDefinition(MutablePropertyDefinition def) {
        if (def != null) {
            final MutableConcept concept = (MutableConcept) def.getOwner();
            concept.deletePropertyDefinition(def.getName());
        }
    }


    public void deleteInstance(MutableInstance instance) {
        // TODO - remove (no usage)
        if (null != instance) {
            instance.delete();
        }
    }


    public void deletePropertyInstance(MutablePropertyInstance propertyInstance) {
        if (propertyInstance == null) {
            return;
        }

        final DefaultMutablePropertyInstance dpi = (DefaultMutablePropertyInstance) propertyInstance;
        final MutableInstance instance = (MutableInstance) dpi.getInstance();

        /** Remove the PropertyInstance from its set */
        String name = dpi.getPropertyDefinitionName();
        Collection propertySet = (ArrayList) instance.getPropertyInstances(name);
        if (propertySet == null) {
            return;
        }
        propertySet.remove(dpi);

        /** Remove the PropertyInstance from its definition */
        if (propertySet.size() == 0) {
            deleteInstanceFromDefinition(dpi);
        }

        instance.notifyListeners();
    }


    public void deletePropertyInstance(MutableInstance instance, String name, int index) {
        if (instance == null || name == null || name.length() == 0) {
            return;
        }

        DefaultMutableInstance di = (DefaultMutableInstance) instance;
        final List propertySet = di.getPropertyInstances(name);
        if (propertySet == null) {
            return;
        }
        MutablePropertyInstance pi = (MutablePropertyInstance) propertySet.remove(index);

        // Slightly inefficient, big deal
        deletePropertyInstance(pi);
        di.notifyListeners();
    }


    public void deletePropertyInstances(MutablePropertyDefinition pd) {
        if (pd == null) {
            return;
        }

        /** Can't be null */
        DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) pd;
        DefaultMutableConcept dc = (DefaultMutableConcept) dpd.getOwner();

        /** Delete all instances of this property on all instances of the owning concept */
        Iterator instancesIt = dc.getAllInstancePaths().iterator();
        while (instancesIt.hasNext()) {
            DefaultMutableInstance di = (DefaultMutableInstance) instancesIt.next();

            /** Remove all PropertyInstances from this set */
            Collection propertyInstances = di.getPropertyInstances(dpd);
            if (propertyInstances == null) {
                continue;
            }

            Iterator propertyInstancesIt = propertyInstances.iterator();
            while (propertyInstancesIt.hasNext()) {
                DefaultMutablePropertyInstance pi = (DefaultMutablePropertyInstance) propertyInstancesIt.next();

                /** Make sure it's from the right PropertyDefinition */
                if (dpd.equals(pi.getPropertyDefinition())) {
                    propertyInstancesIt.remove();
                }
            }

            /** Notify the Instance */
            di.notifyListeners();
        }
        dpd.getInstances().clear();
        dc.deletePropertyDefinition(dpd.getName());

        /** Notify the Concept */
        dc.notifyListeners();
        dc.notifyOntologyOnChange();
    }


    public Folder getRootFolder() {
        return m_rootFolder;
    }


    public Entity getEntity(String pack, String name) {
        if (pack == null || name == null) {
            return null;
        }

        String key = pack + ((pack.charAt(pack.length() - 1) == DefaultMutableFolder.FOLDER_SEPARATOR_CHAR) ? name : DefaultMutableFolder.FOLDER_SEPARATOR_CHAR + name);

        Object o = m_entities.get(key);

        if (o instanceof Entity) {
            return (Entity) o;
        } else {
            return null;
        }
    }


    public Collection getConcepts() {
        ArrayList concepts = new ArrayList();
        Iterator it = m_entities.values().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Concept) {
                concepts.add(o);
            }
        }

        return concepts;
    }


    public Collection getPropertyDefinitions() {
        ArrayList defs = new ArrayList();
        Iterator conceptIt = getConcepts().iterator();
        while (conceptIt.hasNext()) {
            DefaultMutableConcept dc = (DefaultMutableConcept) conceptIt.next();
            defs.addAll(dc.getLocalPropertyDefinitions());
        }

        return defs;
    }


    public Collection getInstances() {
        ArrayList instances = new ArrayList();
        Iterator it = m_entities.values().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Instance) {
                instances.add(o);
            }
        }

        return instances;
    }


    public Collection getPropertyInstances() {
        final List props = new ArrayList();
        for (Iterator instancesIt = m_entities.values().iterator(); instancesIt.hasNext();) {
            final Object o = instancesIt.next();
            if (o instanceof Instance) {
                props.addAll(((Instance) o).getAllPropertyInstances());
            }
        }
        return props;
    }


    public Collection getRules() {
        ArrayList rules = new ArrayList();

        Iterator ruleSetsIt = getRuleSets().iterator();
        while (ruleSetsIt.hasNext()) {
            RuleSet ruleSet = (RuleSet) ruleSetsIt.next();
            Collection ruleSetRules = ruleSet.getRules();

            Iterator rulesIt = ruleSetRules.iterator();
            while (rulesIt.hasNext()) {
                Rule rule = (Rule) rulesIt.next();
                rules.add(rule);
            }
        }

        return rules;
    }


    @Override
    public Collection getRuleTemplates() {
        final List ruleTemplates = new ArrayList();
        for (final Object o : m_entities.values()) {
            if (o instanceof RuleTemplate) {
                ruleTemplates.add(o);
            }
        }
        return ruleTemplates;
    }



    public Collection getViews() {
        ArrayList views = new ArrayList();
        Iterator it = m_entities.values().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof EntityView) {
                views.add(o);
            }
        }

        return views;
    }


    public Collection getConceptViews() {
        ArrayList cViews = new ArrayList();
        Iterator it = m_entities.values().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof ConceptView) {
                cViews.add(o);
            }
        }

        return cViews;
    }


    public Collection getEvents() {
        ArrayList events = new ArrayList();
        Iterator it = m_entities.values().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof com.tibco.cep.designtime.model.event.Event) {
                events.add(o);
            }
        }

        return events;
    }


//    public JDBCChannel getJDBCChannel(String channelPath) {
//        Iterator it = m_entities.values().iterator();
//        while (it.hasNext()) {
//            Object o = it.next();
//            if (o instanceof JDBCChannel) {
//                if (((JDBCChannel) o).getFullPath().equals(channelPath)) {
//                    return (JDBCChannel) o;
//                }
//            }
//        }
//        return null;
//    }


    public Collection getChannels() {
        ArrayList channels = new ArrayList();
        Iterator it = m_entities.values().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Channel) {
                channels.add(o);
            }
        }

        return channels;
    }


//    public Collection getJDBCChannels() {
//        ArrayList channels = new ArrayList();
//        Iterator it = m_entities.values().iterator();
//        while (it.hasNext()) {
//            Object o = it.next();
//            if (o instanceof JDBCChannel) {
//                channels.add(o);
//            }
//        }
//        return channels;
//    }


    public Collection getStates() {
        ArrayList states = new ArrayList();
        Iterator it = m_entities.values().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof State) {
                states.add(o);
            }
        }
        return states;
    }


    public Collection getRuleSets() {
        ArrayList ruleSets = new ArrayList();
        Iterator it = m_entities.values().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof DefaultMutableRuleSet) {
                ruleSets.add(o);
            }
        }

        return ruleSets;
    }


    public Collection getStandaloneRules() {
        final List<Object> list = new LinkedList<Object>();
        for (Object o : this.m_entities.values()) {
            if (o instanceof StandaloneRule) {
                list.add(o);
            }
        }
        return list;
    }


    public Collection getStandaloneStateMachines() {
        final List<Object> list = new LinkedList<Object>();
        for (Object o : this.m_entities.values()) {
            if (o instanceof StandaloneStateMachine) {
                list.add(o);
            }
        }
        return list;
    }


    public void clear() {
        this.m_rootFolder.m_entities.clear();
        this.m_rootFolder.m_children.clear();
        this.m_entities.clear();
    }


    public void serializeEntity(Entity entity, OutputStream out) throws IOException {
        serialize(entity, out);
    }


    public static void serialize(Entity entity, OutputStream os) throws IOException {
        final XiSerializable ae = (XiSerializable) entity;
        XiSerializer.serialize(ae.toXiNode(FACTORY), os, "UTF-8", true);
    }


    public static Entity deserializeEntity(InputStream in) {
        try {
            in.reset();
            InputSource src = new InputSource(in);
            return deserializeEntity(src);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @param in InputSource. This could be a valid InputSource coming from an inputStream or from
     *           EntityInputSource. @see EntityInputSource.
     * @return Entity
     */
    public static Entity deserializeEntity(InputSource in) {
        Entity entity = null;
        try {
            //Check to See if it is an EntityInputSource
            if (in instanceof EntityInputSource) {
                return ((EntityInputSource) in).getEntity();
            }

            XiParser parser = XiParserFactory.newInstance();
            if ((in.getByteStream() != null) && (in.getByteStream().available() == -1)) {
                in.getByteStream().reset();
            }
            XiNode documentNode = parser.parse(in);
            XiNode root = XiChild.getFirstChild(documentNode);
            root.getParentNode().removeChild(root);
            XiNodeUtilities.cleanTextNodes(root);

            entity = createEntityFromNode(root);

        } catch (Exception e) {
            int i=0;//todo remove this
            e.printStackTrace();
        }

        return entity;
    }


    public Entity getEntity(String path, boolean bIncludeAliases) {
        Entity e = (Entity) m_entities.get(path);

        if (e == null && bIncludeAliases) {
            e = m_aliases.get(path);
        }

        return e;
    }

    public Entity getEntity(String path) {
        Entity e = (Entity) m_entities.get(path);
        return e;
    }

    public Entity getEntity(ExpandedName name) {
        String fullPath = name.getNamespaceURI().substring(RDFTnsFlavor.BE_NAMESPACE.length());
        return getEntity(fullPath);
    }

    public Concept getConcept(String path) {
        Entity e = getEntity(path);
        return (e instanceof Concept) ? ((Concept) e) : null;
    }


    public Instance getInstance(String path) {
        Entity e = getEntity(path);
        return (e instanceof Instance) ? ((Instance) e) : null;
    }


    public RuleSet getRuleSet(String path) {
        Entity e = getEntity(path);
        return (e instanceof RuleSet) ? ((RuleSet) e) : null;
    }


    public StandaloneRule getStandaloneRule(String path) {
        final Entity e = this.getEntity(path);
        return (StandaloneRule) ((e instanceof StandaloneRule) ? e : null);
    }


    public Calendar getCalendar(String path) {
        Entity e = getEntity(path);
        return (e instanceof com.tibco.cep.designtime.model.service.calendar.Calendar) ? ((Calendar) e) : null;
    }


    public RuleFunction getRuleFunction(String path) {
        Entity e = getEntity(path);
        return (e instanceof RuleFunction) ? ((RuleFunction) e) : null;
    }


    public MutableEvent createTimeEvent(MutableFolder folder, String name, long specifiedTimeInSeconds, String intervalInSeconds, int schedule, boolean renameOnConflict) throws ModelException {
        return createEvent(folder, name, Event.TIME_EVENT, "-1", Event.SECONDS_UNITS, specifiedTimeInSeconds, intervalInSeconds, schedule, renameOnConflict);
    }


    public MutableEvent createTimeEvent(String fullPath, String name, long specifiedTimeInSeconds, String intervalInSeconds, int schedule, boolean renameOnConflict) throws ModelException {
        final MutableFolder folder = (MutableFolder) createFolder(fullPath, false);
        return createEvent(folder, name, com.tibco.cep.designtime.model.event.Event.TIME_EVENT, "-1", com.tibco.cep.designtime.model.event.Event.SECONDS_UNITS, specifiedTimeInSeconds, intervalInSeconds, schedule, renameOnConflict);
    }


    protected DefaultMutableEvent createEvent(Folder folder, String name, int typeFlag, String ttl, int ttlUnits, long specifiedTimeInSeconds, String interval, int schedule, boolean renameOnConflict) throws ModelException {
        if (folder == null) {
            throw new ModelException("bad.createEvent");
        }

        DefaultMutableFolder defFolder = (DefaultMutableFolder) folder;

        /** Rename if necessary, or throw an Exception */
        name = getNameForEntity(defFolder, name, renameOnConflict);

        DefaultMutableEvent de = null;
        if (typeFlag == com.tibco.cep.designtime.model.event.Event.SIMPLE_EVENT) {
            de = new DefaultMutableEvent(this, defFolder, name, ttl, ttlUnits, null, null, true);
        } else {
            de = new DefaultMutableEvent(this, defFolder, name, specifiedTimeInSeconds, interval, schedule, null, null,
                    true);
        }

        /** Register the Rule with its Folder, and with the Ontology */
        defFolder.m_entities.put(name, de);
        m_entities.put(de.getFullPath(), de);
        notifyEntityAdded(de);
        registerEvent(de);

        return de;
    }


    public MutableEvent createEvent(MutableFolder folder, String name, String ttl, int ttlUnits, boolean renameOnConflict) throws ModelException {
        return createEvent(folder, name, Event.SIMPLE_EVENT, ttl, ttlUnits, 0, "0", com.tibco.cep.designtime.model.event.Event.RULE_BASED, renameOnConflict);
    }


    public com.tibco.cep.designtime.model.event.mutable.MutableEvent createEvent(String folderPath, String name, String ttl, int ttlUnits, boolean renameOnConflict) throws ModelException {
        final MutableFolder folder = (MutableFolder) this.createFolder(folderPath, false);
        return createEvent(folder, name, ttl, ttlUnits, renameOnConflict);
    }


    public void setEventFolder(MutableEvent event, MutableFolder folder) throws ModelException {
        setEntityFolder(event, folder);
    }


    public Event getEvent(String path) {
        final Entity e = getEntity(path);
        return (e instanceof Event) ? ((Event) e) : null;
    }


//    public MutableJDBCChannel createJDBCChannel(MutableFolder folder, String name, boolean renameOnConflict) throws ModelException {
//        if (folder == null || name == null || name.length() == 0) {
//            throw new ModelException("bad.createChannel");
//        }
//
//        DefaultMutableJDBCChannel dch = null;
//        DefaultMutableFolder defFolder = (DefaultMutableFolder) folder;
//
//        /** Rename if necessary, or throw an Exception */
//        name = getNameForEntity(defFolder, name, renameOnConflict);
//
//        dch = new DefaultMutableJDBCChannel(this, defFolder, name);
//
//        /** Register the MutableChannel with its Folder, and with the Ontology */
//        defFolder.m_entities.put(name, dch);
//        m_entities.put(dch.getFullPath(), dch);
//
//        notifyEntityAdded(dch);
//
//        return dch;
//    }
//
//
//    public MutableJDBCChannel createJDBCChannel(String folderPath, String name, boolean renameOnConflict) throws ModelException {
//        final MutableFolder folder = (MutableFolder) createFolder(folderPath, false);
//        return createJDBCChannel(folder, name, renameOnConflict);
//    }


    public MutableChannel createChannel(MutableFolder folder, String name, boolean renameOnConflict) throws ModelException {
        if (folder == null || name == null || name.length() == 0) {
            throw new ModelException("bad.createChannel");
        }

        DefaultMutableChannel dch = null;
        DefaultMutableFolder defFolder = (DefaultMutableFolder) folder;

        /** Rename if necessary, or throw an Exception */
        name = getNameForEntity(defFolder, name, renameOnConflict);

        dch = new DefaultMutableChannel(this, defFolder, name);

        /** Register the MutableChannel with its Folder, and with the Ontology */
        defFolder.m_entities.put(name, dch);
        m_entities.put(dch.getFullPath(), dch);

        notifyEntityAdded(dch);

        return dch;
    }


    public MutableChannel createChannel(String folderPath, String name, boolean renameOnConflict) throws ModelException {
        final MutableFolder folder = (MutableFolder) createFolder(folderPath, false);
        return createChannel(folder, name, renameOnConflict);
    }


    public MutableRuleFunction createRuleFunction(String folderPath, String name, boolean renameOnConflict) throws ModelException {
        final MutableFolder folder = (MutableFolder) createFolder(folderPath, false);
        return createRuleFunction(folder, name, renameOnConflict);
    }


    public MutableRuleFunction createRuleFunction(MutableFolder folder, String name, boolean renameOnConflict) throws ModelException {
        if (folder == null || name == null || name.length() == 0) {
            throw new ModelException("bad.createRuleFunction");
        }

        DefaultMutableRuleFunction drf = null;
        DefaultMutableFolder defFolder = (DefaultMutableFolder) folder;

        /** Rename if necessary, or throw an Exception */
        name = getNameForEntity(defFolder, name, renameOnConflict);

        drf = new DefaultMutableRuleFunction(this, defFolder, name);

        /** Register the RuleFunction with its Folder, and with the Ontology */
        defFolder.m_entities.put(name, drf);
        m_entities.put(drf.getFullPath(), drf);

        notifyEntityAdded(drf);

        return drf;
    }


    public Collection getRuleFunctions() {
        ArrayList ruleFns = new ArrayList();
        Iterator it = m_entities.values().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof RuleFunction) {
                ruleFns.add(o);
            }
        }

        return ruleFns;
    }




    public Folder getFolder(String fullPath) {
        String[] subFolders = parseFolderName(fullPath);

        if (subFolders == null) {
            return null;
        }

        /** Look up each sub folder until one doesn't exist, or we go through every folder */
        DefaultMutableFolder df = m_rootFolder;
        for (int i = 0; i < subFolders.length; i++) {
            df = (DefaultMutableFolder) df.getSubFolder(subFolders[i]);

            /** If a sub folder doesn't exist, then the full folder doesn't exist */
            if (df == null) {
                break;
            }
        }

        return df;
    }


    public void setFolderParent(MutableFolder f, MutableFolder parent) throws ModelException {
        if (f == null) {
            return;
        }

        DefaultMutableFolder df = (DefaultMutableFolder) f;
        DefaultMutableFolder dp = (DefaultMutableFolder) parent;

        if ((df == m_rootFolder) || (parent == null) || f.equals(parent) || f.hasDescendant(parent)) {
            throw new ModelException("bad.set.parent");
        }

        String oldPath = df.getFullPath();

        /** Remove af from its old parent */
        df.m_parent.m_children.remove(f.getName());

        /** Set the new parent and child pointers */
        dp.m_children.put(df.getName(), f);
        df.m_parent = (DefaultMutableFolder) parent;

        String newPath = df.getFullPath();

        df.pathChanged(oldPath, newPath);
        notifyEntityChanged(parent);
        notifyEntityMoved(f, oldPath);
    }


    public MutableFolder createFolder(String fullName, boolean autoNameOnConflict) throws ModelException {
        DefaultMutableFolder df = createFolder(m_rootFolder, fullName, autoNameOnConflict);
        notifyEntityAdded(df);
        return df;
    }


    /**
     * Creates a Folder given a root folder, and the path of the MutableFolder to create.
     *
     * @param root
     * @param pathToCreate
     * @param autoNameOnConflict
     * @return
     * @throws ModelException
     */
    public static DefaultMutableFolder createFolder(DefaultMutableFolder root, String pathToCreate, boolean autoNameOnConflict) throws ModelException {
        if (root == null || ModelUtils.IsEmptyString(pathToCreate)) {
            throw new ModelException("bad.createFolder");
        }
        String[] names = parseFolderName(pathToCreate);
        if (names == null) {
            throw new ModelException("bad.createFolder");
        }

        DefaultMutableFolder df = root;
        for (int i = 0; i < names.length; i++) {
            df = (DefaultMutableFolder) df.createSubFolder(names[i], autoNameOnConflict && (i == (names.length - 1)));
        }

        return df;
    }


    //creates a calendar object
    public MutableCalendar createCalendar(String folderPath, String name, boolean isRecurring) throws ModelException {
        DefaultMutableFolder defFolder = (DefaultMutableFolder) createFolder(folderPath, false);
        DefaultMutableCalendar defCalendar = new DefaultMutableCalendar(this, name, defFolder);
        notifyEntityAdded(defCalendar);
        //Calendar calendar= defCalendar.getCalendar(isRecurring);
        return defCalendar;
    }
    //todo add registration to ontology


    /**
     * Helpers
     */
    public void setEntityFolder(Entity entity, Folder folder) throws ModelException {
        if (entity == null || folder == null) {
            return;
        }

        Entity folderEntity = folder.getEntity(entity.getName(), false);

        /** Return if the Entity is being moved to a location at which it already exists */
        if (entity.equals(folderEntity)) {
            return;
        }

        /** Otherwise, throw an Exception if another Entity of the same name exists at this folder */
        else if ((folderEntity != null)) {
            throw new ModelException("bad.setFolder");
        }

        AbstractMutableEntity ae = (AbstractMutableEntity) entity;
        DefaultMutableFolder df = (DefaultMutableFolder) folder;
        String oldFolder = ae.getFolderPath();      //SS, NP - Changed

        /** Register the Entity with its new folder */
        df.m_entities.put(ae.getName(), ae);

        /** Unregister the Entity from its old package */
        DefaultMutableFolder aeFolder = (DefaultMutableFolder) ae.getFolder();
        aeFolder.m_entities.remove(ae.getName());

        /** Rehash the Entity according to its new location */
        m_entities.remove(ae.m_folder.toString() + ae.m_name);
        m_entities.put(df.toString() + ae.m_name, ae);

        ae.m_folder = df.getFullPath();
        notifyEntityMoved(ae, oldFolder);
        ae.notifyListeners();
    }


    protected static String[] parseFolderName(String fullPath) {
        if (fullPath == null || fullPath.length() == 0 || !(fullPath.charAt(0) == DefaultMutableFolder.FOLDER_SEPARATOR_CHAR))
        {
            return null;
        }

        /** Root */
        if (fullPath.length() == 1) {
            return new String[0];
        }

        /** Remove trailing and leading separators, since split() will return empty Strings before and after them */
        fullPath = fullPath.substring(1, (fullPath.charAt(fullPath.length() - 1) == DefaultMutableFolder.FOLDER_SEPARATOR_CHAR) ? fullPath.length() - 1 : (fullPath.length()));

        /** Find the folder object that corresponds to the specified path name */
        return fullPath.split("[" + DefaultMutableFolder.FOLDER_SEPARATOR_CHAR + "]");
    }


    protected void deleteInstanceFromDefinition(MutablePropertyInstance pi) {
        if (pi == null) {
            return;
        }

        DefaultMutablePropertyInstance dpi = (DefaultMutablePropertyInstance) pi;
        DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) dpi.getPropertyDefinition();

        if (dpd == null) {
            return;
        }

        dpd.getInstances().remove(dpi.getInstance().getFullPath());
    }


    public static void setMostRecentConcept(DefaultMutableConcept mostRecentConceptArg) {
        mostRecentConcept = mostRecentConceptArg;
    }// end setMostRecentConcept


    public static void setMostRecentStateMachine(MutableStateMachine sm) {
        mostRecentStateMachine = sm;
    }


    public static MutableEntity createEntityFromNode(XiNode root) throws ModelException {
        String typeName = root.getName().getLocalName();
        MutableEntity model = null;

        if (typeName.equals("concept")) {
            model = DefaultMutableConcept.createDefaultConceptFromNode(root);
        } else if (typeName.equals("instance")) {
            model = DefaultMutableInstance.createDefaultInstanceFromNode(root);
        } else if (typeName.equals("conceptView")) {
            model = createEntityViewFromNode(root, true);
        } else if (typeName.equals("instanceView")) {
            model = createEntityViewFromNode(root, false);
        } else if (typeName.equals("event")) {
            model = DefaultMutableEvent.createDefaultEventFromNode(root);
        } else if (typeName.equals("channel")) {
            model = DefaultMutableChannel.createDefaultMutableChannelFromNode(root);
        } else if (typeName.equals("standaloneRule")) {
            model = DefaultMutableStandaloneRule.createDefaultStandaloneRuleFromNode(root);
        } else if (typeName.equals("ruleset")) {
            model = DefaultMutableRuleSet.createDefaultRuleSetFromNode(root);
        } else if (typeName.equals("ruleFunction")) {
            model = DefaultMutableRuleFunction.createDefaultRuleFunctionFromNode(root);
        } else if (typeName.equals("rule")) {
            model = DefaultMutableRule.createDefaultRuleFromNode(root, null);
        } else if (typeName.equals("metric")) {
        	model = DefaultMutableConcept.createDefaultConceptFromNode(root);
        	System.out.println("Creating a metric using Concept logic in DefaultMutableOntology");
        } else if (typeName.equals("process")) {
        	model = DefaultMutableProcess.createDefaultProcessFromNode(root);
        }

        /******** StateMachine stuff *******/
        else if (typeName.equals(DefaultMutableStateComposite.PERSISTENCE_NAME)) {
            model = new DefaultMutableStateComposite(null, "NameToBeReplaced", null, mostRecentStateMachine);
        } else if (typeName.equals(DefaultMutableStateEnd.PERSISTENCE_NAME)) {
            model = new DefaultMutableStateEnd(null, "NameToBeReplaced", null, mostRecentStateMachine);
        } else if (typeName.equals(DefaultMutableStateGroupBox.PERSISTENCE_NAME)) {
            model = new DefaultMutableStateGroupBox(null, "NameToBeReplaced", null, mostRecentStateMachine);
        } else if (typeName.equals(DefaultMutableStandaloneStateMachine.PERSISTENCE_NAME)) {
//            model = DefaultMutableStandaloneStateMachine.createDefaultStandaloneStateMachineFromNode(root);
            final String name = root.getAttributeStringValue(DefaultMutableStandaloneStateMachine.NAME_NAME);
            final String folderPath = root.getAttributeStringValue(DefaultMutableStandaloneStateMachine.FOLDER_NAME);
            final DefaultMutableFolder rootFolder = new DefaultMutableFolder(null, null,
                    String.valueOf(Folder.FOLDER_SEPARATOR_CHAR));
            final DefaultMutableFolder folder = DefaultMutableOntology.createFolder(rootFolder, folderPath, false);
            model = new DefaultMutableStandaloneStateMachine(null, name, folder, null, null, null);
            mostRecentStateMachine = (MutableStateMachine) model;
            //model = new DefaultMutableStandaloneStateMachine(null, "NameToBeReplaced", null, null);
        } else if (typeName.equals(DefaultMutableStateMachine.PERSISTENCE_NAME)) {
            model = new DefaultMutableStateMachine(null, "NameToBeReplaced", null, null);
            mostRecentStateMachine = (MutableStateMachine) model;
            // mostRecentConcept is set in the DefaultConcept.createDefaultConceptFromNode method
            mostRecentStateMachine.setOwnerConcept(mostRecentConcept);
        } else if (typeName.equals(DefaultMutableStatePseudo.PERSISTENCE_NAME)) {
            model = new DefaultMutableStatePseudo(null, "NameToBeReplaced", null, mostRecentStateMachine);
        } else if (typeName.equals(DefaultMutableStateRectangle.PERSISTENCE_NAME)) {
            model = new DefaultMutableStateRectangle(null, "NameToBeReplaced", null, mostRecentStateMachine);
        } else if (typeName.equals(DefaultMutableStateRoundedRectangle.PERSISTENCE_NAME)) {
            model = new DefaultMutableStateRoundedRectangle(null, "NameToBeReplaced", null, mostRecentStateMachine);
        } else if (typeName.equals(DefaultMutableStateSimple.PERSISTENCE_NAME)) {
            model = new DefaultMutableStateSimple(null, "NameToBeReplaced", null, mostRecentStateMachine);
        } else if (typeName.equals(DefaultMutableStateStart.PERSISTENCE_NAME)) {
            model = new DefaultMutableStateStart(null, "NameToBeReplaced", null, mostRecentStateMachine);
        } else if (typeName.equals(DefaultMutableStateSubmachine.PERSISTENCE_NAME)) {
            model = new DefaultMutableStateSubmachine(null, "NameToBeReplaced", null, mostRecentStateMachine);
        } else if (typeName.equals(DefaultMutableStateTextLabel.PERSISTENCE_NAME)) {
            model = new DefaultMutableStateTextLabel(null, "NameToBeReplaced", null, mostRecentStateMachine);
        } else if (typeName.equals(DefaultMutableStateTransition.PERSISTENCE_NAME)) {
            model = new DefaultMutableStateTransition(null, "NameToBeReplaced", mostRecentStateMachine);
        } else if (typeName.equals(DefaultMutableStateAnnotationLink.PERSISTENCE_NAME)) {
            model = new DefaultMutableStateAnnotationLink(null, "NameToBeReplaced", mostRecentStateMachine);
        } else if (typeName.equals(DefaultMutableStateVertex.PERSISTENCE_NAME)) {
            model = new DefaultMutableStateVertex(null, "NameToBeReplaced", null, mostRecentStateMachine);
        } else {
//            try {
//                ModuleRegistry registry = ModuleRegistry.getInstance();
//                CEPModule module = registry.getModule(typeName);
//                if (module != null) {
//                    DTFactory dtFactory = module.getDesigntimeFactory();
//                    model = (MutableEntity) dtFactory.createEntity(typeName, root);
//                    model.setTransientProperty("typeName", typeName);
//                }
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
        }

        if (model == null) return null;

        if (model instanceof DefaultMutableStateEntity) {
            ((DefaultMutableStateEntity) model).fromXiNode(root);
        }//e
        // ndif
        /* *****************/

        String bindings = root.getAttributeStringValue(ExpandedName.makeName("bindings"));
        String iconPath = root.getAttributeStringValue(ExpandedName.makeName("icon"));
        String namespace = root.getAttributeStringValue(ExpandedName.makeName("namespace"));

        String lastModified;

        XiNode lastModifiedNode = XiChild.getChild(root, AbstractMutableEntity.LAST_MODIFIED_NAME);
        if (lastModifiedNode == null) {
//            lastModified = XsDateTime.currentDateTime().castAsString();
            lastModified = "";
        } else {
            lastModified = lastModifiedNode.getStringValue();
        }

        /** Model should not be null */
        XiNode hiddenPropsNode = XiChild.getChild(root, AbstractMutableEntity.HIDDEN_PROPERTIES_NAME);
        //createHiddenPropertiesFromNode(hiddenPropsNode, model);
        if (hiddenPropsNode != null) {
            Iterator it = hiddenPropsNode.getChildren();
            while (it.hasNext()) {
                XiNode hiddenPropNode = (XiNode) it.next();
                String key = hiddenPropNode.getAttributeStringValue(AbstractMutableEntity.HIDDEN_PROPERTY_KEY_NAME);
                String value = hiddenPropNode.getAttributeStringValue(AbstractMutableEntity.HIDDEN_PROPERTY_VALUE_NAME);
                model.setHiddenProperty(key, value);
            }

        }

        model.setBindingString((bindings != null) ? bindings : "");
        model.setIconPath(iconPath);
        model.setNamespace(namespace);
        model.setLastModified(lastModified);

        return model;
    }

    private static void createHiddenPropertiesFromNode(XiNode hiddenPropsNode, MutableEntity model) {
        // Model should not be null
        if (hiddenPropsNode != null) {
            for (Iterator it = hiddenPropsNode.getChildren(); it.hasNext(); ) {
                XiNode hiddenPropNode = (XiNode) it.next();
                final ExpandedName nodeName = hiddenPropNode.getName();
                if (AbstractMutableEntity.HIDDEN_PROPERTIES_NAME.equals(nodeName)) {
                    final String key = hiddenPropNode.getAttributeStringValue(AbstractMutableEntity.HIDDEN_PROPERTY_KEY_NAME);
                    //model.setHiddenProperty(key, value);

                } else if (AbstractMutableEntity.HIDDEN_PROPERTY_NAME.equals(nodeName)) {
                  final String key = hiddenPropNode.getAttributeStringValue(AbstractMutableEntity.HIDDEN_PROPERTY_KEY_NAME);
                  final String value = hiddenPropNode.getAttributeStringValue(AbstractMutableEntity.HIDDEN_PROPERTY_VALUE_NAME);
                  model.setHiddenProperty(key, value);
                }
            }

        }
    }

    /* no usage!
    protected static MutableChannel createChannelFromNode(XiNode root) throws ModelException {
        final String folder = root.getAttributeStringValue(ExpandedName.makeName("folder"));
        final String name = root.getAttributeStringValue(ExpandedName.makeName("name"));
        final String guid = root.getAttributeStringValue(ExpandedName.makeName("guid"));
        final String description = root.getAttributeStringValue(ExpandedName.makeName("description"));

        final DefaultMutableFolder rootFolder = new DefaultFolder(null, null, String.valueOf(Folder.FOLDER_SEPARATOR_CHAR));
        final DefaultFolder channelFolder = createFolder(rootFolder, folder, false);

        final DefaultMutableChannel dch = new DefaultMutableChannel(null, channelFolder, name);
        dch.m_guid = guid;
        dch.setDescription(description);

        final String type = XiChild.getString(root, ExpandedName.makeName("type"));
        final String version = XiChild.getString(root, ExpandedName.makeName("version"));
        final String registeredVersion = DefaultMutableChannel.DRIVER_MANAGER.getVersion(type);
        if (!registeredVersion.equals(version)) {
            System.out.println("Warning: Channel version from XiNode ("+version
                    +") does not match registered version ("+registeredVersion+").");
        }//if
        final String configMethod = XiChild.getString(root, ExpandedName.makeName("configMethod"));

        final List destinations = new ArrayList();

        DriverConfig driver = null;

        if (DriverConfig.CONFIG_BY_REFERENCE.equals(configMethod)) {
            final String reference = XiChild.getString(root, ExpandedName.makeName("reference"));
            driver = new  DefaultMutableDriverConfig(dch, type, DriverConfig.CONFIG_BY_REFERENCE, reference, destinations);
            dch.m_driver = driver;

        } else if (DriverConfig.CONFIG_BY_PROPERTIES.equals(configMethod)) {
            final XiNode propertiesNode = XiChild.getChild(root, ExpandedName.makeName("properties"));
            final XiNode instanceNode = XiChild.getChild(propertiesNode, ExpandedName.makeName("instance"));
            final Instance instance = createInstanceFromNode(instanceNode);
            instance.setOntology(DefaultDriverConfig.ONTOLOGY);
            driver = new DefaultMutableDriverConfig(dch, type, DriverConfig.CONFIG_BY_PROPERTIES, instance, destinations);
            dch.m_driver = driver;
        }//else

        final XiNode destinationsNode = XiChild.getChild(root, ExpandedName.makeName("destinations"));
        for (Iterator it = XiChild.getIterator(destinationsNode, ExpandedName.makeName("destination")); it.hasNext(); ) {
            final XiNode destinationNode = (XiNode) it.next();

            final String destinationName = destinationNode.getAttributeStringValue(ExpandedName.makeName("name"));
            final String eventURI = XiChild.getString(destinationNode, ExpandedName.makeName("event"));
            final String sdClass = XiChild.getString(destinationNode, ExpandedName.makeName("serializer"));
            final boolean isInput = XiChild.getBoolean(destinationNode, ExpandedName.makeName("isInput"), false);
            final boolean isOutput = XiChild.getBoolean(destinationNode, ExpandedName.makeName("isOutput"), false);

            final XiNode propsNode = XiChild.getChild(destinationNode, ExpandedName.makeName("properties"));
            final XiNode instanceNode = XiChild.getChild(propsNode, ExpandedName.makeName("instance"));
            final Instance properties = createInstanceFromNode(instanceNode);
            properties.setOntology(DriverConfig.ONTOLOGY);

            final MutableDestination destination = new DefaultMutableDestination(destinationName, driver, isInput, isOutput,
                    properties, eventURI, sdClass);
            driver.addDestination(destination);
        }//for

        return dch;
    }//createChannelFromNode
    */


    protected static MutableEntityView createEntityViewFromNode(XiNode root, boolean creatingConceptView) throws ModelException {
        AbstractMutableEntityView aev = null;
        String name = root.getAttributeStringValue(ExpandedName.makeName("name"));
        String description = root.getAttributeStringValue(ExpandedName.makeName("description"));
        String folder = root.getAttributeStringValue(ExpandedName.makeName("folder"));

        DefaultMutableFolder rootFolder = new DefaultMutableFolder(null, null, String.valueOf(Folder.FOLDER_SEPARATOR_CHAR));
        DefaultMutableFolder viewFolder = createFolder(rootFolder, folder, false);

        if (creatingConceptView) {
            aev = new DefaultMutableConceptView(null, viewFolder, name);
        } else {
            aev = new DefaultMutableMutableInstanceView(null, viewFolder, name);
        }

        aev.setDescription(description);

        /** Create the references and links */
        Iterator it;
        XiNode refsNode = XiChild.getChild(root, ExpandedName.makeName("references"));
        XiNode linksNode = XiChild.getChild(root, ExpandedName.makeName("links"));

        /** This really should never be null */
        if (refsNode != null) {
            it = refsNode.getChildren();
            while (it.hasNext()) {
                XiNode refNode = (XiNode) it.next();
                if (creatingConceptView) {
                    createConceptReferenceFromNode(refNode, (DefaultMutableConceptView) aev);
                } else {
                    createInstanceReferenceFromNode(refNode, (DefaultMutableMutableInstanceView) aev);
                }
            }
        }

        /** This should also never be null */
        if (linksNode != null) {
            it = linksNode.getChildren();
            while (it.hasNext()) {
                XiNode linkNode = (XiNode) it.next();
                if (creatingConceptView) {
                    createConceptLinkFromNode(linkNode, (DefaultMutableConceptView) aev);
                } else {
                    createInstanceLinkFromNode(linkNode, (DefaultMutableMutableInstanceView) aev);
                }
            }
        }

        return aev;
    }


    protected static void createConceptReferenceFromNode(XiNode refNode, DefaultMutableConceptView dcv) {
        String refEntityPath = refNode.getAttributeStringValue(ExpandedName.makeName("entityPath"));
        String refX = refNode.getAttributeStringValue(ExpandedName.makeName("x"));
        String refY = refNode.getAttributeStringValue(ExpandedName.makeName("y"));
        Point refLocation = new Point(Integer.parseInt(refX), Integer.parseInt(refY));

        DefaultMutableConceptReference dcr = new DefaultMutableConceptReference(null, dcv, refEntityPath);
        dcr.setLocation(refLocation);
        dcv.m_references.put(refEntityPath, dcr);
    }


    protected static void createConceptLinkFromNode(XiNode linkNode, DefaultMutableConceptView dcv) throws ModelException {
        String name = linkNode.getAttributeStringValue(ExpandedName.makeName("name"));
        String linkFrom = linkNode.getAttributeStringValue(ExpandedName.makeName("from"));
        String linkTo = linkNode.getAttributeStringValue(ExpandedName.makeName("to"));
        String linkType = linkNode.getAttributeStringValue(ExpandedName.makeName("type"));
        String linkPropertyName = linkNode.getAttributeStringValue(DefaultMutableConceptLink.PROPERTY_NAME);

        /** These references should  not be null since they've just been created */
        DefaultMutableConceptReference fromRef = (DefaultMutableConceptReference) dcv.getReference(linkFrom);
        DefaultMutableConceptReference toRef = (DefaultMutableConceptReference) dcv.getReference(linkTo);

        /** But if they are null, we can't create the link, so just return */
        if (fromRef == null || toRef == null) {
            return;
        }

        DefaultMutableConceptLink dcl = new DefaultMutableConceptLink(null, dcv, fromRef, toRef, linkPropertyName);
        dcl.m_name = name;
        dcl.m_type = Integer.parseInt(linkType);

        if (fromRef.m_links == null) {
            fromRef.m_links = new LinkedHashMap();
        }
        fromRef.m_links.put(dcl.getLabel(), dcl);
    }


    protected static void createInstanceReferenceFromNode(XiNode refNode, DefaultMutableMutableInstanceView div) {
        String refEntityPath = refNode.getAttributeStringValue(ExpandedName.makeName("entityPath"));
        String refX = refNode.getAttributeStringValue(ExpandedName.makeName("x"));
        String refY = refNode.getAttributeStringValue(ExpandedName.makeName("y"));
        Point refLocation = new Point(Integer.parseInt(refX), Integer.parseInt(refY));

        DefaultMutableInstanceReference dcr = new DefaultMutableInstanceReference(null, div, refEntityPath);
        dcr.setLocation(refLocation);
        div.m_references.put(refEntityPath, dcr);
    }


    protected static void createInstanceLinkFromNode(XiNode linkNode, DefaultMutableMutableInstanceView div) throws ModelException {
        String linkFrom = linkNode.getAttributeStringValue(ExpandedName.makeName("from"));
        String linkTo = linkNode.getAttributeStringValue(ExpandedName.makeName("to"));
        String linkType = linkNode.getAttributeStringValue(ExpandedName.makeName("type"));
        String linkPropertyName = linkNode.getAttributeStringValue(ExpandedName.makeName("propertyName"));
        String linkIndex = linkNode.getAttributeStringValue(ExpandedName.makeName("index"));

        /** These references should not be null since they've just been created */
        DefaultMutableInstanceReference fromRef = (DefaultMutableInstanceReference) div.getReference(linkFrom);
        DefaultMutableInstanceReference toRef = (DefaultMutableInstanceReference) div.getReference(linkTo);

        /** But if they are null, we can't create the link, so just return */
        if (fromRef == null || toRef == null) {
            return;
        }

        DefaultMutableInstanceLink dcl = new DefaultMutableInstanceLink(null, div, fromRef, toRef, linkPropertyName, Integer.parseInt(linkIndex));
        dcl.m_type = Integer.parseInt(linkType);

        fromRef.m_links = new LinkedHashMap();
        fromRef.m_links.put(dcl.getLabel(), dcl);
    }


    public String getNameForEntity(DefaultMutableFolder defFolder, String name, boolean renameOnConflict) throws ModelException {
        if (defFolder == null) {
            throw new ModelException("bad.create.Entity");
        }

        AbstractMutableEntity ae = (AbstractMutableEntity) defFolder.m_entities.get(name);

        /** If an entity with the path exists... */
        if (ae != null) {
            /** Rename if appropriate... */
            if (renameOnConflict) {
                EntityNameValidator env = EntityNameValidator.DEFAULT_INSTANCE;
                env.setOntology(this);
                env.setFolder(defFolder);
                env.setFolderIsBeingNamed(false);
                name = UniqueNamer.generateUniqueName(name, env);
            }
            /** Otherwise, report it as an error... */
            else {
                throw new ModelException("bad.create.Entity");
            }
        }

        return name;
    }


    protected AbstractMutableEntityView createEntityView(String folder, String name, boolean autoNameOnConflict, int viewTypeFlag) throws ModelException {
        DefaultMutableFolder defFolder = (DefaultMutableFolder) createFolder(folder, false);
        name = getNameForEntity(defFolder, name, autoNameOnConflict);

        AbstractMutableEntityView aev = null;
        if (viewTypeFlag == CONCEPT_VIEW_FLAG) {
            aev = new DefaultMutableConceptView(this, defFolder, name);
        } else if (viewTypeFlag == INSTANCE_VIEW_FLAG) {
            aev = new DefaultMutableMutableInstanceView(this, defFolder, name);
        }

        /** Register the View */
        m_entities.put(aev.getFullPath(), aev);
        defFolder.m_entities.put(name, aev);

        return aev;
    }


    public void unregisterAbstractEntity(AbstractMutableEntity entity) {
        DefaultMutableFolder df = (DefaultMutableFolder) entity.getFolder();
        if (df == null) {
            return;
        }
        df.removeAbstractEntity(entity);

        String fullPath = entity.getFullPath();
        m_entities.remove(fullPath);
    }


    /**
     * @param entityListener
     */
    public void addEntityChangeListener(EntityChangeListener entityListener) {
        if (!m_entityChangeListeners.contains(entityListener)) {
            m_entityChangeListeners.add(entityListener);
        }
    }


    public void removeEntityChangeListener(EntityChangeListener entityListener) {
        m_entityChangeListeners.remove(entityListener);
    }


    /**
     * @param entity
     */
    public void notifyEntityAdded(MutableEntity entity) {
        for (int i = 0; i < m_entityChangeListeners.size(); i++) {
            ((EntityChangeListener) m_entityChangeListeners.get(i)).entityAdded(entity);
        }
    }


    public void notifyEntityDeleted(MutableEntity entity) {
        for (int i = 0; i < m_entityChangeListeners.size(); i++) {
            ((EntityChangeListener) m_entityChangeListeners.get(i)).entityRemoved(entity);
        }
    }


    public void notifyEntityChanged(MutableEntity entity) {
        for (int i = 0; i < m_entityChangeListeners.size(); i++) {
            ((EntityChangeListener) m_entityChangeListeners.get(i)).entityChanged(entity);
        }
    }


    public void notifyEntityRenamed(MutableEntity entity, String oldName) {
        for (int i = 0; i < m_entityChangeListeners.size(); i++) {
            ((EntityChangeListener) m_entityChangeListeners.get(i)).entityRenamed(entity, oldName);
        }
    }


    public void notifyEntityMoved(MutableEntity entity, String oldFolderPath) {
        for (int i = 0; i < m_entityChangeListeners.size(); i++) {
            ((EntityChangeListener) m_entityChangeListeners.get(i)).entityMoved(entity, oldFolderPath);
        }
    }


    public void addEntity(Entity e) {
        m_entities.put(e.getFullPath(), e);
    }


    public void removeEntity(Entity e) {
        m_entities.remove(e.getFullPath());
    }


    public void removeEntity(String path) {
        m_entities.remove(path);
    }

    public void addAlias(String alias, Entity e) {
        m_aliases.put(alias, e);
    }

    public Entity getAlias(String alias) {
        return m_aliases.get(alias);
    }

    public void removeAlias(String alias) {
        if (alias == null) return;
        m_aliases.remove(alias);
    }

    public void removeAlias(Entity e) {
        String alias = e.getAlias();
        removeAlias(alias);

    }
    
    public String getName() {
    	return m_project.getName();
    }

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getLastModifiedDate()
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getLastPersistedDate()
	 */
	public Date getLastPersistedDate() {
		return lastPersistedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * @param lastPersistedDate the lastPersistedDate to set
	 */
	public void setLastPersistedDate(Date lastPersistedDate) {
		this.lastPersistedDate = lastPersistedDate;
	}
	
    @Override
    public Collection<Entity> getEntities(ElementTypes[] types) {
    	throw new UnsupportedOperationException();
    }
    
}
