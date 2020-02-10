package com.tibco.cep.webstudio.client.decisiontable.constraint;

import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionConstraintUtils.COND_OPERATORS.EQUALS;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableConstraintConstants.MAX_VALUE;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableConstraintConstants.MIN_VALUE;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.Operator.AND_OP;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.Operator.OR_OP;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;



import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SliderItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils;
import com.tibco.cep.webstudio.client.decisiontable.model.Table;
import com.tibco.cep.webstudio.client.widgets.DateTimeDialog;


/**
 * 
 * @author sasahoo
 *
 */
public class DecisionConstraintUtils {

	public enum COND_OPERATORS {
		OR("||"), AND("&&"), EQUALS("=="), GT(">"), LT("<"), GTE(">="), LTE(
				"<="), NE("!="), NE2("<>"), ASSIGNMNT("=");

		private final String opString;

		private COND_OPERATORS(String value) {
			this.opString = value;
		}

		public String opString() {
			return opString;
		}

		public static String ASTERISK = "*";

		private static final COND_OPERATORS[] VALUES_ARRAY = new COND_OPERATORS[] {
				OR, AND, EQUALS, GT, LT, GTE, LTE, NE, NE2, ASSIGNMNT };

		public static COND_OPERATORS getByOpString(String opString) {
			for (int i = 0; i < VALUES_ARRAY.length; ++i) {
				COND_OPERATORS result = VALUES_ARRAY[i];
				if (result.opString.equals(opString)) {
					return result;
				}
			}
			return null;
		}
	}

	public static DecisionTable createOptimizedDecisionTable(Table table) {
		DecisionTableCreator creator = new DecisionTableCreator(table);
		DecisionTable contraintTable = creator.createDecisionTable();
		contraintTable.optimize();
		return contraintTable;
	}

	/**
	 * Canonicalize the input expression to normalize the spaces
	 * 
	 * @param expression
	 * @return
	 */
	public static String canonicalizeExpression(String expression) {
		if (expression.trim().length() == 0) {
			return "";
		}

		String[] rangeElements = new String[] { expression };
		// Check for ==
		String equalsString = EQUALS.opString();
		int equalsOpsIndex = expression.indexOf(equalsString);
		if (equalsOpsIndex != -1) {
			rangeElements = expression.split(equalsString);

			StringBuilder stringBuilder = new StringBuilder();
			for (String element : rangeElements) {
				stringBuilder.append(element.trim());
			}
			return stringBuilder.toString();
		}
		int andIndex = expression.indexOf(AND_OP);
		if (andIndex != -1) {
			rangeElements = expression.split(AND_OP);
		}
		int orIndex = expression.indexOf(OR_OP);
		if (orIndex != -1) {
			String orOp = "\\|\\|";
			rangeElements = expression.split(orOp);
		}
		StringBuilder canonicalized = new StringBuilder();
		for (String rangeEle : rangeElements) {
			rangeEle = rangeEle.trim();
			char opChar = rangeEle.charAt(0);

			if ('>' == opChar || '<' == opChar) {
				canonicalized.append(opChar);
				// Courtesy existing code
				int index = 1;
				// Check if the next operator is = or operand
				char next = rangeEle.charAt(1);
				if ('=' == next) {
					// Concat the 2 operators
					canonicalized.append(next);
					index = 2;
				}
				String lhsOperand = rangeEle.substring(index);
				lhsOperand = lhsOperand.trim();
				// Append this with one space
				canonicalized.append(" ");
				canonicalized.append(lhsOperand);

			} else {
				canonicalized.append(rangeEle);
			}
			// Check if and is added
			int containsAndIndex = canonicalized.indexOf(AND_OP);
			if (andIndex != -1 && containsAndIndex == -1) {
				canonicalized.append(" ");
				canonicalized.append(AND_OP);
				canonicalized.append(" ");
			}
			// Check if || is added
			int containsORIndex = canonicalized.indexOf(OR_OP);
			if (orIndex != -1 && containsORIndex == -1) {
				canonicalized.append(" ");
				canonicalized.append(OR_OP);
				canonicalized.append(" ");
			}
		}
		return canonicalized.toString();
	}

