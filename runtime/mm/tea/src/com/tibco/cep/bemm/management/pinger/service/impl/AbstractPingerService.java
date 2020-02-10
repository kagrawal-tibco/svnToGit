package com.tibco.cep.bemm.management.pinger.service.impl;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.bemm.common.ConnectionContext;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.pinger.service.PingerService;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.util.annotation.Idempotent;

/**
 * @author vdhumal
 *
 * @param <C> ConnectionContext
 */
public abstract class AbstractPingerService<C extends ConnectionContext<P>, P> implements PingerService {
	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(AbstractPingerService.class);
	protected static Timer timer = new Timer("PingerService", true);

	private Map<Monitorable, C> pingerRegistry = null;
	private PingerTimerTask pingerTimerTask = null;
    private long frequency;
    private PingerResponseCallbackHandler pingerResponseCallbackHandler = null;
    private MessageService messageService;
    
	public AbstractPingerService() {
		pingerRegistry = new ConcurrentHashMap<>();
	}

	@Override
	public void init(Properties configuration) throws Exception {
		String frequencyStr = ConfigProperty.BE_TEA_AGENT_INSTANCE_PINGER_FREQUENCY.getValue(configuration).toString();
		messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		try {
			this.frequency = Long.parseLong(frequencyStr);
		} catch (NumberFormatException nfex) {
			this.frequency = 30000;
			if (LOGGER.isEnabledFor(Level.ERROR)) {
				LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.INVALID_PROPERTY_VALUE, frequencyStr, ConfigProperty.BE_TEA_AGENT_INSTANCE_PINGER_FREQUENCY.getPropertyName()));
			}			
		}
	}

	@Override
	@Idempotent
	public synchronized void start() throws Exception {
		if (pingerTimerTask == null)
			pingerTimerTask = new PingerTimerTask();		
        if (pingerTimerTask.running == false) {
        	if (LOGGER.isEnabledFor(Level.DEBUG)) {
        		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.SCHEDULED_TIMER_TASK));
        	}	
        	timer.schedule(pingerTimerTask, 100, frequency);
        	pingerTimerTask.running = true;
        }
	}

	@Override
	@Idempotent
	public synchronized void stop() throws Exception {
        if (pingerTimerTask != null) {
        	pingerTimerTask.running = false;
        	pingerTimerTask.cancel();
        	pingerTimerTask = null;
        }
	}

	@Override
	public void suspend() {
		//Nop
	}

	@Override
	public void resume() {
		//Nop
	}

	@Override
	public boolean isStarted() {
		return pingerTimerTask.running;
	}

	@Override
	public synchronized void register(Monitorable monitorableEntity) throws Exception {		
		if (!pingerRegistry.containsKey(monitorableEntity)) {
			C connContext = initConnection(monitorableEntity);
			if (connContext != null) {
				pingerRegistry.put(monitorableEntity, connContext);
				//Start the Pinger service
				start();
			} else {
				if (LOGGER.isEnabledFor(Level.ERROR)) {
					LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.INITCONNECTION_MONITORABLE_ENTITY_FAILED, monitorableEntity.getKey()));
				}
			}
		} else {
			if (LOGGER.isEnabledFor(Level.WARN)) {
				LOGGER.log(Level.WARN, messageService.getMessage(MessageKey.MONITORABLE_ENTITY_ALREADY_REGISTERED, monitorableEntity.getKey()));
			}	
		}
	}

	@Override
	public synchronized void unregister(Monitorable monitorableEntity) throws Exception {
		C connContext = pingerRegistry.get(monitorableEntity);
		if (connContext != null) {
			pingerRegistry.remove(monitorableEntity);
			if (pingerRegistry.isEmpty()) {
				stop();
			}
		} else {
			if (LOGGER.isEnabledFor(Level.ERROR)) {
				LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.UNREGISTER_MONITORABLE_ENTITY_FAILED, monitorableEntity.getKey()));
			}	
		}		
	}

	@Override
	public void setResponseCallbackHandler(PingerResponseCallbackHandler pingerResponseCallbackHandler) {
		this.pingerResponseCallbackHandler = pingerResponseCallbackHandler;
	}

	protected final C getConnectionContext(Monitorable monitorableEntity) {
		return this.pingerRegistry.get(monitorableEntity);
	}

	/**
	 * @param monitorableEntity
	 * @return
	 * @throws Exception
	 */
	abstract protected C initConnection(Monitorable monitorableEntity) throws Exception;
	
	/**
	 * @param monitorableEntity
	 * @return true if reachable, false otherwise
	 */
	abstract protected boolean ping(Monitorable monitorableEntity);
	
	/**
	 * Close Connection
	 */
	abstract protected void closeConnection(Monitorable monitorableEntity);
	
    /**
     * TimerTask for the Pinger
     */
    class PingerTimerTask extends TimerTask {

        private boolean running;

        PingerTimerTask() {
            running = false;
        }

        @Override
        public void run() {
			Set<Monitorable> monitorableEntities = pingerRegistry.keySet();			
			for (Monitorable monitorableEntity : monitorableEntities) {
				boolean isReachable = ping(monitorableEntity);
				if (isReachable == false) {
					if (pingerResponseCallbackHandler != null) {
						pingerResponseCallbackHandler.handle(monitorableEntity, isReachable);
					}
				}
			}
        }

        @Override
    	public boolean cancel() {
        	boolean flag = super.cancel();
        	running = false;
        	return flag;
    	}    	
    }
	
}
