/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pie Chart Visualization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             The extension of chart visualization to handle generation of pie charts
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getStartingAngle <em>Starting Angle</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getDirection <em>Direction</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getSector <em>Sector</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPieChartVisualization()
 * @model extendedMetaData="name='PieChartVisualization' kind='elementOnly'"
 * @generated
 */
public interface PieChartVisualization extends ChartVisualization {
	/**
	 * Returns the value of the '<em><b>Starting Angle</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Starting Angle</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Starting Angle</em>' attribute.
	 * @see #isSetStartingAngle()
	 * @see #unsetStartingAngle()
	 * @see #setStartingAngle(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPieChartVisualization_StartingAngle()
	 * @model default="0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='startingAngle'"
	 * @generated
	 */
	int getStartingAngle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getStartingAngle <em>Starting Angle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Starting Angle</em>' attribute.
	 * @see #isSetStartingAngle()
	 * @see #unsetStartingAngle()
	 * @see #getStartingAngle()
	 * @generated
	 */
	void setStartingAngle(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getStartingAngle <em>Starting Angle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetStartingAngle()
	 * @see #getStartingAngle()
	 * @see #setStartingAngle(int)
	 * @generated
	 */
	void unsetStartingAngle();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getStartingAngle <em>Starting Angle</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Starting Angle</em>' attribute is set.
	 * @see #unsetStartingAngle()
	 * @see #getStartingAngle()
	 * @see #setStartingAngle(int)
	 * @generated
	 */
	boolean isSetStartingAngle();

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The default value is <code>"clockwise"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartDirectionEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PieChartDirectionEnum
	 * @see #isSetDirection()
	 * @see #unsetDirection()
	 * @see #setDirection(PieChartDirectionEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPieChartVisualization_Direction()
	 * @model default="clockwise" unsettable="true"
	 *        extendedMetaData="kind='element' name='direction'"
	 * @generated
	 */
	PieChartDirectionEnum getDirection();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PieChartDirectionEnum
	 * @see #isSetDirection()
	 * @see #unsetDirection()
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(PieChartDirectionEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDirection()
	 * @see #getDirection()
	 * @see #setDirection(PieChartDirectionEnum)
	 * @generated
	 */
	void unsetDirection();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getDirection <em>Direction</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Direction</em>' attribute is set.
	 * @see #unsetDirection()
	 * @see #getDirection()
	 * @see #setDirection(PieChartDirectionEnum)
	 * @generated
	 */
	boolean isSetDirection();

	/**
	 * Returns the value of the '<em><b>Sector</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sector</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sector</em>' attribute.
	 * @see #isSetSector()
	 * @see #unsetSector()
	 * @see #setSector(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPieChartVisualization_Sector()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='sector'"
	 * @generated
	 */
	int getSector();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getSector <em>Sector</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sector</em>' attribute.
	 * @see #isSetSector()
	 * @see #unsetSector()
	 * @see #getSector()
	 * @generated
	 */
	void setSector(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getSector <em>Sector</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSector()
	 * @see #getSector()
	 * @see #setSector(int)
	 * @generated
	 */
	void unsetSector();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getSector <em>Sector</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Sector</em>' attribute is set.
	 * @see #unsetSector()
	 * @see #getSector()
	 * @see #setSector(int)
	 * @generated
	 */
	boolean isSetSector();

} // PieChartVisualization
