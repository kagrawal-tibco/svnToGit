/**
 * 
 */
package com.tibco.cep.security.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.security.SecurityHelper;
import com.tibco.cep.security.tokens.AuthToken;
import com.tibco.cep.security.tokens.Authen;
import com.tibco.cep.security.tokens.Role;

/**
 * @author hitesh
 *
 */
public class AuthTokenUtils {

	private static AuthToken token;
	
	private static String AUTH_TOKEN_LOC;
	
	public static final String AUTH_TOKEN_FILE = "/Tokens/authToken.token";
	
	
	/**
	 * @return the token either the one which is cached, or from local storage
	 */
	public static final AuthToken getToken() {
		if (token == null) {
			if (AUTH_TOKEN_LOC == null) {
				throw new RuntimeException("You are not logged in to RMS and so do not have the permissions to perform the specific action.");
			}
			File tokenFile = new File(AUTH_TOKEN_LOC);
			if (tokenFile.exists() == true) {
				Reader fileReader = null;
				try {
					fileReader = new BufferedReader(new InputStreamReader(
						    new FileInputStream(tokenFile), "UTF-8"));
					StringBuilder sb = new StringBuilder();
					char[] chars = new char[256];
					int read = fileReader.read(chars);
					while (read != -1) {
						sb.append(chars, 0, read);
						read = fileReader.read(chars);
					}
					if (sb.length() > 0) {
						token = SecurityHelper.deserializeAuthToken(new String(sb));
					}
				} catch (IOException e) {
					//TODO Converting IOException's into RuntimeException to avoid changing a lot of dependent code 
					throw new RuntimeException("could not read token from "+AUTH_TOKEN_LOC,e);
				} finally {
					if (fileReader != null) {
						try {
							fileReader.close();
						} catch (IOException ignoreex) {
						}
					}
				}
			}
		}
		return token;		
	}
	
	 /**
     * Convenience API to fetch logged in user's roles.
     * @return
     */
    public static List<Role> getLoggedInUserRoles() {
    	AuthToken token = AuthTokenUtils.getToken();
    	if (token != null) {
    		return token.getAuthz().getRoles();
    	}
    	return new ArrayList<Role>(0);
    }
    
    public static String getLoggedinUser() {
		AuthToken token = AuthTokenUtils.getToken();
		if (token != null) {
			String username = token.getAuthen().getUser().toString();
			return username;
		}
		return null;
	}
    
    /**
	 * @param token
	 *            the token to set
	 */
	public static final void setToken(AuthToken token) {
		AuthTokenUtils.token = token;
	}
	
	/**
     * Clean up token file
     */
    public static void cleanResidualToken() {
    	File tokenFile = new File(AUTH_TOKEN_LOC);
		if (tokenFile.exists() == true) {
			//Delete
			tokenFile.delete();
		}
		token = null;
		AUTH_TOKEN_LOC = null;
    }
    
    /**
     * 
     * @param tokenString
     * @param tokenFileLocation -> Setting this to null will create token file under platform's metadata directory
     *                             iff studio is running.
     * @return
     * @throws IOException
     */
    public static AuthToken storeAuthToken(String tokenString, String tokenFileLocation) throws IOException {
    	if (tokenFileLocation == null) {
    		return null;
    		/*
    		//Check if eclipse runs
    		if (PlatformUtil.isStudioRunning()) {
    			tokenFileLocation = StudioUtilPlugin.getDefault().getStateLocation().toString() + AUTH_TOKEN_FILE;
    			AUTH_TOKEN_LOC = tokenFileLocation;
    		}
    		*/
    	}
		if (tokenString != null) {
			AuthToken deserializeAuthToken = 
				SecurityHelper.deserializeAuthToken(tokenString);
			if (deserializeAuthToken != null) {
				//Check whether mandatory elements exist or not
				Authen auth = deserializeAuthToken.getAuthen();
				if (auth == null) {
					//Auth Failure
					return null;
				}
				Writer fileWriter = null;
				try {
					AUTH_TOKEN_LOC = tokenFileLocation;
					File authLocFile = new File(tokenFileLocation);
					File parent = authLocFile.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					fileWriter = new BufferedWriter(new OutputStreamWriter(
						    new FileOutputStream(authLocFile), "UTF-8"));
					fileWriter.write(tokenString);
					fileWriter.flush();
					return deserializeAuthToken;
				} finally {
					if (fileWriter != null) {
						fileWriter.close();
					}
				}
			}
		}
		return null;
	}
    
	public static final String getTokenAsString() {
		String tokenString = null;
		if (token == null) {
			File tokenFile = new File(AUTH_TOKEN_LOC);
			if (tokenFile.exists() == true) {
				Reader fileReader = null;
				try {
					fileReader = new BufferedReader(new InputStreamReader(
						    new FileInputStream(tokenFile), "UTF-8"));
					StringBuilder sb = new StringBuilder();
					char[] chars = new char[256];
					int read = fileReader.read(chars);
					while (read != -1) {
						sb.append(chars, 0, read);
						read = fileReader.read(chars);
					}
					if (sb.length() > 0) {
						tokenString = new String(sb);
					}
				} catch (IOException e) {
					throw new RuntimeException("Could not read token from " + AUTH_TOKEN_LOC, e);
				} finally {
					if (fileReader != null) {
						try {
							fileReader.close();
						} catch (IOException ignoreex) {
						}
					}
				}
			}
		} else {
			tokenString = SecurityHelper.serializeAuthToken(token);
		}
		return tokenString;		
	}
}
