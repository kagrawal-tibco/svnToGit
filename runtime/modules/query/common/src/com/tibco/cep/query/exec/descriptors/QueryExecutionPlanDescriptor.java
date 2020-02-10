package com.tibco.cep.query.exec.descriptors;

import java.util.LinkedHashMap;
import java.util.List;

import com.tibco.be.util.idgenerators.serial.PrefixedLeftPaddedNumericGenerator;
import com.tibco.cep.query.exec.descriptors.impl.AggregationDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.BridgeDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.DistinctFilteredStreamDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.FilterDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.JoinDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.PartitionDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.ProxyDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SinkDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SortedStreamDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SourceDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.TransformedStreamDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.TruncatedStreamDescriptor;
import com.tibco.cep.query.exec.impl.NameGenerator;
import com.tibco.cep.query.service.Query;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jan 13, 2009
* Time: 6:37:07 PM
*/
public interface QueryExecutionPlanDescriptor {


    AggregationDescriptor getAggregationDescriptor();


    BridgeDescriptor getBridgeDescriptor();


    LinkedHashMap<String, String> getColumnNameToType();


    DistinctFilteredStreamDescriptor getDistinctDescriptor();


    List<FilterDescriptor> getFilterDescriptors();


    List<FilterDescriptor> getHavingFilterDescriptors();


    PrefixedLeftPaddedNumericGenerator getIdGenerator();


    JoinDescriptor getJoinDescriptor();


    TruncatedStreamDescriptor getLimitDescriptor();


    NameGenerator getNameGenerator();


    LinkedHashMap<String, Object> getNameToObject();


    List<PartitionDescriptor> getPartitionDescriptors();


    List<ProxyDescriptor> getProxyDescriptors();


    Query getQuery();

    void setSelfJoin(boolean flag);

    boolean isSelfJoin();

    String getRegionName();


    SinkDescriptor getSinkDescriptor();


    SortedStreamDescriptor getSortDescriptor();


    List<SourceDescriptor> getSourceDescriptors();


    TransformedStreamDescriptor getTransformDescriptor();


    void setAggregationDescriptor(AggregationDescriptor aggregationDescriptor);


    void setBridgeDescriptor(BridgeDescriptor bridgeDescriptor);


    void setColumnNameToType(LinkedHashMap<String, String> columnNameToType);


    void setDistinctDescriptor(DistinctFilteredStreamDescriptor distinctDescriptor);


    void setJoinDescriptor(JoinDescriptor joinDescriptor);


    void setLimitDescriptor(TruncatedStreamDescriptor limitDescriptor);


    void setSinkDescriptor(SinkDescriptor sinkDescriptor);


    void setSortDescriptor(SortedStreamDescriptor sortDescriptor);


    void setTransformDescriptor(TransformedStreamDescriptor transformDescriptor);


}
