package com.tibco.cep.studio.ui.views;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.utils.FunctionHelpBundle;

public class FunctionHoverMouseListener implements MouseTrackListener,	MouseListener {

	private static FunctionHoverMouseListener mlistener;
	private FunctionHelpBundle bundle;

	private FunctionHoverMouseListener(FunctionHelpBundle bundle) {
		this.bundle = bundle;
	};

	public static FunctionHoverMouseListener getInstance(FunctionHelpBundle bundle) {
		if (mlistener == null)
			mlistener = new FunctionHoverMouseListener(bundle);
		mlistener.bundle = bundle;
		return mlistener;
	}

	public void mouseEnter(MouseEvent event) {
		// ignore
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseTrackListener#mouseExit(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseExit(MouseEvent event) {
		FunctionHover.getInstance().clearHover();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseTrackListener#mouseHover(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseHover(MouseEvent event) {
		Widget widget = event.widget;
		FunctionHover.getInstance().clearHover();
		if (widget != null && widget instanceof Tree) {
			Tree tree = (Tree) widget;
			//tree.setFocus();
			Rectangle r = tree.getParent().getBounds();
			if (tree.getParent().getParent() != null) {
				r = tree.getParent().getParent().getBounds();
			}
			Point pt = new Point(event.x, event.y);
			int hoverX = r.x + event.x - (FunctionHover.HOVER_WIDTH + 25);
			if (hoverX < 0) {
				hoverX = r.x + tree.getParent().getBounds().width;
			}
			Point hoverLocation = new Point(hoverX, r.y + event.y);
			widget = tree.getItem(pt);
			if (widget instanceof TreeItem) {
				TreeItem treeItem = (TreeItem) widget;
				if(treeItem.getData() instanceof Predicate)
					FunctionHover.getInstance().drawHover(treeItem, hoverLocation, bundle);
			}
		}
	}

	public void mouseDoubleClick(MouseEvent event) {
		// ignore
	}

	public void mouseDown(MouseEvent event) {
		mouseExit(event);
	}

	public void mouseUp(MouseEvent event) {
		// ignore
	}

	public void updateBundle(FunctionHelpBundle helpBundle) {
		mlistener.bundle = helpBundle;
	}

}
