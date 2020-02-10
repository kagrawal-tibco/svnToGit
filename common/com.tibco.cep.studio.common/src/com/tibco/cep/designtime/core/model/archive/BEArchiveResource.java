/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>BE Archive Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource#isCompileWithDebug <em>Compile With Debug</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource#isDeleteTempFiles <em>Delete Temp Files</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource#getCompilePath <em>Compile Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource#getExtraClassPath <em>Extra Class Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBEArchiveResource()
 * @model
 * @generated
 */
public interface BEArchiveResource extends ArchiveResource {

	/**
	 * Returns the value of the '<em><b>Compile With Debug</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compile With Debug</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compile With Debug</em>' attribute.
	 * @see #setCompileWithDebug(boolean)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBEArchiveResource_CompileWithDebug()
	 * @model
	 * @generated
	 */
	boolean isCompileWithDebug();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource#isCompileWithDebug <em>Compile With Debug</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compile With Debug</em>' attribute.
	 * @see #isCompileWithDebug()
	 * @generated
	 */
	void setCompileWithDebug(boolean value);

	/**
	 * Returns the value of the '<em><b>Delete Temp Files</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delete Temp Files</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delete Temp Files</em>' attribute.
	 * @see #setDeleteTempFiles(boolean)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBEArchiveResource_DeleteTempFiles()
	 * @model
	 * @generated
	 */
	boolean isDeleteTempFiles();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource#isDeleteTempFiles <em>Delete Temp Files</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete Temp Files</em>' attribute.
	 * @see #isDeleteTempFiles()
	 * @generated
	 */
	void setDeleteTempFiles(boolean value);

	/**
	 * Returns the value of the '<em><b>Compile Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compile Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compile Path</em>' attribute.
	 * @see #setCompilePath(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBEArchiveResource_CompilePath()
	 * @model
	 * @generated
	 */
	String getCompilePath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource#getCompilePath <em>Compile Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compile Path</em>' attribute.
	 * @see #getCompilePath()
	 * @generated
	 */
	void setCompilePath(String value);

	/**
	 * Returns the value of the '<em><b>Extra Class Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extra Class Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extra Class Path</em>' attribute.
	 * @see #setExtraClassPath(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBEArchiveResource_ExtraClassPath()
	 * @model
	 * @generated
	 */
	String getExtraClassPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource#getExtraClassPath <em>Extra Class Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extra Class Path</em>' attribute.
	 * @see #getExtraClassPath()
	 * @generated
	 */
	void setExtraClassPath(String value);
} // BEArchiveResource
