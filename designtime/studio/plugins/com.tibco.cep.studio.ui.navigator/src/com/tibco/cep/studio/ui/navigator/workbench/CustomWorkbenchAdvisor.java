package com.tibco.cep.studio.ui.navigator.workbench;

import java.net.URL;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Bundle;

/**
 * @author sasahoo
 */
public abstract class CustomWorkbenchAdvisor extends WorkbenchAdvisor
{
	
    /*** Constants for Images ***/
    public final static String IMG_ETOOL_BUILD_EXEC = "IMG_ETOOL_BUILD_EXEC"; //$NON-NLS-1$

    public final static String IMG_ETOOL_BUILD_EXEC_HOVER = "IMG_ETOOL_BUILD_EXEC_HOVER"; //$NON-NLS-1$

    public final static String IMG_ETOOL_BUILD_EXEC_DISABLED = "IMG_ETOOL_BUILD_EXEC_DISABLED"; //$NON-NLS-1$

    public final static String IMG_ETOOL_SEARCH_SRC = "IMG_ETOOL_SEARCH_SRC"; //$NON-NLS-1$

    public final static String IMG_ETOOL_SEARCH_SRC_HOVER = "IMG_ETOOL_SEARCH_SRC_HOVER"; //$NON-NLS-1$

    public final static String IMG_ETOOL_SEARCH_SRC_DISABLED = "IMG_ETOOL_SEARCH_SRC_DISABLED"; //$NON-NLS-1$

    public final static String IMG_ETOOL_NEXT_NAV = "IMG_ETOOL_NEXT_NAV"; //$NON-NLS-1$

    public final static String IMG_ETOOL_PREVIOUS_NAV = "IMG_ETOOL_PREVIOUS_NAV"; //$NON-NLS-1$

    public final static String IMG_ETOOL_PROBLEM_CATEGORY = "IMG_ETOOL_PROBLEM_CATEGORY"; //$NON-NLS-1$

	public final static String IMG_ETOOL_PROBLEMS_VIEW= "IMG_ETOOL_PROBLEMS_VIEW"; //$NON-NLS-1$

	public final static String IMG_ETOOL_PROBLEMS_VIEW_ERROR= "IMG_ETOOL_PROBLEMS_VIEW_ERROR"; //$NON-NLS-1$

	public final static String IMG_ETOOL_PROBLEMS_VIEW_WARNING= "IMG_ETOOL_PROBLEMS_VIEW_WARNING"; //$NON-NLS-1$

    public final static String IMG_LCL_FLAT_LAYOUT = "IMG_LCL_FLAT_LAYOUT"; //$NON-NLS-1$
    
    public final static String IMG_LCL_HIERARCHICAL_LAYOUT = "IMG_LCL_HIERARCHICAL_LAYOUT"; //$NON-NLS-1$

    //wizard images
    public final static String IMG_WIZBAN_NEWPRJ_WIZ = "IMG_WIZBAN_NEWPRJ_WIZ"; //$NON-NLS-1$

    public final static String IMG_WIZBAN_NEWFOLDER_WIZ = "IMG_WIZBAN_NEWFOLDER_WIZ"; //$NON-NLS-1$

    public final static String IMG_WIZBAN_NEWFILE_WIZ = "IMG_WIZBAN_NEWFILE_WIZ"; //$NON-NLS-1$

    public final static String IMG_WIZBAN_IMPORTDIR_WIZ = "IMG_WIZBAN_IMPORTDIR_WIZ"; //$NON-NLS-1$

    public final static String IMG_WIZBAN_IMPORTZIP_WIZ = "IMG_WIZBAN_IMPORTZIP_WIZ"; //$NON-NLS-1$

    public final static String IMG_WIZBAN_EXPORTDIR_WIZ = "IMG_WIZBAN_EXPORTDIR_WIZ"; //$NON-NLS-1$

    public final static String IMG_WIZBAN_EXPORTZIP_WIZ = "IMG_WIZBAN_EXPORTZIP_WIZ"; //$NON-NLS-1$

    public final static String IMG_WIZBAN_RESOURCEWORKINGSET_WIZ = "IMG_WIZBAN_EXPORTZIP_WIZ"; //$NON-NLS-1$
    
    public final static String IMG_DLGBAN_SAVEAS_DLG = "IMG_DLGBAN_SAVEAS_DLG"; //$NON-NLS-1$
    
    public final static String IMG_DLGBAN_QUICKFIX_DLG = "IMG_DLGBAN_QUICKFIX_DLG"; //$NON-NLS-1$

    // task objects
    public final static String IMG_OBJS_COMPLETE_TSK = "IMG_OBJS_COMPLETE_TSK"; //$NON-NLS-1$

