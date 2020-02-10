package com.tibco.cep.modules;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 7, 2007
 * Time: 3:10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class CEPModule {

    String name;
//    DTFactory dtFactory;
    RTFactory rtFactory;
//    CGFactory cgFactory;
//    UIFactory uiFactory;

    protected CEPModule(String name,String rtClassNm)  {
        this.name = name;
        Class klazz;
//        try {
//            klazz = Class.forName(dtClassNm);
//            dtFactory = (DTFactory) klazz.newInstance();
//        } catch (Throwable e) {
//        }

        try {
            klazz = Class.forName(rtClassNm);
            rtFactory = (RTFactory) klazz.newInstance();
        } catch (Throwable e) {
        }
//        try {
//            klazz = Class.forName(cgClassNm);
//            cgFactory = (CGFactory) klazz.newInstance();
//        } catch (Throwable e) {
//        }
//        try {
//            klazz = Class.forName(uiClassNm);
//            uiFactory = (UIFactory) klazz.newInstance();
//        } catch (Throwable e) {
//
//        }

    }

    public String getName() {
        return name;
    }

//    public DTFactory getDesigntimeFactory() {
//        return dtFactory;
//    }

    public RTFactory getRuntimeFactory() {
        return rtFactory;
    }

//    public CGFactory getCodegenFactory() {
//        return cgFactory;
//    }

//    public UIFactory getUIResourceFactory() {
//        return uiFactory;
//    }

}
