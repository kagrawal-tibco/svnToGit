package com.tibco.cep.bpmn.ui.graph.model.factory.ui;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Stroke;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.BPMNCommonImages.IMAGE_SIZE;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.preferences.BpmnPreferenceConstants;
import com.tibco.cep.diagramming.ui.CollapsedSubprocessNodeUI;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;


/**
 * 
 * @author ggrigore
 *
 */
@SuppressWarnings("serial")
public class BPMNCallActivityNodeUI extends CollapsedSubprocessNodeUI {
	private static int BADGE_SIZE = 12;
	private static int MULTIPLE_WIDTH = 2;
	private static int BADGE_GAP = 2;
	private static int COLLAPSE_SIGN_GAP = 2;
	
	private boolean isLoop;
	private boolean isMultiple;
	private boolean isSequential;
	private boolean isCompensation;
	
	public BPMNCallActivityNodeUI() {
		super();
		
		BpmnUIPlugin plugInstance = BpmnUIPlugin.getDefault();
		boolean fillTaskIcons = false;
		if (plugInstance != null) {
			IPreferenceStore store = plugInstance.getPreferenceStore();
			fillTaskIcons = store.getBoolean(BpmnPreferenceConstants.PREF_FILL_TASK_NODES);
		}
		else {
			// TODO: also drive web-based from some preference
			fillTaskIcons = false;
		}
		if (fillTaskIcons /* || BpmnUIPlugin.getDefault() == null */) {
			this.setDrawGradient(true);
			this.setBorderDrawn(false);
		}else {
			this.setDrawGradient(false);
			this.setBorderWidth(6);			
		}
	}

	@Override
	public void setNodeColor() {
		// TODO Auto-generated method stub
		super.setNodeColor();
		if(isGradientDrawn()){
			setGradient(getColor(), getColor());
		}
	}
	
	@Override
	public void draw(TSEGraphics graphics) {
		// TODO Auto-generated method stub
		super.draw(graphics);
		
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
		
		if(isDrawError()) {
			drawError(graphics,Color.RED, 5);
		}
	}
	
	
	protected void drawBottommBadges(TSEGraphics graphics) {

		int noOfBadges = 0;
		boolean isParent = false;
		/*isLoop = false;
		isMultiple = false;
		isCompensation = false;*/

		TSTransform transform = graphics.getTSTransform();		
		TSConstRect localMarkBounds = this.getLocalBounds();
//		int x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 2.0);
//		int y = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE + COLLAPSE_SIGN_GAP /* + 2 */);

//		if (this.getOwnerNode().hasChildGraph()) {
			noOfBadges++;
			isParent = true;
//		}

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
			this.drawBottomChildGraphMark(graphics, noOfBadges);
		}

		Color oldColor = graphics.getColor();
		graphics.setColor(Color.BLACK);

		if (isLoop) {
			this.drawBottomLoopModeMark(graphics,noOfBadges);
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
	
	
	private void drawBottomLoopModeMark(TSEGraphics graphics, int noOfBadges) {
		TSTransform transform = graphics.getTSTransform();		
		TSConstRect localMarkBounds = this.getLocalBounds();
		// int x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE / 2.0);
		// int y = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE /* + 2 */);
		int width = transform.widthToDevice(MULTIPLE_WIDTH);
		int x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0);
		int y = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE + COLLAPSE_SIGN_GAP /* + 2 */);
		double locX =localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2;
		if (noOfBadges == 2) {
			locX = locX+ BADGE_SIZE/2 + BADGE_GAP/2;
			x =transform.xToDevice(locX- BADGE_SIZE/2);
		}
		// draw the circle
		if(isCompensation){
			x-=3.5 * width;
			
		}
		graphics.drawOval(x, y-DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(BADGE_SIZE), transform.heightToDevice(BADGE_SIZE));
		// draw one line of arrow (horizontal one)
		int x1 = transform.xToDevice(locX - BADGE_SIZE / 2.5);
		int x2 = transform.xToDevice(locX);
		int y1 = transform.yToDevice(localMarkBounds.getBottom()+COLLAPSE_SIGN_GAP);
		if(isCompensation){
			x1-=3.5 * width;
			x2-=3.5 * width;
			
		}
		graphics.drawLine(x1, y1-DEFAULT_BREAKPOINT_OFFSET, x2, y1-DEFAULT_BREAKPOINT_OFFSET);
		// draw other line of arrow (slanted one)
		x1 = transform.xToDevice(locX - BADGE_SIZE / 4);
		y1 = transform.yToDevice(localMarkBounds.getBottom()+ COLLAPSE_SIGN_GAP + BADGE_SIZE / 4.0);
		x2 = transform.xToDevice(locX);
		int y2 = transform.yToDevice(localMarkBounds.getBottom() +COLLAPSE_SIGN_GAP);
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
		int y = transform.yToDevice(localMarkBounds.getBottom()+BADGE_SIZE+COLLAPSE_SIGN_GAP);
		
