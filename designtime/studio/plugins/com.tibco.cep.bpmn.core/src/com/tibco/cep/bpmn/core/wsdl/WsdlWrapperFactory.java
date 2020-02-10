package com.tibco.cep.bpmn.core.wsdl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

/**
 * 
 * @author majha
 *
 */
public class WsdlWrapperFactory {

	private static Map<String, WsdlWrapper> wsdlMap = new ConcurrentHashMap<String, WsdlWrapper>();
	
	public static WsdlWrapper getWsdl(IProject project, String path){
		WsdlWrapper wsdl = wsdlMap.get(path);
		if(wsdl == null){
			wsdl = new WsdlWrapper(project, path);
			wsdlMap.put(path, wsdl);
		}
	
		return wsdl;
	}
	
	
	public static void addWsdl(IProject project, String path){
		getWsdl(project, path);
	}
	
	public static WsdlWrapper getWsdl( String path){
		WsdlWrapper wsdl = wsdlMap.get(path);
		return wsdl;
	}
	
	public static void removeWsdl(IFile file){
		String path = file.getLocation().toPortableString();
		if(!wsdlMap.containsKey(path)){
			path = file.getProjectRelativePath().removeFileExtension().toPortableString();
			if(!path.startsWith("/"))
				path = "/"+path;
		}
		wsdlMap.remove(path);
	}
	
	public static void wsdlChanged(IProject project, IFile file){
		removeWsdl(file);
//		addWsdl(project, path);
	}
}
