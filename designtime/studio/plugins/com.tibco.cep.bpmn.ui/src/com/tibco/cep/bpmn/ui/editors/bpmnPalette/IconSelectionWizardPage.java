package com.tibco.cep.bpmn.ui.editors.bpmnPalette;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

public class IconSelectionWizardPage extends WizardPage {
	
	private BpmnPaletteConfigurationModelMgr modelmgr;
	private Button bFileSys;
	private Canvas canvas;
	private Text filePath;
	private Table iconTable;
	private ArrayList<String> filter = new ArrayList<String>();
	private Set<String> iconSet=new HashSet<String>();
	private Image sourceImage;
	private Image screenImage;
	private AffineTransform transform = new AffineTransform();
	private static final int MAX_LENGTH_ITEM = 6;
	List<Image> externalImages = new ArrayList<Image>();
	/**
	 * @param pageName
	 * @param mdlmgr
	 */
	protected IconSelectionWizardPage(String pageName, BpmnPaletteConfigurationModelMgr mdlmgr) {
		super(pageName);
		this.modelmgr = mdlmgr; 
		filter.add("*.gif");
		filter.add("*.png");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		setPageComplete(true);
		GridLayout layout = new GridLayout(1, false);
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.horizontalSpacing = 0;
		parent.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		// layoutData.heightHint = 500;
		parent.setLayoutData(layoutData);
		Control control = null;
		try {
			control = createDialogArea(parent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setControl(control);
	}
	
	/**
	 * @param parent
	 * @return
	 * @throws IOException 
	 */
	protected Control createDialogArea(Composite parent) throws IOException {
		Composite mainComposite = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		mainComposite.setLayoutData(gd);
		mainComposite.setLayout(new GridLayout(2, false));
		
		filePath = PanelUiUtil.createText(mainComposite, 250);
		filePath.setEditable(false);

		bFileSys = new Button(mainComposite, SWT.PUSH);
		bFileSys.setText(BpmnMessages.getString("iconSelectionDialog_externalIcon_label"));
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.widthHint = 150;
		bFileSys.setLayoutData(gd);
		bFileSys.setEnabled(true);
		bFileSys.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				FileDialog fileDialog = new FileDialog(getShell());
				fileDialog.setFilterExtensions(filter.toArray(new String[2]));
				String file = fileDialog.open();
				loadImage(file);
				fitCanvas(sourceImage, canvas);
				
				if(file != null){
				filePath.setText(file);
				canvas.setData(file);
				} else {
				filePath.setText("Null Image");
				canvas.setData(BpmnImages.DEFAULT_NULL_ICON);
				}
			}
		});
		bFileSys.setLayoutData(gd);

		createIconTable(mainComposite);

