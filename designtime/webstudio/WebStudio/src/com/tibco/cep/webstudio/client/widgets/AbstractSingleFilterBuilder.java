package com.tibco.cep.webstudio.client.widgets;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DragRepositionStartEvent;
import com.smartgwt.client.widgets.events.DragRepositionStartHandler;
import com.smartgwt.client.widgets.events.DragRepositionStopEvent;
import com.smartgwt.client.widgets.events.DragRepositionStopHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.model.ruletemplate.DomainInfo;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.RuleTemplateHelper;
import com.tibco.cep.webstudio.model.rule.instance.EmptyFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.FilterValue;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedListener;
import com.tibco.cep.webstudio.model.rule.instance.RangeFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.SimpleFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.SingleFilter;
import com.tibco.cep.webstudio.model.rule.instance.impl.BuilderSubClauseImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.RelatedLinkImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.SingleFilterImpl;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

/**
 * 
 * @author Ryan
 * @author Vikram Patil
 */
public abstract class AbstractSingleFilterBuilder extends HLayout implements IRuleTemplateFilter, IFilterBuilder {

	protected boolean emptyClause;
	protected boolean allowNestedClause;

	protected AbstractBuilderSubClauseLayout parentClause;
	protected DataSourcesLink firstLink;
	protected Canvas valueForm;
	protected HashMap<RelatedLink, AbstractRelatedLink> linksToWidgetsMap = new HashMap<RelatedLink, AbstractRelatedLink>();
	protected HashMap<RelatedLink, AbstractRelatedLink> valuelinksToWidgetsMap = new HashMap<RelatedLink, AbstractRelatedLink>();

	protected Filter filter;
	private AbstractOperatorLink operatorLink;
	protected AbstractEditor editor;
	protected List<SymbolInfo> symbols;

	public Filter getFilter() {
		return this.filter;
	}

	public AbstractSingleFilterBuilder() {
		super();
	}

	public AbstractSingleFilterBuilder(Filter filter,
										AbstractBuilderSubClauseLayout builderSubClause,
										AbstractEditor view,
										boolean emptyClause,
										boolean allowNestedClause,
										List<SymbolInfo> symbols) {
		this.filter = filter;
		this.parentClause = builderSubClause;
		this.emptyClause = emptyClause;
		this.allowNestedClause = allowNestedClause;
		this.editor = view;
		this.symbols = symbols;
		this.initialize();
		this.setCanDragReposition(true);
		this.setCanDrop(true);
		this.setDragTracker();
		this.setKeepInParentRect(true);
		this.addDragRepositionStartHandler(new DragRepositionStartHandler() {
			
			@Override
			public void onDragRepositionStart(DragRepositionStartEvent event) {
				RuleTemplateInstanceEditor editor = (RuleTemplateInstanceEditor) AbstractSingleFilterBuilder.this.editor;
				if (editor.getSourceModel() == null) {
					Object source = event.getSource();
					if (source instanceof SingleFilterBuilder) {
						SingleFilterBuilder singleFilterBuilderSource = (SingleFilterBuilder) source;
						if (singleFilterBuilderSource.emptyClause) {
							event.cancel();
						} else {
							editor.setSourceModel((BuilderSubClauseImpl) singleFilterBuilderSource.parentClause.model);
							editor.setDndFilter((SingleFilterImpl) singleFilterBuilderSource.filter);
							editor.setCommandBuilder(false);
						}
					} else if (source instanceof CommandFilterBuilder) {
						CommandFilterBuilder commandFilterBuilderSource = (CommandFilterBuilder) source;
						editor.setSourceModel((BuilderSubClauseImpl) commandFilterBuilderSource.parentClause.model);
						editor.setDndFilter((SingleFilterImpl) commandFilterBuilderSource.filter);
						editor.setCommandBuilder(true);
					} 
				}
			}
		});
		this.addDragRepositionStopHandler(new DragRepositionStopHandler() {
			
			@Override
			public void onDragRepositionStop(DragRepositionStopEvent event) {
				RuleTemplateInstanceEditor editor = (RuleTemplateInstanceEditor) AbstractSingleFilterBuilder.this.editor;
				if (editor.getSourceModel() != null && editor.getTargetModel() == null) {
					editor.setSourceModel(null);
					editor.setDndFilter(null);
				}
			}
		});
	}

