package com.tibco.cep.bpmn.common.palette.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.studio.common.palette.PaletteGroup;
import com.tibco.cep.studio.common.palette.PaletteModel;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 *  
 * @author majha
 *
 */
public class BpmnCommonPaletteModel{
	
	protected PaletteModel model;
	Map<String , BpmnCommonPaletteGroup> paletteGroups;
	List<BpmnCommonPaletteGroup> paletteGroupsList = new ArrayList<BpmnCommonPaletteGroup>();
	String name;
	
	public BpmnCommonPaletteModel(PaletteModel mdl){
		model = mdl;
		initializePaletteGroup();
	}
	
	private void initializePaletteGroup() {
		paletteGroups = new LinkedHashMap<String, BpmnCommonPaletteGroup>();
		EList<PaletteGroup> paletteToolSet = model.getPaletteToolSet();
		for (PaletteGroup paletteGroup : paletteToolSet) {
			BpmnCommonPaletteGroup bpmnPaletteGroup = new BpmnCommonPaletteGroup(this,paletteGroup);
			paletteGroups.put(paletteGroup.getId(),bpmnPaletteGroup);
			paletteGroupsList.add(bpmnPaletteGroup);
		}
	}
	
	public String getName() {
		return model.getName();
	}

	public void  setName(String name) {
		if(!model.getName().equalsIgnoreCase(name))
			model.setName(name);
	}
	
	public List<BpmnCommonPaletteGroup> getBpmnPaletteGroups() {
		return paletteGroupsList;
	}
	
	public List<BpmnCommonPaletteGroup> getBpmnPaletteGroups(boolean includeInvisible) {
		List<BpmnCommonPaletteGroup> arrayList =null;
		if(includeInvisible)
			arrayList = paletteGroupsList;
		else{
			arrayList = new ArrayList<BpmnCommonPaletteGroup>();
			for (BpmnCommonPaletteGroup bpmnPaletteGroup : paletteGroupsList) {
				if(bpmnPaletteGroup.isVisible())
					arrayList.add(bpmnPaletteGroup);
			}
		}
		
		return arrayList;
	}
	
	public void addPaletteGroup(BpmnCommonPaletteGroup group){
		paletteGroups.put(group.getId(), group);
		model.getPaletteToolSet().add(group.getGroup());
		paletteGroupsList.add(group);
	}
	
	public void removePaletteGroup(String toolSetID){
		BpmnCommonPaletteGroup paletteToolSet = paletteGroups.remove(toolSetID);
		paletteGroupsList.remove(paletteToolSet);
		if(paletteToolSet != null)
			model.getPaletteToolSet().remove(paletteToolSet.getGroup());
	}
	
	public BpmnCommonPaletteGroup getBpmnPaletteGroup(String id) {
		return paletteGroups.get(id);
	}
	
	public void moveUp(BpmnCommonPaletteGroup item){
		EList<PaletteGroup> palettes = model.getPaletteToolSet();
		int index = palettes.indexOf(item.getGroup()); 
		if(index != 0){
			palettes.move(index-1, index);
			Collections.swap(paletteGroupsList,index-1, index);
		}
	}
	
	public void moveDown(BpmnCommonPaletteGroup item){
		EList<PaletteGroup> palettes = model.getPaletteToolSet();
		int index = palettes.indexOf(item.getGroup()); 
		if(index <(palettes.size() -1)){
			palettes.move(index+1, index);
			Collections.swap(paletteGroupsList,index, index+1);
		}
	}
	
	public PaletteModel getModel() {
		return model;
	}
	
	public BpmnCommonPaletteGroupItem getPaletteItemById(String id){
		List<BpmnCommonPaletteGroup> bpmnPaletteGroups = getBpmnPaletteGroups();
		for (BpmnCommonPaletteGroup bpmnPaletteGroup : bpmnPaletteGroups) {
			BpmnCommonPaletteGroupItem paletteItem = bpmnPaletteGroup.getPaletteItem(id);
			if(paletteItem != null)
				return paletteItem;
		}
		return null;
	}
	
	public List<BpmnCommonPaletteGroupItem> getPaletteToolItemByType(EClass type, EClass extType, boolean includeVisibleOnly){
		List<BpmnCommonPaletteGroupItem> result = new ArrayList<BpmnCommonPaletteGroupItem>();
		if(type != null){
			List<BpmnCommonPaletteGroup> bpmnPaletteGroups = getBpmnPaletteGroups(!includeVisibleOnly);
			for (BpmnCommonPaletteGroup bpmnPaletteGroup : bpmnPaletteGroups) {
				List<BpmnCommonPaletteGroupItem> paletteItems = bpmnPaletteGroup.getPaletteItems(!includeVisibleOnly);
				for (BpmnCommonPaletteGroupItem bpmnPaletteGroupItem : paletteItems) {
					BpmnCommonPaletteGroupItemType itemType = bpmnPaletteGroupItem.getItemType();
					if(itemType.getType() == BpmnCommonPaletteGroupItemType.EMF_TYPE){
						BpmnCommonPaletteGroupEmfItemType emfType =(BpmnCommonPaletteGroupEmfItemType) itemType;
						EClass emfTypeClass = BpmnMetaModel.getInstance().getEClass(emfType.getEmfType());
						EClass extendedType =  BpmnMetaModel.getInstance().getEClass(emfType.getExtendedType());
						if( emfTypeClass!= null && type.getName().equals(emfTypeClass.getName()) ){
							if(extType != null && extendedType != null){
								if(extType.getName().equals(extendedType.getName()))
									result.add(bpmnPaletteGroupItem);
							}
							else if(extType == null && extendedType == null)
								result.add(bpmnPaletteGroupItem);
						}
						
					}
				}
				
			}
		}
		
		return result;
	}
	
	public List<BpmnCommonPaletteGroupItem> getPaletteToolItemByType(EClass type, EClass extType){
		return getPaletteToolItemByType(type, extType, false);
	}
	
	public List<BpmnCommonPaletteGroupItem> getPaletteToolBySubType(EClass classType, boolean includeInvisible, EClass ...ignoreTypeList ) {
		Set<EClass> ignoreList  = new HashSet<EClass>();
		if(ignoreTypeList != null &&  ignoreTypeList.length > 0) {
			for (int i = 0; i < ignoreTypeList.length; i++) {
				ignoreList.add(ignoreTypeList[i]);
			}
		}
		List<BpmnCommonPaletteGroupItem> paletteTools = new ArrayList<BpmnCommonPaletteGroupItem>();
		if (classType != null) {
			List<BpmnCommonPaletteGroup> paletteToolsets = getBpmnPaletteGroups(includeInvisible);
			for (BpmnCommonPaletteGroup paletteToolSet : paletteToolsets) {
				List<BpmnCommonPaletteGroupItem> itemRefs = paletteToolSet.getPaletteItems(includeInvisible);
				for (BpmnCommonPaletteGroupItem paletteItem : itemRefs) {
					 BpmnCommonPaletteGroupItemType itemType = paletteItem.getItemType();
					if ((itemType.getType() != BpmnCommonPaletteGroupItemType.EMF_TYPE))
						continue;

					ExpandedName spec = ((BpmnCommonPaletteGroupEmfItemType)itemType).getEmfType();
					EClass cType = BpmnMetaModel.getInstance().getEClass(spec);

					if (cType != null && classType.isSuperTypeOf(cType) && !ignoreList.contains(cType)) {
						paletteTools.add(paletteItem);
					}
				}
			}
		}
		return paletteTools;
	}

}
