/**
 * 
 */
package com.tibco.cep.studio.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.tibco.be.util.BEProperties;

/**
 * @author aathalye
 *
 */
public class RMSConfig {
	
	private static BEProperties beProperties;
	
	static {
		String configPath = System.getProperty("wrapper.tra.file",
				"be-rms.tra");
		if (configPath != null) {
			try {
				File file = new File(configPath);
				System.out.println(configPath);
				if (file.exists()) {
					beProperties = new BEProperties();
					beProperties.load(new FileInputStream(file));
				}
			} catch (IOException e) {
				//TODO Handle better
				e.printStackTrace();
			}
		}
	}
	
	public static BEProperties getBranch(final String prefix) {
		if (beProperties != null) {
			return beProperties.getBranch(prefix);
		}
		return null;
	}
}
