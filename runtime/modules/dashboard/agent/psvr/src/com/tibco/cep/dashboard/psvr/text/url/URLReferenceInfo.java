package com.tibco.cep.dashboard.psvr.text.url;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.tibco.cep.dashboard.common.Once;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @deprecated
 * @author anpatil
 *
 */
public class URLReferenceInfo implements Serializable {

	private static final long serialVersionUID = 5112389156623827305L;

	private static final String PATTERN0 = "(\\$\\{([a-zA-Z]+):([^}]+)\\})";

//	private static final String PATTERN1 = "^([\\p{ASCII}&&[^\\$\\{]]+)(\\$\\{[a-zA-Z_0-9:]+\\})";

	private String mBaseUrl = null;
	private HashMap<String, SubstitutionInfo> mParameters = new LinkedHashMap<String, SubstitutionInfo>();
	private SubstitutionContext mSubstitutionContext = null;

	private Logger logger;

	private static final String NO_NAME_ARG = "NO_NAME_ARG";

	private URLReferenceInfo(Logger logger) {
		this.logger = logger;
	}

	private URLReferenceInfo(Logger logger,String baseUrl) {
		this(logger);
		mBaseUrl = baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.mBaseUrl = baseUrl;
	}

	public void setSubstitutionContext(SubstitutionContext substitutionContext) {
		this.mSubstitutionContext = substitutionContext;
	}

	private void addParameter(String paramName, SubstitutionInfo subsInfo) {
		mParameters.put(paramName, subsInfo);
	}

	public Collection<String> getParameters() {
		return this.mParameters.keySet();
	}

	/**
	 * This method is to be used during modeling/design time to extract
	 * metadata.
	 */
	public SubstitutionInfo getParameterSubstitutionInfo(String paramName) {
		return this.mParameters.get(paramName);
	}

	/**
	 * This method is to be used during runtime to extract dynamic metadata.
	 */
	private String getParameterSubstitutionInstanceValue(String paramName) {
		if (mSubstitutionContext == null) {
			if (Once.firstTime(paramName) == true) {
				logger.log(Level.WARN, "Context is not set, can not perform substitution for " + paramName);
			}
			return this.mParameters.get(paramName).getKey();
		} else {
			return SubstitutionUtil.resolve(mSubstitutionContext, this.mParameters.get(paramName));
		}
	}

	/**
	 * Parses a string as shown below to create URLReferenceInfo"http://localhost:6161/drilldown?QueryUser=${usr:NAME}&QueryName=OverstockByLocation&Date=${var:TxnDate} ..."
	 */
	public static URLReferenceInfo fromString(Logger logger,String urlString) {
		throw new UnsupportedOperationException();
//		logger.log(Level.DEBUG, "Calling URLReferenceInfo fromString() :" + urlString);
//		if (urlString == null) {
//			return null;
//		}
//
//		Pattern urlPattern = Pattern.compile(PATTERN0, Pattern.CASE_INSENSITIVE);
//		Matcher urlMatcher = urlPattern.matcher(urlString);
//
//		StringBuffer urlBase = new StringBuffer();
//		URLReferenceInfo result = new URLReferenceInfo(logger,urlBase.toString());
//
//		int paramCtr = 0;
//		int startIdx = 0;
//		while (urlMatcher.find()) {
//			urlBase.append(urlString.substring(startIdx, urlMatcher.start()));
//			for (int i = 0; i <= urlMatcher.groupCount(); i++) {
//				switch (i) {
//				case 0: // This is match=${subsRefType:subsRefKey}
//					break;
//				case 1: // This is subsRef=${subsRefType:subsRefKey}
//					urlBase.append("{" + paramCtr + "}");
//					String noNamePrm = URLReferenceInfo.NO_NAME_ARG + (paramCtr++);
//					result.addParameter(noNamePrm, new SubstitutionInfo(logger,urlMatcher.group(i)));
//					break;
//				case 2: // This is subsRefType (could be used directly)
//				case 3: // This is subsRefKey (could be used directly)
//					break;
//				default:
//					logger.log(Level.ERROR, "Unexpected match! " + urlMatcher.group(i));
//					break;
//				}
//			}
//			startIdx = urlMatcher.end();
//		}
//		urlBase.append(urlString.substring(startIdx, urlString.length()));
//
//		logger.log(Level.DEBUG, "Base URLReference is :" + urlBase);
//		result.setBaseUrl(urlBase.toString());
//
//		return result;
	}

	/*
	 * Alternative way of doing similar thing.
	 */
//	public static URLReferenceInfo fromString1(Logger logger,String urlString) {
//		logger.log(Level.DEBUG, "Calling URLReferenceInfo fromString1() :" + urlString);
//		if (urlString == null) {
//			return null;
//		}
//
//		Pattern urlPattern = Pattern.compile(PATTERN1, Pattern.CASE_INSENSITIVE);
//		Matcher urlMatcher = urlPattern.matcher(urlString);
//
//		StringBuffer urlBase = new StringBuffer();
//		URLReferenceInfo result = new URLReferenceInfo(urlBase.toString());
//
//		int paramCtr = 0;
//		while (urlMatcher.find()) {
//			for (int i = 1; i <= urlMatcher.groupCount(); i++) {
//				switch (i) {
//				case 0:
//					break;
//				case 1:
//					urlBase.append(urlMatcher.group(i));
//					break;
//				case 2:
//					urlBase.append("{" + paramCtr + "}");
//					String noNamePrm = URLReferenceInfo.NO_NAME_ARG + (paramCtr++);
//					result.addParameter(noNamePrm, new SubstitutionInfo(urlMatcher.group(i)));
//					break;
//				default:
//					logger.log(Level.ERROR, "Unexpected match! " + urlMatcher.group(i));
//					break;
//				}
//			}
//
//			// Re-process remaining...
//			urlString = urlString.substring(urlMatcher.group(0).length());
//			urlMatcher = urlPattern.matcher(urlString);
//		}
//		urlBase.append(urlString);
//
//		logger.log(Level.DEBUG, "Base URLReference is :" + urlBase);
//		result.setBaseUrl(urlBase.toString());
//
//		return result;
//	}

	public String toReferenceString() {
		return toString(false, false);
	}

	public String toInstanceString(boolean encode) {
		return toString(true, encode);
	}

	public String toString() {
		return toString(false, false);
	}

	private String toString(boolean substituteInstance, boolean encode) {
		String result = this.mBaseUrl;

		if (substituteInstance) {
			int idx = 0;
			Object[] paramVals = new String[this.mParameters.size()];
			for (String paramName : getParameters()) {
				String paramValue = getParameterSubstitutionInstanceValue(paramName);
				if (encode == true) {
					try {
						paramVals[idx] = URLEncoder.encode(paramValue, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						paramVals[idx] = paramValue;
					}
				}
				else {
					paramVals[idx] = paramValue;
				}
				idx++;
			}
			result = MessageFormat.format(this.mBaseUrl, paramVals);
		}

		logger.log(Level.DEBUG, "URLReferenceInfo instance string :" + result);
		return result;
	}
}
