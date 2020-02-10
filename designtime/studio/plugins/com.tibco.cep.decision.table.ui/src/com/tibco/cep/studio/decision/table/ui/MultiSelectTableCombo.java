package com.tibco.cep.studio.decision.table.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.style.IStyle;
import org.eclipse.nebula.widgets.nattable.widget.NatCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.studio.decision.table.providers.MultiValueDataConverter;

/**
 * This class extends the NatCombo implementation to add proper domain model support
 * to the decision table editor.  
 * @author vdhumal
 *
 */
public class MultiSelectTableCombo extends NatCombo {

	private List<String> tableHeaders = null;
	private IDisplayConverter displayConverter = null;
	private List<String> originalList = new ArrayList<String>();
	private List<String> searchTextList = new ArrayList<String>();
	protected boolean iswithinShell = false;
	private Text searchTxt;
	private Button selectAllBtn;
	private Object originalCanonicalValue = null;
	
	public MultiSelectTableCombo(Composite parent, List<String> tableHeaders, IStyle cellStyle, int style) {
		this(parent, tableHeaders, cellStyle, style, style, null, null, null);
	}

	public MultiSelectTableCombo(Composite parent, List<String> tableHeaders, IStyle cellStyle, int maxVisibleItems, int style, IDisplayConverter displayConverter, Object originalCanonicalValue) {
		this(parent, tableHeaders, cellStyle, maxVisibleItems, style, null, displayConverter, originalCanonicalValue);
	}

	public MultiSelectTableCombo(Composite parent, List<String> tableHeaders, IStyle cellStyle, int maxVisibleItems, int style, Image iconImage, IDisplayConverter displayConverter, Object originalCanonicalValue) {
		super(parent, cellStyle, maxVisibleItems, style, iconImage);
		this.tableHeaders = tableHeaders;
		this.displayConverter = displayConverter;
		this.originalCanonicalValue = originalCanonicalValue;
		if (itemList != null) {
			this.originalList = itemList;
		}
		getDropdownTable();
	}

	@Override
	protected void createDropdownControl(int style) {
		dropdownShell = new Shell(getShell(), SWT.NO_TRIM | SWT.ON_TOP);
		selectAllBtn = new Button(dropdownShell, SWT.CHECK);
		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.horizontalSpacing = 0; 
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = gridLayout.marginHeight = 5;
		gridLayout.marginLeft = gridLayout.marginRight = 0;
		dropdownShell.setLayout(gridLayout);
		dropdownShell.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				String[] searchTextArray = searchTextList.toArray(new String[searchTextList.size()]); 
				if (searchTextArray != null) {
					text.setText(getTransformedText(searchTextArray));
				}
				setItems(originalList.toArray(new String[originalList.size()]));
				hideDropdownControl();
				if (freeEdit) {
					text.forceFocus();
					text.setText(getTransformedText(searchTextArray));
				}
				
			}
		});
