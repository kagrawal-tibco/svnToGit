package com.tibco.cep.dptest.examples;

import com.tibco.cep.decision.table.utils.DecisionTableUtility;

/*
 * This class imports an excel file and creates a decision table out of it
 * It does not check ACL if user has permission to add implementation to specified VRF
 */

public class TestImport {
	
	public static void main(String[] args) {
		
		try {			
			if (args == null || args.length < 4){
			System.out.println("Usage :\n");
			System.out.println("Arguments :   -decisionProjectPath    -excelPath \n"
						+ " - decisionTableName  -VRFPath ");
				return;
			} 
			
			String dpPath = args[0];
			String excelPath = args[1];
			String dtName = args[2];
			String vrfPath = args[3];
			
			
			
			/** Consider CreditCardApplication example; E:\CC-BUI --> location where CreditCard example is checked out
			 * 
			 * @param dpLocation --> Decision Project Location (absolute path of .dp file) --> E:\CC-BUI\CC-BUI.dp
			 * @param excelLocation --> Excel Location (absolute path of .xls) --> C:\tibco\be\3.0\DecisionManager\examples\CreditCardApplication\ExcelFiles\Application_VirtualRuleFunction.xls
			 * @param dtName --> Decision Table name after import --> sample
			 * @param vrfPath --> Virtual Rule Function that is implemented by Decision Table --> \Virtual_RF\Application_VirtualRuleFunction
			 * @param option(optional parameter)--> if Decision Table with the specified name already exists
			 * then "o" is for over write the existing one or "m" auto merge with the existing. If no option is supplied
			 * then merge will take precedence.
			 * @throws Exception
			 */
	
			//Simply will overwrite the existing file
			DecisionTableUtility.importExcel(dpPath, excelPath, dtName, vrfPath, "o");
			
			System.out.println("Excel File is imported successfully >>>>");
			
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Import failed >>>"+e.getMessage());
		} 
	}
	
}
