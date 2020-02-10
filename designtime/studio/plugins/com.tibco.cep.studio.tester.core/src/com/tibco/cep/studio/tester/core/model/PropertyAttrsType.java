//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.10.15 at 12:35:44 PM IST 
//


package com.tibco.cep.studio.tester.core.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for PropertyAttrsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PropertyAttrsType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="dataType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="STRING"/>
 *             &lt;enumeration value="INTEGER"/>
 *             &lt;enumeration value="LONG"/>
 *             &lt;enumeration value="DOUBLE"/>
 *             &lt;enumeration value="BOOLEAN"/>
 *             &lt;enumeration value="DATETIME"/>
 *             &lt;enumeration value="CONTAINED_CONCEPT"/>
 *             &lt;enumeration value="CONCEPT_REFERENCE"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="multiple" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyAttrsType", propOrder = {
    "value"
})
@XmlSeeAlso({
    PropertyType.class
})
public class PropertyAttrsType {

    @XmlValue
    protected String value;
    @XmlAttribute
    protected String name;
    @XmlAttribute
    protected PropertyAttrsType.PropertyDataTypeEnum dataType;
    @XmlAttribute
    protected Boolean multiple;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the dataType property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyAttrsType.PropertyDataTypeEnum }
     *     
     */
    public PropertyAttrsType.PropertyDataTypeEnum getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyAttrsType.PropertyDataTypeEnum }
     *     
     */
    public void setDataType(PropertyAttrsType.PropertyDataTypeEnum value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the multiple property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isMultiple() {
        if (multiple == null) {
            return false;
        } else {
            return multiple;
        }
    }

    /**
     * Sets the value of the multiple property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMultiple(Boolean value) {
        this.multiple = value;
    }


    /**
     * <p>Java class for null.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p>
     * <pre>
     * &lt;simpleType>
     *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *     &lt;enumeration value="STRING"/>
     *     &lt;enumeration value="INTEGER"/>
     *     &lt;enumeration value="LONG"/>
     *     &lt;enumeration value="DOUBLE"/>
     *     &lt;enumeration value="BOOLEAN"/>
     *     &lt;enumeration value="DATETIME"/>
     *     &lt;enumeration value="CONTAINED_CONCEPT"/>
     *     &lt;enumeration value="CONCEPT_REFERENCE"/>
     *   &lt;/restriction>
     * &lt;/simpleType>
     * </pre>
     * 
     */
    @XmlType(name = "")
    @XmlEnum
    public enum PropertyDataTypeEnum {

        STRING,
        INTEGER,
        LONG,
        DOUBLE,
        BOOLEAN,
        DATETIME,
        CONTAINED_CONCEPT,
        CONCEPT_REFERENCE;

        public String value() {
            return name();
        }

        public static PropertyAttrsType.PropertyDataTypeEnum fromValue(String v) {
            return valueOf(v);
        }

    }

}
