package com.tibco.cep.webstudio.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.I18nUtil;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.authentication.LoginPage;
import com.tibco.cep.webstudio.client.groups.MyGroups;
import com.tibco.cep.webstudio.client.i18n.CustomizedSmartgwtMessages;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.I18nService;
import com.tibco.cep.webstudio.client.i18n.I18nServiceAsync;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.model.AclData;
import com.tibco.cep.webstudio.client.model.ApplicationPreferences;
import com.tibco.cep.webstudio.client.model.NotificationDetails;
import com.tibco.cep.webstudio.client.model.User;
import com.tibco.cep.webstudio.client.model.UserData;
import com.tibco.cep.webstudio.client.model.UserPreferences;
import com.tibco.cep.webstudio.client.portal.WebStudioPortalPage;
import com.tibco.cep.webstudio.client.preferences.WebStudioPreferencesPage;
import com.tibco.cep.webstudio.client.util.WebStudioShortcutUtil;
import com.tibco.cep.webstudio.client.widgets.WebStudioEditorPanel;
import com.tibco.cep.webstudio.client.widgets.WebStudioHeader;
import com.tibco.cep.webstudio.client.widgets.WebStudioMenubar;
import com.tibco.cep.webstudio.client.widgets.WebStudioStatusBar;
import com.tibco.cep.webstudio.client.widgets.WebStudioToolbar;

/**
 * This Web Studio Entry point classes define <code>onModuleLoad()</code>.
 */
public class WebStudio implements EntryPoint{

	private VLayout mainLayout, bodyLayout;
	private WebStudioWorkspacePage workSpacePage;
	private WebStudioPortalPage dashboardPage;  
	private WebStudioPreferencesPage preferencesPage;

	private ApplicationPreferences applicationPreferences;
	
	private WebStudioHeader header;

	// currently stored artifacts
	private String currentProjectDir;
	
	private boolean process_debug_level = false;

	private User user;
	private static WebStudio singleton;
	private HandlerManager eventBus = new HandlerManager(null);
	private boolean rmsOptionViaContextMenu;
	private UserPreferences userPreference;
	private UserData userDataObject;
	private AclData aclObject;
	private NotificationDetails notifyObject;
	
	public NotificationDetails getNotifyObject() {
		return notifyObject;
	}

	public void setNotifyObject(NotificationDetails notifyObject) {
		this.notifyObject = notifyObject;
	}

	private static String detectedBrowser;
	
	private static WebStudioClientLogger logger = null;
	private static GlobalMessages globalMsgBundle = null;
	
	private static I18nRegistry i18nRegistry;
	
	private String apiToken;
	
	// include custom css
	interface GlobalResources extends ClientBundle {
		@NotStrict
		@Source("CustomWebStudio.css")
		CssResource css();
		
		@NotStrict
		@Source("CustomWebStudioRTL.css")
		CssResource cssRTL();
		
		@NotStrict
		@Source("DatePicker_custom.css")
		CssResource datePickerCss();
	}
	
	public static WebStudio get() {
		return singleton;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		// load the locale based i18n messages
		loadI18nRegistry();
	}

