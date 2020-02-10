package com.tibco.cep.repo;

import org.eclipse.emf.ecore.EObject;


public class TnsCacheLoader {
	private static ThreadLocal<BETargetNamespaceCache> tnsLocal = new ThreadLocal<BETargetNamespaceCache>();
	private static ThreadLocal<EObject> indexLocal = new ThreadLocal<EObject>();
	private static ThreadLocal<BEProject> projectLocal = new ThreadLocal<BEProject>();
	
	
	public static BETargetNamespaceCache getTnsCache() {
		 return tnsLocal.get();
	}
	
	public static void setTnsCache( BETargetNamespaceCache c){
		tnsLocal.set(c);
	}
	
	public static void removeTnsCache() {
		tnsLocal.remove();
	}
	
	public static EObject getIndex() {
		return indexLocal.get();
	}
	
	public static void setIndex(EObject p) {
		indexLocal.set(p);
	}
	
	public static void removeIndex() {
		indexLocal.remove();
	}
	
	public static BEProject getProject() {
		return projectLocal.get();
	}
	
	
	public static void setProject(BEProject p) {
		projectLocal.set(p);
	}
	
	public static void removeProject() {
		projectLocal.remove();
	}
	
	

}
