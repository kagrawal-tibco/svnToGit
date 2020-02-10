package com.tibco.cep.studio.debug.smap;

import java.util.List;

import com.tibco.cep.studio.debug.core.model.impl.IMappedResourcePosition;

/*
@author ssailapp
@date Jul 30, 2009 3:57:58 PM
 */

/**
 * The mapping between a BE Entity and Java Source. There is one-2-one mapping between BE Entity and Java Source.
 */
public interface SourceMapper {

    /**
     * @deprecated
     * Regular Life Cycle method.
     * @throws Exception
     */
    void init() throws Exception;

    /**
     * Given a BE Entity (Rule/RuleFunction/SM Transition ... find the corresponding Java line position
     * @param entityName : The be entity name for Rule, RuleFunction, and SMs, Only Rule Action is supported as of now.
     * @param beEntityPosition
     * @return int - The mapped line nos. A value of -1 means not mapped
     */
    IMappedResourcePosition getJavaPosition(String entityName, int beEntityPosition);

    /**
     * Given a javaPosition, find the BE Entity, and corresponding Line Number
     * @param javaName - The java classNAme
     * @param javaPosition
     * @return int The mapped line nos from Java -> BE Etntity , A value of -1 means not mapped
     */
    IMappedResourcePosition getBEPosition(String javaName, int javaPosition);

    /**
     * Return the BE Entity Name, given the className
     * @param className
     * @return The entity name that maps to the className - A Null value means no Mapping exists.
     */
    String getEntityName(String className);


    /**
     * Return the Java name for the entity.
     * @param entityName
     * @return The java name for the entity that has been mapped. A Null value means no Mapping/debug information exist
     */
    String getJavaName(String entityName);

    /**
     * Return the list of maps having the target class name
     * @param clazzName
     * @return
     */
    List<SourceMap> getJavaMaps(String clazzName );

    /**
     * Return the list of maps having the source entity name
     * @param entityName
     * @return
     */
    List<SourceMap> getEntityMaps(String entityName );

	public void init(List<byte[]> smapList) throws Exception;

	/**
	 * checks if the maps have been initialized
	 * @return
	 */
	boolean isEmpty();

	/**
	 * return a list of Java classes for the given Rule path i.e condition class, action class
	 * @param referenceTypeName
	 * @return
	 */
	String[] getJavaNames(String entityName);
	
}
