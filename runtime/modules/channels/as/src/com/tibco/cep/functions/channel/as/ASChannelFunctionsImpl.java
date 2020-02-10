package com.tibco.cep.functions.channel.as;

import static com.tibco.cep.driver.as.ASConstants.K_BE_EVENT_PROP_CONSUMPTION_MODE;
import static com.tibco.cep.driver.as.ASConstants.K_BE_EVENT_PROP_EVENT_TYPE;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.tibco.as.space.ASException;
import com.tibco.as.space.LockOptions;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.SpaceResultList;
import com.tibco.as.space.TransactionId;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.UnlockOptions;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.cep.driver.as.IASChannel;
import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.driver.as.utils.ASUtils;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.model.serializers.SerializableLite;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.xml.data.primitive.ExpandedName;

public class ASChannelFunctionsImpl {

    public static String getConsumptionMode(SimpleEvent event) {
        return (String) getEventProperty(event, K_BE_EVENT_PROP_CONSUMPTION_MODE);
    }

    public static String getEventType(SimpleEvent event) {
        return (String) getEventProperty(event, K_BE_EVENT_PROP_EVENT_TYPE);
    }

    public static void beginTransaction(String channelUri) {
        IASChannel asChannel = getASChannel(channelUri);
        try {
            asChannel.getMetaspace().beginTransaction();
        }
        catch (ASException asEx) {
            throw new RuntimeException(asEx);
        }
    }

    public static void commitTransaction(String channelUri) {
        IASChannel asChannel = getASChannel(channelUri);
        try {
            asChannel.getMetaspace().commitTransaction();
        }
        catch (ASException asEx) {
            throw new RuntimeException(asEx);
        }
    }

    public static void rollbackTransaction(String channelUri) {
        IASChannel asChannel = getASChannel(channelUri);
        try {
            asChannel.getMetaspace().rollbackTransaction();
        }
        catch (ASException asEx) {
            throw new RuntimeException(asEx);
        }
    }

