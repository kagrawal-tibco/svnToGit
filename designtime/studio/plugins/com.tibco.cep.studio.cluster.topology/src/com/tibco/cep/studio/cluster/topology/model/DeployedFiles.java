//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.04.06 at 02:03:38 AM PDT 
//


package com.tibco.cep.studio.cluster.topology.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd}cdd-deployed"/>
 *         &lt;element ref="{http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd}ear-deployed"/>
 *       &lt;/all>
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
@XmlRootElement(name = "deployed-files")
public class DeployedFiles {

    @XmlElement(name = "cdd-deployed", required = true)
    protected String cddDeployed;
    @XmlElement(name = "ear-deployed", required = true)
    protected String earDeployed;

    /**
     * Gets the value of the cddDeployed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCddDeployed() {
        return cddDeployed;
    }

    /**
     * Sets the value of the cddDeployed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCddDeployed(String value) {
        this.cddDeployed = value;
     
    }
  
    /**
     * Gets the value of the earDeployed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEarDeployed() {
        return earDeployed;
    }

    /**
     * Sets the value of the earDeployed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEarDeployed(String value) {
        this.earDeployed = value;
    }

}
