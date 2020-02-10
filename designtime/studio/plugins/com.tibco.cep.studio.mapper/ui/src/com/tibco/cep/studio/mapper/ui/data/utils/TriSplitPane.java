package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JSplitPane;

/**
 * A 3 pane split pane that sizes itself well, and can operate in 2 pane mode.
 */
public class TriSplitPane extends JComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mCurrentCount;
    private boolean mContinuousLayout;

    private Component mLeft;
    private Component mMiddle;
    private Component mRight;
    private JSplitPane mLeftSplit;
    private JSplitPane mRightSplit;
    private JSplitPane mTwoSplit;

    private double mTwoDividerResizeWeight=.5;

    private int mTwoDividerLocation = 100;
    private int mLeftDividerLocation = 100;
    private int mRightDividerLocation = 100;

    public TriSplitPane() {
        setLayout(new BorderLayout());
    }

    public void setContinuousLayout(boolean val) {
        mContinuousLayout = val;
        if (mLeftSplit!=null) {
            mLeftSplit.setContinuousLayout(val);
        }
        if (mRightSplit!=null) {
            mRightSplit.setContinuousLayout(val);
        }
        if (mTwoSplit!=null) {
            mTwoSplit.setContinuousLayout(val);
        }
    }

    public void setRightResizeWeight(double weight) {
        //mRightResizeWeight = weight;
        if (mRightSplit!=null) {
            mRightSplit.setResizeWeight(weight);
        }
    }

    public void setLeftResizeWeight(double weight) {
        //mLeftResizeWeight = weight;
        if (mLeftSplit!=null) {
            mLeftSplit.setResizeWeight(weight);
        }
    }

    public int getLeftDividerLocation() {
        if (mLeftSplit!=null) {
            return mLeftSplit.getDividerLocation();
        }
        return mLeftDividerLocation;
    }

    public void setLeftDividerLocation(int loc) {
        if (mLeftSplit!=null) {
            mLeftSplit.setDividerLocation(loc);
        } else {
            mLeftDividerLocation = loc;
        }
    }

    public int getRightDividerLocation() {
        if (mRightSplit!=null) {
            return mRightSplit.getDividerLocation();
        }
        return mRightDividerLocation;
    }

    public void setRightDividerLocation(int loc) {
        if (mRightSplit!=null) {
            mRightSplit.setDividerLocation(loc);
        } else {
            mRightDividerLocation = loc;
        }
    }

    public void setTwoPaneDividerLocation(int loc) {
        if (mTwoSplit!=null) {
            mTwoSplit.setDividerLocation(loc);
        } else {
            mTwoDividerLocation = loc;
        }
    }

    public int getTwoPaneDividerLocation() {
        if (mTwoSplit!=null) {
            return mTwoSplit.getDividerLocation();
        } else {
            return mTwoDividerLocation;
        }
    }

    public void setLeftComponent(Component c) {
        mLeft = c;
        rebuild();
    }

    public void setMiddleComponent(Component c) {
        mMiddle = c;
        rebuild();
    }

    public void setRightComponent(Component c) {
        mRight = c;
        rebuild();
    }

    private void rebuild() {
        int ct=0;
        Component[] c = new Component[3];
        if (mLeft!=null) {
            c[ct] = mLeft;
            ct++;
        }
        if (mMiddle!=null) {
            c[ct] = mMiddle;
            ct++;
        }
        if (mRight!=null) {
            c[ct] = mRight;
            ct++;
        }
        if (ct!=mCurrentCount) {
            mCurrentCount = ct;
            removeAll();
        }
        if (ct==1) {
            removeAll();
            add(c[0]);
            revalidate();
            repaint();
            return;
        }
        if (ct==2) {
            if (mTwoSplit==null) {
                mTwoSplit = new JSplitPane();
                mTwoSplit.setBorder(BorderFactory.createEmptyBorder());
                mTwoSplit.setContinuousLayout(mContinuousLayout);
                mTwoSplit.setResizeWeight(mTwoDividerResizeWeight);
                mTwoSplit.setDividerLocation(mTwoDividerLocation);
            }
            add(mTwoSplit);
        }
        if (ct==3) {
            if (mRightSplit==null) {
                mRightSplit = new JSplitPane();
                mRightSplit.setBorder(BorderFactory.createEmptyBorder());
                mRightSplit.setContinuousLayout(mContinuousLayout);
                mLeftSplit = new JSplitPane();
                mLeftSplit.setBorder(BorderFactory.createEmptyBorder());
                mLeftSplit.setContinuousLayout(mContinuousLayout);
                mLeftSplit.setRightComponent(mRightSplit);
            }
            add(mLeftSplit);
        }
        if (ct==2) {
            mTwoSplit.setLeftComponent(c[0]);
            mTwoSplit.setRightComponent(c[1]);
            if (mLeftSplit!=null) {
                mLeftDividerLocation = mLeftSplit.getDividerLocation();
                mRightDividerLocation = mRightSplit.getDividerLocation();
            }
            mTwoSplit.setDividerLocation(mTwoDividerLocation);
        }
        if (ct==3) {
            if (mTwoSplit!=null) {
                mTwoDividerLocation = mTwoSplit.getDividerLocation();
            }
            mLeftSplit.setLeftComponent(c[0]);
            mRightSplit.setLeftComponent(c[1]);
            mRightSplit.setRightComponent(c[2]);
            mLeftSplit.setResizeWeight(0);
            mRightSplit.setResizeWeight(1);

            mLeftSplit.setDividerLocation(mLeftDividerLocation);
            mRightSplit.setDividerLocation(mRightDividerLocation);
        }
        revalidate();
        repaint();
    }
}
