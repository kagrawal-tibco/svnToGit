/**
 * User: ishaan
 * Date: Mar 29, 2004
 * Time: 6:45:39 PM
 */
package com.tibco.cep.designtime.model.element.mutable.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelError;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutableConcept;
import com.tibco.cep.designtime.model.element.mutable.MutableConceptView;
import com.tibco.cep.designtime.model.element.mutable.MutableInstance;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyDefinition;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateMachineRuleSet;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStandaloneStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateMachineRuleSet;
import com.tibco.cep.designtime.model.mutable.MutableEntity;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableRuleParticipant;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.RulesetEntry;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleSet;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRule;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRuleSet;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.impl.DefaultComponentFactory;
import com.tibco.xml.schema.impl.DefaultModelGroup;
import com.tibco.xml.schema.impl.DefaultParticle;
import com.tibco.xml.schema.impl.DefaultSchema;
import com.tibco.xml.schema.impl.DefaultType;


public class DefaultMutableConcept extends AbstractMutableRuleParticipant implements MutableConcept, MutableRuleSet {
    public static final ExpandedName CONCEPT_NAME = ExpandedName.makeName("concept");
    public static final ExpandedName PROPERTY_DEFINITIONS_NAME = ExpandedName.makeName("propertyDefinitions");
    public static final ExpandedName SUB_CONCEPTS_NAME = ExpandedName.makeName("subConcepts");
    public static final ExpandedName INSTANCES_NAME = ExpandedName.makeName("instances");
    public static final ExpandedName INSTANCE_NAME = ExpandedName.makeName("instance");
    public static final ExpandedName VIEWS_NAME = ExpandedName.makeName("views");
    public static final ExpandedName VIEW_NAME = ExpandedName.makeName("view");
    public static final ExpandedName REFERRING_PROPS_NAME = ExpandedName.makeName("referringProps");
    public static final ExpandedName REFERRING_CONCEPT_NAME = ExpandedName.makeName("referringConcept");
    public static final ExpandedName REFERRING_CONCEPT_PATH_NAME = ExpandedName.makeName("path");
    public static final ExpandedName REFERRING_PROP_NAME = ExpandedName.makeName("referringProp");
    public static final ExpandedName STATE_MACHINES = ExpandedName.makeName("stateMachines");
    public static final ExpandedName ROOT_STATE_MACHINE_GUID = ExpandedName.makeName("rootStateMachineGUID");
    public static final ExpandedName RULE_SET_FOR_STATE_MACHINES = ExpandedName.makeName("ruleSet");

    public static final String CANT_INHERIT_PROPERTY_CONFLICT = "DefaultMutableConcept.setSuper.inheritPropertyConflict";
    public static final String INHERITANCE_LOOP_KEY = "DefaultMutableConcept.setSuper.inheritanceLoop";
    public static final String NULL_CONCEPT_TYPE_KEY = "DefaultMutableConcept.createPropertyDefinition.nullConceptType";
    public static final String CONCEPT_TYPE_ALREADY_CONTAINED = "DefaultMutableConcept.createPropertyDefinition.conceptTypeAlreadyContained";
    public static final String SELF_CONTAINED_CONCEPT = "DefaultMutableConcept.createPropertyDefinition.selfContainedConcept";
    public static final String CONTAINMENT_CIRCLE = "DefaultMutableConcept.createPropertyDefinition.containmentCircle";

    protected String m_super;
    protected LinkedHashSet m_concepts;
    protected LinkedHashSet m_instances;
    protected LinkedHashMap m_properties;
    protected LinkedHashSet m_views;
    protected LinkedHashMap m_referringPropsMap;
    protected boolean m_isAScorecard;

    protected String m_iconRef = null;
    protected ArrayList m_stateMachines = null;
    protected DefaultMutableStateMachineRuleSet m_ruleSet; //One Ruleset per concept
    protected boolean m_transient=false;
    protected boolean m_isPOJO=false;
    protected String m_pojoImplClass="";
    protected boolean isAutoStartStateMachine=true;


    public DefaultMutableConcept(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name, String superConceptPath) {
        this(ontology, folder, name, superConceptPath, false);
    }


    public DefaultMutableConcept(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name, String superConceptPath, boolean isAScorecard) {
        super(ontology, folder, name);
        m_super = superConceptPath;
        m_concepts = new LinkedHashSet();
        m_instances = new LinkedHashSet();
        m_properties = new LinkedHashMap();
        m_views = new LinkedHashSet();
        m_referringPropsMap = new LinkedHashMap();
        m_isAScorecard = isAScorecard;
        m_ruleSet = new DefaultMutableStateMachineRuleSet(this, this.getFullPath(), ontology, folder, name);
    }


    public Set getInstances() {
        return this.m_instances;
    }


    public LinkedHashSet getViews() {
        return this.m_views;
    }


    public boolean isAScorecard() {
        return m_isAScorecard;
    }


