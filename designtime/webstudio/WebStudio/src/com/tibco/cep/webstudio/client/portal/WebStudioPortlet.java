package com.tibco.cep.webstudio.client.portal;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.Portlet;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.preferences.UserPreferenceHelper;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

public class WebStudioPortlet extends Portlet {  
	  
	private Canvas modularContainer;
    private Canvas modularCanvas;
	private Canvas maximizedCanvas;
	protected boolean maximized = false;
	protected boolean initialized = false;
	protected boolean isEmpty;
	private Label titleLabel;
	private boolean isHidden;
	
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	public WebStudioPortlet() {  

        setShowShadow(false);  

        // enable predefined component animation  
        setAnimateMinimize(true);  
        
        // use a placeholder canvas for the modular canvas to achieve the "rounded inner border" look
        // not sure if there is an easier way to do this?
        modularContainer = new Canvas();
        modularContainer.setWidth100();
        modularContainer.setHeight100();
        modularContainer.setShowEdges(false);
        modularContainer.setStyleName("ws-modularcontainerborder");
        modularContainer.setBackgroundColor("white");

        // Window is draggable with "outline" appearance by default.  
        // "target" is the solid appearance.  
        setDragAppearance(DragAppearance.OUTLINE);  
        setCanDrop(true);  
        
        // customize the appearance and order of the controls in the window header  
        titleLabel = new Label(getTitle());
        titleLabel.setWidth100();
        titleLabel.setStyleName("ws-portlet-header");
        titleLabel.setVAlign();
        titleLabel.setAlign(Alignment.CENTER);
        ClickHandler clickHandler = new ClickHandler() {  
            public void onClick(ClickEvent event) {  
                String src = ((HeaderControl) event.getSource()).getTitle();
                if ("maximize".equals(src)) {
                	if (getMinimized() != null && getMinimized()) {
                		maximize();
                	} else if (getMaximized() != null && getMaximized()) {
                		restore();
                	} else {
                		maximize();
                	}
                } else if ("minimize".equals(src)) {
                	if (getMaximized() != null && getMaximized()) {
                		restore();
                		minimize();
                	} else if (getMinimized() != null && getMinimized()) {
                		restore();
                	} else {
                		minimize();
                	}
                }
            }  
        };  
        final HeaderControl maximize = new HeaderControl(HeaderControl.MAXIMIZE, clickHandler);  
        maximize.setTitle("maximize");
        final HeaderControl minimize = new HeaderControl(HeaderControl.MINIMIZE, clickHandler);  
        minimize.setTitle("minimize");
        setHeaderControls(titleLabel, minimize, maximize, HeaderControls.CLOSE_BUTTON);  
		setHeaderStyle("ws-portlet-header");
        
        // show either a shadow, or translucency, when dragging a portlet  
        // (could do both at the same time, but these are not visually compatible effects)  
//        setShowDragShadow(true);  
        setDragOpacity(30);  
        setMinimizeHeight(50);
        setShadowDepth(10);
        setShowShadow(true);
        setVPolicy(LayoutPolicy.FILL);  
        setHPolicy(LayoutPolicy.FILL);  
        setOverflow(Overflow.AUTO);  
        String userAgent = Window.Navigator.getUserAgent().toLowerCase();
        boolean isFireFox = false;
        if(userAgent.matches(".*firefox/[0-9].*") && !userAgent.matches(".*seamonkey/.*")){
        	isFireFox = true;
        }
        if(LocaleInfo.getCurrentLocale().isRTL() && isFireFox){	
        	setDefaultHeight(120);
        }
        else{
        	setDefaultHeight(250);
        }
        setHeight(getDefaultHeight());
        setBodyStyle("ws-modularcontainerborder");
		setStyleName("ws-portletborder");
        setOverflow(Overflow.HIDDEN);  
		setShowEdges(false);
		addCloseClickHandler(new CloseClickHandler() {
			public void onCloseClick(CloseClickEvent event) {
				CustomSC.confirm(globalMsgBundle.portlet_confirm_close_title(), globalMsgBundle.portlet_confirm_close_text(), new BooleanCallback() {
					public void execute(Boolean value) {
						if (value) {
							if (WebStudioPortlet.this.getMaximized() != null && WebStudioPortlet.this.getMaximized()) {
								restore();
							}
							hideInPortal();
							WebStudio.get().getPreferencesPage().updatePortletListInAppearancePortlet();
							
							String portletId = "";
							if (WebStudioPortlet.this instanceof WebPagePortlet) {
								if (DASHBOARD_PORTLETS.DOCUMENTATION.getTitle().equals(titleLabel.getContents())) {
									portletId =  titleLabel.getContents();
								} else {
									portletId = WebPagePortlet.CUSTOM_PAGE_PREFIX + titleLabel.getContents();
								}
							} else {
								portletId =  titleLabel.getContents();
							}
							WebStudio.get().getUserPreference().removeDashboardPortlets(portletId);
							UserPreferenceHelper.getInstance().updateUserPreferences(WebStudio.get().getUserPreference());
						}
					}
				});
			}
		});
        super.addItem(modularContainer);
	}

