//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.04.06 at 02:03:38 AM PDT 
//


package com.tibco.cep.studio.cluster.topology.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd}tra" minOccurs="0"/>
 *         &lt;element ref="{http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd}home"/>
 *       &lt;/all>
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" default="4.0.0" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "be")
public class Be {

    protected String tra;
    @XmlElement(required = true)
    protected String home;
    @XmlAttribute
    protected String version;

    /**
     * Gets the value of the tra property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTra() {
        return tra;
    }

    /**
     * Sets the value of the tra property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTra(String value) {
        this.tra = value;
    }

    /**
     * Gets the value of the home property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHome() {
        return home;
    }

    /**
     * Sets the value of the home property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHome(String value) {
        this.home = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        if (version == null) {
            return "5.0.0";
        } else {
            return version;
        }
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
