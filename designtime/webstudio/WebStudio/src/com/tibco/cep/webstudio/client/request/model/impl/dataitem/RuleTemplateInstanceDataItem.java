/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model.impl.dataitem;

import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Random;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.model.ruletemplate.BindingInfo;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;
import com.tibco.cep.webstudio.client.util.RuleTemplateHelper;
import com.tibco.cep.webstudio.model.rule.instance.Binding;
import com.tibco.cep.webstudio.model.rule.instance.Command;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.FilterValue;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;
import com.tibco.cep.webstudio.model.rule.instance.RangeFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;
import com.tibco.cep.webstudio.model.rule.instance.SimpleFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.SingleFilter;

/**
 * Used to represent contents for Rule Template Instance
 * 
 * @author Vikram Patil
 */
public class RuleTemplateInstanceDataItem implements IRequestDataItem {
	private RuleTemplateInstance ruleTemplateInstance;
	private boolean viewTemplate;
	private List<BindingInfo> bindingInfoList;
	private List<Command> commandList;
	private boolean isSyncMerge = false;

	public RuleTemplateInstanceDataItem(RuleTemplateInstance ruleTemplateInstance, boolean isSyncMerge) {
		this.ruleTemplateInstance = ruleTemplateInstance;
		this.isSyncMerge = isSyncMerge;
	}

	public Object getRuleTemplateInstance() {
		return ruleTemplateInstance;
	}

	public void setRuleTemplateInstance(RuleTemplateInstance ruleTemplateInstance) {
		this.ruleTemplateInstance = ruleTemplateInstance;
	}

	public boolean isViewTemplate() {
		return viewTemplate;
	}

	public void setViewTemplate(boolean viewTemplate) {
		this.viewTemplate = viewTemplate;
	}

	public List<BindingInfo> getBindingInfoList() {
		return bindingInfoList;
	}

	public void setBindingInfoList(List<BindingInfo> bindingInfoList) {
		this.bindingInfoList = bindingInfoList;
	}

	public List<Command> getCommandList() {
		return commandList;
	}

	public void setCommandList(List<Command> commandList) {
		this.commandList = commandList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.webstudio.client.request.model.ISerializableObject#serialize
	 * (com.google.gwt.xml.client.Document, com.google.gwt.xml.client.Node)
	 */
	public void serialize(Document rootDocument, Node rootNode) {
		Node artifactItemElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_ITEM_ELEMENT);
		rootNode.appendChild(artifactItemElement);

		String ruleTemplateInstanceName = ruleTemplateInstance.getName();
		if (ruleTemplateInstanceName.indexOf(".") != -1) {
			ruleTemplateInstanceName = ruleTemplateInstanceName.substring(0, ruleTemplateInstanceName.indexOf("."));
		}

		Node artifactNameElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_PATH_ELEMENT);
		Text artifactNameText = rootDocument.createTextNode(ruleTemplateInstanceName);
		artifactNameElement.appendChild(artifactNameText);
		artifactItemElement.appendChild(artifactNameElement);

