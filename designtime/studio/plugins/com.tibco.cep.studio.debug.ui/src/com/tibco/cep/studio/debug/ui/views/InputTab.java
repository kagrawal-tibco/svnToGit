package com.tibco.cep.studio.debug.ui.views;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;

public abstract class InputTab implements SelectionListener {
	static String DEFAULT_TOOLTIP_TEXT="";
	
	public static final String PROCESS_AGENT_RULE_SESSION = "ProcessAgent"; ///$NON-NLS-N$
	
	private RuleDebugInputView inputView;
	private String tabName;


	private Shell shell;


	private Display display;


	private Composite tabFolderPage;


	private CTabFolder tabFolder;
	
	private boolean fActive;
	
	protected Map<String, Map<String, String>> targetRuleSessionsMap = new HashMap<String, Map<String, String>>();
	protected Map<String, Boolean> targetAgentMap = new HashMap<String, Boolean>();
	
	/**
	 * the selected debug target
	 */
	private IRuleRunTarget fDebugTarget;
	
	public InputTab(String name) {
		setTabName(name);
	}

	public InputTab(RuleDebugInputView ruleDebugInputView,String name) {
		setInputView(ruleDebugInputView);
		setTabName(name);
	}

	public String getTooltipText() {
		return DEFAULT_TOOLTIP_TEXT;
	}

	public String getTabName() {
		return tabName;
	}
	
	

	/**
	 * @return the inputView
	 */
	public RuleDebugInputView getInputView() {
		return inputView;
	}

	/**
	 * @param inputView the inputView to set
	 */
	public void setInputView(RuleDebugInputView inputView) {
		this.inputView = inputView;
	}
	
	public boolean isActive() {
		return getInputView().isActive() && fActive;
	}
	
	public void setActive(boolean active) {
		this.fActive = active;
	}
	
	/**
	 * @return the fDebugTarget
	 */
	public IRuleRunTarget getDebugTarget() {
		return fDebugTarget;
	}



	/**
	 * @param debugTarget the fDebugTarget to set
	 */
	public void setDebugTarget(IRuleRunTarget debugTarget) {
		fDebugTarget = debugTarget;
	}
	
	
	/**
	 * @return the shell
	 */
	public Shell getShell() {
		return shell;
	}

	/**
	 * @param shell the shell to set
	 */
	public void setShell(Shell shell) {
		this.shell = shell;
	}

	/**
	 * @return the display
	 */
	public Display getDisplay() {
		return display;
	}

	/**
	 * @param display the display to set
	 */
	public void setDisplay(Display display) {
		this.display = display;
	}
	
	

	/**
	 * @return the tabFolder
	 */
	public CTabFolder getTabFolder() {
		return tabFolder;
	}

	/**
	 * @param tabFolder the tabFolder to set
	 */
	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

	/**
	 * @return the tabFolderPage
	 */
	public Composite getTabFolderPage() {
		return tabFolderPage;
	}

	/**
	 * @param tabFolderPage the tabFolderPage to set
	 */
	public void setTabFolderPage(Composite tabFolderPage) {
		this.tabFolderPage = tabFolderPage;
	}

	/**
	 * @param tabName the tabName to set
	 */
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public Control createTabFolderPage(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
		/* Cache the shell and display. */
		shell = tabFolder.getShell ();
		display = shell.getDisplay ();
		
		tabFolderPage = new Composite (tabFolder, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		GridData gridData = new GridData(SWT.DEFAULT,SWT.DEFAULT);
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		tabFolderPage.setLayoutData(gridData);
		tabFolderPage.setLayout (layout);
		tabFolderPage.setBackground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
//		tabFolderPage.setBackground(new Color(display,255,0,0));
		tabFolder.addSelectionListener(this);
		createControl(tabFolderPage);
		return tabFolderPage;
	}

	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		
	}

	protected abstract void handleDebugTargetCreate(IRuleRunTarget target) ;
	protected abstract void handleDebugTargetRemove(IRuleRunTarget target) ;

	abstract public Image getImage();

	public abstract void dispose();
	
	public abstract void refreshControls();
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
	}
}
