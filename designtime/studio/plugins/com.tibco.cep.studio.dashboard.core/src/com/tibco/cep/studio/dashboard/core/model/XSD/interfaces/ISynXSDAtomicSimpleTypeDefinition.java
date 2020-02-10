package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

/**
 * @ *
 */
public interface ISynXSDAtomicSimpleTypeDefinition extends ISynXSDSimpleTypeDefinition {

	/**
	 * Returns the primitive java type used to define this simple type If null then return the primitive java type from getBase().getJavaType()
	 *
	 * @return ISynXSDSimpleTypeDefinition A java .class object corresponding to the java primitive type
	 * @see
	 */
	public Class<?> getJavaType();

}
