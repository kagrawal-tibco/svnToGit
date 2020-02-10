package com.tibco.cep.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.core.rete.conflict.AgendaItem;
import com.tibco.cep.kernel.helper.ActionExecutionContext;
import com.tibco.cep.kernel.helper.EventExpiryExecutionContext;
import com.tibco.cep.kernel.helper.FunctionExecutionContext;
import com.tibco.cep.kernel.helper.FunctionMapArgsExecutionContext;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.ChangeListener;
import com.tibco.cep.kernel.model.knowledgebase.ExecutionContext;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * InMemory collector of Entity statistics.
 * 
 * @author moshaikh
 */
public class InmemoryRecorder implements ChangeListener {

	private static ConcurrentHashMap<String, InmemoryRecorder> registeredRecorders = new ConcurrentHashMap<String, InmemoryRecorder>();

	private int options;
	/**
	 * Map of - Entity's NamespaceURI : statistic
	 */
	private ConcurrentHashMap<String, EntityStatistic> entityStats = new ConcurrentHashMap<String, EntityStatistic>();

	private InmemoryRecorder() {
	}

	public static void startRecorder(RuleSessionImpl session, String mode) {
		String ruleSessionName = session.getName();
		InmemoryRecorder recorder = registeredRecorders.putIfAbsent(ruleSessionName, new InmemoryRecorder());
		if (recorder != null) {
			return;// Already registered recorder for RuleSession
		} else {
			recorder = registeredRecorders.get(ruleSessionName);
		}

		recorder.resetOptions(mode);

		((WorkingMemoryImpl) session.getWorkingMemory()).addChangeListener(recorder);
	}

	private void resetOptions(String mode) {
		int optionsVal = 0;

		if (mode != null && mode.length() > 0) {
			if (mode.indexOf("c") != -1) {
				optionsVal |= FileBasedRecorder.RECORD_ASSERTED;
			}

			if (mode.indexOf("u") != -1) {
				optionsVal |= FileBasedRecorder.RECORD_MODIFIED;
			}

			if (mode.indexOf("d") != -1) {
				optionsVal |= FileBasedRecorder.RECORD_RETRACTED;
			}

			if (mode.indexOf("s") != -1) {
				optionsVal |= FileBasedRecorder.RECORD_SCHEDULED_TIMEVENT;
			}

			if (mode.indexOf("r") != -1) {
				optionsVal |= FileBasedRecorder.RECORD_RULE_FIRED;
			}

			if (mode.indexOf("a") != -1) {
				optionsVal |= FileBasedRecorder.RECORD_ACTION_EXECUTED;
			}

			if (mode.indexOf("x") != -1) {
				optionsVal |= FileBasedRecorder.RECORD_EVENT_EXPIRY;
			}

			if (mode.indexOf("f") != -1) {
				optionsVal |= FileBasedRecorder.RECORD_FUNCTION_EXECUTED;
			}

			if (mode.indexOf("e") != -1) {
				optionsVal |= FileBasedRecorder.RECORD_EVENT_EXPIRED;
			}
		} else {
			optionsVal |= FileBasedRecorder.RECORD_ASSERTED;
			optionsVal |= FileBasedRecorder.RECORD_MODIFIED;
			optionsVal |= FileBasedRecorder.RECORD_RETRACTED;
			optionsVal |= FileBasedRecorder.RECORD_SCHEDULED_TIMEVENT;
			optionsVal |= FileBasedRecorder.RECORD_RULE_FIRED;
			optionsVal |= FileBasedRecorder.RECORD_ACTION_EXECUTED;
			optionsVal |= FileBasedRecorder.RECORD_EVENT_EXPIRY;
			optionsVal |= FileBasedRecorder.RECORD_FUNCTION_EXECUTED;
			optionsVal |= FileBasedRecorder.RECORD_EVENT_EXPIRED;
		}

		this.options = optionsVal;
	}

