package com.tibco.store.query.model.impl;

import com.tibco.store.query.model.QueryExpression;

/**
 * Created by IntelliJ IDEA. User: aathalye Date: 25/12/13 Time: 12:57 PM To
 * change this template use File | Settings | File Templates.
 */
public class ValueExpression<T> implements QueryExpression {

	private T value;

	public ValueExpression(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		if (value == null) {
			return "NULL";
		}
		return value.toString();
	}
}
