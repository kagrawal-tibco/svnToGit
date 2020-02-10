package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.Map;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.ui.editors.utils.NodeAbstractDetailsPage;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 14, 2009 5:47:11 PM
 */

public abstract class ClusterNodeDetailsPage extends NodeAbstractDetailsPage {

	protected ClusterConfigModelMgr modelmgr;
	protected Label lField=null;

	public ClusterNodeDetailsPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {	
		super(viewer);
		this.modelmgr = modelmgr;
	}
	
	public GvField createGvTextField(Composite parent, String key) {
		return createGvTextField(parent, null, key);
	}

	public GvField createGvTextField(Composite parent, String label, String key) {
		if (label != null) {
			lField = PanelUiUtil.createLabel(parent, label);
		}
		GvField gvField = GvUiUtil.createTextGv(parent, getValue(key), lField);
		setGvFieldListeners(gvField, SWT.Modify, key);
		return gvField;
	}

	public GvField createGvPasswordField(Composite parent, String key) {
		return createGvPasswordField(parent, null, key);
	}

	public GvField createGvPasswordField(Composite parent, String label,
			String key) {
		if (label != null) {
			Label lField = PanelUiUtil.createLabel(parent, label);
		}
		GvField gvField = GvUiUtil.createPasswordGv(parent, getValue(key));
		setGvFieldListeners(gvField, SWT.Modify, key);
		return gvField;
	}

	public GvField createGvCheckboxField(Composite parent, String key) {
		return createGvCheckboxField(parent, null, key);
	}

	public GvField createGvCheckboxField(Composite parent, String label,
			String key) {
		if (label != null) {
			Label lField = PanelUiUtil.createLabel(parent, label);
		}
		GvField gvField = GvUiUtil.createCheckBoxGv(parent, getValue(key));
		setGvFieldListeners(gvField, SWT.Selection, key);
		return gvField;
	}

	public GvField createGvComboField(Composite parent, String items[],
			String key) {
		return createGvComboField(parent, null, items, key);
	}

	public GvField createGvComboField(Composite parent, String label,
			String items[], String key) {
		if (label != null) {
			Label lField = PanelUiUtil.createLabel(parent, label);
		}
		GvField gvField = GvUiUtil.createComboGv(parent, getValue(key));
		Combo combo = (Combo) gvField.getField();
		combo.setItems(items);
		String selItem = getValue(key);
		if (!selItem.equals(""))
			combo.setText(selItem);
		else if (combo.getItemCount() > 0)
			combo.setText(combo.getItem(0));
		setGvFieldListeners(gvField, SWT.Selection, key);
		return gvField;
	}

	protected void setGvFieldListeners(final GvField gvField, int eventType,
			String modelId) {
		gvField.setFieldListener(eventType,
				getListener(gvField.getField(), modelId));
		gvField.setGvListener(getListener(gvField.getGvText(), modelId));
		// add general listener to validate global variable exists
		gvField.getGvText().addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				Map<String, GlobalVariableDescriptor> glbVars = DefaultResourceValidator.getGlobalVariableNameValues(modelmgr.project.getName());
				String problemMessage = null;
				String text = gvField.getGvValue();
				if (GvUtil.isGlobalVar(gvField.getGvValue())) {
					GlobalVariableDescriptor gvd = glbVars.get(GvUtil.stripGvMarkers(text));
					if (gvd == null) {
						problemMessage = text + " is not defined";
					}
					
					if (problemMessage != null) {
						gvField.getGvText().setForeground(Display.getDefault().getSystemColor(
								SWT.COLOR_RED));
						gvField.getGvText().setToolTipText(problemMessage);
					} else {
						gvField.getGvText().setForeground(Display.getDefault().getSystemColor(
								SWT.COLOR_BLACK));
						gvField.getGvText().setToolTipText("");
					}
				}
			}
		});
	}

	public abstract Listener getListener(final Control field, final String key);

	public abstract String getValue(String key);
}
