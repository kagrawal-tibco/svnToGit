package com.tibco.cep.studio.ui.advance.event.payload;

import javax.swing.Icon;
import javax.swing.JTree;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.edittree.render.DefaultNameValueTreeCellPlugin;
import com.tibco.cep.studio.mapper.ui.edittree.render.TextRenderer;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea;

/**
 * Used by {@link ParameterEditorTree} for renderering.
 */
class ParameterEditorTreeCellRendererPlugin extends DefaultNameValueTreeCellPlugin {
   private DataIconRenderer m_renderer;
   private JExtendedEditTextArea m_nameEditor;

   public ParameterEditorTreeCellRendererPlugin(JTree tree, UIAgent uiAgent) {
      super(uiAgent);
      m_renderer = new DataIconRenderer(tree.getBackground(), uiAgent);
      m_nameEditor = new JExtendedEditTextArea();
      m_nameEditor.setFont(uiAgent.getAppFont());
   }

   public TextRenderer getDataRenderer(Object node) {
      return null; // n/a
   }

   public int getIconWidth() {
      return m_renderer.getIconWidth();
   }

   public Icon getIcon(Object node) {
    /*  ParameterPayloadNode pn = (ParameterPayloadNode) node;
   //   m_renderer.setBaseIcon(pn.getIcon());
      m_renderer.setMin(pn.getMin());
      m_renderer.setMax(pn.getMax());
      m_renderer.setNilled(false);//pn.isMin());

      return m_renderer;*/
	   return null;
   }

   public String getNodeNameValue(Object node) {
     // return ((ParameterPayloadNode) node).getDisplayName();
	   return null;
   }

   public boolean isNameEditable(Object node) {
   //   return ((ParameterPayloadNode) node).isNameEditable();
	   return true;
   }

   public JExtendedEditTextArea getNameEditor(Object node) {
     /* String val = ((ParameterPayloadNode) node).getName();
      m_nameEditor.setText(val);
      return m_nameEditor;*/
	   return  null;
   }

   public void nameEditingFinished(Object node, JExtendedEditTextArea edit) {
     /* ParameterPayloadNode pnode = (ParameterPayloadNode) node;
      String text = edit.getText();
      String val = pnode.getName();
      if (!text.equals(val)) {
         pnode.setName(text);
  //       pnode.getTree().contentChanged();//WCETODO this sucks.
      }*/
   }
}
