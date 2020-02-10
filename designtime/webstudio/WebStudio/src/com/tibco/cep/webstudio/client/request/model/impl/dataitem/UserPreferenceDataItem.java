/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model.impl.dataitem;

import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.model.DashboardPortlet;
import com.tibco.cep.webstudio.client.model.UserPreferences;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;
import com.tibco.cep.webstudio.client.util.Base64Utils;

/**
 * XML representation of User Preferences
 * 
 * @author Vikram Patil
 */
public class UserPreferenceDataItem implements IRequestDataItem {
	private UserPreferences userPreference;

	public UserPreferenceDataItem(UserPreferences userPreference) {
		this.userPreference = userPreference;
	}

	public void serialize(Document rootDocument, Node rootNode) {
		Node userPreferenceItemElement = rootDocument
				.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_ITEM_ELEMENT);
		rootNode.appendChild(userPreferenceItemElement);

		Node portalColumns = rootDocument
				.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_PORTAL_COLUMN_ELEMENT);
		userPreferenceItemElement.appendChild(portalColumns);

		Text portalColumnsText = rootDocument.createTextNode(String.valueOf(userPreference.getPortalColumns()));
		portalColumns.appendChild(portalColumnsText);

		Node favoriteArtifactLimit = rootDocument
				.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_FAVORITE_LIMIT_ELEMENT);
		userPreferenceItemElement.appendChild(favoriteArtifactLimit);

		Text favoriteArtifactLimitText = rootDocument.createTextNode(String.valueOf(userPreference
				.getFavoriteArtifactLimit()));
		favoriteArtifactLimit.appendChild(favoriteArtifactLimitText);

		Node recentlyOpenedLimit = rootDocument
				.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_RECENTLY_OPENED_LIMIT_ELEMENT);
		userPreferenceItemElement.appendChild(recentlyOpenedLimit);

		Text recentlyOpenedLimitText = rootDocument.createTextNode(String.valueOf(userPreference
				.getRecentlyOpenedArtifactLimit()));
		recentlyOpenedLimit.appendChild(recentlyOpenedLimitText);

		Node customURL = rootDocument.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_CUSTOM_URL_ELEMENT);
		userPreferenceItemElement.appendChild(customURL);

		Text customURLText = rootDocument.createTextNode(userPreference.getCustomURL());
		customURL.appendChild(customURLText);
		
		Node itemView = rootDocument.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_ITEM_VIEW);
		userPreferenceItemElement.appendChild(itemView);

		Text itemViewText = rootDocument.createTextNode(userPreference.getItemView());
		itemView.appendChild(itemViewText);

		Node decisionTablePageSize = rootDocument.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_DT_PAGE_SIZE);
		userPreferenceItemElement.appendChild(decisionTablePageSize);

		Text decisionTablePageSizeText = rootDocument.createTextNode(String.valueOf(userPreference.getDecisionTablePageSize()));
		decisionTablePageSize.appendChild(decisionTablePageSizeText);
		
		if (userPreference.getScsUserName() != null && userPreference.getScsUserPassword() != null) {
			Node scsUserName = rootDocument.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_SCS_USER_NAME_ELEMENT);
			userPreferenceItemElement.appendChild(scsUserName);

			Text scsUserNameText = rootDocument.createTextNode(userPreference.getScsUserName());
			scsUserName.appendChild(scsUserNameText);

			Node scsUserPassword = rootDocument.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_SCS_USER_PASSWORD_ELEMENT);
			userPreferenceItemElement.appendChild(scsUserPassword);

			Text scsUserPasswordText = rootDocument.createTextNode(Base64Utils.toBase64(userPreference.getScsUserPassword().getBytes()));
			scsUserPassword.appendChild(scsUserPasswordText);
		}

		Node isAutoUnLockOnReview = rootDocument.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_AUTO_UNLOCK_ON_REVIEW);
		userPreferenceItemElement.appendChild(isAutoUnLockOnReview);

		Text isAutoUnLockOnReviewText = rootDocument.createTextNode(String.valueOf(userPreference.isAutoUnLockOnReview()));
		isAutoUnLockOnReview.appendChild(isAutoUnLockOnReviewText);
		
		Node isGroupRelatedArtifacts = rootDocument.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_GROUP_RELATED_ARTIFACTS);
		userPreferenceItemElement.appendChild(isGroupRelatedArtifacts);

		Text isGroupRelatedArtifactsText = rootDocument.createTextNode(String.valueOf(userPreference.isGroupRelatedArtifacts()));
		isGroupRelatedArtifacts.appendChild(isGroupRelatedArtifactsText);
		
		Node isAllowCustomDomainValue = rootDocument.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_ALLOW_CUSTOM_DOMAIN_VALUES);
		userPreferenceItemElement.appendChild(isAllowCustomDomainValue);

		Text isAllowCustomDomainValueText = rootDocument.createTextNode(String.valueOf(userPreference.isAllowCustomDomainValues()));
		isAllowCustomDomainValue.appendChild(isAllowCustomDomainValueText);
		
		Node isShowColumnAliasIfPresent = rootDocument.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_SHOW_COLUMN_ALIAS_IFPRESENT);
		userPreferenceItemElement.appendChild(isShowColumnAliasIfPresent);

		Text isShowColumnAliasIfPresentText = rootDocument.createTextNode(String.valueOf(userPreference.isShowColumnAliasIfPresent()));
		isShowColumnAliasIfPresent.appendChild(isShowColumnAliasIfPresentText);
		
		Node defaultRtiFilterType = rootDocument.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_DEFAULT_RTI_FILTER_TYPE);
		userPreferenceItemElement.appendChild(defaultRtiFilterType);
		
		Text defaultRtiFilterTypeText = rootDocument.createTextNode(userPreference.getRtiDefaultFilterType());
		defaultRtiFilterType.appendChild(defaultRtiFilterTypeText);
		
		Node autoFitColumnsApproch = rootDocument.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_AUTO_FIT_COLUMNS_APPROCH);
		userPreferenceItemElement.appendChild(autoFitColumnsApproch);

		Text autoFitColumnsColumnsText = rootDocument.createTextNode(userPreference.getAutoFitColumnsApproch());
		autoFitColumnsApproch.appendChild(autoFitColumnsColumnsText);

		Node dashboardPortlet = rootDocument
				.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_DASHBOARD_PORTLET_ELEMENT);
		userPreferenceItemElement.appendChild(dashboardPortlet);

		for (DashboardPortlet portlet : (List<DashboardPortlet>) userPreference.getDashboardPortlets()) {
			Node portletElement = rootDocument
					.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_DASHBOARD_USER_PORTLET_ELEMENT);
			dashboardPortlet.appendChild(portletElement);

			Node portletId = rootDocument
					.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_DASHBOARD_PORTLET_ID_ELEMENT);
			portletElement.appendChild(portletId);

			Text portletIdText = rootDocument.createTextNode(portlet.getPortletId());
			portletId.appendChild(portletIdText);

			Node column = rootDocument
					.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_DASHBOARD_PORTLET_COLUMN_ELEMENT);
			portletElement.appendChild(column);

			Text columnText = rootDocument.createTextNode(String.valueOf(portlet.getColumn()));
			column.appendChild(columnText);

			Node row = rootDocument
					.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_DASHBOARD_PORTLET_ROW_ELEMENT);
			portletElement.appendChild(row);

			Text rowText = rootDocument.createTextNode(String.valueOf(portlet.getRow()));
			row.appendChild(rowText);

			Node height = rootDocument
					.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_DASHBOARD_PORTLET_HEIGHT_ELEMENT);
			portletElement.appendChild(height);

			Text heightText = rootDocument.createTextNode(String.valueOf(portlet.getHeight()));
			height.appendChild(heightText);

			Node colSpan = rootDocument
					.createElement(XMLRequestBuilderConstants.USER_PREFERENCE_DASHBOARD_PORTLET_COLSPAN_ELEMENT);
			portletElement.appendChild(colSpan);

			Text colSpanText = rootDocument.createTextNode(String.valueOf(portlet.getColSpan()));
			colSpan.appendChild(colSpanText);
		}
	}

	public UserPreferences getUserPreference() {
		return userPreference;
	}

	public void setUserPreference(UserPreferences userPreference) {
		this.userPreference = userPreference;
	}
}
