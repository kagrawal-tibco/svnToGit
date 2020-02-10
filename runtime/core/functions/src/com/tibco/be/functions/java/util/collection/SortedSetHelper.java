package com.tibco.be.functions.java.util.collection;

/**
* Generated Code from String Template.
* Date : Sep 11, 2012
*/

import java.util.*;

import static com.tibco.be.model.functions.FunctionDomain.*;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Collections.SortedSet",
        synopsis = "SortedSet functions")
public class SortedSetHelper {
	
  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "comparator",
  	  	    synopsis = "Returns the comparator used to order the elements in this set, or null if this map uses the Comparable natural ordering of its elements.",
  	  	    signature = "Object comparator ( Object set )",
  	  	    params = {
  	  	    		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "set", type = "Object", desc = "Instance of SortedSet ") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "the comparator used to order the elements in this set, or <tt>null</tt> if this map uses the natural ordering, of its elements"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object comparator (Object  set) {
  	  		if(set != null && set instanceof SortedSet){
  	  			return ((SortedSet)set).comparator();
  	  		}
  	  		throw new RuntimeException("Invalid SortedSet parameter");
  	  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "first",
  	    synopsis = " Returns the first (lowest) element currently in this set.",
  	    signature = "Object first (Object set  )",
  	    params = {
  	    	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "set", type = "Object", desc = "Instance of SortedSet") 
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The first (lowest) element currently in this set"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = " Returns the first (lowest) element currently in this set.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object first (Object set  ) {
  	
  		if(set != null && set instanceof SortedSet){
  			return ((SortedSet)set).first();
  		}
  		throw new RuntimeException("Invalid SortedSet parameter");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "headSet",
  	    synopsis = " Returns a view of the portion of this set whose elements are strictly less than toElement.",
  	    signature = "Object headSet ( Object set, Object toElement   )",
  	    params = {
  	    	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "set", type = "Object", desc = "Instance of SortedSet"),
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "toElement", type = "Object", desc = " High endpoint (exclusive) of the returned set ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "A view of the portion of this set whose elements are strictly\nless than toElement"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = " Returns a view of the portion of this set whose elements are strictly less than toElement.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object headSet ( Object set,Object toElement   ) {
  	
  		if(set != null && set instanceof SortedSet){
  			return ((SortedSet)set).headSet(toElement);
  		}
  		throw new RuntimeException("Invalid SortedSet parameter");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "last",
  	    synopsis = "Returns the last (highest) element currently in this set. ",
  	    signature = "Object last ( Object set )",
  	    params = {
  	    		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "set", type = "Object", desc = "Instance of SortedSet")
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The last (highest) element currently in this set."),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the last (highest) element currently in this set. ",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object last (Object set  ) {
  	
  		if(set != null && set instanceof SortedSet){
  			return ((SortedSet)set).last();
  		}
  		throw new RuntimeException("Invalid SortedSet parameter");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "subSet",
  	    synopsis = "Returns a view of the portion of this set whose elements range from fromElement, inclusive, to toElement, exclusive. (If fromElement and toElement are equal, the returned set is empty.) ",
  	    signature = "Object subSet ( Object set, Object fromElement  ,Object toElement   )",
  	    params = {
  	    		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "set", type = "Object", desc = "Instance of SortedSet"),
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fromElement", type = "Object", desc = "Low endpoint (inclusive) of the returned set ") ,
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "toElement", type = "Object", desc = "High endpoint (exclusive) of the returned set ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "A view of the portion of this set whose elements range from\nfromElement, inclusive, to toElement, exclusive"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns a view of the portion of this set whose elements range from fromElement, inclusive, to toElement, exclusive. (If fromElement and toElement are equal, the returned set is empty.) ",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object subSet (Object set, Object fromElement  ,Object toElement   ) {
  	
  		if(set != null && set instanceof SortedSet){
  			return ((SortedSet)set).subSet(fromElement,toElement);
  		}
  		throw new RuntimeException("Invalid SortedSet parameter");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "tailSet",
  	    synopsis = " Returns a view of the portion of this set whose elements are greater than or equal to fromElement.",
  	    signature = "Object tailSet ( Object set, Object fromElement   )",
  	    params = {
  	    	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "set", type = "Object", desc = "Instance of SortedSet"),
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fromElement", type = "Object", desc = " Low endpoint (inclusive) of the returned set ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "A view of the portion of this set whose elements are greater\nthan or equal to fromElement"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = " Returns a view of the portion of this set whose elements are greater than or equal to fromElement.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object tailSet ( Object set,Object fromElement  ) {
  	
  		if(set != null && set instanceof SortedSet){
  			return ((SortedSet)set).tailSet(fromElement);
  		}
  		throw new RuntimeException("Invalid SortedSet parameter");
  	}
  	
}
