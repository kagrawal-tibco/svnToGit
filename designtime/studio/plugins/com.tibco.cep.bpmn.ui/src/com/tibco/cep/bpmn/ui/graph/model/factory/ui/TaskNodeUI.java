package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.RECEIVE_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SEND_TASK;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.image.ImageObserver;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.BPMNCommonImages.IMAGE_SIZE;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.preferences.BpmnPreferenceConstants;
import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.ui.RoundRectNodeUI;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;

/**
 * 
 * @author ggrigore
 *
 */
public class TaskNodeUI extends RoundRectNodeUI implements ImageObserver {
	
	private static final long serialVersionUID = 1L;
	private boolean drawChildGraphMark;
	private boolean drawMinus;

	static {
		TSEImage.setLoaderClass(TaskNodeUI.class);			
	}
	
	private static TSEImage JAVA_IMAGE = new TSEImage("/icons/javadev_obj.gif");
	private static TSEImage RF_IMAGE = new TSEImage("/icons/rule-function_48x48.png");
	// private static TSEImage TABLE_IMAGE = new TSEImage("/icons/table_48x48.png");
	private static TSEImage CQ_IMAGE = new TSEImage("/icons/continuousQuery49x35.png");
	private static TSEImage SQ_IMAGE = new TSEImage("/icons/snapshotQuery46x50.png");
	
	public static final TSEImage SEND_EVENT_IMAGE = new TSEImage(TaskNodeUI.class, "/icons/sendEvent16x16.png");
	public static final TSEImage RECEIVE_EVENT_IMAGE = new TSEImage(TaskNodeUI.class, "/icons/receiveEvent16x16.png");
	public static final TSEImage BE_SCRIPT_IMAGE = new TSEImage("/icons/rule-function.png");
	public static final TSEImage SCRIPT_IMAGE = new TSEImage("/icons/scripttask_16x16.gif");
	public static final TSEImage TABLE_IMAGE = new TSEImage(TaskNodeUI.class, "/icons/new_table_16x16.png");
	public static final TSEImage EVENT_IMAGE = new TSEImage(TaskNodeUI.class, "/icons/simple-event.png");
	public static final TSEImage RULE_IMAGE = new TSEImage(TaskNodeUI.class, "/icons/rules.png");
	public static final TSEImage WS_IMAGE = new TSEImage(TaskNodeUI.class, "/icons/sphere.gif");
	public static final TSEImage MANUAL_IMAGE = new TSEImage(TaskNodeUI.class, "/icons/sample.gif");

	@SuppressWarnings("unused")
	private static TSEImage RADIO_ON_IMAGE = new TSEImage("/icons/radio_button_selected.gif");
	@SuppressWarnings("unused")
	private static TSEImage RADIO_OFF_IMAGE = new TSEImage("/icons/radio_button_unselected.gif");
	@SuppressWarnings("unused")
	private static TSEImage RADIO_BRK_HIT_IMAGE = new TSEImage("/icons/radio_button_brkhit.gif");
	
	public static int TYPE_NONE = -1;
	public static int TYPE_RF = 0;
	public static int TYPE_JAVA = 1;
	public static int TYPE_TABLE = 2;
	public static int TYPE_CQ = 3;
	public static int TYPE_SQ = 4;
	
	// This constant represents the size of the marks that are drawn
	private static final int MARK_SIZE = 50;
	private static final int TEXT_OFFSET = MARK_SIZE / 2;
	 // This constant represents the distance the marks are from the bounds of the node
	private static final int MARK_GAP = 2;
	
	private static boolean SHOW_TASK_ICONS = true;
	
	private TSEImage image;
	protected TSEImage badgeImage = null;
	protected boolean displayNameAttribute;
	boolean isLoop = false;
	boolean isMultiple = false;
	boolean isCompensation = false;
	boolean isSequential = false;

