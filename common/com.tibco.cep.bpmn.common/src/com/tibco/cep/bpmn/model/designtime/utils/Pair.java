package com.tibco.cep.bpmn.model.designtime.utils;

public class Pair<T> {
	private T p;
	private T q;
	public Pair(T p,T q) { this.p = p; this.q = q; }
	T getP(){ return p; }
	T getQ(){ return q; }
	public String toString() {
		return "{"+p.toString()+","+q.toString()+"}";
	}
}