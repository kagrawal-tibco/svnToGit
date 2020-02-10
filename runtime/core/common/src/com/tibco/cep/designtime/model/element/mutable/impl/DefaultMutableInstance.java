/**
 * User: ishaan
 * Date: Mar 31, 2004
 * Time: 4:23:22 PM
 */
package com.tibco.cep.designtime.model.element.mutable.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.tibco.be.util.UniqueNamer;
import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.ConceptChangeListener;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutableConcept;
import com.tibco.cep.designtime.model.element.mutable.MutableInstance;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyInstance;
import com.tibco.cep.designtime.model.mutable.MutableEntity;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.helpers.XiChild;


public class DefaultMutableInstance extends AbstractMutableEntity implements MutableInstance, ConceptChangeListener {


    protected static final InstanceNameValidator DEFAULT_NAME_VALIDATOR = new InstanceNameValidator(null);

    protected String m_conceptPath;
    protected LinkedHashSet m_secondaryDomains;
    protected LinkedHashMap m_properties;
    protected LinkedHashSet m_views;


    public DefaultMutableInstance(DefaultMutableOntology ontology, String name, DefaultMutableFolder folder, String conceptPath) {
        super(ontology, folder, name);
        m_conceptPath = (conceptPath != null) ? conceptPath : "";
        m_secondaryDomains = new LinkedHashSet();
        m_properties = new LinkedHashMap();
        m_views = new LinkedHashSet();
    }


    public Set getViews() {
        return this.m_views;
    }


    public static DefaultMutableInstance createDefaultInstanceFromNode(XiNode root) throws ModelException {
        DefaultMutableInstance di = null;
        String folderPath = root.getAttributeStringValue(ExpandedName.makeName("folder"));
        String name = root.getAttributeStringValue(ExpandedName.makeName("name"));
        String conceptPath = root.getAttributeStringValue(ExpandedName.makeName("concept"));
        String guid = root.getAttributeStringValue(ExpandedName.makeName("guid"));

        DefaultMutableFolder rootFolder = new DefaultMutableFolder(null, null, String.valueOf(Folder.FOLDER_SEPARATOR_CHAR));
        DefaultMutableFolder instanceFolder = DefaultMutableOntology.createFolder(rootFolder, folderPath, false);

        di = new DefaultMutableInstance(null, name, instanceFolder, conceptPath);
        di.setGUID(guid);

        Iterator it;

        /** Add secondary Domains */
        XiNode domainsNode = XiChild.getChild(root, ExpandedName.makeName("domains"));
        if (domainsNode != null) {
            it = domainsNode.getChildren();
            while (it.hasNext()) {
                XiNode domainNode = (XiNode) it.next();
                String domainPath = domainNode.getStringValue();
                di.getSecondaryDomains().add(domainPath);
            }
        }

        /** Add InstanceView paths */
        XiNode viewsNode = XiChild.getChild(root, ExpandedName.makeName("views"));
        if (viewsNode != null) {
            it = viewsNode.getChildren();
            while (it.hasNext()) {
                XiNode viewNode = (XiNode) it.next();
                String viewPath = viewNode.getStringValue();
                di.m_views.add(viewPath);
            }
        }

        /** Add PropertyInstances */
        XiNode piNode = XiChild.getChild(root, ExpandedName.makeName("properties"));
        if (piNode != null) {
            it = piNode.getChildren();
            while (it.hasNext()) {
                XiNode instanceNode = (XiNode) it.next();
                String defName = instanceNode.getAttributeStringValue(ExpandedName.makeName("definition"));
                String value = instanceNode.getAttributeStringValue(ExpandedName.makeName("value"));
                String hasBeenSetStr = instanceNode.getAttributeStringValue(ExpandedName.makeName("hasBeenSet"));

                boolean hasBeenSet = ("true".equalsIgnoreCase(hasBeenSetStr));

                DefaultMutablePropertyInstance dpi = new DefaultMutablePropertyInstance(null, di, defName, hasBeenSet);
                dpi.m_value = value;

                Collection c = di.getPropertyInstances(defName);
                if (c == null) {
                    c = new ArrayList();
                    di.m_properties.put(defName, c);
                }
                c.add(dpi);
            }
        }

        return di;
    }


