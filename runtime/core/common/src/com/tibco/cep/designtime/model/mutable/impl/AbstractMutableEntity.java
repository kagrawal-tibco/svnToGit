/**
 * User: ishaan
 * Date: Mar 26, 2004
 * Time: 4:04:18 PM
 */
package com.tibco.cep.designtime.model.mutable.impl;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;

import com.tibco.be.util.BEResourceBundle;
import com.tibco.be.util.GUIDGenerator;
import com.tibco.be.util.UniqueNamer;
import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.EntityChangeListener;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelError;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.Validatable;
import com.tibco.cep.designtime.model.XiSerializable;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateEntity;
import com.tibco.cep.designtime.model.mutable.MutableEntity;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.helpers.XiChild;


public abstract class AbstractMutableEntity implements MutableEntity,XiSerializable {


    public static final String NAME_CONFLICT_KEY = "AbstractEntity.setName.nameConflict";

    public static final String EMPTY_NAME_KEY = "AbstractEntity.setName.emptyString";

    protected String m_name;
    protected DefaultMutableOntology m_ontology;
    protected LinkedHashMap m_listeners;
    protected String m_guid;
    public String m_folder;
    protected String m_description;
    protected String m_iconPath;
    protected String m_bindings;
    protected String m_namespace;
    protected transient LinkedHashMap m_transientProperties;
    protected LinkedHashMap m_hiddenProperties;
    protected String m_lastModified;
    protected Map m_extendedProperties;

    protected static final BEResourceBundle RESOURCE_BUNDLE = BEModelBundle.getBundle();

    public static final ExpandedName NAME_NAME = ExpandedName.makeName("name");
    public static final ExpandedName DESCRIPTION_NAME = ExpandedName.makeName("description");
    public static final ExpandedName FOLDER_NAME = ExpandedName.makeName("folder");
    public static final ExpandedName EXTENDED_PROPERTIES_NAME = ExpandedName.makeName("extendedProperties");
    public static final ExpandedName EXTENDED_PROPERTY_NAME = ExpandedName.makeName("extendedProperty");
    public static final ExpandedName VALUE_NAME = ExpandedName.makeName("value");


    public void delete() {
        if (m_ontology != null) {
            m_ontology.notifyEntityDeleted(this);
            m_ontology.unregisterAbstractEntity(this);
        }//if
        if (m_listeners != null) {
            notifyListeners();
            m_listeners.clear();
        }//if
    }//delete


    public String getLastModified() {
        return m_lastModified;
    }

    public String getAlias() {
        return (String)m_extendedProperties.get("alias");
    }


    public void setLastModified(String xsDateTime) {
        m_lastModified = xsDateTime;
    }


    public AbstractMutableEntity(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name) {
        m_ontology = ontology;
        m_folder = (folder == null) ? "" : folder.getFullPath();
        m_name = name;
        m_guid = GUIDGenerator.getUniqueURI();
        m_description = "";
        m_iconPath = "";
        m_bindings = "";
        m_namespace = "";
        m_transientProperties = new LinkedHashMap();
        m_hiddenProperties = new LinkedHashMap();
        this.setExtendedProperties(null);
//        m_lastModified = XsDateTime.currentDateTime().castAsString();
        m_lastModified = "";
    }


    public String getNamespace() {
        if (!ModelUtils.IsEmptyString(m_namespace)) {
            return m_namespace;
        }
        if (m_folder == null) {
            return "";
        }
        return m_folder;
    }


    public void setNamespace(String namespace) {
        m_namespace = namespace;
        if (m_namespace == null) {
            m_namespace = "";
        }
    }


