/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.service.spi;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ksubrama
 */
public interface DictionaryService {

    Map<String, List<String>> getGroups();

}