	protected abstract void updateValueForm();

	protected abstract List<IBuilderOperator> getOperatorsForType(FieldType type);

	@Override
	public String[] getOperators() {
		FieldType type = this.getLinkType();
		List<IBuilderOperator> operators = this.getOperatorsForType(type);
		String[] ops = new String[operators.size()];
		for (int i = 0; i < ops.length; i++) {
			ops[i] = operators.get(i).getValue();
		}
		return ops;
	}

	protected FieldType getLinkType() {
		FieldType[] fieldTypes = FieldType.values();
		for (FieldType field : fieldTypes) {
			if (field.getValue().equalsIgnoreCase(ArtifactUtil.getClientType(this.getLastLink().getModel().getLinkType()))) {
				return field;
			}
		}
		return null;
	}

	protected void initialize() {
		if (this.filter != null) {
			// can be null for '+' builder
			this.filter.addChangeListener(this.editor.getChangeHandler());
			this.filter.addChangeListener(new IInstanceChangedListener() {

				@Override
				public void instanceChanged(IInstanceChangedEvent changeEvent) {
					if ((changeEvent.getChangeType() == IInstanceChangedEvent.ADDED)
							&& (changeEvent.getFeatureId() == SingleFilter.LINKS_FEATURE_ID)) {
						RelatedLink modelLink = (RelatedLink) changeEvent.getValue();
						AbstractSingleFilterBuilder.this.createAndAddRelatedLink(modelLink);
						AbstractSingleFilterBuilder.this.updateValueFormFromFilterValue();
						AbstractSingleFilterBuilder.this.changeOperatorLink();
					} else if ((changeEvent.getChangeType() == IInstanceChangedEvent.CHANGED)
							&& (changeEvent.getFeatureId() == SingleFilter.OPERATOR_FEATURE_ID)) {
						AbstractSingleFilterBuilder.this.updateValueForm();
					} else if ((changeEvent.getChangeType() == IInstanceChangedEvent.CHANGED)
							&& (changeEvent.getFeatureId() == SingleFilter.FILTER_VALUE_FEATURE_ID)) {
						AbstractSingleFilterBuilder.this.updateValueFormFromFilterValue();
					} else if ((changeEvent.getChangeType() == IInstanceChangedEvent.REMOVED)
							&& (changeEvent.getFeatureId() == SingleFilter.LINKS_FEATURE_ID)) {
						RelatedLink modelLink = (RelatedLink) changeEvent.getValue();
						AbstractRelatedLink link = AbstractSingleFilterBuilder.this.linksToWidgetsMap.get(modelLink);
						if (link != null) {
							AbstractSingleFilterBuilder.this.removeMember(link);
							link.destroy();
							AbstractSingleFilterBuilder.this.linksToWidgetsMap.remove(modelLink);
						}
					}
				}
			});
		}
		this.setAutoHeight();
		this.setMargin(5);
		this.setMembersMargin(5);
		Label l = new Label();
		l.setTitle("");
		if (!this.emptyClause) {
			l.setIcon("[SKINIMG]/actions/remove.png");
			l.setSize("16px", "16px");
			l.setLayoutAlign(VerticalAlignment.CENTER);
			this.addMember(l);
			l.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					AbstractSingleFilterBuilder.this.removeSubClause();
				}
			});
		} else {
			l.setIcon("[SKINIMG]/actions/add.png");
			l.setSize("16px", "16px");
			l.setLayoutAlign(VerticalAlignment.CENTER);
			this.addMember(l);
			l.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					AbstractSingleFilterBuilder.this.addNewSubClause();
				}
			});
			if (this.allowNestedClause) {
				Button nestedClauseButton = new Button("+()");
				nestedClauseButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						AbstractSingleFilterBuilder.this.addNewNestedClause();
					}
				});
				nestedClauseButton.setSize("25px", "25px");
				this.addMember(nestedClauseButton);
			}
		}

		if (!this.emptyClause) {
			if (this.filter instanceof SingleFilter) {
				SingleFilter singleFilter = (SingleFilter) this.filter;
				List<SymbolInfo> fields = (this.symbols == null) ? ((RuleTemplateInstanceEditor) this.editor)
						.getSymbols() : this.symbols;
				if (fields.size() == 0) {
					return;
				}

				List<RelatedLink> links = singleFilter.getLinks();
				RelatedLink modelLink = null;
				if ((links != null) && (links.size() > 0)) {
					modelLink = links.get(0);
					if (modelLink.getLinkText() == null) {
						modelLink.setLinkText(fields.get(0).getAlias());
						modelLink.setLinkType(fields.get(0).getType());
					}
				} else {
					modelLink = new RelatedLinkImpl();
					modelLink.setLinkText(fields.get(0).getAlias());
					modelLink.setLinkType(fields.get(0).getType());
				}
				this.firstLink = new DataSourcesLink(modelLink, this, fields, false, this.editor);
				this.addRelatedLink(modelLink, null, this.firstLink);
				for (int i = 1; i < links.size(); i++) {
					RelatedLink relatedLink = links.get(i);
					this.createAndAddRelatedLink(relatedLink);
				}
				this.operatorLink = this.createInitialOperatorLink(this.firstLink);
				this.addMember(this.operatorLink);
				
				FilterValue filterValue = singleFilter.getFilterValue();
				if (filterValue == null) {
					
					this.updateValueForm();
					singleFilter.setOperator(OperatorDisplayNameUtil.getOperator(singleFilter.getOperator()));
				} else {
					if (filterValue instanceof RelatedFilterValue) {
						final RelatedFilterValue relatedVal = (RelatedFilterValue) filterValue;
						
						relatedVal.addChangeListener(this.editor.getChangeHandler());
						relatedVal.addChangeListener(new IInstanceChangedListener() {

							@Override
							public void instanceChanged(IInstanceChangedEvent changeEvent) {
								AbstractSingleFilterBuilder.this.processRelatedFilterValueChangedEvent(relatedVal,
										changeEvent);
							}
						});
					}
					this.updateValueFormFromFilterValue();
				}
			}

		}
	}

	protected abstract AbstractOperatorLink createInitialOperatorLink(DataSourcesLink initOpLink);

	protected void addNewNestedClause() {
		this.parentClause.addNewNestedClause();
	}

	protected void addNewSubClause() {
		this.parentClause.addNewSubClause();
	}

	protected void removeSubClause() {
		this.parentClause.removeSubClause(this.filter);
		this.animateHide(AnimationEffect.WIPE, new AnimationCallback() {

			@Override
			public void execute(boolean earlyFinish) {
				AbstractSingleFilterBuilder.this.destroy();
			}
		});
	}

	public void addRelatedLink(RelatedLink modelLink, AbstractRelatedLink previousLink, AbstractRelatedLink newLink) {
		this.linksToWidgetsMap.put(modelLink, newLink);
		if (previousLink == null) {
			this.addMember(newLink);
		} else {
			this.addMember(newLink, this.getMemberNumber(previousLink) + 1);
		}
	}

	public void addValueRelatedLink(RelatedLink modelLink, AbstractRelatedLink previousLink, AbstractRelatedLink newLink) {
		this.valuelinksToWidgetsMap.put(modelLink, newLink);
		modelLink.addChangeListener(this.editor.getChangeHandler());
		// if (previousLink == null) {
		// this.addMember(newLink);
		// } else {
		// this.addMember(newLink, getMemberNumber(previousLink)+1);
		// }
	}

	protected ValueForm createDynamicForm() {
		ValueForm operatorForm = new ValueForm();
		operatorForm.setNumCols(5);
		operatorForm.setLayoutAlign(VerticalAlignment.CENTER);
		return operatorForm;
	}

	protected Canvas createCompFieldForm() {
		HLayout layout = new HLayout();
		layout.setAutoWidth();
		layout.setMembersMargin(5);
		layout.setAutoHeight();
		return layout;
	}

	public AbstractRelatedLink getFirstLink() {
		if (this.filter instanceof SingleFilter) {
			List<RelatedLink> links = ((SingleFilter) this.filter).getLinks();
			if ((links == null) || (links.size() == 0)) {
				return null;
			}
			RelatedLink link = links.get(0);
			return this.linksToWidgetsMap.get(link);
		}
		return null;
	}

	@Override
	public AbstractRelatedLink getPreviousLink(RelatedLink link) {
		if (this.filter instanceof SingleFilter) {
			List<RelatedLink> links = ((SingleFilter) this.filter).getLinks();
			if ((links == null) || (links.size() == 0)) {
				return null;
			}
			int idx = links.indexOf(link);
			RelatedLink prevLink = links.get(idx - 1);
			return this.linksToWidgetsMap.get(prevLink);
		}
		return null;
	}

	@Override
	public AbstractRelatedLink getPreviousValueLink(RelatedLink link) {
		if ((this.filter instanceof SingleFilter)
				&& (((SingleFilter) this.filter).getFilterValue() instanceof RelatedFilterValue)) {
			RelatedFilterValue value = (RelatedFilterValue) ((SingleFilter) this.filter).getFilterValue();
			List<RelatedLink> links = value.getLinks();
			if ((links == null) || (links.size() == 0)) {
				return null;
			}
			int idx = links.indexOf(link);
			if (idx <= 0) {
				return null;
			}
			RelatedLink prevLink = links.get(idx - 1);
			if (prevLink == null) {
				return null;
			}
			return this.valuelinksToWidgetsMap.get(prevLink);
		}
		return null;
	}

	@Override
	public AbstractRelatedLink getLastLink() {
		if (this.filter instanceof SingleFilter) {
			List<RelatedLink> links = ((SingleFilter) this.filter).getLinks();
			if ((links == null) || (links.size() == 0)) {
				return null;
			}
			RelatedLink prevLink = links.get(links.size() - 1);
			return this.linksToWidgetsMap.get(prevLink);
		}
		return null;
	}

	protected abstract AbstractRelatedLink createNewRelatedLink(RelatedLink modelLink, AbstractRelatedLink prevLink);

	protected void updateValueFormFromFilterValue() {
		if (this.valueForm != null) {
			if (this.contains(this.valueForm)) {
				this.removeMember(this.valueForm);
			}
			this.valueForm = null;
		}
		final FilterValue filterValue = ((SingleFilter) this.filter).getFilterValue();
		if (filterValue == null) {
			return;
		}
		String type = RuleTemplateHelper.getLinkType(((SingleFilter) this.filter).getLinks());
		if (filterValue instanceof RangeFilterValue) {
			this.valueForm = this.createDynamicForm();
			IntegerItem item1 = new IntegerItem();
			item1.setTitle("");
			item1.setValue(((RangeFilterValue) filterValue).getMinValue());
			item1.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					((RangeFilterValue) filterValue).setMinValue((String) event.getValue());
				}
			});

			IntegerItem item2 = new IntegerItem();
			item2.setName("and");
			item2.setValue(((RangeFilterValue) filterValue).getMaxValue());
			item2.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					((RangeFilterValue) filterValue).setMaxValue((String) event.getValue());
				}
			});
			((DynamicForm) this.valueForm).setItems(item1, item2);
		} else if (filterValue instanceof SimpleFilterValue) {
			this.valueForm = this.createDynamicForm();
			
			List<SymbolInfo> associatedSymbols = (this.symbols != null) ? this.symbols : ((RuleTemplateInstanceEditor) this.editor).getSymbols();
			
			// check if the symbol associated to this link has a domain model
			DomainInfo domainInfo = ArtifactUtil.getAssociatedDomainInfo(((SingleFilter) this.filter).getLinks(), associatedSymbols);
			FormItem formItem = createFormItemByType(type, filterValue, domainInfo);
			formItem.setTitle("");
			formItem.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					((SimpleFilterValue) filterValue).setValue((String) event.getValue());
				}
			});
			((DynamicForm) this.valueForm).setItems(formItem);
		} else if (filterValue instanceof RelatedFilterValue) {
			this.valueForm = this.createCompFieldForm();

			List<SymbolInfo> fields = ((RuleTemplateInstanceEditor) this.editor).getSymbols();
			List<RelatedLink> links = ((RelatedFilterValue) filterValue).getLinks();
			RelatedLink modelLink = null;
			if ((links != null) && (links.size() > 0)) {
				modelLink = links.get(0);
				if (modelLink.getLinkText() == null) {
					modelLink.setLinkText(fields.get(0).getAlias());
					modelLink.setLinkType(fields.get(0).getType());
				}
				DataSourcesLink initOpLink = new DataSourcesLink(modelLink, this, fields, true, this.editor);
				this.addValueRelatedLink(modelLink, null, initOpLink);
				((Layout) this.valueForm).addMember(initOpLink);
				for (int i = 1; i < links.size(); i++) {
					RelatedLink relatedLink = links.get(i);
					this.createAndAddToFieldRelatedLink((RelatedFilterValue) filterValue, relatedLink);
				}
			} else {
				modelLink = new RelatedLinkImpl();
				if (fields.size() > 0) {
					modelLink.setLinkText(fields.get(0).getAlias());
					modelLink.setLinkType(fields.get(0).getType());
				}
				((RelatedFilterValue) filterValue).addLink(modelLink);
			}
		} else if (filterValue instanceof EmptyFilterValue) {
			// no value form for empty filter value
		}

		if (filterValue != null) {
			filterValue.addChangeListener(this.editor.getChangeHandler());
		}
		
		if (this.valueForm != null) {
			this.valueForm.show();
			this.addMember(this.valueForm);
		}

	}
	
	/**
	 * Method to get the first operator of the filter link type.
	 * @return
	 */
	private String getOperator() {
		String type = RuleTemplateHelper.getLinkType(((SingleFilter) this.filter).getLinks());
		FieldType[] fieldTypes = FieldType.values();
		String operator = null;
		String clientType = ArtifactUtil.getClientType(type);
		if (type.indexOf("/") != -1) {
			clientType = "custom";
		}
		for (FieldType field : fieldTypes) {
			if (field.getValue().equals(clientType)) {
				operator = this.getOperatorsForType(field).get(0).getValue();
				break;
			}
		}
		
		return OperatorDisplayNameUtil.getOperator(operator);
	}
	
	/**
	 * Method to change the operator of the filter.
	 */
	protected void changeOperatorLink() {
		String operator = getOperator();
		((SingleFilter) filter).setOperator(operator);
	}

	protected void processRelatedFilterValueChangedEvent(final RelatedFilterValue relatedVal,
															IInstanceChangedEvent changeEvent) {
		if ((changeEvent.getChangeType() == IInstanceChangedEvent.ADDED)
				&& (changeEvent.getFeatureId() == SingleFilter.LINKS_FEATURE_ID)) {
			RelatedLink modelLink = (RelatedLink) changeEvent.getValue();
			this.createAndAddToFieldRelatedLink(relatedVal, modelLink);
		} else if ((changeEvent.getChangeType() == IInstanceChangedEvent.REMOVED)
				&& (changeEvent.getFeatureId() == SingleFilter.LINKS_FEATURE_ID)) {
			RelatedLink modelLink = (RelatedLink) changeEvent.getValue();
			AbstractRelatedLink link = this.valuelinksToWidgetsMap.get(modelLink);
			if (link != null) {
				if (this.valueForm.contains(link)) {
					this.valueForm.removeChild(link);
				}
				link.destroy();
				this.valuelinksToWidgetsMap.remove(modelLink);
			}
		}
	}

	private void createAndAddToFieldRelatedLink(final RelatedFilterValue relatedVal, RelatedLink modelLink) {
		AbstractRelatedLink prevLink = this.getPreviousValueLink(modelLink);
		if (prevLink == null) {
			List<SymbolInfo> fields = ((RuleTemplateInstanceEditor) this.editor).getSymbols();
			DataSourcesLink initOpLink = new DataSourcesLink(modelLink, this, fields, true, this.editor);
			this.addValueRelatedLink(modelLink, null, initOpLink);
			((Layout) this.valueForm).addMember(initOpLink);
			initOpLink.updateFromDataSourceField();
		} else {
			ToFieldRelatedLink link = new ToFieldRelatedLink(modelLink, relatedVal, prevLink, this.editor);
			this.addValueRelatedLink(modelLink, null, link);
			((Layout) this.valueForm).addMember(link);
			prevLink.setNextLink(link);
			link.updateFromDataSourceField();
		}
	}

	private void createAndAddRelatedLink(RelatedLink modelLink) {
		modelLink.addChangeListener(this.editor.getChangeHandler());
		AbstractRelatedLink prevLink = this.getPreviousLink(modelLink);
		AbstractRelatedLink link = this.createNewRelatedLink(modelLink, prevLink);
		this.addRelatedLink(modelLink, prevLink, link);
		prevLink.setNextLink(link);
		link.updateFromDataSourceField();
	}

	/**
	 * Set the operator link value
	 * @param operator
	 */
	public void setOperatorLink(AbstractOperatorLink operator) {
		operatorLink = operator;
	}
	
	/**
	 * Fetch the operator link
	 * @return
	 */
	public AbstractOperatorLink getOperatorLink() {
		return operatorLink;
	}
	
	/**
	 * This method creates a FormItem depending on type of field.<br/>
	 * ->A DateTimePicker for a DateTime field.<br/>
	 * A Selection list for Domain types.<br/>
	 * Textbox for all other types.
	 * 
	 * @param type
	 * @param filterValue
	 * @param domainInfo
	 * @return
	 */
	private static FormItem createFormItemByType(String type, final FilterValue filterValue, DomainInfo domainInfo) {
		FormItem formItem = null;
		if ("DateTime".equalsIgnoreCase(type)) {
			formItem = getDateTimePickerAsFormItem(filterValue);
		}
		else {
			if (domainInfo == null) {
				formItem = new TextItem();
				formItem.setValue(((SimpleFilterValue) filterValue).getValue());
			} else {
				formItem = new ComboBoxItem();
				List<String> domainValues = new ArrayList<String>();
				for(String domainValue : domainInfo.getValues().values()) {
					domainValues.add(domainValue);
				}
				formItem.setValueMap(domainValues.toArray(new String[domainValues.size()]));
			}
		}
		if (formItem != null) {
			formItem.setValue(((SimpleFilterValue) filterValue).getValue());
		}
		return formItem;
	}
	
	/**
	 * Creates a CanvasItem with a DateTimePicker and configures all Handlers.
	 * @param filterValue
	 * @return
	 */
	private static FormItem getDateTimePickerAsFormItem(final FilterValue filterValue) {
		final DateTimePicker dtp = new DateTimePicker(null);
		final CanvasItem canvasItem = new CanvasItem() {
			@Override
			public void setValue(String value) {
				if (value != null) {
					try {
						Date date = DateTimeFormat.getFormat(RuleTemplateHelper.DATE_TIME_FORMAT).parse(value);
						dtp.setValue(date);
					} catch(Exception e) {
						dtp.setValue(null);
					}
				}
			}
		};
		
		canvasItem.setShouldSaveValue(true);
		
		dtp.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				if (event.getValue() != null) {
					String formattedDate = DateTimeFormat.getFormat(RuleTemplateHelper.DATE_TIME_FORMAT).format(event.getValue());
					canvasItem.setValue(formattedDate);
					((SimpleFilterValue) filterValue).setValue(formattedDate);
				}
			}
		});
		
		HLayout hLayout = new HLayout();
		hLayout.addMember(dtp);
		hLayout.setLayoutData(dtp);//Required to disable DTP while showing RTI in readonly mode (DIFF). [Ref: RuleTemplateInstanceEditorFactory.makeElementReadOnly(Object)]
		hLayout.setPixelSize(220, 30);
		
		canvasItem.setCellHeight(30);
		canvasItem.setCanvas(hLayout);
		
		return canvasItem;
	}
}