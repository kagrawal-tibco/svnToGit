package com.tibco.cep.runtime.model.event.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.service.cluster.system.EntityIdMask;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Oct 16, 2006
 * Time: 8:47:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdvisoryEventImpl extends EntityImpl implements AdvisoryEvent {
    protected String m_category;
    protected String m_type;
    protected String m_message;
    protected String m_ruleUri;
	protected Object[] m_ruleScope;

    public final static int ADVISORYEVENTIMPL_TYPEID = 1003;

    public AdvisoryEventImpl(long id, String _extId, String category, String type, String message) {
        super(id, _extId);
        m_category = category;
        m_type = type;
        m_message = message;
    }
    
    public AdvisoryEventImpl(long id, String _extId, String category, String type, String message
    		, String ruleUri, Object[] ruleScope) {
        this(id, _extId, category, type, message);
        m_ruleUri = ruleUri;
        m_ruleScope = ruleScope;
    }

//    public void delete() {
//    }

    public void start(Handle handle) {
    }

    public String getCategory() {
        return m_category;
    }

    public String getType() {
        return m_type;
    }

    public String getMessage() {
        return m_message;
    }

    public String getRuleUri() {
    	return m_ruleUri;
    }
	
    public Object[] getRuleScope() {
    	return m_ruleScope;
    }

    public void setTTL(long ttl) {
        throw new RuntimeException("TTL is not settable for " + this);
    }

    public long getTTL() {
        return 0;
    }

    public boolean hasExpiryAction() {
        return false;
    }

    public void onExpiry() {
        //no-op
    }

    public boolean getRetryOnException() {
        return false;  
    }

    public void toXiNode(XiNode node) throws Exception {
        baseXiNode(node);
        node.setAttributeStringValue(com.tibco.xml.data.primitive.ExpandedName.makeName(com.tibco.cep.designtime.model.event.AdvisoryEvent.ATTRIBUTE_CATEGORY), getCategory());
        node.setAttributeStringValue(com.tibco.xml.data.primitive.ExpandedName.makeName(com.tibco.cep.designtime.model.event.AdvisoryEvent.ATTRIBUTE_TYPE), getType());
        node.setAttributeStringValue(com.tibco.xml.data.primitive.ExpandedName.makeName(com.tibco.cep.designtime.model.event.AdvisoryEvent.ATTRIBUTE_MESSAGE), getMessage());
    }

    public String toString() {
        if (EntityIdMask.isMasked(getId())) {
            return "AdvisoryEvent@site=" + EntityIdMask.getMaskedId(getId()) + "@id=" + EntityIdMask.getEntityId(getId());

        } else {
            return "AdvisoryEvent@id=" + this.getId();
        }
    }

    public void setPropertyValue(String name, Object value) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getPropertyValue(String name) throws NoSuchFieldException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
    
    public static void assertExceptionAdvisory(RuleSession session, Exception exp, StringWriter msg) {
        try {
            AdvisoryEvent adv = new AdvisoryEventImpl(session.getRuleServiceProvider().getIdGenerator().nextEntityId(AdvisoryEventImpl.class)
                , null, AdvisoryEvent.CATEGORY_EXCEPTION, exp.getClass().getName()
                , advisoryEventExceptionMessage(exp, msg).toString());
            session.assertObject(adv, true);
        } catch (DuplicateExtIdException dupex) {
            //no extId specified so this should never happen
            throw new RuntimeException(dupex);
        }
    }
    
    public static StringWriter advisoryEventExceptionMessage(Exception exp, StringWriter msg) {
        if(msg == null) msg = new StringWriter();
        
        if(msg.getBuffer().length() > 0) msg.append('\n');
 
        String expMsg = exp.getMessage();
        if(expMsg != null && expMsg.length() > 0) {
            msg.append(expMsg);
            msg.append('\n');
        }

        PrintWriter pw = new PrintWriter(msg);
        exp.printStackTrace(pw);
        pw.close();
        
        return msg;
    }
    
    public static String getRuleUri(AdvisoryEvent ae) {
    	if(ae instanceof AdvisoryEventImpl) return ((AdvisoryEventImpl)ae).getRuleUri();
    	return null;
    }
    
    public static Object[] getRuleScope(AdvisoryEvent ae) {
    	if(ae instanceof AdvisoryEventImpl) return ((AdvisoryEventImpl)ae).getRuleScope();
    	return null;
    }
}
