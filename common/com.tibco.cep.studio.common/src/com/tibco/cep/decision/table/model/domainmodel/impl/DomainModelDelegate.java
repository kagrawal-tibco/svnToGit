/**
 * 
 */
package com.tibco.cep.decision.table.model.domainmodel.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.decision.table.model.domainmodel.Domain;
import com.tibco.cep.decision.table.model.domainmodel.DomainEntry;
import com.tibco.cep.decision.table.model.dtmodel.ResourceType;

/**
 * @author aathalye
 *
 */
public class DomainModelDelegate extends EObjectImpl implements Domain {
	
public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setModified(boolean value) {
		// TODO Auto-generated method stub
		
	}
	/*	private ArgumentProperty argProperty;
	
	public DomainModelDelegate(final ArgumentProperty argProperty) {
		this.argProperty = argProperty;
	}

	 (non-Javadoc)
	 * @see com.tibco.cep.decision.table.model.domainModel.Domain#getDomainEntry()
	 
	
	public EList<DomainEntry> getDomainEntry() {
		BasicEList<DomainEntry> list = (BasicEList<DomainEntry>)argProperty.getDomain().getDomainEntry();
		return new DomainModelEntryList<DomainEntry>(list, argProperty);
	}
	

	 (non-Javadoc)
	 * @see com.tibco.cep.decision.table.model.domainModel.Domain#getType()
	 
	public String getType() {
		return argProperty.getDomain().getType();
	}

	 (non-Javadoc)
	 * @see com.tibco.cep.decision.table.model.domainModel.Domain#setType(java.lang.String)
	 
	public void setType(String value) {
		argProperty.getDomain().setType(value);
	}

	 (non-Javadoc)
	 * @see org.eclipse.emf.ecore.impl.BasicEObjectImpl#eInverseRemove(org.eclipse.emf.ecore.InternalEObject, int, org.eclipse.emf.common.notify.NotificationChain)
	 * This uses the default implementation of <tt>DomainImpl</tt>.
	 * 
	 
	@Override
	public final NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		DomainImpl domainImpl = (DomainImpl)argProperty.getDomain();
		return domainImpl.eInverseRemove(otherEnd, featureID, msgs);
	}

	 (non-Javadoc)
	 * @see org.eclipse.emf.ecore.impl.BasicEObjectImpl#eGet(int, boolean, boolean)
	 
	@Override
	public final Object eGet(int featureID, boolean resolve, boolean coreType) {
		DomainImpl domainImpl = (DomainImpl)argProperty.getDomain();
		return domainImpl.eGet(featureID, resolve, coreType);
	}

	 (non-Javadoc)
	 * @see org.eclipse.emf.ecore.impl.BasicEObjectImpl#eSet(int, java.lang.Object)
	 
	@Override
	public final void eSet(int featureID, Object newValue) {
		DomainImpl domainImpl = (DomainImpl)argProperty.getDomain();
		domainImpl.eSet(featureID, newValue);
	}

	 (non-Javadoc)
	 * @see org.eclipse.emf.ecore.impl.BasicEObjectImpl#eIsSet(int)
	 
	@Override
	public final boolean eIsSet(int featureID) {
		DomainImpl domainImpl = (DomainImpl)argProperty.getDomain();
		return domainImpl.eIsSet(featureID);
	}

	 (non-Javadoc)
	 * @see org.eclipse.emf.ecore.impl.BasicEObjectImpl#eUnset(int)
	 
	@Override
	public final void eUnset(int featureID) {
		DomainImpl domainImpl = (DomainImpl)argProperty.getDomain();
		domainImpl.eUnset(featureID);
	}*/
	public EList<DomainEntry> getDomainEntry() {

		return null;
	}
	public String getType() {
		return null;
	}
	public void setType(String value) {
		
	}
	public String getResource() {
		// TODO Auto-generated method stub
		return null;
	}
	public ResourceType getResourceType() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setResource(String value) {
		// TODO Auto-generated method stub
		
	}
	public void setResourceType(ResourceType value) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.ontology.AccessControlCandidate#isLocallyModified()
	 */
	public boolean isLocallyModified() {
		// TODO Auto-generated method stub
		return false;
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.ontology.AccessControlCandidate#setLocallyModified(boolean)
	 */
	public void setLocallyModified(boolean value) {
		// TODO Auto-generated method stub
		
	}
	public boolean isOverride() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setOverride(boolean value) {
		// TODO Auto-generated method stub
		
	}
	public String getDbRef() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setDbRef(String value) {
		// TODO Auto-generated method stub
		
	}
	

}
