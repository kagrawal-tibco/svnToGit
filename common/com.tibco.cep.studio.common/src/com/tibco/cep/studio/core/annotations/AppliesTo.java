/**
 * 
 */
package com.tibco.cep.studio.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;

/**
 * Used to indicate what {@linkplain EVENT_TYPE} this method applies to
 * @author aathalye
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface AppliesTo {
	
	/**
	 * The {@linkplain EVENT_TYPE} to indicate at development time which
	 * type of event should be used with this method.
	 * @see EVENT_TYPE#TIME_EVENT
	 * @see EVENT_TYPE#SIMPLE_EVENT
	 */
	EVENT_TYPE value();
}
