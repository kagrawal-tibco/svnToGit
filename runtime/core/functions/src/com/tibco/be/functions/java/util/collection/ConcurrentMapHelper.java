package com.tibco.be.functions.java.util.collection;

/**
* Generated Code from String Template.
* Date : 11 Sep, 2012
*/

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.tibco.be.model.functions.FunctionDomain.*;

/**
* Generated Code from String Template.
* Date : 11 Sep, 2012
* @author majha
*/
@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Collections.Map.Concurrent",
        synopsis = "Concurrent Map functions")
public class ConcurrentMapHelper extends MapHelper{
	
	
  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "createConcurrentHashMap",
  	  	    synopsis = "Create and return instance of ConcurrentHashMap.<br/>This function returns a thread safe hash map.",
  	  	    signature = "Object createConcurrentHashMap (  )",
  	  	    params = {
  	  	        
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of ConcurrentHashMap"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Create and return instance of ConcurrentHashMap.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object createConcurrentHashMap (  ) {
  	  	
  	  		return new ConcurrentHashMap();
  	  	}
  	
  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "createHashMapWithId",
  	  	    synopsis = "Create and return instance of ConcurrentHashMap.",
  	  	    signature = "Object createHashMapWithId ( String mapId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapId", type = "String", desc = "The String ID of the concurrent hash map to create ") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of ConcurrentHashMap"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Create and return instance of ConcurrentHashMap for the given mapID. If the map already exists, return the existing Map.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object createConcurrentHashMapWithId (String mapId ) {
  			Map<Object, Object> map = createdMaps.get(mapId);
  			if(map == null){
  				map = new ConcurrentHashMap<Object, Object>();
  				createdMaps.put(mapId, map);
  			}
  	  		return map;
  	  	}

  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "putIfAbsent",
  	  	    synopsis = "If the specified key is not already associated with a value, associate it with the given value. The action is performed atomically.",
  	  	    signature = "Object putIfAbsent ( Object map  ,Object key ,Object value  )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The concurrent map object ") ,
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "Object", desc = "key with which the specified value is to be associated. ") ,
  	  	    	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "value to be associated with the specified key. ") 
  	  	        
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "previous value associated with specified key, or null if there was no mapping for key. A null return can also indicate that the map previously associated null with the specified key, if the implementation supports null values."),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = " If the specified key is not already associated with a value, associate it with the given value. The action is performed atomically.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object putIfAbsent ( Object map  ,Object key, Object value) {
  	  		if(map != null && map instanceof ConcurrentMap){
  	  			return ((ConcurrentMap)map).putIfAbsent(key, value);
  	  		}
  	  		throw new RuntimeException("Invalid Map parameter specified");
  	  	} 
  	
  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "remove",
  	  	    synopsis = "Remove entry for key only if currently mapped to given value. The action is performed atomically.",
  	  	    signature = "boolean remove ( Object map  ,Object key ,Object value  )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The concurrent map object ") ,
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "Object", desc = "key with which the specified value is to be associated. ") ,
  	  	    	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "value to be associated with the specified key. ") 
  	  	        
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the value was removed, false otherwise ."),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = " Remove entry for key only if currently mapped to given value. The action is performed atomically.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static boolean remove ( Object map  ,Object key, Object value) {
  	  		if(map != null && map instanceof ConcurrentMap){
  	  			return ((ConcurrentMap)map).remove(key, value);
  	  		}
  	  		throw new RuntimeException("Invalid Map parameter specified");
  	  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "replaceIfEqualToGivenValue",
  	  	    synopsis = "Replace entry for key only if currently mapped to given value. The action is performed atomically.",
  	  	    signature = "boolean replaceIfEqualToGivenValue ( Object map  ,Object key ,Object oldValue, Object newValue  )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The concurrent map object ") ,
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "Object", desc = "key with which the specified value is to be associated. ") ,
  	  	    	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "oldvalue", type = "Object", desc = "value expected to be associated with the specified key. "), 
  	  	    	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "newValue", type = "Object", desc = "value to be associated with the specified key. ") 
  	  	        
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the value was replaced, false otherwise ."),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = " Replace entry for key only if currently mapped to given value. The action is performed atomically.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static boolean replaceIfEqualToGivenValue ( Object map  ,Object key, Object oldValue, Object newValue) {
  	  		if(map != null && map instanceof ConcurrentMap){
  	  			return ((ConcurrentMap)map).replace(key, oldValue, newValue);
  	  		}
  	  		throw new RuntimeException("Invalid Map parameter specified");
  	  	} 
  	
  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "replace",
  	  	    synopsis = "Replace entry for key only if currently mapped to some value. The action is performed atomically.",
  	  	    signature = "Object replace ( Object map  ,Object key ,Object value  )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The concurrent map object ") ,
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "Object", desc = "key with which the specified value is to be associated. ") ,
  	  	    	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "value to be associated with the specified key. ") 
  	  	        
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Returns an object which is the previously associated value with the key<br/>If the key doesnot exist then returns null"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = " Replace entry for key only if currently mapped to some value. The action is performed atomically.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object replace ( Object map  ,Object key, Object value) {
  	  		if(map != null && map instanceof ConcurrentMap){
  	  			return ((ConcurrentMap)map).replace(key, value);
  	  		}
  	  		throw new RuntimeException("Invalid Map parameter specified");
  	  	} 
}
