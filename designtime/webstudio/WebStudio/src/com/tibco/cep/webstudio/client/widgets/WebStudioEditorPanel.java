package com.tibco.cep.webstudio.client.widgets;

import com.google.gwt.i18n.client.LocaleInfo;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TabBarControls;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableAnalyzerPane;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditor;
import com.tibco.cep.webstudio.client.handler.EditorPaneCloseHandler;
import com.tibco.cep.webstudio.client.handler.EditorPaneTabChangeHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.palette.PalettePane;
import com.tibco.cep.webstudio.client.problems.ProblemsPane;
import com.tibco.cep.webstudio.client.process.properties.PropertiesPane;

/**
 * This class defines an editor Panel to contain various artifacts editors
 * 
 * @author Vikram Patil
 */
public class WebStudioEditorPanel extends VLayout {

	private TabSet tabContainer;
	private PropertiesPane propertiesPane;
	private VLayout leftPane;
	private DecisionTableAnalyzerPane decisionTableAnalyzerPane;
	private PalettePane palettePane;

	private ProblemsPane problemsPane;

	private HLayout editorPane;

	private TabSet bottomContainer;
	private VLayout bottomPaneLayout;
	
	private HeaderControl maximizebutton;

	private HeaderControl restorebutton;
	
	private EditorPaneTabChangeHandler editorPaneTabChangeHandler;
	
	private static GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

	/**
	 * Setup and initialize WebStudioEditor Panel
	 */
	public WebStudioEditorPanel() {
		super();
		// initialize the layout container
		this.setWidth("*");
		this.setBackgroundColor("#EEEEEE"); 
		
		setRedrawOnResize(true);
		setResizeBarTarget("next");
		editorPane = new HLayout();
		editorPane.setWidth100();
		editorPane.setHeight100();
		editorPane.setShowResizeBar(true);
		editorPane.setRedrawOnResize(true);
		editorPane.setResizeBarTarget("next");
		
		tabContainer = new TabSet();
		tabContainer.setTabBarPosition(Side.TOP);
		if(LocaleInfo.getCurrentLocale().isRTL()){
			tabContainer.setTabBarAlign(Side.RIGHT);
		}
		else{
			tabContainer.setTabBarAlign(Side.LEFT);
		}
		tabContainer.setWidth100();
		tabContainer.setHeight100();

		tabContainer.addCloseClickHandler(new EditorPaneCloseHandler(this));
		
		editorPaneTabChangeHandler = new EditorPaneTabChangeHandler(this);
		tabContainer.addTabSelectedHandler(editorPaneTabChangeHandler);
		tabContainer.addTabDeselectedHandler(editorPaneTabChangeHandler);
		
		tabContainer.setShowResizeBar(true);
		tabContainer.setResizeBarTarget("next");
		
		leftPane = new VLayout();
		leftPane.setWidth(250);
		leftPane.addResizedHandler(new ResizedHandler() {
			
			@Override
			public void onResized(ResizedEvent event) {
				if (decisionTableAnalyzerPane != null && decisionTableAnalyzerPane.isVisible()) {
					decisionTableAnalyzerPane.setWidth100();
					decisionTableAnalyzerPane.reflow();	
				}
				if (palettePane != null && palettePane.isVisible()) {
					palettePane.setWidth100();
					palettePane.reflow();	
				}
			}
		});
		leftPane.setVisible(false);
		setTopToolBar();
		editorPane.setMembers(tabContainer, leftPane);
		initializeBottomPane();//For Properties/Problems View etc.
		this.setMembers(editorPane, bottomPaneLayout);
	}
	
