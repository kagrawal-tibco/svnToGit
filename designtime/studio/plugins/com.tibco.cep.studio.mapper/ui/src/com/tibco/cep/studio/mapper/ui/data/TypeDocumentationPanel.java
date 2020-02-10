package com.tibco.cep.studio.mapper.ui.data;

import java.awt.BorderLayout;
import java.util.Iterator;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.param.ParameterEditorResources;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTreeNode;
import com.tibco.xml.schema.SmAnnotation;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.SmTypeFacet;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;
import com.tibco.xml.tns.parse.TnsError;
import com.tibco.xml.tns.parse.TnsErrorSeverity;

/**
 * A panel which contains an javadocish version of documentation for schema.
 */
public class TypeDocumentationPanel extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JScrollPane m_documentationScrollPane;
   private JEditorPane m_documentationEditor;
//   private UIAgent uiAgent;
   
   public TypeDocumentationPanel(UIAgent uiAgent) {
      super(new BorderLayout());
//      this.uiAgent = uiAgent;
      if (uiAgent == null) {
         throw new RuntimeException();
      }
      m_documentationScrollPane = new JScrollPane();
      add(m_documentationScrollPane);
   }

   /**
    * Clears the documentation display.
    */
   public void clear() {
      clearDocs();
   }

   /**
    * Displays documentation for the given type.
    *
    * @param type The type, if null, equivalent to {@link #clear}
    */
   public void set(SmSequenceType type) {
      if (type == null) {
         clear();
         return;
      }
      setupDocs(type);
   }

   private void setupDocs(SmSequenceType t) {
      StringBuffer docs = new StringBuffer();
      buildDocsHtml(t, docs);
      if (docs.length() == 0) {
         clearDocs();
      }
      else {
         setupDocs("<html>" + docs.toString());
      }
   }

   private void buildDocsHtml(SmSequenceType t, StringBuffer docs) {
      SmSequenceType pt = SmSequenceTypeSupport.stripOccursAndParens(t);
      SmCardinality xo = SmSequenceTypeSupport.stripParens(t).getOccurrence();
      if (xo == null) {
         xo = SmCardinality.EXACTLY_ONE;
      }
      setupOccurrence(xo, docs);
      buildDocsHtmlInternal(pt, docs);
   }

   private void setupOccurrence(SmCardinality xo, StringBuffer docs) {
      SmCardinality nxo = SmCardinality.create(xo.getMinOccurs(), xo.getMaxOccurs()); // remove 'known.
      if (nxo.equals(SmCardinality.EXACTLY_ONE)) {
         return;
      }
      if (nxo.equals(SmCardinality.OPTIONAL)) {
         docs.append(ParameterEditorResources.OCCURRENCE_OPTIONAL_WITH_PAREN);
      }
      else {
         if (nxo.equals(SmCardinality.AT_LEAST_ONE)) {
            docs.append(ParameterEditorResources.OCCURRENCE_AT_LEAST_ONE_WITH_PAREN);
         }
         else {
            if (nxo.equals(SmCardinality.REPEATING)) {
               docs.append(ParameterEditorResources.OCCURRENCE_REPEATING_WITH_PAREN);
            }
            else {
               // strange one:
               String min = ParameterEditorResources.OCCURRENCE_MIN;
               String max = ParameterEditorResources.OCCURRENCE_MAX;
               String mstr = nxo.getMaxOccurs() == SmParticle.UNBOUNDED ? ParameterEditorResources.OCCURRENCE_UNBOUNDED : "" + nxo.getMaxOccurs();
               docs.append(min + ": " + nxo.getMinOccurs() + " " + max + ": " + mstr);
            }
         }
      }
      docs.append("<br></br>");
   }

   private void buildDocsHtmlInternal(SmSequenceType t, StringBuffer docs) {
      if (t.getTypeCode() == SmSequenceType.TYPE_CODE_ELEMENT) {
         String elLabel = DataIcons.getElementLabel().toLowerCase();
         if (t.getName() == null) {
            appendHeaderText(elLabel, docs);
         }
         else {
            appendHeaderText(elLabel + " <b>" + t.getName().getLocalName() + "</b>", docs);
         }
      }
      if (t.getTypeCode() == SmSequenceType.TYPE_CODE_ATTRIBUTE) {
         if (t.getName() == null) {
            appendHeaderText(DataIcons.getAttributeLabel(), docs);
         }
         else {
            appendHeaderText(DataIcons.getAttributeLabel() + " " + t.getName().getLocalName(), docs);
         }
      }
      if (t.getTypeCode() == SmSequenceType.TYPE_CODE_CHOICE) {
         SmSequenceType[] choices = SmSequenceTypeSupport.getAllChoices(t);
         appendHeaderText(ParameterEditorResources.CHOICE_OF, docs);
         for (int i = 0; i < choices.length; i++) {
            //WCETODO will need to indent somehow.
            buildDocsHtmlInternal(choices[i], docs);
         }
      }
      //WCETODO other stuff....
      SmParticleTerm pat = t.getParticleTerm();
      if (pat != null) {
         buildDocHtml(pat.getAnnotation(), docs);
      }
      //WCETODO --- this probably should just grow into a full-fledged browser control, really....
      if (pat instanceof SmElement) {
         SmElement sub = ((SmElement) pat).getSubstitutionGroup();
         if (sub != null) {
            docs.append(ParameterEditorResources.SUBSTITUTION_GROUP + ": " + sub.getName());
            docs.append("<br></br>");
         }
      }
      addErrors(pat, docs);
      SmType type = SmSequenceTypeSupport.getActualElementType(t);

      String leader = ParameterEditorResources.IS_A + " ";
      while (type != null) {
         String tname = type.getName();
         if (tname == null) {
            tname = "";
         }
         if (XSDL.NAMESPACE.equals(type.getNamespace()) && (tname.equals("any") || tname.equals("anySimpleType") || tname.equals("anyType"))) {
            // don't bother showing anySimpleType, urType, etc.
            break;
         }
         if (tname.length() == 0) {
            appendHeaderText(leader + DataIcons.getTypeLabel().toLowerCase(), docs);
         }
         else {
            appendHeaderText(leader + DataIcons.getTypeLabel().toLowerCase() + " <b>" + tname + "</b>", docs);
         }
         buildDocHtml(type.getAnnotation(), docs);
         // If it has enums, show these:
         buildDocEnums(type, docs);

         addErrors(type, docs);

         // Don't show hierarchy of xsd schema, kind of pointless, treat as primitives.
         if (XSDL.NAMESPACE.equals(type.getNamespace())) {
            break;
         }

         type = type.getBaseType();
         leader = ParameterEditorResources.EXTENDS + " "; // WCETODO add extends versus restricts.
      }
   }

   private void appendHeaderText(String header, StringBuffer docs) {
      docs.append("<font color='blue'>" + header + "</font><br></br>");
   }

   private void setupDocs(String htmlString) {
      if (m_documentationEditor == null) {
         m_documentationEditor = new JEditorPane();
         m_documentationEditor.setContentType("text/html");
         ;
         m_documentationEditor.setEditable(false);
      }
      m_documentationEditor.setText(htmlString);
      m_documentationScrollPane.getViewport().setView(m_documentationEditor);
   }

   @SuppressWarnings("rawtypes")
