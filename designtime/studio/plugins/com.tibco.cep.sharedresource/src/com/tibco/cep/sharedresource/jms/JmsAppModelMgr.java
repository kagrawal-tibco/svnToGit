package com.tibco.cep.sharedresource.jms;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.PersistenceUtil;

/*
@author ssailapp
@date Dec 31, 2009 12:05:24 AM
 */

public class JmsAppModelMgr extends SharedResModelMgr {

	private JmsAppModel model;
	protected String resourceName;
	
    public static String keys[] = new String[] { "PropertyName", "Type", "Cardinality" };
	
	public JmsAppModelMgr(IProject project, JmsAppEditor editor) {
		super(project, editor);
	}
	
	public JmsAppModelMgr(IResource resource) {
		super(resource);
		this.resourceName = resource.getName();
	}

	@Override
	public JmsAppModel getModel() {
		return model;
	}

	@Override
	public LinkedHashMap<String,String> getPropertyNames() {
		return new LinkedHashMap<String, String>();
	}

	public void initModel() {
		String name = "";
		if (getEditor() != null) {
			name = getEditor().getEditorFileName();
		} else {
			name = resourceName;
		}
		name = name.replace(".sharedjmsapp", "");
		model = new JmsAppModel(name);
		model.values.put("description", "");
		model.values.put("resourceType", "ae.shared.JMSAppPropResource");
	}
	
	@Override
	public void parseModel() {
		initModel();
		JmsAppModelParser parser = new JmsAppModelParser();
		if (getFilePath() != null) {
			parser.loadModel(getFilePath(), this);
		} else if (getEditor().getDocument() != null) {
			parser.loadModel(getEditor().getDocument(), this);
		}
	}

	@Override
	public String saveModel() {
		Document doc = JmsAppModelParser.getSaveDocument(getRootAttributes(), "BWSharedResource");
		new JmsAppModelParser().saveModelParts(doc, this);
		String docString = PersistenceUtil.getDocumentString(doc);
		return docString;
	}

	public void updateApplicationProperties(Table table) {
		if (table == null)
			return;
		model.appProps.clear();
		for (TableItem item: table.getItems()) {
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			for (int col=0; col<table.getColumnCount(); col++)
				map.put(keys[col], item.getText(col));
			model.appProps.add(map);
		}
		modified();
	}
}
