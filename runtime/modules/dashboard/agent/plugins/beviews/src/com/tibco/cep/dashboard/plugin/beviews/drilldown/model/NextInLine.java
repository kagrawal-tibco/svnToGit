package com.tibco.cep.dashboard.plugin.beviews.drilldown.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.utils.DrilldownParameterNames;


/**
 * The <code>NextInLine</code> provides all the information needs to go down or drill down from a specific instance of some alert, event, metric or correlation.
 * 
 * @author apatil
 * 
 */
public class NextInLine implements Comparable<NextInLine> {

	private String parentTypeID;

	private String parentInstanceID;

	private String name;

	private String typeID;

	private int count;

	private Map<String, String> additionalParameters;

	private String groupFieldName;

	private Object groupFieldValue;

	private String iteratorId;

	/**
	 * Creates an instance of <code>NextInLine</code>.
	 * 
	 * @param parentTypeID
	 *            The type id of the instance which is being drilled down from
	 * @param parentInstanceID
	 *            The id of the instance which is being drilled down from
	 * @param name
	 *            The name of the element to which we are drilling to
	 * @param typeid
	 *            The type id of the element to which we are drilling to
	 * @param count
	 *            The number of instance data rows which form a part of the drill down
	 */
	public NextInLine(String parentTypeID, String parentInstanceID, String name, String typeid, int count) {
		this(parentTypeID, parentInstanceID, name, typeid, count, new HashMap<String, String>());
	}

	/**
	 * Creates an instance of <code>NextInLine</code>.
	 * 
	 * @param parentTypeID
	 *            The type id of the instance which is being drilled down from
	 * @param parentInstanceID
	 *            The id of the instance which is being drilled down from
	 * @param name
	 *            The name of the element to which we are drilling to
	 * @param typeid
	 *            The type id of the element to which we are drilling to
	 * @param count
	 *            The number of instance data rows which form a part of the drill down
	 * @param additionalParameters
	 *            Any additional parameters (introduced for future enhancements)
	 */
	public NextInLine(String parentTypeID, String parentInstanceID, String name, String typeid, int count, Map<String, String> additionalParameters) {
		this.parentTypeID = parentTypeID;
		this.parentInstanceID = parentInstanceID;
		this.name = name;
		this.typeID = typeid;
		this.count = count;
		this.additionalParameters = additionalParameters;
	}

	/**
	 * The number of instance data rows which form a part of the drill down
	 * 
	 * @return number of instance data rows
	 */
	public int getCount() {
		return count;
	}

	/**
	 * The name of the element to which we are drilling to.
	 * 
	 * @return name of the element
	 */
	public String getName() {
		return name;
	}

	/**
	 * The type id of the element to which we are drilling to
	 * 
	 * @return type id of the element
	 */
	public String getTypeID() {
		return typeID;
	}

	/**
	 * Adds a additional parameter. This parameter is to be used in the link generation, since the drill down <code>TypeHandler</code> implementation may need them for proper functioning.
	 * 
	 * @param name
	 *            The name of the additional parameter
	 * @param value
	 *            The value of the additional parameter
	 */
	public void addParameter(String name, String value) {
		additionalParameters.put(name, value);
		// List values = (List) additionalParameters.get(name);
		// if (values == null){
		// values = new ArrayList();
		// additionalParameters.put(name,values);
		// }
		// if (values.contains(value) == false){
		// values.add(value);
		// }
	}

	/**
	 * Returns a well formed escaped query string which can be used as a part of some hyperlink. <b>The parameter name are escaped by prefixinf '_addp_'.</b>
	 * 
	 * @return a well formed URLEncoded query string
	 * @throws UnsupportedEncodingException
	 */
	public Map<String,Object> getQueryParameters() {
		Map<String,Object> mapParams = new HashMap<String, Object>();
		if (parentTypeID != null) {
			mapParams.put(DrilldownParameterNames.PARENT_TYPE_ID, parentTypeID);
			mapParams.put(DrilldownParameterNames.PARENT_INSTANCE_ID, parentInstanceID);
		}
		mapParams.put(DrilldownParameterNames.TYPE_ID, typeID);
		if (additionalParameters != null && additionalParameters.size() > 0) {
			Iterator<String> keys = additionalParameters.keySet().iterator();
			while (keys.hasNext()) {
				String parameterName = keys.next();
				String parameterValue = additionalParameters.get(parameterName);
				mapParams.put(escapeAdditionalParameterName(parameterName), parameterValue);
			}
		}
		return mapParams;
	}

	/**
	 * Returns a well formed escaped query string which can be used as a part of some hyperlink. <b>The parameter name are escaped by prefixinf '_addp_'.</b>
	 * 
	 * @return a well formed URLEncoded query string
	 * @throws UnsupportedEncodingException
	 */
	public String getQueryString() throws UnsupportedEncodingException {
		StringBuilder buffer = new StringBuilder();
		appendParameter(buffer, "parenttypeid", parentTypeID);
		buffer.append("&");
		appendParameter(buffer, "parentinstanceid", parentInstanceID);
		buffer.append("&");
		appendParameter(buffer, "typeid", typeID);
		if (additionalParameters != null && additionalParameters.size() > 0) {
			Iterator<String> keys = additionalParameters.keySet().iterator();
			while (keys.hasNext()) {
				String parameterName = keys.next();
				String parameterValue = additionalParameters.get(parameterName);
				appendParameter(buffer, escapeAdditionalParameterName(parameterName), parameterValue);
			}
		}		
		return buffer.toString();
	}

	private static void appendParameter(StringBuilder buffer, String name, String value) throws UnsupportedEncodingException {
		buffer.append(name);
		buffer.append("=");
		buffer.append(URLEncoder.encode(value, "UTF-8"));
	}

	public static final String escapeAdditionalParameterName(String name) {
		return "_addp_" + name;
	}

	public static final boolean isAdditionalParameterName(String name) {
		return name.startsWith("_addp_");
	}

	public static final String unescapeAdditionalParameterName(String name) {
		return name.substring(6);
	}

	/**
	 * @return The parent instance id
	 */
	public String getParentInstanceID() {
		return parentInstanceID;
	}

	/**
	 * @return the parent type id
	 */
	public String getParentTypeID() {
		return parentTypeID;
	}

	public String getGroupFieldName() {
		return groupFieldName;
	}

	public void setGroupFieldName(String groupFieldName) {
		this.groupFieldName = groupFieldName;
	}

	public Object getGroupFieldValue() {
		return groupFieldValue;
	}

	public void setGroupFieldValue(Object fieldValue) {
		this.groupFieldValue = fieldValue;
	}

	public void incrementCount(int incrementBy) {
		count = count + incrementBy;
	}

	public void reduceCountTo(int newCount) {
		count = newCount;
	}

	public int compareTo(NextInLine obj) {
		if (obj == this) {
			return 0;
		}
		return name.compareTo(obj.name);
	}

	/**
	 * @param string
	 */
	public void setIteratorId(String iteratorId) {
		this.iteratorId = iteratorId;
	}

	/**
	 * @return
	 */
	public String getIteratorId() {
		return iteratorId;
	}

	
}
