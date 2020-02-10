package com.tibco.cep.studio.decision.table.providers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultDisplayConverter;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;

import com.tibco.cep.decision.table.language.DTLanguageUtil;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.studio.decision.table.editor.DTBodyLayerStack;
import com.tibco.cep.studio.decision.table.editor.DTColumnHeaderLayerStack;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

/**
 * Display Converter implementation for Domain model support in Decision table editor.
 * @author vdhumal
 *
 */
public class MultiValueDataConverter extends DefaultDisplayConverter implements IDataConverter {

	private boolean isMultiValue = true;
	protected String separator = null;
	private boolean isShowDropDownMode = false;
	
	/**
	 * @return true if the converter is being used to show the Drop down values
	 * false, other-wise 
	 */
	public boolean isShowDropDownMode() {
		return isShowDropDownMode;
	}

	/**
	 * Set to true if the converter is to be used to show the Drop down values
	 * false, other-wise. 
	 */
	public void setShowDropDownMode(boolean isShowDropDownMode) {
		this.isShowDropDownMode = isShowDropDownMode;
	}

	/**
	 * @param isMultiValue
	 * @param separator
	 */
	public MultiValueDataConverter(boolean isMultiValue, String separator) {
		this.separator = separator;
		this.isMultiValue = isMultiValue;
	}
	
	/**
	 * @return true if its for a multi-value combo box, false other-wise.
	 */
	protected boolean isMultiValue() {
		return isMultiValue;
	}
	
	/**
	 * Utility method that return the Show Domain description preference. 
	 * @return 
	 */
	public boolean isShowDomainDescription() {
		return  DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.SHOW_DOMAIN_DESCRIPTION);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object canonicalToDisplayValue(ILayerCell cell, IConfigRegistry configRegistry, Object canonicalValue) {			            		
		if (isShowDropDownMode) {
			return canonicalToDisplayValue(canonicalValue);
		} else {
			Object displayValue = canonicalValue;
			if (isShowDomainDescription()) {
				ILayer underlyingLayer = cell.getLayer();
				if (underlyingLayer instanceof GridLayer) {
					GridLayer gridLayer = (GridLayer) underlyingLayer;		
					underlyingLayer = gridLayer.getBodyLayer();
					String projectName = null;
					if (underlyingLayer instanceof DTBodyLayerStack<?>) {
						DTBodyLayerStack<TableRule> dtBodyLayerStack = (DTBodyLayerStack<TableRule>) underlyingLayer;
						SpanningGlazedListsDataProvider<TableRule> glazedListsDataProvider = dtBodyLayerStack.getBodyDataProvider();
						projectName = glazedListsDataProvider.getProjectName();
					}
					
					underlyingLayer = gridLayer.getColumnHeaderLayer();
					if (underlyingLayer instanceof DTColumnHeaderLayerStack<?>) {
						DTColumnHeaderLayerStack<TableRule> dtColumnHeaderLayerStack = (DTColumnHeaderLayerStack<TableRule>) underlyingLayer;
						IDataProvider dataProvider = dtColumnHeaderLayerStack.getColumnHeaderDataProvider();
						Column columnObject = ((DTHeaderDataProvider) dataProvider).getColumn(cell.getColumnIndex());
						List<DomainInstance> domainInstances = DTDomainUtil.getDomains(columnObject.getPropertyPath(), projectName);
	
						if (domainInstances != null && domainInstances.size() > 0) { 
							String colName = null;
							Object colObj = ((DTHeaderDataProvider) dataProvider).getDataValue(cell.getColumnIndex(), cell.getRowIndex());
							if (colObj != null) {
								colName = colObj.toString();
							}
							String substitutionFormat = (columnObject.isSubstitution()) ? DTLanguageUtil.canonicalizeExpression(colName.substring(colName.indexOf(' '))) : "";
	
							List<List<String>> values2 = DTDomainUtil.getDomainEntryDropDownStrings(domainInstances, 
									columnObject.getPropertyPath(), 
									projectName, 
									substitutionFormat,
									columnObject.getColumnType() == ColumnType.ACTION,
									false);
							List<String> valuesList = values2.get(0);
							List<String> descsList = values2.get(1);
							displayValue = new ArrayList<Object>();
							if (canonicalValue instanceof List<?>) {
								List<?> canonicalValueList = (List<?>) canonicalValue;
								for (int i = 0; i < canonicalValueList.size(); i++) {
									int valueIndx = valuesList.indexOf(canonicalValueList.get(i));
									if (valueIndx >= 0) {
										((List<Object>)displayValue).add(descsList.get(valueIndx));
									} else if(canonicalValueList.get(i).toString().startsWith("!=")){
										Object converted = converCustomMultiValueEntry(descsList,valuesList,canonicalValueList.get(i));
										((List<Object>)displayValue).add(converted);
									} else {
										((List<Object>)displayValue).add(canonicalValueList.get(i));
									}
								}	
							} else {
								int valueIndx = valuesList.indexOf(canonicalValue);
								if (valueIndx >= 0) {
									((List<Object>)displayValue).add(descsList.get(valueIndx));
								} else {
									((List<Object>)displayValue).add(canonicalValue);
								}
							}
						}
					}
				}
			}
			return canonicalToDisplayValue(displayValue);			
		}	
	}
	
