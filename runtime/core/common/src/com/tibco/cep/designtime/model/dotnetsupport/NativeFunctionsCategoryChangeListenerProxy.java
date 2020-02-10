package com.tibco.cep.designtime.model.dotnetsupport;


import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.FunctionsCategoryChangeListener;


/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 8, 2006
 * Time: 9:12:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class NativeFunctionsCategoryChangeListenerProxy extends AbstractNativeChangeListenerProxy implements FunctionsCategoryChangeListener {


    public NativeFunctionsCategoryChangeListenerProxy(long gcHandle) {
        super(gcHandle);
    }


    public void onCategoryChange(FunctionsCategory functionsCategory) {
        System.out.println("oncategorychange");
        onCategoryChange(gcHandle, functionsCategory);
    }


    private static native void onCategoryChange(long gcHandle, FunctionsCategory functionsCategory);
}
