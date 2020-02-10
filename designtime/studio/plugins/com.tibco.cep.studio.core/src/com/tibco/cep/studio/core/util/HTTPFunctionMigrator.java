/**
 * 
 */
package com.tibco.cep.studio.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.cep.studio.core.index.utils.IndexUtils;

/**
 * Migrating old HTTP Function to new function set.
 * 
 * @author vpatil
 */
public class HTTPFunctionMigrator extends FunctionMigrator {
	public static String[][] fHTTPFunctionMappings;
	
	private int connectionInfoInstance_Counter;
	
	private static final String CONNECTION_INFO_DEFINITION_STMT = "HTTP.ConnectionInfo.createHTTPConnectionInfo(";
	private static final String CONNECTION_INFO_INSTANTIATION_STMT = "Object connectionInfo_ = " + CONNECTION_INFO_DEFINITION_STMT;
	private static final String CONNECTION_INFO_PROXY_FUNCTION_STMT = "HTTP.ConnectionInfo.setProxy(";
	private static final String CONNECTION_INFO_SECURE_FUNCTION_STMT = "HTTP.ConnectionInfo.setSecureInfo(";
	private static final String CONNECTION_INFO_DISABLE_COOKIE_FUNCTION_STMT = "HTTP.ConnectionInfo.disableCookie(";
	private static final String CONNECTION_INFO_DISABLE_EXPECT_CONTINUE_FUNCTION_STMT = "HTTP.ConnectionInfo.disableExpectContinueHeader(";
	private static final String CONNECTION_INFO_SET_METHOD_NAME_FUNCTION_STMT = "HTTP.ConnectionInfo.setHttpMethod(";
	
	private static final String OLD_FUNCTION_IGNORE_COOKIE = "HTTP.setIgnoreCookie(";
	private static final String OLD_FUNCTION_USE_EXPECT_CONTINUE = "HTTP.useExpectContinueHeader(";
	private static final String OLD_FUNCTION_SET_ERROR_CALLBACK_RULEFUNCTION = "HTTP.setErrorCallbackRuleFunction(";
	
	static {
		fHTTPFunctionMappings = new String[12][2];
		fHTTPFunctionMappings[0][0] = "HTTP.sendRequest(";
		fHTTPFunctionMappings[0][1] = "HTTP.sendRequest(";
		fHTTPFunctionMappings[1][0] = "HTTP.sendRequestViaProxy(";
		fHTTPFunctionMappings[1][1] = "HTTP.sendRequest(";
		fHTTPFunctionMappings[2][0] = "HTTP.sendSecureRequest(";
		fHTTPFunctionMappings[2][1] = "HTTP.sendRequest(";
		fHTTPFunctionMappings[3][0] = "HTTP.sendSecureRequestViaProxy(";
		fHTTPFunctionMappings[3][1] = "HTTP.sendRequest(";
		fHTTPFunctionMappings[4][0] = "HTTP.sendSecureRequestByProtocol(";
		fHTTPFunctionMappings[4][1] = "HTTP.sendRequest(";
		fHTTPFunctionMappings[5][0] = "HTTP.sendSecureRequestByProtocolViaProxy(";
		fHTTPFunctionMappings[5][1] = "HTTP.sendRequest(";
		fHTTPFunctionMappings[6][0] = "HTTP.sendAsynchronousRequest("; // last
		fHTTPFunctionMappings[6][1] = "HTTP.sendAsynchronousRequest(";
		fHTTPFunctionMappings[7][0] = "HTTP.sendAsynchronousRequestViaProxy("; // last - 2
		fHTTPFunctionMappings[7][1] = "HTTP.sendAsynchronousRequest(";
		fHTTPFunctionMappings[8][0] = "HTTP.sendSecureAsynchronousRequest("; // last - 5
		fHTTPFunctionMappings[8][1] = "HTTP.sendAsynchronousRequest(";
		fHTTPFunctionMappings[9][0] = "HTTP.sendSecureAsynchronousRequestViaProxy("; // last - 7
		fHTTPFunctionMappings[9][1] = "HTTP.sendAsynchronousRequest(";
		fHTTPFunctionMappings[10][0] = "HTTP.sendSecureAsynchronousRequestByProtocol("; //last - 6
		fHTTPFunctionMappings[10][1] = "HTTP.sendAsynchronousRequest(";
		fHTTPFunctionMappings[11][0] = "HTTP.sendSecureAsynchronousRequestByProtocolViaProxy("; // last - 8
		fHTTPFunctionMappings[11][1] = "HTTP.sendAsynchronousRequest(";
	}
	
