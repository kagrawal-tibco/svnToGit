package com.tibco.cep.studio.ui.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class DisplayModelControl extends ResourceBundle.Control {

    public static final List<String> FORMAT_DEFAULT
    = Collections.unmodifiableList(Arrays.asList("display"));

    @Override
	public List<String> getFormats(String baseName) {
		return FORMAT_DEFAULT;
	}

	@Override
	public ResourceBundle newBundle(String baseName, Locale locale,
			String format, ClassLoader loader, boolean reload)
			throws IllegalAccessException, InstantiationException,
			IOException {
        String bundleName = toBundleName(baseName, locale);
        ResourceBundle bundle = null;

        final String resourceName = toResourceName(bundleName, "display");
        final ClassLoader classLoader = loader;
        final boolean reloadFlag = reload;
        InputStream stream = null;
        try {
            stream = AccessController.doPrivileged(
                new PrivilegedExceptionAction<InputStream>() {
                    public InputStream run() throws IOException {
                        InputStream is = null;
                        if (reloadFlag) {
                            URL url = classLoader.getResource(resourceName);
                            if (url != null) {
                                URLConnection connection = url.openConnection();
                                if (connection != null) {
                                    // Disable caches to get fresh data for
                                    // reloading.
                                    connection.setUseCaches(false);
                                    is = connection.getInputStream();
                                }
                            }
                        } else {
                            is = classLoader.getResourceAsStream(resourceName);
                        }
                        return is;
                    }
                });
        } catch (PrivilegedActionException e) {
            throw (IOException) e.getException();
        }
        if (stream != null) {
            try {
                bundle = new PropertyResourceBundle(stream);
            } finally {
                stream.close();
            }
        }
        return bundle;
	}

}