    public void setOntology(MutableOntology ontology) {
        Iterator it = m_properties.values().iterator();
        while (it.hasNext()) {
            DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) it.next();
            dpd.setOntology(ontology);
        }
        super.setOntology(ontology);
        m_ruleSet.setOntology(ontology);
        if(m_stateMachines != null) {
            for (Object sm: new ArrayList(this.m_stateMachines)) {
                ((DefaultMutableStateMachine) sm).setOntology(ontology);
            }
        }
        final String path = this.getFullPath();
        for (Object oSSM: ontology.getStandaloneStateMachines()) {
            final MutableStandaloneStateMachine ssm = (MutableStandaloneStateMachine) oSSM;
            if (path.equals(ssm.getOwnerConceptPath())) {
                this.addStateMachine(ssm);
            }
        }
    }


    public void setName(String name, boolean renameOnConflict) throws ModelException {
        if (name == null || name.trim().length() == 0) {
            BEModelBundle bundle = BEModelBundle.getBundle();
            String msg = bundle.getString(AbstractMutableEntity.EMPTY_NAME_KEY);
            throw new ModelException(msg);
        }
        if (name.equals(m_name)) {
            return;
        }

        String oldPath = getFullPath();
        super.setName(name, renameOnConflict);
        String newPath = getFullPath();

        pathChanged(oldPath, newPath);
    }

    public boolean isTransient() {
        return m_transient;
    }

    /**
     *
     * @param isTransient
     */
    public void setTransient(boolean isTransient) {
        m_transient=isTransient;
    }

    public boolean isAutoStartStateMachine() {
        return isAutoStartStateMachine;
    }

    public void setIsAutoStartStateMachine(boolean isAutoStartStateMachine) {
        this.isAutoStartStateMachine=isAutoStartStateMachine;
    }

    public void pathChanged(String oldPath, String newPath) throws ModelException {
        if (m_ontology == null) {
            return;
        }
        if (ModelUtils.IsEmptyString(oldPath)) {
            return;
        }
        if (newPath == null) {
            newPath = "";
        }

//        /* Since we are also a RuleSet that may also point to ourself, re-key ourself in our MutableRuleSet list */
//        Object selfRules = m_referringRulesMap.remove(oldPath);
//        if (selfRules != null) {
//            m_referringRulesMap.put(newPath, selfRules);
//        }

        /* Need to tell each sub-MutableConcept, Instance, ConceptReference, ConceptView, Rule, PropertyDefinitions, referring Concepts that refers to this Concept that its name has change. */
        Iterator conceptsIt = m_concepts.iterator();
        while (conceptsIt.hasNext()) {
            String conceptPath = (String) conceptsIt.next();
            DefaultMutableConcept dc = (DefaultMutableConcept) m_ontology.getConcept(conceptPath);
            if (dc != null) {
                dc.m_super = newPath;
                dc.notifyListeners();
                dc.notifyOntologyOnChange();
            }
        }

        Iterator instancesIt = m_instances.iterator();
        while (instancesIt.hasNext()) {
            String instancePath = (String) instancesIt.next();
            DefaultMutableInstance di = (DefaultMutableInstance) m_ontology.getInstance(instancePath);
            if (di != null) {
                di.m_conceptPath = newPath;
            }
        }

        Iterator viewsIt = m_views.iterator();
        while (viewsIt.hasNext()) {
            String viewPath = (String) viewsIt.next();
            DefaultMutableConceptView dcv = (DefaultMutableConceptView) m_ontology.getConceptView(viewPath);
            if (dcv != null) {
                // ref should not be null
                final DefaultMutableConceptReference ref = (DefaultMutableConceptReference) dcv.getReference(oldPath);
                if (null != ref) {
                    dcv.removeReference(oldPath);
                    ref.setEntityPath(newPath);
                    dcv.addEntityReference(ref);
                }
            }
        }

        /* Notify the Rules in which we participate */
        super.pathChanged(oldPath, newPath);

        /* Tell Concepts that we refer to that our path has changed */
        Iterator propsIt = m_properties.values().iterator();
        while (propsIt.hasNext()) {
            DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) propsIt.next();

            int type = dpd.getType();
            if (type != PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE && type != PropertyDefinition.PROPERTY_TYPE_CONCEPT)
            {
                continue;
            }

            String oldDCTypePath = dpd.getConceptTypePath();

            DefaultMutableConcept dcType;

            /* Self-referential PropertyDefinition */
            if (oldPath.equals(oldDCTypePath)) {
                dcType = this;
            }

            /* Not self-referential, safe to use getConceptType() */
            else {
                dcType = (DefaultMutableConcept) dpd.getConceptType();
            }

            if (dcType == null) {
                continue;
            }

            dcType.referenceChanged(oldPath, newPath);
        }

        /* Re-register referring PropertyDefinitions */
        Set keySet = m_referringPropsMap.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            String conceptPath = (String) it.next();
            Collection names = (Collection) m_referringPropsMap.get(conceptPath);

            DefaultMutableConcept dc = null;

            /* Special case for self-referencing PropertyDefinitions */
            if (conceptPath.equals(oldPath)) {
                conceptPath = newPath;
                referenceChanged(oldPath, newPath);
                dc = this;
            } else {
                dc = (DefaultMutableConcept) m_ontology.getConcept(conceptPath);
            }

            if (dc == null) {
                continue;
            }

            /* Make all referring PropertyDefinitions reflect the new path */
            Iterator nameIt = names.iterator();
            while (nameIt.hasNext()) {
                String name = (String) nameIt.next();
                DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) dc.m_properties.get(name);
                if (dpd == null) {
                    System.err.println("Referenced property " + name + " on " + dc.getFullPath() + " could not be found");
                } else {
                    dpd.m_conceptType = newPath;
                }
            }

            if (dc != this) {
                dc.notifyListeners();
                dc.notifyOntologyOnChange();
            }
        }

        m_ruleSet.pathChanged(oldPath, newPath);
        m_ontology.registerConcept(this);
        notifyListeners();
    }


    public void setFolder(MutableFolder pack) throws ModelException {
        String oldPath = getFullPath();
        m_ontology.setEntityFolder(this, pack);
        String newPath = getFullPath();
        pathChanged(oldPath, newPath);
        notifyListeners();
    }


    public boolean contains(Concept concept) {
        if (concept == null) {
            return false;
        }

        Iterator localPropsIt = m_properties.values().iterator();
        while (localPropsIt.hasNext()) {
            DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) localPropsIt.next();
            if (!(dpd.getType() == RDFTypes.CONCEPT_TYPEID)) {
                continue;
            }

            Concept propConcept = dpd.getConceptType();
            if (propConcept == null) {
                continue;
            }

            if (concept.equals(propConcept)) {
                return true;
            } else if (propConcept.contains(concept)) {
                return true;
            }
        }

        return false;
    }


    public boolean isContained() {
        return (getParentPropertyDefinition() != null);
    }


    public Concept getParentConcept() {
        PropertyDefinition pd = getParentPropertyDefinition();
        if (pd != null) {
            return pd.getOwner();
        }

        return null;
    }


    public PropertyDefinition getParentPropertyDefinition() {
        if (m_ontology == null) {
            return null;
        }

        Set concepts = m_referringPropsMap.keySet();
        Iterator conceptIt = concepts.iterator();
        while (conceptIt.hasNext()) {
            String path = (String) conceptIt.next();
            Concept c = m_ontology.getConcept(path);
            if (c == null) {
                System.err.println("Referring Concept missing: " + path);
                continue;
            }

            Collection propNames = (Collection) m_referringPropsMap.get(path);
            Iterator propNamesIt = propNames.iterator();
            while (propNamesIt.hasNext()) {
                String propName = (String) propNamesIt.next();
                PropertyDefinition pd = c.getPropertyDefinition(propName, true);
                if (pd == null) {
                    System.err.println("Referring Prop missing: " + path + ":" + propName);
                    continue;
                }

                if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {
                    if (pd.getConceptType().getFullPath().equals(getFullPath())) {
                        return pd;
                    }
                }
            }
        }

        return null;
    }


    public Concept getSuperConcept() {
        if (m_ontology == null) {
            return null;
        }
        return m_ontology.getConcept(m_super);
    }


    public String getSuperConceptPath() {
        return m_super;
    }


    public void setSuperConcept(String parentPath) throws ModelException {
        if (null == m_ontology) {
            return;
        }

        final DefaultMutableConcept dp = (DefaultMutableConcept) m_ontology.getConcept(parentPath);
        if (dp == this) {
            return;
        }

        final BEModelBundle bundle = BEModelBundle.getBundle();
        final String fullPath = getFullPath();

        /* If the proposed parent Concept descends from the child, an Exception is thrown */
        if ((null != dp) && dp.isA(this)) {
            throw new ModelException(bundle.formatString(DefaultMutableConcept.INHERITANCE_LOOP_KEY, fullPath, parentPath));
        }

        /* If the proposed parent Concept shares a PropertyDefinition name with the child, an Exception is thrown */
        if (this.hasPropertyDefinitionConflict(dp)) {
            throw new ModelException(bundle.formatString(DefaultMutableConcept.CANT_INHERIT_PROPERTY_CONFLICT, fullPath, parentPath));
        }

        /* Unregisters the child from its old parent */
        final DefaultMutableConcept oldParent = (DefaultMutableConcept) this.getSuperConcept();
        if (null != oldParent) {
            oldParent.removeSubConcept(fullPath);
        }

        /* Registers the child with its new parent */
        m_super = (dp != null) ? dp.getFullPath() : "";
        if (dp != null) {
            dp.addSubConcept(fullPath);
        }

        /* Notifies the old parent */
        if (oldParent != null) {
            oldParent.notifyListeners();
            oldParent.notifyOntologyOnChange();
        }

//        this.setReferringRuleCompilationStatus(-1);

        /* Notifies children */
        this.notifyListeners();
        this.notifyOntologyOnChange();
    }


    public boolean canInheritFrom(Concept concept) {
        if (concept == null) {
            return true;
        }

        if (concept.isA(this)) {
            return false;
        }

        DefaultMutableConcept dc = (DefaultMutableConcept) concept;
        if (hasPropertyDefinitionConflict(dc)) {
            return false;
        }

        /* Check containment */
        Concept owner = dc.getParentConcept();
        if (owner == null) {
            return true;
        }

        /*
        * Don't allow contained Concepts to be derived by their owners,
        * or by ancestors of their owners
        */
        boolean isAncestorOfOwner = false;

        isAncestorOfOwner = owner.isA(this);
        if (isAncestorOfOwner) {
            return false;
        }

        return true;
    }


    public boolean isA(Concept concept) {
        if (concept == null) {
            return false;
        }

        Concept ptr = this;
        while (ptr != null) {
            /* Found a match */
            if (ptr.equals(concept)) {
                return true;
            }

            /* Advance the pointer */
            ptr = ptr.getSuperConcept();
        }
        return false;
    }


    public boolean hasPropertyDefinitionConflict(DefaultMutableConcept dc) {
        if (dc == null) {
            return false;
        }

        /* A Concept can't be in conflict with an ancestor */
        if (isA(dc)) {
            return false;
        }

        /**
         * Check if this Concept, or any of its sub Concepts have a property name
         * in common with the other MutableConcept, or any of that Concept's ancestor.
         */

        /* Get all of the other Concept's props, local and inherited */
        Collection c = dc.getPropertyDefinitions(false);
        Iterator it = c.iterator();
        while (it.hasNext()) {
            DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) it.next();
            String name = dpd.getName();

            /* Check local props */
            PropertyDefinition pd = getPropertyDefinition(name, true);
            if (pd != null) {
                return true;
            }

            /* Check derivers props */
            pd = getSubPropertyDefinition(name);
            if (pd != null) {
                return true;
            }
        }

        return false;
    }


    public Collection getSubConceptPaths() {
        ArrayList subs = new ArrayList();
        Collection concepts = m_ontology.getConcepts();
        Iterator it = concepts.iterator();
        while(it.hasNext()) {
            Concept concept = (Concept) it.next();
            if(this.equals(concept.getSuperConcept())) {
                subs.add(concept.getFullPath());
            }
        }
        return subs;
    }


    public Collection getReferencedConceptPaths() {
        LinkedHashSet refersTo = new LinkedHashSet();

        Iterator it = m_properties.values().iterator();
        while (it.hasNext()) {
            PropertyDefinition pd = (PropertyDefinition) it.next();
            if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE || pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT)
            {
                String conceptPath = pd.getConceptTypePath();
                refersTo.add(conceptPath);
            }
        }

        return refersTo;
    }


    public MutableInstance instantiate(MutableFolder domain, String name) throws ModelException {
        MutableInstance instance = m_ontology.createInstance(domain, this, name, true);
        notifyListeners();
        notifyOntologyOnChange();
        return instance;
    }


    public void removeInstance(MutableInstance instance) throws ModelException {
        // TODO - Bad implementation or bad name? This mehtod deletes the instance!!!
        if (null != instance) {
            instance.delete();
            this.notifyListeners();
            this.notifyOntologyOnChange();
        }
    }


    public Collection getAllInstancePaths() {
        Collection allInstances = new ArrayList();

        allInstances.addAll(getLocalInstancesPaths());
        allInstances.addAll(getSubInstancePaths());

        return allInstances;
    }


    public Collection getSubInstancePaths() {
        ArrayList subInstances = new ArrayList();

        Iterator it = m_concepts.iterator();
        while (it.hasNext()) {
            String subConceptPath = (String) it.next();
            Concept subConcept = m_ontology.getConcept(subConceptPath);
            if (subConcept != null) {
                subInstances.addAll(subConcept.getAllInstancePaths());
            }
        }

        return subInstances;
    }


    public Collection getLocalInstancesPaths() {
        return new ArrayList(m_instances);
    }


    public List getLocalPropertyDefinitions() {
        ArrayList props = new ArrayList(m_properties.values());

        Collections.sort(props, new Comparator() {
            public int compare(Object o1, Object o2) {
                DefaultMutablePropertyDefinition dpd1 = (DefaultMutablePropertyDefinition) o1;
                DefaultMutablePropertyDefinition dpd2 = (DefaultMutablePropertyDefinition) o2;
                return dpd1.getOrder() - dpd2.getOrder();
            }
        });

        return props;
    }

    /**
     *
     * @param concept
     * @param list
     */
    void _getPropertyDefinitions (Concept concept, LinkedHashMap list) {
        if (concept.getSuperConcept() != null) {
            _getPropertyDefinitions(concept.getSuperConcept(), list);
        }
        Iterator localProperties = concept.getLocalPropertyDefinitions().iterator();
        while (localProperties.hasNext()) {
            DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) localProperties.next();
            if (list.get(dpd.getName()) == null) {
                list.put(dpd.getName(), dpd);
            }
        }
    }

    /**
     *
     * @return a List
     */
    public List  getAllPropertyDefinitions() {
        LinkedHashMap propTable = new LinkedHashMap();
        _getPropertyDefinitions(this,propTable);
        return new ArrayList(propTable.values());
    }

    /**
     * This is the older version of getAllPropertyDefinitions() that returned the properties in the reverse order
     * This has to remain due to backward compatibility issues with ontology functions, xml schemas..
     * @return a List
     */
    public List _getAllPropertyDefinitions() {

        LinkedHashMap propTable = new LinkedHashMap();

        Collection localPDs = getLocalPropertyDefinitions();

        /* Fill our property table with our own PropertyDefinitions first */
        Iterator localIt = localPDs.iterator();
        while (localIt.hasNext()) {
            DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) localIt.next();
            propTable.put(dpd.getName(), dpd);
        }

        /* Go through the entire inheritance tree */
        DefaultMutableConcept ancestor = (DefaultMutableConcept) getSuperConcept();
        while (ancestor != null) {
            /* Go through the ancestors properties, and add only those that haven't been overriden */
            Collection inheritedPDs = ancestor.getLocalPropertyDefinitions();
            Iterator inheritedPropIt = inheritedPDs.iterator();
            while (inheritedPropIt.hasNext()) {
                DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) inheritedPropIt.next();
                if (!propTable.containsKey(dpd.getName())) {
                    propTable.put(dpd.getName(), dpd);
                }
            }

            /* Go up the chain */
            ancestor = (DefaultMutableConcept) ancestor.getSuperConcept();
        }

        return new ArrayList(propTable.values());
    }


    public List getPropertyDefinitions(boolean localOnly) {
        if (localOnly) {
            return getLocalPropertyDefinitions();
        } else {
            return _getAllPropertyDefinitions();
        }
    }


    public Collection getAttributeDefinitions() {
        ArrayList retAttributes = new ArrayList();
        if (this.getSuperConcept() == null) {
            fillStaticAttributes(retAttributes);
        } else {
            retAttributes.addAll(getSuperConcept().getAttributeDefinitions());
        }

        if (this.isContained()) {
            retAttributes.add(new DefaultMutableAttributeDefinition((DefaultMutableOntology) this.getOntology(), "parent", this, false, RDFTypes.CONCEPT_REFERENCE_TYPEID, this.getParentConcept().getFullPath(), PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, null, 1));
        }
        return retAttributes;
    }


    public PropertyDefinition getAttributeDefinition(String attributeName) {
        if (attributeName.equals(BASE_ATTRIBUTE_NAMES[0])) {
            return new DefaultMutableAttributeDefinition((DefaultMutableOntology) this.getOntology(), BASE_ATTRIBUTE_NAMES[0], this, false, RDFTypes.LONG_TYPEID, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, null, 1);
        } else if (attributeName.equals(BASE_ATTRIBUTE_NAMES[1])) {
            return new DefaultMutableAttributeDefinition((DefaultMutableOntology) this.getOntology(), BASE_ATTRIBUTE_NAMES[1], this, false, RDFTypes.STRING_TYPEID, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, null, 1);
        } else if (attributeName.equals("parent")) {
            if (isContained()) {
                return new DefaultMutableAttributeDefinition((DefaultMutableOntology) this.getOntology(), "parent", this, false, RDFTypes.CONCEPT_REFERENCE_TYPEID, this.getParentConcept().getFullPath(), PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, null, 1);
            }
        }
        return null;
    }


    /**
     *
     */
    protected synchronized void fillStaticAttributes(ArrayList list) {
        fillStaticAttributes((DefaultMutableOntology) getOntology(), this, list);
    }


    public synchronized static void fillStaticAttributes(DefaultMutableOntology ont, DefaultMutableConcept dc, List list) {
        list.add(new DefaultMutableAttributeDefinition(ont, BASE_ATTRIBUTE_NAMES[0], dc, false, RDFTypes.LONG_TYPEID, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, null, 1));
        list.add(new DefaultMutableAttributeDefinition(ont, BASE_ATTRIBUTE_NAMES[1], dc, false, RDFTypes.STRING_TYPEID, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, null, 1));
    }


    public PropertyDefinition getSubPropertyDefinition(String name) {
        if (m_ontology == null) {
            return null;
        }

        Iterator it = m_concepts.iterator();
        while (it.hasNext()) {
            String subPath = (String) it.next();
            Concept concept = m_ontology.getConcept(subPath);
            if (concept == null) {
                continue;
            }

            PropertyDefinition pd = concept.getPropertyDefinition(name, true);
            if (pd == null) {
                pd = concept.getSubPropertyDefinition(name);
            }

            if (pd != null) {
                return pd;
            }
        }

        return null;
    }


    public static final String BAD_RDF_TYPE = "DefaultMutableConcept.createPropertyDefinition.badRDFType";
    public static final String DUPLICATE_PROPERTY_DEFINITION_NAME = "DefaultMutableConcept.createPropertyDefinition.duplicate";
    public static final String MAX_PROPERTY_DEFINITIONS_REACHED = "DefaultMutableConcept.createPropertyDefinition.maxPropertyDefinitions";


    public MutablePropertyDefinition createPropertyDefinition(String name, int typeFlag, String conceptTypePath, int historyPolicy, int historySize, boolean isArray, String defaultValue) throws ModelException {
        BEModelBundle bundle = BEModelBundle.getBundle();
        String fullPath = getFullPath();

        if (m_properties.size() >= Concept.MAX_LOCAL_PROPERTY_DEFINITIONS) {
            String maxProps = String.valueOf(MAX_LOCAL_PROPERTY_DEFINITIONS);
            String msg = bundle.formatString(MAX_PROPERTY_DEFINITIONS_REACHED, fullPath, maxProps);
            throw new ModelException(msg);
        }

        if (name == null || name.length() == 0) {
            String msg = bundle.getString(AbstractMutableEntity.EMPTY_NAME_KEY);
            throw new ModelException(msg);
        }

        if (conceptTypePath == null) {
            conceptTypePath = "";
        }

        /* Check to see if the owner already has a local property with the specified name **/
        DefaultMutablePropertyDefinition def = (DefaultMutablePropertyDefinition) getPropertyDefinitionForName(name);
        if (def != null) {
            Concept owner = def.getOwner();
            String ownerPath = owner.getFullPath();

            String msg = bundle.formatString(DUPLICATE_PROPERTY_DEFINITION_NAME, name, ownerPath);
            throw new ModelException(msg);
        }

        /* Check to see if the type is valid */
        try {
            final RDFUberType type = RDFTypes.types[typeFlag];
        }
        catch (Exception e) {
            String msg = bundle.getString(BAD_RDF_TYPE);
            throw new ModelException(msg);
        }

        boolean usesConcept = (RDFTypes.CONCEPT_REFERENCE_TYPEID == typeFlag) || (RDFTypes.CONCEPT_TYPEID == typeFlag);
        DefaultMutableConcept dcType = (DefaultMutableConcept) m_ontology.getConcept(conceptTypePath);

        /** If this is a Concept PropertyDefinition, make sure they have provided a legitimate MutableConcept */
        if (dcType == null && usesConcept) {
            String msg = bundle.formatString(NULL_CONCEPT_TYPE_KEY, conceptTypePath);
            throw new ModelException(msg);
        }

        /* If dcType is a contained Concept, make sure it isn't already contained. */
        if (typeFlag == RDFTypes.CONCEPT_TYPEID) {
            if (fullPath.equals(conceptTypePath)) {
                String msg = bundle.getString(SELF_CONTAINED_CONCEPT);
                throw new ModelException(msg);
            }

            Concept parentConcept = dcType.getParentConcept();
            if (parentConcept != null) {
//            if(parentConcept != null && !parentConcept.equals(this)) {
                String msg = bundle.formatString(CONCEPT_TYPE_ALREADY_CONTAINED, conceptTypePath, parentConcept.getFullPath());
                throw new ModelException(msg);
            }

            /* Make sure a containment circle isn't created */
            if (dcType.contains(this)) {
                String msg = bundle.formatString(CONTAINMENT_CIRCLE, fullPath, conceptTypePath);
                throw new ModelException(msg);
            }
        }

        /** Validate history size **/
        if (historySize < PropertyDefinition.MIN_HISTORY_SIZE) {
            historySize = PropertyDefinition.MIN_HISTORY_SIZE;
        } else if (historySize > PropertyDefinition.MAX_HISTORY_SIZE) {
            historySize = PropertyDefinition.MAX_HISTORY_SIZE;
        }

        int order = getNextPropertyDefinitionOrder();

        DefaultMutablePropertyDefinition dpd = new DefaultMutablePropertyDefinition(m_ontology, name, this, isArray, typeFlag, conceptTypePath, historyPolicy, historySize, defaultValue, order);

        /* Register the prop with its owner **/
        m_properties.put(name, dpd);

        /* Register the prop with conceptType if appropriate **/
        if ((typeFlag == PropertyDefinition.PROPERTY_TYPE_CONCEPT) || (typeFlag == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE))
        {
            dcType.addReferringProperty(fullPath, name);
//            Collection c = (Collection) dcType.m_referringPropsMap.get(fullPath);
//            if(c == null) {
//                c = new LinkedHashSet();
//                dcType.m_referringPropsMap.put(fullPath, c);
//            }
//
//            c.add(name);
        }

//        setReferringRuleCompilationStatus(-1);

        notifyListeners();
        notifyOntologyOnChange();
        return dpd;
    }


    protected int getNextPropertyDefinitionOrder() {
        int order = 0;

        Iterator it = m_properties.values().iterator();
        while (it.hasNext()) {
            DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) it.next();
            if (dpd.getOrder() >= order) {
                order = dpd.getOrder() + 1;
            }
        }

        return order;
    }


    protected PropertyDefinition getPropertyDefinitionForName(String name) {
        if (m_ontology == null) {
            return null;
        }

        PropertyDefinition pd = null;

        /* If this or ancestors have the name, it is in use */
        pd = getPropertyDefinition(name, false);
        if (pd != null) {
            return pd;
        }

        /* If derivers have the name, it is in use */
        Iterator it = m_concepts.iterator();
        while (it.hasNext()) {
            String path = (String) it.next();
            Concept subConcept = m_ontology.getConcept(path);
            if (subConcept == null) {
                continue;
            }
            pd = subConcept.getPropertyDefinition(name, true);
            if (pd != null) {
                return pd;
            }
        }

        return pd;
    }


    public PropertyDefinition getPropertyDefinition(String name, boolean localOnly) {
        MutablePropertyDefinition pd = (MutablePropertyDefinition) m_properties.get(name);
        DefaultMutableConcept superConcept = (DefaultMutableConcept) getSuperConcept();

        /* If we're only looking for local definitions, or we've found the definition, or we have no parent return it */
        if (localOnly || pd != null || superConcept == null) {
            return pd;
        }

        /* Otherwise, if we haven't found it, check with out super class */
        else {
            return superConcept.getPropertyDefinition(name, false);
        }
    }


    public void deletePropertyDefinition(String name) {
        DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) m_properties.remove(name);

        if (dpd != null) {
            DefaultMutableConcept dcType = (DefaultMutableConcept) dpd.getConceptType();
            if (dcType != null) {
                String path = getFullPath();
                dcType.removeReferringProperty(path, name);
            }
        }

