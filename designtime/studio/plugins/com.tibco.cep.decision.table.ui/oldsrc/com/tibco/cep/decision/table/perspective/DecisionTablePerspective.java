package com.tibco.cep.decision.table.perspective;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerView;
import com.tibco.cep.studio.ui.overview.CommonOverview;
import com.tibco.cep.studio.util.StudioConfig;

/**
 * 
 * @author Gabe
 * @author aathalye
 * @author sasahoo
 * 
 */
public class DecisionTablePerspective implements IPerspectiveFactory {
	public static final String ID = "com.tibco.cep.decision.table.perspective";
	private static final String LOG_VIEW = "org.eclipse.pde.runtime.LogView";
	private static final String CONSOLE_VIEW = "org.eclipse.ui.console.ConsoleView";
	private static final String BUI_SHOW_ERROR_LOG = "bui.show.ErrorLog";
	public static final String NAVIGATOR_ID = "com.tibco.cep.studio.projectexplorer.view";

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
		layout.addActionSet(IDebugUIConstants.DEBUG_ACTION_SET);
		
		String editorArea = layout.getEditorArea();
		IFolderLayout leftLayout = layout.createFolder("left",IPageLayout.LEFT, 0.21F, editorArea);
		/**
		 * Changed this layout in favour of the new 4.0 Project explorer based on CNF
		 */
		leftLayout.addView(NAVIGATOR_ID);
		IFolderLayout bottom = layout.createFolder("bottom",IPageLayout.BOTTOM, 0.68F, editorArea);

		bottom.addView(CONSOLE_VIEW);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottom.addView(IPageLayout.ID_PROP_SHEET);
		// only show the Error Log view if BUI_SHOW_ERROR_LOG
		// (bui.show.ErrorLog) is true in bui-config.tra
		if ("true".equals(StudioConfig.getInstance().getProperty(BUI_SHOW_ERROR_LOG))) {
			bottom.addView(LOG_VIEW);
		}
		bottom.addView("com.tibco.cep.studio.debug.ui.inputView");

		/**
		 * Changed this layout in favour of the new 4.0 Project explorer based on CNF
		 */
		IFolderLayout right = layout.createFolder("right", IPageLayout.RIGHT,0.8F, editorArea);
		right.addView(DecisionTableAnalyzerView.ID);

		/*
		layout.addView(RuleSetToolOntologyFunctionsView.ID, 4, 0.3F,RuleSetToolStandardFunctionsView.ID);
		layout.addView(RuleSetToolCustomFunctionsView.ID, 4, 0.3F,RuleSetToolStandardFunctionsView.ID);
		if (DecisionTablePlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.ANALYZER_SHOW_TABLE_ANALYZER_VIEW)) {
			layout.addView(DecisionTableAnalyzerView.ID, 4, 0.4F,RuleSetToolStandardFunctionsView.ID);
		}
		*/

//		layout.addView(ArgumentExplorerView.ID, 4, 0.7F, NAVIGATOR_ID);
//		layout.addView(TableOverview.ID, 4, 0.6F, ArgumentExplorerView.ID);
		layout.addView(CommonOverview.ID, 4, 0.72F, NAVIGATOR_ID);
		layout.addShowViewShortcut(CONSOLE_VIEW);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
		layout.addNewWizardShortcut(ID);
		layout.addPerspectiveShortcut(ID);
	}
}
