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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConceptType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConceptType">
 *   &lt;complexContent>
 *     &lt;extension base="{www.tibco.com/be/studio/tester}EntityType">
 *       &lt;attribute name="isScorecard" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConceptType")
public class ConceptType
    extends EntityType
{

    @XmlAttribute
    protected Boolean isScorecard;

    /**
     * Gets the value of the isScorecard property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIsScorecard() {
        if (isScorecard == null) {
            return false;
        } else {
            return isScorecard;
        }
    }

    /**
     * Sets the value of the isScorecard property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsScorecard(Boolean value) {
        this.isScorecard = value;
    }

}
