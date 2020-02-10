package com.tibco.cep.sharedresource.model;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.NamedNodeMap;

import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;
import com.tibco.cep.studio.core.util.PasswordUtil;
import com.tibco.cep.studio.core.utils.ModelUtils;

/*
@author ssailapp
@date Feb 5, 2010 1:01:40 PM
 */

public abstract class SharedResModelMgr {
	
	private AbstractSharedResourceEditor editor;
	private String filePath;
	private IProject project;
	private IResource resource;
	private NamedNodeMap rootAttr;
	private boolean allowResourceSave = false;
	
	public SharedResModelMgr(IProject project, AbstractSharedResourceEditor editor) {
		this.project = project;
		this.editor = editor;
		this.filePath = editor.getEditorFilePath();
	}

	public SharedResModelMgr(IResource resource) {
		this.resource = resource;
		this.project = resource.getProject();
		this.filePath = resource.getLocation().toString();
	}
	
	public SharedResModelMgr(String filePath) {
		this.filePath = filePath;
	}
	
    public void decodeFields(String keys[]) {
        for (String key : keys) {
            String encoded = (String) getModel().values.get(key);
            if (encoded != null) {
                String password = PasswordUtil.getDecodedString(encoded);
                // Fix for BE-24967; encoded can be an encoded string or clear password
                if (!PasswordUtil.isEncodedString(encoded) && password != null && password.equals("")) {
                    password = encoded;
                }
                getModel().values.put(key, password);
            }
        }
    }
	
	public void encodeFields(String keys[]) {
		for (String key: keys) {
			String decoded = (String) getModel().values.get(key);
			if (decoded != null) {
				String encoded = PasswordUtil.getEncodedString(decoded);
				getModel().values.put(key, encoded);
			}
		}
	}

	public boolean getBooleanValue(String key) {
		Object val = getModel().values.get(key);
		if (val != null) {
			if (val instanceof Boolean) {
				return ((Boolean)val).booleanValue();
			} else {
				return false;
			}
		}
		return false;
	}
	
	public AbstractSharedResourceEditor getEditor() {
		return editor;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public abstract SharedResModel getModel();
	
	public IProject getProject() {
		return project;
	}
	
	public LinkedHashMap<String, String> getProperties() {
		if (getModel() == null)
			parseModel();
		LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();
		for (Map.Entry<String, String> entry: getPropertyNames().entrySet()) {
			properties.put(entry.getValue(), getModel().values.get(entry.getKey()).toString());
		}
		return properties;
	}
	
	public abstract LinkedHashMap<String,String> getPropertyNames(); //display map: <Model ID, Display Name>
	
	public NamedNodeMap getRootAttributes() {
		return rootAttr;
	}
	
	public String getStringValue(String key) {
		Object val = getModel().values.get(key); 
		if (val != null)
			return val.toString();
		return ("");
	}
	
	public void modified() {
		if (editor != null) {
			editor.modified();
		} else if (resource != null && allowResourceSave) {
			String contents = saveModel();
			try {
				ByteArrayInputStream is = new ByteArrayInputStream(contents.getBytes(ModelUtils.DEFAULT_ENCODING));
				((IFile)resource).setContents(is, true, true, null);
			} catch (Exception e) {
			}
		}
	}
	
	public abstract void parseModel();
	
	public abstract String saveModel();
	
	public void setAllowResourceSave(boolean allow) {
		this.allowResourceSave = allow;
	}
	
	public void setRootAttributes(NamedNodeMap map) {
		rootAttr = map;
	}
	
	public boolean updateBooleanValue(String key, boolean en) {
		if (en ^ (getBooleanValue(key))) {
			getModel().values.put(key, new Boolean(en));
			modified();
			return true;
		}
		return false;
	}
	
	public boolean updateStringValue(String key, String value) {
		if (value == null)
			return false;
		if (!value.equalsIgnoreCase(getStringValue(key))) {
			getModel().values.put(key, value);
			modified();
			return true;
		}
		return false;
	}
}
