package com.tibco.cep.studio.mapper.ui.data.param;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.basic.DetailsViewFactory;
import com.tibco.cep.studio.mapper.ui.data.utils.DocumentNameValidator;
import com.tibco.cep.studio.mapper.ui.data.utils.NameValidatingDocument;
import com.tibco.cep.studio.mapper.ui.data.utils.XMLNameDocument;
import com.tibco.xml.schema.SmParticle;

/**
 * Purposefully package private.
 * The details panel of the type tree editor.
 */
class ParameterDetails extends JPanel implements DetailsViewFactory {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private final ParameterEditor mEditor;
   private JTextField mNameField;
   private JLabel mNameLabel;
   private NameValidatingDocument mNameFieldDocument; // The document set into the above name field.
   private JComboBox m_cardinalityCombo;
   private JComboBox m_contentModelTypeCombo = new JComboBox();
   private JLabel m_contentModelTypeComboLabel;
   private JLabel m_typeTypeLabel;
   private JComboBox m_typeTypeCombo = new JComboBox();
   private JPanel mTypeDetails;
   private JComponent mCurrentDetails;
   private JLabel m_DefaultValueFieldLabel;
   private JTextField mDefaultValueField;
   private boolean mReading;
   private ContentModelCategory[] m_contentModelCategories;
   private TypeCategory[] m_typeCategories;
   private UIAgent uiAgent;
   private boolean mRootTypeEditable = true;
   private boolean mAllowsRepeating = true;
   private String m_originalName;

   private boolean m_editable = true;

   // 1-12XN1X -- need to avoid causing events while initializing
   private boolean adjusting = false;

   /**
    * If the editor is configured to allow only a fixed cardinality, this is = to the CARDINALITY_COMBO_XXX index of it,
    * or -1 to indicate not fixed.
    */
   private int mFixedCardinality = -1;

   private ParameterNode mNode;

