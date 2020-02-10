package com.tibco.cep.studio.debug.core.launch;

import org.eclipse.core.runtime.CoreException;

public interface RunProfile {
	
	public static enum RUNTIME_ENV_TYPE {
		ENV_APPLICATION(1,"com.tibco.cep.container.standalone.BEMain","BE Application"),
		ENV_RULE(2,"com.tibco.be.ui.tools.debugger.impl.RuleDebugMain","BE Rule");
		
		public int type;
		private String entryPoint;
		private String name;
		private RUNTIME_ENV_TYPE(int type,String entryPoint,String name) { 
			this.type = type;
			this.entryPoint = entryPoint;
			this.name = name;
		}
		public String getEntryPoint() {
			return entryPoint;
		}
		public int getType() {
			return type;
		}
		public String getName() {
			return name;
		}		
	}
	
	public static final String PROP_BE_HOME ="BE_HOME";
	public static final String TRA_FILE_NAME = "be-engine.tra";
    public static final String DEFAULT_DEBUG_PORT = "35192";
	public static final String DEFAULT_DEBUG_HOST = "localhost";
    
   
    /**
     * 
     * @throws Exception
     */
    public void init() throws CoreException;

	/**
	 * 
	 * @return
	 */
	public String getEntryPoint();

	/**
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 
	 * @return
	 */
	public RUNTIME_ENV_TYPE getType();

	/**
	 * 
	 * @return
	 */
	public String getWorkingDir();
	/**
	 * 
	 * @return
	 */
	String getVmArgs();
	
	/**
	 * 
	 * @return
	 */
	String getCmdStartUpOpts();
	/**
	 * 
	 * @return
	 */
    String getBEHome();

    /**
     * 
     * @return
     */
    String getEngineTra();
    /**
     * 
     * @return
     */
	public String getCddFilePath();
	/**
	 * 
	 * @return
	 */
	String getEarFilePath();
	
	/**
	 * 
	 * @return
	 */
	public String getUnitName();
	

}
