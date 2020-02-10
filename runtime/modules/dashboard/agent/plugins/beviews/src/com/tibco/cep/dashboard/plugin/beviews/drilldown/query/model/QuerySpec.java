package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.dashboard.common.data.TupleSchema;

public class QuerySpec implements Serializable, Cloneable {

	private static final String SELECT_KEYWORD = "select";
	// This is to be used for drilldown
	public static int TIME_FILTER_UNIT_UNKNOWN = -1;
	public static int TIME_FILTER_UNIT_NONE = 5;
	public static int TIME_FILTER_UNIT_SECOND = 10;
	public static int TIME_FILTER_UNIT_MINUTE = 20;
	public static int TIME_FILTER_UNIT_HOUR = 30;
	public static int TIME_FILTER_UNIT_DAY = 40;

	public static int TIME_FILTER_VALUE_NONE = 0;

	/**
	 * serial version uid to maintain backward compatibility
	 */
	static final long serialVersionUID = -1572000138033797631L;

	/**
	 * Reference to the <code>TupleSchema</code>. The <code>TupleSchema</code>
	 * is used to validate the fields being used in the query
	 */
	protected transient TupleSchema mSchema;

	protected String mAlias;

	/**
	 * A list of projection fields.
	 */
	private ArrayList<QueryProjectionField> mProjectionFields = new ArrayList<QueryProjectionField>();

	/**
	 * Reference to the <code>QueryCondition</code> which forms the start of the
	 * query condition
	 */
	private QueryCondition mCondition;

	/**
	 * A list of the order by fields
	 */
	private ArrayList<OrderBySpec> mOrderByFields = new ArrayList<OrderBySpec>();

	/**
	 * A list of the group by fields
	 */
	private ArrayList<String> mGroupByFields = new ArrayList<String>();

	/**
	 * Reference to the <code>QueryCondition</code> which forms the having
	 * condition.
	 */
	private QueryCondition mHavingCondition;

	/**
	 * The name of the query
	 */
	private String name;

	/**
	 * The typeid of the visibility object being queried by this query
	 */
	// I have to add this field since the out alert does not behave like other
	// visibility objects
	private String mTypeId;

	/**
	 * The scope name of the visibility object being queried by this query
	 */
	private String mScopeName;

	/**
	 * Cache for toString.
	 */
	private String mToString;

	protected String selectKeyWord;

	protected QuerySpec(){
		selectKeyWord = SELECT_KEYWORD;
	}

	/**
	 * Creates an instance of <code>QuerySpec</code>.
	 *
	 * @param schema
	 *            The schema which will be used internally to validate added
	 *            fields
	 * @throw IllegalArgumentException if the schema is null
	 */
	public QuerySpec(TupleSchema schema) {
		this();
		setSchema(schema);
	}

	/**
	 * Returns the schema being used by the <code>QuerySpec</code>
	 *
	 * @return the tuple schema
	 */
	public TupleSchema getSchema() {
		return this.mSchema;
	}

	void setSchema(TupleSchema schema) {
		if (schema == null) {
			throw new IllegalArgumentException("Schema cannot be null");
		}
		this.mSchema = schema;
		mTypeId = schema.getTypeID();
		mScopeName = mSchema.getScopeName();
	}

	public void setAlias(String alias) {
		this.mAlias = alias;
		if (mCondition != null) {
			mCondition.setAlias(alias);
		}
		for (QueryProjectionField field : mProjectionFields) {
			field.setAlias(alias);
		}
	}

	public String getAlias() {
		return mAlias;
	}

	/**
	 * Returns the starting condition of the <code>QuerySpec</code>
	 *
	 * @return The <code>QueryCondition</code> which forms the start of the
	 *         <code>QuerySpec</code>
	 */
	public QueryCondition getCondition() {
		return this.mCondition;
	}

