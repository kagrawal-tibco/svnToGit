package com.tibco.cep.runtime.service.debug;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.tibco.cep.kernel.service.Filter;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.tester.core.ResultObjectSerializer;
import com.tibco.cep.runtime.service.tester.model.ResultObject;
import com.tibco.cep.runtime.service.tester.model.ReteObject;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 6, 2010
 * Time: 10:18:48 AM
 * <!--
 * Add Description of the class here
 * -->
 */
public class WMContentsResponseTaskImpl extends AbstractDebugResponseTask {

    private String ruleSessionName;

    private int fetchCount;

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(WMContentsResponseTaskImpl.class);

    public WMContentsResponseTaskImpl(String ruleSessionName, int fetchCount) {
        this.ruleSessionName = ruleSessionName;
        this.fetchCount = fetchCount;
    }

    @SuppressWarnings("unchecked")
    public void run() {
        DebuggerService debuggerService = DebuggerService.getInstance();
        //This will never be null so safe not to put a null check
        RuleServiceProvider ruleServiceProvider = debuggerService.getRuleServiceProvider();

        //Look for Rule session with this name
        RuleSession ruleSession = ruleServiceProvider.getRuleRuntime().getRuleSession(ruleSessionName);

        if (ruleSession == null) {
            response = null;
        } else {
            Filter countFilter = new CountFilter(fetchCount);
            try {
                List<Object> filteredObjects = (fetchCount < 0) ? ruleSession.getObjects() :
                                               ruleSession.getObjects(countFilter);
                Set<ReteObject> filteredReteObjects = new LinkedHashSet<ReteObject>(filteredObjects.size());
                for (Object filteredObject : filteredObjects) {
                    LOGGER.log(Level.DEBUG, "Filtered Object >>> %s", filteredObject);
                    ReteObject reteObject = new ReteObject(filteredObject);
                    filteredReteObjects.add(reteObject);
                }
                ResultObject resultObject = new ResultObject(filteredReteObjects);
                ResultObjectSerializer serializer = new ResultObjectSerializer();
                response = serializer.serialize(resultObject);
                LOGGER.log(Level.DEBUG, "Serialized Response >>> %s", response);
                
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e, e.getMessage());
            }
        }
    }

    class CountFilter implements Filter {

        private int expectedFetchCount;

        private int objectCounter = 1;

        CountFilter(int expectedFetchCount) {
            this.expectedFetchCount = expectedFetchCount;
        }

        public boolean evaluate(Object object) {
            while (objectCounter <= expectedFetchCount) {
                objectCounter++;
                return true;
            }
            return false;
        }
    }
}