private static void addErrors(SmComponent component, StringBuffer docs) {
      if (component == null) {
         return;
      }
      Iterator errs = component.getErrors(TnsErrorSeverity.WARNING, false); // What the heck, get 'em all, bring 'em on!
      while (errs.hasNext()) {
         TnsError e = (TnsError) errs.next();
         docs.append("<code>" + e.getMessage() + "</code><br/>");
      }
   }

   private static final ExpandedName DOC_ELEMENT = ExpandedName.makeName(XSDL.NAMESPACE, "documentation");

   @SuppressWarnings("rawtypes")
private void buildDocHtml(SmAnnotation annotation, StringBuffer htmlBuf) {
      if (annotation == null) {
         return;
      }
      Iterator i = annotation.getDocumentation();
      while (i.hasNext()) {
         XmlTreeNode tn = (XmlTreeNode) i.next();
         buildDocHtml(tn, htmlBuf);
         htmlBuf.append("<br></br>");
      }
   }

   private void buildDocEnums(SmType t, StringBuffer htmlBuf) {
      SmTypeFacet f = t.getFacet(SmTypeFacet.FACET_ENUM_NAME);
      if (f == null) {
         return;
      }
      f.getAnnotation();
      htmlBuf.append("<i>" + ParameterEditorResources.ENUMERATIONS + "</i>");
      htmlBuf.append(f.getStringValue());
      htmlBuf.append("<br></br>");
   }

   private void buildDocHtml(XmlTreeNode node, StringBuffer htmlBuf) {
      if (DOC_ELEMENT.equals(node.getName())) {
         buildDocumentationNodeHtml(node, htmlBuf);
         return;
      }
      //WCETODO handle these (need example?)
      // (This is an internal error situation; should not be localized)
      htmlBuf.append("Node: " + node.getName());
   }

   private void buildDocumentationNodeHtml(XmlTreeNode doc, StringBuffer htmlBuf) {
      // try just taking String value of it for now.
      String val = doc.getStringValue();
      htmlBuf.append(val);//WCETODO encode.
   }

   private void clearDocs() {
      m_documentationScrollPane.getViewport().setView(new JLabel());
   }
}
