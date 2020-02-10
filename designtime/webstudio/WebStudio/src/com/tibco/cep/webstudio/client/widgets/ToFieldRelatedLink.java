package com.tibco.cep.webstudio.client.widgets;

import java.util.List;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.i18n.DisplayUtils;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.model.rule.instance.FilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.impl.RelatedLinkImpl;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

/**
 * 
 * @author Ryan
 * @author Vikram Patil
 */
public class ToFieldRelatedLink extends AbstractRelatedLink {
	private FilterValue value;

	public ToFieldRelatedLink(RelatedLink modelLink, FilterValue value, AbstractRelatedLink previousLink, AbstractEditor view) {
		super(modelLink, null, previousLink);
		this.value = value;
		this.editor = view;
	}

	@Override
	protected void handleLinkClicked() {
		final Menu menu = new Menu();
		menu.setShowShadow(true);  
		menu.setShadowDepth(10);
		menu.setPageLeft(getPageLeft());
		menu.setPageTop(getPageTop()+15);
		
		SymbolInfo containedSymbol = null;
		AbstractRelatedLink previousLink = (AbstractRelatedLink)getPreviousLink();
		
		if (previousLink != null && previousLink.fieldList != null) {
			for (final SymbolInfo field : (List<SymbolInfo>)previousLink.fieldList){
				if (field.getAlias().equals(getSelectedName())) {
					containedSymbol = field;
					continue;
				}
				if (DisplayUtils.isHidden((AbstractRelatedLink) getPreviousLink(), field)) {
					continue;
				}
				final MenuItem newItem = new MenuItem(DisplayUtils.getDisplayText(getDisplayProperties(), field, (AbstractRelatedLink) this.getPreviousLink(), false), ArtifactUtil.getEntityIcon(field.getType()));  
				menu.addItem(newItem);
				newItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						if (!newItem.getTitle().equals(getSelectedName())) {
							setContents(field.getAlias());
							model.setLinkText(field.getAlias());
							model.setLinkType(field.getType());
							getPreviousLink().setAccessSuffix(true);
							if (nextLink != null) {
								removeTrailingLinks(nextLink);
								nextLink.update();
								nextLink = null;

								removeSuffix();
							}
						} else {
							// make editor dirty
							editor.makeDirty();
						}
					}
				});
			}

			if (containedSymbol != null && !ArtifactUtil.isPrimitive(containedSymbol.getType())){
				RelatedLink model = new RelatedLinkImpl();
				model.setLinkText(containedSymbol.getAlias());
				model.setLinkType(containedSymbol.getType());
				
				getArtifactProperties(model, menu);
			} else {
				menu.draw();
			}
		}
	}


	@Override
	public void populateOperatorMenu(Menu opSubMenu) {
		return;
	}

	@Override
	public void populateMenuFromDS(Menu menu, final DataSource asDataSource) {
		if (fieldList != null && fieldList.size() > 0) {
			MenuItem fieldsMenu = new MenuItem(globalMsgBundle.text_fields_of_an_entity(DisplayUtils.getLinkText(getDisplayProperties(), this)));
			Menu fieldsSubMenu = new Menu();
			fieldsMenu.setSubmenu(fieldsSubMenu);
			menu.addItem(fieldsMenu);
			
			for (final SymbolInfo field : (List<SymbolInfo>)fieldList) {
				if (DisplayUtils.isHidden((AbstractRelatedLink) getPreviousLink(), field)) {
					continue;
				}
				final MenuItem newItem = new MenuItem(DisplayUtils.getDisplayText(getDisplayProperties(), field, (AbstractRelatedLink) getPreviousLink(), false), ArtifactUtil.getEntityIcon(field.getType()));  
				fieldsSubMenu.addItem(newItem);
				newItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						setAccessSuffix(true);
						RelatedLink newLink = new RelatedLinkImpl();
						newLink.setLinkText(field.getAlias());
						newLink.setLinkType(field.getType());
						if (nextLink != null) {
							removeTrailingLinks(nextLink);
							nextLink = null;
						}
						if (value instanceof RelatedFilterValue) {
							((RelatedFilterValue) value).addLink(newLink);
						}
					}
				});
			}
		}
	}

	@Override
	protected void removeTrailingLinks(IRelatedLink nextLink) {
		if (value instanceof RelatedFilterValue) {
			RelatedLink modelLink = nextLink.getModel();
			List<RelatedLink> links = ((RelatedFilterValue)value).getLinks();
			int idx = links.indexOf(modelLink);
			while (links.size() > idx) {
				((RelatedFilterValue)value).removeLink(links.get(idx));
			}
		}
	}

	@Override
	public IBuilderOperator getOperator() {
		return null;
	}

	@Override
	protected List<IBuilderOperator> getOperatorsForType(FieldType type) {
		return null;
	}
}
