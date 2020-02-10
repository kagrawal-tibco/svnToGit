// (c) Copyright 2001,2002 TIBCO Software Inc.  All rights reserved.
// LEGAL NOTICE:  This source code is provided to specific authorized end
// users pursuant to a separate license agreement.  You MAY NOT use this
// source code if you do not have a separate license from TIBCO Software
// Inc.  Except as expressly set forth in such license agreement, this
// source code, or any portion thereof, may not be used, modified,
// reproduced, transmitted, or distributed in any form or by any means,
// electronic or mechanical, without written permission from  TIBCO
// Software Inc.

package com.tibco.hawk.jshma.util;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Vector;

import COM.TIBCO.hawk.utilities.misc.TimeTool;
import COM.TIBCO.hawk.utilities.trace.Trace;

/**
 * This class provides utilities to parse or format HTML text.
 */
public class Html {
	private static String sClassName = Html.class.getName();

	private static String[] sTableTags = { "TABLE", "TR", "TD", "TH" };
	private static final int HTML_TABLE = 0, HTML_table = 1, HTML_TR = 2, HTML_tr = 3, HTML_TD = 4, HTML_td = 5,
			HTML_TH = 6, HTML_th = 7;
	private static boolean firstTime = true;
	private static boolean debugParsing = false;

	/**
	 * This method is equivalent to call:
	 * <code>parseTable(src, selection, hasTableTag, skipFirstRow, true);</code>
	 */
	public static Object[][] parseTable(String src, Object selection, boolean hasTableTag, boolean skipFirstRow)
			throws Exception {
		return parseTable(src, selection, hasTableTag, skipFirstRow, true);
	}

	/**
	 * Parse Html to extract an HTML table data. The parsing is based on HTML
	 * table structure with tags like table, tr, th, td to extract a two
	 * dimensional array. The parsing stops when the &lt;/table...&gt; after
	 * &lt;/tr&gt; is reached. If the "hasTableTag" flag is true, the extraction
	 * starts from the first &lt;table&gt; tag encountered. If the flag
	 * "skipFirstRow" is set to true, then the first row (contained within tr
	 * tags) of data are ignored. This is typically used when the first row
	 * contains column labels. If the flag "filterOutTags" is set to true, then
	 * all the extra tags between &lt;td&gt; and &lt;/td&gt; or &lt;th&gt; and
	 * &lt;/th&gt; are discarded. The "selection" is used to selecte a specific
	 * set of columns. &lt;br&gt;If an HTML page contains multiple tables, the
	 * page can be parsed with Text.parse() method first to extract a block of
	 * text which contains the table to be parsed.
	 * 
	 * @param src
	 *            the HTML text block to be parsed.
	 * @param selection
	 *            the selected element indexes. It can be passed in through a
	 *            Java Number array (Number[]) or a JavaScript array, for
	 *            example, &lt;code&gt;new Array(2, 3, 5, 9)&lt;/code&gt;.
	 * @param hasTableTag
	 *            the text block to be parsed contains the top level
	 *            "&lt;table ...&gt;" tage. (Table tags within td is not
	 *            considered.)
	 * @param skipFirstRow
	 *            indicates whether the first row of data should be skipped.
	 * @param filterOutTags
	 *            indicates whether the extra tags between &lt;td&gt; and
	 *            &lt;/td&gt; or &lt;th&gt; and &lt;/th&gt; should be discarded.
	 * @return the result two dimensional data array.
	 */
	public static Object[][] parseTable(String src, Object selection, boolean hasTableTag, boolean skipFirstRow,
			boolean filterOutTags) throws Exception {
		if (src == null)
			return null;

		Trace trace = ContextControl.getTrace();

		if (firstTime) {
			// trace.log(Trace.DEBUG, "hawk.debugParsing=" +
			// System.getProperty("hawk.debugParsing"));
			if (System.getProperty("hawk.debugParsing") != null)
				debugParsing = true;
			firstTime = false;
		}

		Object[] ia = null;
		if (selection != null) {
			try {
				// trace.log(Trace.DEBUG, "selection=" + selection);
				ia = (Object[]) JSHandler.convertToJavaArray(selection);
			} catch (Exception err) {
				trace.log(Trace.DEBUG, "Exception: " + err);
			}
		}

		HTMLTokenizer t = new HTMLTokenizer(new ByteArrayInputStream(src.getBytes()), sTableTags);

		boolean notag_in_td = filterOutTags;
		int tagType;
		Vector table = new Vector();
		Vector row = null;
		String s = new String("");
		int col_cnt = -1;

		boolean[][] stack = new boolean[20][3];
		int in_table = 0, in_tr = 1, in_td = 2;
		int sp = -1;
		if (!hasTableTag) {
			sp = 0;
			stack[0][in_table] = true;
			stack[0][in_tr] = false;
			stack[0][in_td] = false;

			skipFirstRow = false; // Makes no sense to skip the first row
									// since it's already in the first row
									// and will skip everything until next tr
									// tag
		}

		while ((tagType = t.nextHTML()) != HTMLTokenizer.HTML_EOF) {
			if (debugParsing)
				trace.log(Trace.DEBUG, "tagType=" + tagType);
			if (sp < 0 && tagType != HTML_TABLE)
				continue;

			if (!hasTableTag) {
				if (tagType != HTML_TR)
					continue;
				else
					hasTableTag = true;
			}

			if (tagType == HTML_TABLE) {
				if (!notag_in_td && (sp > 0 || (sp == 0 && stack[0][in_td])))
					s = s.concat(t.sval);
				sp++;
				stack[sp][in_table] = true;
				stack[sp][in_tr] = false;
				stack[sp][in_td] = false;
				row = null;
				s = new String("");
			} else if (tagType == HTML_table) {
				if (!notag_in_td && (sp > 0 || (sp == 0 && stack[0][in_td])))
					s = s.concat(t.sval);
				--sp;
				if (sp < 0)
					break;
			} else if (tagType == HTML_TR) {
				if (!notag_in_td && (sp > 0 || (sp == 0 && stack[0][in_td] && stack[0][in_tr])))
					s = s.concat(t.sval);
				if (sp == 0 && (!stack[0][in_td])) {
					if (stack[0][in_tr] && (row != null)) {
						table.addElement(row);
						if (row.size() > col_cnt)
							col_cnt = row.size();
					}
					s = new String("");
					row = new Vector();
				}

				if (!stack[sp][in_td])
					stack[sp][in_tr] = true;
			} else if (tagType == HTML_tr) {
				if (!notag_in_td && (sp > 0 || (sp == 0 && stack[0][in_td] && stack[0][in_tr])))
					s = s.concat(t.sval);
				if (sp == 0 && (!stack[0][in_td]) && stack[0][in_tr]) {
					if (row != null) {
						table.addElement(row);
						if (row.size() > col_cnt)
							col_cnt = row.size();
						row = null;
					}
					s = new String("");
				}
				if (!stack[sp][in_td])
					stack[sp][in_tr] = false;
			} else if ((tagType == HTML_TD) || (tagType == HTML_TH)) {
				if (!notag_in_td && (sp > 0))
					s = s.concat(t.sval);
				if (sp == 0 && stack[0][in_td] && stack[0][in_tr]) {
					if (row != null)
						row.addElement(s);
					s = new String("");
				}
				stack[sp][in_td] = true;
			} else if ((tagType == HTML_td) || (tagType == HTML_th)) {
				// trace.log(Trace.DEBUG, "td state: sp = " + sp + " " +
				// stack[0][in_tr] +
				// " " + stack[0][in_td]);
				if (!notag_in_td && (sp > 0))
					s = s.concat(t.sval);
				if (sp == 0 && stack[0][in_td] && stack[0][in_tr]) {
					if (row != null)
						row.addElement(s);
					s = new String("");
				}
				stack[sp][in_td] = false;
			} else { // no matter it's text or unknown tags
						// trace.log(Trace.DEBUG, "extra " + t.sval +
						// " state: sp = " + sp + " " + stack[0][in_tr] +
						// " " + stack[0][in_td]);
				if (notag_in_td && sp > 0)
					continue;
				if (sp > 0 || (sp == 0 && stack[0][in_td] && stack[0][in_tr]))
					if (tagType == HTMLTokenizer.HTML_TEXT)
						s = s.concat(t.sval);
					else if (!notag_in_td)
						s = s.concat("<" + t.sval + ">");
			}
		}

		int row_cnt = table.size();
		// trace.log(Trace.DEBUG, "row = " + row_cnt + " col = " + col_cnt);
		if (row_cnt <= 0)
			return null;

		Object[][] result = null;
		if (skipFirstRow) {
			if (row_cnt == 1)
				return null;
			result = new Object[row_cnt - 1][col_cnt];
		} else
			result = new Object[row_cnt][col_cnt];

		int n = 0;
		for (int i = 0; i < row_cnt; i++) {
			n = i;
			if (skipFirstRow) {
				if (i == 0)
					continue;
				n = i - 1;
			}

			Vector r = (Vector) table.elementAt(i);
			if (r == null)
				continue;
			for (int j = 0; j < r.size(); j++) {
				result[n][j] = r.elementAt(j);
				if (debugParsing)
					trace.log(Trace.DEBUG, "Row " + n + " col " + j + " =" + result[n][j]);
			}
		}

		return ArrayUtil.selectColumns(result, selection);
	}

