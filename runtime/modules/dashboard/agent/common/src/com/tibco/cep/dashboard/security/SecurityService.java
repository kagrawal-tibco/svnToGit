package com.tibco.cep.dashboard.security;

import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;

import com.tibco.cep.dashboard.common.utils.DateTimeUtils;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.management.Service;
import com.tibco.cep.dashboard.timer.ExceptionResistentTimerTask;
import com.tibco.cep.dashboard.timer.TimerProvider;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.jmx.AnnotationAwareStandardMBean;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.security.SecurityHelper;
import com.tibco.cep.security.tokens.AuthToken;

/**
 * @author anpatil
 *
 */
public class SecurityService extends Service {

	static final String NAME = "security";

	static final String DESCRIPTIVE_NAME = "Security Service";

	private int loginAttempts;

	private int successfulLoginAttempts;

	private int logoutAttempts;

	private int successfulLogoutAttempts;

	private Map<String, SecurityToken> tokens;

	 private Map<String, SecurityToken> sysTokens;

	private List<SecurityTokenListener> listeners;

	private TokenTimeOutChecker tokenTimeOutChecker;

	public SecurityService() throws ManagementException {
		super(NAME, DESCRIPTIVE_NAME);
		tokens = new ConcurrentHashMap<String, SecurityToken>();
		sysTokens = new ConcurrentHashMap<String, SecurityToken>();
		listeners = new LinkedList<SecurityTokenListener>();
	}

	@Override
	protected void doInit() throws ManagementException {
		String authConfig = (String) SecurityProperties.JAAS_AUTH_KEY.getValue(properties);
		if (StringUtil.isEmptyOrBlank(authConfig) == true) {
			throw new ManagementException(messageGenerator.getMessage("invalid.property.value", new MessageGeneratorArgs(null, SecurityProperties.JAAS_AUTH_KEY.getName(), authConfig)));
		}
		System.setProperty(SecurityProperties.JAAS_AUTH_KEY.getName(), authConfig);
	}

	@Override
	protected void doStart() throws ManagementException {
		tokens.clear();
		listeners.clear();
		doResume();
	}

	@Override
	protected void doPause() throws ManagementException {
		if (tokenTimeOutChecker != null) {
			tokenTimeOutChecker.cancel();
		}
	}

