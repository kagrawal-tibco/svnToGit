
package com.tibco.cep.studio.mapper.ui.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.HashMap;

public class StudioGridLayout implements LayoutManager2, java.io.Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * arrays will be 1 too big to match API for HIC layout
     */
    protected int[] widths;
    protected int[] heights;
    protected int[] colWeights;
    protected int[] rowWeights;

	protected ArrayList<Component>[] colElements;
	protected ArrayList<Component>[] rowElements;

    protected HashMap<Component, StudioGridConstraints> componentToConstraint;

    protected Dimension preferredLayoutSize;
    protected Dimension minimumLayoutSize;
    protected int[] xOrigins;
    protected int[] yOrigins;

    /**
     * Construct a new layout object. Length of passed arrays define number of columns and
     * number of rows. Each width or height can be less then 0, equal 0 or greater then 0.
     * Passed arrays defines design grid - sizes and dependences between columns and rows.
     * @param widths array of column widths.
     * @param heights array of row heights.
     */
    @SuppressWarnings("unchecked")
	public StudioGridLayout(int[] widths, int[] heights)
    {
        this.widths = new int[widths.length+1];
        this.heights = new int[heights.length+1];
        this.colWeights = new int[widths.length+1];
        this.rowWeights = new int[heights.length+1];
        this.colElements = new ArrayList[widths.length+1];
        this.rowElements = new ArrayList[heights.length+1];
        System.arraycopy(widths, 0, this.widths, 1, widths.length);
        System.arraycopy(heights, 0, this.heights, 1, heights.length);

        componentToConstraint = new HashMap<Component, StudioGridConstraints>();
    }
    /**
     * Sets column width. A negative value means that the width is related to another col.
     */
    @SuppressWarnings("unchecked")
	public void setColumnWidth(int col, int width)
    {
        if(widths.length <= col)
        {
            widths = (int[]) resizeArray(widths,col+1);
            colWeights = (int[]) resizeArray(colWeights,col+1);
            colElements = (ArrayList[]) resizeArray(colElements,col+1);
        }
        widths[col] = width;
    }

    /**
     * Sets row height. A negative value means that the height is related to another row.
     */
    @SuppressWarnings("unchecked")
	public void setRowHeight(int row, int height)
    {
        if(heights.length <= row)
        {
            heights = (int[]) resizeArray(heights,row+1);
            rowWeights = (int[]) resizeArray(rowWeights,row+1);
            rowElements = (ArrayList[]) resizeArray(rowElements,row+1);
        }
        heights[row] = height;
    }

    /**
     * Sets weight of specified column. Weight determines distribution
     * of difference when resizing.
     * @param col index of column. Index must be > 0.
     */
    public void setColumnWeight(int col, int weight)
    {
        if (col >= widths.length)
        {
            throw new RuntimeException("Column index cannot be greater then " + (widths.length-1) + ".");
        }
        colWeights[col] = weight;
        updateSum(colWeights);
    }

    /**
     * Sets weight of specified row. Weight determines distribution
     * of difference when resizing.
     * @param row index of row. Index must be > 0.
     */
    public void setRowWeight(int row, int weight)
    {
        if (row >= heights.length)
        {
            throw new RuntimeException("Row index cannot be greater then " + (heights.length-1) + ".");
        }
        rowWeights[row] = weight;
        updateSum(rowWeights);
    }

    /**
     * @deprecated  replaced by <code>addLayoutComponent(Component, Object)</code>. Throws
     * <EM>UnsupportedOperationException</EM>.
     */
    public void addLayoutComponent(String name, Component comp)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the specified component from the layout.
     * @param comp the component to be removed
     */
    public void removeLayoutComponent(Component comp)
    {
        synchronized (comp.getTreeLock())
        {
            StudioGridConstraints c = componentToConstraint.remove(comp);
            if (c == null) return;
            colElements[c.col].remove(comp);
            rowElements[c.row].remove(comp);
        }
    }

    /**
     * Adds the specified component to the layout, using the DesignerGridConstraints
     * constraint object. Constraints object is copied so passed instance
     * can be safely modifed.
     * @param comp the component to be added
     * @param StudioGridConstraints object determining where/how the component is added to the layout.
     */
    public void addLayoutComponent(Component comp, Object constraints)
    {
        synchronized (comp.getTreeLock())
        {
            StudioGridConstraints dc = (StudioGridConstraints) constraints;
            if (dc.col >= widths.length)
            {
                throw new RuntimeException("Column index cannot be greater then " + (widths.length-1) + ".");
            }
            else if (dc.col + dc.width - 1 >= widths.length)
            {
                throw new RuntimeException("Width cannot be greater then " + (widths.length-1 - dc.col + 1) + ".");
            }
            else if (dc.row >= heights.length)
            {
                throw new RuntimeException("Row index cannot be greater then " + (heights.length-1) + ".");
            }
            else if (dc.row + dc.height - 1 >= heights.length)
            {
                throw new RuntimeException("Height cannot be greater then " + (heights.length-1 - dc.row + 1) + ".");
            }

            //Track only single row, or single col components, ignore spanners
            if (dc.width == 1)
            {
                if (colElements[dc.col] == null)
                {
                    colElements[dc.col] = new ArrayList<Component>();
                }
                colElements[dc.col].add(comp);
            }

            if (dc.height == 1)
            {
                if (rowElements[dc.row] == null)
                {
                    rowElements[dc.row] = new ArrayList<Component>();
                }
                rowElements[dc.row].add(comp);
            }

            //Copy the constaint and cache it
            componentToConstraint.put(comp, new StudioGridConstraints(dc));
        }
    }

    /**
     * Calculates the preferred size dimensions for the specified
     * container given the components in the specified parent container.
     * @param parent the component to be laid out
     */
    public Dimension preferredLayoutSize(Container target)
    {
        if(preferredLayoutSize == null)
        {
            synchronized (target.getTreeLock())
            {
                int width=calculateWidth(false),height=calculateHeight(false);
                Insets insets = target.getInsets();
                width += insets.left + insets.right;
                height += insets.top + insets.bottom;
                preferredLayoutSize = new Dimension(width, height);
            }
        }

        return preferredLayoutSize;
    }

    public Dimension minimumLayoutSize(Container target)
    {
        if(minimumLayoutSize == null)
        {
            synchronized (target.getTreeLock())
            {
                int width=calculateWidth(true),height=calculateHeight(true);
                Insets insets = target.getInsets();
                width += insets.left + insets.right;
                height += insets.top + insets.bottom;
                minimumLayoutSize = new Dimension(width, height);
            }
        }

        return minimumLayoutSize;
    }

    /**
     * Returns the maximum size of this component.
     * @see LayoutManager
     */
    public Dimension maximumLayoutSize(Container target)
    {
        synchronized (target.getTreeLock())
        {
            return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
    }

    public void layoutContainer(Container target)
    {
        synchronized (target.getTreeLock())
        {
            Dimension fullSize = target.getSize();
            Insets insets = target.getInsets();
            int fullWidth = fullSize.width-insets.left- insets.right;
            int fullHeight = fullSize.height-insets.top - insets.bottom;

            int xOrigins[] = getXOrigins(fullWidth, insets);
            int yOrigins[] = getYOrigins(fullHeight, insets);

            Component comps[] = target.getComponents();
            for (int i = 0,max=comps.length; i < max;i++)
            {
                Component comp = comps[i];
                StudioGridConstraints dc = componentToConstraint.get(comp);
                Dimension preferred = comp.getPreferredSize();
                int width = preferred.width;
                int height = preferred.height;
                int cellWidth;
                int cellHeight;

                if(dc==null)
                {
                    System.err.println("No constraints for "+comp+" in "+target);
                    continue;
                }

                if (dc.width < 0)//fixed pixels
                {
                    width = -dc.width;
                    cellWidth = xOrigins[dc.col + 1] - xOrigins[dc.col];
                }
                else
                {
                    width += dc.widthCorrection;
                    cellWidth = xOrigins[dc.col + dc.width] - xOrigins[dc.col];
                }

                if (dc.height < 0)//fixed pixels
                {
                    height = -dc.height;
                    cellHeight = yOrigins[dc.row + 1] - yOrigins[dc.row];
                }
                else
                {
                    height += dc.heightCorrection;
                    cellHeight = yOrigins[dc.row + dc.height] - yOrigins[dc.row];
                }

                float diffWidth = ((float) (cellWidth - width)) / 2.0f;
                float diffHeight = ((float) (cellHeight - height)) / 2.0f;

                float compX = (float) xOrigins[dc.col] + diffWidth;
                float compY = (float) yOrigins[dc.row] + diffHeight;
                String anchor = dc.anchor;
                boolean xSize = false;
                boolean ySize = false;

                for (int j = anchor.length() - 1; j >= 0; j--)
                {
                    if (anchor.charAt(j) == 'l')
                    {
                        compX = (float) xOrigins[dc.col];
                        if (xSize) width = cellWidth;
                        xSize = true;
                    }
                    else if (anchor.charAt(j) == 'r')
                    {
                        if (xSize)
                            width = cellWidth;
                        else
                            compX += diffWidth;
                        xSize = true;
                    }
                    else if (anchor.charAt(j) == 't')
                    {
                        compY = (float) yOrigins[dc.row];
                        if (ySize) height = cellHeight;
                        ySize = true;
                    }
                    else if (anchor.charAt(j) == 'b')
                    {
                        if (ySize)
                            height = cellHeight;
                        else
                            compY += diffHeight;
                        ySize = true;
                    }
                    else
                    {
                        throw new RuntimeException("Bad character in anchor "+anchor.charAt(j)+".");
                    }
                }

                comp.setBounds((int)compX + dc.colCorrection, (int) compY + dc.rowCorrection, width, height);
            }
        }
    }

    /**
     * Returns 0.
     */
    public float getLayoutAlignmentX(Container target)
    {
        return 0.0f;
    }

    /**
     * Returns 0.
     */
    public float getLayoutAlignmentY(Container target)
    {
        return 0.0f;
    }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     */
    public void invalidateLayout(Container target)
    {
        preferredLayoutSize = null;
        minimumLayoutSize = null;
        xOrigins = null;
        yOrigins = null;
    }

    private Object resizeArray(Object array, int newSize)
    {
        Object newArray = java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), newSize);
        System.arraycopy(array, 0, newArray, 0, java.lang.reflect.Array.getLength(array));
        return newArray;
    }

    private void updateSum(int[] array)
    {
        array[0] = 0;

        for(int i=1,max=array.length;i<max;i++)
        {
            array[0] += array[i];
        }
    }

    private int getSum(int[] array)
    {
        return array[0];
    }

    private int[] getXOrigins(int width, Insets insets)
    {
        if (xOrigins == null)
        {
            int[] prefColWidths = calculateWidths(false) ;
            int[] minColWidths = calculateWidths(true);

            distributeSizeDifference(width, prefColWidths, minColWidths, colWeights);

            int origins[] = new int[widths.length+1];

            origins[1] = (insets == null) ? 0 : insets.left;

            for (int i = 2; i < origins.length; i++) origins[i] = origins[i - 1] + prefColWidths[i - 1];

            xOrigins = origins;
        }

        return xOrigins;
    }

    private int[] getYOrigins(int height, Insets insets)
    {
        if (yOrigins == null)
        {
            int[] prefRowHeights = calculateHeights(false) ;
            int[] minRowHeights = calculateHeights(true);

            distributeSizeDifference(height, prefRowHeights, minRowHeights, rowWeights);

            int origins[] = new int[heights.length+1];

            origins[1] = (insets == null) ? 0 : insets.top;

            for (int i = 2; i < origins.length; i++) origins[i] = origins[i - 1] + prefRowHeights[i - 1];

            this.yOrigins = origins;
        }

        return yOrigins;
    }

    private void handleRelativeValues(int fixedValues[], int calcValues[])
    {
        short path[] = new short[fixedValues.length];
        int counter = 0;

        /* marks of visited vertices. 0 - not visited, 1 - visited, 2 - visited and set final value */
        byte visited[] = new byte[fixedValues.length];

        for (int i = fixedValues.length - 1; i > 0; i--)
        {
            if ((fixedValues[i] < 0) && (visited[i] == 0))//this is a relative value, we haven't worked out yet
            {
                int curIndex = i;

                counter = 0;
                int maxLength = 0;
                int prevIndex;

                do
                {
                    maxLength = (calcValues[curIndex] > maxLength) ? calcValues[curIndex] : maxLength;
                    path[counter++] = (short) curIndex;
                    visited[curIndex] = 1;
                    prevIndex = curIndex;
                    curIndex = -fixedValues[curIndex];
                }
                while ((curIndex > 0) && (visited[curIndex] == 0));

                if (curIndex <= 0)
                {
                    maxLength = calcValues[prevIndex];
                }
                else if (curIndex == 0)
                {
                    maxLength = calcValues[prevIndex];
                }
                else if (visited[curIndex] == 1)
                {
                    int start = curIndex;
                    maxLength = 0;
                    do
                    {
                        maxLength = (calcValues[curIndex] > maxLength) ? calcValues[curIndex] : maxLength;
                        curIndex = -fixedValues[curIndex];
                    }
                    while (start != curIndex);
                }
                else if (visited[curIndex] == 2)//visited and done
                {
                    maxLength = calcValues[curIndex];
                }
                else
                {
                    throw new RuntimeException("Illegal values in constraints, widths or heights.");
                }

                while (counter > 0)
                {
                    calcValues[path[--counter]] = (short) maxLength;
                    visited[path[counter]] = 2;
                }
            }
        }
    }

    private int[] calculateWidths(boolean min)
    {
        int tmp[] = new int[widths.length];

        for (int i = 1; i < widths.length; i++)
        {
            if (widths[i] > 0)
            {
                tmp[i] = widths[i];
            }
            else
            {
                int maxWidth = 0;
                ArrayList<Component> elements = colElements[i];
                if (elements != null)
                {
                    for(int j=0,max=elements.size();j<max;j++)
                    {
                        Component c = (Component) elements.get(j);
                        int w = c.isVisible() ? (min?c.getMinimumSize().width:c.getPreferredSize().width) : 0;
                        if(w > 0)
                        {
                            StudioGridConstraints dc = componentToConstraint.get(c);

                            if (dc.width < 0)//it is in pixels
                                w = -dc.width;
                            else
                                w += dc.widthCorrection;
                        }
                        maxWidth = Math.max(w,maxWidth);
                    }
                }
                tmp[i]=maxWidth;
            }
        }

        handleRelativeValues(widths,tmp);
        updateSum(tmp);
        return tmp;
    }

    private int calculateWidth(boolean min)
    {
        int[] tmp = calculateWidths(min);
        return getSum(tmp);
    }

    private int[] calculateHeights(boolean min)
    {
        int tmp[] = new int[heights.length];

        for (int i = 1; i < heights.length; i++)
        {
            if (heights[i] > 0)
            {
                tmp[i] = heights[i];
            }
            else
            {
                int maxHeight = 0;
                ArrayList<Component> elements = rowElements[i];
                if (elements != null)
                {
                    for(int j=0,max=elements.size();j<max;j++)
                    {
                        Component c = (Component) elements.get(j);
                        int w = c.isVisible() ? (min?c.getMinimumSize().height:c.getPreferredSize().height) : 0;
                        if(w > 0)
                        {
                            StudioGridConstraints dc = componentToConstraint.get(c);

                            if (dc.height < 0)//it is in pixels
                                w = -dc.height;
                            else
                                w += dc.heightCorrection;
                        }
                        maxHeight = Math.max(w,maxHeight);
                    }
                }
                tmp[i] =maxHeight;
            }
        }
        handleRelativeValues(heights,tmp);
        updateSum(tmp);
        return tmp;
    }

    private int calculateHeight(boolean min)
    {
        int[] tmp = calculateHeights(min);
        return getSum(tmp);
    }

    private void distributeSizeDifference(int desiredLength, int[] lengths, int[] minLengths, int[] weights)
    {
        int preferred = 0;
        int newLength;

        for (int i = lengths.length - 1; i > 0; i--) preferred += lengths[i];

        double unit = ((double) (desiredLength - preferred)) / (double) ((getSum(weights)==0)?weights.length-1:getSum(weights));

        for (int i = lengths.length - 1; i > 0; i--)
        {
            newLength = lengths[i] + (int) (unit * (double) weights[i]);
            lengths[i] = (newLength > minLengths[i]) ? (short) newLength : minLengths[i];
        }
    }
}
