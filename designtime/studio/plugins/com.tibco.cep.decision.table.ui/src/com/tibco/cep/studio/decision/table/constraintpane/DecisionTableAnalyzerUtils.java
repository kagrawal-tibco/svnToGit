/**
 * 
 */
package com.tibco.cep.studio.decision.table.constraintpane;

import static com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS.EQUALS;
import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.DONT_CARE;
import static com.tibco.cep.studio.decision.table.constraintpane.Operator.AND_OP;
import static com.tibco.cep.studio.decision.table.constraintpane.Operator.OR_OP;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;


/**
 * @author aathalye
 *
 */
public class DecisionTableAnalyzerUtils {
	
	public enum COND_OPERATORS {
		OR("||"), AND("&&"), EQUALS("=="), GT(">"), LT("<"), GTE(">="), LTE("<="), NE("!="), NE2("<>"), ASSIGNMNT("=");

		private final String opString;

		private COND_OPERATORS(String value) {
			this.opString = value;
		}

		public String opString() {
			return opString;
		}

		public static String ASTERISK = "*";

		private static final COND_OPERATORS[] VALUES_ARRAY =
				new COND_OPERATORS[] {
			OR,
			AND,
			EQUALS,
			GT,
			LT,
			GTE,
			LTE,
			NE,
			NE2,
			ASSIGNMNT
		};

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
	
	public static String FORWARD_SLASH="/";
	public static String MARKER="##";
	public static final String CONCEPT_FILE_EXT = ".concepttestdata";
	public static final String SPLIT = ";";
	
	/**
	 * Get all domain entries for this property path.
	 * <p>
	 * Also fetches domains from super concept/event 
	 * </p>
	 * @param propertyPath
	 * @param project
	 * @return
	 */
	public static String[] getDomainValues(String propertyPath,
			                               String project,
			                               String substitutionFormat,boolean showDomainsDescription) {
		List<DomainInstance> domainInstances = 
			DTDomainUtil.getDomains(propertyPath, project);
		
		return DTDomainUtil.getDomainEntryStrings(domainInstances, 
				                                  propertyPath, 
				                                  project, 
												  substitutionFormat,showDomainsDescription,false);
	}
	
	/**
	 * Get list of configured domains for a certain property.
	 * @param propertyPath
	 * @param project
	 * @return
	 */
	public static List<DomainInstance> getConfiguredDomains(String propertyPath,
			                                                String project) {
		List<DomainInstance> domainInstances = 
			DTDomainUtil.getDomains(propertyPath, project);
		return domainInstances;
	}
	
	/**
	 * Get the property path given a decision table column name.
	 * <p>
	 * Column name is of format &quot;&lt;alias&gt;&lt;.name&gt;
	 * </p>
	 * @param column
	 * @param table
	 * @return
	 */
	public static String getPropertyPathForColumn(String column,
			                                      DecisionTable table) {
		//This list has to be a subset of the domain list
		Cell.CellInfo cellInfo = table.getCellInfo(column);
		//Find property path
		String propertyPath = cellInfo.getPath();
		return propertyPath;
	}
	
