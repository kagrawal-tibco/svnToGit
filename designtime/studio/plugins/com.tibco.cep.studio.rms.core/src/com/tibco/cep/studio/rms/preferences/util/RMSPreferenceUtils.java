/**
 * 
 */
package com.tibco.cep.studio.rms.preferences.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.be.util.BEStringUtilities;
import com.tibco.cep.security.util.EncryptionHandler;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.rms.core.RMSCorePlugin;
import com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo;
import com.tibco.cep.studio.rms.preferences.model.AuthenticationURLInfo;
import com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo;
import com.tibco.cep.studio.rms.preferences.model.CheckoutURLInfo;
import com.tibco.cep.studio.rms.preferences.model.PreferencesFactory;
import com.tibco.cep.studio.rms.preferences.model.PreferencesPackage;
import com.tibco.cep.studio.rms.preferences.model.RMSPreferences;
import com.tibco.cep.studio.rms.preferences.model.URL;
import com.tibco.cep.studio.rms.preferences.model.URLInfo;
import com.tibco.cep.studio.rms.preferences.model.UserInfo;

/**
 * @author aathalye
 *
 */
public class RMSPreferenceUtils {
	
	private static final String RMS_PREFERENCES_FILE_NAME = "RMSPreferences.prefs";
	
	/**
	 * Single instance per workspace.
	 */
	private static RMSPreferences rmsPreferences;

	private static EncryptionHandler encryptionHandler;
		
	/**
	 * 	
	 * @return
	 */
	private static String getRMSPreferencesLocation() {
		IPath pluginStateLocation = RMSCorePlugin.getDefault().getStateLocation();
		String fileLoc = pluginStateLocation.toOSString() + File.separator + RMS_PREFERENCES_FILE_NAME;
		File prefsFile = new File(fileLoc);
		if (!prefsFile.exists()) {
			//Create one
			try {
				boolean fileCreated = prefsFile.createNewFile();
				if (fileCreated) {
					return prefsFile.getAbsolutePath();
				}
			} catch (IOException e) {
				RMSCorePlugin.log(e);
			}
		}
		return fileLoc;
	}
	
	/**
	 * 
	 * @return
	 */
	public static RMSPreferences getRMSPreferences() {
		//If still null create one.
		if (rmsPreferences == null) {
			rmsPreferences = PreferencesFactory.eINSTANCE.createRMSPreferences();
		}
		return rmsPreferences;
	}
	
