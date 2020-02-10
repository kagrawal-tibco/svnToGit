/**
 * 
 */
package com.tibco.cep.studio.ui.editors.utils.kstreams;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shivkumarchelwa
 *
 */
public class TransformationBuilder {

	public static Transformation buildTransformation(String type) {
		Transformation t = new Transformation();
		t.setType(type);
		if (KSTREAM_TYPES.FILTER.getName().equals(type) || KSTREAM_TYPES.FILTER_NOT.getName().equals(type)) {
			addFilterInputs(t);
		} else if (KSTREAM_TYPES.FLAT_MAP_VALUES.getName().equals(type)
				|| KSTREAM_TYPES.MAP_VALUES.getName().equals(type)) {
			addMapValuesInputs(t);
		} else if (KGROUPEDSTREAM_TYPES.AGGREGATE.getName().equals(type)) {
			addAggregateInputs(t);
		} else if (KSTREAM_TYPES.JOIN.getName().equals(type) || KSTREAM_TYPES.LEFT_JOIN.getName().equals(type)
				|| KSTREAM_TYPES.OUTER_JOIN.getName().equals(type)) {
			addJoinInputs(t);
		} else if (KSTREAM_TYPES.SELECT_KEY.getName().equals(type)) {
			addSelectKeyInputs(t);
		} else if (KSTREAM_TYPES.GROUP_BY_KEY.getName().equals(type)) {
			addGroupByKeyInputs(t);
		} else if (KGROUPEDSTREAM_TYPES.COUNT.getName().equals(type)) {
			addCountInputs(t);
		}

		return t;
	}

	public static Transformation addAggregateInputs(Transformation t) {
		t.addInput(TransformationConstants.INPUT_INITIALIZER, null);
		t.addInput(TransformationConstants.INPUT_AGGREGATOR, null);
		return t;
	}

	public static Transformation addReduceInputs(Transformation t) {
		t.addInput(TransformationConstants.INPUT_REDUCER, null);
		return t;
	}

	public static Transformation addJoinInputs(Transformation t) {
		t.addInput(TransformationConstants.INPUT_JOIN_TOPIC, null);
		t.addInput(TransformationConstants.INPUT_VALUE_JOINER, null);
		Map<String, Long> joinWindowMap = new LinkedHashMap<String, Long>();
		joinWindowMap.put(TransformationConstants.INPUT_JOINWINDOWS_WINDOW, 0L);
		joinWindowMap.put(TransformationConstants.INPUT_JOINWINDOWS_AFTER, 0L);
		joinWindowMap.put(TransformationConstants.INPUT_JOINWINDOWS_BEFORE, 0L);
		joinWindowMap.put(TransformationConstants.INPUT_JOINWINDOWS_GRACE, 0L);
		t.addInput(TransformationConstants.INPUT_JOINWINDOWS, joinWindowMap);

		Map<String, String> joinWindowUnitMap = new LinkedHashMap<String, String>();
		joinWindowUnitMap.put(TransformationConstants.INPUT_JOINWINDOWS_WINDOW,
				TransformationConstants.DURATION_TYPES[3]);
		joinWindowUnitMap.put(TransformationConstants.INPUT_JOINWINDOWS_AFTER,
				TransformationConstants.DURATION_TYPES[3]);
		joinWindowUnitMap.put(TransformationConstants.INPUT_JOINWINDOWS_BEFORE,
				TransformationConstants.DURATION_TYPES[3]);
		joinWindowUnitMap.put(TransformationConstants.INPUT_JOINWINDOWS_GRACE,
				TransformationConstants.DURATION_TYPES[3]);
		t.addInput(TransformationConstants.INPUT_JOINWINDOWUNITS, joinWindowUnitMap);

		return t;
	}

	public static Transformation addFilterInputs(Transformation t) {
		t.addInput(TransformationConstants.INPUT_PREDICATE, null);
		return t;
	}

	public static Transformation addMapValuesInputs(Transformation t) {
		t.addInput(TransformationConstants.INPUT_VALUEMAPPER, null);
		return t;
	}

	public static Transformation addSelectKeyInputs(Transformation t) {
		t.addInput(TransformationConstants.INPUT_KEYVALUEMAPPER, null);
		return t;
	}

	public static Transformation addGroupByKeyInputs(Transformation t) {
		return t;
	}

	public static Transformation addCountInputs(Transformation t) {
		return t;
	}
}