	/**
	 * Sets the starting condition of the <code>QuerySpec</code>
	 *
	 * @param aCondition
	 *            The <code>QueryCondition</code> which forms the start of the
	 *            <code>QuerySpec</code>
	 * @throws IllegalArgumentException
	 *             if the schema of the querycondition does not match that of
	 *             the <code>QuerySpec</code>
	 */
	public void setCondition(QueryCondition aCondition) {
		if (aCondition != null) {
			if (!getTypeID().equals(aCondition.getSchema().getTypeID())) {
				throw new IllegalArgumentException("Conditional terms have different schemas.");
			}
		}
		this.mCondition = aCondition;
		this.mCondition.setAlias(mAlias);
		reset();
	}

	public void reset() {
		mToString = null;
	}

	/**
	 * Add another conditional term, adding by the binaryOperator AND/OR.
	 *
	 * @param binaryOperator
	 *            QueryBinaryTerm.AND or QueryBinaryTerm.OR.
	 * @param condition
	 *            The <code>QueryCondition</code> additional conditional term.
	 * @throws IllegalArgumentException
	 *             if the schema of the querycondition does not match that of
	 *             the <code>QuerySpec</code>
	 */
	public void addCondition(String binaryOperator, QueryCondition condition) {
		if (condition == null)
			throw new IllegalArgumentException("Conditional is null.");

		if (!getTypeID().equals(condition.getSchema().getTypeID())) {
			throw new IllegalArgumentException("Conditional terms have different schemas.");
		}

		if (mCondition == null) {
			setCondition(condition);
		} else {
			setCondition(new QueryBinaryTerm(mCondition, binaryOperator, condition));
		}
	}

	/**
	 * Add another conditional term with an AND operator.
	 *
	 * @param condition
	 *            The <code>QueryCondition</code> additional conditional term.
	 * @throws IllegalArgumentException
	 *             if the schema of the querycondition does not match that of
	 *             the <code>QuerySpec</code>
	 */
	public void addAndCondition(QueryCondition condition) {
		addCondition(QueryBinaryTerm.AND, condition);
	}

	/**
	 * Add another conditional term with an OR operator.
	 *
	 * @param condition
	 *            The <code>QueryCondition</code> additional conditional term.
	 * @throws IllegalArgumentException
	 *             if the schema of the querycondition does not match that of
	 *             the <code>QuerySpec</code>
	 */
	public void addOrCondition(QueryCondition condition) {
		addCondition(QueryBinaryTerm.OR, condition);
	}

	/**
	 * Get the projection array.
	 */
	public ArrayList<QueryProjectionField> getProjectionFields() {
		return this.mProjectionFields;
	}

	/**
	 * Add a projection field.
	 *
	 * @param projectionField
	 *            The projection field object.
	 */
	public void addProjectionField(QueryProjectionField projectionField) {
		if (!getTypeID().equals(projectionField.getSchema().getTypeID())) {
			throw new IllegalArgumentException("Projection field has different schema than this query spec.");
		}
		mProjectionFields.add(projectionField);
		projectionField.setAlias(mAlias);
		reset();
	}

	/**
	 * Populate the projection fields from the schema.
	 */
	public void populateProjectionFields() {
		for (int i = 0; i < mSchema.getFieldCount(); i++) {
			addProjectionField(new QueryProjectionField(mSchema, mSchema.getFieldNameByPosition(i)));
		}
	}

	/**
	 * Remove all projection fields.
	 */
	public void removeAllProjectionFields() {
		mProjectionFields.clear();
		reset();
	}

	/**
	 * Returns the order by field definition
	 *
	 * @return A list of the <code>OrderBySpec</code> objects
	 */
	public ArrayList<OrderBySpec> getOrderByFields() {
		return this.mOrderByFields;
	}

	/**
	 * Adds a order by field specifiction to the query.
	 *
	 * @param fieldName
	 *            The name of the field
	 * @param ascending
	 *            Whether to order in ascending or descending order.
	 * @throws IllegalArgumentException
	 *             if the field is non existent
	 */
	public void addOrderByField(String fieldName, boolean ascending) {
		if (mSchema.isDynamic() == false && mSchema.hasField(fieldName) == false) {
			throw new IllegalArgumentException("Field [" + fieldName + "] is not part of schema");
		}

		mOrderByFields.add(new OrderBySpec(fieldName, ascending));
		reset();
	}

