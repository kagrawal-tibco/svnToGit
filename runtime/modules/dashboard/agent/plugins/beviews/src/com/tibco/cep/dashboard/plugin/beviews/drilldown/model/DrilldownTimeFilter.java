package com.tibco.cep.dashboard.plugin.beviews.drilldown.model;

import javax.servlet.http.HttpSession;

import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;

public class DrilldownTimeFilter {

	private int duration;
	private int unit;
	private boolean isEnable;

	private DrilldownTimeFilter() {
		isEnable = false;
		duration = 0;
		unit = 0;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public void update(BizSessionRequest request) {
		if ("changetimefilter".equals(request.getParameter("action"))) {
			isEnable = Boolean.parseBoolean(request.getParameter("status"));
			if (isEnable) {
				unit = Integer.parseInt(request.getParameter("unit"));
				duration = Integer.parseInt(request.getParameter("duration"));
			}
		}
	}

	public static boolean isExist(HttpSession session) {
		return session.getAttribute(DrilldownTimeFilter.class.getName()) != null;
	}

	public static void attach(HttpSession session) {
		session.setAttribute(DrilldownTimeFilter.class.getName(), new DrilldownTimeFilter());
	}

	public static DrilldownTimeFilter get(BizSession enhancedSession) {
		return (DrilldownTimeFilter) enhancedSession.getAttribute(DrilldownTimeFilter.class.getName());
	}
}
