package com.tibco.rta.impl;

import com.tibco.rta.Key;

import java.util.UUID;

public class FactKeyImpl implements Key {

	private static final long serialVersionUID = 1L;

	private String uid;
	private String schemaName;

    public FactKeyImpl() {}

    /**
     *
     * @param schemaName
     */
	public FactKeyImpl(String schemaName) {
		this.schemaName = schemaName;
		this.uid = UUID.randomUUID().toString();
	}

    /**
     *
     * @param schemaName
     * @param uid
     */
	public FactKeyImpl(String schemaName, String uid) {
		this.schemaName = schemaName;
        if (uid == null || uid.isEmpty()) {
            uid = UUID.randomUUID().toString();
        }
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public String getSchemaName() {
		return schemaName;
	}

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


	@Override
	public boolean equals (Object keyObj) {
		if (! (keyObj instanceof FactKeyImpl)) {
			return false;
		}
		FactKeyImpl factImplKey = (FactKeyImpl) keyObj;
		return strEql(uid, factImplKey.uid) &&
			   strEql(schemaName, factImplKey.schemaName);
	}

	@Override
	public int hashCode() {
		int hashCode = uid.hashCode();
		if (schemaName != null) {
			hashCode += schemaName.hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return uid;
	}

	boolean strEql (String str1, String str2) {
		if (str1 == str2) {
			return true;
		} else if (str1 != null && str1.equals(str2)) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(Object other) {
		return toString().compareTo(other.toString());
	}
}