	@Override
	protected void createReplaceEdits(StringBuilder sb, File file) {
		StringBuilder replacementFunctionStmts = new StringBuilder();
		
		//initialize the parameters per file
		connectionInfoInstance_Counter = 1;
		String disableExpectContinueParams = null, disableCookieParams = null, errorCallbackRuleFunction = null;
		boolean changed = false;
		List<String> paramList = new ArrayList<String>();
		
		if (needsConnectionInfoMigration(sb)) {
			for (String[] mapping : fHTTPFunctionMappings) {
				String oldFunction = mapping[0];
				String newFunction = mapping[1];

				int endStmtIndex = 0;
				int offset = -1;
				while ((offset = sb.indexOf(oldFunction, endStmtIndex)) != -1) {
					disableExpectContinueParams = disableCookieParams = errorCallbackRuleFunction = null;

					int startStmtIndex = sb.lastIndexOf(";", offset);
					if (startStmtIndex >= offset) {
						throw new RuntimeException("Invalid Index");
					}
					endStmtIndex = sb.indexOf(";", offset);
					
					String functionPrefix = sb.substring(startStmtIndex + 1, offset);
					if (isMisMatchedParenthese(functionPrefix)) {
						endStmtIndex = checkForEndParenthese(sb, startStmtIndex, endStmtIndex);
					} else {
						endStmtIndex -= 1;
					}
					
					String oldFunctionParams = sb.substring(offset + oldFunction.length(), endStmtIndex);

					boolean hasSecure = (oldFunction.indexOf("Secure") != -1) ? true : false;
					boolean hasProxy = (oldFunction.indexOf("Proxy") != -1) ? true : false;
					boolean hasProtocol = (oldFunction.indexOf("Protocol") != -1) ? true : false;

					// calculate the TAB for maintaining the appropriate formating
					int TABCount = getTABCount(functionPrefix);
					String TABIndentation = "";
					for (int i=0; i<TABCount; i++) {TABIndentation += TAB;}

					// check for commented code, if found ignore
					int commentStartIndex = functionPrefix.lastIndexOf(CRLF);
					boolean commentsFound = (commentStartIndex != -1) 
							? (functionPrefix.indexOf(COMMENTS, commentStartIndex) != -1) 
									? true 
											: false 
											: functionPrefix.indexOf(COMMENTS) != -1 
											? true 
													: false;

					String connectionInfoInstance = "connectionInfo_" + connectionInfoInstance_Counter++;
					replacementFunctionStmts.append(TABIndentation + ((commentsFound)?COMMENTS:"") + CONNECTION_INFO_INSTANTIATION_STMT.replace("connectionInfo_", connectionInfoInstance) + hasSecure +");" + CRLF);
					if (hasProxy) replacementFunctionStmts.append(TABIndentation + ((commentsFound)?COMMENTS:"") + CONNECTION_INFO_PROXY_FUNCTION_STMT + connectionInfoInstance + ", " + getProxyInfoParameters(oldFunction, oldFunctionParams) + ");" + CRLF);
					String protocol = (hasProtocol) ? getSecureProtocol(oldFunction, oldFunctionParams) : "null"; 
					if (hasSecure) replacementFunctionStmts.append(TABIndentation + ((commentsFound)?COMMENTS:"") + CONNECTION_INFO_SECURE_FUNCTION_STMT + connectionInfoInstance + ", " + protocol + ", " + getSecureInfoParameters(oldFunction, oldFunctionParams) + ");" + CRLF);

					if ((disableCookieParams = getDisableCookieFunctionParams(sb, startStmtIndex)) != null && oldFunctionParams.startsWith(disableCookieParams)) {
						replacementFunctionStmts.append(TABIndentation + ((commentsFound)?COMMENTS:"") + CONNECTION_INFO_DISABLE_COOKIE_FUNCTION_STMT + connectionInfoInstance + ", true);" + CRLF);
					}

					if ((disableExpectContinueParams = getDisableExpectContinueParams(sb, startStmtIndex)) != null) {
						if (Boolean.valueOf(disableExpectContinueParams)) {
							replacementFunctionStmts.append(TABIndentation + ((commentsFound)?COMMENTS:"") + CONNECTION_INFO_DISABLE_EXPECT_CONTINUE_FUNCTION_STMT + connectionInfoInstance + ", " + disableExpectContinueParams + ");" + CRLF);
						}
					}

					if (oldFunction.indexOf("Asynchronous") != -1) {
						int httpMethodParamIndex = 5;
						if (hasSecure) {
							httpMethodParamIndex = 4;
							errorCallbackRuleFunction = getErrorCallbackRuleFunction(sb, startStmtIndex);
						}
						String httpMethod = oldFunctionParams.split(",")[httpMethodParamIndex];
						replacementFunctionStmts.append(TABIndentation + ((commentsFound)?COMMENTS:"") + CONNECTION_INFO_SET_METHOD_NAME_FUNCTION_STMT + connectionInfoInstance.trim() + "," + httpMethod + ");" + CRLF);
					}

					replacementFunctionStmts.append(TABIndentation + removeLineFeedsAndTabs(functionPrefix) + newFunction + getCommonFunctionParameters(oldFunction, oldFunctionParams, errorCallbackRuleFunction) + ", " + connectionInfoInstance);

					sb = sb.replace(getStartIndexForReplacement(functionPrefix, startStmtIndex), endStmtIndex, replacementFunctionStmts.toString());
					endStmtIndex = startStmtIndex + replacementFunctionStmts.length();

					changed = true;
					replacementFunctionStmts.delete(0, replacementFunctionStmts.length());
				}
			}
		} else {
			// Already using ConnectionInfo Instance, removing httpMethod from sendAsynchronousRequest, if not already removed
			if (needsHTTPMethodMigration(sb)) {
				String functionToCheck = fHTTPFunctionMappings[6][0];
				if ((sb.indexOf(functionToCheck, 0) != -1)) {
					int endStmtIndex = 0;
					int offset = -1;
					while ((offset = sb.indexOf(functionToCheck, endStmtIndex)) != -1) {
						int startStmtIndex = sb.lastIndexOf(";", offset);
						if (startStmtIndex >= offset) {
							throw new RuntimeException("Invalid Index");
						}
						endStmtIndex = sb.indexOf(";", offset);
						
						String functionPrefix = sb.substring(startStmtIndex + 1, offset);
						if (isMisMatchedParenthese(functionPrefix)) {
							endStmtIndex = checkForEndParenthese(sb, startStmtIndex, endStmtIndex);
						} else {
							endStmtIndex -= 1;
						}

						String functionParams = sb.substring(offset + functionToCheck.length(), endStmtIndex);

						Collections.addAll(paramList, functionParams.split(","));
						String connectionInfoInstance = paramList.get(paramList.size()-1);
						String httpMethod = paramList.remove(paramList.size()-2);
						functionParams = String.join(",", paramList);			

						// calculate the TAB for maintaining the appropriate formating
						int TABCount = getTABCount(functionPrefix);
						String TABIndentation = "";
						for (int i=0; i<TABCount; i++) {TABIndentation += TAB;}

						// check for commented code, if found ignore
						int commentStartIndex = functionPrefix.lastIndexOf(CRLF);
						boolean commentsFound = (commentStartIndex != -1) 
								? (functionPrefix.indexOf(COMMENTS, commentStartIndex) != -1) 
										? true 
												: false 
												: functionPrefix.indexOf(COMMENTS) != -1 
												? true 
														: false;
						replacementFunctionStmts.append(TABIndentation + ((commentsFound)?COMMENTS:"") + CONNECTION_INFO_SET_METHOD_NAME_FUNCTION_STMT + connectionInfoInstance.trim() + "," + httpMethod + ");" + CRLF);
						replacementFunctionStmts.append(TABIndentation + removeLineFeedsAndTabs(functionPrefix) + functionToCheck + functionParams);

						sb = sb.replace(getStartIndexForReplacement(functionPrefix, startStmtIndex), endStmtIndex, replacementFunctionStmts.toString());
						endStmtIndex = startStmtIndex + replacementFunctionStmts.length();

						changed = true;
						paramList.clear();
						replacementFunctionStmts.delete(0, replacementFunctionStmts.length());
					}
				}
			}
		}
		
		if (changed) {
			clearFunctions(sb);
			writeFile(sb, file);
		}
	}
	
