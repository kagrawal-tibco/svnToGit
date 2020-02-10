/**
 * @author ishaan
 * @version Jul 12, 2004, 9:13:33 PM
 */
package com.tibco.cep.studio.ui.editors.events;

import java.util.Iterator;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.ImportRegistryEntry;
import com.tibco.cep.mapper.xml.xdata.DefaultImportRegistry;
import com.tibco.cep.mapper.xml.xdata.DefaultNamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.NamespaceMapper;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;

/**
 * A class for converting between BE and BW's ImportRegistry / NamespaceImporter.  This is needed to keep BW's
 * ParameterEditor happy.  A terrible hack.
 */

public class NSUtilitiesConverter {

    protected NSUtilitiesConverter() {}

    /**
     * Very simple method to just copy over ImportRegistryEntries from BE to BW.
     * @param beRegistry The BE ImportRegistry to convert.
     * @return A non-null BW ImportRegistry.
     */
    public static ImportRegistry ConvertToBWImportRegistry(com.tibco.xml.ImportRegistry beRegistry) {
        ImportRegistry bwRegistry = null;

        bwRegistry = new DefaultImportRegistry();

        if(beRegistry != null) {
            com.tibco.xml.ImportRegistryEntry[] beRegistrySet = beRegistry.getImports();

            if(beRegistrySet != null) {
                ImportRegistryEntry[] bwRegistrySet = new ImportRegistryEntry[beRegistrySet.length];
                for(int i = 0; i < beRegistrySet.length; i++) {
                    com.tibco.xml.ImportRegistryEntry beRegistryEntry = beRegistrySet[i];
                    bwRegistrySet[i] = new ImportRegistryEntry(beRegistryEntry.getNamespaceURI(), beRegistryEntry.getLocation());
                }

                bwRegistry.setImports(bwRegistrySet);
            }
        }

        return bwRegistry;
    }

    /**
     * Very simple method to just copy over ImportRegistryEntries from BW to BE.
     * @param bwRegistry The BW ImportRegistry to convert.
     * @return A non-null BE ImportRegistry.
     */
    public static com.tibco.xml.ImportRegistry ConvertTOBEImportRegistry(ImportRegistry bwRegistry) {
        com.tibco.xml.ImportRegistry beRegistry = null;
        beRegistry = new com.tibco.xml.DefaultImportRegistry();

        if(bwRegistry != null) {
            ImportRegistryEntry[] bwRegistrySet = bwRegistry.getImports();

            if(bwRegistrySet != null) {
                com.tibco.xml.ImportRegistryEntry[] beRegistrySet = new com.tibco.xml.ImportRegistryEntry[bwRegistrySet.length];
                for(int i = 0; i < bwRegistrySet.length; i++) {
                    ImportRegistryEntry bwRegistryEntry = bwRegistrySet[i];
                    beRegistrySet[i] = new com.tibco.xml.ImportRegistryEntry(bwRegistryEntry.getNamespaceURI(), bwRegistryEntry.getLocation());
                }

                beRegistry.setImports(beRegistrySet);
            }
        }

        return beRegistry;
    }

    public static NamespaceMapper ConvertToBWNamespaceMapper(com.tibco.xml.NamespaceMapper beMapper) {
        NamespaceMapper bwMapper = null;
        bwMapper = new DefaultNamespaceMapper();

        if(beMapper != null) {
            Iterator it = beMapper.getPrefixes();
            while(it.hasNext()) {
                try {
                    String prefix = (String) it.next();
                    String namespace = beMapper.getNamespaceURIForPrefix(prefix);
                    bwMapper.addNamespaceURI(prefix, namespace);
                } catch (PrefixToNamespaceResolver.PrefixNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return bwMapper;
    }

    public static com.tibco.xml.NamespaceMapper ConvertToBENamespaceMapper(NamespaceMapper bwMapper) {
        com.tibco.xml.NamespaceMapper beMapper = null;
        beMapper = new com.tibco.xml.DefaultNamespaceMapper();

        if(bwMapper != null) {
            Iterator it = bwMapper.getPrefixes();
            while(it.hasNext()) {
                try {
                    String prefix = (String) it.next();
                    String namespace = bwMapper.getNamespaceURIForPrefix(prefix);
                    beMapper.addNamespaceURI(prefix, namespace);
                } catch (PrefixToNamespaceResolver.PrefixNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return beMapper;
    }
}
