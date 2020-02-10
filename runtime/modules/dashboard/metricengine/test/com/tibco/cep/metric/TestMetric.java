package com.tibco.cep.metric;

public class TestMetric extends MetricConceptImpl {

	public static java.lang.String[] typeTable = { "testStringField",
			"testIntField", "testLongField", "testDoubleField",
			"testBooleanField", "testDateTimeField" };;
	static public java.lang.Class parentPropertyClass = null;
	public static java.lang.String type = "www.tibco.com/be/ontology/Concepts/TestMetric";
	static public int maxPropertyLevel = -1;
	public static com.tibco.xml.data.primitive.ExpandedName concept_expandedName = com.tibco.xml.data.primitive.ExpandedName
			.makeName("www.tibco.com/be/ontology/Concepts/TestMetric",
					"TestMetric");
	protected $1ztestStringField $2ztestStringField;
	protected $1ztestIntField $2ztestIntField;
	protected $1ztestLongField $2ztestLongField;
	protected $1ztestDoubleField $2ztestDoubleField;
	protected $1ztestBooleanField $2ztestBooleanField;
	protected $1ztestDateTimeField $2ztestDateTimeField;

	public com.tibco.cep.runtime.model.element.Concept duplicateThis() {
		TestMetric ret = new TestMetric();
		ret.copy(this);
		if ($2ztestStringField != null) {
			ret.$2ztestStringField = new $1ztestStringField(ret);
			ret.$2ztestStringField.copy($2ztestStringField);
		}
		if ($2ztestIntField != null) {
			ret.$2ztestIntField = new $1ztestIntField(ret);
			ret.$2ztestIntField.copy($2ztestIntField);
		}
		if ($2ztestLongField != null) {
			ret.$2ztestLongField = new $1ztestLongField(ret);
			ret.$2ztestLongField.copy($2ztestLongField);
		}
		if ($2ztestDoubleField != null) {
			ret.$2ztestDoubleField = new $1ztestDoubleField(ret);
			ret.$2ztestDoubleField.copy($2ztestDoubleField);
		}
		if ($2ztestBooleanField != null) {
			ret.$2ztestBooleanField = new $1ztestBooleanField(ret);
			ret.$2ztestBooleanField.copy($2ztestBooleanField);
		}
		if ($2ztestDateTimeField != null) {
			ret.$2ztestDateTimeField = new $1ztestDateTimeField(ret);
			ret.$2ztestDateTimeField.copy($2ztestDateTimeField);
		}
		return ret;
	}

	public com.tibco.cep.runtime.model.element.Property[] getProperties() {
		return new com.tibco.cep.runtime.model.element.Property[] {
				get$2ztestStringField(), get$2ztestIntField(),
				get$2ztestLongField(), get$2ztestDoubleField(),
				get$2ztestBooleanField(), get$2ztestDateTimeField() };
	}

	public com.tibco.cep.runtime.model.element.Property[] getPropertiesNullOK() {
		if (propertyBasedStore()) {
			return nullUnsetProps(getProperties());
		} else {
			return new com.tibco.cep.runtime.model.element.Property[] {
					$2ztestStringField, $2ztestIntField, $2ztestLongField,
					$2ztestDoubleField, $2ztestBooleanField,
					$2ztestDateTimeField };
		}
	}

	public int getNumProperties() {
		return 6;

	}

	protected com.tibco.cep.runtime.model.element.Property getPropertyWithIndex(
			int index) {
		switch (index) {
		case 0:
			return get$2ztestStringField();
		case 1:
			return get$2ztestIntField();
		case 2:
			return get$2ztestLongField();
		case 3:
			return get$2ztestDoubleField();
		case 4:
			return get$2ztestBooleanField();
		case 5:
			return get$2ztestDateTimeField();
		}
		throw new java.lang.RuntimeException("Invalid Property Index " + index);

	}

	public com.tibco.cep.runtime.model.element.Property[] getLocalProperties() {
		return new com.tibco.cep.runtime.model.element.Property[] {
				get$2ztestStringField(), get$2ztestIntField(),
				get$2ztestLongField(), get$2ztestDoubleField(),
				get$2ztestBooleanField(), get$2ztestDateTimeField() };
	}

	public com.tibco.cep.runtime.model.element.Property.PropertyContainedConcept[] getContainedConceptProperties() {
		return null;
	}

