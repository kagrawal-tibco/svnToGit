package com.tibco.rta.metric;

import com.tibco.rta.client.AbstractClientBrowser;
import com.tibco.rta.log.Level;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/2/13
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetricChildrenBrowserProxy<T> extends AbstractClientBrowser<T> {

    @Override
    public boolean hasNext() {
        try {
            return session.hasNextChild(id);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
        return false;
    }

    @Override
    public T next() {
        try {
            return session.nextChild(id);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
        return null;
    }
}
