/**
 * User: ishaan
 * Date: Mar 26, 2004
 * Time: 2:49:46 PM
 */
package com.tibco.cep.designtime.model.mutable.impl;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.tibco.be.util.UniqueNamer;
import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DefaultMutableFolder extends AbstractMutableEntity implements MutableFolder {


    protected DefaultMutableFolder m_parent;
    protected LinkedHashMap m_children;
    protected LinkedHashMap m_entities;


    public DefaultMutableFolder(DefaultMutableOntology ontology, DefaultMutableFolder parent, String shortName) {
        super(ontology, parent, shortName);
        this.m_parent = parent;
        this.m_children = new LinkedHashMap();
        this.m_entities = new LinkedHashMap();
    }


    public void delete() {
        /** Can't use iterators, because we'll get a ConcurrentModificationException */
        Object[] o = m_entities.values().toArray();
        for (int i = 0; i < o.length; i++) {
            AbstractMutableEntity ae = (AbstractMutableEntity) o[i];
            ae.delete();
        }

        o = m_children.values().toArray();
        for (int i = 0; i < o.length; i++) {
            DefaultMutableFolder df = (DefaultMutableFolder) o[i];
            df.delete();
        }

        m_parent.m_children.remove(m_name);
        if (m_ontology != null) {
            m_ontology.notifyEntityDeleted(this);
        }
    }


    public void pathChanged(String oldPath, String newPath) throws ModelException {
        /** Tell all the Entities that we store about our new path */
        Iterator it = m_entities.values().iterator();
        while (it.hasNext()) {
            AbstractMutableEntity ae = (AbstractMutableEntity) it.next();

            String oldEntityPath = ae.getFullPath();
            ae.m_folder = newPath;
            String newEntityPath = ae.getFullPath();

            m_ontology.m_entities.remove(oldEntityPath);
            m_ontology.m_entities.put(newEntityPath, ae);

            ae.pathChanged(oldEntityPath, newEntityPath);
        }

        /** Tell all of our sub-folders to tell their new paths */
        it = m_children.values().iterator();
        while (it.hasNext()) {
            DefaultMutableFolder child = (DefaultMutableFolder) it.next();
            String newChildPath = child.getFullPath();
            child.pathChanged(null, newChildPath);
        }

    }


    public void setOntology(MutableOntology ontology) {
        Iterator it = m_children.values().iterator();
        while (it.hasNext()) {
            DefaultMutableFolder df = (DefaultMutableFolder) it.next();
            df.setOntology(ontology);
        }

        super.setOntology(ontology);
    }


    public Folder getParent() {
        return m_parent;
    }


    public void setParent(MutableFolder parent) throws ModelException {
        m_ontology.setFolderParent(this, parent);
    }


    public Collection getSubFolders() {
        return m_children.values();
    }


    public Folder getSubFolder(String shortName) {
        return (DefaultMutableFolder) m_children.get(shortName);
    }


    public MutableFolder createSubFolder(String shortName, boolean autoNameOnConflict) throws ModelException {
        DefaultMutableFolder subFolder = (DefaultMutableFolder) getSubFolder(shortName);

        if (subFolder != null) {
            if (autoNameOnConflict) {
                EntityNameValidator env = EntityNameValidator.DEFAULT_INSTANCE;
                env.setOntology(m_ontology);
                env.setFolder(this);
                env.setFolderIsBeingNamed(true);
                shortName = UniqueNamer.generateUniqueName(shortName, env);
            } else {
                return subFolder;
            }
        }

        subFolder = new DefaultMutableFolder(m_ontology, this, shortName);
        m_children.put(shortName, subFolder);

        return subFolder;
    }


    public void deleteSubFolder(String shortName) {
        DefaultMutableFolder child = (DefaultMutableFolder) m_children.get(shortName);
        if (child != null) {
            child.delete();
        }
    }


    public boolean hasPredecessor(Folder f) {
        DefaultMutableFolder predecessor = m_parent;
        while (predecessor != null) {
            if (predecessor == f) {
                return true;
            }
            predecessor = (DefaultMutableFolder) predecessor.getParent();
        }

        return false;
    }


    public boolean hasSibling(Folder f) {
        if (m_parent == null || this == f) {
            return false;
        }
        return m_parent.hasChild(f);
    }


    public boolean hasDescendant(Folder f) {
        if (hasChild(f)) {
            return true;
        }

        Iterator it = m_children.values().iterator();
        while (it.hasNext()) {
            DefaultMutableFolder child = (DefaultMutableFolder) it.next();
            if (child.hasDescendant(f)) {
                return true;
            }
        }
        return false;
    }


    public boolean hasChild(Folder f) {
        return (m_children.get(f.getName()) != null);
    }


    public Collection getFolderList() {
        ArrayList list = new ArrayList();

        DefaultMutableFolder df = this;
        do {
            list.add(0, df);
            df = (DefaultMutableFolder) df.getParent();
        } while (df != null);

        return list;
    }


    public List getEntities(boolean includeSubFolders) {
        final ArrayList entities = new ArrayList(m_entities.size());
        for (Iterator it = m_entities.values().iterator(); it.hasNext();) { // Iterator to preserve order
            entities.add(it.next());
        }
        if (includeSubFolders) {
            for (Iterator it = m_children.values().iterator(); it.hasNext();) { // Iterator to preserve order
                entities.add(it.next());
            }
        }
        return entities;
    }


    public String toString() {
        StringBuffer sb = new StringBuffer();

        Iterator it = getFolderList().iterator();
        while (it.hasNext()) {
            DefaultMutableFolder af = (DefaultMutableFolder) it.next();
            sb.append(af.getName());
            if (af.getParent() != null) {
                sb.append(DefaultMutableFolder.FOLDER_SEPARATOR_CHAR);
            }
        }

        return sb.toString();
    }


    public static final String ROOT_SET_NAME_KEY = "DefaultFolder.setName.cantNameRootFolder";
    public static final String NAME_CONFLICT_KEY = "DefaultFolder.setName.nameConflict";


    public void setName(String name, boolean renameOnConflict) throws ModelException {
        BEModelBundle bundle = BEModelBundle.getBundle();

        if (name == null || name.length() == 0) {
            String msg = bundle.getString(AbstractMutableEntity.NAME_CONFLICT_KEY);
            throw new ModelException(msg);
        }
        if (name.equals(m_name)) {
            return;
        }

        DefaultMutableFolder root = (DefaultMutableFolder) m_ontology.getRootFolder();

        /** Can't set the name of the root */
        if (this == root) {
            String msg = bundle.getString(ROOT_SET_NAME_KEY);
            throw new ModelException(msg);
        }

        /** Can't set the name to one that already exists at this level */
        if (m_parent.getSubFolder(name) != null) {
            if (renameOnConflict) {
                EntityNameValidator env = EntityNameValidator.DEFAULT_INSTANCE;
                env.setOntology(m_ontology);
                env.setFolder(m_parent);
                env.setFolderIsBeingNamed(true);
                name = UniqueNamer.generateUniqueName(name, env);
            } else {
                String msg = bundle.formatString(NAME_CONFLICT_KEY, name, m_parent.getFullPath());
                throw new ModelException(msg);
            }
        }

        String oldPath = getFolderPath();

        /** Unregister this folder from its parent */
        m_parent.m_children.remove(m_name);

        /** Assign the new name */
        m_name = name;

        /** Register this folder under the new name */
        m_parent.m_children.put(m_name, this);

        String newPath = getFolderPath();

        pathChanged(oldPath, newPath);
    }


    public Entity getEntity(String name, boolean includeFolders) {
        Entity e = (Entity) m_entities.get(name);
        if (e == null && includeFolders) {
            e = (Entity) m_children.get(name);
        }
        return e;
    }


    /**
     * Override to do nothing
     */
    public void serialize(OutputStream out) throws IOException {
    }


    public String getFullPath() {
        return toString();
    }


    public Folder getFolder() {
        return this;
    }


    public String getFolderPath() {
        return toString();
    }


    public void setFolderPath(String fullPath) throws ModelException {
    }


    public void setFolder(MutableFolder folder) throws ModelException {
    }


    /**
     * Override to do nothing
     */
    public XiNode toXiNode(XiFactory factory) {
        return null;
    }


    /**
     * Default Implemenation methods
     */
    public void removeAbstractEntity(AbstractMutableEntity entity) {
        m_entities.remove(entity.getName());
    }


    /**
     * Default Implemenation methods
     */
    public void addAbstractEntity(AbstractMutableEntity entity) {
        m_entities.put(entity.getName(), entity);
    }
}