/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

import com.tibco.be.monitor.service.spi.AnalyzerService;
import com.tibco.be.monitor.service.spi.DictionaryService;
import com.tibco.be.monitor.thread.analyzer.Analyzer;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Nov 30, 2009 / Time: 10:25:27 PM
 */
public class ServiceLoaderUtil {

    private ServiceLoaderUtil() {
    }

    public static final <T> List<T> getServices(Class<T> classInstance) {
        List<T> services = new LinkedList<T>();
        ServiceLoader<T> loader = ServiceLoader.load(classInstance);
        Iterator<T> it = loader.iterator();
        while (it.hasNext()) {
            services.add(it.next());
        }
        return services;
    }

    public static List<Analyzer> getAnalyzers() {
        List<AnalyzerService> services = getServices(
                AnalyzerService.class);
        List<Analyzer> analyzers = new LinkedList<Analyzer>();
        for (AnalyzerService service : services) {
            analyzers.addAll(service.getAnalyzers());
        }
        return analyzers;
    }

    public static final List<DictionaryService> getDictionaryServices() {
        return getServices(DictionaryService.class);
    }
}
