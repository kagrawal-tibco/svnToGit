package com.tibco.cep.studio.mapper.ui.utils;

public class StudioGridConstraints implements java.io.Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final String RLTB="rltb";
    protected int row;
    protected int col;
    protected int width;
    protected int height;
    protected int rowCorrection;
    protected int colCorrection;
    protected int widthCorrection;
    protected int heightCorrection;
    protected String anchor;

    StudioGridConstraints(StudioGridConstraints other)
    {
        this();
        row = other.row;
        col = other.col;
        width = other.width;
        height = other.height;
        rowCorrection = other.rowCorrection;
        colCorrection = other.colCorrection;
        widthCorrection = other.widthCorrection;
        heightCorrection = other.heightCorrection;
        anchor = other.anchor;
    }

    public StudioGridConstraints()
    {
        anchor = RLTB;
    }

    /**
     * Set horizontal correction until changed or cleared. When layout manager
     * takes component's preferred size it will add to it passed width correction;
     * after positioning it will change position about position correction.
     * @param x correction of horizontal position, in pixels. Can be negative.
     * @param w correction of width, in pixels. Can be negative.
     * @return this
     */
    public StudioGridConstraints setHCorrection(int x, int w)
    {
        colCorrection = x;
        widthCorrection = w;
        return this;
    }

    /**
     * Set vertical correction until changed or cleared. When layout manager
     * takes component's preferred size it will add to it passed height correction;
     * after positioning it will change position about position correction.
     * @param y correction of vertical position, in pixels. Can be negative.
     * @param h correction of height, in pixels. Can be negative.
     * @return this
     */
    public StudioGridConstraints setVCorrection(int y, int h)
    {
        rowCorrection = y;
        heightCorrection = h;
        return this;
    }

    /**
     * Clears all corrections.
     * @return this
     */
    public StudioGridConstraints clearCorrection()
    {
        rowCorrection = 0;
        colCorrection = 0;
        widthCorrection = 0;
        heightCorrection = 0;
        return this;
    }

    /**
     * Increases current row index by one. Preserves all previous settings.
     * @return this
     */
    public StudioGridConstraints nextRow()
    {
        row += 1;
        return this;
    }

    /**
     * Increases current row index by two. Preserves all previous settings.
     * @return this
     */
    public StudioGridConstraints next2Row()
    {
        row += 2;
        return this;
    }

    /**
     * Increases current column index by one. Preserves all previous settings.
     * @return this
     */
    public StudioGridConstraints nextCol()
    {
        col +=1;
        return this;
    }

    /**
     * Increases current column index by two. Preserves all previous settings.
     * @return this
     */
    public StudioGridConstraints next2Col()
    {
        col+=2;
        return this;
    }

    /**
     * Sets column index. Preserves row index, sets anchor to "rltb", width and height to 1.
     * @param x column index
     * @return this
     */
    public StudioGridConstraints x(int x)
    {
        anchor = RLTB;
        col = x;
        width = 1;
        height = 1;
        return this;
    }

    /**
     * Sets column index. Preserves row index, sets anchor to "rltb", width and height to 1.
     * @param c column index
     * @return this
     */
    public StudioGridConstraints c(int c)
    {
        return x(c);
    }

    /**
     * Sets column index and anchors. Preserves row index, sets width and height to 1.
     * @param x column index
     * @param anchors anchors string (of letters 'l','r','t','b')
     * @return this
     */
    public StudioGridConstraints x(int x, String anchors)
    {
        anchor = anchors;
        col = x;
        width = 1;
        height = 1;
        return this;
    }

    /**
     * Sets column index and anchors. Preserves row index, sets width and height to 1.
     * @param c column index
     * @param anchors anchors string (of letters 'l','r','t','b')
     * @return this
     */
    public StudioGridConstraints c(int c, String anchors)
    {
        return x(c,anchors);
    }

    /**
     * Sets column index, target area width and height. Preserves row index, sets anchors string to "lrtb".
     * @param x column index
     * @param w width, number of columns
     * @param h height, number of rows
     * @return this
     */
    public StudioGridConstraints xwh(int x, int w, int h)
    {
        anchor = RLTB;
        col = x;
        width = w;
        height = h;
        return this;
    }

    /**
     * Sets column index, target area width and height. Preserves row index, sets anchors string to "lrtb".
     * @param c column index
     * @param w width, number of columns
     * @param h height, number of rows
     * @return this
     */
    public StudioGridConstraints cwh(int c, int w, int h)
    {
        return xwh(c,w,h);
    }

    /**
     * Sets column index, target area width, height and anchors. Preserver row index.
     * @param x column index
     * @param w width, number of columns
     * @param h height, number of rows
     * @param anchors anchors string (of letters 'l','r','t','b')
     * @return this
     */
    public StudioGridConstraints xwh(int x, int w, int h, String anchors)
    {
        anchor = anchors;
        col = x;
        width = w;
        height = h;
        return this;
    }

    /**
     * Sets column index, target area width, height and anchors. Preserver row index.
     * @param c column index
     * @param w width, number of columns
     * @param h height, number of rows
     * @param anchors anchors string (of letters 'l','r','t','b')
     * @return this
     */
    public StudioGridConstraints cwh(int c, int w, int h, String anchors)
    {
        return xwh(c,w,h,anchors);
    }

    /**
     * Sets row index. Preserves column index, sets anchor to "rltb", width and height to 1.
     * @param y row index
     * @return this
     */
    public StudioGridConstraints y(int y)
    {
        anchor = RLTB;
        row = y;
        width = 1;
        height = 1;
        return this;
    }

    /**
     * Sets row index. Preserves column index, sets anchor to "rltb", width and height to 1.
     * @param r row index
     * @return this
     */
    public StudioGridConstraints r(int r)
    {
        return y(r);
    }

    /**
     * Sets row index and anchors. Preserves column index, width and height to 1.
     * @param y row index
     * @param anchors anchors string (of letters 'l','r','t','b')
     * @return this
     */
    public StudioGridConstraints y(int y, String anchors)
    {
        anchor = anchors;
        row = y;
        width = 1;
        height = 1;
        return this;
    }

    /**
     * Sets row index and anchors. Preserves column index, width and height to 1.
     * @param r row index
     * @param anchors anchors string (of letters 'l','r','t','b')
     * @return this
     */
    public StudioGridConstraints r(int r, String anchors)
    {
        return y(r,anchors);
    }

    /**
     * Sets row index, target area width and height. Preserves row index, sets anchors string to "lrtb".
     * @param y row index
     * @param w width, number of columns
     * @param h height, number of rows
     * @return this
     */
    public StudioGridConstraints ywh(int y, int w, int h)
    {
        anchor = RLTB;
        row = y;
        width = w;
        height = h;
        return this;
    }

    /**
     * Sets row index, target area width and height. Preserves row index, sets anchors string to "lrtb".
     * @param r row index
     * @param w width, number of columns
     * @param h height, number of rows
     * @return this
     */
    public StudioGridConstraints rwh(int r, int w, int h)
    {
        return ywh(r,w,h);
    }

    /**
     * Sets row index, target area width, height and anchors. Preserver column index.
     * @param y row index
     * @param w width, number of columns
     * @param h height, number of rows
     * @param anchors anchors string (of letters 'l','r','t','b')
     * @return this
     */
    public StudioGridConstraints ywh(int y, int w, int h, String anchors)
    {
        anchor = anchors;
        row = y;
        width = w;
        height = h;
        return this;
    }

    /**
     * Sets row index, target area width, height and anchors. Preserver column index.
     * @param r row index
     * @param w width, number of columns
     * @param h height, number of rows
     * @param anchors anchors string (of letters 'l','r','t','b')
     * @return this
     */
    public StudioGridConstraints rwh(int r, int w, int h, String anchors)
    {
        return ywh(r,w,h,anchors);
    }

    /**
     * Sets row and column index. Sets anchor to "rltb", width and height to 1.
     * @param x column index
     * @param y row index
     * @return this
     */
    public StudioGridConstraints xy(int x, int y)
    {
        anchor = RLTB;
        col = x;
        row = y;
        width = 1;
        height = 1;
        return this;
    }

    /**
     * Sets row and column index. Sets anchor to "rltb", width and height to 1.
     * @param r row index
     * @param c column index
     * @return this
     */
    public StudioGridConstraints rc(int r, int c)
    {
        return xy(c,r);
    }

    /**
     * Sets row and column index and anchors. Sets width and height to 1.
     * @param x column index
     * @param y row index
     * @param anchors anchors string (of letters 'l','r','t','b')
     * @return this
     */
    public StudioGridConstraints xy(int x, int y, String anchors)
    {
        anchor = anchors;
        col = x;
        row = y;
        width = 1;
        height = 1;
        return this;
    }

    /**
     * Sets row and column index and anchors. Sets width and height to 1.
     * @param r row index
     * @param c column index
     * @param anchors anchors string (of letters 'l','r','t','b')
     * @return this
     */
    public StudioGridConstraints rc(int r, int c, String anchors)
    {
        return xy(c,r,anchors);
    }

    /**
     * Sets row and column index, width and height. Sets anchors to "lrtb".
     * @param x column index
     * @param y row index
     * @param w width, number of columns
     * @param h height, number of rows
     * @return this
     */
    public StudioGridConstraints xywh(int x, int y, int w, int h)
    {
        anchor = RLTB;
        col = x;
        row = y;
        width = w;
        height = h;
        return this;
    }

    /**
     * Sets row and column index, width and height. Sets anchors to "lrtb".
     * @param r row index
     * @param c column index
     * @param w width, number of columns
     * @param h height, number of rows
     * @return this
     */
    public StudioGridConstraints rcwh(int r, int c, int w, int h)
    {
        return xywh(c,r,w,h);
    }

    /**
     * Sets row and column index, width, height and anchors.
     * @param x column index
     * @param y row index
     * @param w width, number of columns
     * @param h height, number of rows
     * @param anchors anchors string (of letters 'l','r','t','b')
     * @return this
     */
    public StudioGridConstraints xywh(int x, int y, int w, int h, String anchors)
    {
        anchor = anchors;
        col = x;
        row = y;
        width = w;
        height = h;
        return this;
    }

    /**
     * Sets row and column index, width, height and anchors.
     * @param r row index
     * @param c column index
     * @param w width, number of columns
     * @param h height, number of rows
     * @param anchors anchors string (of letters 'l','r','t','b')
     * @return this
     */
    public StudioGridConstraints rcwh(int r, int c, int w, int h, String anchors)
    {
        return xywh(c,r,w,h,anchors);
    }

    /**
     * Sets width to absolute size in pixels. Preserves all other settings.
     * <br>
     * Negative values are used in the instance variable to indicate pixels vs rows/cols.
     * @return this
     */
    public StudioGridConstraints W(int w)
    {
        width = -w;
        return this;
    }

    /**
     * Sets width to absolute size in pixels. Preserves all other settings.
     * <br>
     * Negative values are used in the instance variable to indicate pixels vs rows/cols.
     * @return this
     */
    public StudioGridConstraints H(int h)
    {
        height = -h;
        return this;
    }

    /**
     * Sets anchors string. Preserves all other settings.
     * @param anchors anchors string (of letters 'l','r','t','b')
     * @return this
     */
    public StudioGridConstraints anchors(String anchors)
    {
        anchor = anchors;
        return this;
    }

    /**
     * Returns current column index.
     */
    public int x()
    {
        return col;
    }

    /**
     * Returns current row index.
     */
    public int y()
    {
        return row;
    }

    /**
     * Returns current column index.
     */
    public int c()
    {
        return col;
    }

    /**
     * Returns current row index.
     */
    public int r()
    {
        return row;
    }
}