		Node artifactTypeElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_TYPE_ELEMENT);
		Text artifactTypeText = rootDocument.createTextNode("ruletemplateinstance");
		artifactTypeElement.appendChild(artifactTypeText);
		artifactItemElement.appendChild(artifactTypeElement);

		Node artifactExtnElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_FILE_EXTN_ELEMENT);
		Text artifactExtnText = rootDocument.createTextNode("ruletemplateinstance");
		artifactExtnElement.appendChild(artifactExtnText);
		artifactItemElement.appendChild(artifactExtnElement);

		Node implementsPathElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_IMPLEMENTS_PATH_ELEMENT);
		String implementsPath = ruleTemplateInstance.getImplementsPath();

		if (implementsPath.indexOf(".") != -1) {
			implementsPath = implementsPath.substring(0, implementsPath.indexOf("."));
		}
		Text implementsPathText = rootDocument.createTextNode(implementsPath);
		implementsPathElement.appendChild(implementsPathText);
		artifactItemElement.appendChild(implementsPathElement);
		
		Node descriptionElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_DESCRIPTION_ELEMENT);
		Text descriptionText = rootDocument.createTextNode(ruleTemplateInstance.getDescription());
		descriptionElement.appendChild(descriptionText);
		artifactItemElement.appendChild(descriptionElement);
		
		Node priorityElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_PRIORITY_ELEMENT);
		Text priorityText = rootDocument.createTextNode(ruleTemplateInstance.getPriority()+"");
		priorityElement.appendChild(priorityText);
		artifactItemElement.appendChild(priorityElement);

		Node isSyncMergeElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_IS_SYNC_MERGE);
		Text isSyncMergeText = rootDocument.createTextNode(String.valueOf(this.isSyncMerge()));
		isSyncMergeElement.appendChild(isSyncMergeText);
		artifactItemElement.appendChild(isSyncMergeElement);

		StringBuffer artifactContentData = new StringBuffer();
		if (isViewTemplate()) {
			Node viewElement = createBindings(rootDocument);
			artifactContentData.append(viewElement.toString());
		} else {
			// Create a root builder element
			Node builderElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_BUILDER_ELEMENT);

			Node conditionElement = createConditions(rootDocument);
			builderElement.appendChild(conditionElement);

			Node commandsElement = createCommands(rootDocument);
			builderElement.appendChild(commandsElement);

			artifactContentData.append(builderElement.toString());
		}

		Node artifactContentElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_CONTENTS_ELEMENT);
		Text artifactContentsText = rootDocument.createTextNode(artifactContentData.toString());
		artifactContentElement.appendChild(artifactContentsText);
		artifactItemElement.appendChild(artifactContentElement);
	}

	private Node createBindings(Document rootDocument) {
		Node viewElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_VIEW_ELEMENT);

		Node bindingElement, bindingIdElement, bindingValueElement;
		Text idText, valueText;

		for (Binding binding : (List<Binding>) ruleTemplateInstance.getBindings()) {
			bindingElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_BINDINGINFO_ELEMENT);

			bindingIdElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_BINDINGINFO_ID_ELEMENT);
			idText = rootDocument.createTextNode(binding.getId());
			bindingIdElement.appendChild(idText);
			bindingElement.appendChild(bindingIdElement);

			bindingValueElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_BINDINGINFO_VALUE_ELEMENT);
			String bindingValue = binding.getValue();
			if (bindingValue != null) {
				if (checkIfBindingTypeIsDateTime(bindingInfoList, binding.getId())) {
					bindingValue = formatDateValue(bindingValue);
				}
				valueText = rootDocument.createTextNode(bindingValue);
				bindingValueElement.appendChild(valueText);
			}
			bindingElement.appendChild(bindingValueElement);

			viewElement.appendChild(bindingElement);
		}

		return viewElement;
	}

	private Node createConditions(Document rootDocument) {
		Node conditionElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_CONDITION_ELEMENT);
		conditionElement.appendChild(createMultiFilter(ruleTemplateInstance.getConditionFilter(), rootDocument));

		return conditionElement;
	}

	private Node createCommands(Document rootDocument) {
		Node commandElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_COMMAND_ELEMENT);

		Node commandInfoElement, commandAliasElement, commandTypeElement, commandActionTypeElement;
		Text aliasText, typeText, actionTypeText;

		List<Command> addedCommands = (List<Command>) ruleTemplateInstance.getActions().getActions();
		
		for (Command basecommand : commandList) {
			int commandIndex = addedCommands.indexOf(basecommand);
			if (commandIndex != -1) {
				Command command = addedCommands.get(commandIndex);
				if (command != null) {
					commandInfoElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_COMMANDINFO_ELEMENT);

					commandAliasElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_COMMANDINFO_ALIAS_ELEMENT);
					aliasText = rootDocument.createTextNode(command.getAlias());
					commandAliasElement.appendChild(aliasText);
					commandInfoElement.appendChild(commandAliasElement);

					commandTypeElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_COMMANDINFO_TYPE_ELEMENT);
					typeText = rootDocument.createTextNode(command.getType());
					commandTypeElement.appendChild(typeText);
					commandInfoElement.appendChild(commandTypeElement);

					commandActionTypeElement = rootDocument
							.createElement(XMLRequestBuilderConstants.RTI_COMMANDINFO_ACTIONTYPE_ELEMENT);
					actionTypeText = rootDocument.createTextNode(command.getActionType());
					commandActionTypeElement.appendChild(actionTypeText);
					commandInfoElement.appendChild(commandActionTypeElement);

					if (command.getSubClause() != null) {
						for (Filter filter : (List<Filter>) command.getSubClause().getFilters()) {
							commandInfoElement.appendChild(createSingleFilter((SingleFilter) filter, rootDocument));
						}
					}

					commandElement.appendChild(commandInfoElement);

				}
			}
			
		}

		return commandElement;
	}

	private Node createMultiFilter(MultiFilter multiFilter, Document rootDocument) {
		Node filterElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_FILTER_ELEMENT);

		if (multiFilter.getFilterId() == null || multiFilter.getFilterId().isEmpty()) {
			multiFilter.setFilterId(getUniqueFilterId());
		}
		Node filterIdElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_FILTER_ID_ELEMENT);
		Text filterIdText = rootDocument.createTextNode(multiFilter.getFilterId());
		filterIdElement.appendChild(filterIdText);
		filterElement.appendChild(filterIdElement);
		
		Node matchTypeElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_FILTER_MATCHTYPE_ELEMENT);
		Text matchTypeText = rootDocument.createTextNode(multiFilter.getMatchType());
		matchTypeElement.appendChild(matchTypeText);
		filterElement.appendChild(matchTypeElement);

		for (Filter filter : (List<Filter>) multiFilter.getSubClause().getFilters()) {
			if (filter instanceof MultiFilter) {
				filterElement.appendChild(createMultiFilter((MultiFilter) filter, rootDocument));
			} else {
				filterElement.appendChild(createSingleFilter((SingleFilter) filter, rootDocument));
			}
		}
		return filterElement;
	}

	private Node createSingleFilter(SingleFilter filter, Document rootDocument) {
		Node filterElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_FILTER_ELEMENT);

		createLinks(filter.getLinks(), rootDocument, filterElement);
		
		if (filter.getFilterId() == null || filter.getFilterId().isEmpty()) {
			filter.setFilterId(getUniqueFilterId());
		}
		Node filterIdElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_FILTER_ID_ELEMENT);
		Text filterIdText = rootDocument.createTextNode(filter.getFilterId());
		filterIdElement.appendChild(filterIdText);
		filterElement.appendChild(filterIdElement);

		Node filterOperatorElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_FILTER_OPERATOR_ELEMENT);
		Text filterOperatorText = rootDocument.createTextNode(filter.getOperator() == null ? "" : filter.getOperator());
		filterOperatorElement.appendChild(filterOperatorText);
		filterElement.appendChild(filterOperatorElement);
		
		filterElement.appendChild(createFilterValue(filter.getFilterValue(), rootDocument, checkIfLinkTypeIsDateTime(filter.getLinks())));

		return filterElement;
	}

	private Node createFilterValue(FilterValue filterValue, Document rootDocument, boolean isLinkTypeDateTime) {
		Node filterValueElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_FILTER_VALUE_ELEMENT);

		if (filterValue instanceof SimpleFilterValue) {
			Node filterValueSimpleElement = rootDocument
					.createElement(XMLRequestBuilderConstants.RTI_FILTER_VALUE_SIMPLE_ELEMENT);
			String filterData = (((SimpleFilterValue) filterValue).getValue() == null) ? ""
					: ((SimpleFilterValue) filterValue).getValue();
			
			if (filterData != null && !filterData.isEmpty() && isLinkTypeDateTime) {
				filterData = formatDateValue(filterData);
			}
			Text filterValueSimpleText = rootDocument.createTextNode(filterData);
			filterValueSimpleElement.appendChild(filterValueSimpleText);
			filterValueElement.appendChild(filterValueSimpleElement);

		} else if (filterValue instanceof RangeFilterValue) {
			Node filterValueMinElement = rootDocument
					.createElement(XMLRequestBuilderConstants.RTI_FILTER_VALUE_MIN_ELEMENT);
			Text filterValueMinText = rootDocument.createTextNode(((RangeFilterValue) filterValue).getMinValue());
			filterValueMinElement.appendChild(filterValueMinText);
			filterValueElement.appendChild(filterValueMinElement);

			Node filterValueMaxElement = rootDocument
					.createElement(XMLRequestBuilderConstants.RTI_FILTER_VALUE_MAX_ELEMENT);
			Text filterValueMaxText = rootDocument.createTextNode(((RangeFilterValue) filterValue).getMaxValue());
			filterValueMaxElement.appendChild(filterValueMaxText);
			filterValueElement.appendChild(filterValueMaxElement);

		} else if (filterValue instanceof RelatedFilterValue) {
			createLinks(((RelatedFilterValue) filterValue).getLinks(), rootDocument, filterValueElement);
		}

		return filterValueElement;
	}

	private void createLinks(List<RelatedLink> links, Document rootDocument, Node rootNode) {
		Node linkElement, linkNameElement, linkTypeElement;
		Text linkNameText, linkTypeText;

		for (RelatedLink link : links) {
			linkElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_FILTER_LINK_ELEMENT);

			linkNameElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_FILTER_LINK_NAME_ELEMENT);
			linkNameText = rootDocument.createTextNode(link.getLinkText());
			linkNameElement.appendChild(linkNameText);
			linkElement.appendChild(linkNameElement);

			linkTypeElement = rootDocument.createElement(XMLRequestBuilderConstants.RTI_FILTER_LINK_TYPE_ELEMENT);
			linkTypeText = rootDocument.createTextNode(link.getLinkType());
			linkTypeElement.appendChild(linkTypeText);
			linkElement.appendChild(linkTypeElement);

			rootNode.appendChild(linkElement);
		}
	}
	
	/**
	 * Check if Link type is of DateTime
	 * 
	 * @param links
	 * @return
	 */
	private boolean checkIfLinkTypeIsDateTime(List<RelatedLink> links) {
		RelatedLink lastLink = links.get(links.size() - 1);
		
		String linkType = lastLink.getLinkType();;
		if (linkType.indexOf(".") != -1) {
			linkType = linkType.substring(0, linkType.indexOf("."));
		}
		
		if (linkType != null && !linkType.isEmpty() && linkType.equalsIgnoreCase("DateTime")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Check if Binding type is of DateTime
	 * 
	 * @param bindingInfoList
	 * @param bindingId
	 * @return
	 */
	private boolean checkIfBindingTypeIsDateTime(List<BindingInfo> bindingInfoList, String bindingId) {
		for (BindingInfo bindingInfo : bindingInfoList) {
			if (bindingInfo.getId().equals(bindingId)) {
				if (bindingInfo.getType().equalsIgnoreCase("DateTime")) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * Format DateTime as per the format specified
	 * 
	 * @param dateValue
	 * @return
	 */
	private String formatDateValue(String dateValue) {
		DateTimeFormat dtFmt = DateTimeFormat.getFormat(RuleTemplateHelper.DATE_TIME_FORMAT);
		
		Date dt = null;
		try {
			dt = dtFmt.parse(dateValue);
		} catch (IllegalArgumentException illegalArgExcep) {
			dt = null;
		}
		
		if (dt != null) {
			String formatedDate = dtFmt.format(dt);
			
			dateValue = dateValue.substring(dateValue.indexOf("."));
			String originalTimeZoneValue = dateValue.lastIndexOf("-") != -1? dateValue.substring(dateValue.lastIndexOf("-")):dateValue.lastIndexOf("+") != -1?dateValue.substring(dateValue.lastIndexOf("+")):"";
			String formatedTimeZoneValue = formatedDate.substring(formatedDate.indexOf(".")+4);
			
			if (!originalTimeZoneValue.isEmpty() && !originalTimeZoneValue.equalsIgnoreCase(formatedTimeZoneValue)) {
				formatedDate = formatedDate.substring(0, formatedDate.indexOf(".")+4);
				dateValue = formatedDate + originalTimeZoneValue;
			} else {
				dateValue = formatedDate;
			}
		}
		
		return dateValue;
	}
	
	/**
	 * Generate a unique filter Id
	 * @return
	 */
	private String getUniqueFilterId() {
		return Long.toString(new Date().getTime()) + Random.nextInt(1000) + Random.nextInt(1000) + Random.nextInt(1000); 
	}

	public boolean isSyncMerge() {
		return isSyncMerge;
	}
}
