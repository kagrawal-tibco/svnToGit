package com.tibco.cep.webstudio.client.widgets;

import java.util.HashMap;
import java.util.List;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.i18n.DisplayUtils;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.FilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.SingleFilter;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

/**
 * 
 *  @author Ryan 
 *  @author Vikram Patil
 */
public abstract class AbstractRelatedLink extends Label implements IRelatedLink {

	protected IRelatedLink prevLink;
	protected IRelatedLink nextLink;
	protected boolean allowDrillDown = true;
	protected RelatedLink model;
	protected AbstractSingleFilterBuilder parentClause;
	protected AbstractEditor editor;
	protected List<SymbolInfo> fieldList;
	
	protected static GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	public AbstractRelatedLink() {
		super();
	}
	
	public AbstractRelatedLink(RelatedLink model, AbstractSingleFilterBuilder subClause, IRelatedLink previousLink) {
		this.model = model;
		this.parentClause = subClause;
		this.prevLink = previousLink;
		initialize();
	}
	
	protected abstract void populateMenuFromDS(Menu menu, DataSource asDataSource);
	protected abstract List<IBuilderOperator> getOperatorsForType(FieldType type);
	
	protected HashMap<String, String> getDisplayProperties() {
		return ((RuleTemplateInstanceEditor)editor).getDisplayProperties();
	}
	
