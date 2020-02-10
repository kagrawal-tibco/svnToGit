package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.CommentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

/**
 * The panel for representing XML comments in the XSLT editor.
 */
public class SourceCommentPanel implements StatementPanel {
   public SourceCommentPanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new CommentBinding("");
   }

   public Class<CommentBinding> getHandlesBindingClass() {
      return CommentBinding.class;
   }

   public Binding getDefaultAddAroundBinding() {
      return null;
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      // no children allowed!
      return false;
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return null;
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_COMMENT;
   }

   public String getDisplayName() {
      return "Comment";
   }

   public String getDisplayNameFor(TemplateReport templateReport) {
      return "[" + getDisplayName() + "]";
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getSourceCommentIcon();
   }

   public boolean isAvailable(SmSequenceType term) {
      return true; // always available.
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return true;
   }
}

