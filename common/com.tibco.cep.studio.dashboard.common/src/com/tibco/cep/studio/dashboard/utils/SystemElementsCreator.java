package com.tibco.cep.studio.dashboard.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationFactory;
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet;
import com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor;
import com.tibco.cep.designtime.core.model.beviewsconfig.Skin;
import com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet;

public class SystemElementsCreator {

	private String project;
	private String folder;
	private String namespace;

	public SystemElementsCreator(String project, String folder, String namespace) {
		this.project = project;
		this.folder = folder;
		this.namespace = namespace;
	}

	public List<Entity> create() throws Exception {
		List<Entity> persistElements = new ArrayList<Entity>();
		// get the root node
		Node rootNode = SystemElementsTemplate.getRootNode();
		// create the series colors
		Map<String, SeriesColor> seriesColors = createSeriesColors(rootNode);
		persistElements.addAll(seriesColors.values());
		// create chart component color set
		Map<String, ComponentColorSet> componentColorSets = createChartColorSets(rootNode, seriesColors);
		persistElements.addAll(componentColorSets.values());
		// create text component color set
		Map<String, ComponentColorSet> textComponentColorSets = createTextColorSets(rootNode, seriesColors);
		persistElements.addAll(textComponentColorSets.values());
		// add all the text component color set to the chart component color set map for skin de-referencing
		componentColorSets.putAll(textComponentColorSets);
		// create skin
		Skin skin = createSkin(rootNode, componentColorSets);
		persistElements.add(skin);

		return persistElements;
	}

	private Skin createSkin(Node configurationsNode, Map<String, ComponentColorSet> colorSetMap) throws Exception {
		String expression = "config[@type='Skin']";
		Node node = (Node) SystemElementsTemplate.get(configurationsNode, expression, XPathConstants.NODE);
		Skin skin = BEViewsConfigurationFactory.eINSTANCE.createSkin();
		skin.setGUID(GUIDGenerator.getGUID());
		skin.setName(SystemElementsTemplate.get(node, "@name"));
		skin.setDisplayName(SystemElementsTemplate.get(node, "attribute[@name='displayName']"));
		skin.setDescription(SystemElementsTemplate.get(node, "@name"));
		skin.setFolder(folder);
		skin.setNamespace(folder);
		skin.setOwnerProjectName(project);

		PropertyMap extendedPropertiesHolder = ModelFactory.eINSTANCE.createPropertyMap();
		SimpleProperty propertyObject = ModelFactory.eINSTANCE.createSimpleProperty();
		propertyObject.setName("system");
		propertyObject.setValue("true");
		extendedPropertiesHolder.getProperties().add(propertyObject);
		skin.setExtendedProperties(extendedPropertiesHolder);

		skin.setFontColor(SystemElementsTemplate.get(node, "attribute[@name='fontColor']"));
		skin.setComponentBackGroundColor(SystemElementsTemplate.get(node, "attribute[@name='componentBackGroundColor']"));
		skin.setComponentBackGroundGradientEndColor(SystemElementsTemplate.get(node, "attribute[@name='componentBackGroundGradientEndColor']"));
		skin.setComponentForeGroundColor(SystemElementsTemplate.get(node, "attribute[@name='componentForeGroundColor']"));
		skin.setVisualizationBackGroundColor(SystemElementsTemplate.get(node, "attribute[@name='visualizationBackGroundColor']"));
		skin.setVisualizationBackGroundGradientEndColor(SystemElementsTemplate.get(node, "attribute[@name='visualizationBackGroundGradientEndColor']"));
		skin.setVisualizationForeGroundColor(SystemElementsTemplate.get(node, "attribute[@name='visualizationForeGroundColor']"));

		NodeList defaultColorsetNodeList = (NodeList) SystemElementsTemplate.get(node, "attribute[@name='defaultComponentColorSet']/value", XPathConstants.NODESET);
		for (int j = 0; j < defaultColorsetNodeList.getLength(); j++) {
			Node defaultColorsetNode = defaultColorsetNodeList.item(j);
			String colorsetName = defaultColorsetNode.getTextContent();
			ComponentColorSet colorset = colorSetMap.get(colorsetName);
			skin.getDefaultComponentColorSet().add(colorset);
		}

		NodeList colorsetNodeList = (NodeList) SystemElementsTemplate.get(node, "attribute[@name='componentColorSet']/value", XPathConstants.NODESET);
		for (int k = 0; k < colorsetNodeList.getLength(); k++) {
			Node colorsetNode = colorsetNodeList.item(k);
			String colorsetName = colorsetNode.getTextContent();
			ComponentColorSet colorset = colorSetMap.get(colorsetName);
			skin.getComponentColorSet().add(colorset);
		}
		return skin;
	}

