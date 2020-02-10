package com.tibco.cep.studio.mapper.ui.xmlui;

import java.util.HashSet;

import com.tibco.cep.studio.mapper.TreeFilter;


/**
 * A tree filter that only allows files with given suffixes.
 * WCETODO push into Designer.
 */
public class FileSuffixTreeFilter implements TreeFilter {
   private final HashSet<String> m_allowedSuffixes = new HashSet<String>();

   /**
    * By default allows no files (ignoring suffix),
    * Suffixes may be added with {@link #addAllowedSuffix}
    */
   public FileSuffixTreeFilter() {
   }

   public FileSuffixTreeFilter(String allowedSuffix) {
      this();
      addAllowedSuffix(allowedSuffix);
   }

   public FileSuffixTreeFilter(String[] allowedSuffixes) {
      for (int i = 0; i < allowedSuffixes.length; i++) {
         addAllowedSuffix(allowedSuffixes[i]);
      }
   }

   public void addAllowedSuffix(String suffix) {
      m_allowedSuffixes.add(suffix);
   }

   public boolean allowNode(Object node) {
       return true;
       //RYANTODO-Should run over the indexer.  This should check if the extension matches the one in our FileSuffixSets
       
//       boolean isFile = ((AEResource)node).mapsToVFile();
//       if (!isFile)
//       {
//           return false;
//       }
//       AEResource r = (AEResource) node;
//       String path = r.getPath();
//       String rt = r.getVFileExtension();
//       if (rt==null || rt.length()==0)
//       {
//           return false;
//       }
//       return m_allowedSuffixes.contains(rt);
   }
}