	public com.tibco.cep.runtime.model.element.Property.PropertyConceptReference[] getConceptReferenceProperties() {
		return null;
	}

	protected com.tibco.cep.runtime.model.element.Property.PropertyContainedConcept getContainedConceptProperty(
			String propertyName) {
		return super.getContainedConceptProperty(propertyName);
	}

	protected com.tibco.cep.runtime.model.element.Property.PropertyConceptReference getConceptReferenceProperty(
			String propertyName) {
		return super.getConceptReferenceProperty(propertyName);
	}

	public java.lang.String getType() {
		return type;
	}

	public boolean isAutoStartupStateMachine() {
		return true;

	}

	public TestMetric() {
		super();

	}

	public TestMetric(long _id) {
		super(_id);

	}

	public TestMetric(long _id, java.lang.String _extId) {
		super(_id, _extId);

	}

	public void readExternal(java.io.DataInput dataInput) {
		try {
			com.tibco.cep.runtime.model.serializers.DataInputConceptDeserializer deser = new com.tibco.cep.runtime.model.serializers.DataInputConceptDeserializer(
					dataInput);
			this.deserialize(deser);
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new java.lang.RuntimeException(ex);
		}

	}

	public void writeExternal(java.io.DataOutput dataOutput) {
		try {
			com.tibco.cep.runtime.model.serializers.DataOutputConceptSerializer ser = new com.tibco.cep.runtime.model.serializers.DataOutputConceptSerializer(
					dataOutput, this.getClass());
			this.serialize(ser);
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new java.lang.RuntimeException(ex);
		}

	}

	public com.tibco.cep.runtime.model.element.PropertyAtomString get$2ztestStringField() {
		if ($2ztestStringField == null) {
			if (propertyBasedStore())
				return ($1ztestStringField) ((com.tibco.cep.runtime.service.om.PropertyBasedStore) getObjectManager())
						.getProperty(this, $1ztestStringField.class.getName());
			else
				$2ztestStringField = new $1ztestStringField(this);
		}
		return $2ztestStringField;
	}

	public void set$2ztestStringField(java.lang.String value) {
		get$2ztestStringField().setString(value);
	}

	public com.tibco.cep.runtime.model.element.PropertyAtomInt get$2ztestIntField() {
		if ($2ztestIntField == null) {
			if (propertyBasedStore())
				return ($1ztestIntField) ((com.tibco.cep.runtime.service.om.PropertyBasedStore) getObjectManager())
						.getProperty(this, $1ztestIntField.class.getName());
			else
				$2ztestIntField = new $1ztestIntField(this);
		}
		return $2ztestIntField;
	}

	public void set$2ztestIntField(int value) {
		get$2ztestIntField().setInt(value);
	}

	public com.tibco.cep.runtime.model.element.PropertyAtomLong get$2ztestLongField() {
		if ($2ztestLongField == null) {
			if (propertyBasedStore())
				return ($1ztestLongField) ((com.tibco.cep.runtime.service.om.PropertyBasedStore) getObjectManager())
						.getProperty(this, $1ztestLongField.class.getName());
			else
				$2ztestLongField = new $1ztestLongField(this);
		}
		return $2ztestLongField;
	}

	public void set$2ztestLongField(long value) {
		get$2ztestLongField().setLong(value);
	}

	public com.tibco.cep.runtime.model.element.PropertyAtomDouble get$2ztestDoubleField() {
		if ($2ztestDoubleField == null) {
			if (propertyBasedStore())
				return ($1ztestDoubleField) ((com.tibco.cep.runtime.service.om.PropertyBasedStore) getObjectManager())
						.getProperty(this, $1ztestDoubleField.class.getName());
			else
				$2ztestDoubleField = new $1ztestDoubleField(this);
		}
		return $2ztestDoubleField;
	}

	public void set$2ztestDoubleField(double value) {
		get$2ztestDoubleField().setDouble(value);
	}

	public com.tibco.cep.runtime.model.element.PropertyAtomBoolean get$2ztestBooleanField() {
		if ($2ztestBooleanField == null) {
			if (propertyBasedStore())
				return ($1ztestBooleanField) ((com.tibco.cep.runtime.service.om.PropertyBasedStore) getObjectManager())
						.getProperty(this, $1ztestBooleanField.class.getName());
			else
				$2ztestBooleanField = new $1ztestBooleanField(this);
		}
		return $2ztestBooleanField;
	}

