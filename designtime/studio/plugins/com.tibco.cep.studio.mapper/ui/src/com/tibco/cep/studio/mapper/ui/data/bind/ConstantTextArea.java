package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.Color;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea;

/* (c) Copyright 1999-2003, TIBCO Software Inc.  All rights reserved.
 *
 * LEGAL NOTICE:  This source code is provided to specific authorized end
 * users pursuant to a separate license agreement.  You MAY NOT use this
 * source code if you do not have a separate license from TIBCO Software
 * Inc.  Except as expressly set forth in such license agreement, this
 * source code, or any portion thereof, may not be used, modified,
 * reproduced, transmitted, or distributed in any form or by any means,
 * electronic or mechanical, without written permission from  TIBCO
 * Software Inc.
 *
 * User: jbaysdon
 * Date: Sep 29, 2005
 * Version ${VERSION}
 * See LOG at end of file for modification notes.
 */

public class ConstantTextArea extends JExtendedEditTextArea {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private final BindingEditor mBindingEditor;
   /**
    * Flag to avoid issuing change notifications on setText()
    */
   private boolean mIgnoreChanges;

   public ConstantTextArea(BindingEditor be) {
      super();
      mBindingEditor = be;
//      super.setAllowsEmptyString(true);
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
      mBindingEditor.fireContentChanged();
   }
}

/*
 * ${Log:}
 */