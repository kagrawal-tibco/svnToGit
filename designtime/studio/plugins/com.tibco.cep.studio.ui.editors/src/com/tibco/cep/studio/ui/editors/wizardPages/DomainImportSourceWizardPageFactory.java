package com.tibco.cep.studio.ui.editors.wizardPages;

import java.lang.reflect.Constructor;

import org.eclipse.jface.wizard.WizardPage;

import com.tibco.cep.studio.core.domain.importSource.DOMAIN_IMPORT_SOURCES;

/**
 * Instantiate import wizard only via this factory class.
 * @author aathalye
 *
 */
public class DomainImportSourceWizardPageFactory {
	
	public static final DomainImportSourceWizardPageFactory INSTANCE = new DomainImportSourceWizardPageFactory();
	
	private DomainImportSourceWizardPageFactory() {}
	
	@SuppressWarnings("unchecked")
	public <P extends WizardPage & IDomainSourceWizardPage> P getWizardPage(
			DOMAIN_IMPORT_SOURCES domainImportSource,
			String pageName) {
		try {
			switch (domainImportSource) {
			
			case EXCEL :
				Constructor<ImportExcelDomainWizardPage> constructor =
					ImportExcelDomainWizardPage.class.getConstructor(String.class);
				return (P)constructor.newInstance(pageName);
			case DATABASE_CONCEPT :
				Constructor<ImportDBDomainWizardPage> dbConstructor =
					ImportDBDomainWizardPage.class.getConstructor(String.class);
				return (P)dbConstructor.newInstance(pageName);
			case DATABASE_TABLE :
				Constructor<ImportDatabaseTableDomainWizardPage> constructor_DatabaseTable =
					ImportDatabaseTableDomainWizardPage.class.getConstructor(String.class);
				return (P)constructor_DatabaseTable.newInstance(pageName);
			default :
				return null;
			} 
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
