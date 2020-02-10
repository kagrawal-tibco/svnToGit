package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util;

import java.util.HashMap;

/**
 * @author rajesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class ExpandCollapseHandleConfigurator {

    //Different types of images index used.
    public static final Integer SINGLE_ITEM_NODE = new Integer(0);
    public static final Integer SINGLE_OPEN_NODE = new Integer(1);
    public static final Integer SINGLE_CLOSE_NODE = new Integer(2);
    public static final Integer LAST_ITEM_NODE = new Integer(3);
    public static final Integer LAST_OPEN_NODE = new Integer(4);
    public static final Integer LAST_CLOSE_NODE = new Integer(5);
    public static final Integer FIRST_ITEM_NODE = new Integer(6);
    public static final Integer FIRST_OPEN_NODE = new Integer(7);
    public static final Integer FIRST_CLOSE_NODE = new Integer(8);
    public static final Integer ONLY_ITEM_NODE = new Integer(9);
    public static final Integer ONLY_OPEN_NODE = new Integer(10);
    public static final Integer ONLY_CLOSE_NODE = new Integer(11);
    public static final Integer PROGRESS_NODE = new Integer(12);

    //Open-Close-No Child Image Indices.
    static final int INDEX_CLOSE_IMAGE = 0;
    static final int INDEX_OPEN_IMAGE = 1;
    static final int INDEX_NOCHILD_IMAGE = 2;
    static final int INDEX_PROGRESS_IMAGE = 3;

    private String rowExpandedLink;
    //private String arrExpandCollapseImages[];
    private HashMap mapExpandCollapseImages = new HashMap();
    private String expandCollapseImagePath;

    /**
     *
     */
    public ExpandCollapseHandleConfigurator(String rowExpandedLink) {
        this();
        this.rowExpandedLink = rowExpandedLink;
    }
    /**
     *
     */
    public ExpandCollapseHandleConfigurator() {
        super();
        //expandCollapseImagePath = getImagePath();
        mapExpandCollapseImages.put(SINGLE_ITEM_NODE, "treeItemOnly.gif");
        mapExpandCollapseImages.put(SINGLE_CLOSE_NODE, "treeNodeCloseOnly.gif");
        mapExpandCollapseImages.put(SINGLE_OPEN_NODE, "treeNodeOpenOnly.gif");

        mapExpandCollapseImages.put(FIRST_ITEM_NODE, "treeItemFirst.gif");
        mapExpandCollapseImages.put(FIRST_CLOSE_NODE, "treeNodeCloseFirst.gif");
        mapExpandCollapseImages.put(FIRST_OPEN_NODE, "treeNodeOpenFirst.gif");

        mapExpandCollapseImages.put(LAST_ITEM_NODE, "treeItemLast.gif");
        mapExpandCollapseImages.put(LAST_CLOSE_NODE, "treeNodeCloseLast.gif");
        mapExpandCollapseImages.put(LAST_OPEN_NODE, "treeNodeOpenLast.gif");

        mapExpandCollapseImages.put(ONLY_ITEM_NODE, "treeItem.gif");
        mapExpandCollapseImages.put(ONLY_CLOSE_NODE, "treeNodeClose.gif");
        mapExpandCollapseImages.put(ONLY_OPEN_NODE, "treeNodeOpen.gif");
        mapExpandCollapseImages.put(PROGRESS_NODE, "treeNodeprogress.gif");

        //treeLine.gif
    }

    void loadExpandCollapseImages(
        String[] imgExpandCollapse,
        boolean isFirst,
        boolean isLast) {
        String lc_closeImage = "";
        String lc_openImage = "";
        String lc_noChildImage = "";

        if (isFirst) {
            if (isLast) {
                lc_closeImage = getExpandCollapseImage(SINGLE_CLOSE_NODE);
                lc_openImage = getExpandCollapseImage(SINGLE_OPEN_NODE);
                lc_noChildImage = getExpandCollapseImage(SINGLE_ITEM_NODE);
            } else {
                lc_closeImage = getExpandCollapseImage(FIRST_CLOSE_NODE);
                lc_openImage = getExpandCollapseImage(FIRST_OPEN_NODE);
                lc_noChildImage = getExpandCollapseImage(FIRST_ITEM_NODE);
            }
        } else {

            if (isLast) {

                lc_closeImage = getExpandCollapseImage(LAST_CLOSE_NODE);
                lc_openImage = getExpandCollapseImage(LAST_OPEN_NODE);
                lc_noChildImage = getExpandCollapseImage(LAST_ITEM_NODE);
            } else {

                lc_closeImage = getExpandCollapseImage(ONLY_CLOSE_NODE);
                lc_openImage = getExpandCollapseImage(ONLY_OPEN_NODE);
                lc_noChildImage = getExpandCollapseImage(ONLY_ITEM_NODE);
            }
        }

        String imageDirectory = getExpandCollapseImagePath();
        imgExpandCollapse[INDEX_CLOSE_IMAGE] =
            imageDirectory + "/" + lc_closeImage;
        imgExpandCollapse[INDEX_OPEN_IMAGE] =
            imageDirectory + "/" + lc_openImage;
        imgExpandCollapse[INDEX_NOCHILD_IMAGE] =
            imageDirectory + "/" + lc_noChildImage;
        imgExpandCollapse[INDEX_PROGRESS_IMAGE] =
            imageDirectory + "/" + getExpandCollapseImage(PROGRESS_NODE);
        return;
    }

    /**
     * @return
     */
    public String getExpandCollapseImagePath() {
        return expandCollapseImagePath;
    }

    /**
     * @param ONLY_CLOSE_NODE
     * @return
     */
    private String getExpandCollapseImage(Integer imgIndex) {
        //return "exCollImage";
        //return arrExpandCollapseImages[imgIndex];
        return (String)mapExpandCollapseImages.get(imgIndex);
    }

    /**
     * @return
     */
    public HashMap getExpandCollapseImageMap() {
        return mapExpandCollapseImages;
    }
    /**
     * @param strings
     */
    public void setExpandCollapseImageMap(HashMap mapImages) {
        mapExpandCollapseImages = mapImages;
    }

    /**
     * @param string
     */
    public void setExpandCollapseImagePath(String string) {
        expandCollapseImagePath = string;
    }

    /**
     * @return
     */
    public String getRowExpandedLink() {
        return rowExpandedLink;
    }

    /**
     * @param string
     */
    public void setRowExpandedLink(String string) {
        rowExpandedLink = string;
    }
}