	public void set$2ztestBooleanField(boolean value) {
		get$2ztestBooleanField().setBoolean(value);
	}

	public com.tibco.cep.runtime.model.element.PropertyAtomDateTime get$2ztestDateTimeField() {
		if ($2ztestDateTimeField == null) {
			if (propertyBasedStore())
				return ($1ztestDateTimeField) ((com.tibco.cep.runtime.service.om.PropertyBasedStore) getObjectManager())
						.getProperty(this, $1ztestDateTimeField.class.getName());
			else
				$2ztestDateTimeField = new $1ztestDateTimeField(this);
		}
		return $2ztestDateTimeField;
	}

	public void set$2ztestDateTimeField(java.util.Calendar value) {
		get$2ztestDateTimeField().setDateTime(value);
	}

	public com.tibco.cep.runtime.model.element.Property getProperty(
			java.lang.String name) {
		switch (name.hashCode()) {
		case 2101733239: // testStringField
			return get$2ztestStringField();
		case 1255845117: // testIntField
			return get$2ztestIntField();
		case 774403948: // testLongField
			return get$2ztestLongField();
		case 1994335159: // testDoubleField
			return get$2ztestDoubleField();
		case -30741660: // testBooleanField
			return get$2ztestBooleanField();
		case -678858067: // testDateTimeField
			return get$2ztestDateTimeField();
		default:
			return null;
		}
	}

	public com.tibco.cep.runtime.model.element.Property getPropertyNullOK(
			java.lang.String name) {
		if (propertyBasedStore()) {
			return nullUnsetProp(getProperty(name));
		} else {
			switch (name.hashCode()) {
			case 2101733239: // testStringField
				return $2ztestStringField;
			case 1255845117: // testIntField
				return $2ztestIntField;
			case 774403948: // testLongField
				return $2ztestLongField;
			case 1994335159: // testDoubleField
				return $2ztestDoubleField;
			case -30741660: // testBooleanField
				return $2ztestBooleanField;
			case -678858067: // testDateTimeField
				return $2ztestDateTimeField;
			default:
				return null;
			}
		}
	}

	public com.tibco.cep.runtime.model.element.Property newProperty(
			String propClassName) {
		if (propClassName.equals($1ztestStringField.class.getName())) {
			return new $1ztestStringField(this);
		}
		if (propClassName.equals($1ztestIntField.class.getName())) {
			return new $1ztestIntField(this);
		}
		if (propClassName.equals($1ztestLongField.class.getName())) {
			return new $1ztestLongField(this);
		}
		if (propClassName.equals($1ztestDoubleField.class.getName())) {
			return new $1ztestDoubleField(this);
		}
		if (propClassName.equals($1ztestBooleanField.class.getName())) {
			return new $1ztestBooleanField(this);
		}
		if (propClassName.equals($1ztestDateTimeField.class.getName())) {
			return new $1ztestDateTimeField(this);
		}
		return super.newProperty(propClassName);
	}

	public void removeProperties() {
		if (propertyBasedStore()) {
			com.tibco.cep.runtime.service.om.PropertyBasedStore om = (com.tibco.cep.runtime.service.om.PropertyBasedStore) getObjectManager();
			super.removeProperties(om);
			om.removeProperty(this, $1ztestStringField.class.getName());
			om.removeProperty(this, $1ztestIntField.class.getName());
			om.removeProperty(this, $1ztestLongField.class.getName());
			om.removeProperty(this, $1ztestDoubleField.class.getName());
			om.removeProperty(this, $1ztestBooleanField.class.getName());
			om.removeProperty(this, $1ztestDateTimeField.class.getName());
		}
	}

	public void removeProperties(
			com.tibco.cep.runtime.service.om.PropertyBasedStore om) {
		super.removeProperties(om);
		om.removeProperty(this, $1ztestStringField.class.getName());
		om.removeProperty(this, $1ztestIntField.class.getName());
		om.removeProperty(this, $1ztestLongField.class.getName());
		om.removeProperty(this, $1ztestDoubleField.class.getName());
		om.removeProperty(this, $1ztestBooleanField.class.getName());
		om.removeProperty(this, $1ztestDateTimeField.class.getName());

	}

