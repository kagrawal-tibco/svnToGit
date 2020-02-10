/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.service.spi;

import java.util.List;

import com.tibco.be.monitor.thread.analyzer.Analyzer;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Dec 2, 2009 / Time: 5:14:20 PM
 */
public interface AnalyzerService {

    List<Analyzer> getAnalyzers();
}
