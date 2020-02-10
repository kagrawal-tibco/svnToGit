package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA. User: nprade Date: Oct 24, 2007 Time: 2:56:17 PM To
 * change this template use File | Settings | File Templates.
 */
public interface AliasedIdentifier extends Identifier, Aliased, ProxyContext {
    Stream getStream();

    void setStream(Stream stream);
}
