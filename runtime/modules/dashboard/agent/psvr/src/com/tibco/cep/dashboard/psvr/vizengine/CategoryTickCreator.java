package com.tibco.cep.dashboard.psvr.vizengine;

import com.tibco.cep.dashboard.common.data.Tuple;

public interface CategoryTickCreator {

	abstract CategoryTick getCategoryTick(String tickLabel, String tickValue, Tuple tuple);

	abstract void updateCollidingTick(CategoryTick tick, String tickValue, Tuple tuple);

}