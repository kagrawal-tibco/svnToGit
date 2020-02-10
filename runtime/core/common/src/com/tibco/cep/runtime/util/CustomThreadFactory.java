package com.tibco.cep.runtime.util;

import java.util.concurrent.ThreadFactory;

/*
* Author: Ashwin Jayaprakash Date: Mar 16, 2009 Time: 11:48:08 AM
*/
public interface CustomThreadFactory<T extends Thread> extends ThreadFactory {
    T newThread(Runnable r);
}
