/*
 * Copyright (c) 2004-2013.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.jdbcstore.impl;

import java.util.ArrayList;
import java.util.List;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;
import com.tibco.cep.runtime.service.cluster.backingstore.EntityDescription;

// This class construct property information of a given java class
public  class ConceptDescription implements EntityDescription {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(ConceptDescription.class);
    private ArrayList _properties = new ArrayList();
    private boolean _isContained = false;
    protected com.tibco.cep.runtime.model.element.Concept _TEMPLATE;
    protected Class _conceptClass;
    protected boolean _isMetric = false;
    protected boolean _isProcess = false;
    //protected _isStateMachine = false;

    public ConceptDescription(String conceptClassName, Class conceptClass, boolean isProcess) throws Exception{
        this._conceptClass = conceptClass;
        this._isProcess = isProcess;
        initialize();
    }

    /**
     *
     * @return
     */
    public String getImplClass() {
        return this._conceptClass.getName();
    }

    public String getConceptSimpleName() {
        return this._conceptClass.getSimpleName();
    }

    // How do we handle state machine property inside the parent class?
    protected void initialize() throws Exception{
        if (com.tibco.cep.runtime.model.element.Metric.class.isAssignableFrom(_conceptClass)) {
        	_isMetric = true;
        }
        
        _TEMPLATE = (com.tibco.cep.runtime.model.element.Concept) this._conceptClass.newInstance();

        setContained(_TEMPLATE instanceof ContainedConcept);
        Property[] allProperties = _TEMPLATE.getProperties();

        for (int i=0; i < allProperties.length;i++) {
            Property property= allProperties[i];
            ConceptProperty p = new ConceptProperty();
            p.propertyName = property.getName();
            p.isArray = (property instanceof PropertyArray);
            p.historySize = property.getHistorySize();
            if (p.isArray || p.historySize > 0) {
                p.isSingleValue = false;
            }
            else {
                p.isSingleValue = true;
            }
            
            if (property instanceof PropertyImpl) {
                p.modifiedIndex = ((PropertyImpl)property).modifiedIndex();
            } else {
                p.modifiedIndex = -1;
            }
            
            if (property instanceof Property.PropertyString) {
                p.type = RDFTypes.STRING_TYPEID;
            } else if (property instanceof Property.PropertyBoolean) {
                p.type = RDFTypes.BOOLEAN_TYPEID;
            } else if (property instanceof Property.PropertyDateTime) {
                p.type = RDFTypes.DATETIME_TYPEID;
            } else if (property instanceof Property.PropertyDouble) {
                p.type = RDFTypes.DOUBLE_TYPEID;
            } else if (property instanceof Property.PropertyLong) {
                p.type = RDFTypes.LONG_TYPEID;
            } else if (property instanceof Property.PropertyInt) {
                p.type = RDFTypes.INTEGER_TYPEID;
            } else if (property instanceof Property.PropertyContainedConcept) {
                p.type = RDFTypes.CONCEPT_TYPEID;
                p.referredToConceptPath =  ((Property.PropertyContainedConcept) property).getType().getName();
                logger.log(Level.DEBUG, "ConceptDescription - contained concept path: %s", p.referredToConceptPath);
            } else if (property instanceof Property.PropertyConceptReference) {
                p.type = RDFTypes.CONCEPT_REFERENCE_TYPEID;
                p.referredToConceptPath =  ((Property.PropertyConceptReference) property).getType().getName();
                logger.log(Level.DEBUG, "ConceptDescription - referenced concept path: %s", p.referredToConceptPath);
            } else {
                throw new RuntimeException ("UnIdentified Property Type " + property);
            }
            p.index=i;
            _properties.add(p);
        }
    }

    /**
     *
     * @return
     */
    public List getProperties() {
        return _properties;
    }

    /**
     *
     * @return
     */
    public boolean isContained() {
        return _isContained;
    }
    
    /**
     *
     * @return
     */
    public boolean isMetric() {
    	return _isMetric;
   	}
    
    /**
     *
     * @return
     */
    public boolean isProcess() {
        return _isProcess;
    }
    
    /**
     *
     * @param contained
     */
    public void setContained(boolean contained) {
        _isContained = contained;
    }

    public class ConceptProperty {
        public int index;
        public String propertyName;
        public int type;
        public boolean isSingleValue;
        public boolean isArray;
        public int historySize;
        public String referredToConceptPath;
        public int modifiedIndex;

        public boolean sameAs(ConceptProperty other) {
            return (propertyName.equalsIgnoreCase(other.propertyName)) &&
                   (type == other.type) &&
                   (isArray == other.isArray) &&
                    checkConceptReference(other);
        }

        boolean checkConceptReference(ConceptProperty other) {
            if ((other.referredToConceptPath != null) && (referredToConceptPath != null)) {
                return referredToConceptPath.equals(other.referredToConceptPath);
            } else if ((other.referredToConceptPath == null) && (referredToConceptPath == null)) {
                return true;
            } else {
                return false;
            }
        }
    }

    // FIX THIS - what is this for?
    class PropertyDescriptionImpl implements ConceptSerializer.PropertyDescription {
        int prev_index;
        int cur_index;
        Property deletedProperty;

        protected PropertyDescriptionImpl(int prev_index, int cur_index) {
            this.prev_index=prev_index;
            this.cur_index=cur_index;
        }

        protected PropertyDescriptionImpl(int prev_index, Property deletedProperty) {
            this.prev_index=prev_index;
            this.cur_index=-1;
            this.deletedProperty=deletedProperty;
        }

        public int previousIndex() {
            return prev_index;
        }

        public int currentIndex() {
            return cur_index;
        }
        public Property getDeletedProperty() {
            return deletedProperty;
        }
    }
}
