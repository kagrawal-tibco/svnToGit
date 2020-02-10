/**
 * 
 */
package com.tibco.cep.liveview.project;

/**
 * @author vpatil
 *
 */
public interface LVConfigConstants {
	public static final String LV_XML_PREFIX = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	public static final String LV_CONFIG_FILE_EXTN = "lvconf";
	
	public static final String LV_CONFIGURATION_START_TAG = "<liveview-configuration xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://www.streambase.com/schemas/lvconf/\">";
	public static final String LV_CONFIGURATION_END_TAG = "</liveview-configuration>";
	
	public static final String LV_DATA_TABLE_START_TAG = "<data-table id=\"%s\">";
	public static final String LV_DATA_TABLE_END_TAG = "</data-table>";
	
	public static final String LV_FIELDS_START_TAG = "<fields>";
	public static final String LV_FIELDS_END_TAG = "</fields>";
	
	public static final String LV_FIELD_TAG = "<field name=\"%s\" type=\"%s\"/>";
	public static final String LV_FIELD_REF_TAG = "<field ref=\"%s\"/>";
	
	public static final String LV_DELETE_RULE_TAG = "<table-delete-rule>%s</table-delete-rule>";
	
	public static final String LV_FIELD_RULES_TAG = "<field-rules>\n<rule description=\"Setting table trimming field to current timestamp\">\n<default>\n<insert field=\"%1$s\">now()</insert>\n<update field=\"%1$s\">now()</update>\n</default>\n</rule>\n</field-rules>";
	
	public static final String LV_PRIMARY_KEY_START_TAG = "<primary-key>";
	public static final String LV_PRIMARY_KEY_END_TAG = "</primary-key>";
	
	public static final String LV_INDICES_START_TAG = "<indices>";
	public static final String LV_INDICES_END_TAG = "</indices>";
	
	public static final String LV_INDEX_START_TAG = "<index>";
	public static final String LV_INDEX_END_TAG = "</index>";
	
	public static final String LV_BE_SYSTEM_FIELD_ID = "id";
	public static final String LV_BE_SYSTEM_FIELD_EXTID = "extId";
	public static final String LV_BE_SYSTEM_FIELD_TYPEID = "typeId__";
	public static final String LV_BE_SYSTEM_FIELD_VERSION = "version__";
	public static final String LV_BE_SYSTEM_FIELD_DELETED = "deleted__";
	public static final String LV_BE_SYSTEM_FIELD_PARENT = "parent__";
	public static final String LV_BE_SYSTEM_FIELD_REVERSE_REF = "rrf__";
	public static final String LV_BE_SYSTEM_FIELD_PAYLOAD = "payload__";	
}
