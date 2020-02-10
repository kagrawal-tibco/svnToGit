package com.tibco.cep.dptest.examples;

import com.tibco.cep.decision.table.utils.DecisionTableUtility;

/*
 * this class exports a decision table to excel file format 
 */

public class TestExport {
	
	public static void main(String[] args){
		
		try {
			if (args == null || args.length < 2){
				System.out.println("Usage :\n");
				System.out.println("Arguments :   -decisionProjectPath(optional)  -targetExcelPath  -decisionTablePath ");
				return;	
		} 
			
		String dpPath = args[0];
		String excelPath = args[1];
		String dtPath = null;
		if(args.length >= 3){
			dtPath = args[2];
		}
			
		long startTime = System.currentTimeMillis();

		/**
		 * Consider CreditCardApplication example; E:\CC-BUI --> location where CreditCard example is checked out
		 * 
		 * @param dpLocation(optional) --> Decision Project Location (.dp file) --> E:\CC-BUI\CC-BUI.dp
		 * @param targetExcelLocation --> excel file location to which DT is exported(.xls) --> E:\CC-BUI\Excel\exported-sample.xls
		 * @param dtPath --> Decision Table Path (.rulefunctionimpl) --> E:\CC-BUI\Virtual_RF\sample.rulefunctionimpl
		 * if not provided then Column order will be taken from Table itself instead of BUI setting persisted in the DP area
		 * @throws Exception
		 */
		
		DecisionTableUtility.exportExcel(dpPath, excelPath, dtPath);
					
		long endTime = System.currentTimeMillis();
		System.out.println("DT Export Successful >>Total time taken >>"+(endTime-startTime));
		
		} catch (Exception e) {
			System.out.println("DT Export Failed >>"+e.getMessage());
			e.printStackTrace();
		} 
	}
}
