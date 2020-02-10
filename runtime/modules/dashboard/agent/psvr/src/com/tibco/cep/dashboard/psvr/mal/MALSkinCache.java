package com.tibco.cep.dashboard.psvr.mal;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.psvr.mal.managers.MALSkinManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALSkin;
import com.tibco.cep.dashboard.psvr.mal.store.SystemIdentity;
import com.tibco.cep.dashboard.psvr.mal.store.XMIPersistentStore;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.studio.dashboard.utils.SystemElementsCreator;

/**
 * @author apatil
 *
 */
public class MALSkinCache extends MALElementCache<MALSkin> {

    private static final String SKIN_CFG_TYPE = "Skin";

    private static MALSkinCache instance = null;

    public static final synchronized MALSkinCache getInstance() {
        if (instance == null) {
            instance = new MALSkinCache();
        }
        return instance;
    }

    private MALSkin systemSkin;

    private Map<String, MALSkinIndexer> insightSkinIndexerMap;

    private MALSkinCache(){
    	super("malskincache","MALSkin Cache");
        insightSkinIndexerMap = new HashMap<String, MALSkinIndexer>();
    }

    @Override
    protected void doStart() throws ManagementException {
        try {
			List<MALSkin> skins = getAllSpecificComponentConfigs(SKIN_CFG_TYPE);
			logger.log(Level.DEBUG,"Loading system skin...");
			systemSkin = loadDefaultSkin();
			skins.add(systemSkin);
			for (MALSkin viewsSkin : skins) {
				MALSkinIndexer insightSkinIndexer = new MALSkinIndexer(logger,viewsSkin);
				String name = viewsSkin.getName();
				addObject(name, viewsSkin);
				insightSkinIndexerMap.put(name,insightSkinIndexer);
			}
		} catch (MALException e) {
			String msg = messageGenerator.getMessage("skincache.buildup.failure");
			throw new ManagementException(msg,e);
		}
    }

	public final MALSkinIndexer getInsightSkinIndexer(MALSkin skin){
		if (skin == null) {
			skin = systemSkin;
		}
        return insightSkinIndexerMap.get(skin.getName());
    }

    public final Collection<MALSkinIndexer> getAllInsightSkinIndexers(){
    	return insightSkinIndexerMap.values();
    }

    private MALSkin loadDefaultSkin() throws MALException {
    	MALElementsCollector elementsCollector = new MALElementsCollector();
    	SystemPersistentStore store = new SystemPersistentStore(serviceContext.getRuleServiceProvider().getProject().getName());
    	store.init();
    	return (MALSkin) store.getElementsByType(MALSkinManager.DEFINITION_TYPE, elementsCollector).get(0);
	}

    class SystemPersistentStore extends XMIPersistentStore {

    	private String projectName;

		private ResourceSetImpl resourceSet;

		public SystemPersistentStore(String projectName) {
			super(null, SystemIdentity.getInstance());
			this.projectName = projectName;
			this.resourceSet = new ResourceSetImpl();
		}

		@Override
		protected void init() throws MALException {
			try {
				List<Entity> entities = new SystemElementsCreator(projectName, "", "").create();
				for (Entity entity : entities) {
					this.cacheIt(entity.getGUID(), projectName, entity);
				}
			} catch (Exception e) {
				throw new MALException("could not load default skin",e);
			}
		}

		@Override
		protected void cacheIt(String id, String name, Entity entity) throws MALException {
			idToElementsMap.put(id, entity);
			nameToIdMap.put(name, id);

			String type = entity.eClass().getName();
			List<String> ids = typeToIdsMap.get(type);
			if (ids == null) {
				ids = new LinkedList<String>();
				typeToIdsMap.put(type, ids);
			}
			ids.add(id);

			List<String> names = typeToNamesMap.get(type);
			if (names == null) {
				names = new LinkedList<String>();
				typeToNamesMap.put(type, names);
			}
			names.add(name);

			URIToIdMap.put(name, id);
			idToURIMap.put(id, name);
		}

		protected void uncacheIt(String id, String name, Entity entity) {
			String type = entity.eClass().getName();

			idToElementsMap.remove(id);
			nameToIdMap.remove(name);

			List<String> ids = typeToIdsMap.get(type);
			if (ids != null) {
				ids.remove(id);
			}

			List<String> names = typeToNamesMap.get(type);
			if (names != null) {
				names.remove(name);
			}

			URIToIdMap.remove(name);
			idToURIMap.remove(id);
		}

		@Override
		public Entity createElement(String definitionType) {
			throw new UnsupportedOperationException("createElement");
		}

		@Override
		protected Entity saveElement(Entity entity) throws MALException {
			throw new UnsupportedOperationException("saveElement");
		}

		@Override
		public void deleteElementById(String id) {
			throw new UnsupportedOperationException("deleteElementById");
		}

		@Override
		protected URI generateStorageURI(Entity entity) {
			throw new UnsupportedOperationException("generateStorageURI");
		}

		@Override
		protected void setDefaultProperties(Entity entity) {
		}

		@Override
		protected boolean deleteElement(Entity entity) {
			throw new UnsupportedOperationException("deleteElement");
		}

		@Override
		protected URI getReferenceableRootURI() {
			throw new UnsupportedOperationException("getReferenceableRootURI");
		}

		@Override
		protected ResourceSet getResourceSet() {
			return resourceSet;
		}

    }
}