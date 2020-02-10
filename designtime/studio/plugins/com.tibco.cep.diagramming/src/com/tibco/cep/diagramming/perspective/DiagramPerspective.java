package com.tibco.cep.diagramming.perspective;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class DiagramPerspective implements IPerspectiveFactory {

	public static final String ID = "com.tibco.cep.diagramming.diagram.perspective";
	public static final String NAVIGATOR_ID = "com.tibco.cep.studio.projectexplorer.view";
	public static final String PALETTE_VIEW_ID = "com.tibco.cep.studio.palette.views.paletteview";
//	public static final String OVERVIEW_ID = "com.tibco.cep.diagramming.views.drawing.overview";
	public static final String OVERVIEW_ID = "com.tibco.cep.studio.ui.overview.commonoverview";
	private static final String CONSOLE_VIEW = "org.eclipse.ui.console.ConsoleView";
//	private static final String PROCESS_VIEW = "com.tibco.cep.decision.process.processview";
	public static final String ERROR_VIEW_ID = "org.eclipse.pde.runtime.LogView";

	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);
	}

	public void defineActions(IPageLayout layout) {
		layout.addShowViewShortcut(PALETTE_VIEW_ID);
		layout.addShowViewShortcut(OVERVIEW_ID);
		layout.addShowViewShortcut(NAVIGATOR_ID);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
		
		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
		//Debug Action Set is not required in studio perspective
		//layout.addActionSet(IDebugUIConstants.DEBUG_ACTION_SET);
	}

	public void defineLayout(IPageLayout layout) {

		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);

		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT,(float) 0.20, editorArea);
		left.addView(NAVIGATOR_ID);

		layout.addView(OVERVIEW_ID, 4, 0.72F, NAVIGATOR_ID);

		IFolderLayout bottom = layout.createFolder("bottom",IPageLayout.BOTTOM, (float) 0.75, editorArea);
		bottom.addView(CONSOLE_VIEW);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottom.addView(ERROR_VIEW_ID);
		bottom.addView(IPageLayout.ID_PROP_SHEET);
//		bottom.addView(PROCESS_VIEW);

//		layout.addView(PALETTE_VIEW_ID, IPageLayout.RIGHT, 0.8F, editorArea);
		
		IFolderLayout right = layout.createFolder("right", IPageLayout.RIGHT, (float) 0.8, editorArea);
		right.addView(PALETTE_VIEW_ID);
		right.addPlaceholder("com.tibco.cep.studio.ui.views.CatalogFunctionsView");
		right.addPlaceholder("com.tibco.cep.studio.ui.views.GlobalVariablesView");
	}
}