	@Override
	public void asserted(Object obj, ExecutionContext context) {
		if ((options & FileBasedRecorder.RECORD_ASSERTED) == 0)
			return;
		try {
			if (obj instanceof Concept) {
				EntityStatistic entityStatistic = getOrCreateEntityStatistics(((Concept) obj).getExpandedName().getNamespaceURI());
				entityStatistic.incrementAssertedCount();
			} else if (obj instanceof SimpleEvent) {
				EntityStatistic entityStatistic = getOrCreateEntityStatistics(((SimpleEvent) obj).getExpandedName().getNamespaceURI());
				entityStatistic.incrementAssertedCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modified(Object obj, ExecutionContext context) {
		if ((options & FileBasedRecorder.RECORD_MODIFIED) == 0)
			return;
		try {
			if (obj instanceof Concept) {
				EntityStatistic entityStatistic = getOrCreateEntityStatistics(((Concept) obj).getExpandedName().getNamespaceURI());
				entityStatistic.incrementModifiedCount();
			} else if (obj instanceof SimpleEvent) {
				EntityStatistic entityStatistic = getOrCreateEntityStatistics(((SimpleEvent) obj).getExpandedName().getNamespaceURI());
				entityStatistic.incrementModifiedCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void retracted(Object obj, ExecutionContext context) {
		try {
			if ((options & FileBasedRecorder.RECORD_RETRACTED) == 0)
				return;
			if (obj instanceof Concept) {
				EntityStatistic entityStatistic = getOrCreateEntityStatistics(((Concept) obj).getExpandedName().getNamespaceURI());
				entityStatistic.incrementRetractedCount();
			} else if (obj instanceof SimpleEvent) {
				EntityStatistic entityStatistic = getOrCreateEntityStatistics(((SimpleEvent) obj).getExpandedName().getNamespaceURI());
				entityStatistic.incrementRetractedCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ruleFired(AgendaItem context) {
		// TODO
	}

	@Override
	public void scheduledTimeEvent(Event evt, ExecutionContext context) {
		// TODO
	}

	@Override
	public void actionExecuted(ActionExecutionContext context) {
		// TODO
	}

	@Override
	public void eventExpiryExecuted(EventExpiryExecutionContext context) {
		// TODO
	}

	@Override
	public void eventExpired(Event obj) {
		if ((options & FileBasedRecorder.RECORD_EVENT_EXPIRED) == 0)
			return;
		if (obj instanceof SimpleEvent) {
			EntityStatistic entityStatistic = getOrCreateEntityStatistics(((SimpleEvent) obj).getExpandedName().getNamespaceURI());
			entityStatistic.incrementExpiredCount();
		}
	}

	@Override
	public void functionExecuted(FunctionExecutionContext context) {
		// TODO
	}

	@Override
	public void functionExecuted(FunctionMapArgsExecutionContext context) {
		// TODO
	}

	public static boolean stop(RuleSessionImpl s) {
		Collection<ChangeListener> changeListeners = ((WorkingMemoryImpl) s.getWorkingMemory()).getChangeListeners();
		InmemoryRecorder fr = null;
		for (ChangeListener changeListener : changeListeners) {
			if (changeListener instanceof InmemoryRecorder) {
				fr = (InmemoryRecorder) changeListener;
				break;
			}
		}
		if (fr != null) {
			((WorkingMemoryImpl) s.getWorkingMemory()).removeChangeListener(fr);
			return true;
		} else {
			return false;
		}
	}

	private EntityStatistic getOrCreateEntityStatistics(String key) {
		EntityStatistic entityStatistic = entityStats.putIfAbsent(key, new EntityStatistic());
		return entityStatistic != null ? entityStatistic : entityStats.get(key);
	}

	public EntityStatistic getEntityStatistic(String uri) {
		EntityStatistic statistic = entityStats.get(getNamespaceURIByEntityURI(uri));
		return statistic;
	}

	public Map<String, EntityStatistic> getAllEntityStatistic() {
		Map<String, EntityStatistic> map = new HashMap<String, EntityStatistic>();
		for (Entry<String, EntityStatistic> entry : entityStats.entrySet()) {
			map.put(getEntityURIByNamespaceURI(entry.getKey()), entry.getValue());
		}
		return map;
	}

	public static class EntityStatistic {
		private AtomicLong assertedCount;
		private AtomicLong modifiedCount;
		private AtomicLong retractedCount;
		private AtomicLong expiredCount;

		public EntityStatistic() {
			assertedCount = new AtomicLong();
			modifiedCount = new AtomicLong();
			retractedCount = new AtomicLong();
			expiredCount = new AtomicLong();
		}

		public long getAssertedCount() {
			return assertedCount.get();
		}

		public long getModifiedCount() {
			return modifiedCount.get();
		}

		public long getDeletedCount() {
			return retractedCount.get();
		}

		public long getExpiredCount() {
			return expiredCount.get();
		}

		public void incrementAssertedCount() {
			assertedCount.incrementAndGet();
		}

		public void incrementModifiedCount() {
			modifiedCount.incrementAndGet();
		}

		public void incrementRetractedCount() {
			retractedCount.incrementAndGet();
		}

		public void incrementExpiredCount() {
			expiredCount.incrementAndGet();
		}
	}

	/**
	 * Returns entity statistics for the specified rulesession.
	 * 
	 * @param ruleSessionName
	 * @param uri
	 * @return
	 */
	public static EntityStatistic getEntityStatistic(String ruleSessionName, String uri) {
		InmemoryRecorder recorder = registeredRecorders.get(ruleSessionName);
		if (recorder != null) {
			EntityStatistic statistic = recorder.getEntityStatistic(uri);
			return statistic;
		}
		return null;
	}

	/**
	 * Returns all the entities' statistics for the specified rulesession.
	 * 
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public static Map<String, EntityStatistic> getEntityStatistics(String ruleSessionName) throws Exception {
		InmemoryRecorder recorder = registeredRecorders.get(ruleSessionName);
		if (recorder == null) {
			throw new Exception("No Recorder registered for rule session - " + ruleSessionName);
		}
		return recorder.getAllEntityStatistic();
	}

	private static String getNamespaceURIByEntityURI(String entityUri) {
		return "www.tibco.com/be/ontology" + entityUri;
	}

	private static String getEntityURIByNamespaceURI(String namespaceUri) {
		return namespaceUri.replace("www.tibco.com/be/ontology", "");
	}
}
