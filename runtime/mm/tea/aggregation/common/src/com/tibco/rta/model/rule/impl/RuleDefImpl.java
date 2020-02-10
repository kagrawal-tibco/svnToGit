package com.tibco.rta.model.rule.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CLEARACTIONS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CLEAR_CONDITION;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CREATED_DATE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DESC_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_MODIFIED_DATE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RULE_ENABLED;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RULE_PRIORITY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RULE_SET_ONCE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SCHEDULE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SETACTIONS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SET_CONDITION;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_STREAMING_QUERY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_USER_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_VERSION_NAME;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.impl.MetadataElementImpl;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;
import com.tibco.rta.model.serialize.jaxb.adapter.QueryDefAdapter;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.impl.QueryFilterDefImpl;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class RuleDefImpl extends MetadataElementImpl implements MutableRuleDef {

	private static final long serialVersionUID = 8280457423540780659L;

	protected List<ActionDef> setActionDefs = new ArrayList<ActionDef>();
	protected List<ActionDef> clearActionDefs = new ArrayList<ActionDef>();
	@JsonDeserialize(as=QueryFilterDefImpl.class)
	protected QueryDef setCondition;
	@JsonDeserialize(as=QueryFilterDefImpl.class)
	protected QueryDef clearCondition;
	protected String scheduleName;
	protected String version = "1.0";
	protected String userName;
	protected Calendar createdDateTime;
	protected Calendar modifiedDateTime;
	protected boolean persist;
	protected boolean streamingQuery;

	protected int priority;
	protected boolean isEnabled = true;
	
	protected boolean isSetOnce = false;

	public RuleDefImpl() {

	}

	public RuleDefImpl (String name) {
		super(name);
	}

	public RuleDefImpl (String name, RtaSchema ownerSchema) {
		super(name, ownerSchema);
	}

	@XmlElement(name=ELEM_SCHEDULE_NAME)
	@Override
	public String getScheduleName() {
		return scheduleName;
	}

	@Override
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	@XmlElement(name=ELEM_SET_CONDITION)
	@XmlJavaTypeAdapter(QueryDefAdapter.class)
	@Override
	public QueryDef getSetCondition() {
		return setCondition;
	}

	@Override
	public void setSetCondition(QueryDef setCondition) {
		this.setCondition = setCondition;
	}

	@XmlElement(name=ELEM_CLEAR_CONDITION)
	@XmlJavaTypeAdapter(QueryDefAdapter.class)
	@Override
	public QueryDef getClearCondition() {
		return clearCondition;
	}

	@Override
	public void setClearCondition(QueryDef clearCondition) {
		this.clearCondition = clearCondition;
	}

	@XmlElement(name=ELEM_SETACTIONS_NAME, type=ActionDefImpl.class)
	@Override
	public Collection<ActionDef> getSetActionDefs() {
		return setActionDefs;
	}

	@XmlElement(name=ELEM_CLEARACTIONS_NAME, type=ActionDefImpl.class)
	@Override
	public Collection<ActionDef> getClearActionDefs() {
		return clearActionDefs;
	}


	@XmlElement(name=ELEM_USER_NAME)
	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@XmlElement(name=ELEM_VERSION_NAME)
	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void setVersion(String version) {
		this.version = version;
	}

	@XmlElement(name=ELEM_CREATED_DATE)
	@Override
	public Calendar getCreatedDate() {
		return createdDateTime;
	}

	@Override
	public void setCreatedDate(Calendar createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	@XmlElement(name=ELEM_MODIFIED_DATE)
	@Override
	public Calendar getModifiedDate() {
		return this.modifiedDateTime;
	}

	@Override
	public void setModifiedDate(Calendar modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}


	
	public void addSetActionDef(ActionDef descriptor) {
		setActionDefs.add(descriptor);
	}

	
	public void addClearActionDef(ActionDef descriptor) {
		clearActionDefs.add(descriptor);
	}

	@XmlElement(name=ELEM_RULE_PRIORITY)
	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}

	@XmlElement(name=ELEM_DESC_NAME)
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		 this.description = description;
	}

	@XmlElement(name=ELEM_STREAMING_QUERY)
	@Override
      public boolean isStreamingQuery() {
	      return streamingQuery;
      }

	@Override
      public void setAsStreamingQuery(boolean streamingQuery) {
	      this.streamingQuery = streamingQuery;
      }

	@Override
	public void removeAllSetActionDefs() {
		setActionDefs.clear();
	}

	@Override
	public void removeAllClearActionDefs() {
		clearActionDefs.clear();
	}

	@XmlElement(name=ELEM_RULE_ENABLED)
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	@XmlElement(name=ELEM_RULE_SET_ONCE)
	@Override
	public boolean isSetOnce() {
		return isSetOnce;
	}

	@Override
	public void setSetOnce(boolean isSetOnce) {
		this.isSetOnce = isSetOnce;
	}
	
	
}