	public void evictProperties() {
		if (propertyBasedStore()) {
			super.evictProperties();
			$2ztestStringField = null;
			$2ztestIntField = null;
			$2ztestLongField = null;
			$2ztestDoubleField = null;
			$2ztestBooleanField = null;
			$2ztestDateTimeField = null;
		}
	}

	public int getMaxPropertyLevel() {
		return maxPropertyLevel;
	}

	public static TestMetric newTestMetric(
			java.lang.String extId, java.lang.String $2ztestStringField,
			int $2ztestIntField, long $2ztestLongField,
			double $2ztestDoubleField, boolean $2ztestBooleanField,
			java.util.Calendar $2ztestDateTimeField) {
		TestMetric instance = new TestMetric(
				com.tibco.cep.runtime.session.RuleSessionManager
						.getCurrentRuleSession().getRuleServiceProvider()
						.getIdGenerator().nextEntityId(), extId);
		instance.set$2ztestStringField($2ztestStringField);
		instance.set$2ztestIntField($2ztestIntField);
		instance.set$2ztestLongField($2ztestLongField);
		instance.set$2ztestDoubleField($2ztestDoubleField);
		instance.set$2ztestBooleanField($2ztestBooleanField);
		instance.set$2ztestDateTimeField($2ztestDateTimeField);
		try {
			if (instance.isAutoStartupStateMachine()
					&& instance.hasMainStateMachine()) {
				instance
						.startStateMachine(com.tibco.cep.runtime.session.RuleSessionManager
								.getCurrentRuleSession()
								.getRuleServiceProvider().getIdGenerator()
								.nextEntityId());
			}
			com.tibco.cep.runtime.session.RuleSessionManager
					.getCurrentRuleSession().assertObject(instance, false);
		} catch (com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException e) {
			throw new java.lang.RuntimeException(e.getMessage(), e);
		}
		return instance;

	}

	public boolean excludeNullProps() {
		return true;
	}

    public boolean includeNullProps() {
        return true;
    }

	public boolean expandPropertyRefs() {
		return false;
	}

	public boolean setNilAttribs() {
		return false;
	}

	public boolean treatNullValues() {
		return false;
	}

	public com.tibco.xml.data.primitive.ExpandedName getExpandedName() {
		return concept_expandedName;
	}

	public boolean hasMainStateMachine() {
		return super.hasMainStateMachine();

	}

	public com.tibco.cep.runtime.model.element.Property.PropertyStateMachine getMainStateMachineProperty() {
		return super.getMainStateMachineProperty();

	}

	public com.tibco.cep.runtime.model.element.Property.PropertyStateMachine[] getStateMachineProperties() {
		return new com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineImpl[] {};

	}

	public com.tibco.cep.runtime.model.element.Property.PropertyStateMachine getStateMachineProperty(
			java.lang.String stateMachineName) {
		return null;

	}

	public com.tibco.cep.runtime.model.element.StateMachineConcept getMainStateMachine() {
		return super.getMainStateMachine();

	}

	static public class $1ztestStringField
			extends
			com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomStringSimple {

		static public int propertyIndex = -1;
		static public int propertyLevel = -1;
		static public int propertyId = 0;
		static public int historyPolicy = 0;

		public $1ztestStringField(
				com.tibco.cep.runtime.model.element.Concept subject) {
			super(subject);
		}

		$1ztestStringField(com.tibco.cep.runtime.model.element.Concept subject,
				java.lang.String defaultValue) {
			super(subject, defaultValue);
		}

		public java.lang.String getName() {
			return "testStringField";
		}

		public int getPropertyIndex() {
			return propertyIndex;
		}

		public int getPropertyLevel() {
			return propertyLevel;
		}

		public int getPropertyId() {
			return propertyId;
		}

		static public int getIndex() {
			return propertyIndex;
		}

		static public int getLevel() {
			return propertyLevel;
		}

		public int getHistoryPolicy() {
			return historyPolicy;
		}
	}

	static public class $1ztestIntField
			extends
			com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomIntSimple {

		static public int propertyIndex = -1;
		static public int propertyLevel = -1;
		static public int propertyId = 1;
		static public int historyPolicy = 0;

		public $1ztestIntField(
				com.tibco.cep.runtime.model.element.Concept subject) {
			super(subject);
		}

		$1ztestIntField(com.tibco.cep.runtime.model.element.Concept subject,
				int defaultValue) {
			super(subject, defaultValue);
		}

		public java.lang.String getName() {
			return "testIntField";
		}

		public int getPropertyIndex() {
			return propertyIndex;
		}

		public int getPropertyLevel() {
			return propertyLevel;
		}

		public int getPropertyId() {
			return propertyId;
		}

		static public int getIndex() {
			return propertyIndex;
		}

		static public int getLevel() {
			return propertyLevel;
		}

		public int getHistoryPolicy() {
			return historyPolicy;
		}
	}

