package com.tibco.cep.studio.debug.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugTarget;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tibco.cep.studio.debug.core.launch.ApplicationRuntime;
import com.tibco.cep.studio.debug.core.launch.LibraryInfo;
import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.IRuleDebugTarget;
import com.tibco.cep.studio.debug.core.model.IRuleDebugThread;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;

/**
 * The activator class controls the plug-in life cycle
 */
public class StudioDebugCorePlugin extends Plugin implements IPropertyChangeListener {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.debug.core";
	
	public static final int ERROR = 130;
	public static final int INTERNAL_ERROR = 140;
	
	/**
	 * Breakpoint listener list.
	 */
	private ListenerList fBreakpointListeners = null;
	
	/**
	 * Breakpoint notification types
	 */
	private static final int ADDING = 1;
	private static final int INSTALLED = 2;
	private static final int REMOVED = 3;
	private static final int COMPILATION_ERRORS = 4;
	private static final int RUNTIME_EXCEPTION = 5;
	
	/**
	 * integer preference controlling if we should, by default, suspend the VM instead of the thread
	 * 
	 */
	public static final String PREF_DEFAULT_BREAKPOINT_SUSPEND_POLICY = StudioDebugCorePlugin.getUniqueIdentifier() + ".default_breakpoint_suspend_policy"; //$NON-NLS-1$
	/**
	 * Preference key for launch/connect timeout. VM Runners should honor this timeout
	 * value when attempting to launch and connect to a debuggable VM. The value is
	 * an int, indicating a number of milliseconds.
	 * 
	 */
	public static final String PREF_CONNECT_TIMEOUT = StudioDebugCorePlugin.getUniqueIdentifier() + ".PREF_CONNECT_TIMEOUT"; //$NON-NLS-1$
	/**
	 * integer preference controlling which default suspend option to set on new watchpoints
	 * 
	 */
	public static final String PREF_DEFAULT_WATCHPOINT_SUSPEND_POLICY = StudioDebugCorePlugin.getUniqueIdentifier() + "default_watchpoint_suspend_policy"; //$NON-NLS-1$
	
	/**
	 * Boolean preference controlling if references should be displayed as variables in the variables and expressions view
	 * 
	 */
	public static final String PREF_SHOW_REFERENCES_IN_VAR_VIEW = StudioDebugCorePlugin.getUniqueIdentifier() + ".show_references_in_var_view"; //$NON-NLS-1$
	
	/**
	 * Integer preference determining the maximum number of references that should be returned to the user when displaying reference information
	 * 
	 */
	public static final String PREF_ALL_REFERENCES_MAX_COUNT = StudioDebugCorePlugin.getUniqueIdentifier() + "._all_references_max_count"; //$NON-NLS-1$
	
	/**
	 * Integer preference determining the maximum number of instances that should be returned to the user when displaying instance information
	 * 
	 */
	public static final String PREF_ALL_INSTANCES_MAX_COUNT = StudioDebugCorePlugin.getUniqueIdentifier() + ".all_instances_max_count"; //$NON-NLS-1$

	// The shared instance
	private static StudioDebugCorePlugin plugin;
	public static boolean DEBUG = false;
	/**
	 * Mapping of top-level VM installation directories to library info for that
	 * VM.
	 */
	@SuppressWarnings("rawtypes")
	private static Map fgLibraryInfoMap = null;
	private static DocumentBuilder fgXMLParser;

	/**
	 * The constructor
	 */
	public StudioDebugCorePlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		ResourcesPlugin.getWorkspace().addSaveParticipant(PLUGIN_ID, new ISaveParticipant() {
			public void doneSaving(ISaveContext c) {}
			public void prepareToSave(ISaveContext c)	throws CoreException {}
			public void rollback(ISaveContext c) {}
			public void saving(ISaveContext c) throws CoreException {
//				savePluginPreferences();
				try {
					InstanceScope.INSTANCE.getNode(PLUGIN_ID).flush();
				} catch (BackingStoreException e) {
					e.printStackTrace();
				}
			}
		});
		fBreakpointListeners = new ListenerList();
		
		//The Following preference Moved to ApplicationDelegate.launch
//		getPluginPreferences().setDefault(ApplicationRuntime.PREF_CONNECT_TIMEOUT, ApplicationRuntime.DEF_CONNECT_TIMEOUT);
		
