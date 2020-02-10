package com.tibco.cep.webstudio.server.ui.tools;

import com.tomsawyer.drawing.geometry.shared.TSConstPoint;

/**
 * This class is used to hold the data related to model element.
 * 
 * @author dijadhav
 *
 */
public class BpmnModelElement {
	private String elementId;
	private String elementName;
	private String elementLabel;
	private String elementType;
	private String sourceRefernceId;
	private String targetReferenceId;
	private String toolId;
	private boolean isNewElement;
	private boolean isDefaultSeq;
	private String textValue;
	private String definationId;
	private String extendedType;
	private String resourcePath;
	private String resourceName;
	private TSConstPoint mouseLocation;
	private String loopType;
	

	/**
	 * @return the elementId
	 */
	public String getElementId() {
		return elementId;
	}

	/**
	 * @param elementId
	 *            the elementId to set
	 */
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	/**
	 * @return the elementName
	 */
	public String getElementName() {
		return elementName;
	}

	/**
	 * @param elementName
	 *            the elementName to set
	 */
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	/**
	 * @return the elementLabel
	 */
	public String getElementLabel() {
		return elementLabel;
	}

	/**
	 * @param elementLabel
	 *            the elementLabel to set
	 */
	public void setElementLabel(String elementLabel) {
		this.elementLabel = elementLabel;
	}

	/**
	 * @return the elementType
	 */
	public String getElementType() {
		return elementType;
	}

	/**
	 * @param elementType
	 *            the elementType to set
	 */
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	/**
	 * @return the sourceRefernceId
	 */
	public String getSourceRefernceId() {
		return sourceRefernceId;
	}

	/**
	 * @param sourceRefernceId
	 *            the sourceRefernceId to set
	 */
	public void setSourceRefernceId(String sourceRefernceId) {
		this.sourceRefernceId = sourceRefernceId;
	}

	/**
	 * @return the targetReferenceId
	 */
	public String getTargetReferenceId() {
		return targetReferenceId;
	}

	/**
	 * @param targetReferenceId
	 *            the targetReferenceId to set
	 */
	public void setTargetReferenceId(String targetReferenceId) {
		this.targetReferenceId = targetReferenceId;
	}

	/**
	 * @return the toolId
	 */
	public String getToolId() {
		return toolId;
	}

	/**
	 * @param toolId
	 *            the toolId to set
	 */
	public void setToolId(String toolId) {
		this.toolId = toolId;
	}

	/**
	 * @return the isNewElement
	 */
	public boolean isNewElement() {
		return isNewElement;
	}

	/**
	 * @param isNewElement
	 *            the isNewElement to set
	 */
	public void setNewElement(boolean isNewElement) {
		this.isNewElement = isNewElement;
	}

	/**
	 * @return the isDefaultSeq
	 */
	public boolean isDefaultSeq() {
		return isDefaultSeq;
	}

	/**
	 * @param isDefaultSeq
	 *            the isDefaultSeq to set
	 */
	public void setDefaultSeq(boolean isDefaultSeq) {
		this.isDefaultSeq = isDefaultSeq;
	}

	/**
	 * @return the textValue
	 */
	public String getTextValue() {
		return textValue;
	}

	/**
	 * @param textValue
	 *            the textValue to set
	 */
	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	/**
	 * @return the definationId
	 */
	public String getDefinationId() {
		return definationId;
	}

	/**
	 * @param definationId
	 *            the definationId to set
	 */
	public void setDefinationId(String definationId) {
		this.definationId = definationId;
	}

	/**
	 * @return the extendedType
	 */
	public String getExtendedType() {
		return extendedType;
	}

	/**
	 * @param extendedType
	 *            the extendedType to set
	 */
	public void setExtendedType(String extendedType) {
		this.extendedType = extendedType;
	}

	/**
	 * @return the resourcePath
	 */
	public String getResourcePath() {
		return resourcePath;
	}

	/**
	 * @param resourcePath
	 *            the resourcePath to set
	 */
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public TSConstPoint getMouseLocation() {
		return mouseLocation;
	}

	public void setMouseLocation(TSConstPoint mouseLocation) {
		this.mouseLocation = mouseLocation;
	}

	/**
	 * @return the loopType
	 */
	public String getLoopType() {
		return loopType;
	}

	/**
	 * @param loopType the loopType to set
	 */
	public void setLoopType(String loopType) {
		this.loopType = loopType;
	}

}
