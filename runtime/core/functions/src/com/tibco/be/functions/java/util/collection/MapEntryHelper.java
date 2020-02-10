package com.tibco.be.functions.java.util.collection;



import java.util.Map.Entry;

import static com.tibco.be.model.functions.FunctionDomain.*;

/**
* Generated Code from String Template.
* Date : 11 Sep, 2012
* @author majha
*/

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Collections.Map.Entry",
        synopsis = "Utility Functions for java.util.Map.Entry")
public class MapEntryHelper {

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "getKey",
  	    synopsis = "Returns the key corresponding to this entry.",
  	    signature = "Object getKey ( Object entry)",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entry", type = "Object", desc = "The Entry object ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "the key corresponding to this entry"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the key corresponding to this entry..",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
	public static Object getKey(Object entry) {
		if (entry instanceof Entry) {
			return ((Entry) entry).getKey();
		} else
			return null;
	}

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "getValue",
  	    synopsis = "Returns the value corresponding to this entry.",
  	    signature = "Object getValue ( Object entry)",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entry", type = "Object", desc = "The Entry object ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "the value corresponding to this entry"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the value corresponding to this entry.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object getValue ( Object entry   ) {
  		if (entry instanceof Entry) {
			return ((Entry) entry).getValue();
		} else
			return null;
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "setValue",
  	    synopsis = "Replaces the value corresponding to this entry with the specified value.",
  	    signature = "Object setValue ( Object entry  ,Object value   )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entry", type = "Object", desc = "The Entry object ") ,
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "new value to be stored in this entry ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "old value corresponding to the entry"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Replaces the value corresponding to this entry with the specified value.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object setValue ( Object entry  ,Object value   ) {
  		if (entry instanceof Entry) {
			return ((Entry) entry).setValue(value);
		} else
			return null;
  	} 

}
