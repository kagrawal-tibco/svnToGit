package com.tibco.cep.dashboard.security;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.AbstractLocalClientImpl;

public class SecurityMXBeanImpl extends AbstractLocalClientImpl implements SecurityMXBean {
	
	private SecurityService securityService;
	
	public SecurityMXBeanImpl() {
		super(SecurityService.NAME);
		securityService = (SecurityService) service;
	}
	
	@Override
	public int getLoginAttempts() {
		return securityService.getLoginAttempts();
	}
	
	@Override
	public int getSuccessfulLoginAttempts() {
		return securityService.getSuccessfulLoginAttempts();
	}
	
	@Override
	public int getFailedLoginAttempts() {
		return getLoginAttempts() - getSuccessfulLoginAttempts();
		
	}	
	
	@Override
	public int getLoggedInUserCount() {
		return securityService.getTokens().size();
	}
	
	@Override
	public double getAverageLoggedInTime() {
		double sum = 0;
		double count = 0;
		for (SecurityToken token : securityService.getTokens()) {
			long loggedInTime = System.currentTimeMillis() - ((LocalSecurityTokenImpl)token).getCreationTime();
			sum = sum + loggedInTime;
			count++;
		}
		if (count == 0){
			return 0;
		}
		return sum/count;
	}

	@Override
	public UserRuntimeInfo getMaxLoggedInUser() {
		UserRuntimeInfo info = null;
		long loggedInTime = -1;
		for (SecurityToken token : securityService.getTokens()) {
			long tempLoggedInTime = System.currentTimeMillis() - ((LocalSecurityTokenImpl)token).getCreationTime();
			if (tempLoggedInTime > loggedInTime){
				if (info == null){
					info = new UserRuntimeInfo(token.getUserID(),tempLoggedInTime);
				}
				else {
					info.setUserid(token.getUserID());
					info.setTime(tempLoggedInTime);
				}
				loggedInTime = tempLoggedInTime;
			}
		}
		return info;
	}

	@Override
	public UserRuntimeInfo getMinLoggedInUser() {
		UserRuntimeInfo info = null;
		long loggedInTime = -1;
		for (SecurityToken token : securityService.getTokens()) {
			long tempLoggedInTime = System.currentTimeMillis() - ((LocalSecurityTokenImpl)token).getCreationTime();
			if (tempLoggedInTime < loggedInTime){
				if (info == null){
					info = new UserRuntimeInfo(token.getUserID(),tempLoggedInTime);
				}
				else {
					info.setUserid(token.getUserID());
					info.setTime(tempLoggedInTime);
				}
				loggedInTime = tempLoggedInTime;
			}
		}
		return info;		
	}

	@Override
	public int logout(String userid) {
		if (StringUtil.isEmptyOrBlank(userid) == true){
			return 0;
		}
		List<SecurityToken> targetTokens = new ArrayList<SecurityToken>();
		for (SecurityToken token : securityService.getTokens()) {
			if (userid.equals(token.getUserID()) == true){
				targetTokens.add(token);
			}
		}
		for (SecurityToken targetToken : targetTokens) {
			securityService.logout(targetToken);
		}
		return targetTokens.size();
	}
	
	@Override
	public int getLogoutAttempts() {
		return securityService.getLogoutAttempts();
	}
	
	@Override
	public int getSuccessfulLogoutAttempts() {
		return securityService.getSuccessfulLogoutAttempts();
	}

	@Override
	public int getFailedLogoutAttempts() {
		return getLogoutAttempts() - getSuccessfulLogoutAttempts();
	}

	@Override
	public void resetStats() {
		securityService.resetCounters();
	}

}
