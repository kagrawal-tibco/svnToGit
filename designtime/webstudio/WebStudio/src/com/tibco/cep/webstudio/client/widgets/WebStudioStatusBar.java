package com.tibco.cep.webstudio.client.widgets;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.google.gwt.i18n.client.LocaleInfo;
public class WebStudioStatusBar extends HLayout {

	private static final String STATUSBAR_HEIGHT = "23px";	
	private ImgButton showViewsButton;
	private ImgButton showResourceButton;
	private Label selectedLabel;
	private ImgButton otherStatusButton;
	private Label otherLabel;
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	private WebStudioClientLogger logger = WebStudioClientLogger.getLogger(WebStudioStatusBar.class.getName());
	
	public WebStudioStatusBar() {
		super();

		logger.info("Init WebStudio StatusBar");

		// initialize the web studio StatusBar layout container	
		this.setStyleName("ws-StatusBar");	
		this.setHeight(STATUSBAR_HEIGHT);	
		
		showViewsButton = new ImgButton();  
		showViewsButton.setShowRollOver(false);
		showViewsButton.setShowHover(true);
		showViewsButton.setShowDisabled(false);      
		showViewsButton.setShowDown(false);
		showViewsButton.setSize(12); 
		showViewsButton.setLayoutAlign(VerticalAlignment.CENTER);
		showViewsButton.setSrc(Page.getAppImgDir() + "icons/16/showViews.png");   
		showViewsButton.setVisible(false);
		
		showResourceButton = new ImgButton();  
		showResourceButton.setShowRollOver(false);
		showResourceButton.setShowHover(false); 
		showResourceButton.setShowDisabled(false);      
		showResourceButton.setShowDown(false);  
		showResourceButton.setSize(12); 
		showResourceButton.setLayoutAlign(VerticalAlignment.CENTER);
		showResourceButton.setSrc(Page.getAppImgDir() + "icons/16/file.png");       
		
		selectedLabel = new Label(); 
		selectedLabel.setStyleName("ws-StatusBar-Label");
		selectedLabel.setContents(globalMsgBundle.message_noResourceSelected()); 
		selectedLabel.setOverflow(Overflow.HIDDEN); 		
		if(LocaleInfo.getCurrentLocale().isRTL()){
			selectedLabel.setAlign(Alignment.RIGHT);  
		}
		else{
			selectedLabel.setAlign(Alignment.LEFT);  
		}

		otherStatusButton = new ImgButton();  
		otherStatusButton.setShowRollOver(false); 
		otherStatusButton.setShowDisabled(false);      
		otherStatusButton.setShowDown(false);  
		otherStatusButton.setSize(12); 
		otherStatusButton.setLayoutAlign(VerticalAlignment.CENTER);
		otherStatusButton.setSrc(Page.getAppImgDir() + "icons/16/showProgress.png");   
		otherStatusButton.setVisible(false);
		
		otherLabel = new Label(); 
		otherLabel.setStyleName("ws-StatusBar-Label");
		otherLabel.setContents(globalMsgBundle.text_progress() + ".."); 
		otherLabel.setWidth(50); 
		otherLabel.setAlign(Alignment.RIGHT);  
		otherLabel.setOverflow(Overflow.HIDDEN); 
		otherLabel.setVisible(false);

		this.addMember(showViewsButton);
		

		addMember(addSeparator());
		
		this.addMember(showResourceButton);	
		this.addMember(selectedLabel);	
		
		// force right alignment
		Label alignRight = new Label("&nbsp;"); 
		alignRight.setAlign(Alignment.RIGHT);  
		alignRight.setOverflow(Overflow.HIDDEN);    
		
		this.addMember(alignRight);	
		
		this.addMember(otherStatusButton);	
		
		this.addMember(otherLabel);	
		
		// add some padding
		LayoutSpacer paddingRight = new LayoutSpacer(); 
		paddingRight.setWidth(8); 
		this.addMember(paddingRight);
	}
	
	/**
	 * @param title
	 * @param icon
	 */
	public void updateStatus(String title, String icon) {
		selectedLabel.setContents(title); 
		showResourceButton.setSrc(icon);       
	}
	
	public ImgButton addSeparator() {
		ImgButton separatorButton = new ImgButton();  
		separatorButton.setShowRollOver(false);
		separatorButton.setShowHover(true);
		separatorButton.setShowDisabled(false);      
		separatorButton.setShowDown(false);
		separatorButton.setHeight100();
		separatorButton.setWidth("10px");
		separatorButton.setLayoutAlign(VerticalAlignment.CENTER);
		separatorButton.setSrc(Page.getAppImgDir() + "icons/16/statusbar_separator.png");   
		return separatorButton;
	}
}