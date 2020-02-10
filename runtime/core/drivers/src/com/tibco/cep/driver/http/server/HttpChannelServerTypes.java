package com.tibco.cep.driver.http.server;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 19, 2009
 * Time: 10:09:01 AM
 * To change this template use File | Settings | File Templates.
 */
public enum HttpChannelServerTypes {

    HC_SYNC("HTTP SYNC"), HC_ASYNC("BUILT-IN"), TOMCAT("TOMCAT");

    private String literal;

    private HttpChannelServerTypes(final String literal) {
        this.literal = literal;
    }

    private static final HttpChannelServerTypes[] VALUES_ARRAY =
            new HttpChannelServerTypes[] {
                 HC_SYNC,
                 HC_ASYNC,
                 TOMCAT
            };

    public static HttpChannelServerTypes getByLiteral(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			HttpChannelServerTypes result = VALUES_ARRAY[i];
			if (result.toString().equalsIgnoreCase(literal)) {
				return result;
			}
		}
		return HC_ASYNC;// default server type
	}

    public String getLiteral() {
        return literal;
    }

    public String toString() {
        return literal;
    }
}

