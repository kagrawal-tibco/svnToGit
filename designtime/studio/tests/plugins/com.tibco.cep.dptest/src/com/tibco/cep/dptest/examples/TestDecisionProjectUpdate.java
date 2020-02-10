package com.tibco.cep.dptest.examples;

import com.tibco.cep.decision.table.utils.DecisionProjectUtility;

/*
 * this class updates a decision project without using RMS
 */

public class TestDecisionProjectUpdate {

	public static void main(String[] args) {
		
		try {
			String earPath = null;		
			String dpTargetPath = null;		
			if (args != null && args.length >= 2) {
				earPath = args[0];			
				dpTargetPath = args[1];
			} else {
				System.out.println("Usage :\n");
				System.out.println("Arguments :   -earPath  -oldDecisionProjectPath  ");
				return;
			}
			long startTime = System.currentTimeMillis();
			
			/**
			 * Consider CreditCardApplication example; E:\CC-BUI --> location where CreditCard example is checked out
			 * already checked out project is updated with the updated EAR
			 * 
			 * @param earFilePath --> EAR file absolute Path with which DP should be updated(.ear) --> E:\CC-BUI\CC-BUI.ear
			 * @param oldDecisionProjectLocation --> Decision project that needs to be updated(.dp file) --> E:\CC-BUI\CC-BUI.dp
			 * @throws Exception
			 */
			
			DecisionProjectUtility.updateDecisionProject(earPath, dpTargetPath);
//			UpdateDecisionProjectUtility.updateDecisionProject(earPath, dpTargetPath);
			
			long endTime = System.currentTimeMillis();
			System.out.println("\nDecision Project Updated >>Total Time Taken is :"
							+ (endTime - startTime) + "ms");
			System.exit(0);
		} catch (Exception e) {			
			e.printStackTrace();
			System.out.println("Decision Project update failed >>");
		} 
	}
	
}
