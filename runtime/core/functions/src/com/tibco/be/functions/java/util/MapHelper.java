package com.tibco.be.functions.java.util;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tibco.be.model.functions.FunctionDomain.*;


/**
 * User: nprade
 * Date: Apr 14, 2005
 * Time: 4:03:45 AM
 */


@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Util.HashMap",
        synopsis = "Utility Functions for Maps")
public class MapHelper {

    protected static Map<String,Map<?,?>> m_maps;
    static {
    	synchronized (MapHelper.class) {
    		m_maps= new ConcurrentHashMap<String,Map<?,?>>();
		}
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "get",
        synopsis = "Returns the String for the specified key in the specified map.",
        signature = "String get(String mapID, String key)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapID", type = "String", desc = "The String ID of the map in which the lookup is to be performed."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "The String key for which the value will be retrieved.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "the value at <code>key</code> in the map of id <code>mapID</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the String for the specified key in the specified map.\nIf the map or the key is non-existent, returns null.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        reevaluate = true,
        example = ""
    )
    public static String get(String mapID, String key) {
        Map map;
            map = (Map) m_maps.get(mapID);
        if (null == map) {
            return null;
        }
        return (String)map.get(key);
    }//get


    @com.tibco.be.model.functions.BEFunction(
        name = "put",
        synopsis = "Maps an object for a specified key in a specified map.",
        signature = "String put(String mapID, String key, String value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapID", type = "String", desc = "The String ID of the map in which the mapping is added."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "The String key for which the value is mapped."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "The String value to be added.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "<code>value</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Maps an object for a specified key in a specified map.\nIf the map is non-existent, returns null.",
        cautions = "",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
    public static String put(String mapID, String key, String value) {
        Map map;
            map = (Map) m_maps.get(mapID);
        if (null == map) {
            return null;
        }//if
        map.put(key, value);
        return value;
    }//put


    @com.tibco.be.model.functions.BEFunction(
        name = "getObject",
        synopsis = "Returns the object for the specified key in the specified map.",
        signature = "Object getObject(String mapID, String key)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapID", type = "String", desc = "The String ID of the map in which the lookup is to be performed."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "The String key for which the value will be retrieved.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "the value at <code>key</code> in the map of id <code>mapID</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the String for the specified key in the specified map.\nIf the map or the key is non-existent, returns null.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        reevaluate = true,
        example = ""
    )
    public static Object getObject(String mapID, String key) {
        Map map;
            map = (Map) m_maps.get(mapID);
        if (null == map) {
            return null;
        }
        return map.get(key);
    }//get


    @com.tibco.be.model.functions.BEFunction(
        name = "getMapKeys",
        synopsis = "Returns the object for the specified key in the specified map.",
        signature = "String[] getMapKeys(String mapID)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapID", type = "String", desc = "The String ID of the map in which the lookup is to be performed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "of the keys in the map of id <code>mapID</code>"),
        version = "4.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the String[] of the keys in the specified map.\nIf the map or the key is non-existent, returns null.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        reevaluate = true,
        example = ""
    )
    public static String[] getMapKeys(String mapID) {
        Map map = (Map) m_maps.get(mapID);
        if (null == map) {
            return null;
        }

        synchronized (map){
            return (String[]) map.keySet().toArray(new String[map.size()]);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getMap",
        synopsis = "Returns the specified map.",
        signature = "Object getMap(String mapID)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapID", type = "String", desc = "The String ID of the map in which the lookup is to be performed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The map"),
        version = "5.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the specified map or if the key is non-existent, returns null.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        reevaluate = true,
        example = ""
    )
    public static Object getMap(String mapID) {
        return m_maps.get(mapID);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "putObject",
        synopsis = "Maps an object for a specified key in a specified map.",
        signature = "Object putObject(String mapID, String key, Object value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapID", type = "String", desc = "The String ID of the map in which the mapping is added."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "The String key for which the value is mapped."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "The Object value to be added.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "<code>value</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Maps an object for a specified key in a specified map.\nIf the map is non-existent, returns null.",
        cautions = "",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
    public static Object putObject(String mapID, String key, Object value) {
        Map map;
            map = (Map) m_maps.get(mapID);
        if (null == map) {
            return null;
        }//if
        map.put(key, value);
        return value;
    }//put


    @com.tibco.be.model.functions.BEFunction(
        name = "remove",
        synopsis = "Removes a mapping for a specified key in a specified map.",
        signature = "String remove (String mapID, String key)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapID", type = "String", desc = "The String ID of the map in which the mapping is removed."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "The String key for which the value is removed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "the value String removed, if it existed."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removes a mapping for a specified key in a specified map.\nIf the map or the mapping is non-existent, returns null.",
        cautions = "",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
    public static String remove(String mapID, String key) {
        return (String) removeObject(mapID, key);
    }//remove

    @com.tibco.be.model.functions.BEFunction(
        name = "removeObject",
        synopsis = "Removes a mapping for a specified key in a specified map.",
        signature = "Object removeObject(String mapID, String key)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapID", type = "String", desc = "The String ID of the map in which the mapping is removed."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "The String key for which the value is removed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "the value removed, if it existed."),
        version = "4.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removes a mapping for a specified key in a specified map.\nIf the map or the mapping is non-existent, returns null.",
        cautions = "",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
    public static Object removeObject(String mapID, String key) {
        Map map = (Map) m_maps.get(mapID);
        if (null == map) {
            return null;
        }//if
        return map.remove(key);
    }//remove

    @com.tibco.be.model.functions.BEFunction(
        name = "createMap",
        synopsis = "Creates a new HashMap for the given mapID.",
        signature = "void createMap (String mapID)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapID", type = "String", desc = "The String ID of the map to create.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new <code>HashMap</code> for the given <code>mapID</code>.\nIf the map already exists, no action is taken.",
        cautions = "",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
    public static void createMap(String mapID) {
        final Map map = (Map) m_maps.get(mapID);
        if (null == map) {
            m_maps.put (mapID, new HashMap());
        }//if
    }//createMap


    @com.tibco.be.model.functions.BEFunction(
        name = "deleteMap",
        synopsis = "Deletes the HashMap for the given mapID.",
        signature = "void deleteMap (String mapID)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapID", type = "String", desc = "The String ID of the map to delete.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Deletes the <code>HashMap</code> for the given <code>mapID</code>.\nIf the map does not exist, no action is taken.",
        cautions = "",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
    public static void deleteMap(String mapID) {
        m_maps.remove(mapID);
    }//deleteMap


    @com.tibco.be.model.functions.BEFunction(
        name = "size",
        synopsis = "Returns the number of mappings in the map specified by mapID.",
        signature = "int size (String mapID)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapID", type = "String", desc = "The String ID of the map.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "the size of the map."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the number of mappings in the map specified by <code>mapID</code>.\nIf the map does not exist, returns -1.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        reevaluate = true,
        example = ""
    )
    public static int size(String mapID) {
        final Map map = (Map) m_maps.get(mapID);
        if (null != map) {
            return map.size();
        } else {
            return -1;
        }
    }//size


    @com.tibco.be.model.functions.BEFunction(
        name = "clear",
        synopsis = "Clears the map specified by mapID.",
        signature = "void clear (String mapID)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapID", type = "String", desc = "The String ID of the map.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Clears the map specified by <code>mapID</code>.",
        cautions = "",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
    public static void clear(String mapID) {
        final Map map = (Map) m_maps.get(mapID);
        if (null != map) {
            map.clear();
        }
    }//clear

	@com.tibco.be.model.functions.BEFunction(
        name = "getValue",
        synopsis = "Gets the value for the given key in the given map",
        signature = "Object getValue(Object map,String key)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "map", type = "Object", desc = "The map object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "The key for the value")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "value for the given key in the given map"),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value for the given key in the given map",
        cautions = "none",
        fndomain = {ACTION, BUI},
        reevaluate = true,
        example = ""
    )
	public static Object getValue(Object map,String key) {
		Object mapValue = null;
		if(map instanceof Map) {
			if(map != null)
				mapValue = ((Map)map).get(key);
		} else {
			return null;
		}
		return mapValue;
	}
}//class
