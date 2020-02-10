package com.tibco.cep.webstudio.client.widgets;

import java.util.HashMap;
import java.util.List;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.FieldType;
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
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedListener;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.SingleFilter;
import com.tibco.cep.webstudio.model.rule.instance.impl.RelatedLinkImpl;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

/**
 * Abstract Operater Widget for all the various types of Operators
 * @author Vikram Patil
 */
public abstract class AbstractOperatorLink extends Label {

	protected abstract List<IBuilderOperator> getOperatorsForType(FieldType type);
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

	private IFilterBuilder filterBuilder;
	private AbstractEditor editor;
	protected List<SymbolInfo> fieldList;

	public AbstractOperatorLink(IFilterBuilder filterBuilder, AbstractEditor view) {
		this.filterBuilder = filterBuilder;
		this.editor = view;

		this.initialize();
	}

	private void initialize() {
		this.filterBuilder.getFilter().addChangeListener(this.editor.getChangeHandler());
		this.filterBuilder.getFilter().addChangeListener(new IInstanceChangedListener() {

			@Override
			public void instanceChanged(IInstanceChangedEvent changeEvent) {
				if ((changeEvent.getChangeType() == IInstanceChangedEvent.CHANGED)
						&& (changeEvent.getFeatureId() == SingleFilter.OPERATOR_FEATURE_ID)) {
					String value = (String) changeEvent.getValue();
					AbstractOperatorLink.this.setContents(OperatorDisplayNameUtil.getOperator(value));
				}
			}
		});
		this.setLayoutAlign(VerticalAlignment.CENTER);
		this.setStyleName("ws-filterlink");
		this.setAutoHeight();
		this.setAutoWidth();
		String initialContents = this.getInitialContents();
		this.setContents(OperatorDisplayNameUtil.getOperator(initialContents));
		this.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				AbstractOperatorLink.this.handleLinkClicked();
			}
		});
	}

	protected void handleLinkClicked() {
		final Menu menu = new Menu();
		menu.setShowShadow(true);
		menu.setShadowDepth(10);
		menu.setPageLeft(this.getPageLeft());
		menu.setPageTop(this.getPageTop() + 15);

		if ((this.filterBuilder.getLastLink() == null)
				|| ArtifactUtil.isPrimitive(this.filterBuilder.getLastLink().getModel().getLinkType())) {
			this.fieldList = null;
			this.populateOperatorMenu(menu);

			if (menu.getItems().length > 0) {
				ArtifactUtil.setMenuScroll(menu.getItems().length, menu);
				menu.draw();
			}
		} else {
			this.getArtifactProperties(this.filterBuilder.getLastLink().getModel(), menu);
		}		
	}

	protected HashMap<String, String> getDisplayProperties() {
		return ((RuleTemplateInstanceEditor)editor).getDisplayProperties();
	}
	
	private void populateMenuFromDS(Menu menu, final DataSource asDataSource) {
		MenuItem operatorsMenu = new MenuItem(OperatorDisplayNameUtil.getOperator("Operators"));
		menu.addItem(operatorsMenu);

		Menu opSubMenu = new Menu();
		operatorsMenu.setSubmenu(opSubMenu);		
		this.populateOperatorMenu(opSubMenu);

		if ((this.fieldList != null) && (this.fieldList.size() > 0) && (this.filterBuilder.getLastLink() != null)) {
			MenuItem fieldsMenu = new MenuItem(globalMsgBundle.text_fields_of_an_entity(DisplayUtils.getLinkText(getDisplayProperties(), this.filterBuilder.getLastLink())));
			menu.addItem(fieldsMenu);

			Menu fieldsSubMenu = new Menu();
			fieldsMenu.setSubmenu(fieldsSubMenu);

			for (final SymbolInfo field : this.fieldList) {
				if (DisplayUtils.isHidden(this.filterBuilder.getLastLink(), field)) {
					continue;
				}
				final MenuItem newItem = new MenuItem(DisplayUtils.getDisplayText(getDisplayProperties(), field, this.filterBuilder.getLastLink(), false), ArtifactUtil.getEntityIcon(field.getType()));
				fieldsSubMenu.addItem(newItem);
				newItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						AbstractOperatorLink.this.filterBuilder.getLastLink().setAccessSuffix(true);
						RelatedLink modelLink = new RelatedLinkImpl();
						modelLink.setLinkText(field.getAlias());
						modelLink.setLinkType(field.getType());
						((SingleFilter) AbstractOperatorLink.this.filterBuilder.getFilter()).addRelatedLink(modelLink);
					}
				});
			}
		}
	}

	private void populateOperatorMenu(Menu menu) {
		String[] operators = this.filterBuilder.getOperators();
		for (final String op : operators) {
			final MenuItem newItem = new MenuItem(OperatorDisplayNameUtil.getOperator(op), null);
			newItem.setAttribute("op_id", op);
			menu.addItem(newItem);
			newItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					if (!newItem.getAttributeAsString("op_id").equals(AbstractOperatorLink.this.getContents())) {
						Filter filter = AbstractOperatorLink.this.filterBuilder.getFilter();
						if (filter instanceof MultiFilter) {
							((MultiFilter) filter).setMatchType(newItem.getAttributeAsString("op_id"));
						} else if (filter instanceof SingleFilter) {
							((SingleFilter) filter).setOperator(newItem.getAttributeAsString("op_id"));
						}
					}
				}
			});
		}
		menu.setPageLeft(this.getPageLeft());
		menu.setPageTop(this.getPageTop() + 15);
	}

	private String getInitialContents() {
		Filter filter = this.filterBuilder.getFilter();
		if (filter instanceof MultiFilter) {
			if (((MultiFilter) filter).getMatchType() != null) {
				return ((MultiFilter) filter).getMatchType();
			} else if (this.filterBuilder.getOperators().length > 0) {
				return this.filterBuilder.getOperators()[0];
			}
		} else if (filter instanceof SingleFilter) {
			String op = ((SingleFilter) filter).getOperator();
			if (op == null) {
				AbstractRelatedLink link = this.filterBuilder.getLastLink();
				FieldType type = getFieldType(link.getModel().getLinkType());
				List<IBuilderOperator> operators = this.getOperatorsForType(type);
				if ((operators != null) && (operators.size() > 0)) {
					String operator = operators.get(0).getValue();
					((SingleFilter) filter).setOperator(OperatorDisplayNameUtil.getOperator(operator));
					return operator;
				}
			}
			return op;
		}
		return null;
	}
	
	private FieldType getFieldType(String type){
		String clientType = ArtifactUtil.getClientType(type);
		for (FieldType field : FieldType.values()) {
			if (field.getValue().equals(clientType)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * Fetch properties specific to an entity
	 * 
	 * @param model
	 * @param menu
	 */
	private void getArtifactProperties(RelatedLink model, Menu menu) {
		if (this instanceof CommandOperatorLink) {
			this.fieldList = ArtifactUtil.getCommandSymbols(model.getLinkType(),
					model.getLinkText(),
					((RuleTemplateInstanceEditor) this.editor).getCommandSymbols());
			if (this.fieldList == null) {
				this.fieldList = ArtifactUtil.getSymbols(model.getLinkType(),
						model.getLinkText(),((RuleTemplateInstanceEditor) this.editor).getSymbols());
			}
		} else {
			this.fieldList = ArtifactUtil.getSymbols(model.getLinkType(),
					model.getLinkText(),((RuleTemplateInstanceEditor) this.editor).getSymbols());
		}

		this.populateMenuFromDS(menu, null);
		ArtifactUtil.setMenuScroll(menu.getItems().length, menu);
		menu.draw();
	}
}