	public TaskNodeUI() {
		// TSEImage.setLoaderClass(this.getClass());	
		
		this.drawChildGraphMark = true;
		this.drawMinus = true;
		
		// TODO: turned off until they work better with rounded rectangles
		this.setBorderDrawn(true);
		//this.setDrawGlow(false);
		
		// turned off at Suresh's request to not draw inside contents
		// read from preference if we should fill in task node UIs or not
		boolean fillTaskIcons = false;

		//TODO: remove below commented code 
//		BpmnUIPlugin plugInstance = BpmnUIPlugin.getDefault();
//		if (plugInstance != null) {
//			IPreferenceStore store = plugInstance.getPreferenceStore();
//			fillTaskIcons = store.getBoolean(BpmnPreferenceConstants.PREF_FILL_TASK_NODES);
//		}
//		else {
//			// TODO: also drive web-based from some preference
//			fillTaskIcons = false;
//		}
//		if (fillTaskIcons /* || BpmnUIPlugin.getDefault() == null */) {
//			this.setDrawGradient(true);
//		}
//		else {
//			this.setDrawGradient(false);
//			this.setBorderWidth(4);			
//		}
	
		this.setDrawShadow(false);
		
		// TBS-like color:
		this.setFillColor(getColor());
		// super.setDrawTag(true);
		this.displayNameAttribute = true;
	}
	
	public void setFillTaskIcons(boolean fillTaskIcons) {
		if (fillTaskIcons) {
			this.setDrawGradient(true);
		}
		else {
			this.setDrawGradient(false);
			this.setBorderWidth(4);			
		}
	}

	public TaskNodeUI(TSEImage image) {
		this();
		this.image = image;
	}	
	
	public TaskNodeUI(int type) {
		this();
		if (type == TYPE_RF) {
			this.image = RF_IMAGE;
		}
		else if (type == TYPE_JAVA) {
			this.image = JAVA_IMAGE;
		}
		else if (type == TYPE_TABLE) {
			this.image = TABLE_IMAGE;
		}
		else if (type == TYPE_CQ) {
			this.image = CQ_IMAGE;
		}
		else if (type == TYPE_SQ) {
			this.image = SQ_IMAGE;
		}
		else if (type == TYPE_NONE) {
			this.image = null;
		}
		else {
			DiagrammingPlugin.logErrorMessage("UNKNOWN activity type.");
		}
	}
	
	public void setBadgeImage(TSEImage badgeImage) {
		this.badgeImage = badgeImage;
	}
	
	public TSEImage getBadgeImage() {
		return this.badgeImage;
	}	
	

	public void setDrawMinus(boolean drawMinus) {
		this.drawMinus = drawMinus;
	}
	
	public void draw(TSEGraphics graphics) {
		super.draw(graphics);
		this.drawChildGraphMark(graphics);
		
		drawImageBadge(graphics);
		
		if (isShowBreakPoints()) {
			if (isInputBreakpointHit()) {
				drawBreakpointHit(graphics, true, Color.RED);
			} else if (isOutputBreakpointHit()) {
				drawBreakpointHit(graphics, false, Color.RED);
			} else {
				this.drawInputBreakpoints(graphics, isInputBreakpoint(), isInputBreakPointToggle(), getBreakpointColor());
				this.drawOutputBreakpoints(graphics,isOutBreakpoint(), isOutputBreakPointToggle(), getBreakpointColor());
			}
		}
		
		// drawToolArea(graphics);
		if (isDrawError()) {
			drawError(graphics, Color.RED, 0);
		}
	}
	
	public void drawChildGraphMark(TSEGraphics graphics)
	{
		if (this.drawChildGraphMark)
		{
		    if (this.drawMinus)
		    {
//		        this.drawMark(graphics,
//			            TSENodeUI.DEFAULT_CHILD_GRAPH_COLLAPSE_MARK_COLOR,
//			            TSEColor.white,
//						this.getLocalChildGraphMarkBounds(),
//						true);
		        this.drawMark(graphics,
			            new TSEColor(2, 27, 255),
			            TSEColor.white,
						this.getLocalChildGraphMarkBounds(),
						true);		    	
		    }
		    else
		    {
//		        this.drawMark(graphics,
//		            TSENodeUI.DEFAULT_CHILD_GRAPH_EXPAND_MARK_COLOR,
//		            TSEColor.white,
//					this.getLocalChildGraphMarkBounds(),
//					false);
		        this.drawMark(graphics,
			            new TSEColor(255, 110, 0),
			            TSEColor.white,
						this.getLocalChildGraphMarkBounds(),
						false);
		    }
		}
	}
	