	public static void highlightRows(JTable mainTable, String[] rowIndexes) {
		mainTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		ListSelectionModel selectionModel = mainTable.getSelectionModel();
		DecisionTableUIPlugin.debug(DecisionTableAnalyzerUtils.class.getName(), "Need to highlight {0} rows", rowIndexes.length);
		
		selectionModel.clearSelection();
		for (int i = 0; i < rowIndexes.length; i++) {
			int rowId = Integer.parseInt(rowIndexes[i]);
			int rowNumber = rowId;//getRowNumber((JoinTableModel) decisionTable.getModel(), rowId);
			if (rowNumber == -1) {
				// could not find the proper row number, do not select this row
				// TODO : consider writing error message
				continue;
			}
			selectionModel.addSelectionInterval(rowNumber, rowNumber);
			mainTable.getColumnModel().getSelectionModel().setSelectionInterval(0, mainTable.getColumnCount());
		}
	}
	
		
	/**
	 * Canonicalize the input expression to normalize the spaces
	 * @param expression
	 * @return
	 */
	public static String canonicalizeExpression(String expression) {
		if (expression.trim().length() == 0) {
			return "";
		}
		
		String[] rangeElements = new String[] {expression};
		//Check for ==
		String equalsString = EQUALS.opString();
		int equalsOpsIndex = 
			expression.indexOf(equalsString);
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
			while (rangeEle.charAt(0) == '(') {
				rangeEle = rangeEle.substring(1);
			}
			while (rangeEle.charAt(rangeEle.length()-1) == ')') {
				rangeEle = rangeEle.substring(0,rangeEle.length()-1);
			}
			char opChar = rangeEle.charAt(0);
			
			if ('>' == opChar || '<' == opChar) {
				canonicalized.append(opChar);
				//Courtesy existing code
				int index = 1;
				//Check if the next operator is = or operand
				char next = rangeEle.charAt(1);
				if ('=' == next) {
					//Concat the 2 operators
					canonicalized.append(next);
					index = 2;
				}
				String lhsOperand = rangeEle.substring(index);
				lhsOperand = lhsOperand.trim();
				//Append this with one space
				canonicalized.append(" ");
				canonicalized.append(lhsOperand);
				
			} else {
				canonicalized.append(rangeEle);
			}
			//Check if and is added
			int containsAndIndex = canonicalized.indexOf(AND_OP);
			if (andIndex != -1 && containsAndIndex == -1) {
				canonicalized.append(" ");
				canonicalized.append(AND_OP);
				canonicalized.append(" ");
			}
			//Check if || is added
			int containsORIndex = canonicalized.indexOf(OR_OP);
			if (orIndex != -1 && containsORIndex == -1) {
				canonicalized.append(" ");
				canonicalized.append(OR_OP);
				canonicalized.append(" ");
			}
		}
		return canonicalized.toString();
	}
	
	public static void setIdForQuickFix(TableRuleVariable trv, 
										String columnName,
			                            Table tableEModel, 
			                            int ruleID) {
		if (trv.getColId() == null) {
			//Get Decision Table TRS
			TableRuleSet decisionTable = tableEModel.getDecisionTable();
			//Get its columns
			Columns columns = decisionTable.getColumns();
			//Search this column
			Column column = columns.searchByName(columnName, ColumnType.CONDITION);
			if (column != null) {
				String colId = column.getId();
				String trvId = ruleID + "_" + colId;
				trv.setId(trvId);
				trv.setColId(colId);
			}
		}
	}
	
	/**
	 * <p>
	 * Searches a {@link Column} in the decision {@link TableRuleSet} for a given column Id.
	 * </p>
	 * <b>
	 * <i>
	 * To be used only from analyzer code as it searches only in decision table part.
	 * </i>
	 * </b>
	 * @param columnId
	 * @param decisionTable
	 * @return
	 */
	public static Column getColumnFromId(String columnId, TableRuleSet decisionTable) {
		//Get columns
		Columns allColumns = decisionTable.getColumns();
		//Search with this id
		return allColumns.search(columnId);
	}	
	
	public static Object[] getMinMax(List<Filter> allFilters, boolean isdouble, DateFormat sdf) {
		Filter filter1 = allFilters.get(0);
		Filter filter2 = allFilters.get(1);
		if (filter1 instanceof EqualsFilter) {
			// need to find the min/max of the equality to ensure the range
			// covers those values
			EqualsFilter eq = (EqualsFilter) filter1;
			return getMinMax(eq,isdouble,sdf);
		} else if (filter2 instanceof EqualsFilter) {
			// need to find the min/max of the equality to ensure the range
			// covers those values
			EqualsFilter eq = (EqualsFilter) filter2;
			return getMinMax(eq,isdouble,sdf);
		}
		return null;
	}

