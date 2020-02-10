package com.tibco.cep.dashboard.psvr.mal;

import java.util.Arrays;
import java.util.List;

import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALFooter;
import com.tibco.cep.dashboard.psvr.mal.model.MALHeader;
import com.tibco.cep.dashboard.psvr.mal.model.MALLogin;

/**
 * @author apatil
 *
 */
public class MALGlobalElementsCache extends MALElementCache<MALElement> {

	private static final String FOOTER_CFG_TYPE = "Footer";
	private static final String HEADER_CFG_TYPE = "Header";
	private static final String LOGIN_CFG_TYPE = "Login";

	private static MALGlobalElementsCache instance = null;

	public static final synchronized MALGlobalElementsCache getInstance() {
		if (instance == null) {
			instance = new MALGlobalElementsCache();
		}
		return instance;
	}

	private final List<String> GLOBAL_ELEMENT_CFG_TYPES = Arrays.asList(new String[] { LOGIN_CFG_TYPE, HEADER_CFG_TYPE, FOOTER_CFG_TYPE });

	private MALGlobalElementsCache() {
		super("malglobalelementscache", "MALGlobal Elements Cache");
	}

	@Override
	protected void doStart() throws ManagementException {
		try {
			List<MALElement> elements = getAllSpecificComponentConfigs(GLOBAL_ELEMENT_CFG_TYPES);
			for (MALElement element : elements) {
				addObject(element.getDefinitionType(), element);
			}
		} catch (MALException e) {
			String msg = messageGenerator.getMessage("globalelementscache.buildup.failure");
			throw new ManagementException(msg,e);
		}
	}

	public final MALLogin getLogin() {
		return (MALLogin) getObject(LOGIN_CFG_TYPE);
	}

	public final MALHeader getHeader() {
		return (MALHeader) getObject(HEADER_CFG_TYPE);
	}

	public final MALFooter getFooter() {
		return (MALFooter) getObject(FOOTER_CFG_TYPE);
	}
}
