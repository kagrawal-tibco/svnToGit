package com.tibco.rta.service.persistence.as;

import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.as.space.browser.BrowserDef.DistributionScope;
import com.tibco.as.space.browser.BrowserDef.TimeScope;
import com.tibco.as.space.remote.MemberInvocable;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

public class PurgeInvocableHandler implements MemberInvocable{
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
			LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());
	
	@Override
	public Tuple invoke(Space space, Tuple contextTuple) {
		int deletionCount = 0;

		BrowserDef browserDef = BrowserDef.create();
		browserDef.setDistributionScope(DistributionScope.SEEDED);
		browserDef.setTimeScope(TimeScope.SNAPSHOT);
		Browser browser;
		if(LOGGER.isEnabledFor(Level.DEBUG)){
			LOGGER.log(Level.DEBUG, "Running purge job on seeder");
		}
		try {
			String query = contextTuple.getString(ASPersistenceService.PURGE_QUERY_FIELD);
			browser = space.browse(BrowserType.TAKE, browserDef, query);
			Tuple tuple;
			while ((tuple = browser.next()) != null) {
				deletionCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(LOGGER.isEnabledFor(Level.DEBUG)){
			LOGGER.log(Level.DEBUG, "Purge job at seeder completed, purge count [%s]", deletionCount);
		}
		Tuple resultTuple = Tuple.create();
		resultTuple.put(ASPersistenceService.PURGED_ITEM_COUNT, deletionCount);

		return resultTuple;
	}
}
