package com.jidesoft.decision.cell.editors.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.swing.CheckBoxListSelectionModel;
import com.jidesoft.swing.CheckBoxListWithSelectable;
import com.jidesoft.swing.DefaultSelectable;
import com.jidesoft.swing.NullCheckBox;
import com.jidesoft.swing.NullLabel;
import com.jidesoft.swing.NullRadioButton;
import com.jidesoft.swing.Selectable;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;

public class CheckBoxTableCellRenderer extends JPanel implements ListCellRenderer, Serializable {
    
	private static final long serialVersionUID = 2003073492549917883L;

    /**
     * The checkbox that is used to paint the check box in cell renderer
     */
    protected JToggleButton _checkBox;
    protected JLabel _label = new NullLabel();
    protected JLabel _colLabel;
    
    private Object actualValue;
    
    private ConverterContext context;
    private JComponent listCellRendererComponent;
    private CheckBoxTable table;
    
    /**
     * The label which appears after the check box.
     */
    protected ListCellRenderer _actualListRenderer;

    public CheckBoxTableCellRenderer(ListCellRenderer renderer) {
        setOpaque(true);
        setLayout(new GridLayout(1, 3, 0, 0));
        setPreferredSize(new Dimension(120, 20));
        add(_checkBox);
        _colLabel = new JLabel();
        _colLabel.setOpaque(true);
        _colLabel.setHorizontalAlignment(SwingConstants.LEFT);
		_actualListRenderer = renderer;
    }

    /**
     * Constructs a default renderer object for an item in a list.
     */
    public CheckBoxTableCellRenderer() {
        this(null);
    }

    public CheckBoxTableCellRenderer(ListCellRenderer renderer, 
    		                         ConverterContext context, 
    		                         CheckBoxTable _table) {
        this.context = context;
        this.table = _table;
 
        setOpaque(true);
        setLayout(new GridLayout(1, 3, 0, 0));
        setPreferredSize(new Dimension(120, 20));
        
        boolean isActionColumn = ((DefaultConverterContext)context).isActionColumn();
        if (isActionColumn) {
        	_checkBox = new NullRadioButton();
        	add(_checkBox);
        	 table.getRButtonlist().add((JRadioButton)_checkBox);
        	 JRadioButton button = (JRadioButton)_checkBox;
        	 button.addItemListener(new ItemListener(){
     			@Override
     			public void itemStateChanged(ItemEvent e) {
     				if (e.getStateChange()== ItemEvent.SELECTED) {
     					for (JRadioButton button: table.getRButtonlist()) {
     						if (button != _checkBox) {
     							button.setSelected(false);
     						}
     					}
     				}
     				
     			}});
        } else {
        	_checkBox = new NullCheckBox();
        	add(_checkBox);
        }
        _colLabel = new JLabel();
        _colLabel.setOpaque(true);
        _colLabel.setHorizontalAlignment(SwingConstants.LEFT);
		_actualListRenderer = renderer;
    }
   
    
    public ListCellRenderer getActualListRenderer() {
        return _actualListRenderer;
    }

    public void setActualListRenderer(ListCellRenderer actualListRenderer) {
        _actualListRenderer = actualListRenderer;
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        if (_actualListRenderer instanceof JComponent) {
            Point p = event.getPoint();
            p.translate(-_checkBox.getWidth(), 0);
            MouseEvent newEvent = new MouseEvent(((JComponent) _actualListRenderer), event.getID(),
                    event.getWhen(),
                    event.getModifiers(),
                    p.x, p.y, event.getClickCount(),
                    event.isPopupTrigger());

            String tip = ((JComponent) _actualListRenderer).getToolTipText(
                    newEvent);

            if (tip != null) {
                return tip;
            }
        }
        return super.getToolTipText(event);
    }

