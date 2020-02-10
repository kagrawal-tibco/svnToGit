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

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.FilesConfig;
import com.tibco.be.util.config.cdd.LineLayoutConfig;
import com.tibco.be.util.config.cdd.LogConfigConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.TerminalConfig;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Log Config Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LogConfigConfigImpl#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LogConfigConfigImpl#getRoles <em>Roles</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LogConfigConfigImpl#getLineLayout <em>Line Layout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LogConfigConfigImpl#getTerminal <em>Terminal</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LogConfigConfigImpl#getFiles <em>Files</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LogConfigConfigImpl extends ArtifactConfigImpl implements LogConfigConfig {
    /**
     * The cached value of the '{@link #getEnabled() <em>Enabled</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEnabled()
     * @generated
     * @ordered
     */
    protected OverrideConfig enabled;

    /**
     * The cached value of the '{@link #getRoles() <em>Roles</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRoles()
     * @generated
     * @ordered
     */
    protected OverrideConfig roles;

    /**
     * The cached value of the '{@link #getLineLayout() <em>Line Layout</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLineLayout()
     * @generated
     * @ordered
     */
    protected LineLayoutConfig lineLayout;

    /**
     * The cached value of the '{@link #getTerminal() <em>Terminal</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTerminal()
     * @generated
     * @ordered
     */
    protected TerminalConfig terminal;

    /**
     * The cached value of the '{@link #getFiles() <em>Files</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFiles()
     * @generated
     * @ordered
     */
    protected FilesConfig files;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected LogConfigConfigImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CddPackage.eINSTANCE.getLogConfigConfig();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OverrideConfig getEnabled() {
        return enabled;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEnabled(OverrideConfig newEnabled, NotificationChain msgs) {
        OverrideConfig oldEnabled = enabled;
        enabled = newEnabled;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOG_CONFIG_CONFIG__ENABLED, oldEnabled, newEnabled);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEnabled(OverrideConfig newEnabled) {
        if (newEnabled != enabled) {
            NotificationChain msgs = null;
            if (enabled != null)
                msgs = ((InternalEObject)enabled).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOG_CONFIG_CONFIG__ENABLED, null, msgs);
            if (newEnabled != null)
                msgs = ((InternalEObject)newEnabled).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOG_CONFIG_CONFIG__ENABLED, null, msgs);
            msgs = basicSetEnabled(newEnabled, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOG_CONFIG_CONFIG__ENABLED, newEnabled, newEnabled));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OverrideConfig getRoles() {
        return roles;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRoles(OverrideConfig newRoles, NotificationChain msgs) {
        OverrideConfig oldRoles = roles;
        roles = newRoles;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOG_CONFIG_CONFIG__ROLES, oldRoles, newRoles);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRoles(OverrideConfig newRoles) {
        if (newRoles != roles) {
            NotificationChain msgs = null;
            if (roles != null)
                msgs = ((InternalEObject)roles).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOG_CONFIG_CONFIG__ROLES, null, msgs);
            if (newRoles != null)
                msgs = ((InternalEObject)newRoles).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOG_CONFIG_CONFIG__ROLES, null, msgs);
            msgs = basicSetRoles(newRoles, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOG_CONFIG_CONFIG__ROLES, newRoles, newRoles));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LineLayoutConfig getLineLayout() {
        return lineLayout;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLineLayout(LineLayoutConfig newLineLayout, NotificationChain msgs) {
        LineLayoutConfig oldLineLayout = lineLayout;
        lineLayout = newLineLayout;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOG_CONFIG_CONFIG__LINE_LAYOUT, oldLineLayout, newLineLayout);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLineLayout(LineLayoutConfig newLineLayout) {
        if (newLineLayout != lineLayout) {
            NotificationChain msgs = null;
            if (lineLayout != null)
                msgs = ((InternalEObject)lineLayout).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOG_CONFIG_CONFIG__LINE_LAYOUT, null, msgs);
            if (newLineLayout != null)
                msgs = ((InternalEObject)newLineLayout).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOG_CONFIG_CONFIG__LINE_LAYOUT, null, msgs);
            msgs = basicSetLineLayout(newLineLayout, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOG_CONFIG_CONFIG__LINE_LAYOUT, newLineLayout, newLineLayout));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TerminalConfig getTerminal() {
        return terminal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTerminal(TerminalConfig newTerminal, NotificationChain msgs) {
        TerminalConfig oldTerminal = terminal;
        terminal = newTerminal;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOG_CONFIG_CONFIG__TERMINAL, oldTerminal, newTerminal);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTerminal(TerminalConfig newTerminal) {
        if (newTerminal != terminal) {
            NotificationChain msgs = null;
            if (terminal != null)
                msgs = ((InternalEObject)terminal).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOG_CONFIG_CONFIG__TERMINAL, null, msgs);
            if (newTerminal != null)
                msgs = ((InternalEObject)newTerminal).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOG_CONFIG_CONFIG__TERMINAL, null, msgs);
            msgs = basicSetTerminal(newTerminal, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOG_CONFIG_CONFIG__TERMINAL, newTerminal, newTerminal));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FilesConfig getFiles() {
        return files;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetFiles(FilesConfig newFiles, NotificationChain msgs) {
        FilesConfig oldFiles = files;
        files = newFiles;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOG_CONFIG_CONFIG__FILES, oldFiles, newFiles);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFiles(FilesConfig newFiles) {
        if (newFiles != files) {
            NotificationChain msgs = null;
            if (files != null)
                msgs = ((InternalEObject)files).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOG_CONFIG_CONFIG__FILES, null, msgs);
            if (newFiles != null)
                msgs = ((InternalEObject)newFiles).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOG_CONFIG_CONFIG__FILES, null, msgs);
            msgs = basicSetFiles(newFiles, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOG_CONFIG_CONFIG__FILES, newFiles, newFiles));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CddPackage.LOG_CONFIG_CONFIG__ENABLED:
                return basicSetEnabled(null, msgs);
            case CddPackage.LOG_CONFIG_CONFIG__ROLES:
                return basicSetRoles(null, msgs);
            case CddPackage.LOG_CONFIG_CONFIG__LINE_LAYOUT:
                return basicSetLineLayout(null, msgs);
            case CddPackage.LOG_CONFIG_CONFIG__TERMINAL:
                return basicSetTerminal(null, msgs);
            case CddPackage.LOG_CONFIG_CONFIG__FILES:
                return basicSetFiles(null, msgs);
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
            case CddPackage.LOG_CONFIG_CONFIG__ENABLED:
                return getEnabled();
            case CddPackage.LOG_CONFIG_CONFIG__ROLES:
                return getRoles();
            case CddPackage.LOG_CONFIG_CONFIG__LINE_LAYOUT:
                return getLineLayout();
            case CddPackage.LOG_CONFIG_CONFIG__TERMINAL:
                return getTerminal();
            case CddPackage.LOG_CONFIG_CONFIG__FILES:
                return getFiles();
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
            case CddPackage.LOG_CONFIG_CONFIG__ENABLED:
                setEnabled((OverrideConfig)newValue);
                return;
            case CddPackage.LOG_CONFIG_CONFIG__ROLES:
                setRoles((OverrideConfig)newValue);
                return;
            case CddPackage.LOG_CONFIG_CONFIG__LINE_LAYOUT:
                setLineLayout((LineLayoutConfig)newValue);
                return;
            case CddPackage.LOG_CONFIG_CONFIG__TERMINAL:
                setTerminal((TerminalConfig)newValue);
                return;
            case CddPackage.LOG_CONFIG_CONFIG__FILES:
                setFiles((FilesConfig)newValue);
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
            case CddPackage.LOG_CONFIG_CONFIG__ENABLED:
                setEnabled((OverrideConfig)null);
                return;
            case CddPackage.LOG_CONFIG_CONFIG__ROLES:
                setRoles((OverrideConfig)null);
                return;
            case CddPackage.LOG_CONFIG_CONFIG__LINE_LAYOUT:
                setLineLayout((LineLayoutConfig)null);
                return;
            case CddPackage.LOG_CONFIG_CONFIG__TERMINAL:
                setTerminal((TerminalConfig)null);
                return;
            case CddPackage.LOG_CONFIG_CONFIG__FILES:
                setFiles((FilesConfig)null);
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
            case CddPackage.LOG_CONFIG_CONFIG__ENABLED:
                return enabled != null;
            case CddPackage.LOG_CONFIG_CONFIG__ROLES:
                return roles != null;
            case CddPackage.LOG_CONFIG_CONFIG__LINE_LAYOUT:
                return lineLayout != null;
            case CddPackage.LOG_CONFIG_CONFIG__TERMINAL:
                return terminal != null;
            case CddPackage.LOG_CONFIG_CONFIG__FILES:
                return files != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * @generated NOT
     */
    @Override
    public Map<Object, Object> toProperties() {
        final Properties props = new Properties();
        if(this.enabled != null)
            props.put(SystemProperty.TRACE_ENABLED.getPropertyName(),this.enabled.getMixed().getValue(0));
        else
            props.put(SystemProperty.TRACE_ENABLED.getPropertyName(),"true");
        if (this.files == null) {
            props.put(SystemProperty.TRACE_FILE_APPEND.getPropertyName(), "true");
        } else {
            props.putAll(this.files.toProperties());
        }

        if (this.lineLayout != null) {
            props.putAll(this.lineLayout.toProperties());
        }

        CddTools.addEntryFromMixed(props, SystemProperty.TRACE_ROLES.getPropertyName(), this.roles, true);

        if (this.terminal != null) {
            props.putAll(this.terminal.toProperties());
        }

        return props;
    }

} //LogConfigConfigImpl
