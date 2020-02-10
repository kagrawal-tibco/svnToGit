package com.tibco.cep.webstudio.client.widgets;

import java.util.List;

import com.google.gwt.i18n.client.LocaleInfo;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.tibco.cep.webstudio.client.diff.MergedDiffHelper;
import com.tibco.cep.webstudio.client.diff.MergedDiffModificationEntry;
import com.tibco.cep.webstudio.client.diff.ModificationEntry;
import com.tibco.cep.webstudio.client.diff.ModificationType;
import com.tibco.cep.webstudio.client.diff.SyncMergeHelper;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedListener;
import com.tibco.cep.webstudio.model.rule.instance.impl.BuilderSubClauseImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.MultiFilterImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.RelatedLinkImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.SingleFilterImpl;

/**
 * Widget for creating Sub Clauses for the Filter and Command
 * @author Vikram Patil
 * @author Ryan
 */
public abstract class AbstractBuilderSubClauseLayout extends HStack implements IRuleTemplateFilter {

	protected DataSource dataSource;
	protected DataSource templateDataSource;
	protected Layout vLayout;
	protected BuilderSubClause model;
	protected boolean allowNestedClause;
	protected AbstractEditor editor;
	protected List<SymbolInfo> symbols;
	private boolean addBefore;

	protected AbstractBuilderSubClauseLayout() {
		
	}
	
	/**
	 * Basic Constructor
	 * @param model
	 * @param templateDataSource
	 * @param dataSource
	 * @param allowNestedClause
	 * @param view
	 * @param symbols
	 */
	public AbstractBuilderSubClauseLayout(BuilderSubClause model,
											DataSource templateDataSource,
											DataSource dataSource,
											boolean allowNestedClause,
											AbstractEditor view,
											List<SymbolInfo> symbols) {
		this.model = model;
		this.templateDataSource = templateDataSource;
		this.dataSource = dataSource;
		this.allowNestedClause = allowNestedClause;
		this.editor = view;
		this.symbols = symbols;
		initialize();
	}

	/**
	 * Initialize the Widget
	 */
	protected void initialize() {
		if(!symbolsExist()) {
			//Add an empty filter, for cases which do not have symbols e.g. No parameter RF
			SingleFilterImpl filter = new SingleFilterImpl();
			this.model.addFilter(filter);
			return;
		}
		
		this.model.addChangeListener(editor.getChangeHandler());
		this.model.addChangeListener(new IInstanceChangedListener() {

			@Override
			public void instanceChanged(IInstanceChangedEvent changeEvent) {
				Filter filter = (Filter) changeEvent.getValue();
				if (changeEvent.getChangeType() == IInstanceChangedEvent.ADDED) {
					if (symbolsExist()) {
						addBefore = true;
						createAndAddFilterBuilder(filter);
					}
				}
			}
		});

		this.setLayoutAlign(VerticalAlignment.CENTER);
		this.setWidth("95%");
		this.setMargin(0);
		this.setMembersMargin(0);
		this.setLayoutMargin(0);

		final Layout borderMarker = new VLayout();
		borderMarker.setHeight(10);
		borderMarker.setWidth(10);
		borderMarker.setStyleName("ws-filterborder");
		this.addMember(borderMarker);

		final VLayout vLayout = new VLayout();
		
		vLayout.setAutoHeight();
		vLayout.setMargin(1);
		vLayout.setMembersMargin(1);
		vLayout.setLayoutMargin(1);
		vLayout.setLayoutLeftMargin(1);
		vLayout.setLayoutRightMargin(1);
		vLayout.setLayoutTopMargin(1);
		vLayout.setLayoutBottomMargin(1);
		vLayout.setPadding(0);
		vLayout.setCanAcceptDrop(true);
		vLayout.setDropLineThickness(1);
		if(LocaleInfo.getCurrentLocale().isRTL()){
			vLayout.setWidth(900);
		}
		else{
			vLayout.setAutoWidth();
		}

		this.vLayout = vLayout;
		createInitialLinkItems();
		this.setAutoHeight();
		this.addMember(vLayout);
		borderMarker.setHeight(vLayout.getVisibleHeight());
		vLayout.addResizedHandler(new ResizedHandler() {

			@Override
			public void onResized(ResizedEvent event) {
				int height = vLayout.getVisibleHeight();
				if (height != borderMarker.getHeight()) {
					borderMarker.setHeight(height);
				}
			}
		});
		vLayout.addDrawHandler(new DrawHandler() {

			@Override
			public void onDraw(DrawEvent event) {
				int height = vLayout.getVisibleHeight();
				if (height != borderMarker.getHeight()) {
					borderMarker.setHeight(height);
				}
			}
		});
		vLayout.addDropHandler(new DropHandler() {
	
			@Override
			public void onDrop(DropEvent event) {
				VLayout vLayout = (VLayout)event.getSource();
				RuleTemplateInstanceEditor editor = (RuleTemplateInstanceEditor) AbstractBuilderSubClauseLayout.this.editor;
				if (AbstractBuilderSubClauseLayout.this instanceof FilterBuilderSubClause && editor.isCommandBuilder()) {
					event.cancel();
				}
				else if (AbstractBuilderSubClauseLayout.this instanceof CommandBuilderSubClause && !editor.isCommandBuilder()) {
					event.cancel();
				}
				else {
					BuilderSubClauseImpl targetModel = (BuilderSubClauseImpl) AbstractBuilderSubClauseLayout.this.model; 
					editor.setTargetModel(targetModel);
					editor.setDropPosition(vLayout.getDropPosition());
					editor.updateModelsOnDnD();
				}
			}
		});
	}

