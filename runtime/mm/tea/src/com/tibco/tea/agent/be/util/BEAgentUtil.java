package com.tibco.tea.agent.be.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dijadhav
 *
 */
public class BEAgentUtil {
	public static String getFormatedDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * Get the path on windows when ssh is used.
	 * 
	 * @param dir
	 *            -Path on Windows
	 * @return Path on windows when ssh used.
	 */
	public static String getWinSshPath(String dir) {
		String path = "";
		path = dir.replaceAll("\\\\", "/");
		path = path.replaceAll(":/", "/");
		path = "/cygdrive/" + path;
		path=path.trim().replaceAll(" ", "\\\\ ");
		return path;
	}

	/**
	 * This method is used to get directory path from file path
	 * 
	 * @param fileFqPath
	 *            - Path of the file.
	 * @return - Directory file path
	 */
	public static String getDir(String fileFqPath) {
		fileFqPath=fileFqPath.replace("\\", "/");
		int idx = fileFqPath.lastIndexOf('/');
		int idx1 = fileFqPath.lastIndexOf('\\');

		if (idx == -1 && idx1 == -1)
			return null;

		if (idx != -1) {
			return fileFqPath.substring(0, idx);
		}

		return fileFqPath.substring(0, idx1);
	}

	/**
	 * This method is used to determine the targeted host.
	 * 
	 * @param targetOs
	 *            - Host details.
	 * @return Targeted host
	 */
	public static String determineMethod(final String targetOs) {

		if (targetOs == null || targetOs.isEmpty()) {
			return "windows";
		}
		if (targetOs.toLowerCase().contains("windows"))
			return "windows";
		else
			return "sftp";
	}

}
