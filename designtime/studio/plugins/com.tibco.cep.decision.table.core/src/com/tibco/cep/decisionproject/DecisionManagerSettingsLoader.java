package com.tibco.cep.decisionproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.tibco.decision.table.core.DecisionTableCorePlugin;

/**
 * 
 * @author sasahoo
 *
 */

public class DecisionManagerSettingsLoader {

	private static DecisionManagerSettingsLoader decisionManagerSettingsLoader;	
	private static List<String> projects = new ArrayList<String>();
	private static List<String> editors = new ArrayList<String>();
	
	private static File decisionProjectSettingsFile = null;
	private static File editorSettingsFile = null;
	
	public static final String RECENT_DECISION_MANAGER_PROJECTS = ".DMPROJECTSETTINGS";//for decision projects settings
	public static final String RECENT_DECISION_MANAGER_EDITORS = ".DMEDITORSETTINGS";//for editor settings
	
	private static String recentProject = null;
	private static String recentEditor = null;

	synchronized public static DecisionManagerSettingsLoader getInstance() {
		if (decisionManagerSettingsLoader == null) {
			decisionManagerSettingsLoader = new DecisionManagerSettingsLoader();

			//For Recent projects settings
			if (DecisionTableCorePlugin.getDefault() == null) return decisionManagerSettingsLoader;
			decisionProjectSettingsFile = new File(DecisionTableCorePlugin.getDefault().getStateLocation().append(RECENT_DECISION_MANAGER_PROJECTS).toPortableString());
			if(!decisionProjectSettingsFile.exists()){
				try {
					decisionProjectSettingsFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				projects = readDecisionManagerSettings(decisionProjectSettingsFile,true,false); 
			}
			
			//For Recent editors settings
			editorSettingsFile = new File(DecisionTableCorePlugin.getDefault().getStateLocation().append(RECENT_DECISION_MANAGER_EDITORS).toPortableString());
			if(!editorSettingsFile.exists()){
				try {
					editorSettingsFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				editors = readDecisionManagerSettings(editorSettingsFile,false,true); 
			}
		}
		return decisionManagerSettingsLoader;
	}
	
	/**
	 * @param list
	 * @param fullPath
	 */
	public void updateRecent(List<String> list,String fullPath,int max){
		if(fullPath!=null){
			if(list.contains(fullPath)){
				list.remove(fullPath);
			}
			list.add(fullPath);
			if(max!=-1){
				if(list.size() > max){
					list.remove(0);
				}
			}
		}
	}
	
	/**
	 * @param elements
	 * @param file
	 * @param activeRecentElement
	 * @param SETTINGS_FILE
	 */
	public void writeRecentElements(List<String> elements, File file, String activeRecentElement,String SETTINGS_FILE){
		List<String> tempeditors = checkRecentElement(elements,activeRecentElement);
		BufferedWriter bufWriter = null;
		try {
			if(file.exists()){
				file.delete();
			}
			file = DecisionTableCorePlugin.getDefault().getStateLocation().append(SETTINGS_FILE).toFile();
			bufWriter = new BufferedWriter(new FileWriter(file));
			for (String editor:tempeditors) {
				bufWriter.write(editor);
				bufWriter.newLine();
			}
			bufWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * editors
	 * @param recentProject
	 */
	private List<String> checkRecentElement(List<String> elements,String recentElement){
		List<String> tempeditors = new ArrayList<String>();
		for (String editor:elements) {
			if(recentElement!=null && editor.equalsIgnoreCase(recentElement))
				editor =  "*" + editor;
			tempeditors.add(editor);
		}
		return tempeditors;
	}
	
	/**
	 * @param file
	 * @param project
	 * @param editor
	 * @return
	 */
	private static List<String> readDecisionManagerSettings(File file,
			                                                boolean project,
			                                                boolean editor
			                                                ) {
		BufferedReader in = null;
		List<String> list = new ArrayList<String>();
		try {
			if(file!=null){
				in = new BufferedReader(new FileReader(file));
				String line;
				try {
					while ((line = in.readLine()) != null) {
						if(line.contains("*")){
							line = line.substring(1);
							if(project)
								recentProject = line;
							if(editor)
								recentEditor = line;
						}
						list.add(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * projects
	 * @param file
	 * @param content
	 */
	public void removeLineFromFile(String file, String content) {
		try {
			File inFile = new File(file);
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
			BufferedReader br = new BufferedReader(new FileReader(file));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (!line.trim().equals(content)) {
					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			br.close();
			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			}
			if (!tempFile.renameTo(inFile))
				System.out.println("Could not rename file");
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Projects
	 * @return
	 */
	public File getDecisionProjectSettingsFile() {
		return decisionProjectSettingsFile;
	}

	/**
	 * Projects
	 * @return
	 */
	public List<String> getProjects() {
		return projects;
	}

	/**
	 * Projects
	 * @return
	 */
	public static String getRecentProject() {
		return recentProject;
	}

	/**
	 * Editors
	 * @return
	 */
	public List<String> getEditors() {
		return editors;
	}

	/**
	 * Editor
	 * @return
	 */
	public static String getRecentEditor() {
		return recentEditor;
	}

	/**
	 * Editor
	 * @return
	 */
	public File getEditorSettingsFile() {
		return editorSettingsFile;
	}
}
