package com.tibco.cep.studio.ui.editors.domain;

import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.RANGE_VALUE_SEPARATOR;

import java.util.List;

import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.designtime.core.model.domain.Single;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

/**
 * 
 * @author sasahoo
 * 
 */
public class DomainSaveAction extends DefaultResourceValidator {

	/**
	 * @param formViewer
	 */
	public boolean doSave(DomainFormViewer formViewer) {
		List<DomainEntry> domainEntryList = formViewer.getDomain().getEntries();
		try {
			// Empty Domain Entry List
			domainEntryList.clear();
			for (TableItem item : formViewer.getViewer().getTable().getItems()) {
				String description = item.getText(0).trim();
				String value = item.getText(1).trim();
				if (value.contains(RANGE_VALUE_SEPARATOR)) {
					Range range = DomainFactory.eINSTANCE.createRange();
					range.setLowerInclusive(value.startsWith("[") ? true : false);
					range.setUpperInclusive(value.endsWith("]") ? true : false);
					String lowerValue = value.substring(1,
							value.indexOf(RANGE_VALUE_SEPARATOR)).trim();
					String upperValue = value.substring(
							value.indexOf(RANGE_VALUE_SEPARATOR) + 1,
							value.length() - 1).trim();
					range.setLower(lowerValue.equalsIgnoreCase("") ? "Undefined"
							: lowerValue.trim());
					range.setUpper(upperValue.equalsIgnoreCase("") ? "Undefined"
							: upperValue.trim());
					range.setDescription(description);
					// range.setValue(range);
					domainEntryList.add(range);
				} else {
					Single single = DomainFactory.eINSTANCE.createSingle();
					if (!value.trim().equalsIgnoreCase("")) {
						single.setValue(value);
						single.setDescription(description);
						domainEntryList.add(single);
					}
				}
			}
			if (domainEntryList.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			EditorsUIPlugin.log(e);
			return false;
		}
		return true;
	}
}
