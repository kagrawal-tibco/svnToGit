/**
 * 
 */
package com.tibco.cep.webstudio.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ErrorMessageDialog;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * @author vpatil
 */
public class WebStudioAboutDialog extends AbstractWebStudioDialog implements HttpSuccessHandler, HttpFailureHandler {
	
	private Label versionValue,/* buildDateValue*/ copyrightValue;
	
	private static GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	public WebStudioAboutDialog() {
		this.setDialogWidth(500);
		this.setDialogHeight(230);
		this.setDialogTitle(globalMsgBundle.ws_aboutDialog_title());
		this.setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX + "tibco16-32.gif");
		this.setShowCancelButton(false);
		if(LocaleInfo.getCurrentLocale().isRTL()){
			this.setHeaderStyle("webStudioAboutHeader");
			this.setHeaderControls(HeaderControls.CLOSE_BUTTON, HeaderControls.MINIMIZE_BUTTON, HeaderControls.HEADER_LABEL, HeaderControls.HEADER_ICON);			
			this.initialize(true);
		}
		else{
			this.initialize();
		}
		fetchAboutDetails();
		this.okButton.enable();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog#getWidgetList()
	 */
	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();
    	widgetList.add(createMain());
 		widgetList.add(createCopyRight());
		return widgetList;
	}
	
	private HLayout createMain() {
		HLayout headerLayout = new HLayout(3);
		headerLayout.setWidth100();
		
		Img headerImg = new Img(Page.getAppImgDir() + "about_image.png", 122, 135);
		
		VLayout aboutDetails = new VLayout();
		aboutDetails.setLayoutLeftMargin(50);
		aboutDetails.setWidth100();
		
		Label headerTitle = new Label();
		
		headerTitle.setContents("TIBCO BusinessEvents<sup>&reg;</sup> WebStudio");
		headerTitle.setWidth100();
		headerTitle.setStyleName("ws-about-header");
		
		aboutDetails.addMember(headerTitle);
		createAboutDetails(aboutDetails);
		if(LocaleInfo.getCurrentLocale().isRTL()){
			headerLayout.setMembers(aboutDetails, headerImg);
		}
		else{
			headerLayout.setMembers(headerImg, aboutDetails);
		}
		return headerLayout;
	}
	
	private void createAboutDetails(VLayout aboutDetails) {
		
		HLayout versionLayout = new HLayout(2);
		
		Label versionLabel = new Label(globalMsgBundle.aboutDialogBox_version());
		versionLabel.setWidth(90);
		versionLabel.setStyleName("ws-about-detail-label");
		versionValue = new Label();
		versionValue.setStyleName("ws-about-detail-value");
		versionValue.setWidth100();
		if(LocaleInfo.getCurrentLocale().isRTL()){
			versionLayout.addMember(versionValue);
			versionLayout.addMember(versionLabel);
		}
		else{
			versionLayout.addMember(versionLabel);
			versionLayout.addMember(versionValue);
		}
		aboutDetails.addMember(versionLayout);
	}
	
	private HLayout createCopyRight() {
		HLayout copyrightLayout = new HLayout();
		copyrightLayout.setLayoutTopMargin(10);
		copyrightLayout.setLayoutLeftMargin(10);
		copyrightValue = new Label();
		copyrightValue.setStyleName("ws-about-detail-value");
		copyrightValue.setWidth100();
		copyrightLayout.addMember(copyrightValue);
		
		return copyrightLayout;
	}

	
	private void fetchAboutDetails() {
		ArtifactUtil.addHandlers(this);
		
		HttpRequest request = new HttpRequest();
		request.submit(ServerEndpoints.RMS_GET_ABOUT_DETAILS.getURL());
	}
	
	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_ABOUT_DETAILS.getURL()) != -1) {
			Element docElement = event.getData();
			String version = docElement.getElementsByTagName("version").item(0).getFirstChild().getNodeValue();
			String build = docElement.getElementsByTagName("build").item(0).getFirstChild().getNodeValue();
			
			versionValue.setContents(version + "." + build);
			
//			String buildDate = docElement.getElementsByTagName("buildDate").item(0).getFirstChild().getNodeValue();
//			buildDateValue.setContents(buildDate);
			
			String copyright = docElement.getElementsByTagName("copyright").item(0).getFirstChild().getNodeValue();
			String copyrightYearRange = copyright.split(" ")[1];
			copyrightValue.setContents(globalMsgBundle.aboutDialogBox_copyright(copyrightYearRange));
			
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_ABOUT_DETAILS.getURL()) != -1) {
			ArtifactUtil.removeHandlers(this);
			
			Element docElement = event.getData();
			String responseMessage = docElement.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
			ErrorMessageDialog.showError(responseMessage);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog#onAction()
	 */
	@Override
	public void onAction() {
		this.destroy();
	}
}