	/**
	 * This is a convenient method to add HTML table tags for a NamedTabularData
	 * instance. The result is a block of text between &lt;table...&gt; and
	 * &lt;/table&gt; (The &lt;table...&gt; and &lt;/table&gt; are not
	 * included.) Typically, it's in a form of <br>
	 * 
	 * <pre>
	 * &lt;tr&gt;&lt;th&gt;.. &lt;/th&gt;&lt;th&gt;...&lt;/th&gt;... &lt;/tr&gt;
	 * &lt;tr&gt;&lt;td&gt;.. &lt;/td&gt;&lt;td&gt;...&lt;/td&gt;... &lt;/tr&gt;
	 * &lt;tr&gt;&lt;td&gt;.. &lt;/td&gt;&lt;td&gt;...&lt;/td&gt;... &lt;/tr&gt;
	 * </pre>
	 * 
	 * <br>
	 * The user can specify the detailed tr tags for the label row, the th tags,
	 * the tr tags for the data rows, and the td tags for the data rows.
	 * 
	 * @param selectedColumns
	 *            the selected column names (include auxiliary data names. It
	 *            can be passed in through a Java String array (String[]) or a
	 *            JavaScript array, for example, &lt;code&gt;new Array("col1",
	 *            "col2", "col3")&lt;/code&gt;.
	 * @param label_tr
	 *            the tr tag for the label row. It could be a tr tag with some
	 *            additional attributes or even with some additional tags. If
	 *            null is specified, the default "&lt;tr&gt;" is used.
	 * @param th
	 *            the th tag for the label row. It could be a th tag with some
	 *            additional attributes or even with some additional tags. If
	 *            null is specified, the default "&lt;th&gt;" is used.
	 * @param tr
	 *            the tr tag for the data row. It could be a tr tag with some
	 *            additional attributes or even with some additional tags. If
	 *            null is specified, the default "&lt;tr&gt;" is used.
	 * @param td
	 *            the td tag for the data row. It could be a td tag with some
	 *            additional attributes or even with some additional tags. If
	 *            null is specified, the default "&lt;td&gt;" is used.
	 * @param dateFormat
	 *            This is in the form of SimpleDateFormat. All the data with
	 *            java "Date" type will be converted with this format.
	 * @param maxFractionDigits
	 *            Maximum fraction digits that will be kept for Float or Double
	 *            type Data.
	 * @return the result HTML text block
	 * @see java.text.SimpleDateFormat
	 */
	public static String addTableTags(NamedTabularData data, Object selectedColumns, String label_tr, String th,
			String tr, String td, String dateFormat, int maxFractionDigits) {
		return addTableTags(data, selectedColumns, null, label_tr, th, tr, td, true, dateFormat, maxFractionDigits,
				false);
	}

