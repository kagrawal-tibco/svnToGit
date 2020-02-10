/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.impl.EntityImpl;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Template View</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateViewImpl#getRuleTemplatePath <em>Rule Template Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateViewImpl#getPresentationText <em>Presentation Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateViewImpl#getHtmlFile <em>Html File</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleTemplateViewImpl extends EntityImpl implements RuleTemplateView {
	/**
	 * The default value of the '{@link #getRuleTemplatePath() <em>Rule Template Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleTemplatePath()
	 * @generated
	 * @ordered
	 */
	protected static final String RULE_TEMPLATE_PATH_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getRuleTemplatePath() <em>Rule Template Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleTemplatePath()
	 * @generated
	 * @ordered
	 */
	protected String ruleTemplatePath = RULE_TEMPLATE_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getPresentationText() <em>Presentation Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPresentationText()
	 * @generated
	 * @ordered
	 */
	protected static final String PRESENTATION_TEXT_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getPresentationText() <em>Presentation Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPresentationText()
	 * @generated
	 * @ordered
	 */
	protected String presentationText = PRESENTATION_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getHtmlFile() <em>Html File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHtmlFile()
	 * @generated
	 * @ordered
	 */
	protected static final String HTML_FILE_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getHtmlFile() <em>Html File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHtmlFile()
	 * @generated
	 * @ordered
	 */
	protected String htmlFile = HTML_FILE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleTemplateViewImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulePackage.Literals.RULE_TEMPLATE_VIEW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRuleTemplatePath() {
		return ruleTemplatePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleTemplatePath(String newRuleTemplatePath) {
		String oldRuleTemplatePath = ruleTemplatePath;
		ruleTemplatePath = newRuleTemplatePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_TEMPLATE_VIEW__RULE_TEMPLATE_PATH, oldRuleTemplatePath, ruleTemplatePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPresentationText() {
		return presentationText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPresentationText(String newPresentationText) {
		String oldPresentationText = presentationText;
		presentationText = newPresentationText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_TEMPLATE_VIEW__PRESENTATION_TEXT, oldPresentationText, presentationText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHtmlFile() {
		return htmlFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHtmlFile(String newHtmlFile) {
		String oldHtmlFile = htmlFile;
		htmlFile = newHtmlFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_TEMPLATE_VIEW__HTML_FILE, oldHtmlFile, htmlFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulePackage.RULE_TEMPLATE_VIEW__RULE_TEMPLATE_PATH:
				return getRuleTemplatePath();
			case RulePackage.RULE_TEMPLATE_VIEW__PRESENTATION_TEXT:
				return getPresentationText();
			case RulePackage.RULE_TEMPLATE_VIEW__HTML_FILE:
				return getHtmlFile();
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
			case RulePackage.RULE_TEMPLATE_VIEW__RULE_TEMPLATE_PATH:
				setRuleTemplatePath((String)newValue);
				return;
			case RulePackage.RULE_TEMPLATE_VIEW__PRESENTATION_TEXT:
				setPresentationText((String)newValue);
				return;
			case RulePackage.RULE_TEMPLATE_VIEW__HTML_FILE:
				setHtmlFile((String)newValue);
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
			case RulePackage.RULE_TEMPLATE_VIEW__RULE_TEMPLATE_PATH:
				setRuleTemplatePath(RULE_TEMPLATE_PATH_EDEFAULT);
				return;
			case RulePackage.RULE_TEMPLATE_VIEW__PRESENTATION_TEXT:
				setPresentationText(PRESENTATION_TEXT_EDEFAULT);
				return;
			case RulePackage.RULE_TEMPLATE_VIEW__HTML_FILE:
				setHtmlFile(HTML_FILE_EDEFAULT);
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
			case RulePackage.RULE_TEMPLATE_VIEW__RULE_TEMPLATE_PATH:
				return RULE_TEMPLATE_PATH_EDEFAULT == null ? ruleTemplatePath != null : !RULE_TEMPLATE_PATH_EDEFAULT.equals(ruleTemplatePath);
			case RulePackage.RULE_TEMPLATE_VIEW__PRESENTATION_TEXT:
				return PRESENTATION_TEXT_EDEFAULT == null ? presentationText != null : !PRESENTATION_TEXT_EDEFAULT.equals(presentationText);
			case RulePackage.RULE_TEMPLATE_VIEW__HTML_FILE:
				return HTML_FILE_EDEFAULT == null ? htmlFile != null : !HTML_FILE_EDEFAULT.equals(htmlFile);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (ruleTemplatePath: ");
		result.append(ruleTemplatePath);
		result.append(", presentationText: ");
		result.append(presentationText);
		result.append(", htmlFile: ");
		result.append(htmlFile);
		result.append(')');
		return result.toString();
	}

} //RuleTemplateViewImpl
