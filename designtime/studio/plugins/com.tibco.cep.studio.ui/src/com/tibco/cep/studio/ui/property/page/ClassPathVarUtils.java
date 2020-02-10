package com.tibco.cep.studio.ui.property.page;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;

public class ClassPathVarUtils {

	/**
	 * @param edit
	 * @return
	 */
	public static IPath[] getExistingBuildPaths(String projectName) {
		final StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
		
		EList<? extends BuildPathEntry> coreList = configuration.getCoreInternalLibEntries();
		EList<? extends BuildPathEntry> custList = configuration.getCustomFunctionLibEntries();
		EList<? extends BuildPathEntry> thirdPartyList = configuration.getThirdpartyLibEntries();
		
		ArrayList<IPath> existingPaths= new ArrayList<IPath>(coreList.size() + custList.size() + thirdPartyList.size());
		
		createBuildPathList(coreList, existingPaths);
		createBuildPathList(custList, existingPaths);
		createBuildPathList(thirdPartyList, existingPaths);

		return existingPaths.toArray(new IPath[existingPaths.size()]);
	}
	
	/**
	 * @param list
	 * @param existingPaths
	 * @param edit
	 */
	public static void createBuildPathList(List<? extends BuildPathEntry> list, 
			                               ArrayList<IPath> existingPaths) {
		for (BuildPathEntry bpe : list) {
			if (bpe.isVar()) {
				IPath path = new Path(bpe.getPath(bpe.isVar()));
				existingPaths.add(path);
			}
		}
	}
	
	/**
	 * @param path
	 * @return
	 */
	public static boolean containsVar(IPath path) {
		String vars[] = StudioCore.getPathVariableNames();
		for (String var : vars) {
			if (path.toOSString().startsWith(var)) {
				return true;
			}
		}
		return false;
	}
}