    public final static String IMG_OBJS_INCOMPLETE_TSK = "IMG_OBJS_INCOMPLETE_TSK"; //$NON-NLS-1$
    
    //problems images
    public static final String IMG_OBJS_ERROR_PATH = "IMG_OBJS_ERROR_PATH"; //$NON-NLS-1$

    public static final String IMG_OBJS_WARNING_PATH = "IMG_OBJS_WARNING_PATH"; //$NON-NLS-1$

    public static final String IMG_OBJS_INFO_PATH = "IMG_OBJS_INFO_PATH"; //$NON-NLS-1$

    // product
    public final static String IMG_OBJS_DEFAULT_PROD = "IMG_OBJS_DEFAULT_PROD"; //$NON-NLS-1$

    // welcome
    public final static String IMG_OBJS_WELCOME_ITEM = "IMG_OBJS_WELCOME_ITEM"; //$NON-NLS-1$
    
    public final static String IMG_OBJS_WELCOME_BANNER = "IMG_OBJS_WELCOME_BANNER"; //$NON-NLS-1$

    //Quick fix images
	public static final String IMG_DLCL_QUICK_FIX_DISABLED = "IMG_DLCL_QUICK_FIX_DISABLED";//$NON-NLS-1$
	public static final String IMG_ELCL_QUICK_FIX_ENABLED = "IMG_ELCL_QUICK_FIX_ENABLED"; //$NON-NLS-1$
	public static final String IMG_OBJS_FIXABLE_WARNING= "IMG_OBJS_FIXABLE_WARNING"; //$NON-NLS-1$
	public static final String IMG_OBJS_FIXABLE_ERROR= "IMG_OBJS_FIXABLE_ERROR"; //$NON-NLS-1$
	
