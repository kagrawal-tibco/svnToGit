package com.tibco.cep.designtime.model.rule;

import com.tibco.cep.designtime.model.rule.mutable.MutableSymbol;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 11:07:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SymbolsNotificationListener {


    void onSymbolAdded(MutableSymbol symbol);


    void onSymbolChanged(String oldType, MutableSymbol symbol);


    void onSymbolRemoved(MutableSymbol symbol);


    void onSymbolRenamed(String oldName, MutableSymbol symbol);

}