	/**
	 * This method is used to draw a box with a plug sign in the center
	 * with the given bounds and with the given colors
	 */
	private void drawMark(TSEGraphics graphics,
		TSEColor backgroundColor,
		TSEColor crossColor,
		TSRect localMarkBounds,
		boolean isCollapseMarker)
	{
		Shape oldClip = graphics.getClip();

		graphics.clipRect(this.getOwnerNode().getLocalBounds());

		TSTransform transform = graphics.getTSTransform();

		int width = transform.widthToDevice(localMarkBounds.getWidth());
		int height = transform.heightToDevice(localMarkBounds.getHeight());
		
//		width = transform.widthToDevice(52);
//		height = transform.heightToDevice(52);
		
		int x = transform.xToDevice(localMarkBounds.getLeft());
		int y = transform.yToDevice(localMarkBounds.getTop());

		// only draw something black if images are too small...
//		if ((width < 5) && (height < 5))
//		{
//			graphics.setColor(TSEColor.black);
//			graphics.drawRect(x, y,	width, height);
//			return;
//		}		

		@SuppressWarnings("unused")
		int outerAndInnerX = width * 2 / 5;
		@SuppressWarnings("unused")
		int outerAndInnerY = height * 2 / 5;

		// experimental //////////////////////////////////////////////

		TSEImage image = null; 
		if (this.image != null) {
			image = this.image;
		}

		// take the non-scaled width and height of the image
		if (image == null || image.getImage() == null) {
			// DecisionGraphPlugin.LOGGER.logDebug("NULL image!");
			graphics.setClip(oldClip);
			return;
		}

		double worldWidth = image.getImage().getWidth(this) + 4;
		double worldHeight = image.getImage().getHeight(this) + 4;
		// get device equivalents
		int devLeft = transform.xToDevice(
				this.getOwnerNode().getLocalCenterX());
		int devTop = transform.yToDevice(
				this.getOwnerNode().getLocalCenterY());
		int devWidth = transform.widthToDevice(worldWidth);
		int devHeight = transform.widthToDevice(worldHeight);
		
		if ((devWidth < 3) && (devHeight < 3)) {
			graphics.setColor(TSEColor.black);
			graphics.drawRect(devLeft, devTop, devWidth, devHeight);
		}
		else {
			worldWidth = image.getImage().getWidth(this);
			/////x = transform.xToDevice(this.getOwnerNode().getLocalCenterX() - 8.0);
			// graphics.drawImage(image.getImage(), devLeft, devTop, devWidth, devHeight, this);
			graphics.drawImage(image.getImage(), x, y, width, height, this);
		}

		graphics.setClip(oldClip);
	}
	
	

	private int BADGE_SIZE = 12;
	private int BADGE_GAP = 4;
	private int MULTIPLE_WIDTH = 3;
	private int COLLAPSE_SIGN_GAP = 2;
	private IProject fProject;
	
