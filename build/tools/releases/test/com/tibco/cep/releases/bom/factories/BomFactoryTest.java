package com.tibco.cep.releases.bom.factories;

import com.tibco.cep.releases.bom.AddOn;
import com.tibco.cep.releases.bom.Bom;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.SortedSet;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/27/11
 * Time: 6:26 PM
 */
public class BomFactoryTest {


    @Parameters({"bom-path" })
    @Test
    public void MainTest(
            String bomPath)
            throws Exception {

        final Bom bom = new BomFactory().make(bomPath);

        assert(bom.size() == 6);

        SortedSet<AddOn> addOns, addOns2;

        addOns = bom.getByInstalledGaPath("/this/path/does/not/exist");
        assert(addOns.size() == 0);

        addOns = bom.getByInstalledGaPath(".");
        assert(addOns.size() == 0);

        addOns = bom.getBySourcePath("/plugins/com.tibco.cep.eventstreamprocessing/");
        assert(addOns.size() == 1);
        assert(addOns.first().getName().equals("eventstreamprocessing"));

        addOns2 = bom.getBySourcePath("/plugins/com.tibco.cep.eventstreamprocessing/123");
        assert(addOns2.size() == 1);
        assert(addOns2.first().getName().equals("eventstreamprocessing"));
        assert(addOns2.equals(addOns));

        addOns = bom.getByInstalledGaPath("/studio/eclipse/plugins/");
        assert(addOns.size() == 5);
        assert(!addOns.contains(bom.get("express")));

        addOns = bom.getByInstalledHfPath("/examples/event_stream_processing/bql/Something/More");
        assert(addOns.size() == 1);
        assert(addOns.first().getName().equals("eventstreamprocessing"));
    }


}