	private boolean isMisMatchedParenthese(String functionPrefix) {
		boolean misMatch = false;
		return (functionPrefix.lastIndexOf("(") > functionPrefix.lastIndexOf(")"));
	}
	
	private int checkForEndParenthese(StringBuilder sb, int startIndex, int endIndex) {
		for (int i = endIndex-1; i > startIndex; i--) {
			if (sb.charAt(i) == ')') continue;
			return i+1;
		}
		return endIndex;
	}
	
	private String getSecureInfoParameters(String functionName, String paramList) {
		String[] params = paramList.split(",");
		
		StringBuilder secureParams = new StringBuilder();
		int startIndex = (functionName.indexOf("Asynchronous") != -1) ? 5 : 3;
		for (int i = startIndex; i < startIndex+5; i++) {
			secureParams.append(params[i]);
			if (i+1 < startIndex+5) secureParams.append(", ");
		}
		
		return secureParams.toString();
	}
	
	private String getProxyInfoParameters(String functionName, String paramList) {
		String[] params = paramList.split(",");
		
		String proxyParams = params[params.length-2] + "," + params[params.length-1];
		
		return proxyParams;
	}
	
	private String getSecureProtocol(String functionName, String paramList) {
		String[] params = paramList.split(",");
		
		String protocol = (functionName.indexOf("Proxy") != -1) ? params[params.length - 3] : params[params.length - 1]; 

		return protocol;
	}
	
