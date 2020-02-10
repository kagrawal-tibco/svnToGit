package com.tibco.cep.webstudio.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.i18n.DisplayUtils;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.model.rule.instance.Command;
import com.tibco.cep.webstudio.model.rule.instance.FilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.SingleFilter;
import com.tibco.cep.webstudio.model.rule.instance.impl.RelatedLinkImpl;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

/**
 * 
 * @author Vikram Patil
 */
public class DataSourcesLink extends AbstractRelatedLink {

	private List<SymbolInfo> symbolList;

	public DataSourcesLink(RelatedLink modelLink, AbstractSingleFilterBuilder parentClause, DataSourceField[] fields, boolean allowDrillDown) {
		this.parentClause = parentClause;
		this.allowDrillDown = allowDrillDown;
		this.model = modelLink;
		initialize();
	}
	
	public DataSourcesLink(RelatedLink modelLink, AbstractSingleFilterBuilder parentClause, List<SymbolInfo> fields, boolean allowDrillDown, AbstractEditor view) {
		this.parentClause = parentClause;
		this.symbolList = fields;
		this.allowDrillDown = allowDrillDown;
		this.model = modelLink;
		this.editor = view;
		initialize();
	}
	
	private void createSubMenu(SymbolInfo symbol, Menu menu){
		if (!ArtifactUtil.isPrimitive(symbol.getType())){
			RelatedLink model = new RelatedLinkImpl();
			model.setLinkText(symbol.getAlias());
			model.setLinkType(symbol.getType());

			getArtifactProperties(model, menu);
		}
	}
	
	@Override
	protected void initialize() {
		if (this.model != null) {
			AbstractBuilderSubClauseLayout clause = this.parentClause != null ? this.parentClause.parentClause : null;
			if (getPreviousLink() == null && clause instanceof CommandBuilderSubClause) {
				// for command builders, need to provide the context link to properly obtain the display properties
				Command command = ((CommandBuilderSubClause) clause).getCommand();
				String alias = command.getAlias();
				String type = command.getType();
				String qName = alias + '.' + this.model.getLinkText();
				if (getDisplayProperties().containsKey(qName)) {
					this.setContents(getDisplayProperties().get(qName));
				} else {
					String linkText = DisplayUtils.getLinkText(this, type);
					this.setContents(linkText);
				}
			} else {
				this.setContents(DisplayUtils.getLinkText(getDisplayProperties(), this));
			}
		}
		super.initialize();
	}

