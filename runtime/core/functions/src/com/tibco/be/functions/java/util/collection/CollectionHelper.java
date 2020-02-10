package com.tibco.be.functions.java.util.collection;

import java.util.Collection;

import static com.tibco.be.model.functions.FunctionDomain.*;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Collections",
        synopsis = "Collections functions")
public class CollectionHelper {
	
	 @com.tibco.be.model.functions.BEFunction(
            name = "add",
            synopsis = "Inserts the specified element at the specified position in this collection.",
            signature = "void add ( Object collection, Object element)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "The Collection object "), 
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
    public static void add ( Object collection, Object element ) {
        if (collection != null && collection instanceof Collection) {
            ((Collection)collection).add( element);
            return;
        }
        throw new RuntimeException("Invalid Collection parameter specified");
    }
    @com.tibco.be.model.functions.BEFunction(
            name = "addAll",
            synopsis = "Inserts all of the elements in the specified collection into this collection at the specified position.",
            signature = "boolean addAll ( Object collection, Object collection2 )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "The Collection object "), 
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
    public static boolean addAll ( Object collection, Object collection2 ) {
        if(collection != null && collection instanceof Collection && collection2 instanceof Collection){
            return ((Collection)collection).addAll( (Collection)collection2);
        }
        throw new RuntimeException("Invalid Collection parameter specified");
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "clear",
            synopsis = "Removes all of the elements from this collection.",
            signature = "void clear ( Object collection )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "The Collection object ")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Removes all of the elements from this collection.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
	)
    public static void clear ( Object collection ) {
        if(collection != null && collection instanceof Collection){
            ((Collection)collection).clear();
            return;
        }
        throw new RuntimeException("Invalid Collection parameter specified");
    }
    @com.tibco.be.model.functions.BEFunction(
            name = "contains",
            synopsis = "Returns true if this collection contains the specified element.",
            signature = "boolean contains ( Object collection, Object element )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "The Collection object "), 
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "element", type = "Object", desc = "Element whose presence in this collection is to be tested ")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true, If this collection contains the specified element"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns true if this collection contains the specified element.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
	)
    public static boolean contains ( Object collection, Object element ) {
        if(collection != null && collection instanceof Collection){
            return ((Collection)collection).contains(element);
        }
        throw new RuntimeException("Invalid Collection parameter specified");
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "containsAll",
            synopsis = "Returns true if this collection contains all of the elements of the specified collection.",
            signature = "boolean containsAll ( Object collection, Object collection2 )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "The Collection object "), 
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection2", type = "Object", desc = "Collection to be checked for containment in this collection ")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true, If this collection contains all of the elements of the specified collection"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns true if this collection contains all of the elements of the specified collection.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
	)
    public static boolean containsAll ( Object collection, Object collection2 ) {
        if(collection instanceof Collection && collection2 instanceof Collection){
            return ((Collection)collection).containsAll((Collection)collection2);
        }
        throw new RuntimeException("Invalid Collection parameter specified");
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "iterator",
            synopsis = "Returns an iterator over the elements in this collection in proper sequence.",
            signature = "Object iterator ( Object collection )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "The Collection object ")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "An iterator over the elements in this collection in proper sequence"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns an iterator over the elements in this collection in proper sequence.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
	)
    public static Object iterator ( Object collection ) {
        if(collection != null && collection instanceof Collection){
            return ((Collection)collection).iterator();
        }
        throw new RuntimeException("Invalid Collection parameter specified");
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "remove",
            synopsis = "Removes the first occurrence of the specified element from this collection, if it is present.",
            signature = "boolean remove ( Object collection, Object element )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "The Collection Object "), 
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "element", type = "Object", desc = "Element to be removed from this collection, if present ")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true, If this collection contained the specified element"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Removes the first occurrence of the specified element from this collection, if it is present..",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
	)
    public static boolean remove ( Object collection, Object element ) {
        if(collection != null && collection instanceof Collection){
            return ((Collection)collection).remove(element);
        }
        throw new RuntimeException("Invalid Collection parameter specified");
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "removeAll",
            synopsis = "Removes from this collection all of its elements that are contained in the specified collection.",
            signature = "boolean removeAll ( Object collection, Object collection2 )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "The Collection object "), 
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection2", type = "Object", desc = "Collection containing elements to be removed from this collection ")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true, If this collection changed as a result of the call"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Removes from this collection all of its elements that are contained in the specified collection.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
	)
    public static boolean removeAll ( Object collection, Object collection2 ) {
        if(collection instanceof Collection && collection2 instanceof Collection){
            return ((Collection)collection).removeAll((Collection)collection2);
        }
        throw new RuntimeException("Invalid Collection parameter specified");
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "retainAll",
            synopsis = "Retains only the elements in this collection that are contained in the specified collection.",
            signature = "boolean retainAll ( Object collection, Object collection2 )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "The Collection object "), 
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection2", type = "Object", desc = "Collection containing elements to be retained in this collection ")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true, If this collection changed as a result of the call"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Retains only the elements in this collection that are contained in the specified collection.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
	)
    public static boolean retainAll ( Object collection, Object collection2 ) {
        if(collection instanceof Collection && collection2 instanceof Collection){
            return ((Collection)collection).retainAll((Collection)collection2);
        }
        throw new RuntimeException("Invalid Collection parameter specified");
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "size",
            synopsis = "Returns the number of elements in this collection.",
            signature = "int size ( Object collection )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "The Collection object ")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The number of elements in this collection"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the number of elements in this collection.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
	)
    public static int size ( Object collection ) {

        if(collection != null && collection instanceof Collection ){
            return ((Collection)collection).size();
        }
        throw new RuntimeException("Invalid Collection parameter specified");
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "toArray",
            synopsis = "Returns an array containing all of the elements in this collection in proper \n sequence (from first to last element).",
            signature = "[Object[] toArray ( Object collection )",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "The Collection object ")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "An array containing all of the elements in this collection in proper sequence"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns an array containing all of the elements in this collection in proper \n sequence (from first to last element).",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
	)
    public static Object toArray ( Object collection ) {
        if(collection != null && collection instanceof Collection ){
            return ((Collection)collection).toArray();
        }
        throw new RuntimeException("Invalid Collection parameter specified");
    }
}
