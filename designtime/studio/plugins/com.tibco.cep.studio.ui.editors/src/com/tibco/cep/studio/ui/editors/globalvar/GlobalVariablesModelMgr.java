package com.tibco.cep.studio.ui.editors.globalvar;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.getSharedElementJarInput;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IStorage;
import org.eclipse.ui.IStorageEditorInput;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;

import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.resources.JarEntryFile;
import com.tibco.cep.studio.core.util.IdUtil;
import com.tibco.cep.studio.core.util.PasswordUtil;
import com.tibco.cep.studio.core.util.PersistenceUtil;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.globalvar.GlobalVariablesModel.GlobalVariablesDescriptor;
import com.tibco.cep.studio.ui.editors.globalvar.GlobalVariablesModel.GlobalVariablesGroup;
import com.tibco.cep.studio.ui.editors.globalvar.GlobalVariablesModel.IGlobalVariables;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/*
@author ssailapp
@date Dec 29, 2009 11:37:02 PM
 */

public class GlobalVariablesModelMgr {

	public final static String GV_DIR = "defaultVars";
	
	private String projPath;
	private ArrayList<String> gvFiles;
	private GlobalVariablesEditor editor;
	private GlobalVariablesModel model;
	private NamedNodeMap rootAttr;
	private String projectName;
	
	public GlobalVariablesModelMgr(String projPath, ArrayList<String> gvFiles, GlobalVariablesEditor editor) {
		this.projPath = projPath;
		this.gvFiles = gvFiles;
		this.editor = editor;
		this.projectName = editor.getEditorFile().getProject().getName();
	}

	public void modified() {
		editor.modified();
	}
	
	public void setRootAttributes(NamedNodeMap map) {
		rootAttr = map;
	}
	
	public void parseModel() {
		initModel();
		for (String gvFile: gvFiles) {
			gvFile = gvFile.replace('\\', '/');
			String relPath = gvFile.replace(projPath + "/" + GV_DIR, "");
			GlobalVariablesModelParser.loadModel(relPath, gvFile, this);
		}
	}

	public void initModel() {
		model = new GlobalVariablesModel();
	}
	
	public void saveModel() {
		String rootDir = projPath + "/" + GV_DIR;
		cleanCurrentGvEntries(rootDir);
		saveGvGroup(rootDir, model.gvGrp);
		cleanEmptyGvFolders(new File(rootDir));
	}
	
	private void cleanCurrentGvEntries(String rootDir) {
		// Deleting the entire directory causes problems when GVs are in source control. So, we just delete the specific files alone.

		//deleteDir(new File(rootDir));
		List<String> deletedFiles = new ArrayList<String>();
		for (String gvFile: gvFiles) {
			try {
				new File(gvFile).delete();
				deletedFiles.add(gvFile);
			} catch (Exception e) {
			}
		}
		gvFiles.removeAll(deletedFiles);
	}

	private void cleanEmptyGvFolders(File dir) {
	    if (dir.isDirectory()) {
	        String[] gvFolders = dir.list();
	        if (gvFolders.length == 0 &&
	        		!dir.getName().startsWith(".")) {
	        	dir.delete();
	        } else {
		        for (int i=0; i<gvFolders.length; i++) {
		        	if (gvFolders[i].startsWith("."))
		        		continue;
		        	cleanEmptyGvFolders(new File(dir, gvFolders[i]));
		        }
		        if (dir.list().length == 0) {
		        	dir.delete();
		        }
	        }
	    }
	}
	
	private void saveGvGroup(String path, GlobalVariablesGroup gvGrp) {
		new File(path).mkdirs();
		Document doc = GlobalVariablesModelParser.getSaveDocument(rootAttr, "repository");
		GlobalVariablesModelParser.saveModelParts(doc, gvGrp.gvs);
		PersistenceUtil.writeFile(doc, path + "/defaultVars.substvar");
		gvFiles.add(path + "/defaultVars.substvar");
		for (GlobalVariablesGroup grp: gvGrp.gvGrps) {
			saveGvGroup(path + "/" + grp.name, grp);
		}
	}
		
	public GlobalVariablesModel getModel() {
		return model;
	}
	
	public IGlobalVariables[] getRootGvs(GlobalVariablesGroup gvGrp) {
		return getGroupGvs(gvGrp);
	}
	
