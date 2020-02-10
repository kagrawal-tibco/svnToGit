package com.tibco.cep.studio.decision.table.ui.constraintpane;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.decision.table.providers.IColumnObjectAccessor;
import com.tibco.cep.studio.tester.core.model.TestDataModel;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;

/**
 * @author vdhumal
 *
 */
public class TestDataCoverageTableColumnPropertyAccessor implements IColumnPropertyAccessor<List<String>>,IColumnObjectAccessor<String> {

	private String[] propertyNames = null;
	private TestDataModel testDataModel = null;
	
	public TestDataCoverageTableColumnPropertyAccessor(String[] propertyNames, TestDataModel testDataModel) {
		this.propertyNames = propertyNames;
		this.testDataModel = testDataModel;
	}
	
	@Override
	public Object getDataValue(List<String> rowObject, int columnIndex) {
		String dataValue = rowObject.get(columnIndex);
		String columnName = propertyNames[columnIndex];
		PropertyDefinition property = DecisionTableTestDataCoverageUtils.getConceptPropertyDefinition(testDataModel, columnName);
		if (property != null) {
			if (DecisionTableTestDataCoverageUtils.isContainedOrReferenceConcept(property, columnName)) {
				String parts[] = dataValue.split(TesterCoreUtils.MARKER);
				if(parts.length == 2){
					parts[1] = parts[1].replace(TesterCoreUtils.FORWARD_SLASH, TesterCoreUtils.DOT);
					parts[1] = parts[1].replaceFirst("\\.", "").trim();
					dataValue = parts[1] + TesterCoreUtils.DOT + "["+parts[0]+"]";
				}
			}	
		}
		
		return 	dataValue;		
	}

	@Override
	public void setDataValue(List<String> rowObject, int columnIndex, Object newValue) {
		//do nothing
	}

	@Override
	public int getColumnCount() {
		return propertyNames.length;
	}

	@Override
	public String getColumnProperty(int columnIndex) {
		return "col" + columnIndex;
	}

	@Override
	public int getColumnIndex(String propertyName) {
		return 0;
	}

	@Override
	public String getColumnObject(int columnIdx) {
		return propertyNames[columnIdx];
	}

}