    public void setName(String name, boolean renameOnConflict) throws ModelException {
        if (name == null || name.length() == 0) {
            throw new ModelException(RESOURCE_BUNDLE.getString(EMPTY_NAME_KEY));
        }

        if (name.equals(m_name)) {
            return;
        }

        /** Since some Entities exist without Folders, we need to check if the folder exists **/
        DefaultMutableFolder folder = (DefaultMutableFolder) getFolder();
        if (folder != null) {
            String oldPath = getFullPath();

            /** Check if folder allows rename **/
            Entity oldEntity = folder.getEntity(name, false);
            if (oldEntity != null) {
                if (oldEntity.equals(this)) {
                    return;
                } else if (renameOnConflict) {
                    EntityNameValidator env = EntityNameValidator.DEFAULT_INSTANCE;
                    env.setOntology(m_ontology);
                    env.setFolder(folder);
                    env.setFolderIsBeingNamed(false);
                    name = UniqueNamer.generateUniqueName(name, env);
                } else {
                    throw new ModelException(RESOURCE_BUNDLE.formatString(NAME_CONFLICT_KEY, name, m_folder));
                }
            }

            /* Unregister from under the old name from our folder */
            folder.m_entities.remove(m_name);
            m_ontology.m_entities.remove(oldPath);

            String newPath = m_folder + name;

            /* Register under new name with Package */
            folder.m_entities.put(name, this);
            m_ontology.m_entities.put(newPath, this);

        }
        String oldName = m_name;
        m_name = name;
        if (m_ontology != null) {
            m_ontology.notifyEntityRenamed(this, oldName);
        }
    }


    public String getDescription() {
        return m_description;
    }


    public void notifyOntologyOnChange() {
        if (m_ontology != null) {
            m_ontology.notifyEntityChanged(this);
        }
    }


    protected void notifyOntologyOnRename(String oldName) {
        if (m_ontology != null) {
            m_ontology.notifyEntityRenamed(this, oldName);
        }
    }


    public void setDescription(String description) {
        if (description == null) {
            m_description = "";
        }
        m_description = description;
    }


    public Ontology getOntology() {
        return m_ontology;
    }


