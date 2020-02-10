package com.tibco.cep.dashboard.psvr.vizengine;

import com.tibco.cep.dashboard.common.data.Tuple;

/**
 * @deprecated
 * @author anpatil
 *
 */
public class CategoryBucket {

	private CategoryTick tick;

	private Tuple tuple;

	public CategoryBucket(CategoryTick tick, Tuple tuple) {
		super();
		this.tick = tick;
		this.tuple = tuple;
	}

	public CategoryTick getTick() {
		return tick;
	}

	public Tuple getTuple() {
		return tuple;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tick == null) ? 0 : tick.hashCode());
		result = prime * result + ((tuple == null) ? 0 : tuple.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryBucket other = (CategoryBucket) obj;
		if (tick == null) {
			if (other.tick != null)
				return false;
		} else if (!tick.equals(other.tick))
			return false;
		if (tuple == null) {
			if (other.tuple != null)
				return false;
		} else if (!tuple.equals(other.tuple))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CategoryBucket[");
		sb.append("tick=");
		sb.append(tick);
		sb.append(",tuple=");
		sb.append(tuple);
		sb.append("]");
		return sb.toString();
	}
}