package com.tibco.be.functions.java.util.collection;



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
        category = "Collections.Set",
        synopsis = "Utility functions Java.util.Set")
public class SetHelper {
	private static ConcurrentHashMap<Object, Set<Object>> createdSets;

	static {
		synchronized (SetHelper.class) {
			createdSets = new ConcurrentHashMap<Object, Set<Object>>();
		}
	}
	
  	@com.tibco.be.model.functions.BEFunction(
  	    name = "createHashSet",
  	    synopsis = "Create and return the instance of HashSet.",
  	    signature = "Object createHashSet ()",
  	    params = {
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of HashSet"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Create and return the instance of HashSet.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object createHashSet ( ) {
  	
  		return new HashSet();
  	} 

  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "createHashSetWithId",
  	  	    synopsis = "Create and return instance of HashSet.",
  	  	    signature = "Object createHashSetWithId ( String setId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "setId", type = "String", desc = "The String ID of the set to create ") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of HashSet"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Create and return instance of HashSet for the given setID. If the set already exists, return the existing Set.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object createHashSetWithId (String setId ) {
  			Set<Object> set = createdSets.get(setId);
  			if(set == null){
  				set = new HashSet<Object>();
  				createdSets.put(setId, set);
  			}
  	  		return set;
  	  	}
  	
  	@com.tibco.be.model.functions.BEFunction(
  	    name = "createLinkedHashSet",
  	    synopsis = "Create and return the instance of LinkedHashSet.",
  	    signature = "Object createLinkedHashSet (  )",
  	    params = {
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of LinkedHashSet"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Create and return the instance of LinkedHashSet.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object createLinkedHashSet (  ) {
  	
  		return new LinkedHashSet();
  	}
  	
  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "createLinkedHashSetWithId",
  	  	    synopsis = "Create and return instance of LinkedHashSet.",
  	  	    signature = "Object createLinkedHashSetWithId ( String setId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "setId", type = "String", desc = "The String ID of the set to create ") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of LinkedHashSet"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Create and return instance of LinkedHashSet for the given setID. If the set already exists, return the existing Set.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object createLinkedHashSetWithId (String setId ) {
  			Set<Object> set = createdSets.get(setId);
  			if(set == null){
  				set = new LinkedHashSet<Object>();
  				createdSets.put(setId, set);
  			}
  	  		return set;
  	  	}

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "createSet",
  	    synopsis = "Create and return instance of user specified Set implementation.",
  	    signature = "Object createSet ( String className   )",
  	    params = {
  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "className", type = "String", desc = "Specify the className that implements the Set interface  ") 
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of user specified Set implementation"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Create and return instance of user specified Set implementation.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object createSet ( String className   ) {
  	
  		Exception ex = null;
        try {
            Class<Set> cls = (Class<Set>) Class.forName(className);
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
  	  	    name = "createSetWithId",
  	  	    synopsis = "Create and return instance of user specified Set implementation for the given setID.",
  	  	    signature = "Object createSetWithId ( String className,  String setId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "className", type = "String", desc = " Specify the className that implements the Set interface "),
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "setId", type = "String", desc = "The String ID of the set to create ") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of user specified Set implementation"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Create and return instance of user specified Set implementation for the given setID. If the list already exists, return the existing Set.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object createSetWithId ( String className, String setId ) {
  			Set<Object> set = createdSets.get(setId);
  			if(set == null){
  				Exception ex = null;
  		        try {
  		            Class<Set> cls = (Class<Set>) Class.forName(className);
  		            set= cls.newInstance();
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
  		        
  		      createdSets.put(setId, set);
  			}
  	  		return set;
  	  	}

  	@com.tibco.be.model.functions.BEFunction(
  	    name = "createTreeSet",
  	    synopsis = "Create and return the instance of TreeSet.",
  	    signature = "Object createTreeSet (  )",
  	    params = {
  	        
  	    },
  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of TreeSet"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Create and return the instance of TreeSet.",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static Object createTreeSet (  ) {
  	
  		return new TreeSet();
  	} 
  	
  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "createTreeSetWithId",
  	  	    synopsis = "Create and return instance of TreeSet.",
  	  	    signature = "Object createTreeSetWithId ( String setId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "setId", type = "String", desc = "The String ID of the set to create ") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of TreeSet"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Create and return instance of TreeSet for the given setID. If the set already exists, return the existing Set.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object createTreeSetWithId (String setId ) {
  			Set<Object> set = createdSets.get(setId);
  			if(set == null){
  				set = new LinkedHashSet<Object>();
  				createdSets.put(setId, set);
  			}
  	  		return set;
  	  	}
  	

  	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "deleteSet",
  	  	    synopsis = "Deletes the instance of set for the given setId.<br/>This function can not be used if the collection is created with createSet().",
  	  	    signature = "Object deleteSet ( String setId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "setId", type = "String", desc = "The String ID of the Set to delete ") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = " "),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Deletes the instance of Set  for the given setID.<br/>This function can not be used if the collection is created with createSet().",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static void deleteSet (String setId ) {
  			createdSets.remove(setId);
  	  	}

	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "getSet",
  	  	    synopsis = "Returns the instance of Set for the given setId.<br/>This function can not be used when a set is created using createSet(), createHashSet(), createLinkedHashSet() and createTreeSet() functions.",
  	  	    signature = "Object getSet ( String setId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "setId", type = "String", desc = "The String ID of the Set") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The instance of Set"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Returns the instance of Set  for the given setID.<br/>This function can not be used when a set is created using createSet(), createHashSet(), createLinkedHashSet() and createTreeSet() functions.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object getSet (String setId ) {
  			return createdSets.get(setId);
  	  	}

}
