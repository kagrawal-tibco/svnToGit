package com.tibco.cep.webstudio.client.widgets;

import java.util.List;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.tibco.cep.webstudio.client.FilterOperatorUtils;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.i18n.DisplayUtils;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.operators.FilterOperator;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

public class FilterRelatedLink extends AbstractRelatedLink {

	public FilterRelatedLink(RelatedLink model, AbstractSingleFilterBuilder subClause, IRelatedLink previousLink, AbstractEditor view) {
		super(model, subClause, previousLink);
		this.editor = view;
	}

	@Override
	protected List<IBuilderOperator> getOperatorsForType(FieldType type) {
		return FilterOperatorUtils.getOperators(type);
	}

	@Override
	public void populateMenuFromDS(Menu menu, final DataSource asDataSource) {
		MenuItem operatorsMenu = new MenuItem(OperatorDisplayNameUtil.getOperator("Operators"));
		MenuItem fieldsMenu = new MenuItem(globalMsgBundle.text_fields_of_an_entity(DisplayUtils.getLinkText(getDisplayProperties(), (AbstractRelatedLink) getPreviousLink())));
		menu.setItems(operatorsMenu, fieldsMenu);
		
		Menu opSubMenu = new Menu();
		operatorsMenu.setSubmenu(opSubMenu);
		populateOperatorMenu(opSubMenu);
		
		Menu fieldsSubMenu = new Menu();
		fieldsMenu.setSubmenu(fieldsSubMenu);
		
		for (final SymbolInfo field : (List<SymbolInfo>)fieldList) {
			if (DisplayUtils.isHidden((AbstractRelatedLink) getPreviousLink(), field)) {
				continue;
			}
			final String displayText = DisplayUtils.getDisplayText(getDisplayProperties(), field, (AbstractRelatedLink) getPreviousLink(), false);
			final MenuItem newItem = new MenuItem(displayText, ArtifactUtil.getEntityIcon(field.getType()));  
			fieldsSubMenu.addItem(newItem);
			newItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				@Override
				public void onClick(MenuItemClickEvent event) {
					if (!newItem.getTitle().equals(getSelectedName())) {
//						setContents(field.getAlias());
						setContents(displayText);
						getPreviousLink().setAccessSuffix(true);
						model.setLinkText(field.getAlias());
						model.setLinkType(field.getType());

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
					
					parentClause.updateValueFormFromFilterValue();
				}
			});
		}
	}
	
	@Override
	public IBuilderOperator getOperator() {
		FilterOperator[] values = FilterOperator.values();
		for (FilterOperator fOp : values) {
			if (fOp.getValue().equals(getContents())) {
				return fOp;
			}
		}
		return null;
	}
	
}
