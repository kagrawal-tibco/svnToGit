/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Visualization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the concrete subclass of SeriesConfig to handle state[step] formatting.
 * 			Contains reference to a State
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateVisualization#getStateRefID <em>State Ref ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getStateVisualization()
 * @model extendedMetaData="name='StateVisualization' kind='elementOnly'"
 * @generated
 */
public interface StateVisualization extends OneDimVisualization {
	/**
	 * Returns the value of the '<em><b>State Ref ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Ref ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Ref ID</em>' attribute.
	 * @see #setStateRefID(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getStateVisualization_StateRefID()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getStateRefID();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateVisualization#getStateRefID <em>State Ref ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Ref ID</em>' attribute.
	 * @see #getStateRefID()
	 * @generated
	 */
	void setStateRefID(String value);

} // StateVisualization