	private Object converCustomMultiValueEntry(List<String> descsList, List<String> valuesList, Object object) {
		
		String valueOnly = object.toString().substring(2);
		
		int valueIndx = valuesList.indexOf(valueOnly);
		
		if (valueIndx >= 0) {
			return "!="+descsList.get(valueIndx);
		}else{
			return "!="+valueOnly;
		}		
		
	}

	@Override
    public Object canonicalToDisplayValue(Object sourceValue) {
		StringBuffer stringbuffer = new StringBuffer();
		if (sourceValue instanceof List<?>) {
        	List<?> list = (List<?>) sourceValue;
        	Iterator<?> itr = list.iterator();
        	while (itr.hasNext()) {
        		Object obj = itr.next();
        		stringbuffer.append(obj.toString());
	            if (itr.hasNext())
	            	stringbuffer.append(separator);
        	}	        	
        } else {
        	stringbuffer.append(sourceValue.toString());
        }
		
		return new String(stringbuffer);
    }

	@Override
    public Object displayToCanonicalValue(Object destinationValue) {
//        if(destinationValue == null || destinationValue.toString().length() == 0)
//            return null;
//        else
            return fromString(destinationValue.toString());
    }

	@Override
	public String toString(Object aobj) {
		return toString(aobj, 0);
	}
	
	protected String toString(Object aobj, int valueIndex) {
        StringBuffer stringbuffer = new StringBuffer();
        if (aobj != null ) {
        	if (isMultiValue) {
	        	if (aobj.getClass().isArray()) {
		            int length = Array.getLength(aobj);
		        	for(int i = 0; i < length; i++) {
		                Object o = Array.get(aobj, i);
			            stringbuffer.append(o.toString());
			            if(i != (length - 1))
			                stringbuffer.append(separator);
			        }
		        } else if (aobj instanceof List<?>) {
		        	List<?> list = (List<?>) aobj;
		        	Iterator<?> itr = list.iterator();
		        	while (itr.hasNext()) {
		        		Object obj = itr.next();
		        		if (obj instanceof List<?> && ((List<?>) obj).size() > 0) {
		        			if (valueIndex >= ((List<?>) obj).size())
		        				obj = ((List<?>) obj).get(0);
		        			else	
		        				obj = ((List<?>) obj).get(valueIndex);
		        		}
		        		stringbuffer.append(obj.toString());
			            if (itr.hasNext())
			            	stringbuffer.append(separator);
		        	}	        	
		        } else {
		        	stringbuffer.append(aobj.toString());
		        }
        	} else {
        		Object obj = aobj;
        		if (aobj instanceof List<?>) {
		        	List<?> list = (List<?>) aobj;
	        		if (valueIndex < list.size()) {
	        			obj = list.get(valueIndex);
	        		} else if (list.size() == 1) {
	        			obj = list.get(0);
	        		}        	
		        }
        		stringbuffer.append(obj.toString());
        	}
        }
        return new String(stringbuffer);
	}

	@Override
	public Object fromString(String s) {
        List<String> aList = new ArrayList<String>();
		if(s != null && s.trim().length() > 0) {
	        StringTokenizer stringtokenizer = new StringTokenizer(s, separator.trim());
	        while(stringtokenizer.hasMoreTokens()) {
	            String s1 = stringtokenizer.nextToken().trim();
	            aList.add(s1);
	        }
		}    
        return aList;
	}

}