	/**
	 * 
	 * @return
	 */
	public static RMSPreferences loadRMSPreferences() {
		if (rmsPreferences != null) {
			return rmsPreferences;
		}
		String rmsPreferencesLocation = getRMSPreferencesLocation();
		//Load the file if it has contents
		FileInputStream fis = null;
		try {
            File file = new File(rmsPreferencesLocation);
            fis = new FileInputStream(file);
			ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(PreferencesPackage.eNS_URI, new XMIResourceFactoryImpl());
			final EPackage.Registry registry = EPackage.Registry.INSTANCE;
			registry.put(PreferencesPackage.eNS_URI, PreferencesPackage.eINSTANCE);
			if (fis.available() > 0) {
	            rmsPreferences = 
	            	(RMSPreferences)CommonIndexUtils.loadEObject(rmsPreferencesLocation, fis);
			}
		} catch (Exception e) {
			RMSCorePlugin.log(e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					RMSCorePlugin.log(e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	private static AuthenticationInfo getAuthenticationInfo() {
		RMSPreferences rmsPreferences = getRMSPreferences();
		AuthenticationInfo authenticationInfo = rmsPreferences.getAuthInfo();
		if (authenticationInfo == null) {
			authenticationInfo = PreferencesFactory.eINSTANCE.createAuthenticationInfo();
			rmsPreferences.setAuthInfo(authenticationInfo);
		}
		return authenticationInfo;
	}
	
	/**
	 * 
	 * @return
	 */
	private static CheckoutRepoInfo getCheckoutInfo() {
		RMSPreferences rmsPreferences = getRMSPreferences();
		CheckoutRepoInfo checkoutRepoInfo = rmsPreferences.getCheckoutInfo();
		if (checkoutRepoInfo == null) {
			checkoutRepoInfo = PreferencesFactory.eINSTANCE.createCheckoutRepoInfo();
			rmsPreferences.setCheckoutInfo(checkoutRepoInfo);
		}
		return checkoutRepoInfo;
	}
	
	/**
	 * 
	 * @return
	 */
	private static AuthenticationURLInfo getAuthURLInfo() {
		AuthenticationInfo authenticationInfo = getAuthenticationInfo();
		AuthenticationURLInfo urlInfo = authenticationInfo.getUrlInfo();
		if (urlInfo == null) {
			urlInfo = PreferencesFactory.eINSTANCE.createAuthenticationURLInfo();
			authenticationInfo.setUrlInfo(urlInfo);
		}
		return urlInfo;
	}
	
	/**
	 * 
	 * @return
	 */
	private static CheckoutURLInfo getCheckoutURLInfo() {
		CheckoutRepoInfo checkoutRepoInfo = getCheckoutInfo();
		CheckoutURLInfo checkoutURLInfo = checkoutRepoInfo.getUrlInfo();
		if (checkoutURLInfo == null) {
			checkoutURLInfo = PreferencesFactory.eINSTANCE.createCheckoutURLInfo();
			checkoutRepoInfo.setUrlInfo(checkoutURLInfo);
		}
		return checkoutURLInfo;
	}
	
	/**
	 * 
	 * @param urlString
	 * @return
	 */
	public static boolean addAuthURLInfo(String urlString) {
		URLInfo urlInfo = getAuthURLInfo();
		return addURLInfo(urlInfo, urlString);
	}
	
	/**
	 * @param urlInfo
	 * @param urlString
	 * @return
	 */
	private static boolean addURLInfo(URLInfo urlInfo, String urlString) {
		List<URL> urls = urlInfo.getUrls();
		boolean added = true;
		//Check if this is already present
		for (URL url : urls) {
			if (url.getUrlString().equals(urlString)) {
				added = false;
				//Do not add
			}
		}
		if (added) {
			URL newURL = PreferencesFactory.eINSTANCE.createURL();
			newURL.setUrlString(urlString);
			urls.add(newURL);
		}
		return added;
	}
	
	/**
	 * 
	 * @param urlString
	 * @return
	 */
	public static boolean addCheckoutURLInfo(String urlString) {
		URLInfo urlInfo = getCheckoutURLInfo();
		return addURLInfo(urlInfo, urlString);
	}
	
	/**
	 * Save preferences model to file.
	 */
	public static void saveRMSPreferences() {
		RMSPreferences rmsPreferences = getRMSPreferences();
		FileOutputStream fos = null;
		try {
			String resourceLocation = getRMSPreferencesLocation();
			XMIResource resource = 
				new XMIResourceImpl(URI.createFileURI(resourceLocation));
			resource.getContents().add(rmsPreferences);
			//File newFile = file.getLocation().toFile();
			File newFile = new File(resourceLocation);
			fos = new FileOutputStream(newFile);
			resource.save(fos, null);			
		} catch (Throwable e) {
			RMSCorePlugin.log(e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					RMSCorePlugin.log(e);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param number -> Number of urls to be retrieved
	 * @return
	 */
	public static List<String> getAuthenticationURLs(int number) {
		AuthenticationURLInfo urlInfo = getAuthURLInfo();
		return getURLs(urlInfo, number);
	}
	
	private static List<String> getURLs(URLInfo urlInfo, int number) {
		if (number <= 0) {
			number = 1;
		}
		//Get available URLs
		List<URL> availableURLS = urlInfo.getUrls();
		number = (availableURLS.size() <= number) ? availableURLS.size() : number;
		List<URL> urls = availableURLS.subList(0, number);
		List<String> urlStrings = new ArrayList<String>(urls.size());
		
		for (URL url : urls) {
			urlStrings.add(url.getUrlString());
		}
		return urlStrings;
	}
	
	/**
	 * 
	 * @param number -> Number of urls to be retrieved
	 * @return
	 */
	public static List<String> getCheckoutURLs(int number) {
		URLInfo urlInfo = getCheckoutURLInfo();
		return getURLs(urlInfo, number);
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean savePassword(String username, String password) {
		try {
			byte[] encryptedPassword = encryptPassword(password);
			AuthenticationInfo authenticationInfo = getAuthenticationInfo();
			List<UserInfo> userInfos = authenticationInfo.getUserInfos();
			//Check if it already exists
			for (UserInfo userInfo : userInfos) {
				if (userInfo.getUsername().equals(username)) {
					//Set password on it
					userInfo.setPassword(encryptedPassword);
					return true;
				}
			}
			//Create new
			UserInfo userInfo = PreferencesFactory.eINSTANCE.createUserInfo();
			userInfo.setUsername(username);
			userInfo.setPassword(encryptedPassword);
			return userInfos.add(userInfo);
		} catch (Exception e) {
			RMSCorePlugin.log(e);
		}
		return false;
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public static String getPassword(String username) {
		AuthenticationInfo authenticationInfo = getAuthenticationInfo();
		List<UserInfo> userInfos = authenticationInfo.getUserInfos();
		//Check if it already exists
		for (UserInfo userInfo : userInfos) {
			if (userInfo.getUsername().equals(username)) {
				//Get password
				byte[] encryptedPassword = userInfo.getPassword();
				//Decrypt it
				try {
					return BEStringUtilities.convertByteArrayToString(decryptPassword(encryptedPassword), "UTF-8");
				} catch (Exception e) {
					RMSCorePlugin.log(e);
				}
			}
		}
		return "";
	}
	
	/**
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static byte[] encryptPassword(String password) throws Exception {
		if (encryptionHandler == null) {
			encryptionHandler = new EncryptionHandler(512, true);
		}
		byte[] passwordBytes = getPasswordBytes(password);
		byte[] pwdArray = encryptionHandler.encrypt(passwordBytes, null);
	    if (pwdArray != null && pwdArray.length > 0) {
	    	return pwdArray;
	    }
	    return new byte[0];
	}
	
	/**
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static byte[] decryptPassword(byte[] encryptedPassword) throws Exception {
		if (encryptionHandler == null) {
			encryptionHandler = new EncryptionHandler(512, true);
		}
		byte[] pwdArray = encryptionHandler.decrypt(encryptedPassword, null);
	    if (pwdArray != null && pwdArray.length > 0) {
	    	return pwdArray;
	    }
	    return new byte[0];
	}
	
	/**
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static byte[] getPasswordBytes(CharSequence password) throws Exception {
		CharBuffer charBuffer = CharBuffer.wrap(password);
		//Default UTF-8
		CharsetEncoder charsetEncoder = Charset.forName("UTF-8").newEncoder();
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(password.toString().getBytes("UTF-8").length);//ByteBuffer capacity must be number of bytes needed to store the password.
		if (!charsetEncoder.canEncode(password)) {
			throw new Exception("Cannot encrypt password");
		}
		charsetEncoder.encode(charBuffer, byteBuffer, false);
		//Flip the byte buffer and take its position to 0
		((Buffer)byteBuffer).flip();
		byte[] passwordBytes = new byte[byteBuffer.capacity()];
		byteBuffer.get(passwordBytes);
		return passwordBytes;
	}
}
