package com.tibco.cep.studio.ui.palette.views;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.IContributedContentsView;
import org.eclipse.ui.part.ViewPart;

import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.ui.palette.StudioPaletteUI;
import com.tibco.cep.studio.ui.palette.actions.PalettePresentationUpdater;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;

/**
 * 
 * @author sasahoo
 * 
 */
public class PaletteView extends ViewPart implements IContributedContentsView{

	public static final String ID = "com.tibco.cep.studio.palette.views.paletteview";

	private IWorkbenchWindow window;
	private FormToolkit toolkit;
	private Form form;
	private Palette palette;

	private ExpandBar rootExpandBar;
	private PalettePresentationUpdater listener = null;
	private PALETTE type;
	private String projectName;

	public PALETTE getType() {
		return type;
	}

	public void setType(PALETTE type) {
		this.type = type;
	}


	public void createPartControl(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createForm(parent);
		GridLayout layout = new GridLayout();
		form.getBody().setLayout(layout);
		//form.setBackground(new Color(null, 0, 0, 100));
		//form.setBackground(new Color(null, 128, 128, 128));
		form.setBackground(new Color(null, 255, 255, 255));

		rootExpandBar = new ExpandBar(form.getBody(), SWT.V_SCROLL);
		listener = new PalettePresentationUpdater(rootExpandBar);
		
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(rootExpandBar);
		
		rootExpandBar.addExpandListener(new ExpandListener() {
			
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ExpandListener#itemExpanded(org.eclipse.swt.events.ExpandEvent)
			 */
			@Override
			public void itemExpanded(ExpandEvent e) {
				layout();
			}
			
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ExpandListener#itemCollapsed(org.eclipse.swt.events.ExpandEvent)
			 */
			@Override
			public void itemCollapsed(ExpandEvent e) {
				layout();
			}
		});
		createInitialPalette();
	}
	private PaletteViewPartListener paletteViewPartListener;
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		window = site.getWorkbenchWindow();
		paletteViewPartListener = new PaletteViewPartListener();
		getViewSite().getPage().addPartListener(paletteViewPartListener);
	}

	public Palette getPalette() {
		return palette;
	}

	public void setPalette(Palette palette) {
		if (this.palette != null) {
			this.palette.removePaletteEntrySelectionChangedListener(listener);
		}
		this.palette = palette;
		updateExpandBar();
	}

	/**
	 * This should be done by the editor that wants to use the palette, but I'll
	 * add it here so that it functions as it did before the refactor.
	 */
	public void createInitialPalette() {
		palette = new Palette();
		palette.addPaletteEntrySelectionChangedListener(listener);
	}

	public void initializeExpandBar() {
		if (palette == null) {
			return;
		}
		for (PaletteDrawer drawer : palette.getPaletteDrawers()) {
			StudioPaletteUI.createDrawer(window, drawer, rootExpandBar,
					listener);
		}
	}

	public void updateExpandBar() {
		if (palette == null) {
			return;
		}
		ExpandItem[] items = rootExpandBar.getItems();
		for (ExpandItem item : items) {
			item.dispose();
		}
		Control[] children = rootExpandBar.getChildren();
		for (Control control : children) {
			control.dispose();
		}
		initializeExpandBar();
		
		layout();
	}

	public void layout() {
		rootExpandBar.layout(true);
		form.layout(true);
	}
	
	/**
	 * Passing the focus request to the form.
	 */
	public void setFocus() {
		form.setFocus();
	}

	/**
	 * Disposes the toolkit
	 */
	public void dispose() {
		toolkit.dispose();
		super.dispose();
		if(paletteViewPartListener != null){
			getViewSite().getPage().removePartListener(paletteViewPartListener);
		}
	}

	public Form getForm() {
		return form;
	}

	public ExpandBar getRootExpandBar() {
		return rootExpandBar;
	}

	public PalettePresentationUpdater getListener() {
		return listener;
	}

	@Override
	public IWorkbenchPart getContributingPart() {
		return getSite().getPage().getActiveEditor();
	}
	
	public void setCursor(final boolean busy) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if(busy) {
					getViewSite().getShell().setCursor(new org.eclipse.swt.graphics.Cursor(getViewSite().getShell().getDisplay(), SWT.CURSOR_WAIT));
				} else {
					getViewSite().getShell().setCursor(new org.eclipse.swt.graphics.Cursor(getViewSite().getShell().getDisplay(), SWT.CURSOR_ARROW));
				}
			}
		});
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}