	@Override
	protected void doResume() throws ManagementException {
		final long timeOutValue = (Long) SecurityProperties.TOKEN_TIME_OUT_KEY.getValue(properties);
		if (timeOutValue != -1) {
			if (timeOutValue < DateTimeUtils.MINUTE) {
				throw new ManagementException(messageGenerator.getMessage("invalid.timeout.value", new MessageGeneratorArgs(null, SecurityProperties.TOKEN_TIME_OUT_KEY.getName(), timeOutValue)));
			}
			// long remainder = timeOutValue % ONE_MIN;
			long minRepeatValue = DateTimeUtils.DAY;
			if (timeOutValue < DateTimeUtils.DAY) {
				int scale = DateTimeUtils.computeScaleUnit(timeOutValue);
				minRepeatValue = DateTimeUtils.getUnitValue(scale);
			}
			tokenTimeOutChecker = new TokenTimeOutChecker("Token TimeOut Checker", logger, exceptionHandler, true, timeOutValue);
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Scheduling '"+tokenTimeOutChecker+"' every " + DateTimeUtils.getTimeAsStr(minRepeatValue) + " for " + DateTimeUtils.getTimeAsStr(timeOutValue) + " timeout...");
			}
			TimerProvider.getInstance().scheduleWithFixedDelay(tokenTimeOutChecker, minRepeatValue, minRepeatValue, TimeUnit.MILLISECONDS);
		}
	}

	@Override
	protected boolean doStop() {
		if (tokenTimeOutChecker != null){
			tokenTimeOutChecker.cancel();
		}
		for (SecurityTokenListener listener : listeners) {
			for (SecurityToken token : tokens.values()) {
				listener.tokenDeleted(token);
			}
		}
		return true;
	}

	SecurityToken login(String username, String password) throws AccountNotFoundException, LoginException, GeneralSecurityException {
		checkAvailability();
		synchronized (this) {
			RuleSession existingSession = RuleSessionManager.getCurrentRuleSession();
			try {
				RuleSessionManager.currentRuleSessions.set(serviceContext.getRuleSession());
				LocalSecurityTokenImpl token = null;
				if (username == null && password == null) {
					token = new LocalSecurityTokenImpl("", Collections.emptyList(), true);
					this.sysTokens.put(token.toString(), token);
				}
				else {
					loginAttempts++;
					AuthToken authToken = SecurityHelper.authenticateAsToken(username, password);
					String userName = authToken.getAuthen().getUser().getUsername();
					List<?> roles = authToken.getAuthz().getRoles();
					token = new LocalSecurityTokenImpl(userName, roles, false);
					this.tokens.put(token.toString(), token);

				}
				for (SecurityTokenListener listener : listeners) {
					listener.tokenCreated(token);
				}
				if (token.isSystem() == false) {
					successfulLoginAttempts++;
				}
				return token;
			} finally {
				RuleSessionManager.currentRuleSessions.set(existingSession);
			}
		}
	}

	void logout(SecurityToken token) {
		checkAvailability();
		boolean tokenRemoved = false;
		if (token.isSystem() == true) {
			tokenRemoved = sysTokens.remove(token.toString()) != null;
		} else {
			logoutAttempts++;
			tokenRemoved = tokens.remove(token.toString()) != null;

		}
		if (tokenRemoved == true) {
			for (SecurityTokenListener listener : listeners) {
				listener.tokenDeleted(token);
			}
		}
		if (token.isSystem() == false) {
			successfulLogoutAttempts++;
		}
	}

	void addSecurityTokenListener(SecurityTokenListener listener) {
		checkAvailability();
		if (listeners.contains(listener) == false) {
			listeners.add(listener);
		}
	}

	void removeSecurityTokenListener(SecurityTokenListener listener) {
		// checkAvailability();
		listeners.remove(listener);
	}

	SecurityToken convert(String token) throws InvalidTokenException {
		checkAvailability();
		SecurityToken tokenObj = tokens.get(token);
		if (tokenObj == null) {
			tokenObj = sysTokens.get(token);
			if (tokenObj == null) {
				throw new InvalidTokenException(token);
			}
		}
		return tokenObj;
	}

	void verify(SecurityToken securityToken) throws InvalidTokenException {
		checkAvailability();
		String token = securityToken.toString();
		if (this.tokens.containsKey(token) == false) {
			if (this.sysTokens.containsKey(securityToken) == false) {
				throw new InvalidTokenException(token);
			}
		}
	}

	Collection<SecurityToken> getTokens() {
		return Collections.unmodifiableCollection(tokens.values());
	}

	int getLoginAttempts() {
		return loginAttempts;
	}

	int getSuccessfulLoginAttempts() {
		return successfulLoginAttempts;
	}

	int getLogoutAttempts() {
		return logoutAttempts;
	}

	int getSuccessfulLogoutAttempts() {
		return successfulLogoutAttempts;
	}

	void resetCounters() {
		loginAttempts = 0;
		successfulLoginAttempts = 0;
		logoutAttempts = 0;
		successfulLogoutAttempts = 0;
	}

	@Override
	protected void registerMBeans(MBeanServer server, ObjectName baseName) {
		String registrationName = baseName.toString() + ",service=security";
		try {
			ObjectName name = new ObjectName(registrationName);
			AnnotationAwareStandardMBean standardMBean = new AnnotationAwareStandardMBean(new SecurityMXBeanImpl(), SecurityMXBean.class, true);
			server.registerMBean(standardMBean, name);
		} catch (InstanceAlreadyExistsException e) {
			String message = messageGenerator.getMessage("service.mbean.instancealreadyexisting.failure", new MessageGeneratorArgs(e,SecurityMXBean.class.getName(),registrationName));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (MBeanRegistrationException e) {
			String message = messageGenerator.getMessage("service.mbean.registration.failure", new MessageGeneratorArgs(e,SecurityMXBean.class.getName(),registrationName));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (NotCompliantMBeanException e) {
			String message = messageGenerator.getMessage("service.mbean.noncompliantbean.failure", new MessageGeneratorArgs(e,SecurityMXBean.class));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (MalformedObjectNameException e) {
			throw new RuntimeException(e);
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		}
	}

	private final class TokenTimeOutChecker extends ExceptionResistentTimerTask {

		private final long timeOutValue;

		private TokenTimeOutChecker(String name, Logger logger, ExceptionHandler exceptionHandler, boolean cancelOnException, long timeOutValue) {
			super(name, logger, exceptionHandler, cancelOnException);
			this.timeOutValue = timeOutValue;
		}

		@Override
		public void doRun() {
			List<String> expiredTokenIDs = new LinkedList<String>();
			for (String tokenID : tokens.keySet()) {
				LocalSecurityTokenImpl token = (LocalSecurityTokenImpl) tokens.get(tokenID);
				if (token.isSystem() == false && System.currentTimeMillis() - token.getLastTouchedTime() >= timeOutValue) {
					expiredTokenIDs.add(tokenID);
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, token + " has been marked for expiration...");
					}
				}
			}
			for (String tokenID : expiredTokenIDs) {
				SecurityToken sToken = tokens.get(tokenID);
				for (SecurityTokenListener listener : listeners) {
					listener.tokenExpired(sToken);
				}
				tokens.remove(tokenID);
			}
		}
	}
}