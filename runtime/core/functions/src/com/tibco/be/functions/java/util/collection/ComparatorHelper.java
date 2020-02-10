package com.tibco.be.functions.java.util.collection;
/**
* Generated Code from String Template.
* Date : Sep 11, 2012
*/

import java.util.*;

import static com.tibco.be.model.functions.FunctionDomain.*;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Collections.Comparator",
        synopsis = "Comparator functions")
public class ComparatorHelper {
	
	@com.tibco.be.model.functions.BEFunction(
	  	    name = "compare",
	  	    synopsis = "Compares its two arguments for order.",
	  	    signature = "int compare ( Object comparator, Object o1, Object o2   )",
	  	    params = {
	  	    	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "comparator", type = "Object", desc = "The Comparator object. ") ,
	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "o1", type = "Object", desc = "The first object to be compared. ") ,
	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "o2", type = "Object", desc = "The second object to be compared. ") 
	  	        
	  	    },
	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "A negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second."),
	  	    version = "5.1.1",
	  	    see = "",
	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
	  	    description = "Compares its two arguments for order.",
	  	    cautions = "none",
	  	    fndomain = {ACTION},
	  	    example = ""
	  	)
	  	public static int compare (Object comparator, Object o1  ,Object o2   ) {
		  	if(comparator != null && comparator instanceof Comparator) {
		  		return ((Comparator)comparator).compare(o1, o2);
		  	}
		  	throw new RuntimeException("Invalid Comparator specified");
	  	} 
	
	
	@com.tibco.be.model.functions.BEFunction(
	  	    name = "createComparator",
	  	    synopsis = "Creates instance of user specified Comparator implementation.",
	  	    signature = "Object createSet ( String className   )",
	  	    params = {
	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "className", type = "String", desc = "Specify the className that implements the Comparator interface  ") 
	  	        
	  	    },
	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of user specified Comparator implementation"),
	  	    version = "5.1.1",
	  	    see = "",
	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
	  	    description = "Creates instance of user specified Comparator implementation.",
	  	    cautions = "none",
	  	    fndomain = {ACTION},
	  	    example = ""
	  	)
	  	public static Object createComparator ( String className   ) {
	  	
	  		Exception ex = null;
	        try {
	            Class<Comparator> cls = (Class<Comparator>) Class.forName(className);
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


}
