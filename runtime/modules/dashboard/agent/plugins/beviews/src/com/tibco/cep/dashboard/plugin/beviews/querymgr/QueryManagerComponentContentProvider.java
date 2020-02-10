package com.tibco.cep.dashboard.plugin.beviews.querymgr;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizerProvider;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.runtime.ComponentContentProvider;
import com.tibco.cep.dashboard.plugin.beviews.runtime.VelocityViewHelper;
import com.tibco.cep.dashboard.plugin.beviews.search.BizSessionSearchStore;
import com.tibco.cep.dashboard.plugin.beviews.search.QuerySpecModel;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.designtime.core.model.Entity;

public class QueryManagerComponentContentProvider extends ComponentContentProvider {

	@SuppressWarnings("unchecked")
	@Override
	public String getComponentContent(BizSessionRequest request) {
		//add all the entities
		request.setAttribute("entities", getEntities());
		BizSessionSearchStore searchStore = BizSessionSearchStore.getInstance(request.getSession());
		QuerySpecModel queryMgrModel = searchStore.getQueryManagerModel();
		if (queryMgrModel != null) {
			String typeID = queryMgrModel.getTypeID();
			request.setAttribute("selectedEntityId", typeID);
			List<String> commands = (List<String>) request.getAttribute("runCommands");
			commands.add("typeIdChanged();");
		}
		try {
			String viewTemplatePath = VelocityViewHelper.getInstance().getViewTemplatePath("querymgr", "baserender");
			return VelocityViewHelper.getInstance().prepareResponseUsingTemplatePath(request, viewTemplatePath).toString();
		} finally {
			request.setAttribute("entities", null);
			request.setAttribute("selectedEntityId", null);
		}
		
	}

	public List<Map<String, String>> getEntities() {
		Iterator<Entity> allObjects = EntityCache.getInstance().getAllObjects();
		List<Map<String,String>> entities = new LinkedList<Map<String,String>>();
		while (allObjects.hasNext()) {
			Entity entity = allObjects.next();
			Map<String,String> entityInfo = new HashMap<String, String>();
			entityInfo.put("id", entity.getGUID());
			entityInfo.put("name", EntityVisualizerProvider.getInstance().getEntityVisualizer(entity).getName());
			entities.add(entityInfo);
		}
		return entities;
	}
	
}