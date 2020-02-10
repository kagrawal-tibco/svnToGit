//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.04.12 at 02:08:33 AM PDT 
//


package com.tibco.cep.bemm.persistence.topology.model;

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
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd}clusters"/>
 *         &lt;element ref="{http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd}host-resources" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "description",
    "clusters",
    "hostResources"
})
@XmlRootElement(name = "site")
public class Site {

    protected String description;
    @XmlElement(required = true)
    protected Clusters clusters;
    @XmlElement(name = "host-resources")
    protected HostResources hostResources;
    @XmlAttribute
    protected String name;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the clusters property.
     * 
     * @return
     *     possible object is
     *     {@link Clusters }
     *     
     */
    public Clusters getClusters() {
        return clusters;
    }

    /**
     * Sets the value of the clusters property.
     * 
     * @param value
     *     allowed object is
     *     {@link Clusters }
     *     
     */
    public void setClusters(Clusters value) {
        this.clusters = value;
    }

    /**
     * Gets the value of the hostResources property.
     * 
     * @return
     *     possible object is
     *     {@link HostResources }
     *     
     */
    public HostResources getHostResources() {
        return hostResources;
    }

    /**
     * Sets the value of the hostResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostResources }
     *     
     */
    public void setHostResources(HostResources value) {
        this.hostResources = value;
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

}
