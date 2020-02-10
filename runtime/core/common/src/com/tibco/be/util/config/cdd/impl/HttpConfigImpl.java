/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.CiphersConfig;
import com.tibco.be.util.config.cdd.HttpConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.ProtocolsConfig;
import com.tibco.be.util.config.cdd.SslConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Http Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.HttpConfigImpl#getAcceptCount <em>Accept Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.HttpConfigImpl#getConnectionLinger <em>Connection Linger</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.HttpConfigImpl#getConnectionTimeout <em>Connection Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.HttpConfigImpl#getDocumentPage <em>Document Page</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.HttpConfigImpl#getDocumentRoot <em>Document Root</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.HttpConfigImpl#getMaxProcessors <em>Max Processors</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.HttpConfigImpl#getSocketOutputBufferSize <em>Socket Output Buffer Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.HttpConfigImpl#getSsl <em>Ssl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.HttpConfigImpl#getStaleConnectionCheck <em>Stale Connection Check</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.HttpConfigImpl#getTcpNoDelay <em>Tcp No Delay</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HttpConfigImpl extends EObjectImpl implements HttpConfig {
	/**
	 * The cached value of the '{@link #getAcceptCount() <em>Accept Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAcceptCount()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig acceptCount;

	/**
	 * The cached value of the '{@link #getConnectionLinger() <em>Connection Linger</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectionLinger()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig connectionLinger;

	/**
	 * The cached value of the '{@link #getConnectionTimeout() <em>Connection Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectionTimeout()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig connectionTimeout;

	/**
	 * The cached value of the '{@link #getDocumentPage() <em>Document Page</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentPage()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig documentPage;

	/**
	 * The cached value of the '{@link #getDocumentRoot() <em>Document Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentRoot()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig documentRoot;

	/**
	 * The cached value of the '{@link #getMaxProcessors() <em>Max Processors</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxProcessors()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig maxProcessors;

	/**
	 * The cached value of the '{@link #getSocketOutputBufferSize() <em>Socket Output Buffer Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSocketOutputBufferSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig socketOutputBufferSize;

	/**
	 * The cached value of the '{@link #getSsl() <em>Ssl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSsl()
	 * @generated
	 * @ordered
	 */
	protected SslConfig ssl;

	/**
	 * The cached value of the '{@link #getStaleConnectionCheck() <em>Stale Connection Check</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStaleConnectionCheck()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig staleConnectionCheck;

	/**
	 * The cached value of the '{@link #getTcpNoDelay() <em>Tcp No Delay</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTcpNoDelay()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig tcpNoDelay;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HttpConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getHttpConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getAcceptCount() {
		return acceptCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAcceptCount(OverrideConfig newAcceptCount, NotificationChain msgs) {
		OverrideConfig oldAcceptCount = acceptCount;
		acceptCount = newAcceptCount;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__ACCEPT_COUNT, oldAcceptCount, newAcceptCount);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAcceptCount(OverrideConfig newAcceptCount) {
		if (newAcceptCount != acceptCount) {
			NotificationChain msgs = null;
			if (acceptCount != null)
				msgs = ((InternalEObject)acceptCount).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__ACCEPT_COUNT, null, msgs);
			if (newAcceptCount != null)
				msgs = ((InternalEObject)newAcceptCount).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__ACCEPT_COUNT, null, msgs);
			msgs = basicSetAcceptCount(newAcceptCount, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__ACCEPT_COUNT, newAcceptCount, newAcceptCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getConnectionLinger() {
		return connectionLinger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnectionLinger(OverrideConfig newConnectionLinger, NotificationChain msgs) {
		OverrideConfig oldConnectionLinger = connectionLinger;
		connectionLinger = newConnectionLinger;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__CONNECTION_LINGER, oldConnectionLinger, newConnectionLinger);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnectionLinger(OverrideConfig newConnectionLinger) {
		if (newConnectionLinger != connectionLinger) {
			NotificationChain msgs = null;
			if (connectionLinger != null)
				msgs = ((InternalEObject)connectionLinger).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__CONNECTION_LINGER, null, msgs);
			if (newConnectionLinger != null)
				msgs = ((InternalEObject)newConnectionLinger).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__CONNECTION_LINGER, null, msgs);
			msgs = basicSetConnectionLinger(newConnectionLinger, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__CONNECTION_LINGER, newConnectionLinger, newConnectionLinger));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnectionTimeout(OverrideConfig newConnectionTimeout, NotificationChain msgs) {
		OverrideConfig oldConnectionTimeout = connectionTimeout;
		connectionTimeout = newConnectionTimeout;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__CONNECTION_TIMEOUT, oldConnectionTimeout, newConnectionTimeout);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnectionTimeout(OverrideConfig newConnectionTimeout) {
		if (newConnectionTimeout != connectionTimeout) {
			NotificationChain msgs = null;
			if (connectionTimeout != null)
				msgs = ((InternalEObject)connectionTimeout).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__CONNECTION_TIMEOUT, null, msgs);
			if (newConnectionTimeout != null)
				msgs = ((InternalEObject)newConnectionTimeout).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__CONNECTION_TIMEOUT, null, msgs);
			msgs = basicSetConnectionTimeout(newConnectionTimeout, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__CONNECTION_TIMEOUT, newConnectionTimeout, newConnectionTimeout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDocumentPage() {
		return documentPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocumentPage(OverrideConfig newDocumentPage, NotificationChain msgs) {
		OverrideConfig oldDocumentPage = documentPage;
		documentPage = newDocumentPage;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__DOCUMENT_PAGE, oldDocumentPage, newDocumentPage);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentPage(OverrideConfig newDocumentPage) {
		if (newDocumentPage != documentPage) {
			NotificationChain msgs = null;
			if (documentPage != null)
				msgs = ((InternalEObject)documentPage).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__DOCUMENT_PAGE, null, msgs);
			if (newDocumentPage != null)
				msgs = ((InternalEObject)newDocumentPage).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__DOCUMENT_PAGE, null, msgs);
			msgs = basicSetDocumentPage(newDocumentPage, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__DOCUMENT_PAGE, newDocumentPage, newDocumentPage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDocumentRoot() {
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocumentRoot(OverrideConfig newDocumentRoot, NotificationChain msgs) {
		OverrideConfig oldDocumentRoot = documentRoot;
		documentRoot = newDocumentRoot;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__DOCUMENT_ROOT, oldDocumentRoot, newDocumentRoot);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentRoot(OverrideConfig newDocumentRoot) {
		if (newDocumentRoot != documentRoot) {
			NotificationChain msgs = null;
			if (documentRoot != null)
				msgs = ((InternalEObject)documentRoot).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__DOCUMENT_ROOT, null, msgs);
			if (newDocumentRoot != null)
				msgs = ((InternalEObject)newDocumentRoot).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__DOCUMENT_ROOT, null, msgs);
			msgs = basicSetDocumentRoot(newDocumentRoot, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__DOCUMENT_ROOT, newDocumentRoot, newDocumentRoot));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMaxProcessors() {
		return maxProcessors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxProcessors(OverrideConfig newMaxProcessors, NotificationChain msgs) {
		OverrideConfig oldMaxProcessors = maxProcessors;
		maxProcessors = newMaxProcessors;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__MAX_PROCESSORS, oldMaxProcessors, newMaxProcessors);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxProcessors(OverrideConfig newMaxProcessors) {
		if (newMaxProcessors != maxProcessors) {
			NotificationChain msgs = null;
			if (maxProcessors != null)
				msgs = ((InternalEObject)maxProcessors).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__MAX_PROCESSORS, null, msgs);
			if (newMaxProcessors != null)
				msgs = ((InternalEObject)newMaxProcessors).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__MAX_PROCESSORS, null, msgs);
			msgs = basicSetMaxProcessors(newMaxProcessors, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__MAX_PROCESSORS, newMaxProcessors, newMaxProcessors));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSocketOutputBufferSize() {
		return socketOutputBufferSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSocketOutputBufferSize(OverrideConfig newSocketOutputBufferSize, NotificationChain msgs) {
		OverrideConfig oldSocketOutputBufferSize = socketOutputBufferSize;
		socketOutputBufferSize = newSocketOutputBufferSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__SOCKET_OUTPUT_BUFFER_SIZE, oldSocketOutputBufferSize, newSocketOutputBufferSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSocketOutputBufferSize(OverrideConfig newSocketOutputBufferSize) {
		if (newSocketOutputBufferSize != socketOutputBufferSize) {
			NotificationChain msgs = null;
			if (socketOutputBufferSize != null)
				msgs = ((InternalEObject)socketOutputBufferSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__SOCKET_OUTPUT_BUFFER_SIZE, null, msgs);
			if (newSocketOutputBufferSize != null)
				msgs = ((InternalEObject)newSocketOutputBufferSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__SOCKET_OUTPUT_BUFFER_SIZE, null, msgs);
			msgs = basicSetSocketOutputBufferSize(newSocketOutputBufferSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__SOCKET_OUTPUT_BUFFER_SIZE, newSocketOutputBufferSize, newSocketOutputBufferSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SslConfig getSsl() {
		return ssl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSsl(SslConfig newSsl, NotificationChain msgs) {
		SslConfig oldSsl = ssl;
		ssl = newSsl;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__SSL, oldSsl, newSsl);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSsl(SslConfig newSsl) {
		if (newSsl != ssl) {
			NotificationChain msgs = null;
			if (ssl != null)
				msgs = ((InternalEObject)ssl).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__SSL, null, msgs);
			if (newSsl != null)
				msgs = ((InternalEObject)newSsl).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__SSL, null, msgs);
			msgs = basicSetSsl(newSsl, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__SSL, newSsl, newSsl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getStaleConnectionCheck() {
		return staleConnectionCheck;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStaleConnectionCheck(OverrideConfig newStaleConnectionCheck, NotificationChain msgs) {
		OverrideConfig oldStaleConnectionCheck = staleConnectionCheck;
		staleConnectionCheck = newStaleConnectionCheck;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__STALE_CONNECTION_CHECK, oldStaleConnectionCheck, newStaleConnectionCheck);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStaleConnectionCheck(OverrideConfig newStaleConnectionCheck) {
		if (newStaleConnectionCheck != staleConnectionCheck) {
			NotificationChain msgs = null;
			if (staleConnectionCheck != null)
				msgs = ((InternalEObject)staleConnectionCheck).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__STALE_CONNECTION_CHECK, null, msgs);
			if (newStaleConnectionCheck != null)
				msgs = ((InternalEObject)newStaleConnectionCheck).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__STALE_CONNECTION_CHECK, null, msgs);
			msgs = basicSetStaleConnectionCheck(newStaleConnectionCheck, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__STALE_CONNECTION_CHECK, newStaleConnectionCheck, newStaleConnectionCheck));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getTcpNoDelay() {
		return tcpNoDelay;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTcpNoDelay(OverrideConfig newTcpNoDelay, NotificationChain msgs) {
		OverrideConfig oldTcpNoDelay = tcpNoDelay;
		tcpNoDelay = newTcpNoDelay;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__TCP_NO_DELAY, oldTcpNoDelay, newTcpNoDelay);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTcpNoDelay(OverrideConfig newTcpNoDelay) {
		if (newTcpNoDelay != tcpNoDelay) {
			NotificationChain msgs = null;
			if (tcpNoDelay != null)
				msgs = ((InternalEObject)tcpNoDelay).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__TCP_NO_DELAY, null, msgs);
			if (newTcpNoDelay != null)
				msgs = ((InternalEObject)newTcpNoDelay).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.HTTP_CONFIG__TCP_NO_DELAY, null, msgs);
			msgs = basicSetTcpNoDelay(newTcpNoDelay, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.HTTP_CONFIG__TCP_NO_DELAY, newTcpNoDelay, newTcpNoDelay));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
		final Properties p = new Properties();
		
		CddTools.addEntryFromMixed(p, "be.http.acceptCount", getAcceptCount(), true);
		CddTools.addEntryFromMixed(p, "be.http.connectionLinger", getConnectionLinger(), true);
		CddTools.addEntryFromMixed(p, "be.http.connectionTimeout", getConnectionTimeout(), true);
		CddTools.addEntryFromMixed(p, "be.http.docPage", getDocumentPage(), true);
		CddTools.addEntryFromMixed(p, "be.http.docRoot", getDocumentRoot(), true);
		CddTools.addEntryFromMixed(p, "be.http.maxProcessors", getMaxProcessors(), true);
		CddTools.addEntryFromMixed(p, "be.http.socketBufferSize", getSocketOutputBufferSize(), true);

        if (null != this.ssl) {
        	
        	final CiphersConfig ciphers = this.ssl.getCiphers();
            if (null != ciphers) {
            	final StringBuffer sb = new StringBuffer();
            	for (final OverrideConfig cipher : ciphers.getCipher()) {
            		final String name = CddTools.getValueFromMixed(cipher, true);
            		if((null != name) && !name.isEmpty()) {
            			sb.append(name).append(" ");
            		}
            	}
                p.put("be.http.ssl_server_ciphers", sb.toString().trim());
            }

        	final ProtocolsConfig protocols = this.ssl.getProtocols();
            if (null != protocols) {
            	final StringBuffer sb = new StringBuffer();
            	for (final OverrideConfig protocol : protocols.getProtocol()) {
            		final String name = CddTools.getValueFromMixed(protocol, true);
            		if((null != name) && !name.isEmpty()) {
            			sb.append(name).append(" ");
            		}
            	}
                p.put("be.http.ssl_server_enabledprotocols", sb.toString().trim());
            }

        	CddTools.addEntryFromMixed(p, "be.http.ssl_server_keymanageralgorithm", this.ssl.getKeyManagerAlgorithm(), true);
        	CddTools.addEntryFromMixed(p, "be.http.ssl_server_trustmanageralgorithm", this.ssl.getTrustManagerAlgorithm(), true);
        }
        
		CddTools.addEntryFromMixed(p, "be.http.async.staleConnectionCheck", getStaleConnectionCheck(), true);
		CddTools.addEntryFromMixed(p, "be.http.tcpNoDelay", getTcpNoDelay(), true);

        return p;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.HTTP_CONFIG__ACCEPT_COUNT:
				return basicSetAcceptCount(null, msgs);
			case CddPackage.HTTP_CONFIG__CONNECTION_LINGER:
				return basicSetConnectionLinger(null, msgs);
			case CddPackage.HTTP_CONFIG__CONNECTION_TIMEOUT:
				return basicSetConnectionTimeout(null, msgs);
			case CddPackage.HTTP_CONFIG__DOCUMENT_PAGE:
				return basicSetDocumentPage(null, msgs);
			case CddPackage.HTTP_CONFIG__DOCUMENT_ROOT:
				return basicSetDocumentRoot(null, msgs);
			case CddPackage.HTTP_CONFIG__MAX_PROCESSORS:
				return basicSetMaxProcessors(null, msgs);
			case CddPackage.HTTP_CONFIG__SOCKET_OUTPUT_BUFFER_SIZE:
				return basicSetSocketOutputBufferSize(null, msgs);
			case CddPackage.HTTP_CONFIG__SSL:
				return basicSetSsl(null, msgs);
			case CddPackage.HTTP_CONFIG__STALE_CONNECTION_CHECK:
				return basicSetStaleConnectionCheck(null, msgs);
			case CddPackage.HTTP_CONFIG__TCP_NO_DELAY:
				return basicSetTcpNoDelay(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CddPackage.HTTP_CONFIG__ACCEPT_COUNT:
				return getAcceptCount();
			case CddPackage.HTTP_CONFIG__CONNECTION_LINGER:
				return getConnectionLinger();
			case CddPackage.HTTP_CONFIG__CONNECTION_TIMEOUT:
				return getConnectionTimeout();
			case CddPackage.HTTP_CONFIG__DOCUMENT_PAGE:
				return getDocumentPage();
			case CddPackage.HTTP_CONFIG__DOCUMENT_ROOT:
				return getDocumentRoot();
			case CddPackage.HTTP_CONFIG__MAX_PROCESSORS:
				return getMaxProcessors();
			case CddPackage.HTTP_CONFIG__SOCKET_OUTPUT_BUFFER_SIZE:
				return getSocketOutputBufferSize();
			case CddPackage.HTTP_CONFIG__SSL:
				return getSsl();
			case CddPackage.HTTP_CONFIG__STALE_CONNECTION_CHECK:
				return getStaleConnectionCheck();
			case CddPackage.HTTP_CONFIG__TCP_NO_DELAY:
				return getTcpNoDelay();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CddPackage.HTTP_CONFIG__ACCEPT_COUNT:
				setAcceptCount((OverrideConfig)newValue);
				return;
			case CddPackage.HTTP_CONFIG__CONNECTION_LINGER:
				setConnectionLinger((OverrideConfig)newValue);
				return;
			case CddPackage.HTTP_CONFIG__CONNECTION_TIMEOUT:
				setConnectionTimeout((OverrideConfig)newValue);
				return;
			case CddPackage.HTTP_CONFIG__DOCUMENT_PAGE:
				setDocumentPage((OverrideConfig)newValue);
				return;
			case CddPackage.HTTP_CONFIG__DOCUMENT_ROOT:
				setDocumentRoot((OverrideConfig)newValue);
				return;
			case CddPackage.HTTP_CONFIG__MAX_PROCESSORS:
				setMaxProcessors((OverrideConfig)newValue);
				return;
			case CddPackage.HTTP_CONFIG__SOCKET_OUTPUT_BUFFER_SIZE:
				setSocketOutputBufferSize((OverrideConfig)newValue);
				return;
			case CddPackage.HTTP_CONFIG__SSL:
				setSsl((SslConfig)newValue);
				return;
			case CddPackage.HTTP_CONFIG__STALE_CONNECTION_CHECK:
				setStaleConnectionCheck((OverrideConfig)newValue);
				return;
			case CddPackage.HTTP_CONFIG__TCP_NO_DELAY:
				setTcpNoDelay((OverrideConfig)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CddPackage.HTTP_CONFIG__ACCEPT_COUNT:
				setAcceptCount((OverrideConfig)null);
				return;
			case CddPackage.HTTP_CONFIG__CONNECTION_LINGER:
				setConnectionLinger((OverrideConfig)null);
				return;
			case CddPackage.HTTP_CONFIG__CONNECTION_TIMEOUT:
				setConnectionTimeout((OverrideConfig)null);
				return;
			case CddPackage.HTTP_CONFIG__DOCUMENT_PAGE:
				setDocumentPage((OverrideConfig)null);
				return;
			case CddPackage.HTTP_CONFIG__DOCUMENT_ROOT:
				setDocumentRoot((OverrideConfig)null);
				return;
			case CddPackage.HTTP_CONFIG__MAX_PROCESSORS:
				setMaxProcessors((OverrideConfig)null);
				return;
			case CddPackage.HTTP_CONFIG__SOCKET_OUTPUT_BUFFER_SIZE:
				setSocketOutputBufferSize((OverrideConfig)null);
				return;
			case CddPackage.HTTP_CONFIG__SSL:
				setSsl((SslConfig)null);
				return;
			case CddPackage.HTTP_CONFIG__STALE_CONNECTION_CHECK:
				setStaleConnectionCheck((OverrideConfig)null);
				return;
			case CddPackage.HTTP_CONFIG__TCP_NO_DELAY:
				setTcpNoDelay((OverrideConfig)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CddPackage.HTTP_CONFIG__ACCEPT_COUNT:
				return acceptCount != null;
			case CddPackage.HTTP_CONFIG__CONNECTION_LINGER:
				return connectionLinger != null;
			case CddPackage.HTTP_CONFIG__CONNECTION_TIMEOUT:
				return connectionTimeout != null;
			case CddPackage.HTTP_CONFIG__DOCUMENT_PAGE:
				return documentPage != null;
			case CddPackage.HTTP_CONFIG__DOCUMENT_ROOT:
				return documentRoot != null;
			case CddPackage.HTTP_CONFIG__MAX_PROCESSORS:
				return maxProcessors != null;
			case CddPackage.HTTP_CONFIG__SOCKET_OUTPUT_BUFFER_SIZE:
				return socketOutputBufferSize != null;
			case CddPackage.HTTP_CONFIG__SSL:
				return ssl != null;
			case CddPackage.HTTP_CONFIG__STALE_CONNECTION_CHECK:
				return staleConnectionCheck != null;
			case CddPackage.HTTP_CONFIG__TCP_NO_DELAY:
				return tcpNoDelay != null;
		}
		return super.eIsSet(featureID);
	}

} //HttpConfigImpl
