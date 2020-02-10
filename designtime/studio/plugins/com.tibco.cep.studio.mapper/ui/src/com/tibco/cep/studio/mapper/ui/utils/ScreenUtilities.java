package com.tibco.cep.studio.mapper.ui.utils;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;


public class ScreenUtilities {

    public static void centerInScreen(Window window) {
        Dimension d = window.getSize();
        Dimension screen = getScreenSize();
        int extraWidth = screen.width - d.width;
        int extraHeight = screen.height - d.height;
        window.setLocation(extraWidth / 2, extraHeight / 2);
    }

    public static void centerInScreen(Dialog dialog) {
        Dimension d = dialog.getSize();
        Dimension screen = getScreenSize();
        int extraWidth = screen.width - d.width;
        int extraHeight = screen.height - d.height;
        dialog.setLocation(extraWidth / 2, extraHeight / 2);
    }

    public static void centerInParent(Window window) {
        center(window, window.getOwner());
    }

    public static void center(Window window, Window owner) {
        if(owner == null) {
            centerInScreen(window);
        } else {
            Dimension parentSize = owner.getSize();
            Dimension windowSize = window.getSize();
            Point parentOrigin = owner.getLocation();
            int x = parentOrigin.x + (parentSize.width - windowSize.width) / 2;
            int y = parentOrigin.y + (parentSize.height - windowSize.height) / 2;
            if(x < 0 || y < 0)
                centerInScreen(window);
            else
                window.setLocation(x, y);
        }
    }

    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
