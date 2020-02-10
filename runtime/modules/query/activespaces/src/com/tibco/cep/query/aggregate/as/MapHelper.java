package com.tibco.cep.query.aggregate.as;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 12/12/12
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapHelper {
    public static final ThreadLocal mapThreadLocal = new ThreadLocal();

    public static void set(Map[] mapArray) {
        mapThreadLocal.set(mapArray);
    }

    public static void unset() {
        mapThreadLocal.remove();
    }

    public static Map[] get() {
        return (Map[]) mapThreadLocal.get();
    }
}