	/**
	 * @param trvId
	 * @return
	 */
	public static String getContainingRuleId(String trvId) {
		String ruleId = null;
		ruleId = trvId.substring(0, trvId.indexOf('_'));
		return ruleId;
	}

	/**
	 * @param columnName
	 * @param decisionTable
	 * @return
	 */
	public static FormItem[] createTypedPane(
			DecisionTableAnalyzerComponent.ColumnFilter columnFilter,
			Map<String, Map<String, Object>> fRangeColumnValues) {
		if (columnFilter.isRangeFilter()) {
			return createRangePane(columnFilter.getColumnName(),
					columnFilter.getRange(), fRangeColumnValues);
		} else {
			return componentizeEqualsFilter(columnFilter.getColumnName(),
					columnFilter.getItems());
		}
	}

	/**
	 * @param columnName
	 * @param objects
	 * @return
	 */
	private static FormItem[] createRangePane(String columnName,
			Object[] objects,
			Map<String, Map<String, Object>> fRangeColumnValues) {
		if (objects == null)
			return null;
		try {
			double mind = 0.0;
			double maxd = 0.0;
			
			long min = 0;
			long max = 0;
			
			if(objects[0] instanceof Double || objects[1] instanceof Double){
				 mind = Double.parseDouble(objects[0].toString());
				 maxd = Double.parseDouble(objects[1].toString());
			}else{
				min = Long.parseLong(objects[0].toString());
				max = Long.parseLong(objects[1].toString());
			}
			
			if(objects[0] instanceof Double || objects[1] instanceof Double){

				if (mind == Double.MIN_VALUE || maxd == Double.MAX_VALUE) {
					return null;
				}
				if (fRangeColumnValues.containsKey(columnName)) {
					if (fRangeColumnValues.get(columnName) != null) {
						Map<String, Object> map = fRangeColumnValues
								.get(columnName);
						map.put(MIN_VALUE, mind);
						map.put(MAX_VALUE, maxd);
					}
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(MIN_VALUE, mind);
					map.put(MAX_VALUE, maxd);
					fRangeColumnValues.put(columnName, map);
				}
			}else{
				if (min == Long.MIN_VALUE || max == Long.MAX_VALUE) {
					return null;
				}
				if (fRangeColumnValues.containsKey(columnName)) {
					if (fRangeColumnValues.get(columnName) != null) {
						Map<String, Object> map = fRangeColumnValues
								.get(columnName);
						map.put(MIN_VALUE, min);
						map.put(MAX_VALUE, max);
					}
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(MIN_VALUE, min);
					map.put(MAX_VALUE, max);
					fRangeColumnValues.put(columnName, map);
				}
			}

			SliderItem minSliderItem = new SliderItem();
			minSliderItem.setVertical(false);
			minSliderItem.setName(columnName + "_MIN");
			// item.setTitle("MIN");
			minSliderItem.setWidth(250);
			minSliderItem.setAlign(Alignment.LEFT);
			minSliderItem.setShowTitle(false);
			if(objects[0] instanceof Double || objects[1] instanceof Double){
				minSliderItem.setMinValue((float)Float.parseFloat(String.valueOf(mind-1)));
				minSliderItem.setMaxValue((float)Float.parseFloat(String.valueOf(maxd+1)));
				minSliderItem.setDefaultValue((float) ((Math.round(mind) + Math.round(maxd)) / 2));
			}else{
				minSliderItem.setMinValue(min-1);
				minSliderItem.setMaxValue(max+1);
				minSliderItem.setDefaultValue((int) ((min + max) / 2));
			}
			
			// item.setNumValues();
			minSliderItem.setCellHeight(2);
			minSliderItem.setHeight(5);
			
			TextItem minTextItem = new TextItem();
			minTextItem.setName("_MIN_TEXT");
			minTextItem.setWidth(150);
			minTextItem.setAlign(Alignment.LEFT);
			minTextItem.setTitle("Lower Value");
			minTextItem.setShowTitle(false);
			minTextItem.setValue(String.valueOf(minSliderItem.getDefaultValue()));
			minTextItem.setCellHeight(2);
			minTextItem.setHeight(5);

			SliderItem maxSliderItem = new SliderItem();
			maxSliderItem.setVertical(false);
			maxSliderItem.setName(columnName + "_MAX");
			// item2.setTitle("MAX");
			maxSliderItem.setWidth(250);
			maxSliderItem.setAlign(Alignment.LEFT);
			maxSliderItem.setShowTitle(false);
			if(objects[0] instanceof Double || objects[1] instanceof Double){
				maxSliderItem.setMinValue(Float.parseFloat(String.valueOf(mind-1)));
				maxSliderItem.setMaxValue(Float.parseFloat(String.valueOf(maxd+1)));
				maxSliderItem.setDefaultValue((float) ((Math.round(mind) + Math.round(maxd)) / 2));
			}else{
				maxSliderItem.setMinValue(min-1);
				maxSliderItem.setMaxValue(max+1);
				maxSliderItem.setDefaultValue((int) ((min + max) / 2));
			}
			maxSliderItem.setHeight(5);
			maxSliderItem.setCellHeight(2);
			
			TextItem maxTextItem = new TextItem();
			maxTextItem.setName("_MAX_TEXT");
			maxTextItem.setWidth(150);
			//maxTextItem.setAlign(Alignment.LEFT);
			maxTextItem.setTitle("Upper Value");
			maxTextItem.setShowTitle(false);
			maxTextItem.setValue(String.valueOf(maxSliderItem.getDefaultValue()));
			maxTextItem.setCellHeight(2);
			maxTextItem.setHeight(5);
			if(objects[0] instanceof Double || objects[1] instanceof Double){
				minTextItem.setValue(String.valueOf((float) ((Math.round(mind) + Math.round(maxd)) / 2)));
				maxTextItem.setValue(String.valueOf((float) ((Math.round(mind) + Math.round(maxd)) / 2)));
			}else{
				minTextItem.setValue(String.valueOf((int) ((min + max) / 2)));
				maxTextItem.setValue(String.valueOf((int) ((min + max) / 2)));
			}
			DecisionTableAnalyzerSliderHandler dtAnalyzerSliderChangeHandler = new DecisionTableAnalyzerSliderHandler(
					minSliderItem, maxSliderItem, minTextItem, maxTextItem, minSliderItem.getMinValue(), minSliderItem.getMaxValue());
			minSliderItem.addChangeHandler(dtAnalyzerSliderChangeHandler);
			maxSliderItem.addChangeHandler(dtAnalyzerSliderChangeHandler);
			minTextItem.addEditorExitHandler(dtAnalyzerSliderChangeHandler);
			maxTextItem.addEditorExitHandler(dtAnalyzerSliderChangeHandler);

			return new FormItem[] { minSliderItem, minTextItem, maxSliderItem, maxTextItem };

		} catch (NumberFormatException nfe) {
			
             
            final TextItem textItem = new TextItem();
			
			textItem.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					DecisionTableUtils.getDateTimeDialog(textItem);
					
				}
					
			});
			textItem.setShowTitle(false);
			textItem.setWidth("210px");
            
			return new FormItem[] {textItem};
		} catch (RuntimeException re) {
			re.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @param columnName
	 * @param equalsFilter
	 * @return
	 */
	private static FormItem[] componentizeEqualsFilter(String columnName,
			Set<String> items) {
		SelectItem item = new SelectItem();
		item.setName(columnName);
		item.setWidth(230);
		// item.setAlign(Alignment.CENTER);
		item.setCellHeight(28);
		item.setVAlign(VerticalAlignment.BOTTOM);
		item.setShowTitle(false);
		item.setMultiple(true);
		item.setMultipleAppearance(MultipleAppearance.PICKLIST);
		String[] values = new String[items.size()];
		items.toArray(values);
		item.setValueMap(values);
		return new FormItem[] { item };
	}
}