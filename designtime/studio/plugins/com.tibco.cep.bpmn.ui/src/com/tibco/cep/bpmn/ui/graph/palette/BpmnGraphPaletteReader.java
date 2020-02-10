package com.tibco.cep.bpmn.ui.graph.palette;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.ui.IEditorSite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.studio.ui.palette.PaletteEntryUI.Tool;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * 
 * @author ggrigore
 *
 */
public class BpmnGraphPaletteReader {

	private String filename;
	@SuppressWarnings("unused")
	private File file;
	private IEditorSite site;
	private Palette palette;
	private List<PaletteDrawer> paletteSections;
	private BpmnPaletteEntry paletteUI;
	
	public BpmnGraphPaletteReader(BpmnPaletteEntry paletteUI,
		Palette palette,
		IEditorSite site) {
		this.site = site;
		this.palette = palette;
		this.paletteUI = paletteUI;
	}
	
	public void read() throws Exception {
		this.read(this.filename);
	}
	
	public void read(String filename) throws Exception {
		
		if (filename == null || filename.length() == 0) {
			throw new IOException(BpmnMessages.getString("bpmnGraphPalettereader_exception_message"));
		}
		
		if (this.paletteSections == null) {
			this.paletteSections = new LinkedList<PaletteDrawer>();
		}
		else {
			this.paletteSections.clear();
		}
		
		this.parsePaletteSections(filename);
	}
	
	private void parsePaletteSections(String filename) throws Exception {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        File palettefile = new File(filename);
        if(palettefile.exists() && palettefile.isFile()) {
        	Document doc = docBuilder.parse(palettefile);
        	// normalize text representation
        	doc.getDocumentElement().normalize();
        	List<Element> paletteSections = this.getElements(doc, doc.getDocumentElement().getNodeName());
        	if (paletteSections != null) {
        		for (Element paletteSection : paletteSections) {
        			this.processPaletteSection(paletteSection);
        		}
        	}
        } else {
        	throw new FileNotFoundException("Palette file:"+filename+" not found.");
        }
	}
	
	private List<Element> getElements(Node doc, String nodeName) {
		List<Element> elements = new LinkedList<Element>();
        NodeList topLevelBlock = null;
        if (doc instanceof Document) {
        	topLevelBlock = ((Document) doc).getElementsByTagName(nodeName);
        }
        else if (doc instanceof Element) {
        	topLevelBlock = ((Element) doc).getElementsByTagName(nodeName);
        }

        if (topLevelBlock != null) {
            // get all the preferences of this section
            NodeList allPaletteSections = topLevelBlock.item(0).getChildNodes();
            int numberOfPalettes = allPaletteSections.getLength();
            Element palette;
            for (int j = 0; j < numberOfPalettes; j++) {
                if (allPaletteSections.item(j).getNodeType() == Node.ELEMENT_NODE) {
                	palette = (Element) allPaletteSections.item(j);
                	elements.add(palette);
                }
            }
        }
		
        return elements;
	}
	
	private void processPaletteSection(Element element) {
        String title = null;
        String label = null;
		boolean visible = true;
		String tooltip = null;
		String icon = null;

        if (element.getNodeName().compareTo(BpmnGraphPaletteConstants.SECTION) == 0) {
        	title = element.getAttribute(BpmnGraphPaletteConstants.TITLE);

        	Element child = (Element) element.getElementsByTagName(BpmnGraphPaletteConstants.LABEL).item(0);
            if (child != null) {
            	String labelAttr = child.getAttribute(BpmnGraphPaletteConstants.VALUE);
            	label = labelAttr;
            }

        	child = (Element) element.getElementsByTagName(BpmnGraphPaletteConstants.VISIBLE).item(0);
            if (child != null) {
            	String visibleAttr = child.getAttribute(BpmnGraphPaletteConstants.VALUE);
            	visible = Boolean.parseBoolean(visibleAttr);
            }

            child = (Element) element.getElementsByTagName(BpmnGraphPaletteConstants.TOOLTIP).item(0);
            if (child != null) {
            	tooltip = child.getAttribute(BpmnGraphPaletteConstants.VALUE);
            }

            child = (Element) element.getElementsByTagName(BpmnGraphPaletteConstants.ICON).item(0);
            if (child != null) {
            	icon = child.getAttribute(BpmnGraphPaletteConstants.VALUE);
            }
        }
        
        List<PaletteItem> items = new LinkedList<PaletteItem>();
        
        List<Element> paletteSections = this.getElements(element, BpmnGraphPaletteConstants.PALETTEITEMS);
        if (paletteSections != null) {
	        for (Element paletteSection : paletteSections) {
	            items.add(this.processPaletteItem(paletteSection));
	        }
        }
       
        if (visible) {
        	this.paletteSections.add(this.createPaletteSection(title, label, tooltip, icon, items));
        }
	}
	
