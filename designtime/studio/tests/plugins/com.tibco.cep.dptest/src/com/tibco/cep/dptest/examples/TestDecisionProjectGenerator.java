package com.tibco.cep.dptest.examples;

import com.tibco.cep.decision.table.utils.DecisionProjectUtility;

/*
 * this class generates a decision project 
 */

public class TestDecisionProjectGenerator {
	
public static void main(String[] args) throws Exception{
		
		try {
			String earPath = null;
			String aclPath = null;
			String domainModelPath = null;
			String dpTargetPath= null;
			String dpName = null;
			if (args != null && args.length >= 3) {
				earPath = args[0];
				aclPath = args[1];
				dpTargetPath = args[2];							
				if (args.length >= 4) {
					dpName = args[3];
				}
				if (args.length >= 5){
					domainModelPath = args[4];
					
				}

			} else {
				System.out.println("Usage :\n");
				System.out.println("Arguments :   -earPath    -aclPath  -decisionProjectTargetPath  \n"
								+ " -decisionProjectName(optional)   -domainModelPath(optional)");
				return;
			}
			System.out.println("Generation of Decision Project Started >>>>");
			long startTime = System.currentTimeMillis();
			
			/**
			 * Consider CreditCardApplication example; E:\CC-BUI --> location where CreditCard example is checked out
			 * 
			 * @param earPath --> EAR file absolute path --> E:\CC-BUI\CC-BUI.ear
			 * @param aclPath --> ACL file path  --> C:\tibco\be\3.0\rms\examples\CreditCardApplication\config\CreditCardApplication.ac
			 * @param dpTargetLocation --> Target Location for generated DP --> E:\CC-BUI\New-DP
			 * @param dpName (optional)--> if not provided then it will be taken from EAR name --> new 
			 * @param domainModelPath (optional)--> Domain Model file absolute path --> E:\CC-BUI\CC-BUI.dm
			 * @throws Exception
			 */
			
			DecisionProjectUtility.createDecisionProject(earPath, aclPath, dpTargetPath, dpName, domainModelPath);
//			DecisionProjectGeneratorUtility.generateDecisionProject(earPath, aclPath, dpTargetPath, dpName, domainModelPath);

			long endTime = System.currentTimeMillis();
			System.out.println("\nDecision Project Generated >>Total Time Taken is :"
							+ (endTime - startTime) + "ms");
			System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Decision Project Generation failed>>"+e.getMessage());
		}
	}
}