//        setReferringRuleCompilationStatus(-1);

        notifyListeners();
        notifyOntologyOnChange();
    }


    public void deletePropertyDefinition(MutablePropertyDefinition propertyDefinition) {
        deletePropertyDefinition(propertyDefinition.getName());
    }


    public Collection getReferringConceptPaths() {
        return new ArrayList(m_referringPropsMap.keySet());
    }


    public void clearReferringViewPaths() {
        m_views.clear();
        notifyListeners();
        notifyOntologyOnChange();
    }


    public boolean removeReferringViewPath(String path) {
        return m_views.remove(path);
    }


    public void clearReferringConceptPaths() {
        m_referringPropsMap.clear();
    }


    public void clearSubConceptPaths() {
        m_concepts.clear();
    }


    public Collection getReferringPropertyDefinitionNames(String conceptPath) {
        Collection c = (Collection) m_referringPropsMap.get(conceptPath);
        return (c != null) ? c : new LinkedHashSet();
    }


    public Collection getReferringViewPaths() {
        return m_views;
    }


    public SmElement toSmElement() {

        String fullPath = getFullPath();
        DefaultComponentFactory factory = new DefaultComponentFactory();
        DefaultSchema schema = (DefaultSchema) factory.createSchema();
        schema.setNamespace(fullPath);

        // The complex type for this Concept
        DefaultType type = (DefaultType) factory.createType();
        type.setName(m_name);
        type.setNamespace(fullPath);
        type.setSchema(schema);
        type.setComplex();

        // todo Might not need these - rkt
        type.setAllowedDerivation(SmType.RESTRICTION + SmType.EXTENSION);
        type.setAllowedSubstitution(SmType.RESTRICTION + SmType.EXTENSION);
        type.setMixedContent(false);
        type.setIsOnAttributesOnly(false);
        type.setAbstract(false);
        type.setNative(false);

        Concept superConcept = getSuperConcept();
        if (superConcept != null) {
            type.setBaseType(superConcept.toSmElement().getType());
        }
        schema.addSchemaComponent(type); /* Add the complex type to the schema */

        /* The element for this Concept within the schema */
        //DefaultElement element = (DefaultElement) factory.createElement();
        MutableElement element = createElement(fullPath, m_name, type, schema);
        //element.setName(fullPath);
        schema.addSchemaComponent(element); /* Add an element of this type to the schema */

        /* Create the sub-element group for this type */
        DefaultModelGroup modelGroup = (DefaultModelGroup) factory.createModelGroup();
        modelGroup.setCompositor(SmModelGroup.SEQUENCE);
        modelGroup.setSchema(schema);

        Collection props = this._getAllPropertyDefinitions();
        Iterator propIt = props.iterator();
        while (propIt.hasNext()) {
            DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) propIt.next();
            DefaultParticle propParticle = (DefaultParticle) factory.createParticle();
            if (dpd.isArray()) {
                propParticle.setMinOccurrence(0);
                propParticle.setMaxOccurrence(Integer.MAX_VALUE);
            } else {
                propParticle.setMinOccurrence(1);
                propParticle.setMaxOccurrence(1);
            }
            SmType propElemType = getTypeForPropertyDefinition(schema, dpd);
            MutableElement propElement = createElement(fullPath, dpd.getName(), propElemType, schema);
            //propElement.setName(dpd.getName());
            //propElement.setNamespace(fullPath);
            //propElement.setType(propElemType);
            propParticle.setTerm(propElement);
            modelGroup.addParticle(propParticle);
        }//endwhile
        type.setContentModel(modelGroup);
        return element;
    }


    protected MutableElement createElement(String namespace, String localname, SmType type, MutableSchema schema) {
        //throws ConversionException {
        // If element already exists in our schema, just return that element.
        MutableElement element = null; // todo (caching)   SmSupport.getElement(schema, localname);
        // If we still don't have an element, create one.
        if (element == null) {
            MutableElement mutableElement = schema.getComponentFactory().createElement();
            mutableElement.setName(localname);
            mutableElement.setNamespace(namespace);
            mutableElement.setSchema(schema);
            mutableElement.setType(type);
            mutableElement.setAllowedSubstitution(SmType.RESTRICTION + SmType.EXTENSION + SmType.SUBSTITUTION);
            mutableElement.setAllowedDerivation(SmType.RESTRICTION + SmType.EXTENSION);
            mutableElement.setNillable(false);
            mutableElement.setAbstract(false);
            element = mutableElement;
        }
        return element;
    }


    protected SmType getTypeForPropertyDefinition(DefaultSchema schema, DefaultMutablePropertyDefinition dpd) {
        SmType type = null;
        switch (dpd.getType()) {
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN :
                type = XSDL.BOOLEAN;
                break;
            case PropertyDefinition.PROPERTY_TYPE_INTEGER :
                type = XSDL.INTEGER;
                break;
            case PropertyDefinition.PROPERTY_TYPE_REAL :
                type = XSDL.DOUBLE;
                break;
            case PropertyDefinition.PROPERTY_TYPE_STRING :
                type = XSDL.STRING;
                break;
            case PropertyDefinition.PROPERTY_TYPE_CONCEPT :
            case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE :
                type = SmSupport.getType(schema, dpd.getConceptTypePath());
                if (type != null) {
                    break;
                }
                Concept concept = dpd.getConceptType();
                type = concept.toSmElement().getType();
                schema.addSchemaComponent(type);
                break;
        }
        return type;
    }


    public String toString() {
        DefaultMutableConcept superConcept = (DefaultMutableConcept) getSuperConcept();
        StringBuffer me = new StringBuffer("DefaultMutableConcept: " + m_folder + m_name + " (" + m_guid + ")");
        me.append("\nSuper: ");
        if (superConcept != null) {
            me.append(superConcept.getFolder() + superConcept.getName() + " (" + superConcept.m_guid + ")");
        }

        me.append("\nLocal Properties:");
        if (m_properties.size() > 0) {
            Iterator it = m_properties.values().iterator();
            while (it.hasNext()) {
                me.append("\n\t" + it.next());
            }
        }

        return me.toString();
    }


    public void entityChanged(MutableEntity entity) {
        if (!(entity instanceof Concept)) {
            return;
        }
    }


    public void notifyCardinalityChanged(PropertyDefinition definition, int min, int max) {
    }


    public boolean isValid(boolean recurse) {
        /* Not valid if the super Concept is specified, but non-existent */
        if (m_super != null && m_super.length() > 0) {
            if (getSuperConcept() == null) {
                return false;
            }
        }
        return super.isValid(recurse);
    }


    public void delete() {
        if (m_ontology == null) {
            return;
        }

        String path = getFullPath();

        /* Removes references from its ConceptViews */
        Iterator viewIt = m_views.iterator();
        while (viewIt.hasNext()) {
            String viewPath = (String) viewIt.next();
            final MutableConceptView dcv = (MutableConceptView) m_ontology.getConceptView(viewPath);
            if (dcv != null) {
                dcv.removeReference(path);
            }
        }

        /* Deletes the property definitions, avoiding ConcurrentModificationException */
        final Object[] toBeDeleted = m_properties.values().toArray();
        for (int i = 0, max = toBeDeleted.length; i < max; i++) {
            final MutablePropertyDefinition pd = (MutablePropertyDefinition) toBeDeleted[i];
            deletePropertyDefinition(pd);
        }//for

        /* Notifies referrers */
        Set keySet = m_referringPropsMap.keySet();
        Iterator refIt = keySet.iterator();
        while (refIt.hasNext()) {
            String refPath = (String) refIt.next();
            DefaultMutableConcept refConcept = (DefaultMutableConcept) m_ontology.getConcept(refPath);
            if (refConcept != null) {
                refConcept.notifyListeners();
                refConcept.notifyOntologyOnChange();
            }
        }

        /* Notifies sub-concepts */
        Iterator subConceptIt = m_concepts.iterator();
        while (subConceptIt.hasNext()) {
            String subPath = (String) subConceptIt.next();
            DefaultMutableConcept subConcept = (DefaultMutableConcept) m_ontology.getConcept(subPath);
            if (subConcept != null) {
                subConcept.notifyListeners();
                subConcept.notifyOntologyOnChange();
            }
        }

        if (m_ruleSet != null) {
            m_ruleSet.delete();
        }

        notifyListeners();
        super.delete();
    }


    public XiNode toXiNode(XiFactory factory) {
        Concept superConcept = getSuperConcept();

        XiNode root = super.toXiNode(factory, "concept");
        XiAttribute.setStringValue(root, "folder", m_folder.toString());
        XiAttribute.setStringValue(root, "super", (superConcept != null) ? superConcept.getFullPath() : "");
        XiAttribute.setStringValue(root, "isTransient", String.valueOf(m_transient));
        XiAttribute.setStringValue(root, "isAutoStartStateMachine", String.valueOf(isAutoStartStateMachine));
        XiAttribute.setStringValue(root, "isPOJO", String.valueOf(m_isPOJO));
        XiAttribute.setStringValue(root, "implClass", m_pojoImplClass);
        if (m_iconRef != null) {
            XiAttribute.setStringValue(root, "iconRef", m_iconRef);
        }//endif

        XiAttribute.setStringValue(root, "isAScorecard", String.valueOf(m_isAScorecard));

        XiNode propertyDefs = root.appendElement(PROPERTY_DEFINITIONS_NAME);
        Iterator it = m_properties.values().iterator();
        while (it.hasNext()) {
            DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) it.next();
            propertyDefs.appendChild(dpd.toXiNode(factory));
        }

        XiNode subConcepts = root.appendElement(SUB_CONCEPTS_NAME);
        it = m_concepts.iterator();
        while (it.hasNext()) {
            String dcPath = (String) it.next();
            XiNode concept = subConcepts.appendElement(CONCEPT_NAME);
            concept.setStringValue(dcPath);
        }

        XiNode instances = root.appendElement(INSTANCES_NAME);
        it = m_instances.iterator();
        while (it.hasNext()) {
            String diPath = (String) it.next();
            XiNode instance = instances.appendElement(INSTANCE_NAME);
            instance.setStringValue(diPath);
        }

        XiNode views = root.appendElement(VIEWS_NAME);
        it = m_views.iterator();
        while (it.hasNext()) {
            String dcvPath = (String) it.next();
            XiNode view = views.appendElement(VIEW_NAME);
            view.setStringValue(dcvPath);
        }

        XiNode referringProps = root.appendElement(REFERRING_PROPS_NAME);
        Set keySet = m_referringPropsMap.keySet();
        it = keySet.iterator();
        while (it.hasNext()) {
            String conceptPath = (String) it.next();
            Collection c = (Collection) m_referringPropsMap.get(conceptPath);
            if (c == null || c.isEmpty()) {
                continue;
            }

            /* Append a node for each MutableConcept that has PropertyDefinitions that refer to this MutableConcept */
            XiNode referringConceptNode = referringProps.appendElement(REFERRING_CONCEPT_NAME);
            referringConceptNode.setAttributeStringValue(REFERRING_CONCEPT_PATH_NAME, conceptPath);

            /* Append a node for referring PropertyDefinition in that MutableConcept */
            Iterator nameIt = c.iterator();
            while (nameIt.hasNext()) {
                String name = (String) nameIt.next();
                XiNode propNameNode = referringConceptNode.appendElement(REFERRING_PROP_NAME);
                propNameNode.setStringValue(name);
            }

        }

        //XiNode ruleSetsNode = super.rulesToXiNode();
        //root.appendChild(ruleSetsNode);

