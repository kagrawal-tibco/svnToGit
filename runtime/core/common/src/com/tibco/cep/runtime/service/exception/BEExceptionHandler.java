package com.tibco.cep.runtime.service.exception;

import java.io.StringWriter;
import java.util.Arrays;

import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.ExecutionContext;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.service.impl.DefaultExceptionHandler;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;


/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Oct 6, 2006
 * Time: 2:29:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class BEExceptionHandler extends DefaultExceptionHandler {
    protected WorkingMemory m_wm = null;
    protected RuleServiceProvider m_rsp = null;

    /**
     * After using this constructor, this class will behave the same as DefaultExceptionHandler
     * Until setWorkingMemory is called with a non-null argument. 
     * @param rsp
     */
    public BEExceptionHandler(RuleServiceProvider rsp) {
        super(rsp.getLogger(BEExceptionHandler.class));
        m_rsp = rsp;
    }

    /**
     * This class will behave the same as DefaultExceptionHandler
     * Until setWorkingMemory is called with a non-null argument.
     * @param wm ReteWM to assert BEExceptions to
     */
    public void setWorkingMemory(ReteWM wm) {
        m_wm = wm;
    }

    /**
     * Add an AdvisoryEvent to the ReteWM provided to setWorkingMemory 
     *  describing the exp and call super method
     * @param exp exception to add to working memory
     * @param message message to pass onto DefaultExceptionHandler
     */
    public void handleException(Exception exp, String message) {
    	handleRuleException(exp, message, null, null);
    }
    public void handleRuleException(Exception exp, String message, String ruleUri, Object[] ruleScope) {
        super.handleException(exp, message);
        if(m_wm != null) {
            try {
                StringWriter msg = new StringWriter();
                if(message != null) msg.append(message);

                RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
                if(session != null ) {
                    ExecutionContext context =((WorkingMemoryImpl)session.getWorkingMemory()).getCurrentContext();
                    if(context != null) {
                        String[] info = context.info();
                        if(info != null && info.length > 0) {
                            msg.append("Exception in ").append(info[0]);
                            msg.append(" { ");
                            for(int ii = 1; ii < info.length; ii++) {
                                if(ii > 1) msg.append(", ");
                                msg.append(info[ii]);
                            }
                            msg.append(" }\n");
                        }
                    }
                }

                AdvisoryEventImpl.advisoryEventExceptionMessage(exp, msg);
                Object[] ruleScopeCopy = null;
                if(ruleScope != null) {
                	ruleScopeCopy =  Arrays.copyOf(ruleScope, ruleScope.length, Object[].class);
                }
                AdvisoryEvent adv = new AdvisoryEventImpl(m_rsp.getIdGenerator().nextEntityId(AdvisoryEventImpl.class), null
                			, AdvisoryEvent.CATEGORY_EXCEPTION, exp.getClass().getName(), msg.toString(), ruleUri, ruleScopeCopy);
                        
                m_wm.assertObject(adv, true);
            } catch (DuplicateExtIdException ex) {
                //passed null for extId in AdvisoryEvent constructor so this should never happen
                m_logger.log(Level.DEBUG,"DuplicateExtIdException when trying to assert an AdvisoryEvent in BEExceptionHandler", ex);
                assert false;
            }
        }
    }
}
