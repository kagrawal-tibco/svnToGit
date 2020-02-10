package com.tibco.cep.studio.mapper.ui.data.bind;


import java.util.ListResourceBundle;

public class Bundle extends ListResourceBundle {
//   private static ResourceManager MANAGER;
//
//   public static ResourceManager manager() {
//      if (MANAGER == null) {
//         MANAGER = new ResourceManager("com.tibco.ui.im.bind.Bundle");
//      }
//      return MANAGER;
//   }

   public Object[][] getContents() {
      return contents;
   }

   private final static String[][] contents =
           {
              {"test", "thisout"},
              {"ObjectIcon", "/com/tibco/ae/tools/console/bind/images/Object.gif"},
              {"AttributeIcon", "/com/tibco/ae/tools/console/bind/images/Attribute.gif"},
              {"NewIcon", "/com/tibco/ae/tools/console/ui/images/new_resource.gif"} //@hack for now.
           };
}
