//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.10.15 at 12:35:44 PM IST 
//


package com.tibco.cep.studio.tester.core.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Root of the tester results XML.
 * 			
 * 
 * <p>Java class for TesterResultsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TesterResultsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RunName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ExecutionObject" type="{www.tibco.com/be/studio/tester}ExecutionObjectType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="project" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="TesterResults", namespace="www.tibco.com/be/studio/tester")
@XmlType(name = "TesterResultsType", propOrder = {
    "runName",
    "executionObject"
})
public class TesterResultsType {

    @XmlElement(name = "RunName", required = true)
    protected String runName;
    @XmlElement(name = "ExecutionObject")
    protected List<ExecutionObjectType> executionObject;
    @XmlAttribute(namespace = "www.tibco.com/be/studio/tester")
    protected String project;

    /**
     * Gets the value of the runName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunName() {
        return runName;
    }

    /**
     * Sets the value of the runName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunName(String value) {
        this.runName = value;
    }

    /**
     * Gets the value of the executionObject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the executionObject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExecutionObject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExecutionObjectType }
     * 
     * 
     */
    public List<ExecutionObjectType> getExecutionObject() {
        if (executionObject == null) {
            executionObject = new ArrayList<ExecutionObjectType>();
        }
        return this.executionObject;
    }

    /**
     * Gets the value of the project property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProject() {
        return project;
    }

    /**
     * Sets the value of the project property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProject(String value) {
        this.project = value;
    }

}
