package com.tibco.rta.service.rs.resource;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/2/14
 * Time: 6:19 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "QueryState")
public class QueryState {

    private String hasNextUrl;

    private String nextUrl;

    public QueryState(String hasNextUrl, String nextUrl) {
        this.hasNextUrl = hasNextUrl;
        this.nextUrl = nextUrl;
    }

    @XmlElement
    public String getHasNextUrl() {
        return hasNextUrl;
    }

    @XmlElement
    public String getNextUrl() {
        return nextUrl;
    }
}
