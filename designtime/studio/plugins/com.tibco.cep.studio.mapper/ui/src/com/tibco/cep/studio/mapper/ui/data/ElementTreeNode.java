package com.tibco.cep.studio.mapper.ui.data;

import java.io.StringWriter;
import java.util.Iterator;

import javax.swing.Icon;

import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmException;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableModelGroup;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;
import com.tibco.xml.tns.cache.TnsDocumentProvider;
import com.tibco.xml.tns.parse.TnsError;
import com.tibco.xml.tns.parse.TnsErrorSeverity;
import com.tibco.xml.util.NamespaceCounter;
import com.tibco.xml.util.XSDWriter;

/**
 * The {@link DataTypeTreeNode} which displays xml elements.
 */
public class ElementTreeNode extends DataTypeTreeNode {
   private final SmSequenceType mElement;
   private final String mName;
   private final Icon mIcon;
   private final boolean mIsLeaf;
   private final SmCardinality mOccurrence;
   private final boolean m_isNillable;
   private final boolean m_nillabilityKnown;
   private final String m_srcXPathIfCoerced;
   private String m_error;

   private static final ExpandedName ALL = ExpandedName.makeName("*", "*");

   @SuppressWarnings("rawtypes")
public ElementTreeNode(SmSequenceType element, SmCardinality occurs, String srcXpathIfCoerced) {
      super(occurs);
      mElement = element;
      mIsLeaf = isLeaf(element);
      mName = DataIcons.getName(element);
      mIcon = DataIcons.getIcon(element);
      mOccurrence = occurs;
      m_isNillable = mElement.isNillable();
      m_nillabilityKnown = mElement.isNillabilityKnown();
      m_srcXPathIfCoerced = srcXpathIfCoerced;
      if (mElement.getParticleTerm() instanceof SmElement) {
         SmElement pt = (SmElement) mElement.getParticleTerm();
         Iterator errs = pt.getErrors(TnsErrorSeverity.ERROR, false);
         while (errs.hasNext()) {
            TnsError e = (TnsError) errs.next();
            try {
               m_error = e.getMessage();
            }
            catch (Throwable t) {
               // Be somewhat resilient:
               t.printStackTrace(System.err);
               m_error = "Internal error getting message: " + t.getMessage();
            }
            break;
         }
         if (m_error == null) {
            // check the type:
            SmType type = SmSequenceTypeSupport.getActualElementType(element);
            if (type == null) {
               m_error = "<internal error: null type>"; // robust bug display.
            }
            else {
               //WCETODO this doesn't ever seem to get tripped, check on it.
               Iterator terrs = type.getErrors(TnsErrorSeverity.ERROR, false);
               while (terrs.hasNext()) {
                  TnsError e = (TnsError) terrs.next();
                  m_error = e.getMessage();
                  break;
               }
               /*if (m_error==null)
               {
                   if (type.getContentModel()!=null)
                   {
                       System.out.println("ERR is " + type.getContentModel().getErrors(TnsErrorSeverity.ERROR,false));
                   }
               }*/
            }
         }
      }
   }

   private static boolean isLeaf(SmSequenceType element) {
      /* w/ this in place, a substituted wildcard (known type, unknown element can't display children)if (XTypeSupport.isWildcardElement(element))
      {
          // wildcards don't have any meaningful child display.
          return true;
      }*/
      SmType type = SmSequenceTypeSupport.getActualElementType(element);
      if (type == XSDL.ANY_TYPE) {
         return true;
      }
      if (element.isNillable() && element.isNillabilityKnown()) {
         // If nil, we want to show that.
         return false;
      }
      // if it has no children:
      if (SmSequenceTypeSupport.isVoid(SmSequenceTypeSupport.stripMagicAttributes(element.attributeAxis())) && SmSequenceTypeSupport.isVoid(element.childAxis().nameTest(ALL))) {
         return true;
      }
      return false;
   }

   public boolean getNillable() {
      return m_isNillable && m_nillabilityKnown;
   }

   public boolean isSubstituted() {
      return m_srcXPathIfCoerced != null;
   }

   public SmSequenceType getAsXType() {
      return SmSequenceTypeFactory.createRepeats(mElement, mOccurrence);
   }

