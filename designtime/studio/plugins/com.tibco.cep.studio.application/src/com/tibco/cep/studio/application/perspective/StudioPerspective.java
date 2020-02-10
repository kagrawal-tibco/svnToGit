package com.tibco.cep.studio.application.perspective;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.ui.texteditor.templates.TemplatesView;

public class StudioPerspective implements IPerspectiveFactory {

	public static final String ID = "com.tibco.cep.studio.application.perspective";
//	private static final String CONSOLE_VIEW = "org.eclipse.ui.console.ConsoleView";
	public static final String OVERVIEW_ID = "com.tibco.cep.diagramming.views.drawing.overview";
	public static final String NAVIGATOR_ID = "com.tibco.cep.studio.projectexplorer.view";
	public static final String ERROR_VIEW_ID = "org.eclipse.pde.runtime.LogView";

	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);
	}

	public void defineActions(IPageLayout layout) {
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");

		layout.addShowViewShortcut(NAVIGATOR_ID);
		layout.addShowViewShortcut(ERROR_VIEW_ID);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
		
		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
		//Debug Action Set is not required in studio perspective
		//layout.addActionSet(IDebugUIConstants.DEBUG_ACTION_SET);
		layout.addActionSet("org.eclipse.debug.ui.breakpointActionSet");
		
		// Add JDT Perspective items
		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
		layout.addActionSet(JavaUI.ID_ACTION_SET);
		layout.addActionSet(JavaUI.ID_ELEMENT_CREATION_ACTION_SET);
		layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);

		// views - java
		layout.addShowViewShortcut(JavaUI.ID_PACKAGES);
		layout.addShowViewShortcut(JavaUI.ID_TYPE_HIERARCHY);
		layout.addShowViewShortcut(JavaUI.ID_SOURCE_VIEW);
		layout.addShowViewShortcut(JavaUI.ID_JAVADOC_VIEW);


		// views - search
		layout.addShowViewShortcut(NewSearchUI.SEARCH_VIEW_ID);

		// views - debugging
		layout.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);

		// views - standard workbench
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
		layout.addShowViewShortcut("com.tibco.cep.studio.projectexplorer.view");
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
		layout.addShowViewShortcut(IProgressConstants.PROGRESS_VIEW_ID);
		layout.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
		layout.addShowViewShortcut(TemplatesView.ID);
		layout.addShowViewShortcut("org.eclipse.pde.runtime.LogView"); //$NON-NLS-1$

		// new actions - Java project creation wizard
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.JavaProjectWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewPackageCreationWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewClassCreationWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewInterfaceCreationWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewEnumCreationWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewAnnotationCreationWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewSourceFolderCreationWizard");	 //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewSnippetFileCreationWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewJavaWorkingSetWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");//$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");//$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.ui.editors.wizards.UntitledTextFileWizard");//$NON-NLS-1$

		// 'Window' > 'Open Perspective' contributions
		layout.addPerspectiveShortcut(JavaUI.ID_BROWSING_PERSPECTIVE);
		layout.addPerspectiveShortcut(IDebugUIConstants.ID_DEBUG_PERSPECTIVE);
	}

	public void defineLayout(IPageLayout layout) {

		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);

		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT,(float) 0.20, editorArea);
		left.addView(NAVIGATOR_ID);

		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, (float) 0.75, editorArea);
		bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
		bottom.addView(ERROR_VIEW_ID);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottom.addView(IPageLayout.ID_PROP_SHEET);
        bottom.addView("com.tibco.cep.studio.debug.ui.inputView");
		bottom.addView(JavaUI.ID_JAVADOC_VIEW);
		bottom.addView(JavaUI.ID_SOURCE_VIEW);
		bottom.addPlaceholder(NewSearchUI.SEARCH_VIEW_ID);
		bottom.addPlaceholder(IPageLayout.ID_BOOKMARKS);
		bottom.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);
        
        IPlaceholderFolderLayout right = layout.createPlaceholderFolder("right", IPageLayout.RIGHT, (float) 0.8, editorArea);
        
//        right.addView(IPageLayout.ID_OUTLINE/*, IPageLayout.RIGHT, 0.8F, editorArea*/);
        right.addPlaceholder(IPageLayout.ID_OUTLINE);
        right.addPlaceholder("com.tibco.cep.studio.ui.views.CatalogFunctionsView");
        right.addPlaceholder("com.tibco.cep.studio.ui.views.GlobalVariablesView");
        right.addPlaceholder(TemplatesView.ID);
	}
}
