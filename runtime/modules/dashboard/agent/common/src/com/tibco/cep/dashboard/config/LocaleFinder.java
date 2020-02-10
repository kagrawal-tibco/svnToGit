package com.tibco.cep.dashboard.config;

import java.util.Locale;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public final class LocaleFinder {

	private String localeID;

	private Locale locale;

	private Locale defaultLocale;

	private Logger logger;

	public LocaleFinder(Logger logger, String localeID) {
		this(logger, localeID, Locale.getDefault());
	}

	public LocaleFinder(Logger logger, String localeID, Locale defaultLocale) {
		this.localeID = localeID;
		this.defaultLocale = defaultLocale;
		this.logger = logger;
		parse();
	}

	private void parse() {
		if (StringUtil.isEmptyOrBlank(localeID) == false) {
			String[] parts = localeID.split("_");
			switch (parts.length) {
				case 1:
					locale = new Locale(parts[0]);
					break;
				case 2:
					locale = new Locale(parts[0], parts[1]);
					break;
				case 3:
					locale = new Locale(parts[0], parts[1], parts[2]);
					break;
				default:
					locale = null;
			}
			if (locale != null) {
				boolean matched = false;
				Locale[] availableLocales = Locale.getAvailableLocales();
				for (Locale availableLocale : availableLocales) {
					if (availableLocale.equals(locale) == true) {
						matched = true;
						break;
					}
				}
				if (matched == false) {
					locale = null;
				}
			}
		} else {
			locale = defaultLocale;
			logger.log(Level.INFO, "No locale id specified, defaulting to " + locale + " as the locale");
		}
		if (locale == null) {
			locale = defaultLocale;
			logger.log(Level.INFO, "Invalid locale id " + localeID + " defaulting to " + locale);
		}
	}

	public Locale getLocale() {
		return locale;
	}

}