	@Override
	protected void handleLinkClicked() {
		Menu menu = new Menu();
		menu.setShowShadow(true);  
		menu.setShadowDepth(10);
		
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		SymbolInfo drillableField = null;
		for (final SymbolInfo field : symbolList) {
			String dispText = getFieldDisplayText(field);
			if (dispText.equals(getSelectedName()) && allowDrillDown) {	
				drillableField = field;
				continue;
			} else {
				if (DisplayUtils.isHidden(this, field)) {
					continue;
				}
				final MenuItem newItem = new MenuItem(dispText, ArtifactUtil.getEntityIcon(field.getType()));
				menuItems.add(newItem);
				newItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						if (!newItem.getTitle().equals(getSelectedName())) {
							setContents(newItem.getTitle());
							model.setLinkText(field.getAlias());
							model.setLinkType(field.getType());
							
							if(nextLink != null) {
								if (isValueLink()) {
									removeTrailingValueLinks(nextLink);
								} else {
									removeTrailingLinks(nextLink);
								}
								setAccessSuffix(false);
							} else {
								// make editor dirty
								editor.makeDirty();
							}
							
							parentClause.updateValueFormFromFilterValue();
						}
					}
				});
			}
		}
		ArtifactUtil.setMenuScroll(menuItems.size(), menu);
		
		menu.setItems(menuItems.toArray(new MenuItem[menuItems.size()]));
		menu.setPageLeft(getPageLeft());
		menu.setPageTop(getPageTop()+15);
		
		if (drillableField != null) {
			createSubMenu(drillableField, menu);
		} else {
			menu.draw();
		}		
	}

	private String getFieldDisplayText(SymbolInfo field) {
		String dispText = null;
		if (getPreviousLink() == null) {
			AbstractBuilderSubClauseLayout clause = this.parentClause != null ? this.parentClause.parentClause : null;
			if (clause instanceof CommandBuilderSubClause) {
				// for command builders, need to provide the context link to properly obtain the display properties
				Command command = ((CommandBuilderSubClause) clause).getCommand();
				String alias = command.getAlias();
				String type = command.getType();
				String qName = alias + '.' + field.getAlias();
				if (getDisplayProperties().containsKey(qName)) {
					return getDisplayProperties().get(qName);
				} else {
					dispText = DisplayUtils.getSymbolText(field, this, type, false);
				}
			}
		}
		if (dispText == null) {
			dispText = DisplayUtils.getDisplayText(getDisplayProperties(), field, this, true);
		}
		
		
		return dispText;
	}

	@Override
	public void setPreviousLink(IRelatedLink link) {
		throw new UnsupportedOperationException("Cannot set previous related link for Operator Links");
	}
	
	@Override
	public void update() {
		if (this.nextLink != null) {
			this.nextLink.update();
		}
	}

	@Override
	protected void populateMenuFromDS(Menu menu, final DataSource asDataSource) {
		MenuItem fieldsMenu = new MenuItem(globalMsgBundle.text_fields_of_an_entity(DisplayUtils.getLinkText(getDisplayProperties(), this)));
		menu.addItem(fieldsMenu);
		
		Menu fieldsSubMenu = new Menu();
		fieldsSubMenu.setOverflow(Overflow.AUTO);
		fieldsMenu.setSubmenu(fieldsSubMenu);
		
		ArtifactUtil.setMenuScroll(fieldList.size(), fieldsSubMenu);
		
		for (final SymbolInfo field : (List<SymbolInfo>)fieldList) {
			if (DisplayUtils.isHidden(this, field)) {
				continue;
			}
			final MenuItem newItem = new MenuItem(DisplayUtils.getDisplayText(getDisplayProperties(), field, this, false), ArtifactUtil.getEntityIcon(field.getType()));  
			fieldsSubMenu.addItem(newItem);
			
			newItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					setAccessSuffix(true);
					RelatedLink newLink = new RelatedLinkImpl();
					newLink.setLinkText(field.getAlias());
					newLink.setLinkType(field.getType());
					if (nextLink != null) {
						removeTrailingValueLinks(nextLink);
						nextLink = null;
					}
					FilterValue filterValue = ((SingleFilter)parentClause.getFilter()).getFilterValue();
					if (filterValue instanceof RelatedFilterValue) {
						((RelatedFilterValue) filterValue).addLink(newLink);
					}
				}
			});
		}		
	}
	
	/**
	 * Check if the link is of base builder or value builder
	 * @return
	 */
	private boolean isValueLink() {
		boolean valueLink = false;
		
		List<RelatedLink> links = ((SingleFilter) parentClause.filter).getLinks();
		if (links.indexOf(nextLink.getModel()) != -1) {
			valueLink = false;
		} else {
			FilterValue filterValue = ((SingleFilter) parentClause.filter).getFilterValue();
			if (filterValue instanceof RelatedFilterValue) {
				links = ((RelatedFilterValue) filterValue).getLinks();
				if (links.indexOf(nextLink.getModel()) != -1) {
					valueLink = true;
				}
			}
		}
		
		return valueLink;
	}

	@Override
	public void populateOperatorMenu(Menu opSubMenu) {
	}

	@Override
	protected void updateFromDataSourceField() {
	}

	@Override
	public IBuilderOperator getOperator() {
		return null;
	}

	@Override
	protected List<IBuilderOperator> getOperatorsForType(FieldType type) {
		return null;
	}
	
	public List<SymbolInfo> getSymbols(){
		return this.symbolList;
	}
}
