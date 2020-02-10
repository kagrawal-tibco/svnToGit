package com.tibco.cep.decision.tree.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.tree.common.model.DecisionTree;
import com.tibco.cep.decision.tree.core.DecisionTreeCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/*
@author ssailapp
@date Nov 1, 2011
 */

public class DecisionTreeCoreUtil {

	public static DecisionTree parseFile(IFile file) {
		String filePath = file.getLocation().toString();
		try {
			if (filePath == null || new File(filePath).length() == 0)
				return null;
			FileInputStream fis = new FileInputStream(filePath);
			return loadModel(fis);
		} catch (FileNotFoundException e) {
			DecisionTreeCorePlugin.log(e);
			e.printStackTrace();
		} catch (Exception e) {
			DecisionTreeCorePlugin.log(e);
			e.printStackTrace();
		}
		return null;
	}

	public static DecisionTree loadModel(InputStream is) throws Exception {	
		EObject eObject = CommonIndexUtils.deserializeEObject(is);
		if (eObject instanceof DecisionTree) {
			return (DecisionTree)eObject;
		}
		return null;
	}	
}
