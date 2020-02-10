package com.tibco.cep.dashboard.psvr.biz;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.naming.NamingException;

import com.tibco.cep.dashboard.common.utils.DateTimeUtils;
import com.tibco.cep.dashboard.common.utils.SUID;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ManagementUtils;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.management.ServiceDependent;
import com.tibco.cep.dashboard.security.SecurityClient;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.dashboard.security.SecurityTokenListener;
import com.tibco.cep.dashboard.timer.ExceptionResistentTimerTask;
import com.tibco.cep.dashboard.timer.TimerProvider;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

class BizSessionProvider extends ServiceDependent {

	private static BizSessionProvider instance;

	static final synchronized BizSessionProvider getInstance() {
		if (instance == null) {
			instance = new BizSessionProvider();
		}
		return instance;
	}

	private SecurityClient securityClient;

	private SecurityTokenListenerImpl securityTokenListener;

	private Map<String, BizSession> sessions;

	private SessionTimeOutChecker sessionTimeOutChecker;

	private BizSessionProvider() {
		super("bizsessionprovider","Business Session Provider");
		sessions = new ConcurrentHashMap<String, BizSession>();
	}

	@Override
	protected void doStart() throws ManagementException {
        try {
            securityClient = (SecurityClient) ManagementUtils.getContext().lookup("security");
        } catch (NamingException ex) {
            String msg = messageGenerator.getMessage("bizservice.security.lookup.failure");
            throw new ManagementException(msg,ex);
        }
		this.securityTokenListener = new SecurityTokenListenerImpl();
		securityClient.addSecurityTokenListener(securityTokenListener);
		doResume();
	}

	@Override
	protected void doPause() throws ManagementException {
		if (sessionTimeOutChecker != null){
			sessionTimeOutChecker.cancel();
			sessionTimeOutChecker = null;
		}
	}

	@Override
	protected void doResume() throws ManagementException {
		final long timeOutValue = (Long) BizProperties.SESSION_TIMEOUT_KEY.getValue(properties);
		if (timeOutValue != -1) {
			if (timeOutValue < DateTimeUtils.MINUTE) {
				throw new ManagementException(messageGenerator.getMessage("invalid.timeout.value", new MessageGeneratorArgs(null, BizProperties.SESSION_TIMEOUT_KEY.getName(), timeOutValue)));
			}
			long minRepeatValue = DateTimeUtils.DAY;
			if (timeOutValue < DateTimeUtils.DAY) {
				int scale = DateTimeUtils.computeScaleUnit(timeOutValue);
				minRepeatValue = DateTimeUtils.getUnitValue(scale);
			}
			sessionTimeOutChecker = new SessionTimeOutChecker("Session TimeOut Checker", logger, exceptionHandler, true, timeOutValue);
			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "Scheduling '"+sessionTimeOutChecker+"' every " + DateTimeUtils.getTimeAsStr(minRepeatValue) + " for " + DateTimeUtils.getTimeAsStr(timeOutValue) + " timeout...");
			}

			TimerProvider.getInstance().scheduleWithFixedDelay(sessionTimeOutChecker, minRepeatValue, minRepeatValue, TimeUnit.MILLISECONDS);
		}
	}

	@Override
	protected boolean doStop() {
		if (sessionTimeOutChecker != null) {
			sessionTimeOutChecker.cancel();
		}
		securityClient.removeSecurityTokenListener(securityTokenListener);
		// close all sessions
		for (BizSession session : sessions.values()) {
			session.shutdown();
		}
		sessions.clear();
		try {
			this.securityClient.cleanup();
		} catch (ManagementException e) {
			//TODO handle securityClient.cleanup() exception
		}
		return true;
	}

	BizSession createSession(String token) {
		synchronized (token.intern()) {
			String sessionid = SUID.createId().toString();
			BizSession session = new BizSession(token, sessionid);
			sessions.put(sessionid, session);
			return session;
		}
	}

	BizSession getSession(String id) {
		BizSession bizSession = sessions.get(id);
		if (bizSession != null) {
			bizSession.setIsOld();
			bizSession.setLastAccessedTime(System.currentTimeMillis());
		}
		return bizSession;
	}

	BizSession[] getSessions(String tokenId) {
		List<BizSession> sessionList = new LinkedList<BizSession>();
		for (BizSession bizSession : sessions.values()) {
			if (bizSession.getTokenId().equals(tokenId) == true) {
				sessionList.add(bizSession);
			}
		}
		return sessionList.toArray(new BizSession[sessionList.size()]);
	}

	BizSession removeSession(String id) {
		return sessions.remove(id);
	}

	private final class SessionTimeOutChecker extends ExceptionResistentTimerTask {
		private final long timeOutValue;

		private SessionTimeOutChecker(String name, Logger logger, ExceptionHandler exceptionHandler, boolean cancelOnException, long timeOutValue) {
			super(name, logger, exceptionHandler, cancelOnException);
			this.timeOutValue = timeOutValue;
		}

		@Override
		public void doRun() {
			if (isRunning() == false) {
				return;
			}
			List<BizSession> expiredSessions = new LinkedList<BizSession>();
			for (BizSession session : sessions.values()) {
				if (System.currentTimeMillis() - session.getLastAccessedTime() >= timeOutValue) {
					expiredSessions.add(session);
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, session + " has been marked for invalidation...");
					}
				}
				if (isRunning() == false) {
					return;
				}
			}
			if (isRunning() == false) {
				return;
			}
			for (BizSession expiredSession : expiredSessions) {
				expiredSession.invalidate();
			}
		}
	}

	class SecurityTokenListenerImpl implements SecurityTokenListener {

		@Override
		public void tokenCreated(SecurityToken token) {
			// do nothing
		}

		@Override
		public void tokenDeleted(SecurityToken token) {
			BizSession[] sessions = getSessions(token.toString());
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Shutting down " + sessions.length + " session(s) for " + token);
			}
			for (BizSession bizSession : sessions) {
				if (removeSession(bizSession.getId()) != null) {
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Shutting down " + bizSession + " for " + token);
					}
					bizSession.shutdown();
				}
			}
		}

		@Override
		public void tokenExpired(SecurityToken token) {
			tokenDeleted(token);
		}

	}
}
