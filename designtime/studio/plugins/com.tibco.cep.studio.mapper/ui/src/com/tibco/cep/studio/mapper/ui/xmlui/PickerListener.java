package com.tibco.cep.studio.mapper.ui.xmlui;


import com.tibco.xml.datamodel.XiNode;

/**
 * Picker listener interface
 */
public interface PickerListener {

   /**
    * The user has selected
    *
    * @param data The XiNode
    */
   public void pickCompleted(XiNode data);

}