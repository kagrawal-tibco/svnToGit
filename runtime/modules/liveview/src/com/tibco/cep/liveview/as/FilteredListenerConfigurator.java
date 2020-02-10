/**
 * 
 */
package com.tibco.cep.liveview.as;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.as.space.browser.BrowserDef.DistributionScope;
import com.tibco.as.space.event.SpaceEvent.EventType;
import com.tibco.as.space.listener.ListenerDef.TimeScope;
import com.tibco.as.space.listener.ListenerDef;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.liveview.agent.LiveViewAgent;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

/**
 * @author vpatil
 *
 */
public class FilteredListenerConfigurator {
	
	public static void configureSpaceListener(Map<String, String> entityToFilterMap, DaoProvider daoProvider, LiveViewAgent lvAgent) throws ASException {
		if (entityToFilterMap.size() > 0) {
			ThreadPoolExecutor fixedPreloadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(entityToFilterMap.keySet().size());

			for (String entityURI : entityToFilterMap.keySet()) {
				EntityDao dao = getDao(getClassName(entityURI), daoProvider);
				SpaceMap spaceMap = (SpaceMap) dao.getInternal();

				Space space = spaceMap.getSpace();
				String filter = entityToFilterMap.get(entityURI);
				ListenerDef ld = ListenerDef.create(TimeScope.NEW_EVENTS, ListenerDef.DistributionScope.ALL);
				ld.setQueryLimit(-1);
				if (null != filter && !filter.isEmpty()) {
					space.listen(new LVSpaceListener(lvAgent), ld, filter);
				} else {
					space.listen(new LVSpaceListener(lvAgent), ld);
				}
				
				// preload existing entries
				fixedPreloadPool.execute(new EntryPreLoader(space, filter, lvAgent));
			}
			fixedPreloadPool.shutdown();
		}
	}
	
	private static Class getClassName(String entityURI) {
		RuleServiceProviderManager RSPM = RuleServiceProviderManager.getInstance();
        TypeDescriptor td = RSPM.getDefaultProvider().getTypeManager().getTypeDescriptor(entityURI);
        if (td == null) {
            throw new RuntimeException("cant find type: " + entityURI);
        }
        return td.getImplClass();
	}
	
	private static final EntityDao getDao(Class<? extends Entity> klass, DaoProvider daoProvider) {
        EntityDao dao = (EntityDao) daoProvider.getEntityDao(klass);
        if (dao != null) {
            return dao;
        }

        throw new IllegalArgumentException("No DAO found for class [" + klass + "]");
    }
}

class EntryPreLoader implements Runnable {
	private Space space;
	private String filter;
	private LiveViewAgent lvAgent;
	
	public EntryPreLoader(Space space, String filter, LiveViewAgent lvAgent) {
		this.space = space;
		this.filter = filter;
		this.lvAgent = lvAgent;
	}
	
	@Override
	public void run() {
		BrowserDef browserDef = BrowserDef.create(-1, com.tibco.as.space.browser.BrowserDef.TimeScope.CURRENT, DistributionScope.ALL);
		browserDef.setQueryLimit(-1);
		browserDef.setPrefetch(-1);
		
		Browser browser = null;
		try {
			if (filter != null) {
				browser = space.browse(BrowserType.GET, browserDef, filter);
			} else {
				browser = space.browse(BrowserType.GET, browserDef);
			}
		
			Tuple entry = null;
			while ((entry = browser.next()) != null) {
				lvAgent.submitPublisherTask(new TupleContent(space.getName(), entry, EventType.PUT));
			}
		} catch (ASException asException) {
			throw new RuntimeException(asException);
		}
	}
}
