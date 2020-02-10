package com.tibco.cep.webstudio.client.diff;

import java.util.Map;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.model.ruletemplate.BindingInfo;
import com.tibco.cep.webstudio.client.util.RuleTemplateHelper;
import com.tibco.cep.webstudio.client.view.BindingViewForm;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;
import com.tibco.cep.webstudio.model.rule.instance.impl.SingleFilterImpl;

/**
 * Helper class with utility methods for Sync Merge operation.
 * @author moshaikh
 */
public class SyncMergeHelper {
	
	/**
	 * Creates a Sync Merge Menu for the DIFF if required, else returns null also marks the modification entry as applied (to denote no user-action required)
	 * @param modificationEntry
	 * @param compClause
	 * @param filter
	 * @return
	 */
	public static Menu createSyncMergeContextMenu(final MergedDiffModificationEntry modificationEntry, final HLayout compClause, final Filter filter, final RuleTemplateInstanceEditor rtiEditor) {
		if (filter instanceof MultiFilter && hasLocalChangeInSubFilters((MultiFilter)filter, rtiEditor.getFilterModifications())) {
			
		}
		else if (!modificationEntry.isLocalChange()) {
			modificationEntry.setApplied(true);//User action not required - consider applied.
			return null;
		}
		
		if (modificationEntry.getLocalChangeType() == ModificationType.DELETED && modificationEntry.getServerChangeType() == ModificationType.DELETED) {
			//Context menu not needed in case no local change OR Deleted both locally and on server.
			
			modificationEntry.setApplied(true);//User action not required - consider applied.
			return null;
		}
		
		final RuleTemplateInstance ruleTemplateInstance = rtiEditor.getSelectedRuleTemplateInstance();
		
		Menu contextMenu = new Menu();
		switch(modificationEntry.getModificationType()) {
		case ADDED: {
			MenuItem descriptionMenuItem = new MenuItem(modificationEntry.getMessage());
			descriptionMenuItem.setEnabled(false);
			descriptionMenuItem.set_baseStyle("ws-context-menu-title");
			contextMenu.addItem(descriptionMenuItem);
			
			MenuItem keepMenuItem = new MenuItem("Keep");
			keepMenuItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					//Need not do any thing already present in model.
					syncMergeActioned(compClause, modificationEntry, rtiEditor);
				}
			});
			contextMenu.addItem(keepMenuItem);
			
