/**
 * 
 */
package com.tibco.cep.webstudio.client.http;

import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.StringUtil;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.authentication.LoginPage;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.panels.CustomSC;
/**
 * HttpRequest class to send request and receive/handle response.
 * 
 * @author Vikram Patil
 */
public class HttpRequest implements RequestCallback {
	private String url;
	private HttpMethod httpMethod;
	private String postData;
	private boolean unEscapseData;
	private Map<String, Object> requestParams = new HashMap<String, Object>();
	private Map<String, String> headerParams = new HashMap<String, String>();

	private static final String CONTENT_TYPE_KEY = "Content-type";
	private static final String CONTENT_TYPE_VALUE = "application/xml;charset=utf-8";
	
	private static boolean errorMsgDisplayed = false;
//	private static boolean loginDisplayed = false;

	private static GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	public HttpRequest() {
		this.httpMethod = HttpMethod.GET;
	}

	public HttpRequest(String url) {
		this.url = url;
		this.httpMethod = HttpMethod.GET;
	}

	public HttpRequest(String url, HttpMethod method) {
		this.url = url;
		this.httpMethod = method;
	}

	public void setPostData(String postData) {
		this.postData = postData;
	}

	/**
	 * Setter for url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Getter for url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Setter for method-GET/POST
	 */
	public void setMethod(HttpMethod method) {
		this.httpMethod = method;
	}

	/**
	 * Clears any existing request parameters
	 */
	public void clearParameters() {
		this.requestParams.clear();
	}

	/**
	 * Add request parameters
	 * 
	 * @param parameter
	 */

	public void addRequestParameters(RequestParameter parameter) {
		this.requestParams.putAll(parameter.getParameters());
	}

	/**
	 * Add header parameters if any
	 * 
	 * @param key
	 * @param value
	 */
	public void addHeaderParameter(String key, String value) {
		this.headerParams.put(key, value);
	}

	/**
	 * Set whether to UnEscapse Data
	 * 
	 * @param unEscapseData
	 */
	public void setUnEscapseData(boolean unEscapseData) {
		this.unEscapseData = unEscapseData;
	}

	/**
	 * Submits the request to the server. Parameters will go as the body of the
	 * request.
	 */
	public void submit() {
		Method method = null;
		switch(this.httpMethod) {
		case GET: method = RequestBuilder.GET; break;
		case POST: method = RequestBuilder.POST; break;
		case PUT: method = RequestBuilder.PUT; break;
		case DELETE: method = RequestBuilder.DELETE; break;
		default: method = RequestBuilder.POST;//Maintaining previous default behaviour.
		}

		if (this.getRequestData() != null && !this.getRequestData().isEmpty()) {
			this.url += "?" + this.getRequestData();
		}

		RequestBuilder rb = new RequestBuilder(method, this.url);
		this.setRequestHeaders(rb);

		String data = this.postData;
		
		try {
			rb.sendRequest(data, this);
		} catch (RequestException e) {
			showErrorMessage();
		}
	}

	/**
	 * Add header parameters(if any) to request
	 * 
	 * @param rb
	 */
	private void setRequestHeaders(RequestBuilder rb) {
		rb.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
		if (WebStudio.get().getUser() != null) {
			rb.setHeader(RequestParameter.REQUEST_USER_NAME, URL.encode(WebStudio.get().getUser().getUserName()));
		}

		for (String key : this.headerParams.keySet()) {
			rb.setHeader(key, this.headerParams.get(key));
		}
	}

	/**
	 * Sets the url and calls the noarg version of submit()
	 * 
	 * @param url
	 */
	public void submit(String url) {
		this.setUrl(url);
		this.submit();
	}

	/**
	 * Forms the request parameters string as like query string
	 */
	@SuppressWarnings("unchecked")
	public String getRequestData() {
		StringBuilder buf = new StringBuilder();
		Iterator<Entry<String, Object>> it = this.requestParams.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			Object value = entry.getValue();
			if (value instanceof ArrayList) {
				for (String v : (ArrayList<String>) value) {
					buf.append(entry.getKey());
					buf.append("=");
					String encodedValue = URL.encode(v.toString());
					buf.append(this.encodeHtmlCharacters(encodedValue));
					buf.append("&");
				}
			} else {
				buf.append(entry.getKey());
				buf.append("=");
				String encodedValue = URL.encode(value.toString());
				buf.append(this.encodeHtmlCharacters(encodedValue));
				buf.append("&");
			}
		}