//        if (m_ruleSet != null) {
//            XiNode  ruleSetNode = m_ruleSet.toXiNode (factory);
//            root.appendChild (ruleSetNode);
//        }//endif

        // Add StateMachines
        final List<StateMachine> smList = new LinkedList<StateMachine>();
        if (null != this.m_stateMachines) {
            for (Object smObject : this.m_stateMachines) {
                if (!(smObject instanceof MutableStandaloneStateMachine)) {
                    smList.add((StateMachine) smObject);
                }
            }
        }
        addXiNodesOfList(factory, root, smList, STATE_MACHINES);
//        if (m_mainStateMachine != null) {
//            root.setAttributeStringValue(ROOT_STATE_MACHINE_GUID, m_mainStateMachine.getGUID());
//        }//endif

        return root;
    }


    /**
     * Get the list of StateMachines contained within this Concept.
     *
     * @return The List of StateMachines contained within this Concept.
     */
    public List getStateMachines() {
        return m_stateMachines;
    }// end getStateMachines


    public List getAllStateMachines() {
        final List list = new ArrayList(this.getStateMachines());
        if (this.getSuperConcept() != null) {
            list.addAll(this.getSuperConcept().getAllStateMachines());
        }
        return list;
    }




    /**
     * Is the StateMachine passed already a member of this Concept.
     *
     * @param stateMachineToCheck A StateMachine to check if present in this Concept.
     */
    public boolean isStateMachinePresent(StateMachine stateMachineToCheck) {
        Iterator stateMachines = getStateMachines().iterator();
        while (stateMachines.hasNext()) {
            if (stateMachines.next() == stateMachineToCheck) {
                return true;
            }//endif
        }//endwhile
        return false;
    }// end isStateMachinePresent


    /**
     * Add the StateMachine passed to this Concept.
     *
     * @param newStateModel A StateMachine to add to this Concept.
     */
    public void addStateMachine(MutableStateMachine newStateModel) {
        if (m_stateMachines == null) {
            m_stateMachines = new ArrayList();
        }//endif

        if (isStateMachinePresent(newStateModel)) {
            return;
        }

        m_stateMachines.add(newStateModel);

     // Not safe, since this is used when loading the concept, and the parents are possibly not loaded yet.
//      //If there is only one machine, set it to be the "main" machine
//      if (this.getAllStateMachines().size() == 1) {
//          ((MutableStateMachine) m_stateMachines.get(0)).setAsMain(true);
//      }//endif

//        notifyListeners();
        notifyOntologyOnChange();
    }// end addStateMachine


    /**
     * Delete the state machine at the index passed from this Concept.
     * If the index is greater than the size of the array then nothing is deleted.
     *
     * @param index The index of a StateMachine to delete from this Concept.
     */
    public void deleteStateMachine(int index) {
        if (m_stateMachines != null && index < m_stateMachines.size()) {
            if (((StateMachine) m_stateMachines.get(index)).isMain()) {
//                m_stateMachines.remove (index);
//                // Must promote some other StateMachine to be main, so make first one in list the new main
//                if (m_stateMachines.size () > 0) {
//                    ((StateMachine) m_stateMachines.get (0)).setAsMain ("");
//                }//endif
//                m_mainStateMachine = null;
            }
            m_stateMachines.remove(index);
            notifyOntologyOnChange();
//            }//endif
        }//endif
    }// end deleteStateMachine


    /**
     * Delete the state machine passed from this Concept.
     *
     * @param stateMachineToDelete The StateMachine to delete from this Concept.
     */
    public void deleteStateMachine(
            MutableStateMachine stateMachineToDelete) {
        if (m_stateMachines != null) {
            Iterator stateMachineIterator = m_stateMachines.iterator();
            int index = 0;
            while (stateMachineIterator.hasNext()) {
                StateMachine candidateStateMachine = (StateMachine) stateMachineIterator.next();
                if (candidateStateMachine == stateMachineToDelete) {
                    deleteStateMachine(index);
                    break;
                }//endif
                index++;
            }//endwhile
        }//endif
    }// end deleteStateMachine


    /**
     * Return the "main" StateMachine.  The main state machine is the where the
     * state machine will start.
     *
     * @return The "main" StateMachine.
     */
    public StateMachine getMainStateMachine() {
        final List<StateMachine> list = this.getStateMachines();
        if (null != list) {
            for (StateMachine sm : list) {
                if ((null != sm) && sm.isMain()) {
                    return sm;
                }
            }
        }
        return null;
//        return m_mainStateMachine;
    }// end getMainStateMachine


    /**
     * Set the "main" StateModel.  The main state model is the where the
     * state machine should start.
     *
     * @param mainStateMachine The new "main" StateModel.
     */
    public void setMainStateMachine(MutableStateMachine mainStateMachine) {
//        m_mainStateMachine = mainStateMachine;
        mainStateMachine.setAsMain(true);
        notifyOntologyOnChange();
    }// end setMainStateMachine


    /**
     * ************** Methods used by the default implementation *************
     */
    public void addSubConcept(String subConceptPath) {
        addToSet(m_concepts, subConceptPath);
    }


    public boolean removeSubConcept(String subConceptPath) {
        boolean removed = removeFromSet(m_concepts, subConceptPath);
        return removed;
    }


    public void modifySubConcept(String oldPath, String newPath) {
        modifySet(m_concepts, oldPath, newPath);
    }


    public void addView(String viewPath) {
        addToSet(m_views, viewPath);
    }


    public boolean removeView(String viewPath) {
        boolean removed = removeFromSet(m_views, viewPath);
        return removed;
    }


    public void modifyView(String oldViewPath, String newViewPath) {
        modifySet(m_views, oldViewPath, newViewPath);
    }


    protected void addToSet(Set set, String entry) {
        set.remove(entry);
        set.add(entry);
        notifyListeners();
        notifyOntologyOnChange();
    }


    protected boolean removeFromSet(Set set, String entry) {
        boolean exists = set.remove(entry);
        if (exists) {
            notifyListeners();
            notifyOntologyOnChange();
        }

        return exists;
    }


    protected void modifySet(Set set, String oldEntry, String newEntry) {
        boolean exists = set.remove(oldEntry);
        if (!exists) {
            return;
        }

        set.add(newEntry);
        notifyListeners();
        notifyOntologyOnChange();
    }


    public void addReferringProperty(String conceptPath, String propertyName) {
        Collection c = (Collection) m_referringPropsMap.remove(conceptPath);
        if (c == null) {
            c = new LinkedHashSet();
        }

        c.add(propertyName);
        m_referringPropsMap.put(conceptPath, c);

        notifyListeners();
        notifyOntologyOnChange();
    }


    public boolean removeReferringProperty(String conceptPath, String propertyName) {
        Collection c = (Collection) m_referringPropsMap.get(conceptPath);
        if (c == null) {
            return false;
        }

        boolean exists = c.remove(propertyName);
        if (c.size() == 0) {
            m_referringPropsMap.remove(conceptPath);
        }

        if (exists) {
            notifyListeners();
            notifyOntologyOnChange();
        }

        return exists;
    }


    public void modifyReferringProperty(String conceptPath, String oldPropertyName, String newPropertyName) {
        Collection c = (Collection) m_referringPropsMap.get(conceptPath);
        if (c == null) {
            return;
        }

        boolean exists = c.remove(oldPropertyName);
        if (!exists) {
            return;
        }

        c.add(newPropertyName);
        notifyListeners();
        notifyOntologyOnChange();
    }


    public void addReferences(String conceptPath, Collection propNames) {
        if (propNames == null || propNames.size() == 0) {
            return;
        }

        Collection c = (Collection) m_referringPropsMap.get(conceptPath);
        if (c == null) {
            c = new LinkedHashSet();
        }

        c.addAll(propNames);
        m_referringPropsMap.put(conceptPath, c);

        notifyListeners();
        notifyOntologyOnChange();
    }


    public void deleteAllReferences(String conceptPath) {
        Object o = m_referringPropsMap.remove(conceptPath);

        if (o != null) {
            notifyListeners();
            notifyOntologyOnChange();
        }
    }


    public void referenceChanged(String oldPath, String newPath) {
        Object props = m_referringPropsMap.remove(oldPath);
        if (props != null) {
            m_referringPropsMap.put(newPath, props);
            notifyListeners();
            notifyOntologyOnChange();
        }
    }


    public String getIconRef() {
        return m_iconRef;
    }// end getIconRef


    public void setIconRef(
            String iconRef) {
        m_iconRef = iconRef;
    }// end setIconRef


    public static String SUPER_CONCEPT_NULL_ERROR = "DefaultMutableConcept.errors.superConceptIsNull";
    public static String PROPERTY_CONCEPT_MISSING_ERROR = "DefaultMutableConcept.errors.propertyConceptMissing";


    public List getModelErrors() {
        BEModelBundle bundle = BEModelBundle.getBundle();
        List errors = super.getModelErrors();

        String fullPath = getFullPath();

        /* Check that our super class exists */
        Concept superConcept = getSuperConcept();
        if (!ModelUtils.IsEmptyString(m_super) && superConcept == null) {
            String msg = bundle.formatString(SUPER_CONCEPT_NULL_ERROR, fullPath, m_super);
            ModelError error = new ModelError(this, msg);
            errors.add(error);
        }

        /* Check that the Concepts to whom we refer exist */
        Iterator pdIt = m_properties.values().iterator();
        while (pdIt.hasNext()) {
            PropertyDefinition pd = (PropertyDefinition) pdIt.next();
            if (!(pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT) && !(pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE))
            {
                continue;
            }

            Concept cType = pd.getConceptType();
            if (cType == null) {
                String cTypePath = pd.getConceptTypePath();
                String msg = bundle.formatString(PROPERTY_CONCEPT_MISSING_ERROR, fullPath, pd.getName(), cTypePath);
                ModelError error = new ModelError(this, msg);
                errors.add(error);
            }
        }

        return errors;
    }


    public static DefaultMutableConcept createDefaultConceptFromNode(XiNode root) throws ModelException {
        DefaultMutableConcept dc = null;
        String folder = root.getAttributeStringValue(ExpandedName.makeName("folder"));
        String name = root.getAttributeStringValue(ExpandedName.makeName("name"));
        String description = root.getAttributeStringValue(AbstractMutableEntity.DESCRIPTION_NAME);
        String superPath = root.getAttributeStringValue(ExpandedName.makeName("super"));
        String guid = root.getAttributeStringValue(ExpandedName.makeName("guid"));
        String iconRef = root.getAttributeStringValue(ExpandedName.makeName("iconRef"));
        String isSC = root.getAttributeStringValue(ExpandedName.makeName("isAScorecard"));
        String isTransient= root.getAttributeStringValue(ExpandedName.makeName("isTransient"));
        String isAutoStartStateMachine= root.getAttributeStringValue(ExpandedName.makeName("isAutoStartStateMachine"));
        String isPOJO= root.getAttributeStringValue(ExpandedName.makeName("isPOJO"));
        String pojoClassName = root.getAttributeStringValue(ExpandedName.makeName("implClass"));

        /* We pass null for superPath since the super Concept may not yet exist */
        DefaultMutableFolder rootFolder = new DefaultMutableFolder(null, null, String.valueOf(Folder.FOLDER_SEPARATOR_CHAR));
        DefaultMutableFolder conceptFolder = DefaultMutableOntology.createFolder(rootFolder, folder, false);

        dc = new DefaultMutableConcept(null, conceptFolder, name, "");

        if (isSC != null) {
            dc.m_isAScorecard = Boolean.valueOf(isSC).booleanValue();
        } else {
            dc.m_isAScorecard = false;
        }


        if ((isTransient != null) && (isTransient.trim().length() > 0)) {
            dc.setTransient(Boolean.valueOf(isTransient).booleanValue());
        } else {
            dc.setTransient(false);
        }

        if ((isAutoStartStateMachine != null) && (isAutoStartStateMachine.trim().length() > 0)) {
            dc.setIsAutoStartStateMachine(Boolean.valueOf(isAutoStartStateMachine).booleanValue());
        } else {
            dc.setIsAutoStartStateMachine(true);
        }

        if ((isPOJO != null) && (isPOJO.trim().length() > 0)) {
            dc.setPOJO(Boolean.valueOf(isPOJO).booleanValue());

        } else {
            dc.setPOJO(false);
        }

        if ((pojoClassName != null) && (pojoClassName.trim().length() > 0)) {
            dc.setPOJOImplClassName(pojoClassName);
        }



        // This sort of sucks, but I must know the most recently loaded Concept immediately so
        // the RuleSet can be accessed during loading of other objects
        DefaultMutableOntology.setMostRecentConcept(dc);
        dc.m_super = superPath;
        dc.setGUID(guid);
        dc.m_iconRef = iconRef;
        dc.m_description = description;

        /* Add the sub Concept paths */
        Iterator it;
        XiNode subConcepts = XiChild.getChild(root, SUB_CONCEPTS_NAME);
        if (subConcepts != null) {
            it = subConcepts.getChildren();
            while (it.hasNext()) {
                XiNode childNode = (XiNode) it.next();
                String subPath = childNode.getStringValue();
                dc.m_concepts.add(subPath);
            }
        }

        /* Add the Instance paths */
        XiNode instances = XiChild.getChild(root, INSTANCES_NAME);
        if (instances != null) {
            it = instances.getChildren();
            while (it.hasNext()) {
                XiNode childNode = (XiNode) it.next();
                String instancePath = childNode.getStringValue();
                dc.m_instances.add(instancePath);
            }
        }

        /* Add ConceptView paths */
        XiNode views = XiChild.getChild(root, VIEWS_NAME);
        if (views != null) {
            it = views.getChildren();
            while (it.hasNext()) {
                XiNode childNode = (XiNode) it.next();
                String viewPath = childNode.getStringValue();
                dc.m_views.add(viewPath);
            }
        }

        // Extended properties
        XiNode extPropsNode = XiChild.getChild(root, EXTENDED_PROPERTIES_NAME);
        dc.setExtendedProperties(createExtendedPropsFromXiNode(extPropsNode));


        /* Add the PropertyDefinitions */
        XiNode propertyDefs = XiChild.getChild(root, PROPERTY_DEFINITIONS_NAME);
        if (propertyDefs != null) {
            it = propertyDefs.getChildren();
            while (it.hasNext()) {
                XiNode pdNode = (XiNode) it.next();
                String defName = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.NAME_NAME);
                String defGuid = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.GUID_NAME);
                String type = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.TYPE_NAME);
                String conceptType = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.CONCEPT_TYPE_NAME);
                String isArrayStr = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.IS_ARRAY_NAME);
                String policyStr = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.POLICY_NAME);
                String history = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.HISTORY_NAME);
                String value = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.VALUE_NAME);
                String orderString = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.ORDER_NAME);


                int policy = (policyStr == null) ? PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY : Integer.parseInt(policyStr);

                int order = (orderString == null) ? -1 : Integer.parseInt(orderString);
                DefaultMutablePropertyDefinition dpd = new DefaultMutablePropertyDefinition(null, defName, dc, Boolean.valueOf(isArrayStr).booleanValue(), Integer.parseInt(type), conceptType, policy, Integer.parseInt(history), value, order);

                extPropsNode = XiChild.getChild(pdNode, AbstractMutableEntity.EXTENDED_PROPERTIES_NAME);
                dpd.setExtendedProperties(createExtendedPropsFromXiNode(extPropsNode));

                if (dpd.getExtendedProperties() != null) {
                    String indexValue= (String) dpd.getExtendedProperties().get("index");
                    if ((indexValue == null) || (indexValue.trim().length() <= 0)) {
                        dpd.getExtendedProperties().put("index", "false");
                    }
                }

                /* Restore the Property Definition's hidden properties */
                XiNode hidden = XiChild.getChild(pdNode, DefaultMutablePropertyDefinition.HIDDEN);
                if(hidden != null) {
                    LinkedHashMap hiddenProperties = new LinkedHashMap();
                    Iterator hiddenPropIt = hidden.getChildren();
                    while(hiddenPropIt.hasNext()) {
                        XiNode hiddenPropNode = (XiNode) hiddenPropIt.next();
                        String hiddenKey = hiddenPropNode.getName().getLocalName();
                        String hiddenValue = hiddenPropNode.getStringValue();
                        hiddenProperties.put(hiddenKey, hiddenValue);
                    }
                    dpd.setHiddenProperties(hiddenProperties);
                }


                dc.m_properties.put(defName, dpd);

                dpd.setGUID(defGuid);

                /* Add in PropertyInstances */
                XiNode instancesNode = XiChild.getChild(root, ExpandedName.makeName("instances"));
                if (instancesNode != null) {
                    Iterator it2 = instancesNode.getChildren();
                    while (it2.hasNext()) {
                        XiNode instanceNode = (XiNode) it2.next();
                        dpd.m_instances.add(instanceNode.getStringValue());
                    }
                }

                /* Add in RDF fields */
                String transitive = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.TRANSITIVE);
                String superProp = pdNode.getAttributeStringValue(DefaultMutablePropertyDefinition.SUPER);

                dpd.m_super = superProp;
                dpd.m_isTransitive = Boolean.valueOf(transitive).booleanValue();

                XiNode equivalentsNode = XiChild.getChild(root, ExpandedName.makeName("equivalents"));
                if (equivalentsNode != null) {
                    Iterator it2 = equivalentsNode.getChildren();
                    while (it2.hasNext()) {
                        XiNode equivalentNode = (XiNode) it2.next();
                        dpd.m_equivalenceSet.add(equivalentNode.getStringValue());
                    }
                }

                XiNode disjointsNode = XiChild.getChild(root, ExpandedName.makeName("disjoints"));
                if (disjointsNode != null) {
                    Iterator it2 = disjointsNode.getChildren();
                    while (it2.hasNext()) {
                        XiNode disjointNode = (XiNode) it2.next();
                        dpd.m_disjointSet.add(disjointNode.getStringValue());
                    }
                }
            }

            /* Resolve the orders of any missing numbers. */
            int largest = -1;
            Collection props = dc.getLocalPropertyDefinitions();
            Collection reset = new ArrayList();
            it = props.iterator();
            while (it.hasNext()) {
                DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) it.next();
                if (dpd.getOrder() > largest) {
                    largest = dpd.getOrder();
                }
                if (dpd.getOrder() == -1) {
                    reset.add(dpd);
                }
            }

            largest++;

            it = reset.iterator();
            while (it.hasNext()) {
                DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) it.next();
                dpd.setOrder(largest);
                largest++;
            }
        }

        XiNode referringPropsNode = XiChild.getChild(root, REFERRING_PROPS_NAME);
        if (referringPropsNode != null) {
            for (it = XiChild.getIterator(referringPropsNode, REFERRING_CONCEPT_NAME); it.hasNext();) {
                XiNode refConceptNode = (XiNode) it.next();
                String refConceptPath = refConceptNode.getAttributeStringValue(REFERRING_CONCEPT_PATH_NAME);

                LinkedHashSet propNames = new LinkedHashSet();
                Iterator propNameIt = refConceptNode.getChildren();
                while (propNameIt.hasNext()) {
                    XiNode refPropNode = (XiNode) propNameIt.next();
                    String propName = refPropNode.getStringValue();
                    propNames.add(propName);
                }

                dc.m_referringPropsMap.put(refConceptPath, propNames);
            }
        }

        //XiNode ruleSetsNode = XiChild.getChild(root, AbstractMutableRuleParticipant.REFERRING_RULESETS_NAME);
        //AbstractMutableRuleParticipant.rulesFromXiNode(dc, ruleSetsNode);

        // Get the RuleSet from the child
        // Get a special version of the RuleSet that is not a first class object (lives in Concept)
        dc.m_ruleSet = new DefaultMutableStateMachineRuleSet(dc, dc.getFullPath(), null, conceptFolder, name);
        XiNode ruleSetNode = XiChild.getChild(root, ExpandedName.makeName("ruleset"));

        if (ruleSetNode != null) { //SS ADDED. For existing RepoTypes, they dont have
            XiNode rulesNode = XiChild.getChild(ruleSetNode, DefaultMutableRuleSet.RULES_NAME);
            Iterator rulesIt = rulesNode.getChildren();
            while (rulesIt.hasNext()) {
                XiNode ruleNode = (XiNode) rulesIt.next();
                DefaultMutableRule.createDefaultRuleFromNode(ruleNode, dc.m_ruleSet);
            }//endwhile
        }

        dc.m_stateMachines = createListFromXiNodes(root, STATE_MACHINES);
        String mainStateMachineGUID = root.getAttributeStringValue(ROOT_STATE_MACHINE_GUID);
        if (dc.m_stateMachines != null) {
            for (MutableStateMachine stateMachine : new ArrayList<MutableStateMachine>(dc.m_stateMachines)) {
//                if (!(stateMachine instanceof MutableStandaloneStateMachine)) {
                    stateMachine.setOwnerConcept(dc);
                    if (stateMachine.getGUID().equals(mainStateMachineGUID)) {
                        dc.setMainStateMachine(stateMachine);
                    }
            }
        }

        return dc;
    }


    public void clear() {
        m_ruleSet.clear();
    }


    public Map getAllExtendedProperties() {
        final Map props = new HashMap();
        final DefaultMutableConcept parent = (DefaultMutableConcept) this.getSuperConcept();
        if (null != parent) {
            props.putAll(parent.getAllExtendedProperties());
        }
        props.putAll(this.getExtendedProperties());
        return props;
    }


    public void setExtendedProperties(Map props) {
        super.setExtendedProperties(props);
//        if (null == props) {
//            this.m_extendedProperties = new LinkedHashMap();
//            final Map<String, Object> bsProps = new LinkedHashMap<String, Object>();
//            bsProps.put("hasBackingStore", "true");
//            bsProps.put("Table Name", "");
//            this.m_extendedProperties.put("Backing Store", bsProps);
//        } else {
//            super.setExtendedProperties(props);
//        }
    }


    /**
     * Rule name is always the full path ie StateMachine + State + <TypeofRule>
     *
     * @param name
     * @param renameOnConflict
     * @return
     * @throws ModelException
     */
    public MutableRule createRule(String name, boolean renameOnConflict, boolean isARuleFunction) throws ModelException {
        return this.m_ruleSet.createRule(name, renameOnConflict, isARuleFunction);
    }


    public void deleteRule(String name) {
        m_ruleSet.deleteRule(name);
    }


    public RulesetEntry getRule(String name) {
        return m_ruleSet.getRule(name);
    }


    public List getRules() {
        return m_ruleSet.getRules();
    }


    public StateMachineRuleSet getStateMachineRuleSet() {
        return m_ruleSet;
    }


    /* ******************** End methods used by default implementation ************ */

    public static class DefaultMutableAttributeDefinition extends DefaultMutablePropertyDefinition {


        public DefaultMutableAttributeDefinition(DefaultMutableOntology ontology, String name, DefaultMutableConcept owner, boolean isArray, int typeFlag, String conceptTypePath, int historyPolicy, int historySize, String defValue, int order) {
            super(ontology, name, owner, isArray, typeFlag, conceptTypePath, historyPolicy, historySize, defValue, order);
        }
    }

    public boolean isPOJO() {
        return m_isPOJO;
    }

    public void setPOJO(boolean b) {
        m_isPOJO = b;
    }

    public void setPOJOImplClassName(String clazzName) {
        m_pojoImplClass = clazzName;
    }

    public String getPOJOImplClassName() {
        return m_pojoImplClass;
    }

    public boolean isMetric() {
    	return false;
    }

    public void enableMetricTracking() {
    }

    public void disableMetricTracking() {
    }

    public boolean isMetricTrackingEnabled() {
    	return false;
    }
}
