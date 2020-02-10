/**
 * 
 */
package com.tibco.cep.decisionproject.acl.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decisionproject.acl.AccessControlCellData;
import com.tibco.cep.decisionproject.acl.AccessController;
import com.tibco.cep.decisionproject.acl.AccessControllerImpl2;
import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.validator.ValidationException;
import com.tibco.cep.security.tokens.AuthToken;
import com.tibco.cep.security.tokens.Authz;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.util.AuthTokenUtils;

/**
 * @author aathalye
 *
 */
public class AccessControlEventListener<T extends AccessControlEvent, 
                                        I extends Implementation> {
	
	private AccessControlEventQueue<T> eventQueue;
	
	private I table;//The implementation instance
	
		
	public AccessControlEventListener(final AccessControlEventQueue<T> eventQueue,
			                          final I table) {
		this.eventQueue = eventQueue;
		this.table = table;
	}
	
	public boolean enqueueEvent(T event) {
		return eventQueue.addEvent(event);
	}
	
	public boolean dequeueEvent(T event) {
		return eventQueue.remove(event);
	}
	
	public void dispose() {
		eventQueue.clear();
		eventQueue = null;
	}
	
	public T fetchEvent(String uid) {
		return eventQueue.searchEvent(uid);
	}
	
	
	public void handleEvents(final Collection<ValidationError> vErrors) throws ValidationException {
		AccessController controller = null;
					
		AuthToken authToken = AuthTokenUtils.getToken();
		if (authToken == null){
//			TRACE.logDebug(CLASS, "Auth Token is null");
			throw new ValidationException("Auth Token Null::");
		}
		Authz authz = authToken.getAuthz();
		if (authz == null){
//			TRACE.logDebug(CLASS, "No User Role present");
			//throw new ValidationException("No User Role present::");
			return;
		}
		List<Role> userRoleList = authz.getRoles();	
		controller = new AccessControllerImpl2(userRoleList);
		final Iterator<T> iterator = eventQueue.iterator();
		while (iterator.hasNext()) {
			T event = iterator.next();
//			TRACE.logDebug(CLASS, 
//		       "Candidate identified for access control check");
			Collection<ValidationError> localErrors = 
				new ArrayList<ValidationError>();
			//Get the source
			Object source = event.getSource();
			if (source instanceof AccessControlCellData) {
				AccessControlCellData acd = (AccessControlCellData)source;
				TableRuleVariable trv = acd.getTrv();
//				TRACE.logDebug(CLASS, 
//						       "Table rule variable used for the ACL check {0}",
//						       trv);
				if (trv.isModified()) {
					controller.checkACL(table, acd, localErrors);
					if (localErrors.isEmpty()) {
//						TRACE.logDebug(CLASS, 
//							       "Access requested for TableRule Variable {0} was allowed",
//							       trv);
						//Remove this event from the queue
						eventQueue.remove(event);
					}
					vErrors.addAll(localErrors);
				}
			}
		}
	}
}
