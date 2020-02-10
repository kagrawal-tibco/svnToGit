package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.TupleSchema;

/**
 * Base class for the query condition term in the conditional expression.
 */
public abstract class QueryCondition implements Serializable, Cloneable {

	static final long serialVersionUID = -1572000138033797632L;

	protected transient TupleSchema mSchema;

	protected String mAlias;

	protected transient Map<String,String> annotations;

	public QueryCondition(TupleSchema schema) {
		setSchema(schema);
		annotations = new HashMap<String, String>();
	}

	public TupleSchema getSchema() {
		return this.mSchema;
	}

	void setSchema(TupleSchema schema) {
		if (schema == null) {
			throw new IllegalArgumentException("Missing schema.");
		}
		this.mSchema = schema;
	}

	public String getTypeId() {
		return mSchema.getTypeID();
	}

	/**
	 * Base class version of equals(). Derived class should override this and
	 * call this method to do the common equal checking before doing its own
	 * checking.
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// Not even the same class.
		if (obj.getClass() != this.getClass())
			return false;

		// See if this == obj
		return super.equals(obj);
	}

	/** Derived class has to implement it. */
	@Override
	public abstract Object clone() throws CloneNotSupportedException;

	public void addAnnotation(String key, String value){
		annotations.put(key, value);
	}

	public String removeAnnotation(String key){
		return annotations.remove(key);
	}

	public String getAnnotation(String key){
		return annotations.get(key);
	}

	protected void setAlias(String alias) {
		this.mAlias = alias;
	}

}