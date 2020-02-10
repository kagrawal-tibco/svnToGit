package com.tibco.cep.driver.as.internal.consumptionmodes;

import static com.tibco.as.space.browser.BrowserDef.WAIT_FOREVER;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_CONSUMPTION_MODE_CONDITION_DESTINATION_CONFIG;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_BROWSER_TYPE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_DISTRIBUTION_SCOPE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_TIME_SCOPE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_PREFETCH;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_QUERYLIMIT;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_START_MODE;

import java.util.Map;
import java.util.Properties;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.Space;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.driver.as.internal.consumers.IASPayloadConsumer;
import com.tibco.cep.driver.as.internal.consumptionmodes.entrybrowser.EntryBrowserMainLoop;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.util.SystemProperty;

class ASConsumptionModeEntryBrowser extends AASConsumptionMode {

    private Browser browser;
    private EntryBrowserMainLoop mainLoop;
    private Thread mainLoopThread;
    
    private BrowserDef browserDef;
    private BrowserType browserType;

    ASConsumptionModeEntryBrowser(IASDestination asDest, Logger logger, IASPayloadConsumer... consumers) {
        super(asDest, logger, consumers);
    }

    @Override
    protected void doStart(Map<Object, Object> condition) throws Exception {
        if (!initialized) {
            Space space = asDest.getSpace();
            if (null != space) {
                DestinationConfig config = (DestinationConfig) condition
                        .get(K_AS_DEST_CONSUMPTION_MODE_CONDITION_DESTINATION_CONFIG);
                Properties props = config.getProperties();

                String browserTimeScope = props.getProperty(K_AS_DEST_PROP_TIME_SCOPE);
                String distributionScope = props.getProperty(K_AS_DEST_PROP_DISTRIBUTION_SCOPE);
                long prefetch = Long.valueOf(props.getProperty(K_AS_DEST_PROP_PREFETCH));
                //TODO: long queryLimit = Long.valueOf(props.getProperty(K_AS_DEST_PROP_QUERYLIMIT, "-1"));
                long queryLimit = Long.parseLong(System.getProperty(SystemProperty.AS_CHANNEL_QUERYLIMIT.getPropertyName(), "-1"));
                browserDef = BrowserDef.create().setTimeout(WAIT_FOREVER).setPrefetch(prefetch).setQueryLimit(queryLimit);

                if (browserTimeScope != null && !browserTimeScope.equals("")) {
                    browserDef.setTimeScope(BrowserDef.TimeScope.valueOf(browserTimeScope));
                } else {
                    browserDef.setTimeScope(BrowserDef.TimeScope.ALL);
                }
                if (distributionScope != null && !distributionScope.equals("")) {
                    browserDef.setDistributionScope(BrowserDef.DistributionScope.valueOf(distributionScope));
                } else {
                    browserDef.setDistributionScope(BrowserDef.DistributionScope.ALL);
                }

                browserType = BrowserType.valueOf(props.getProperty(K_AS_DEST_PROP_BROWSER_TYPE));

                logger.log(Level.INFO, "AS Channel created entry browser for %s type=%s filter=%s time-scope=%s dist-scope=%s prefetch=%s limit=%s",
                        asDest.getSpace().getName(), browserType.name(), filter, browserTimeScope, distributionScope, prefetch, queryLimit);
                initialized = true;
            }
        }

    	int startMode = (int) condition.get(K_AS_DEST_START_MODE);
        if (initialized && startMode == ChannelManager.ACTIVE_MODE) {
            doResume();
        }
    }

    public void startMainLoop() {
        Metaspace metaspace = asDest.getChannel().getMetaspace();
        Space space = asDest.getSpace();

        // create the main loop thread
        this.mainLoop = new EntryBrowserMainLoop(this, browser, logger, metaspace, space, hashCode());
        this.mainLoopThread = new Thread(this.mainLoop, "EntryBrowser-MainLoopThread-" + metaspace.getName() + "."
                + space.getName() + "@" + hashCode());
        mainLoopThread.setDaemon(true);
        mainLoopThread.start();
    }

    @Override
    public void stop() throws Exception{
        if (null != mainLoop) {
            mainLoop.shutdown(); // actually, the main loop thread cannot be
                                    // shutdown by this approach.
        }
        if (null != mainLoopThread) {
            mainLoopThread.interrupt();
        }
        if (null != browser) {
            try {
                browser.stop();
            } catch (ASException asEx) {
                // do nothing
                // usually, browser.next() will be blocked if no matched tuple,
                // and it will throw exception when calling Browser.stop() at that
                // time,
                // although, we have to catch this exception but do nothing.
            }
        }
        super.stop();
    }

    @Override
    protected void doResume() throws Exception {
        createBrowser();
    	startMainLoop();
    }

	private void createBrowser() throws Exception {
        // create browser
		Space space = asDest.getSpace();
        if (null != filter && !"".equals(filter)) {
            browser = space.browse(browserType, browserDef, filter);
        } else {
            browser = space.browse(browserType, browserDef);
        }
        isAttachedToSpace = true;
	}
}
