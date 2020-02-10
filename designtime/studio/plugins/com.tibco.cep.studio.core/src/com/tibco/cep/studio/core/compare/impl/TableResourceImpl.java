package com.tibco.cep.studio.core.compare.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.util.TableXMLLoad;

public class TableResourceImpl extends XMIResourceImpl {

	public TableResourceImpl(URI uri) {
		super(uri);
	}

	@Override
	public void load(Map<?, ?> options) throws IOException {
		if (options.get("RELOAD") != null && options.get("RELOAD").equals("true")) {
			if (getURI() != null) {
				StudioCorePlugin.debug(getClass().getName(), "Setting 'loaded' flag to false for Table. URI: {0}", getURI().toFileString());
			} else {
				StudioCorePlugin.debug(getClass().getName(), "Setting 'loaded' flag to false for Table.");
			}
			setLoaded(false);
		}
		super.load(options);
	}

	@Override
	protected XMLLoad createXMLLoad() {
		return new TableXMLLoad(createXMLHelper());
	}

	public TableRuleSet resolveDecisionTable() {
		return null;
	}

	@Override
	public void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		loadTables();
		super.doSave(outputStream, options);
	}

	@Override
	public void doSave(Writer writer, Map<?, ?> options) throws IOException {
		loadTables();
		super.doSave(writer, options);
	}

	private void loadTables() {
		EList<EObject> contents = getContents();
		for (EObject eObj : contents) {
			if (eObj instanceof Table) {
				((Table)eObj).getDecisionTable(); // this assures that the decision/exception table is loaded before saving
			}
		}
	}

	@Override
	public void save(Map<?, ?> options) throws IOException {
		loadTables();
//		if (options == null) {
//			options = new HashMap();
//		}
//		((Map)options).put(OPTION_SAVE_ONLY_IF_CHANGED, OPTION_SAVE_ONLY_IF_CHANGED_FILE_BUFFER);
		super.save(options);
	}
}
