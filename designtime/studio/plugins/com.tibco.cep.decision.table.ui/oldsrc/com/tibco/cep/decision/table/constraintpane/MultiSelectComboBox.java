package com.tibco.cep.decision.table.constraintpane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;


/**
 * @author vdhumal
 *
 */
public class MultiSelectComboBox extends Composite {

	private Shell dropDownPopup;
	private Label textLabel;
	private Button arrowButton;
	private List<Object> elementsList;
	private List<Object> selectionList;
	private Composite buttonComposite;
	private Button okButton;
	private Button cancelButton;
	private Listener listener;
	private String separator;
	private Table table;
	public static int DEFAULT_TABLE_HEIGHT = 8; //8 items 
	
	/**
	 * @param parent
	 * @param style
	 * @param elementsList
	 */
	public MultiSelectComboBox(Composite parent, int style, List<Object> elementsList) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.horizontalSpacing = gridLayout.verticalSpacing = 0; 
		gridLayout.marginWidth = gridLayout.marginHeight = 0;
		setLayout(gridLayout);
        GridData gridData = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
        gridData.widthHint = 200;
        setLayoutData(gridData);
		
		textLabel = new Label(this, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
		textLabel.setBackground(this.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		GridData txtLblGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		txtLblGridData.heightHint = 18;
		textLabel.setLayoutData(txtLblGridData);
		
		arrowButton = new Button(this, SWT.ARROW | SWT.DOWN);
		arrowButton.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));

