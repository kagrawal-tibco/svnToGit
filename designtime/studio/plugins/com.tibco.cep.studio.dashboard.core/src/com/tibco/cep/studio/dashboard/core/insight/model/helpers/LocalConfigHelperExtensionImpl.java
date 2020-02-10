package com.tibco.cep.studio.dashboard.core.insight.model.helpers;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class LocalConfigHelperExtensionImpl extends LocalConfigHelperImpl
		implements ILocalConfigHelperExtension {

	public EObject createChild(LocalConfig parent, 
			LocalPropertyConfig propHelper) throws Exception {
		return null;
//        String[] pathTokens = propHelper.pathTokens;
//
//        if (pathTokens.length == 0)
//            return null;
//
//        // Get the parent object.
//        MDConfig mdParentConfig = (MDConfig) getMdObject(parent, propHelper, true);
//
//        if (mdParentConfig == null) {
//            throw new Exception("Could not create child object by path: " + propHelper.path + " for element " + parent.getName());
//        }
//
//        // Get info about the object we're about to create.
//
//        // child object name
//        String childName = pathTokens[pathTokens.length - 1];
//
//        // child object type
//        String childType = propHelper.type;
//        LocalConfigType localChildConfigType = parent.getLocalSession().getConfigType(childType);
//        if (localChildConfigType == null) {
//            throw new Exception("Could not find local config type for " + childType);
//        }
//        MDConfigType mdChildConfigType = (MDConfigType) localChildConfigType.getMdElement();
//        if (mdChildConfigType == null) {
//            throw new Exception("Could not find mds configtype for " + childType);
//        }
//
//        // type of field in the parent object that points to the child object.
//        List l = mdParentConfig.getConfigType().getConfigTypeInfo(new ArrayList()).getPropertyList();
//        MDConfigFieldType mdFieldType = null;
//        for (int i = 0; i < l.size(); ++i) {
//            MDConfigFieldType f = (MDConfigFieldType) l.get(i);
//            if (f.getName().equals(childName)) {
//                mdFieldType = f;
//                break;
//            }
//        }
//        if (mdFieldType == null)
//            throw new Exception("Could not get mds field type info for " + childName + " in element type " + mdParentConfig.getConfigType().getName());
//
//        // Assume multiplicity == 1.
//
//        // Check if the child object is already there. If so, and if it
//        // is not of the type that we want it to be, we need to remove
//        // it and create a correct one in its place.
//        MDConfigValue mdCV = mdParentConfig.getConfigValueByName(childName);
//
//        //boolean removeExistingChild = true;
//
//        if (mdCV != null) {
//            MDConfig existingChildObject = mdCV.getConfigStructureValue();
//            if (existingChildObject != null) {
//                MDConfigFieldType existingFieldType = mdCV.getConfigFieldType();
//                if (existingFieldType.getName().equals(mdFieldType.getName()) && existingChildObject.getConfigType().getName().equals(mdChildConfigType.getName())) {
//                    return existingChildObject;//removeExistingChild = false;
//                }
//            }
//        }
//
//        //if (removeExistingChild) {
//            mdCV.removeConfigStructureValue();
//            mdParentConfig.removeConfigValue(mdCV);
//            mdCV = mdParentConfig.createConfigValue(childName);
//            mdCV.setConfigFieldType(mdFieldType);
//        //}
//
//        MDConfig mdChildConfig = mdCV.createConfig(mdChildConfigType, childName, localChildConfigType.getMdCategoryInfo(), localChildConfigType.getMdPropertyList(), false);
//        return mdChildConfig;
	}

	/**
	 * @param parent
	 * @param propertyHelper
	 * @return
	 * @throws Exception
	 */
//	public EObject getInstanceReferenceChild(LocalConfig parent, String propertyName) throws Exception {
//		SynProperty prop = (SynProperty) parent.getProperty(propertyName);
//		LocalPropertyConfig propertyHelper = parent.getPropertyHelper(prop);
//        String[] pathTokens = propertyHelper.pathTokens;
//		if (pathTokens.length == 0)
//            return null;
//        else {
//			EObject startEObject = getEObject(parent, propertyHelper, false);
//            String token = pathTokens[pathTokens.length - 1];
//            EStructuralFeature eSFeature = startEObject.eClass().getEStructuralFeature(token);
//            if (eSFeature != null) {
//            	//Check datatype here
//            	return (EObject) startEObject.eGet(eSFeature);
//            }
//        }
//		return null;
//	}

	public void setInstanceReferenceChild(LocalConfig parent, EObject eObject, String propertyName, LocalConfig child) throws Exception {
		SynProperty prop = (SynProperty) parent.getProperty(propertyName);
		LocalPropertyConfig propertyHelper = parent.getPropertyHelper(prop);
        String leaf = propertyHelper.pathTokens[propertyHelper.pathTokens.length - 1];
		setConfigReferenceChild(parent, eObject, leaf, 1, Arrays.asList(new LocalElement[]{child}));
	}

}