//		dropdownShell.addFocusListener(new FocusListener() {
//			
//			@Override
//			public void focusLost(FocusEvent arg0) {
//				setItems(itemList.toArray(new String[itemList.size()]));
//			}
//			
//			@Override
//			public void focusGained(FocusEvent arg0) {
//					
//			}
//		});

		int dropdownListStyle = style | SWT.V_SCROLL | HorizontalAlignmentEnum.getSWTStyle(cellStyle) | SWT.FULL_SELECTION;
        searchTxt = new Text(dropdownShell, SWT.BORDER | SWT.SEARCH);
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
        gd.horizontalSpan = 5;
        gd.verticalSpan = 5;
        searchTxt.setLayoutData(gd);
        searchTxt.setMessage("Search");
        searchTxt.addModifyListener(new ModifyListener() {
			
        	public void modifyText(ModifyEvent arg0) {
				List<String> filteredList = new ArrayList<String>();
				for(String item : originalList){
					if(item.toUpperCase().startsWith(searchTxt.getText().toUpperCase())){
						filteredList.add(item);
					}
				}
				if (filteredList!= null) {
					setItems(filteredList.toArray(new String[filteredList.size()]));
				}
				
			}
		});
        
        searchTxt.addMouseTrackListener(new MouseTrackListener() {
			
			@Override
			public void mouseHover(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExit(MouseEvent arg0) {
				iswithinShell = false;
				
			}
			
			@Override
			public void mouseEnter(MouseEvent arg0) {
				iswithinShell = true;
				
			}
		});
        
        searchTxt.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				if(!iswithinShell){
					hideDropdownControl();
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        if(useCheckbox){
			selectAllBtn.setText("Select All");
			selectAllBtn.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					Button btn = (Button) event.getSource();
					boolean checkedSelectAll = btn.getSelection();
					if(checkedSelectAll){
						TableItem[] tableItem = dropdownTable.getItems();
						searchTextList.clear();
						for(TableItem item : tableItem){
							if(!item.getChecked()){
								item.setChecked(true);
								dropdownTable.select(dropdownTable.indexOf(item));
								if(!searchTextList.contains(item.getText(0))){
									searchTextList.add(item.getText(0));
								}
							}
						}
						
					}
					else{
						TableItem[] tableItem = dropdownTable.getItems();
						for(TableItem item : tableItem){
							if(item.getChecked()){
								item.setChecked(false);
								dropdownTable.deselect(dropdownTable.indexOf(item));
								searchTextList.remove(item.getText(0));
							}
						}
						
					}
					String[] searchTxtArray = searchTextList.toArray(new String[searchTextList.size()]); 
					if (searchTxtArray != null) {
						text.setText(getTransformedText(searchTxtArray));
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent event) {
					// TODO Auto-generated method stub

				}
			});
        }
		dropdownTable = new Table(dropdownShell, dropdownListStyle);
		dropdownTable.setHeaderVisible(true);
		dropdownTable.setBackground(cellStyle.getAttributeValue(CellStyleAttributes.BACKGROUND_COLOR));
		dropdownTable.setForeground(cellStyle.getAttributeValue(CellStyleAttributes.FOREGROUND_COLOR));
		dropdownTable.setFont(cellStyle.getAttributeValue(CellStyleAttributes.FONT));
		dropdownTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		dropdownTable.setLayout(new GridLayout());
//		this.dropdownTable.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				boolean selected = e.detail != SWT.CHECK;
//				TableItem item = (TableItem) e.item;
//
//				// checkbox clicked, now sync the selection
//				if (!selected) {
//					if (!item.getChecked()) {
//						dropdownTable.deselect(itemList.indexOf(item.getText()));
//					} else {
//						dropdownTable.select(itemList.indexOf(item.getText()));
//					}
//				}
//				// item selected, now sync checkbox
//				else if (useCheckbox) {
//					// after selection is performed we need to ensure that
//					// selection and checkboxes are in sync
//					for (TableItem tableItem : dropdownTable.getItems()) {
//						tableItem.setChecked(
//								dropdownTable.isSelected(itemList.indexOf(tableItem.getText())));
//					}
//				}
//
//				updateTextControl(false);
//			}
//		});
		dropdownTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean selected = e.detail != SWT.CHECK;
				TableItem tableItem = (TableItem) e.item;
				// checkbox clicked, now sync the selection
				if (!selected) {
					if (!tableItem.getChecked()) {
						dropdownTable.deselect(itemList.indexOf(tableItem.getText()));
						searchTextList.remove(tableItem.getText(0));

						if(selectAllBtn.getSelection()){
							selectAllBtn.setSelection(false);
						}
					} else {
						dropdownTable.select(itemList.indexOf(tableItem.getText()));
						if(!searchTextList.contains(tableItem.getText(0))){
							searchTextList.add(tableItem.getText(0));
						}
						if(!selectAllBtn.getSelection() && (searchTextList.size() == originalList.size())){
							selectAllBtn.setSelection(true);
						}
					}
				}
				// item selected, now sync checkbox
				else if (useCheckbox) {
					// after selection is performed we need to ensure that
					// selection and checkboxes are in sync
					searchTextList.clear();
					for (TableItem item : dropdownTable.getItems()) {
						if (item.getChecked()) {
							searchTextList.add(item.getText(0));
						} else {
							item.setChecked(dropdownTable.isSelected(itemList.indexOf(item.getText())));
							if (item.getChecked()) {
								searchTextList.add(item.getText(0));
							}
							
						}
					}
					selectAllBtn.setSelection(false);
					searchTxt.setText("");
				} else{
					searchTextList.clear();
					searchTextList.add(tableItem.getText(0));
					searchTxt.setText("");
					
				}

				String[] searchTxtArray = searchTextList.toArray(new String[searchTextList.size()]); 
				if (searchTxtArray != null) {
					text.setText(getTransformedText(searchTxtArray));
				}
