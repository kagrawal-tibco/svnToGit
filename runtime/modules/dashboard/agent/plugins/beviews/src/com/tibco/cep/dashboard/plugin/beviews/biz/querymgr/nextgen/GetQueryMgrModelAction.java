package com.tibco.cep.dashboard.plugin.beviews.biz.querymgr.nextgen;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizerProvider;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.search.BizSessionSearchStore;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerEntity;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerModel;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.designtime.core.model.Entity;

public class GetQueryMgrModelAction extends BaseQueryManagerAction {

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		try {
			QueryManagerModel queryManagerModel = new QueryManagerModel();
			// get all entities from entity cache
			Iterator<Entity> allEntities = EntityCache.getInstance().getAllObjects();
			// get the query spec in the session
			List<QuerySpec> querySpecsInSession = BizSessionSearchStore.getInstance(request.getSession()).getQuerySpecs();
			if (querySpecsInSession != null && querySpecsInSession.isEmpty() == false) {
				// set the selected entity id
				queryManagerModel.setSelectedEntityID(querySpecsInSession.get(0).getSchema().getTypeID());
			}
			while (allEntities.hasNext()) {
				Entity entity = allEntities.next();
				String typeId = entity.getGUID();
				EntityVisualizer entityVisualizer = EntityVisualizerProvider.getInstance().getEntityVisualizer(entity);
				QueryManagerEntity qmEntity = new QueryManagerEntity();
				// set typeid
				qmEntity.setId(typeId);
				// set name using EntityVisualizer
				qmEntity.setName(entityVisualizer.getName());
				if (typeId.equals(queryManagerModel.getSelectedEntityID()) == true) {
					// we add the fields of the entity since it is the selected entity
					// populateQueryManagerEntity(querySpecInSession.getSchema(), BizSessionSearchStore.getInstance(request.getSession()).getQueryManagerModel(), qmEntity);
					addFieldConditions(token, qmEntity, entityVisualizer, BizSessionSearchStore.getInstance(request.getSession()).getQueryManagerModel());
				}
				queryManagerModel.addQueryManagerEntity(qmEntity);
			}
			//sort the query manager entity
			List<QueryManagerEntity> queryManagerEntityList = Arrays.asList(queryManagerModel.getQueryManagerEntity());
			Collections.sort(queryManagerEntityList, new Comparator<QueryManagerEntity>() {

				@Override
				public int compare(QueryManagerEntity o1, QueryManagerEntity o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			queryManagerModel.setQueryManagerEntity(queryManagerEntityList.toArray(new QueryManagerEntity[queryManagerEntityList.size()]));
			return handleSuccess(OGLMarshaller.getInstance().marshall(token, queryManagerModel));
		} catch (OGLException e) {
			return handleError(getMessage("getquerymanagermodel.marshalling.failure", getMessageGeneratorArgs(token, e, "query manager")), e);
		}
	}
}