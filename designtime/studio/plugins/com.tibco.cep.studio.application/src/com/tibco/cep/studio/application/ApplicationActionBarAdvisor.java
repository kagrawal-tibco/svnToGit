package com.tibco.cep.studio.application;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.ActionSetRegistry;
import org.eclipse.ui.internal.registry.IActionSetDescriptor;

import com.tibco.cep.studio.application.actions.PreferenceAction;
import com.tibco.cep.studio.application.utils.Messages;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
@SuppressWarnings("restriction")
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	@Override
	public IAction getAction(String id) {
		return super.getAction(id);
	}

	// Actions - important to allocate these only in makeActions, and then use
	// them in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.
	private IWorkbenchWindow window;
	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;
	private Action helpAction;
	private IWorkbenchAction newWindowAction;
	
	private IWorkbenchAction copyAction;
	private IWorkbenchAction cutAction;
	private IWorkbenchAction selectAllAction;
	private IWorkbenchAction findAction;
	private IWorkbenchAction closeAction;
	private IWorkbenchAction closeAllAction;
	private IWorkbenchAction pasteAction;

	private IWorkbenchAction maxEditorAction;
	private IWorkbenchAction minEditorAction;
	private IWorkbenchAction activateEditorAction;
	private IWorkbenchAction nextEditorAction;
	private IWorkbenchAction previousEditorAction;
	private IWorkbenchAction switchEditorActon;
	private IWorkbenchAction saveAction;
	private IWorkbenchAction saveAllAction;
	private IWorkbenchAction saveAsAction;

	private PreferenceAction preferenceAction;
	// temorary action to build EAR
	//private Action buildEAR;

	public static final String NAVIGATOR_ID = "com.tibco.cep.studio.projectexplorer.view";
	
	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(final IWorkbenchWindow window) {
		ActionSetRegistry reg = WorkbenchPlugin.getDefault()
				.getActionSetRegistry();
		IActionSetDescriptor[] actionSets = reg.getActionSets();
		String actionSetAnnotaionId = "org.eclipse.ui.edit.text.actionSet.annotationNavigation";
		String actionSetNavigationId = "org.eclipse.ui.edit.text.actionSet.navigation";

		for (int i = 0; i < actionSets.length; i++) {
			if ((actionSets[i].getId().equals(actionSetAnnotaionId))
					|| (actionSets[i].getId().equals(actionSetNavigationId))) {
				IExtension ext = actionSets[i].getConfigurationElement()
						.getDeclaringExtension();
				reg.removeExtension(ext, new Object[] { actionSets[i] });
			}
		}

		// action to build EAR
		//buildEAR = new BuildEARAction();
		//register(buildEAR);
		// Creates the actions and registers them.
		// Registering is needed to ensure that key bindings work.
		// The corresponding commands keybindings are defined in the plugin.xml
		// file.
		// Registering also provides automatic disposal of the actions when the
		// window is closed.
		this.window = window;
		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);

		aboutAction = ActionFactory.ABOUT.create(window);
		register(aboutAction);

//		helpAction = new ShowHelpAction();
//		register(helpAction);

		newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
		newWindowAction.setImageDescriptor(StudioApplicationPlugin
				.getImageDescriptor("/icons/new_report_project.gif"));
		newWindowAction.setText(Messages.Apps_New_Window_Action);
		register(newWindowAction);

//		showConsoleViewAction = new ShowConsoleViewAction(window,
//				Messages.Apps_Show_Console_Action,
//				"org.eclipse.ui.console.ConsoleView");
//		register(showConsoleViewAction);

//		showProblemsViewAction = new ShowProblemsViewAction(window,
//				Messages.Apps_Show_Problems_Action,
//				"org.eclipse.ui.views.ProblemView");
//		register(showProblemsViewAction);

//		showPaletteViewAction = new ShowPaletteViewAction(window,
//				Messages.Apps_Show_Palette_Action, PaletteView.ID);
//		register(showPaletteViewAction);
//
//		showOverViewAction = new ShowOverViewAction(window,
//				Messages.Apps_Show_Overview_Action, DrawingOverview.ID);
//		register(showOverViewAction);

//		showProjectExplorerAction = new ShowProjectExplorerAction(window,
//				Messages.Apps_Show_Project_Explorer_Action, NAVIGATOR_ID);
//		register(showProjectExplorerAction);
		
		if (window == null)
			System.out.println("null window");

//		if (ActionFactory.INTRO != null) {
//			introAction = ActionFactory.INTRO.create(window);
//			introAction.setImageDescriptor(Activator.getImageDescriptor("/icons/welcome16.gif"));
//			register(introAction);
//		}

		closeAction = ActionFactory.CLOSE.create(window);
		register(closeAction);

		closeAllAction = ActionFactory.CLOSE_ALL.create(window);
		register(closeAllAction);

		preferenceAction = new PreferenceAction(
				Messages.Apps_Preferences_Action, window);
		register(preferenceAction);

