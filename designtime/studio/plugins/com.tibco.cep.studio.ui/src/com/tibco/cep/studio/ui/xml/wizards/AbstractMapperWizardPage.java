package com.tibco.cep.studio.ui.xml.wizards;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JRootPane;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.diagramming.utils.SyncXErrorHandler;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.xml.utils.MapperUtils;
import com.tibco.cep.studio.ui.xml.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractMapperWizardPage extends WizardPage implements IMapperConstants{

	// TODO: get the project!
	protected EMFTnsCache cache;
	
	protected SashForm sashForm;
	protected Section functionSection;
	protected Section inputSection;
	protected Section xsltTemplateSection;

	protected ManagedForm managedForm;
	protected FormToolkit toolkit;
	
	protected AbstractMapperWizardPage(String pageTitle, String projectName) {
		super(pageTitle);
		 cache = StudioCorePlugin.getCache(projectName);
	}
	/**
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
    	managedForm = new ManagedForm(parent);
    	toolkit = managedForm.getToolkit();
		final ScrolledForm form = managedForm.getForm();
//		form.setBackgroundImage(XmlUIPlugin.getImageDescriptor("icons/form_banner.gif").createImage());
		FormToolkit toolkit = managedForm.getToolkit();
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		form.getBody().setLayout(layout);
		sashForm = new SashForm(form.getBody(), SWT.VERTICAL);
		sashForm.setData("form", managedForm);
		toolkit.adapt(sashForm, false, false);
		sashForm.setMenu(form.getBody().getMenu());
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 500;
		sashForm.setLayoutData(gd);
		createFunctionPart(managedForm, sashForm);
		createInputPart(managedForm, sashForm);
		createXSLTTemplatePart(managedForm, sashForm);
		setControl(form);
		setPageComplete(true);
		sashForm.setWeights(new int[] { WSfn, WSin});
		// createToolBarActions();
		MapperSectionsExpansionListener listener = new MapperSectionsExpansionListener(this);
		inputSection.addExpansionListener(listener);
		functionSection.addExpansionListener(listener);
		
		// Workaround for Linux Platforms
		// BE-22214: BEStudio : CentOS 7 : Shared Resources are blank when created, 
		// user needs to close/re-open the editors
		MapperUtils.refreshMapperShell(getShell());
		
	}
	
	@SuppressWarnings("serial")
	protected Container getSwingContainer(Composite parent) {
		java.awt.Frame frame = SWT_AWT.new_Frame(parent);
		new SyncXErrorHandler().installHandler();
		Panel panel = new Panel(new BorderLayout()) {
			public void update(java.awt.Graphics g) {
				paint(g);
			}
		};
		frame.add(panel);
		JRootPane root = new JRootPane();
		panel.add(root);
		return root.getContentPane();
	}
	
	 /**
     * Resize handler method
     */
    protected void hookResizeListener() {
		Listener listener = ((MDSashForm) sashForm).listener;
		Control[] children = sashForm.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Sash)
				continue;
			children[i].addListener(SWT.Resize, listener);
		}
	}
    
    /**
     * Defining Orientation Tool bar.
     */
	protected void createToolBarActions() {
		final ScrolledForm form = getForm();
		Action haction = new Action("", Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.HORIZONTAL);
				sashForm.setWeights(new int[] { 50, 40});
				form.reflow(true);
			}
		};
		haction.setChecked(false);
		haction.setToolTipText(Messages.getString("form.hor.orientation"));
		haction.setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/th_horizontal.gif"));
		Action vaction = new Action("", Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.VERTICAL);
				sashForm.setWeights(new int[] {  WSfn, WSin});
				form.reflow(true);
			}
		};
		vaction.setChecked(true);
		vaction.setToolTipText(Messages.getString("form.ver.orientation"));
		vaction.setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/th_vertical.gif"));
		form.getToolBarManager().add(haction);
		form.getToolBarManager().add(vaction);
		
		form.updateToolBar();
	}
	/**
	 * Sash form class
	 * @author sasahoo
	 *
	 */
	protected class MDSashForm extends SashForm {
		List sashes = new ArrayList();
		Listener listener = new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.MouseEnter:
					e.widget.setData("hover", Boolean.TRUE); //$NON-NLS-1$
					((Control) e.widget).redraw();
					break;
				case SWT.MouseExit:
					e.widget.setData("hover", null); //$NON-NLS-1$
					((Control) e.widget).redraw();
					break;
				case SWT.Paint:
					onSashPaint(e);
					break;
				case SWT.Resize:
					hookSashListeners();
					break;
				}
			}
		};
        /**
         * 
         * @param parent
         * @param style
         */
		public MDSashForm(Composite parent, int style) {
			super(parent, style);
		}

		public void layout(boolean changed) {
			super.layout(changed);
			hookSashListeners();
		}

		public void layout(Control[] children) {
			super.layout(children);
			hookSashListeners();
		}

		@SuppressWarnings("unchecked")
		private void hookSashListeners() {
			purgeSashes();
			Control[] children = getChildren();
			for (int i = 0; i < children.length; i++) {
				if (children[i] instanceof Sash) {
					Sash sash = (Sash) children[i];
					if (sashes.contains(sash))
						continue;
					sash.addListener(SWT.Paint, listener);
					sash.addListener(SWT.MouseEnter, listener);
					sash.addListener(SWT.MouseExit, listener);
					sashes.add(sash);
				}
			}
		}

		private void purgeSashes() {
			for (Iterator iter = sashes.iterator(); iter.hasNext();) {
				Sash sash = (Sash) iter.next();
				if (sash.isDisposed())
					iter.remove();
			}
		}
	}
	
	protected ScrolledForm getForm(){
		return managedForm.getForm();
	}
	
	/**
	 * This method handles the resize action.
	 * @param e
	 */
	private void onSashPaint(Event e) {
		Sash sash = (Sash) e.widget;
		IManagedForm form = (IManagedForm) sash.getParent().getData("form"); //$NON-NLS-1$
		FormColors colors = form.getToolkit().getColors();
		boolean vertical = (sash.getStyle() & SWT.VERTICAL) != 0;
		GC gc = e.gc;
		Boolean hover = (Boolean) sash.getData("hover"); //$NON-NLS-1$
		gc.setBackground(colors.getColor(IFormColors.TB_BG));
		gc.setForeground(colors.getColor(IFormColors.TB_BORDER));
		Point size = sash.getSize();
		if (vertical) {
			if (hover != null)
				gc.fillRectangle(0, 0, size.x, size.y);
			// else
			// gc.drawLine(1, 0, 1, size.y-1);
		} else {
			if (hover != null)
				gc.fillRectangle(0, 0, size.x, size.y);
			// else
			// gc.drawLine(0, 1, size.x-1, 1);
		}
	}
	public Control getControl() {
		return getForm();
	}
	
	public Section getFunctionSection() {
		return functionSection;
	}
	
	public Section getInputSection() {
		return inputSection;
	}

	public Section getXsltTemplateSection() {
		return xsltTemplateSection;
	}
		
	public SashForm getSashForm() {
		return sashForm;
	}

	 /**
     * 
     * @param managedForm
     * @param parent
     */
    protected abstract void createFunctionPart(final IManagedForm managedForm, Composite parent);
    
	 /**
     * 
     * @param managedForm
     * @param parent
     */
    protected abstract void createInputPart(final IManagedForm managedForm, Composite parent);
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected abstract void createXSLTTemplatePart(final IManagedForm managedForm, Composite parent);
}