		// below parameter will force a new request every time
		buf.append("rnd=" + new Date().getTime());
		return buf.toString();
	}

	/**
	 * Encode Html Characters if any
	 * 
	 * @param encodedValue
	 * @return
	 */
	public String encodeHtmlCharacters(String encodedValue) {
		/* encode &, <, >, ' , ", &nbsp characters */
		return encodedValue == null ? encodedValue : encodedValue.replaceAll("&amp;", "%26").replaceAll("&lt;", "%3C")
				.replaceAll("&gt;", "%3E").replaceAll("&quot;", "%22").replaceAll("&nbsp;", "%A0")
				.replaceAll("&#038;", "%26").replaceAll("&#060;", "%3C").replaceAll("&#062;", "%3E")
				.replaceAll("&#39;", "%27").replaceAll("&#039;", "%27").replaceAll("&#034;", "%22")
				.replaceAll("&#160;", "%A0").replaceAll("&", "%26").replaceAll("[\\+]", "%2B")
				.replaceAll("[\\#]", "%23");
	}

	/**
	 * Call back method to handle the response received from the server
	 * 
	 * @param request
	 * @param response
	 */
	public void onResponseReceived(Request request, Response response) {
		String responseTxt = response.getText();
		if (200 == response.getStatusCode()) {
			if (response.getHeader(CONTENT_TYPE_KEY).indexOf("application/xml") != -1) {
				if (this.unEscapseData) {
					responseTxt = StringUtil.unescapeHTML(responseTxt);
				}
				Document document = XMLParser.parse(responseTxt);
				Element docElement = document.getDocumentElement();
				if (this.isSuccess(docElement)) {
					WebStudio.get().getEventBus().fireEvent(new HttpSuccessEvent(this.url, docElement));
				} else {
					if (hasAuthenticationFailed(docElement)) {
						String errorMessage = docElement.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
						String errorCode = null;
						if(docElement.getElementsByTagName("errorCode") != null && docElement.getElementsByTagName("errorCode").getLength() > 0) {
							errorCode = docElement.getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue();
						}
						Cookies.setCookie("AUTH_FAILED_MSG", LoginPage.getGlobalErrorMessage(errorMessage, errorCode));
						Window.Location.assign(Window.Location.getHref());
					} else {
						WebStudio.get().getEventBus().fireEvent(new HttpFailureEvent(this.url, docElement));
					}
				}
			} else {
				CustomSC.say("Content other than application/xml");
			}
		} else {
			showErrorMessage();
		}
	}

	/**
	 * Check if the response received is a success/failure
	 * 
	 * @param docElement
	 * @return
	 */
	private boolean isSuccess(Element docElement) {
		String statusValue = docElement.getElementsByTagName("status").item(0).getFirstChild().getNodeValue();

		return (statusValue.equals("0")) ? true : false;
	}

	/**
	 * Handler for any communication error with the server
	 * 
	 * @param request
	 * @param exception
	 */
	public void onError(Request request, Throwable exception) {
		showErrorMessage();
	}

	public void clearRequestParameters() {
		this.requestParams.clear();
	}
	
	/**
	 * Check if Authentication has failed.
	 * 
	 * @param docElement
	 * @return
	 */
	private boolean hasAuthenticationFailed(Element docElement) {
		boolean authenticationFailed = false;
		
		String errorCode = docElement.getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue();
		if (errorCode.equals("ERR_1102")) {
			authenticationFailed = true;
		}
		
		return authenticationFailed;
	}
	
	/**
	 * Show error message(if any) during server communication
	 */
	public static void showErrorMessage() {
		if (!errorMsgDisplayed) {
			errorMsgDisplayed = true;
			showError(globalMsgBundle.serverConnection_error(), new BooleanCallback() {
				public void execute(Boolean value) {
					if (value) {
						errorMsgDisplayed = false;
					}
				}
			});
		}
	}
}
