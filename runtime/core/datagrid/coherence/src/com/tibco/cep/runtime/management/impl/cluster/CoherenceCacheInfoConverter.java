package com.tibco.cep.runtime.management.impl.cluster;

import java.util.HashMap;
import java.util.Map;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import com.tibco.cep.runtime.management.CacheInfo;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Apr 16, 2009 Time: 5:19:23 PM
*/
public class CoherenceCacheInfoConverter {
    public static enum CoherenceMBeanAttribute {
        Size,

        TotalGets,
        TotalPuts,

        AverageGetMillis,
        AveragePutMillis,

        HitProbability,

        HighUnits,
        LowUnits,

        ExpiryDelay
    }

    protected static final Map<String, CoherenceMBeanAttribute> ATTR_STRINGS_AND_ENUMS =
            new HashMap<String, CoherenceMBeanAttribute>() {
                {
                    for (CoherenceMBeanAttribute attribute : CoherenceMBeanAttribute
                            .values()) {
                        put(attribute.name(), attribute);
                    }
                }
            };

    /**
     * @param mBeanServer
     * @param cacheName
     * @param objectNameOfCache
     * @return <code>null</code> if it throws an {@link javax.management.InstanceNotFoundException}.
     * @throws IntrospectionException
     * @throws ReflectionException
     * @throws AttributeNotFoundException
     * @throws MBeanException
     */
    public static CacheInfo convert(MBeanServer mBeanServer,
                                    String cacheName,
                                    ObjectName objectNameOfCache)
            throws IntrospectionException, ReflectionException, AttributeNotFoundException,
            MBeanException {
        CacheInfoImpl cacheInfo = new CacheInfoImpl();

        cacheInfo.setName(new FQName(cacheName));

        try {
            MBeanInfo mbeanInfo = mBeanServer.getMBeanInfo(objectNameOfCache);

            for (MBeanAttributeInfo attributeInfo : mbeanInfo.getAttributes()) {
                String key = attributeInfo.getName();

                CoherenceMBeanAttribute attribute = ATTR_STRINGS_AND_ENUMS.get(key);
                if (attribute == null) {
                    continue;
                }

                Object value = mBeanServer.getAttribute(objectNameOfCache, key);
                switch (attribute) {
                    case Size:
                        cacheInfo.setSize((Integer) value);
                        break;

                    case TotalGets:
                        cacheInfo.setNumberOfGets((Long) value);
                        break;
                    case TotalPuts:
                        cacheInfo.setNumberOfPuts((Long) value);
                        break;

                    case AverageGetMillis:
                        cacheInfo.setAvgGetTimeMillis((Double) value);
                        break;
                    case AveragePutMillis:
                        cacheInfo.setAvgPutTimeMillis((Double) value);
                        break;

                    case HitProbability:
                        cacheInfo.setHitRatio((Double) value);
                        break;

                    case HighUnits:
                        cacheInfo.setMaxSize((Integer) value);
                        break;
                    case LowUnits:
                        cacheInfo.setMinSize((Integer) value);
                        break;

                    case ExpiryDelay:
                        cacheInfo.setExpiryDelayMillis((Integer) value);
                        break;
                }
            }
        }
        catch (InstanceNotFoundException e) {
            return null;
        }

        return cacheInfo;
    }
}
