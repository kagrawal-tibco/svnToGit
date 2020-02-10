package com.tibco.cep.diagramming.actions;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.utils.DiagramUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class DiagramZoomComboContributionItem extends ContributionItem implements IZoomListener{
	
	private boolean forceSetText;
	private Combo combo;
	private String[] items;
	private ToolItem toolitem;
	private DiagramZoomManager zoomManager;
	private IPartService service;
	private IPartListener partListener;

	/**
	 *
	 * @param partService 
	 */
	public DiagramZoomComboContributionItem(IPartService partService) {
		this(partService, "8888%");
	}

	/**
	 * 
	 * @param partService
	 * @param items
	 */
	public DiagramZoomComboContributionItem(IPartService partService, String items) {
		this(partService, new String[] {items});
	}

	/**
	 * 
	 * @param partService
	 * @param items
	 */
	public DiagramZoomComboContributionItem(IPartService partService, String[] items) {
		super("com.tibco.tomswayer.diagram.zoom.widget");
		this.items = items;
		service = partService;
		Assert.isNotNull(partService);
		partService.addPartListener(partListener = new IPartListener() {
			/* (non-Javadoc)
			 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
			 */
			public void partActivated(IWorkbenchPart part) {
				try{
				if(getZoomManager()==null){
					setZoomManager(new DiagramZoomManager());
				}
				if(((IWorkbenchPage)service).getActivePart() instanceof IGraphDrawing){
					Combo zoomCombo = (Combo)DiagrammingPlugin.getDefault().getMap().get("ZOOM");
					if(zoomCombo!=null && !zoomCombo.isDisposed() && zoomCombo.isEnabled() == false){
						zoomCombo.setEnabled(true);
						zoomCombo.setText(DiagramUtils.getCurrentZoomLevel((IEditorPart)((IWorkbenchPage)service).getActivePart()));
					}
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			/* (non-Javadoc)
			 * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.IWorkbenchPart)
			 */
			public void partBroughtToTop(IWorkbenchPart p) {}
			/* (non-Javadoc)
			 * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
			 */
			public void partClosed(IWorkbenchPart p) { }
			/* (non-Javadoc)
			 * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
			 */
			public void partDeactivated(IWorkbenchPart p) {
				Combo zoomCombo = (Combo)DiagrammingPlugin.getDefault().getMap().get("ZOOM");
				if(zoomCombo!=null && !zoomCombo.isDisposed()){
					zoomCombo.setEnabled(false);
				}
			}
			/* (non-Javadoc)
			 * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart)
			 */
			public void partOpened(IWorkbenchPart p) { }
		});
	}

	/**
	 * @param repopulateCombo
	 */
	private void refresh(boolean repopulateCombo) {
		if (combo == null || combo.isDisposed())
			return;
		try {
			if (zoomManager == null) {
				combo.setEnabled(false);
				combo.setText(""); 
			} else {
				if (repopulateCombo)
					combo.setItems(getZoomManager().getZoomLevelsAsText());
				String zoom = getZoomManager().getZoomAsText();
				int index = combo.indexOf(zoom);
				if (index == -1 || forceSetText)
					combo.setText(zoom);
				else
					combo.select(index);
				combo.setEnabled(true);
				DiagrammingPlugin.getDefault().getMap().clear();
				DiagrammingPlugin.getDefault().getMap().put("ZOOM", combo);
			}
		} catch (SWTException exception) {
				throw exception;
		}
	}

	
	/**
	 * @param control
	 * @return
	 */
	protected int computeWidth(Control control) {
		return control.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x + 20;
	}

	
	/**
	 * @param parent
	 * @return
	 */
	protected Control createControl(Composite parent) {
		combo = new Combo(parent, SWT.DROP_DOWN |SWT.BORDER);
		combo.addSelectionListener(new SelectionListener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				handleWidgetSelected(e);
			}
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetDefaultSelected(SelectionEvent e) {
				handleWidgetDefaultSelected(e);
			}
		});
		combo.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				// do nothing
			}
			public void focusLost(FocusEvent e) {
				refresh(false);
			}
		});

		// Initialize width of combo
		combo.setItems(items);
		toolitem.setWidth(computeWidth(combo));
		refresh(true);
		return combo;
	}

	/**
	 * @see org.eclipse.jface.action.ContributionItem#dispose()
	 */
	public void dispose() {
		if (partListener == null)
			return;
		service.removePartListener(partListener);
		if (zoomManager != null) {
			zoomManager.removeZoomListener(this);
			zoomManager = null;
		}
		combo = null;
		partListener = null;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.ContributionItem#fill(org.eclipse.swt.widgets.Composite)
	 */
	public final void fill(Composite parent) {
		createControl(parent);
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.ContributionItem#fill(org.eclipse.swt.widgets.Menu, int)
	 */
	public final void fill(Menu parent, int index) {
		Assert.isTrue(false, "Can't add a control to a menu");//$NON-NLS-1$
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.ContributionItem#fill(org.eclipse.swt.widgets.ToolBar, int)
	 */
	public void fill(ToolBar parent, int index) {
		toolitem = new ToolItem(parent, SWT.SEPARATOR, index);
		Control control = createControl(parent);
		toolitem.setControl(control);	
	}

	/**
	 *
	 * @return ZoomManager
	 */
	public DiagramZoomManager getZoomManager() {
		return zoomManager;
	}

	/**
	 * 
	 * @param zm The ZoomManager
	 */
	public void setZoomManager(DiagramZoomManager zm) {
		if (zoomManager == zm)
			return;
		if (zoomManager != null)
			zoomManager.removeZoomListener(this);

		zoomManager = zm;
		refresh(true);

		if (zoomManager != null)
			zoomManager.addZoomListener(this);
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(SelectionEvent)
	 */
	private void handleWidgetDefaultSelected(SelectionEvent event) {
		if (zoomManager != null) {
			if (combo.getSelectionIndex() >= 0)
				zoomManager.setZoomAsText(combo.getItem(combo.getSelectionIndex()));
			else
				zoomManager.setZoomAsText(combo.getText());
		}
		refresh(false);
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(SelectionEvent)
	 */
	private void handleWidgetSelected(SelectionEvent event) {
		forceSetText = true;
		handleWidgetDefaultSelected(event);
		forceSetText = false;
	}

	/**
	 * @see IZoomListener#zoomChanged(double)
	 */
	public void zoomChanged(double zoom) {
	    DiagramUtils.zoomWindow((IWorkbenchPage)service, zoom);
		refresh(false);
	}

}
