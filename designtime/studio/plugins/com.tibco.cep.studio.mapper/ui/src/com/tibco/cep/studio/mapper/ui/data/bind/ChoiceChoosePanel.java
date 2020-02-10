package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JPanel;

import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Utility panel for use inside the any substitution, does double-duty for choice and substitutions group.
 */
class ChoiceChoosePanel extends JPanel {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//    private ParticleTermXType mTerm;
//   private JList mList;

   static class Renderer extends DefaultListCellRenderer {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList list, Object val, int index, boolean sel, boolean foc) {
         SmSequenceType bt = (SmSequenceType) val;
         String d = DataIcons.getName(bt);
         super.getListCellRendererComponent(list, d, index, sel, foc);
         Icon i = DataIcons.getIcon(bt);
         setIcon(i);
         return this;
      }
   }

   /*

   public ChoiceChoosePanel(XType choiceTerm, AnySubstitution currentAny) {
       this(makeArray(choiceTerm),currentAny);
   }

   public ChoiceChoosePanel(SmElement[] substitutions, AnySubstitution currentAny) {
       this(makeArray(substitutions),currentAny);
   }

   private static XType[] makeArray(SmElement[] subs) {
       XType[] bt = new XType[subs.length];
       for (int i=0;i<bt.length;i++) {
           bt[i] = XTypeFactory.create(subs[i]);
       }
       return bt;
   }

   private static XType[] makeArray(XType choiceTerm)
   {
       throw new RuntimeException("FIXME");//WCETODO return choiceTerm.getChildComponents();
   }

   private ChoiceChoosePanel(XType[] terms, AnySubstitution currentAny) {
       super(new BorderLayout());
       //mTerm = term;

       mList = new JList();
       mList.setCellRenderer(new Renderer());
       DefaultListModel dlm = new DefaultListModel();

       int set = 0;
       int index = 0;
       for (int i=0;i<terms.length;i++) {
           XType c = terms[i];
           dlm.addElement(c);
           if (currentAny!=null) {
               String matchTermName = com.tibco.ui.data.DataIcons.getName(currentAny.getParticleTerm());
               String pname = com.tibco.ui.data.DataIcons.getName(c);
               if (pname.equals(matchTermName)) {
                   set = index;
               }
           }
           index++;
       }
       mList.setModel(dlm);
       add(new JLabel("Choices"),BorderLayout.NORTH);
       add(new JScrollPane(mList));
       mList.setSelectedIndex(set);
   }

   public void test_setSelectedChoice(int choice) {
       mList.setSelectedIndex(choice);
   }

   public int test_getSelectedChoice() {
       return mList.getSelectedIndex();
   }

   public int test_getChoiceCount() {
       return mList.getModel().getSize();
   }

   public AnySubstitution buildAny() {
       if (mList.getSelectedIndex()<0) {
           return null;
       }
       XType bt = (XType) mList.getSelectedValue();
       SmParticleTerm term = bt.getParticleTerm();
       return new AnySubstitution(term);
   }*/
}
