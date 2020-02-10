package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration;

import java.util.Collections;
import java.util.Map;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTreeConstants;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;

/**
 * @author rajesh
 * 
 */
public class RequestTableModel extends DefaultTableModel {

	protected BizSessionRequest request;

	public RequestTableModel(BizSessionRequest request) {
		super();
		this.request = request;
	}

	@Override
	public int getColumnCount() throws TableModelException {
		try {
			return Integer.parseInt(request.getParameter(TableTreeConstants.KEY_COLUMN_COUNT));
		} catch (Exception e) {

		}
		return 0;

	}

	@Override
	public Map getParameters() throws TableModelException {
		return request.getParameterMap();
	}

	@Override
	public Map getNestedTableParam(int rowIndex) throws TableModelException {
		return Collections.EMPTY_MAP;
	}

	public Map getRowMenuParameters(int rowIndex) throws TableModelException {
		return Collections.EMPTY_MAP;
	}

	public Map getHeaderMenuParameters() throws TableModelException {
		return Collections.EMPTY_MAP;
	}

	@Override
	public String getBackgroundColor() throws TableModelException {
		return request.getParameter(TableTreeConstants.KEY_BG_COLOR);
	}

	@Override
	public String getGroupByValue() throws TableModelException {
		return null;
	}

	@Override
	public String getGroupByField() throws TableModelException {
		return null;
	}
}
