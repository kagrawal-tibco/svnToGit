package com.tibco.rta.common.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.model.impl.DimensionHierarchyImpl;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 7/11/12
 * Time: 12:11 PM
 * Runtime registry to lookup model objects.
 */
public class ModelRegistry {
    
    private Map<String, RtaSchema> registryNodeMap = new ConcurrentHashMap<String, RtaSchema>();

    public static ModelRegistry INSTANCE = new ModelRegistry();
    
    public static final String SPM_2_0_SCHEMANAME = "BETEA";
    public static final String SPM_2_0_ALERTS_CUBENAME = "SystemAlerts";
    public static final String SPM_2_0_ALERTS_DIMHRNAME = "Alerts";
    
    //key is schema name, value is a map, where key is schemaname+assetname and value is the asset hierarchy
	protected Map<String, Map<String,DimensionHierarchy>> schemaToAssetDhMap = new ConcurrentHashMap<String, Map<String,DimensionHierarchy>>();
	
	//key is the FQN of the hierarchy and value is a map,  where key is schemaname+assetname and value is the asset hierarchy
	protected Map<String, Map<String,DimensionHierarchy>> dhToAssetDhMap = new ConcurrentHashMap<String, Map<String,DimensionHierarchy>>();

    private ModelRegistry() {}

   /**
     *
     * @param schema
 * @throws Exception 
     */
    public void put(RtaSchema schema) throws Exception {
        String schemaName = schema.getName();

        RtaSchema rootNode = registryNodeMap.get(schemaName);
        if (rootNode == null) {
            registryNodeMap.put(schemaName, schema);
            loadAllAssetDhWithNoTimeDimensions(schema);
            loadDhToAssetDhMap(schema);
        } else {
        	throw new DuplicateSchemaElementException(String.format("%s already registered. Cannot register it again", schema.getName()));
        }
    }
    

    /**
     *
     * @return List<>
     */
    public List<RtaSchema> getAllRegistryEntries() {
        return new ArrayList<RtaSchema>(registryNodeMap.values());
    }
    
    /**
     *
     * @return RtaSchema
     */
    public RtaSchema getRegistryEntry(String schemaName) {
       return registryNodeMap.get(schemaName);
    }

    /**
     *
     * @param schemaName
     */
    public void removeRegistryEntry(String schemaName) {
        registryNodeMap.remove(schemaName);
    }
    
 	
	public Map<String, DimensionHierarchy> getMatchingAssetHierarchies(DimensionHierarchy dh) {
		return dhToAssetDhMap.get(dh.getOwnerSchema().getName()+dh.getOwnerCube().getName()+dh.getName());
	}
 	
 	public Collection<DimensionHierarchy> getAllAssetHierarchies(String schemaName) {
 		return schemaToAssetDhMap.get(schemaName).values();
 	}

 	public DimensionHierarchy getAssetHierarchyForAsset(DimensionHierarchy assetDh) {
		if (!DimensionHierarchyImpl.isAssetHierarchy(assetDh)) {
			return null;
		}
		Map<String, DimensionHierarchy> schemaMap = schemaToAssetDhMap.get(assetDh.getOwnerSchema().getName());
		
		return schemaMap.get(assetDh.getOwnerSchema().getName()+DimensionHierarchyImpl.getAssetName(assetDh));
	}

 	private void loadAllAssetDhWithNoTimeDimensions(RtaSchema schema) {
 		Map<String,DimensionHierarchy> assetDhMap = schemaToAssetDhMap.get(schema.getName());
 		if (assetDhMap == null) {
 			//first time!
 			assetDhMap = new ConcurrentHashMap<String, DimensionHierarchy>();
 			schemaToAssetDhMap.put(schema.getName(), assetDhMap);
 			for (Cube c : schema.getCubes()) {
 				outer : for (DimensionHierarchy dh : c.getDimensionHierarchies()) {
 					if (DimensionHierarchyImpl.isAssetHierarchy(dh)) {
 						for (Dimension d : dh.getDimensions()) {
 							if (d instanceof TimeDimension) {
 								continue outer;
 							}
 						}
 						assetDhMap.put(dh.getOwnerSchema().getName()+DimensionHierarchyImpl.getAssetName(dh),dh);
 					}
 				}
 			}
 		}
 	}
 	
	private void loadDhToAssetDhMap(RtaSchema schema) {
		Collection<DimensionHierarchy> allAssetDhList = getAllAssetHierarchies(schema.getName());

		for (Cube c : schema.getCubes()) {
			for (DimensionHierarchy dh : c.getDimensionHierarchies()) {
				if (!DimensionHierarchyImpl.isAssetHierarchy(dh)) {
					Map<String, DimensionHierarchy> assetsForThisDh = dhToAssetDhMap.get(schema.getName()+c.getName()+dh.getName());
					if (assetsForThisDh == null) {
						assetsForThisDh = new ConcurrentHashMap<String, DimensionHierarchy>();
						dhToAssetDhMap.put(schema.getName()+c.getName()+dh.getName(), assetsForThisDh);
					}

					for (DimensionHierarchy assetDh: allAssetDhList) {
						Collection<String> assetAttribList = assetDh.getDimensionAttribNames();
						if (isAssetDimSubsetOf(dh, assetAttribList)) {
							assetsForThisDh.put(schema.getName()+DimensionHierarchyImpl.getAssetName(assetDh), assetDh);
						}
					}
				}
			}
		}
	}
 	
	private boolean isAssetDimSubsetOf(DimensionHierarchy hierarchy2, Collection<String> assetAttribList) {
		// check if ALL asset-attribs are present in this dimension.
		// asset as a attribute.
		boolean toProcess = true;

		for (String attribName : assetAttribList) {
			boolean canProceed = false;
			for (Dimension dim : hierarchy2.getDimensions()) {
				if (dim.getAssociatedAttribute().getName().equals(attribName)) {
					// found a match
					canProceed = true;
					break;
				}
			}
			if (!canProceed) {
				toProcess = false;
				break;
			}
		}
		return toProcess;
	}


}
