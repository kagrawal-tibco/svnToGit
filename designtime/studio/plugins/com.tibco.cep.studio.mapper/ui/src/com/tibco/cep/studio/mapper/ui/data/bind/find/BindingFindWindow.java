package com.tibco.cep.studio.mapper.ui.data.bind.find;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import com.tibco.cep.mapper.xml.xdata.bind.AttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditor;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingNode;
import com.tibco.objectrepo.object.ObjectProvider;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * WCETODO Rename this.
 */
class BindingFindWindow implements FindWindowPlugin {
   private JRadioButton mSearchLeftSide;
   private JRadioButton mSearchRightSide;
   private JCheckBox mNameIsRegex;
   private JLabel mFindLabel;
   private JTextField mFindText;
   private BindingEditor mOptionalEditor;
   private JPanel mMainPanel;
   private JLabel mRenderer;
   private ChangeListener mChangeListener;

   public BindingFindWindow(BindingEditor optionalEditor) {
      super();
      ActionListener al = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            searchChanged();
         }
      };
      mOptionalEditor = optionalEditor;
      mSearchLeftSide = new JRadioButton("Search inputs");
      mSearchLeftSide.addActionListener(al);
      mSearchRightSide = new JRadioButton("Search output");
      mSearchRightSide.addActionListener(al);
      mNameIsRegex = new JCheckBox("Regular Expression");
      mNameIsRegex.addActionListener(al);
      JPanel type = new JPanel();
      type.add(mSearchLeftSide);
      type.add(mSearchRightSide);

      JPanel top = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.NONE;
      gbc.weightx = 0;
      gbc.weighty = 0;

      mFindLabel = new JLabel("Find Text:");
      mFindText = new JTextField();

      gbc.gridy = 0;

      gbc.gridx = 0;
      gbc.weightx = 0;
      gbc.fill = GridBagConstraints.BOTH;
      top.add(mFindLabel, gbc);
      gbc.insets = new Insets(0, 8, 0, 0);
      gbc.weightx = 1;
      gbc.gridx = 1;
      top.add(mFindText, gbc);
      mFindText.getDocument().addDocumentListener(new DocumentListener() {
         public void changedUpdate(DocumentEvent e) {
            searchChanged();
         }

         public void insertUpdate(DocumentEvent e) {
            searchChanged();
         }

         public void removeUpdate(DocumentEvent e) {
            searchChanged();
         }
      });

      gbc.gridy++;

      gbc.gridx = 0;
      top.add(mNameIsRegex, gbc);

      gbc.gridx = 0;
      gbc.weightx = 0;
      mMainPanel = top;
      mRenderer = new JLabel();
      mRenderer.setOpaque(true);
   }

   public JComponent getResultRendererComponent(Object result, boolean isSelected) {
      BindResult b = (BindResult) result;
      mRenderer.setText(b.toString());
      mRenderer.setBackground(isSelected ? GenericFindWindow.getDefaultSelectionBackgroundColor() : GenericFindWindow.getDefaultNonSelectionBackgroundColor());
      mRenderer.setForeground(isSelected ? GenericFindWindow.getDefaultSelectionForegroundColor() : GenericFindWindow.getDefaultNonSelectionForegroundColor());
      return mRenderer;
   }

   private static String computeName(Binding b) {
      if (b == null) {
         return "";
      }
      if (b instanceof TemplateBinding) {
         return "";
      }
      String localName;
      if (b instanceof ElementBinding) {
         if (b.getName() == null) {
            localName = "element";
         }
         else {
            localName = b.getName().getLocalName();
         }
      }
      else {
         if (b instanceof AttributeBinding) {
            if (b.getName() == null) {
               localName = "attribute";
            }
            else {
               localName = "@" + b.getName().getLocalName();
            }
         }
         else {
            localName = b.getName().getLocalName();
         }
      }
      String prn = computeName(b.getParent());
      if (prn.length() > 0) {
         return prn + "/" + localName;
      }
      else {
         return localName;
      }
   }


   /**
    * The closure structure.
    */
   private static class FindData {
      public String mName;
      public RE mNameRegex; // null if not a regex search, set to mName if is a regex search.
      public RESyntaxException mRegexError;
   }

   public JComponent getFindParametersPanel() {
      return mMainPanel;
   }

   public void setEditorChangeListener(ChangeListener cl) {
      mChangeListener = cl;
   }

   public JComponent getPrimaryEntryComponent() {
      return mFindText;
   }

   public Object getFindData() {
      FindData fd = new FindData();
      fd.mName = mFindText.getText();
      try {
         fd.mNameRegex = mNameIsRegex.isSelected() ? new RE(fd.mName) : null;
      }
      catch (RESyntaxException res) {
         fd.mRegexError = res;
      }
      return fd;
   }

   public String isDataSufficient(Object findData) {
      FindData fd = (FindData) findData;
      if (fd.mRegexError != null) {
         return "Invalid regular expression: " + fd.mRegexError.getMessage();
      }
      if (fd.mName.length() == 0) {
         return "Specify at least 1 character for search";
      }
      // Ok, it's sufficient:
      return null;
   }

   /**
    * Indicates if this finder has been set up for a global (full project) search or a local (single template) search.
    */
   private boolean isGlobal() {
      return mOptionalEditor == null;
   }

   private static class BindResult {
//      private final Binding mResult;
      private final String mName;

      public BindResult(Binding b) {
//         mResult = b;
         mName = computeName(b);
      }

      public String toString() {
         return mName;
      }
   }

   public void find(FindResultHandler toHandler, ObjectProvider objectProvider, String resourceLoc, Object findData) {
      if (!isGlobal()) {
         FindData fd = (FindData) findData;
         recompute(toHandler, fd, ((BindingNode) mOptionalEditor.getBindingTree().getRootNode()).getBinding(), false);
      }
      else {
         if (resourceLoc.endsWith(".xslt")) {
/* TEMPORARY COMMENT OUT BY JTB
            try {
               XsltStylesheetSharedResource sc = (XsltStylesheetSharedResource) objectProvider.getObject(resourceLoc, XsltStylesheetSharedResource.class, false);
               StylesheetBinding sb = sc.getStylesheetModel().getParsedXslt();
               if (sb != null) {
                  FindData fd = (FindData) findData;
                  recompute(toHandler, fd, sb, false);
               }
            }
            catch (ObjectRepoException ore) {
               ore.printStackTrace(System.err);
            }
*/
         }
      }
   }

   private void recompute(FindResultHandler toHandler, FindData fd, Binding at, boolean formulasOnly) {
      String f = at.getFormula();
      if (f != null) {
         if (matchString(f, fd.mName, fd.mNameRegex)) {
            toHandler.result(new BindResult(at));
         }
      }
      if (!formulasOnly) {
         if (at instanceof ElementBinding || at instanceof AttributeBinding) {
            ExpandedName n = at.getName();
            if (n != null) {
               if (matchString(n.getLocalName(), fd.mName, fd.mNameRegex)) {
                  toHandler.result(new BindResult(at));
               }
               if (matchString(n.getNamespaceURI(), fd.mName, fd.mNameRegex)) {
                  toHandler.result(new BindResult(at));
               }
            }
         }
         //WCETODO marker comments.
      }
      int cc = at.getChildCount();
      for (int i = 0; i < cc; i++) {
         recompute(toHandler, fd, at.getChild(i), formulasOnly);
      }
   }

   protected boolean matchString(String str, String matchTo, RE re) {
      if (str == null) {
         return false;
      }
      if (re != null) {
         return re.match(str);
      }
      if (str.indexOf(matchTo) >= 0) {
         return true;
      }
      return false;
   }

   protected void searchChanged() {
      if (mChangeListener != null) {
         mChangeListener.stateChanged(new ChangeEvent(this));
      }
   }

   public void selectResult(String displayResource, Object result) {
      if (isGlobal()) {
//         BindResult br = (BindResult) result;
/* TEMPORARY COMMENT OUT BY JTB
         XsltStylesheetSharedResource r = (XsltStylesheetSharedResource) displayResource;
         r.showNode(br.mResult);
*/
      }
   }

//   private void action(Object src) {
//      if (src == mSearchLeftSide) {
//         System.out.println("SLS");
//      }
//      if (src == mSearchRightSide) {
//         System.out.println("SRS");
//      }
//   }
}
