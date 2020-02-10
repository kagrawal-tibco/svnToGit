package com.tibco.cep.releases.be;

import com.tibco.cep.releases.bom.Bom;
import com.tibco.cep.releases.bom.BomProvider;
import com.tibco.cep.releases.bom.factories.BomFactory;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 7/8/11
 * Time: 3:59 PM
 */
public class BeBomProvider
        extends BomProvider {


    private static final String DEFAULT_BOM_PATH = "bom-be-5.1.xml";


    @SuppressWarnings({"UnusedDeclaration"})
    public static BomProvider getDefaultBomProvider() {

        return DefaultBomProviderHolder.DEFAULT_BOM_PROVIDER;

    }


    public static Bom getDefaultBom() {

        return DefaultBomProviderHolder.DEFAULT_BOM;
    }


    private static class DefaultBomProviderHolder {

        static final BomProvider DEFAULT_BOM_PROVIDER = new BomProvider();
        static final Bom DEFAULT_BOM;
        static {
            final InputStream is = DefaultBomProviderHolder.class.getResourceAsStream(DEFAULT_BOM_PATH);
            try {
                try {
                    DEFAULT_BOM = new BomFactory().make(is);
                } finally {
                    is.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            DEFAULT_BOM_PROVIDER.register(DEFAULT_BOM);
        }

    }

}
