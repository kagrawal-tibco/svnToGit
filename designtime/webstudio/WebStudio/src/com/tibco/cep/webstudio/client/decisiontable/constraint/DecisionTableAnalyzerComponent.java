package com.tibco.cep.webstudio.client.decisiontable.constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.model.ArtifactDetail;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;

public class DecisionTableAnalyzerComponent implements IRequestDataItem {

	private String projectName = null;
	private String artifactPath = null;	
	private List<ColumnFilter> filters = null;
	private List<ArtifactDetail> testDataArtifacts = null;
	
	public DecisionTableAnalyzerComponent(String projectName, String artifactPath) {
		this.projectName = projectName;
		this.artifactPath = artifactPath;
		filters = new ArrayList<ColumnFilter>();
		testDataArtifacts = new ArrayList<ArtifactDetail>();
	}
	
	public class ColumnFilter {
		private String columnName = null;	
		private boolean isRangeFilter = false;
		private Object[] range = new Object[2];
		private Set<String> items = new TreeSet<String>();
	
		public ColumnFilter(String columnName) {
			this.columnName = columnName;
		}
		
		public String getColumnName() {
			return columnName;
		}
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		public boolean isRangeFilter() {
			return isRangeFilter;
		}
		public Object[] getRange() {
			return new Object[] {range[0], range[1]};
		}
		public void setRange(Object min, Object max) {
			isRangeFilter = true;		
			range[0] = min;
			range[1] = max;
		}	
		public Set<String> getItems() {
			return Collections.unmodifiableSet(items);
		}
		public void addItem(String item) {
			items.add(item);
		}
		public void addItems(Collection<String> items) {
			this.items.addAll(items);
		}
		
		private void serialize(Document rootDocument, Node rootNode) {
			Element filterElement = rootDocument.createElement("filter");
			rootNode.appendChild(filterElement);
			
			Element columnNameElement = rootDocument.createElement("columnName");
			Text columnNameText = rootDocument.createTextNode(columnName);
			columnNameElement.appendChild(columnNameText);
			filterElement.appendChild(columnNameElement);

			Element isRangeFilterElement = rootDocument.createElement("isRangeFilter");
			Text isRangeFilterText = rootDocument.createTextNode(String.valueOf(isRangeFilter));
			isRangeFilterElement.appendChild(isRangeFilterText);
			filterElement.appendChild(isRangeFilterElement);

			if (isRangeFilter) {
				Element minValueElement = rootDocument.createElement("minValue");
				Text minValueText = rootDocument.createTextNode(String.valueOf(range[0]));
				minValueElement.appendChild(minValueText);
				filterElement.appendChild(minValueElement);

				Element maxValueElement = rootDocument.createElement("maxValue");
				Text maxValueText = rootDocument.createTextNode(String.valueOf(range[1]));
				maxValueElement.appendChild(maxValueText);
				filterElement.appendChild(maxValueElement);
			} 
			else {
				for (String itemValue : items) {
					Element itemValueElement = rootDocument.createElement("value");
					Text itemValueText = rootDocument.createTextNode(itemValue);
					itemValueElement.appendChild(itemValueText);
					filterElement.appendChild(itemValueElement);					
				}
			}			
		}
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getArtifactPath() {
		return artifactPath;
	}

	public void setArtifactPath(String artifactPath) {
		this.artifactPath = artifactPath;
	}

	public List<ColumnFilter> getFilters() {
		return Collections.unmodifiableList(filters);
	}

	public void addFilter(ColumnFilter filter) {
		this.filters.add(filter);
	}	

	public void addFilters(List<ColumnFilter> filters) {
		this.filters.addAll(filters);
	}

	public void addTestDataArtifact(ArtifactDetail testDataArtifact) {
		this.testDataArtifacts.add(testDataArtifact);
	}	

	public void addTestDataArtifacts(List<ArtifactDetail> testDataArtifacts) {
		this.testDataArtifacts.addAll(testDataArtifacts);
	}

	public List<ArtifactDetail> getTestDataArtifacts() {
		return Collections.unmodifiableList(this.testDataArtifacts);
	}

	public Set<String> getColumns() {
		Set<String> columns = new TreeSet<String>();
		for (ColumnFilter filter : filters) {
			columns.add(filter.getColumnName());
		}		
		return columns;
	}

	public ColumnFilter getColumnFilter(String columnName) {		
		ColumnFilter columnFilter = null;
		for (ColumnFilter filter : filters) {
			if (columnName != null && columnName.equals(filter.getColumnName())){
				columnFilter = filter; 
			}
		}		
		return columnFilter;
	}
	
	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		Node artifactItemElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_ITEM_ELEMENT);
		rootNode.appendChild(artifactItemElement);
		
		Element projectNameElement = rootDocument.createElement("projectName");
		Text projectNameText = rootDocument.createTextNode(projectName);
		projectNameElement.appendChild(projectNameText);
		artifactItemElement.appendChild(projectNameElement);
		
		Element artifactPathElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_PATH_ELEMENT);
		Text artifactPathText = rootDocument.createTextNode(artifactPath);
		artifactPathElement.appendChild(artifactPathText);
		artifactItemElement.appendChild(artifactPathElement);

		for (ColumnFilter columnFilter : filters) {
			columnFilter.serialize(rootDocument, artifactItemElement);
		}		
		for (ArtifactDetail testDataArtifact : testDataArtifacts) {
			testDataArtifact.serialize(rootDocument, artifactItemElement);
		}
	}
}
