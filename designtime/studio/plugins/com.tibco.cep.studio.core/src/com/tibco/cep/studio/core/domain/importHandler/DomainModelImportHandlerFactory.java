package com.tibco.cep.studio.core.domain.importHandler;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.studio.core.domain.importHandler.impl.DomainModelDBConceptImportHandler;
import com.tibco.cep.studio.core.domain.importHandler.impl.DomainModelDatabaseTableImportHandler;
import com.tibco.cep.studio.core.domain.importHandler.impl.DomainModelExcelImportHandler;
import com.tibco.cep.studio.core.domain.importSource.DOMAIN_IMPORT_SOURCES;
import com.tibco.cep.studio.core.domain.importSource.DomainModelDataSourceFactory;
import com.tibco.cep.studio.core.domain.importSource.IDomainImportSource;
import com.tibco.cep.studio.core.domain.importSource.impl.DomainModelDBConceptDataSource;
import com.tibco.cep.studio.core.domain.importSource.impl.DomainModelDatabaseTableDataSource;
import com.tibco.cep.studio.core.domain.importSource.impl.DomainModelExcelDataSource;
import com.tibco.cep.studio.core.validation.ValidationError;


public class DomainModelImportHandlerFactory {
	
	public static final DomainModelImportHandlerFactory INSTANCE = new DomainModelImportHandlerFactory();
	
	private DomainModelImportHandlerFactory() {}
	
	/**
	 * 
	 * @param <O>
	 * @param <D>
	 * @param domainImportSource
	 * @param errors
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <O extends Object, D extends IDomainImportSource<O>> IDomainModelImportHandler<O, D> getImportHandler(DOMAIN_IMPORT_SOURCES domainImportSource, 
			                                                                                                     List<ValidationError> errors,
			                                                                                                     O dataSource) throws Exception {
		switch (domainImportSource) {
		
		case EXCEL :
			IDomainImportSource<String> domainModelExcelImportSource = 
				DomainModelDataSourceFactory.INSTANCE.getDataSource(DOMAIN_IMPORT_SOURCES.EXCEL, (String)dataSource);
			Constructor<DomainModelExcelImportHandler> constructor = DomainModelExcelImportHandler.class.getConstructor(List.class, DomainModelExcelDataSource.class);
			return (IDomainModelImportHandler<O, D>)constructor.newInstance(errors, domainModelExcelImportSource);
            
		case DATABASE_CONCEPT :
			IDomainImportSource<Map<String, String>> domainModelDBConceptImportSource = 
				DomainModelDataSourceFactory.INSTANCE.getDataSource(DOMAIN_IMPORT_SOURCES.DATABASE_CONCEPT, (Map<String, String>)dataSource);
			Constructor<DomainModelDBConceptImportHandler> dbConstructor = DomainModelDBConceptImportHandler.class.getConstructor(List.class, DomainModelDBConceptDataSource.class);
			return (IDomainModelImportHandler<O, D>)dbConstructor.newInstance(errors, domainModelDBConceptImportSource);
			
		case DATABASE_TABLE :
			IDomainImportSource<HashMap<String, String>> domainModelDatabaseTableImportSource = 
				DomainModelDataSourceFactory.INSTANCE.getDataSource(DOMAIN_IMPORT_SOURCES.DATABASE_TABLE, (HashMap<String,String>)dataSource);
			Constructor<DomainModelDatabaseTableImportHandler> constructor_DatabaseTable = DomainModelDatabaseTableImportHandler.class.getConstructor(List.class, DomainModelDatabaseTableDataSource.class);
			return (IDomainModelImportHandler<O, D>)constructor_DatabaseTable.newInstance(errors, domainModelDatabaseTableImportSource);
			
		default :
			throw new UnsupportedOperationException("Not supported domain import soure");
		}
	}
}
