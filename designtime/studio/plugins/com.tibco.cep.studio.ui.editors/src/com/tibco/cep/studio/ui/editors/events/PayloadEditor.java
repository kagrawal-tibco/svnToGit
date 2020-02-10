package com.tibco.cep.studio.ui.editors.events;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.ui.advance.event.payload.ParameterPayloadEditor;

/**
 * 
 * @author sasahoo
 *
 */
public class PayloadEditor {

	private EMFTnsCache cache;
	private ParameterPayloadEditor editor;
	private String fProjectName;
	
	public PayloadEditor(String projectName){
		this.fProjectName = projectName;
	}

	public ParameterPayloadEditor getEditorPanel(){
		if(cache == null){
			cache = StudioCorePlugin.getCache(fProjectName);
		}
		if(editor == null){
			UIAgent agent = StudioCorePlugin.getUIAgent(fProjectName);
			editor = new ParameterPayloadEditor(agent){
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void update(java.awt.Graphics g) {
				//	paint(g);
				}
			};
		//	editor.setBackground(Color.WHITE);
		}
		return editor;
	}

	public String getfProjectName() {
		return fProjectName;
	}

	public void setfProjectName(String fProjectName) {
		this.fProjectName = fProjectName;
	}
	
}
