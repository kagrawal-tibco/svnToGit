/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Http Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.HttpConfig#getAcceptCount <em>Accept Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.HttpConfig#getConnectionLinger <em>Connection Linger</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.HttpConfig#getConnectionTimeout <em>Connection Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.HttpConfig#getDocumentPage <em>Document Page</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.HttpConfig#getDocumentRoot <em>Document Root</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.HttpConfig#getMaxProcessors <em>Max Processors</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.HttpConfig#getSocketOutputBufferSize <em>Socket Output Buffer Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.HttpConfig#getSsl <em>Ssl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.HttpConfig#getStaleConnectionCheck <em>Stale Connection Check</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.HttpConfig#getTcpNoDelay <em>Tcp No Delay</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getHttpConfig()
 * @model extendedMetaData="name='http-type' kind='elementOnly'"
 * @generated
 */
public interface HttpConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Accept Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Maximum queue length for incoming connection
	 * 						requests. Any requests received when the queue
	 * 						is full will be
	 * 						refused.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Accept Count</em>' containment reference.
	 * @see #setAcceptCount(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getHttpConfig_AcceptCount()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='accept-count' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getAcceptCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.HttpConfig#getAcceptCount <em>Accept Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Accept Count</em>' containment reference.
	 * @see #getAcceptCount()
	 * @generated
	 */
	void setAcceptCount(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Connection Linger</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Number of milliseconds during which the sockets
	 * 						used by the HTTP server will linger when they
	 * 						are closed.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Connection Linger</em>' containment reference.
	 * @see #setConnectionLinger(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getHttpConfig_ConnectionLinger()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='connection-linger' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getConnectionLinger();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.HttpConfig#getConnectionLinger <em>Connection Linger</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connection Linger</em>' containment reference.
	 * @see #getConnectionLinger()
	 * @generated
	 */
	void setConnectionLinger(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Connection Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Number of milliseconds the HTTP server will
	 * 						wait, after accepting a connection, for the
	 * 						request URI line to be
	 * 						presented.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Connection Timeout</em>' containment reference.
	 * @see #setConnectionTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getHttpConfig_ConnectionTimeout()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='connection-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getConnectionTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.HttpConfig#getConnectionTimeout <em>Connection Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connection Timeout</em>' containment reference.
	 * @see #getConnectionTimeout()
	 * @generated
	 */
	void setConnectionTimeout(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Document Page</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Default static html file name.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Document Page</em>' containment reference.
	 * @see #setDocumentPage(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getHttpConfig_DocumentPage()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='document-page' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDocumentPage();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.HttpConfig#getDocumentPage <em>Document Page</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Document Page</em>' containment reference.
	 * @see #getDocumentPage()
	 * @generated
	 */
	void setDocumentPage(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Document Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Location of static documents.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Document Root</em>' containment reference.
	 * @see #setDocumentRoot(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getHttpConfig_DocumentRoot()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='document-root' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDocumentRoot();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.HttpConfig#getDocumentRoot <em>Document Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Document Root</em>' containment reference.
	 * @see #getDocumentRoot()
	 * @generated
	 */
	void setDocumentRoot(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Max Processors</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Maximum number of simultaneous requests that can
	 * 						be handled by the HTTP server.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Processors</em>' containment reference.
	 * @see #setMaxProcessors(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getHttpConfig_MaxProcessors()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='max-processors' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxProcessors();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.HttpConfig#getMaxProcessors <em>Max Processors</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Processors</em>' containment reference.
	 * @see #getMaxProcessors()
	 * @generated
	 */
	void setMaxProcessors(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Socket Output Buffer Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						The size (in bytes) of the socket output buffer.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Socket Output Buffer Size</em>' containment reference.
	 * @see #setSocketOutputBufferSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getHttpConfig_SocketOutputBufferSize()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='socket-output-buffer-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSocketOutputBufferSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.HttpConfig#getSocketOutputBufferSize <em>Socket Output Buffer Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Socket Output Buffer Size</em>' containment reference.
	 * @see #getSocketOutputBufferSize()
	 * @generated
	 */
	void setSocketOutputBufferSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Ssl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ssl</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ssl</em>' containment reference.
	 * @see #setSsl(SslConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getHttpConfig_Ssl()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ssl' namespace='##targetNamespace'"
	 * @generated
	 */
	SslConfig getSsl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.HttpConfig#getSsl <em>Ssl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ssl</em>' containment reference.
	 * @see #getSsl()
	 * @generated
	 */
	void setSsl(SslConfig value);

	/**
	 * Returns the value of the '<em><b>Stale Connection Check</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Whether stale connection check is to be used.
	 * 						Disabling stale connection check may result in
	 * 						slight performance
	 * 						improvement at the risk of
	 * 						getting an I/O error when executing a
	 * 						request.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Stale Connection Check</em>' containment reference.
	 * @see #setStaleConnectionCheck(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getHttpConfig_StaleConnectionCheck()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='stale-connection-check' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getStaleConnectionCheck();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.HttpConfig#getStaleConnectionCheck <em>Stale Connection Check</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stale Connection Check</em>' containment reference.
	 * @see #getStaleConnectionCheck()
	 * @generated
	 */
	void setStaleConnectionCheck(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Tcp No Delay</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Value of TCP_NO_DELAY for the server socket. Set
	 * 						to true to improve performance under most
	 * 						circumstances.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Tcp No Delay</em>' containment reference.
	 * @see #setTcpNoDelay(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getHttpConfig_TcpNoDelay()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='tcp-no-delay' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getTcpNoDelay();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.HttpConfig#getTcpNoDelay <em>Tcp No Delay</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tcp No Delay</em>' containment reference.
	 * @see #getTcpNoDelay()
	 * @generated
	 */
	void setTcpNoDelay(OverrideConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();
	
	

} // HttpConfig
