package com.tibco.be.functions.java.util.collection;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

/**
 * Generated Code from String Template.
 * Date : 11 Sep, 2012
 * @author majha
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Collections.List",
        synopsis = "Utility Functions for java.util.List ")
public class ListHelper {

   private static ConcurrentHashMap<Object, List<Object>> createdLists ;
   
	static {
		synchronized (ListHelper.class) {
			createdLists = new ConcurrentHashMap<Object, List<Object>>();
		}
	}
	
	 @com.tibco.be.model.functions.BEFunction(
            name = "add",
            synopsis = "Inserts the specified element at the specified position in this collection.",
            signature = "void add ( Object collection  ,int index  ,Object element)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "The Collection object ") ,
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "Index at which the specified element is to be inserted ") ,
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "element", type = "Object", desc = "Element to be inserted ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Inserts the specified element at the specified position in this collection.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static void add ( Object collection  ,int index  ,Object element   ) {
        if(collection != null && collection instanceof List){
            ((List)collection).add(index, element);
            return;
        }
        throw new RuntimeException("Invalid Collection parameter specified");
    }
    @com.tibco.be.model.functions.BEFunction(
            name = "addAll",
            synopsis = "Inserts all of the elements in the specified collection into this collection at the specified position.",
            signature = "boolean addAll ( Object collection  ,int index  ,Object collection )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "The Collection object ") ,
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "Index at which to insert the first element from the specified collection") ,
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection2", type = "Object", desc = "Collection containing elements to be added to this collection ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true, If this collection changed as a result of the call"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Inserts all of the elements in the specified collection into this collection at the specified position.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static boolean addAll ( Object collection  ,int index  ,Object collection2   ) {
        if(collection != null && collection instanceof List && collection2 instanceof Collection){
            return ((List)collection).addAll(index, (Collection)collection2);
        }
        throw new RuntimeException("Invalid Collection parameter specified");
    }
   
    @com.tibco.be.model.functions.BEFunction(
            name = "createArrayList",
            synopsis = "Create and returns instance of ArrayList.",
            signature = "Object createArrayList (  )",
            params = {

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of ArrayList"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Create and returns instance of ArrayList.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object createArrayList (  ) {

        return new ArrayList();
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "createArrayListWithId",
            synopsis = "Create and return instance of ArrayList for the given listID",
            signature = "Object createArrayListWithId ( String listId  )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listId", type = "String", desc = "The String ID of the list to create")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of ArrayList"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Create and return instance of ArrayList for the given listID. If the list already exists, return the existing List.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object createArrayListWithId ( String listId   ) {
    	List<Object> list = createdLists.get(listId);
    	if(list == null){
    		list = new ArrayList();
            createdLists.put(listId, list);
    	}
        return list;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "createLinkedList",
            synopsis = "Create and returns instance of LinkedList.",
            signature = "Object createLinkedList (  )",
            params = {

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of LinkedList"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Create and returns instance of LinkedList.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object createLinkedList (  ) {

        return new LinkedList();
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "createLinkedListWithId",
            synopsis = "Create and return instance of LinkedList for the given listID",
            signature = "Object createLinkedListWithId ( String listId  )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listId", type = "String", desc = "The String ID of the list to create")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of LinkedList"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Create and return instance of LinkedList for the given listID. If the list already exists, return the existing List.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object createLinkedListWithId ( String listId   ) {
    	List<Object> list = createdLists.get(listId);
    	if(list == null){
    		list = new LinkedList();
            createdLists.put(listId, list);
    	}
        return list;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "createList",
            synopsis = "Creates user specified List implementation",
            signature = "Object createList ( String className   )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "className", type = "String", desc = "Specify the className that implements the List interface ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of user specified List implementation"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates user specified List implementation",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object createList ( String className   ) {

        Exception ex = null;
        try {
            Class<List> cls = (Class<List>) Class.forName(className);
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
            name = "createListWithId",
            synopsis = "Creates user specified List implementation",
            signature = "Object createListWithId ( String className , String listId  )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "className", type = "String", desc = "Specify the className that implements the List interface "),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listId", type = "String", desc = "The String ID of the list to create")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of user specified List implementation"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Create and return instance of user specified list implementation for the given listID. If the list already exists, return the existing List.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object createListWithId ( String className, String listId   ) {
    	List<Object> list = createdLists.get(listId);
    	if(list == null){
    		Exception ex = null;
            try {
                Class<List> cls = (Class<List>) Class.forName(className);
                list = cls.newInstance();
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
            
            createdLists.put(listId, list);
    	}
        return list;
    }




    @com.tibco.be.model.functions.BEFunction(
            name = "get",
            synopsis = "Returns the element at the specified position in this list.",
            signature = "Object get ( Object list  ,int index   )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "Object", desc = "The List object ") ,
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "Index of the element to return ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The element at the specified position in this list"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the element at the specified position in this list.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object get ( Object list  ,int index   ) {
        if(list != null && list instanceof List){
            return ((List)list).get(index);
        }
        throw new RuntimeException("Invalid List parameter specified");
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "indexOf",
            synopsis = "Returns the index of the first occurrence of the specified element \n in this list, or -1 if this list does not contain the element.",
            signature = "int indexOf ( Object list  ,Object element   )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "Object", desc = "The List object ") ,
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "element", type = "Object", desc = " Element to search for")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The index of the first occurrence of the specified element in \n this list, or -1 if this list does not contain the element"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the index of the first occurrence of the specified element \n in this list, or -1 if this list does not contain the element.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static int indexOf ( Object list  ,Object element   ) {
        if(list != null && list instanceof List){
            return ((List)list).indexOf(element);
        }
        throw new RuntimeException("Invalid List parameter specified");
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "isEmpty",
            synopsis = "Returns true, if this list contains no elements.",
            enabled = @Enabled(value=false),
            signature = "boolean isEmpty ( Object list   )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "Object", desc = "The List object ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true, If this list contains no elements"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns true, if this list contains no elements.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static boolean isEmpty ( Object list   ) {
        if(list != null && list instanceof List){
            return ((List)list).isEmpty();
        }
        throw new RuntimeException("Invalid List parameter specified");
    }



    @com.tibco.be.model.functions.BEFunction(
            name = "lastIndexOf",
            synopsis = "Returns the index of the last occurrence of the specified element \n in this list, or -1 if this list does not contain the element.",
            signature = "int lastIndexOf ( Object list  ,Object element   )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "Object", desc = "The List object ") ,
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "element", type = "Object", desc = "Element to search for ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The index of the last occurrence of the specified element in \n this list, or -1 if this list does not contain the element"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the index of the last occurrence of the specified element \n in this list, or -1 if this list does not contain the element.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static int lastIndexOf ( Object list  ,Object element   ) {
        if(list != null && list instanceof List){
            return ((List)list).lastIndexOf(element);
        }
        throw new RuntimeException("Invalid List parameter specified");
    }

   

    

    @com.tibco.be.model.functions.BEFunction(
            name = "set",
            synopsis = "Replaces the element at the specified position in this list with the specified element.",
            signature = "Object set ( Object list  ,int index  ,Object element   )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "Object", desc = "The list object ") ,
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "Index of the element to replace ") ,
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "element", type = "Object", desc = "Element to be stored at the specified position ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The element previously at the specified position"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Replaces the element at the specified position in this list with the specified element.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object set ( Object list  ,int index  ,Object element   ) {
        if(list != null && list instanceof List ){
            return ((List)list).set(index, element);
        }
        throw new RuntimeException("Invalid List parameter specified");
    }

    

    @com.tibco.be.model.functions.BEFunction(
            name = "subList",
            synopsis = "Returns a view of the portion of this list between the specified \n fromIndex, inclusive, and <tt>toIndex</tt>, exclusive.",
            signature = "Object subList ( Object list  ,int fromIndex  ,int toIndex   )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "Object", desc = "The List object ") ,
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fromIndex", type = "int", desc = "Low endpoint (inclusive) of the subList ") ,
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "toIndex", type = "int", desc = "High endpoint (exclusive) of the subList")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "A view of the specified range within this list"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns a view of the portion of this list between the specified \n fromIndex, inclusive, and <tt>toIndex</tt>, exclusive.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Object subList ( Object list  ,int fromIndex  ,int toIndex   ) {
        if(list != null && list instanceof List ){
            return ((List)list).subList(fromIndex, toIndex);
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
  	  	    name = "deleteList",
  	  	    synopsis = "Deletes the instance of List for the given listId.<br/>>This function can not be used if the collection is created with createList().",
  	  	    signature = "Object deleteList ( String listId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listId", type = "String", desc = "The String ID of the List to delete ") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = " "),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Deletes the instance of List  for the given listID.<br/>This function can not be used if the collection is created with createList().",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static void deleteList (String listId ) {
    	createdLists.remove(listId);
  	  	}

	@com.tibco.be.model.functions.BEFunction(
  	  	    name = "getList",
  	  	    synopsis = "Returns the instance of List for the given listId.",
  	  	    signature = "Object getList ( String listId )",
  	  	    params = {
  	  	    	 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listId", type = "String", desc = "The String ID of the List") 
  	  	    },
  	  	    freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The instance of List"),
  	  	    version = "5.1.1",
  	  	    see = "",
  	  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	  	    description = "Returns the instance of List  for the given listID.",
  	  	    cautions = "none",
  	  	    fndomain = {ACTION},
  	  	    example = ""
  	  	)
  	  	public static Object getList (String listId ) {
  			return createdLists.get(listId);
  	  	}

}