	public IGVProjectGroup[] getProjectGroups() {
		List<IGVProjectGroup> projectGroups = new ArrayList<IGVProjectGroup>();
		ProjectGroup currentGroup =  new ProjectGroup(projectName, projPath, false, model.gvGrp, this);
		model.curPrGrp = currentGroup;
		projectGroups.add(currentGroup);
		List<IGVProjectGroup> projectlibGroups = new ArrayList<IGVProjectGroup>();
		loadProjectLibraryGlobalVariables(projectlibGroups);
		if (projectlibGroups.size() > 0 ) {
			ProjectLibGroup prlibGrp = new ProjectLibGroup();
			prlibGrp.getProjectGroup().addAll(projectlibGroups);
			projectGroups.add(prlibGrp);
		}
		IGVProjectGroup[] grp = new IGVProjectGroup[projectGroups.size()];
		projectGroups.toArray(grp);
		return grp;
	}
	
	/**
	 * @param projectGroups
	 */
	private void loadProjectLibraryGlobalVariables(List<IGVProjectGroup> projectGroups) {
		Map<String, List<SharedElement>> map = new HashMap<String, List<SharedElement>>();
		StudioResourceUtils.getProjectLibraryGVList(projectName, map);
		for (String key: map.keySet()) {
			List<SharedElement> elements = map.get(key);
			String jPath = null;
			String fPath = null;
			if (elements == null) {
				continue;
			}
			GlobalVariablesModel model = new GlobalVariablesModel();
			for (SharedElement element: elements) {
				InputStream stream = null;
				IStorage store = null;
				try {
					IStorageEditorInput storageInput = getSharedElementJarInput(element);
					store = storageInput.getStorage();
					JarEntryFile file = (JarEntryFile)store;		
					stream = store.getContents();
					String fullPath = file.getFullPath().toOSString();
					fullPath = fullPath.replace('\\', '/');
					String path = fullPath.replaceFirst(GV_DIR, "");
					if (path.contains("/defaultVars.substvar")) {
						jPath = file.getJarFilePath();
						fPath = fullPath;
					}
					GlobalVariablesModelParser.loadModel(model, path, stream, this, true);
				} catch (Exception e) {
					EditorsUIPlugin.debug(this.getClass().getName(), e.getMessage());
				}finally {
					try {	
						if (stream != null){
							stream.close();
						}
						((JarEntryFile) store).closeJarFile();
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}
			IGlobalVariables[] gvs = getRootGvs(model.gvGrp);
			if (gvs.length > 0) {
				ProjectGroup group =  new ProjectGroup(jPath, fPath, true, model.gvGrp ,this);
				projectGroups.add(group);
			}
		}
	}
	
	public IGlobalVariables[] getGroupGvs(GlobalVariablesGroup gvGrp) {
		ArrayList<IGlobalVariables> objs = new ArrayList<IGlobalVariables>();
		for (GlobalVariablesDescriptor desc: gvGrp.gvs) {
			objs.add(desc);
		}
		for (GlobalVariablesGroup grp: gvGrp.gvGrps) {
			objs.add(grp);
		}
		return objs.toArray(new IGlobalVariables[0]);
	}
	
	public String getStringValue(GlobalVariablesDescriptor gvDesc, String key) {
		String retval = null;
		if (gvDesc == null)
			return ("");
		if (key.equals(GlobalVariablesModel.FIELD_NAME)) {
			retval = gvDesc.getName();
		} else if (key.equals(GlobalVariablesModel.FIELD_VALUE)) {
			retval = gvDesc.getValueAsString();
			if (isPassword(gvDesc))
				retval = PasswordUtil.getDecodedString(retval);
		} else if (key.equals(GlobalVariablesModel.FIELD_TYPE)) {
			retval = gvDesc.getType();
		} else if (key.equals(GlobalVariablesModel.FIELD_ISDEPLOY)) {
			retval = new Boolean(gvDesc.isDeploymentSettable()).toString();
		} else if (key.equals(GlobalVariablesModel.FIELD_ISSERVICE)) {
			retval = new Boolean(gvDesc.isServiceSettable()).toString();
		} else if (key.equals(GlobalVariablesModel.FIELD_DESCRIPTION)) {
			retval = gvDesc.getDescription();
		} else if (key.equals(GlobalVariablesModel.FIELD_CONSTRAINT)) {
			retval = gvDesc.getConstraint();
		} else if (key.equals(GlobalVariablesModel.FIELD_MODTIME)) {
			retval = new Long(gvDesc.getModificationTime()).toString();
		}
		if (retval != null)
			return retval;
		return ("");
	}
	
	public boolean getBooleanValue(GlobalVariablesDescriptor gvDesc, String key) {
		if (gvDesc == null)
			return false;
		if (key.equals(GlobalVariablesModel.FIELD_ISDEPLOY)) {
			return gvDesc.isDeploymentSettable();
		} else if (key.equals(GlobalVariablesModel.FIELD_ISSERVICE)) {
			return gvDesc.isServiceSettable();
		}
		return false;
	}
	
	public boolean updateValue(GlobalVariablesDescriptor gvDesc, String key, String value) {
		if (gvDesc == null)
			return false;
		String setValue = getStringValue(gvDesc, key);
		if (setValue.equals(value))
			return false;
		if (key.equals(GlobalVariablesModel.FIELD_NAME)) {
			gvDesc.setName(value);
		} else if (key.equals(GlobalVariablesModel.FIELD_VALUE)) {
			if (isPassword(gvDesc))
				gvDesc.setValue(PasswordUtil.getEncodedString(value));
			else
				gvDesc.setValue(value);
		} else if (key.equals(GlobalVariablesModel.FIELD_TYPE)) {
			gvDesc.setType(value);
			if (isPassword(gvDesc))
				gvDesc.setValue(PasswordUtil.getEncodedString(gvDesc.getValueAsString()));
		} else if (key.equals(GlobalVariablesModel.FIELD_ISDEPLOY)) {
			gvDesc.setDeploymentSettable(new Boolean(value).booleanValue());
		} else if (key.equals(GlobalVariablesModel.FIELD_ISSERVICE)) {
			gvDesc.setServiceSettable(new Boolean(value).booleanValue());
		} else if (key.equals(GlobalVariablesModel.FIELD_DESCRIPTION)) {
			gvDesc.setDescription(value);
		} else if (key.equals(GlobalVariablesModel.FIELD_CONSTRAINT)) {
			gvDesc.setConstraint(value);
		} 
		gvDesc.setModificationTime(System.currentTimeMillis());
		modified();
		return true;
	}
	
	public boolean updateGroupName(GlobalVariablesGroup gvGrp, String newName) {
		if (gvGrp != null && !newName.equals(gvGrp.name)) {
			gvGrp.name = newName;
			modified();
			return true;
		}
		return false;
	}
	
	private ArrayList<String> getGvNames(ArrayList<GlobalVariablesDescriptor> gvs) {
		ArrayList<String> names = new ArrayList<String>();
		for (GlobalVariablesDescriptor gvDesc: gvs) {
			names.add(gvDesc.getName());
		}
		return names;
	}
	
	private ArrayList<String> getGvGrpNames(ArrayList<GlobalVariablesGroup> gvGrps) {
		ArrayList<String> names = new ArrayList<String>();
		for (GlobalVariablesGroup gvGrp: gvGrps) {
			names.add(gvGrp.name);
		}
		return names;
	}
	
	public GlobalVariablesDescriptor addGlobalVariablesDescriptor() {
		return (addGlobalVariablesDescriptor(model.gvGrp));
	}
	
	public GlobalVariablesDescriptor addGlobalVariablesDescriptor(GlobalVariablesGroup gvGrp) {
		if (gvGrp == null)
			return null;
		GlobalVariablesDescriptor gvDesc = model.new GlobalVariablesDescriptor(false);
		String newName = IdUtil.generateSequenceId("New_Variable", getGvNames(gvGrp.gvs));
		gvDesc.setName(newName);
		gvDesc.setValue(""); // initialize it, otherwise it is null
		gvDesc.setType(GlobalVariablesModel.TYPE_STRING);
		gvDesc.setDeploymentSettable(true);
		gvDesc.setModificationTime(System.currentTimeMillis());
		gvGrp.gvs.add(gvDesc);
		gvDesc.parentGrp = gvGrp;
		modified();
		return gvDesc;
	}
	
	public GlobalVariablesGroup addGlobalVariablesGroup() {
		return (addGlobalVariablesGroup(model.gvGrp));
	}
	
	public GlobalVariablesGroup addGlobalVariablesGroup(GlobalVariablesGroup gvGrp) {
		if (gvGrp == null)
			return null;
		String name = IdUtil.generateSequenceId("New_Group", getGvGrpNames(gvGrp.gvGrps));
		GlobalVariablesGroup chdGrp = model.new GlobalVariablesGroup(gvGrp.path + "/" + name, false);
		gvGrp.gvGrps.add(chdGrp);
		chdGrp.parentGrp = gvGrp;
		modified();
		return chdGrp;
	}
	
	public void removeGlobalVariablesGroup(GlobalVariablesGroup gvGrp) {
		if (gvGrp.parentGrp != null) {
			gvGrp.parentGrp.gvGrps.remove(gvGrp);
			modified();
		}
	}
	
	public void removeGlobalVariablesDescriptor(GlobalVariablesDescriptor gvDesc) {
		if (gvDesc.parentGrp != null) {
			gvDesc.parentGrp.gvs.remove(gvDesc);
			modified();
		}
	}
	
	public String getDateTime(Long time) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date(time);
		return dateFormat.format(date);
	}

	private boolean isPassword(GlobalVariablesDescriptor gvDesc) {
		return (gvDesc.getType().equalsIgnoreCase("password"));
	}
}