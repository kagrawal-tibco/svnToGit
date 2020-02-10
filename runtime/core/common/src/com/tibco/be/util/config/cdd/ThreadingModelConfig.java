/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Threading Model Config</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.cdd.CddPackage#getThreadingModelConfig()
 * @model extendedMetaData="name='threading-model-type'"
 * @generated
 */
public enum ThreadingModelConfig implements Enumerator {
	/**
	 * The '<em><b>Shared Queue</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHARED_QUEUE_VALUE
	 * @generated
	 * @ordered
	 */
	SHARED_QUEUE(0, "sharedQueue", "shared-queue"),

	/**
	 * The '<em><b>Workers</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WORKERS_VALUE
	 * @generated
	 * @ordered
	 */
	WORKERS(1, "workers", "workers"),

	/**
	 * The '<em><b>Caller</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CALLER_VALUE
	 * @generated
	 * @ordered
	 */
	CALLER(2, "caller", "caller");

	/**
	 * The '<em><b>Shared Queue</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Shared Queue</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SHARED_QUEUE
	 * @model name="sharedQueue" literal="shared-queue"
	 * @generated
	 * @ordered
	 */
	public static final int SHARED_QUEUE_VALUE = 0;

	/**
	 * The '<em><b>Workers</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Workers</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WORKERS
	 * @model name="workers"
	 * @generated
	 * @ordered
	 */
	public static final int WORKERS_VALUE = 1;

	/**
	 * The '<em><b>Caller</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Caller</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CALLER
	 * @model name="caller"
	 * @generated
	 * @ordered
	 */
	public static final int CALLER_VALUE = 2;

	/**
	 * An array of all the '<em><b>Threading Model Config</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ThreadingModelConfig[] VALUES_ARRAY =
		new ThreadingModelConfig[] {
			SHARED_QUEUE,
			WORKERS,
			CALLER,
		};

	/**
	 * A public read-only list of all the '<em><b>Threading Model Config</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ThreadingModelConfig> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Threading Model Config</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ThreadingModelConfig get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ThreadingModelConfig result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Threading Model Config</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ThreadingModelConfig getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ThreadingModelConfig result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Threading Model Config</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ThreadingModelConfig get(int value) {
		switch (value) {
			case SHARED_QUEUE_VALUE: return SHARED_QUEUE;
			case WORKERS_VALUE: return WORKERS;
			case CALLER_VALUE: return CALLER;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ThreadingModelConfig(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //ThreadingModelConfig