	public void drawBottommBadges(TSEGraphics graphics) {

		int noOfBadges = 0;
		boolean isParent = false;
		/*isLoop = false;
		isMultiple = false;
		isCompensation = false;*/

		TSTransform transform = graphics.getTSTransform();		
		TSConstRect localMarkBounds = this.getLocalBounds();
		int x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 2.0);
		int y = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE /* + 2 */);

		if (this.getOwnerNode().hasChildGraph()) {
			noOfBadges++;
			isParent = true;
		}

		String mode = (String) this.getOwnerNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_MODE);
		EObject userObject = (EObject)this.getOwnerNode().getUserObject();
		EObjectWrapper<EClass, EObject> userObjectWrapper =null;
		if(userObject != null){
			userObjectWrapper = EObjectWrapper.wrap(userObject);
		}
		if (mode != null) {
			if (mode.equals(BpmnUIConstants.NODE_ATTR_TASK_MODE_LOOP)) {
				isLoop = true;
				isMultiple = false;
				isSequential = false;
				noOfBadges++;
			}
			else if (mode.equals(BpmnUIConstants.NODE_ATTR_TASK_MODE_MULTIPLE)) {
				isMultiple = true;
				isLoop = false;
				isSequential = false;
				if(userObjectWrapper != null){
					EObject attribute = userObjectWrapper
							.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
					if(attribute != null){
						EObjectWrapper<EClass, EObject> loopWrapper = EObjectWrapper.wrap(attribute);
						isSequential = loopWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_SEQUENTIAL);
					}
				}
				noOfBadges++;
			}else if(mode.equals(BpmnUIConstants.NODE_ATTR_TASK_MODE_NONE)){
				isMultiple = false;
				isLoop = false;
				isSequential = false;
				noOfBadges--;
			}
							
		}
		
		Object attributeValue = this.getOwnerNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_MODE_COMPENSATION);
		if(attributeValue != null)
			isCompensation = (Boolean) attributeValue;
		
		if (isCompensation) {
			noOfBadges++;
		}
		
		if (isParent) {
			this.drawBottomChildGraphMark(graphics, x, y, noOfBadges);
		}

		Color oldColor = graphics.getColor();
		graphics.setColor(Color.BLACK);

		if (isLoop) {
			this.drawBottomLoopModeMark(graphics, x, y, noOfBadges);
		}
		else if (isMultiple) {
			if(!isSequential)
				this.drawBottomMultipleModeMark(graphics, noOfBadges);
			else
				this.drawBottomMultipleModeMarkForSequential(graphics, noOfBadges);
		}
		if (isCompensation) {
			this.drawBottomCompensationMark(graphics, noOfBadges);
		}

		graphics.setColor(oldColor);
	}
	

	private void drawBottomChildGraphMark(TSEGraphics graphics, int x, int y, int noOfBadges) {
		TSTransform transform = graphics.getTSTransform();		
		TSConstRect localMarkBounds = this.getLocalBounds();

		if (noOfBadges == 2) {
			x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 2.0
					- 0.5 * (BADGE_SIZE + BADGE_GAP));
		}
		else if (noOfBadges == 3) {
			x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 2.0
					- (BADGE_SIZE + BADGE_GAP));
		}
		
		Color oldColor = graphics.getColor();
		graphics.setColor(Color.BLACK);
		// draw outer box to enclose the "+" first
		graphics.drawRect(x, y, transform.widthToDevice(BADGE_SIZE), transform.heightToDevice(BADGE_SIZE));
		// horizontal line
		int x1 = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 2.0 + COLLAPSE_SIGN_GAP);
		////x1 = x + transform.xToDevice(COLLAPSE_SIGN_GAP);
		
		int y1 = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE/2.0);
		
		int x2 = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 + BADGE_SIZE / 2.0 - COLLAPSE_SIGN_GAP);
		////x2 = 
		
		graphics.drawLine(x1, y1, x2, y1);
		
		// vertical line
		x1 = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0);
		
		y1 = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE - COLLAPSE_SIGN_GAP);
		int y2 = transform.yToDevice(localMarkBounds.getBottom() + COLLAPSE_SIGN_GAP);
		graphics.drawLine(x1, y1, x1, y2);
		graphics.setColor(oldColor);
	}
	
	private void drawBottomLoopModeMark(TSEGraphics graphics, int x, int y, int noOfBadges) {
		TSTransform transform = graphics.getTSTransform();		
		TSConstRect localMarkBounds = this.getLocalBounds();
		// int x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 2.0);
		// int y = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE /* + 2 */);
		int width = transform.widthToDevice(MULTIPLE_WIDTH);
		// draw the circle
		if(isCompensation){
			x-=3.5 * width;
			
		}
		graphics.drawOval(x, y-DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(BADGE_SIZE), transform.heightToDevice(BADGE_SIZE));
		// draw one line of arrow (horizontal one)
		int x1 = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 2.5);
		int x2 = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0);
		int y1 = transform.yToDevice(localMarkBounds.getBottom());
		if(isCompensation){
			x1-=3.5 * width;
			x2-=3.5 * width;
			
		}
		graphics.drawLine(x1, y1-DEFAULT_BREAKPOINT_OFFSET, x2, y1-DEFAULT_BREAKPOINT_OFFSET);
		// draw other line of arrow (slanted one)
		x1 = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 4.0);
		y1 = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE / 4.0);
		x2 = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0);
		int y2 = transform.yToDevice(localMarkBounds.getBottom());
		if(isCompensation){
			x1-=3.5 * width;
			x2-=3.5 * width;
			
		}
		graphics.drawLine(x1, y1-DEFAULT_BREAKPOINT_OFFSET, x2, y2-DEFAULT_BREAKPOINT_OFFSET);		
	}
	
	private void drawBottomMultipleModeMark(TSEGraphics graphics, int noOfBadges) {
		TSTransform transform = graphics.getTSTransform();		
		TSConstRect localMarkBounds = this.getLocalBounds();
		
		int x = transform.xToDevice(
				localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 2.0);
		int y = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE + 2);
		int width = transform.widthToDevice(MULTIPLE_WIDTH);
		int height = transform.heightToDevice(BADGE_SIZE);
		if(isCompensation){
			x-=3.5 * width;
			
		}
		graphics.fillRect(x, y, width, height);

		x = transform.xToDevice(
				localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 2.0 + 2*MULTIPLE_WIDTH);
		if(isCompensation){
			x-=3.5 *width;
			
		}
		graphics.fillRect(x, y, width, height);

		x = transform.xToDevice(
				localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 2.0 + 4*MULTIPLE_WIDTH);
		if(isCompensation){
			x-=3.5 *width;
			
		}
		graphics.fillRect(x, y, width, height);		
	}
	
	private void drawBottomMultipleModeMarkForSequential(TSEGraphics graphics, int noOfBadges) {
		TSTransform transform = graphics.getTSTransform();		
		TSConstRect localMarkBounds = this.getLocalBounds();
		
		int x = transform.xToDevice(
				localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 2.0);
		int y = transform.yToDevice(localMarkBounds.getBottom()  + 2*MULTIPLE_WIDTH);
		int width = transform.widthToDevice(BADGE_SIZE);
		int height = transform.heightToDevice(MULTIPLE_WIDTH);
		if(isCompensation){
			x-=3.5 * height;
			
		}
		graphics.fillRect(x, y, width, height);	

		y = transform.yToDevice(localMarkBounds.getBottom() +4*MULTIPLE_WIDTH);

		graphics.fillRect(x, y, width, height);	

		y = transform.yToDevice(localMarkBounds.getBottom() + 6*MULTIPLE_WIDTH);

		graphics.fillRect(x, y, width, height);	
	}
	
	private void drawBottomCompensationMark(TSEGraphics graphics, int noOfBadges) {
		TSTransform transform = graphics.getTSTransform();		
		TSConstRect localMarkBounds = this.getLocalBounds();
		
		BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
		Stroke newStroke = new BasicStroke(1);
		graphics.setStroke(newStroke);
		Polygon p = new Polygon();
		int width = transform.widthToDevice(MULTIPLE_WIDTH);

		// drawing first point of first triangle: <| (left most, top most, bottom most points)
		int x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 2.0);
		int y = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE/2.0 + 2);
		if(isLoop || isMultiple){
			x+=3.5 *width;
			
		}
		p.addPoint(x, y);
		
		x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0);
		y = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE + 2);
		if(isLoop || isMultiple){
			x+=3.5 *width;
			
		}
		p.addPoint(x, y);
		
		// x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE);
		y = transform.yToDevice(localMarkBounds.getBottom() + 2);
		p.addPoint(x, y);
		
		graphics.drawPolygon(p);
		
		// Now draw the second triangle
		p.reset();
		
		x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0);
		y = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE/2.0 + 2);
		if(isLoop || isMultiple){
			x+=3.5 *width;
			
		}
		p.addPoint(x, y);
		
		x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 + BADGE_SIZE / 2.0);
		y = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE + 2);
		if(isLoop || isMultiple){
			x+=3.5 *width;
			
		}
		p.addPoint(x, y);
		
		// x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 + BADGE_SIZE / 2.0);
		y = transform.yToDevice(localMarkBounds.getBottom() + 2);
		p.addPoint(x, y);
		
		graphics.drawPolygon(p);
		
		graphics.setStroke(oldStroke);		
	}

	
	@SuppressWarnings("static-access")
	public void drawImageBadge(TSEGraphics graphics) {
		
		if(isSHOW_TASK_ICONS()){
		
		TSEImage image = this.badgeImage;
		if (image == null) {
			// BpmnUIPlugin.log("image was NULL");
			//System.err.println("NULL image");
			return;
		}

		TSTransform transform = graphics.getTSTransform();		
		TSConstRect localMarkBounds = this.getLocalBounds();
		
		int x = transform.xToDevice(localMarkBounds.getLeft()) + 4;
		int y = transform.yToDevice(localMarkBounds.getTop()) + 4;

		if (image.getImage() == null) {
			image.loadImage(this.getClass(), "/icons/rule-function.png");
			if (image.getImage() == null) {
				image = new TSEImage(this.badgeImage);
				if (image.getImage() == null) {
					// System.err.println("last try was still null");
				}
				else {
					// System.err.println("last try loaded image lazily");
				}
			}
			return;
		}
		double worldWidth = image.getImage().getWidth(this);
		double worldHeight = image.getImage().getHeight(this);
		// get device equivalents
		int devLeft = transform.xToDevice(
				this.getOwnerNode().getLocalCenterX() - worldWidth / 2.0);
		int devTop = transform.yToDevice(
			this.getOwnerNode().getLocalCenterY() +
			(this.getTextHeight() + worldHeight) / 2.0);
		int devWidth = transform.widthToDevice(worldWidth);
		int devHeight = transform.heightToDevice(worldHeight);
			
		// if it's too small, we don't bother spending time to draw the image
		if ((devWidth < 3) && (devHeight < 3)) {
			graphics.setColor(TSEColor.black);
			graphics.drawRect(devLeft, devTop, devWidth, devHeight);
		}
		else {
			// if send ore receive message, we don't draw the image but draw the badge
			if (image == SEND_EVENT_IMAGE || image == RECEIVE_EVENT_IMAGE) {
				this.drawMessageBadge(graphics);
			}
			else {
				String imageSizePref = BpmnUIPlugin.getDefault().getPreferenceStore().getString(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS);
				IMAGE_SIZE iSize = IMAGE_SIZE.get(imageSizePref);
//				graphics.drawImage(image.getImage(),
//					x,
//					y,
//					transform.widthToDevice(iSize.getWidth()/2),
//					transform.heightToDevice(iSize.getHeight()/2),
//					this);
				graphics.drawImage(image.getImage(),
						x,
						y,
						transform.widthToDevice(Math.min((int)(iSize.getWidth()*0.9),(int)localMarkBounds.getSize().getWidth()/2.5)),
						transform.heightToDevice(Math.min((int)(iSize.getHeight()*0.9),(int)localMarkBounds.getSize().getHeight()/2.5)),
						this);
			}
		}
	}
	}
	
	public void drawMessageBadge(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();
		TSConstRect bounds = owner.getLocalBounds();		
//		double newWidth = bounds.getWidth() * 0.375;
		double newWidth = bounds.getWidth() * 0.2625;
		double newHeight = bounds.getHeight() * 0.2625;
		TSRect badgeBounds = new TSRect(bounds);
		badgeBounds.setWidth(newWidth);
		badgeBounds.setHeight(newHeight);	
		badgeBounds.setLeft(bounds.getLeft() + 5);
		badgeBounds.setTop(bounds.getTop() - 5);
		badgeBounds.setRight(bounds.getLeft() + 5 + newWidth);
		badgeBounds.setBottom(bounds.getTop() - 5 - newHeight);

		BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
		Stroke newStroke = new BasicStroke(
				oldStroke.getLineWidth() * 2 /* (this.outerBorderMultiplier-1) */,
				oldStroke.getEndCap(),
				oldStroke.getLineJoin(),
				oldStroke.getMiterLimit(),
				oldStroke.getDashArray(),
				oldStroke.getDashPhase());
		graphics.setStroke(newStroke);
		graphics.setColor(GLOW_OUTER_HIGH_COLOR);

		this.drawMessageBackground(graphics, badgeBounds);		

		super.drawMessageBadge(graphics, badgeBounds);

		graphics.setStroke(oldStroke);		
	}
	
	
	protected void drawMessageBackground(TSEGraphics graphics, TSRect badgeBounds) {
		boolean isSend;
		EClass nodeType = (EClass) this.getOwnerNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);

		if (SEND_TASK.isSuperTypeOf(nodeType)) {
			isSend = true;
		}
		else if (RECEIVE_TASK.isSuperTypeOf(nodeType)) {
			isSend = false;		
		}
		else {
			return;
		}
		
		if (isSend) {
			graphics.fillRect(badgeBounds);
		}
		else {
			graphics.drawRect(badgeBounds);
		}

		if (isSend) {
			graphics.setColor(Color.white);
		}
		else {
			graphics.setColor(GLOW_OUTER_HIGH_COLOR);
		}
	}
	
	
	/**
	 * This method returns child graph mark bounds in the coordinate system of
	 * owner graph.
	 */
	public TSRect getLocalChildGraphMarkBounds()
	{
		TSConstRect nodeBounds = this.getOwnerNode().getLocalBounds();
		TSRect childGraphMarkBounds = new TSRect();
		
		childGraphMarkBounds.setLeft(nodeBounds.getLeft() + MARK_GAP+15);
		childGraphMarkBounds.setTop(nodeBounds.getTop() - MARK_GAP);
		childGraphMarkBounds.setRight(childGraphMarkBounds.getLeft() + MARK_SIZE);
		childGraphMarkBounds.setBottom(childGraphMarkBounds.getTop() - MARK_SIZE);
		
		return childGraphMarkBounds;
	}	
	
	public boolean imageUpdate(Image image,	int flags, int x, int y, int width,	int height) {
		return (((flags & ImageObserver.ALLBITS) == 0) ||
			((flags & ImageObserver.FRAMEBITS) == 0));
	}
	
	public double getTextOffsetY() {
		return super.getTextOffsetY() - TEXT_OFFSET;
	}
	
	// TODO
	// slightly changes super's version. Fix this.
	protected String getFormattedText(TSEGraphics g) {
		if (this.getOwner() == null) {
			return (this.getDefaultText());
		}

		if (this.getOwner().getText() == null) {
			if (this.getOwner() instanceof TSENode) {
				return null;
			}
			else {
				return (this.getDefaultText());
			}
		}

		if (!this.isFormattingEnabled()) {
			this.formattedText = this.getOwner().getText();
			return (this.formattedText);
		}

		int resizability = ((TSESolidObject) this.getOwner()).getResizability();
		String text = "";
		
		if (this.displayNameAttribute) {
			if (this.getOwnerNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME) != null) {
				this.formattedText = (String) this.getOwnerNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
				text = this.formattedText;
			}
		}
		else {
			text = this.getOwner().getText();
		}
		
		if (((resizability & TSESolidObject.RESIZABILITY_TIGHT_WIDTH) != 0
			|| (resizability & TSESolidObject.RESIZABILITY_TIGHT_HEIGHT) != 0)
			&& !(this.getOwner() instanceof TSENode
				&& ((TSENode) this.getOwner()).isExpanded())) {
			this.formattedText = this.getOwner().getText();
			
			return (this.formattedText);
		}

		// we account for the collapse marker here.
		double width = this.getWidth() - 15;