   // 1-2TI4I4 this is a new and improved method to create an XML string - 10/28/04 TWH
   public String toXML() {
      SmParticleTerm t = mElement.getParticleTerm();
      final SmSchema s;
      if (t.getSchema() != null) {
         s = t.getSchema();
      }
      else {
         final SmFactory smFactory = SmFactory.newInstance();
         s = smFactory.createMutableSchema();
         if (t.getNamespace() != null) {
            ((MutableSchema) s).setNamespace(t.getNamespace());
         }
         if (t.getName() == null) {
            //we need to put it in some named group
            MutableModelGroup mg = (MutableModelGroup) MutableSupport
                    .createComponent((MutableSchema) s, SmComponent.MODEL_GROUP_TYPE);
            mg.setExpandedName(new ExpandedName(s.getNamespace(), "root"));
            MutableSupport.addParticleTerm(mg, t, 1, 1);
            t = mg;//the group will next be added to the schema
         }
         ((MutableSchema) s).addSchemaComponent(t);
      }
      StringWriter sw = new StringWriter();
      sw.write("<?xml version='1.0'?>\n");//not much value in writing it now; encoding can't be determined anyway
      final NamespaceCounter nsCounter = new NamespaceCounter();
      try {
         s.accept(nsCounter);
      }
      catch (SmException e) {
         return e.getLocalizedMessage();
      }
      try {
         final XSDWriter xsdWriter = new XSDWriter();
         xsdWriter.write(s, sw, nsCounter.getImportedNamespaces());
      }
      catch (Exception e) {
         return e.getLocalizedMessage();
      }
      return sw.toString();
   }

   // 1-2TI4I4 this method had problems with complex, nested structures
   //   Anli Shundi supplied the improved method, this code is being
   //   left in just to maintain an audit trail of what it used to do
   /*
   public String oldtoXML()
   {
       SmElement e = (SmElement) mElement.getParticleTerm();
       StringWriter sw = new StringWriter();
       sw.write("<?xml version=\"1.0\"?>\n");
       sw.write("<xsd:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"/>");
       writeElement(e,1,1,sw,"    ");
       sw.write("</xsd:schema/>");
       return super.toXML();
   }

   private void writeElement(SmElement e, int min, int max, StringWriter sw, String indent)
   {
       SmType t = e.getType();
       sw.write(indent);
       sw.write("<xsd:element name=\"");
       sw.write(e.getName());
       if (min!=1)
       {
           sw.write(" minOccurs=\"");
           sw.write(""+min);
           sw.write("\"");
       }
       if (max!=1)
       {
           sw.write(" minOccurs=\"unbounded\"");
       }

       if (SmSupport.isTextOnlyContent(t))
       {
           sw.write("\" type=\"");
           String tname = t.getName();
           sw.write("xsd:" + tname);
           sw.write(">\n");
       }
       else
       {
           sw.write("\">\n");

           sw.write(indent);
           sw.write("   ");
           sw.write("<xsd:complexType><xsd:sequence>\n");

           Iterator i = t.getContentModel().getParticles();
           String nindent = indent + "        ";
           while (i.hasNext())
           {
               SmParticle p = (SmParticle) i.next();
               writeElement((SmElement) p.getTerm(),p.getMinOccurrence(),p.getMinOccurrence(),sw,nindent);
           }
           sw.write(indent);
           sw.write("   ");
           sw.write("</xsd:sequence></xsd:complexType>\n");

           sw.write(indent);
           sw.write("</xsd:element>");
       }
   }
   // 1-2TI4I4 end of change
   */

   public boolean hasChildContent() {
      return ((DataTree) getTree()).nodeHasChildContent(this);
   }

   public Icon getIcon() {
      return mIcon;
   }

   public boolean isEditable() {
      return false;
   }

   public String getName() {
      return mName;
   }

   public BasicTreeNode[] buildChildren() {
      if (mIsLeaf) {
         return EMPTY_ARRAY;
      }
//       return super.createChildNodesForXType(mElement);
      TnsDocumentProvider tdp = getTree() == null ? null : getTree().getTnsDocumentProvider();
      return super.createChildNodesForXType(mElement, tdp, true);
   }

   public String getLineError() {
      return m_error;
   }

   public boolean isErrorIcon() {
      return m_error != null;
   }

   public String getUnsubstitutedXStepName(boolean isLastStep, NamespaceContextRegistry ni) {
      if (!isLastStep || m_srcXPathIfCoerced == null) {
         // no change.
         return getXStepName(isLastStep, ni);
      }
      else {
         String[] str = Utilities.getAsArray(m_srcXPathIfCoerced);
         if (str.length > 0) {
            return str[str.length - 1];
         }
         // Shouldn't happen.
         return "*";
      }
   }

   public String getXStepName(boolean isLastStep, NamespaceContextRegistry namespaceContextRegistry) {
      ExpandedName ename = mElement.getName();
      if (ename != null) {
         if (ename.getNamespaceURI() != null && ename.getNamespaceURI().length() > 0) {
            String pfx = namespaceContextRegistry.getOrAddPrefixForNamespaceURI(ename.getNamespaceURI());
            String name;
            if (pfx == null || pfx.length() == 0) {
               name = ename.getLocalName();
            }
            else {
               name = pfx + ":" + ename.getLocalName();
            }
            return name;
         }
         else {
            return ename.getLocalName();
         }
      }
      return "*";
   }

   public boolean isLeaf() {
      return mIsLeaf;
   }

   public Object getIdentityTerm() {
      return mElement.getParticleTerm();
   }
}
