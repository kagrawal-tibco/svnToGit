package com.tibco.rta.query.impl;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.tibco.rta.query.BaseQueryDef;
import com.tibco.rta.query.QueryType;
import com.tibco.rta.query.SortOrder;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.*;

@XmlAccessorType(XmlAccessType.NONE)
abstract public class BaseQueryDefImpl implements BaseQueryDef {

	private static final long serialVersionUID = 8504618934155070441L;

	@XmlElement(name=ELEM_SCHEMA_NAME)
	protected String schemaName;
	
	@XmlAttribute(name=ATTR_NAME_NAME)
	protected String name;
	
	protected int batchSize = 1; //default
	
	@XmlAttribute(name=ATTR_TYPE_NAME)
	protected QueryType queryType;
	
	@XmlElement(name=ELEM_VERSION_NAME)
	protected String version;
	
	@XmlElement(name=ELEM_USER_NAME)
	protected String userName;
	
	protected Calendar createdDateTime;
	protected Calendar modifiedDateTime;
	
	@XmlAttribute(name=ATTR_SORT_NAME)
	protected SortOrder sortOrder;
	
	public BaseQueryDefImpl() {
		super();
	}

	@XmlElement(name=ELEM_METADATA_BATCH)
	@Override
	public int getBatchSize() {
		return batchSize;
	}

	@Override
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;		
	}

	
	@Override	
	public QueryType getQueryType() {
		return queryType;
	}

	@Override
	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}


	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public Calendar getCreatedDate() {
		return createdDateTime;
	}

	@Override
	public void setCreatedDate(Calendar createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	@Override
	public Calendar getModifiedDate() {
		return this.modifiedDateTime;
	}

	@Override
	public void setModifiedDate(Calendar modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	@Override
	public SortOrder getSortOrder() {
		return sortOrder;
	}

	@Override
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	
	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

}
