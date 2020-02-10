package com.tibco.cep.studio.debug.ui.perspective;

import org.eclipse.debug.internal.ui.IInternalDebugUIConstants;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

import com.tibco.cep.studio.debug.ui.views.RuleBreakpointsView;
import com.tibco.cep.studio.debug.ui.views.RuleDebugAgendaView;
import com.tibco.cep.studio.debug.ui.views.RuleDebugInputView;
import com.tibco.cep.studio.debug.ui.views.RuleStackView;

/**
 * 
 * @author ggrigore, ssailapp
 *
 */
@SuppressWarnings({"restriction","unused"})
public class DebugPerspective implements IPerspectiveFactory {

	public static final String ID = "org.eclipse.debug.ui.DebugPerspective";
	public static final String NAVIGATOR_ID = "com.tibco.cep.studio.projectexplorer.view";
	public static final String OVERVIEW_ID = "com.tibco.cep.diagramming.views.drawing.overview";
	private static final String CONSOLE_VIEW = "org.eclipse.ui.console.ConsoleView";
	
	private static final String RULE_TRACE_VIEW_ID = "com.tibco.cep.studio.debug.ui.views.ruletraceview";
	private static final String VARIABLES_VIEW_ID = "com.tibco.cep.studio.debug.ui.views.variablesview";

	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);
	}

	public void defineActions(IPageLayout layout) {
		layout.addShowViewShortcut(NAVIGATOR_ID);
		layout.addShowViewShortcut(RuleStackView.RULE_STACK_VIEW_ID);
		layout.addShowViewShortcut(VARIABLES_VIEW_ID);
		layout.addShowViewShortcut(RuleDebugInputView.RULE_INPUT_VIEW_ID);
		layout.addShowViewShortcut(RuleDebugAgendaView.RULE_AGENDA_VIEW_ID);
//		layout.addShowViewShortcut(IDebugUIConstants.ID_BREAKPOINT_VIEW);
		layout.addShowViewShortcut(RuleBreakpointsView.RULE_BREAKPOINT_VIEW_ID);
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
		layout.addShowViewShortcut(OVERVIEW_ID);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
	
		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
		layout.addActionSet(IDebugUIConstants.DEBUG_ACTION_SET);
		layout.addActionSet("org.eclipse.debug.ui.breakpointActionSet");
	}

	
	public void defineLayout(IPageLayout layout) {
		IFolderLayout consoleFolder = layout.createFolder(IInternalDebugUIConstants.ID_CONSOLE_FOLDER_VIEW, IPageLayout.BOTTOM, (float)0.75, layout.getEditorArea());
		consoleFolder.addView(IConsoleConstants.ID_CONSOLE_VIEW);
		consoleFolder.addView("org.eclipse.pde.runtime.LogView");
		consoleFolder.addView(RuleDebugInputView.RULE_INPUT_VIEW_ID);
		consoleFolder.addPlaceholder(IPageLayout.ID_PROP_SHEET);
		consoleFolder.addView(IPageLayout.ID_PROP_SHEET);
		
		IFolderLayout navFolder= layout.createFolder(IInternalDebugUIConstants.ID_NAVIGATOR_FOLDER_VIEW, IPageLayout.TOP, (float) 0.45, layout.getEditorArea());
//		navFolder.addView(IDebugUIConstants.ID_DEBUG_VIEW);
		navFolder.addView(RuleStackView.RULE_STACK_VIEW_ID);
		navFolder.addPlaceholder(IPageLayout.ID_RES_NAV);
		navFolder.addView(IPageLayout.ID_RES_NAV);
		
		
		IFolderLayout toolsFolder= layout.createFolder(IInternalDebugUIConstants.ID_TOOLS_FOLDER_VIEW, IPageLayout.RIGHT, (float) 0.50, IInternalDebugUIConstants.ID_NAVIGATOR_FOLDER_VIEW);
		toolsFolder.addView(VARIABLES_VIEW_ID);
		toolsFolder.addView(RuleDebugAgendaView.RULE_AGENDA_VIEW_ID);
//		toolsFolder.addView(IDebugUIConstants.ID_BREAKPOINT_VIEW);
		toolsFolder.addView(RuleBreakpointsView.RULE_BREAKPOINT_VIEW_ID);
		
		IFolderLayout outlineFolder= layout.createFolder(IInternalDebugUIConstants.ID_OUTLINE_FOLDER_VIEW, IPageLayout.RIGHT, (float) 0.75, layout.getEditorArea());
		outlineFolder.addView(NAVIGATOR_ID);
		outlineFolder.addPlaceholder(IPageLayout.ID_OUTLINE);
		outlineFolder.addPlaceholder(NAVIGATOR_ID);
//		outlineFolder.addView(IPageLayout.ID_OUTLINE);
		
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);

	}
}
