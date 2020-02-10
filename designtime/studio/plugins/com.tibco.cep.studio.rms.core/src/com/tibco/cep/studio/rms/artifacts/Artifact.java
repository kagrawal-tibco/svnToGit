/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.artifacts;

import java.util.Date;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Artifact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactPath <em>Artifact Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactType <em>Artifact Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactContent <em>Artifact Content</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactExtension <em>Artifact Extension</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.Artifact#getContainerPath <em>Container Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.Artifact#getUpdateTime <em>Update Time</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.Artifact#getCommittedVersion <em>Committed Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifact()
 * @model
 * @generated
 */
public interface Artifact extends EObject {
	/**
	 * Returns the value of the '<em><b>Artifact Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact Path</em>' attribute.
	 * @see #setArtifactPath(String)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifact_ArtifactPath()
	 * @model
	 * @generated
	 */
	String getArtifactPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactPath <em>Artifact Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact Path</em>' attribute.
	 * @see #getArtifactPath()
	 * @generated
	 */
	void setArtifactPath(String value);

	/**
	 * Returns the value of the '<em><b>Artifact Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.studio.rms.artifacts.ArtifactsType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact Type</em>' attribute.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsType
	 * @see #setArtifactType(ArtifactsType)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifact_ArtifactType()
	 * @model
	 * @generated
	 */
	ArtifactsType getArtifactType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactType <em>Artifact Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact Type</em>' attribute.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsType
	 * @see #getArtifactType()
	 * @generated
	 */
	void setArtifactType(ArtifactsType value);

	/**
	 * Returns the value of the '<em><b>Artifact Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact Content</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact Content</em>' attribute.
	 * @see #setArtifactContent(String)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifact_ArtifactContent()
	 * @model
	 * @generated
	 */
	String getArtifactContent();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactContent <em>Artifact Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact Content</em>' attribute.
	 * @see #getArtifactContent()
	 * @generated
	 */
	void setArtifactContent(String value);

	/**
	 * Returns the value of the '<em><b>Artifact Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact Extension</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact Extension</em>' attribute.
	 * @see #setArtifactExtension(String)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifact_ArtifactExtension()
	 * @model
	 * @generated
	 */
	String getArtifactExtension();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactExtension <em>Artifact Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact Extension</em>' attribute.
	 * @see #getArtifactExtension()
	 * @generated
	 */
	void setArtifactExtension(String value);

	/**
	 * Returns the value of the '<em><b>Container Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Container Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Container Path</em>' attribute.
	 * @see #setContainerPath(String)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifact_ContainerPath()
	 * @model
	 * @generated
	 */
	String getContainerPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getContainerPath <em>Container Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container Path</em>' attribute.
	 * @see #getContainerPath()
	 * @generated
	 */
	void setContainerPath(String value);

	/**
	 * Returns the value of the '<em><b>Update Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Update Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Update Time</em>' attribute.
	 * @see #setUpdateTime(Date)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifact_UpdateTime()
	 * @model
	 * @generated
	 */
	Date getUpdateTime();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getUpdateTime <em>Update Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Update Time</em>' attribute.
	 * @see #getUpdateTime()
	 * @generated
	 */
	void setUpdateTime(Date value);

	/**
	 * Returns the value of the '<em><b>Committed Version</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Committed Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Committed Version</em>' attribute.
	 * @see #setCommittedVersion(String)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifact_CommittedVersion()
	 * @model default=""
	 * @generated
	 */
	String getCommittedVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getCommittedVersion <em>Committed Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Committed Version</em>' attribute.
	 * @see #getCommittedVersion()
	 * @generated
	 */
	void setCommittedVersion(String value);

} // Artifact
