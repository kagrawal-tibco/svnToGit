package com.tibco.cep.studio.tester.ui.editor.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.tester.core.model.ConceptType;
import com.tibco.cep.studio.tester.core.model.EntityType;
import com.tibco.cep.studio.tester.core.model.EventType;
import com.tibco.cep.studio.tester.core.model.PropertyModificationType;
import com.tibco.cep.studio.tester.core.model.PropertyType;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractTestViewer {
	
	protected static final Font DEFAULT_FONT = new Font(null, new FontData("Tahoma", 12, SWT.BOLD /*| SWT.ITALIC*/));

	protected String entity;
	protected String entityType;
	protected String entityName;
	protected String fullpath;
	protected Vector<String> referenceConcept;
	protected Vector<String> referenceEvent;
	protected Vector<String> resultDataChangeColumns = null;
	protected List<Integer> resultColList = null;
	
	protected Vector<Boolean> multipleProperty;
	protected Vector<Integer> dataType;
	protected Vector<String> resourcePath;
	
	protected final int SMALL_BUTTON_SIZE = 12;

	protected IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	
	private boolean IsEditorPopupMenuEnabled = false;
	
	protected boolean rowInserted = false;
	protected boolean rowRemoved = false;
	protected boolean rowUpdated = false;
	protected String projectName;
	protected TestDataEditor editor;
	
	protected Composite container;
	protected ManagedForm managedForm;
//	protected Composite swtAwtComponent;
	protected Section testDatasection;
	protected Text resourceText;
	
	protected int removedRow = -1;
	
	protected boolean modifiedResult = false;
	
	private static final int UNDERLINE 		= 12; // TODO : This is a Windows constant, might not work for UNIX platforms
	private static final int STRIKETHROUGH 	= 13; // TODO : This is a Windows constant, might not work for UNIX platforms

	/**
	 * @param domainEntries
	 * @param value
	 * @return
	 */
	public boolean isDuplicateDomainEntry(String[] domainEntries,String value){
		for(String actVal:domainEntries){
			if(actVal.equalsIgnoreCase(value))
				return true;
		}
		return false;
	}

	/**
	 * @param isMultiple
	 * @param dataType
	 * @return
	 */
	public static String getImageIcon(boolean isMultiple, int dataType) {
		if (dataType == PropertyDefinition.PROPERTY_TYPE_STRING) {
			if (isMultiple)
				return "/icons/iconStringM16.gif";
			else
				return "/icons/iconString16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_INTEGER) {
			if (isMultiple)
				return "/icons/iconIntegerM16.gif";
			else
				return "/icons/iconInteger16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_LONG) {
			if (isMultiple)
				return "/icons/iconLongM16.gif";
			else
				return "/icons/iconLong16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_REAL) {
			if (isMultiple)
				return "/icons/iconRealM16.gif";
			else
				return "/icons/iconReal16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_BOOLEAN) {
			if (isMultiple)
				return "/icons/iconBooleanM16.gif";
			else
				return "/icons/iconBoolean16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_DATETIME) {
			if (isMultiple)
				return "/icons/iconDateM16.gif";
			else
				return "/icons/iconDate16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {
			if (isMultiple)
				return "/icons/iconConceptM16.gif";
			else
				return "/icons/iconConcept16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
			if (isMultiple)
				return "/icons/iconConceptRefM16.gif";
			else
				return "/icons/iconConceptRef16.gif";
		}
		return "";
	}
	
	public static String getImage(boolean isMultiple, int dataType) {
		if (dataType == PropertyDefinition.PROPERTY_TYPE_STRING) {
			if (isMultiple)
				return "iconStringM16.gif";
			else
				return "iconString16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_INTEGER) {
			if (isMultiple)
				return "iconIntegerM16.gif";
			else
				return "iconInteger16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_LONG) {
			if (isMultiple)
				return "iconLongM16.gif";
			else
				return "iconLong16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_REAL) {
			if (isMultiple)
				return "iconRealM16.gif";
			else
				return "iconReal16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_BOOLEAN) {
			if (isMultiple)
				return "iconBooleanM16.gif";
			else
				return "iconBoolean16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_DATETIME) {
			if (isMultiple)
				return "iconDateM16.gif";
			else
				return "iconDate16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {
			if (isMultiple)
				return "iconConceptM16.gif";
			else
				return "iconConcept16.gif";
		}
		if (dataType == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
			if (isMultiple)
				return "iconConceptRefM16.gif";
			else
				return "iconConceptRef16.gif";
		}
		return "";
	}
	

	/**
	 * @param refCon
	 * @param filename
	 * @return
	 */
	public Vector<String> getRefId(String refCon, String filename) {
		BufferedReader in = null;
		Vector<String> refID = new Vector<String>();
		try {
			in = new BufferedReader(new FileReader(filename));
			@SuppressWarnings("unused")
			String line;
			int count = 0;
			try {
				while ((line = in.readLine()) != null) {
					refID.add(refCon + " Ref ID [" + count + "]");
					count++;
				}
			} catch (IOException e) {
				StudioTesterUIPlugin.log(e);
			}
		} catch (FileNotFoundException e) {
			StudioTesterUIPlugin.log(e);
		}
		return refID;
	}

	/**
	 * @param vector
	 * @param entityType
	 * @param path
	 * @param projectName
	 * @param isOldResultTable
	 * @return
	 */
	public Map<String, Vector<Vector<? extends Object>>> saveResult( Vector<Vector<? extends Object>> vector, 
															         EntityType entityType, 
															         String path, 
															         String projectName, 
															         boolean isOldResultTable) {
		Map<String, Vector<Vector<? extends Object>>> omEntityToVectorMap = new HashMap<String, Vector<Vector<? extends Object>>>();
		if (resultDataChangeColumns == null) {
			resultDataChangeColumns = new Vector<String>();
		} else {
			resultDataChangeColumns.clear();
		}
		try {
			Entity entity = IndexUtils.getEntity(projectName, path);
			Vector<String> instanceVector = null;
			if (entityType instanceof ConceptType) {
				Concept concept = (Concept)entity;
				ConceptType conceptType = (ConceptType) entityType;
				instanceVector = new Vector<String>();
				instanceVector.add(conceptType.getId() == null ? "" : new Long(conceptType.getId()).toString());
				instanceVector.add(conceptType.getExtId() == null ? "" : conceptType.getExtId());
				vector.add(getPropertyValues(conceptType.getProperty(),
						conceptType.getModifiedProperty(), concept.getAllPropertyDefinitions(), instanceVector, isOldResultTable));
				omEntityToVectorMap.put(conceptType.getNamespace(),	vector);
			} else if (entityType instanceof EventType) {
				Event event = (Event)entity;
				EventType eventType = (EventType) entityType;
				instanceVector = new Vector<String>();
				instanceVector.add(eventType.getId() == null ? "" : new Long(eventType.getId()).toString());
				instanceVector.add(eventType.getExtId() == null ? "" : eventType.getExtId());
				vector.add(getPropertyValues(eventType.getProperty(), 
						eventType.getModifiedProperty(), event.getAllUserProperties(), instanceVector, isOldResultTable));
				omEntityToVectorMap.put(eventType.getNamespace(), vector);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return omEntityToVectorMap;
		
	}
	
	/**
	 * @param columnName
	 * @param list
	 * @return
	 */
	protected int getIndex(String columnName, EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> list) {
		int count = 0;
		for (com.tibco.cep.designtime.core.model.element.PropertyDefinition propertyDefinition : list) {
			if (propertyDefinition.getName().equals(columnName)) {
				return count;
			}
			count ++;
		}
		return -1;
	}

	/**
	 * @param prop
	 * @param modProp
	 * @param properties
	 * @param instanceVector
	 * @param isOldResultTable
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused") 
	public Vector<String> getPropertyValues(List<PropertyType> prop, 
			                                List<PropertyModificationType> modProp,
			                                EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> properties,
											Vector<String> instanceVector, 
											boolean isOldResultTable)  throws Exception {
		for (com.tibco.cep.designtime.core.model.element.PropertyDefinition propertyDefinition : properties) {
			instanceVector.add("");
		}
		for (PropertyType propertyType : prop) {
			int index = getIndex(propertyType.getName(), properties);
			if (index != -1) {
				boolean isDouble = false;
				if (properties.get(index).getType() == PROPERTY_TYPES.DOUBLE) {
					isDouble = true;
				}
				String val = propertyType.getValue();
				instanceVector.set(index + 2, getActualValue(isDouble, val));
			}
		}
		for (PropertyModificationType propModType: modProp) {
			int index = getIndex(propModType.getName(), properties);
			index = getIndex(propModType.getName(), properties);
			resultDataChangeColumns.add(propModType.getName());
			if (index != -1) {
				boolean isDouble = false;
				if (properties.get(index).getType() == PROPERTY_TYPES.DOUBLE) {
					isDouble = true;
				}
				String oldval = getActualValue(isDouble, propModType.getPreviousValue().getValue());
				String newval = getActualValue(isDouble, propModType.getNewValue().getValue());
				instanceVector.set(index + 2, isOldResultTable ? oldval : newval);
			}
		}
		return instanceVector;
	}
	
	/**
	 * @param dataType
	 * @param val
	 * @return
	 */
	private String getActualValue(boolean isDouble, String val) {
		if (isDouble) {
			if (val.contains("E") || val.contains("e")) {
				double d = Double.parseDouble(val);
				val = String.valueOf(d);
			}
		}
		return val;
	}

	/**
	 * @param eventType
	 * @param event
	 * @param instanceVector
	 * @return
	 * @throws Exception
	 */
	public Vector<String> getPropertyValues(EventType eventType, Event event, 
			Vector<String> instanceVector)  throws Exception {
		List<PropertyType> prop = eventType.getProperty();
		EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> properties = event.getAllUserProperties();
		if (prop != null)
			for (int r = 0; r < prop.size(); r++) {
				try {
					if (prop.get(r) != null) {
						if (properties.get(r).getType() == PROPERTY_TYPES.DATE_TIME ) {
//							Calendar calendar = (GregorianCalendar) event
//									.getProperty(prop[r]);
//							instanceVector.add(TestUtil.SIMPLE_DATE_FORMAT.format(calendar.getTime()));
							instanceVector.add(prop.get(r).getValue());
						} else {
							instanceVector.add(prop.get(r).getValue());
						}
					} else {
						instanceVector.add("null");
					}
				} catch (Exception e) {
					StudioTesterUIPlugin.log(e);
				}
			}
		return instanceVector;
	}
	
	public boolean isEditableCol(int col) {
		for (int column : resultColList)
			if (column == col)
				return true;
		return false;
	}

	/**
	 * @param fontString
	 * @return
	 */
	private String[] convertFontString(String fontString) {
		StringTokenizer st = new StringTokenizer(fontString, "|");
		List<String> data = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			data.add(st.nextToken());
		}
		String[] arr = new String[data.size()];
		data.toArray(arr);
		return arr;
	}
	
	private boolean checkResult(String col) {
		for (int k = 0; k < resultDataChangeColumns.size(); k++) {
			if (resultDataChangeColumns.get(k).equals(col)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Simple Data Type
	 * @param node
	 * @param v
	 * @param value
	 */
	private void setColumnValue(XiNode node, Vector<String> v, String value) {
		XiNode typeNode = node.getAttribute(ExpandedName.makeName("type"));
		if (typeNode != null && ( typeNode.getStringValue().equals(PROPERTY_TYPES.CONCEPT_REFERENCE.getName()) ||
				typeNode.getStringValue().equals(PROPERTY_TYPES.CONCEPT.getName()))) {
			String fValue = node.getAttributeStringValue(ExpandedName.makeName("rowNum"));
			v.add(fValue);
		} else {
			v.add(value);
		}
	}
	
	/**
	 * Multiple Data Type
	 * @param mulNodes
	 * @param v
	 */
	private void setColumnValue(List<XiNode> mulNodes, Vector<String> v) {
		String fValue = "";
		XiNode typeNode = mulNodes.get(0).getAttribute(ExpandedName.makeName("type"));
		if (typeNode != null && ( typeNode.getStringValue().equals(PROPERTY_TYPES.CONCEPT_REFERENCE.getName()) ||
				typeNode.getStringValue().equals(PROPERTY_TYPES.CONCEPT.getName()))) {
			fValue = mulNodes.get(0).getAttributeStringValue(ExpandedName.makeName("rowNum"));
			for (int l = 1; l < mulNodes.size(); l++) {
				fValue = fValue	+ TesterCoreUtils.SPLIT + mulNodes.get(l).getAttributeStringValue(ExpandedName.makeName("rowNum"));
			}

		} else {
			fValue = mulNodes.get(0).getStringValue().toString();
			for (int l = 1; l < mulNodes.size(); l++) {
				fValue = fValue	+ TesterCoreUtils.SPLIT + mulNodes.get(l).getStringValue();
			}
		}
		v.add(fValue);
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	

	public Vector<String> getReferenceEvent() {
		return referenceEvent;
	}

	public void setReferenceEvent(Vector<String> referenceEvent) {
		this.referenceEvent = referenceEvent;
	}
	
	public Vector<String> getReferenceConcept() {
		return referenceConcept;
	}

	public void setReferenceConcept(Vector<String> referenceConcept) {
		this.referenceConcept = referenceConcept;
	}
	
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	public String getFullpath() {
		return fullpath;
	}

	public void setFullpath(String fullpath) {
		this.fullpath = fullpath;
	}

	public boolean isRowUpdated() {
		return rowUpdated;
	}

	public void setRowUpdated(boolean rowUpdated) {
		this.rowUpdated = rowUpdated;
	}

	public boolean isRowInserted() {
		return rowInserted;
	}

	public void setRowInserted(boolean rowInserted) {
		this.rowInserted = rowInserted;
	}

	public boolean isRowRemoved() {
		return rowRemoved;
	}

	public void setRowRemoved(boolean rowRemoved) {
		this.rowRemoved = rowRemoved;
	}

	public boolean isModifiedResult() {
		return modifiedResult;
	}
}