    public void setOntology(MutableOntology ontology) {
        try {
            m_ontology = (DefaultMutableOntology) ontology;
            if (!ModelUtils.IsEmptyString(m_folder)) {
                DefaultMutableFolder folder = (DefaultMutableFolder) m_ontology.createFolder(m_folder, false);
                folder.m_entities.put(getName(), this);
                String path = getFullPath();
                m_ontology.m_entities.put(path, this);
                m_ontology.addAlias(this.getAlias(), this);
                m_ontology.notifyEntityAdded(this);
            }
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }


    public String getGUID() {
        return m_guid;
    }


    public void setGUID(String guid) {
        if ((null == guid) || (guid.trim().equals(""))) {
            this.m_guid = GUIDGenerator.getGUID();
        } else {
            this.m_guid = guid;
        }
    }


    public String getName() {
        return m_name;
    }


    public boolean isValid(boolean recurse) {
        return false;
    }


    public String getStatusMessage() {
        return null;
    }


    public Validatable[] getInvalidObjects() {
        return new Validatable[0];
    }


    /**
     * Returns a List of ModelError objects.  Each ModelError object contains
     * an invalid model object and a message describing why it is invalid.
     *
     * @return A List of ModelError objects.
     */
    public List getModelErrors() {
        return new ArrayList();
    }// end getModelErrors


    public void makeValid(boolean recurse) {
    }


    public void addEntityChangeListener(EntityChangeListener listener) {
        if (m_listeners == null) {
            m_listeners = new LinkedHashMap();
        }

        if (!m_listeners.containsKey(listener)) {
            m_listeners.put(listener, listener);
        }
    }


    public void removeEntityChangeListener(EntityChangeListener listener) {
        if (m_listeners != null) {
            m_listeners.remove(listener);
        }
    }


    public void notifyListeners() {
        if (m_listeners == null) {
            return;
        }

        Iterator it = m_listeners.values().iterator();
        while (it.hasNext()) {
            EntityChangeListener listener = (EntityChangeListener) it.next();
            listener.entityChanged(this);
        }
        if (m_ontology != null) {
            m_ontology.notifyEntityChanged(this);
        }
    }


    public Icon getIcon() {
        return null;
    }


    public String getIconPath() {
        return m_iconPath;
    }


    public void setIconPath(String path) {
        m_iconPath = (path == null) ? "" : path;
    }


    public void setTransientProperty(String key, Object value) {
        m_transientProperties.put(key, value);
    }


    public Object getTransientProperty(String key) {
        return m_transientProperties.get(key);
    }


    public void serialize(OutputStream out) throws IOException {
        DefaultMutableOntology.serialize(this, out);
    }


    public String getFullPath() {
        if (m_folder != null) {
            return m_folder + getName();
        }
        return "";
    }


    public String getFolderPath() {
        return m_folder;
    }


    public void setFolderPath(String fullPath) throws ModelException {
        this.setFolder((MutableFolder) m_ontology.createFolder(fullPath, false));
    }


    public Folder getFolder() {
        if (m_ontology == null) {
            return null;
        }
        return m_ontology.getFolder(m_folder);
    }


    public void setFolder(MutableFolder folder) throws ModelException {
        m_ontology.setEntityFolder(this, folder);
    }


    public static final ExpandedName LAST_MODIFIED_NAME = ExpandedName.makeName("lastModified");

    public static final ExpandedName HIDDEN_PROPERTIES_NAME = ExpandedName.makeName("hiddenProperties");
    public static final ExpandedName HIDDEN_PROPERTY_NAME = ExpandedName.makeName("hiddenProperty");
    public static final ExpandedName HIDDEN_PROPERTY_KEY_NAME = ExpandedName.makeName("key");
    public static final ExpandedName HIDDEN_PROPERTY_VALUE_NAME = ExpandedName.makeName("value");
    public static final ExpandedName GUID_NAME = ExpandedName.makeName("guid");


    protected XiNode toXiNode(XiFactory factory, String type) {
        XiNode root = factory.createElement(ExpandedName.makeName(type));
        XiAttribute.setStringValue(root, "icon", m_iconPath);
        XiAttribute.setStringValue(root, "name", m_name);
        XiAttribute.setStringValue(root, "description", m_description);
        XiAttribute.setStringValue(root, "guid", m_guid);
        XiAttribute.setStringValue(root, "bindings", m_bindings);
        XiAttribute.setStringValue(root, "namespace", m_namespace);

        XiNode lastModifiedNode = root.appendElement(LAST_MODIFIED_NAME);
        lastModifiedNode.setStringValue(m_lastModified);

        // Extended properties
        root.appendChild(createXiNodeFromExtendedProperties(this.getExtendedProperties()));

        XiNode hiddenProps = root.appendElement(HIDDEN_PROPERTIES_NAME);
        Set s = m_hiddenProperties.keySet();
        Iterator it = s.iterator();
        while (it.hasNext()) {
            XiNode hiddenProp = hiddenProps.appendElement(HIDDEN_PROPERTY_NAME);

            String key = (String) it.next();
            String value = getHiddenProperty(key);

            hiddenProp.setAttributeStringValue(HIDDEN_PROPERTY_KEY_NAME, key);
            if (value != null) {
                hiddenProp.setAttributeStringValue(HIDDEN_PROPERTY_VALUE_NAME, value);
            }
        }

        return root;
    }


    


    protected static BEResourceBundle getResourceBundle() {
        return RESOURCE_BUNDLE;
    }


    public String getBindingString() {
        return m_bindings;
    }


    public void setBindingString(String bindings) {
        if (bindings == null) {
            bindings = "";
        }
        m_bindings = bindings;
    }


    /**
     * Accept a List of objects that can have toXiNode called on them and add them as a set of children
     * to the parent XiNode passed in.
     *
     * @param factory  An XiNode factory.
     * @param parent   Where to add the XiNode children.
     * @param entities A list of entities (DefaultStateEntity extenders)
     * @param tag      Name to give the added XiNode.
     */
    public static void addXiNodesOfList(
            XiFactory factory,
            XiNode parent,
            List entities,
            ExpandedName tag) {
        if (entities != null) {
            Iterator entityIterator = entities.iterator();
            // Is there at least one element, if so add all elements as children
            if (entityIterator.hasNext()) {
                XiNode entityXiNode = factory.createElement(tag);
                while (entityIterator.hasNext()) {
                    DefaultMutableStateEntity entity = (DefaultMutableStateEntity) entityIterator.next();
                    entityXiNode.appendChild(entity.toXiNode(factory));
                }//endwhile
                parent.appendChild(entityXiNode);
            }//endif
        }//endif
    }// end addXiNodesOfList


    public static ArrayList createListFromXiNodes(
            XiNode parent,
            ExpandedName tag) {
        ArrayList result = null;
        try {
            XiNode entitiesXiNode = XiChild.getChild(parent, tag);
            if (entitiesXiNode != null) {
                Iterator entitiesIterator = entitiesXiNode.getChildren();
                if (entitiesIterator.hasNext()) {
                    result = new ArrayList();
                    while (entitiesIterator.hasNext()) {
                        XiNode entityXiNode = (XiNode) entitiesIterator.next();
                        // This cast isn't actually required, but it's safer since it forces an error for incorrect objects
                        StateEntity loadedEntity = (StateEntity) DefaultMutableOntology.createEntityFromNode(entityXiNode);
                        result.add(loadedEntity);
                    }//endwhile
                }//endif
            }//endif
        } catch (ModelException exception) {
            // todo rkt What should I do here?
            exception.printStackTrace();
        }//endtry
        return result;
    }// end createListFromXiNodes


    public String getHiddenProperty(String key) {
        return (String) m_hiddenProperties.get(key);
    }


    public void setHiddenProperty(String key, String value) {
        m_hiddenProperties.put(key, value);
    }


    public Map getHiddenProperties() {
        return m_hiddenProperties;
    }


    public Map getTransientProperties() {
        return m_transientProperties;
    }


    public void pathChanged(String oldPath, String newPath) throws ModelException {
    }


    /**
     * Add an error to the List passed in if the boolean condition is true.
     *
     * @param condition    If this condition is true the error will be added.
     * @param modelErrors  The List to add the error to.
     * @param errorMessage The message describing this error.
     */
    public void addErrorIfTrue(boolean condition, List modelErrors, String errorMessage) {
        if (condition) {
            modelErrors.add(new ModelError(this, errorMessage));
        }
    }


    public void addWarningIfTrue(boolean condition, List modelErrors, String errorMessage) {
        if (condition) {
            ModelError me = new ModelError(this, errorMessage);
            me.setIsWarning(true);
            modelErrors.add(me);
        }
    }

    public void setExtendedProperties(Map props) {
        if (null == props) {
            props=this.m_extendedProperties = new LinkedHashMap();
        }
        MutableOntology mo = (MutableOntology) this.getOntology();
        if (mo != null) { //mo can be null during deserialization.
            mo.removeAlias(this);
        }
        this.m_extendedProperties = props;
        if (mo != null) {
            mo.addAlias(this.getAlias(), this);
        }

        // Backing Store Properties
        Map bs= (Map) props.get(EXTPROP_ENTITY_BACKINGSTORE);
        if (bs == null) {
            bs = new LinkedHashMap();
            props.put(EXTPROP_ENTITY_BACKINGSTORE, bs);
        }
        // Get the table name
        String tableName= (String) bs.get(EXTPROP_ENTITY_BACKINGSTORE_TABLENAME);
        if (tableName == null) {
            bs.put(EXTPROP_ENTITY_BACKINGSTORE_TABLENAME, "");
        }

        String typeName= (String) bs.get(EXTPROP_ENTITY_BACKINGSTORE_TYPENAME);
        if (typeName == null) {
            bs.put(EXTPROP_ENTITY_BACKINGSTORE_TYPENAME, "");
        }

        String hasBackingStore= (String) bs.get(EXTPROP_ENTITY_BACKINGSTORE_HASBACKINGSTORE);
        if (hasBackingStore == null) {
            bs.put(EXTPROP_ENTITY_BACKINGSTORE_HASBACKINGSTORE, "true");
        }

        Map cs= (Map) props.get(EXTPROP_ENTITY_CACHE);
        if (cs == null) {
            cs = new LinkedHashMap();
            props.put(EXTPROP_ENTITY_CACHE, cs);
        }

        String constant= (String) cs.get(EXTPROP_ENTITY_CACHE_CONSTANT);
        if (constant == null) {
            cs.put(EXTPROP_ENTITY_CACHE_CONSTANT, "false");
        }

        String preloadAll= (String) cs.get(EXTPROP_ENTITY_CACHE_PRELOAD_ALL);
        if (preloadAll == null) {
            cs.put(EXTPROP_ENTITY_CACHE_PRELOAD_ALL, "false");
        }


        String fetchSize= (String) cs.get(EXTPROP_ENTITY_CACHE_PRELOAD_FETCHSIZE);

        if (fetchSize == null) {
            cs.put(EXTPROP_ENTITY_CACHE_PRELOAD_FETCHSIZE, "0");
        }

        String requiresVersionCheck= (String) cs.get(EXTPROP_ENTITY_CACHE_REQUIRESVERSIONCHECK);
        if (requiresVersionCheck == null) {
            cs.put(EXTPROP_ENTITY_CACHE_REQUIRESVERSIONCHECK, "true");
        }

        String isCacheLimited= (String) cs.get(EXTPROP_ENTITY_CACHE_ISCACHELIMITED);
        if (isCacheLimited == null) {
            cs.put(EXTPROP_ENTITY_CACHE_ISCACHELIMITED, "true");
        }

        String evictOnUpdate= (String) cs.get(EXTPROP_ENTITY_CACHE_EVICTONUPDATE);
        if (evictOnUpdate == null) {
            cs.put(EXTPROP_ENTITY_CACHE_EVICTONUPDATE, "true");
        }
    }


    public Map getExtendedProperties() {
        return this.m_extendedProperties;
    }


    public static Map createExtendedPropsFromXiNode(XiNode extPropsNode) {
        final Map props = new LinkedHashMap();
        if (null != extPropsNode) {
            for (Iterator it = extPropsNode.getChildren(); it.hasNext();) {
                final XiNode node = (XiNode) it.next();
                final ExpandedName xname = node.getName();
                final String name = node.getAttributeStringValue(NAME_NAME);
                if (null != name) {
                    if (EXTENDED_PROPERTIES_NAME.equals(xname)) {
                        props.put(name, createExtendedPropsFromXiNode(node));
                    }
                    else if (EXTENDED_PROPERTY_NAME.equals(xname)) {
                        props.put(name, node.getAttributeStringValue(VALUE_NAME));
                    }
                }
            }
        }
        return props;
    }

    public static XiNode createXiNodeFromExtendedProperties(Map extProps) {
        final XiFactory factory = XiFactoryFactory.newInstance();
        final XiNode props = factory.createElement(EXTENDED_PROPERTIES_NAME);
        if (null == extProps)  {
            return props;
        }

        for (Iterator it = new LinkedHashMap(extProps).entrySet().iterator(); it.hasNext(); ) {
            final Map.Entry entry = (Map.Entry) it.next();
            final Object k = entry.getKey();
            if (null != k) {
                final Object value = entry.getValue();
                XiNode node;
                if (value instanceof Map) {
                    node = props.appendChild(createXiNodeFromExtendedProperties((Map) value));
                } else {
                    node = props.appendElement(EXTENDED_PROPERTY_NAME);
                    node.setAttributeStringValue(VALUE_NAME,  String.valueOf(value));
                }
                node.setAttributeStringValue(NAME_NAME, String.valueOf(k));
            }
        }

        return props;
    }
    public static void setExtendedProperty(MutableEntity entity, Object propName, Object propVal) {
    	entity.getExtendedProperties().put(propName, propVal);
        String s = entity.getAlias();
        if (s != null) {
            MutableOntology mo = (MutableOntology) entity.getOntology();
            mo.addAlias(s, entity);
        }

    }
    public static Object getExtendedProperty(Entity entity, Object propName) {
    	return entity.getExtendedProperties().get(propName);
    }
}