//		showKeyAssistAction = new ShowKeyAssistAction(Messages.Apps_Key_Assist);
//		register(showKeyAssistAction);

		copyAction = ActionFactory.COPY.create(window);
		register(copyAction);

		pasteAction = ActionFactory.PASTE.create(window);
		register(pasteAction);

		cutAction = ActionFactory.CUT.create(window);
		register(cutAction);

		selectAllAction = ActionFactory.SELECT_ALL.create(window);
		register(selectAllAction);

		findAction = ActionFactory.FIND.create(window);
		register(findAction);
		findAction.setText("Find..."); // Replace, change label to "Find..."
		findAction.setImageDescriptor(StudioApplicationPlugin
				.getImageDescriptor("/icons/searchtool.gif"));

		maxEditorAction = ActionFactory.MAXIMIZE.create(window);
		register(maxEditorAction);

		minEditorAction = ActionFactory.MINIMIZE.create(window);
		register(minEditorAction);

		activateEditorAction = ActionFactory.ACTIVATE_EDITOR.create(window);
		register(activateEditorAction);

		nextEditorAction = ActionFactory.NEXT_EDITOR.create(window);
		register(nextEditorAction);

		previousEditorAction = ActionFactory.PREVIOUS_EDITOR.create(window);
		register(previousEditorAction);

		switchEditorActon = ActionFactory.SHOW_OPEN_EDITORS.create(window);
		register(switchEditorActon);

		saveAction = ActionFactory.SAVE.create(window);
		register(saveAction);

		/* TODO - Remove this if it is not needed 
		analyzeTableAction = new AnalyzeTableAction(com.tibco.cep.decision.table.utils.Messages.Apps_Show_Analyzer_Action, window);
		register(analyzeTableAction);
		*/
		
		saveAllAction = ActionFactory.SAVE_ALL.create(window);
		register(saveAllAction);

		saveAsAction = ActionFactory.SAVE_AS.create(window);
		register(saveAsAction);

		setActionsState();
	}

	private void setActionsState() {

	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager(Messages.Apps_Menu_File,
				IWorkbenchActionConstants.M_LAUNCH);
		MenuManager helpMenu = new MenuManager(Messages.Apps_Menu_Help,
				IWorkbenchActionConstants.M_LAUNCH);

		menuBar.add(fileMenu);

		// File

		fileMenu.add(closeAction);
		fileMenu.add(closeAllAction);
		fileMenu.add(new Separator());
		fileMenu.add(saveAction);
		fileMenu.add(saveAsAction);
		fileMenu.add(saveAllAction);
		fileMenu.add(new Separator());
		fileMenu.add(exitAction);
		// build EAR Action temporary
		//fileMenu.add(new Separator());
		//fileMenu.add(buildEAR);

		// Edit
		MenuManager editMenu = new MenuManager(Messages.Apps_Menu_Edit,
				IWorkbenchActionConstants.M_LAUNCH);
		editMenu.add(copyAction);
		editMenu.add(pasteAction);
		editMenu.add(cutAction);
		editMenu.add(new Separator());
		editMenu.add(selectAllAction);
		editMenu.add(new Separator());
		editMenu.add(findAction);
		menuBar.add(editMenu);

		// StateMachine (GroupMarker)
		menuBar.add(new GroupMarker(Messages.Apps_Menu_State_Machine));

		// Concept Editor (GroupMarker)
		menuBar.add(new GroupMarker(Messages.Apps_Menu_Concept));

		// Event Editor (GroupMarker)
		menuBar.add(new GroupMarker(Messages.Apps_Menu_Event));

		// Diagram menu (GroupMarker)
		menuBar.add(new GroupMarker(Messages.Apps_Menu_Diagram_GroupMarker));

		// Table menu (GroupMarker)
		menuBar.add(new GroupMarker(Messages.Apps_Menu_Table_GroupMarker));
		
		// Window
		MenuManager windowMenu = new MenuManager(Messages.Apps_Menu_Window,
				IWorkbenchActionConstants.M_LAUNCH);
		windowMenu.add(newWindowAction);
		windowMenu.add(new Separator());

		MenuManager showViewMenu = new MenuManager(
				Messages.Apps_Menu_Window_ShowView, ICommandIds.CMD_SHOWVIEW);
//		showViewMenu.add(showProjectExplorerAction);
//		showViewMenu.add(showPaletteViewAction);
//		showViewMenu.add(showOverViewAction);
//		showViewMenu.add(showConsoleViewAction);
//		showViewMenu.add(showProblemsViewAction);
		windowMenu.add(showViewMenu);
		windowMenu.add(new Separator());

		MenuManager navigationMenuManager = new MenuManager(
				Messages.Apps_Navigation_Action, ICommandIds.CMD_NAVIGATION);
		windowMenu.add(navigationMenuManager);
		navigationMenuManager.add(maxEditorAction);
		navigationMenuManager.add(minEditorAction);
		navigationMenuManager.add(new Separator());
		navigationMenuManager.add(activateEditorAction);
		navigationMenuManager.add(nextEditorAction);
		navigationMenuManager.add(previousEditorAction);
		navigationMenuManager.add(switchEditorActon);
		windowMenu.add(new Separator());
		windowMenu.add(preferenceAction);

		menuBar.add(windowMenu);

		// Help
//		if (introAction != null)
//			helpMenu.add(introAction);
		helpMenu.add(new Separator());
		helpMenu.add(helpAction);
		helpMenu.add(new Separator());
//		helpMenu.add(showKeyAssistAction);
		helpMenu.add(new Separator());
		helpMenu.add(new Separator());
		helpMenu.add(aboutAction);

		menuBar.add(helpMenu);

	}

	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
		coolBar.add(new ToolBarContributionItem(toolbar,
				Messages.Apps_Coolbar_Main));
		toolbar.add(saveAction);
		toolbar.add(new Separator());
		toolbar.add(new Separator());
	}

	public IWorkbenchWindow getWindow() {
		return window;
	}
}
