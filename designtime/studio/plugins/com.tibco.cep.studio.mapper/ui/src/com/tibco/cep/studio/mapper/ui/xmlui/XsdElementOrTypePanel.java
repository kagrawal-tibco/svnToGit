package com.tibco.cep.studio.mapper.ui.xmlui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.param.ParameterEditor;
import com.tibco.cep.studio.mapper.ui.data.param.SpecialTypeCategory;
import com.tibco.cep.studio.mapper.ui.data.param.TypeCategory;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * A xsd:type/xsd:element chooser panel (control).<br>
 * Allows the selection of an xsd:type or xsd:element.
 */
public class XsdElementOrTypePanel extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JPanel m_body;
   private JPanel m_radioArea;
   private JRadioButton m_type;
   private JRadioButton m_element;
   private int m_typeType = -1; // initialized in ctr.
   private UIAgent uiAgent;
   private XsdTypePanel m_types;
   private QNamePanel m_elements;
   private JRadioButton m_group;
   private QNamePanel m_groups; // allocated when required.
   private ButtonGroup m_buttonGroup;
   private ActionListener m_actionListener;
   private PropertyChangeListener m_propertyChangeListener;

   public static TypeCategory[] ALL_TYPES;

   static {
      ALL_TYPES = new TypeCategory[ParameterEditor.BUILT_IN_TYPES.length + 1];
      for (int i = 0; i < ParameterEditor.BUILT_IN_TYPES.length; i++) {
         ALL_TYPES[i] = ParameterEditor.BUILT_IN_TYPES[i];
      }
      ALL_TYPES[ParameterEditor.BUILT_IN_TYPES.length] = SpecialTypeCategory.INSTANCE;
   }

   /**
    * The java-bean property that can be used to listen for changes in this control.
    */
   public static final String VALUE_PROPERTY = "value";

   /**
    * The java-bean property that can be used to listen for changes in this control.
    */
   public static final String TYPE_PROPERTY = "type";

   public XsdElementOrTypePanel(UIAgent uiAgent) {
      this(uiAgent, null, null, ALL_TYPES);
   }

   public XsdElementOrTypePanel(UIAgent uiAgent,
                                NamespaceContextRegistry ni, ImportRegistry ir) {
      this(uiAgent, ni, ir, ALL_TYPES);
   }

   public XsdElementOrTypePanel(UIAgent uiAgent,
                                NamespaceContextRegistry ni, ImportRegistry ir, TypeCategory[] typeCats) {
      super(new BorderLayout());
      this.uiAgent = uiAgent;
      m_types = new XsdTypePanel(uiAgent, ni, ir, typeCats);
      m_elements = new QNamePanel(uiAgent, XsdQNamePlugin.ELEMENT, ni, ir);

      m_radioArea = new JPanel(new FlowLayout());
      m_type = new JRadioButton(DataIcons.getTypeLabel());
      m_element = new JRadioButton(DataIcons.getElementLabel());
      m_radioArea.add(m_type);
      m_radioArea.add(m_element);
      ButtonGroup bg = new ButtonGroup();
      m_buttonGroup = bg;
      JPanel spacer = new JPanel(new BorderLayout());
      spacer.add(m_radioArea, BorderLayout.WEST);
      bg.add(m_type);
      bg.add(m_element);
      m_type.setSelected(true);
      ActionListener al = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            typeOrElChanged();
         }
      };
      m_actionListener = al;
      m_type.addActionListener(al);
      m_element.addActionListener(al);
      PropertyChangeListener pcl = new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent evt) {
            fireNameChange();
         }
      };
      m_propertyChangeListener = pcl;
      m_elements.addPropertyChangeListener(QNamePanel.VALUE_PROPERTY, pcl);
      m_types.addPropertyChangeListener(XsdTypePanel.VALUE_PROPERTY, pcl);
      m_body = new JPanel(new BorderLayout());
      m_body.setBorder(BorderFactory.createLineBorder(Color.lightGray));
      add(spacer, BorderLayout.NORTH);
      add(m_body, BorderLayout.CENTER);

      // initialize w/ default.
      setIsType(true);
      setExpandedName(ExpandedName.makeName(XSDL.NAMESPACE, "string"));
   }

   public void setAllowsGroupRefs(boolean allowGroupRefs) {
      if (allowGroupRefs) {
         m_group = new JRadioButton(DataIcons.getModelGroupLabel());
         m_group.addActionListener(m_actionListener);
         m_group.addPropertyChangeListener(m_propertyChangeListener);
         m_groups = new QNamePanel(uiAgent, XsdQNamePlugin.MODEL_GROUP);
         m_groups.setImportRegistry(m_types.getImportRegistry());
         m_groups.setNamespaceImporter(m_types.getNamespaceImporter());
         m_radioArea.add(m_group);
         m_radioArea.revalidate();
         m_radioArea.repaint();
         m_buttonGroup.add(m_group);
      }
   }

   /**
    * Sets the import context for this qname pick.<br>
    * This is <b>mandatory</b> before doing any other calls such as {@link #setExpandedName}.
    * This may be called with different values, i.e. for forms recycling.
    *
    * @param importRegistry The import registry.
    */
   public void setImportRegistry(ImportRegistry importRegistry) {
      m_types.setImportRegistry(importRegistry);
      m_elements.setImportRegistry(importRegistry);
      if (m_groups != null) {
         m_groups.setImportRegistry(importRegistry);
      }
   }

   /**
    * If you want the import locations to be relative paths, then provide the
    * project relative path here, which the locations should be relative to...
    * <p/>
    * Sets the location in which this qname is used.<br>
    * By default, this is not set (null), but this is required
    * for use in a context where the references may be made within the file itself but not
    * necessarily in the same TargetNamespace -- for example, a WSDL may reference
    * types in embedded schemas that have different namespaces.
    * (WCETODO see comments on same method in QNamePanel}
    *
    * @param path The project relative path.
    */
   public void setContextProjectRelativePath(String path) {
      m_elements.setContextProjectRelativePath(path);
      m_types.setContextProjectRelativePath(path);
   }

   public String getContextProjectRelativePath() {
      return m_elements.getContextProjectRelativePath();
   }

   public ImportRegistry getImportRegistry() {
      return m_types.getImportRegistry();
   }


   /**
    * Sets the namespace context for this qname pick.<br>
    * This is <b>mandatory</b> before doing any other calls such as {@link #setExpandedName}.
    * This may be called with different values, i.e. for forms recycling.
    *
    * @param namespaceContextRegistry The import registry.
    */
   public void setNamespaceImporter(NamespaceContextRegistry namespaceContextRegistry) {
      m_types.setNamespaceImporter(namespaceContextRegistry);
      m_elements.setNamespaceImporter(namespaceContextRegistry);
   }

   public NamespaceContextRegistry getNamespaceImporter() {
      return m_types.getNamespaceImporter();
   }

   /**
    * Sets the value of the type (by expanded name).<br>
    * For a qname version, see {@link #setQName}.
    *
    * @param expandedName The expanded name.
    */
   public void setExpandedName(ExpandedName expandedName) {
      if (m_typeType == TYPE_TYPE) {
         m_types.setExpandedName(expandedName);
      }
      else {
         if (m_typeType == TYPE_ELEMENT) {
            m_elements.setExpandedName(expandedName);
         }
         else {
            // Group:
            m_groups.setExpandedName(expandedName);
         }
      }
   }

   /**
    * Gets the current value of the type (by expanded name).<br>
    * For a qname version, see {@link #getQName}.
    *
    * @return The name, never null.
    */
   public ExpandedName getExpandedName() {
      if (m_typeType == TYPE_TYPE) {
         return m_types.getExpandedName();
      }
      else {
         if (m_typeType == TYPE_ELEMENT) {
            return m_elements.getExpandedName();
         }
         else {
            if (m_typeType == TYPE_GROUP) {
               return m_groups.getExpandedName();
            }
            else {
               // at ctr, m_typeType will be -1, just return empty name for this.
               return ExpandedName.makeName("");
            }
         }
      }
   }

   /**
    * Sets the qname.<br>
    * The {@link ExpandedName} is computed from the qname using the {@link NamespaceContextRegistry}.
    * It will not crash'n'burn (i.e. throw exceptions, etc.) if the prefix doesn't exist,
    * but the exact behavior is 'private' to the control.
    *
    * @param qname The new qname, cannot be null.
    */
   public void setQName(QName qname) {
      String ns;
      try {
         ns = getNamespaceImporter().getNamespaceURIForPrefix(qname.getPrefix());
      }
      catch (Exception e) {
         // be fault tolerant
         ns = "";
      }
      ExpandedName ename = ExpandedName.makeName(ns, qname.getLocalName());
      setExpandedName(ename);
   }

   /**
    * Returns the qname from the full name and namespace.<br>
    * This is computed from {@link #getExpandedName} and {@link NamespaceContextRegistry} --- if the
    * namespace is not declared at the time of this call, a new declaration will be added using
    * {@link NamespaceContextRegistry#getOrAddPrefixForNamespaceURI} on the namespace.
    */
   public QName getQName() {
      // String location = getResourceLocation();
      // ugly, need to resolve this much much better.
      ExpandedName ename = getExpandedName();
      return NamespaceManipulationUtils.getOrAddQNameFromExpandedName(ename, getNamespaceImporter());
   }

   /**
    * Sets if this is picking a type (true) or element (false).
    *
    * @param isType True if is a type.
    */
   public void setIsType(boolean isType) {
      setIsTypeInternal(isType ? TYPE_TYPE : TYPE_ELEMENT);
   }

   /**
    * Same as {@link #setIsType} except allows picking {@link #TYPE_GROUP} in addition to {@link #TYPE_ELEMENT} and
    * {@link #TYPE_TYPE}.
    *
    * @param type
    */
   public void setReferenceType(int type) {
      setIsTypeInternal(type);
   }

   public int getReferenceType() {
      return m_typeType;
   }

   public static final int TYPE_TYPE = 0;
   public static final int TYPE_ELEMENT = 1;
   public static final int TYPE_GROUP = 2;

   private void setIsTypeInternal(int typeType) {
      if (typeType == m_typeType) {
         return;
      }
      ExpandedName ename = getExpandedName();
      m_typeType = typeType;
      setExpandedName(ename);
      if (typeType == TYPE_TYPE) {
         m_type.setSelected(true);
      }
      else {
         if (typeType == TYPE_ELEMENT) {
            m_element.setSelected(true);
         }
         else {
            m_group.setSelected(true);
         }
      }
      m_body.removeAll();
      if (typeType == TYPE_TYPE) {
         m_body.add(m_types);
      }
      else {
         if (typeType == TYPE_GROUP) {
            m_body.add(m_groups);
         }
         else {
            m_body.add(m_elements);
         }
      }
      m_body.revalidate();
      m_body.repaint();
   }

   public boolean getIsType() {
      return m_typeType == TYPE_TYPE;
   }

   /**
    * Implementation override - disabled component controls, too.
    */
   public void setEnabled(boolean enabled) {
      if (isEnabled() == enabled) {
         return;
      }
      super.setEnabled(enabled);
      m_types.setEnabled(enabled);
      m_elements.setEnabled(enabled);
      m_type.setEnabled(enabled);
      m_element.setEnabled(enabled);
      if (m_group != null) {
         m_group.setEnabled(enabled);
      }
   }

   public void setCategories(TypeCategory[] typeCats) {
      m_types.setCategories(typeCats);
   }

   private void typeOrElChanged() {
      boolean val = m_type.isSelected();
      if (val) {
         setIsTypeInternal(TYPE_TYPE);
      }
      else {
         if (m_element.isSelected()) {
            setIsTypeInternal(TYPE_ELEMENT);
         }
         else {
            setIsTypeInternal(TYPE_GROUP);
         }
      }

      // Kept as boolean for backward compat:
      super.firePropertyChange(TYPE_PROPERTY, !val, val);
   }

   private void fireNameChange() {
      super.firePropertyChange(VALUE_PROPERTY, null, getExpandedName());
   }
}
