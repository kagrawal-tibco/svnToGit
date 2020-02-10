package com.tibco.be.functions.java.util.collection;

/**
* Generated Code from String Template.
* Date : 11 Sep, 2012
*/

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.tibco.be.model.functions.FunctionDomain.*;

/**
* Generated Code from String Template.
* Date : 11 Sep, 2012
* @author majha
*/
@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Collections.Map",
        synopsis = "Map functions")
public class MapHelper {
	protected static ConcurrentHashMap<String, Map<Object, Object>> createdMaps;
	static {
		synchronized (MapHelper.class) {
			 createdMaps = new ConcurrentHashMap<String, Map<Object, Object>>();
		}
	}
  	@com.tibco.be.model.functions.BEFunction(
  	    name = "clear",
  	    synopsis = "Removes all of the mappings from this map.",
  	    signature = "void clear ( Object map )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The Map object ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Removes all of the mappings from this map.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static void clear ( Object map ) {
  		if(map != null && map instanceof Map){
  			((Map)map).clear();
  			return;
  		}
  		throw new RuntimeException("Invalid Map parameter specified");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "containsKey",
  	    synopsis = "Returns true, if this map contains a mapping for the specified.",
  	    signature = "boolean containsKey ( Object map, Object key )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The Map object "), 
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "Object", desc = "Key whose presence in this map is to be tested ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true, If this map contains a mapping for the specified  key"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = " Returns true, if this map contains a mapping for the specified.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static boolean containsKey ( Object map, Object key ) {
  		if(map != null && map instanceof Map){
  			return ((Map)map).containsKey(key);
  		}
  		throw new RuntimeException("Invalid Map parameter specified");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "containsValue",
  	    synopsis = "Returns true, if this map maps one or more keys to the specified value.",
  	    signature = "boolean containsValue ( Object map, Object value )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The Map object "), 
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "value whose presence in this map is to be tested ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true, If this map maps one or more keys to the specified value"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns true, if this map maps one or more keys to the specified value.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static boolean containsValue ( Object map, Object value ) {
  		if(map != null && map instanceof Map){
  			return ((Map)map).containsValue(value);
  		}
  		throw new RuntimeException("Invalid Map parameter specified");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "createHashMap",
  	    synopsis = "Create and return instance of HashMap.<br/>The hashmap returned by this function is not thread safe hence any updates if using a multi-threaded RETE should be done carefully.",
  	    signature = "Object createHashMap ( )",
  	    params = {
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of HashMap"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Create and return instance of HashMap.<br/>The hashmap returned by this function is not thread safe hence any updates if using a multi-threaded RETE should be done carefully.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object createHashMap ( ) {
  	
  		return new HashMap();
  	}
  	
  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "createHashMapWithId",
  	  	    synopsis = "Create and return instance of HashMap.",
  	  	    signature = "Object createHashMapWithId ( String mapId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapId", type = "String", desc = "The String ID of the map to create ") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of HashMap"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Create and return instance of HashMap for the given mapID. If the map already exists, return the existing Map.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object createHashMapWithId (String mapId ) {
  			Map<Object, Object> map = createdMaps.get(mapId);
  			if(map == null){
  				map = new HashMap<Object, Object>();
  				createdMaps.put(mapId, map);
  			}
  	  		return map;
  	  	}

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "createLinkedHashMap",
  	    synopsis = "Create and return instance of LinkedHashMap.",
  	    signature = "Object createLinkedHashMap ( )",
  	    params = {
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of LinkedHashMap"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Create and return instance of LinkedHashMap.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object createLinkedHashMap ( ) {
  	
  		return new LinkedHashMap();
  	} 
  	
  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "createLinkedHashMapWithId",
  	  	    synopsis = "Create and return instance of LinkedHashMap.",
  	  	    signature = "Object createLinkedHashMapWithId ( String mapId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapId", type = "String", desc = "The String ID of the map to create ") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of LinkedHashMap"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Create and return instance of LinkedHashMap for the given mapID. If the map already exists, return the existing Map.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object createLinkedHashMapWithId (String mapId ) {
  			Map<Object, Object> map = createdMaps.get(mapId);
  			if(map == null){
  				map = new LinkedHashMap<Object, Object>();
  				createdMaps.put(mapId, map);
  			}
  	  		return map;
  	  	}

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "createMap",
  	    synopsis = "Creates user specified Map implementation",
  	    signature = "Object createMap ( String className )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "className", type = "String", desc = "Specify the className that implements the Map interface ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of user specified Map implementation"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Creates user specified Map implementation",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object createMap ( String className ) {
  	
  		 Exception ex = null;
         try {
             Class<Map> cls = (Class<Map>) Class.forName(className);
             return cls.newInstance();
         }
         catch (ClassNotFoundException e) {
             ex = e;
         } catch (InstantiationException e) {
             ex = e;
         } catch (IllegalAccessException e) {
             ex = e;
         }
         throw new RuntimeException(ex);
  	} 
  	
  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "createMapWithId",
  	  	    synopsis = "Creates user specified Map implementation.",
  	  	    signature = "Object createMapWithId ( String mapId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "className", type = "String", desc = "Specify the className that implements the Map interface"), 
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapId", type = "String", desc = "The String ID of the map to create ") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of user specified Map implementation"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Create and return instance of user specified Map implementation for the given mapID. If the map already exists, return the existing Map.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object createMapWithId (String className, String mapId ) {
  			Map<Object, Object> map = createdMaps.get(mapId);
  			if(map == null){
  				 Exception ex = null;
  		         try {
  		             Class<Map> cls = (Class<Map>) Class.forName(className);
  		             map = cls.newInstance();
  		         }
  		         catch (ClassNotFoundException e) {
  		             ex = e;
  		         } catch (InstantiationException e) {
  		             ex = e;
  		         } catch (IllegalAccessException e) {
  		             ex = e;
  		         }
  		         if(ex != null)
  		        	 throw new RuntimeException(ex);
  				createdMaps.put(mapId, map);
  			}
  	  		return map;
  	  	}

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "createTreeMap",
  	    synopsis = "Create and return instance of TreeMap.",
  	    signature = "Object createTreeMap ( )",
  	    params = {
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of TreeMap"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Create and return instance of TreeMap.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object createTreeMap ( ) {
  	
  		return new TreeMap();
  	}
  	
  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "createTreeMapWithId",
  	  	    synopsis = "Create and return instance of TreeMap.",
  	  	    signature = "Object createTreeMapWithId ( String mapId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapId", type = "String", desc = "The String ID of the map to create ") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of TreeMap"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Create and return instance of TreeMap for the given mapID. If the map already exists, return the existing Map.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object createTreeMapWithId (String mapId ) {
  			Map<Object, Object> map = createdMaps.get(mapId);
  			if(map == null){
  				map = new TreeMap<Object, Object>();
  				createdMaps.put(mapId, map);
  			}
  	  		return map;
  	  	}

  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "deleteMap",
  	  	    synopsis = "Deletes the instance of map for the given mapId.<br/>This function can not be used if the collection is created with createMap().",
  	  	    signature = "Object deleteMap ( String mapId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapId", type = "String", desc = "The String ID of the map to delete ") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = " "),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Deletes the instance of Map  for the given mapId.<br/>This function can not be used if the collection is created with createMap().",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static void deleteMap (String mapId ) {
  			createdMaps.remove(mapId);
  	  	}

	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "getMap",
  	  	    synopsis = "Returns the instance of map for the given mapId.",
  	  	    signature = "Object getMap ( String mapId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapId", type = "String", desc = "The String ID of the map") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = " Returns the instance of Map for the given mapId"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Returns the instance of Map  for the given mapId.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object getMap (String mapId ) {
  			return createdMaps.get(mapId);
  	  	}
	
  	@com.tibco.be.model.functions.BEFunction(
  	    name = "entrySet",
  	    synopsis = "Returns a view of the mappings contained in this map.",
  	    signature = "Object entrySet ( Object map )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The Map object ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "A set view of the mappings contained in this map"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns a view of the mappings contained in this map.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object entrySet ( Object map ) {
  		if(map != null && map instanceof Map){
  			return ((Map)map).entrySet();
  		}
  		throw new RuntimeException("Invalid Map parameter specified");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "get",
  	    synopsis = "Returns the value to which the specified key is mapped.",
  	    signature = "Object get ( Object map, Object key )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The Map object "), 
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "Object", desc = "The key whose associated value is to be returned ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the value to which the specified key is mapped.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object get ( Object map, Object key ) {
  		if(map != null && map instanceof Map){
  			return ((Map)map).get(key);
  		}
  		throw new RuntimeException("Invalid Map parameter specified");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "isEmpty",
  	    synopsis = "Returns true, if this map contains no key-value mappings.",
  	    signature = "boolean isEmpty ( Object map )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The Map object ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true, If this map contains no key-value mappings"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns true, if this map contains no key-value mappings",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static boolean isEmpty ( Object map ) {
  		if(map != null && map instanceof Map){
  			return ((Map)map).isEmpty();
  		}
  		throw new RuntimeException("Invalid Map parameter specified");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "keySet",
  	    synopsis = "Returns a  view of the keys contained in this map.",
  	    signature = "Object keySet ( Object map )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The Map object ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "A set view of the keys contained in this map"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns a view of the keys contained in this map.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object keySet ( Object map ) {
  		if(map != null && map instanceof Map){
  			return ((Map)map).keySet();
  		}
  		throw new RuntimeException("Invalid Map parameter specified");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "put",
  	    synopsis = "Associates the specified value with the specified key in this map.",
  	    signature = "Object put ( Object map, Object key, Object value )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The Map object "), 
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "Object", desc = "key with which the specified value is to be associated "), 
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "Value to be associated with the specified key ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value associated with key, \n or null if there was no mapping for key."),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Associates the specified value with the specified key in this map.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object put ( Object map, Object key, Object value ) {
  		if(map != null && map instanceof Map){
  			return ((Map)map).put(key, value);
  		}
  		throw new RuntimeException("Invalid Map parameter specified");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "putAll",
  	    synopsis = "Copies all of the mappings from the specified map to this map.",
  	    signature = "void putAll ( Object map, Object mapToBeAdded )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The Map object "), 
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapToBeAdded", type = "Object", desc = "Mappings to be stored in this map ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Copies all of the mappings from the specified map to this map.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static void putAll ( Object map, Object mapToBeAdded ) {
  		if(map != null && map instanceof Map && mapToBeAdded instanceof Map){
  			((Map)map).putAll((Map)mapToBeAdded);
  			return;
  		}
  		throw new RuntimeException("Invalid Map parameter specified");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "remove",
  	    synopsis = "Removes the mapping for a key from this map if it is present.",
  	    signature = "Object remove ( Object map, Object key )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The map object "), 
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "Object", desc = "Key whose mapping is to be removed from the map ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value associated with key, or \n null if there was no mapping for key."),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Removes the mapping for a key from this map if it is present.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object remove ( Object map, Object key) {
  		if(map != null && map instanceof Map ){
  			return ((Map)map).remove(key);
  		}
  		throw new RuntimeException("Invalid Map parameter specified");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "size",
  	    synopsis = "Returns the number of key-value mappings in this map.",
  	    signature = "int size ( Object map )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The map object ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The number of key-value mappings in this map"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "the number of key-value mappings in this map.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static int size ( Object map ) {
  		if(map != null && map instanceof Map ){
  			return ((Map)map).size();
  		}
  		throw new RuntimeException("Invalid Map parameter specified");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "values",
  	    synopsis = "Returns a view of the values contained in this map.",
  	    signature = "Object values ( Object map )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The Map object ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "A collection view of the values contained in this map"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns a  view of the values contained in this map.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object values ( Object map ) {
  		if(map != null && map instanceof Map ){
  			return ((Map)map).values();
  		}
  		throw new RuntimeException("Invalid Map parameter specified");
  	} 

}
