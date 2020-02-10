package com.tibco.cep.bpmn.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.services.IEvaluationService;
import org.eclipse.ui.texteditor.MarkerAnnotation;
import org.osgi.framework.BundleContext;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupItemType;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.editor.GraphDocumentProvider;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.palette.BpmnPaletteEntry;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.refactoring.JavaSourceChangeListener;
import com.tibco.cep.studio.ui.palette.PaletteEntryUI.Tool;
import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graphicaldrawing.builder.TSObjectBuilder;

/**
 * The activator class controls the plug-in life cycle
 */
public class BpmnUIPlugin extends AbstractUIPlugin implements IElementChangedListener {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.bpmn.ui"; //$NON-NLS-1$

	// The shared instance
	private static BpmnUIPlugin plugin;
	
	public static boolean DEBUG = false;
	
	public GraphDocumentProvider fDocumentProvider = null;
	
	@Override
	public void elementChanged(ElementChangedEvent event) {
		IJavaElementDelta delta = event.getDelta();
		JavaSourceChangeListener.traverseAndPrint(delta);
		
	}
	// static preferences
	/*public static final String PREF_SHOW_TASK_ICONS = BpmnUIPlugin.getUniqueIdentifier() + ".showTaskIcons"; //$NON-NLS-1$
	public static final String PREF_EXPAND_SUBPROCESS = BpmnUIPlugin.getUniqueIdentifier() + ".expandSubProcess"; //$NON-NLS-1$
	public static final String PREF_DISPLAY_FULL_NAMES = BpmnUIPlugin.getUniqueIdentifier() + ".useLabels"; //$NON-NLS-1$
	public static final String PREF_ALIGN_TO_LEFT_TRIGGERING_NODES = BpmnUIPlugin.getUniqueIdentifier() + ".leftAlign"; //$NON-NLS-1$
	public static final String BPMN_ACTIVITY_ID = "com.tibco.cep.bpmn.activities"; //$NON-NLS-1$
	*/
	/**
	 * The constructor
	 */
	public BpmnUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		JavaCore.addElementChangedListener(this, ElementChangedEvent.POST_CHANGE);
		Job.getJobManager().resume();
		// using capabilities to enable/disable bpmn functionality
		// see preferences/Capabilities/TIBCO BusinessEvents Process Model
	}	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static BpmnUIPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public Image getImage(String path) {
		return (getImageDescriptor(path)).createImage();
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
	 * Returns the standard display to be used. The method first checks, if
	 * the thread calling this method has an associated display. If so, this
	 * display is returned. Otherwise the method returns the default display.
	 */
	public static Display getStandardDisplay() {
		Display display;
		display= Display.getCurrent();
		if (display == null)
			display= Display.getDefault();
		return display;		
	}
	
	/**
	 * Returns the currently active workbench window shell or <code>null</code>
	 * if none.
	 * 
	 * @return the currently active workbench window shell or <code>null</code>
	 */
	public static Shell getActiveWorkbenchShell() {
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
	 * Return a <code>java.io.File</code> object that corresponds to the specified
	 * <code>IPath</code> in the plugin directory.
	 */
	public static File getFileInPlugin(IPath path) {
		try {
			URL installURL =
				new URL(getDefault().getBundle().getEntry("/"), path.toString()); //$NON-NLS-1$
			URL localURL = FileLocator.toFileURL(installURL);
			return new File(localURL.getFile());
		} catch (IOException ioe) {
			BpmnUIPlugin.log(ioe);
			return null;
		}
	}
	/****************************************************** DEBUG ******************************/
	/**
	 * @param className
	 * @param message
	 */
	public static void debug(String className, String message) {
		debug(MessageFormat.format("[{0}] {1}", className,message)); //$NON-NLS-1$
		
	}
	
	/**
	 * If the debug flag is set the specified message is printed to the console
	 * 
	 * @param message
	 */
	public static void debug(String message) {
		if(getDefault()!= null && getDefault().isDebugging()) { // run eclipse with -debug option
			System.out.println(MessageFormat.format("[{0}] {1}",PLUGIN_ID,message)); //$NON-NLS-1$
		}
	}
	
	/**
	 * @param shell
	 * @param title
	 * @param message
	 */
	public static void errorDialog(final Shell shell, final String title, final String message){
		invokeOnDisplayThread(new Runnable(){
			public void run() {
				MessageDialog.openError(shell, title, message);
			}
		}, false);
	}
	
	/**
	 * Utility method for invoking anything on Eclipse UI Thread
	 */
	public static void invokeOnDisplayThread(final Runnable runnable, boolean syncExec) {
		Display display = PlatformUI.getWorkbench().getDisplay();
		if(display == null){
			display = Display.getDefault();
		}
		if(display != null && !display.isDisposed()) {
			if (display.getThread() != Thread.currentThread()) {
				if (syncExec) {
					display.syncExec(runnable);
				} else {
					display.asyncExec(runnable);
				}
			} else	{
				runnable.run();
			}
		}
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
			status= new Status(IStatus.ERROR, getUniqueIdentifier(), -1, "Error within BPMN UI: ", t); //$NON-NLS-1$
			log(status);
		}
		ErrorDialog.openError(shell, title, message, status);
	}
	
	public static void log(String msg) {
		log(msg, null);
	}

	public static void log(String msg, Exception e) {
		if (getDefault() != null)
			getDefault().getLog().log(
					new Status(Status.INFO, PLUGIN_ID, Status.OK, msg, e));
	}
	
	/**
	 * Logs the specified status with this plug-in's log.
	 * 
	 * @param status status to log
	 */
	public static void log(IStatus status) {
		if (getDefault() != null)
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


	public static String getUniqueIdentifier() {
		return PLUGIN_ID;
	}
	
	/**-----------------------------------------
	 * Palette related 
	 *-----------------------------------------
	 */

//	@SuppressWarnings("rawtypes")
//	public static Dimension getMaximumSizeFor(Class modelClass) {
//		return IFigure.MAX_DIMENSION;
//	}
//
//	@SuppressWarnings("rawtypes")
//	public static Dimension getMinimumSizeFor(Class modelClass) {
//		return IFigure.MIN_DIMENSION;
//	}

	/**
	 * @param project
	 * @param root
	 * @return
	 */
//	@SuppressWarnings({"rawtypes" })
//	static private List createCategories(IProject project, PaletteRoot root) {
//		List categories = new ArrayList();
//		try {
//			createProcessDrawer(project, categories, root.getSite());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return categories;
//	}
	
	/**
	 * @param project
	 * @param site
	 * @return
	 */
//	public static PaletteRoot createPalette(IProject project, IEditorSite site) {
//		PaletteRoot logicPalette = new PaletteRoot();
//		logicPalette.setSite(site);
//		logicPalette.addAll(createCategories(project, logicPalette));
//		return logicPalette;
//	}


	/**
	 * @param project
	 * @param categories
	 * @param site
	 * @throws Exception
	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	static private void createProcessDrawer(IProject project, List categories, IEditorSite site) throws Exception {
//		BpmnPaletteEntry graphPaletteEntry = new BpmnPaletteEntry();
//		BpmnPaletteModel model = PaletteLoader.load(project);
//		List<BpmnPaletteGroup> toolSets = model.getBpmnPaletteGroups(false);
//		for(BpmnPaletteGroup toolSet:toolSets) {
//			List<BpmnPaletteGroupItem> items = toolSet.getPaletteItems(false);
//			
//			String icon = toolSet.getIcon();
//			ImageDescriptor imd = BpmnImages.getInstance().getImageDescriptor(icon);
//			
//			PaletteDrawer drawer = new PaletteDrawer(
//					toolSet.getTitle(),
//					imd);//$NON-NLS-1$
//			createBPMNPaletteGroupItems(drawer, graphPaletteEntry, items, site);
//			categories.add(drawer);
//		}
//	}
	
	/**
	 * @param drawer
	 * @param graphPaletteEntry
	 * @param items
	 * @param site
	 */
	@SuppressWarnings({ "unused" })
//	static private void createBPMNPaletteGroupItems(PaletteDrawer drawer, 
//			                                        BpmnPaletteEntry graphPaletteEntry, 
//			                                        List<BpmnPaletteGroupItem> items, IEditorSite site) {
//		List entries = new ArrayList();
//		for (BpmnPaletteGroupItem item : items) {
//			String icon = item.getIcon();
//			ImageDescriptor imd = BpmnImages.getInstance().getImageDescriptor(icon);
//			CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
//					item.getTitle(),
//					item.getTooltip(),
//					null,
//					imd, //$NON-NLS-1$
//					imd//$NON-NLS-1$
//			);
//			createPaletteEntry(item, graphPaletteEntry, site);
//			combined.setTemplate(item);
//			entries.add(combined);
//
//			/**
//			 * Sample Code Sub Tool definition Group
//			 * UnComment the following code snippet to implement according to the requirement
//			 * It would be better, have a separate function call 
//			 */
////			PaletteStack stack = new PaletteStack("label","desc",	null);
////			combined = new CombinedTemplateCreationEntry(
////					"sublabel1",
////					"sublabel1_desc",
////					null, 
////					imd,//$NON-NLS-1$
////					imd//$NON-NLS-1$
////			);
////			stack.add(combined);
////			combined = new CombinedTemplateCreationEntry(
////					"sublabel2",
////					"sublabel2_desc",
////					null, 
////					imd,//$NON-NLS-1$
////					imd//$NON-NLS-1$
////			);
////			stack.add(combined);
////			entries.add(stack);
//		}
//		drawer.addAll(entries);
//	}
	
	/**
	 * @param item
	 * @param paletteUI
	 * @param site
	 */
	static private void createPaletteEntry(BpmnPaletteGroupItem item,  BpmnPaletteEntry paletteUI, IEditorSite site) {
		BpmnCommonPaletteGroupItemType itemType = item.getItemType();
		switch(itemType.getType()) {
		case BpmnCommonPaletteGroupItemType.EMF_TYPE:
			BpmnCommonPaletteGroupEmfItemType emfType = (BpmnCommonPaletteGroupEmfItemType)itemType;
			{
				ExpandedName classSpec = emfType.getEmfType();
				ExpandedName extClassSpec = emfType.getExtendedType();
				BpmnLayoutManager layoutManager = paletteUI.getLayoutManager(site);
				BpmnGraphUIFactory uiFactory = BpmnGraphUIFactory.getInstance(layoutManager);
				EClass modelType = BpmnMetaModel.getInstance().getEClass(classSpec);
				TSObjectBuilder graphObjFactory;
				if(modelType == null)
					break;
				if(BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(modelType)||
						BpmnModelClass.ASSOCIATION.isSuperTypeOf(modelType)) {
					 graphObjFactory = uiFactory.getEdgeUIFactory(
							item.getTitle(),
							classSpec,
							extClassSpec);

				} else {
					if (extClassSpec == null)
						graphObjFactory = uiFactory.getNodeUIFactory(
								item.getTitle(),
								item.getAttachedResource(),
								item.getId(), 
								classSpec);
					else
						graphObjFactory = uiFactory.getNodeUIFactory(
								item.getTitle(),
								item.getAttachedResource(),
								item.getId(), 
								classSpec, 
								extClassSpec);
				}
				PaletteEntry paletteEntry = paletteUI.createPaletteEntry(
						site.getPage(),
						null, 
						null,
						item.getTitle(),
						item.getTooltip(),
						item.getIcon(),
						graphObjFactory, 
						Tool.NONE, 
						!item.isInternal());
				item.setEntry(paletteEntry);
			}
			break;
		case BpmnCommonPaletteGroupItemType.JAVA_TYPE:
			break;
		case BpmnCommonPaletteGroupItemType.MODEL_TYPE:
			break;
		}
	}

	public static MarkerAnnotation getModelPresentation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public synchronized GraphDocumentProvider getDocumentProvider() {
		if (fDocumentProvider == null) {
			fDocumentProvider = new GraphDocumentProvider();
		}
		return fDocumentProvider;
	}

	/**
	 * Creates a new {@link IEvaluationContext} initialized with the current platform state if the 
	 * {@link IEvaluationService} can be acquired, otherwise the new context is created with no 
	 * parent context
	 * 
	 * @param defaultvar the default variable for the new context
	 * @return a new {@link IEvaluationContext}
	 */
	
	public static IEvaluationContext createEvaluationContext(Object defaultvar) {
		IEvaluationContext parent = null;
		IEvaluationService esrvc = (IEvaluationService)PlatformUI.getWorkbench().getService(IEvaluationService.class);
		if (esrvc != null) {
			parent = esrvc.getCurrentState();
		}
		return new EvaluationContext(parent, defaultvar);
	}
}