	/**
	 * Removes the order by specifition for a field
	 *
	 * @param fieldName
	 *            The name of the field whose order by specification is to be
	 *            removed
	 */
	public void removeOrderByField(String fieldName) {
		for (int i = 0; i < mOrderByFields.size(); i++) {
			OrderBySpec spec = mOrderByFields.get(i);
			if (spec.getOrderByField().equals(fieldName)) {
				mOrderByFields.remove(i);
				return;
			}
		}
		reset();
	}

	/**
	 * Removes all order by specifications.
	 */
	public void removeAllOrderByFields() {
		mOrderByFields.clear();
		reset();
	}

	/**
	 * Returns the order by field definition
	 *
	 * @return a list of the group-by field strings.
	 */
	public List<String> getGroupByFields() {
		return this.mGroupByFields;
	}

	/**
	 * Adds a group by field specifiction to the query.
	 *
	 * @param fieldName
	 *            The name of the field
	 * @throws IllegalArgumentException
	 *             if the field is non existent
	 */
	public void addGroupByField(String fieldName) {
		if (mSchema.isDynamic() == false && mSchema.hasField(fieldName) == false) {
			throw new IllegalArgumentException("Field [" + fieldName + "] is not part of schema");
		}

		mGroupByFields.add(fieldName);
		reset();
	}

	/**
	 * Removes the group by specifition for a field
	 *
	 * @param fieldName
	 *            The name of the field whose order by specification is to be
	 *            removed
	 */
	public void removeGroupByField(String fieldName) {
		mGroupByFields.remove(fieldName);
		reset();
	}

	/**
	 * Removes all group by specifition.
	 */
	public void removeAllGroupByFields() {
		mGroupByFields.clear();
		reset();
	}

	/**
	 * Sets the starting condition for the group-by having clause. The leaf
	 * nodes of the condition tree must be QueryHavingPredicate objects.
	 *
	 * @param havingCondition
	 *            The having condition tree.
	 * @throws IllegalArgumentException
	 */
	public void setGroupByHavingCondition(QueryCondition havingCondition) {
		if (havingCondition != null) {
			if (!getTypeID().equals(havingCondition.getSchema().getTypeID())) {
				throw new IllegalArgumentException("Conditional terms have different schemas.");
			}
		}
		this.mHavingCondition = havingCondition;
		reset();
	}

	/**
	 * Get the starting condition for the group-by having clause.
	 */
	public QueryCondition getGroupByHavingCondition() {
		return this.mHavingCondition;
	}

	/**
	 * Returns a string representation of the <code>QuerySpec</code>
	 */
	@Override
	public String toString() {
		if (mToString == null) {
			try {
				StringBuilder buffer = new StringBuilder(selectKeyWord);
				addProjection(buffer);
				buffer.append(" from ");
				addFrom(buffer);
				if (mCondition != null){
					buffer.append(" where ");
					addCondition(buffer);
				}
				if (mGroupByFields.isEmpty() == false) {
					buffer.append(" group by ");
					addGroupBy(buffer);
				}
				if (mOrderByFields.isEmpty() == false) {
					buffer.append(" order by ");
					addOrderBy(buffer);
				}
				mToString = buffer.toString();
			} catch (Exception e) {
				throw new RuntimeException("could not obtain scope name for " + mTypeId, e);
			}
		}
		return mToString;
	}

	protected void addProjection(StringBuilder buffer) {
		if (mProjectionFields.isEmpty() == true) {
			if (mAlias != null) {
				buffer.append(" "+mAlias+" ");
			}
			else {
				buffer.append(" * ");
			}
		}
		else {
			Iterator<QueryProjectionField> iterator = mProjectionFields.iterator();
			while (iterator.hasNext()) {
				QueryProjectionField projection = (QueryProjectionField) iterator.next();
				buffer.append(" ");
				buffer.append(projection.toString());
				if (iterator.hasNext() == true) {
					buffer.append(",");
				}
			}
		}
	}

