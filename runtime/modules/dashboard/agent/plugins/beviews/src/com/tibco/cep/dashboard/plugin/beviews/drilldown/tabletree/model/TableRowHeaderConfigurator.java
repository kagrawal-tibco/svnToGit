package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

/**
 * @author rajesh
 *
 */
public class TableRowHeaderConfigurator {

	private String rowSelectedImage;
	private String rowUnSelectedImage;
	private String rowSelectedHoverImage;
	private String rowUnSelectedHoverImage;

	private String rowSelectedMouseDownImage;
	private String rowUnSelectedMouseDownImage;

	private String imagePath;

	/**
     *
     */
	public TableRowHeaderConfigurator() {
		super();
	}

	public TableRowHeaderConfigurator(String rowSelectedImage, String rowUnSelectedImage, String rowSelectedHoverImage, String rowUnSelectedHoverImage, String rowSelectedMouseDownImage, String rowUnSelectedMouseDownImage,
			String imagePath) {
		this(rowSelectedImage, rowUnSelectedImage, rowSelectedHoverImage, rowUnSelectedHoverImage, imagePath);
		this.rowSelectedMouseDownImage = rowSelectedMouseDownImage;
		this.rowUnSelectedMouseDownImage = rowUnSelectedMouseDownImage;
	}

	/**
     *
     */
	public TableRowHeaderConfigurator(String rowSelectedImage, String rowUnSelectedImage, String rowSelectedHoverImage, String rowUnSelectedHoverImage, String imagePath) {
		this(rowSelectedImage, rowUnSelectedImage, imagePath);
		this.rowSelectedHoverImage = rowSelectedHoverImage;
		this.rowUnSelectedHoverImage = rowUnSelectedHoverImage;
	}

	/**
     *
     */
	public TableRowHeaderConfigurator(String rowSelectedImage, String rowUnSelectedImage, String imagePath) {
		this.rowSelectedImage = rowSelectedImage;
		this.rowUnSelectedImage = rowUnSelectedImage;
		this.imagePath = imagePath;
	}

	/**
	 * @return
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @return
	 */
	public String getRowSelectedImage() {
		return rowSelectedImage;
	}

	/**
	 * @return
	 */
	public String getRowUnSelectedImage() {
		return rowUnSelectedImage;
	}

	/**
	 * @param string
	 */
	public void setImagePath(String string) {
		imagePath = string;
	}

	/**
	 * @param string
	 */
	public void setRowSelectedImage(String string) {
		rowSelectedImage = string;
	}

	/**
	 * @param string
	 */
	public void setRowUnSelectedImage(String string) {
		rowUnSelectedImage = string;
	}

	public String getRowSelectedHoverImage() {
		return rowSelectedHoverImage;
	}

	public void setRowSelectedHoverImage(String rowSelectedHoverImage) {
		this.rowSelectedHoverImage = rowSelectedHoverImage;
	}

	public String getRowUnSelectedHoverImage() {
		return rowUnSelectedHoverImage;
	}

	public void setRowUnSelectedHoverImage(String rowUnSelectedHoverImage) {
		this.rowUnSelectedHoverImage = rowUnSelectedHoverImage;
	}

	public String getRowSelectedMouseDownImage() {
		return rowSelectedMouseDownImage;
	}

	public void setRowSelectedMouseDownImage(String rowSelectedMouseDownImage) {
		this.rowSelectedMouseDownImage = rowSelectedMouseDownImage;
	}

	public String getRowUnSelectedMouseDownImage() {
		return rowUnSelectedMouseDownImage;
	}

	public void setRowUnSelectedMouseDownImage(String rowUnSelectedMouseDownImage) {
		this.rowUnSelectedMouseDownImage = rowUnSelectedMouseDownImage;
	}

}
