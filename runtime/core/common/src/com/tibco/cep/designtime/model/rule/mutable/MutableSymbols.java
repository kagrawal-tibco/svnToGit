package com.tibco.cep.designtime.model.rule.mutable;


import com.tibco.cep.designtime.model.MutationObservableContainer;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 11:03:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MutableSymbols extends Symbols, MutationObservableContainer {


    boolean move(String name, int index);


    Symbol put(String name, String type);


    MutableSymbol put(MutableSymbol symbol);


    boolean renameSymbol(
            String oldName,
            String newName);

}