	/**
	 * Declares all IDE-specific workbench images. This includes both "shared"
	 * images (named in {@link IDE.SharedImages}) and internal images (named in
	 */
	protected void declareWorkbenchImages()	{
		final String ICONS_PATH = "$nl$/icons/full/";//$NON-NLS-1$
		final String PATH_ELOCALTOOL = ICONS_PATH + "elcl16/"; // Enabled
		// //$NON-NLS-1$

		// toolbar
		// icons.
		final String PATH_DLOCALTOOL = ICONS_PATH + "dlcl16/"; // Disabled
		// //$NON-NLS-1$
		// //$NON-NLS-1$
		// toolbar
		// icons.
		final String PATH_ETOOL = ICONS_PATH + "etool16/"; // Enabled toolbar
		// //$NON-NLS-1$
		// //$NON-NLS-1$
		// icons.
		final String PATH_DTOOL = ICONS_PATH + "dtool16/"; // Disabled toolbar
		// //$NON-NLS-1$
		// //$NON-NLS-1$
		// icons.
		final String PATH_OBJECT = ICONS_PATH + "obj16/"; // Model object
		// //$NON-NLS-1$
		// //$NON-NLS-1$
		// icons
		final String PATH_WIZBAN = ICONS_PATH + "wizban/"; // Wizard
		// //$NON-NLS-1$
		// //$NON-NLS-1$
		// icons

		Bundle ideBundle = Platform.getBundle("org.eclipse.ui.ide");

		declareWorkbenchImage(ideBundle, IMG_ETOOL_BUILD_EXEC,
				PATH_ETOOL + "build_exec.gif", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_ETOOL_BUILD_EXEC_HOVER,
				PATH_ETOOL + "build_exec.gif", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_ETOOL_BUILD_EXEC_DISABLED,
				PATH_DTOOL + "build_exec.gif", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IMG_ETOOL_SEARCH_SRC,
				PATH_ETOOL + "search_src.gif", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_ETOOL_SEARCH_SRC_HOVER,
				PATH_ETOOL + "search_src.gif", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_ETOOL_SEARCH_SRC_DISABLED,
				PATH_DTOOL + "search_src.gif", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IMG_ETOOL_NEXT_NAV, PATH_ETOOL
				+ "next_nav.gif", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IMG_ETOOL_PREVIOUS_NAV,
				PATH_ETOOL + "prev_nav.gif", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IMG_WIZBAN_NEWPRJ_WIZ,
				PATH_WIZBAN + "newprj_wiz.png", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_WIZBAN_NEWFOLDER_WIZ,
				PATH_WIZBAN + "newfolder_wiz.png", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_WIZBAN_NEWFILE_WIZ,
				PATH_WIZBAN + "newfile_wiz.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IMG_WIZBAN_IMPORTDIR_WIZ,
				PATH_WIZBAN + "importdir_wiz.png", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_WIZBAN_IMPORTZIP_WIZ,
				PATH_WIZBAN + "importzip_wiz.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IMG_WIZBAN_EXPORTDIR_WIZ,
				PATH_WIZBAN + "exportdir_wiz.png", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_WIZBAN_EXPORTZIP_WIZ,
				PATH_WIZBAN + "exportzip_wiz.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IMG_WIZBAN_RESOURCEWORKINGSET_WIZ, PATH_WIZBAN
						+ "workset_wiz.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IMG_DLGBAN_SAVEAS_DLG,
				PATH_WIZBAN + "saveas_wiz.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IMG_DLGBAN_QUICKFIX_DLG,
				PATH_WIZBAN + "quick_fix.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IDE.SharedImages.IMG_OBJ_PROJECT, PATH_OBJECT
				+ "prj_obj.gif", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IDE.SharedImages.IMG_OBJ_PROJECT_CLOSED, PATH_OBJECT
				+ "cprj_obj.gif", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IDE.SharedImages.IMG_OPEN_MARKER, PATH_ELOCALTOOL
				+ "gotoobj_tsk.gif", true); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IMG_ELCL_QUICK_FIX_ENABLED,
				PATH_ELOCALTOOL + "smartmode_co.gif", true); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IMG_DLCL_QUICK_FIX_DISABLED,
				PATH_DLOCALTOOL + "smartmode_co.gif", true); //$NON-NLS-1$

		// task objects
		// declareRegistryImage(IMG_OBJS_HPRIO_TSK,
		// PATH_OBJECT+"hprio_tsk.gif");
		// declareRegistryImage(IMG_OBJS_MPRIO_TSK,
		// PATH_OBJECT+"mprio_tsk.gif");
		// declareRegistryImage(IMG_OBJS_LPRIO_TSK,
		// PATH_OBJECT+"lprio_tsk.gif");

		declareWorkbenchImage(ideBundle, IDE.SharedImages.IMG_OBJS_TASK_TSK, PATH_OBJECT
				+ "taskmrk_tsk.gif", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IDE.SharedImages.IMG_OBJS_BKMRK_TSK, PATH_OBJECT
				+ "bkmrk_tsk.gif", true); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IMG_OBJS_COMPLETE_TSK,
				PATH_OBJECT + "complete_tsk.gif", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_OBJS_INCOMPLETE_TSK,
				PATH_OBJECT + "incomplete_tsk.gif", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_OBJS_WELCOME_ITEM,
				PATH_OBJECT + "welcome_item.gif", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_OBJS_WELCOME_BANNER,
				PATH_OBJECT + "welcome_banner.gif", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_OBJS_ERROR_PATH,
				PATH_OBJECT + "error_tsk.gif", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_OBJS_WARNING_PATH,
				PATH_OBJECT + "warn_tsk.gif", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_OBJS_INFO_PATH, PATH_OBJECT
				+ "info_tsk.gif", true); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IMG_LCL_FLAT_LAYOUT,
				PATH_ELOCALTOOL + "flatLayout.gif", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_LCL_HIERARCHICAL_LAYOUT,
				PATH_ELOCALTOOL + "hierarchicalLayout.gif", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_ETOOL_PROBLEM_CATEGORY,
				PATH_ETOOL + "problem_category.gif", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IMG_ETOOL_NEXT_NAV,
				PATH_ELOCALTOOL + "linkto_help.gif", false); //$NON-NLS-1$
	}

	/**
	 * Declares an IDE-specific workbench image.
	 * 
	 * @param symbolicName
	 *            the symbolic name of the image
	 * @param path
	 *            the path of the image file; this path is relative to the base
	 *            of the IDE plug-in
	 * @param shared
	 *            <code>true</code> if this is a shared image, and
	 *            <code>false</code> if this is not a shared image
	 */
	private void declareWorkbenchImage(Bundle ideBundle, String symbolicName, String path,
			boolean shared)	{
		URL url = FileLocator.find(ideBundle, new Path(path), null);
		ImageDescriptor desc = ImageDescriptor.createFromURL(url);
		getWorkbenchConfigurer().declareImage(symbolicName, desc, shared);
	}

    /**
     * Returns the image descriptor for the workbench image with the given
     * symbolic name. Use this method to retrieve image descriptors for any
     * of the images named in this class.
     *
     * @param symbolicName the symbolic name of the image
     * @return the image descriptor, or <code>null</code> if none
     */
    public static ImageDescriptor getImageDescriptor(String symbolicName) {
        return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
                symbolicName);
    }
	
	@Override
	public void initialize(IWorkbenchConfigurer configurer)	{
		configurer.setSaveAndRestore(true);
		declareWorkbenchImages();
	}

	@Override
	public IAdaptable getDefaultPageInput()	{
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	@Override
	public void preStartup() {
		IDE.registerAdapters();
	}
}
