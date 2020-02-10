/**
 * 
 */
package com.tibco.be.ws.display;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.lang3.StringEscapeUtils;

import com.tibco.be.util.FileUtils;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * @author vpatil
 */
public class DisplayHelper {
	
	private static final String DISPLAY_SUFFIX = "display";

	public static String getDisplayProperties(String  projectPath, String locale) {
		String projectName = projectPath.substring(projectPath.lastIndexOf("/") + 1);
		
		File projFile = new File(projectPath);
		List<File> selectedFiles = collectFiles(projFile);
		
		Locale l = new Locale(locale);
		
		StringBuffer responseBuffer = new StringBuffer();
		try {
			responseBuffer.append("<project>");
			responseBuffer.append("<projectName>");
			responseBuffer.append(projectName);
			responseBuffer.append("</projectName>");
			
			Path projPath = new Path(projFile.getAbsolutePath());
			
			for (File file : selectedFiles) {
				Path fileAbsPath = new Path(file.getAbsolutePath());
				
				String relPath = "/"+fileAbsPath.setDevice(null).removeFirstSegments(projPath.segmentCount()).removeFileExtension().toString();
				String parentName = file.getParent();
				String folderName = parentName.substring(projFile.getAbsolutePath().length());
				String fName = file.getName();
				
				int idx = fName.lastIndexOf('.');
				if (idx > 0) {
					fName = fName.substring(0, idx);
				}
				Properties dispProps = new Properties();
				loadDisplayProperties(dispProps, projectPath, projectName, parentName, folderName, fName, l);
				
				writeProperties(relPath, dispProps, responseBuffer);
			}
			responseBuffer.append("</project>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseBuffer.toString();
	}
	
	private static void loadDisplayProperties(Properties dispProps, String projPath, String projectName, String parentName,
			String folderName, String fileName, Locale l) {
		// first, load super concept/event display properties
		Entity entity = CommonIndexUtils.getEntity(projectName, folderName+File.separator+fileName);
		String superPath = null;
		Stack<String> ancestors = new Stack<String>();
		
		while (entity != null) {
			if (entity instanceof Concept) {
				superPath = ((Concept) entity).getSuperConceptPath();
			} else if (entity instanceof Event) {
				superPath = ((Event) entity).getSuperEventPath();
			}
			if (superPath == null || superPath.trim().length() == 0) {
				break;
			}
			ancestors.push(superPath);
			entity = CommonIndexUtils.getEntity(projectName, superPath);
		}
		while (!ancestors.empty()) {
			String superEntityPath = ancestors.pop();

			String dispPath = projPath + File.separator + superEntityPath;
			String fName = dispPath.substring(dispPath.lastIndexOf("/") + 1);
			String parent = dispPath.substring(0, dispPath.lastIndexOf("/"));
			//loadProperties(dispProps, parent, fName, Locale.ROOT);
			//loadProperties(dispProps, parent, fName, Locale.getDefault());
			loadProperties(dispProps, parent, fName, l);
		}
		
		// now, load all locale specific properties for this file
		//loadProperties(dispProps, parentName, fileName, Locale.ROOT);
		//loadProperties(dispProps, parentName, fileName, Locale.getDefault());
		loadProperties(dispProps, parentName, fileName, l);
	}

	private static void writeProperties(String entity, Properties dispProps, StringBuffer responseBuffer) {
		String entityDisplayText = (String) dispProps.get("displayText");
		if (entityDisplayText != null && !entityDisplayText.isEmpty()) {
			dispProps.remove("displayText");
		}
		
		Set<Object> keySet = dispProps.keySet();
		Iterator<Object> iterator = keySet.iterator();
		if (keySet.isEmpty()) {
			return;
		}
		responseBuffer.append("<displayProperty>");
		responseBuffer.append("<path>");
		responseBuffer.append(entity);
		responseBuffer.append("</path>");
		if (entityDisplayText != null && !entityDisplayText.isEmpty()) {
			responseBuffer.append("<displayText>");
			responseBuffer.append(StringEscapeUtils.escapeXml(entityDisplayText));
			responseBuffer.append("</displayText>");
		}
		
		List<String> keyList = new ArrayList<String>();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (keyList.contains(key)) {
				continue;
			}
			responseBuffer.append("<propInfo>");
			responseBuffer.append("<dispkey>");
			responseBuffer.append(key);
			responseBuffer.append("</dispkey>");
			
			String value = (String) dispProps.get(key);
			responseBuffer.append("<value>");
			responseBuffer.append(StringEscapeUtils.escapeXml(value));
			responseBuffer.append("</value>");
			
			String hiddenKey = key.substring(0, key.lastIndexOf(".") + 1) + "hidden";
			String hiddenValue = dispProps.getProperty(hiddenKey);

			if (hiddenValue != null) {
				if (Boolean.parseBoolean(hiddenValue)) {
					responseBuffer.append("<hidden>");
					responseBuffer.append(hiddenValue);
					responseBuffer.append("</hidden>");
				}
				
				keyList.add(hiddenKey);
			}
			
			responseBuffer.append("</propInfo>");
		}
		responseBuffer.append("</displayProperty>");
		
	}
	
	private static void loadProperties(Properties dispProps, String parentName, String fileName, Locale locale) {
		FileInputStream fis = null;
		InputStreamReader reader = null;
		DisplayModelControl ctrl = new DisplayModelControl();
		try {
			String bundleName = ctrl.toBundleName(fileName, locale);
			String resName = ctrl.toResourceName(bundleName, DISPLAY_SUFFIX);
			String bundleFileName = parentName + File.separator + resName;
			File dispFile = new File(bundleFileName);
			if (dispFile.exists()) {
				fis = new FileInputStream(dispFile);
				reader = new InputStreamReader(fis, ModelUtils.DEFAULT_ENCODING);
				dispProps.load(reader);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e) {
			}
		}

	}
	
	private static List<File> collectFiles(File parentFile) {
		ArrayList<File> selectedFiles = new ArrayList<File>();
		
		FileUtils.appendFilesRecursively(parentFile, selectedFiles, new String[]{"concept", "event"});
		
		return selectedFiles;
	}
	
	public static class DisplayModelControl extends ResourceBundle.Control {
        public static final List<String> FORMAT_DEFAULT = Collections.unmodifiableList(Arrays.asList("display"));

        @Override
		public List<String> getFormats(String baseName) {
			return FORMAT_DEFAULT;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(DisplayHelper.getDisplayProperties("C:/tibco/BE5.2/V428/be/5.2/examples/standard/WebStudio/CreditCardApplication", "en"));
	}

}
