/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.impl;

import com.tibco.cep.designtime.core.model.archive.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.core.model.archive.AdapterArchive;
import com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting;
import com.tibco.cep.designtime.core.model.archive.ArchiveFactory;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.BEArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE;
import com.tibco.cep.designtime.core.model.archive.CACHE_MODE;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.InputDestination;
import com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE;
import com.tibco.cep.designtime.core.model.archive.ProcessArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;
import com.tibco.cep.designtime.core.model.archive.WORKERS;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ArchiveFactoryImpl extends EFactoryImpl implements ArchiveFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ArchiveFactory init() {
		try {
			ArchiveFactory theArchiveFactory = (ArchiveFactory)EPackage.Registry.INSTANCE.getEFactory(ArchivePackage.eNS_URI);
			if (theArchiveFactory != null) {
				return theArchiveFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ArchiveFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchiveFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ArchivePackage.ENTERPRISE_ARCHIVE: return createEnterpriseArchive();
			case ArchivePackage.BE_ARCHIVE_RESOURCE: return createBEArchiveResource();
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE: return createBusinessEventsArchiveResource();
			case ArchivePackage.INPUT_DESTINATION: return createInputDestination();
			case ArchivePackage.ADVANCED_ENTITY_SETTING: return createAdvancedEntitySetting();
			case ArchivePackage.SHARED_ARCHIVE: return createSharedArchive();
			case ArchivePackage.PROCESS_ARCHIVE: return createProcessArchive();
			case ArchivePackage.ADAPTER_ARCHIVE: return createAdapterArchive();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ArchivePackage.BE_ARCHIVE_TYPE:
				return createBE_ARCHIVE_TYPEFromString(eDataType, initialValue);
			case ArchivePackage.CACHE_MODE:
				return createCACHE_MODEFromString(eDataType, initialValue);
			case ArchivePackage.WORKERS:
				return createWORKERSFromString(eDataType, initialValue);
			case ArchivePackage.OBJECT_MGMT_TYPE:
				return createOBJECT_MGMT_TYPEFromString(eDataType, initialValue);
			case ArchivePackage.CACHE_CONFIG_TYPE:
				return createCACHE_CONFIG_TYPEFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ArchivePackage.BE_ARCHIVE_TYPE:
				return convertBE_ARCHIVE_TYPEToString(eDataType, instanceValue);
			case ArchivePackage.CACHE_MODE:
				return convertCACHE_MODEToString(eDataType, instanceValue);
			case ArchivePackage.WORKERS:
				return convertWORKERSToString(eDataType, instanceValue);
			case ArchivePackage.OBJECT_MGMT_TYPE:
				return convertOBJECT_MGMT_TYPEToString(eDataType, instanceValue);
			case ArchivePackage.CACHE_CONFIG_TYPE:
				return convertCACHE_CONFIG_TYPEToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnterpriseArchive createEnterpriseArchive() {
		EnterpriseArchiveImpl enterpriseArchive = new EnterpriseArchiveImpl();
		return enterpriseArchive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BEArchiveResource createBEArchiveResource() {
		BEArchiveResourceImpl beArchiveResource = new BEArchiveResourceImpl();
		return beArchiveResource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BusinessEventsArchiveResource createBusinessEventsArchiveResource() {
		BusinessEventsArchiveResourceImpl businessEventsArchiveResource = new BusinessEventsArchiveResourceImpl();
		return businessEventsArchiveResource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputDestination createInputDestination() {
		InputDestinationImpl inputDestination = new InputDestinationImpl();
		return inputDestination;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdvancedEntitySetting createAdvancedEntitySetting() {
		AdvancedEntitySettingImpl advancedEntitySetting = new AdvancedEntitySettingImpl();
		return advancedEntitySetting;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedArchive createSharedArchive() {
		SharedArchiveImpl sharedArchive = new SharedArchiveImpl();
		return sharedArchive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessArchive createProcessArchive() {
		ProcessArchiveImpl processArchive = new ProcessArchiveImpl();
		return processArchive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdapterArchive createAdapterArchive() {
		AdapterArchiveImpl adapterArchive = new AdapterArchiveImpl();
		return adapterArchive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BE_ARCHIVE_TYPE createBE_ARCHIVE_TYPEFromString(EDataType eDataType, String initialValue) {
		BE_ARCHIVE_TYPE result = BE_ARCHIVE_TYPE.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBE_ARCHIVE_TYPEToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CACHE_MODE createCACHE_MODEFromString(EDataType eDataType, String initialValue) {
		CACHE_MODE result = CACHE_MODE.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCACHE_MODEToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WORKERS createWORKERSFromString(EDataType eDataType, String initialValue) {
		WORKERS result = WORKERS.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertWORKERSToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OBJECT_MGMT_TYPE createOBJECT_MGMT_TYPEFromString(EDataType eDataType, String initialValue) {
		OBJECT_MGMT_TYPE result = OBJECT_MGMT_TYPE.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOBJECT_MGMT_TYPEToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CACHE_CONFIG_TYPE createCACHE_CONFIG_TYPEFromString(EDataType eDataType, String initialValue) {
		CACHE_CONFIG_TYPE result = CACHE_CONFIG_TYPE.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCACHE_CONFIG_TYPEToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchivePackage getArchivePackage() {
		return (ArchivePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ArchivePackage getPackage() {
		return ArchivePackage.eINSTANCE;
	}

} //ArchiveFactoryImpl
