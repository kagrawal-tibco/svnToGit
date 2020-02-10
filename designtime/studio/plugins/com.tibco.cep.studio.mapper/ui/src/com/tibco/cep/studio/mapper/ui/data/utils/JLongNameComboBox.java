package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.CellRendererPane;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * A combo box whose name is both self descriptive and who more gracefully displays long selected names.<br>
 */
public class JLongNameComboBox extends JComboBox
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OvershowList m_overshowList;
    private static Window m_overshowWindow; // static because only 1 can ever be visible, and we don't want to leak more than 1 :-)
    private static Timer m_overshowTimer; // static for same reason.  Need a timer because w/o JDK1.4, it's impossible to tell when focus is gone.
    private Object m_currentOvershowSelection;

    private CellRendererPane m_cellRendererPane = new CellRendererPane();
    private ListCellRenderer m_renderer;
    private MouseListener m_oldMouseListener;
    private MouseMotionListener m_oldMouseMotionListener;

    private static final int MAX_WIDTH = 100;
    private static final int COMBO_BOX_ARROW_WIDTH = 26; // Slop; width of arrow on right hand side of combo box.

    private static final int TOP_OVERSHOW_PIXEL_OFFSET = 1;
    private static final int RIGHT_OVERSHOW_PIXEL_OFFSET = 2;

    public JLongNameComboBox()
    {
        super();
        setup();
    }

    public JLongNameComboBox(Object[] items)
    {
        super(items);
        setup();
    }

    private Color getDisabledBackgroundColor()
    {
        return UIManager.getColor("ComboBox.disabledBackground");
    }

    /**
     * <b>NOTE:</b> This call wraps the renderer in another one; subsequent calls to
     * {@link #getRenderer} will return a different object (this was unavoidable, it appears ---
     * cannot override {@link #getRenderer} because that is called internally during painting, too...
     * @param r
     */
    public void setRenderer(ListCellRenderer r)
    {
        if (m_renderer == r)
        {
            return;
        }
        m_renderer = r;
        super.setRenderer(new ListCellRenderer()
        {
            private boolean m_isSel;
            private Object m_obj;
            private int m_index;

            private JPanel m_surroundingPanel = new JPanel(new BorderLayout())
            {
                /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void paint(Graphics g)
                {
                    super.paint(g);
                    if (m_isSel && m_index>=0) // if index<0, we're not painting list entries, we're painting the combo box area.
                    {
                        int pw = this.getPreferredSize().width;
                        int aw = JLongNameComboBox.this.getWidth();
                        if (pw > aw)
                        {
                            checkPanelOvershow(this,m_obj);
                        }
                        else
                        {
                            disposeOvershow();
                        }
                    }
                }
            };

            public Component getListCellRendererComponent(
                    JList list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus)
            {
                Component c = m_renderer.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
                if (!JLongNameComboBox.this.isEnabled())
                {
                    // Hacky, but couldn't figure out how to get the non-enabled painting correct otherwise
                    c.setBackground(getDisabledBackgroundColor());
                }
                m_isSel = isSelected;
                m_obj = value;
                m_index = index;
                if (m_surroundingPanel.getComponentCount()==1 && m_surroundingPanel.getComponent(0)==c)
                {
                    // same.
                    return m_surroundingPanel;
                }
                m_surroundingPanel.removeAll();
                m_surroundingPanel.add(c);
                m_surroundingPanel.revalidate();
                return m_surroundingPanel;
            }
        });
    }

    public Dimension getMinimumSize()
    {
        Dimension d = super.getMinimumSize();
        return new Dimension(Math.min(MAX_WIDTH,d.width),d.height);
    }

    public Dimension getPreferredSize()
    {
        Dimension d = super.getPreferredSize();
        return new Dimension(Math.min(MAX_WIDTH,d.width),d.height);
    }

    private void setup()
    {
        MouseMotionListener mml = new MouseMotionListener()
        {
            public void mouseMoved(MouseEvent me)
            {
                checkOvershow(me.getPoint());
            }

            public void mouseDragged(MouseEvent me)
            {
            }
        };
        addMouseMotionListener(mml);

        /** Well, rats. can't use this 1.4 feature. WCETODO FIX, use replacement.
        addPopupMenuListener(new PopupMenuListener()
        {
            public void popupMenuCanceled(PopupMenuEvent e)
            {
                disposeOvershow();
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
            {
                disposeOvershow();
            }

            public void popupMenuWillBecomeVisible(PopupMenuEvent e)
            {
            }
        });
         */

        m_overshowList = new OvershowList();
        // make it really long?
        disposeOvershow();
    }

    public void removeNotify()
    {
        disposeOvershow();
        super.removeNotify();
    }

    private void checkOvershow(Point at)
    {
        if (isPopupVisible())
        {
            // do nothing, not our business.
            return;
        }
        // Check that we're in the text area (and not the arrow area)
        // exclude a couple pixels at top/bottom.
        int topBottomExclusionPixels = 3;
        if (at.y<topBottomExclusionPixels || at.y>getHeight()-topBottomExclusionPixels || at.x>=getWidth()-COMBO_BOX_ARROW_WIDTH)
        {
            disposeOvershow();
            return;
        }
        checkOvershowForSize();
    }

    private void checkOvershowForSize()
    {
        if (m_overshowWindow!=null)
        {
            if (m_currentOvershowSelection==getSelectedItem())
            {
                return;
            }
        }
        m_currentOvershowSelection = getSelectedItem(); // hacky, but overshow list computes sized based on this, so must set now:
        int prefWidth = m_overshowList.getTextWidth();
        if (prefWidth>getWidth()-COMBO_BOX_ARROW_WIDTH)
        {
            updateOvershow();
        }
        else
        {
            disposeOvershow();
        }
    }

    private void disposeOvershow()
    {
        Window ol = m_overshowWindow;
        m_overshowWindow = null;
        m_currentOvershowSelection = null;
        if (ol!=null)
        {
            ol.setVisible(false);
            ol.dispose();
        }
        // Dispose of timer:
        Timer t = m_overshowTimer;
        m_overshowTimer = null;
        if (t!=null)
        {
            t.stop();
        }
    }

    class OvershowList extends JList
    {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public OvershowList()
        {
            this.setOpaque(true);
            this.setBackground(JLongNameComboBox.this.getBackground());
            add(m_cellRendererPane);
        }

        public void paint(Graphics g)
        {
            super.paint(g);
            Component jc = getRendererComponent();
            Dimension d = getPreferredSize();
            int yoffset = 0;//-TOP_OVERSHOW_PIXEL_OFFSET;
            m_cellRendererPane.paintComponent(g,jc,this,0,yoffset,d.width,d.height,true);

            // draw extension line:
            g.setColor(Color.black);
            Insets i = JLongNameComboBox.this.getInsets();
            int w = JLongNameComboBox.this.getWidth() + RIGHT_OVERSHOW_PIXEL_OFFSET - (i.left+i.right);
            if (JLongNameComboBox.this.isPopupVisible())
            {
                // we're in the list:
                g.drawLine(w,0,d.width,0);
            }
            else
            {
                // we're in the combo itself, draw all the way.
                g.drawLine(0,0,d.width,0);
            }
            int h = d.height-2;
            g.drawLine(w,h,d.width,h);
            g.drawLine(d.width-1,0,d.width-1,h);
        }

        public Dimension getPreferredSize()
        {
            Component jc = getRendererComponent();
            Dimension d = jc.getPreferredSize();
            return new Dimension(d.width+RIGHT_OVERSHOW_PIXEL_OFFSET,d.height+1+TOP_OVERSHOW_PIXEL_OFFSET);
        }

        public int getTextWidth()
        {
            Component jc = getRendererComponent();
            return jc.getPreferredSize().width;
        }

        private Component getRendererComponent()
        {
            Object selection = m_currentOvershowSelection;
            boolean isSel;
            boolean isFoc;
            if (!JLongNameComboBox.this.isPopupVisible())
            {
                // drawing in the combo itself:
                isSel = JLongNameComboBox.this.hasFocus();
                isFoc = false;
            } else
            {
                // drawing in the list.
                isSel = true; // since we only overpaint selected items...
                isFoc = false;
            }
            Component jc = JLongNameComboBox.this.m_renderer.getListCellRendererComponent(this,selection,0,isSel,isFoc);

            // For whatever reason, somehow can get disabled, reset here:
            jc.setEnabled(true);

            return jc;
        }

        public boolean isEnabled()
        {
            return false;//
        }
    }

    private void updateOvershow()
    {
        disposeOvershow();
        m_currentOvershowSelection = getSelectedItem();
        Window owner = SwingUtilities.getWindowAncestor(this);
        Window w = new Window(owner);
        w.setLayout(new BorderLayout());
        w.add(m_overshowList);
        w.pack();
        Insets i = getInsets();
        Point p = SwingUtilities.convertPoint(this,i.left,i.top,w);
        w.setLocation(p.x,p.y-TOP_OVERSHOW_PIXEL_OFFSET);
        if (m_oldMouseListener!=null)
        {
            m_overshowList.removeMouseListener(m_oldMouseListener);
        }
        m_oldMouseListener = new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                // Essentially forward to combo box:
                disposeOvershow();
                JLongNameComboBox.this.showPopup();
            }

            public void mouseExited(MouseEvent e)
            {
                disposeOvershow();
            }
        };
        m_overshowList.addMouseListener(m_oldMouseListener);
        if (m_oldMouseMotionListener!=null)
        {
            m_overshowList.removeMouseMotionListener(m_oldMouseMotionListener);
        }
        m_oldMouseMotionListener = new MouseMotionListener()
        {
            public void mouseDragged(MouseEvent e)
            {
            }

            public void mouseMoved(MouseEvent e)
            {
                // when it gets over the arrow area:
                if (e.getPoint().x > getWidth()-COMBO_BOX_ARROW_WIDTH)
                {
                    disposeOvershow();
                }
            }
        };
        m_overshowList.addMouseMotionListener(m_oldMouseMotionListener);

        m_overshowWindow = w;

        w.setVisible(true);
    }

    private void checkPanelOvershow(JPanel panel, final Object sel)
    {
        if (m_currentOvershowSelection==sel)
        {
            return;
        }
        disposeOvershow();
        m_currentOvershowSelection = sel;
        Window owner = SwingUtilities.getWindowAncestor(this);
        Window w = new Window(owner);
        w.setLayout(new BorderLayout());
        w.add(m_overshowList);
        w.pack();
        Insets i = panel.getInsets();
        Point p = SwingUtilities.convertPoint(panel,i.left,i.top,w);
        w.setLocation(p);
        w.setVisible(true);
        if (m_oldMouseListener!=null)
        {
            m_overshowList.removeMouseListener(m_oldMouseListener);
        }
        if (m_oldMouseMotionListener!=null)
        {
            m_overshowList.removeMouseMotionListener(m_oldMouseMotionListener);
        }
        m_oldMouseListener = new MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                // Essentially forward to combo box:
                disposeOvershow();
                JLongNameComboBox.this.setPopupVisible(false);
                // select whatever we were over.
                JLongNameComboBox.this.setSelectedItem(sel);
            }
        };
        m_overshowList.addMouseListener(m_oldMouseListener);
        m_overshowWindow = w;

        // Hacky, but only way (or only way I could figure w/o JDK 1.4) to tell if popup isn't visible anymore is
        // by polling.  We allocate the timer when displaying the popup & keep only 1 per jvm, which gets cleaned
        // up when closing the popup.
        Timer t = new Timer(100, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!m_overshowList.isVisible() || !isPopupVisible())
                {
                    disposeOvershow();
                }
                if (!isVisible() || !isDisplayable())
                {
                    disposeOvershow();
                }
            }
        });
        t.setRepeats(true);
        m_overshowTimer = t;
        t.start();
    }
}