		filltable();
		createCanvasComponent(mainComposite);
		return mainComposite;
	}

	 
	/**
	 * @param parent
	 */
	private void createCanvasComponent(Composite parent) {
		canvas = new Canvas(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.NO_BACKGROUND);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.widthHint = ((GridData)bFileSys.getLayoutData()).widthHint;
		gd.heightHint= 200;
		canvas.setLayoutData(gd);
		
		canvas.getHorizontalBar().setVisible(false);
		canvas.getVerticalBar().setVisible(false);
		
		canvas.addPaintListener(new PaintListener() { 
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.PaintEvent)
			 */
			public void paintControl(final PaintEvent event) {
				if(canvas.isDisposed()){
					return;
				}
				paint(event.gc, canvas);
			}
		});
	}

	/**
	 * @param parent
	 */
	private void createIconTable(Composite parent) {
		iconTable = new Table(parent, SWT.BORDER | SWT.SINGLE);
		iconTable.setLayout(new GridLayout());

		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.FILL_HORIZONTAL);
		data.heightHint = 200;

		iconTable.setLayoutData(data);

		iconTable.setLinesVisible(true);
		
		new TableColumn(iconTable, SWT.NULL);
		new TableColumn(iconTable, SWT.NULL);
		new TableColumn(iconTable, SWT.NULL);
		new TableColumn(iconTable, SWT.NULL);
		new TableColumn(iconTable, SWT.NULL);
		new TableColumn(iconTable, SWT.NULL);
		
		autoTableLayout(iconTable);
	}
	
	/**
	 * @param table
	 */
	protected void autoTableLayout(Table table) {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(table);
		for (int loop = 0; loop < table.getColumns().length; loop++) {
			autoTableLayout.addColumnData(new ColumnWeightData(1));
		}
		table.setLayout(autoTableLayout);
	}
	
	/**
	 * @param table
	 * @throws IOException 
	 */
	private void filltable() throws IOException {
		/*iconSet = modelmgr.getIcons();
		Iterator<String> iterator = iconSet.iterator();
		while (iterator.hasNext()) {
			int index = 0;
			TableItem item = new TableItem(iconTable, SWT.NONE);
			while(iterator.hasNext() && index < MAX_LENGTH_ITEM) {
				String icon = iterator.next();
				item.setText(index++, icon);
			}
		}*/
		List<String> extImages = BpmnImages.getInstance().getExtrernalImage();
		Iterator<String> itr = extImages.iterator();
		
		while(itr.hasNext()){
			int index = 0;
			TableItem item = new TableItem(iconTable, SWT.NONE);
			while(itr.hasNext() && index < MAX_LENGTH_ITEM) {
				String path = itr.next();
				item.setText(index++,path);
				}
		}
		
		for (TableItem item: iconTable.getItems()) {
			createIconTableEditor(item);
		}
	}
	
	/**
	 * @param item
	 */
	private void createIconTableEditor(final TableItem item) {
		if (item.isDisposed()) {
			return;
		}
		for (int i = 0; i < MAX_LENGTH_ITEM ; i++ ) {
			String iconPath = item.getText(i);
			if (iconPath.trim().equals("")) {
				continue;
			}
			//Image image;
			//if (iconPath.trim().equals("ext")) {
			Image image;
			try {
				ImageData imageData = new ImageData(iconPath);
				image = new Image(Display.getDefault(), imageData);
				externalImages.add(image);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				continue;
			}
			/*} else {
				image = BpmnImages.getInstance().getImage(iconPath);
			}*/
			 
			item.setText(i, "");
			TableEditor editor = new TableEditor (iconTable);
			final CLabel imageLabel = new CLabel(iconTable, SWT.CENTER);
			imageLabel.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
			imageLabel.setImage(image);
			imageLabel.setData(iconPath);
			imageLabel.addMouseListener(new MouseListener() {
				/* (non-Javadoc)
				 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
				 */
				@Override
				public void mouseUp(MouseEvent e) {
					if (imageLabel == null || imageLabel.isDisposed()) {
						return;
					}
					String path = imageLabel.getData().toString();
					Image image = loadDefaultBPMNImage(path);
					fitCanvas(image, canvas);
					canvas.setData(path);
				}

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			imageLabel.addMouseTrackListener(new MouseTrackListener() {
				
				/* (non-Javadoc)
				 * @see org.eclipse.swt.events.MouseTrackListener#mouseHover(org.eclipse.swt.events.MouseEvent)
				 */
				@Override
				public void mouseHover(MouseEvent e) {
					if (imageLabel == null || imageLabel.isDisposed()) {
						return;
					}
					imageLabel.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_TITLE_BACKGROUND));
				}
				
				/* (non-Javadoc)
				 * @see org.eclipse.swt.events.MouseTrackListener#mouseExit(org.eclipse.swt.events.MouseEvent)
				 */
				@Override
				public void mouseExit(MouseEvent e) {
					if (imageLabel == null || imageLabel.isDisposed()) {
						return;
					}
					imageLabel.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
					
				}
				
				/* (non-Javadoc)
				 * @see org.eclipse.swt.events.MouseTrackListener#mouseEnter(org.eclipse.swt.events.MouseEvent)
				 */
				@Override
				public void mouseEnter(MouseEvent e) {
					if (imageLabel == null || imageLabel.isDisposed()) {
						return;
					}
					imageLabel.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_TITLE_BACKGROUND));
				}
			});
	
			imageLabel.pack();
			editor.grabHorizontal = true;
			editor.setEditor(imageLabel, item, i);
		}
	}
	
	/** Reload image from a file
	 * @param filename image file
	 * @return swt image created from image file
	 */
	public Image loadImage(String file) {
		if (sourceImage != null && !sourceImage.isDisposed()) {
//			sourceImage.dispose();
			sourceImage = null;
		}
		if (file != null){
		ImageData imageData = new ImageData(file);
		sourceImage = new Image(Display.getDefault(), imageData);
		} else {
		sourceImage =BpmnImages.getInstance().getImage(file);
		}
		showOriginal();
		return sourceImage;
	}
	
	/** Reload image from BPMN Default Cache
	 * @param filename image file
	 * @return swt image created from image file
	 */
	public Image loadDefaultBPMNImage(String path) {
		if (sourceImage != null && !sourceImage.isDisposed()) {
//			sourceImage.dispose();
			sourceImage = null;
		}
		sourceImage = BpmnImages.getInstance().getImage(path);
		showOriginal();
		return sourceImage;
	}

	public void showOriginal() {
		if (sourceImage == null)
			return;
		transform = new AffineTransform();
		syncScrollBars();
	}
	
	/**
	 * @param gc
	 * @param canvas
	 */
	private void paint(GC gc, Canvas canvas) {
		Rectangle clientRect = canvas.getClientArea(); 
		Image copyScreenImage=null;
		if (sourceImage != null) {
			Rectangle imageRect = inverseTransformRect(transform, clientRect);
			int gap = 2; /* find a better start point to render */
			imageRect.x -= gap; imageRect.y -= gap;
			imageRect.width += 2 * gap; imageRect.height += 2 * gap;

			if (sourceImage.isDisposed()) {
				return;
			}
			
			Rectangle imageBound = sourceImage.getBounds();
			imageRect = imageRect.intersection(imageBound);
			Rectangle destRect = transformRect(transform, imageRect);
			
						
			screenImage = new Image(Display.getDefault(), clientRect.width, clientRect.height);
			copyScreenImage = new Image(screenImage.getDevice(), screenImage.getImageData());
			
			GC newGC = new GC(copyScreenImage);
			newGC.setClipping(clientRect);
			newGC.drawImage(
					sourceImage,
					imageRect.x,
					imageRect.y,
					imageRect.width,
					imageRect.height,
					destRect.x,
					destRect.y,
					destRect.width,
					destRect.height);
			newGC.dispose();

			gc.drawImage(copyScreenImage, 0, 0);
		} else {
			gc.setClipping(clientRect);
			gc.fillRectangle(clientRect);
		}
	}

	/**
	 * Fit the image onto the canvas
	 * @param sourceImage
	 * @param canvas
	 */
	public void fitCanvas(Image sourceImage, Canvas canvas) {
		if (sourceImage == null || sourceImage.isDisposed()) {
			return;
		}
		
		Rectangle imageBound = sourceImage.getBounds();
		Rectangle destRect = canvas.getClientArea();
		double sx = (double) destRect.width / (double) imageBound.width;
		double sy = (double) destRect.height / (double) imageBound.height;
		double s = Math.min(sx, sy);
		double dx = 0.5 * destRect.width;
		double dy = 0.5 * destRect.height;
		centerZoom(dx, dy, s, new AffineTransform());
	}

	/**
	 * Perform a zooming operation centered on the given point
	 * (dx, dy) and using the given scale factor. 
	 * The given AffineTransform instance is preconcatenated.
	 * @param dx center x
	 * @param dy center y
	 * @param scale zoom rate
	 * @param af original affinetransform
	 */
	public void centerZoom (
		double dx,
		double dy,
		double scale,
		AffineTransform af) {
		af.preConcatenate(AffineTransform.getTranslateInstance(-dx, -dy));
		af.preConcatenate(AffineTransform.getScaleInstance(scale, scale));
		af.preConcatenate(AffineTransform.getTranslateInstance(dx, dy));
		transform = af;
		syncScrollBars();
	}

	/**
	 * Given an arbitrary rectangle, get the rectangle with the given transform.
	 * The result rectangle is positive width and positive height.
	 * @param af AffineTransform
	 * @param src source rectangle
	 * @return rectangle after transform with positive width and height
	 */
	public static Rectangle transformRect(AffineTransform af, Rectangle src){
		Rectangle dest= new Rectangle(0,0,0,0);
		src=absRect(src);
		Point p1=new Point(src.x,src.y);
		p1=transformPoint(af,p1);
		dest.x=p1.x; dest.y=p1.y;
		dest.width=(int)(src.width*af.getScaleX());
		dest.height=(int)(src.height*af.getScaleY());
		return dest;
	}

	/**
	 * Given an arbitrary rectangle, get the rectangle with the inverse given transform.
	 * The result rectangle is positive width and positive height.
	 * @param af AffineTransform
	 * @param src source rectangle
	 * @return rectangle after transform with positive width and height
	 */
	public static Rectangle inverseTransformRect(AffineTransform af, Rectangle src){
		Rectangle dest= new Rectangle(0,0,0,0);
		src=absRect(src);
		Point p1=new Point(src.x,src.y);
		p1=inverseTransformPoint(af,p1);
		dest.x=p1.x; dest.y=p1.y;
		dest.width=(int)(src.width/af.getScaleX());
		dest.height=(int)(src.height/af.getScaleY());
		return dest;
	}

	/**
	 * Given an arbitrary point, get the point with the given transform.
	 * @param af affine transform
	 * @param pt point to be transformed
	 * @return point after tranform
	 */
	public static Point transformPoint(AffineTransform af, Point pt) {
		Point2D src = new Point2D.Float(pt.x, pt.y);
		Point2D dest= af.transform(src, null);
		Point point=new Point((int)Math.floor(dest.getX()), (int)Math.floor(dest.getY()));
		return point;
	}
	
	/**
	 * Given an arbitrary point, get the point with the inverse given transform.
	 * @param af AffineTransform
	 * @param pt source point
	 * @return point after transform
	 */
	public static Point inverseTransformPoint(AffineTransform af, Point pt){
		Point2D src=new Point2D.Float(pt.x,pt.y);
		try{
			Point2D dest= af.inverseTransform(src, null);
			return new Point((int)Math.floor(dest.getX()), (int)Math.floor(dest.getY()));
		}catch (Exception e){
			BpmnUIPlugin.log(e);
			return new Point(0,0);
		}
	}

	/**
	 * Given arbitrary rectangle, return a rectangle with upper-left 
	 * start and positive width and height.
	 * @param src source rectangle
	 * @return result rectangle with positive width and height
	 */
	public static Rectangle absRect(Rectangle src){
		Rectangle dest= new Rectangle(0,0,0,0);
		if(src.width<0) { dest.x=src.x+src.width+1; dest.width=-src.width; } 
		else{ dest.x=src.x; dest.width=src.width; }
		if(src.height<0) { dest.y=src.y+src.height+1; dest.height=-src.height; } 
		else{ dest.y=src.y; dest.height=src.height; }
		return dest;
	}
	
	/**
	 * Synchronize the scrollbar with the image. If the transform is out
	 * of range, it will correct it. This function considers only following
	 * factors :<b> transform, image size, client area</b>.
	 */
	public void syncScrollBars() {
		if (sourceImage == null) {
			canvas.redraw();
			return;
		}

		AffineTransform af = transform;
		double sx = af.getScaleX(), sy = af.getScaleY();
		double tx = af.getTranslateX(), ty = af.getTranslateY();
		if (tx > 0) tx = 0;
		if (ty > 0) ty = 0;

		ScrollBar horizontal = canvas.getHorizontalBar();
		horizontal.setIncrement((int) (canvas.getClientArea().width / 100));
		horizontal.setPageIncrement(canvas.getClientArea().width);
		if (sourceImage.isDisposed()) {
			canvas.redraw();
			return;
		}
		Rectangle imageBound = sourceImage.getBounds();
		int cw = canvas.getClientArea().width, ch = canvas.getClientArea().height;
		if (imageBound.width * sx > cw) { /* image is wider than client area */
			horizontal.setMaximum((int) (imageBound.width * sx));
			horizontal.setEnabled(true);
			if (((int) - tx) > horizontal.getMaximum() - cw)
				tx = -horizontal.getMaximum() + cw;
		} else { /* image is narrower than client area */
			horizontal.setEnabled(false);
			tx = (cw - imageBound.width * sx) / 2; //center if too small.
		}
		horizontal.setSelection((int) (-tx));
		horizontal.setThumb((int) (canvas.getClientArea().width));

		ScrollBar vertical = canvas.getVerticalBar();
		vertical.setIncrement((int) (canvas.getClientArea().height / 100));
		vertical.setPageIncrement((int) (canvas.getClientArea().height));
		if (imageBound.height * sy > ch) { /* image is higher than client area */
			vertical.setMaximum((int) (imageBound.height * sy));
			vertical.setEnabled(true);
			if (((int) - ty) > vertical.getMaximum() - ch)
				ty = -vertical.getMaximum() + ch;
		} else { /* image is less higher than client area */
			vertical.setEnabled(false);
			ty = (ch - imageBound.height * sy) / 2; //center if too small.
		}
		vertical.setSelection((int) (-ty));
		vertical.setThumb((int) (canvas.getClientArea().height));

		/* update transform. */
		af = AffineTransform.getScaleInstance(sx, sy);
		af.preConcatenate(AffineTransform.getTranslateInstance(tx, ty));
		transform = af;
		
		canvas.getHorizontalBar().setVisible(false);
		canvas.getVerticalBar().setVisible(false);

		canvas.redraw();
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
}