    public static Object getEvent(String destinationUri, SimpleEvent key) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        IASDestination asDestination = getASDestination(destinationUri, session);
        Tuple condition = Tuple.create();
        try {
            ASUtils.fillTupleWithEvent(asDestination.getSpaceDef(), condition, key, session);
            Tuple result = asDestination.getSpace().get(condition);
            return result != null ? ASUtils.convertTuple(asDestination, key, result) : null;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object[] getEvents(String destinationUri, SimpleEvent[] keys) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        IASDestination asDestination = getASDestination(destinationUri, session);
        try {
            Collection<Tuple> conditions = new HashSet<Tuple>();
            for (SimpleEvent key : keys) {
                Tuple condition = Tuple.create();
                ASUtils.fillTupleWithEvent(asDestination.getSpaceDef(), condition, key, session);
                conditions.add(condition);
            }
            SpaceResultList spaceResults = asDestination.getSpace().getAll(conditions);
            Collection<Tuple> tuples = spaceResults.getTuples();
            SimpleEvent eventTemplate = ASUtils.findSimpleEventTemplate(keys);
            return ASUtils.convertTuples(asDestination, eventTemplate, tuples);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object takeEvent(String destinationUri, SimpleEvent key) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        IASDestination asDestination = getASDestination(destinationUri, session);
        Tuple condition = Tuple.create();
        try {
            ASUtils.fillTupleWithEvent(asDestination.getSpaceDef(), condition, key, session);
            Tuple result = asDestination.getSpace().take(condition);
            return result != null ? ASUtils.convertTuple(asDestination, key, result) : null;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object[] takeEvents(String destinationUri, SimpleEvent[] keys) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        IASDestination asDestination = getASDestination(destinationUri, session);
        try {
            Collection<Tuple> conditions = new HashSet<Tuple>();
            for (SimpleEvent key : keys) {
                Tuple condition = Tuple.create();
                ASUtils.fillTupleWithEvent(asDestination.getSpaceDef(), condition, key, session);
                conditions.add(condition);
            }
            SpaceResultList spaceResults = asDestination.getSpace().takeAll(conditions);
            Collection<Tuple> tuples = spaceResults.getTuples();
            SimpleEvent eventTemplate = ASUtils.findSimpleEventTemplate(keys);
            return ASUtils.convertTuples(asDestination, eventTemplate, tuples);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object putEvent(String destinationUri, SimpleEvent event) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        IASDestination asDestination = getASDestination(destinationUri, session);
        Tuple tuple = Tuple.create();
        try {
            ASUtils.fillTupleWithEvent(asDestination.getSpaceDef(), tuple, event, session);
            Tuple putTuple = asDestination.getSpace().put(tuple);
            return ASUtils.convertTuple(asDestination, event, putTuple);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object[] putEvents(String destinationUri, SimpleEvent[] keys) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        IASDestination asDestination = getASDestination(destinationUri, session);
        try {
            Collection<Tuple> tuples = new HashSet<Tuple>();
            for (SimpleEvent key : keys) {
                Tuple tuple = Tuple.create();
                ASUtils.fillTupleWithEvent(asDestination.getSpaceDef(), tuple, key, session);
                tuples.add(tuple);
            }
            SpaceResultList spaceResults = asDestination.getSpace().putAll(tuples);
            Collection<Tuple> putTuples = spaceResults.getTuples();
            SimpleEvent eventTemplate = ASUtils.findSimpleEventTemplate(keys);
            return ASUtils.convertTuples(asDestination, eventTemplate, putTuples);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object lockEvent(String destinationUri, SimpleEvent event, LockOptions lockOptions) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        IASDestination asDestination = getASDestination(destinationUri, session);
        Tuple tuple = Tuple.create();
        try {
            ASUtils.fillTupleWithEvent(asDestination.getSpaceDef(), tuple, event, session);
            Tuple result = asDestination.getSpace().lock(tuple, lockOptions);
            return result != null ? ASUtils.convertTuple(asDestination, event, result) : null;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object[] lockEvents(String destinationUri, SimpleEvent[] keys, LockOptions lockOptions) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        IASDestination asDestination = getASDestination(destinationUri, session);
        try {
            Collection<Tuple> tuples = new HashSet<Tuple>();
            for (SimpleEvent key : keys) {
                Tuple tuple = Tuple.create();
                ASUtils.fillTupleWithEvent(asDestination.getSpaceDef(), tuple, key, session);
                tuples.add(tuple);
            }
            SpaceResultList spaceResults = asDestination.getSpace().lockAll(tuples, lockOptions);
            Collection<Tuple> lockedTuples = spaceResults.getTuples();
            SimpleEvent eventTemplate = ASUtils.findSimpleEventTemplate(keys);
            return ASUtils.convertTuples(asDestination, eventTemplate, lockedTuples);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void unlockEvent(String destinationUri, SimpleEvent event, UnlockOptions unlockOptions) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        IASDestination asDestination = getASDestination(destinationUri, session);
        Tuple tuple = Tuple.create();
        try {
            ASUtils.fillTupleWithEvent(asDestination.getSpaceDef(), tuple, event, session);
            asDestination.getSpace().unlock(tuple, unlockOptions);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object[] unlockEvents(String destinationUri, SimpleEvent[] keys, UnlockOptions unlockOptions) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        IASDestination asDestination = getASDestination(destinationUri, session);
        try {
            Collection<Tuple> tuples = new HashSet<Tuple>();
            for (SimpleEvent key : keys) {
                Tuple tuple = Tuple.create();
                ASUtils.fillTupleWithEvent(asDestination.getSpaceDef(), tuple, key, session);
                tuples.add(tuple);
            }
            SpaceResultList spaceResults = asDestination.getSpace().unlockAll(tuples, unlockOptions);
            Collection<Tuple> unlockedTuples = spaceResults.getTuples();
            SimpleEvent eventTemplate = ASUtils.findSimpleEventTemplate(keys);
            return ASUtils.convertTuples(asDestination, eventTemplate, unlockedTuples);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object[] takeSnapshot(String destinationUri, SimpleEvent eventTemplate, String browserTypeStr, String distributionScope, int timeout, long prefetch, String filter) {
        return query(destinationUri, eventTemplate, browserTypeStr, BrowserDef.TimeScope.SNAPSHOT.name(), distributionScope, BrowserDef.NO_WAIT, -1L, filter);
    }

    public static Object[] query(String destinationUri, SimpleEvent eventTemplate, String browserTypeStr, String timeScope, String distributionScope, int timeout, long prefetch, String filter) {
        IASDestination asDestination = getASDestination(destinationUri);
        IASChannel asChannel = asDestination.getChannel();
        Metaspace metaspace = asChannel.getMetaspace();
        long queryLimit = Long.parseLong(System.getProperty(SystemProperty.AS_CHANNEL_QUERYLIMIT.getPropertyName(), "-1"));
        BrowserDef browserDef = ASUtils.getBrowserDef(timeScope, distributionScope, timeout, prefetch, queryLimit);
        BrowserType browserType = getBrowserType(browserTypeStr);

        List<Object> resultSet = new ArrayList<Object>();
        try {
            Browser browser = metaspace.browse(asDestination.getSpace().getName(), browserType, browserDef, filter);

            Tuple tuple = null;
            while ((tuple = browser.next()) != null) {
                resultSet.add(ASUtils.convertTuple(asDestination, eventTemplate, tuple));
            }
            browser.stop();
            return resultSet.size() != 0 ? resultSet.toArray(new Object[resultSet.size()]) : null;
        }
        catch (Exception asEx) {
            throw new RuntimeException(asEx);
        }
    }

    public static Object queryIterator(String destinationUri, SimpleEvent eventTemplate, String browserTypeStr, String timeScope, String distributionScope, int timeout, long prefetch, String filter) {
        IASDestination asDestination = getASDestination(destinationUri);
        IASChannel asChannel = asDestination.getChannel();
        Metaspace metaspace = asChannel.getMetaspace();
        BrowserDef browserDef = ASUtils.getBrowserDef(timeScope, distributionScope, timeout, prefetch);
        BrowserType browserType = getBrowserType(browserTypeStr);

        try {
            Browser browser = metaspace.browse(asDestination.getSpace().getName(), browserType, browserDef, filter);
            return new BrowserIterator(browser,asDestination, eventTemplate);
        }
        catch (Exception asEx) {
            throw new RuntimeException(asEx);
        }
    }

    public static Object releaseTransaction(String channelUri) {
        try {
            return getASChannel(channelUri).getMetaspace().releaseTransaction();
        }
        catch (ASException asEx) {
            throw new RuntimeException(asEx);
        }
    }

    public static void takeTransaction(String channelUri, Object transactionId) {
        if (!(transactionId instanceof TransactionId)) {
            throw new IllegalArgumentException("The argument transactionId is not an instance of TransactionId");
        }
        try {
            getASChannel(channelUri).getMetaspace().takeTransaction((TransactionId) transactionId);
        }
        catch (ASException asEx) {
            throw new RuntimeException(asEx);
        }
    }

    private static IASDestination getASDestination(String destinationUri) {
        return getASDestination(destinationUri, RuleSessionManager.getCurrentRuleSession());
    }
    private static IASDestination getASDestination(String destinationUri, RuleSession session) {
        ChannelManager cmgr = session.getRuleServiceProvider().getChannelManager();
        Channel.Destination destination = cmgr.getDestination(destinationUri);
        if (destination == null || !(destination instanceof IASDestination)) {
            throw new RuntimeException("Invalid Destination[" + destinationUri + "]");
        }
        return (IASDestination) destination;
    }

    private static IASChannel getASChannel(String channelUri) {
        ChannelManager cmgr = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getChannelManager();
        Channel channel = cmgr.getChannel(channelUri);
        if (channel == null || !(channel instanceof IASChannel)) {
            throw new RuntimeException("Invalid Channel[" + channelUri + "]");
        }
        return (IASChannel) channel;
    }

    private static BrowserType getBrowserType(String browserTypeStr) {
        BrowserType browserType = null;
        if (browserTypeStr.equalsIgnoreCase("get")) {
            browserType = BrowserDef.BrowserType.GET;
        }
        else if (browserTypeStr.equalsIgnoreCase("take")) {
            browserType = BrowserDef.BrowserType.TAKE;
        }
        else if (browserTypeStr.equalsIgnoreCase("lock")) {
            browserType = BrowserDef.BrowserType.LOCK;
        }
        else {
            throw new RuntimeException("unknown BrowserType " + browserType);
        }
        return browserType;
    }

    private static Object getEventProperty(SimpleEvent event, String propName) {
        if (event == null) {
            throw new IllegalArgumentException("Input event cannot be null");
        }
        Object propValue = null;
        try {
            String[] existingPropNames = event.getPropertyNames();
            for (String existingPropName : existingPropNames) {
                if (propName.equals(existingPropName)) {
                    propValue = event.getProperty(propName);
                }
            }
        }
        catch (NoSuchFieldException ex) {
            // ex.printStackTrace();
            // do nothing
        }
        return propValue;
    }

    public static Object getConceptBlobFromPayload(SimpleEvent event, String blobFieldName, Concept conceptTemplate) {
        ExpandedName expandedName = null;
        if (null != conceptTemplate) {
            expandedName = conceptTemplate.getExpandedName();
        }
        return getBlobFromPayload(event, blobFieldName, expandedName);
    }

    public static Object getSimpleEventBlobFromPayload(SimpleEvent event, String blobFieldName, SimpleEvent eventTemplate) {
        ExpandedName expandedName = null;
        if (null != eventTemplate) {
            expandedName = eventTemplate.getExpandedName();
        } else {
            expandedName = event.getExpandedName();
        }
        return getBlobFromPayload(event, blobFieldName, expandedName);
    }

    private static Object getBlobFromPayload(SimpleEvent event, String blobFieldName, ExpandedName expandedName) {
        Object result = null;
        EventPayload payload = event.getPayload();
        if (null != payload && payload instanceof ObjectPayload) {
            ObjectPayload objPayload = (ObjectPayload) payload;
            Object[][] nameValuePairs = (Object[][]) objPayload.getObject();
            if (null != nameValuePairs) {
                for (Object[] nameValuePair : nameValuePairs) {
                    String name = (String) nameValuePair[0];
                    if (name.equals(blobFieldName)) {
                        byte[] bytes = (byte[]) nameValuePair[1];
                        if (null != bytes && 0 != bytes.length) {
                            /**
                             * [Huabin, 2012/05/31]:
                             *      The following logic is copied from SerializableLiteCodecHook and ObjectCodecHook.
                             */
                            if ('U' == bytes[0]) {
                                result = decodeSerializableLite(bytes, expandedName);
                            }
                            else {
                                result = decodeObject(bytes);
                            }
                        }
                        break;
                    }
                }
            }
        }
        return result;
    }

    private static Object decodeSerializableLite(byte[] bytes, ExpandedName expandedName) {
        Object result = null;
        DataInputStream dis = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            dis = new DataInputStream(bais);

            //read() and readUTF() must be called before calling concept.readExternal(), refer to java api doc
            dis.read();     // skip character 'U'
            dis.readUTF();  // skip class name

            if (null != expandedName) {
                SerializableLite concept = (SerializableLite) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()
                        .getTypeManager().createEntity(expandedName);
                concept.readExternal(dis);
                result = concept;
            }
            else {
                result = bytes;
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        finally {
            try {
                if (dis != null) dis.close();
            }
            catch (Exception ex) {
            }
        }
        return result;
    }

    private static Object decodeObject(byte[] bytes) {
        Object result = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            Object blobObj = ois.readObject();
            result = blobObj;
        }
        catch (Exception ex) {
            // this means that byte[] is not an instance of Object, so we return raw array to the caller
            result = bytes;
        }
        finally {
            try {
                if (ois != null) ois.close();
            }
            catch (Exception ex) {
            }
        }
        return result;
    }

}

class BrowserIterator implements Iterator<Object> {
    private Browser asBrowser;
    private IASDestination asDestination;
    private SimpleEvent eventTemplate;
    private Tuple tempTuple;
    private boolean goNext=true;

    public BrowserIterator(Browser asBrowser, IASDestination asDestination, SimpleEvent eventTemplate){
        this.asBrowser = asBrowser;
        this.asDestination = asDestination;
        this.eventTemplate = eventTemplate;
    }

    @Override
    public boolean hasNext() {
        try {
            if (goNext) {
                tempTuple = asBrowser.next();
                goNext = false;
            }
        } catch (ASException e) {
            e.printStackTrace();
        }
        return !(tempTuple==null);
    }

    @Override
    public Object next() {
        if (hasNext()) {
            goNext = true;
            try {
                return ASUtils.convertTuple(asDestination, eventTemplate, tempTuple);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("BrowserIterator does not support remove()");
    }
}
