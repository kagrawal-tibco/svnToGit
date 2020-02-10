package com.tibco.cep.loadbalancer.impl.message;

import static com.tibco.cep.loadbalancer.impl.message.DistributionKeyMaker.$makeKeys;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.DistributionKey;
import com.tibco.cep.util.annotation.Lazy;

/*
* Author: Ashwin Jayaprakash / Date: Mar 19, 2010 / Time: 2:56:38 PM
*/
//todo Get rid of these Serializable classes

/**
 * Warning! The {@link #getUniqueId()} changes when some headers are set. It is not advisable to use this object
 * directly as a key in Maps and Sets.
 */
public class SimpleDistributableMessage implements DistributableMessage, Serializable {
    protected Object content;

    /**
     * @see #DEFAULT_LIKELY_DUP_DELIVERY
     * @see KnownHeader#__likely_dup_delivery__
     */
    protected boolean likelyDupDelivery;

    /**
     * @see #DEFAULT_ACK_EXPECTED
     * @see KnownHeader#__ack_expected__
     */
    protected boolean ackExpected;

    /**
     * @see #DEFAULT_VERSION_ID
     * @see KnownHeader#__version_id__
     */
    protected long versionId;

    /**
     * @see KnownHeader#__content_id__
     */
    protected String contentId;

    protected String distributionKeySeed;

    protected int numKeysToMake;

    @Lazy
    protected transient DistributionKey[] distributionKeys;

    @Lazy
    protected transient String cachedUniqueId;

    public SimpleDistributableMessage() {
    }

    /**
     * @param content
     * @param contentId
     * @param distributionKeySeed This string will be used to generate a single key - {@link #getPrimaryKey()}.
     */
    public SimpleDistributableMessage(Object content, String contentId, String distributionKeySeed) {
        this(content, contentId, distributionKeySeed, 1);
    }

    /**
     * @param content
     * @param contentId
     * @param distributionKeySeed This string will be used to seed and generate the keys - {@link #getAllKeys()}.
     * @param numKeysToMake       The number of keys to make using the seed.
     */
    public SimpleDistributableMessage(Object content, String contentId, String distributionKeySeed, int numKeysToMake) {
        this.content = content;
        this.contentId = contentId;
        this.distributionKeySeed = distributionKeySeed;
        this.numKeysToMake = numKeysToMake;

        this.likelyDupDelivery = DEFAULT_LIKELY_DUP_DELIVERY;
        this.ackExpected = DEFAULT_ACK_EXPECTED;
        this.versionId = DEFAULT_VERSION_ID;
    }

    public static String constructUniqueId(String contentId, long versionId) {
        return contentId + "." + versionId;
    }

    @Override
    public DistributionKey getPrimaryKey() {
        //Lazy init if deserialized.
        if (distributionKeys == null) {
            this.distributionKeys = $makeKeys(distributionKeySeed, 1);
        }

        return distributionKeys[0];
    }

    @Lazy
    @Override
    public DistributionKey[] getAllKeys() {
        //Lazy init if deserialized.
        if (distributionKeys == null || distributionKeys.length < numKeysToMake) {
            this.distributionKeys = $makeKeys(distributionKeySeed, numKeysToMake);
        }

        return distributionKeys;
    }

    /**
     * @param key
     * @return <code>null</code>
     */
    @Override
    public String getHeaderValue(String key) {
        return null;
    }

    /**
     * No op.
     *
     * @param key
     * @param value
     */
    @Override
    public void setHeaderValue(String key, String value) {
    }

    /**
     * Handles {@link KnownHeader#__likely_dup_delivery__}.
     *
     * @param key
     * @return
     */
    @Override
    public final Object getHeaderValue(KnownHeader key) {
        switch (key) {
            case __likely_dup_delivery__:
                return likelyDupDelivery;

            case __ack_expected__:
                return ackExpected;

            case __content_id__:
                return contentId;

            case __version_id__:
                return versionId;

            default:
                return null;
        }
    }

    /**
     * Handles {@link KnownHeader#__likely_dup_delivery__}, {@link KnownHeader#__version_id__} and {@link
     * KnownHeader#__content_id__}.
     *
     * @param key
     * @param value
     */
    @Override
    public final void setHeaderValue(KnownHeader key, Object value) {
        switch (key) {
            case __likely_dup_delivery__:
                likelyDupDelivery = (value == null) ? DEFAULT_LIKELY_DUP_DELIVERY : (Boolean) value;
                break;

            case __ack_expected__:
                ackExpected = (value == null) ? DEFAULT_ACK_EXPECTED : (Boolean) value;
                break;

            case __content_id__:
                cachedUniqueId = null;
                contentId = (String) value;
                break;

            case __version_id__:
                cachedUniqueId = null;
                versionId = (value == null) ? DEFAULT_VERSION_ID : (Long) value;
                break;

            default:
        }
    }

    /**
     * @return Map with {@link KnownHeader}s.
     */
    @Override
    public Map<String, String> getHeaders() {
        HashMap<String, String> map = new HashMap<String, String>();

        for (KnownHeader knownHeader : KnownHeader.values()) {
            Object v = getHeaderValue(knownHeader);

            map.put(knownHeader.name(), (v == null ? null : v.toString()));
        }

        return map;
    }

    @Override
    public Object getContent() {
        return content;
    }

    @Override
    public void setContent(Object content) {
        this.content = content;
    }

    @Lazy
    @Override
    public final String getUniqueId() {
        if (cachedUniqueId == null) {
            cachedUniqueId = constructUniqueId(contentId, versionId);
        }

        return cachedUniqueId;
    }

    //---------------

    @Override
    public String toString() {
        if (distributionKeys == null) {
            return getClass().getSimpleName()
                    + "{headers=" + getHeaders() + ", keySeed=" + distributionKeySeed + ", distributionKeys=<lazy>}";
        }

        return getClass().getSimpleName() +
                "{headers=" + getHeaders() + ", keySeed=" + distributionKeySeed
                + ", distributionKeys=" + Arrays.asList(distributionKeys) + '}';
    }

    /**
     * Uses {@link #getUniqueId()}.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleDistributableMessage)) {
            return false;
        }

        SimpleDistributableMessage that = (SimpleDistributableMessage) o;

        String thisU = getUniqueId();
        String thatU = that.getUniqueId();

        if (thisU == thatU) {
            return true;
        }

        if (thisU != null && thatU != null) {
            return thisU.equals(thatU);
        }

        return false;
    }

    /**
     * Uses {@link #getUniqueId()}.
     *
     * @return
     */
    @Override
    public int hashCode() {
        String thisU = getUniqueId();

        return thisU != null ? thisU.hashCode() : 0;
    }
}
