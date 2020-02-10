package com.tibco.cep.studio.mapper.ui.data;

import javax.swing.JComponent;

/**
 * The objects the various XPath trees, text fields use to drag'n'drop
 */
public class XPathSoftDragItem {
   private boolean m_isFunction;

   public XPathSoftDragItem(String path, JComponent c) {
      this.path = path;
      fromWindow = c;
      isCharacterReference = false;
      namespace = null;
      m_isFunction = false;
   }

   /**
    * @deprecated
    */
   public XPathSoftDragItem(String path, JComponent c, boolean isCharRef, String ns) {
      this.path = path;
      fromWindow = c;
      isCharacterReference = isCharRef;
      this.namespace = ns;
      m_isFunction = false;
   }

   /**
    * @param isCharRef Set to true if this is a reference to a character.
    */
   public XPathSoftDragItem(String path, JComponent c, boolean isCharRef) {
      this.path = path;
      fromWindow = c;
      isCharacterReference = isCharRef;
      this.namespace = null;
      m_isFunction = false;
   }

   public XPathSoftDragItem(String function, JComponent c, String functionNs) {
      this.path = function;
      fromWindow = c;
      isCharacterReference = false;
      m_isFunction = true;
      this.namespace = functionNs;
   }

   public String getXPath() {
      return path;
   }

   public boolean getIsFunction() {
      return m_isFunction;
   }

   public final String path;
   public final JComponent fromWindow;
   public final boolean isCharacterReference;
   public final String namespace;
}
