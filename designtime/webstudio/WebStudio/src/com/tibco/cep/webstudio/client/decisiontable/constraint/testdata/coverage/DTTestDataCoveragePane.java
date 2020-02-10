package com.tibco.cep.webstudio.client.decisiontable.constraint.testdata.coverage;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class DTTestDataCoveragePane extends Tab {

	private ListGrid testDataResultsGrid;

	public DTTestDataCoveragePane(Element docElement) {
		setTitle(Canvas.imgHTML(Page.getAppImgDir() + "icons/16/testdata_coverage.png") + "TestData Coverage");
		init(docElement);
	}
	
	public void init(Element docElement) {
		VLayout vLayout = new VLayout();
		vLayout.setWidth100();
		vLayout.setHeight100();
				
		testDataResultsGrid = new ListGrid();  
		testDataResultsGrid.setCanEdit(false);    
		testDataResultsGrid.setWidth100();  
		testDataResultsGrid.setHeight100();  
		testDataResultsGrid.setShowAllRecords(true);  
		testDataResultsGrid.setCellHeight(22);
		testDataResultsGrid.setCanFreezeFields(false);
		testDataResultsGrid.setShowFilterEditor(true);
		testDataResultsGrid.setFilterOnKeypress(true);
		testDataResultsGrid.setCanReorderFields(false);		  
		testDataResultsGrid.setAutoFetchData(false);

        ListGridField nofield = new ListGridField("#");		
        nofield.setType(ListGridFieldType.INTEGER);

        nofield.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
	        public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
	            
	            String[] coveredRules = record.getAttributeAsStringArray("coverage");
	            return (coveredRules != null) ? coveredRules.toString() : "";
	        }
	    });
		
		List<ListGridField> listGridFields = new ArrayList<ListGridField>();
        listGridFields.add(nofield);
        
		Node artifactDetailsNode = docElement.getElementsByTagName("artifactDetails").item(0);
		NodeList artifactDetailsChildNodesList = artifactDetailsNode.getChildNodes();
		for (int i = 0; i < artifactDetailsChildNodesList.getLength(); i++) {
			Node artifactDetailsChildNode = artifactDetailsChildNodesList.item(i);					
			if (artifactDetailsChildNode.getNodeType() == Node.ELEMENT_NODE && "columnName".equals(artifactDetailsChildNode.getNodeName())
																&& artifactDetailsChildNode.getFirstChild() != null) {
				listGridFields.add(new ListGridField(artifactDetailsChildNode.getFirstChild().getNodeValue()));		
			}
		}		
		ListGridField[] listGridFieldsArr = listGridFields.toArray(new ListGridField[listGridFields.size()]);
		testDataResultsGrid.setFields(listGridFieldsArr);

		loadTestDataCoverageResults(artifactDetailsNode);
        vLayout.addMember(testDataResultsGrid);
        
        setPane(vLayout);
		//loadTestDataCoverageResults(artifactDetailsNode);
	}

	public void loadTestDataCoverageResults(Node artifactDetailsNode) {
		ListGridField[] listGridFields = testDataResultsGrid.getAllFields();
		NodeList artifactDetailsChildNodesList = artifactDetailsNode.getChildNodes();
		List<ListGridRecord> listGridRecordList = new ArrayList<ListGridRecord>();
		int rowIndex = 1;
		for (int i = 0; i < artifactDetailsChildNodesList.getLength(); i++) {
			Node artifactDetailsChildNode = artifactDetailsChildNodesList.item(i);								
			if (artifactDetailsChildNode.getNodeType() == Node.ELEMENT_NODE && "record".equals(artifactDetailsChildNode.getNodeName())) {
				ListGridRecord listGridRecord = new ListGridRecord();
				listGridRecord.setAttribute(listGridFields[0].getName(), rowIndex);				
				rowIndex++;
				
				List<String> coveredRules = new ArrayList<String>();
				Node recordNode = artifactDetailsChildNode;
				NodeList recordChildNodesList = recordNode.getChildNodes();
				int colIndex = 1;
				for (int j = 0; j < recordChildNodesList.getLength(); j++) {
					Node recordChildNode = recordChildNodesList.item(j);
					if (recordChildNode.getNodeType() == Node.ELEMENT_NODE && "value".equals(recordChildNode.getNodeName())) {
						String columnName = listGridFields[colIndex].getName();
						String cellValue = null;
						if (recordChildNode.getFirstChild() != null)
							cellValue = recordChildNode.getFirstChild().getNodeValue();
						listGridRecord.setAttribute(columnName, cellValue);
						colIndex++;
					} else if (recordChildNode.getNodeType() == Node.ELEMENT_NODE && "coveredRuleId".equals(recordChildNode.getNodeName())
																		&& recordChildNode.getFirstChild() != null) {
						coveredRules.add(recordChildNode.getFirstChild().getNodeValue());
					}				
				}
				if (!coveredRules.isEmpty()) {
					listGridRecord.setCustomStyle("ws-dt-coverage-row-style");
					String[] coveredRulesArr = coveredRules.toArray(new String[coveredRules.size()]);
					listGridRecord.setAttribute("coverage", coveredRulesArr);
				}
				listGridRecordList.add(listGridRecord);
			}
		}
		testDataResultsGrid.setData(listGridRecordList.toArray(new ListGridRecord[listGridRecordList.size()]));
	}	
}
