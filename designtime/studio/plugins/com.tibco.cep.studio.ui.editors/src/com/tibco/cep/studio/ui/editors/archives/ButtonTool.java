package com.tibco.cep.studio.ui.editors.archives;

/**
 * 
 * @author sasahoo
 *
 */
public class ButtonTool {

	private String text;
	private String tooltip;
	private javax.swing.Action action;
	private String ImageIcon;

	public ButtonTool(String text,String tooltip,String ImageIcon,javax.swing.Action action){
		this.text = text;
		this.tooltip = tooltip;
		this.action = action;
		this.ImageIcon = ImageIcon;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public javax.swing.Action getAction() {
		return action;
	}
	public void setAction(javax.swing.Action action) {
		this.action = action;
	}
	public String getImageIcon() {
		return ImageIcon;
	}
	public void setImageIcon(String imageIcon) {
		ImageIcon = imageIcon;
	}

}
