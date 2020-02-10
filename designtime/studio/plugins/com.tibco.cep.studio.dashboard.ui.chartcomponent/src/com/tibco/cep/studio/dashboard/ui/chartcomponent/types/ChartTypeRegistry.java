package com.tibco.cep.studio.dashboard.ui.chartcomponent.types;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.w3c.dom.Node;

import com.tibco.cep.studio.dashboard.core.util.XMLUtil;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.DashboardChartPlugin;

public class ChartTypeRegistry {

	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    
	private static ChartTypeRegistry instance;

	public static final synchronized ChartTypeRegistry getInstance() {
		if (instance == null) {
			instance = new ChartTypeRegistry();
		}
		return instance;
	}

	private Map<String, ChartType> typeMap;

	private ChartTypeRegistry() {
		try {
			typeMap = new LinkedHashMap<String, ChartType>();
			InputStream stream = this.getClass().getResourceAsStream("charttypes.xml");
			Node docNode = XMLUtil.parse(stream);
			Iterator<Node> typeNodesIterator = XMLUtil.getAllNodes(docNode, "type");
			while (typeNodesIterator.hasNext()) {
				Node typeNode = (Node) typeNodesIterator.next();
				ChartType chartType = parseTypeNode(typeNode);
				if (chartType != null) {
					typeMap.put(chartType.getId(), chartType);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("could not chart types registry", e);
		}
	}

	private ChartType parseTypeNode(Node typeNode) {
		String id = XMLUtil.getString(typeNode, "@id");
		try {
			//chart type information 
			String family = XMLUtil.getString(typeNode, "@family");
			String name = XMLUtil.getString(typeNode, "name");
			String desc = XMLUtil.getString(typeNode, "desc");
			desc = desc.replace("<br>", "\n");
			String processor = XMLUtil.getString(typeNode, "processor");
			TypeProcessor typeProcessor = (TypeProcessor) Class.forName(processor).newInstance();
			//chart sub type group information
			List<ChartSubTypeGroup> subTypeGroups = new ArrayList<ChartSubTypeGroup>();
			Iterator<Node> subTypeGroupNodesIterator = XMLUtil.getAllNodes(typeNode, "subtypegroup");
			while (subTypeGroupNodesIterator.hasNext()) {
				Node subTypeGroupNode = (Node) subTypeGroupNodesIterator.next();
				ChartSubTypeGroup chartSubTypeGroup = parseSubTypeGroupNode(subTypeGroupNode);
				subTypeGroups.add(chartSubTypeGroup);
			}
			ChartType chartType = new ChartType(id, family, name, desc, typeProcessor, subTypeGroups.toArray(new ChartSubTypeGroup[subTypeGroups.size()]));
			chartType.setWizardOptionsFormProvider(XMLUtil.getString(typeNode, "wizardoptionsprovider"));
			chartType.setEditorOptionsFormProvider(XMLUtil.getString(typeNode, "editoroptionsprovider"));
			chartType.setWizardDataFormProvider(XMLUtil.getString(typeNode, "wizarddataprovider"));
			chartType.setEditorDataFormProvider(XMLUtil.getString(typeNode, "editordataprovider"));
			return chartType;
		} catch (Exception e) {
			DashboardChartPlugin.getDefault().getLog().log(new Status(IStatus.WARNING, DashboardChartPlugin.PLUGIN_ID, "could not load chart type [" + id + "]", e));
			return null;
		}
	}

	private ChartSubTypeGroup parseSubTypeGroupNode(Node subTypeGroupNode) {
		String subTypeGroupId = XMLUtil.getString(subTypeGroupNode, "@id");
		String subTypeGroupName = XMLUtil.getString(subTypeGroupNode, "name");
		//chart sub type information
		List<ChartSubType> subTypes = new ArrayList<ChartSubType>();
		Iterator<Node> subTypeNodesIterator = XMLUtil.getAllNodes(subTypeGroupNode, "subtype");
		while (subTypeNodesIterator.hasNext()) {
			Node subTypeNode = (Node) subTypeNodesIterator.next();
			ChartSubType chartSubType = parseSubTypeNode(subTypeNode);
			subTypes.add(chartSubType);
		}
		ChartSubTypeGroup chartSubTypeGroup = new ChartSubTypeGroup(subTypeGroupId,subTypeGroupName,subTypes.toArray(new ChartSubType[subTypes.size()]));
		return chartSubTypeGroup;
	}

	private ChartSubType parseSubTypeNode(Node subTypeNode) {
		String id = XMLUtil.getString(subTypeNode, "@id");
		String name = XMLUtil.getString(subTypeNode, "name");
		String desc = XMLUtil.getString(subTypeNode, "desc");
		desc = desc.replace("<br>", "\n");
		return new ChartSubType(id, name, desc);
	}

	public ChartType get(String id) {
		return typeMap.get(id);
	}

	public Collection<ChartType> getTypes() {
		return typeMap.values();
	}
	
	public static void main(String[] args) {
		Collection<ChartType> types = ChartTypeRegistry.getInstance().getTypes();
		System.out.println(types);
	}
}
