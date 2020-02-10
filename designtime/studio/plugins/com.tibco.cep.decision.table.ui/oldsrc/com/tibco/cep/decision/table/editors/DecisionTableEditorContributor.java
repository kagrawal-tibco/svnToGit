package com.tibco.cep.decision.table.editors;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.FindReplaceAction;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * 
 * @author sasahoo
 * @author hitesh
 * 
 */
@SuppressWarnings({"unused"})
public class DecisionTableEditorContributor extends MultiPageEditorActionBarContributor {

	private IEditorPart activeEditorPart;

	private IWorkbenchWindow workBenchWindow;

	private RetargetAction findAction;

	private FindReplaceAction findHandler;
//	private Action saveAction;
//	private Action saveAsAction;
//	private Action saveAllAction;

	/**
	 * Creates a multi-page contributor.
	 */
	public DecisionTableEditorContributor() {
		super();
		this.workBenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}

	/**
	 * Returns the action registered with the given text editor.
	 * 
	 * @return IAction or null if editor is null.
	 */
	protected IAction getAction(ITextEditor editor, String actionID) {
		return (editor == null ? null : editor.getAction(actionID));
	}

	/*
	 * (non-JavaDoc) Method declared in
	 * AbstractMultiPageEditorActionBarContributor.
	 */

	public void setActivePage(IEditorPart part) {
//		IEditorPart editorPart = workbenchPage.getActiveEditor();
//		if (editorPart != null && editorPart instanceof DecisionTableEditor) {
//			((DecisionTableEditor) editorPart).setSaveAction(saveAction);
//		}
//		if (activeEditorPart == part)
//			return;
//
//		activeEditorPart = part;
//
//		IActionBars actionBars = getActionBars();
//		if (actionBars != null) {
//		}
	}

	public void contributeToMenu(IMenuManager manager) {
//		workbenchPage = getPage();
//		IContributionItem[] contributionItems = manager.getItems();
//		for (int i = 0; i < contributionItems.length; ++i) {
//			IContributionItem contributionItem = contributionItems[i];
//			if (contributionItem instanceof MenuManager) {
//				MenuManager menuManager = (MenuManager) contributionItem;
//				if (menuManager.getMenuText().equals(Messages.APPS_MENU_FILE)) {
//					IContributionItem[] contrItems = menuManager.getItems();
//					for (int j = 0; j < contrItems.length; ++j) {
//						IContributionItem contrItem = contrItems[j];
//						if (contrItem instanceof ActionContributionItem) {
//							ActionContributionItem actionItem = (ActionContributionItem) contrItem;
//							IAction action = actionItem.getAction();
//							if (action instanceof DTSaveAction) {
//								saveAction = (DTSaveAction) action;
//								// saveAction.setEnabled(true);
//							} else if (action instanceof DTSaveAsAction) {
//								saveAsAction = (DTSaveAsAction) action;
//								saveAsAction.setEnabled(true);
//							} else if (action instanceof SaveAllAction) {
//								saveAllAction = (SaveAllAction) action;
//								saveAllAction.setEnabled(true);
//							}
//						}
//
//					}
//				}
//			}
//		}
//		DecisionTableUtil.enableTableActions();
//		// DecisionTableUtil.enableEditMenu();
	}

//	@Override
//	public void dispose() {
//		if (saveAction != null) {
//			saveAction.setEnabled(false);
//		}
//		if (saveAsAction != null) {
//			saveAsAction.setEnabled(false);
//		}
//		if (saveAllAction != null) {
//			saveAllAction.setEnabled(false);
//		}
//		DecisionTableUtil.disableDeleteRenameAction();
//		DecisionTableUtil.disableTableActions();
//		// DecisionTableUtil.disableEditMenu();
//		super.dispose();
//	}

	@Override
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		if (activeEditorPart == part)
			return;

		activeEditorPart = part;

		IActionBars actionBars = getActionBars();
		if (actionBars != null) {
			actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), ((DecisionTableEditor) activeEditorPart)
					.getActionHandler(ActionFactory.COPY.getId()));

			actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), ((DecisionTableEditor) activeEditorPart)
					.getActionHandler(ActionFactory.PASTE.getId()));

			actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), ((DecisionTableEditor) activeEditorPart)
					.getActionHandler(ActionFactory.SELECT_ALL.getId()));

			actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(),
					((DecisionTableEditor) activeEditorPart)
							.getActionHandler(ActionFactory.CUT.getId()));
			
			actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
					((DecisionTableEditor) activeEditorPart)
							.getActionHandler(ActionFactory.UNDO.getId()));

			actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(),
					((DecisionTableEditor) activeEditorPart)
							.getActionHandler(ActionFactory.FIND.getId()));

			actionBars.updateActionBars();
		}

	}

	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
//		toolBarManager.add(new AnalyzeTableAction(Messages.Apps_Show_Analyzer_Action, workBenchWindow));
//		toolBarManager.add(new ValidateTableAction(Messages.ValidateTable_title, workBenchWindow));
//		toolBarManager.add(new Separator());
//		toolBarManager.add(new DeployDecisionTableAction(Messages.DeployTable_title, workBenchWindow));
//		toolBarManager.add(new UnDeployAllDecisionTableAction(Messages.UndeployTable_title, workBenchWindow));
//		toolBarManager.add(new GenerateClassAction(Messages.Apps_DT_Gen_Class_Action, workBenchWindow));
//		toolBarManager.add(new Separator());
//		toolBarManager.add(new CreateDTreeAction(com.tibco.cep.decision.tree.utils.Messages.DTR_Create_Action_Tooltip, workBenchWindow));
	}

	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);
//		findAction = new RetargetAction(ActionFactory.FIND.getId(), "My Find");
//		findHandler = new FindReplaceAction(ResourceBundle
//				.getBundle("com.tibco.cep.decision.table.utils.messages"),
//				"FindReplaceAction.", new Shell(),
//				new IFindReplaceTarget() {
//				
//					@Override
//					public void replaceSelection(String text) {
//						System.out.println("repl");
//					}
//				
//					@Override
//					public boolean isEditable() {
//						return true;
//					}
//				
//					@Override
//					public String getSelectionText() {
//						return "selText";
//					}
//				
//					@Override
//					public Point getSelection() {
//						return null;
//					}
//				
//					@Override
//					public int findAndSelect(int widgetOffset, String findString,
//							boolean searchForward, boolean caseSensitive, boolean wholeWord) {
//						return 0;
//					}
//				
//					@Override
//					public boolean canPerformFind() {
//						return true;
//					}
//				});
//		bars.setGlobalActionHandler(ActionFactory.FIND.getId(), findHandler);
//		
//		page.addPartListener(findAction);
//		
//		IWorkbenchPart activePart = page.getActivePart();
//		if (activePart != null) {
//			findAction.partActivated(activePart);
//		}
		
	}
	
}