	protected void addFrom(StringBuilder buffer) {
		buffer.append(mSchema.getScopeName());
		if (mAlias != null) {
			buffer.append(" "+mAlias);
		}
	}

	protected void addCondition(StringBuilder buffer) {
		buffer.append(mCondition.toString());
	}

	protected void addGroupBy(StringBuilder buffer) {
		Iterator<String> groupByFields = mGroupByFields.iterator();
		while (groupByFields.hasNext()) {
			String fieldName = groupByFields.next();
			buffer.append(FieldToStringConvertor.convert(mAlias, fieldName));
			if (groupByFields.hasNext() == true) {
				buffer.append(", ");
			}
		}
	}

	protected void addOrderBy(StringBuilder buffer) {
		Iterator<OrderBySpec> orderByFields = mOrderByFields.iterator();
		while (orderByFields.hasNext()) {
			OrderBySpec orderBySpec = orderByFields.next();
			buffer.append(FieldToStringConvertor.convert(mAlias, orderBySpec.getOrderByField()));
			if (orderBySpec.getAscending() == true) {
				buffer.append(" asc");
			} else {
				buffer.append(" desc");
			}
			if (orderByFields.hasNext() == true) {
				buffer.append(", ");
			}
		}
	}

	/**
	 * The typeid of the visibility object being queried
	 *
	 * @return typeid of the visibility object being queried
	 */
	private String getTypeID() {
		return mTypeId;
	}

	/**
	 * The scope name of the visibility object being queried
	 *
	 * @return scope name of the visibility object being queried
	 */
	public String getScopeName() {
		return mScopeName;
	}

	/**
	 * Returns the hash code value of this <code>QuerySpec</code>. Generally
	 * used in conjunction with <code>equals</code> in a map. The hash code is
	 * garunteed to be the same for two <code>QuerySpec</code> if both the
	 * objects are equals.
	 *
	 * @return a hash code
	 */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * Checks for equality with some other object. Returns true iff the other
	 * object is either itself or an instance of <code>QuerySpec</code> and the
	 * query condition and order by clause matches completely.
	 *
	 * @return <code>true</code> iff the object matches else <code>false</code>
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof QuerySpec)) {
			return false;
		}
		return toString().equals(obj.toString());
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		QuerySpec clonedQuerySpec = getInstance();
		clonedQuerySpec.mCondition = (QueryCondition) ((this.mCondition == null) ? null : mCondition.clone());
		clonedQuerySpec.mGroupByFields = new ArrayList<String>(mGroupByFields);
		clonedQuerySpec.mOrderByFields = new ArrayList<OrderBySpec>();
		Iterator<OrderBySpec> orderByFieldsIter = this.mOrderByFields.iterator();
		while (orderByFieldsIter.hasNext()) {
			OrderBySpec element = orderByFieldsIter.next();
			clonedQuerySpec.mOrderByFields.add((OrderBySpec) element.clone());
		}
		clonedQuerySpec.mSchema = this.mSchema;
		clonedQuerySpec.mTypeId = this.mTypeId;
		clonedQuerySpec.name = this.name;
		return clonedQuerySpec;
	}

	protected QuerySpec getInstance() {
		QuerySpec clonedQuerySpec = new QuerySpec(this.mSchema);
		return clonedQuerySpec;
	}

//	public void acquireThread(){
//		threadLocalQuerySpec.set(this);
//	}
//
//	public void relinquishThread(){
//		threadLocalQuerySpec.set(null);
//	}
//
//	public static QuerySpec getCurrentQuerySpec(){
//		return threadLocalQuerySpec.get();
//	}
//
//	private static ThreadLocal<QuerySpec> threadLocalQuerySpec = new ThreadLocal<QuerySpec>();
}