	private String getCommonFunctionParameters(String functionName, String paramList, String errorCallbackRuleFunction) {
		String[] params = paramList.split(",(?![^(]*\\))");
		
		StringBuilder commonParams = new StringBuilder();
		
		int initialParamCount = 0;
		if (functionName.indexOf("Asynchronous") != -1) {
			if (functionName.indexOf("Secure") != -1) {
				initialParamCount = 5;
			} else {
				initialParamCount = 6;
			}
		} else {
			if (functionName.indexOf("Secure") != -1) {
				initialParamCount = 3;
			} else {
				initialParamCount = 4;
			}
		}
		
		boolean ignoreMethodTypeParam = false;
		for (int i=0; i < params.length; i++) {
			ignoreMethodTypeParam = false;
			
			if (i < initialParamCount) {
				if (functionName.indexOf("Asynchronous") != -1) {
					if (i+1 == initialParamCount) {
						ignoreMethodTypeParam = true;
						if (functionName.indexOf("Secure") != -1) {
							commonParams.append(errorCallbackRuleFunction);
						}
					}
				}
				if (!ignoreMethodTypeParam) commonParams.append(params[i]);
				if (i+1 < initialParamCount) commonParams.append(", ");
			} else if (functionName.indexOf("Asynchronous") == -1 && functionName.indexOf("Secure") != -1 ) {
				if (i == initialParamCount + 5) {
					commonParams.append(", " + params[i]);
				}
			}
		}
		
		String commonParameters = commonParams.toString();
		commonParameters = commonParameters.endsWith(", ") ? commonParameters.substring(0, commonParameters.length() - 2) : commonParameters;
		return commonParameters;
	}
	
	private String getDisableCookieFunctionParams(StringBuilder sb, int currentStmtIndex) {
		String disableCookieParams = null;
		int offset = sb.lastIndexOf(OLD_FUNCTION_IGNORE_COOKIE, currentStmtIndex);
		if (offset != -1 && offset < currentStmtIndex) {
			int endStmtIndex = sb.indexOf(";", offset);
			disableCookieParams = sb.substring(offset + OLD_FUNCTION_IGNORE_COOKIE.length(), endStmtIndex-1);
			disableCookieParams = disableCookieParams.substring(0, disableCookieParams.indexOf(","));
		}
		
		return disableCookieParams;
	}
	
	private String getDisableExpectContinueParams(StringBuilder sb, int currentStmtIndex) {
		String disableExpectContinueParam = null;
		int offset = sb.lastIndexOf(OLD_FUNCTION_USE_EXPECT_CONTINUE, currentStmtIndex);
		if (offset != -1 && offset < currentStmtIndex) {
			int endStmtIndex = sb.indexOf(";", offset);
			String functionParams = sb.substring(offset + OLD_FUNCTION_USE_EXPECT_CONTINUE.length(), endStmtIndex-1);
			disableExpectContinueParam = (functionParams.indexOf("true") != -1) ? "false" : "true";
		}
		
		return disableExpectContinueParam;
	}
	
