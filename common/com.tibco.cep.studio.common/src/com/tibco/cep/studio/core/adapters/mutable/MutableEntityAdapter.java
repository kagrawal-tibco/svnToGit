package com.tibco.cep.studio.core.adapters.mutable;

import java.util.Map;

import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.studio.core.adapters.EntityAdapter;


public class MutableEntityAdapter <E extends com.tibco.cep.designtime.core.model.Entity>
	extends EntityAdapter<E>
	implements Entity {
	
//	protected String m_name;
//    protected Ontology m_ontology;
//    protected String m_guid;
//    public String m_folder;
//    protected String m_description;
//    protected String m_iconPath;
//    protected String m_bindings;
//    protected String m_namespace;
//    protected transient LinkedHashMap m_transientProperties;
//    protected LinkedHashMap m_hiddenProperties;
//    protected String m_lastModified;
//    protected Map m_extendedProperties;
//	protected E adapted;
//	protected Ontology emfOntology;
	
	public MutableEntityAdapter(E adapted, Ontology o) {
		super(adapted,o);
	}

//	public CGMutableEntityAdapter(Ontology ontology, Folder folder,String name) {
//		m_ontology = ontology;
//        m_folder = (folder == null) ? "" : folder.getFullPath();
//        m_name = name;
//        m_guid = GUIDGenerator.getUniqueURI();
//        m_description = "";
//        m_iconPath = "";
//        m_bindings = "";
//        m_namespace = "";
//        m_transientProperties = new LinkedHashMap();
//        m_hiddenProperties = new LinkedHashMap();
//        this.setExtendedProperties(null);
//        m_lastModified = "";
//	}
	
	@Override
	protected E getAdapted() {
		return adapted;
	}

//	@Override
//	public String getAlias() {
////		return (String)m_extendedProperties.get("alias");
//		List<com.tibco.cep.designtime.core.model.Entity> list = adapted.getExtendedProperties().getProperties();
//		for(com.tibco.cep.designtime.core.model.Entity item:list) {
//			if(item instanceof SimpleProperty) {
//				SimpleProperty sp = (SimpleProperty) item;
//				if(sp.getName().equals("alias")) {
//					return sp.getValue();
//				}
//			}
//		}
//		return null;
//	}

//	@Override
//	public String getBindingString() {
////		return m_bindings;
//		return null;
//	}

//	@Override
//	public String getDescription() {
////		return m_description;
//		return adapted.getDescription();
//	}

//	@Override
//	public Map getExtendedProperties() {
////		return m_extendedProperties;
//		Map ep = new LinkedHashMap();
//		List<com.tibco.cep.designtime.core.model.Entity> list = adapted.getExtendedProperties().getProperties();
//		for(com.tibco.cep.designtime.core.model.Entity item: list) {
//			if(item instanceof SimpleProperty) {
//				SimpleProperty sp = (SimpleProperty) item;
//				ep.put(sp.getName(), sp.getValue());
//			} else if( item instanceof ObjectProperty) {
//				ObjectProperty op = (ObjectProperty) item;
//				ep.put(op.getName(), op.getValue());
//			}
//		}
//		return ep;
//	}

//	public Folder getFolder() {
////		return m_ontology.getFolder(m_folder);
//
//	}

//	public String getFolderPath() {
////		return m_folder;
//		return adapted.getFolder();
//	}

//	public String getFullPath() {
////		if (m_folder != null) {
////            return m_folder + getName();
////        }
////        return "";
//		adapted.getFullPath();
//	}

//	public String getGUID() {
////		return m_guid;
//		return adapted.getGUID();
//	}

//	@Override
//	public Map getHiddenProperties() {
////		return m_hiddenProperties;
//		Map ep = new LinkedHashMap();
//		List<com.tibco.cep.designtime.core.model.Entity> list = adapted.getHiddenProperties().getProperties();
//		for(com.tibco.cep.designtime.core.model.Entity item: list) {
//			if(item instanceof SimpleProperty) {
//				SimpleProperty sp = (SimpleProperty) item;
//				ep.put(sp.getName(), sp.getValue());
//			} else if( item instanceof ObjectProperty) {
//				ObjectProperty op = (ObjectProperty) item;
//				ep.put(op.getName(), op.getValue());
//			}
//		}
//		return ep;
//	}

//	@Override
//	public String getHiddenProperty(String key) {
//		//return (String) m_hiddenProperties.get(key);
//		return (String) getHiddenProperties().get(key);
//	}

//	@Override
//	public Icon getIcon() {
//		return null;
//	}
//
//	@Override
//	public String getIconPath() {
//		
//		return null;
//	}
//
//	@Override
//	public String getLastModified() {
//		return adapted.getLastModified();
////		return null;
//	}

//	@Override
//	public String getName() {
//		return adapted.getName();
////		return m_name;
//	}
//
//	@Override
//	public String getNamespace() {
//		return adapted.getNamespace();
////		return m_namespace;
//	}
//
//	@Override
//	public Ontology getOntology() {
//		return emfOntology;
////		return m_ontology;
//	}
//
//	@Override
//	public Map getTransientProperties() {		
////		return m_transientProperties;
//		Map ep = new LinkedHashMap();
//		List<com.tibco.cep.designtime.core.model.Entity> list = adapted.getTransientProperties().getProperties();
//		for(com.tibco.cep.designtime.core.model.Entity item: list) {
//			if(item instanceof SimpleProperty) {
//				SimpleProperty sp = (SimpleProperty) item;
//				ep.put(sp.getName(), sp.getValue());
//			} else if( item instanceof ObjectProperty) {
//				ObjectProperty op = (ObjectProperty) item;
//				ep.put(op.getName(), op.getValue());
//			}
//		}
//		return ep;
//	}
//
//	@Override
//	public Object getTransientProperty(String key) {
//		
////		return m_transientProperties.get(key);
//		return getTransientProperties().get(key);
//	}
//
//	@Override
//	public void serialize(OutputStream out) throws IOException {
//		
//
//	}
//
//	@Override
//	public Validatable[] getInvalidObjects() {
//		return new Validatable[0];
//	}
//
//	@Override
//	public List getModelErrors() {
//		return new ArrayList();
//	}
//
//	@Override
//	public String getStatusMessage() {
//		
//		return null;
//	}
//
//	@Override
//	public boolean isValid(boolean recurse) {
//		
//		return false;
//	}

	@Override
	public void makeValid(boolean recurse) {
		

	}
	
	public void setBindingString(String bindings) {
//        if (bindings == null) {
//            bindings = "";
//        }
//        m_bindings = bindings;
    }
	
	public void setDescription(String description) {
//        if (description == null) {
//            m_description = "";
//        }
//        m_description = description;
		adapted.setDescription(description);
    }
	
	public void setExtendedProperties(Map props) {
//        if (null == props) {
//            props=this.m_extendedProperties = new LinkedHashMap();
//        }
////        MutableOntology mo = (MutableOntology) this.getOntology();
////        if (mo != null) { //mo can be null during deserialization.
////            mo.removeAlias(this);
////        }
////        this.m_extendedProperties = props;
////        if (mo != null) {
////            mo.addAlias(this.getAlias(), this);
////        }
//
//        // Backing Store Properties
//        Map bs= (Map) props.get(EXTPROP_ENTITY_BACKINGSTORE);
//        if (bs == null) {
//            bs = new LinkedHashMap();
//            props.put(EXTPROP_ENTITY_BACKINGSTORE, bs);
//        }
//        // Get the table name
//        String tableName= (String) bs.get(EXTPROP_ENTITY_BACKINGSTORE_TABLENAME);
//        if (tableName == null) {
//            bs.put(EXTPROP_ENTITY_BACKINGSTORE_TABLENAME, "");
//        }
//
//        String typeName= (String) bs.get(EXTPROP_ENTITY_BACKINGSTORE_TYPENAME);
//        if (typeName == null) {
//            bs.put(EXTPROP_ENTITY_BACKINGSTORE_TYPENAME, "");
//        }
//
//        String hasBackingStore= (String) bs.get(EXTPROP_ENTITY_BACKINGSTORE_HASBACKINGSTORE);
//        if (hasBackingStore == null) {
//            bs.put(EXTPROP_ENTITY_BACKINGSTORE_HASBACKINGSTORE, "true");
//        }
//
//        Map cs= (Map) props.get(EXTPROP_ENTITY_CACHE);
//        if (cs == null) {
//            cs = new LinkedHashMap();
//            props.put(EXTPROP_ENTITY_CACHE, cs);
//        }
//
//        String constant= (String) cs.get(EXTPROP_ENTITY_CACHE_CONSTANT);
//        if (constant == null) {
//            cs.put(EXTPROP_ENTITY_CACHE_CONSTANT, "false");
//        }
//
//        String preloadAll= (String) cs.get(EXTPROP_ENTITY_CACHE_PRELOAD_ALL);
//        if (preloadAll == null) {
//            cs.put(EXTPROP_ENTITY_CACHE_PRELOAD_ALL, "false");
//        }
//
//
//        String fetchSize= (String) cs.get(EXTPROP_ENTITY_CACHE_PRELOAD_FETCHSIZE);
//
//        if (fetchSize == null) {
//            cs.put(EXTPROP_ENTITY_CACHE_PRELOAD_FETCHSIZE, "0");
//        }
//
//        String requiresVersionCheck= (String) cs.get(EXTPROP_ENTITY_CACHE_REQUIRESVERSIONCHECK);
//        if (requiresVersionCheck == null) {
//            cs.put(EXTPROP_ENTITY_CACHE_REQUIRESVERSIONCHECK, "true");
//        }
//
//        String isCacheLimited= (String) cs.get(EXTPROP_ENTITY_CACHE_ISCACHELIMITED);
//        if (isCacheLimited == null) {
//            cs.put(EXTPROP_ENTITY_CACHE_ISCACHELIMITED, "true");
//        }
//
//        String evictOnUpdate= (String) cs.get(EXTPROP_ENTITY_CACHE_EVICTONUPDATE);
//        if (evictOnUpdate == null) {
//            cs.put(EXTPROP_ENTITY_CACHE_EVICTONUPDATE, "true");
//        }
    }
	
	public void setFolder(Folder folder)  {
		adapted.setFolder(folder.getFullPath());
//		m_folder = folder.getFullPath();
//        //m_ontology.setEntityFolder(this, folder);
    }
	
	public void setFolderPath(String fullPath) throws ModelException {
		adapted.setFolder(fullPath);
//		m_folder = fullPath;
////        this.setFolder((MutableFolder) m_ontology.createFolder(fullPath, false));
    }
	
	public void setGUID(String guid) {
//        if ((null == guid) || (guid.trim().equals(""))) {
//            this.m_guid = GUIDGenerator.getGUID();
//        } else {
//            this.m_guid = guid;
//        }
		adapted.setGUID(guid);
    }
	
	public void setHiddenProperty(String key, String value) {
//        m_hiddenProperties.put(key, value);
		
		SimpleProperty sp = ModelFactory.eINSTANCE.createSimpleProperty();
		sp.setName(key);
		sp.setValue(value);
		adapted.getHiddenProperties().getProperties().add(sp);
    }
	
	public void setName(String name, boolean renameOnConflict) {
		adapted.setName(name);
////        if (name == null || name.length() == 0) {
////            throw new ModelException(RESOURCE_BUNDLE.getString(EMPTY_NAME_KEY));
////        }
//
//        if (name.equals(m_name)) {
//            return;
//        }
//
//        /** Since some Entities exist without Folders, we need to check if the folder exists **/
//        Folder folder = (Folder) getFolder();
//        if (folder != null) {
//            String oldPath = getFullPath();
//
//            /** Check if folder allows rename **/
//            Entity oldEntity = folder.getEntity(name, false);
//            if (oldEntity != null) {
//                if (oldEntity.equals(this)) {
//                    return;
//                } else if (renameOnConflict) {
////                    EntityNameValidator env = EntityNameValidator.DEFAULT_INSTANCE;
////                    env.setOntology(m_ontology);
////                    env.setFolder(folder);
////                    env.setFolderIsBeingNamed(false);
////                    name = UniqueNamer.generateUniqueName(name, env);
//                } else {
////                    throw new ModelException(RESOURCE_BUNDLE.formatString(NAME_CONFLICT_KEY, name, m_folder));
//                }
//            }
//
//            /* Unregister from under the old name from our folder */
////            folder.m_entities.remove(m_name);
////            m_ontology.m_entities.remove(oldPath);
//
//            String newPath = m_folder + name;
//
//            /* Register under new name with Package */
////            folder.m_entities.put(name, this);
////            m_ontology.m_entities.put(newPath, this);
//
//        }
////        String oldName = m_name;
//        m_name = name;
////        if (m_ontology != null) {
////            m_ontology.notifyEntityRenamed(this, oldName);
////        }
    }

	public void setOntology(Ontology ontology) {
		emfOntology = ontology;
		
	}

	public void delete() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	

}