			MenuItem removeMenuItem = new MenuItem("Delete");
			removeMenuItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					if(RuleTemplateHelper.removeFilter(ruleTemplateInstance.getConditionFilter(), filter)) {	//Actual remove from model
						compClause.setVisible(false);															//Hiding from the UI
						syncMergeActioned(compClause, modificationEntry, rtiEditor);
					}
				}
			});
			contextMenu.addItem(removeMenuItem);
		}
		break;
		case DELETED: {
			MenuItem descriptionMenuItem = new MenuItem(modificationEntry.getMessage());
			descriptionMenuItem.setEnabled(false);
			descriptionMenuItem.set_baseStyle("ws-context-menu-title");
			contextMenu.addItem(descriptionMenuItem);
			
			MenuItem keepMenuItem = new MenuItem("Keep");
			keepMenuItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					syncMergeActioned(compClause, modificationEntry, rtiEditor);
				}
			});
			contextMenu.addItem(keepMenuItem);
			
			MenuItem removeMenuItem = new MenuItem("Delete");
			removeMenuItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					if(RuleTemplateHelper.removeFilter(ruleTemplateInstance.getConditionFilter(), filter)) {	//Actual remove from model
						compClause.setVisible(false);										//Hiding from the UI
						syncMergeActioned(compClause, modificationEntry, rtiEditor);
					}
				}
			});
			contextMenu.addItem(removeMenuItem);
		}
		break;
		case MODIFIED: {
			MenuItem descriptionMenuItem = new MenuItem(modificationEntry.getMessage());
			descriptionMenuItem.setEnabled(false);
			descriptionMenuItem.set_baseStyle("ws-context-menu-title");
			contextMenu.addItem(descriptionMenuItem);
			
			if (modificationEntry.getLocalVersion() != null) {
				MenuItem useLocalCopy = new MenuItem("Use mine");
				useLocalCopy.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(MenuItemClickEvent event) {
						if (!(modificationEntry.getLocalVersionObj() instanceof SingleFilterImpl)) {
							return;
						}
						SingleFilterImpl localVersoin = (SingleFilterImpl)modificationEntry.getLocalVersionObj();
						SingleFilterImpl singleFilter = (SingleFilterImpl)filter;
						
						singleFilter.getLinks().clear();
						singleFilter.getLinks().addAll(localVersoin.getLinks());
						
						singleFilter.setOperator(localVersoin.getOperator());
						
						singleFilter.setFilterValue(localVersoin.getFilterValue());
						
						//Make sure that the filter is readOnly after applying  changes.
						rtiEditor.checkLeafElements(new Canvas[]{compClause});
						
						syncMergeActioned(compClause, modificationEntry, rtiEditor);
					}
				});
				contextMenu.addItem(useLocalCopy);
			}
			
			MenuItem acceptServerMenuItem = new MenuItem("Use theirs");
			acceptServerMenuItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					//do nothing, already showing 'theirs'.
					syncMergeActioned(compClause, modificationEntry, rtiEditor);
				}
			});
			contextMenu.addItem(acceptServerMenuItem);
		}
		break;
		case UNCHANGED: return null;
		}
		
		return contextMenu;
	}
	
	public static Menu createSyncMergeContextMenu(final MergedDiffModificationEntry modificationEntry,
			final Widget widget, final BindingInfo bindingInfo, final RuleTemplateInstanceEditor rtiEditor, final BindingViewForm bindingViewForm) {
		if (!modificationEntry.isLocalChange()) {
			modificationEntry.setApplied(true);//User action not required - consider applied.
			return null;
		}
		
		Menu contextMenu = new Menu();
		switch(modificationEntry.getModificationType()) {
		case MODIFIED: {
			MenuItem descriptionMenuItem = new MenuItem(modificationEntry.getMessage());
			descriptionMenuItem.setEnabled(false);
			descriptionMenuItem.set_baseStyle("ws-context-menu-title");
			contextMenu.addItem(descriptionMenuItem);
			
			if (modificationEntry.getLocalVersion() != null) {
				MenuItem useLocalCopy = new MenuItem("Use mine");
				useLocalCopy.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(MenuItemClickEvent event) {
						if (widget instanceof HasValue) {
							bindingViewForm.getBinding(bindingInfo.getId()).setValue(modificationEntry.getLocalVersion());
							((HasValue)widget).setValue(modificationEntry.getLocalVersion());
						}
						syncMergeActioned(widget, modificationEntry, rtiEditor);
					}
				});
				contextMenu.addItem(useLocalCopy);
			}
			
			MenuItem acceptServerMenuItem = new MenuItem("Use theirs");
			acceptServerMenuItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					//do nothing, already showing 'theirs'.
					syncMergeActioned(widget, modificationEntry, rtiEditor);
				}
			});
			contextMenu.addItem(acceptServerMenuItem);
		}
		break;
		default : return null;
		}
		
		return contextMenu;
	
	}
	
	/**
	 * Checks whether any of the filters down under this multifilter has localchange.
	 * @param multiFilter
	 * @return
	 */
	public static boolean hasLocalChangeInSubFilters(MultiFilter multiFilter, Map<String, ModificationEntry> modifications) {
		for (Filter filter : multiFilter.getSubClause().getFilters()) {
			ModificationEntry modificationEntry = modifications.get(filter.getFilterId());
			if (modificationEntry instanceof MergedDiffModificationEntry && ((MergedDiffModificationEntry) modificationEntry).isLocalChange()) {
				return true;
			}
			if (filter instanceof MultiFilter) {
				if(hasLocalChangeInSubFilters((MultiFilter)filter, modifications)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Performs post merge action activities such as fainting the diff coloring, removing the context menu etc.
	 * @param layout
	 * @param modificationEntry
	 * @param rtiEditor
	 */
	private static void syncMergeActioned(HLayout layout, ModificationEntry modificationEntry, RuleTemplateInstanceEditor rtiEditor) {
		layout.setContextMenu(null);
		layout.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				event.cancel();
			}
		});
		
		modificationEntry.setApplied(true);
		MergedDiffHelper.applyDiffCSSStyle(modificationEntry, layout, true);
		rtiEditor.makeDirty();
	}
	
	/**
	 * Performs post merge action activities such as fainting the diff coloring.
	 * @param widget
	 * @param modificationEntry
	 * @param rtiEditor
	 */
	public static void syncMergeActioned(Widget widget, ModificationEntry modificationEntry, RuleTemplateInstanceEditor rtiEditor) {
		modificationEntry.setApplied(true);
		MergedDiffHelper.applyDiffCSSStyle(modificationEntry, widget);
		rtiEditor.makeDirty();
	}
}
