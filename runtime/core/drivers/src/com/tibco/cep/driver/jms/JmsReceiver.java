package com.tibco.cep.driver.jms;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.util.ResourceManager;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: nprade Date: 9/17/12 Time: 3:58 PM
 */
public class JmsReceiver implements Callable<Object> {

	private interface State {

		int NONE = 0;
		int START_PASSIVE = 1;
		int START_ACTIVE = 2;
		int START_PSEUDO_ACTIVE = 3;
		int START_SUSPENDED = 4;
		int STOP = 5;
		int DESTROY = 6;
	}

	private static final int WAIT_FOR_STATE = 200;

	private final boolean ack;
	private final JMSDestination destination;
	private final List<String> ignoredErrorCodes;
	private final Logger logger;
	private final boolean oneJmsSessionPerMessage;
	private final JMSInterceptor optionalInterceptor;
	private final RuleSessionImpl ruleSession;
	private final AtomicInteger state = new AtomicInteger(State.NONE);
	private final AtomicInteger stateRequested = new AtomicInteger(State.NONE);
	private final long waitTimeForReceive;

	private boolean consumerCreated = false; // for CR:1-96M3M9
	private String jmsFilter;
	private JmsSessionContext sessionContext;

	public JmsReceiver(JMSDestination destination, RuleSession ruleSession, JMSInterceptor interceptor, boolean ack,
			boolean oneJmsSessionPerMessage) throws Exception {

		final Properties destinationProperties = destination.getConfig().getProperties();

		this.ack = ack;
		this.destination = destination;
		this.ignoredErrorCodes = Arrays.asList(destinationProperties
				.getProperty("com.tibco.cep.driver.jms.errorcodes.ignored", "JMSCC0032").split(",|\\s+"));
		this.logger = this.destination.getLogger();
		this.oneJmsSessionPerMessage = oneJmsSessionPerMessage;
		this.optionalInterceptor = interceptor;
		this.ruleSession = (RuleSessionImpl) ruleSession;
		this.waitTimeForReceive = Long
				.valueOf(destinationProperties.getProperty("com.tibco.cep.driver.jms.receiver.wait", "500"));

		String selector = destination.getConfig().getJmsSelector();
		final Event eventFilter = destination.getEventFilter(ruleSession);
		if (null != eventFilter) {
			final String dynamicFilter = Channel.EVENT_NAME_PROPERTY + "= '" + eventFilter.getName() + "' AND "
					+ Channel.EVENT_NAMESPACE_PROPERTY + "= '" + RDFTnsFlavor.BE_NAMESPACE + eventFilter.getNamespace()
					+ eventFilter.getName() + "'";
			selector = ((null == selector) || selector.trim().isEmpty()) ? dynamicFilter
					: (selector.trim() + " AND " + dynamicFilter);
		}
		this.jmsFilter = selector;
	}

	@Override
	public Object call() throws Exception {

		if (!this.oneJmsSessionPerMessage) {
			this.createSessionContext();
		}

		try {
			for (;;) {
				try {
					switch (this.stateRequested.getAndSet(State.NONE)) {

					case State.START_ACTIVE:
						this.doStartActive();
						break;

					case State.START_PASSIVE:
						this.doStartPassive();
						break;

					case State.START_SUSPENDED:
						this.doStartSuspended();
						break;

					case State.STOP:
						this.doStop();
						break;

					case State.DESTROY:
						this.doDestroy();
						return null;

					default:
						if (State.START_ACTIVE == this.state.intValue()) {
							this.doReceive();
						} else {
							Thread.sleep(WAIT_FOR_STATE);
						}
					}
				} catch (Throwable t) {
					this.logger.log(Level.ERROR, t, "Exception in JMS message receiver, in rule session '%s'.",
							this.ruleSession.getName());
				}
			}
		} finally {
			if (State.DESTROY != this.stateRequested.get()) {
				this.doDestroy();
			}
		}
	}

	private void createConsumer() throws JMSException {

		this.sessionContext.createConsumer(this.jmsFilter, this.ruleSession.getName());
		this.consumerCreated = true;
	}

	private void createSessionContext() throws JMSException {

		this.sessionContext = this.destination.getJmsSessionContextProvider().createJmsSessionContext();
	}

	public void destroy() {

		this.stateRequested.set(State.DESTROY);
	}

