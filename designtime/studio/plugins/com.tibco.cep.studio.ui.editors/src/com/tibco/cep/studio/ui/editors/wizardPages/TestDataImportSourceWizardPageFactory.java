/**
 * 
 */
package com.tibco.cep.studio.ui.editors.wizardPages;

import java.lang.reflect.Constructor;

import org.eclipse.jface.wizard.WizardPage;
import com.tibco.cep.studio.core.testdata.importSource.TESTDATA_IMPORT_SOURCES;

/**
 * @author mgujrath
 *
 */
public class TestDataImportSourceWizardPageFactory {public static final TestDataImportSourceWizardPageFactory INSTANCE = new TestDataImportSourceWizardPageFactory();

private TestDataImportSourceWizardPageFactory() {}

@SuppressWarnings("unchecked")
public <P extends WizardPage & ITestDataSourceWizardPage> P getWizardPage(
		TESTDATA_IMPORT_SOURCES domainImportSource,
		String pageName) {
	try {
		switch (domainImportSource) {
		
		case EXCEL :
			Constructor<ImportExcelTestDataWizardPage> constructor =
				ImportExcelTestDataWizardPage.class.getConstructor(String.class);
			return (P)constructor.newInstance(pageName);
	
		default :
			return null;
		} 
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
}

}