	/**
	 * Same as the method above except with an additional parameter
	 * <code>displayColumnNames</code>.
	 * 
	 * @param displayColumnNames
	 *            the display column names for the selected columns. If it's
	 *            null, the original column names are used as the display column
	 *            names. If <code>selectedColumns</code> is null, then the
	 *            displayColumnNames must be either null or with the same length
	 *            as the orginal column names.
	 */

	public static String addTableTags(NamedTabularData data, Object selectedColumns, String[] displayColumnNames,
			String label_tr, String th, String tr, String td, String dateFormat, int maxFractionDigits) {
		return addTableTags(data, selectedColumns, displayColumnNames, label_tr, th, tr, td, true, dateFormat,
				maxFractionDigits, false);
	}

	/**
	 * This method is similar to the method with the first argument is of data
	 * type <code>NamedTabularData</code>.
	 */
	public static String addTableTags(NamedArray data, Object selectedColumns, String label_tr, String th, String tr,
			String td, String dateFormat, int maxFractionDigits) {
		NamedTabularData n = new NamedTabularData(data.getElementNames(), new Object[][] { data.getData() });
		return addTableTags(n, selectedColumns, null, label_tr, th, tr, td, true, dateFormat, maxFractionDigits, false);
	}

	/**
	 * Same as the method above except with an additional parameter
	 * <code>displayColumnNames</code>.
	 * 
	 * @param displayColumnNames
	 *            the display column names for the selected columns. If it's
	 *            null, the original column names are used as the display column
	 *            names. If <code>selectedColumns</code> is null, then the
	 *            displayColumnNames must be either null or with the same length
	 *            as the orginal column names.
	 */

	public static String addTableTags(NamedArray data, Object selectedColumns, String[] displayColumnNames,
			String label_tr, String th, String tr, String td, String dateFormat, int maxFractionDigits) {
		NamedTabularData n = new NamedTabularData(data.getElementNames(), new Object[][] { data.getData() });
		return addTableTags(n, selectedColumns, displayColumnNames, label_tr, th, tr, td, true, dateFormat,
				maxFractionDigits, false);
	}

	/**
	 * This method is equivalent to call: &lt;code&gt;&lt;pre&gt;
	 * addTableTags(data, selectedColumns, null, null, tr, td, dateFormat,
	 * maxFractionDigits); &lt;/pre&gt;&lt;/code&gt; and the label row is
	 * excluded.
	 */
	public static String addTableTags(NamedTabularData data, Object selectedColumns, String tr, String td,
			String dateFormat, int maxFractionDigits) {
		return addTableTags(data, selectedColumns, null, null, null, tr, td, false, dateFormat, maxFractionDigits,
				false);
	}

	/**
	 * Same as the method above except with an additional parameter
	 * <code>displayColumnNames</code>.
	 * 
	 * @param displayColumnNames
	 *            the display column names for the selected columns. If it's
	 *            null, the original column names are used as the display column
	 *            names. If <code>selectedColumns</code> is null, then the
	 *            displayColumnNames must be either null or with the same length
	 *            as the orginal column names.
	 */

	public static String addTableTags(NamedTabularData data, Object selectedColumns, String[] displayColumnNames,
			String tr, String td, String dateFormat, int maxFractionDigits) {
		return addTableTags(data, selectedColumns, displayColumnNames, null, null, tr, td, false, dateFormat,
				maxFractionDigits, false);
	}

	private static String addTableTags(NamedTabularData data, Object selectedColumns, String[] displayColumnNames,
			String label_tr, String th, String tr, String td, boolean includeLabel, String dateFormat,
			int maxFractionDigits, boolean skipFirstTr) {
		if (data == null)
			return "";

		if (tr == null)
			tr = "<tr>";
		if (th == null)
			th = "<th>";
		if (label_tr == null)
			label_tr = "<tr>";
		if (td == null)
			td = "<td>";

		StringBuffer s = new StringBuffer();

		String[] colNames = data.getColumnNames();
		Object[] cols = null;
		int[] index = null;
		if (selectedColumns == null) {
			cols = colNames;
			index = new int[cols.length];
			for (int i = 0; i < cols.length; i++)
				index[i] = i;
		} else {
			if (colNames == null) { // TODO
				return "";
			}
			cols = JSHandler.convertToJavaArray(selectedColumns);
			if (cols == null) { // TODO
				return "";
			}
			index = new int[cols.length];
			for (int i = 0; i < cols.length; i++) {
				int k = -1;
				for (int j = 0; j < colNames.length; j++)
					if (((String) cols[i]).equals(colNames[j])) {
						k = j;
						break;
					}
				index[i] = k;

			}
		}

		// trace.log(Trace.DEBUG, "cols="+ObjChecker.toString(cols));
		if (includeLabel) {
			int dn = displayColumnNames == null ? 0 : displayColumnNames.length;
			s.append(label_tr);
			for (int j = 0; j < cols.length; j++) {
				String thTag = th;
				s.append(thTag + (j >= dn ? cols[j] : displayColumnNames[j]) + "</th>");
			}
			s.append("</tr>");
		}

		DecimalFormat nf = new DecimalFormat();
		nf.setMaximumFractionDigits(maxFractionDigits);

		Object[][] d = data.getData();
		if (d == null)
			return s.toString();

		for (int i = 0; i < d.length; i++) {
			if (!skipFirstTr)
				s.append(tr);
			for (int j = 0; j < cols.length; j++) {
				String str = "&nbsp;";
				Object e = null;

				if (index[j] >= 0)
					e = d[i][index[j]];
				else
					e = data.getTableAttr((String) cols[j]);

				if (e != null) {
					if (e instanceof String)
						str = (String) e;
					else if (e instanceof Double || e instanceof Float)
						str = nf.format(((Number) e).doubleValue());
					else if (e instanceof Date && dateFormat != null) {
						str = TimeTool.localtime(((Date) e).getTime(), dateFormat);
					} else
						str = e.toString();
				}
				s.append(td + str + "</td>");

			}
			s.append("</tr>");
			// trace.log(Trace.DEBUG, "curr s="+s);
		}
		return s.toString();
	}

