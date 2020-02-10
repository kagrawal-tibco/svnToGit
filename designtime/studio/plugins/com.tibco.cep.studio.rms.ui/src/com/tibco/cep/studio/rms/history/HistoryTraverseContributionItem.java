package com.tibco.cep.studio.rms.history;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
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

/**
 * 
 * @author sasahoo
 *
 */
public class HistoryTraverseContributionItem extends ContributionItem {

	private Combo combo;
	private String[] items;
	private ToolItem toolitem;
	private String defaultValue;

	/**
	 * @param items
	 * @param defaultValue
	 */
	public HistoryTraverseContributionItem(String[] items, String defaultValue) {
		super("com.tibco.cep.studio.rms.history.combo.widget");
		this.items = items;
		this.defaultValue = defaultValue;
	}

	/**
	 * @param parent
	 * @return
	 */
	protected Control createControl(Composite parent) {
		combo = new Combo(parent, SWT.DROP_DOWN |SWT.BORDER | SWT.READ_ONLY);
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
		combo.setText("50");
		toolitem.setWidth(computeWidth(combo));
		refresh(true);
		return combo;
	}

	/**
	 * @see org.eclipse.jface.action.ContributionItem#dispose()
	 */
	public void dispose() {
		combo = null;
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
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(SelectionEvent)
	 */
	private void handleWidgetDefaultSelected(SelectionEvent event) {
		refresh(false);
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(SelectionEvent)
	 */
	private void handleWidgetSelected(SelectionEvent event) {
		handleWidgetDefaultSelected(event);
	}

	/**
	 * @return
	 */
	public String getValue() {
		if (combo != null && !combo.isDisposed()) {
			return combo.getText();
		}
		return defaultValue;
	}

	/**
	 * @param val
	 */
	public void setValue (String val) {
		this.defaultValue = val;
	}

	/**
	 * @param repopulateCombo
	 */
	private void refresh(boolean repopulateCombo) {
		//TODO
	}

	/**
	 * @param control
	 * @return
	 */
	protected int computeWidth(Control control) {
		return control.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x + 10;
	}
}