		selectionList = new ArrayList<Object>();
		this.elementsList = elementsList;
		this.separator = "; ";

		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				if (event.widget == textLabel) {
					handleTextLabelEvent(event);
					return;
				}
				if (event.widget instanceof Button) {
					handleButtonEvent(event);
					return;
				}
				if (event.widget == dropDownPopup) {
					handleDropDownPopupEvent(event);
					return;
				}
				if (MultiSelectComboBox.this == event.widget) {
					multiSelectComboBoxEvent(event);
					return;
				}
			}
		};

		arrowButton.addListener(SWT.Selection, listener);		
		int[] buttonEvents = { SWT.MouseDown};
		for (int i = 0; i < buttonEvents.length; i++) {
			textLabel.addListener(buttonEvents[i], listener);
		}
		
		createDropDownPopup();
		setSelectedValues();
		textLabel.setData("ORIGINAL_TEXT", textLabel.getText());
	}

	
	/**
	 * Create drop-down pop-up box
	 */
	private void createDropDownPopup() {
		dropDownPopup = new Shell(getShell(), SWT.NO_TRIM | SWT.ON_TOP);
		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.horizontalSpacing = 0; 
		gridLayout.verticalSpacing = 0;
		gridLayout.marginLeft = gridLayout.marginRight = 0;
		dropDownPopup.setLayout(gridLayout);
		final Color white = getDisplay().getSystemColor(SWT.COLOR_WHITE);		
		dropDownPopup.setBackground(white);

		final int[] popupEvents = { SWT.Close, SWT.Paint, SWT.Deactivate, SWT.Dispose };
		for (int i = 0; i < popupEvents.length; i++) {
			dropDownPopup.addListener(popupEvents[i], listener);
		}

		if (elementsList == null) {
			return;
		}

	    table = new Table(dropDownPopup, SWT.CHECK | SWT.V_SCROLL | SWT.H_SCROLL);

	    rebuildTable();
	    
	    table.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setSelectedValues();				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				setSelectedValues();				
			}
		}); 
	    
	    final int[] tableEvents = { SWT.MouseDown };
		for (int i = 0; i < tableEvents.length; i++) {
		    table.addListener(tableEvents[i], new Listener() {			
				@Override
				public void handleEvent(Event event) {
					Point pt = new Point(event.x, event.y);
					for (final TableItem tableItem : table.getItems()) {
						Rectangle rect = tableItem.getBounds(0);
			            if (rect.contains(pt)) {
			            	if (tableItem.getChecked())
			            		tableItem.setChecked(false);
			            	else
			            		tableItem.setChecked(true);
			            }
					}					
				}
			});
		}
		
		buttonComposite = new Composite(dropDownPopup, SWT.BORDER);
		GridData gridData2 = new GridData(SWT.FILL, SWT.BOTTOM, true, true);
		gridData2.heightHint = 40;
		buttonComposite.setLayoutData(gridData2);				
		buttonComposite.setLayout(new FormLayout());
					
		okButton = new Button(buttonComposite, SWT.PUSH);		
		okButton.setText("OK");
		okButton.addListener(SWT.Selection, listener);
		FormData formData1 = new FormData();
		formData1.top = new FormAttachment(1, 5, 0);
		formData1.right = new FormAttachment(100, -80);
		formData1.width = 70;
		formData1.height = 25;
		okButton.setLayoutData(formData1);
		
		cancelButton = new Button(buttonComposite, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.addListener(SWT.Selection, listener);
		FormData formData2 = new FormData();
		formData2.top = new FormAttachment(1, 5, 0);
		formData2.right = new FormAttachment(100, -5);
		formData2.width = 70;
		formData2.height = 25;
		cancelButton.setLayoutData(formData2);
		
		dropDownPopup.layout();
	}	

	
	/**
	 * Handle text label events
	 * @param event
	 */
	private void handleTextLabelEvent(final Event event) {
		switch (event.type) {
			case SWT.FocusIn: {
				//handleFocus(SWT.FocusIn);
				break;
			}
			case SWT.MouseDown: {
				dropDownBox(!isDroppedDown());
				break;
			}
		}
	}
	
	
	/**
	 * Handle button events
	 * @param event
	 */
	private void handleButtonEvent(Event event) {
		if (event.widget == arrowButton) {
			switch (event.type) {
				case SWT.FocusIn: {
					//handleFocus(SWT.FocusIn);
					break;
				}
				case SWT.Selection: {
					dropDownBox(!isDroppedDown());
					break;
				}
			}
		}
		else if (event.widget == okButton) {
			switch (event.type) {
				case SWT.FocusIn: {
					//handleFocus(SWT.FocusIn);
					break;
				}
				case SWT.Selection: {
					selectionList.clear();
					for (final TableItem tableItem : table.getItems()) {
						if (tableItem.getChecked()) {
							selectionList.add(tableItem.getData());
						}	
					}
					textLabel.setData("ORIGINAL_TEXT", textLabel.getText());
					dropDownBox(false);
					break;
				}
			}
		}
		else if (event.widget == cancelButton) {
			switch (event.type) {
				case SWT.FocusIn: {
					//handleFocus(SWT.FocusIn);
					break;
				}
				case SWT.Selection: {
					textLabel.setText((String)textLabel.getData("ORIGINAL_TEXT"));
					dropDownBox(false);
					break;
				}
			}
		}		
	}
		
	
	/**
	 * Handle events on pop-up
	 * @param event
	 */
	private void handleDropDownPopupEvent(final Event event) {
		switch (event.type) {
			case SWT.Paint:
				Rectangle listRect = dropDownPopup.getBounds();
				Color black = getDisplay().getSystemColor(SWT.COLOR_BLACK);
				event.gc.setForeground(black);
				event.gc.drawRectangle(0, 0, listRect.width - 1, listRect.height - 1);
				break;
			case SWT.Close:
				event.doit = false;
				dropDownBox(false);
				break;
			case SWT.Deactivate:
				textLabel.setText((String)textLabel.getData("ORIGINAL_TEXT"));
				dropDownBox(false);				
				break;
			case SWT.Dispose:
				if (table != null) {
					table.dispose();
				}
				table = null;				
				break;
		}
	}

	
	/**
	 * Handle events on multi-select combo
	 * @param event
	 */
	private void multiSelectComboBoxEvent(final Event event) {
		switch (event.type) {
			case SWT.Dispose:
				if (dropDownPopup != null && !dropDownPopup.isDisposed()) {
					dropDownPopup.dispose();
				}
				Shell shell = getShell();
				shell.removeListener(SWT.Deactivate, listener);
				dropDownPopup = null;
				arrowButton = null;
				break;
			case SWT.Move:
				dropDownBox(false);
				break;
			case SWT.Resize:
				if (isDroppedDown()) {
					dropDownBox(false);
				}
				break;
		}
	}

	
	/**
	 * @return is dropped down
	 */
	private boolean isDroppedDown() {
		return !dropDownPopup.isDisposed() && dropDownPopup.getVisible();
	}

	
	/**
	 * @param dropDown
	 */
	private void dropDownBox(final boolean dropDown) {
		if (dropDown == isDroppedDown()) {
			return;
		}
		if (!dropDown) {
			dropDownPopup.setVisible(false);
			if (!isDisposed()) {
				textLabel.setFocus();
//				if (textLabel == event.widget) {
//					selectionList.clear();
//					for (final TableItem tableItem : table.getItems()) {
//						if (tableItem.getChecked()) {
//							selectionList.add(tableItem.getData());
//						}	
//					}
//					textLabel.setData("ORIGINAL_TEXT", textLabel.getText());			
//					return;
//				}
			}
			for (final TableItem tableItem : table.getItems()) {
				tableItem.setChecked(false);
			}				
			return;
		}
		if (getShell() != dropDownPopup.getParent()) {
			dropDownPopup.dispose();
			dropDownPopup = null;
			createDropDownPopup();
		}

		dropDownPopup.pack();
		Point textLabelRect = textLabel.toDisplay(0, 0);
		if (dropDownPopup.getSize().x < 200) {
			dropDownPopup.setSize(200, dropDownPopup.getSize().y);
		} 

		int offset = 0;
		int width = textLabel.getSize().x + arrowButton.getSize().x;
		if (dropDownPopup.getSize().x < width) {
			dropDownPopup.setSize(width, dropDownPopup.getSize().y);
		} else {
			offset = dropDownPopup.getSize().x - width;
		}	
		dropDownPopup.setLocation(textLabelRect.x - offset, textLabelRect.y + textLabel.getSize().y);
		dropDownPopup.setVisible(true);
		dropDownPopup.setFocus();
		for (final TableItem tableItem : table.getItems()) {
			tableItem.setChecked(false);
			tableItem.setChecked(selectionList.contains(tableItem.getData()));
		}			
	}

		
	/**
	 * Rebuild the drop-down
	 */
	private void rebuildTable() {
		table.removeAll();
		for (Object obj : elementsList) {
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	item.setText(obj.toString());
	    	item.setData(obj);
	    	item.setChecked(selectionList.contains(obj));
	    }		
		int tableHeight = table.getItemHeight() * ( table.getItemCount() < DEFAULT_TABLE_HEIGHT ? table.getItemCount() : DEFAULT_TABLE_HEIGHT);
	    GridData tableGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		Rectangle trim = table.computeTrim(0, 0, 0, tableHeight);
		tableGridData.heightHint = trim.height + 5;
		table.setLayoutData(tableGridData);		
	}

	
	/**
	 * Set the selected values
	 */
	private void setSelectedValues() {
		if (table == null) {
			textLabel.setText("");
			return;
		}

		final List<String> values = new ArrayList<String>();
		for (final TableItem tableItem : table.getItems()) {
			if (tableItem.getChecked()) {
				values.add(tableItem.getText());
			}
		}

		final StringBuffer sb = new StringBuffer();
		final Iterator<String> it = values.iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			if (it.hasNext()) {
				sb.append(separator);
			}
		}
		textLabel.setText(sb.toString());
	}	

	
	/**
	 * Set the Elements in drop-down and refresh
	 * the selection list
	 * @param list Items in the drop-down
	 */
	public void setElements(List<Object> list) {
		elementsList.clear();
		elementsList.addAll(list);
		if (selectionList != null) {
			Iterator<Object> itr = selectionList.iterator();
			while(itr.hasNext()) {
				Object obj = itr.next();
				if (!elementsList.contains(obj))
					itr.remove();					
			}			
		}
		rebuildTable();
		setSelectedValues();
		textLabel.setData("ORIGINAL_TEXT", textLabel.getText());
	}

	
	/**
	 * @return Array of Selected Items
	 */
	public Object[] getSelectedObjects() {
		return selectionList.toArray();
	}

	
	/**
	 * @param obj array of Selected Items
	 */
	public void setSelectedObjects(Object[] obj) {
		selectionList.clear();
		selectionList.addAll(Arrays.asList(obj));
	}

}
