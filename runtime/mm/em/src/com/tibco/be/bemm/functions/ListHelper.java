package com.tibco.be.bemm.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BEMM",
        category = "Util.LinkedList",
        synopsis = "Functions for querying key JMX attributes in the BE runtime cluster for")

public class ListHelper {

	private static Map<String, List<Object>> lists = new HashMap<String, List<Object>>();

	@com.tibco.be.model.functions.BEFunction(
        name = "createList",
        synopsis = "Creates a <code>LinkedList</code> using the 'listID' as the unique identifier.\nDoes nothing if a <code>LinkedList</code> already exists again the 'listID'",
        signature = "void createList(String listID)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a <code>LinkedList</code> using the 'listID' as the unique identifier",
        cautions = "",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
	public static void createList(String listID){
		if (lists.containsKey(listID) == false){
			lists.put(listID, new LinkedList<Object>());
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "deleteList",
        synopsis = "Deletes the <code>LinkedList</code> identified by the 'listID'.\nDoes nothing if no <code>LinkedList</code> is found",
        signature = "void deleteList(String listID)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Deletes the <code>LinkedList</code> identified by the 'listID'",
        cautions = "",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
	public static void deleteList(String listID){
		if (lists.containsKey(listID) == true){
			List<Object> list = lists.remove(listID);
			list.clear();
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "getList",
        synopsis = "Returns the <code>LinkedList</code> identified by the 'listID'",
        signature = "Object getList(String listID)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listID", type = "String", desc = "The unique name for the <code>LinkedList</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <code>LinkedList</code> identified by the 'listID'",
        cautions = "",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
	public static Object getList(String listID){
		return lists.get(listID);
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "add",
        synopsis = "Adds the element to the <code>LinkedList</code> identified by 'listID' at \nthe specified index. Does nothing if no <code>LinkedList</code> is found",
        signature = "void add(String listID, int index, Object element)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listID", type = "String", desc = "The unique name for the <code>LinkedList</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index at which to add the element")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Adds the element to the <code>LinkedList</code> identified by 'listID' at \nthe specified index",
        cautions = "",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
	public static void add(String listID, int index, Object element) {
		List<Object> list = lists.get(listID);
		if (list != null){
			try {
				list.add(index, element);
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "set",
        synopsis = "Sets the element in the <code>LinkedList</code> identified by 'listID' at \nthe specified index. Does nothing is no <code>LinkedList</code> is found",
        signature = "Object set(String listID,int index, Object element)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listID", type = "String", desc = "The unique name for the <code>LinkedList</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index at which to set the element"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "element", type = "Object", desc = "The element to be set to the <code>LinkedList</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the element in the <code>LinkedList</code> identified by 'listID' at \nthe specified index",
        cautions = "",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
	public static Object set(String listID,int index, Object element) {
		List<Object> list = lists.get(listID);
		if (list != null){
			return list.set(index,element);
		}
		return null;
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "contains",
        synopsis = "Returns <code>true</code> if the <code>LinkedList</code> identified by 'listID'\ncontains the element. Returns <code>null</code> if no <code>LinkedList</code>\nexists for 'listID'",
        signature = "boolean contains(String listID,Object element)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listID", type = "String", desc = "The unique name for the <code>LinkedList</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "element", type = "Object", desc = "The element to be searched in the <code>LinkedList</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns <code>true</code> if the <code>LinkedList</code> identified by 'listID'\ncontains the element",
        cautions = "",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
	public static boolean contains(String listID,Object element) {
		List<Object> list = lists.get(listID);
		if (list != null){
			return list.contains(element);
		}
		return false;
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "get",
        synopsis = "Returns the element at index in the <code>LinkedList</code> identified \nby 'listID'. Returns <code>null</code> if no <code>LinkedList</code>\nexists for 'listID'",
        signature = "Object get(String listID,int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listID", type = "String", desc = "The unique name for the <code>LinkedList</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index of the element to be returned")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the element at index in the <code>LinkedList</code> identified \nby 'listID'",
        cautions = "",
        fndomain = {ACTION, BUI},
        		reevaluate = true,
        example = ""
    )
	public static Object get(String listID,int index) {
		List<Object> list = lists.get(listID);
		if (list != null){
			return list.get(index);
		}
		return null;
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "indexOf",
        synopsis = "Returns index of a element in the <code>LinkedList</code> identified by 'listID'\nReturns -1 if no <code>LinkedList</code> exists for 'listID'",
        signature = "int indexOf(String listID,Object element)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listID", type = "String", desc = "The unique name for the <code>LinkedList</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "element", type = "Object", desc = "The element to be searched in the <code>LinkedList</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns index of a element in the <code>LinkedList</code> identified by 'listID'",
        cautions = "",
        fndomain = {ACTION, BUI},
        		reevaluate = true,
        example = ""
    )

	public static int indexOf(String listID,Object element) {
		List<Object> list = lists.get(listID);
		if (list != null){
			return list.indexOf(element);
		}
		return -1;
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "size",
        synopsis = "Returns number of elements in the <code>LinkedList</code> identified by 'listID'. \nReturns 0 if no <code>LinkedList</code> exists for 'listID'",
        signature = "int size(String listID)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listID", type = "String", desc = "The unique name for the <code>LinkedList</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns number of elements in the <code>LinkedList</code> identified by 'listID'",
        cautions = "",
        fndomain = {ACTION, BUI},
        		reevaluate = true,
        example = ""
    )

	public static int size(String listID) {
		List<Object> list = lists.get(listID);
		if (list != null){
			return list.size();
		}
		return 0;
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "isEmpty",
        synopsis = "Returns <code>true</code> if the <code>LinkedList</code> identified by 'listID'\nis empty. Returns <code>false</code> if no <code>LinkedList</code>\nexists for 'listID'",
        signature = "boolean isEmpty(String listID)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listID", type = "String", desc = "The unique name for the <code>LinkedList</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns <code>true</code> if the <code>LinkedList</code> identified by 'listID'\nis empty",
        cautions = "",
        fndomain = {ACTION, BUI},
        		reevaluate = true,
        example = ""
    )

	public static boolean isEmpty(String listID) {
		List<Object> list = lists.get(listID);
		if (list != null){
			return list.isEmpty();
		}
		return false;
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "removeByIndex",
        synopsis = "Removes the element at specified index in the <code>LinkedList</code> identified \nby 'listID'. Returns <code>null</code> if no <code>LinkedList</code>\nexists for 'listID'",
        signature = "Object removeByIndex(String listID,int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listID", type = "String", desc = "The unique name for the <code>LinkedList</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index of the element to be removed")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removes the element at specified index in the <code>LinkedList</code> identified \nby 'listID'",
        cautions = "",
        fndomain = {ACTION, BUI},
        		reevaluate = true,
        example = ""
    )

	public static Object removeByIndex(String listID,int index) {
		List<Object> list = lists.get(listID);
		if (list != null){
			return list.remove(index);
		}
		return null;
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "removeByElement",
        synopsis = "Removes the element from the <code>LinkedList</code> identified \nby 'listID'. Returns <code>false</code> if no <code>LinkedList</code>\nexists for 'listID'",
        signature = "boolean removeByElement(String listID,Object element)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listID", type = "String", desc = "The unique name for the <code>LinkedList</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "element", type = "Object", desc = "The element to be removed")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removes the element from the <code>LinkedList</code> identified \nby 'listID'",
        cautions = "",
        fndomain = {ACTION, BUI},
        		reevaluate = true,
        example = ""
    )

	public static boolean removeByElement(String listID,Object element) {
		List<Object> list = lists.get(listID);
		if (list != null){
			return list.remove(element);
		}
		return false;
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "clear",
        synopsis = "Clears the <code>LinkedList</code> identified by 'listID'. Does nothing \nif no <code>LinkedList</code> exists for 'listID'",
        signature = "void clear(String listID)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Clears the <code>LinkedList</code> identified by 'listID'",
        cautions = "",
        fndomain = {ACTION, BUI},
        		reevaluate = true,
        example = ""
    )

	public static void clear(String listID) {
		List<Object> list = lists.get(listID);
		if (list != null){
			list.clear();
		}
	}

}
