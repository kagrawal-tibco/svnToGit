package com.tibco.cep.runtime.model.element;

import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 30, 2004
 * Time: 4:05:13 PM
 * To change this template use Options | File Templates.
 */

/**
 * Base for concept and scorecard properties.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
//TODO better comments
public interface Property {


    /**
     * History policy in which a new value is stored in the history only when different from the previous value.
     *
     * @.category public-api
     * @see #getHistoryPolicy
     * @since 2.0.0
     */
    final static int HISTORY_POLICY_CHANGES_ONLY = 0;

    /**
     * History policy in which every set value is stored in the history even if it is the same as the previous value.
     *
     * @.category public-api
     * @see #getHistoryPolicy
     * @since 2.0.0
     */
    final static int HISTORY_POLICY_ALL_VALUES = 1;


    /**
     * Gets the name of the property.
     *
     * @return the name of the property.
     * @.category public-api
     * @since 2.0.0
     */
    String getName();


    /**
     * Gets the history policy of this property
     *
     * @return history policy
     * @.category public-api
     * @see #HISTORY_POLICY_ALL_VALUES
     * @see #HISTORY_POLICY_CHANGES_ONLY
     * @since 2.0.0
     */
    int getHistoryPolicy();


    /**
     * Gets the history size of this property (the number of values stored in its history ring).
     *
     * @return the history size.
     * @.category public-api
     * @since 2.0.0
     */
    int getHistorySize();


    /**
     * Returns the concept instance that this property belongs to.
     *
     * @return the parent of this property.
     * @.category public-api
     * @since 2.0.0
     */
    Concept getParent();


    void serialize(ConceptSerializer serializer, int order);


    void deserialize(ConceptDeserializer deserializer, int order);


    /**
     * Base for properties of type <code>Concept</code>.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public interface PropertyConcept extends Property {


        /**
         * Gets the target <code>Concept</code> type of this property.
         *
         * @return the <code>Class</code> of this property.
         * @.category public-api
         * @since 2.0.0
         */
        Class getType();

        void clearReferences();
    }

    /**
     * Base for properties which refer to a <code>Concept</code>.
     *
     * @.category public-api
     * @see Property.PropertyContainedConcept
     * @since 2.0.0
     */
    public interface PropertyConceptReference extends PropertyConcept {

        boolean maintainReverseRef();
        
        /**
         * Check if reverse reference is disabled in cdd domain object override or property metadata.
         * 
         * @return false if reverse reference is disabled in cdd or property metadata, true otherwise.
         * @param metaProperty the metadata endoded designtime for this property.
         * @param clz concept class to which the property belongs.
         */
        default boolean maintainReverseRef(MetaProperty metaProperty, Class<?> clz) {

        	StringBuilder builder = new StringBuilder("be.engine.cluster.");
        	builder.append(clz.getName());
        	builder.append(".property.");
        	builder.append(this.getName());
        	builder.append(".reverseReferences");
        	
			String revRefPropValue = "";
			RuleSession currentRuleSession = RuleSessionManager.getCurrentRuleSession();
			if (currentRuleSession != null && currentRuleSession.getRuleServiceProvider() != null) {
				revRefPropValue = currentRuleSession.getRuleServiceProvider().getProperties()
						.getProperty(builder.toString());
			}

        	//reverse reference is disabled in cdd
        	if("false".equalsIgnoreCase(revRefPropValue)) {
        		return false;
        	}
        	//in case property belongs to super concept
        	if(clz.getSuperclass() != null && clz.getSuperclass().getName().startsWith("be.gen.")) {
        		return maintainReverseRef(metaProperty, clz.getSuperclass());
        	}
        	
        	//if no override, return designtime property metadata
            return metaProperty.getMaintainReverseRefs();
        }
    }

    /**
     * Base for properties which contain a <code>Concept</code>.
     *
     * @.category public-api
     * @see Property.PropertyConceptReference
     * @since 2.0.0
     */
    public interface PropertyContainedConcept extends PropertyConcept {


    }

    /**
     * Base for properties which contain a boolean value.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public interface PropertyBoolean extends Property {


    }

//    public interface PropertyChar extends Property {}

    /**
     * Base for properties which contain a date and time.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public interface PropertyDateTime extends Property {


    }

    /**
     * Base for properties which contain a <code>double</code> value.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public interface PropertyDouble extends Property {


    }

    /**
     * Base for properties which contain an <code>int</code> value.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public interface PropertyInt extends Property {


    }

    /**
     * Base for properties which contain a <code>long</code> value.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public interface PropertyLong extends Property {


    }

    /**
     * Base for properties which contain a <code>String</code> value.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public interface PropertyString extends Property {


    }

    public interface PropertyStateMachine extends Property.PropertyContainedConcept {
        void startMachine();
        StateMachineConcept getStateMachineConcept();
    }
}