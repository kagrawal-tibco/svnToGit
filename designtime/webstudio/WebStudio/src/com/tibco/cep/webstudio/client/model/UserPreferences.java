/**
 * 
 */
package com.tibco.cep.webstudio.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model to represent user preferences
 * 
 * @author Vikram Patil
 */
public class UserPreferences {
	private List<DashboardPortlet> dashboardPortlets;
	private int portalColumns;
	private int recentlyOpenedArtifactLimit;
	private int favoriteArtifactLimit;
	private String customURL;
	private String itemView;
	private int decisionTablePageSize;
	private String scsUserName;
	private String scsUserPassword;
	private boolean autoUnLockOnReview;
	private boolean groupRelatedArtifacts;
	private boolean allowCustomDomainValues;
	private boolean showColumnAliasIfPresent;
	private boolean isGroupingPropertyChanged;
	private String autoFitColumnsApproch;
	private String rtiDefaultFilterType;

	public UserPreferences() {
		dashboardPortlets = new ArrayList<DashboardPortlet>();
	}

	public List<DashboardPortlet> getDashboardPortlets() {
		return dashboardPortlets;
	}

	public void setDashboardPortlets(List<DashboardPortlet> dashboardPortlets) {
		this.dashboardPortlets = dashboardPortlets;
	}

	public int getPortalColumns() {
		return portalColumns;
	}

	public void setPortalColumns(int portalColumns) {
		this.portalColumns = portalColumns;
	}

	public int getRecentlyOpenedArtifactLimit() {
		return recentlyOpenedArtifactLimit;
	}

	public void setRecentlyOpenedArtifactLimit(int recentlyOpenedArtifactLimit) {
		this.recentlyOpenedArtifactLimit = recentlyOpenedArtifactLimit;
	}

	public int getFavoriteArtifactLimit() {
		return favoriteArtifactLimit;
	}

	public void setFavoriteArtifactLimit(int favoriteArtifactLimit) {
		this.favoriteArtifactLimit = favoriteArtifactLimit;
	}
	
	public void addDashboardPortlets(DashboardPortlet portlet) {
		dashboardPortlets.add(portlet);
	}
	
	public void removeDashboardPortlets(DashboardPortlet portlet) {
		dashboardPortlets.remove(portlet);
	}

	public String getCustomURL() {
		return customURL;
	}

	public void setCustomURL(String customURL) {
		this.customURL = customURL;
	}
	
	public String getScsUserName() {
		return scsUserName;
	}

	public void setScsUserName(String scsUserName) {
		this.scsUserName = scsUserName;
	}

	public String getScsUserPassword() {
		return scsUserPassword;
	}

	public void setScsUserPassword(String scsUserPassword) {
		this.scsUserPassword = scsUserPassword;
	}

	public void removeDashboardPortlets(String portletId) {
		DashboardPortlet portletToRemove = new DashboardPortlet(portletId);
		if (dashboardPortlets.contains(portletToRemove)) {
			removeDashboardPortlets(portletToRemove);
		}
	}
	
	public void addDashboardPortlets(String portletId) {
		DashboardPortlet portletToAdd = new DashboardPortlet(portletId);
		if (!dashboardPortlets.contains(portletToAdd)) {
			addDashboardPortlets(portletToAdd);
		}
	}

	public String getItemView() {
		return itemView;
	}

	public void setItemView(String itemView) {
		this.itemView = itemView;
	}

	public int getDecisionTablePageSize() {
		return decisionTablePageSize;
	}

	public void setDecisionTablePageSize(int decisionTablePageSize) {
		this.decisionTablePageSize = decisionTablePageSize;
	}

	public boolean isAutoUnLockOnReview() {
		return autoUnLockOnReview;
	}

	public void setAutoUnLockOnReview(boolean autoUnLockOnReview) {
		this.autoUnLockOnReview = autoUnLockOnReview;
	}

	public boolean isGroupRelatedArtifacts() {
		return groupRelatedArtifacts;
	}

	public void setGroupRelatedArtifacts(boolean groupRelatedArtifacts) {
		this.groupRelatedArtifacts = groupRelatedArtifacts;
	}

	public boolean isAllowCustomDomainValues() {
		return allowCustomDomainValues;
	}

	public void setAllowCustomDomainValues(boolean allowCustomDomainValues) {
		this.allowCustomDomainValues = allowCustomDomainValues;
	}
	
	public boolean isShowColumnAliasIfPresent() {
		return showColumnAliasIfPresent;
	}

	public void setShowColumnAliasIfPresent(boolean showColumnAliasIfPresent) {
		this.showColumnAliasIfPresent = showColumnAliasIfPresent;
	}
	
	public boolean isGroupingPropertyChanged() {
		return this.isGroupingPropertyChanged;
	}
	
	public void setIsGroupingPropertyChanged(boolean isGroupingPropertyChanged) {
		this.isGroupingPropertyChanged = isGroupingPropertyChanged;
	}
	
	public String getAutoFitColumnsApproch() {
		return this.autoFitColumnsApproch;
	}
	
	public void setAutoFitColumnsApproch(String autoFitColumnsApproch) {
		this.autoFitColumnsApproch = autoFitColumnsApproch;
	}

	public String getRtiDefaultFilterType() {
		return this.rtiDefaultFilterType;
	}

	public void setRtiDefaultFilterType(String rtiDefaultFilterType) {
		this.rtiDefaultFilterType = rtiDefaultFilterType;
	}
}
