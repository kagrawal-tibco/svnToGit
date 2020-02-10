package com.tibco.cep.bpmn.ui.graph.palette;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroup;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil;
import com.tibco.cep.studio.common.configuration.BpmnPalettePathEntry;
import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;

public class PaletteLoader {
	
    private static boolean initialized = false;
	private static Map<String, BpmnPaletteModel> paletteModelsMap;
    
    protected static void init() {
        if (!initialized) {
            initialized = true;
            paletteModelsMap = new HashMap<String, BpmnPaletteModel>();
        }
    }
    
    public static BpmnPaletteModel load(IProject project) throws Exception {
    	init();

    	BpmnPaletteModel loadProjectPalette = loadProjectPalettes(project);
    	
    	if(loadProjectPalette == null){
    		loadProjectPalette = loadDefault();
    	}

    	paletteModelsMap.put(project.getName(), loadProjectPalette);
    	return loadProjectPalette;
    }
    
    private static BpmnPaletteModel loadProjectPalettes(IProject project){
    	List<BpmnPaletteModel> models = new ArrayList<BpmnPaletteModel>();
    	String projectName=project.getName();
    	BpmnProcessSettings config = BpmnCorePlugin.getBpmnProjectConfiguration(project.getName());
    	EList<BpmnPalettePathEntry> pathEntries = config.getPalettePathEntries();
    	for(BpmnPalettePathEntry pathEntry:pathEntries) {
    		String path = pathEntry.getPath();
    		File pfile = new File(path);
    		String internalPath=path.substring(path.indexOf(projectName)+projectName.length());
    		IFile file = (IFile)project.findMember(internalPath);
    		if(file != null){
    			BpmnPaletteModel loadBpmnPalette = BpmnPaletteResourceUtil.loadBpmnPalette(file, null);
    			if(loadBpmnPalette != null)
    				models.add(loadBpmnPalette);
    		}else if(pfile.exists()) {
    			BpmnPaletteModel loadBpmnPalette = BpmnPaletteResourceUtil.loadBpmnPalette(pfile, null);
    			if(loadBpmnPalette != null)
    				models.add(loadBpmnPalette);
    		} 
    	}
    	return mergePaletteModelList(models);
    }
    
    private static BpmnPaletteModel mergePaletteModelList(List<BpmnPaletteModel> models){
    	if(models.size() >0){
    		BpmnPaletteModel merged = models.remove(0);
    		for (BpmnPaletteModel bpmnPaletteModel : models) {
    			List<BpmnPaletteGroup> bpmnPaletteGroups = bpmnPaletteModel.getBpmnPaletteGroups();
    			for (BpmnPaletteGroup bpmnPaletteGroup : bpmnPaletteGroups) {
					if(merged.getBpmnPaletteGroup(bpmnPaletteGroup.getId()) == null)
						merged.addPaletteGroup(bpmnPaletteGroup);
					else{
						BpmnPaletteGroup originalGroup = merged.getBpmnPaletteGroup(bpmnPaletteGroup.getId());
						List<BpmnPaletteGroupItem> paletteItems = bpmnPaletteGroup.getPaletteItems();
						for (BpmnPaletteGroupItem bpmnPaletteGroupItem : paletteItems) {
							if(originalGroup.getPaletteItem(bpmnPaletteGroupItem.getId())== null)
								originalGroup.addPaletteItem(bpmnPaletteGroupItem);
						}
						
					}
				}
			}
    		
    		return merged;
    	}
    	return null;
    }
    

    public static BpmnPaletteModel loadDefault() throws Exception {
    	BpmnPaletteModel loadDefault = BpmnPaletteResourceUtil.loadDefault();
    	List<BpmnPaletteGroup> bpmnPaletteGroups = loadDefault.getBpmnPaletteGroups();
    	for (BpmnPaletteGroup bpmnPaletteGroup : bpmnPaletteGroups) {
    		bpmnPaletteGroup.setInternal(true);
    		List<BpmnPaletteGroupItem> paletteItems = bpmnPaletteGroup.getPaletteItems();
    		for (BpmnPaletteGroupItem bpmnPaletteGroupItem : paletteItems) {
				//TODO: For now Manual Task is not exposed
				if (bpmnPaletteGroupItem.getTitle().equals(BpmnUIConstants.NODE_MANUAL_TASK)) {
					bpmnPaletteGroupItem.setVisible(false);
				}
    			bpmnPaletteGroupItem.setInternal(true);
			}
		}
    	return loadDefault;
    }

    
    public static BpmnPaletteModel getBpmnPaletteModel(IProject project){
    	init();
    	BpmnPaletteModel bpmnPaletteModel = paletteModelsMap.get(project.getName());
    	if(bpmnPaletteModel == null){
    		try {
				load(project);
				bpmnPaletteModel = paletteModelsMap.get(project.getName());
			} catch (Exception e) {
				BpmnUIPlugin.log(e);
				if(paletteModelsMap== null)
					paletteModelsMap = new HashMap<String, BpmnPaletteModel>();
			}
    	}
    	return bpmnPaletteModel;
    }
    
    public static BpmnPaletteModel getBpmnPaletteModel(String projectName){
    	init();
    	BpmnPaletteModel bpmnPaletteModel = paletteModelsMap.get(projectName);

    	return bpmnPaletteModel;
    }

}