	private Map<String, ComponentColorSet> createTextColorSets(Node node, Map<String, SeriesColor> seriesColorMap) throws Exception {
		String expression = "config[@type='TextComponentColorSet']";
		Object colorSetNodes = SystemElementsTemplate.get(node, expression, XPathConstants.NODESET);
		NodeList nodeList = (NodeList) colorSetNodes;
		Map<String, ComponentColorSet> colorSetMap = new LinkedHashMap<String, ComponentColorSet>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node colorSetNode = nodeList.item(i);
			String name = SystemElementsTemplate.get(colorSetNode, "@name");
			TextComponentColorSet colorSet = BEViewsConfigurationFactory.eINSTANCE.createTextComponentColorSet();
			colorSet.setGUID(GUIDGenerator.getGUID());
			colorSet.setName(name);
			colorSet.setDisplayName(SystemElementsTemplate.get(colorSetNode, "attribute[@name='displayName']"));
			colorSet.setDescription(name);
			colorSet.setFolder(folder);
			colorSet.setNamespace(namespace);
			colorSet.setOwnerProjectName(project);

			colorSet.setHeaderColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='headerColor']"));
			colorSet.setHeaderRollOverColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='headerRollOverColor']"));
			colorSet.setHeaderTextFontColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='headerTextFontColor']"));
			colorSet.setHeaderSeparatorColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='headerSeparatorColor']"));
			colorSet.setCellColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='cellColor']"));
			colorSet.setCellTextFontColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='cellTextFontColor']"));
			colorSet.setRowSeparatorColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='rowSeparatorColor']"));
			colorSet.setRowRollOverColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='rowRollOverColor']"));

			colorSetMap.put(name, colorSet);
		}
		return colorSetMap;
	}

	private Map<String, ComponentColorSet> createChartColorSets(Node node, Map<String, SeriesColor> seriesColorMap) throws Exception {
		String expression = "config[@type='ChartComponentColorSet']";
		Object colorSetNodes = SystemElementsTemplate.get(node, expression, XPathConstants.NODESET);
		NodeList nodeList = (NodeList) colorSetNodes;
		Map<String, ComponentColorSet> colorSetMap = new LinkedHashMap<String, ComponentColorSet>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node colorSetNode = nodeList.item(i);
			String name = SystemElementsTemplate.get(colorSetNode, "@name");
			ChartComponentColorSet colorSet = BEViewsConfigurationFactory.eINSTANCE.createChartComponentColorSet();
			colorSet.setGUID(GUIDGenerator.getGUID());
			colorSet.setName(name);
			colorSet.setDisplayName(SystemElementsTemplate.get(colorSetNode, "attribute[@name='displayName']"));
			colorSet.setDescription(name);
			colorSet.setFolder(folder);
			colorSet.setNamespace(namespace);
			colorSet.setOwnerProjectName(project);

			colorSet.setGuideLineLabelFontColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='guideLineLabelFontColor']"));
			colorSet.setGuideLineValueLabelFontColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='guideLineValueLabelFontColor']"));
			colorSet.setDataPointLabelFontColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='dataPointLabelFontColor']"));
			colorSet.setTopCapColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='topCapColor']"));
			colorSet.setPieEdgeColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='pieEdgeColor']"));
			colorSet.setPieDropShadowColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='pieDropShadowColor']"));
			colorSet.setLineDropShadowColor(SystemElementsTemplate.get(colorSetNode, "attribute[@name='lineDropShadowColor']"));

			NodeList seriesColorNodeList = (NodeList) SystemElementsTemplate.get(colorSetNode, "attribute[@name='seriesColor']/value", XPathConstants.NODESET);
			for (int j = 0; j < seriesColorNodeList.getLength(); j++) {
				Node seriesColorNode = seriesColorNodeList.item(j);
				String seriesColorName = seriesColorNode.getTextContent();
				SeriesColor seriesColor = seriesColorMap.get(seriesColorName);
				colorSet.getSeriesColor().add(seriesColor);
			}
			colorSetMap.put(name, colorSet);
		}
		return colorSetMap;
	}

	private Map<String, SeriesColor> createSeriesColors(Node node) throws XPathExpressionException, Exception {
		String expression = "config[@type='SeriesColor']";
		Object seriesColorNodes = SystemElementsTemplate.get(node, expression, XPathConstants.NODESET);
		NodeList nodeList = (NodeList) seriesColorNodes;
		Map<String, SeriesColor> seriesColors = new LinkedHashMap<String, SeriesColor>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node seriesColorNode = nodeList.item(i);
			String name = SystemElementsTemplate.get(seriesColorNode, "@name");
			String color = SystemElementsTemplate.get(seriesColorNode, "attribute[@name='baseColor']");
			String highLightColor = SystemElementsTemplate.get(seriesColorNode, "attribute[@name='highLightColor']");
			if (highLightColor == null || highLightColor.trim().length() == 0) {
				highLightColor = "FFFFFF";
			}
			SeriesColor seriesColor = BEViewsConfigurationFactory.eINSTANCE.createSeriesColor();
			seriesColor.setGUID(GUIDGenerator.getGUID());
			seriesColor.setName(name);
			seriesColor.setDescription(name);
			seriesColor.setFolder(folder);
			seriesColor.setNamespace(namespace);
			seriesColor.setOwnerProjectName(project);
			seriesColor.setBaseColor(color);
			seriesColor.setHighlightColor(highLightColor);
			seriesColors.put(name, seriesColor);
		}
		return seriesColors;
	}

}
