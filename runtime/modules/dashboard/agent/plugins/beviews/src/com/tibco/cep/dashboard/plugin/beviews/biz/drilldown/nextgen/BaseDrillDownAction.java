package com.tibco.cep.dashboard.plugin.beviews.biz.drilldown.nextgen;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.biz.BaseSessionCheckerAction;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownRequest;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.DrillDownTreePath;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.DrillDownTreePathParser;
import com.tibco.cep.dashboard.plugin.beviews.search.BizSessionSearchStore;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.XMLBizResponseImpl;
import com.tibco.cep.dashboard.security.SecurityToken;

public abstract class BaseDrillDownAction extends BaseSessionCheckerAction {

	private static enum SORT_ORDER { ascending, descending };

	protected DrillDownTreePathParser treePathParser;

	private boolean defaultSortIsAscending;

	private int pageSize;

	private int maxTableCount;

	private int maxPageCount;

	private DrillDownRequest.PAGINATION_MODE paginationMode;

	@Override
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception {
		super.init(command, properties, configuration);
		treePathParser = new DrillDownTreePathParser(logger, exceptionHandler, messageGenerator, EntityCache.getInstance());
		SORT_ORDER defaultSortOrder = SORT_ORDER.valueOf(String.valueOf(BEViewsProperties.DRILLDOWN_TABLE_SORT_ORDER.getValue(properties)).toLowerCase());
		defaultSortIsAscending = defaultSortOrder.compareTo(SORT_ORDER.ascending) == 0;
		// how much rows to show in one type table page
		pageSize = (Integer) BEViewsProperties.DRILLDOWN_TABLE_PAGE_COUNT.getValue(properties);
		// how much rows to show in one type table across all it's pages
		maxTableCount = (Integer) BEViewsProperties.DRILLDOWN_TABLE_MAX_COUNT.getValue(properties);
		// how much rows to show on the entire page
		maxPageCount = (Integer) BEViewsProperties.DRILLDOWN_PAGE_MAX_COUNT.getValue(properties);
		// what is the pagination mode
		paginationMode = DrillDownRequest.PAGINATION_MODE.valueOf(String.valueOf(BEViewsProperties.DRILLDOWN_PAGINATION_MODE.getValue(properties)).toLowerCase());
	}

	@Override
	protected BizResponse handleInvalidSession(SecurityToken token, String sessionId, BizSessionRequest request) {
		return handleError("Your session has timed out");
	}

	protected DrillDownRequest getRequest(SecurityToken token, BizSessionRequest request) {
		List<QuerySpec> querySpecs = BizSessionSearchStore.getInstance(request.getSession()).getQuerySpecs();
		DrillDownTreePath path = treePathParser.parse(request.getParameter("path"));
//		if (querySpec != null && path != null && path.getPathElements().size() == 1 && path.getRoot().getToken().equals(querySpec.getSchema().getTypeID()) == true) {
//			path = null;
//		}
		String sortField = request.getParameter("sortfield");
		String sortFieldDirection = request.getParameter("sortfielddirection");
		String groupbyField = request.getParameter("groupbyfield");
		int startIndex = -1;
		if (StringUtil.isEmptyOrBlank(request.getParameter("startindex")) == false) {
			try {
				startIndex = Integer.parseInt(request.getParameter("startindex"));
			} catch (NumberFormatException e) {
				// do nothing, startIndex is already -1
			}
		}
		DrillDownRequest drillDownRequest = new DrillDownRequest(token, querySpecs, path, pageSize, maxTableCount, maxPageCount, paginationMode);
		if (StringUtil.isEmptyOrBlank(sortField) == false) {
			boolean ascending = defaultSortIsAscending;
			if (StringUtil.isEmptyOrBlank(sortFieldDirection) == false) {
				if ("asc".equals(sortFieldDirection) == true) {
					ascending = true;
				} else if ("desc".equals(sortFieldDirection) == true) {
					ascending = false;
				} else {
					drillDownRequest.setInitialSortRequest(true);
				}
			}
			else {
				drillDownRequest.setInitialSortRequest(true);
			}
			drillDownRequest.addOrderBy(sortField, ascending);
		}
		if (StringUtil.isEmptyOrBlank(groupbyField) == false) {
			drillDownRequest.setGroupByField(groupbyField);
		}
		drillDownRequest.setStartIndex(startIndex);
		return drillDownRequest;
	}

	protected final BizResponse handleSuccess(String message, String content) {
		if (StringUtil.isEmptyOrBlank(message) == true) {
			message = null;
		}
		XMLBizResponseImpl response = new XMLBizResponseImpl(XMLBizResponseImpl.SUCCESS_STATUS, message);
		response.addAttribute("content", content);
		return response;
	}

}
