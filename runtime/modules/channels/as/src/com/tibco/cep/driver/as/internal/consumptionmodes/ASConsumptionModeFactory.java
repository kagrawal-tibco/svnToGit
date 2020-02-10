package com.tibco.cep.driver.as.internal.consumptionmodes;

import static com.tibco.as.space.browser.BrowserDef.BrowserType.GET;
import static com.tibco.as.space.browser.BrowserDef.BrowserType.TAKE;
import static com.tibco.as.space.browser.BrowserDef.BrowserType.LOCK;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_BROWSER_TYPE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_CONSUMPTION_MODE;
import static com.tibco.cep.driver.as.ASConstants.K_BE_EVENT_PROP_BROWSER_TYPE;
import static com.tibco.cep.driver.as.ASConstants.K_BE_EVENT_PROP_CONSUMPTION_MODE;
import static com.tibco.cep.driver.as.ASConstants.K_BE_EVENT_PROP_EVENT_TYPE;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_EXPIRE_EVENT;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_PUT_EVENT;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_TAKE_EVENT;
import static com.tibco.cep.driver.as.ASConstants.ConsumptionMode.EntryBrowser;
import static com.tibco.cep.driver.as.ASConstants.ConsumptionMode.EventListener;
import static com.tibco.cep.driver.as.ASConstants.ConsumptionMode.Router;

import java.util.Properties;

import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.as.space.event.ExpireEvent;
import com.tibco.as.space.event.PutEvent;
import com.tibco.as.space.event.TakeEvent;
import com.tibco.as.space.router.RouterWriteOp;
import com.tibco.cep.driver.as.ASConstants.ConsumptionMode;
import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.driver.as.internal.consumers.ASEventConsumer;
import com.tibco.cep.driver.as.internal.consumers.ASRouterConsumer;
import com.tibco.cep.driver.as.internal.consumers.ASTupleConsumer;
import com.tibco.cep.driver.as.internal.consumers.IASPayloadConsumer;
import com.tibco.cep.driver.as.internal.fillers.ASTupleFiller;
import com.tibco.cep.driver.as.internal.fillers.IASTupleFiller;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.DestinationConfig;

public class ASConsumptionModeFactory {

    private static final IASPayloadConsumer _EVENT_LISTENER_PUT_EVENT_CONSUMER;
    private static final IASPayloadConsumer _EVENT_LISTENER_TAKE_EVENT_CONSUMER;
    private static final IASPayloadConsumer _EVENT_LISTENER_EXPIRE_EVENT_CONSUMER;

    private static final IASPayloadConsumer _ENTRY_BROWSER_GET_TYPE_CONSUMER;
    private static final IASPayloadConsumer _ENTRY_BROWSER_TAKE_TYPE_CONSUMER;
    private static final IASPayloadConsumer _ENTRY_BROWSER_LOCK_TYPE_CONSUMER;

    private static final IASPayloadConsumer _ROUTER_PUT_EVENT_CONSUMER;
    private static final IASPayloadConsumer _ROUTER_TAKE_EVENT_CONSUMER;

