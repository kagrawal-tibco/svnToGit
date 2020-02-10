package com.tibco.cep.bemm.common.util;

/**
 * Enum defines the memory pools and their cannonical names
 * 
 * @author dijadhav
 *
 */
public enum MemoryPoolNames {
	HEAP_MEMORY_USAGE("Heap Memory Usage", "java.lang:type=Memory"), NON_HEAP_MEMORY_USAGE("Non-Heap Memory Usage",
			"java.lang:type=Memory"), CMS_OLD_GEN("CMS Old Gen", "java.lang:name=CMS Old Gen,type=MemoryPool"), PAR_EDEN_SPACE(
			"Par Eden Space", "java.lang:name=Par Eden Space,type=MemoryPool"), CODE_CAHE("Code Cache",
			"java.lang:name=Code Cache,type=MemoryPool"), PAR_SERVIVOR_SPACE("Par Survivor Space",
			"java.lang:name=Par Survivor Space,type=MemoryPool");

	/**
	 * Constructor of ENUM
	 * 
	 * @param name
	 * @param cannonicalName
	 */
	private MemoryPoolNames(String name, String cannonicalName) {
		this.name = name;
		this.cannonicalName = cannonicalName;
	}

	private String name;

	private String cannonicalName;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cannonicalName
	 */
	public String getCannonicalName() {
		return cannonicalName;
	}

	/**
	 * @param cannonicalName
	 *            the cannonicalName to set
	 */
	public void setCannonicalName(String cannonicalName) {
		this.cannonicalName = cannonicalName;
	}

}
