package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;

/**
 * Embraces and extends JDialog to provide extra features.<br>
 * Features:
 * <ul>
 * <li>The ESCAPE key works on disposing modal dialogs, like the window standard does.
 * (this portion of code taken from
 * <a href="http://www.javaworld.com/javaworld/javatips/jw-javatip69.html">a Javaworld article</a>).
 * (Note that this bug exists in JDK 1.3 and JDK 1.4.1_01)
 * </ul>
 */
public class BetterJDialog extends JDialog
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Internal mInternalListener;

    private String m_baseSizePrefsKey; // optionally set.
    protected UIAgent uiAgent;

    public BetterJDialog(UIAgent uiAgent, Frame frame) {
        super(frame);

        setup(uiAgent);
    }

    public BetterJDialog(UIAgent uiAgent, Dialog dialog) {
        super(dialog);
        setup(uiAgent);
    }

    public BetterJDialog(UIAgent uiAgent, Frame frame, String title) {
        super(frame, title);
        setup(uiAgent);
    }

    public BetterJDialog(UIAgent uiAgent, Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        setup(uiAgent);
    }

    public void dispose() {
        // cleanup:
        removeKeyAndContainerListenerRecursively(this);
        writeSize();
        super.dispose();
    }

    private void setup(UIAgent uiAgent) {
        this.uiAgent = uiAgent;
        mInternalListener = new Internal();
        addKeyAndContainerListenerRecursively(this);
    }


//The following function is recursive and is intended for internal use only. It is private to prevent anyone calling it from other classes
//The function takes a Component as an argument and adds this Dialog as a KeyListener to it.
//Besides it checks if the component is actually a Container and if it is, there  are 2 additional things to be done to this Container :
// 1 - add this Dialog as a ContainerListener to the Container
// 2 - call this function recursively for every child of the Container.

    private void addKeyAndContainerListenerRecursively(Component c) {
        //To be on the safe side, try to remove KeyListener first just in case it has been added before.
        //If not, it won't do any harm
        c.removeKeyListener(mInternalListener);
        //Add KeyListener to the Component passed as an argument
        c.addKeyListener(mInternalListener);

        if (c instanceof Container) {

            //Component c is a Container. The following cast is safe.
            Container cont = (Container) c;

            //To be on the safe side, try to remove ContainerListener first just in case it has been added before.
            //If not, it won't do any harm
            cont.removeContainerListener(mInternalListener);
            //Add ContainerListener to the Container.
            cont.addContainerListener(mInternalListener);

            //Get the Container's array of children Components.
            Component[] children = cont.getComponents();

            //For every child repeat the above operation.
            for (int i = 0; i < children.length; i++) {
                addKeyAndContainerListenerRecursively(children[i]);
            }
        }
    }


    /**
     * The following function is the same as the function above with the exception that it does exactly the opposite - removes this Dialog
     */
    private void removeKeyAndContainerListenerRecursively(Component c) {
        c.removeKeyListener(mInternalListener);

        if (c instanceof Container) {

            Container cont = (Container) c;

            cont.removeContainerListener(mInternalListener);

            Component[] children = cont.getComponents();

            for (int i = 0; i < children.length; i++) {
                removeKeyAndContainerListenerRecursively(children[i]);
            }
        }
    }


    /**
     * Calling this will 1) read the size & location from the app preferences and 2) on dispose, write them out again.
     * @param basePrefsKey
     * @param minSize
     * @param defaultSize
     */
    public void setSizePreferences(String basePrefsKey, Dimension minSize, Dimension defaultSize)
    {
        if (basePrefsKey.endsWith("."))
        {
            throw new IllegalArgumentException("Base prefs key should not end with .");
        }
        // Write these down for use in dispose() (for writing back out again)
        m_baseSizePrefsKey = basePrefsKey;


        Dimension d = PreferenceUtils.readDimension(
            uiAgent,
            basePrefsKey + ".lastWindowSize",
            minSize,
            Toolkit.getDefaultToolkit().getScreenSize(), // max size.
            defaultSize  // default size.
        );
        setSize(d);
        Point loc = PreferenceUtils.readLocation(
            uiAgent,
            basePrefsKey + ".lastWindowLocation",
            null,
            d // size used.
        );
        if (loc!=null) {
            setLocation(loc);
        } else {
            if (getOwner()!=null)
            {
                setLocationRelativeTo(getOwner());
            }
        }
    }

    private void writeSize()
    {
        if (uiAgent==null || m_baseSizePrefsKey==null)
        {
            return;
        }
        PreferenceUtils.writeDimension(
            uiAgent,
            m_baseSizePrefsKey + ".lastWindowSize",
            getSize()
        );
        PreferenceUtils.writePoint(
            uiAgent,
            m_baseSizePrefsKey + ".lastWindowLocation",
            getLocation()
        );
    }

    class Internal implements ContainerListener, KeyListener {
        //ContainerListener interface
        /**********************************************************/

        //This function is called whenever a Component or a Container is added to another Container belonging to this Dialog
        public void componentAdded(ContainerEvent e) {
            addKeyAndContainerListenerRecursively(e.getChild());
        }

        //This function is called whenever a Component or a Container is removed from another Container belonging to this Dialog
        public void componentRemoved(ContainerEvent e) {
            removeKeyAndContainerListenerRecursively(e.getChild());
        }

        //KeyListener interface
        /**********************************************************/
        //This function is called whenever a Component belonging to this Dialog (or the Dialog itself) gets the KEY_PRESSED event
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_ESCAPE) {
                //Key pressed is the ESCAPE key. Hide this Dialog.
                setVisible(false);
            } else if (code == KeyEvent.VK_ENTER) {
                //Key pressed is the ENTER key. Redefine performEnterAction() in subclasses to respond to depressing the ENTER key.
                performEnterAction(e);
            }

            //Insert code to process other keys here
        }

        //We need the following 2 functions to complete imlementation of KeyListener
        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }

        void performEnterAction(KeyEvent e) {
            //Default response to ENTER key pressed goes here
            //Redefine this function in subclasses to respond to ENTER key differently
        }
    }

}
