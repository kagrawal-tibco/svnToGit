
package com.tibco.cep.studio.mapper.ui.xmlui;

import javax.swing.JPanel;

import com.tibco.cep.studio.mapper.ui.data.DataIcons;

/**
 * Resource constants for {@link QNamePanel}
 */
public class QNamePanelResources extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// For qname panel:
	public static final String SUGGESTED;

	// For non-suggestion selection;
	public static final String OTHER;

	public static final String LOCATION;

	public static final String BY_LOCATION;
	public static final String BY_NAMESPACE;
	public static final String BY_SEARCH;
	public static final String PREVIEW;

	public static final String KEEP_OLD_IMPORT;
	public static final String REPLACE_OLD_IMPORT;
	public static final String REFERENCE_BY_LOCATION;
	public static final String IMPORT_CONFLICT;
	public static final String IMPORT_CONFLICT_TITLE;

	// For lack of a better place, these are really xsd specific (but pretty generic)
	public static final String DOCUMENTATION;
	public static final String TREE;

	// Likewise, these are from the qname panel search:
	public static final String SEARCHING;
	public static final String NAME;
	public static final String SEARCH_BY;
	public static final String CASE_SENSITIVE;

	public static final String SUBSTITUTES_FOR;

	// TODO these should be shared w/ the WSDL editor, couldn't find those.
	public static final String PORT_TYPE;
	public static final String OPERATION;
	public static final String SERVICE;
	public static final String PORT;
	public static final String MESSAGE;

	public static final String STYLE;
	public static final String SOAP_ACTION;
	public static final String ENDPOINT;
	public static final String DESTINATION_TYPE;
	public static final String DESTINATION_NAME;
	public static final String DESTINATION_TYPE_QUEUE;
	public static final String DESTINATION_TYPE_TOPIC;
	public static final String INPUT_MESSAGE;
	public static final String OUTPUT_MESSAGE;
	public static final String FAULT_MESSAGE;
	public static final String MESSAGE_XXX_HAS_NO_PARTS;
	public static final String MESSAGE_PART_XXX_IS_DEFINED_USING_YYY;
	public static final String TYPE_YYY_IN_NAMESPACE_ZZZ;
	public static final String ELEMENT_YYY_IN_NAMESPACE_ZZZ;
	public static final String NO_MESSAGE_FOUND_IN_OPERATION_XXX;
	public static final String ANY_ELEMENT_OR_TYPE;
	public static final String UNABLE_TO_FIND_XXX_FOR_PART_YYY;
	public static final String MESSAGE_PART_IS_NULL;
	public static final String MESSAGE_IS_NULL;
	public static final String INTERNAL_ERROR_EXCEPTION_MAKING_PREVIEW;
	public static final String INTERNAL_ERROR;
	public static final String INTERNAL_ERROR_NULL_COMPONENT_RETURNED_BY;
	public static final String NO_SCHEMA_FOUND;
	public static final String UNKNOWN_MESSAGE;

	static {
		SUGGESTED = getLabel("suggested");
		OTHER = getLabel("other");
		LOCATION = getLabel("location");
		BY_LOCATION = getLabel("bylocation");
		BY_NAMESPACE = getLabel("bynamespace");
		BY_SEARCH = getLabel("bysearch");
		PREVIEW = getLabel("preview");

		IMPORT_CONFLICT = getLabel("importconflict");
		IMPORT_CONFLICT_TITLE = getTitle("importconflict");

		KEEP_OLD_IMPORT = getLabel("keepoldimport");
		REPLACE_OLD_IMPORT = getLabel("replaceoldimport");
		REFERENCE_BY_LOCATION = getLabel("referencebylocation");

		DOCUMENTATION = getLabel("documentation");
		TREE = getLabel("tree");

		SEARCHING = getLabel("searching");
		NAME = getLabel("name");
		SEARCH_BY = getLabel("searchby");
		CASE_SENSITIVE = getLabel("casesensitive");

		SUBSTITUTES_FOR = getLabel("substitutesfor");

		PORT_TYPE = getLabel("porttype");
		OPERATION = getLabel("operation");
		MESSAGE = getLabel("message");
		SERVICE = getLabel("service");
		PORT = getLabel("port");
		STYLE = getLabel("style");
		SOAP_ACTION = getLabel("soapaction");
		ENDPOINT = getLabel("endpoint");
		INPUT_MESSAGE = getLabel("inputMessage");
		OUTPUT_MESSAGE = getLabel("outputMessage");
		FAULT_MESSAGE = getLabel("faultMessage");
		DESTINATION_TYPE = getLabel("destinationType");
		DESTINATION_NAME = getLabel("destinationName");
		DESTINATION_TYPE_QUEUE = getLabel("destinationTypeQueue");
		DESTINATION_TYPE_TOPIC = getLabel("destinationTypeTopic");
		UNKNOWN_MESSAGE = getLabel("unnamedMessage");
		MESSAGE_XXX_HAS_NO_PARTS = getLabel("message_xxx_has_no_parts");
		MESSAGE_PART_XXX_IS_DEFINED_USING_YYY = getLabel("message_part_xxx_is_defined_using_yyy");
		TYPE_YYY_IN_NAMESPACE_ZZZ = getLabel("type_yyy_in_namespace_zzz");
		ELEMENT_YYY_IN_NAMESPACE_ZZZ = getLabel("element_yyy_in_namespace_zzz");
		NO_MESSAGE_FOUND_IN_OPERATION_XXX = getLabel("no_message_found_in_operation_xxx");
		ANY_ELEMENT_OR_TYPE = getLabel("any_element_or_type");
		UNABLE_TO_FIND_XXX_FOR_PART_YYY = getLabel("unable_to_find_xxx_for_part_yyy");
		MESSAGE_PART_IS_NULL = getLabel("message_part_is_null");
		MESSAGE_IS_NULL = getLabel("message_is_null");

		INTERNAL_ERROR_EXCEPTION_MAKING_PREVIEW = getLabel("internal_error_exception_making_preview");
		INTERNAL_ERROR = getLabel("internal_error");
		INTERNAL_ERROR_NULL_COMPONENT_RETURNED_BY = getLabel("internal_error_null_component_returned_by");
		NO_SCHEMA_FOUND = getLabel("no_schema_found");
	}

	private static String getLabel(String name) {
		return DataIcons.getString("ae.qname." + name + ".label");
	}

	private static String getTitle(String name) {
		return DataIcons.getString("ae.qname." + name + ".title");
	}
}
