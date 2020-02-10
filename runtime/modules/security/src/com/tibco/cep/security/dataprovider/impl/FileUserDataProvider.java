/**
 *
 */
package com.tibco.cep.security.dataprovider.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.security.dataprovider.IUserDataProvider;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.tokens.TokenFactory;
import com.tibco.cep.security.tokens.User;

/**
 * <p>
 * This class is not thread-safe. Callers should handle thread-safety
 * </p>
 * Date : 20/04/08
 * 
 * @author aathalye
 * 
 * 
 */
public class FileUserDataProvider implements IUserDataProvider {

	private List<UserData> userDatas;
	private Map<Object, Object> userNotifyIds;
	
	public static final char BOM_CHARACTER = '\uFEFF';

	private MessageDigest md;

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(FileUserDataProvider.class);

	@Override
	public void init(Properties properties) throws DataProviderException {
		// Try to load users file using the current class's class loader
		URL usersFileURL = this.getClass().getResource("/users.pwd");
        InputStream usersFile = null;
		if (usersFileURL != null) {
			try {
                usersFile = usersFileURL.openStream();
				md = MessageDigest.getInstance(DataProviderConstants.MD5_ALGORITHM);
				readUserData(usersFile);
				return;
			} catch (NoSuchAlgorithmException e) {
				// can't do much here
				throw new DataProviderException(String.format("Could not access [%s] algorithm to read user database", DataProviderConstants.MD5_ALGORITHM), e);
			} catch (IOException e) {
				LOGGER.log(Level.WARN, "Could not load user database using [%s] , will attempt loading using [%s] property", usersFileURL, DataProviderConstants.BE_AUTH_FILE_LOCATION);
			} finally {
                try {
                    if (usersFile != null) {
                        usersFile.close();
                    }
                } catch (IOException e) {
                    LOGGER.log(Level.ERROR, "", e);
                }
            }
		}
		// could not load usersFile using classloader(s), lets attempt to load using file location
		// Try to load from properties
		if (properties == null) {
			throw new DataProviderException("No properties were found in the current runtime environment");
		}
		String dataFile = properties.getProperty(DataProviderConstants.BE_AUTH_FILE_LOCATION);
		if (dataFile == null) {
			throw new DataProviderException(String.format("Missing [%s] in properties...", DataProviderConstants.BE_AUTH_FILE_LOCATION));
		}
		File file = new File(dataFile);
		if (!file.isAbsolute()) {
			throw new DataProviderException(String.format("Non absolute user database location specified for [%s] ", DataProviderConstants.BE_AUTH_FILE_LOCATION));
		}
		if (!file.exists()) {
			throw new DataProviderException(String.format("The user database location [%s] is non-existent", file.getAbsolutePath()));
		}
		try {
            usersFile = new FileInputStream(file);
			md = MessageDigest.getInstance(DataProviderConstants.MD5_ALGORITHM);
			readUserData(usersFile);
		} catch (FileNotFoundException e) {
			// should not happen
			throw new DataProviderException(file.getAbsolutePath() + " is non-existent");
		} catch (NoSuchAlgorithmException e) {
			throw new DataProviderException(String.format("Could not access [%s] algorithm to read user database", DataProviderConstants.MD5_ALGORITHM), e);
		} catch (IOException e) {
			throw new DataProviderException(String.format("Could not read contents of [%s]", file.getAbsolutePath()), e);
		}  finally {
            try {
                if (usersFile != null) {
                    usersFile.close();
                }
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
	}

	@Override
	public User authenticate(final String username, final String password) throws DataProviderException {
		if (userDatas == null || userDatas.isEmpty()) {
			throw new IllegalStateException("The provider has not been initialized");
		}
		if (username == null) {
			throw new IllegalArgumentException("Username cannot be null");
		}
		// Assuming we have clear text password here
		for (UserData userData : userDatas) {
			if (userData.getUsername().intern() == username.intern()) {
				// match password
				User user;
				String configPassword = userData.getPassword();
				configPassword = (configPassword.trim().length() == 0) ? null : configPassword;
				if ((password == null && configPassword == null) || (password.trim().length() == 0 && userData.getPassword().length() == 0)) {
					user = TokenFactory.INSTANCE.createUser();
					user.setUsername(username);
					return user;
				} else {
					// Hash the incoming password bits
					if (password != null) {
						try {
							byte[] pBits = password.getBytes("UTF-8");
							byte[] digested = md.digest(pBits);
							// Get the password in file
							String filePasswd = userData.getPassword();
							// The MD5 digested strings in the file are supposed
							// to be 32 byte length
							if (filePasswd == null) {
								throw new DataProviderException("Login failure");
							}
							// Convert it to 16 bytes
							byte[] fpBits = new byte[filePasswd.length() >>> 1];
							for (int i = 0; i < fpBits.length; i++) {
								// Take 2 characters at a time and compute the byte rep
								/*
								 * 0-2, 3-4 etc
								 */
								int shifted = i << 1;
								String hexString = filePasswd.substring(shifted, shifted + 2);
								fpBits[i] = (byte) Integer.parseInt(hexString, 16);
							}
							if (MessageDigest.isEqual(digested, fpBits)) {
								// Auth is successful
								user = TokenFactory.INSTANCE.createUser();
								user.setUsername(username);
								return user;
							}
						} catch (UnsupportedEncodingException une) {
							throw new DataProviderException("could not decode password", une);
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public Iterator<Role> getUserRoles(User user) throws DataProviderException {
		if (user == null) {
			throw new IllegalArgumentException("The input should be an authenticated user");
		}
		List<Role> roles = new ArrayList<Role>(0);
		// Get his roles
		UserData uData = getMatching(user);
		if (uData != null) {
			List<String> rolesList = uData.getRoles();
			for (String roleName : rolesList) {
				Role role = TokenFactory.INSTANCE.createRole();
				role.setName(roleName);
				roles.add(role);
			}
		}
		return roles.iterator();
	}

	@Override
	public String getUserNotifyId(String username) throws DataProviderException {
		if (userNotifyIds == null) {
			Properties properties = System.getProperties();
			loadUserNotifyIdsData(properties);
		}
		String userNotifyId = null;
		Object objVal = userNotifyIds.get(username);
		if (objVal != null) {
			userNotifyId = objVal.toString();
		}	
		
		return userNotifyId;
	}
	
	private UserData getMatching(User user) {
		for (UserData userData : userDatas) {
			if (userData.getUsername().intern() == user.getUsername().intern()) {
				return userData;
			}
		}
		return null;
	}

	private void readUserData(final InputStream fis) throws IOException {
		if (fis == null) {
			throw new IllegalArgumentException("The argument input stream cannot be null");
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		StreamTokenizer sToken = new StreamTokenizer(reader);
		sToken.resetSyntax();
		// sToken.ordinaryChar('\'');
		sToken.wordChars('A', 'z');
		sToken.wordChars(';', '_');
		sToken.quoteChar(';');
		sToken.quoteChar('\r');
		sToken.eolIsSignificant(false);
		populateTokens(sToken);
	}

	private void populateTokens(final StreamTokenizer sToken) throws IOException {
		StringBuilder sb = new StringBuilder();
		userDatas = new ArrayList<UserData>();
		while (sToken.nextToken() != StreamTokenizer.TT_EOF) {
			String temp;
			switch (sToken.ttype) {
				case StreamTokenizer.TT_WORD:
					// Read the entire word upto ;
					temp = sToken.sval;
					sb.append(temp);
					break;
				case StreamTokenizer.TT_EOL:
					// Do not do anything with this
					break;
				default: // single character in ttype
					temp = String.valueOf((char) sToken.ttype);
					if ((temp.charAt(0) != ';') && (temp.charAt(0) != '\r')) {
						sb.append(temp);
					} else {
						UserData uData = tokenizetoken(sb.toString());
						if (uData != null) {
							userDatas.add(uData);
						}
						sb.delete(0, sb.length());
					}
			}
		}
	}

	private UserData tokenizetoken(final String record) {
		if (record != null && record.length() > 0) {
			String[] tokens = record.charAt(0) == BOM_CHARACTER ? record.substring(1).split(":") : record.split(":");//Remove BOM character
			UserData uData;
			String username = tokens[0];
			String password = tokens[1];
			String roles = tokens[2];
			String subscriptionId = "";
			if(tokens.length == 4 && tokens[3] !=null  && !tokens[3].isEmpty()){
				subscriptionId = tokens[3];
			}
			List<String> rolesList = null;
			if (roles != null) {
				StringTokenizer rolesTok = new StringTokenizer(roles, ",");
				rolesList = new ArrayList<String>(rolesTok.countTokens());
				while (rolesTok.hasMoreTokens()) {
					rolesList.add(rolesTok.nextToken());
				}
			}
			uData = new UserData(username, password, rolesList, subscriptionId);
			return uData;
		}
		return null;
	}

	private void loadUserNotifyIdsData(Properties properties) throws DataProviderException {
		userNotifyIds = new HashMap<Object, Object>();
		String notifyDataFile = properties.getProperty(DataProviderConstants.BE_USER_NOTIFY_FILE_LOCATION);
		if (notifyDataFile == null) {
			throw new DataProviderException(String.format("Missing [%s] in properties...", DataProviderConstants.BE_USER_NOTIFY_FILE_LOCATION));
		}
		File file = new File(notifyDataFile);
		if (!file.isAbsolute()) {
			throw new DataProviderException(String.format("User NotifyIds Data file path [%s] is non-absolute", notifyDataFile));
		}
		if (!file.exists()) {
			throw new DataProviderException(String.format("User NotifyIds Data file path [%s] is non-existent", file.getAbsolutePath()));
		}
    	if (!file.isFile()) {
			throw new DataProviderException(String.format("Resource [%s] is not a file", notifyDataFile));
    	}
    	if (!file.canRead()) {
			throw new DataProviderException(String.format("Cannot read file [%s]", notifyDataFile));
    	}
    	
        InputStream usersNotifyFile = null;
		try {
            usersNotifyFile = new FileInputStream(file);
        	Properties props = new Properties();
    		props.load(usersNotifyFile);
    		userNotifyIds.putAll(props);
		} catch (FileNotFoundException e) {
			throw new DataProviderException(file.getAbsolutePath() + " is non-existent");
		} catch (IOException ioe) {
			throw new DataProviderException("Error reading file");
		} finally {
            try {
                if (usersNotifyFile != null) {
                	usersNotifyFile.close();
                }
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }		
	}
	
	
	public List<UserData> getUserDatas() throws DataProviderException {
	  
		return userDatas;
	}
	
	public class UserData {

		private String username;

		private String password;
		
		private String subscriptionId;

		private List<String> roles;

		UserData(final String username, final String password, final List<String> roles, final String subscriptionId) {
			this.username = username;
			this.password = password;
			this.roles = roles;
			this.subscriptionId = subscriptionId;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}
		
		public String getSubscriptionId() {
			return subscriptionId;
		}

		public List<String> getRoles() {
			return roles;
		}

	}
}