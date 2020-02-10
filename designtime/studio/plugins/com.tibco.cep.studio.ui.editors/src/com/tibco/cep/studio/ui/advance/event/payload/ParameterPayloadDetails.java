/**
 * 
 */
package com.tibco.cep.studio.ui.advance.event.payload;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.DocumentNameValidator;
import com.tibco.cep.studio.mapper.ui.data.utils.NameValidatingDocument;

/**
 * @author mgujrath
 *
 */
public class ParameterPayloadDetails {
	
	private UIAgent uiAgent;
	private final ParameterPayloadEditor mEditor;
	private boolean mReading;
	private ContentModelCategory[] m_contentModelCategories;
	private TypeCategory[] m_typeCategories;
	private boolean mRootTypeEditable = true;
	private boolean mAllowsRepeating = true;
	private String m_originalName;

	private boolean m_editable = true;

	// 1-12XN1X -- need to avoid causing events while initializing
	private boolean adjusting = false;
	
	public ParameterPayloadDetails(ParameterPayloadEditor editor, UIAgent uiAgent,
			ContentModelCategory[] allCats, TypeCategory[] typeCats) {
		this.uiAgent = uiAgent;

		m_contentModelCategories = allCats;
		m_typeCategories = typeCats;
		mEditor = editor;
		
/*		JComponent cardinality = buildCardinality();
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
		mNameFieldDocument = new NameValidatingDocument(
				XMLNameDocument.VALIDATOR); // initialize with the XML name
											// validator.
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
		m_contentModelTypeComboLabel = new JLabel(
				ParameterEditorResources.CONTENT_MODEL + ":");
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
		nameAndCard.add(
				new JLabel(ParameterEditorResources.CARDINALITY + ":  "), gbc);
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
		m_DefaultValueFieldLabel = new JLabel(
				ParameterEditorResources.DEFAULTVALUENAME);
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
		mDefaultValueField.getDocument().addDocumentListener(
				new DocumentListener() {
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
*/	}
	
	private NameValidatingDocument mNameFieldDocument;

	public void setDocumentNameValidator(DocumentNameValidator nameValidator) {
		mNameFieldDocument.setDocumentNameValidator(nameValidator);
	}

	public DocumentNameValidator getDocumentNameValidator() {
		return mNameFieldDocument.getDocumentNameValidator();
	}
	
/*	 private void fillInContentModelCategories() {
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
*/
/*	   private void fillInTypeCategories() {
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
*/
}