	public void populateOperatorMenu(Menu opSubMenu) {
		FieldType type = getLinkType();
		List<IBuilderOperator> operators = getOperatorsForType(type);
		
		for (IBuilderOperator op : operators) {
			final String opName = op.getValue();
			final MenuItem newItem = new MenuItem(OperatorDisplayNameUtil.getOperator(opName), null);
			newItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					parentClause.getOperatorLink().setContents(OperatorDisplayNameUtil.getOperator(opName));
					parentClause.getOperatorLink().setAutoWidth();

					setOperatorLink(opName);
					removeTrailingLinks(AbstractRelatedLink.this);
					
					parentClause.updateValueForm();
					AbstractRelatedLink.this.destroy();
				}
			});
			opSubMenu.addItem(newItem);
		}
	}
	
	private FieldType getLinkType(){
		FieldType[] fieldTypes = FieldType.values();
		for (FieldType field : fieldTypes){
			if (field.getValue().equals(getPreviousLink().getModel().getLinkType())){
				return field;
			}
		}
		return null;
	}
	
	protected void updateFromDataSourceField() {
		if (nextLink != null) {
			// first, destroy any existing related link chain
			((Label)nextLink).destroy();
			nextLink = null;
		}
		setContents(DisplayUtils.getLinkText(getDisplayProperties(), this));
		getPreviousLink().setAccessSuffix(true);
	}

	@Override
	public IRelatedLink getNextLink() {
		return nextLink;
	}

	@Override
	public IRelatedLink getPreviousLink() {
		return prevLink;
	}

	@Override
	public void setNextLink(IRelatedLink link) {
		this.nextLink = link;
		link.setPreviousLink(this);
	}

	@Override
	public void setPreviousLink(IRelatedLink link) {
		this.prevLink = link;
	}
	
	public List<SymbolInfo> getFieldList() {
		return this.fieldList;
	}

	public void setAccessSuffix(boolean showSuffix) {
		String currentContents = getContents();
		if (showSuffix) {
			if (currentContents.endsWith("'s")) {
				return; // nothing to do
			}
			setContents(getContents()+"'s");
		} else {
			if (currentContents.endsWith("'s")) {
				setContents(currentContents.substring(0, currentContents.length()-2));
			}
		}
	}
	
	public String getSelectedName() {
		String currentContents = getContents();
		if (currentContents.endsWith("'s")) {
			return currentContents.substring(0, currentContents.length()-2);
		}
		return currentContents;
	}
	
	protected void initialize() {
		this.setLayoutAlign(VerticalAlignment.CENTER);
		this.setStyleName("ws-filterlink");
		this.setAutoHeight();
		this.setAutoWidth();
		this.setWrap(false);
		this.setOverflow(Overflow.VISIBLE);
		
		this.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(
					com.smartgwt.client.widgets.events.ClickEvent event) {
				handleLinkClicked();
			}
		});
	}

	@Override
	public void update() {
		if (getPreviousLink() != null) {
			updateFromDataSourceField();
			if (parentClause != null) {
				parentClause.updateValueForm();
			}
		}
	}

	@Override
	protected void onDestroy() {
		if (this.nextLink != null) {
			this.nextLink = null;
		}
		super.onDestroy();
	}
	
	protected void handleLinkClicked() {
		final Menu menu = new Menu();
		menu.setShowShadow(true);  
		menu.setShadowDepth(10);
		menu.setPageLeft(getPageLeft());
		menu.setPageTop(getPageTop()+15);
		
		if (ArtifactUtil.isPrimitive((getPreviousLink().getModel().getLinkType()))){
			fieldList = null;
			populateOperatorMenu(menu);
			
			if (menu.getItems().length > 0)	{
				ArtifactUtil.setMenuScroll(menu.getItems().length, menu);
				menu.draw();			
			}
		} else {
			getArtifactProperties(getPreviousLink().getModel(), menu);
		}
	}
	
	@Override
	public RelatedLink getModel() {
		return model;
	}
	
	/**
	 * Remove any dependencies based on the the operator
	 * 
	 * @param operator
	 */
	protected void setOperatorLink(String operator) {
		Filter f = parentClause.filter;
		if (f instanceof SingleFilter) {
			SingleFilter filter = (SingleFilter) f;
			filter.setOperator(operator);
		}
	}
	
	/**
	 * Remove any trailing links on the left side of the filter
	 * 
	 * @param nextLink
	 */
	protected void removeTrailingLinks(IRelatedLink nextLink) {
		Filter f = parentClause.filter;
		if (f instanceof SingleFilter) {
			RelatedLink modelLink = nextLink.getModel();
			List<RelatedLink> links = ((SingleFilter) f).getLinks();
			int idx = links.indexOf(modelLink);
			while (idx != -1 && links.size() > idx) {
				((SingleFilter) f).removeRelatedLink(links.get(idx));
			}
		}
	}

	/**
	 * Remove any trailing links on the right side of the filter
	 * 
	 * @param nextLink
	 */
	protected void removeTrailingValueLinks(IRelatedLink nextLink) {
		Filter f = parentClause.filter;
		if (f instanceof SingleFilter) {
			RelatedLink modelLink = nextLink.getModel();
			FilterValue filterValue = ((SingleFilter) f).getFilterValue();
			if (filterValue instanceof RelatedFilterValue) {
				List<RelatedLink> links = ((RelatedFilterValue) filterValue).getLinks();
				int idx = links.indexOf(modelLink);
				while (idx != -1 && links.size() > idx) {
					((RelatedFilterValue) filterValue).removeLink(links.get(idx));
				}
			}
			
		}
	}
	
	/**
	 * Fetch artifact properties for the specified entity
	 * 
	 * @param model
	 * @param menu
	 */
	protected void getArtifactProperties(RelatedLink model, Menu menu) {
		if (this instanceof CommandRelatedLink) {
			fieldList = ArtifactUtil.getCommandSymbols(model.getLinkType(),
					model.getLinkText(),
					((RuleTemplateInstanceEditor) this.editor).getCommandSymbols());
			if	(this.fieldList == null) {
				fieldList = ArtifactUtil.getSymbols(model.getLinkType(),
						model.getLinkText(),
						((RuleTemplateInstanceEditor) editor).getSymbols());
			}
		} else {
			fieldList = ArtifactUtil.getSymbols(model.getLinkType(),
					model.getLinkText(),
					((RuleTemplateInstanceEditor) editor).getSymbols());
		}

		populateMenuFromDS(menu, null);
		
		ArtifactUtil.setMenuScroll(menu.getItems().length, menu);
		menu.draw();
	}
	
	/**
	 * Removes the suffix for simple type
	 */
	protected void removeSuffix(){
		this.setAccessSuffix(false);
	}
	
}
