package com.tibco.rta.query;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/5/14
 * Time: 10:52 AM
 *
 * Marker interface to denote a result tuple obtained by querying the server.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = QueryResultTuple.class, name = "queryResultTuples"),
        @JsonSubTypes.Type(value = FactResultTuple.class, name = "factResultTuples")})
public interface ResultTuple {
}
