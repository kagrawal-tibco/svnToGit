/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.designtime.core.model.validation.ValidationFactory;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;
import com.tibco.cep.studio.common.validation.utils.Messages;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.SimpleEventImpl#getChannelURI <em>Channel URI</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.SimpleEventImpl#getDestinationName <em>Destination Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimpleEventImpl extends EventImpl implements SimpleEvent {
	/**
	 * The default value of the '{@link #getChannelURI() <em>Channel URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChannelURI()
	 * @generated
	 * @ordered
	 */
	protected static final String CHANNEL_URI_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getChannelURI() <em>Channel URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChannelURI()
	 * @generated
	 * @ordered
	 */
	protected String channelURI = CHANNEL_URI_EDEFAULT;
	/**
	 * The default value of the '{@link #getDestinationName() <em>Destination Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestinationName()
	 * @generated
	 * @ordered
	 */
	protected static final String DESTINATION_NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getDestinationName() <em>Destination Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestinationName()
	 * @generated
	 * @ordered
	 */
	protected String destinationName = DESTINATION_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimpleEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EventPackage.Literals.SIMPLE_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getChannelURI() {
		return channelURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChannelURI(String newChannelURI) {
		String oldChannelURI = channelURI;
		channelURI = newChannelURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.SIMPLE_EVENT__CHANNEL_URI, oldChannelURI, channelURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDestinationName() {
		return destinationName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestinationName(String newDestinationName) {
		String oldDestinationName = destinationName;
		destinationName = newDestinationName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.SIMPLE_EVENT__DESTINATION_NAME, oldDestinationName, destinationName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Destination getDestination() {
		if (channelURI != null && destinationName != null) {
			StringBuilder sbuilder = new StringBuilder(channelURI);
			sbuilder.append(".channel");
			sbuilder.append("/");
			sbuilder.append(destinationName);
			Destination dest = CommonIndexUtils.getDestination(ownerProjectName, 
					                                     sbuilder.toString());

			return dest;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EventPackage.SIMPLE_EVENT__CHANNEL_URI:
				return getChannelURI();
			case EventPackage.SIMPLE_EVENT__DESTINATION_NAME:
				return getDestinationName();
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
			case EventPackage.SIMPLE_EVENT__CHANNEL_URI:
				setChannelURI((String)newValue);
				return;
			case EventPackage.SIMPLE_EVENT__DESTINATION_NAME:
				setDestinationName((String)newValue);
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
			case EventPackage.SIMPLE_EVENT__CHANNEL_URI:
				setChannelURI(CHANNEL_URI_EDEFAULT);
				return;
			case EventPackage.SIMPLE_EVENT__DESTINATION_NAME:
				setDestinationName(DESTINATION_NAME_EDEFAULT);
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
			case EventPackage.SIMPLE_EVENT__CHANNEL_URI:
				return CHANNEL_URI_EDEFAULT == null ? channelURI != null : !CHANNEL_URI_EDEFAULT.equals(channelURI);
			case EventPackage.SIMPLE_EVENT__DESTINATION_NAME:
				return DESTINATION_NAME_EDEFAULT == null ? destinationName != null : !DESTINATION_NAME_EDEFAULT.equals(destinationName);
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
		result.append(" (channelURI: ");
		result.append(channelURI);
		result.append(", destinationName: ");
		result.append(destinationName);
		result.append(')');
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.core.model.event.impl.EventImpl#getModelErrors()
	 */
	@Override
	public EList<ModelError> getModelErrors() {
		EList<ModelError> modelErrors = super.getModelErrors();
		Destination destination = getDestination();
		List<Object> argList = new ArrayList<Object>();
		for (PropertyDefinition propDefinition : getProperties()){
			if (propDefinition.getName().trim().equals("id")) {
				argList.clear(); 
				argList.add(propDefinition.getName());
				String msg = CommonValidationUtils.formatMessage("EnityImpl.errors.propertyNameId", argList);
				ModelError modelError = ValidationFactory.eINSTANCE.createModelError();            
				modelError.setMessage(msg);
				modelError.setSource(this);
				modelErrors.add(modelError);
			}
		}
		if (destination == null) {
			ModelError error = ValidationFactory.eINSTANCE.createModelError();
			if (channelURI != null && destinationName != null) {
				String msg = Messages.getString("DefaultEvent.setDestination.invalidDefaultDest.msg");
				msg = MessageFormat.format(msg, channelURI+"/"+destinationName);
				error.setMessage(msg);
			} else {
				error.setWarning(true);
				String msg = Messages.getString("DefaultEvent.setDestination.noDefaultDest.msg");
				error.setMessage(msg);
			}
			error.setSource(this);
			modelErrors.add(error);			
		}
		
		 // Validate Domain Model Instance associated with property Definitions
        for (DomainInstance di : getAllDomainInstances()){
        	String diPath = di.getResourcePath();
        	if (diPath == null || diPath.trim().length() == 0) continue;
        	// get resource reference 
        	//Entity domainEntity = IndexUtils.getEntity(getOwnerProjectName(), diPath, ELEMENT_TYPES.DOMAIN);
       		String ext = CommonIndexUtils.getFileExtension(ELEMENT_TYPES.DOMAIN);
       		if (!diPath.endsWith("."+ext)){
       			diPath = diPath + "." + ext;
       		}
       		argList.clear();
        	if (!CommonValidationUtils.resolveReference(diPath, getOwnerProjectName())){
        		// dangling references 
        		argList.clear();
        		argList.add(di.getOwnerProperty().getName());
        		argList.add("Event");
        		argList.add(getFullPath());
        		argList.add(diPath);
        		ModelError me = CommonValidationUtils.constructModelError(this, "RuleParticipant.error.property.hasDanglingDomainReference", argList, false);
        		modelErrors.add(me);
        	}
        }
		return modelErrors;
	}
} //SimpleEventImpl