    static {
        // initialize consumers of event listener
        IASTupleFiller putEventFiller = new ASTupleFiller(
                new String[][] {
                    {K_BE_EVENT_PROP_CONSUMPTION_MODE, EventListener.toString()},
                    {K_BE_EVENT_PROP_EVENT_TYPE, K_V_AS_DEST_PROP_PUT_EVENT}});
        IASTupleFiller takeEventFiller = new ASTupleFiller(
                new String[][] {
                    {K_BE_EVENT_PROP_CONSUMPTION_MODE, EventListener.toString()},
                    {K_BE_EVENT_PROP_EVENT_TYPE, K_V_AS_DEST_PROP_TAKE_EVENT}});
        IASTupleFiller expireEventFiller = new ASTupleFiller(
                new String[][] {
                    {K_BE_EVENT_PROP_CONSUMPTION_MODE, EventListener.toString()},
                    {K_BE_EVENT_PROP_EVENT_TYPE, K_V_AS_DEST_PROP_EXPIRE_EVENT}});
        _EVENT_LISTENER_PUT_EVENT_CONSUMER = new ASEventConsumer(PutEvent.class, putEventFiller);
        _EVENT_LISTENER_TAKE_EVENT_CONSUMER = new ASEventConsumer(TakeEvent.class, takeEventFiller);
        _EVENT_LISTENER_EXPIRE_EVENT_CONSUMER = new ASEventConsumer(ExpireEvent.class, expireEventFiller);

        // initialize consumers of entry browser
        IASTupleFiller entryBrowserGetTypefiller = new ASTupleFiller(
                new String[][] {
                    {K_BE_EVENT_PROP_CONSUMPTION_MODE, EntryBrowser.toString()},
                    {K_BE_EVENT_PROP_BROWSER_TYPE, GET.toString()}});
        IASTupleFiller entryBrowserTakeTypefiller = new ASTupleFiller(
                new String[][] {
                    {K_BE_EVENT_PROP_CONSUMPTION_MODE, EntryBrowser.toString()},
                    {K_BE_EVENT_PROP_BROWSER_TYPE, TAKE.toString()}});
        IASTupleFiller entryBrowserLockTypefiller = new ASTupleFiller(
                new String[][] {
                    {K_BE_EVENT_PROP_CONSUMPTION_MODE, EntryBrowser.toString()},
                    {K_BE_EVENT_PROP_BROWSER_TYPE, LOCK.toString()}});
        _ENTRY_BROWSER_GET_TYPE_CONSUMER = new ASTupleConsumer(entryBrowserGetTypefiller);
        _ENTRY_BROWSER_TAKE_TYPE_CONSUMER = new ASTupleConsumer(entryBrowserTakeTypefiller);
        _ENTRY_BROWSER_LOCK_TYPE_CONSUMER = new ASTupleConsumer(entryBrowserLockTypefiller);

        IASTupleFiller routerPutFiller = new ASTupleFiller(
                new String[][] {
                    {K_BE_EVENT_PROP_CONSUMPTION_MODE, Router.toString()},
                    {K_BE_EVENT_PROP_EVENT_TYPE, K_V_AS_DEST_PROP_PUT_EVENT}});
        _ROUTER_PUT_EVENT_CONSUMER = new ASRouterConsumer(routerPutFiller, RouterWriteOp.OpType.PUT);

        IASTupleFiller routerTakeFiller = new ASTupleFiller(
                new String[][] {
                    {K_BE_EVENT_PROP_CONSUMPTION_MODE, Router.toString()},
                    {K_BE_EVENT_PROP_EVENT_TYPE, K_V_AS_DEST_PROP_TAKE_EVENT}});
        _ROUTER_TAKE_EVENT_CONSUMER = new ASRouterConsumer(routerTakeFiller, RouterWriteOp.OpType.TAKE);

    }

    public static synchronized IASConsumptionMode createASConsumptionMode(
            IASDestination asDest,
            DestinationConfig config,
            Logger logger) {
        IASConsumptionMode consumptionMode = null;
        Properties ps = config.getProperties();
        ConsumptionMode cm = ConsumptionMode.valueOf(ps.getProperty(K_AS_DEST_PROP_CONSUMPTION_MODE));
        if (ConsumptionMode.EventListener == cm) {
            // EventListener mode
            consumptionMode = new ASConsumptionModeEventListener(
                    asDest, logger,
                    _EVENT_LISTENER_PUT_EVENT_CONSUMER, _EVENT_LISTENER_TAKE_EVENT_CONSUMER, _EVENT_LISTENER_EXPIRE_EVENT_CONSUMER);
        } else if (ConsumptionMode.EntryBrowser == cm) {
            // EntryBrowser mode
            BrowserType browserType = BrowserType.valueOf(ps.getProperty(K_AS_DEST_PROP_BROWSER_TYPE));
            IASPayloadConsumer consumer = null;
            if (GET == browserType) {
                consumer = _ENTRY_BROWSER_GET_TYPE_CONSUMER;
            } else if (TAKE == browserType) {
                consumer = _ENTRY_BROWSER_TAKE_TYPE_CONSUMER;
            } else if (LOCK == browserType) {
                consumer = _ENTRY_BROWSER_LOCK_TYPE_CONSUMER;
            }
            consumptionMode = new ASConsumptionModeEntryBrowser(
                    asDest, logger,
                    consumer);
        } else if (ConsumptionMode.Router == cm) {
            consumptionMode = new ASConsumptionModeRouter(asDest, logger,_ROUTER_PUT_EVENT_CONSUMER,_ROUTER_TAKE_EVENT_CONSUMER);
        }
        return consumptionMode;
    }

}
