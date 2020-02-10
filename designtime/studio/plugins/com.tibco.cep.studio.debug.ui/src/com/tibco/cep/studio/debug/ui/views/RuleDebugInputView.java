package com.tibco.cep.studio.debug.ui.views;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.ViewPart;

import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;
@SuppressWarnings({"unused"})
public class RuleDebugInputView extends ViewPart implements IPartListener2,IDebugEventSetListener {
	public static final String RULE_INPUT_VIEW_ID = "com.tibco.cep.studio.debug.ui.inputView";

	
	private Composite control;	

		/**
	 * debug target to rule sessions map
	 */
	private Map<IDebugTarget, Map<String,String>> ruleSessionMap = Collections
			.synchronizedMap(new HashMap<IDebugTarget, Map<String,String>>());
	
	private boolean fIsActive;
	
	private CTabFolder tabFolder;

	private InputTab[] inputTabs;
	
	/**
	 * constructor
	 */
	public RuleDebugInputView() {
	}
	
	
	
	/**
	 * @return the ruleSessions
	 */
	public Map<String,String> getRuleSessions(IDebugTarget target) {
		return ruleSessionMap.get(target);
	}


	/**
	 * @return the ruleSessionMap
	 */
	public Map<IDebugTarget, Map<String,String>> getRuleSessionMap() {
		return ruleSessionMap;
	}
	
	
    	

	/**
	 * @return the inputTabs
	 */
	public InputTab[] getInputTabs() {
		return inputTabs;
	}



