package com.tibco.be.functions.java.util.collection;

/**
* Generated Code from String Template.
* Date : Sep 11, 2012
*/

import java.util.*;

import static com.tibco.be.model.functions.FunctionDomain.*;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Collections.SortedMap",
        synopsis = "SortedMap functions")
public class SortedMapHelper {

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "comparator",
  	    synopsis = "Returns the comparator used to order the keys in this map, or null if this map uses the Comparable natural ordering of its keys.",
  	    signature = "Object comparator ( Object map )",
  	    params = {
  	    		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "Instance of SortedMap ") 
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "the comparator used to order the keys in this map, or <tt>null</tt> if this map uses the natural ordering, of its keys"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the comparator used to order the keys in this map, or null if this map uses the Comparable natural ordering of its keys.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object comparator (Object  map) {
  		if(map != null && map instanceof SortedMap){
  			return ((SortedMap)map).comparator();
  		}
  		throw new RuntimeException("Invalid SortedMap parameter");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "firstKey",
  	    synopsis = "Returns the first (lowest) key currently in this map.",
  	    signature = "Object firstKey ( Object map )",
  	    params = {
  	    		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "Instance of SortedMap ")
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The first (lowest) key currently in this map"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the first (lowest) key currently in this map.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object firstKey (Object map  ) {
  	
  		if(map != null && map instanceof SortedMap){
  			return ((SortedMap)map).firstKey();
  		}
  		throw new RuntimeException("Invalid SortedMap parameter");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "headMap",
  	    synopsis = "Returns a view of the portion of this map whose keys are strictly less than toKey.",
  	    signature = "Object headMap ( Object map, Object toKey   )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "Instance of SortedMap "),
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "toKey", type = "Object", desc = "High endpoint (exclusive) of the keys in the returned map ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of Map, a view of the portion of this map whose keys are strictly\nless than toKey."),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns a view of the portion of this map whose keys are strictly less than toKey.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object headMap (Object map, Object toKey   ) {
  	
  		if(map != null && map instanceof SortedMap){
  			return ((SortedMap)map).headMap(toKey);
  		}
  		throw new RuntimeException("Invalid SortedMap parameter");
  	} 


  	@com.tibco.be.model.functions.BEFunction(
  	    name = "lastKey",
  	    synopsis = "Returns the last (highest) key currently in this map.",
  	    signature = "Object lastKey (Object map  )",
  	    params = {
  	    		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "Instance of SortedMap ")
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The last (highest) key currently in this map."),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the last (highest) key currently in this map.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object lastKey (Object map  ) {
  		if(map != null && map instanceof SortedMap){
  			return ((SortedMap)map).lastKey();
  		}
  		throw new RuntimeException("Invalid SortedMap parameter");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "subMap",
  	    synopsis = "Returns a view of the portion of this map whose keys range from fromKey, inclusive, to toKey, exclusive. (If fromKey and toKey are equal, the returned map is empty.) ",
  	    signature = "Object subMap ( Object map, Object fromKey  ,Object toKey   )",
  	    params = {
  	    		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "Instance of SortedMap "),
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fromKey", type = "Object", desc = "Low endpoint (inclusive) of the keys in the returned map ") ,
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "toKey", type = "Object", desc = "High endpoint (exclusive) of the keys in the returned map ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of SortedSet, a view of the portion of this map whose keys range from\nfromKey, inclusive, to toKey, exclusive"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns a view of the portion of this map whose keys range from fromKey, inclusive, to toKey, exclusive. (If fromKey and toKey are equal, the returned map is empty.) ",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object subMap (Object map, Object fromKey  ,Object toKey   ) {
  	
  		if(map != null && map instanceof SortedMap){
  			return ((SortedMap)map).subMap(fromKey,toKey);
  		}
  		throw new RuntimeException("Invalid SortedMap parameter");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "tailMap",
  	    synopsis = " Returns a view of the portion of this map whose keys are greater than or equal to fromKey.",
  	    signature = "Object tailMap ( Object map, Object fromKey   )",
  	    params = {
  	    		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "Instance of SortedMap "),
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fromKey", type = "Object", desc = "Low endpoint (inclusive) of the keys in the returned map  ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of SortedSet, a view of the portion of this map whose keys are greater\nthan or equal to fromKey"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns a view of the portion of this map whose keys are greater than or equal to fromKey.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object tailMap (Object map, Object fromKey   ) {
  	
  		if(map != null && map instanceof SortedMap){
  			return ((SortedMap)map).tailMap(fromKey);
  		}
  		throw new RuntimeException("Invalid SortedMap parameter");
  	} 



  	

}