//		FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(this.getFont().getFont());
		FontMetrics fm = g.getFontMetrics(this.getFont().getFont());
		StringBuffer buffer = new StringBuffer();
		int start = 0;
		int wordBreak = -1;
		boolean foundEnd = false;
		int numberOfLines = 1;
		
		for (int i = 1; i < text.length(); i++) {
			if (text.charAt(i) == '\n') {
				buffer.append(text.substring(start, i + 1));
				start = i + 1;
				wordBreak = -1;
				numberOfLines++;
			}
			else {
				if (Character.isWhitespace(text.charAt(i))) {
					wordBreak = i;
				}

				if (fm.stringWidth(text.substring(start, i + 1)) > width) {
					if (wordBreak != -1) {
						buffer.append(text.substring(start, wordBreak));
						start = wordBreak + 1;
						wordBreak = -1;
					}
					else {
						buffer.append(text.substring(start, i));
						start = i;
					}

					if (!this.localDisplayCompleteName && numberOfLines == MAX_TEXT_LINES) {
						buffer.append("...");
						foundEnd = true;
						break;
					}
					else {
						buffer.append('\n');
						numberOfLines++;
					}
				}
			}
		}

		if (text.substring(start).length() > 0 && !foundEnd) {
			buffer.append(text.substring(start));
		}

		this.formattedText = buffer.toString();

		return this.formattedText;
	}		
    
	public void drawToolArea(TSEGraphics graphics) {
		Color oldColor = graphics.getColor();
		graphics.setColor(oldColor);
		TSConstRect ownerBounds = this.getOwner().getLocalBounds();
		TSConstRect rect= new TSConstRect(
				ownerBounds.getLeft() + 1,
				ownerBounds.getCenterY() + DEFAULT_BREAKPOINT_SIZE/2,
				ownerBounds.getLeft() + 1 + ownerBounds.getWidth(),
				ownerBounds.getCenterY() - DEFAULT_BREAKPOINT_SIZE/2);
		graphics.fillRect(rect);
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(Color.BLUE);
	}

	

	/**
	 * @param graphics
	 * @param image
	 * @param backgroundColor
	 * @param crossColor
	 * @param localBounds
	 */
	protected void drawImage(TSEGraphics graphics, 
			                 TSEImage image,
			                 TSEColor backgroundColor,	
			                 TSEColor crossColor,
			                 TSConstRect localBounds) {
		Shape oldClip = graphics.getClip();
		graphics.clipRect(this.getOwnerNode().getLocalBounds());
		TSTransform transform = graphics.getTSTransform();

		int width = transform.widthToDevice(localBounds.getWidth());
		int height = transform.heightToDevice(localBounds.getHeight());

		int x = transform.xToDevice(localBounds.getLeft());
		int y = transform.yToDevice(localBounds.getTop());

		double worldWidth = image.getImage().getWidth(this);;
		double worldHeight = image.getImage().getHeight(this);;

		// get device equivalents
		int devLeft = transform.xToDevice(this.getOwnerNode().getLocalCenterX() - worldWidth / 2.0);
		int devTop = transform.yToDevice(this.getOwnerNode().getLocalCenterY() + (this.getTextHeight() + worldHeight) / 2.0);
		int devWidth = transform.widthToDevice(worldWidth);
		int devHeight = transform.widthToDevice(worldHeight);

		if ((devWidth < 3) && (devHeight < 3)) {
			graphics.setColor(TSEColor.black);
			graphics.drawRect(devLeft, devTop, devWidth, devHeight);
		}
		else {
			x = transform.xToDevice(localBounds.getLeft() + 1);				
			graphics.drawImage(image.getImage(), x, y, width, height, this);
		}
		graphics.setClip(oldClip);
	}

	
	public void drawBreakpoints(TSEGraphics graphics,
            Color color) {
		if (!isInputBreakpoint() && !isOutBreakpoint()) {
			return;
		}
		else {
			boolean useConnector = false;
			
			if (isInputBreakpoint()) {
				if (useConnector) {
					TSEConnector inConnector = (TSEConnector) this.getOwnerNode().addConnector();
					inConnector.setSize(7.0, 7.0);
					inConnector.setProportionalXOffset(-0.45);
					inConnector.setProportionalYOffset(0);
				}
				else {
					Color oldColor = graphics.getColor();
					graphics.setColor(color);
					TSConstRect ownerBounds = this.getOwner().getLocalBounds();		
					// TODO: for performance reasons we should pre-calculate these math operations!
					int BREAKPOINT_SIZE = 8;
			        TSConstRect rect = new TSConstRect(
			            	ownerBounds.getLeft() + 1,
			            	ownerBounds.getCenterY() + BREAKPOINT_SIZE/2,
			            	ownerBounds.getLeft() + 1 + BREAKPOINT_SIZE,
			            	ownerBounds.getCenterY() - BREAKPOINT_SIZE/2);
			        graphics.fillOval(rect);
			        graphics.setColor(oldColor);
				}
			}
			if (isOutBreakpoint()) {
				if (useConnector) { 
					TSEConnector outConnector = (TSEConnector) this.getOwnerNode().addConnector();
					outConnector.setSize(7.0, 7.0);
					outConnector.setProportionalXOffset(0.45);
					outConnector.setProportionalYOffset(0);
				}
				else {
					Color oldColor = graphics.getColor();
					graphics.setColor(color);
					TSConstRect ownerBounds = this.getOwner().getLocalBounds();		
					// TODO: for performance reasons we should pre-calculate these math operations!
					int BREAKPOINT_SIZE = 8;
			        TSConstRect rect = new TSConstRect(
			            	ownerBounds.getRight() - 1 - BREAKPOINT_SIZE,
			            	ownerBounds.getCenterY() + BREAKPOINT_SIZE/2,
			            	ownerBounds.getRight() - 1,
			            	ownerBounds.getCenterY() - BREAKPOINT_SIZE/2);
			        graphics.fillOval(rect);
			        graphics.setColor(oldColor);					
				}
			}
		}
	}


	public static boolean isSHOW_TASK_ICONS() {
		return SHOW_TASK_ICONS;
	}


	public static void setSHOW_TASK_ICONS(boolean sHOW_TASK_ICONS) {
		SHOW_TASK_ICONS = sHOW_TASK_ICONS;
	}
	
	@Override
	public void setNodeColor() {
		// TODO Auto-generated method stub
		super.setNodeColor();
		if(isGradientDrawn()){
			setGradient(getColor(), getColor());
		}
	}
}
