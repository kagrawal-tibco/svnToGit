/**
 * User: ishaan
 * Date: Apr 13, 2004
 * Time: 7:45:57 PM
 */
package com.tibco.cep.designtime.model.mutable.impl;


import com.tibco.be.util.UniqueNamer;


public class EntityNameValidator implements UniqueNamer.NameValidator {


    public static final EntityNameValidator DEFAULT_INSTANCE = new EntityNameValidator(null, null, false);

    private DefaultMutableFolder m_folder;
    private DefaultMutableOntology m_ontology;
    private boolean m_folderIsBeingNamed;


    public EntityNameValidator(DefaultMutableOntology ontology, DefaultMutableFolder folder, boolean renamingFolder) {
        m_ontology = ontology;
        m_folder = folder;
        m_folderIsBeingNamed = renamingFolder;
    }


    public boolean isNameUnique(String name) {
        if (m_folderIsBeingNamed) {
            return (null == m_folder.getSubFolder(name));
        }

        String key = m_folder.toString() + name;
        return (m_ontology.getEntity(key) == null);
    }


    public void setOntology(DefaultMutableOntology ontology) {
        m_ontology = ontology;
    }


    public DefaultMutableOntology getOntology() {
        return m_ontology;
    }


    public void setFolder(DefaultMutableFolder folder) {
        m_folder = folder;
    }


    public DefaultMutableFolder getFolder() {
        return m_folder;
    }


    public void setFolderIsBeingNamed(boolean folderIsBeingNamed) {
        m_folderIsBeingNamed = folderIsBeingNamed;
    }


    public boolean isFolderBeingNamed() {
        return m_folderIsBeingNamed;
    }
}