	/**
	 * This method is to escape angle bracket characters for all String type
	 * elements in a NamedTabularData. (Those elements are replaced in place.)
	 */
	public static void escapeAngleBrackets(NamedTabularData data) {
		Object[][] d = data.getData();
		if (d == null)
			return;

		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < d[i].length; j++) {
				Object e = d[i][j];

				if (e != null && e instanceof String) {
					String str = Text.substitute((String) e, ">", "&gt;", false);
					d[i][j] = Text.substitute(str, "<", "&lt;", false);
				}
			}
		}
	}

	/**
	 * This method is to escape angle bracket characters for all String type
	 * elementsn a NamedArray. (Those elements are replaced in place.)
	 */
	public static void escapeAngleBrackets(NamedArray data) {
		Object[] d = data.getData();
		if (d == null)
			return;

		for (int i = 0; i < d.length; i++) {
			Object e = d[i];

			if (e != null && e instanceof String) {
				String str = Text.substitute((String) e, ">", "&gt;", false);
				d[i] = Text.substitute(str, "<", "&lt;", false);
			}
		}
	}

	/**
	 * This is a convenient method to add HTML table tags for an array of
	 * NamedTabularData instance. The result is a block of text between
	 * &lt;table...&gt; and &lt;/table&gt; (The &lt;table...&gt; and
	 * &lt;/table&gt; are not included.) Typically, it's in a form of <br>
	 * 
	 * <pre>
	 * &lt;tr&gt;&lt;th&gt;.. &lt;/th&gt;&lt;th&gt;...&lt;/th&gt;... &lt;/tr&gt;
	 * &lt;tr&gt;&lt;td&gt;.. &lt;/td&gt;&lt;td&gt;...&lt;/td&gt;... &lt;/tr&gt;
	 * &lt;tr&gt;&lt;td&gt;.. &lt;/td&gt;&lt;td&gt;...&lt;/td&gt;... &lt;/tr&gt;
	 * </pre>
	 * 
	 * <br>
	 * The user can specify the detailed tr tags for the label row, the th tags,
	 * the tr tags for the data rows, and the td tags for the data rows. If
	 * there are "table attribute" columns at the front, those cells will have
	 * row span equal to the number of the rows of the corresponding TabularData
	 * array element.
	 * 
	 * @param selectedColumns
	 *            the selected column names (include auxiliary data names. It
	 *            can be passed in through a Java String array (String[]) or a
	 *            JavaScript array, for example, &lt;code&gt;new Array("col1",
	 *            "col2", "col3")&lt;/code&gt;.
	 * @param label_tr
	 *            the tr tag for the label row. It could be a tr tag with some
	 *            additional attributes or even with some additional tags. If
	 *            null is specified, the default "&lt;tr&gt;" is used.
	 * @param th
	 *            the th tag for the label row. It could be a th tag with some
	 *            additional attributes or even with some additional tags. If
	 *            null is specified, the default "&lt;th&gt;" is used.
	 * @param tr
	 *            the tr tag for the data row. It could be a tr tag with some
	 *            additional attributes or even with some additional tags. If
	 *            null is specified, the default "&lt;tr&gt;" is used.
	 * @param td
	 *            the td tag for the data row. It could be a td tag with some
	 *            additional attributes or even with some additional tags. If
	 *            null is specified, the default "&lt;td&gt;" is used.
	 * @param dateFormat
	 *            This is in the form of SimpleDateFormat. All the data with
	 *            java "Date" type will be converted with this format.
	 * @param maxFractionDigits
	 *            Maximum fraction digits that will be kept for Float or Double
	 *            type Data.
	 * @return the result HTML text block
	 * @see java.text.SimpleDateFormat
	 */

	public static String addNestedTableTags(NamedTabularData[] dataArray, Object selectedColumns, String label_tr,
			String th, String tr, String td, String dateFormat, int maxFractionDigits) {
		return addNestedTableTags(dataArray, selectedColumns, null, label_tr, th, tr, td, dateFormat,
				maxFractionDigits, false);
	}

	/**
	 * Same as the method above except with an additional parameter
	 * <code>displayColumnNames</code>.
	 * 
	 * @param displayColumnNames
	 *            the display column names for the selected columns. If it's
	 *            null, the original column names are used as the display column
	 *            names. If <code>selectedColumns</code> is null, then the
	 *            displayColumnNames must be either null or with the same length
	 *            as the orginal column names.
	 */

	public static String addNestedTableTags(NamedTabularData[] dataArray, Object selectedColumns,
			String[] displayColumnNames, String label_tr, String th, String tr, String td, String dateFormat,
			int maxFractionDigits, boolean inReverseOrder) {
		if (dataArray == null || dataArray.length == 0)
			return "";
		NamedTabularData data = dataArray[0];

		if (tr == null)
			tr = "<tr>";
		if (th == null)
			th = "<th>";
		if (label_tr == null)
			label_tr = "<tr>";
		if (td == null)
			td = "<td>";

		StringBuffer s = new StringBuffer();

		String[] colNames = data.getColumnNames();
		if (colNames == null)
			colNames = new String[0];
		String[] nonAuxCols = null;
		Object[] cols = null;
		int[] index = null;
		int auxDataLen = 0;
		if (selectedColumns == null) {
			cols = colNames;
			index = new int[cols.length];
			for (int i = 0; i < cols.length; i++)
				index[i] = i;
			nonAuxCols = colNames;
		} else {
			cols = JSHandler.convertToJavaArray(selectedColumns);
			if (cols == null) { // TODO
				return "";
			}
			index = new int[cols.length];
			auxDataLen = -1;
			for (int i = 0; i < cols.length; i++) {
				int k = -1;
				for (int j = 0; j < colNames.length; j++)
					if (((String) cols[i]).equals(colNames[j])) {
						k = j;
						break;
					}
				index[i] = k;
				if (k >= 0 && auxDataLen < 0)
					auxDataLen = i;

			}
			if (auxDataLen < 0)
				auxDataLen = cols.length; // no non-aux data
			nonAuxCols = new String[cols.length - auxDataLen];
			for (int j = 0; j < nonAuxCols.length; j++)
				nonAuxCols[j] = (String) cols[j + auxDataLen];
		}
		// trace.log(Trace.DEBUG,
		// "nonAuxCols="+ObjChecker.toString(nonAuxCols));
		// trace.log(Trace.DEBUG, "index[]="+ObjChecker.toString(index));

		{
			int dn = displayColumnNames == null ? 0 : displayColumnNames.length;
			s.append(label_tr);
			for (int j = 0; j < auxDataLen; j++) {
				s.append(th + (j >= dn ? cols[j] : displayColumnNames[j]) + "</th>");
			}
			// s.append("<td>" + table + "<tr>");
			for (int j = auxDataLen; j < cols.length; j++) {
				s.append(th + (j >= dn ? cols[j] : displayColumnNames[j]) + "</th>");
			}
			// s.append("</tr></table></td></tr>");
			s.append("</tr>");
		}

		DecimalFormat nf = new DecimalFormat();
		nf.setMaximumFractionDigits(maxFractionDigits);
		int arrayLength = dataArray.length;
		for (int l = 0; l < arrayLength; l++) {
			int i = l;
			if (inReverseOrder) {
				i = arrayLength - l - 1;
			}
			Object[][] d = dataArray[i].getData();
			if (d == null)
				continue;
			s.append(tr);
			for (int j = 0; j < cols.length; j++) {
				String str = "&nbsp;";
				Object e = null;

				if (index[j] >= 0)
					break;
				else
					e = dataArray[i].getTableAttr((String) cols[j]);

				if (e != null) {
					if (e instanceof String)
						str = (String) e;
					else if (e instanceof Double || e instanceof Float)
						str = nf.format(((Number) e).doubleValue());
					else if (e instanceof Date && dateFormat != null) {
						str = TimeTool.localtime(((Date) e).getTime(), dateFormat);
					} else
						str = e.toString();
				}
				s.append(td.substring(0, 3) + " rowspan=" + d.length + td.substring(3) + str + "</td>");

			}
			// s.append("<td>");
			// s.append(table);

			// String tmp = addTableTags(dataArray[i], nonAuxCols, tr, td,
			// dateFormat, maxFractionDigits);
			String tmp = addTableTags(dataArray[i], nonAuxCols, null, null, null, tr, td, false, dateFormat,
					maxFractionDigits, true);
			s.append(tmp);
			// s.append("</table></td></tr>");
			// trace.log(Trace.DEBUG, "s="+s);
		}
		return s.toString();
	}

	/**
	 * This is a convenient method to add HTML table tags for a NamedTabularData
	 * instance. The result is a block of text between &lt;table...&gt; and
	 * &lt;/table&gt; (The &lt;table...&gt; and &lt;/table&gt; are not
	 * included.) Typically, it's in a form of <br>
	 * 
	 * <pre>
	 * &lt;tr&gt;&lt;th&gt;.. &lt;/th&gt;&lt;th&gt;...&lt;/th&gt;... &lt;/tr&gt;
	 * &lt;tr&gt;&lt;td&gt;.. &lt;/td&gt;&lt;td&gt;...&lt;/td&gt;... &lt;/tr&gt;
	 * &lt;tr&gt;&lt;td&gt;.. &lt;/td&gt;&lt;td&gt;...&lt;/td&gt;... &lt;/tr&gt;
	 * </pre>
	 * 
	 * <br>
	 * The user can use the template parameters to specify the detail
	 * content.The templates are in the form of MessageFormat. For each element
	 * in the format, detailed style can be specified.
	 * 
	 * @param selectedColumns
	 *            the selected column names (include auxiliary data names. It
	 *            can be passed in through a Java String array (String[]) or a
	 *            JavaScript array, for example, &lt;code&gt;new Array("col1",
	 *            "col2", "col3")&lt;/code&gt;.
	 * @param labelTemplate
	 *            the template for the label line. The template is in the form
	 *            of MessageFormat. Typically it's like
	 *            "&lt;tr&gt;&lt;th&gt;{0}&lt;/th&gt;&lt;th&gt;{1}&lt;/th&gt;... &lt;/tr&gt;"
	 *            where the {0}, (1} ... correspond to the column names
	 *            specified in the "selectedColumns" parameter.
	 * @param dataTemplate
	 *            the template for the data lines. The template is in the form
	 *            of MessageFormat. Typically it's like
	 *            "&lt;tr&gt;&lt;td&gt;{0}&lt;/td&gt;&lt;td&gt;{1}&lt;/td&gt;... &lt;/tr&gt;"
	 *            where the {0}, (1} ... correspond to data associated with the
	 *            column names specified in the "selectedColumns" parameter.
	 * @return the result HTML text block
	 * @see java.text.MessageFormat
	 */
	public static String addTableTags(NamedTabularData data, Object selectedColumns, String labelTemplate,
			String dataTemplate) {
		String[] colNames = data.getColumnNames();
		Object[] cols = null;
		int[] index = null;
		StringBuffer s = new StringBuffer();
		if (selectedColumns == null) {
			cols = colNames;
			index = new int[cols.length];
			for (int i = 0; i < cols.length; i++)
				index[i] = i;
		} else {
			if (colNames == null) { // TODO
				return "";
			}
			cols = JSHandler.convertToJavaArray(selectedColumns);
			if (cols == null) { // TODO
				return "";
			}
			index = new int[cols.length];
			for (int i = 0; i < cols.length; i++) {
				int k = -1;
				for (int j = 0; j < colNames.length; j++)
					if (((String) cols[i]).equals(colNames[j])) {
						k = j;
						break;
					}
				index[i] = k;

			}
		}
		if (labelTemplate != null)
			s.append(MessageFormat.format(labelTemplate, cols));

		Object[][] d = data.getData();
		if (d == null || dataTemplate == null)
			return s.toString();

		MessageFormat mf = new MessageFormat(dataTemplate);
		for (int n = 0; n < d.length; n++) {
			Object[] params = new Object[cols.length];
			for (int i = 0; i < cols.length; i++) {
				if (index[i] >= 0)
					params[i] = d[n][index[i]];
				else
					params[i] = data.getTableAttr((String) cols[i]);
			}
			String r = mf.format(params);
			s.append(r);
		}
		return s.toString();

	}

	/**
	 * This is a convenient method to add HTML table tags for an array of
	 * NamedTabularData instances. The result is a block of text between
	 * &lt;table...&gt; and &lt;/table&gt; (The &lt;table...&gt; and
	 * &lt;/table&gt; are not included.) Typically, it's in a form of <br>
	 * 
	 * <pre>
	 * &lt;tr&gt;&lt;th&gt;..&lt;/th&gt;&lt;th&gt;..&lt;/th&gt;...&lt;td&gt;&lt;table&gt;&lt;tr&gt;&lt;th&gt;..&lt;/th&gt;&lt;th&gt;..&lt;/th&gt;...&lt;/tr&gt;&lt;/table&gt;&lt;/td&gt;&lt;/tr&gt;
	 * &lt;tr&gt;&lt;td&gt;.. &lt;/td&gt;&lt;td&gt;...&lt;/td&gt;...&lt;td&gt;&lt;table&gt;&lt;tr&gt;&lt;td&gt;..&lt;/td&gt;&lt;td&gt;..&lt;/td&gt;...&lt;/tr&gt;&lt;/table&gt;&lt;/td&gt;&lt;/tr&gt;
	 * &lt;tr&gt;&lt;td&gt;.. &lt;/td&gt;&lt;td&gt;...&lt;/td&gt;...&lt;td&gt;&lt;table&gt;&lt;tr&gt;&lt;td&gt;..&lt;/td&gt;&lt;td&gt;..&lt;/td&gt;...&lt;/tr&gt;&lt;/table&gt;&lt;/td&gt;&lt;/tr&gt;
	 * </pre>
	 * 
	 * <br>
	 * The first few columns are for the "auxiliary data" associated with each
	 * NamedTabularData instance which would be shared by multiple rows of the
	 * data in that NamedTabularData instance. The user can use the template
	 * parameters to specify the detail content.The templates are in the form of
	 * MessageFormat. For each element in the format, detailed style can be
	 * specified.
	 * 
	 * @param selectedColumns
	 *            the selected column names (include auxiliary data names. It
	 *            can be passed in through a Java String array (String[]) or a
	 *            JavaScript array, for example, &lt;code&gt;new Array("col1",
	 *            "col2", "col3")&lt;/code&gt;.
	 * @param labelTemplate
	 *            the template for the label line. The template is in the form
	 *            of MessageFormat. Typically it's like
	 *            "&lt;tr&gt;&lt;th&gt;{0}&lt;/th&gt;&lt;th&gt;{1}&lt;/th&gt;...&lt;td&gt;&lt;table&gt;&lt;tr&gt;&lt;th&gt;{2}&lt;/th&gt;&lt;th&gt;{3}&lt;/th&gt;...&lt;/tr&gt;&lt;/table&gt;&lt;/td&gt;&lt;/tr&gt;"
	 *            where the {0}, (1} ... correspond to the column names
	 *            specified in the "selectedColumns" parameter.
	 * @param dataTemplate
	 *            the template for the data lines. The template is in the form
	 *            of MessageFormat. Typically it's like
	 *            "&lt;tr&gt;&lt;td&gt;{0}&lt;/td&gt;&lt;td&gt;{1}&lt;/td&gt;...&lt;td&gt;&lt;table&gt;&lt;tr&gt;&lt;td&gt;{2}&lt;/td&gt;&lt;td&gt;{3}&lt;/td&gt;...&lt;/tr&gt;&lt;/table&gt;&lt;/td&gt;&lt;/tr&gt;"
	 *            where the {0}, (1} ... correspond to data associated with the
	 *            column names specified in the "selectedColumns" parameter.
	 * @return the result HTML text block
	 * @see java.text.MessageFormat
	 */
	public static String addNestedTableTags(NamedTabularData[] dataArray, Object selectedColumns, String labelTemplate,
			String dataTemplate) {
		if (dataArray == null || dataArray.length == 0)
			return "";
		NamedTabularData data = dataArray[0];
		Trace trace = ContextControl.getTrace();

		String[] colNames = data.getColumnNames();
		Object[] cols = null;
		int[] index = null;
		StringBuffer s = new StringBuffer();
		if (selectedColumns == null) {
			cols = colNames;
			index = new int[cols.length];
			for (int i = 0; i < cols.length; i++)
				index[i] = i;
		} else {
			if (colNames == null) { // TODO
				return "";
			}
			cols = JSHandler.convertToJavaArray(selectedColumns);
			if (cols == null) { // TODO
				return "";
			}
			index = new int[cols.length];
			for (int i = 0; i < cols.length; i++) {
				int k = -1;
				for (int j = 0; j < colNames.length; j++)
					if (((String) cols[i]).equals(colNames[j])) {
						k = j;
						break;
					}
				index[i] = k;

			}
		}
		if (labelTemplate != null)
			s.append(MessageFormat.format(labelTemplate, cols));

		if (dataTemplate == null)
			return s.toString();

		int tr_open = dataTemplate.indexOf("<TR", 3);
		if (tr_open < 0)
			tr_open = dataTemplate.indexOf("<tr", 3);
		if (tr_open < 0) {
			// TODO
			return s.toString();
		}
		int tr_close = dataTemplate.indexOf("<TR", tr_open);
		if (tr_close < 0)
			tr_close = dataTemplate.indexOf("</tr", tr_open);
		if (tr_close < 0) {
			// TODO
			return s.toString();
		}
		int tr_end = dataTemplate.indexOf(">", tr_close);

		String outer_template = dataTemplate.substring(0, tr_open) + "__TR_ROWS__" + dataTemplate.substring(tr_end + 1);

		String inner_template = dataTemplate.substring(tr_open, tr_end + 1);

		trace.log(Trace.DEBUG, "outer_template=" + outer_template + " inner_template" + inner_template);
		MessageFormat imf = new MessageFormat(inner_template);
		MessageFormat omf = new MessageFormat(outer_template);
		for (int l = 0; l < dataArray.length; l++) {
			Object[] params = new Object[cols.length];
			Object[][] d = dataArray[l].getData();
			if (d == null)
				continue;
			for (int i = 0; i < cols.length; i++) {
				if (index[i] >= 0)
					params[i] = null;
				else
					params[i] = dataArray[l].getTableAttr((String) cols[i]);
			}
			String shell = omf.format(params);
			StringBuffer inner_s = new StringBuffer();
			for (int n = 0; n < d.length; n++) {
				for (int i = 0; i < cols.length; i++) {
					if (index[i] >= 0)
						params[i] = d[n][index[i]];
					else
						params[i] = null;
				}
				String r = imf.format(params);
				inner_s.append(r);
			}
			String block = Text.substitute(shell, "__TR_ROWS__", inner_s.toString(), true);
			// trace.log(Trace.DEBUG, "block=" + block);
			s.append(block);
		}
		return s.toString();

	}

	/**
	 * @deprecated Use addTableTags
	 */
	public static String addHTMLTags(NamedArray data, String label_tr, String th, String tr, String td,
			boolean includeLabel, int maxFractionDigits) {
		return addTableTags(data, null, label_tr, th, tr, td, null, maxFractionDigits);
	}

	/**
	 * @deprecated Use addTableTags
	 */
	public static String addHTMLTags(NamedTabularData data, String label_tr, String th, String tr, String td,
			boolean includeLabel, int maxFractionDigits) {
		return addTableTags(data, null, label_tr, th, tr, td, null, maxFractionDigits);
	}

	/*
	 * 
	 * static String addHTMLTags(NamedTabularData data, String label_tr, Object
	 * selectedColTagAttrs, String th, String tr, String td, boolean
	 * includeLabel, int maxFractionDigits) { Object[][] mdata = data.getData();
	 * String[] cnameList = data.getColumnNames();
	 * 
	 * if (mdata == null) return ""; if (tr == null) tr = "<tr>"; if (th ==
	 * null) th = "<th>"; if (label_tr == null) label_tr = "<tr>"; if (td ==
	 * null) td = "<td>";
	 * 
	 * Object[] colTagAttrs = null; if (selectedColTagAttrs != null) if
	 * (selectedColTagAttrs instanceof Object[]) colTagAttrs =
	 * (Object[])selectedColTagAttrs; else colTagAttrs =
	 * JSHandler.convertToJavaArray( selectedColTagAttrs); int colTagAttrsLen =
	 * colTagAttrs == null ? 0 : colTagAttrs.length;
	 * 
	 * DecimalFormat nf = new DecimalFormat();
	 * nf.setMaximumFractionDigits(maxFractionDigits);
	 * 
	 * String r = ""; for (int i = 0; i < mdata.length; i++) { if (i == 0 &&
	 * includeLabel) { r = r.concat(label_tr); String lastTagAttr = ""; for (int
	 * j = 0; j < mdata[0].length; j++) { String thTag = th; if (colTagAttrsLen
	 * > 0) { if (j < colTagAttrsLen && colTagAttrs[j] != null) lastTagAttr =
	 * colTagAttrs[j].toString(); if (! lastTagAttr.equals("")) if (th.length()
	 * == 4) thTag = Text.insertAt(3, th, " " + lastTagAttr); else thTag =
	 * Text.insertAt(3, th, " " + lastTagAttr + " "); } r = r.concat(thTag +
	 * cnameList[j] + "</th>"); } r = r.concat("</tr>"); } r = r.concat(tr);
	 * String lastTagAttr = ""; for (int j = 0; j < mdata[i].length; j++) {
	 * String s = " "; if (mdata[i][j] != null) if (mdata[i][j] instanceof
	 * Double || mdata[i][j] instanceof Float) s =
	 * nf.format(((Number)mdata[i][j]).doubleValue()); else s =
	 * mdata[i][j].toString(); String tdTag = td; if (colTagAttrsLen > 0) { if
	 * (j < colTagAttrsLen && colTagAttrs[j] != null) lastTagAttr =
	 * colTagAttrs[j].toString(); if (! lastTagAttr.equals("")) if (td.length()
	 * == 4) tdTag = Text.insertAt(3, td, " " + lastTagAttr); else tdTag =
	 * Text.insertAt(3, td, " " + lastTagAttr + " "); } r = r.concat(tdTag + s +
	 * "</td>");
	 * 
	 * } r = r.concat("</tr>"); } return r; }
	 * 
	 * 
	 * 
	 * public static String buildHTMLBlock(NamedTabularData[] array, Object
	 * selectedAuxDataList, Object selectedColList, String label_tr, String th,
	 * String table, String tr, String td, int maxFractionDigits, String
	 * timeFormat) { return buildHTMLBlock(array, selectedAuxDataList, null,
	 * selectedColList, null, label_tr, th, table, tr, td, maxFractionDigits,
	 * timeFormat); }
	 * 
	 * public static String buildHTMLBlock(NamedTabularData[] array, Object
	 * selectedAuxDataList, Object selectedAuxDataTagAttrs, Object
	 * selectedColList, Object selectedColTagAttrs, String label_tr, String th,
	 * String table, String tr, String td, int maxFractionDigits, String
	 * timeFormat) { Trace t = ContextControl.getTrace(); if (array == null ||
	 * array.length == 0) return "";
	 * 
	 * if (tr == null || tr.equals("")) tr = "<tr>"; if (th == null ||
	 * th.equals("")) th = "<th>"; if (label_tr == null || label_tr.equals(""))
	 * label_tr = "<tr>"; if (td == null || td.equals("")) td = "<td>"; if
	 * (table == null || table.equals("")) table = "<table>";
	 * 
	 * String r = ""; try {
	 * 
	 * LastError.clear(); //Trace t = ContextControl.getTrace();
	 * //t.log(Trace.DEBUG, "selectedAuxDataList=" +selectedAuxDataList);
	 * Object[] nameList = null; if (selectedAuxDataList instanceof Object[])
	 * nameList = (Object[])selectedAuxDataList; else nameList =
	 * JSHandler.convertToJavaArray(selectedAuxDataList); //t.log(Trace.DEBUG,
	 * "NameList=" +nameList);
	 * 
	 * Object[] auxDataTagAttrs = null; if (selectedAuxDataTagAttrs != null) if
	 * (selectedAuxDataTagAttrs instanceof Object[]) auxDataTagAttrs =
	 * (Object[])selectedAuxDataTagAttrs; else auxDataTagAttrs =
	 * JSHandler.convertToJavaArray( selectedAuxDataTagAttrs); int
	 * auxDataTagAttrsLen = auxDataTagAttrs == null ? 0 :
	 * auxDataTagAttrs.length;
	 * 
	 * Object[] colTagAttrs = null; if (selectedColTagAttrs != null) if
	 * (selectedColTagAttrs instanceof Object[]) colTagAttrs =
	 * (Object[])selectedColTagAttrs; else colTagAttrs =
	 * JSHandler.convertToJavaArray( selectedColTagAttrs); int colTagAttrsLen =
	 * colTagAttrs == null ? 0 : colTagAttrs.length;
	 * 
	 * 
	 * boolean firstDataFound = false; for (int i = 0; i < array.length; i++) {
	 * if (array[i] == null || array[i].getData() == null) continue;
	 * 
	 * NamedTabularData elem = selectedColList == null? array[i] :
	 * array[i].selectColumns(selectedColList); if (! firstDataFound) {
	 * firstDataFound = true; r = r.concat(label_tr); if (nameList != null) {
	 * String lastTagAttr = ""; for (int j = 0; j < nameList.length; j++) {
	 * String thTag = th; if (auxDataTagAttrsLen > 0) { if (j <
	 * auxDataTagAttrsLen && auxDataTagAttrs[j] != null) lastTagAttr =
	 * auxDataTagAttrs[j].toString(); if (! lastTagAttr.equals("")) if
	 * (th.length() == 4) thTag = Text.insertAt(3, th, " " + lastTagAttr); else
	 * thTag = Text.insertAt(3, th, " " + lastTagAttr + " "); } r =
	 * r.concat(thTag + nameList[j] + "</th>"); } }
	 * 
	 * if (elem != null && elem.getColumnNames() != null) { r = r.concat("<td>"
	 * + table + "<tr>"); String lastTagAttr = ""; for (int j = 0; j <
	 * elem.getColumnNames().length; j++) { String thTag = th; if
	 * (colTagAttrsLen > 0) { if (j < colTagAttrsLen && colTagAttrs[j] != null)
	 * lastTagAttr = colTagAttrs[j].toString(); if (! lastTagAttr.equals("")) if
	 * (th.length() == 4) thTag = Text.insertAt(3, th, " " + lastTagAttr); else
	 * thTag = Text.insertAt(3, th, " " + lastTagAttr + " "); } r =
	 * r.concat(thTag + elem.getColumnNames()[j] + "</th>"); } r =
	 * r.concat("</tr></table></td>"); } r = r.concat("</tr>"); } r =
	 * r.concat(tr); //t.log(Trace.DEBUG, "array " + i + "=" + elem); if (elem
	 * == null) continue; //t.log(Trace.DEBUG, "elem=" + elem); if (nameList !=
	 * null) for (int j = 0; j < nameList.length; j++) { Object data =
	 * elem.getTableAttr(nameList[j].toString()); String s = " "; if (data !=
	 * null) { if (((String)nameList[j]).equalsIgnoreCase("TimeStamp")) s =
	 * TimeTool.localtime(((Date)data).getTime(), timeFormat); else s =
	 * data.toString(); } String x = td + s + "</td>"; r = r.concat(x);
	 * 
	 * } if (elem.getData() != null) { String x = "<td>" + table +
	 * addHTMLTags(elem, label_tr, colTagAttrs, th, tr, td, false,
	 * maxFractionDigits) + "</table></td>"; //t.log(Trace.DEBUG, "i=" + i +
	 * " x=" +x); r = r.concat(x); } r = r.concat("</tr>"); }
	 * //t.log(Trace.DEBUG, "r=" +r); } catch (Throwable err) {
	 * LastError.set("Failed to build HTML segement because of " + err,
	 * sClassName + ".buildHTMLBlock()", err); } return r;
	 * 
	 * }
	 */

	/*
	 * public static Object[] convertFractions(Object[] a, Object selection)
	 * throws Exception { if (a == null) return null; int size = a.length;
	 * Object o; Object[] ia = null; if (selection != null) { try {
	 * //mLogger.log(Trace.DEBUG,"selection=" + selection); ia =
	 * (Object[])JSHandler.convertToJavaArray(selection); } catch (Exception
	 * err) { Trace t = ContextControl.getTrace();
	 * t.log(Trace.DEBUG,"Exception: " + err); } } int i = 0; for (int j = 0; j
	 * < 64; j++) { if (ia != null) { if (j >= ia.length) break; i =
	 * ((Number)ia[j]).intValue(); }
	 * 
	 * o = a[i]; if (o instanceof String) try { String s =
	 * ((String)o).toLowerCase(); String e1 = s; String e2 = ""; String e3 = "";
	 * int idx = s.indexOf("<sup>"); if (idx >= 0) { e1 = s.substring(0, idx);
	 * e2 = s.substring(idx+5); } s = e2; idx = s.indexOf("</sup>"); if (idx >=
	 * 0) { e2 = s.substring(0, idx); e3 = s.substring(idx+6); } idx =
	 * e3.indexOf("<sub>"); if (idx >= 0) { e3 = e3.substring(idx+5); } idx =
	 * e3.indexOf("</sub>"); if (idx >= 0) { e3 = e3.substring(0,idx); } e1 =
	 * e1.trim(); e2 = e2.trim(); e3 = e3.trim(); // mLogger.log(Trace.DEBUG, //
	 * "segs=" + e1 + "," + e2 + "," + e3); Double whole = new
	 * Double((double)0); if (! e1.equals("")) whole = new Double(e1); Double
	 * fraction = new Double(0); if (! (e2.equals("") || e2.equals("")))
	 * fraction = new Double(Double.valueOf(e2).doubleValue() /
	 * Double.valueOf(e3).doubleValue()); Double f = new
	 * Double(whole.doubleValue() + fraction.doubleValue()); a[i] = f; } catch
	 * (Exception err) { Trace t = ContextControl.getTrace();
	 * t.log(Trace.DEBUG,"convert error:" + err); continue; };
	 * 
	 * } // for return a;
	 * 
	 * }
	 */

}