	@Override
	public void setTitle(String title) {
		super.setTitle(title);
		titleLabel.setContents(title);
	}

	@Override
	protected void onDraw() {
		if (!initialized) {
			initialize();
		}
		if (this.maximized && getParentElement() != null && getHeight() != getParentElement().getHeight()) {
			// workaround for lazy initialization
			setHeight(getParentElement().getHeight());
		}
		super.onDraw();
	}

	protected void initialize() {
		initialized = true;
	}

	public void setModularCanvas(Canvas canvas) {
    	if (this.modularCanvas != null) {
    		modularContainer.removeChild(this.modularCanvas);
    	}
		this.modularCanvas = canvas;
		if (!modularContainer.contains(canvas)) {
			addItem(canvas);
			if (this.maximized) {
				canvas.hide();
			}
		}
	}
    
    public void setMaximizedCanvas(Canvas canvas) {
    	if (this.maximizedCanvas != null) {
    		this.removeChild(this.maximizedCanvas);
    	}
    	this.maximizedCanvas = canvas;
    	if (!contains(canvas)) {
    		addItem(canvas);
    		if (!this.maximized) {
    			canvas.hide();
    		}
    	}
    }

	public Canvas getModularCanvas() {
		return modularCanvas;
	}

	public Canvas getMaximizedCanvas() {
		return maximizedCanvas;
	}

	@Override
	public void minimize() {
		super.minimize();
	}
	
	@Override
	public void maximize() {
		if (true) {
			super.maximize();
			return;
		}
		initialize();
		if (getMaximizedCanvas() != null) {
			getModularCanvas().hide();
			setHeight(getParentElement().getHeight());
			getMaximizedCanvas().show();
		} else {
			if (getParentElement() != null) {
				setHeight(getParentElement().getHeight());
				setWidth(getParentElement().getWidth());
			}
		}
		this.maximized = true;
		setMaximized(true);
	}

	@Override
	public void restore() {
		if (true) {
			super.restore();
			return;
		}
		initialize();
		if (getMaximizedCanvas() != null) {
			getMaximizedCanvas().hide();
			setHeight(getDefaultHeight());
			getModularCanvas().show();
		} else {
			setHeight(getDefaultHeight());
		}
		this.maximized = false;

		setMaximized(false);
		setMinimized(false);
	}

	@Override
	public void addItem(Canvas component) {
		modularContainer.addChild(component);
		if (getModularCanvas() == null) {
			// set a default modular canvas
			setModularCanvas(component);
		}
	}
	
	/**
	 * 
	 * @param selectedProjectName
	 */
	protected void getProjectArtifacts(String selectedProjectName){
		HttpRequest request = new HttpRequest();
		request.clearParameters();
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, selectedProjectName));
		request.setMethod(HttpMethod.GET);
		
		request.submit(ServerEndpoints.RMS_GET_PROJECT_ARTIFACTS.getURL());
	}
	
	/**
	 * 
	 * @param docElement
	 * @return
	 */
	protected List<String> getArtifacts(Element docElement){
		List<String> artifactItems = new ArrayList<String>();
		
		NodeList artifactList = docElement.getElementsByTagName("record");
		for (int i=0;i<artifactList.getLength();i++){
			if (!artifactList.item(i).toString().trim().isEmpty()) {
				NodeList artifactDetails = artifactList.item(i).getChildNodes();

				String artifactPath=null, artifactExtn=null;
				for (int j=0;j<artifactDetails.getLength();j++) {
					if (!artifactDetails.item(j).toString().trim().isEmpty()) {
						if (artifactDetails.item(j).getNodeName().equals("artifactPath")) artifactPath = artifactDetails.item(j).getFirstChild().getNodeValue();
						if (artifactDetails.item(j).getNodeName().equals("fileExtension")) artifactExtn = artifactDetails.item(j).getFirstChild().getNodeValue();

						if (artifactPath != null && artifactExtn != null) break;
					}
				}

				artifactItems.add(artifactPath + "." + artifactExtn);
			}
		}
		
		return artifactItems;
	}

	public void hideInPortal() {
		this.isHidden = true;
		hide();
	}
	
	public void showInPortal() {
		this.isHidden = false;
		show();
	}
	
	public boolean isHiddenInPortal() {
		return this.isHidden;
	}
}
