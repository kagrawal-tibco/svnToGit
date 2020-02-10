package com.tibco.cep.studio.ui.xml.wizards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditor;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorPanel;
import com.tibco.cep.studio.mapper.ui.data.param.BinaryCategory;
import com.tibco.cep.studio.mapper.ui.data.param.BooleanCategory;
import com.tibco.cep.studio.mapper.ui.data.param.DateTimeCategory;
import com.tibco.cep.studio.mapper.ui.data.param.DecimalCategory;
import com.tibco.cep.studio.mapper.ui.data.param.IntegerCategory;
import com.tibco.cep.studio.mapper.ui.data.param.StringCategory;
import com.tibco.cep.studio.mapper.ui.data.param.TypeCategory;
import com.tibco.cep.studio.ui.xml.utils.MapperUtils;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("serial")
public class XMLMapperForm extends JPanel{

	private BindingEditorPanel bePanel;
	private BindingEditor ed;
	private EMFTnsCache cache;
	private String projectName;
	
	public XMLMapperForm(String projectName){
		this.projectName = projectName;
		init();
		cache = StudioCorePlugin.getCache(projectName);
	}
	
	private void init(){
		setBackground(Color.WHITE);
		setName("Input");
		setLayout(new BorderLayout());
		setBorder(MapperUtils.createFullBorder());
		
		bePanel = new BindingEditorPanel(StudioCorePlugin.getUIAgent(projectName));
//		bePanel = new BindingEditorPanel(StudioCorePlugin.getDesignerDocument(),
//				cache.getXmluiAgent(),
//				new DefaultStylesheetResolver(),
//				cache.getFunctionResolver());

		ed = bePanel.getEditor();
		ed.setPreferredSize(new Dimension(500,300));

		//This describes the left Panel
		ed.setInputLabel("Scope Variables");
		ed.setInputLabelTooltip("My tooltip"); //TODO - copy the tooltip text (ResourceManager.manager.getString("ae.process.data.input.tooltip"));

		ed.setOutputLabel("Function");
		ed.setOutputRootDisplayName("Function");
//		Icon dispIcon = JIconsFactory.getImageIcon(JIconsFactory.J32); //TODO - ResourceManager.manager.getIcon("aeresource.default.icon");
//		ed.setInputRootDisplayIcon(dispIcon);
		ed.setSubstitutionDialogCallback(
				new TypeCategory[] {
						StringCategory.INSTANCE,
						IntegerCategory.INSTANCE,
						DecimalCategory.INSTANCE,
						BooleanCategory.INSTANCE,
						DateTimeCategory.INSTANCE,
						BinaryCategory.INSTANCE,
				},
				new TypeCategory[] {
				}
		);
		bePanel.setPreferredSize(new Dimension(600,400));
		bePanel.setBorder(BorderFactory.createEmptyBorder());
		add(bePanel, BorderLayout.CENTER);
	}
	public BindingEditorPanel getBePanel() {
		return bePanel;
	}
	public BindingEditor getEditor() {
		return ed;
	}
	public EMFTnsCache getCache() {
		return cache;
	}
}
