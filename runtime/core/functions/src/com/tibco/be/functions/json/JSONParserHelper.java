/**
 * 
 */
package com.tibco.be.functions.json;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * API's for JSON parsing
 * 
 * @author vpatil
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "JSON",
        synopsis = "JSON Parsing functions")
public class JSONParserHelper {
	
	@com.tibco.be.model.functions.BEFunction(
		name = "parseJSON",
		signature = "Object parseJSON(String jsonContent)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "jsonContent", type = "String", desc = "Json Content")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Parsed JSON Object"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Parse the JSON content. If its a valid JSON, the API returns a parsed JsonNode Object, else throws a exception.",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Object parseJSON(String jsonContent) {
		try {
			ObjectMapper objMapper = new ObjectMapper();
			return objMapper.readValue(jsonContent, JsonNode.class);
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getFieldNames",
		signature = "Object getFieldNames(Object jsonNode)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "jsonNode", type = "Object", desc = "JsonNode Object")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "String Iterator containing JSON Object keys"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the Iterator of field names in a JsonNode",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Object getFieldNames(Object jsonNode) {
		if (jsonNode == null || !(jsonNode instanceof JsonNode)) {
            throw new IllegalArgumentException("Expected a non null JsonNode instance");
        }
		JsonNode jsonElement = (JsonNode) jsonNode;
		return jsonElement.fieldNames();
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getFields",
		signature = "Object getFields(Object jsonNode)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "jsonNode", type = "Object", desc = "JsonNode Object")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Iterator Map Entry of key to JsonNode"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the Map of keys to JsonNode",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Object getFields(Object jsonNode) {
		if (jsonNode == null || !(jsonNode instanceof JsonNode)) {
            throw new IllegalArgumentException("Expected a non null JsonNode instance");
        }
		JsonNode jsonElement = (JsonNode) jsonNode;
		return jsonElement.fields();
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getNodeType",
		signature = "String getNodeType(Object jsonNode)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "jsonNode", type = "Object", desc = "JsonNode Object")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "JSON node type"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the type of JsonNode i.e. ARRAY/OBJECT/etc",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static String getNodeType(Object jsonNode) {
		if (jsonNode == null || !(jsonNode instanceof JsonNode)) {
            throw new IllegalArgumentException("Expected a non null JsonNode instance");
        }
		JsonNode jsonElement = (JsonNode) jsonNode;
		return jsonElement.getNodeType().toString();
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "isArrayNode",
		signature = "boolean isArrayNode(Object jsonNode)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "jsonNode", type = "Object", desc = "JsonNode Object")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "True if JsonNode is array, else false"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns if the Json node is an Array or not",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static boolean isArrayNode(Object jsonNode) {
		if (jsonNode == null || !(jsonNode instanceof JsonNode)) {
            throw new IllegalArgumentException("Expected a non null JsonNode instance");
        }
		JsonNode jsonElement = (JsonNode) jsonNode;
		return jsonElement.isArray();
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "isValueNode",
		signature = "boolean isValueNode(Object jsonNode)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "jsonNode", type = "Object", desc = "JsonNode Object")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "True if JsonNode is a value node, else false"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns if the JsonNode is a value node or not",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static boolean isValueNode(Object jsonNode) {
		if (jsonNode == null || !(jsonNode instanceof JsonNode)) {
            throw new IllegalArgumentException("Expected a non null JsonNode instance");
        }
		JsonNode jsonElement = (JsonNode) jsonNode;
		return jsonElement.isValueNode();
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getTextValue",
		signature = "String getTextValue(Object jsonNode)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "jsonNode", type = "Object", desc = "JsonNode Object")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Text value of the JsonNode"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the text value of the JSON node passed",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static String getTextValue(Object jsonNode) {
		if (jsonNode == null || !(jsonNode instanceof JsonNode)) {
            throw new IllegalArgumentException("Expected a non null JsonNode instance");
        }
		JsonNode jsonElement = (JsonNode) jsonNode;
		return jsonElement.asText();
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getAllElements",
		signature = "Object getAllElements(Object jsonNode)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "jsonNode", type = "Object", desc = "JsonNode Object")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Iterator of JsonNode Objects"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the Iterator of all the nodes in the given JsonNode Object",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Object getAllElements(Object jsonNode) {
		if (jsonNode == null || !(jsonNode instanceof JsonNode)) {
            throw new IllegalArgumentException("Expected a non null JsonNode instance");
        }
		JsonNode jsonElement = (JsonNode) jsonNode;
		return jsonElement.elements();
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "isObjectNode",
		signature = "boolean isObjectNode(Object jsonNode)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "jsonNode", type = "Object", desc = "JsonNode Object")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "True if node is Object type else false"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns if the passed JsonNode is of Object type",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static boolean isObjectNode(Object jsonNode) {
		if (jsonNode == null || !(jsonNode instanceof JsonNode)) {
            throw new IllegalArgumentException("Expected a non null JsonNode instance");
        }
		JsonNode jsonElement = (JsonNode) jsonNode;
		return jsonElement.isObject();
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getNode",
		signature = "Object getNode(Object baseNode, String fieldName)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "baseNode", type = "Object", desc = "Base JsonNode Object"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Field name")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Json node associated to the field name"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the JsonNode associated to the fieldName directly under the baseNode i.e. immediate children",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Object getNode(Object baseNode, String fieldName) {
		if (baseNode == null || !(baseNode instanceof JsonNode)) {
            throw new IllegalArgumentException("Expected a non null JsonNode instance");
        }
		JsonNode jsonElement = (JsonNode) baseNode;
		return jsonElement.get(fieldName);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "hasField",
		signature = "boolean hasField(Object baseNode, String fieldName)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "baseNode", type = "Object", desc = "Base JsonNode Object"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Field name")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "True if fieldname exists, else false"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns if the field name exists in the given json base node",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static boolean hasField(Object baseNode, String fieldName) {
		if (baseNode == null || !(baseNode instanceof JsonNode)) {
            throw new IllegalArgumentException("Expected a non null JsonNode instance");
        }
		JsonNode jsonElement = (JsonNode) baseNode;
		return jsonElement.has(fieldName);
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "findParent",
		signature = "Object findParent(Object baseNode, String fieldName)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "baseNode", type = "Object", desc = "Base JsonNode Object"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Field name")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Parent JsonNode Object"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Looks for and returns the parent node associated to the field name under the given baseNode tree",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Object findParent(Object baseNode, String fieldName) {
		if (baseNode == null || !(baseNode instanceof JsonNode)) {
            throw new IllegalArgumentException("Expected a non null JsonNode instance");
        }
		JsonNode jsonElement = (JsonNode) baseNode;
		return jsonElement.findParent(fieldName);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "findNode",
		signature = "Object findNode(Object baseNode, String fieldName)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "baseNode", type = "Object", desc = "Base JsonNode Object"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Field name")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Json Node"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Looks for and returns any Json node associated to the field name under the given baseNode tree",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Object findNode(Object baseNode, String fieldName) {
		if (baseNode == null || !(baseNode instanceof JsonNode)) {
            throw new IllegalArgumentException("Expected a non null JsonNode instance");
        }
		JsonNode jsonElement = (JsonNode) baseNode;
		return jsonElement.findPath(fieldName);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "findValueNode",
		signature = "Object getValueNode(Object baseNode, String fieldName)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "baseNode", type = "Object", desc = "Base JsonNode Object"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Field name")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Json value node object"),
		version = "5.3",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Looks for and returns any value Json node associated to the field name under the given baseNode tree",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Object findValueNode(Object baseNode, String fieldName) {
		if (baseNode == null || !(baseNode instanceof JsonNode)) {
            throw new IllegalArgumentException("Expected a non null JsonNode instance");
        }
		JsonNode jsonElement = (JsonNode) baseNode;
		return jsonElement.findValue(fieldName);
	}
}
