package com.tibco.cep.studio.cli.studiotools;

import java.util.Map;

import com.tibco.security.ObfuscationEngine;

public class EncryptCLI extends CryptoCLI {

	private final static String OPERATION_ENCRYPT = "encrypt";
	private final static String FLAG_INPUT_TEXT_TO_ENCRYPT = "-i";
	private final static String FLAG_ENCRYPT_HELP = "-h"; 	
	
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0 || 
				(args.length == 1 && args[0].trim().endsWith("help"))) {
			StudioCommandLineInterpreter.printPrologue(new String[]{});
			System.out.println(new BuildEarCLI().getHelp());
			System.exit(-1);
		}
		String newArgs[] = new String[args.length+2];
		newArgs[0] = CryptoCLI.OPERATION_CATEGORY_CRYPTO;
		newArgs[1] = OPERATION_ENCRYPT; 
		System.arraycopy(args, 0, newArgs, 2, args.length);
		StudioCommandLineInterpreter.executeCommandLine(newArgs);
	}
	
	
	@Override
	public String[] getFlags() {
		return new String[]{FLAG_ENCRYPT_HELP,
				FLAG_INPUT_TEXT_TO_ENCRYPT};
	}

	@Override
	public String getOperationFlag() {
		// TODO Auto-generated method stub
		return OPERATION_ENCRYPT;
	}

	@Override
	public String getOperationName() {
		return "Encrypt";
	}

	@Override
	public String getUsageFlags() {
		return "[" + FLAG_ENCRYPT_HELP + "] " +
			   "[" + FLAG_INPUT_TEXT_TO_ENCRYPT + " <input text>] ";
	}

	@Override
	public String getHelp() {
		String helpMsg = "Usage: encrypt " + getUsageFlags() + "\n" +
				"where, \n" +
				"	-h prints this usage \n" +
				"	-i input text \n";
        return helpMsg;
	}

	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return false;
		
		if(argsMap.containsKey(FLAG_ENCRYPT_HELP) || argsMap.containsKey(FLAG_INPUT_TEXT_TO_ENCRYPT)){
		
			if (argsMap.containsKey(FLAG_ENCRYPT_HELP))
				return false;
			
			if(argsMap.containsKey(FLAG_INPUT_TEXT_TO_ENCRYPT)){
				String plaintext = argsMap.get(FLAG_INPUT_TEXT_TO_ENCRYPT);
				if(plaintext==null || plaintext.trim().equals("") ){
					return false;
				}
				
				System.out.println(ObfuscationEngine.encrypt(plaintext.toCharArray()));
			}
			
		}
		else{
			return false;
		}
		
		
		return true;
	}

}
