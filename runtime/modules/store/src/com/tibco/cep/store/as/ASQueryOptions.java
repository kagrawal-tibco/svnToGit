/**
 * 
 */
package com.tibco.cep.store.as;

import java.util.Properties;

import com.tibco.cep.store.StoreQueryOptions;
import com.tibco.datagrid.Statement;

/**
 * @author vpatil
 *
 */
public class ASQueryOptions extends StoreQueryOptions {
	private Properties queryOptions;
	
	public ASQueryOptions() {
		this.queryOptions = new Properties();
	}

	public void setPrefetchSize(long prefetchSize) {
		this.queryOptions.setProperty(Statement.TIBDG_STATEMENT_PROPERTY_LONG_PREFETCH, String.valueOf(prefetchSize));
	}
	
	public void setGlobalSnapshotConsistency() {
		this.queryOptions.setProperty(Statement.TIBDG_STATEMENT_PROPERTY_STRING_CONSISTENCY, Statement.TIBDG_STATEMENT_CONSISTENCY_GLOBAL_SNAPSHOT);
	}
	
	public void setLocalSnapshotConsistency() {
		this.queryOptions.setProperty(Statement.TIBDG_STATEMENT_PROPERTY_STRING_CONSISTENCY, Statement.TIBDG_STATEMENT_CONSISTENCY_SNAPSHOT);
	}
	
	public Properties getProperties() {
		return this.queryOptions;
	}
}