   protected ActionListener mInternalListeners = new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
         if (adjusting) {
            return;
         }
         action(ae);
      }
   };

   public ParameterDetails(ParameterEditor editor, UIAgent uiAgent,
                           ContentModelCategory[] allCats, TypeCategory[] typeCats) {
      super(new BorderLayout());
      this.uiAgent = uiAgent;

      m_contentModelCategories = allCats;
      m_typeCategories = typeCats;
      mEditor = editor;
      JComponent cardinality = buildCardinality();
      fillInContentModelCategories();
      fillInTypeCategories();

      mNameField = new JTextField();
      mNameField.addFocusListener(new FocusListener() {
         public void focusGained(FocusEvent e) {
         }

         public void focusLost(FocusEvent e) {
            checkRename();
         }
      });
      mNameFieldDocument = new NameValidatingDocument(XMLNameDocument.VALIDATOR); // initialize with the XML name validator.
      mNameField.setDocument(mNameFieldDocument);
      mNameField.addActionListener(mInternalListeners);
      mNameField.getDocument().addDocumentListener(new DocumentListener() {
         public void changedUpdate(DocumentEvent de) {
            nameChanged();
         }

         public void removeUpdate(DocumentEvent de) {
            nameChanged();
         }

         public void insertUpdate(DocumentEvent de) {
            nameChanged();
         }
      });
      JPanel nameAndCard = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.BOTH;
      gbc.insets = new Insets(4, 4, 4, 4);
      gbc.weightx = 0;
      gbc.weighty = 0;

      gbc.weightx = 0;
      gbc.gridx = 0;
      gbc.gridy = 0;
      m_contentModelTypeComboLabel = new JLabel(ParameterEditorResources.CONTENT_MODEL + ":");
      nameAndCard.add(m_contentModelTypeComboLabel, gbc);
      gbc.gridx = 1;
      JPanel typePanel = new JPanel(new BorderLayout());
      typePanel.add(m_contentModelTypeCombo, BorderLayout.WEST);
      nameAndCard.add(typePanel, gbc);

      gbc.gridx = 0;
      gbc.gridy++;
      mNameLabel = new JLabel(ParameterEditorResources.NAME + ":");
      nameAndCard.add(mNameLabel, gbc);
      gbc.gridx = 1;
      gbc.gridwidth = 2;
      nameAndCard.add(mNameField, gbc);
      gbc.gridwidth = 1;
      gbc.gridy++;
      gbc.gridx = 0;
      nameAndCard.add(new JLabel(ParameterEditorResources.CARDINALITY + ":  "), gbc);
      gbc.gridx = 1;
      nameAndCard.add(cardinality, gbc);

      gbc.gridx = 0;
      gbc.gridy++;
      m_typeTypeLabel = new JLabel(DataIcons.getTypeLabel() + ":");
      nameAndCard.add(m_typeTypeLabel, gbc);
      gbc.gridx = 1;
      nameAndCard.add(m_typeTypeCombo, gbc);

      gbc.gridy++;
      gbc.gridx = 0;
      m_DefaultValueFieldLabel = new JLabel(ParameterEditorResources.DEFAULTVALUENAME);
      nameAndCard.add(m_DefaultValueFieldLabel, gbc);
      gbc.gridx = 1;
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      mDefaultValueField = new JTextField();
      mDefaultValueField.addFocusListener(new FocusListener() {
         public void focusGained(FocusEvent e) {
         }

         public void focusLost(FocusEvent e) {
            updateDefaultValueToNode();
         }
      });
      mDefaultValueField.getDocument().addDocumentListener(new DocumentListener() {
         public void changedUpdate(DocumentEvent de) {
            updateDefaultValueToNode();
         }

         public void removeUpdate(DocumentEvent de) {
            updateDefaultValueToNode();
         }

         public void insertUpdate(DocumentEvent de) {
            updateDefaultValueToNode();
         }
      });
      nameAndCard.add(mDefaultValueField, gbc);

      gbc.gridy++;
      gbc.weightx = 1;
      gbc.gridx = 2;
      nameAndCard.add(new JLabel(), gbc); // spacer


      setBorder(BorderFactory.createEmptyBorder(0, 6, 5, 0));
      JPanel topPanel = new JPanel(new BorderLayout());
      topPanel.add(nameAndCard, BorderLayout.NORTH);
      add(topPanel, BorderLayout.NORTH);

      JPanel catAndType = new JPanel(new BorderLayout());
      mTypeDetails = new JPanel(new BorderLayout());
      catAndType.add(new JSeparator(), BorderLayout.NORTH);
      catAndType.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
      catAndType.add(mTypeDetails, BorderLayout.CENTER);

      add(catAndType, BorderLayout.CENTER);

      // initialize.
      read();
   }

   public void setContentModelTypeInvisible() {
      m_contentModelTypeComboLabel.setVisible(false);
      m_contentModelTypeCombo.setVisible(false);
   }

   public void setDocumentForNameField(Document doc) {
      mNameField.setDocument(doc);

      mNameField.getDocument().addDocumentListener(new DocumentListener() {
         public void changedUpdate(DocumentEvent de) {
            nameChanged();
         }

         public void removeUpdate(DocumentEvent de) {
            nameChanged();
         }

         public void insertUpdate(DocumentEvent de) {
            nameChanged();
         }
      });
   }

   static class ContentModelRenderer extends DefaultListCellRenderer {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean sel, boolean foc) {
         // On Unix, a null value is passed when schema editor
         // is in initial empty state;
         // to avoid an exception, return immediately if value is null.
         if (value == null) {
            return this;
         }
         ContentModelCategory cat = (ContentModelCategory) value;
         String name = cat.getDisplayName();
         super.getListCellRendererComponent(list, name, index, sel, foc);
         setIcon(cat.getDisplayIcon(null));
         return this;
      }
   }

   static class TypeTypeRenderer extends DefaultListCellRenderer {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean sel, boolean foc) {
         // On Unix, a null value is passed when schema editor
         // is in initial empty state;
         // to avoid an exception, return immediately if value is null.
         if (value == null) {
            return this;
         }
         TypeCategory cat = (TypeCategory) value;
         String name = cat.getDisplayName();
         super.getListCellRendererComponent(list, name, index, sel, foc);
         setIcon(cat.getDisplayIcon());
         return this;
      }
   }

   private void fillInContentModelCategories() {
      if (m_contentModelCategories == null) {
         return;
      }
      adjusting = true;
      m_contentModelTypeCombo.removeAllItems();

      for (int i = 0; i < m_contentModelCategories.length; i++) {
         m_contentModelTypeCombo.addItem(m_contentModelCategories[i]);
      }
      m_contentModelTypeCombo.setRenderer(new ContentModelRenderer());
      m_contentModelTypeCombo.addActionListener(mInternalListeners);
      m_contentModelTypeCombo.setMaximumRowCount(m_contentModelCategories.length + 2); //?
      m_contentModelTypeCombo.setLightWeightPopupEnabled(false); // only use these otherwise can go off window...
      adjusting = false;
   }

   private void fillInTypeCategories() {
      if (m_typeCategories == null) {
         return;
      }
      adjusting = true;
      m_typeTypeCombo.removeAllItems();

      for (int i = 0; i < m_typeCategories.length; i++) {
         m_typeTypeCombo.addItem(m_typeCategories[i]);
      }
      m_typeTypeCombo.setRenderer(new TypeTypeRenderer());
      m_typeTypeCombo.addActionListener(mInternalListeners);
      m_typeTypeCombo.setMaximumRowCount(m_typeCategories.length + 2); //?
      m_typeTypeCombo.setLightWeightPopupEnabled(false); // only use these otherwise can go off window...
      adjusting = false;
   }

   public void setTypeCategory(TypeCategory[] typeCategory) {
      m_typeCategories = typeCategory;
      fillInTypeCategories();
   }

   private JComponent buildCardinality() {
      JComboBox jcb = new JComboBox();
      jcb.addActionListener(mInternalListeners);
      m_cardinalityCombo = jcb;
      refillCardinalityCombo();

      return jcb;
   }

   /**
    * Fills in the combo with the allowed items.
    */
   private void refillCardinalityCombo() {
      if (m_cardinalityCombo == null) {
         return;
      }
      adjusting = true;
      m_cardinalityCombo.removeAllItems();
      m_cardinalityCombo.addItem(ParameterEditorResources.OCCURRENCE_REQUIRED);
      m_cardinalityCombo.addItem(ParameterEditorResources.OCCURRENCE_OPTIONAL_WITH_PAREN);
      if (mNode != null) {
         if (mAllowsRepeating) {
            if (mNode.getContentModelCategory().canRepeat()) {
               m_cardinalityCombo.addItem(ParameterEditorResources.OCCURRENCE_REPEATING_WITH_PAREN);
               m_cardinalityCombo.addItem(ParameterEditorResources.OCCURRENCE_AT_LEAST_ONE_WITH_PAREN);
            }
         }
      }
      adjusting = false;
   }

   public void setCategories(ContentModelCategory[] allcats, TypeCategory[] typeCats) {
      m_contentModelCategories = allcats;
      m_typeCategories = typeCats;
      fillInContentModelCategories();
      fillInTypeCategories();
   }

   public void setEditable(boolean editable) {
      if (editable == m_editable) {
         return;
      }
      m_editable = editable;
      read(); // refresh.
   }

   private void setNode(ParameterNode node) {
      /*      if (mNode==node)
            {
                return;
            }       */
      checkRename();
      mNode = node;
      read();
      repaint();
   }

   public JComponent getComponentForNode(Object node) {
      setNode((ParameterNode) node);
      return this;
   }

   private void checkRename() {
      if (mNode != null && !m_originalName.equals(mNode.getName())) {
         if (!mEditor.hasContentModelChangeListeners()) {
            return; // cut back on snapshots.
         }
         /* No more snapshots for now...SmParticleTerm now = mEditor.getParameterTree().snapshot();
         if (now!=null)
         {
             TreePath path = mNode.getTreePath();
             XsdContentModelPath cmpath = mEditor.getParameterTree().getContentModelPathForTreePath(path);
             ExpandedName oldName = ExpandedName.makeName(m_originalName);
             ExpandedName newName = ExpandedName.makeName(mNode.getName());
             XsdContentModelChangeEvent ce = new XsdContentModelChangeEvent(now,cmpath,oldName,newName);
             mEditor.getMasterChangeListener().contentModelChange(ce);
             m_originalName = mNode.getName();
         }*/
      }
   }

   private void action(ActionEvent ae) {
      Object src = ae.getSource();
      if (src == m_contentModelTypeCombo) {
         if (!mReading) {
            contentModelTypeChanged();
            m_contentModelTypeCombo.requestFocus(); // otherwise somehow loses it...
         }
      }
      if (src == m_typeTypeCombo) {
         if (!mReading) {
            typeTypeChanged();
         }
      }
      if (src == m_cardinalityCombo && !mReading) {
         int i = m_cardinalityCombo.getSelectedIndex();
         switch (i) {
            case ParameterEditor.CARDINALITY_REQUIRED:
               setCardinality(1, 1);
               break;
            case ParameterEditor.CARDINALITY_OPTIONAL:
               setCardinality(0, 1);
               break;
            case ParameterEditor.CARDINALITY_REPEATING:
               setCardinality(0, SmParticle.UNBOUNDED);
               break;
            default:
               setCardinality(1, SmParticle.UNBOUNDED);
               break;
         }
      }

   }

   private void setCardinality(int min, int max) {
      if (mNode == null) {
         return;
      }
      if (mNode.getMin() == min && mNode.getMax() == max) {
         // no change.
         return;
      }
      mNode.setMin(min);
      mNode.setMax(max);
      mEditor.repaint();
   }

   public void setRootTypeEditable(boolean rootType) {
      mRootTypeEditable = rootType;
   }

   private void contentModelTypeChanged() {
      if (mNode == null) {
         return;
      }
      ContentModelCategory tc = (ContentModelCategory) m_contentModelTypeCombo.getSelectedItem();
      if (tc != null) {
         Object details = tc.createDetails(mNode);
         mNode.setContentModelDetailsNoUpdate(details); // hacky, but don't want 2 updates here, just 1
         mNode.setContentModelCategoryUpdate(tc); // ... forces an update.
         read();
      }
   }

   private void typeTypeChanged() {
      if (mNode == null) {
         return;
      }
      TypeCategory tc = (TypeCategory) m_typeTypeCombo.getSelectedItem();
      if (tc != null) {
         detailsChanged();
         mNode.setContentModelDetailsNoUpdate(tc.createDetails());
         mNode.setTypeCategory(tc); // ... forces an update.
         read();
      }
   }

   private void nameChanged() {
      if (mReading) { // caused by a setText, not by user editing.
         return;
      }
      if (!mNameField.isEditable()) {
         return;
      }
      String text = mNameField.getText();
      if (mNode == null) {
         return;
      }
      if (text.equals(mNode.getName())) {
         // no change.
         return;
      }
      String newName = text;
      nameChanged(newName);
   }

   private void nameChanged(String newName) {
      if (!mNode.isNameEditable()) {
         return;
      }
      String uniqueName = mNode.uniqueifyName(newName);
      mNode.setName(uniqueName);
      mEditor.repaint();
   }

   private void detailsChanged() {
      if (mNode == null) {
         return;
      }
      if (mCurrentDetails == null) {
         return;
      }
      Object details = mNode.getContentModelCategory().getEditorValue(mNode, mCurrentDetails);
      mNode.setContentModelDetails(details);
      mEditor.repaint();
   }

   void read() {
      if (mReading) {
         return;
      }
      mReading = true;
      try {
         read1();
      }
      finally {
         mReading = false;
      }
   }

   private void read1() {
      boolean hasNode = mNode != null;
      mNameField.setEnabled(hasNode);
      mNameField.setEditable(hasNode && m_editable);
      boolean showName = mNode == null || !mNode.getFixedName();
      mNameField.setVisible(showName);
      mNameLabel.setVisible(showName);

      if (hasNode) {
         mNameField.setText(mNode.getName());
      }
      else {
         mNameField.setText("");
      }
      m_originalName = mNameField.getText(); // remember original name.
      updateAvailableChoices();
      refillCardinalityCombo();
      if (hasNode) {
         ContentModelCategory tc = mNode.getContentModelCategory();
         m_contentModelTypeCombo.setSelectedItem(tc);
         if (mNode.getParent() == null) {
            m_cardinalityCombo.setEnabled(false);
            mDefaultValueField.setEnabled(false);
            m_cardinalityCombo.setSelectedIndex(0); // required.
            m_contentModelTypeCombo.setEnabled(mRootTypeEditable && m_editable);
         }
         else {
            if (mFixedCardinality != -1) {
               m_cardinalityCombo.setEnabled(false);
            }
            else {
               m_cardinalityCombo.setEnabled(m_editable);
            }

            m_contentModelTypeCombo.setEnabled(m_editable);
            mDefaultValueField.setEnabled(m_editable);
         }
         TypeCategory ttc = mNode.getTypeCategory();
         m_typeTypeCombo.setSelectedItem(ttc);
         if (ttc != m_typeTypeCombo.getSelectedItem()) {
            // In case this happens again:
            System.err.println("Internal error: Node type not in list: " + ttc.getClass().getName());
         }
      }
      else {
         m_contentModelTypeCombo.setEnabled(false);
         m_contentModelTypeCombo.setSelectedIndex(-1);
         m_cardinalityCombo.setSelectedIndex(-1);
         m_cardinalityCombo.setEnabled(false);
         mDefaultValueField.setEnabled(false);
      }
      if (hasNode) {
         if (mFixedCardinality != -1) {
            m_cardinalityCombo.setSelectedIndex(mFixedCardinality);
         }
         else {
            if (mNode.getMin() == 0) {
               if (mNode.getMax() == 1 && m_cardinalityCombo.getModel().getSize() > 1) {
                  m_cardinalityCombo.setSelectedIndex(ParameterEditor.CARDINALITY_OPTIONAL);
               }
               else if (m_cardinalityCombo.getModel().getSize() > 2) {
                  m_cardinalityCombo.setSelectedIndex(ParameterEditor.CARDINALITY_REPEATING);
               }
            }
            else {
               if (mNode.getMax() == 1) {
                  m_cardinalityCombo.setSelectedIndex(ParameterEditor.CARDINALITY_REQUIRED);
               }
               else {
                  m_cardinalityCombo.setSelectedIndex(ParameterEditor.CARDINALITY_AT_LEAST_ONE);
               }
            }
         }
      }
      updateContentModelTypeDetails();
      updateTypeVisibility();
      updateNameVisibility();
      updateDefaultValue();
   }

   private void updateDefaultValueToNode() {
      if (mReading) { // caused by a setText, not by user editing.
         return;
      }
      if (mEditor.getAllowsDefaultValue()) {
         if (mDefaultValueField.getText() != null) {
            mNode.setDefaultValue(mDefaultValueField.getText());
         }
      }
      mEditor.repaint();
   }

   private void updateDefaultValue() {
      if (mEditor.getAllowsDefaultValue()) {
         if ((mNode != null) && mNode.getDefaultValue() != null) {
            mDefaultValueField.setText(mNode.getDefaultValue());
         }
         else {
            mDefaultValueField.setText("");
         }
      }
   }

   private void updateAvailableChoices() {
      adjusting = true;
      m_contentModelTypeCombo.removeAllItems();
      boolean isRoot = mNode == null ? true : mNode.getParent() == null;
      for (int i = 0; i < m_contentModelCategories.length; i++) {
         ContentModelCategory cmc = m_contentModelCategories[i];
         if (!mEditor.getAllowsExternalReferences() && (cmc instanceof ElementRefCategory || cmc instanceof GroupRefCategory)) {
            continue;
         }
         if (!mEditor.getAllowsNestedComplex() && (cmc instanceof SequenceCategory || cmc instanceof ElementSequenceCategory || cmc instanceof AllGroupCategory || cmc instanceof ChoiceCategory || cmc instanceof WildcardCategory)) {
            continue;
         }
         if (isRoot && !mEditor.getAllowsRootSequence() && (cmc instanceof SequenceCategory || cmc instanceof AllGroupCategory)) {
            continue;
         }
         if (!mEditor.getAllowsAttributes() && (cmc instanceof AttributeTypeRefCategory)) {
            continue;
         }
         m_contentModelTypeCombo.addItem(cmc);
      }
      adjusting = false;
   }

   private void updateNameVisibility() {
      boolean nameVisible = true;
      if (mNode != null) {
         ContentModelCategory tc = mNode.getContentModelCategory();
         nameVisible = !mNode.getFixedName() && (tc.canHaveName() || (mEditor.getNamedVariablesMode() && mNode.isFirstLevelNode()));
      }
      if (!nameVisible || mNode == null) {
         mNameField.setEditable(false);
         mNameField.setVisible(false);
         mNameLabel.setVisible(false);
      }
      else {
         mNameField.setVisible(true);
         mNameField.setEditable(m_editable);
         mNameLabel.setVisible(true);
      }
   }

   private void updateTypeVisibility() {
      boolean typeVisible = false;
      if (mNode != null) {
         ContentModelCategory tc = mNode.getContentModelCategory();
         typeVisible = tc.hasType();
      }
      if (!typeVisible) {
         m_typeTypeCombo.setVisible(false);
         m_typeTypeLabel.setVisible(false);
      }
      else {
         m_typeTypeCombo.setVisible(true);
         m_typeTypeLabel.setVisible(true);
         m_typeTypeCombo.setEnabled(m_editable);
      }
      boolean defaultVisible = mEditor.getAllowsDefaultValue();
      if (defaultVisible) {
         m_DefaultValueFieldLabel.setVisible(true);
         mDefaultValueField.setVisible(true);
      }
      else {
         m_DefaultValueFieldLabel.setVisible(false);
         mDefaultValueField.setVisible(false);
      }
   }

   private void updateContentModelTypeDetails() {
      adjusting = true;
      mTypeDetails.removeAll();
      if (mNode == null) {
         mTypeDetails.revalidate();
         mTypeDetails.repaint();
         adjusting = false;
         return;
      }
      ContentModelCategory tc = mNode.getContentModelCategory();
      ChangeListener cl = new ChangeListener() {
         public void stateChanged(ChangeEvent ce) {
            detailsChanged();
         }
      };
      Object td = mNode.getContentModelDetails();
      if (td == null) {
         mNode.setContentModelDetails(tc.createDetails(mNode));
         td = mNode.getContentModelDetails();
      }
      JComponent c = tc.createEditor(mNode, cl, td, uiAgent,
                                     mEditor.getNamespaceImporter(), mEditor.getImportRegistry());
      mCurrentDetails = c;
      if (c == null) {
         mTypeDetails.revalidate();
         mTypeDetails.repaint();
         adjusting = false;
         return;
      }
      c.setEnabled(m_editable);
      JScrollPane jsp = new JScrollPane(c);
      jsp.setBorder(BorderFactory.createEmptyBorder());
      mTypeDetails.add(jsp, BorderLayout.CENTER);
      mTypeDetails.revalidate();
      mTypeDetails.repaint();
      adjusting = false;
   }

   /**
    * Sets the fixed cardinality code, or -1 for not fixed.
    */
   public void setAllowsOnlyCardinality(int cardinalityCode) {
      mFixedCardinality = cardinalityCode;
      if (cardinalityCode == -1) {
         m_cardinalityCombo.setEnabled(true);
      }
      else {
         m_cardinalityCombo.setEnabled(false);
         m_cardinalityCombo.setSelectedIndex(cardinalityCode);
      }
   }

   public void setAllowsDefaultValue(boolean defaultVisible) {
      if (defaultVisible) {
         m_DefaultValueFieldLabel.setVisible(true);
         mDefaultValueField.setVisible(true);
      }
      else {
         m_DefaultValueFieldLabel.setVisible(false);
         mDefaultValueField.setVisible(false);
      }
   }

   /**
    * Sets the fixed cardinality code, or -1 for not fixed.
    */
   public int getAllowsOnlyCardinality() {
      return mFixedCardinality;
   }

   /**
    * Sets if repeating (including at-least-one) is allowed in the picker.
    *
    * @param allowsRepeating
    */
   public void setAllowsRepeatingCardinality(boolean allowsRepeating) {
      if (mAllowsRepeating != allowsRepeating) {
         mAllowsRepeating = allowsRepeating;
         // change this to have only the right stuff.
         refillCardinalityCombo();
      }
   }

   public boolean getAllowsRepeatingCardinality() {
      return mAllowsRepeating;
   }

   public void setDocumentNameValidator(DocumentNameValidator nameValidator) {
      mNameFieldDocument.setDocumentNameValidator(nameValidator);
   }

   public DocumentNameValidator getDocumentNameValidator() {
      return mNameFieldDocument.getDocumentNameValidator();
   }

   public void readPreferences(JComponent component, String keyprefix, UIAgent prefWriter) {
      // n/a yet.
   }

   public void writePreferences(JComponent component, String keyprefix, UIAgent prefWriter) {
      // n/a yet.
   }
}