	private void doDestroy() {

		try {
			switch (this.state.get()) {
			case State.START_ACTIVE:
			case State.START_PSEUDO_ACTIVE:
			case State.START_PASSIVE:
			case State.START_SUSPENDED:
				this.doStop();
			}

			if ((!this.oneJmsSessionPerMessage) && (null != this.sessionContext)) {
				this.sessionContext.destroy();
			}
		} finally {
			this.state.set(State.DESTROY);
		}
	}

	private void doReceive() throws InterruptedException {

		if (State.DESTROY == this.state.get()) {
			return;
		}

		if (null == this.sessionContext) {
			try {
				this.createSessionContext();
			} catch (JMSException e) {
				if (!this.ignoredErrorCodes.contains(e.getErrorCode())) {
					this.logger.log(Level.ERROR, e,
							"Exception while creating sessionContext => stopping the receiver, in: %s",
							this.ruleSession.getName());
					this.doStop();
				}
				return;
			}
		}

		if (!this.consumerCreated) {
			try {
				this.createConsumer();
			} catch (JMSException e) {
				if (!this.ignoredErrorCodes.contains(e.getErrorCode())) {
					this.logger.log(Level.ERROR, e,
							"Exception while creating JMS consumer => stopping the receiver, in: %s",
							this.ruleSession.getName());
					this.doStop();
				}
				return;
			}
		}

		Message msg = null;
		try {
			if (this.consumerCreated) {
				msg = this.sessionContext.getJmsMessageConsumer().receive(this.waitTimeForReceive);
			}
		} catch (JMSException e) {
			if (!this.ignoredErrorCodes.contains(e.getErrorCode())) {
				this.logger.log(Level.ERROR, e,
						"Exception while receiving a JMS message => stopping the receiver, jmsSessionPerMessage:%s, ack:%s, ErrorCode:%s, in: %s",
						this.oneJmsSessionPerMessage, this.ack, e.getErrorCode(), this.ruleSession.getName());
				this.doStop();
			} else {
				// Cannot have this as ERROR since the user wants this error to be ignored.
				this.logger.log(Level.WARN,
						"Exception (with Ignored ErrorCode) while receiving a JMS message, jmsSessionPerMessage:%s, ack:%s, ErrorCode:%s",
						this.oneJmsSessionPerMessage, this.ack, e.getErrorCode());
			}
			try {
				if (this.oneJmsSessionPerMessage) {
					this.sessionContext.destroy();
					this.consumerCreated = false;
					this.sessionContext = null;
				} else if (this.ack) {
					try {
						this.sessionContext.getJmsSession().recover(); // TODO?
					} catch (JMSException innerException) {
						if (!this.ignoredErrorCodes.contains(innerException.getErrorCode())) {
							this.logger.log(Level.ERROR, innerException, "Failed to recover JMS session in: %s",
									this.ruleSession.getName());
						}
					}
				}
			} finally {
//Bala: Keeping consistent with other instances, where doStop is called only if the exception's error code is not part of the ignoredErrorCodes
//                this.doStop();
			}
		}

		if (null != msg) {
			try {
				this.onMessage(msg);
			} catch (Throwable e) {
				this.logger.log(Level.ERROR, e, "Failed to process incoming JMS message: %s", msg);
				if (this.oneJmsSessionPerMessage) {
					this.sessionContext.destroy();
				}
			}
			if (this.oneJmsSessionPerMessage) {
				this.consumerCreated = false;
				this.sessionContext = null;
			}
		}
	}

	private void doStartActive() throws Exception {
		if (State.DESTROY == this.state.get()) {
			return;
		}

		this.logger.log(Level.DEBUG, "Starting JMS receiver in active mode for %s", this.ruleSession);

		if (null != this.sessionContext) {
			this.sessionContext.sendPendingMessages();
		}

		if (!this.consumerCreated) {

			// test if interceptor disallows to activate
			if (this.optionalInterceptor != null && !this.optionalInterceptor.startInActiveMode(this.ruleSession,
					this.destination.getChannel(), this.destination)) {
				this.state.set(State.START_PSEUDO_ACTIVE);
			} else {
				// interceptor allows to proceed

				if (!this.oneJmsSessionPerMessage) {
					this.createConsumer();
				}
				this.state.set(State.START_ACTIVE);

			}
		} else {
			this.state.set(State.START_ACTIVE);
		}
		
		// BE-27280: when jmsreceiver state is set to active, if destination is in
		// suspended mode, set suspended to false
		if (this.state.get() == State.START_ACTIVE) {
			this.destination.setSuspended(false);
		}

		this.logger.log(Level.INFO,
				ResourceManager.getInstance().formatMessage(
						"channel.destination.listener.started." + ChannelManager.ACTIVE_MODE, this.destination.getURI(),
						this.ruleSession.getName()));
	}

