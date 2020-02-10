package com.tibco.cep.studio.ui.editors.domain;

import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.DATE_TIME_PATTERN;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.RANGE_VALUE_SEPARATOR;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.studio.ui.util.ColorConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainUtils {
	
	
	public static final SimpleDateFormat BE_DATE_FORMAT = new SimpleDateFormat(DATE_TIME_PATTERN);

	private static TableItem item;
	public static final String plus = "+";
	public static final String minus = "-";
	public static final String Digits     = "(\\p{Digit}+)";
	public static final String HexDigits  = "(\\p{XDigit}+)";
	// an exponent is 'e' or 'E' followed by an optionally 
	// signed decimal integer.
	public static final String Exp        = "[eE][+-]?"+Digits;

	public static final String fpRegex    =
		("[\\x00-\\x20]*"+  // Optional leading "whitespace"
				"[+-]?(" + // Optional sign character
				"NaN|" +           // "NaN" string
				"Infinity|" +      // "Infinity" string

				// A decimal floating-point string representing a finite positive
				// number without a leading sign has at most five basic pieces:
				// Digits . Digits ExponentPart FloatTypeSuffix
				// 
				// Since this method allows integer-only strings as input
				// in addition to strings of floating-point literals, the
				// two sub-patterns below are simplifications of the grammar
				// productions from the Java Language Specification, 2nd 
				// edition, section 3.10.2.

				// Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
				"((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

				// . Digits ExponentPart_opt FloatTypeSuffix_opt
				"(\\.("+Digits+")("+Exp+")?)|"+

				// Hexadecimal strings
				"((" +
				// 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
				"(0[xX]" + HexDigits + "(\\.)?)|" +

				// 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
				"(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

				")[pP][+-]?" + Digits + "))" +
				"[fFdD]?))" +
		"[\\x00-\\x20]*");// Optional trailing "whitespace"

	
	/**
	 * @return
	 */
	public static String getFormattedDateTime() {
		return BE_DATE_FORMAT.format(new Date());
	}
	
	/**
	 * @param input
	 * @return
	 */
	public static boolean isValidName(String input) {
		return input.matches("([A-Za-z]+[A-Za-z0-9\\_]*)");
	}
	/**
	 * @param checkStr
	 * @return
	 */
	public static boolean isStringNumeric(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase(""))
				Integer.parseInt(checkStr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}

	/**
	 * @param checkStr
	 * @return
	 */
	public static boolean isStringDouble(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase(""))
				Double.parseDouble(checkStr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	/**
	 * @param checkStr
	 * @return
	 */
	public static boolean isStringLong(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase(""))
				Long.parseLong(checkStr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	
	/**
	 * @param checkStr
	 * @return
	 */
	public static boolean isNumeric(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase("")){
				Integer.parseInt(checkStr,10);
				return true;
			}
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	
	/**
	 * @param checkStr
	 * @return
	 */
	public static boolean isNumeric(String checkStr, boolean isSign) {
		try {
			if (!checkStr.trim().equalsIgnoreCase("")){
				if(isSign && (checkStr.startsWith("+")|| checkStr.startsWith("-"))){
					checkStr  = checkStr.substring(1);
					isNumeric(checkStr, false);
				}
				Integer.parseInt(checkStr,10);
				return true;
			}
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	
	/**
	 * @param checkStr
	 * @return
	 */
	public static boolean isDouble(String checkStr) {
		if (!checkStr.trim().equalsIgnoreCase("")){
			if(Pattern.matches(fpRegex, checkStr)){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}

	/**
	 * @param checkStr
	 * @return
	 */
	public static boolean isLong(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase("")){
				Long.parseLong(checkStr,10);
				return true;
			}
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}

	/**
	 * @param viewer
	 * @return
	 */
	public static boolean checkDuplicateEntries(TableViewer viewer) {
		TableItem[] items = viewer.getTable().getItems();
		if (items != null && items.length > 0) {
			for (int ii = 0; ii < items.length; ii++) {
				for (int jj = 0; jj < items.length; jj++) {
					if (ii == jj)
						continue;
					if (items[ii].getText(1).trim().equalsIgnoreCase(
							items[jj].getText(1).trim()))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param viewer
	 * @return
	 */
	public static List<String> checkEntries(TableViewer viewer) {
		List<String> list = new ArrayList<String>();
		TableItem[] items = viewer.getTable().getItems();
		if (items != null && items.length > 0) {
			for (int ii = 0; ii < items.length; ii++) {
				for (int jj = 0; jj < items.length; jj++) {
					if (ii == jj)
						continue;
					if (items[ii].getText(1).trim().equalsIgnoreCase(
							items[jj].getText(1).trim()))
						if (!checkList(items[ii].getText(1), list))
							list.add(items[ii].getText(1));
				}
			}
		}
		return list;
	}

	/**
	 * @param viewer
	 * @return
	 */
	public static List<String> checkDouble(TableViewer viewer) {
		List<String> list = new ArrayList<String>();
		TableItem[] items = viewer.getTable().getItems();
		if (items != null && items.length > 0) {
			for (int ii = 0; ii < items.length; ii++) {
				String value = items[ii].getText(1);
				if(!isStringDouble(value)){
					list.add(value);
				}
			}
		}
		return list;
	}
	
	/**
	 * @param value
	 * @param list
	 * @return
	 */
	private static boolean checkList(String value, List<String> list) {
		for (String entry : list) {
			if (entry.equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param viewer
	 */
	public static TableEditor createTableViewer(final TableViewer viewer, final DomainFormEditor editor){
		final TableEditor tableEditor = new TableEditor(viewer.getTable()) {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.custom.TableEditor#layout()
			 */
			@Override
			public void layout() {
				//the text area is resized when initially selected
				if (getEditor() != null && !getEditor().isDisposed() && getEditor().isFocusControl()) {
					return;
				}
				super.layout();
			}
			
		};
		tableEditor.grabHorizontal = true;
		viewer.getTable().addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				Point pt = new Point(event.x, event.y);
				item = viewer.getTable().getItem(pt);
				if (item == null) {
					return;
				}
				
				for (int i = 0; i < viewer.getTable().getColumnCount(); i++) {
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt)) {
						DomainViewerItem hItem = (DomainViewerItem) item.getData();
						if (i == 0) {
							if(hItem!=null && hItem.entryName!=null){
								String desc = hItem.entryName.trim();
								createTextArea(item, desc, i);
							}
						}
					}
				}
			}
			private void createTextArea(TableItem textItem,	final String desc, int column) {
				item = textItem;
				final Text text = new Text(viewer.getTable(), SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
				text.setBackground(ColorConstants.lightYellow);
				text.setText(desc);
				tableEditor.setEditor(text, item, column);
				if (!editor.isEnabled()) {
					text.setEditable(false);
				}
				text.addFocusListener(new FocusListener() {
					
					public void focusLost(FocusEvent e) {
					}
				
					public void focusGained(FocusEvent e) {
						text.setSize(text.getSize().x, 75);
					}
				
				});
				Listener textListener = new Listener () {
					public void handleEvent (final org.eclipse.swt.widgets.Event e) {
						try{
						switch (e.type) {
							case SWT.FocusOut:
								String oldVal = item.getText();
								String newVal = text.getText();
								//saving the domain description  
								item.setText(0, text.getText());
								DomainViewerItem data = (DomainViewerItem) item.getData();
								data.entryName =  text.getText();
                                
								//Domain Editor dirty 
								if(!oldVal.equalsIgnoreCase(newVal)){
									editor.modified();
								}
								
								text.dispose ();
								break;
						case SWT.Traverse:
							switch (e.detail) {
								case SWT.TRAVERSE_RETURN:
									//FALL THROUGH
								case SWT.TRAVERSE_ESCAPE:
	//								text.dispose ();
	//								e.doit = false;
								}
							break;
						}
						switch (e.character) {
							case SWT.CR:
								String oldVal = item.getText();
								String newVal = text.getText();
								//saving the domain description when press 'ENTER' 
								item.setText(0, text.getText());
								DomainViewerItem crdata = (DomainViewerItem) item.getData();
								crdata.entryName =  text.getText();
								
								//Domain Editor dirty
								if(!oldVal.equalsIgnoreCase(newVal)){
									editor.modified();
								}
								
								text.dispose ();
								break;	

						}
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
				};
				text.addListener (SWT.FocusOut, textListener);
				text.addListener (SWT.Traverse, textListener);
				text.addListener (SWT.KeyDown | SWT.KeyUp, textListener);
				text.setFocus();
			}
		});
		tableEditor.grabHorizontal = true;
		
		return tableEditor;
    }
	
	/**
	 * @param types
	 * @param formViewer
	 */
	public static void rangeValuesRefresh(DOMAIN_DATA_TYPES types,DomainFormViewer formViewer){
		TableItem selItem = formViewer.getViewer().getTable().getItem(formViewer.getViewer().getTable().getSelectionIndex());
		DomainViewerItem item = (DomainViewerItem) ((IStructuredSelection) formViewer.getViewer().getSelection()).getFirstElement();
		String lo ="";
		String up ="";
		
		switch(types){
			case INTEGER:
				lo = DomainUtils.isNumeric(formViewer.getIntRangeLoText().getText().trim())? formViewer.getIntRangeLoText().getText().trim():"";
				up = DomainUtils.isNumeric(formViewer.getIntRangeUpText().getText().trim())? formViewer.getIntRangeUpText().getText().trim():"";
				rangeValuesRefresh(selItem, item, formViewer.getIntRangeLoIncBtn(),formViewer.getIntRangeUpIncBtn(), lo, up);
				break;
			case DOUBLE:
				lo = DomainUtils.isDouble(formViewer.getRealRangeLoText().getText().trim())? formViewer.getRealRangeLoText().getText().trim():"";
				up = DomainUtils.isDouble(formViewer.getRealRangeUpText().getText().trim())? formViewer.getRealRangeUpText().getText().trim():"";
				rangeValuesRefresh(selItem, item, formViewer.getRealRangeLoIncBtn(), formViewer.getRealRangeUpIncBtn(), lo, up);
				break;
			case LONG:
				lo = DomainUtils.isLong(formViewer.getLongRangeLoText().getText().trim())? formViewer.getLongRangeLoText().getText().trim():"";
				up = DomainUtils.isLong(formViewer.getLongRangeUpText().getText().trim())? formViewer.getLongRangeUpText().getText().trim():"";
				rangeValuesRefresh(selItem, item, formViewer.getLongRangeLoIncBtn(), formViewer.getLongRangeUpIncBtn(), lo, up);
				break;
			case DATE_TIME:
				rangeValuesRefresh(selItem, item, formViewer.getDateTimeRangeLoIncBtn(), formViewer.getDateTimeRangeUpIncBtn(), 
				formViewer.getDateTimeRangeLoText().getText(), formViewer.getDateTimeRangeUpText().getText());
				break;
			default:
			break;
		}
	}
	
	/**
	 * @param types
	 * @param formViewer
	 */
	public static void rangeUpperValuesRefresh(DOMAIN_DATA_TYPES types,DomainFormViewer formViewer){
		TableItem selItem = formViewer.getViewer().getTable().getItem(formViewer.getViewer().getTable().getSelectionIndex());
		DomainViewerItem item = (DomainViewerItem) ((IStructuredSelection) formViewer.getViewer().getSelection()).getFirstElement();
//		String lo ="";
		String up ="";
		
		switch(types){
			case INTEGER:
				up = DomainUtils.isNumeric(formViewer.getIntRangeUpText().getText().trim())? formViewer.getIntRangeUpText().getText().trim():"";
				rangeUpperValuesRefresh(selItem, item, formViewer.getIntRangeLoIncBtn(),formViewer.getIntRangeUpIncBtn(), up);
				break;
			case DOUBLE:
				up = DomainUtils.isDouble(formViewer.getRealRangeUpText().getText().trim())? formViewer.getRealRangeUpText().getText().trim():"";
				rangeUpperValuesRefresh(selItem, item, formViewer.getRealRangeLoIncBtn(), formViewer.getRealRangeUpIncBtn(), up);
				break;
			case LONG:
				up = DomainUtils.isLong(formViewer.getLongRangeUpText().getText().trim())? formViewer.getLongRangeUpText().getText().trim():"";
				rangeUpperValuesRefresh(selItem, item, formViewer.getLongRangeLoIncBtn(), formViewer.getLongRangeUpIncBtn(), up);
				break;
			case DATE_TIME:
				rangeUpperValuesRefresh(selItem, item, formViewer.getDateTimeRangeLoIncBtn(), formViewer.getDateTimeRangeUpIncBtn(), 
						 formViewer.getDateTimeRangeUpText().getText());
				break;
			default:
			break;
		}
	}
	

	/**
	 * @param types
	 * @param formViewer
	 */
	public static void rangeLowerValuesRefresh(DOMAIN_DATA_TYPES types,DomainFormViewer formViewer){
		TableItem selItem = formViewer.getViewer().getTable().getItem(formViewer.getViewer().getTable().getSelectionIndex());
		DomainViewerItem item = (DomainViewerItem) ((IStructuredSelection) formViewer.getViewer().getSelection()).getFirstElement();
		String lo ="";
				
		switch(types){
			case INTEGER:
				lo = DomainUtils.isNumeric(formViewer.getIntRangeLoText().getText().trim())? formViewer.getIntRangeLoText().getText().trim():"";
				rangeLowerValuesRefresh(selItem, item, formViewer.getIntRangeLoIncBtn(),formViewer.getIntRangeUpIncBtn(), lo);
				break;
			case DOUBLE:
				lo = DomainUtils.isDouble(formViewer.getRealRangeLoText().getText().trim())? formViewer.getRealRangeLoText().getText().trim():"";
				rangeLowerValuesRefresh(selItem, item, formViewer.getRealRangeLoIncBtn(), formViewer.getRealRangeUpIncBtn(), lo);
				break;
			case LONG:
				lo = DomainUtils.isLong(formViewer.getLongRangeLoText().getText().trim())? formViewer.getLongRangeLoText().getText().trim():"";
				rangeLowerValuesRefresh(selItem, item, formViewer.getLongRangeLoIncBtn(), formViewer.getLongRangeUpIncBtn(), lo);
				break;
			case DATE_TIME:
				rangeLowerValuesRefresh(selItem, item, formViewer.getDateTimeRangeLoIncBtn(), formViewer.getDateTimeRangeUpIncBtn(),
					formViewer.getDateTimeRangeLoText().getText());
				break;
			default:
			break;
		}
	}
	


	/**
	 * @param selItem
	 * @param item
	 * @param loButton
	 * @param upButton
	 * @param loText
	 * @param upText
	 */
	public static void rangeValuesRefresh(TableItem selItem, DomainViewerItem item, Button loButton, Button upButton, String loText, String upText){
		String up = upButton.getSelection()?"]":")";
		String lo = loButton.getSelection()?"[":"(";
		String rangeValue = lo + loText + RANGE_VALUE_SEPARATOR + upText + up;
		selItem.setText(1, rangeValue);
		item.entryValue = rangeValue;
	}
	
	/**
	 * @param selItem
	 * @param item
	 * @param loButton
	 * @param upButton
	 * @param loText
	 * @param upText
	 */
	public static void rangeLowerValuesRefresh(TableItem selItem, DomainViewerItem item, Button loButton, Button upButton, String loText){
		try {
			String up = upButton.getSelection()?"]":")";
			String lo = loButton.getSelection()?"[":"(";
			String previousRangeValue = item.entryValue;
			String previousUpText = ""; 
			if(previousRangeValue != null && !previousRangeValue.equals("") && previousRangeValue.contains(","))
				previousUpText = previousRangeValue.substring(previousRangeValue.indexOf(',')+1, previousRangeValue.indexOf(upButton.getSelection()?"]":")"));
			String rangeValue = lo + loText + RANGE_VALUE_SEPARATOR + previousUpText + up;
			selItem.setText(1, rangeValue);
			item.entryValue = rangeValue;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param selItem
	 * @param item
	 * @param loButton
	 * @param upButton
	 * @param loText
	 * @param upText
	 */
	public static void rangeUpperValuesRefresh(TableItem selItem, DomainViewerItem item, Button loButton, Button upButton, String upText){
		try {
			String up = upButton.getSelection()?"]":")";
			String lo = loButton.getSelection()?"[":"(";
			String previousRangeValue = item.entryValue;
			String previousLowerText = "";
			if(previousRangeValue != null && !previousRangeValue.equals("") && previousRangeValue.contains(","))
				previousLowerText = previousRangeValue.substring(previousRangeValue.indexOf(loButton.getSelection()?"[":"(")+1, previousRangeValue.indexOf(','));
			String rangeValue = lo + previousLowerText + RANGE_VALUE_SEPARATOR + upText + up;
			selItem.setText(1, rangeValue);
			item.entryValue = rangeValue;
		}catch(Exception e) {

		}
	}
	
	/**
	 * 
	 * @param shell
	 * @param text
	 */
	public static void openDomainCalendar(final Shell shell, Text text){
			new DomainCalendar(shell, text).open();
	}
	
	/**
	 * @param domain
	 */
	public static void populateExistingDomainValues(Domain domain, TableViewer viewer){
		EList<DomainEntry> domainEntries = domain.getEntries();
		for(DomainEntry entry:domainEntries){
			DomainViewerItem item = null;
			if(entry instanceof Range){
				Range rf = (Range)entry;
				String uv = "Undefined".equalsIgnoreCase(rf.getUpper()) ? "": rf.getUpper();
				String lv = "Undefined".equalsIgnoreCase(rf.getLower()) ? "": rf.getLower();
				item = new DomainViewerItem( rf.getDescription(),(rf.isLowerInclusive()?"[":"(")
													               +lv
													               +RANGE_VALUE_SEPARATOR
													               +uv
													               +(rf.isUpperInclusive()?"]":")"));
			}else{
				item = new DomainViewerItem( entry.getDescription(), entry.getValue().toString());
			}
			viewer.add(item);
		}
	}
	
	 /**
     * Binary search of a {@link DomainEntry} in a {@link EList}.
     * @param list
     * @param key
     * @return non-negative integer if found
     */
    public static int binarySearch(EList<DomainEntry> list, DomainEntry key) {
		int low = 0;
		
		int high = list.size() - 1;
	
		while (low <= high) {
		    int mid = (low + high) >> 1;
		
			DomainEntry midVal = list.get(mid);
		    int cmpResult = midVal.compareTo(key);
	
		    if (cmpResult < 0)
			low = mid + 1;
		    else if (cmpResult > 0)
			high = mid - 1;
		    else
			return mid; // key found
		}
		return -(low + 1);  // key not found
    }
    

	/**
	 * @param text
	 */
	public static void resetWidget(Text text){
		text.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		text.setToolTipText("");
	}
	
	/**
	 * @param text
	 * @param type
	 */
	public static void setErrorWidget(Text text, String type){
		text.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", text.getText() , type);
		text.setToolTipText(problemMessage);
	}
	
	/**
	 * @param text
	 * @param type
	 * @param up
	 * @param low
	 * @param isUpper
	 */
	public static void setErrorWidget(Text text, String type, String up, String low, boolean isUpper){
		text.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString(isUpper ? "invalid.up.range.entry" : "invalid.low.range.entry", up , type, low, type);
		text.setToolTipText(problemMessage);
	}
	
	/**
	 * @param text
	 * @return
	 */
	public static boolean isValidSimpleStringValue(String text) {
		if (text.length() == 1 && text.trim().equalsIgnoreCase("*")) {
			return false;
		}
		return true;
	}
}
