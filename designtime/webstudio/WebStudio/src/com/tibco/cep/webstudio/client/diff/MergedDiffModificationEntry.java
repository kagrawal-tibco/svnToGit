package com.tibco.cep.webstudio.client.diff;

/**
 * Class to represent a difference while showing DIFF between 3 versions (Base,
 * User, Server) of an artifact.
 * 
 * @author moshaikh
 * 
 */
public class MergedDiffModificationEntry extends ModificationEntry {

	/**
	 * Message explaining the nature of diff (eg: 'Modified locally and on Server', 'Added locally'..)
	 */
	private String message;
	private String localVersion;
	private String baseVersion;
	
	private Object localVersionObj;
	private Object baseVersionObj;
	
	private ModificationType localChangeType;
	private ModificationType serverChangeType;

	/**
	 * 
	 * @param modificationType The change type to be depicted to user. (The CSS color depends on this.)
	 * @param localChangeType The type of change done on local.
	 * @param serverChangeType The type of change done on server side.
	 */
	public MergedDiffModificationEntry(ModificationType modificationType, ModificationType localChangeType, ModificationType serverChangeType) {
		super(modificationType);
		this.localChangeType = localChangeType;
		this.serverChangeType = serverChangeType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLocalVersion() {
		return localVersion;
	}

	public void setLocalVersion(String localVersion) {
		this.localVersion = localVersion;
	}

	public String getBaseVersion() {
		return baseVersion;
	}

	public void setBaseVersion(String baseVersion) {
		this.baseVersion = baseVersion;
	}

	public Object getLocalVersionObj() {
		return localVersionObj;
	}

	public void setLocalVersionObj(Object localVersionObj) {
		this.localVersionObj = localVersionObj;
	}

	public Object getBaseVersionObj() {
		return baseVersionObj;
	}

	public void setBaseVersionObj(Object baseVersionObj) {
		this.baseVersionObj = baseVersionObj;
	}

	public boolean isLocalChange() {
		return localChangeType != null || localChangeType == ModificationType.UNCHANGED;
	}

	public boolean isServerChange() {
		return serverChangeType != null || serverChangeType == ModificationType.UNCHANGED;
	}

	public ModificationType getLocalChangeType() {
		return localChangeType;
	}

	public void setLocalChangeType(ModificationType localChangeType) {
		this.localChangeType = localChangeType;
	}

	public ModificationType getServerChangeType() {
		return serverChangeType;
	}

	public void setServerChangeType(ModificationType serverChangeType) {
		this.serverChangeType = serverChangeType;
	}
}
