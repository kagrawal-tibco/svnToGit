package com.tibco.cep.loadbalancer.message;

import java.util.Map;

import com.tibco.cep.util.annotation.Lazy;
import com.tibco.cep.util.annotation.Optional;
import com.tibco.cep.util.annotation.ReadOnly;

/*
* Author: Ashwin Jayaprakash / Date: Mar 18, 2010 / Time: 3:51:06 PM
*/

public interface DistributableMessage {
    DistributionKey getPrimaryKey();

    /**
     * Use {@link #getPrimaryKey()} first.
     *
     * @return Including the primary which should be the first in the collection.
     */
    @Lazy
    DistributionKey[] getAllKeys();

    String getHeaderValue(String key);

    /**
     * @param key
     * @param value If <code>null</code>, then it means resetting to the default.
     */
    @Optional("May or may not be supported by the underlying implementation." +
            " There are also no guarantees that this will be persisted.")
    void setHeaderValue(String key, String value);

    /**
     * @param key
     * @return Same type as {@link KnownHeader#getValueType()}.
     */
    Object getHeaderValue(KnownHeader key);

    /**
     * @param key
     * @param value Same type as {@link KnownHeader#getValueType()}.
     */
    @Optional("May or may not be supported by the underlying implementation." +
            " There are also no guarantees that this will be persisted.")
    void setHeaderValue(KnownHeader key, Object value);

    /**
     * @return Includes both {@link #getHeaderValue(String)} and {@link #getHeaderValue(KnownHeader)}.
     */
    @ReadOnly
    Map<String, String> getHeaders();

    Object getContent();

    void setContent(Object content);
    
    /**
     * @return It is advisable to use {@link KnownHeader#__content_id__} and {@link KnownHeader#__version_id__} for
     *         equals-hashcode.
     */
    Object getUniqueId();

    //--------------

    /**
     * {@value}.
     */
    boolean DEFAULT_LIKELY_DUP_DELIVERY = false;

    /**
     * {@value}.
     */
    boolean DEFAULT_ACK_EXPECTED = false;

    /**
     * {@value}.
     */
    int DEFAULT_VERSION_ID = -1;

    public static enum KnownHeader {
        /**
         * The content's id - like JMS Message Id.
         */
        __content_id__(String.class),
        /**
         * The same message could get delivered to multiple sinks either because of network errors or redelivery on
         * error recovery. This number has to change for every delivery.
         */
        __version_id__(Long.class),
        __likely_dup_delivery__(Boolean.class),
        __ack_expected__(Boolean.class);

        Class valueType;

        KnownHeader(Class valueType) {
            this.valueType = valueType;
        }

        public Class getValueType() {
            return valueType;
        }

        public Object parseValue(String valAsString) {
            switch (this) {
                case __likely_dup_delivery__:
                    return Boolean.parseBoolean(valAsString);

                case __ack_expected__:
                    return Boolean.parseBoolean(valAsString);

                case __version_id__:
                    return Long.parseLong(valAsString);

                case __content_id__:
                default:
                    return valAsString;
            }
        }
    }
}
