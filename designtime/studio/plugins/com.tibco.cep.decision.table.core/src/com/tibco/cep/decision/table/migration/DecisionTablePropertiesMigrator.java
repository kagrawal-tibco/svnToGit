package com.tibco.cep.decision.table.migration;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tibco.cep.decision.table.codegen.DTCodegenUtil;
import com.tibco.cep.decision.table.metadata.DecisionTableMetadataFeature;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.core.StudioCorePlugin;

/**
 * Migrate Decision Table Properties from 4.x to 5.1 format
 * 
 * @author vdhumal
 * 
 */
public class DecisionTablePropertiesMigrator extends DefaultDecisionTableMigrator {

	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DTCodegenUtil.CODEGEN_EFFECTIVE_EXPIRY_DATE_FORMAT);
	private SimpleDateFormat OLD_DATE_FORMAT = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss 'V1'");

	@Override
	public int getPriority() {
		return 100;
	}

	@Override
	public boolean migrateTable(File resourceFile, Table tableEModel) {
		boolean migrateTable = false;

		MetaData metaData = tableEModel.getMd();
		if (metaData != null) {
			Property effectiveDateProp = metaData.search(DecisionTableMetadataFeature.EFFECTIVE_DATE.getFeatureName());
			Property expiryDateProp = metaData.search(DecisionTableMetadataFeature.EXPIRY_DATE.getFeatureName());

			// Modify Effective Date Format
			boolean IsEffDateMigrated = migrateDatePropertyFormat(effectiveDateProp);
			// Modify Expiry Date Format
			boolean IsExpDateMigrated = migrateDatePropertyFormat(expiryDateProp);

			migrateTable = (IsEffDateMigrated || IsExpDateMigrated);
		}
		return migrateTable;
	}

	/**
	 * @param dateProp
	 *            Property If the Date property has the Old format, modifies the
	 *            Format to existing one
	 */
	private boolean migrateDatePropertyFormat(Property dateProp) {

		String datePropValue = (dateProp != null ? dateProp.getValue() : null);

		if (datePropValue != null && datePropValue.length() > 0) {

			Date effectiveDate = null;
			try {
				effectiveDate = DATE_FORMAT.parse(datePropValue);
			} catch (ParseException pe) {
				// Do nothing
			}

			if (effectiveDate == null) {
				try {
					effectiveDate = OLD_DATE_FORMAT.parse(datePropValue);
				} catch (ParseException pe) {
					StudioCorePlugin.log(pe);
				}

				if (effectiveDate != null) {
					dateProp.setValue(DATE_FORMAT.format(effectiveDate));
					return true;
				}
			}
		}

		return false;
	}
}
