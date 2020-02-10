package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.Color;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathTextArea;

/**
 * A simple subclass of {@link XPathTextArea} to interface with the {@link BindingTree}.
 */
class BindingXPathTextArea extends XPathTextArea {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private final BindingEditor mBindingEditor;
   /**
    * Flag to avoid issuing change notifications on setText()
    */
   private boolean mIgnoreChanges;
   
   /**
    * this for the case , if user not interested in inline change 
    * instead user want to use some other listener for updates (like focuschangeListen)
    * default its false
    * TODO 
    */
   private boolean ignoreInlineEditChange = false;

   public BindingXPathTextArea(UIAgent uiAgent, BindingEditor be) {
      super(uiAgent);
      mBindingEditor = be;
      super.setAllowsEmptyString(true);
      setBackground(new Color(245, 245, 255));
      setFieldMode();
      setInlineEditingMode(true);
      getDocument().addDocumentListener(new DocumentListener() {
         public void insertUpdate(DocumentEvent e) {
            if (!mIgnoreChanges) {
               inlineEditChanged();
            }
         }

         public void removeUpdate(DocumentEvent e) {
            if (!mIgnoreChanges) {
               inlineEditChanged();
            }
         }

         public void changedUpdate(DocumentEvent e) {
            if (!mIgnoreChanges) {
               inlineEditChanged();
            }
         }
      });
   }

   public void setText(String st) {
      mIgnoreChanges = true;
      super.setText(st);
      mIgnoreChanges = false;
   }

   public boolean isManagingFocus() {
      return true;
   }

   private void inlineEditChanged() {
	   if(!ignoreInlineEditChange)
		   mBindingEditor.fireContentChanged();
   }
   
   public void enableIgnoreInlineEditChange(boolean enable){
	   ignoreInlineEditChange = enable;
   }
   
   
}