	private void initializeBottomPane() {
		bottomPaneLayout = new VLayout();
		bottomPaneLayout.setWidth100();
		bottomPaneLayout.setHeight(150);
		bottomPaneLayout.setVisible(false);

		bottomContainer = new TabSet();
		bottomContainer.setTabBarPosition(Side.TOP);
		if(LocaleInfo.getCurrentLocale().isRTL()){
			bottomContainer.setTabBarAlign(Side.RIGHT);
		}
		else{
			bottomContainer.setTabBarAlign(Side.LEFT);
		}
		bottomContainer.setWidth100();
		bottomContainer.setHeight100();
		bottomContainer.addTabSelectedHandler(editorPaneTabChangeHandler);

		propertiesPane = new PropertiesPane();
		problemsPane = new ProblemsPane();
		
		bottomContainer.addTab(propertiesPane);
		bottomContainer.addTab(problemsPane);
		
		HStack bottomPaneTopToolBarstack = new HStack();
		bottomPaneTopToolBarstack.setAlign(Alignment.RIGHT);
		HeaderControl close = new HeaderControl(HeaderControl.CLOSE, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setShowPropertiesForAll(false);
				bottomPaneLayout.setVisible(false);
			}
		});
		close.setTooltip(globalMsgBundle.button_close());
		
		Label label = new Label();
		label.setWidth(5);
		
		bottomPaneTopToolBarstack.setMembers(close, label);
		bottomContainer.setTabBarControls(TabBarControls.TAB_SCROLLER, TabBarControls.TAB_PICKER, bottomPaneTopToolBarstack);
		bottomPaneLayout.setMembers(bottomContainer);
		bottomPaneLayout.setVisible(false);
	}
	
	public void setShowPropertiesForAll(boolean showProperties) {
		for ( Tab tab : tabContainer.getTabs()) {
			if (tab instanceof DecisionTableEditor) {
				((DecisionTableEditor)tab).setShowProperties(showProperties);
			}
		}
	}
	
	public void setShowTableAnalyzer(boolean showTableAnalyzer) {
		for ( Tab tab : tabContainer.getTabs()) {
			if (tab instanceof DecisionTableEditor) {
				((DecisionTableEditor)tab).setShowTableAnalyzer(showTableAnalyzer);
			}
		}
	}

	protected void setTopToolBar() {
		HStack topToolBarStack = new HStack();
		topToolBarStack.setAlign(Alignment.RIGHT);

		maximizebutton = new HeaderControl(HeaderControl.MAXIMIZE);
		maximizebutton.setAlign(Alignment.RIGHT);
		maximizebutton.setTooltip(globalMsgBundle.text_maximize());
		maximizebutton.addClickHandler(editorPaneTabChangeHandler);

		restorebutton = new HeaderControl(HeaderControl.CASCADE); 
		restorebutton.setAlign(Alignment.RIGHT);
		restorebutton.setTooltip(globalMsgBundle.text_restore());
		restorebutton.addClickHandler(editorPaneTabChangeHandler);
		restorebutton.setVisible(false);
		
		Label label = new Label();
		label.setWidth(5);

		topToolBarStack.setMembers(maximizebutton, restorebutton, label);
		tabContainer.setTabBarControls(TabBarControls.TAB_SCROLLER, TabBarControls.TAB_PICKER, topToolBarStack);
	}  

	public TabSet getBottomContainer() {
		return bottomContainer;
	}
	
	public Canvas getBottomPane() {
		return bottomPaneLayout;
	}
	
	
	public Canvas getLeftPane() {
		return leftPane;
	}

	/**
	 * Retrieve a list of tabs from the Tab Set
	 * @return
	 */
	public Tab[] getTabs(){
		return tabContainer.getTabs();
	}
	
	/**
	 * Add a specified tab within the Tab Set
	 * @param newTab
	 */
	public void addTab(Tab newTab){
		tabContainer.addTab(newTab);
		tabContainer.selectTab(newTab);
	}
	
	/**
	 * Select the specified tab
	 * @param tab
	 */
	public void selectTab(Tab tab){
		tabContainer.selectTab(tab);
	}
	
	/**
	 * Get the currently selected Tab
	 * @return
	 */
	public Tab getSelectedTab(){
		return tabContainer.getSelectedTab(); 
	}
	
	/**
	 * Remove the specified tab
	 * @param tab
	 */
	public void removeTab(Tab tab) {
		tabContainer.removeTab(tab);
	}

	/**
	 * Get Properties Pane
	 * @return
	 */
	public PropertiesPane getPropertiesPane() {
		return propertiesPane;
	}
	

	public ProblemsPane getProblemsPane() {
		return problemsPane;
	}

	public DecisionTableAnalyzerPane getDecisionTableAnalyzerPane() {
		return decisionTableAnalyzerPane;
	}
	
	public HLayout getEditorPane() {
		return editorPane;
	}
	
	/**
	 * 
	 * @param decisionTableAnalyzerPane
	 */
	public void setDecisionTableAnalyzerPane(
			DecisionTableAnalyzerPane decisionTableAnalyzerPane) {
		this.decisionTableAnalyzerPane = decisionTableAnalyzerPane;
	}

	public HeaderControl getMaximizebutton() {
		return maximizebutton;
	}

	public HeaderControl getRestorebutton() {
		return restorebutton;
	}
	
	public void setBottomContainerTab(Tab tab) {
		bottomContainer.selectTab(tab);
	}
	
	public void showProblemPane() {
		bottomContainer.selectTab(problemsPane);
	}
	
	public PalettePane getPalettePane() {
		return palettePane;
	}

	public void setPalettePane(PalettePane palettePane) {
		this.palettePane = palettePane;
	}

	
}