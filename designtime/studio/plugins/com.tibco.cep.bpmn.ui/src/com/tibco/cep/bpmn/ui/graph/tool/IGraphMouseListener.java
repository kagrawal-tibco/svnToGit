package com.tibco.cep.bpmn.ui.graph.tool;

public interface IGraphMouseListener {
	
	
	public static final int MOUSE_MOVED = 0;
	public static final int MOUSE_PRESSED = 1;
	public static final int MOUSE_RELEASED = 2;
	public static final int MOUSE_CLICKED = 3;
	public static final int MOUSE_DRAGGED = 4;
	public static final int MOUSE_WHEEL_MOVED = 5;
	public static final int MOUSE_ENTERED = 6;
	public static final int MOUSE_EXITED = 7;

	void onMouseClicked(org.eclipse.swt.events.MouseEvent event);

	void onMouseReleased(org.eclipse.swt.events.MouseEvent event);

	void onMousePressed(org.eclipse.swt.events.MouseEvent event);

	void onMouseMoved(org.eclipse.swt.events.MouseEvent event);

	void onMouseDragged(org.eclipse.swt.events.MouseEvent event);

	void onMouseWheelMoved(org.eclipse.swt.events.MouseEvent event);

	void onMouseEntered(org.eclipse.swt.events.MouseEvent event);

	void onMouseExited(org.eclipse.swt.events.MouseEvent event);

}