	private PaletteDrawer createPaletteSection(
			String title, String label, String tooltip, String icon, List<PaletteItem> items) {

		PaletteDrawer paletteSection = new PaletteDrawer(title, label, tooltip, icon, this.palette, false);
		paletteSection.setGlobal(false);

		for (PaletteItem item : items) {
			paletteSection.addPaletteEntry(
				this.paletteUI.createPaletteEntry(this.site.getPage(),
					paletteSection, 
					null,
					item.name,
					item.tooltip,
					item.paletteIcon,
					BpmnGraphUIFactory.getInstance(
						this.paletteUI.getLayoutManager(this.site.getPage())).getNodeUIFactory(
						item.name,
						"",
						"",
						item.type), // class spec: "model.semantic.process.activities.Task"
					Tool.NONE, false));
		}

		return paletteSection;
	}

	private PaletteItem processPaletteItem(Element element) {
		PaletteItem item = new PaletteItem();

        if (element.getNodeName().compareTo(BpmnGraphPaletteConstants.ITEM) == 0) {
        	item.name = element.getAttribute(BpmnGraphPaletteConstants.NAME);

        	Element child = (Element) element.getElementsByTagName(BpmnGraphPaletteConstants.LABEL).item(0);
            if (child != null) {
            	String labelAttr = child.getAttribute(BpmnGraphPaletteConstants.VALUE);
            	item.label = labelAttr;
            }

        	child = (Element) element.getElementsByTagName(BpmnGraphPaletteConstants.VISIBLE).item(0);
            if (child != null) {
            	String visibleAttr = child.getAttribute(BpmnGraphPaletteConstants.VALUE);
            	item.visible = Boolean.parseBoolean(visibleAttr);
            }

            child = (Element) element.getElementsByTagName(BpmnGraphPaletteConstants.TOOLTIP).item(0);
            if (child != null) {
            	item.tooltip = child.getAttribute(BpmnGraphPaletteConstants.VALUE);
            }

            child = (Element) element.getElementsByTagName(BpmnGraphPaletteConstants.PALETTEICON).item(0);
            if (child != null) {
            	item.paletteIcon = child.getAttribute(BpmnGraphPaletteConstants.VALUE);
            }

            child = (Element) element.getElementsByTagName(BpmnGraphPaletteConstants.PROCESSICON).item(0);
            if (child != null) {
            	item.processIcon = child.getAttribute(BpmnGraphPaletteConstants.VALUE);
            }
            
            child = (Element) element.getElementsByTagName(BpmnGraphPaletteConstants.TASK).item(0);
            if (child != null) {
            	item.task = child.getAttribute(BpmnGraphPaletteConstants.VALUE);
            }
            
            child = (Element) element.getElementsByTagName(BpmnGraphPaletteConstants.TYPE).item(0);
            if (child != null) {
            	String type = child.getAttribute(BpmnGraphPaletteConstants.VALUE);
            	item.type = ExpandedName.parse(type);
            }
        }

		return item;
	}

	public List<PaletteDrawer> getPaletteSections() {
		return this.paletteSections;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}

	
	class PaletteItem {
		String name;
		String label;
		ExpandedName type;
		boolean visible = true;
		String tooltip;
		String paletteIcon;
		String processIcon;
		String task;
	}
}
