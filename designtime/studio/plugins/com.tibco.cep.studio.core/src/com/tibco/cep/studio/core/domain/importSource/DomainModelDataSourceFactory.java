package com.tibco.cep.studio.core.domain.importSource;

import java.lang.reflect.Constructor;
import java.util.Map;

import com.tibco.cep.studio.core.domain.importSource.impl.DomainModelDBConceptDataSource;
import com.tibco.cep.studio.core.domain.importSource.impl.DomainModelDatabaseTableDataSource;
import com.tibco.cep.studio.core.domain.importSource.impl.DomainModelExcelDataSource;

/**
 * Use only this factory to instantiate concrete impls.
 * <p>
 * <b>
 * Do not call constructors directly.
 * </b>
 * </p>
 * @author aathalye
 *
 */
public class DomainModelDataSourceFactory {
	
	public static final DomainModelDataSourceFactory INSTANCE = new DomainModelDataSourceFactory();
	
	private DomainModelDataSourceFactory() {}
	
	/**
	 * TODO add implementations
	 * @param <D>
	 * @param domainImportSource
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <O extends Object> IDomainImportSource<O> getDataSource(DOMAIN_IMPORT_SOURCES domainImportSource, O dataSource) throws Exception {
		switch (domainImportSource) {
		
		case EXCEL :
			Constructor<DomainModelExcelDataSource> constructor =
				DomainModelExcelDataSource.class.getConstructor(String.class);
			return (IDomainImportSource<O>)constructor.newInstance(dataSource);
		case DATABASE_CONCEPT :
			Constructor<DomainModelDBConceptDataSource> dbConstructor =
				DomainModelDBConceptDataSource.class.getConstructor(Map.class);
			return (IDomainImportSource<O>)dbConstructor.newInstance(dataSource);
		case DATABASE_TABLE :
			Constructor<DomainModelDatabaseTableDataSource> constructor_DatabaseTable =
				DomainModelDatabaseTableDataSource.class.getConstructor(Map.class);
			return (IDomainImportSource<O>)constructor_DatabaseTable.newInstance(dataSource);
		default :
			throw new UnsupportedOperationException("Not supported domain import soure");
		}
	}
}