	static public class $1ztestLongField
			extends
			com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomLongSimple {

		static public int propertyIndex = -1;
		static public int propertyLevel = -1;
		static public int propertyId = 2;
		static public int historyPolicy = 0;

		public $1ztestLongField(
				com.tibco.cep.runtime.model.element.Concept subject) {
			super(subject);
		}

		$1ztestLongField(com.tibco.cep.runtime.model.element.Concept subject,
				long defaultValue) {
			super(subject, defaultValue);
		}

		public java.lang.String getName() {
			return "testLongField";
		}

		public int getPropertyIndex() {
			return propertyIndex;
		}

		public int getPropertyLevel() {
			return propertyLevel;
		}

		public int getPropertyId() {
			return propertyId;
		}

		static public int getIndex() {
			return propertyIndex;
		}

		static public int getLevel() {
			return propertyLevel;
		}

		public int getHistoryPolicy() {
			return historyPolicy;
		}
	}

	static public class $1ztestDoubleField
			extends
			com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomDoubleSimple {

		static public int propertyIndex = -1;
		static public int propertyLevel = -1;
		static public int propertyId = 3;
		static public int historyPolicy = 0;

		public $1ztestDoubleField(
				com.tibco.cep.runtime.model.element.Concept subject) {
			super(subject);
		}

		$1ztestDoubleField(com.tibco.cep.runtime.model.element.Concept subject,
				double defaultValue) {
			super(subject, defaultValue);
		}

		public java.lang.String getName() {
			return "testDoubleField";
		}

		public int getPropertyIndex() {
			return propertyIndex;
		}

		public int getPropertyLevel() {
			return propertyLevel;
		}

		public int getPropertyId() {
			return propertyId;
		}

		static public int getIndex() {
			return propertyIndex;
		}

		static public int getLevel() {
			return propertyLevel;
		}

		public int getHistoryPolicy() {
			return historyPolicy;
		}
	}

	static public class $1ztestBooleanField
			extends
			com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomBooleanSimple {

		static public int propertyIndex = -1;
		static public int propertyLevel = -1;
		static public int propertyId = 4;
		static public int historyPolicy = 0;

		public $1ztestBooleanField(
				com.tibco.cep.runtime.model.element.Concept subject) {
			super(subject);
		}

		$1ztestBooleanField(
				com.tibco.cep.runtime.model.element.Concept subject,
				boolean defaultValue) {
			super(subject, defaultValue);
		}

		public java.lang.String getName() {
			return "testBooleanField";
		}

		public int getPropertyIndex() {
			return propertyIndex;
		}

		public int getPropertyLevel() {
			return propertyLevel;
		}

		public int getPropertyId() {
			return propertyId;
		}

		static public int getIndex() {
			return propertyIndex;
		}

		static public int getLevel() {
			return propertyLevel;
		}

		public int getHistoryPolicy() {
			return historyPolicy;
		}
	}

	static public class $1ztestDateTimeField
			extends
			com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomDateTimeSimple {

		static public int propertyIndex = -1;
		static public int propertyLevel = -1;
		static public int propertyId = 5;
		static public int historyPolicy = 0;

		public $1ztestDateTimeField(
				com.tibco.cep.runtime.model.element.Concept subject) {
			super(subject);
		}

		$1ztestDateTimeField(
				com.tibco.cep.runtime.model.element.Concept subject,
				java.util.Calendar defaultValue) {
			super(subject, defaultValue);
		}

		public java.lang.String getName() {
			return "testDateTimeField";
		}

		public int getPropertyIndex() {
			return propertyIndex;
		}

		public int getPropertyLevel() {
			return propertyLevel;
		}

		public int getPropertyId() {
			return propertyId;
		}

		static public int getIndex() {
			return propertyIndex;
		}

		static public int getLevel() {
			return propertyLevel;
		}

		public int getHistoryPolicy() {
			return historyPolicy;
		}
	}
}
