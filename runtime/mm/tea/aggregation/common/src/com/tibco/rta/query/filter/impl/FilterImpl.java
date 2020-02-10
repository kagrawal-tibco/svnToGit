package com.tibco.rta.query.filter.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tibco.rta.query.filter.Filter;


@XmlAccessorType(XmlAccessType.NONE)
public class FilterImpl implements Filter {

	private static final long serialVersionUID = -5403173295259518421L;
	@JsonBackReference
	protected Filter parent;

	public Filter getParent() {
		return parent;
	}

	public void setParent(Filter parent) {
		this.parent = parent;
	}

}
