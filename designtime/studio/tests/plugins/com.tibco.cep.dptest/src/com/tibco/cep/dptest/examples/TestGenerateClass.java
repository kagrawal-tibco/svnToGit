package com.tibco.cep.dptest.examples;

import com.tibco.cep.decision.table.utils.DecisionTableUtility;
import com.tibco.cep.decision.util.BUIConfig;

/*
 * this class generates class files for a given decision table 
 */

public class TestGenerateClass {

	public static void main(String[] args) {	
		
		try {
			if (args == null || args.length < 3){
				System.out.println("Usage :\n");
				System.out.println("Arguments :   -earPath    -decisionProjectPath  -decisionTablePath(optional) -classGenerationPath  \n"
								+ " -domainModelPath(optional) ");
				return;
			
			} 
			String earPath = args[0];
			String dpPath = args[1];
			String domainModelPath = null;
			String dtPath = args[2];
			String classGenPath = null;
			if (args.length >= 4){
				classGenPath = args[3];
			}
			if (args.length >=5){
				domainModelPath = args[4];
			}
			
			
//			 While generating classes some TRA properties are accessed so before calling this method set system property as shown,
//			 System.setProperty(BUIConfig.BUI_CONFIG_PARAMETER,TRA file absolute path);
			 
			System.setProperty(BUIConfig.BUI_CONFIG_PARAMETER,"C:/tibco/be/3.0/DecisionManager/configuration/bui-config.tra");
			
			/** Consider CreditCardApplication example; E:\CC-BUI --> location where CreditCard example is checked out
			 * 
			 * @param earPath --> E:\CC-BUI\CC-BUI.ear
			 * @param dpPath --> E:\CC-BUI\CC-BUI.dp
			 * @param dtPath --> E:\CC-BUI\Virtual_RF\sample.rulefunctionimpl
			 * @param classGenPath --> E:\CC-BUI\Class (it creates the "Class" folder, if it does not exist at the specified location)
			 * @param domainModelPath --> E:\CC-BUI\CC-BUI.dm
			 * @throws Exception
			 */
		
			DecisionTableUtility.compileDecisionTable(earPath, dpPath ,dtPath, classGenPath, domainModelPath);
//			DTClassGeneratorUtility.generateDTClass(earPath, dpPath ,dtPath, classGenPath, domainModelPath);
			
			System.out.println("Class generated Successfully>>");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DT Class Generation failed >>"+e.getMessage());
		} 
	}
}
