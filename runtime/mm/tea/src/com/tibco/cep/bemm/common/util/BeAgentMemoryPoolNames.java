package com.tibco.cep.bemm.common.util;

/**
 * Enum defines the memory pools and their canonical names for Self monitoring
 * 
 * @author vasharma
 *
 */
public enum BeAgentMemoryPoolNames {
	HEAP_MEMORY_USAGE("Heap Memory Usage", "java.lang:type=Memory"), NON_HEAP_MEMORY_USAGE("Non-Heap Memory Usage",
			"java.lang:type=Memory"), CODE_CAHE("Code Cache",
			"java.lang:name=Code Cache,type=MemoryPool");

	/**
	 * Constructor of ENUM
	 * 
	 * @param name
	 * @param cannonicalName
	 */
	private BeAgentMemoryPoolNames(String name, String cannonicalName) {
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
