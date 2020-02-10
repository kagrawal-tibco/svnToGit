package com.tibco.cep.query.exec.impl;

import com.tibco.be.util.idgenerators.serial.PrefixedLeftPaddedNumericGenerator;

/**
 * Generator for names of various components in the QEP class.
 */
public class NameGenerator {

    protected static final PrefixedLeftPaddedNumericGenerator qepIdGenerator = new PrefixedLeftPaddedNumericGenerator(
            "QEP", false, ("" + Long.MAX_VALUE).length());

    private final PrefixedLeftPaddedNumericGenerator idGenerator;


    public NameGenerator(PrefixedLeftPaddedNumericGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }


    public String makeAliasMapName() {
        return "ALIAS_MAP_" + this.idGenerator.nextIdentifier();
    }


    public String makeComparatorsName() {
        return "CMPR_" + this.idGenerator.nextIdentifier();
    }


    public String makeEvaluatorName() {
        return "EVAL_" + this.idGenerator.nextIdentifier();
    }

    public String makeExpressionClassName() {
        return "Expression" + this.idGenerator.nextIdentifier();
    }


    public String makeExpressionName() {
        return "EXPR_" + this.idGenerator.nextIdentifier();
    }


    public String makeExtractorsName() {
        return "TVES_" + this.idGenerator.nextIdentifier();
    }


    public String makeGroupAggregateInfoName() {
        return "GRPAGGR_" + this.idGenerator.nextIdentifier();
    }


    public String makeLiteralName() {
        return "LITERAL_" + this.idGenerator.nextIdentifier();
    }


    public String makeLocalAggregationName() {
        return "aggr_" + this.idGenerator.nextIdentifier();
    }


    public String makeLocalBridgeName() {
        return "bridge_" + this.idGenerator.nextIdentifier();
    }


    public String makeLocalDeletedName() {
        return "del_" + this.idGenerator.nextIdentifier();
    }

    
    public String makeLocalDistinctName() {
        return "distinct_" + this.idGenerator.nextIdentifier();
    }


    public String makeLocalFilterName() {
        return "filter_" + this.idGenerator.nextIdentifier();
    }

    public String makeLocalGroupAggregateItemInfoMapName() {
        return "grpAggrMap_" + this.idGenerator.nextIdentifier();
    }


    public String makeLocalInsertName() {
        return "insert_" + this.idGenerator.nextIdentifier();
    }

    public String makeLocalJoinName() {
        return "join_" + this.idGenerator.nextIdentifier();
    }


    public String makeLocalLimitName() {
        return "limit_" + this.idGenerator.nextIdentifier();
    }


    public String makeLocalPartitionName() {
        return "partition_" + this.idGenerator.nextIdentifier();
    }


    public String makeLocalSinkName() {
        return "sink_" + this.idGenerator.nextIdentifier();
    }


    public String makeLocalSortName() {
        return "sort_" + this.idGenerator.nextIdentifier();
    }


    public String makeLocalSourceName() {
        return "source_" + this.idGenerator.nextIdentifier();
    }


    public String makeLocalTransformName() {
        return "transf_" + this.idGenerator.nextIdentifier();
    }


    public String makeQEPClassName(String packageName) {
        return packageName + "." + qepIdGenerator.nextIdentifier();
    }


    public String makeRuleFunctionInstanceName() {
        return "RF_" + this.idGenerator.nextIdentifier();
    }

    public String makeSlidingWindowBuilderName() {
        return "SLIDINGWB_" + this.idGenerator.nextIdentifier();
    }


    public String makeSlidingWindowInfoName() {
        return "SLIDINGWI_" + this.idGenerator.nextIdentifier();
    }


    public String makeSortInfoName() {
        return "SORT_INFO_" + this.idGenerator.nextIdentifier();
    }


    public String makeSortItemInfoName() {
        return "SORT_ITEM_" + this.idGenerator.nextIdentifier();
    }


    public String makeTimeWindowBuilderName() {
        return "TIMEWB_" + this.idGenerator.nextIdentifier();
    }


    public String makeTimeWindowInfoName() {
        return "TIMEWI_" + this.idGenerator.nextIdentifier();
    }


    public String makeTransformationInfoName() {
        return "TR_INFO_" + this.idGenerator.nextIdentifier();
    }


    public String makeTransformationMapName() {
        return "TRANSFINFO_MAP_" + this.idGenerator.nextIdentifier();
    }


    public String makeTupleClassName() {
        return "T" + this.idGenerator.nextIdentifier();
    }


    public String makeTupleInfoClassName() {
        return "TupleInfo" + this.idGenerator.nextIdentifier();
    }


    public String makeTupleInfoName() {
        return "TUPLE_INFO_" + this.idGenerator.nextIdentifier();
    }


    public String makeTumblingWindowBuilderName() {
        return "TUMBLINGWB_" + this.idGenerator.nextIdentifier();
    }


    public String makeTumblingWindowInfoName() {
        return "TUMBLINGWI_" + this.idGenerator.nextIdentifier();
    }


    public String makeTVEClassName() {
        return "TupleValueExtractor" + this.idGenerator.nextIdentifier();
    }


    public String makeTVEName() {
        return "TVE_" + this.idGenerator.nextIdentifier();
    }


}//class
