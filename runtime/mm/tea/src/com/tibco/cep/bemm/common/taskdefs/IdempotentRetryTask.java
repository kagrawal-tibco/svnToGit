package com.tibco.cep.bemm.common.taskdefs;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;

/**
 * The class is used to retry of given task
 * 
 * @author dijadhav
 *
 */
public class IdempotentRetryTask implements Task {
	private Logger LOGGER = LogManagerFactory.getLogManager().getLogger(IdempotentRetryTask.class);
	protected Task wrappedTask;
	protected int retryCount;
	protected long waitTime;
	private MessageService messageService;

	public IdempotentRetryTask(Task wrappedTask, int retryCount, long waitTime) {
		super();
		this.wrappedTask = wrappedTask;
		this.retryCount = retryCount;
		this.waitTime = waitTime;
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.common.taskdefs.Task#perform()
	 */
	@Override
	public Object perform() throws Throwable {
		LOGGER.log(Level.TRACE, messageService.getMessage(MessageKey.RETRY_COUNT_OPERATION, getTaskName(), retryCount));
		for (int loop = 0; loop <= retryCount; loop++) {
			try {
				Object obj=wrappedTask.perform();
				if(obj instanceof Boolean){
					boolean istaskCompleted = (boolean)obj;
					if(istaskCompleted){
						return obj;
					}
				}else{
					return obj;
				}
				
			} catch (Exception e) {

				LOGGER.log(Level.ERROR, e, e.getMessage());

				if (loop >= retryCount) {
					// Retries exceeded
					throw new Exception(String.format(messageService.getMessage(MessageKey.RETRIES_EXCEEDED_TASK, wrappedTask.getTaskName())), e);
				}

				LOGGER.log(Level.DEBUG,
						messageService.getMessage(MessageKey.ATTEMPTING_RETRY_OPERATION,
						getTaskName(), loop, waitTime));

				Thread.sleep(waitTime);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.common.taskdefs.Task#getTaskName()
	 */
	@Override
	public String getTaskName() {
		return this.wrappedTask.getTaskName();
	}

	@Override
	public void stop() {
		//place holder - to revisit
		
	}
}
