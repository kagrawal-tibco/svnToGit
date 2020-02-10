package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
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
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.ui.util.StudioImages;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractFormPropertySection extends AbstractBpmnPropertySection {

	protected ManagedForm managedForm;
    protected SashForm sashForm;
    protected boolean useHelp = true;
	
    public AbstractFormPropertySection(){
    	super();
    }
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.ui.graph.properties.AbstractBpmnPropertySection#createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		if (useHelp) {
			Composite composite = getWidgetFactory().createComposite(parent);
			composite.setLayout(new FillLayout());

			managedForm = new ManagedForm(composite);
			final ScrolledForm form = managedForm.getForm();

			FormToolkit toolkit = managedForm.getToolkit();

			form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
			GridLayout layout = new GridLayout();
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			layout.numColumns = 1;
			form.getBody().setLayout(layout);

			sashForm = new MDSashForm(form.getBody(), SWT.HORIZONTAL);
			sashForm.setData("form", managedForm);
			toolkit.adapt(sashForm, false, false);
			sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));

			createPropertyPartControl(managedForm, sashForm);
			createHelpPartControl(managedForm,sashForm);

			sashForm.setWeights(new int[] {60 , 40});
		}
	}
	
	protected abstract void createPropertyPartControl(IManagedForm form , Composite parent);
	
	protected abstract void createHelpPartControl(IManagedForm form , Composite parent);
	
	protected class MDSashForm extends SashForm {
		@SuppressWarnings("rawtypes")
		ArrayList sashes = new ArrayList();
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

		@SuppressWarnings({ "rawtypes" })
		private void purgeSashes() {
			for (Iterator iter = sashes.iterator(); iter.hasNext();) {
				Sash sash = (Sash) iter.next();
				if (sash.isDisposed())
					iter.remove();
			}
		}
	}
	
	private void onSashPaint(Event e) {
		Sash sash = (Sash) e.widget;
		FormColors colors = getWidgetFactory().getColors();
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
}