	private String getErrorCallbackRuleFunction(StringBuilder sb, int currentStmtIndex) {
		String errorCallbackRuleFunction = null;
		int offset = sb.lastIndexOf(OLD_FUNCTION_SET_ERROR_CALLBACK_RULEFUNCTION, currentStmtIndex);
		if (offset != -1 && offset < currentStmtIndex) {
			int endStmtIndex = sb.indexOf(";", offset);
			errorCallbackRuleFunction = sb.substring(offset + OLD_FUNCTION_SET_ERROR_CALLBACK_RULEFUNCTION.length(), endStmtIndex-1);
		}
		
		return errorCallbackRuleFunction;
	}
	
	private void clearFunctions(StringBuilder sb) {
		int offset; 
		
		// clear cookie functions
		while ((offset = sb.indexOf(OLD_FUNCTION_IGNORE_COOKIE)) != -1) {
			int startStmtIndex = sb.lastIndexOf(CRLF, offset);
			int endStmtIndex = sb.indexOf(";", startStmtIndex+1);
			sb.replace(startStmtIndex, endStmtIndex+1, "");
		}
		
		// clear expect continue function
		while ((offset = sb.indexOf(OLD_FUNCTION_USE_EXPECT_CONTINUE)) != -1) {
			int startStmtIndex = sb.lastIndexOf(CRLF, offset);
			int endStmtIndex = sb.indexOf(";", startStmtIndex+1);
			sb.replace(startStmtIndex, endStmtIndex+1, "");
		}
		
		// clear set error callback function
		while ((offset = sb.indexOf(OLD_FUNCTION_SET_ERROR_CALLBACK_RULEFUNCTION)) != -1) {
			int startStmtIndex = sb.lastIndexOf(CRLF, offset);
			int endStmtIndex = sb.indexOf(";", startStmtIndex+1);
			sb.replace(startStmtIndex, endStmtIndex+1, "");
		}
	}
	
	private int getStartIndexForReplacement(String functionPrefix, int startStmtIndex) {
		int replacementStartIndex = startStmtIndex;
		int offset = functionPrefix.lastIndexOf("\n");
		if (offset != -1) {
			replacementStartIndex += (offset+1);
		}
		
		return replacementStartIndex;
	}
	
	private String removeLineFeedsAndTabs(String functionPrefix) {
		int offset = functionPrefix.lastIndexOf("\n");
		if (offset != -1) {
			functionPrefix = functionPrefix.substring(offset+1);
		}
		functionPrefix = functionPrefix.replace("\t", "");
		return functionPrefix;
	}
	
	private int getTABCount(String functionPrefix) {
		boolean bFound = false;
		int tabCount = 0;
		char[] functioPrefixCharacters = functionPrefix.toCharArray();
		for (char prefix : functioPrefixCharacters) {
			if (prefix == '\t') {
				bFound = true;
				tabCount++;
			} else if (bFound && tabCount > 0) {
				break;
			}
		}
		
		return tabCount;
	}
	

	@Override
	public int getPriority() {
		return 100; // low priority, do after all others
	}
	
	@Override
	protected boolean isValidEntity(String extension) {
		if (IndexUtils.isRuleType(extension) || IndexUtils.isImplementationType(extension)
				|| IndexUtils.RULE_TEMPLATE_INSTANCE_EXTENSION.equalsIgnoreCase(extension) || IndexUtils.STATEMACHINE_EXTENSION.equalsIgnoreCase(extension)
				|| IndexUtils.EVENT_EXTENSION.equalsIgnoreCase(extension)) {
			return true;
		}
		
		return false;
	}
	
	private boolean needsConnectionInfoMigration(StringBuilder sb) {
		return (sb.indexOf(CONNECTION_INFO_DEFINITION_STMT, 0) == -1);
	}
	
	private boolean needsHTTPMethodMigration(StringBuilder sb) {
		return (sb.indexOf(CONNECTION_INFO_SET_METHOD_NAME_FUNCTION_STMT, 0) == -1);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HTTPFunctionMigrator httpFunctionMigrator = new HTTPFunctionMigrator();
		httpFunctionMigrator.migrateFunctionCalls(new File("/Users/vpatil/Downloads/httpMigration"));
	}
}
