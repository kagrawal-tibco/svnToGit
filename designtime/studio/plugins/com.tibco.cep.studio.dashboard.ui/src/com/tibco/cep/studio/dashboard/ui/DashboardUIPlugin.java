package com.tibco.cep.studio.dashboard.ui;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.cep.studio.dashboard.ui.forms.DefaultExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.images.ContributableImageRegistry;
import com.tibco.cep.studio.dashboard.ui.utils.ImagesLoader;

/**
 * The activator class controls the plug-in life cycle
 */
public class DashboardUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.dashboard.ui";

	// The shared instance
	private static DashboardUIPlugin instance;

	private FormColors formColors;

	private ExceptionHandler exceptionHandler;
	
	/**
	 * The constructor
	 */
	public DashboardUIPlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		instance = this;
		exceptionHandler = new DefaultExceptionHandler(PLUGIN_ID);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		super.stop(context);
	}
	
	@Override
	protected ImageRegistry createImageRegistry() {
    	//If we are in the UI Thread use that
    	if(Display.getCurrent() != null) {
			return new ContributableImageRegistry(Display.getCurrent());
		}
    	
    	if(PlatformUI.isWorkbenchRunning()) {
			return new ContributableImageRegistry(PlatformUI.getWorkbench().getDisplay());
		}
    	
    	//Invalid thread access if it is not the UI Thread 
    	//and the workbench is not created.
    	throw new SWTError(SWT.ERROR_THREAD_INVALID_ACCESS);		
		
	}
	
	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		ImagesLoader imagesLoader = new ImagesLoader(getBundle(),reg);
		imagesLoader.load();
	}	

	public FormColors getFormColors(Display display) {
        if (formColors == null) {
            formColors = new FormColors(display);
            formColors.markShared();
        }
        return formColors;
    }

	public static DashboardUIPlugin getInstance() {
		return instance;
	}
	
	public final ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

}