		getPluginPreferences().addPropertyChangeListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		try {
			ILaunchManager launchManager = DebugPlugin.getDefault()
					.getLaunchManager();
			IDebugTarget[] targets = launchManager.getDebugTargets();
			for (int i = 0; i < targets.length; i++) {
				IDebugTarget target = targets[i];
				if (target instanceof IRuleRunTarget) {
					((IRuleRunTarget) target).shutdown();
				}
			}
			ResourcesPlugin.getWorkspace().removeSaveParticipant(this);
		} finally {
			plugin = null;
			super.stop(context);
		}
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static StudioDebugCorePlugin getDefault() {
		return plugin;
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
			System.out.println(MessageFormat.format("[{0}] [{1}] {2}",PLUGIN_ID, Thread.currentThread().getName(), message));
		}
	}
	
	public static void log(String msg) {
		log(msg, null);
	}

	public static void log(String msg, Exception e) {
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


	public static String getUniqueIdentifier() {
		return PLUGIN_ID;
	}
	
	
	/**
	 * Returns the library info that corresponds to the specified JRE install
	 * path, or <code>null</code> if none.
	 * 
	 * @return the library info that corresponds to the specified JRE install
	 * path, or <code>null</code> if none
	 */
	public static LibraryInfo getLibraryInfo(String javaInstallPath) {
		if (fgLibraryInfoMap == null) {
			restoreLibraryInfo();
		}
		return (LibraryInfo) fgLibraryInfoMap.get(javaInstallPath);
	}
	
	/**
	 * Sets the library info that corresponds to the specified JRE install
	 * path.
	 * 
	 * @param javaInstallPath home location for a JRE
	 * @param info the library information, or <code>null</code> to remove
	 */
	@SuppressWarnings("unchecked")
	public static void setLibraryInfo(String javaInstallPath, LibraryInfo info) {
		if (fgLibraryInfoMap == null) {
			restoreLibraryInfo();
		}
		if (info == null) {
			fgLibraryInfoMap.remove(javaInstallPath);
		} else {
			fgLibraryInfoMap.put(javaInstallPath, info);
		}
		saveLibraryInfo();
	}
	
	/**
	 * Saves the library info in a local workspace state location
	 */
	private static void saveLibraryInfo() {
		OutputStream stream= null;
		try {
			String xml = getLibraryInfoAsXML();
			IPath libPath = getDefault().getStateLocation();
			libPath = libPath.append("libraryInfos.xml"); //$NON-NLS-1$
			File file = libPath.toFile();
			if (!file.exists()) {
				file.createNewFile();
			}
			stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(xml.getBytes("UTF8")); //$NON-NLS-1$
		} catch (IOException e) {
			log(e);
		}  catch (CoreException e) {
			log(e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	/**
	 * Return the VM definitions contained in this object as a String of XML.  The String
	 * is suitable for storing in the workbench preferences.
	 * <p>
	 * The resulting XML is compatible with the static method <code>parseXMLIntoContainer</code>.
	 * </p>
	 * @return String the results of flattening this object into XML
	 * @throws IOException if this method fails. Reasons include:<ul>
	 * <li>serialization of the XML document failed</li>
	 * </ul>
	 */
	@SuppressWarnings("rawtypes")
	private static String getLibraryInfoAsXML() throws CoreException {
		
		Document doc = DebugPlugin.newDocument();
		Element config = doc.createElement("libraryInfos");    //$NON-NLS-1$
		doc.appendChild(config);
						
		// Create a node for each info in the table
		Iterator locations = fgLibraryInfoMap.keySet().iterator();
		while (locations.hasNext()) {
			String home = (String)locations.next();
			LibraryInfo info = (LibraryInfo) fgLibraryInfoMap.get(home);
			Element locationElemnet = infoAsElement(doc, info);
			locationElemnet.setAttribute("home", home); //$NON-NLS-1$
			config.appendChild(locationElemnet);
		}
		
		// Serialize the Document and return the resulting String
		return DebugPlugin.serializeDocument(doc);
	}
	
	/**
	 * Creates an XML element for the given info.
	 * 
	 * @param doc
	 * @param info
	 * @return Element
	 */
	private static Element infoAsElement(Document doc, LibraryInfo info) {
		Element libraryElement = doc.createElement("libraryInfo"); //$NON-NLS-1$
		libraryElement.setAttribute("version", info.getVersion()); //$NON-NLS-1$
		appendPathElements(doc, "bootpath", libraryElement, info.getBootpath()); //$NON-NLS-1$
		appendPathElements(doc, "extensionDirs", libraryElement, info.getExtensionDirs()); //$NON-NLS-1$
		appendPathElements(doc, "endorsedDirs", libraryElement, info.getEndorsedDirs()); //$NON-NLS-1$
		return libraryElement;
	}
	
	/**
	 * Appends path elements to the given library element, rooted by an
	 * element of the given type.
	 * 
	 * @param doc
	 * @param elementType
	 * @param libraryElement
	 * @param paths
	 */
	private static void appendPathElements(Document doc, String elementType, Element libraryElement, String[] paths) {
		if (paths.length > 0) {
			Element child = doc.createElement(elementType);
			libraryElement.appendChild(child);
			for (int i = 0; i < paths.length; i++) {
				String path = paths[i];
				Element entry = doc.createElement("entry"); //$NON-NLS-1$
				child.appendChild(entry);
				entry.setAttribute("path", path); //$NON-NLS-1$
			}
		}
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
			return null;
		}
	}
	
	/**
	 * Restores library information for VMs
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void restoreLibraryInfo() {
		fgLibraryInfoMap = new HashMap(10);
		IPath libPath = getDefault().getStateLocation();
		libPath = libPath.append("libraryInfos.xml"); //$NON-NLS-1$
		File file = libPath.toFile();
		if (file.exists()) {
			try {
				InputStream stream = new BufferedInputStream(new FileInputStream(file));
				DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				parser.setErrorHandler(new DefaultHandler());
				Element root = parser.parse(new InputSource(stream)).getDocumentElement();
				if(!root.getNodeName().equals("libraryInfos")) { //$NON-NLS-1$
					return;
				}
				
				NodeList list = root.getChildNodes();
				int length = list.getLength();
				for (int i = 0; i < length; ++i) {
					Node node = list.item(i);
					short type = node.getNodeType();
					if (type == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						String nodeName = element.getNodeName();
						if (nodeName.equalsIgnoreCase("libraryInfo")) { //$NON-NLS-1$
							String version = element.getAttribute("version"); //$NON-NLS-1$
							String location = element.getAttribute("home"); //$NON-NLS-1$
							String[] bootpath = getPathsFromXML(element, "bootpath"); //$NON-NLS-1$
							String[] extDirs = getPathsFromXML(element, "extensionDirs"); //$NON-NLS-1$
							String[] endDirs = getPathsFromXML(element, "endorsedDirs"); //$NON-NLS-1$
							if (location != null) {
								LibraryInfo info = new LibraryInfo(version, bootpath, extDirs, endDirs);
								fgLibraryInfoMap.put(location, info);
							}
						}
					}
				}
			} catch (IOException e) {
				log(e);
			} catch (ParserConfigurationException e) {
				log(e);
			} catch (SAXException e) {
				log(e);
			}
		}
	}
	
	/**
	 * Returns paths stored in XML
	 * @param lib
	 * @param pathType
	 * @return paths stored in XML
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static String[] getPathsFromXML(Element lib, String pathType) {
		List paths = new ArrayList();
		NodeList list = lib.getChildNodes();
		int length = list.getLength();
		for (int i = 0; i < length; ++i) {
			Node node = list.item(i);
			short type = node.getNodeType();
			if (type == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String nodeName = element.getNodeName();
				if (nodeName.equalsIgnoreCase(pathType)) {
					NodeList entries = element.getChildNodes();
					int numEntries = entries.getLength();
					for (int j = 0; j < numEntries; j++) {
						Node n = entries.item(j);
						short t = n.getNodeType();
						if (t == Node.ELEMENT_NODE) {
							Element entryElement = (Element)n;
							String name = entryElement.getNodeName();
							if (name.equals("entry")) { //$NON-NLS-1$
								String path = entryElement.getAttribute("path"); //$NON-NLS-1$
								if (path != null && path.length() > 0) {
									paths.add(path);
								}
							}
						}
					}
				}
			}
		}
		return (String[])paths.toArray(new String[paths.size()]);
	}

	/**
	 * Returns a shared XML parser.
	 * 
	 * @return an XML parser
	 * @throws CoreException if unable to create a parser
	 * @since 3.0
	 */
	public static DocumentBuilder getParser() throws CoreException {
		if (fgXMLParser == null) {
			try {
				fgXMLParser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				fgXMLParser.setErrorHandler(new DefaultHandler());
			} catch (ParserConfigurationException e) {
				abort("Unable to create XML parser.", e);
			} catch (FactoryConfigurationError e) {
				abort("Unable to create XML parser.", e);
			}
		}
		return fgXMLParser;
	}
	
	/**
	 * Throws an exception with the given message and underlying exception.
	 * 
	 * @param message error message
	 * @param exception underlying exception or <code>null</code> if none
	 * @throws CoreException
	 */
	protected static void abort(String message, Throwable exception) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, StudioDebugCorePlugin.getUniqueIdentifier(), 0, message, exception);
		throw new CoreException(status);
	}
	
	
	/**
	 * Notifies listeners that the given breakpoint is about to be
	 * added.
	 * 
	 * @param target Java debug target
	 * @param breakpoint Java breakpoint
	 */
	public void fireBreakpointAdding(IRuleRunTarget target, IRuleBreakpoint breakpoint) {
		getBreakpointNotifier().notify(target, breakpoint, ADDING, null, null);
	}
	
	/**
	 * Notifies listeners that the given breakpoint has been installed.
	 * 
	 * @param target Java debug target
	 * @param breakpoint Java breakpoint
	 */
	public void fireBreakpointInstalled(IRuleRunTarget target, IRuleBreakpoint breakpoint) {
		getBreakpointNotifier().notify(target, breakpoint, INSTALLED, null, null);
	}	
	
	/**
	 * Notifies listeners that the given breakpoint has been removed.
	 * 
	 * @param target Java debug target
	 * @param breakpoint Java breakpoint
	 */
	public void fireBreakpointRemoved(IRuleRunTarget target, IRuleBreakpoint breakpoint) {
		getBreakpointNotifier().notify(target, breakpoint, REMOVED, null, null);
	}
	
	/**
	 * Notifies listeners that the given breakpoint has been hit.
	 * Returns whether the thread should suspend.
	 * 
	 * @param target Java debug target
	 * @param breakpoint Java breakpoint
	 */
	public boolean fireBreakpointHit(IRuleDebugThread thread, IRuleBreakpoint breakpoint) {
		return getHitNotifier().notifyHit(thread, breakpoint);
	}
	
	/**
	 * Notifies listeners that the given breakpoint is about to be installed
	 * in the given type. Returns whether the breakpoint should be
	 * installed.
	 * 
	 * @param target Java debug target
	 * @param breakpoint Java breakpoint
	 * @param type the type the breakpoint is about to be installed in
	 * @return whether the breakpoint should be installed
	 */
	public boolean fireInstalling(IRuleDebugTarget target, IRuleBreakpoint breakpoint, String type) {
		return getInstallingNotifier().notifyInstalling(target, breakpoint, type);
	}
	
	
	private BreakpointNotifier getBreakpointNotifier() {
		return new BreakpointNotifier();
	}

	class BreakpointNotifier implements ISafeRunnable {
		
		private IRuleRunTarget fTarget;
		private IRuleBreakpoint fBreakpoint;
		private int fKind;
		//private String[] fErrors;
		private DebugException fException;
		private IRuleBreakpointListener fListener;
		
		/**
		 * @see org.eclipse.core.runtime.ISafeRunnable#handleException(java.lang.Throwable)
		 */
		public void handleException(Throwable exception) {
		}

		/**
		 * @see org.eclipse.core.runtime.ISafeRunnable#run()
		 */
		public void run() throws Exception {
			switch (fKind) {
				case ADDING:
					fListener.addingBreakpoint(fTarget, fBreakpoint);
					break;
				case INSTALLED:
					fListener.breakpointInstalled(fTarget, fBreakpoint);
					break;
				case REMOVED:
					fListener.breakpointRemoved(fTarget, fBreakpoint);
					break;		
				case COMPILATION_ERRORS:
//					fListener.breakpointHasCompilationErrors((IRuleBreakpoint)fBreakpoint, fErrors);
					break;
				case RUNTIME_EXCEPTION:
					fListener.breakpointHasRuntimeException((IRuleBreakpoint)fBreakpoint, fException);
					break;	
			}			
		}

		/**
		 * Notifies listeners of the given addition, install, or
		 * remove.
		 * 
		 * @param target debug target
		 * @param breakpoint the associated breakpoint
		 * @param kind one of ADDED, REMOVED, INSTALLED
		 * @param errors associated errors, or <code>null</code> if none
		 * @param exception associated exception, or <code>null</code> if none
		 */
		public void notify(IRuleRunTarget target, IRuleBreakpoint breakpoint, int kind, String[] errors, DebugException exception) {
			fTarget = target;
			fBreakpoint = breakpoint;
			fKind = kind;
			//fErrors = errors;
			fException = exception;
			Object[] listeners = fBreakpointListeners.getListeners();
			for (int i = 0; i < listeners.length; i++) {
				fListener = (IRuleBreakpointListener)listeners[i];
                SafeRunner.run(this);
			}
			fTarget = null;
			fBreakpoint = null;
			//fErrors = null;
			fException = null;
			fListener = null;
		}
	}
	
	private InstallingNotifier getInstallingNotifier() {
		return new InstallingNotifier();
	}
		
	class InstallingNotifier implements ISafeRunnable {
		
		private IRuleDebugTarget fTarget;
		private IRuleBreakpoint fBreakpoint;
		private String fType;
		private IRuleBreakpointListener fListener;
		private int fInstall;
		
		/**
		 * @see org.eclipse.core.runtime.ISafeRunnable#handleException(java.lang.Throwable)
		 */
		public void handleException(Throwable exception) {
		}

		/**
		 * @see org.eclipse.core.runtime.ISafeRunnable#run()
		 */
		public void run() throws Exception {
			fInstall = fInstall | fListener.installingBreakpoint(fTarget, fBreakpoint, fType);		
		}
		
		private void dispose() {
			fTarget = null;
			fBreakpoint = null;
			fType = null;
			fListener = null;
		}

		/**
		 * Notifies listeners that the given breakpoint is about to be installed
		 * in the given type. Returns whether the breakpoint should be
		 * installed.
		 * 
		 * @param target Java debug target
		 * @param breakpoint Java breakpoint
		 * @param type the type the breakpoint is about to be installed in
		 * @return whether the breakpoint should be installed
		 */
		public boolean notifyInstalling(IRuleDebugTarget target, IRuleBreakpoint breakpoint, String type) {
			fTarget = target;
			fBreakpoint = breakpoint;
			fType = type;
			fInstall = IRuleBreakpointListener.DONT_CARE;
			Object[] listeners = fBreakpointListeners.getListeners();
			for (int i = 0; i < listeners.length; i++) {
				fListener = (IRuleBreakpointListener)listeners[i];
                SafeRunner.run(this);
			}
			dispose();
			// install if any listener voted to install, or if no one voted to not install
			return (fInstall & IRuleBreakpointListener.INSTALL) > 0 ||
				(fInstall & IRuleBreakpointListener.DONT_INSTALL) == 0;
		}
	}
	
	private HitNotifier getHitNotifier() {
		return new HitNotifier();
	}
		
	class HitNotifier implements ISafeRunnable {
		
		private IRuleDebugThread fThread;
		private IRuleBreakpoint fBreakpoint;
		private IRuleBreakpointListener fListener;
		private int fSuspend;
		
		/**
		 * @see org.eclipse.core.runtime.ISafeRunnable#handleException(java.lang.Throwable)
		 */
		public void handleException(Throwable exception) {
		}

		/**
		 * @see org.eclipse.core.runtime.ISafeRunnable#run()
		 */
		public void run() throws Exception {
			fSuspend = fSuspend | fListener.breakpointHit(fThread, fBreakpoint);
		}

		/**
		 * Notifies listeners that the given breakpoint has been hit.
		 * Returns whether the thread should suspend.
		 * 
		 * @param thread thread in which the breakpoint was hit
		 * @param breakpoint Java breakpoint
		 * @return whether the thread should suspend
		 */
		public boolean notifyHit(IRuleDebugThread thread, IRuleBreakpoint breakpoint) {
			fThread = thread;
			fBreakpoint = breakpoint;
			Object[] listeners = fBreakpointListeners.getListeners();
			fSuspend = IRuleBreakpointListener.DONT_CARE;
			for (int i = 0; i < listeners.length; i++) {
				fListener = (IRuleBreakpointListener)listeners[i];
                SafeRunner.run(this);
			}
			fThread = null;
			fBreakpoint = null;
			fListener = null;
			// Suspend if any listener voted to suspend or no one voted "don't suspend"
			return (fSuspend & IRuleBreakpointListener.SUSPEND) > 0 ||
					(fSuspend & IRuleBreakpointListener.DONT_SUSPEND) == 0;
		}
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(RuleDebugModel.PREF_REQUEST_TIMEOUT)) {
			int value = getPluginPreferences().getInt(RuleDebugModel.PREF_REQUEST_TIMEOUT);
			IDebugTarget[] targets = DebugPlugin.getDefault().getLaunchManager().getDebugTargets();
			for (int i = 0; i < targets.length; i++) {
				if (targets[i] instanceof IRuleRunTarget) {
					((IRuleRunTarget)targets[i]).setRequestTimeout(value);
				}
			}
		}
		
	}
	
	
	

}
