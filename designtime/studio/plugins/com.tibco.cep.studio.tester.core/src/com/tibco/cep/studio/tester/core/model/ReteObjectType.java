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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Represents the actual execution object. This wraps
 * 				over
 * 				the BE entity.
 * 			
 * 
 * <p>Java class for ReteObjectType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReteObjectType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;attribute name="timestamp" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;element name="ChangeType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="ASSERT"/>
 *               &lt;enumeration value="MODIFY"/>
 *               &lt;enumeration value="RETRACT"/>
 *               &lt;enumeration value="RULEEXECUTION"/>
 *               &lt;enumeration value="RULEFIRED"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element ref="{www.tibco.com/be/studio/tester}Concept"/>
 *           &lt;element ref="{www.tibco.com/be/studio/tester}Event"/>
 *           &lt;element ref="{www.tibco.com/be/studio/tester}Rule"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReteObjectType", propOrder = {
	"timestamp",
    "changeType",
    "concept",
    "event",
    "rule"
})
public class ReteObjectType {

    @XmlAttribute
    protected String timestamp;
    @XmlElement(name = "ChangeType", required = true)
    protected ReteObjectType.ReteChangeType changeType;
    @XmlElement(name = "Concept")
    protected ConceptType concept;
    @XmlElement(name = "Event")
    protected EventType event;
    @XmlElement(name = "Rule")
    protected RuleType rule;

    /**
     * Gets the value of the changeType property.
     * 
     * @return
     *     possible object is
     *     {@link ReteObjectType.ReteChangeType }
     *     
     */
    public ReteObjectType.ReteChangeType getChangeType() {
        return changeType;
    }

    /**
     * Sets the value of the changeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReteObjectType.ReteChangeType }
     *     
     */
    public void setChangeType(ReteObjectType.ReteChangeType value) {
        this.changeType = value;
    }

    /**
     * Gets the value of the concept property.
     * 
     * @return
     *     possible object is
     *     {@link ConceptType }
     *     
     */
    public ConceptType getConcept() {
        return concept;
    }

    /**
     * Sets the value of the concept property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConceptType }
     *     
     */
    public void setConcept(ConceptType value) {
        this.concept = value;
    }

    /**
     * Gets the value of the event property.
     * 
     * @return
     *     possible object is
     *     {@link EventType }
     *     
     */
    public EventType getEvent() {
        return event;
    }

    /**
     * Sets the value of the event property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventType }
     *     
     */
    public void setEvent(EventType value) {
        this.event = value;
    }

    /**
     * Gets the value of the rule property.
     * 
     * @return
     *     possible object is
     *     {@link RuleType }
     *     
     */
    public RuleType getRule() {
        return rule;
    }

    /**
     * Sets the value of the rule property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleType }
     *     
     */
    public void setRule(RuleType value) {
        this.rule = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     */
    public String getTimestamp() {
		return timestamp;
	}
    
    /**
     * Sets the value of the timestamp property.
     * 
     * @param value - A string corresponding to format
     *     
     */
    public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
    
    /**
     * <p>Java class for null.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p>
     * <pre>
     * &lt;simpleType>
     *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *     &lt;enumeration value="ASSERT"/>
     *     &lt;enumeration value="MODIFY"/>
     *     &lt;enumeration value="RETRACT"/>
     *     &lt;enumeration value="SCHEDULEDTIMEEVENT"/>
     *     &lt;enumeration value="RULEEXECUTION"/>
     *     &lt;enumeration value="RULEFIRED"/>
     *   &lt;/restriction>
     * &lt;/simpleType>
     * </pre>
     * 
     */
    @XmlType(name = "")
    @XmlEnum
    public enum ReteChangeType {

        ASSERT,
        MODIFY,
        RETRACT,
        SCHEDULEDTIMEEVENT,
        RULEEXECUTION,
    	RULEFIRED;

        public String value() {
            return name();
        }

        public static ReteObjectType.ReteChangeType fromValue(String v) {
            return valueOf(v);
        }

    }

}