	private void doStartPassive() throws Exception {
		if (State.DESTROY == this.state.get()) {
			return;
		}

		this.logger.log(Level.DEBUG, "Starting JMS receiver in passive mode for %s", this.ruleSession);

		if (null != this.sessionContext) {
			this.sessionContext.sendPendingMessages();
		}

		if (null != this.optionalInterceptor) {
			this.optionalInterceptor.startInPassiveMode(this.ruleSession, this.destination.getChannel(),
					this.destination);
		}

		this.state.set(State.START_PASSIVE);

		this.logger.log(Level.INFO,
				ResourceManager.getInstance().formatMessage(
						"channel.destination.listener.started." + ChannelManager.PASSIVE_MODE,
						this.destination.getURI(), this.ruleSession.getName()));
	}

	private void doStartSuspended() throws Exception {
		if (State.DESTROY == this.state.get()) {
			return;
		}

		this.logger.log(Level.DEBUG, "Starting JMS receiver in suspended mode for %s", this.ruleSession);

		if ((null == this.optionalInterceptor) || this.optionalInterceptor.startInSuspendMode(this.ruleSession,
				this.destination.getChannel(), this.destination)) {
			this.destination.setSuspended(true); // TODO ?
		}

		this.state.set(State.START_SUSPENDED);

		this.logger.log(Level.INFO,
				ResourceManager.getInstance().formatMessage(
						"channel.destination.listener.started." + ChannelManager.SUSPEND_MODE,
						this.destination.getURI(), this.ruleSession.getName()));
	}

	private void doStop() {

		if (State.DESTROY == this.state.get()) {
			return;
		}

		if (this.consumerCreated) {
			try {
				this.logger.log(Level.DEBUG, "Destroying the message consumer for destination %s",
						this.destination.getURI());

				this.consumerCreated = false;
				this.sessionContext.getJmsMessageConsumer().close();
			} catch (Exception e) {
				this.logger.log(Level.ERROR, e, "Error occurred while closing JMS Message Consumer.");
			}
		}

		if (null != this.optionalInterceptor) {
			try {
				this.optionalInterceptor.stop();
			} catch (Exception e) {
				this.logger.log(Level.ERROR, e, "Error occurred while stopping interceptor.");
			}
		}

		this.state.set(State.STOP);

		this.logger.log(Level.INFO, ResourceManager.getInstance().formatMessage("channel.destination.listener.stopped",
				this.destination.getURI(), this.ruleSession.getName()));

	}

	public RuleSession getRuleSession() {
		return this.ruleSession;
	}

	private void onMessage(Message message) throws Exception {

		this.logger.log(Level.DEBUG, "%s - Received JMS msg %s", this.ruleSession.getName(), message);

		JmsMessageContext ctx = new JmsMessageContext(this.destination, message, this.sessionContext, this.ack,
				this.oneJmsSessionPerMessage, this.destination.sessionRecoverOnAckFailure());

		if (null != this.optionalInterceptor) {
			ctx = this.optionalInterceptor.onMessage(ctx);
		}

		if (ctx != null) {
			this.ruleSession.getTaskController().processEvent(this.destination, null, ctx);
			this.destination.getStats().addEventIn();
		}
	}

	public void start(int mode) throws Exception {

		switch (mode) {
		case ChannelManager.ACTIVE_MODE: {
			this.stateRequested.set(State.START_ACTIVE);
			break;
		}
		case ChannelManager.PASSIVE_MODE: {
			this.stateRequested.set(State.START_PASSIVE);
			break;
		}
		case ChannelManager.SUSPEND_MODE: {
			this.stateRequested.set(State.START_SUSPENDED);
			break;
		}
		}
	}

	public void stop() {

		this.stateRequested.set(State.STOP);
	}

}
