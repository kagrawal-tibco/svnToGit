package com.tibco.cep.webstudio.client.authentication;

import com.google.gwt.http.client.URL;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.xml.client.Element;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.DisplayPropertiesManager;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.model.User;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.Base64Utils;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Login Page to handle user login. Supports reading cookie value to check for
 * logged in user details.
 * 
 * @author Vikram Patil
 */
public class LoginPage extends Window implements HttpSuccessHandler, HttpFailureHandler {
	private HttpRequest request;
	private TextItem userName;
	private PasswordItem password;
	private CheckboxItem forceLogin;
	private DynamicForm loginForm;
	private IButton login;
	private Label errorMessage;
	private boolean fromCookie;
	private boolean fromAuthFailedError;
	
	private static GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private static RMSMessages rmsMsgBundle = (RMSMessages)I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);

	public static final String API_TOKEN = "WS_SESSION_ID";	
	private static final String WS_INFO_USER = "WS_INFO_USER";
	private static final String WS_INFO_DM_INSTALLED = "WS_INFO_DM_INSTALLED";
	private static final String WS_INFO_BPMN_INSTALLED = "WS_INFO_BPMN_INSTALLED";
	private static final String WS_INFO_LOCKING_ENABLED = "WS_INFO_LOCKING_ENABLED";
	
	private static final String ERROR_CODE_AUTHENTICATION_FAILED = "ERR_1100";
	private static final String ERROR_CODE_USER_ALREADY_LOGGEDIN = "ERR_1101";
	private static final String ERROR_CODE_USER_NOT_LOGGEDIN = "ERR_1102";
	
	private static int dialogHeight = 230;

	/**
	 * Default Constructor
	 */
	public LoginPage() {
		super();

		setWidth(340);
		setHeight(dialogHeight);

		setShowHeader(false);
		setShowHeaderBackground(false);
		setCanDragReposition(false);
		centerInPage();
		setLayoutTopMargin(5);
		setCanDrag(false);
		setCanDragReposition(false);
		setCanDragResize(false);

		initialize();
		
		// Check for the cookie and remove it if present
		String authFailedMsg = Cookies.getCookie("AUTH_FAILED_MSG");
		if (authFailedMsg != null) {
			authFailedMsg = getGlobalErrorMessage(authFailedMsg, null);
			errorMessage.setContents(authFailedMsg);
			errorMessage.show();
			
			Cookies.removeCookie("AUTH_FAILED_MSG");
			removeUserCookies();
			fromAuthFailedError = true;
		}
	}

	/**
	 * Sets up and initializes the login screen
	 */
	private void initialize() {
		VLayout layout = new VLayout(10);
		layout.setHeight100();
		layout.setWidth100();
		layout.setBackgroundColor("lightgrey");
		layout.setAlign(Alignment.CENTER);
		layout.setAlign(VerticalAlignment.CENTER);
		layout.setLayoutMargin(10);

		VLayout loginLayout = new VLayout(10);
		loginLayout.setLayoutMargin(10);
		loginLayout.setBackgroundColor("white");

		HLayout headerContainer = new HLayout(15);
		headerContainer.setWidth100();
		headerContainer.setHeight(32);
		headerContainer.setAlign(Alignment.CENTER);

		Img headerIcon = new Img(Page.getAppImgDir() + "tibco48-8.gif", 32, 32);
		headerContainer.addMember(headerIcon);

		Label headerTitle = new Label();
		headerTitle.setWrap(false);
		headerTitle.setHeight(32);
		headerTitle.setContents("TIBCO BusinessEvents<sup>&reg;</sup> WebStudio");
		headerTitle.setStyleName("ws-login-header-title");
		headerContainer.addMember(headerTitle);

		errorMessage = new Label();
		errorMessage.setWidth100();
		errorMessage.setHeight(5);
		errorMessage.setStyleName("ws-login-error-message");
		errorMessage.setAlign(Alignment.CENTER);
		errorMessage.hide();

		if(getCompanyLogoUrl() != null && !getCompanyLogoUrl().isEmpty()) {
			loginLayout.addMember(createCompanyLogo());
		}
		loginLayout.addMember(headerContainer);
		loginLayout.addMember(createLoginForm());
		loginLayout.addMember(errorMessage);
		loginLayout.addMember(createButtonContainer());

		layout.addMember(loginLayout);

		addItem(layout);
	}
	
	/**
	 * Creates the Company Logo Image component shown in Login window.
	 * @return
	 */
	private Canvas createCompanyLogo() {
		HTMLPane companyLogoPane = new HTMLPane();
		//<img onError=''../> not needed since already checking url before calling this method.
		companyLogoPane.setContents("<div class=\"CompanyLogoLogin\" id=\"CompanyLogoLogin\">" +
				"<img style=\"max-height:38px;\" src='" + LoginPage.getCompanyLogoUrl() + "' alt='Logo'/>" +
				"</div>");
		companyLogoPane.setWidth100();
		companyLogoPane.setHeight(38);
		companyLogoPane.setOverflow(Overflow.HIDDEN);
		return companyLogoPane;
	}
	
	public static native String getCompanyLogoUrl() /*-{
		return $wnd.company_logo_url;
	}-*/;
	
	/**
	 * Create a Login form
	 * 
	 * @return
	 */
	private DynamicForm createLoginForm() {
		loginForm = new DynamicForm();
		loginForm.setWidth100();
		loginForm.setNumCols(2);
		loginForm.setAlign(Alignment.CENTER);
		loginForm.setRequiredTitleSuffix(":");
		loginForm.setAutoFocus(true);

		userName = new TextItem();
		userName.setTitle(globalMsgBundle.userNameText());
		userName.setRequired(true);
		userName.setRequiredMessage(globalMsgBundle.userName_mandatoryField_message());
		userName.setWidth(200);

		password = new PasswordItem();
		password.setTitle(globalMsgBundle.passwordText());
		password.setWidth(200);
		password.setRequired(true);
		password.setRequiredMessage(globalMsgBundle.password_mandatoryField_message());
		password.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					if (loginForm.validate()) {
						login.disable();
						doLogin(userName.getValueAsString(), Base64Utils.toBase64(password.getValueAsString().getBytes()), forceLogin.getValueAsBoolean());
					}
				}
			}
		});
		
		forceLogin = new CheckboxItem("forceLogin", globalMsgBundle.force_userlogin_text());
		forceLogin.setTooltip(globalMsgBundle.force_userlogin_title());
		forceLogin.setHeight(10);
		forceLogin.setVisible(false);
		
		loginForm.setItems(userName, password, forceLogin);
		
		return loginForm;
	}

	/**
	 * Create the login button
	 * 
	 * @return
	 */
	private HLayout createButtonContainer() {
		HLayout buttonContainer = new HLayout();
		buttonContainer.setWidth100();
		buttonContainer.setAlign(Alignment.LEFT);
		buttonContainer.setLayoutLeftMargin(78);

		login = new IButton(globalMsgBundle.button_login());
		login.setShowRollOver(true);
		login.setWidth(75);
		login.setHeight(25);
		
		login.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (loginForm.validate()) {
					login.disable();
					//TODO Handle non-ascii chars.
					doLogin(userName.getValueAsString(), Base64Utils.toBase64(password.getValueAsString().getBytes()), forceLogin.getValueAsBoolean());
				}
			}
		});

		buttonContainer.addMember(login);

		return buttonContainer;
	}

	/**
	 * Method to make the server call passing the credentials
	 */
	private void doLogin(String userName, String password, Boolean forceLogin) {
		request = new HttpRequest(ServerEndpoints.LOGIN.getURL(userName));
		// add requestParameters
//		if (userName != null && !userName.isEmpty()) {
//			request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_USER_NAME, userName));
//		}
		if (password != null && !password.isEmpty()) {
			request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_USER_PASSWORD, password));
		}
		if (forceLogin != null && forceLogin) {
			request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_USER_FORCELOGIN, forceLogin));
		}