    public void setOntology(MutableOntology ontology) {
        super.setOntology(ontology);
        for (Iterator collections = m_properties.values().iterator(); collections.hasNext();) {
            final Collection c = (Collection) collections.next();
            for (Iterator propertyInstances = c.iterator(); propertyInstances.hasNext();) {
                final DefaultMutablePropertyInstance dpi = (DefaultMutablePropertyInstance) propertyInstances.next();
                dpi.setOntology(m_ontology);
            }//for
        }//for
    }//setOntology


    public void delete() {
        String fullPath = this.getFullPath();

        // Removes itself from its Concept
        DefaultMutableConcept concept = (DefaultMutableConcept) this.getConcept();
        if (concept != null) {
            concept.getInstances().remove(fullPath);
        }

        // Deletes its PropertyInstances
        final Collection propertyInstanceCollections = this.getAllPropertyInstances();
        final Set toBeDeleted = new HashSet();
        for (Iterator collections = propertyInstanceCollections.iterator(); collections.hasNext();) {
            final Collection propertyCollection = (Collection) collections.next();
            for (Iterator props = propertyCollection.iterator(); props.hasNext();) {
                final Object property = props.next();
                toBeDeleted.add(property);
            }//for
        }//for
        for (Iterator it = toBeDeleted.iterator(); it.hasNext();) {
            final MutablePropertyInstance prop = (MutablePropertyInstance) it.next();
            prop.delete();
        }//for

        // Removes itself from its Secondary Domains
        Iterator domainIt = this.getSecondaryDomains().iterator();
        while (domainIt.hasNext()) {
            DefaultMutableFolder domain = (DefaultMutableFolder) domainIt.next();
            domain.removeAbstractEntity(this);
        }

        // Removes itself from its views
        if (null != this.m_ontology) {
            for (Iterator viewIt = this.m_views.iterator(); viewIt.hasNext();) {
                String viewPath = (String) viewIt.next();
                DefaultMutableConceptView dcv = (DefaultMutableConceptView) this.m_ontology.getConceptView(viewPath);
                if (dcv != null) {
                    dcv.removeReference(fullPath);
                }
            }
        }

        // Removes itself from its Folder
        DefaultMutableFolder df = (DefaultMutableFolder) this.getFolder();
        df.removeAbstractEntity(this);

        // Removes itself from its Ontology
        if (null != this.m_ontology) {
            this.m_ontology.removeEntity(this);
        }

        this.notifyListeners();
    }


