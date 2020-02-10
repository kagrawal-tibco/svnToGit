package com.tibco.cep.studio.tester.ui.editor;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.cep.studio.ui.util.StudioImages;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractSashForm {

	protected ManagedForm managedForm;
    protected SashForm sashForm;
    protected static final Font DEFAULT_FONT = new Font(null, new FontData("Tahoma", 18, SWT.BOLD));

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
		Action haction = new Action("hor", Action.AS_RADIO_BUTTON) {
			public void run() {
				if(isChecked()){
					sashForm.setOrientation(SWT.HORIZONTAL);
					form.reflow(true);
				}
			}
		};
		haction.setChecked(false);
		haction.setToolTipText("Horizontal orientation");
		haction.setImageDescriptor(StudioImages.getImageDescriptor(StudioImages.IMG_HORIZONTAL));
		Action vaction = new Action("ver", Action.AS_RADIO_BUTTON) {
			public void run() {
				if(isChecked()){
					sashForm.setOrientation(SWT.VERTICAL);
					form.reflow(true);
				}
			}
		};
		vaction.setChecked(true);
		vaction.setToolTipText("Vertical orientation");
		vaction.setImageDescriptor(StudioImages.getImageDescriptor(StudioImages.IMG_VERTICAL));
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

		@SuppressWarnings("rawtypes")
		private void purgeSashes() {
			for (Iterator iter = sashes.iterator(); iter.hasNext();) {
				Sash sash = (Sash) iter.next();
				if (sash.isDisposed())
					iter.remove();
			}
		}
	}
	
	public ScrolledForm getForm(){
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
	
//	@SuppressWarnings("serial")
//	protected Container getSwingContainer(Composite parent) {
//		java.awt.Frame frame = SWT_AWT.new_Frame(parent);
//		new SyncXErrorHandler().installHandler();
//		Panel panel = new Panel(new BorderLayout()) {
//			public void update(java.awt.Graphics g) {
//				paint(g);
//			}
//		};
//		frame.add(panel);
//		JRootPane root = new JRootPane();
//		panel.add(root);
//		return root.getContentPane();
//	}
}
