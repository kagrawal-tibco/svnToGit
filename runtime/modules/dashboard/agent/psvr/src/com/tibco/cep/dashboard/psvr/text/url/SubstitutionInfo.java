package com.tibco.cep.dashboard.psvr.text.url;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Data object to carry details of a reference substitution variable
 */
public class SubstitutionInfo {

	private Logger logger;
	private SubstitutionType mType = null;
	private String mKey = null;
	private String mPrepender = null;
	private String mAppender = null;

	public SubstitutionInfo(Logger logger) {
		this.logger = logger;
		this.setType(SubstitutionType.CONSTANT);
		this.setKey("");
		this.setPrepender("");
		this.setAppender("");
	}

	public SubstitutionInfo(Logger logger, String referencestring) {
		this(logger);
		this.logger.log(Level.DEBUG, "Creating SubstitutionInfo :" + referencestring);

		if (isReference(logger, referencestring) == false) {
			this.setKey(referencestring);
		} else {
			int openIndex = referencestring.indexOf(SubstitutionContext.REF_OPEN);
			int closeIndex = referencestring.indexOf(SubstitutionContext.REF_CLOSE, openIndex);
			String preValueString = referencestring.substring(0, openIndex);
			String typeKeyString = referencestring.substring(openIndex + 2, closeIndex);
			String postValueString = referencestring.substring(closeIndex + 1);
			String[] typeAndKey = typeKeyString.split(SubstitutionContext.REF_DIVIDE, 2);

			this.logger.log(Level.DEBUG, "Substitution required :" + typeAndKey[0] + " with " + typeAndKey[1]);

			SubstitutionType subsRefType = SubstitutionType.valueOf(typeAndKey[0].toUpperCase());
			String subsRefKey = typeAndKey[1];
			this.setType(subsRefType);
			this.setKey(subsRefKey);
			this.setPrepender(preValueString);
			this.setAppender(postValueString);
		}
	}

	public SubstitutionInfo(Logger logger, SubstitutionType type, String key, String prepender, String appender) {
		this(logger);
		if (type != null) {
			this.setType(type);
		}
		if (key != null) {
			this.setKey(key);
		}
		if (prepender != null) {
			this.setPrepender(prepender);
		}
		if (appender != null) {
			this.setAppender(appender);
		}
	}

	public void setType(SubstitutionType type) {
		mType = type;
	}

	public SubstitutionType getType() {
		return mType;
	}

	public void setKey(String key) {
		mKey = key;
	}

	public String getKey() {
		return mKey;
	}

	public void setPrepender(String prepender) {
		mPrepender = prepender;
	}

	public String getPrepender() {
		return mPrepender;
	}

	public void setAppender(String appender) {
		mAppender = appender;
	}

	public String getAppender() {
		return mAppender;
	}

	/**
	 * Create a reference string in the form of '${ ... : ......}' unsing
	 * enumeration type and key (assumed to be from that type)
	 * 
	 * @param SubstitutionType
	 * @param String
	 * @return String
	 */
	public String toString() {
		if (mKey == null) {
			return "";
		}
		if (mType == null) {
			return mKey;
		}
		if (mType == SubstitutionType.CONSTANT) {
			return mKey;
		}

		StringBuffer extxrefbuffer = new StringBuffer();
		extxrefbuffer.append(mPrepender);
		extxrefbuffer.append(SubstitutionContext.REF_OPEN);
		extxrefbuffer.append(mType.toString());
		extxrefbuffer.append(SubstitutionContext.REF_DIVIDE);
		extxrefbuffer.append(mKey);
		extxrefbuffer.append(SubstitutionContext.REF_CLOSE);
		extxrefbuffer.append(mAppender);

		return extxrefbuffer.toString();
	}

	public String toDebugString() {
		return "[" + mType + " >> " + mKey + "]";
	}

	/**
	 * Return true if the string looks like a reference string that requires
	 * substitution section.
	 * 
	 * @param string
	 *            : The <code>String</code> to be tested
	 * @return true if <code>string</code> is a reference string.
	 */
	public static boolean isReference(Logger logger, String referencestring) {
		if (referencestring == null) {
			return false;
		}
		if (referencestring.length() == 0) {
			return false;
		}

		int openIndex = referencestring.indexOf(SubstitutionContext.REF_OPEN);
		int divideIndex = referencestring.indexOf(SubstitutionContext.REF_DIVIDE, openIndex);
		int closeIndex = referencestring.indexOf(SubstitutionContext.REF_CLOSE, divideIndex);
		// Required to have the string be in the form of '${ ... : ......}'
		// Multiple references in a single key is not supported
		logger.log(Level.DEBUG, "Indexes are " + openIndex + "," + divideIndex + "," + closeIndex);
		return (0 <= openIndex) && (openIndex < divideIndex) && (divideIndex < closeIndex);
	}
}