    public void setName(String name, boolean renameOnConflict) throws ModelException {
        BEModelBundle bundle = BEModelBundle.getBundle();

        if (name == null || name.length() == 0) {
            String msg = bundle.getString(AbstractMutableEntity.EMPTY_NAME_KEY);
            throw new ModelException(msg);
        }
        if (name.equals(m_name)) {
            return;
        }

        /** Check if all the domains will allow the rename **/
        String uniqueName;
        DEFAULT_NAME_VALIDATOR.setInstance(this);
        uniqueName = UniqueNamer.generateUniqueName(name, DEFAULT_NAME_VALIDATOR);

        /**
         * If we allow automatic renaming, then continue on with the unique name
         * Otherwise, if the uniqueName is not the same as the suggested name, throw an Exception
         */
        if (renameOnConflict) {
            name = uniqueName;
        } else if (!name.equals(uniqueName)) {
            String msg = bundle.formatString(AbstractMutableEntity.NAME_CONFLICT_KEY, name, m_folder);
            throw new ModelException(msg);
        }

        /** Re-register with all of our domains under the correct name **/
        DefaultMutableFolder folder = (DefaultMutableFolder) getFolder();
        //folder.m_entities.remove(m_name);
        //folder.m_entities.put(name, this);
        folder.removeAbstractEntity(this);


        for (Iterator it = m_secondaryDomains.iterator(); it.hasNext();) {
            DefaultMutableFolder domain = (DefaultMutableFolder) it.next();
            //domain.m_entities.remove(m_name);
            //domain.m_entities.put(name, this);
            domain.removeAbstractEntity(this);
        }


        String oldPath = getFullPath();
        m_name = name;
        String newPath = getFullPath();


        folder.addAbstractEntity(this);
        for (Iterator it = m_secondaryDomains.iterator(); it.hasNext();) {
            DefaultMutableFolder domain = (DefaultMutableFolder) it.next();
            domain.addAbstractEntity(this);
        }

        // Re-registers with the views
        for (Iterator viewsIt = m_views.iterator(); viewsIt.hasNext();) {
            final String viewPath = (String) viewsIt.next();
            final DefaultMutableMutableInstanceView view = (DefaultMutableMutableInstanceView) m_ontology.getConceptView(viewPath);
            if (null != view) {
                // ref should not be null
                final DefaultMutableInstanceReference ref = (DefaultMutableInstanceReference) view.getReference(oldPath);
                if (null != ref) {
                    view.removeReference(oldPath);
                    ref.setEntityPath(newPath);
                    view.addEntityReference(ref);
                }
            }
        }

        /** Re-register with the ontology */
        m_ontology.removeEntity(oldPath);
        m_ontology.addEntity(this);
    }


    public void setFolder(MutableFolder f) throws ModelException {
        m_ontology.setPrimaryDomain(this, f);
    }


    public void setFolderPath(String fullDomainName) throws ModelException {
        this.setFolder((MutableFolder) m_ontology.getFolder(fullDomainName));
    }


    public void addSecondaryDomain(String path) throws ModelException {
        m_ontology.addSecondaryDomain(this, path);
    }


    public void removeSecondaryDomain(String path) {
        m_ontology.removeSecondaryDomain(this, path);
    }


    public Collection getSecondaryDomains() {
        return m_secondaryDomains;
    }


    public Collection getAllPropertyInstances() {
        return m_properties.values();
    }


    public Collection getPropertyInstances(PropertyDefinition definition) {
        if (definition == null) {
            return null;
        }
        return (Collection) m_properties.get(definition.getName());
    }


    public List getPropertyInstances(String propertyName) {
        return (List) m_properties.get(propertyName);
    }


    public void deletePropertyInstance(MutablePropertyInstance propertyInstance) {
        m_ontology.deletePropertyInstance(propertyInstance);
    }


    public void deletePropertyInstance(String name, int index) {
        m_ontology.deletePropertyInstance(this, name, index);
    }


    public void deletePropertyInstances(MutablePropertyDefinition pd) {
        m_ontology.deletePropertyInstances(pd);
    }


    public MutablePropertyInstance createPropertyInstance(String propertyName) throws ModelException {
        if (ModelUtils.IsEmptyString(propertyName)) {
            throw new ModelException("bad.createPropertyInstance");
        }

        DefaultMutableConcept dc = (DefaultMutableConcept) getConcept();
        if (dc == null) {
            throw new ModelException("bad.createPropertyInstance");
        }


        DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) dc.getPropertyDefinition(propertyName, false);
        if (dpd == null) {
            throw new ModelException("bad.createPropertyInstance");
        }

        if (!canInstantiate(propertyName)) {
            throw new ModelException("bad.createPropertyInstance");
        }

        DefaultMutablePropertyInstance dpi = new DefaultMutablePropertyInstance(m_ontology, this, propertyName, false);

        /** Add to appropriate set of PropertyInstances **/
        Collection c = (Collection) m_properties.get(propertyName);

