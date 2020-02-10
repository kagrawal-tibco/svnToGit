/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Input Destination</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getDestinationURI <em>Destination URI</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getPreprocessor <em>Preprocessor</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.InputDestination#isDefault <em>Default</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.InputDestination#isEnable <em>Enable</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getWorkers <em>Workers</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getQueueSize <em>Queue Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getThreadAffinityRuleFunction <em>Thread Affinity Rule Function</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getInputDestination()
 * @model
 * @generated
 */
public interface InputDestination extends EObject {
	/**
	 * Returns the value of the '<em><b>Destination URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination URI</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destination URI</em>' attribute.
	 * @see #setDestinationURI(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getInputDestination_DestinationURI()
	 * @model
	 * @generated
	 */
	String getDestinationURI();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getDestinationURI <em>Destination URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination URI</em>' attribute.
	 * @see #getDestinationURI()
	 * @generated
	 */
	void setDestinationURI(String value);

	/**
	 * Returns the value of the '<em><b>Preprocessor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Preprocessor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Preprocessor</em>' attribute.
	 * @see #setPreprocessor(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getInputDestination_Preprocessor()
	 * @model
	 * @generated
	 */
	String getPreprocessor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getPreprocessor <em>Preprocessor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Preprocessor</em>' attribute.
	 * @see #getPreprocessor()
	 * @generated
	 */
	void setPreprocessor(String value);

	/**
	 * Returns the value of the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default</em>' attribute.
	 * @see #setDefault(boolean)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getInputDestination_Default()
	 * @model
	 * @generated
	 */
	boolean isDefault();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#isDefault <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default</em>' attribute.
	 * @see #isDefault()
	 * @generated
	 */
	void setDefault(boolean value);

	/**
	 * Returns the value of the '<em><b>Enable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enable</em>' attribute.
	 * @see #setEnable(boolean)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getInputDestination_Enable()
	 * @model
	 * @generated
	 */
	boolean isEnable();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#isEnable <em>Enable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable</em>' attribute.
	 * @see #isEnable()
	 * @generated
	 */
	void setEnable(boolean value);

	/**
	 * Returns the value of the '<em><b>Workers</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.archive.WORKERS}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Workers</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Workers</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.archive.WORKERS
	 * @see #setWorkers(WORKERS)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getInputDestination_Workers()
	 * @model
	 * @generated
	 */
	WORKERS getWorkers();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getWorkers <em>Workers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Workers</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.archive.WORKERS
	 * @see #getWorkers()
	 * @generated
	 */
	void setWorkers(WORKERS value);

	/**
	 * Returns the value of the '<em><b>Queue Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue Size</em>' attribute.
	 * @see #setQueueSize(int)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getInputDestination_QueueSize()
	 * @model
	 * @generated
	 */
	int getQueueSize();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getQueueSize <em>Queue Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue Size</em>' attribute.
	 * @see #getQueueSize()
	 * @generated
	 */
	void setQueueSize(int value);
	
	/**
	 * Returns the value of the '<em><b>Thread Affinity Rule Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Thread Affinity Rule Function</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Thread Affinity Rule Function</em>' attribute.
	 * @see #setThreadAffinityRuleFunction(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getInputDestination_ThreadAffinityRuleFunction()
	 * @model
	 * @generated
	 */
	String getThreadAffinityRuleFunction();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getThreadAffinityRuleFunction <em>Thread Affinity Rule Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thread Affinity Rule Function</em>' attribute.
	 * @see #getThreadAffinityRuleFunction()
	 * @generated
	 */
	void setThreadAffinityRuleFunction(String value);

	Object clone() throws CloneNotSupportedException; 

} // InputDestination
