package com.tibco.cep.studio.cli.studiotools;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author ssinghal
 *
 */
public class BEUrlClassLoader extends URLClassLoader{
	
	public BEUrlClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	public void addURL(URL url) {
        super.addURL(url);
    }

}
