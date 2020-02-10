/**
 * 
 */
package com.tibco.cep.webstudio.server;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.I18nService;

/**
 * Service to load and process i18n packs based on the user locale set.
 * 
 * @author vpatil
 */
@SuppressWarnings("serial")
public class I18nServiceImpl extends RemoteServiceServlet implements
		I18nService {
	
	private static final String LOCALE_DIR_PROPERTY = "localeDir";
	private static final String DEFAULT_LOCALE = "en_US";
	private String availableLocale;
	
	private String localeDir;
	
	private static final Logger logger = Logger.getLogger(I18nServiceImpl.class.getName());
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		localeDir = getServletConfig().getInitParameter(LOCALE_DIR_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.i18n.I18nService#getMessages(java.lang.String)
	 */
	@Override
	public I18nRegistry getMessages(String locale) {
		I18nRegistry i18nRegistry = new I18nRegistry();
		
		Properties jarEntryProp = null;
		try {
			File localeJar = checkFileValidity(locale);
			
			String availableLocale = getAvailableLocale();
			String fileSuffix = availableLocale.equals(DEFAULT_LOCALE) ? ".properties" : "_" + availableLocale + ".properties";
			
			String fileSuffixWithoutCountryCode =  availableLocale.contains("_") ? "_" + availableLocale.substring(0, availableLocale.indexOf("_")) + ".properties" : "_" + availableLocale + ".properties";
			
			String fileSuffixWithoutCountryLangCode = ".properties";
			
			if (localeJar != null) {
				JarFile localeJarFile = new JarFile(localeJar);
				Enumeration<JarEntry> jarEntries = localeJarFile.entries();

				while (jarEntries.hasMoreElements()) {
					JarEntry jarEntry = jarEntries.nextElement();
					InputStream jarEntryStream = localeJarFile.getInputStream(jarEntry);

					if (jarEntryStream != null) {
						jarEntryProp = DEFAULT_LOCALE.equals(availableLocale) ? new Properties() : loadFallbackProps(jarEntry.getName());
						jarEntryProp.load(new InputStreamReader(jarEntryStream, Charset.forName("utf-8")));
						
						if (jarEntry.getName().equals(GLOBAL_MESSAGES + fileSuffix) || jarEntry.getName().equals(GLOBAL_MESSAGES + fileSuffixWithoutCountryCode) || jarEntry.getName().equals(GLOBAL_MESSAGES + fileSuffixWithoutCountryLangCode)) i18nRegistry.setGlobalMessages(new HashMap<String, String>((Map)jarEntryProp));
						else if (jarEntry.getName().equals(DT_MESSAGES + fileSuffix) || jarEntry.getName().equals(DT_MESSAGES + fileSuffixWithoutCountryCode) || jarEntry.getName().equals(DT_MESSAGES + fileSuffixWithoutCountryLangCode)) i18nRegistry.setDTMessages(new HashMap<String, String>((Map)jarEntryProp));
						else if (jarEntry.getName().equals(PROCESS_MESSAGES + fileSuffix) || jarEntry.getName().equals(PROCESS_MESSAGES + fileSuffixWithoutCountryCode) || jarEntry.getName().equals(PROCESS_MESSAGES + fileSuffixWithoutCountryLangCode)) i18nRegistry.setProcessMessages(new HashMap<String, String>((Map)jarEntryProp));
						else if (jarEntry.getName().equals(RMS_MESSAGES + fileSuffix) || jarEntry.getName().equals(RMS_MESSAGES + fileSuffixWithoutCountryCode) || jarEntry.getName().equals(RMS_MESSAGES + fileSuffixWithoutCountryLangCode)) i18nRegistry.setRMSMessages(new HashMap<String, String>((Map)jarEntryProp));
						else if (jarEntry.getName().equals(DOMAIN_MESSAGES + fileSuffix) || jarEntry.getName().equals(DOMAIN_MESSAGES + fileSuffixWithoutCountryCode) || jarEntry.getName().equals(DOMAIN_MESSAGES + fileSuffixWithoutCountryLangCode)) i18nRegistry.setDomainMessages(new HashMap<String, String>((Map)jarEntryProp));
						else if (jarEntry.getName().equals(RTI_MESSAGES + fileSuffix) || jarEntry.getName().equals(RTI_MESSAGES + fileSuffixWithoutCountryCode) || jarEntry.getName().equals(RTI_MESSAGES + fileSuffixWithoutCountryLangCode)) i18nRegistry.setRTIMessages(new HashMap<String, String>((Map)jarEntryProp));
						else if (jarEntry.getName().equals(CUSTOM_SGWT_MESSAGES + fileSuffix) || jarEntry.getName().equals(CUSTOM_SGWT_MESSAGES + fileSuffixWithoutCountryCode) || jarEntry.getName().equals(CUSTOM_SGWT_MESSAGES + fileSuffixWithoutCountryLangCode)) i18nRegistry.setCustomSgwtMessages(new HashMap<String, String>((Map)jarEntryProp));
						
						logger.info("Jar Entry - " + jarEntry.getName() + " processed.");
					}
				}
				
				if (!isI18nRegistryLoaded(i18nRegistry)) {
					logger.error("Either no locale matching files found under archive file ("
							+ localeDir + ") or the file content was empty.");
				}
			}
		} catch(Exception exception) {
			logger.error("Failed to process i18n files from the given location ("
							+ localeDir + ").", exception);
		}
		
		return i18nRegistry;
	}
	
	/**
	 * Loads the properties from the default(english) language pack to enable fallback (to english) in case a property is not available in LP.
	 * 
	 * @param jarEntryName
	 * @return
	 * @throws Exception
	 */
	private Properties loadFallbackProps(String propFileName) throws Exception {
		Properties fallbackProps = new Properties();
		
		File localePath = new File(localeDir);
		File fallbackJar = getFallbackLocale(localePath);
		if (fallbackJar != null) {
			JarFile fallbackJarFile = null;
			try {
				fallbackJarFile = new JarFile(fallbackJar);
				Enumeration<JarEntry> jarEntries = fallbackJarFile.entries();
				while (jarEntries.hasMoreElements()) {
					JarEntry jarEntry = jarEntries.nextElement();
					InputStream jarEntryStream = fallbackJarFile.getInputStream(jarEntry);
					
					if (jarEntryStream != null
							&& jarEntry.getName().split("[\\._]")[0].equals(propFileName.split("[\\._]")[0])) {
						fallbackProps.load(new InputStreamReader(jarEntryStream, Charset.forName("utf-8")));
						logger.info("Loading fallback properties. Jar Entry - " + jarEntry.getName() + " processed.");
					}
				}
			}
			finally {
				if (fallbackJarFile != null) {
					fallbackJarFile.close();
				}
			}
		}
		return fallbackProps;
	}
	
	/**
	 * Check file validity
	 * 
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private File checkFileValidity(String locale) throws Exception {
		if (localeDir != null && !localeDir.isEmpty()) {	
			File localePath = new File(localeDir);
			
			if (localePath.isDirectory()) {
				File localeJarFilePath = new File(localePath, locale + ".jar");
				File localeJarFilePathwithoutCountry = null;
				String langCode = null;
				// Local jar without country code
				if(locale.contains("_")){
					langCode = locale.substring(0, locale.indexOf("_"));
					localeJarFilePathwithoutCountry = new File(localePath, langCode + ".jar");
				}
				
				if (localeJarFilePath.exists() && localeJarFilePath.getCanonicalPath().endsWith(locale + ".jar")) {//Locale in jar and file names is case sensitive.
					setAvailableLocale(locale);
					return localeJarFilePath;
				} else if (localeJarFilePathwithoutCountry !=null && localeJarFilePathwithoutCountry.exists() && localeJarFilePathwithoutCountry.getCanonicalPath().endsWith(langCode + ".jar")){
					setAvailableLocale(langCode);
					return localeJarFilePathwithoutCountry;
				} else {
					// if no locale matches
					logger.error("(" + localeJarFilePath.getAbsolutePath() + ") does not exist. Falling back on the default locale (" + DEFAULT_LOCALE + ").");
					setAvailableLocale(DEFAULT_LOCALE);
					return getFallbackLocale(localePath);
				}
			} else {
				throw new Exception(localeDir + " is not a directory. Directory path expected.");
			}
		} else {
			throw new Exception(localeDir + " is either null/empty.");
		}
	}
	
	/**
	 * Fall back on Default locale - en_US
	 * 
	 * @param localePath
	 * @return
	 */
	private File getFallbackLocale(File localePath) {
		File defaultLocaleJarFilePath = new File(localePath, DEFAULT_LOCALE + ".jar");
		return defaultLocaleJarFilePath;
	}
	
	/**
	 * Check if all the i18n messages are loaded
	 * 
	 * @param i18nRegistry
	 * @return
	 */
	private boolean isI18nRegistryLoaded(I18nRegistry i18nRegistry) {
		if (i18nRegistry.getDomainMessages() == null || i18nRegistry.getDomainMessages().size() == 0) return false;
		if (i18nRegistry.getDTMessages() == null || i18nRegistry.getDTMessages().size() == 0) return false;
		if (i18nRegistry.getGlobalMessages() == null || i18nRegistry.getGlobalMessages().size() == 0) return false;
		if (i18nRegistry.getProcessMessages() == null || i18nRegistry.getProcessMessages().size() == 0) return false;
		if (i18nRegistry.getRMSMessages() == null || i18nRegistry.getRMSMessages().size() == 0) return false;
		if (i18nRegistry.getRTIMessages() == null || i18nRegistry.getRTIMessages().size() == 0) return false;
		if (i18nRegistry.getCustomSgwtMessages() == null || i18nRegistry.getCustomSgwtMessages().size() == 0) return false;
		
		return true;
	}
	
	/**
	 * @return
	 */
	public String getAvailableLocale() {
		return availableLocale;
	}

	/**
	 * @param availableLocale
	 */
	public void setAvailableLocale(String availableLocale) {
		this.availableLocale = availableLocale;
	}
}
