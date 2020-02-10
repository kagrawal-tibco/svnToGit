/**
 * 
 */
package com.tibco.cep.store.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.Calendar;

import com.tibco.cep.runtime.model.serializers.FieldType;
import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.cep.store.Item;
import com.tibco.cep.store.StoreConnection;
import com.tibco.cep.store.StoreContainer;

/**
 * @author vpatil
 *
 */
@BEPackage(
        catalog = "CEP Store",
        category = "Store.Item",
        synopsis = "Store Item functions")
public class ItemFunctions extends StoreFunctions {
	
	@com.tibco.be.model.functions.BEFunction(
		name = "create",
		signature = "Object create (String url, String containerName)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
				@FunctionParamDescriptor(name = "containerName", type = "String", desc = "Container name")
		},
		freturn = @FunctionParamDescriptor(name = "Store Item Object", type = "Object", desc = "Returns newly created empty Store Item Object"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Creates an empty Store Item object associated with the given Container. Container must be first opened. Ref: Store.open",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static Object create(String url, String containerName) {
		StoreConnection storeConnection = getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				StoreContainer<? extends Item> storeContainer = storeConnection.getContainer(containerName);
				if (storeContainer != null) {
					return storeContainer.createItem();
				} else {
					throw new IllegalArgumentException(String.format("Invalid container name [%s] or container does not exist.", containerName));
				}
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error creating Store item with url[%s] & container name[%s]", url, containerName), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getInteger",
		signature = "int getInteger (Object storeItem, String fieldName)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "String", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be retrieved")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "int", desc = "Returns int value of the specified field"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns integer value of the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static int getInteger(Object storeItem, String fieldName) {
		return (Integer) getValue(storeItem, fieldName, FieldType.INTEGER.toString());
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getLong",
		signature = "long getLong (Object storeItem, String fieldName)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "String", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be retrieved")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "long", desc = "Returns long value of the specified field"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns long value of the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static long getLong(Object storeItem, String fieldName) {
		return (Long) getValue(storeItem, fieldName, FieldType.LONG.toString());
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getString",
		signature = "String getString (Object storeItem, String fieldName)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "String", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be retrieved")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Returns String value of the specified field"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns String value of the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static String getString(Object storeItem, String fieldName) {
		return (String) getValue(storeItem, fieldName, FieldType.STRING.toString());
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getDouble",
		signature = "double getDouble (Object storeItem, String fieldName)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "String", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be retrieved")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "double", desc = "Returns double value of the specified field"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns double value of the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static double getDouble(Object storeItem, String fieldName) {
		return (Double) getValue(storeItem, fieldName, FieldType.DOUBLE.toString());
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getBoolean",
		signature = "boolean getBoolean (Object storeItem, String fieldName)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "String", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be retrieved")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns boolean value of the specified field"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns boolean value of the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static boolean getBoolean(Object storeItem, String fieldName) {
		return (Boolean)getValue(storeItem, fieldName, FieldType.BOOLEAN.toString());
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getDateTime",
		signature = "Object getDateTime (Object storeItem, String fieldName)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "String", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be retrieved")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "DateTime", desc = "Returns datetime value of the specified field"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns datetime value of the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static Calendar getDateTime(Object storeItem, String fieldName) {
		return (Calendar) getValue(storeItem, fieldName, FieldType.DATETIME.toString());
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getBlob",
		signature = "Object getBlob (Object storeItem, String fieldName)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "String", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be retrieved")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Returns blob value of the specified field"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns blob value of the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static Object getBlob(Object storeItem, String fieldName) {
		return getValue(storeItem, fieldName, FieldType.BLOB.toString());
	}

	private static Object getValue(Object storeItem, String fieldName, String fieldType) {
		if (storeItem instanceof Item) {
			try {
				return ((Item) storeItem).getValue(fieldName, fieldType);
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error getting value from Item with Field Name[%s] & Type[%s]", fieldName, fieldType), exception);
			}
		} else {
			throw new IllegalArgumentException("Argument type not valid. Expected type is Item");
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getExpiration",
		signature = "long getExpiration (Object storeItem)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "Object", desc = "Store Item Object")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "long", desc = "Returns time left to expire (in seconds). If expired but not deleted, returns 0."),
		version = "5.6.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns time left to expire (in seconds). If expired but not deleted, returns 0.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static long getExpiration(Object storeItem) {
		if (storeItem instanceof Item) {
			try {
				return ((Item) storeItem).getExpiration();
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error getting TTL value of this Item"), exception);
			}
		} else {
			throw new IllegalArgumentException("Argument type not valid. Expected type is Item");
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setInteger",
		signature = "void setInteger (Object storeItem, String fieldName, int fieldValue)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "Object", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be set"),
				@FunctionParamDescriptor(name = "fieldValue", type = "int", desc = "Field value")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets integer value to the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setInteger(Object storeItem, String fieldName, int fieldValue) {
		setValue(storeItem, fieldName, FieldType.INTEGER.toString(), fieldValue);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setLong",
		signature = "void setLong (Object storeItem, String fieldName, long fieldValue)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "Object", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be set"),
				@FunctionParamDescriptor(name = "fieldValue", type = "long", desc = "Field value")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets long value to the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setLong(Object storeItem, String fieldName, long fieldValue) {
		setValue(storeItem, fieldName, FieldType.LONG.toString(), fieldValue);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setString",
		signature = "void setString (Object storeItem, String fieldName, String fieldValue)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "Object", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be set"),
				@FunctionParamDescriptor(name = "fieldValue", type = "String", desc = "Field value")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets String value to the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setString(Object storeItem, String fieldName, Object fieldValue) {
		setValue(storeItem, fieldName, FieldType.STRING.toString(), fieldValue);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setDouble",
		signature = "void setDouble (Object storeItem, String fieldName, double fieldValue)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "Object", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be set"),
				@FunctionParamDescriptor(name = "fieldValue", type = "double", desc = "Field value")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets double value to the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setDouble(Object storeItem, String fieldName, double fieldValue) {
		setValue(storeItem, fieldName, FieldType.DOUBLE.toString(), fieldValue);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setBoolean",
		signature = "void setBoolean (Object storeItem, String fieldName, boolean fieldValue)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "Object", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be set"),
				@FunctionParamDescriptor(name = "fieldValue", type = "boolean", desc = "Field value")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets boolean value to the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setBoolean(Object storeItem, String fieldName, boolean fieldValue) {
		setValue(storeItem, fieldName, FieldType.BOOLEAN.toString(), fieldValue);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setDateTime",
		signature = "void setDateTime (Object storeItem, String fieldName, DateTime fieldValue)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "Object", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be set"),
				@FunctionParamDescriptor(name = "fieldValue", type = "DateTime", desc = "Field value")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets datetime value to the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setDateTime(Object storeItem, String fieldName, Calendar fieldValue) {
		setValue(storeItem, fieldName, FieldType.DATETIME.toString(), fieldValue);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setBlob",
		signature = "void setBlob (Object storeItem, String fieldName, Object fieldValue)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "Object", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field whose value needs to be set"),
				@FunctionParamDescriptor(name = "fieldValue", type = "Object", desc = "Field value")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets blob value to the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setBlob(Object storeItem, String fieldName, Object fieldValue) {
		setValue(storeItem, fieldName, FieldType.BLOB.toString(), fieldValue);
	}

	private static void setValue(Object storeItem, String fieldName, String fieldType, Object fieldValue) {
		if (storeItem instanceof Item) {
			try {
				((Item) storeItem).setValue(fieldName, fieldType, fieldValue);
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error setting value within the Item with Field Name[%s] & Type[%s]", fieldName, fieldType), exception);
			}
		} else {
			throw new IllegalArgumentException("Argument type not valid. Expected type is Item");
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setTTL",
		signature = "void setTTL (Object storeItem, long ttl)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "Object", desc = "Store Item Object"),
				@FunctionParamDescriptor(name = "ttl", type = "long", desc = "TTL value (in seconds) after which this item will be available for expiration")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = "Sets the TTL value (in seconds) for this item before putting the item in the store"),
		version = "5.6.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the TTL value (in seconds) for this item",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setTTL(Object storeItem, long ttl) {
		if (storeItem instanceof Item) {
			try {
				((Item) storeItem).setTTL(ttl);
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error setting TTL value for this Item"), exception);
			}
		} else {
			throw new IllegalArgumentException("Argument type not valid. Expected type is Item");
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "destroy",
		signature = "void destroy (Object storeItem)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "Object", desc = "Store Item Object")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Destroy's the Store item object",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void destroy(Object storeItem) {
		if (storeItem instanceof Item) {
			try {
				((Item) storeItem).destroy();
			} catch (Exception exception) {
				throw new RuntimeException("Error destroying the Item.", exception);
			}
		} else {
			throw new IllegalArgumentException("Argument type not valid. Expected type is Item");
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "clear",
		signature = "void clear (Object storeItem)",
		params = {
				@FunctionParamDescriptor(name = "storeItem", type = "Object", desc = "Store Item Object")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Clears the existing data in the item so that new data can be added.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void clear(Object storeItem) {
		if (storeItem instanceof Item) {
			try {
				((Item) storeItem).clear();
			} catch (Exception exception) {
				throw new RuntimeException("Error destroying the Item.", exception);
			}
		} else {
			throw new IllegalArgumentException("Argument type not valid. Expected type is Item");
		}
	}
}
