/**
 * 
 */
package com.tibco.cep.decision.table.provider.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author aathalye
 *
 */
public class CSVParser {
	
	private List<CSVListener> listeners = new ArrayList<CSVListener>();
	
	private static final String DELMITER = ",";
	
	/**
	 * Column position counter
	 */
	private int columnCounter;
	
	/**
	 * Line Number counter required for correct positioning.
	 */
	private int rowCounter;
	
	/**
	 * The column separator to be used.
	 */
	private String columnSeparator;
	
	public CSVParser(String columnSeparator){
		if(columnSeparator==null || columnSeparator.trim().isEmpty())
		{
			this.columnSeparator = DELMITER;
		}
		else{
			this.columnSeparator = columnSeparator;
		}
	}
	
	/**
	 * StringTokenizer cannot be used because it does not
	 * include the delimiter token thus resulting in garbled data.
	 * @param inputStream
	 * @throws Exception
	 */
	public void parse(InputStream inputStream) throws Exception {
		if (inputStream == null) {
			throw new IllegalArgumentException("The argument input stream cannot be null");
		}
		LineNumberReader reader =
			new LineNumberReader(new InputStreamReader(inputStream));
		String line = null;
		while ((line = reader.readLine()) != null) {
		 	rowCounter = reader.getLineNumber();
			populateTokens(line);
		}
	}
	
//	public void parse(InputStream inputStream) throws Exception {
//		if (inputStream == null) {
//			throw new IllegalArgumentException("The argument input stream cannot be null");
//		}
//		BufferedReader reader =
//			new BufferedReader(new InputStreamReader(inputStream));
//		StreamTokenizer sToken = new StreamTokenizer(reader);
////		sToken.resetSyntax();
//		//sToken.ordinaryChar('\'');
////		sToken.wordChars('A', 'z');
//		sToken.wordChars('!', '}');
////		sToken.parseNumbers();
//		sToken.quoteChar('\t');
//		sToken.quoteChar('\r');
//		sToken.eolIsSignificant(true);
//		populateTokens(sToken);
//	}
	
	/**
	 * Take a line and copy fire event for each token in it.
	 */
	private void populateTokens(final String line) throws IOException {
		columnCounter = 0;
		//Get all tokens in it
		String[] lineTokens = getLineTokens(line);
		int length = lineTokens.length;
		for (int loop = 0; loop < length; loop++) {
			columnCounter = loop;
			fireWordEvent(lineTokens[loop]);
		}
	}
	
	/**
	 * Parse the line into tokens.
	 * <p>
	 * Cannot use StringTokenizer because it removes the delimiter which we need.
	 * </p>
	 * @param line
	 * @return
	 */
	private String[] getLineTokens(String line) {
		
		if(line.replaceAll(columnSeparator, "").trim().isEmpty()){
			return new String[0];
		}
		else{
			return (line.split(columnSeparator));
		}
	/*	char[] characters = line.toCharArray();
		List<String> tokens = new ArrayList<String>();
		
		StringBuilder stringBuilder = new StringBuilder();
		for (char c : characters) {
			if (c == DELMITER) {
				//Tab space character seen. 
				//Add previous contents to list. 
				if (stringBuilder.capacity() >= 0) {
					tokens.add(stringBuilder.toString());
					//Cleanse it
					stringBuilder.delete(0, stringBuilder.capacity());
				}
				tokens.add("" + c);
			} else {
				stringBuilder.append(c);
			}
		}
		//If there is stringbuilder not empty yet that needs to be added as well
		tokens.add(stringBuilder.toString());*/
		//return tokens.toArray(new String[tokens.size()]);
	}
	//StreamTokenizer does not seem to work
//	private void populateTokens(final StreamTokenizer sToken) throws IOException {
//		while (sToken.nextToken() != StreamTokenizer.TT_EOF) {
//			String temp;
//			switch (sToken.ttype) {
//			case StreamTokenizer.TT_WORD:
//				temp = sToken.sval;
//				fireWordEvent(temp);
//				break;
//			case StreamTokenizer.TT_NUMBER:
//				double number = sToken.nval;
//				fireWordEvent(number);
//				break;
//			case StreamTokenizer.TT_EOL:
//				//Reset column counter for new line
//				columnCounter = 0;
//		 		//Increment row counter
//		 		rowCounter = sToken.lineno();
//				break;
//			default: 
//				//single character in ttype
//		        temp = String.valueOf((char)sToken.ttype);
//			 	if (temp.charAt(0) == '\t') {
//			 		columnCounter++;
//			 	} 
//			}
//		}
//	}
	
	
	/**
	 * Create a {@link CSVWordEvent} and notify all {@link CSVListener}
	 * @param value
	 */
	private void fireWordEvent(String value) {
		CSVWordEvent wordEvent = new CSVWordEvent(value, rowCounter, columnCounter);
		for (CSVListener listener : listeners) {
			listener.wordEvent(wordEvent);
		}
	}
	
	/**
	 * 
	 * @param listener
	 */
	public void addListener(CSVListener listener) {
		listeners.add(listener);
	}
	
//	public static void main(String[] r) throws Exception {
//		CSVExcelConverter excelConverter = new CSVExcelConverter("C:/temp/ConvertedExcel.xls", "Test");
//		excelConverter.initializeWorkbook();
//		String csvFilePath = "E:/tibco/be/3.0/DecisionManager/examples/CreditCardApplication/ExcelFiles/Application_VirtualRuleFunction_Tabbed.txt";
//		FileInputStream fis = new FileInputStream(csvFilePath);
//		CSVParser parser = new CSVParser();
//		parser.addListener(excelConverter);
//		parser.parse(fis);
//		excelConverter.saveWorkbook();
//	}
}
