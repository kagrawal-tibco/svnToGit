package com.tibco.cep.webstudio.client.widgets;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DragRepositionStartEvent;
import com.smartgwt.client.widgets.events.DragRepositionStartHandler;
import com.smartgwt.client.widgets.events.DragRepositionStopEvent;
import com.smartgwt.client.widgets.events.DragRepositionStopHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.Clause;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedListener;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.impl.BuilderSubClauseImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.MultiFilterImpl;

/**
 * Widget for representing Multiple Filters within Rule Template Instance
 * 
 * @author Vikram Patil 
 * @author Ryan
 */
public class MultiDataSourceFilterBuilder extends HLayout implements IRuleTemplateFilter, IFilterBuilder {

	private static final String[] matchOperators = new String[] { "Match Any", "Match All", "Match None" };
	private DataSource dataSource;
	private OperatorLink matchTypeLink;
	private boolean allowRemove;

	private MultiFilter filter;
	private AbstractEditor editor;
	private AbstractBuilderSubClauseLayout parentClause;

	public MultiDataSourceFilterBuilder(MultiFilter filter, AbstractEditor view) {
		this.filter = filter;
		this.editor = view;
		this.initialize(filter);
	}

	public MultiDataSourceFilterBuilder(AbstractBuilderSubClauseLayout parentClause,
										MultiFilter filter,
										DataSource ds,
										AbstractEditor view,
										boolean allowRemove) {
		this.parentClause = parentClause;
		this.filter = filter;
		this.dataSource = ds;
		this.allowRemove = allowRemove;
		this.editor = view;
		this.initialize(filter);
	}

	@Override
	public String[] getOperators() {
		return matchOperators;
	}

	private Canvas createOperatorLink(String matchType) {
		this.matchTypeLink = new OperatorLink(this, this.editor);
		return this.matchTypeLink;
	}

	private void initialize(MultiFilter model) {
		this.setAutoHeight();
		this.setMargin(15);
		this.setKeepInParentRect(true);
		this.setCanDragReposition(true);
		this.setCanDrop(true);
		model.addChangeListener(this.editor.getChangeHandler());
		model.addChangeListener(new IInstanceChangedListener() {

			@Override
			public void instanceChanged(IInstanceChangedEvent changeEvent) {
				if (changeEvent.getFeatureId() == Clause.SUB_CLAUSE_FEATURE_ID) {
					MultiDataSourceFilterBuilder.this.addMember(MultiDataSourceFilterBuilder.this
							.createSubClause((BuilderSubClause) changeEvent.getValue()));
				}
			}
		});
		this.setLayoutLeftMargin(5);
		HStack opStack = new HStack();
		if (this.allowRemove) {
			Label l = new Label();
			l.setTitle("");
			l.setIcon("[SKINIMG]/actions/remove.png");
			l.setSize("16px", "16px");
			l.setLayoutAlign(VerticalAlignment.CENTER);
			this.addMember(l);
			l.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					MultiDataSourceFilterBuilder.this.removeSubClause();
				}
			});
			opStack.addMember(l);
		}
		opStack.setLayoutAlign(VerticalAlignment.CENTER);
		opStack.setHeight100();
		opStack.setAutoHeight();
		opStack.setDefaultWidth(20);
		final Canvas linkForm = this.createOperatorLink(model.getMatchType());
		linkForm.setWidth(80);
		opStack.addMember(linkForm);
		this.addMember(opStack);
		this.initializeSubClauses(model);
		this.addDragRepositionStartHandler(new DragRepositionStartHandler() {
			
			@Override
			public void onDragRepositionStart(DragRepositionStartEvent event) {
				RuleTemplateInstanceEditor editor = (RuleTemplateInstanceEditor)MultiDataSourceFilterBuilder.this.editor;
				if (editor.getSourceModel() == null) {
					MultiDataSourceFilterBuilder source = (MultiDataSourceFilterBuilder) event.getSource();
					editor.setSourceModel((BuilderSubClauseImpl) source.parentClause.model);
					editor.setDndFilter((MultiFilterImpl) source.filter);	
					editor.setCommandBuilder(false);
				}
			}
		});
		this.addDragRepositionStopHandler(new DragRepositionStopHandler() {
			
			@Override
			public void onDragRepositionStop(DragRepositionStopEvent event) {
				RuleTemplateInstanceEditor editor = (RuleTemplateInstanceEditor)MultiDataSourceFilterBuilder.this.editor;
				if (editor.getSourceModel() !=null && editor.getTargetModel() == null) {
					editor.setSourceModel(null);
					editor.setDndFilter(null);
				}
			}
		});
		
	}

	private void initializeSubClauses(MultiFilter model) {
		BuilderSubClause subClause = model.getSubClause();
		if (subClause == null) {
			subClause = new BuilderSubClauseImpl();
			model.setSubClause(subClause);
		} else {
			this.addMember(this.createSubClause(subClause));
		}
	}

	protected void removeSubClause() {
		if (this.parentClause != null) {
			this.parentClause.removeSubClause(this.filter);
		}
		this.animateHide(AnimationEffect.WIPE, new AnimationCallback() {

			@Override
			public void execute(boolean earlyFinish) {
				MultiDataSourceFilterBuilder.this.destroy();
				MultiDataSourceFilterBuilder.this.editor.makeDirty();
			}
		});
	}

	private Canvas createSubClause(BuilderSubClause model) {
		final FilterBuilderSubClause subClause = new FilterBuilderSubClause(model,
				this.dataSource,
				this.dataSource,
				this.editor);
		return subClause;
	}

	@Override
	public Filter getFilter() {
		return this.filter;
	}

	@Override
	public AbstractRelatedLink getLastLink() {
		return null;
	}

	@Override
	public AbstractRelatedLink getPreviousLink(RelatedLink link) {
		return null;
	}

	@Override
	public AbstractRelatedLink getPreviousValueLink(RelatedLink link) {
		return null;
	}
}
