package com.tibco.cep.query.model;

import com.tibco.cep.query.exception.ResolveException;

public interface FromClause extends NamedContext, QueryContext {
	
	public static NamedContextId CTX_ID = new NamedContextId() {
        public String toString() { return "FROM_CLAUSE"; }
    };
	
	/**
	 * @param alias
	 * @return String
	 */
	String getEntityPathByAlias(String alias) throws Exception;
	
	/**
	 * @param alias
	 * @return Entity
	 */
	Entity getEntityByAlias(String alias) throws Exception;
	
	/**
	 * @param entityName
	 * @return String
	 */
	String getAliasByEntityPath(String entityName) throws Exception;


    /**
     * @param alias String alias name
     * @return Aliased that matches the alias name in this FromClause.
     * @throws ResolveException
     */
    Aliased getAliasedByAlias(String alias) throws Exception;

    /**
	 * @param alias
	 * @return boolean
	 */
	boolean containsAlias(String alias) throws Exception;
	
	/**
	 * @param entityName
	 * @return boolean
	 */
	boolean containsEntity(String entityName) throws Exception;
	
	/**
	 * @return String[]
	 */
	String[] getAllEntityPaths() throws Exception;
	
	/**
	 * @return String[]
     * @param includePseudoAliases
	 */
	String[] getAllEntityAliases(boolean includePseudoAliases) throws Exception;


}