//		request.setUnEscapseData(true);

		ArtifactUtil.addHandlers(this);

		request.submit();
		WebStudio.showLoading();
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.LOGIN.getURL(userName.getValueAsString())) != -1) {
			User loggedInUser = extractUser(event.getData());			
			WebStudio.get().setUser(loggedInUser);
			
			this.destroy();			
			ArtifactUtil.removeHandlers(this);
//			UserPreferenceHelper.getInstance().getUserPreferences();
			
			DisplayPropertiesManager.getInstance().setOnLogin(true);
			DisplayPropertiesManager.getInstance().getDisplayProperties();
		}
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.LOGIN.getURL(userName.getValueAsString())) != -1) {
			if (fromCookie) {
				this.show();
				fromCookie = false;
			} else {
				Element docElement = event.getData();
				String errorCode = null;
				if(docElement.getElementsByTagName("errorCode") != null && docElement.getElementsByTagName("errorCode").getLength() > 0) {
					errorCode = docElement.getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue();
				}
				String error = docElement.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
				error = getGlobalErrorMessage(error, errorCode);
				setHeight(dialogHeight + 30);
				errorMessage.setContents(error);
				errorMessage.show();
				
				login.enable();
				
				forceLogin.setValue(false);
				if (ERROR_CODE_USER_ALREADY_LOGGEDIN.equals(errorCode)) {
					forceLogin.show();
				}
				else {
					forceLogin.hide();
				}
			}
			removeUserCookies();
			WebStudio.hideLoading();
			ArtifactUtil.removeHandlers(this);
		}
	}

	/**
	 * Check if the Login Token exists
	 */
	@SuppressWarnings("unused")
	public void checkLogin() {
		String userToken = Cookies.getCookie(API_TOKEN);
		if (userToken == null || fromAuthFailedError) {
			this.show();
			fromAuthFailedError = false;
		} else {
			fromCookie = true;
			User loggedInUser = getUserInfoFromCookies();
			WebStudio.get().setUser(loggedInUser);
			DisplayPropertiesManager.getInstance().setOnLogin(true);
			DisplayPropertiesManager.getInstance().getDisplayProperties();			
		}
	}
	
	/**
	 * Parse the login token to extract Login details
	 * 
	 * @param tokenElement
	 * @return
	 */
	private User extractUser(Element loginResponse) {
		User loggedInUser = new User();
		loggedInUser.setUserName(userName.getValueAsString());
		
		String apiToken = null;
		if (loginResponse.getElementsByTagName("apiToken").getLength() > 0) {
			apiToken = loginResponse.getElementsByTagName("apiToken").item(0).getFirstChild().getNodeValue();
			if (apiToken != null && !apiToken.isEmpty()) {
				WebStudio.get().setApiToken(apiToken);
			}
		}

		boolean isDMInstalled = false, isBPMNInstalled = false, isLockingEnabled = false;
		if (loginResponse.getElementsByTagName("isDMInstalled").getLength() > 0) {
			String dmins = loginResponse.getElementsByTagName("isDMInstalled").item(0).getFirstChild().getNodeValue();
			if (dmins != null) {
				isDMInstalled = new Boolean(dmins);
				ArtifactUtil.setDecisionManagerInstalled(isDMInstalled);
			}
		}		
		if (loginResponse.getElementsByTagName("isBPMNInstalled").getLength() > 0) {
			String bpmnins = loginResponse.getElementsByTagName("isBPMNInstalled").item(0).getFirstChild().getNodeValue();
			if (bpmnins != null) {
				isBPMNInstalled = new Boolean(bpmnins);
				ArtifactUtil.setBPMNInstalled(isBPMNInstalled);
			}
		}
		if (loginResponse.getElementsByTagName("isLockingEnabled").getLength() > 0) {
			String isLockingEnabledStr = loginResponse.getElementsByTagName("isLockingEnabled").item(0).getFirstChild().getNodeValue();
			if (isLockingEnabledStr != null) {
				isLockingEnabled = new Boolean(isLockingEnabledStr);
				ArtifactUtil.setLockingEnabled(isLockingEnabled);
			}
		}
		setUserCookies(loggedInUser.getUserName(), apiToken, isDMInstalled, isBPMNInstalled, isLockingEnabled);
		return loggedInUser;
	}
	
	/**
	 * Show Error message
	 * @param error
	 */
	public static String getGlobalErrorMessage(String error, String errorCode) {
		if (errorCode == null) {
			return error;
		}
		if (ERROR_CODE_AUTHENTICATION_FAILED.equals(errorCode)) {//Authentication Failed. Please retry or contact the server administrator.
			error = rmsMsgBundle.servermessage_loginFailed();
		}
		else if (ERROR_CODE_USER_ALREADY_LOGGEDIN.equals(errorCode)) {//User [%s] already logged-in from [%s]. Multiple user logins not allowed
			RegExp regexp = RegExp.compile(".*\\[(.*?)\\].*\\[(.*?)\\].*", "i");
			MatchResult matchResult = regexp.exec(error);
			if (matchResult != null && matchResult.getGroupCount() > 2) {
				String user = matchResult.getGroup(1);
				String address = matchResult.getGroup(2);
				error = rmsMsgBundle.servermessage_alreadyLoggedIn(user, address);
			}
		}
		else if (ERROR_CODE_USER_NOT_LOGGEDIN.equals(errorCode)) {
			error = rmsMsgBundle.servermessage_not_loggedin();
		}
		return error;
	}
	
	/**
	 * Store the User Info in client Cookie
	 * @param userName
	 * @param apiToken
	 * @param isDMInstalled
	 * @param isBPMNInstalled
	 * @param isLockingEnabled
	 */
	private static void setUserCookies(String userName, String apiToken, boolean isDMInstalled, boolean isBPMNInstalled, boolean isLockingEnabled) {
		if (userName != null) Cookies.setCookie(WS_INFO_USER, URL.encode(userName));
		if (apiToken != null) Cookies.setCookie(API_TOKEN, apiToken);
		Cookies.setCookie(WS_INFO_DM_INSTALLED, Boolean.toString(isDMInstalled));
		Cookies.setCookie(WS_INFO_BPMN_INSTALLED, Boolean.toString(isBPMNInstalled));
		Cookies.setCookie(WS_INFO_LOCKING_ENABLED, Boolean.toString(isLockingEnabled));
	}
		
	/**
	 * @return
	 */
	private static User getUserInfoFromCookies() {
		User loggedInUser = new User();
		loggedInUser.setUserName(URL.decode(Cookies.getCookie(WS_INFO_USER)));		

		WebStudio.get().setApiToken(Cookies.getCookie(API_TOKEN));

		ArtifactUtil.setDecisionManagerInstalled(Boolean.parseBoolean(Cookies.getCookie(WS_INFO_DM_INSTALLED)));
		ArtifactUtil.setBPMNInstalled(Boolean.parseBoolean(Cookies.getCookie(WS_INFO_BPMN_INSTALLED)));
		ArtifactUtil.setLockingEnabled(Boolean.parseBoolean(Cookies.getCookie(WS_INFO_LOCKING_ENABLED)));

		return loggedInUser;		
	}
	
	/**
	 * remove the User Info client cookies
	 */
	public static void removeUserCookies() {
		Cookies.removeCookie(WS_INFO_USER);
		Cookies.removeCookie(API_TOKEN);
		Cookies.removeCookie(WS_INFO_DM_INSTALLED);
		Cookies.removeCookie(WS_INFO_BPMN_INSTALLED);
		Cookies.removeCookie(WS_INFO_LOCKING_ENABLED);
	}	
}