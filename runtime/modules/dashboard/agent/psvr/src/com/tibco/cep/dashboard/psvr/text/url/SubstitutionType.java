package com.tibco.cep.dashboard.psvr.text.url;

public enum SubstitutionType {
	
	/**
	 * Not used as of now 
	 */
	CONSTANT(new ConstantSubstitutor()),
	
	/**
	 * Java environment variables. Access to System.getProperty(...)
	 */
	ENV(new EnvironmentVariableSubstitutor()),
	
	/**
	 * Properties variables. Access to run-time properties
	 */
	PROP(new PropertyKeySubstitutor()),
	
	/**
	 * Date Time variables. Access to various date/time values
	 */
	SYS(new DateTimeSubstitutor()),
	
	/**
	 * User variables. Access to user-name, preferred principal
	 */
	USR(new UserInformationSubstitutor()),
	
	/**
	 * Tuple variables. Access to fields in the tuple
	 */
	VAR(new TupleValueSubstitutor()),

	/**
	 * Global variables. Access to global variables
	 */
	GVAR(new GlobalVariableSubstitutor());
	
	private Substitutor substitutor;

	private SubstitutionType(Substitutor substitutor){
		this.substitutor = substitutor;
	}
	
	public final Substitutor getSubstitutor(){
		return substitutor;
	}
}