	/**
	 * @param inputTabs the inputTabs to set
	 */
	public void setInputTabs(InputTab[] inputTabs) {
		this.inputTabs = inputTabs;
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
	 * @return the control
	 */
	public Composite getControl() {
		return control;
	}

	/**
	 * @param control the control to set
	 */
	public void setControl(Composite control) {
		this.control = control;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	public void dispose() {
		getSite().getPage().removePartListener(this);
		DebugPlugin.getDefault().removeDebugEventListener(this);
		for(InputTab tab: getInputTabs()) {
			tab.dispose();
		}
		super.dispose();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(final Composite comp) {

		Composite parent = new Composite(comp, SWT.FILL);
		parent.setLayout(new GridLayout());
		
		DebugPlugin.getDefault().addDebugEventListener(this);
		parent.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		tabFolder = new CTabFolder(parent,SWT.NONE|SWT.BOTTOM);
		GridData gd = new GridData(SWT.DEFAULT, SWT.DEFAULT); 
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		GridLayout gl = new GridLayout();
		gl.marginWidth = 0;
		tabFolder.setLayout(gl);
		tabFolder.setLayoutData (gd);
		tabFolder.setBorderVisible(true);
//		tabFolder.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		// Set up a gradient background for the selected tab
	    tabFolder.setSelectionBackground(new Color[]{parent.getDisplay().getSystemColor(SWT.COLOR_WHITE), 
	    		parent.getDisplay().getSystemColor(SWT.COLOR_WHITE),
	    		parent.getDisplay().getSystemColor(SWT.COLOR_WHITE), 
	    		parent.getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT)},
	    		new int[] {20, 50, 100},true);
	    
//	    Display display = parent.getDisplay();
//
//	    Color titleForeColor = display.getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
//	    Color titleBackColor1 = display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
//	    Color titleBackColor2 = display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
//
//	    tabFolder.setSelectionForeground(titleForeColor);
//	    tabFolder.setSelectionBackground(new Color[] {titleBackColor1,titleBackColor2}, new int[] {100}, true); 
		inputTabs = createTabs();
		for (int i = 0; i < inputTabs.length; i++) {
			CTabItem item = new CTabItem(tabFolder, SWT.NONE);
			item.setText(inputTabs[i].getTabName());
			item.setImage(inputTabs[i].getImage());
			item.setToolTipText(inputTabs[i].getTooltipText());
			item.setControl (inputTabs [i].createTabFolderPage(tabFolder));
		    item.setData (inputTabs [i]);
		}
		setControl(tabFolder);
		tabFolder.pack();
		getSite().getPage().addPartListener(this);
	}

	/**
	 * @return
	 */
	private InputTab[] createTabs() {
		return StudioDebugInputUtils.getDebugInputTabs(this);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		control.setFocus();
	}

	/**
	 * @param page
	 * @param perspective
	 */
	public void perspectiveActivated(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
		setActive(page.findView(getSite().getId()) != null);
		// updateObjects();
	}

	/**
	 * @param page
	 * @param perspective
	 * @param changeId
	 */
	public void perspectiveChanged(IWorkbenchPage page,
			IPerspectiveDescriptor perspective, String changeId) {
		setActive(page.findView(getSite().getId()) != null);
	}

	/**
	 * @param page
	 * @param perspective
	 * @param partRef
	 * @param changeId
	 */
	public void perspectiveChanged(IWorkbenchPage page,
			IPerspectiveDescriptor perspective,
			IWorkbenchPartReference partRef, String changeId) {
	}

	/**
	 * @param page
	 */
	public void pageActivated(IWorkbenchPage page) {
		if (getSite().getPage().equals(page)) {
			setActive(true);
		}
	}

	/**
	 * @param page
	 */
	public void pageClosed(IWorkbenchPage page) {
	}

	/**
	 * @param page
	 */
	public void pageOpened(IWorkbenchPage page) {
	}

	/**
	 * Sets whether this view is in the active page of a perspective. Since a
	 * page can have more than one perspective, this view only show's source
	 * when in the active perspective/page.
	 * 
	 * @param active
	 *            whether this view is in the active page of a perspective
	 */
	protected void setActive(boolean active) {
		fIsActive = active;
	}
	
	protected boolean isActive() {
		return fIsActive;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener2#partClosed(org.eclipse.ui.IWorkbenchPartReference)
	 */
	public void partClosed(IWorkbenchPartReference partRef) {
		IWorkbenchPart part = partRef.getPart(false);
		if (part == this) {
			setActive(false);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener2#partVisible(org.eclipse.ui.IWorkbenchPartReference)
	 */
	public void partVisible(IWorkbenchPartReference partRef) {
		IWorkbenchPart part = partRef.getPart(false);
		if (part == this) {
			setActive(true);
		}
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener2#partOpened(org.eclipse.ui.IWorkbenchPartReference)
	 */
	public void partOpened(IWorkbenchPartReference partRef) {
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener2#partActivated(org.eclipse.ui.IWorkbenchPartReference)
	 */
	public void partActivated(IWorkbenchPartReference partRef) {
		IWorkbenchPart part = partRef.getPart(false);
		ILaunch[] launches = DebugPlugin.getDefault().getLaunchManager().getLaunches();
		for(ILaunch launch:launches) {
			IDebugTarget[] targets = launch.getDebugTargets();
			for(IDebugTarget target: targets) {
				if(!target.isTerminated() && !target.isDisconnected()){
					if(target.getModelIdentifier().equals(RuleDebugModel.getModelIdentifier())) {
						final IRuleRunTarget rTarget = (IRuleRunTarget) target;
						if(!getRuleSessionMap().containsKey(target)) {
							Map<String,String> sessions = ((IRuleRunTarget)target).getRuleSessionMap();
							if(sessions != null && sessions.size() > 0){
								getRuleSessionMap().put(rTarget,((IRuleRunTarget)rTarget).getRuleSessionMap());
								for(InputTab tab:getInputTabs()) {
									tab.handleDebugTargetCreate(rTarget);
								}
							}
						} else {
							for(InputTab tab:getInputTabs()) {
								tab.refreshControls();
							}
						}
					}
				}
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener2#partBroughtToTop(org.eclipse.ui.IWorkbenchPartReference)
	 */
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener2#partDeactivated(org.eclipse.ui.IWorkbenchPartReference)
	 */
	public void partDeactivated(IWorkbenchPartReference partRef) {
		IWorkbenchPart part = partRef.getPart(false);
		if (part == this) {
			setActive(false);
		}
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener2#partHidden(org.eclipse.ui.IWorkbenchPartReference)
	 */
	public void partHidden(IWorkbenchPartReference partRef) {
		IWorkbenchPart part = partRef.getPart(false);
		if (part == this) {
			setActive(false);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener2#partInputChanged(org.eclipse.ui.IWorkbenchPartReference)
	 */
	public void partInputChanged(IWorkbenchPartReference partRef) {
	}
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.IDebugEventSetListener#handleDebugEvents(org.eclipse.debug.core.DebugEvent[])
	 */
	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		for (int i = 0; i < events.length; i++) {
			DebugEvent event = events[i];
			if (event.getSource() instanceof IRuleRunTarget) {

				final IRuleRunTarget target = (IRuleRunTarget) event.getSource();
				if (target.getModelIdentifier().equals(
						RuleDebugModel.getModelIdentifier())) {
					if (event.getKind() == DebugEvent.CREATE) {
						getRuleSessionMap().put(target,target.getRuleSessionMap());
						for(InputTab tab:getInputTabs()) {
							tab.handleDebugTargetCreate(target);
						}
					} else if(event.getKind() == DebugEvent.TERMINATE) {
						if (getRuleSessionMap().containsKey(target)) {
							getRuleSessionMap().remove(target);
							for(InputTab tab:getInputTabs()) {
								tab.handleDebugTargetRemove(target);
							}
						}
						
					}
					
				}

			}
		}

	}
}