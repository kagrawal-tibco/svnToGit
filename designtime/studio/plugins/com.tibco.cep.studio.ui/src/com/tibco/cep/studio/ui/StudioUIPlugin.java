package com.tibco.cep.studio.ui;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.mapper.ui.util.SWTMapperUtils;
import com.tibco.cep.studio.ui.persistence.IManageResource;
import com.tibco.cep.studio.ui.persistence.ManageResourceFactory;
import com.tibco.cep.studio.ui.persistence.ManageResourceFile;
import com.tibco.cep.studio.ui.property.StudioPropertyChangeListener;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.validation.PostValidationListener;
import com.tibco.cep.studio.ui.validation.StudioJavaSourceChangeListener;

/**
 * The activator class controls the plug-in life cycle
 */
public class StudioUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.ui";	

	// The shared instance
	private static StudioUIPlugin plugin;
	
	private IResourceChangeListener fValidationListener = new PostValidationListener();
	
	public static boolean DEBUG = false;
	
	private Entity selectedEntity = null;

	
	IManageResource manageResource = null ;

	private IPropertyChangeListener fStudioPropertyChangeListener = new StudioPropertyChangeListener();
	private IResourceChangeListener studioJavaSourceListener = new StudioJavaSourceChangeListener();

	public static final String RESOURCE_MANAGER = "ResourceManager" ; //$NON-NLS-1$
	public static final String EMPTY_CATEGORIE = "CategorieVide" ; //$NON-NLS-1$
	
	/**
	 * The constructor
	 */
	public StudioUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		ResourcesPlugin.getWorkspace().addResourceChangeListener(fValidationListener);
        StudioResourceUtils.migrateOldProjects();
        getPreferenceStore().addPropertyChangeListener(fStudioPropertyChangeListener);
        ResourcesPlugin.getWorkspace().addResourceChangeListener(studioJavaSourceListener, IResourceChangeEvent.POST_CHANGE);
        SWTMapperUtils.initCustomFunctions();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(fValidationListener);
        getPreferenceStore().removePropertyChangeListener(fStudioPropertyChangeListener);
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(studioJavaSourceListener);
	}
	
	/**
	 * Convenience method which returns the unique identifier of this plug-in.
	 */
	public static String getUniqueIdentifier() {
		return PLUGIN_ID;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static StudioUIPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 * 
	 * @param pluginId -> The plugin id to load image from.
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String pluginId, String path) {
		pluginId = (pluginId != null) ? pluginId : PLUGIN_ID;
		return imageDescriptorFromPlugin(pluginId, path);
	}
	
	public Image getImage(String imageName) {
		Image image = getImageRegistry().get(imageName);
		if (image == null) {
			ImageDescriptor desc = getImageDescriptor(imageName);
			if (desc == null) {
				System.out.println("Could not find image "+imageName);
				return null;
			}
			image = desc.createImage();
			getImageRegistry().put(imageName, image);
		}
		return image;
	}
	
	public static IWorkbenchPage getActivePage() {
        IWorkbenchWindow window = getActiveWorkbenchWindow();
        if (window == null)
            return null;
        return getActiveWorkbenchWindow().getActivePage();
	}
	
	/**
	 * Returns the currently active workbench window or <code>null</code>
	 * if none.
	 * 
	 * @return the currently active workbench window or <code>null</code>
	 */
	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		return getDefault().getWorkbench().getActiveWorkbenchWindow();
	}
	
	/**
	 * Returns the currently active workbench window shell or <code>null</code>
	 * if none.
	 * 
	 * @return the currently active workbench window shell or <code>null</code>
	 */
	public static Shell getShell() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null) {
			IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
			if (windows.length > 0) {
				return windows[0].getShell();
			}
		}
		else {
			return window.getShell();
		}
		return null;
	}
	
	/**
	 * @param className
	 * @param message
	 */
	public static void debug(String className, String message, Object... args) {
		String debugMessage = MessageFormat.format(message, args);
		debug(MessageFormat.format("[{0}] {1}", className, debugMessage));
		
	}
	
	/**
	 * @param className
	 * @param message
	 */
	public static void debug(String className, String message) {
		debug(MessageFormat.format("[{0}] {1}", className,message));
		
	}
	
	/**
	 * If the debug flag is set the specified message is printed to the console
	 * 
	 * @param message
	 */
	public static void debug(String message) {
		if(getDefault().isDebugging()) { // run eclipse with -debug option
			System.out.println(MessageFormat.format("[{0}] {1}",PLUGIN_ID,message));
		}
	}
	
	public static void log(String msg) {
		log(msg, null);
	}

	public static void log(String msg, Throwable e) {
		getDefault().getLog().log(new Status(Status.INFO, PLUGIN_ID, Status.OK, msg, e));
	}
	
	/**
	 * Logs the specified status with this plug-in's log.
	 * 
	 * @param status status to log
	 */
	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}
	
	/**
	 * Logs the specified throwable with this plug-in's log.
	 * 
	 * @param t throwable to log
	 */
	public static void log(Throwable t) {
		log(newErrorStatus(MessageFormat.format("Error logged from {0} Plugin: ",PLUGIN_ID), t)); //$NON-NLS-1$
	}
	
	/**
	 * Logs an internal error with the specified message.
	 * 
	 * @param message the error message to log
	 */
	public static void logErrorMessage(String message) {
		// this message is intentionally not internationalized, as an exception may
		// be due to the resource bundle itself
		log(newErrorStatus(MessageFormat.format("Internal message logged from {0} Plugin: {1}",PLUGIN_ID,message), null)); //$NON-NLS-1$
	}
	
	/**
	 * Returns a new error status for this plug-in with the given message
	 * @param message the message to be included in the status
	 * @param exception the exception to be included in the status or <code>null</code> if none
	 * @return a new error status
	 */
	public static IStatus newErrorStatus(String message, Throwable exception) {
		return new Status(IStatus.ERROR, PLUGIN_ID, message, exception);
	}
	
	/**
	 * Utility method with conventions
	 */
	public static void errorDialog(Shell shell, String title, String message, IStatus s) {
		// if the 'message' resource string and the IStatus' message are the same,
		// don't show both in the dialog
		if (s != null && message.equals(s.getMessage())) {
			message= null;
		}
		ErrorDialog.openError(shell, title, message, s);
	}
	
	/**
	 * Utility method with conventions
	 */
	public static void errorDialog(Shell shell, String title, String message, Throwable t) {
		IStatus status;
		if (t instanceof CoreException) {
			status= ((CoreException)t).getStatus();
			// if the 'message' resource string and the IStatus' message are the same,
			// don't show both in the dialog
			if (status != null && message.equals(status.getMessage())) {
				message= null;
			}
		} else {
			status= new Status(IStatus.ERROR, PLUGIN_ID,  "Error within Studio UI Plugin: ", t);
			log(status);
		}
		ErrorDialog.openError(shell, title, message, status);
	}

	
	public Entity getSelectedEntity() {
		return selectedEntity;
	}

	public void setSelectedEntity(Entity selectedEntity) {
		this.selectedEntity = selectedEntity;
	}
	
	/**
	 * Returns a section in the Studio UI plugin's dialog settings. If the section doesn't exist yet, it is created.
	 *
	 * @param name the name of the section
	 * @return the section of the given name
	 * @since 3.2
	 */
	public IDialogSettings getDialogSettingsSection(String name) {
		IDialogSettings dialogSettings= getDialogSettings();
		IDialogSettings section= dialogSettings.getSection(name);
		if (section == null) {
			section= dialogSettings.addNewSection(name);
		}
		return section;
	}

	
	public IManageResource getManageResource() {
		if (manageResource==null) {
			IPreferenceStore store = StudioUIPlugin.getDefault().getPreferenceStore();
			String id = store.getString(StudioUIPlugin.RESOURCE_MANAGER) ;
			manageResource = ManageResourceFactory.resolveManagerResource(id) ;
			if (manageResource == null) 
				manageResource = new ManageResourceFile() ;
		}
		
		return manageResource ;
	}	
	
	/**
	 * Handles the given <code>InvocationTargetException</code>.
	 *
	 * @param e the <code>InvocationTargetException</code> to be handled
	 * @param parent the dialog window's parent shell
	 * @param title the dialog window's window title
	 * @param message message to be displayed by the dialog window
	 */
	public static void handle(InvocationTargetException e, Shell parent, String title, String message) {
		plugin.perform(e, parent, title, message);
	}

	//---- Hooks for subclasses to control exception handling ------------------------------------

	protected void perform(IStatus status, Shell shell, String title, String message) {
		StudioUIPlugin.log(status);
		ErrorDialog.openError(shell, title, message, status);
	}

	protected void perform(CoreException e, Shell shell, String title, String message) {
		StudioUIPlugin.log(e);
		IStatus status= e.getStatus();
		if (status != null) {
			ErrorDialog.openError(shell, title, message, status);
		} else {
			displayMessageDialog(e.getMessage(), shell, title, message);
		}
	}

	protected void perform(InvocationTargetException e, Shell shell, String title, String message) {
		Throwable target= e.getTargetException();
		if (target instanceof CoreException) {
			perform((CoreException)target, shell, title, message);
		} else {
			StudioUIPlugin.log(e);
			if (e.getMessage() != null && e.getMessage().length() > 0) {
				displayMessageDialog(e.getMessage(), shell, title, message);
			} else {
				displayMessageDialog(target.getMessage(), shell, title, message);
			}
		}
	}

	//---- Helper methods -----------------------------------------------------------------------

	private void displayMessageDialog(String exceptionMessage, Shell shell, String title, String message) {
		StringWriter msg= new StringWriter();
		if (message != null) {
			msg.write(message);
			msg.write("\n\n"); //$NON-NLS-1$
		}
		if (exceptionMessage == null || exceptionMessage.length() == 0)
			msg.write("See error log for more details.");
		else
			msg.write(exceptionMessage);
		MessageDialog.openError(shell, title, msg.toString());
	}
	
}
