package com.tibco.cep.dashboard.plugin.beviews.biz.querymgr.nextgen;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizerProvider;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerEntity;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerModel;
import com.tibco.cep.dashboard.security.SecurityToken;

public class GetEntityFieldsAction extends BaseQueryManagerAction {

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		String typeid = request.getParameter("typeid");
		if (StringUtil.isEmptyOrBlank(typeid) == true) {
			return handleError(getMessage("querymanager.invalid.typeid"));
		}
		try {
			QueryManagerModel queryManagerModel = new QueryManagerModel();
			//set typeid as selected entity id
			queryManagerModel.setSelectedEntityID(typeid);
			//get the entity visualizer
			EntityVisualizer entityVisualizer = EntityVisualizerProvider.getInstance().getEntityVisualizerById(typeid);
			QueryManagerEntity queryManagerEntity = new QueryManagerEntity();
			//set the type
			queryManagerEntity.setId(typeid);
			//set display name as name
			queryManagerEntity.setName(entityVisualizer.getName());
			//add field passing null to get default conditions
			addFieldConditions(token, queryManagerEntity, entityVisualizer, null);
			//add the queryManagerEntity to queryManagerModel
			queryManagerModel.addQueryManagerEntity(queryManagerEntity);
			//marshall it
			return handleSuccess(OGLMarshaller.getInstance().marshall(token, queryManagerModel));
		} catch (OGLException e) {
			return handleError(getMessage("getentityfields.marshalling.failure", getMessageGeneratorArgs(token, e, "entity fields")), e);
		}
	}
}
