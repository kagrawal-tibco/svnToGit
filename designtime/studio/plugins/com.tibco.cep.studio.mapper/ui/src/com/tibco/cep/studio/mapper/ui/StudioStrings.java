package com.tibco.cep.studio.mapper.ui;

import com.tibco.cep.mapper.util.ResourceBundleManager;

/**
 * A collection of localized strings for use in the user interface. All constants are named the english
 * version of the string the hold.
 */
public interface StudioStrings
{
    public static final String OK=ResourceBundleManager.getMessage("ok");
    public static final String CANCEL=ResourceBundleManager.getMessage("cancel");
    public static final String RESET=ResourceBundleManager.getMessage("reset");
    public static final String CLOSE=ResourceBundleManager.getMessage("close");
    public static final String APPLY=ResourceBundleManager.getMessage("dialog.apply");

    public static final String OPEN=ResourceBundleManager.getMessage("dialog.open");
    public static final String SAVE=ResourceBundleManager.getMessage("dialog.save");
    public static final String NEW=ResourceBundleManager.getMessage("dialog.new");
    public static final String DELETE=ResourceBundleManager.getMessage("dialog.delete");
    public static final String CREATE=ResourceBundleManager.getMessage("dialog.create");

    public static final String DEFAULT=ResourceBundleManager.getMessage("dialog.default");

    public static final String YES=ResourceBundleManager.getMessage("yes");
    public static final String NO=ResourceBundleManager.getMessage("no");

    public static final String ALL=ResourceBundleManager.getMessage("all");
    public static final String NONE=ResourceBundleManager.getMessage("none");

    public static final String READ_ONLY=ResourceBundleManager.getMessage("readonly");
    public static final String REFRESH=ResourceBundleManager.getMessage("dialog.refresh");

    public static final String WARNING=ResourceBundleManager.getMessage("warning");
    public static final String ERROR=ResourceBundleManager.getMessage("error");
    public static final String UNKNOWN=ResourceBundleManager.getMessage("unknown");
}