    /* (non-Javadoc)
     * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
     */
    public Component getListCellRendererComponent(JList list,
                                                  Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
        _checkBox.setPreferredSize(new Dimension(1, 0));
        applyComponentOrientation(list.getComponentOrientation());
       
        if (list instanceof CheckBoxTable) {
            CheckBoxListSelectionModel selectionModel = ((CheckBoxTable) list).getCheckBoxListSelectionModel();
            if (selectionModel != null) {
                boolean enabled = list.isEnabled()
                        && ((CheckBoxTable) list).isCheckBoxEnabled()
                        && ((CheckBoxTable) list).isCheckBoxEnabled(index);
                if (!enabled && !isSelected) {
                    if (getBackground() != null) {
                        setForeground(getBackground().darker());
                    }
                }
                _checkBox.setEnabled(enabled);
                
                _checkBox.setSelected(selectionModel.isSelectedIndex(index));
            }
            actualValue = value;
         }
        else if (list instanceof CheckBoxListWithSelectable) {
            if (value instanceof Selectable) {
                _checkBox.setSelected(((Selectable) value).isSelected());
                boolean enabled = list.isEnabled() && ((Selectable) value).isEnabled() && ((CheckBoxListWithSelectable) list).isCheckBoxEnabled();
                if (!enabled && !isSelected) {
                    setForeground(getBackground().darker());
                }
                _checkBox.setEnabled(enabled);
            }
            else {
                boolean enabled = list.isEnabled();
                if (!enabled && !isSelected) {
                    setForeground(getBackground().darker());
                }
                _checkBox.setEnabled(enabled);
            }

            if (value instanceof DefaultSelectable) {
                actualValue = ((DefaultSelectable) value).getObject();
            }
            else {
                actualValue = value;
            }
        }
        else {
            throw new IllegalArgumentException("CheckBoxListCellRenderer should only be used for CheckBoxList.");
        }

        if (_actualListRenderer != null) {
            listCellRendererComponent = (JComponent) _actualListRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (list instanceof CheckBoxListWithSelectable) {
                if (!((CheckBoxListWithSelectable) list).isCheckBoxVisible(index)) {
                    return listCellRendererComponent;
                }
            }
            if (list instanceof CheckBoxTable) {
                if (!((CheckBoxTable) list).isCheckBoxVisible(index)) {
                    return listCellRendererComponent;
                }
            }
            Border border = listCellRendererComponent.getBorder();
            setBorder(border);
            listCellRendererComponent.setBorder(BorderFactory.createEmptyBorder());
            if (getComponentCount() == 2) {
                remove(1);
            }
 
            if (DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.SHOW_DOMAIN_DESCRIPTION)){
               	add(_colLabel);
               	add(listCellRendererComponent);
               	String[] values = ((DefaultConverterContext)context).getValues();
               	if(index < values.length) {
               		_colLabel.setText(values[index]);
               	}
            } else {
               	add(listCellRendererComponent);
            	add(_colLabel);
             	String[] descs = ((DefaultConverterContext)context).getDescriptions();
                _colLabel.setText(descs[index]);
            }
               
            setBackground(listCellRendererComponent.getBackground());
            setForeground(listCellRendererComponent.getForeground());
        }
        else {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            if (getComponentCount() == 2) {
                remove(1);
            }
            add(_label);
            customizeDefaultCellRenderer(actualValue);
            setFont(list.getFont());
        }
    	if (index == 0) {
    		_checkBox.setVisible(false);
    		_checkBox.setEnabled(false);
    		listCellRendererComponent.setOpaque(true);
    		_colLabel.setOpaque(true);
    		listCellRendererComponent.setBackground(Color.LIGHT_GRAY);
    		listCellRendererComponent.setForeground(Color.BLUE);
    		listCellRendererComponent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    		_colLabel.setBackground(Color.LIGHT_GRAY);
    		_colLabel.setForeground(Color.BLUE);
    		_colLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    		
    		_checkBox.setBackground(Color.LIGHT_GRAY);
    		_checkBox.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    		
    		if (cellHasFocus) {
    			setBackground(Color.WHITE);
    		}
    		
    	} else {
    		_checkBox.setVisible(true);
    		_checkBox.setEnabled(true);
    		_checkBox.setOpaque(false);
    		listCellRendererComponent.setOpaque(false);
    		_colLabel.setOpaque(false);
    		listCellRendererComponent.setForeground(Color.BLACK);
    		_colLabel.setForeground(Color.BLACK);
    		listCellRendererComponent.setBorder(null);
    		_colLabel.setBorder(null);
    		_checkBox.setBorder(null);
    	}
        return this;
    }

    /**
     * Customizes the cell renderer. By default, it will use toString to covert the object and use it as the text for
     * the checkbox. You can subclass it to set an icon, change alignment etc. Since "this" is a JCheckBox, you can call
     * all methods available on JCheckBox in the overridden method.
     *
     * @param value the value on the cell renderer.
     */
    protected void customizeDefaultCellRenderer(Object value) {
        if (value instanceof Icon) {
            _label.setIcon((Icon) value);
            _label.setText("");
        }
        else {
            _label.setIcon(null);
            _label.setText((value == null) ? "" : value.toString());
        }
    }


    /**
     * A subclass of DefaultListCellRenderer that implements UIResource. DefaultListCellRenderer doesn't implement
     * UIResource directly so that applications can safely override the cellRenderer property with
     * DefaultListCellRenderer subclasses.
     * <p/>
     * <strong>Warning:</strong> Serialized objects of this class will not be compatible with future Swing releases. The
     * current serialization support is appropriate for short term storage or RMI between applications running the same
     * version of Swing.  As of 1.4, support for long term storage of all JavaBeans<sup><font size="-2">TM</font></sup>
     * has been added to the <code>java.beans</code> package. Please see {@link java.beans.XMLEncoder}.
     */
    public static class UIResource extends CheckBoxTableCellRenderer
            implements javax.swing.plaf.UIResource {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
    }

}