package com.tibco.be.functions.java.util.collection;
import java.util.*;

import static com.tibco.be.model.functions.FunctionDomain.*;

/**
* Generated Code from String Template.
* Date : 11 Sep, 2012
* @author majha
*/



@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Collections.Iterator",
        synopsis = "Utility Functions for Java.util.Iterator ")
public class IteratorHelper {

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "hasNext",
  	    synopsis = "Returns true, if the iteration has more elements.",
  	    signature = "boolean hasNext (Object iterator)",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "iterator", type = "Object", desc = "The iterator object ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns true if the iterator has more elements"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns true, if the iteration has more elements.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	
  	public static boolean hasNext ( Object iterator ) {
  		if (iterator instanceof Iterator) {
  			return ((Iterator)iterator).hasNext();
  		}
  		throw new RuntimeException("Invalid iterator parameter specified");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "next",
  	    synopsis = "Returns the next element in the iteration",
  	    signature = "Object next (Object iterator)",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "iterator", type = "Object", desc = "The iterator object ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Returns the next element in the iteration"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the next element in the iteration",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object next ( Object iterator ) {
  		if (iterator instanceof Iterator) {
  			return ((Iterator)iterator).next();
  		}
  		throw new RuntimeException("Invalid iterator parameter specified");
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "remove",
  	    synopsis = "Removes from the underlying collection the last element returned by the iterator.",
  	    signature = "void remove (Object iterator)",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "iterator", type = "Object", desc = "The iterator object") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Removes from the underlying collection the last element returned by the iterator.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static void remove ( Object iterator   ) {
  		if (iterator instanceof Iterator) {
  			((Iterator)iterator).remove();
  			return;
  		}
  		throw new RuntimeException("Invalid iterator parameter specified");
  	} 

}