//				if (!selected) {
//					if (!tableItem.getChecked()) {
//						searchTextList.remove(tableItem.getText(0));
//
//						if(selectAllBtn.getSelection()){
//							selectAllBtn.setSelection(false);
//						}
//					}
//					else {
//						if(!searchTextList.contains(tableItem.getText(0))){
//							searchTextList.add(tableItem.getText(0));
//						}
//						if(!selectAllBtn.getSelection() && (searchTextList.size() == originalList.size())){
//							selectAllBtn.setSelection(true);
//						}
//
//					}
//				} else if (useCheckbox) {
//					for (TableItem item : dropdownTable.getItems()) {
//						if(item.getText().equalsIgnoreCase(tableItem.getText())){
//							item.setChecked(true);
//							searchTextList.clear();
//							searchTextList.add(tableItem.getText(0));
//						}else{
//							item.setChecked(false);
//						}
//
//					}
//					selectAllBtn.setSelection(false);
//					searchTxt.setText("");
//				}
//				else{
//					searchTextList.clear();
//					searchTextList.add(tableItem.getText(0));
//					searchTxt.setText("");
//				}
//
//				String[] searchTxtArray = searchTextList.toArray(new String[searchTextList.size()]); 
//				if (searchTxtArray != null) {
//					text.setText(getTransformedText(searchTxtArray));
//				}

			}
		});
		
		dropdownTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if ((event.keyCode == SWT.CR)
						|| (event.keyCode == SWT.KEYPAD_CR)) {
					updateTextControl(true);
				}
				else if (event.keyCode == SWT.F2 && freeEdit) {
					text.forceFocus();
					hideDropdownControl();
				}
				else if (event.keyCode == SWT.ESC) {
					text.forceFocus();
					hideDropdownControl();
				}
			}
		});
		
		dropdownTable.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusLost(FocusEvent e) {
				String[] searchTxtArray = searchTextList.toArray(new String[searchTextList.size()]); 
				if (searchTxtArray != null) {
					text.setText(getTransformedText(searchTxtArray));
				}
				
				if(!iswithinShell){
					hideDropdownControl();
				}
			}
		});
		
		
		text.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent var1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent var1) {
				if(text.getText() == null || text.getText().isEmpty()){	
					text.setText(getFormattedDisplayValue());	
				}	
			}
		});
		
		
		dropdownTable.addMouseTrackListener(new MouseTrackListener() {
			
			@Override
			public void mouseHover(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExit(MouseEvent arg0) {
				iswithinShell = false;
				
			}
			
			@Override
			public void mouseEnter(MouseEvent arg0) {
				iswithinShell = true;
				
			}
		});
		
		if (this.itemList != null) {
			
			setItems(this.itemList.toArray(new String[itemList.size()]));
		}
		
		setDropdownSelection(getTextAsArray());
	}
		
	@Override
	public void setItems(String[] items) {
		dropdownTable.removeAll();
		if(originalList.isEmpty()){
			for(int i=0; i<items.length; i++){
				originalList.add(items[i]);
			}
		}
		
		if(dropdownTable.getColumns().length == 0){
			if (tableHeaders != null && tableHeaders.size() > 0) {
				for (Object header : tableHeaders) {
					TableColumn tc = new TableColumn(dropdownTable, SWT.NONE);
					tc.setText(header.toString());
					tc.setWidth(150);
				}			
			}
		}
		
		itemList = new ArrayList<String>();
		for (int i = 0; i < items.length; i++) {
			String[] rowValues = items[i].split(Pattern.quote(DTDomainUtil.SEPARATOR));
			itemList.add(rowValues[0]);
			TableItem item = new TableItem(dropdownTable, SWT.NONE);
		    item.setText(rowValues);
	    	item.setData(rowValues[0]);
	    	
	    	if(!searchTextList.isEmpty()){
	    		if(searchTextList.contains(rowValues[0])){
	    			item.setChecked(true);
	    			dropdownTable.select(dropdownTable.indexOf(item));
	    		}
	    	}else{
	    		for(String a : getTextAsArray()){
	    			searchTextList.add(a);
	    		}
	    		if(searchTextList.contains(rowValues[0])){
	    			item.setChecked(true);
	    			dropdownTable.select(dropdownTable.indexOf(item));
	    		}
	    	}
		}
		((MultiValueDataConverter)displayConverter).setShowDropDownMode(false);
	}
	
	@Override
	protected void calculateBounds() {
		// super.calculateBounds() only accounts for the table, not the searchTxt or selectAllBtn widgets
		super.calculateBounds();
		Rectangle shellbounds = dropdownShell.getBounds();
		if (selectAllBtn != null && !selectAllBtn.isDisposed()) {
			shellbounds.height += selectAllBtn.getBounds().height + 5; // +5 for margin
		}
		if (searchTxt != null && !searchTxt.isDisposed()) {
			shellbounds.height += searchTxt.getBounds().height + 5; // +5 for margin
		}
		dropdownShell.setBounds(shellbounds);
	}

	@Override
	protected String[] getTextAsArray() {
		if (!this.text.isDisposed()) {
			String transform = this.text.getText();
			if (transform.length() > 0) {
				if (this.multiselect) {
					int prefixLength = this.multiselectTextPrefix.length();
					int suffixLength = this.multiselectTextSuffix.length();
					if (this.freeEdit) {
						if (!transform.startsWith(multiselectTextPrefix)) {
							prefixLength = 0;
						}
						if (!transform.endsWith(multiselectTextSuffix)) {
							suffixLength = 0;
						}
					}
					transform = transform.substring(prefixLength, transform.length()-suffixLength);
				}
				if (transform.length() > 0) {
					return transform.split(Pattern.quote(this.multiselectValueSeparator));
				}
			}
		}
		return new String[] {};
	}
	
	@Override
	protected int getVisibleItemCount() {
		int itemCount = super.getVisibleItemCount();		
		return (itemCount + 2);
	}	

	@Override
	protected void setDropdownSelection(String[] selection) {
		if (selection.length > 0) {
			java.util.List<String> selectionList = Arrays.asList(selection); 
			java.util.List<TableItem> selectedItems = new ArrayList<TableItem>();
			for (TableItem item : this.dropdownTable.getItems()) {
				String itemText = null;
				if (((MultiValueDataConverter)displayConverter).isShowDomainDescription()) {
					itemText = item.getText(1);
				}
				itemText = (itemText != null && !itemText.isEmpty()) ? itemText : item.getText();
				if (selectionList.contains(itemText)) {
					selectedItems.add(item);
					searchTextList.add(item.getText());
					if (useCheckbox) {
						item.setChecked(true);
					}
				}
			}
			this.dropdownTable.setSelection(selectedItems.toArray(new TableItem[] {}));
		}
	}
	
	@Override
	public int[] getSelectionIndices() {
		if (itemList.size() != originalList.size()) {
			// we're filtering the results, need to get indices from original list
			 String[] selectedItems = getTextAsArray();
	            int[] result = new int[selectedItems.length];
	            for (int i = 0; i < selectedItems.length; i++) {
	                result[i] = this.originalList.indexOf(selectedItems[i]);
	            }
	            return result;
		}
		return super.getSelectionIndices();
	}
	
	public String getFormattedDisplayValue() {
		String  orgString = ((MultiValueDataConverter)displayConverter).canonicalToDisplayValue(this.originalCanonicalValue).toString();
		return orgString;
	}
	
}