		double locX =localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2 -BADGE_SIZE / 2.0;
		if (noOfBadges == 2) {
			locX = locX+ (BADGE_SIZE/2.0 + 0.5* BADGE_GAP);
			x = transform.xToDevice(locX);
		}
		
		int width = transform.widthToDevice(MULTIPLE_WIDTH);
		int height = transform.heightToDevice(BADGE_SIZE);
		if(isCompensation){
			x-=3.5 * width;
			
		}
		graphics.fillRect(x, y, width, height);

		x = transform.xToDevice(locX+ 2*MULTIPLE_WIDTH);
		if(isCompensation){
			x-=3.5 *width;
			
		}
		graphics.fillRect(x, y, width, height);

		x = transform.xToDevice(locX+ 4*MULTIPLE_WIDTH);
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
		int y = transform.yToDevice(localMarkBounds.getBottom()  + MULTIPLE_WIDTH+ COLLAPSE_SIGN_GAP);
		
		double locX =localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2 - BADGE_SIZE / 2.0;
		if (noOfBadges == 2) {
			locX = locX+ + BADGE_SIZE / 2.0+ 0.5* BADGE_GAP;
			x = transform.xToDevice(locX);
		}
		else if (noOfBadges == 3) {
			locX = locX + BADGE_SIZE / 2.0
					+ (BADGE_SIZE + BADGE_GAP);
			x = transform.xToDevice(locX);
		}
		
		
		int width = transform.widthToDevice(BADGE_SIZE);
		int height = transform.heightToDevice(MULTIPLE_WIDTH);
		if(isCompensation){
			x-=3.5 * height;
			
		}
		graphics.fillRect(x, y, width, height);	

		y = transform.yToDevice(localMarkBounds.getBottom() +2*MULTIPLE_WIDTH+ COLLAPSE_SIGN_GAP + 3.0);

		graphics.fillRect(x, y, width, height);	

		y = transform.yToDevice(localMarkBounds.getBottom() + 3*MULTIPLE_WIDTH+ + COLLAPSE_SIGN_GAP + 6.0);

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
	
	private void drawBottomChildGraphMark(TSEGraphics graphics, int noOfBadges) {
		TSTransform transform = graphics.getTSTransform();		
		TSConstRect localMarkBounds = this.getLocalBounds();
		int x = transform.xToDevice(localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2.0 - BADGE_SIZE/2);
		int y = transform.yToDevice(localMarkBounds.getBottom() + BADGE_SIZE + COLLAPSE_SIGN_GAP /* + 2 */);
		double locX =localMarkBounds.getLeft() + localMarkBounds.getWidth() / 2;
		if (noOfBadges == 2) {
			locX = locX- (BADGE_SIZE + 0.5* BADGE_GAP);
			x = transform.xToDevice(locX);
		}
		else if (noOfBadges == 3) {
			locX = locX - BADGE_SIZE / 2.0
					- (BADGE_SIZE + BADGE_GAP);
			x = transform.xToDevice(locX);
		}
		
		Color oldColor = graphics.getColor();
		graphics.setColor(Color.BLACK);
		// draw outer box to enclose the "+" first
		graphics.drawRect(x, y, transform.widthToDevice(BADGE_SIZE), transform.heightToDevice(BADGE_SIZE));
		// horizontal line
		int x1 = x + transform.widthToDevice(COLLAPSE_SIGN_GAP);
		////x1 = x + transform.xToDevice(COLLAPSE_SIGN_GAP);
		
		int y1 = y + transform.heightToDevice(BADGE_SIZE/2);
		
		int x2 = x + transform.widthToDevice( BADGE_SIZE - COLLAPSE_SIGN_GAP);
		////x2 = 
		
		graphics.drawLine(x1, y1, x2, y1);
		
		// vertical line
		x1 = x + transform.widthToDevice( BADGE_SIZE/2);
		
		y1 = y + transform.heightToDevice( BADGE_SIZE - COLLAPSE_SIGN_GAP);
		int y2 = y + transform.heightToDevice( COLLAPSE_SIGN_GAP);
		graphics.drawLine(x1, y1, x1, y2);
		graphics.setColor(oldColor);
		
	}
	
	@Override
	protected int getBadgeImageHeight() {
		String imageSizePref = BpmnUIPlugin.getDefault().getPreferenceStore().getString(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS);
		IMAGE_SIZE iSize = IMAGE_SIZE.get(imageSizePref);
		return iSize.getHeight();
	}

	@Override
	protected int getBadgeImageWidth() {
		String imageSizePref = BpmnUIPlugin.getDefault().getPreferenceStore().getString(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS);
		IMAGE_SIZE iSize = IMAGE_SIZE.get(imageSizePref);
		return iSize.getWidth();
	}
	
	
}
