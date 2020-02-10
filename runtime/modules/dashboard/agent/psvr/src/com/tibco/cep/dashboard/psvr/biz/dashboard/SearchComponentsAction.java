package com.tibco.cep.dashboard.psvr.biz.dashboard;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.mal.managers.MALChartComponentManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALTextComponentManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentCategory;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.security.SecurityToken;

public class SearchComponentsAction extends BaseAuthenticatedAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		try {
			String criteria = request.getParameter("criteria");
			TokenRoleProfile tokenRoleProfile = getTokenRoleProfile(token);
			List<MALComponent> searchResults = search(tokenRoleProfile, criteria);
			ComponentCategory category = new ComponentCategory();
			category.setName("search results");
			category.setCount(searchResults.size());
			for (MALComponent component : searchResults) {
				ComponentDefinition compDefinition = new ComponentDefinition();
				compDefinition.setId(component.getId());
				compDefinition.setTitle(component.getDisplayName());
				compDefinition.setName(component.getName());
				compDefinition.setType(component.getDefinitionType());
				category.addComponentDefinition(compDefinition);
			}
			String searchResultsXML = OGLMarshaller.getInstance().marshall(token, category);
			return handleSuccess(searchResultsXML);
		} catch (RequestProcessingException e) {
			return handleError(getMessage("bizaction.tokenprofile.retrieval.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (OGLException e) {
			return handleError(getMessage("bizaction.marshalling.failure", getMessageGeneratorArgs(token, e, "search results")), e);
		}
	}

	private List<MALComponent> search(TokenRoleProfile tokenRoleProfile, String criteria) {
		Map<String, MALComponent> componentsIndex = new HashMap<String, MALComponent>();
		Collection<MALComponent> components = tokenRoleProfile.getViewsConfigHelper().getComponentsByType(MALChartComponentManager.DEFINITION_TYPE);
		for (MALComponent component : components) {
			componentsIndex.put(component.getId(), component);
		}
		components = tokenRoleProfile.getViewsConfigHelper().getComponentsByType(MALTextComponentManager.DEFINITION_TYPE);
		for (MALComponent component : components) {
			componentsIndex.put(component.getId(), component);
		}
		Iterator<String> componentIDs = tokenRoleProfile.getComponentGallery().getComponentIDs();
		while (componentIDs.hasNext()) {
			String componentId = componentIDs.next();
			if (componentsIndex.containsKey(componentId) == false) {
				MALComponent component = tokenRoleProfile.getComponentGallery().searchComponent(componentId);
				componentsIndex.put(componentId, component);
			}
		}
		List<MALComponent> finalList = new LinkedList<MALComponent>(componentsIndex.values());
		Collections.sort(finalList, new Comparator<MALComponent>() {

			@Override
			public int compare(MALComponent o1, MALComponent o2) {
				String o1Name = o1.getDisplayName();
				if (StringUtil.isEmptyOrBlank(o1Name) == true) {
					o1Name = o1.getName();
				}
				String o2Name = o2.getDisplayName();
				if (StringUtil.isEmptyOrBlank(o2Name) == true) {
					o2Name = o2.getName();
				}
				return o1Name.compareTo(o2Name);
			}

		});
		return finalList;
	}

}