	/**
	 * Create a workspace and portal page
	 * @param user
	 */
	public void createWebStudioPages(User user) {
		hideLoading();
		
		setUser(user);
		
		mainLayout = new VLayout();
		mainLayout.setWidth100(); 
		mainLayout.setHeight100();
		
		// Create Header
		header = new WebStudioHeader();
		mainLayout.addMember(header);
		
		bodyLayout = new VLayout();
		bodyLayout.setWidth100();
		bodyLayout.setHeight100();

		bodyLayout.addMember(getPortalPage());
		getWorkspacePage().hide();
		bodyLayout.addMember(getWorkspacePage());		
		getPreferencesPage().hide();
		bodyLayout.addMember(getPreferencesPage());
		mainLayout.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				WebStudioShortcutUtil.handleKeyPressEvent(event);
			}
		});
		mainLayout.addMember(bodyLayout);		
		
		mainLayout.draw();
	}
	
	public WebStudioWorkspacePage getWorkspacePage(){
		if (workSpacePage == null){
			workSpacePage = new WebStudioWorkspacePage();
		}
		
		return workSpacePage;
	}

	/**
	 * Show Dashboard Page
	 */
	public void showDashboardPage() {
		if (bodyLayout.contains(getWorkspacePage())){
			getWorkspacePage().hide();
		}
		if (bodyLayout.contains(getPreferencesPage())){
			getPreferencesPage().hide();
		}
		getPortalPage().show();
		header.selectDashboardPage();
	}
	
	/**
	 * Show Preference Page
	 */
	public void showPreferencesPage() {
		if (bodyLayout.contains(getWorkspacePage())){
			getWorkspacePage().hide();
		}
		if (bodyLayout.contains(getPortalPage())){
			getPortalPage().hide();
		}
		getPreferencesPage().show();
		header.selectPreferencesPage();
	}
	
	/**
	 * Show Workspace Page
	 */
	public void showWorkSpacePage() {
		if (bodyLayout.contains(getPortalPage())){
			getPortalPage().hide();
		}
		if (bodyLayout.contains(getPreferencesPage())){
			getPreferencesPage().hide();
		}
		getWorkspacePage().show();
		header.selectWorkspacePage();
	}

	public String getCurrentProjectDir() {
		return currentProjectDir;
	}
	
	public void setCurrentlySelectedProject(String selectedProject){
		getWorkspacePage().getGroupContentsWindow().setSelectedProjectId(selectedProject);
	}
	
	public String getCurrentlySelectedProject(){
		return getWorkspacePage().getGroupContentsWindow().getSelectedProjectId();
	}
	
	public String getCurrentlySelectedArtifact() {
		return getWorkspacePage().getGroupContentsWindow().getSelectedArtifact();
	}

	public void setCurrentlySelectedArtifact(String currentlySelectedArtifact) {
		getWorkspacePage().getGroupContentsWindow().setSelectedArtifact(currentlySelectedArtifact);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public VLayout getMainLayout() {
		return mainLayout;
	}
	
	public WebStudioHeader getHeader(){
		return header;
	}

	/**
	 * @return {@link WebStudioToolbar}
	 */
	public WebStudioToolbar getApplicationToolBar() {
		return getWorkspacePage().getApplicationToolBar();
	}
	
	/**
	 * @return {@link WebStudioEditorPanel}
	 */
	public WebStudioEditorPanel getEditorPanel() {
		return getWorkspacePage().getEditorPanel();
	}

	public WebStudioStatusBar getStatusbar() {
		return getWorkspacePage().getStatusbar();
	}
	
	public WebStudioMenubar getApplicationMenu(){
		return getWorkspacePage().getApplicationMenu();
	}

	public WebStudioPortalPage getPortalPage() {
		if (dashboardPage == null) {
			dashboardPage = new WebStudioPortalPage(getUser());
		}
		return dashboardPage;
	}
	
	public WebStudioPreferencesPage getPreferencesPage() {
		if (preferencesPage == null) {
			preferencesPage = new WebStudioPreferencesPage(getUser());
		}
		return preferencesPage;
	}
	
	/**
	 * Load I18n registry based on the user's locale
	 */
	private void loadI18nRegistry() {
		I18nServiceAsync i18nService = GWT.create(I18nService.class);
		
		AsyncCallback<I18nRegistry> callback = new AsyncCallback<I18nRegistry>() {
			@Override
			public void onFailure(Throwable caught) {
				logger.info("Locales call failed, Exception - " + caught.getMessage());
			}
			
			@Override
			public void onSuccess(I18nRegistry result) {
				i18nRegistry = result;
				i18nRegistry.initializeResourceBundle();
				
				CustomizedSmartgwtMessages customSgwtMessages = (CustomizedSmartgwtMessages) I18nRegistry.getResourceBundle(I18nRegistry.CUSTOM_SGWT_MESSAGES);
				if (customSgwtMessages != null && customSgwtMessages.getMessages() != null) {
					I18nUtil.initMessages(customSgwtMessages);
				}
				
				loadWSModule();//Continue loading WS
			}
		};
		
		String locale = Window.Location.getParameter("locale");
		if (locale == null) {//TODO: also handle case if language pack for region is not available.
			locale = "en_US";
		}
		i18nService.getMessages(locale, callback);
	}
	
	public I18nMessages getResourceBundle(String bundleName) {
		return (I18nMessages) i18nRegistry.getResourceBundle(bundleName);
	}
	
	/**
	 * Continues loading WS module, must be called after language pack is loaded.
	 */
	private void loadWSModule() {
		if (!isSupportedBrowser()) {
			hideLoading();
			try {//Try to initialize message bundle.
				globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
			}
			catch(Exception e) {
				showUnsupportedBrowserError(null, null, null);
				return;
			}
			showUnsupportedBrowserError(
					globalMsgBundle.message_unsupportedBrowser(),
					globalMsgBundle.message_recommendedBrowser(),
					detectedBrowser != null ? globalMsgBundle.message_detectedBrowser(detectedBrowser) : null);
			return;
		}
		
		String WSImagesDir = getWebStudioImagesDir();
		if (WSImagesDir == null) {
			Page.setAppImgDir("webstudio/images/");//default images dir
		}
		else {
			WSImagesDir = WSImagesDir.endsWith("/") ? WSImagesDir : WSImagesDir + "/";
			Page.setAppImgDir(WSImagesDir);
		}
		
		logger = WebStudioClientLogger.getLogger(WebStudio.class.getName());
		singleton = this;
		
		// get rid of scroll bars, and clear out the window's built-in margin,
		// because we want to take advantage of the entire client area
		Window.enableScrolling(false);
		Window.setMargin("0px");
		
		// inject global styles
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			GWT.<GlobalResources>create(GlobalResources.class).cssRTL().ensureInjected();   
		}
		GWT.<GlobalResources>create(GlobalResources.class).css().ensureInjected();  
		GWT.<GlobalResources>create(GlobalResources.class).datePickerCss().ensureInjected();

		hideLoading();
		
		GWT.setUncaughtExceptionHandler(new WebStudioUncaughtExceptionHandler());

		globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	        @Override
	        public void execute() {
	        	logger.info("Init WebStudio OnLoadModule");
	        	new LoginPage().checkLogin();
	        }
	    });
	}
	
	public static native void hideLoading() /*-{  
		$wnd.hideLoading();
	}-*/;
	
	public static native void showLoading() /*-{  
	    $wnd.showLoading();
    }-*/;
	
	public static native boolean isLoggingEnabled() /*-{
		return $wnd.webstudio_logging_enabled;
	}-*/;
	
	public static native boolean isProcessLoggingEnabled() /*-{
	 	return $wnd.webstudio_process_logging_enabled;
	}-*/;

	public static native int getDSRequestTimeout() /*-{
		return $wnd.dataSource_request_timeout;
	}-*/;
	
	public static native String getWebStudioImagesDir() /*-{
		return $wnd.webstudio_images_dir;
	}-*/;
	
	//Browser compatibility
	public static boolean isSupportedBrowser() {
		String browserName = getBrowserName();
		String browserVersion = getBrowserVersion(browserName);
		if (browserName != null && browserVersion != null) {
			detectedBrowser = browserName + " " + browserVersion;
			BrowserCompatibility compatibility = BrowserCompatibility.getInstance(browserName);
			return compatibility.isCompatibleVersion(Float.valueOf(browserVersion));
		}
		return false;
	}
	
	/**
	 * Returns browser name. Returns null if the browser is not of the 3 supported browsers.
	 * @return
	 */
	private static String getBrowserName() {
		String userAgent = getUserAgent().toLowerCase();
		if (userAgent.matches(".*;\\s{0,1}msie\\s{0,1}\\d{1,2}\\.\\d{1,2}.*") || userAgent.matches(".*;\\s{0,1}trident/\\d.*\\srv:\\d.*")) {
			return "Internet Explorer";
		}
		else if (userAgent.matches(".*firefox/[0-9].*") && !userAgent.matches(".*seamonkey/.*")) {
			return "Firefox";
		}
		else if (userAgent.matches(".*chrome/[0-9].*") && !userAgent.matches(".*chromium/.*")) {
			return "Chrome";
		}
		return null;
	}
	
	/**
	 * Returns browser version using the passed browserName and useragent string.
	 * @param browserName
	 * @return
	 */
	private static String getBrowserVersion(String browserName) {
		if ("Internet Explorer".equalsIgnoreCase(browserName)) {
			String userAgent = getUserAgent().toLowerCase();
			RegExp regexp = RegExp.compile(userAgent.contains("msie") ? "msie\\s{0,}(.+?)[,;\\s]" : "rv:(.+?)[;)]", "i");
			MatchResult matchResult = regexp.exec(userAgent);
			if (matchResult == null || matchResult.getGroupCount() < 2) {
				return null;
			}
			else {
				return matchResult.getGroup(1);
			}
		}
		else if ("Firefox".equalsIgnoreCase(browserName)) {
			String userAgent = getUserAgent().toLowerCase();
			RegExp regexp = RegExp.compile("firefox/(.+?)$", "i");
			MatchResult matchResult = regexp.exec(userAgent);
			return matchResult.getGroup(1);
		}
		else if ("Chrome".equalsIgnoreCase(browserName)) {
			String userAgent = getUserAgent().toLowerCase();
			RegExp regexp = RegExp.compile("chrome/(.+?)\\.", "i");
			MatchResult matchResult = regexp.exec(userAgent);
			return matchResult.getGroup(1);
		}
		return null;
	}
	
	private static native String getUserAgent() /*-{
		return navigator.userAgent;
	}-*/;
	
	/**
	 * Shows the unsupported browser message, default messages are used in case messageStrings are null.
	 * @param unsupportedBrowserMessage
	 * @param recommendedBrowserMessage
	 * @param detectedBrowserMessage
	 */
	public static native void showUnsupportedBrowserError(String unsupportedBrowserMessage, String recommendedBrowserMessage, String detectedBrowserMessage) /*-{
		$wnd.showUnsupportedBrowserError(unsupportedBrowserMessage, recommendedBrowserMessage, detectedBrowserMessage);
	}-*/;
	
	public HandlerManager getEventBus(){
		return this.eventBus;
	}
	
	public MyGroups getMyGroups(){
		return getWorkspacePage().getMyGroups();
	}

	public boolean isRmsOptionViaContextMenu() {
		return rmsOptionViaContextMenu;
	}

	public void setRmsOptionViaContextMenu(boolean rmsOptionViaContextMenu) {
		this.rmsOptionViaContextMenu = rmsOptionViaContextMenu;
	}

	public VLayout getBodyLayout() {
		return bodyLayout;
	}

	public UserPreferences getUserPreference() {
		return userPreference;
	}

	public void setUserPreference(UserPreferences userPreference) {
		this.userPreference = userPreference;
	}
	
	public UserData getUserData() {
		return userDataObject;
	}

	public void setUserData(UserData userDataObject) {
		this.userDataObject = userDataObject;
	}
	
	public AclData getAclData() {
		return aclObject;
	}

	public void setAclData(AclData aclObject) {
		this.aclObject = aclObject;
	}


	public boolean isAlreadyProcessDebugLevelEnabled() {
		return process_debug_level;
	}

	public void setProcessDebugLevelEnabled(boolean debugLevel) {
		process_debug_level = debugLevel;
	}

	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken(String apiKey) {
		this.apiToken = apiKey;
	}

	public ApplicationPreferences getApplicationPreferences() {
		return applicationPreferences;
	}

	public void setApplicationPreferences(
			ApplicationPreferences applicationPreferences) {
		this.applicationPreferences = applicationPreferences;
	}
}