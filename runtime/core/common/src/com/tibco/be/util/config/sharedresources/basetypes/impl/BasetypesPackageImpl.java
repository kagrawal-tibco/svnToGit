/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.basetypes.impl;

import com.tibco.be.util.config.sharedresources.basetypes.BasetypesFactory;
import com.tibco.be.util.config.sharedresources.basetypes.BasetypesPackage;
import com.tibco.be.util.config.sharedresources.basetypes.JndiPropertyTypeOrGvsEnum;

import com.tibco.be.util.config.sharedresources.basetypes.util.BasetypesValidator;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BasetypesPackageImpl extends EPackageImpl implements BasetypesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum jndiPropertyTypeOrGvsEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType booleanOrGvsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType dateTimeOrGvsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType decimalOrGvsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType doubleOrGvsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType floatOrGvsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType gvEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType hexBinaryOrGVsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType intOrGVsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jndiPropertyTypeOrGvsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jndiPropertyTypeOrGvsEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType longOrGVsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType stringOrGVsEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.tibco.be.util.config.sharedresources.basetypes.BasetypesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BasetypesPackageImpl() {
		super(eNS_URI, BasetypesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link BasetypesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BasetypesPackage init() {
		if (isInited) return (BasetypesPackage)EPackage.Registry.INSTANCE.getEPackage(BasetypesPackage.eNS_URI);

		// Obtain or create and register package
		BasetypesPackageImpl theBasetypesPackage = (BasetypesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BasetypesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BasetypesPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theBasetypesPackage.createPackageContents();

		// Initialize created meta-data
		theBasetypesPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theBasetypesPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return BasetypesValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theBasetypesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BasetypesPackage.eNS_URI, theBasetypesPackage);
		return theBasetypesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getJndiPropertyTypeOrGvsEnum() {
		return jndiPropertyTypeOrGvsEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getBooleanOrGvs() {
		return booleanOrGvsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDateTimeOrGvs() {
		return dateTimeOrGvsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDecimalOrGvs() {
		return decimalOrGvsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDoubleOrGvs() {
		return doubleOrGvsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getFloatOrGvs() {
		return floatOrGvsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getGv() {
		return gvEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getHexBinaryOrGVs() {
		return hexBinaryOrGVsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getIntOrGVs() {
		return intOrGVsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getJndiPropertyTypeOrGvs() {
		return jndiPropertyTypeOrGvsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getJndiPropertyTypeOrGvsEnumObject() {
		return jndiPropertyTypeOrGvsEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getLongOrGVs() {
		return longOrGVsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getStringOrGVs() {
		return stringOrGVsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasetypesFactory getBasetypesFactory() {
		return (BasetypesFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create enums
		jndiPropertyTypeOrGvsEnumEEnum = createEEnum(JNDI_PROPERTY_TYPE_OR_GVS_ENUM);

		// Create data types
		booleanOrGvsEDataType = createEDataType(BOOLEAN_OR_GVS);
		dateTimeOrGvsEDataType = createEDataType(DATE_TIME_OR_GVS);
		decimalOrGvsEDataType = createEDataType(DECIMAL_OR_GVS);
		doubleOrGvsEDataType = createEDataType(DOUBLE_OR_GVS);
		floatOrGvsEDataType = createEDataType(FLOAT_OR_GVS);
		gvEDataType = createEDataType(GV);
		hexBinaryOrGVsEDataType = createEDataType(HEX_BINARY_OR_GVS);
		intOrGVsEDataType = createEDataType(INT_OR_GVS);
		jndiPropertyTypeOrGvsEDataType = createEDataType(JNDI_PROPERTY_TYPE_OR_GVS);
		jndiPropertyTypeOrGvsEnumObjectEDataType = createEDataType(JNDI_PROPERTY_TYPE_OR_GVS_ENUM_OBJECT);
		longOrGVsEDataType = createEDataType(LONG_OR_GVS);
		stringOrGVsEDataType = createEDataType(STRING_OR_GVS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Initialize enums and add enum literals
		initEEnum(jndiPropertyTypeOrGvsEnumEEnum, JndiPropertyTypeOrGvsEnum.class, "JndiPropertyTypeOrGvsEnum");
		addEEnumLiteral(jndiPropertyTypeOrGvsEnumEEnum, JndiPropertyTypeOrGvsEnum.BOOLEAN);
		addEEnumLiteral(jndiPropertyTypeOrGvsEnumEEnum, JndiPropertyTypeOrGvsEnum.BYTE);
		addEEnumLiteral(jndiPropertyTypeOrGvsEnumEEnum, JndiPropertyTypeOrGvsEnum.CHAR);
		addEEnumLiteral(jndiPropertyTypeOrGvsEnumEEnum, JndiPropertyTypeOrGvsEnum.DOUBLE);
		addEEnumLiteral(jndiPropertyTypeOrGvsEnumEEnum, JndiPropertyTypeOrGvsEnum.FLOAT);
		addEEnumLiteral(jndiPropertyTypeOrGvsEnumEEnum, JndiPropertyTypeOrGvsEnum.INT);
		addEEnumLiteral(jndiPropertyTypeOrGvsEnumEEnum, JndiPropertyTypeOrGvsEnum.LONG);
		addEEnumLiteral(jndiPropertyTypeOrGvsEnumEEnum, JndiPropertyTypeOrGvsEnum.SHORT);
		addEEnumLiteral(jndiPropertyTypeOrGvsEnumEEnum, JndiPropertyTypeOrGvsEnum.STRING);

		// Initialize data types
		initEDataType(booleanOrGvsEDataType, Object.class, "BooleanOrGvs", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(dateTimeOrGvsEDataType, Object.class, "DateTimeOrGvs", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(decimalOrGvsEDataType, Object.class, "DecimalOrGvs", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(doubleOrGvsEDataType, Object.class, "DoubleOrGvs", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(floatOrGvsEDataType, Object.class, "FloatOrGvs", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(gvEDataType, String.class, "Gv", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(hexBinaryOrGVsEDataType, Object.class, "HexBinaryOrGVs", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(intOrGVsEDataType, Object.class, "IntOrGVs", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(jndiPropertyTypeOrGvsEDataType, Object.class, "JndiPropertyTypeOrGvs", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(jndiPropertyTypeOrGvsEnumObjectEDataType, JndiPropertyTypeOrGvsEnum.class, "JndiPropertyTypeOrGvsEnumObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(longOrGVsEDataType, Object.class, "LongOrGVs", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(stringOrGVsEDataType, String.class, "StringOrGVs", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
		addAnnotation
		  (booleanOrGvsEDataType, 
		   source, 
		   new String[] {
			 "name", "booleanOrGVs-type",
			 "memberTypes", "http://www.eclipse.org/emf/2003/XMLType#boolean gv-type"
		   });		
		addAnnotation
		  (dateTimeOrGvsEDataType, 
		   source, 
		   new String[] {
			 "name", "dateTimeOrGVs-type",
			 "memberTypes", "http://www.eclipse.org/emf/2003/XMLType#dateTime gv-type"
		   });		
		addAnnotation
		  (decimalOrGvsEDataType, 
		   source, 
		   new String[] {
			 "name", "decimalOrGVs-type",
			 "memberTypes", "http://www.eclipse.org/emf/2003/XMLType#decimal gv-type"
		   });		
		addAnnotation
		  (doubleOrGvsEDataType, 
		   source, 
		   new String[] {
			 "name", "doubleOrGVs-type",
			 "memberTypes", "http://www.eclipse.org/emf/2003/XMLType#double gv-type"
		   });		
		addAnnotation
		  (floatOrGvsEDataType, 
		   source, 
		   new String[] {
			 "name", "floatOrGVs-type",
			 "memberTypes", "http://www.eclipse.org/emf/2003/XMLType#float gv-type"
		   });		
		addAnnotation
		  (gvEDataType, 
		   source, 
		   new String[] {
			 "name", "gv-type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
			 "pattern", "([^%25]*%25%25[^%25]*%25%25)+[^%25]*"
		   });		
		addAnnotation
		  (hexBinaryOrGVsEDataType, 
		   source, 
		   new String[] {
			 "name", "hexBinaryOrGVs-type",
			 "memberTypes", "http://www.eclipse.org/emf/2003/XMLType#hexBinary gv-type"
		   });		
		addAnnotation
		  (intOrGVsEDataType, 
		   source, 
		   new String[] {
			 "name", "intOrGVs-type",
			 "memberTypes", "http://www.eclipse.org/emf/2003/XMLType#int gv-type"
		   });		
		addAnnotation
		  (jndiPropertyTypeOrGvsEDataType, 
		   source, 
		   new String[] {
			 "name", "jndiPropertyTypeOrGvs-type",
			 "memberTypes", "gv-type jndiPropertyTypeOrGvs-type_._member_._1"
		   });		
		addAnnotation
		  (jndiPropertyTypeOrGvsEnumEEnum, 
		   source, 
		   new String[] {
			 "name", "jndiPropertyTypeOrGvs-type_._member_._1"
		   });		
		addAnnotation
		  (jndiPropertyTypeOrGvsEnumObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "jndiPropertyTypeOrGvs-type_._member_._1:Object",
			 "baseType", "jndiPropertyTypeOrGvs-type_._member_._1"
		   });		
		addAnnotation
		  (longOrGVsEDataType, 
		   source, 
		   new String[] {
			 "name", "longOrGVs-type",
			 "memberTypes", "http://www.eclipse.org/emf/2003/XMLType#long gv-type"
		   });		
		addAnnotation
		  (stringOrGVsEDataType, 
		   source, 
		   new String[] {
			 "name", "stringOrGVs-type",
			 "memberTypes", "gv-type http://www.eclipse.org/emf/2003/XMLType#string"
		   });
	}

} //BasetypesPackageImpl
