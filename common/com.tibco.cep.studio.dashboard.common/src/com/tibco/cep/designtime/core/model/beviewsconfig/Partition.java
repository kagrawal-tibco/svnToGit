/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Partition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             Represents the partition in a page. Partition can be vertical or horizontal.
 *             For 2.3 we will support only vertical partitions
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#isVertical <em>Vertical</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getSpan <em>Span</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getState <em>State</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getPanel <em>Panel</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPartition()
 * @model extendedMetaData="name='Partition' kind='elementOnly'"
 * @generated
 */
public interface Partition extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPartition_DisplayName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='displayName'"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Vertical</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vertical</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vertical</em>' attribute.
	 * @see #isSetVertical()
	 * @see #unsetVertical()
	 * @see #setVertical(boolean)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPartition_Vertical()
	 * @model default="true" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='element' name='vertical'"
	 * @generated
	 */
	boolean isVertical();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#isVertical <em>Vertical</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vertical</em>' attribute.
	 * @see #isSetVertical()
	 * @see #unsetVertical()
	 * @see #isVertical()
	 * @generated
	 */
	void setVertical(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#isVertical <em>Vertical</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetVertical()
	 * @see #isVertical()
	 * @see #setVertical(boolean)
	 * @generated
	 */
	void unsetVertical();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#isVertical <em>Vertical</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Vertical</em>' attribute is set.
	 * @see #unsetVertical()
	 * @see #isVertical()
	 * @see #setVertical(boolean)
	 * @generated
	 */
	boolean isSetVertical();

	/**
	 * Returns the value of the '<em><b>Span</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Span</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Span</em>' attribute.
	 * @see #setSpan(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPartition_Span()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='span'"
	 * @generated
	 */
	String getSpan();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getSpan <em>Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Span</em>' attribute.
	 * @see #getSpan()
	 * @generated
	 */
	void setSpan(String value);

	/**
	 * Returns the value of the '<em><b>State</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.PartitionStateEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PartitionStateEnum
	 * @see #isSetState()
	 * @see #unsetState()
	 * @see #setState(PartitionStateEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPartition_State()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='state'"
	 * @generated
	 */
	PartitionStateEnum getState();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PartitionStateEnum
	 * @see #isSetState()
	 * @see #unsetState()
	 * @see #getState()
	 * @generated
	 */
	void setState(PartitionStateEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetState()
	 * @see #getState()
	 * @see #setState(PartitionStateEnum)
	 * @generated
	 */
	void unsetState();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getState <em>State</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>State</em>' attribute is set.
	 * @see #unsetState()
	 * @see #getState()
	 * @see #setState(PartitionStateEnum)
	 * @generated
	 */
	boolean isSetState();

	/**
	 * Returns the value of the '<em><b>Panel</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Panel</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Panel</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPartition_Panel()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='panel'"
	 * @generated
	 */
	EList<Panel> getPanel();

} // Partition