	private static Object[] getMinMax(EqualsFilter equalsFilter,boolean isdouble, DateFormat sdf) {
		Object min = Long.MAX_VALUE;
		Object max = Long.MIN_VALUE;
		
		Object min1 = Double.MAX_VALUE;
		Object max1 = Double.MIN_VALUE;

		HashSet<Object> enabledKeys = new LinkedHashSet<Object>();
		Set<Object> keySet = equalsFilter.getKeySet();
		for (Object object : keySet) {
			HashSet<Cell> cells = equalsFilter.get(object);
			for (Cell cell : cells) {
				if (cell.isEnabled()) {

					// TO DO: Get the Column type and put a check
					/**
					 * Columns allColumns = (Columns)
					 * fCurrentOptimizedTable.getColumns(); Int propertyType =
					 * allColumns.search(cell.columnId).getPropertyType();
					 * DecisionTableAnalyzerUtils
					 * .getColumnFromId(cell.columnId,fCurrentOptimizedTable.);
					 */
					enabledKeys.add(object);
					continue;
				}
			}
		}

		Object[] array = enabledKeys.toArray();
		for (Object object : array) {
			if (object instanceof String) {
				String value = (String) object;
				// check for ANDed cells
				if (value.contains(COND_OPERATORS.OR.opString())) {
					StringTokenizer tokens = new StringTokenizer(value,
							COND_OPERATORS.OR.opString());
					while (tokens.hasMoreTokens()) {
						String token = tokens.nextToken().trim();
						if (!DONT_CARE.equals(token)) {
							try {
								if (value.contains(".") || isdouble) {
									Double val = Double.valueOf(token);
									min1 = Math.min((double) min1,
											(double)val);
									max1 = Math.max((double) max1,
											(double)val);
									//return new Object[] { min1, max1 };
								} else {
									if (token.contains("L")) {
										token = token.replaceAll("L", "").trim();
									}
									if (token.contains("l")) {
										token = token.replaceAll("l,", "").trim();
									}
									long val = Long.valueOf(token);
									min = Math.min((long) min, val);
									max = Math.max((long) max, val);
								}
							} catch (NumberFormatException nfe) {
								try {
									DateFormat format = new SimpleDateFormat(
											"yyyy-MM-dd'T'HH:mm:ss");
									if (max instanceof Long) {
										max = format
												.parse("1601-01-01T00:00:00");

									}
									if (min instanceof Long) {
										min = format
												.parse("9999-12-31T00:00:00");
									}
									Date val = sdf.parse(value);

									long maxTime = ((Date) max).getTime();
									long minTime = ((Date) min).getTime();
									long currentTime = val.getTime();
									min = new Date(Math.min(minTime,
											currentTime));
									max = new Date(Math.max(maxTime,
											currentTime));

								} catch (ParseException parseException) {
									continue;
								}

							}
						}
					}
				} else {
					if (!DONT_CARE.equals(value)) {
						try {
							if (value.contains(".") || isdouble) {
								Double val = Double.valueOf(value);
								min1 =  Math.min((double) min1,
										(double)val);
								max1 =  Math.max((double) max1,
										(double)val);
								//return new Object[] { min1, max1 };
							} else {
								if (value.contains("L")) {
									value = value.replaceAll("L", "").trim();
								}
								if (value.contains("l")) {
									value = value.replaceAll("l,", "").trim();
								}
								long val = Long.valueOf(value);
								min = Math.min((long) min, val);
								max = Math.max((long) max, val);
							}

						} catch (NumberFormatException nfe) {
							try {
                                DateFormat format = new SimpleDateFormat(
                                        "yyyy-MM-dd'T'HH:mm:ss");
								if (max instanceof Long) {
									max = format.parse("1601-01-01T00:00:00");

								}
								if (min instanceof Long) {
									min = format.parse("9999-12-31T00:00:00");
								}
								Date val = sdf.parse(value);

								long maxTime = ((Date) max).getTime();
								long minTime = ((Date) min).getTime();
								long currentTime = val.getTime();
								min = new Date(Math.min(minTime, currentTime));
								max = new Date(Math.max(maxTime, currentTime));

							} catch (ParseException parseException) {
								DecisionTableUIPlugin.log(parseException);
								continue;
							}

						}
					}
				}
			} else if (object instanceof Date) {
			}
		}
		if(isdouble){
			return new Object[] { min1, max1 };
		}else{
			return new Object[] { min, max };
		}
	}
}
