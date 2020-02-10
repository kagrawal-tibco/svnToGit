package com.tibco.cep.dptest.examples;

import com.tibco.cep.decision.table.utils.DecisionTableUtility;
import com.tibco.cep.decision.util.BUIConfig;

/*
 * this class deploys a decision table
 */

public class TestDeployTable {

	public static void main(String[] args) {
		
		try {
			if (args == null || args.length < 3){
				System.out.println("Usage :\n");
				System.out.println("Arguments :   -earPath    -decisionProjectPath  -decisionTablePath(optional) ");
				return;
			} 
			
			String earPath = args[0];
			String dpPath = args[1];
			String dtPath = args[2];
			
//			 While deploying decision table, some TRA properties are accessed so before calling this method set system property as shown,
//			 System.setProperty(BUIConfig.BUI_CONFIG_PARAMETER,TRA file absolute path);
			
			System.setProperty(BUIConfig.BUI_CONFIG_PARAMETER,"C:/tibco/be/3.0/DecisionManager/configuration/bui-config.tra");
			
			/** Consider CreditCardApplication example; E:\CC-BUI --> location where CreditCard example is checked out
			 * 
			 * @param earFilePath  --> E:\CC-BUI\CC-BUI.ear
			 * @param dpPath --> E:\CC-BUI\CC-BUI.dp
			 * @param dtPath --> If no DTPath is specified, then all the DTs under this DP will be deployed --> E:\CC-BUI\Virtual_RF\sample.rulefunctionimpl
			 * @throws Exception
			 */
			
//			DecisionTableDeployer.deployDecisionTable(earPath, dpPath, dtPath);
			DecisionTableUtility.deployDecisionTableToTester(earPath, dpPath, dtPath);
			
		} catch (Exception e) {
			System.out.println("Exception Message >>"+e.getMessage());
			e.printStackTrace();
		} 
	}
	
}