	/**
	 * 
	 * @return
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Add Subclause data 
	 * @param clause
	 */
	void addSubClause(IRuleTemplateFilter clause) {
		int length = vLayout.getMembers().length;
		if (addBefore && length > 0) {
			vLayout.addMember((Canvas) clause, length - 1);
			addBefore = false;
		} else {
			vLayout.addMember((Canvas) clause);
		}
	}

	public void removeSubClause(Filter subClause) {
		this.model.removeFilter(subClause);
	}

	public DataSource getTemplateDataSource() {
		return templateDataSource;
	}

	protected void createInitialLinkItems() {
		List<Filter> filters = this.model.getFilters();
		if (filters.size() == 0) {
			SingleFilterImpl filter = new SingleFilterImpl();
		//	filter.setOperator(getInitialOperator());
			filter.addRelatedLink(new RelatedLinkImpl());
			this.model.addFilter(filter);
		} else {
			for (Filter filter : filters) {
				createAndAddFilterBuilder(filter);
			}
		}

		AbstractSingleFilterBuilder emptyClause = new SingleFilterBuilder(null, this, editor, true, allowNestedClause);
		emptyClause.setWidth100();
		emptyClause.setAutoHeight();
		vLayout.addMember(emptyClause);
	}

	public void addNewSubClause() {
		SingleFilterImpl filter = new SingleFilterImpl();
		//filter.setOperator(getInitialOperator());
		filter.addRelatedLink(new RelatedLinkImpl());
		this.model.addFilter(filter);
	}

	public abstract void addNewNestedClause();

	protected abstract AbstractSingleFilterBuilder createNewFilterBuilder(Filter filter);

	protected abstract String getInitialOperator();

	private void createAndAddFilterBuilder(Filter filter) {
		if (filter instanceof SingleFilterImpl) {
			AbstractSingleFilterBuilder compClause = createNewFilterBuilder(filter);
			compClause.setWidth100();
//			compClause.setAutoHeight();
			compClause.setHeight("35px");
			addSubClause(compClause);
			compClause.hide();
			compClause.animateShow(AnimationEffect.WIPE);
			ModificationEntry modificationEntry = ((RuleTemplateInstanceEditor) this.editor).getFilterModifications().get(filter.getFilterId());
			if (modificationEntry instanceof MergedDiffModificationEntry && ((RuleTemplateInstanceEditor) this.editor).isSyncMerge()) {
				MergedDiffModificationEntry mdme = (MergedDiffModificationEntry)modificationEntry;
				Menu contextMenu = SyncMergeHelper.createSyncMergeContextMenu(mdme, compClause, filter, (RuleTemplateInstanceEditor)this.editor);
				if (contextMenu != null) {
					compClause.setContextMenu(contextMenu);
				}
				else if (mdme.getServerChangeType() == ModificationType.DELETED && !mdme.isLocalChange()) {//Deleted on server and no local change
					((RuleTemplateInstanceEditor) this.editor).addFilterToBeRemovedBeforeSave(filter);
				}
			}
			
			MergedDiffHelper.applyDiffCSSStyle(modificationEntry, compClause, true);
		} else if (filter instanceof MultiFilterImpl) {
			MultiDataSourceFilterBuilder fb = new MultiDataSourceFilterBuilder(this,
					(MultiFilterImpl) filter,
					dataSource,
					editor,
					true);
			fb.setWidth100();
//			fb.setAutoHeight();
			fb.setHeight("106px");
			addSubClause(fb);
			fb.hide();
			fb.animateShow(AnimationEffect.WIPE);
			ModificationEntry modificationEntry = ((RuleTemplateInstanceEditor) this.editor).getFilterModifications().get(filter.getFilterId());
			if (modificationEntry instanceof MergedDiffModificationEntry) {
				MergedDiffModificationEntry mdme = (MergedDiffModificationEntry)modificationEntry;
				Menu contextMenu = SyncMergeHelper.createSyncMergeContextMenu(mdme, fb, filter, (RuleTemplateInstanceEditor) this.editor);
				if (contextMenu != null) {
					fb.setContextMenu(contextMenu);
				}
				else if (mdme.getServerChangeType() == ModificationType.DELETED && !mdme.isLocalChange()) {//Deleted on server and no local change
					((RuleTemplateInstanceEditor) this.editor).addFilterToBeRemovedBeforeSave(filter);
				}
			}
			
			MergedDiffHelper.applyDiffCSSStyle(modificationEntry, fb, false);
		}
	}
	
	/**
	 * Check Symbols exist for this subclause.
	 *  
	 * @return
	 */
	private boolean symbolsExist() {
		List<SymbolInfo> fields = (this.symbols == null) ? ((RuleTemplateInstanceEditor) this.editor)
				.getSymbols() : this.symbols;
		if (fields.size() == 0) {
			return false;
		}
		
		return true;
	}
}