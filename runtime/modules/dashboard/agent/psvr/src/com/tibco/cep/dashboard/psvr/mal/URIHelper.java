package com.tibco.cep.dashboard.psvr.mal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.ListUtil;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.mal.model.MALBEViewsElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALView;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class URIHelper {
	
	static Logger LOGGER;
	
	private static Map<Class<?>,Method[]> getterMethods = new HashMap<Class<?>, Method[]>();
	
	public static String getURI(MALElement element){
		return generateURIs(element,false,null)[0];		
	}
	
	public static String[] getURI(MALElement element, boolean includeReferences){
		return generateURIs(element,includeReferences,null);
	}
	
	public static String[] getURI(MALElement element, boolean includeReferences,String pathPattern){
		return generateURIs(element,includeReferences,pathPattern);
	}
	
	private static String[] generateURIs(MALElement element, boolean includeReferences, String pathPattern){
		List<String> pathCrumbs = new LinkedList<String>();
		MALElement topLevelElement = traverseUptoTopLevelElement(element, pathCrumbs);
//		Collections.reverse(pathCrumbs);
		String path = ListUtil.joinList(pathCrumbs, "/");			
		if (includeReferences == false || (element instanceof MALView) == true){
			return new String[]{path};			
		}
		Collection<MALElement> references = topLevelElement.getReferences();
		List<String> paths = new LinkedList<String>();
		for (MALElement reference : references) {
			if (reference.getClass().equals(element.getClass()) == false) {
				String[] referenceURIs = getURI(reference,includeReferences);
				for (int i = 0; i < referenceURIs.length; i++) {
					paths.add(referenceURIs[i]+"/"+path);
				}
			}
		}
		if (StringUtil.isEmptyOrBlank(pathPattern) == false){
			ArrayList<String> reducedPaths = new ArrayList<String>(paths.size());
			for (String individualPath : paths) {
				if (individualPath.contains(pathPattern) == true){
					reducedPaths.add(individualPath);
				}
			}
			if (reducedPaths.size() > 0){
				reducedPaths.trimToSize();
				return reducedPaths.toArray(new String[reducedPaths.size()]);
			}
		}
		if (LOGGER.isEnabledFor(Level.DEBUG) == true){
			StringBuilder sb = new StringBuilder("Generated "+paths+" for "+element);
			if (includeReferences == true){
				sb.append(" using references");
			}
			if (StringUtil.isEmptyOrBlank(pathPattern) == false){
				if (includeReferences == true){
					sb.append(" and path pattern hint as "+pathPattern);
				}
				else {
					sb.append(" with path pattern hint as "+pathPattern);
				}
			}
			LOGGER.log(Level.DEBUG,sb.toString());
		}
		return paths.toArray(new String[paths.size()]);
	}	
	
	private static MALElement traverseUptoTopLevelElement(MALElement element, List<String> pathCrumbs) {
		List<MALElement> parentage = element.getParentage();
		for (MALElement parent : parentage) {
			pathCrumbs.add(parent.getName());
		}
		return parentage.get(0); 
//		pathCrumbs.add(element.getName());
//		if (element.isTopLevelElement() == true) {
//			return element;
//		} 
//		MALElement parent = element.getParent();
//		while (parent != null && parent.isTopLevelElement() == false) {
//			pathCrumbs.add(parent.getName());
//			parent = parent.getParent();
//		}
//		if (parent != null){
//			pathCrumbs.add(parent.getName());
//		}
//		return parent;
	}	
	
	public static MALElement resolveURI(String uri, ViewsConfigHelper viewsConfigHelper) {
		if (StringUtil.isEmptyOrBlank(uri) == true || viewsConfigHelper == null) {
			return null;
		}		
		try {
			String[] crumbs = uri.split("/");
			MALElement element = viewsConfigHelper.getViewsConfig();
			element = findStartingPoint(viewsConfigHelper.getViewsConfig(), crumbs[0]);
			if (element != null && crumbs.length > 1){
				for (int i = 1; i < crumbs.length; i++) {
					element = findChild(element, crumbs[i],null);
					if (element == null){
						return null;
					}
				}
			}
			return element;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static MALElement findStartingPoint(MALElement element,String name) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		if (element.getName().equals(name) == true){
			return element;
		}
		List<MALElement> collapsedChildList = new LinkedList<MALElement>();
		MALElement startingPoint = findChild(element, name, collapsedChildList);
		if (startingPoint == null){
			for (MALElement child : collapsedChildList) {
				startingPoint = findStartingPoint(child, name);
				if (startingPoint != null){
					break;
				}
			}
		}
		return startingPoint;
	}
	
	private static MALElement findChild(MALElement element,String name, List<MALElement> collapsedChildList) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Method[] methods = getChildrenGetterMethods(element);
		for (Method method : methods) {
			Object returnedObject = method.invoke(element);
			if (returnedObject != null) {
				Object[] returnedChildren = new Object[]{returnedObject};
				if (returnedObject.getClass().isArray() == true){
					returnedChildren = (Object[]) returnedObject;
				}
				for (int i = 0; i < returnedChildren.length; i++) {
					MALElement childElement = (MALElement) returnedChildren[i]; 
					if (childElement.getName().equals(name) == true){
						return childElement;
					}
					if (collapsedChildList != null) {
						collapsedChildList.add(childElement);
					}
				}
			}
		}
		return null;
	}

	private static Method[] getChildrenGetterMethods(MALElement element){
		Method[] methods = getterMethods.get(element.getClass());
		if (methods == null){
			methods = searchChildrenGetterMethods(element);
			getterMethods.put(element.getClass(), methods);
		}
		return methods;
	}
	
	private static Method[] searchChildrenGetterMethods(MALElement element){
		Map<Class<?>,Method> getterMethods = new HashMap<Class<?>,Method>();
		Class<?> clazz = element.getClass();
		Method[] allMethods = clazz.getMethods();
		for (Method method : allMethods) {
			//is method public and has no parameters
			if (Modifier.isPublic(method.getModifiers()) == true && method.getParameterTypes().length == 0){
				//does the method name start with get ?
				if (method.getName().startsWith("get") == true){
					//get return type
					Class<?> returnType = method.getReturnType();
					//if return type is array, then get array element class 
					if (returnType.isArray() == true){
						returnType = returnType.getComponentType();
					}
					//is return type non primitive and a type other then MALElement or MALBEViewsElement
					if (returnType.isPrimitive() == false && returnType.equals(MALElement.class) == false && returnType.equals(MALBEViewsElement.class) == false){
						//is return type is non primitive and subclass of MALElement
						if (MALElement.class.isAssignableFrom(returnType) == true){
							Method existingMethod = getterMethods.get(returnType);
							//add to map if not existing in it or existing method is returning non array value (handles getDefaultPageSet & getDefaultPage
							if (existingMethod == null || existingMethod.getReturnType().isArray() == false){
								getterMethods.put(returnType, method);
							}
						}
					}
				}
			}
		}
		return getterMethods.values().toArray(new Method[getterMethods.size()]);
	}
}