        /** Create the set if necessary **/
        if (c == null) {
            c = new ArrayList();
            m_properties.put(propertyName, c);
        }
        c.add(dpi);

        /** Register the Instance with its PropertyDefinition **/
        dpd.m_instances.add(getFullPath());

        notifyListeners();
        notifyOntologyOnChange();
        return dpi;
    }


    public boolean canInstantiate(PropertyDefinition definition) {
        if (definition == null) {
            return false;
        }

        /** Make sure this definition actually exists in our owning Concept chain **/
        Concept c = getConcept();
        PropertyDefinition pd = c.getPropertyDefinition(definition.getName(), false);
        if (pd == null || !pd.equals(definition)) {
            return false;
        }

        /** Find out how many instances we already have of the definition **/
        Collection col = (Collection) m_properties.get(definition.getName());

        if (col == null) {
            return true;
        } else if (!definition.isArray() && col.size() >= 1) {
            return false;
        }

        return true;
    }


    public boolean canInstantiate(String propertyName) {
        if (propertyName == null) {
            return false;
        }

        Concept c = getConcept();
        PropertyDefinition pd = c.getPropertyDefinition(propertyName, true);
        return canInstantiate(pd);
    }


    public Concept getConcept() {
        return m_ontology.getConcept(m_conceptPath);
    }


    public void setConcept(MutableConcept concept) throws ModelException {
        if ((null != concept)) {
            final DefaultMutableConcept dc = (DefaultMutableConcept) concept;

            final DefaultMutableConcept oldConcept = (DefaultMutableConcept) this.getConcept();
            if (oldConcept != null) {
                oldConcept.getInstances().remove(this.getFullPath());
            }
            dc.getInstances().add(this.getFullPath());
            this.m_conceptPath = dc.getFullPath();
            this.notifyListeners();
        }
    }


    public String toString() {
        StringBuffer me = new StringBuffer("DefaultInstance: " + m_folder + m_name + " (" + m_guid + ")");
        me.append("\nConcept: " + m_conceptPath);

        me.append("\nSecondary Domains:");
        if (m_secondaryDomains.size() > 0) {
            Iterator it = m_secondaryDomains.iterator();
            while (it.hasNext()) {
                me.append("\n\t");
                me.append(it.next());
            }
        }

        me.append("Local PropertyInstances:");
        if (m_properties.size() > 0) {
            Iterator it = m_properties.values().iterator();
            while (it.hasNext()) {
                me.append("\n\t");
                me.append(it.next());
            }
        }

        return me.toString();
    }


    public void entityChanged(MutableEntity entity) {
        if ((entity == null) || !(entity instanceof Concept)) {
            return;
        }

        DefaultMutableConcept dc = (DefaultMutableConcept) entity;

        /** Changed Concept */
        if (!dc.equals(getConcept())) {
            m_conceptPath = dc.getFullPath();
        }
    }


    public void entityAdded(MutableEntity entity) {
    }


    public void entityRemoved(MutableEntity entity) {
    }


    public void entityRenamed(MutableEntity entity, String oldName) {
    }


    public void entityMoved(MutableEntity entity, String oldPath) {
    }


    public XiNode toXiNode(XiFactory factory) {
        XiNode root = super.toXiNode(factory, "instance");
        XiAttribute.setStringValue(root, "folder", m_folder.toString());
        XiAttribute.setStringValue(root, "concept", m_conceptPath);

        XiNode domains = root.appendElement(ExpandedName.makeName("domains"));
        Iterator domainIt = this.m_secondaryDomains.iterator();
        while (domainIt.hasNext()) {
            String folderPath = (String) domainIt.next();
            XiNode domain = domains.appendElement(ExpandedName.makeName("domain"));
            domain.setStringValue(folderPath);
        }

        XiNode properties = root.appendElement(ExpandedName.makeName("properties"));
        Iterator propertiesIt = this.m_properties.values().iterator();
        while (propertiesIt.hasNext()) {
            List propList = (List) propertiesIt.next();
            Iterator propListIt = propList.iterator();
            while (propListIt.hasNext()) {
                DefaultMutablePropertyInstance dpi = (DefaultMutablePropertyInstance) propListIt.next();
                properties.appendChild(dpi.toXiNode(factory));
            }
        }

        XiNode views = root.appendElement(ExpandedName.makeName("views"));
        Iterator viewsIt = this.m_views.iterator();
        while (viewsIt.hasNext()) {
            String viewPath = (String) viewsIt.next();
            XiNode view = views.appendElement(ExpandedName.makeName("view"));
            view.setStringValue(viewPath);
        }

        return root;
    }


    public void pathChanged(MutableConcept concept, String oldPath, String newPath) {
        if (oldPath.equals(m_conceptPath)) {
            m_conceptPath = newPath;
        }
    }


    /**
     * Need to find out if any PropertyInstances need to be deleted
     */
    public void propertyCardinalityChanged(MutableConcept concept, String definitionName, int min, int max) {
        DefaultMutableConcept dc = (DefaultMutableConcept) concept;

        /** Get the PropertyDefinition */
        DefaultMutablePropertyDefinition dpd = (DefaultMutablePropertyDefinition) dc.getPropertyDefinition(definitionName, false);

        HashSet deleteSet = new HashSet();

        Iterator it = this.getPropertyInstances(dpd).iterator();
        while (it.hasNext()) {
            DefaultMutableInstance di = (DefaultMutableInstance) it.next();
            ArrayList propertyInstances = (ArrayList) di.m_properties.get(m_name);

            /** Add all excess PropertyInstances to the delete List */
            if (propertyInstances.size() > 1) {
                for (int i = 1; i < propertyInstances.size(); i++) {
                    deleteSet.add(propertyInstances.get(i));
                }
            }
        }

        it = deleteSet.iterator();
        while (it.hasNext()) {
            m_ontology.deletePropertyInstance((MutablePropertyInstance) it.next());
        }
    }


    public void propertyTypeChanged(MutableConcept concept, int typeFlag, Concept conceptType) {
    }


    public void propertyDefinitionAdded(MutableConcept concept, MutablePropertyDefinition definition) {
    }


    public void propertyDefinitionRemoved(MutableConcept concept, MutablePropertyDefinition definition) {
    }


    public void inheritanceChanged(MutableConcept concept, MutableConcept oldSuper, MutableConcept newSuper) {
    }


    static class InstanceNameValidator implements UniqueNamer.NameValidator {


        protected DefaultMutableInstance m_instance;
        protected HashSet m_nameSet;


        public InstanceNameValidator(DefaultMutableInstance instance) {
            m_instance = instance;
            m_nameSet = new HashSet();
            populateNameSet();
        }


        public void setInstance(DefaultMutableInstance instance) {
            m_instance = instance;
            populateNameSet();
        }


        protected void populateNameSet() {
            if (m_instance == null) {
                return;
            }
            m_nameSet.clear();
            addNamesFromDomain((DefaultMutableFolder) m_instance.getFolder());

            Iterator it = m_instance.getSecondaryDomains().iterator();
            while (it.hasNext()) {
                DefaultMutableFolder df = (DefaultMutableFolder) it.next();
                addNamesFromDomain(df);
            }
        }


        protected void addNamesFromDomain(DefaultMutableFolder domain) {
            Collection c = domain.getEntities(false);
            Iterator it = c.iterator();
            while (it.hasNext()) {
                Entity e = (Entity) it.next();
                m_nameSet.add(e.getName());
            }
        }


        public DefaultMutableInstance getInstance() {
            return m_instance;
        }


        public boolean isNameUnique(String name) {
            return !m_nameSet.contains(name);
        }
    }
}