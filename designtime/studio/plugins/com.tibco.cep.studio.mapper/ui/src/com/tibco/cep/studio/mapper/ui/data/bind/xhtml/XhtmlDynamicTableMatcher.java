package com.tibco.cep.studio.mapper.ui.data.bind.xhtml;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.virt.BindingVirtualizer;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualXsltElement;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualXsltMatcher;
import com.tibco.cep.studio.mapper.ui.data.bind.StatementPanel;
import com.tibco.xml.data.primitive.ExpandedName;

public class XhtmlDynamicTableMatcher implements VirtualXsltMatcher {
   private static final String XHTML_NAMESPACE = "http://www.w3.org/1999/xhtml";
   public static final ExpandedName XHTML_TABLE = ExpandedName.makeName(XHTML_NAMESPACE, "table");
   public static final ExpandedName XHTML_CAPTION = ExpandedName.makeName(XHTML_NAMESPACE, "caption");

   public static final VirtualXsltMatcher INSTANCE = new XhtmlDynamicTableMatcher();

   private final StatementPanel m_myPanel;

   private XhtmlDynamicTableMatcher() {
      m_myPanel = new XhtmlDynamicTablePanel();
   }

   public StatementPanel getStatementPanel() {
      return m_myPanel;
   }

   public boolean matches(Binding at) {
      if (!checkElement(at, XHTML_TABLE)) {
         return false;
      }
      return true; // make tougher later.
   }

   public VirtualXsltElement virtualize(Binding at, BindingVirtualizer virtualizer, CancelChecker cancelChecker) {
      return new XhtmlDynamicTable(at, virtualizer, cancelChecker);
   }

   public Binding normalize(Binding at) {
      return at;
   }

   public static boolean checkElement(Binding at, ExpandedName elname) {
      if (!(at instanceof VirtualElementBinding)) {
         return false;
      }
      VirtualElementBinding veb = (VirtualElementBinding) at;
      if (veb.isExplicitXslRepresentation()) // only support literal for virtualized.
      {
         return false;
      }
      if (at.getName().equals(elname)) {
         return true;
      }
      return false;
   }

   public static Binding getChildElement(Binding at, ExpandedName elname) {
      int cc = at.getChildCount();
      for (int i = 0; i < cc; i++) {
         if (checkElement(at.getChild(i), elname)) {
            return at.getChild(i);
         }
      }
      return null;
   }
}

