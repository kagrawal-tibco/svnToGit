package com.tibco.cep.bpmn.ui.editors.bpmnPalette;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeAdapterFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeEvent;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeListener;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroup;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil;
import com.tibco.cep.bpmn.ui.utils.PaletteHelpTextGenerator;



/**
 * 
 * @author majha
 *
 */

public class BpmnPaletteConfigurationModelMgr implements ModelChangeListener{
	private BpmnPaletteConfigurationModel model;
	private IProject project;
	private IResource resource;
	private BpmnPaletteConfigurationEditor editor;
	boolean isLoading;
	private ModelChangeAdapterFactory modelChangeAdapterFactory;
	
	public BpmnPaletteConfigurationModelMgr(IProject project, BpmnPaletteConfigurationEditor editor) {
		this.project = project;
		this.editor = editor;
		this.resource = editor.getEditorFile();
		this.modelChangeAdapterFactory = new ModelChangeAdapterFactory(this);
	}

	public BpmnPaletteConfigurationModelMgr(IResource resource) {
		this.project = resource.getProject();
		this.resource = resource;
		this.modelChangeAdapterFactory = new ModelChangeAdapterFactory(this);
	}
	

	public IProject getProject() {
		return project;
	}
	public void parseModel() throws Exception {
		initModel();
	}

	public void initModel() throws Exception {
		isLoading = true;
		BpmnPaletteModel loadModel = BpmnPaletteResourceUtil.loadBpmnPalette(resource, modelChangeAdapterFactory);
		model = new BpmnPaletteConfigurationModel(loadModel);
		isLoading = false;
	}
	
	void reloadModel(){
		try {
			initModel();
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
	}

	public void saveModel() {
		BpmnPaletteResourceUtil.saveBpmnPalette(model.getBpmnPaletteModel(), resource);
	}
	

	public BpmnPaletteConfigurationModel getModel() {
		return model;
	}

	public boolean updateEMFType(Object selObj, String text) {
		if (selObj instanceof BpmnPaletteGroupItem) {
			BpmnPaletteGroupItem item = (BpmnPaletteGroupItem) selObj;
			BpmnCommonPaletteGroupEmfItemType emfItemType = (BpmnCommonPaletteGroupEmfItemType)item.getItemType();
			return emfItemType.setEmfType(text);
		}
		
		return false;
	}
	
	public boolean updateExtendedType(Object selObj, String text) {
		if (selObj instanceof BpmnPaletteGroupItem) {
			BpmnPaletteGroupItem item = (BpmnPaletteGroupItem) selObj;
			BpmnCommonPaletteGroupEmfItemType emfItemType = (BpmnCommonPaletteGroupEmfItemType)item.getItemType();
			return emfItemType.setExtendedType(text);
		}
		
		return false;
	}
	
	public boolean updateAttachResource(Object selObj, String text) {
		if (selObj instanceof BpmnPaletteGroupItem) {
			BpmnPaletteGroupItem item = (BpmnPaletteGroupItem) selObj;
			if(item.getAttachedResource() != text){
				item.setAttachedResource(text);
				return true;
			}
		}
		
		return false;
	}

	public boolean updateTitleName(Object selObj, String text) {
	
		 if (selObj instanceof BpmnPaletteGroupItem) {
			BpmnPaletteGroupItem item = (BpmnPaletteGroupItem) selObj;
			if(text.isEmpty()){
//				MessageDialog.openError(null, "Error", Messages.getString("title.invalidName"));
				text = item.getId();
			}
			item.setTitle(text);
			return true;
		} else if (selObj instanceof BpmnPaletteGroup) {
			BpmnPaletteGroup group = (BpmnPaletteGroup) selObj;
			if(text.isEmpty()){
				text = group.getId();
			}
			group.setTitle(text);
			return true;
		}
		return false;
	}
	public boolean updateHelpContent(Object selObj, String text,String tab) {
		 if (selObj instanceof BpmnPaletteGroupItem) {
			 BpmnPaletteGroupItem item = (BpmnPaletteGroupItem) selObj;
			 if(text.isEmpty()){
//					MessageDialog.openError(null, "Error", Messages.getString("title.invalidName"));
				String help = PaletteHelpTextGenerator.getHelpText(item,tab).replace("&nbsp;", "").replace("<ul class=\"noindent\">", "").replace("</ul>", "");
				text = /*item.getHelp(tab)*/help;
			}
			 item.setHelp(tab,text);
			 return true;
		 }
		 return false;
	}

	public boolean validate(Object selObj, String text) {
		if(text == ""){
			MessageDialog.openError(null,BpmnMessages.getString("bpmnPaletteConfigModelMgr_validationerror_title"), BpmnMessages.getString("bpmnPaletteConfigModelMgr_validationerror_message"));
			return false;
		}
		 if (selObj instanceof BpmnPaletteGroupItem){
			 BpmnPaletteGroupItem item = (BpmnPaletteGroupItem) selObj;
			 List<BpmnPaletteGroupItem> items = item.getParentBpmnPaletteGroup().getPaletteItems();
				for(BpmnPaletteGroupItem grpItem:items){
					if(grpItem.getTitle().equals(text) && grpItem.getId()!= item.getId()){
						MessageDialog.openError(null,BpmnMessages.getString("bpmnPaletteConfigModelMgr_validationerror_dup_title"), BpmnMessages.getString("bpmnPaletteConfigModelMgr_validationerror_dupGrpItem_message"));
						return false;
					}
				}
				return true;
		 }else if (selObj instanceof BpmnPaletteGroup) {
			 BpmnPaletteGroup group = (BpmnPaletteGroup) selObj;
			 List<BpmnPaletteGroup> items = group.getParent().getBpmnPaletteGroups();
				for(BpmnPaletteGroup grpItem:items){
					if(grpItem.getTitle().equals(text) && grpItem.getId()!= group.getId()){
						MessageDialog.openError(null,BpmnMessages.getString("bpmnPaletteConfigModelMgr_validationerror_dup_title"), BpmnMessages.getString("bpmnPaletteConfigModelMgr_validationerror_dupGrp_message"));
						return false;
					}
				}
				return true;
		 }
		return false;
		
	}

	public boolean updateTooltipName(Object selObj, String text) {
		 if (selObj instanceof BpmnPaletteGroupItem) {
			BpmnPaletteGroupItem item = (BpmnPaletteGroupItem) selObj;
			item.setTooltip(text);
			return true;
		} else if (selObj instanceof BpmnPaletteGroup) {
			BpmnPaletteGroup group = (BpmnPaletteGroup) selObj;
			group.setTooltip(text);
			return true;
		}
		return false;
	}

	public Set<String> getIcons(){
		Set<String> icons = new HashSet<String>();
		List<BpmnPaletteGroup> bpmnPaletteGroups = model.getBpmnPaletteModel().getBpmnPaletteGroups();
		for (BpmnPaletteGroup bpmnPaletteGroup : bpmnPaletteGroups) {
			Collection<BpmnPaletteGroupItem> items=bpmnPaletteGroup.getPaletteItems();
			Iterator<BpmnPaletteGroupItem> iterator=items.iterator();
			
			while (iterator.hasNext()) {
				icons.add(iterator.next().getIcon());
			}
		}
		
		return icons;
	}
	
	public BpmnPaletteConfigurationEditor getEditor(){
		return editor;
	}

	public ModelChangeAdapterFactory getModelChangeAdapterFactory() {
		return modelChangeAdapterFactory;
	}
	
	@Override
	public void modelChanged(ModelChangeEvent mce) {
		if(!this.isLoading) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					editor.modelChanged();
				}
			});
		}
		
	}
	
}
