package com.tibco.cep.studio.cluster.topology.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyEditor;
import com.tibco.cep.studio.cluster.topology.model.impl.ClusterTopology;
import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentUnitImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.HostResourceImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.ProcessingUnitConfigImpl;
import com.tibco.cep.studio.cluster.topology.ui.SiteEntityFactory;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

public class ClusterTopologyUtils {

	 
	 

	public static final String CLUSTER_TOPOLOGY_JAXB_PACKAGE = "com.tibco.cep.studio.cluster.topology.model";

	public static final String UNIQUE_HOST_ID = "HR_";
	public static final String UNIQUE_PROCESSING_UNIT = "PUID_";
	public static final String UNIQUE_QUERY_AGENT = "QueryAgent_";
	public static final String UNIQUE_INFERENCE_AGENT = "InferenceAgent_";
	public static final String UNIQUE_DASHBORAD_AGENT = "DashboardAgent_";
	public static final String UNIQUE_DEPLOYMENT_UNIT = "DU_";

	private static Set<String> DUIdsSet = new HashSet<String>();
	private static Set<String> PUIDSet = new HashSet<String>();
	private static Set<String> HRIDSet = new HashSet<String>();
	
	@SuppressWarnings("unused")
	private static final String IP_ADDRESS = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
											 "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."  +
											 "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."  +
											 "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$" 
											;
	
	public static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	public static final Pattern pattern = Pattern.compile("^(?:" + _255 + "\\.){3}" + _255 + "$|((l|L)(o|O)(c|C)(a|A)(l|L)(h|H)(o|O)(s|S)(t|T))");

	static public Object getNodeProperty(TSENode node, String propertyName) {
		return node.getAttributeValue(propertyName);
	}

	/**
	 * return ClusterTopologyConstants.CLUSTER_NODE or
	 * ClusterTopologyConstants.HOST_NODE or ClusterTopologyConstants.DU_NODE or
	 * ClusterTopologyConstants.PU_NODE or ClusterTopologyConstants.AGENT_NODE
	 * or ClusterTopologyConstants.UNKNOWN_NODE
	 */
	static public int getNodeType(TSENode node) {
		int type = ClusterTopologyConstants.UNKNOWN_NODE;
		Object nodeType = getNodeProperty(node,
				ClusterTopologyConstants.NODE_TYPE);
		if (nodeType != null) {
			type = ((Integer) nodeType).intValue();
		}

		return type;
	}

	/**
	 * @param graph
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getNodeName(TSEGraph graph,
			String uniqueStateIdentifier) {
		List<TSENode> nodes = graph.nodes();
		List<Integer> noList = new ArrayList<Integer>();
		String id;
		String no;
		try {

			for (TSENode node : nodes) {
				if (node.getUserObject() instanceof ClusterTopology) {
					ClusterTopology clusterTopology = (ClusterTopology) node
							.getUserObject();
					if (clusterTopology instanceof DeploymentUnitImpl
							&& uniqueStateIdentifier
									.equals(UNIQUE_DEPLOYMENT_UNIT)) {
						DeploymentUnitImpl deploymentUnit = (DeploymentUnitImpl) clusterTopology;
						id = deploymentUnit.getId();
						if (id.startsWith(UNIQUE_DEPLOYMENT_UNIT)) {
							no = id.substring(id.indexOf("_") + 1);
							noList.add(getValidNo(no));
						}
					} else if (clusterTopology instanceof HostResourceImpl
							&& uniqueStateIdentifier.equals(UNIQUE_HOST_ID)) {
						HostResourceImpl hostResource = (HostResourceImpl) clusterTopology;
						id = hostResource.getId();
						if (id.startsWith(UNIQUE_HOST_ID)) {
							no = id.substring(id.indexOf("_") + 1);
							noList.add(getValidNo(no));
						}
					} else if (clusterTopology instanceof ProcessingUnitConfigImpl
							&& uniqueStateIdentifier
									.equals(UNIQUE_PROCESSING_UNIT)) {
						ProcessingUnitConfigImpl processingUnit = (ProcessingUnitConfigImpl) clusterTopology;
						id = processingUnit.getId();
						if (id.startsWith(UNIQUE_PROCESSING_UNIT)) {
							no = id.substring(id.indexOf("_") + 1);
							noList.add(getValidNo(no));
						}
					}
				}
			}
			if (noList.size() > 0) {
				java.util.Arrays.sort(noList.toArray());
				int num = noList.get(noList.size() - 1).intValue();
				num++;
				return uniqueStateIdentifier + num;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uniqueStateIdentifier + 0;
	}

	/*public static int getNodeNameNumber(int nodeType) {
		List<Integer> noList = new ArrayList<Integer>();
		String numStr;
		switch (nodeType) {
		case ClusterTopologyConstants.HOST_NODE :
			for (HRName hrName : HRNameList) {
				if (hrName.getHRName().startsWith(UNIQUE_HOST_ID)) {
					numStr = hrName.getHRName().substring(hrName.getHRName().indexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
			break;
		case ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE :
			Iterator<DUName> DUNames = DUNamesSet.iterator();
			DUName duName;
			while (DUNames.hasNext()) {
				duName = DUNames.next();
				if (duName.getDUName().startsWith(UNIQUE_DEPLOYMENT_UNIT)) {
					numStr = duName.getDUName().substring(duName.getDUName().indexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
			break;
		case ClusterTopologyConstants.PU_NODE :
			Iterator<String> PUIDs = PUIDSet.iterator();
			String puId;
			while (PUIDs.hasNext()) {
				puId = PUIDs.next();
				if (puId.startsWith(UNIQUE_PROCESSING_UNIT)) {
					numStr = puId.substring(puId.indexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
			break;
		default :
			break;
		}
		
		if (noList.size() > 0) {
			Object[] noArray = noList.toArray();
			java.util.Arrays.sort(noArray);
			int num = ((Integer)noArray[noArray.length - 1]).intValue();
			num++;
			return num;
		}
		return 0;
	}*/
	
	public static int getNodeIdNumber(int nodeType) {
		List<Integer> noList = new ArrayList<Integer>();
		String numStr;
		switch (nodeType) {
		case ClusterTopologyConstants.HOST_NODE :
			Iterator<String> HRIDs = HRIDSet.iterator();
			String hrId;
			while (HRIDs.hasNext()) {
				hrId = HRIDs.next();
				if (hrId.startsWith(UNIQUE_HOST_ID)) {
					numStr = hrId.substring(hrId.indexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
			break;
		case ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE :
			Iterator<String> DUIDs = DUIdsSet.iterator();
			String duId;
			while (DUIDs.hasNext()) {
				duId = DUIDs.next();
				if (duId.startsWith(UNIQUE_DEPLOYMENT_UNIT)) {
					numStr = duId.substring(duId.indexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
			break;
		case ClusterTopologyConstants.PU_NODE :
			Iterator<String> PUIDs = PUIDSet.iterator();
			String puId;
			while (PUIDs.hasNext()) {
				puId = PUIDs.next();
				if (puId.startsWith(UNIQUE_PROCESSING_UNIT)) {
					numStr = puId.substring(puId.indexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
			break;
		default :
			break;
		}
		
		if (noList.size() > 0) {
			Object[] noArray = noList.toArray();
			java.util.Arrays.sort(noArray);
			int num = ((Integer)noArray[noArray.length - 1]).intValue();
			num++;
			return num;
		}
		return 0;
	}

	/**
	 * @param tag
	 * @return
	 */
	public static String getUniqueTag(String tag) {
		List<Integer> noList = new ArrayList<Integer>();
		if (tag.endsWith("_") && tag.lastIndexOf("_") != -1) {
			String sno = tag.substring(tag.lastIndexOf("_") + 1);
			noList.add(getValidNo(sno));
			if (noList.size() > 0) {
				java.util.Arrays.sort(noList.toArray());
				int no = noList.get(noList.size() - 1).intValue();
				no++;
				return tag + no;
			}
		}

		return tag + 0;
	}

	/**
	 * @param no
	 * @return
	 */
	public static int getValidNo(String no) {
		int n;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
			return 0;
		}
		return n;
	}

	/**
	 * @param clusterEditor
	 */
	public static void editorDirty(final ClusterTopologyEditor clusterEditor) {
		Thread currentThread = Thread.currentThread();
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (currentThread == display.getThread()) {
			clusterEditor.modified();
		} else {
			display.asyncExec(new Runnable() {
				public void run() {
					clusterEditor.modified();
				}
			});
		}
	}

	public static Set<String> getDUIdsSet() {
		return DUIdsSet;
	}

	

	public static Set<String> getPUIDSet() {
		return PUIDSet;
	}
	
	

	public static Set<String> getHRIDSet() {
		return HRIDSet;
	}

	
	/**
	 * @param newName
	 * @param names
	 * @return
	 */
	public static boolean checkDuplicate(String newName, Collection<String> names) {
		for (String name:names) {
			if (name.equalsIgnoreCase(newName)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * @param map
	 * @param nodes
	 */
	public static void addToEditGraphMap(Map<String, Object> map, List<TSENode> nodes) {
		map.clear();
		SiteEntityFactory siteFactory=new SiteEntityFactory();
		for(TSENode node: nodes) {
		 if(node.getUserObject()instanceof DeploymentUnitImpl){
			DeploymentUnitImpl duImpl=(DeploymentUnitImpl) node.getUserObject();
			try {
				DeploymentUnitImpl newDuImpl=siteFactory.newDeploymentUnitImpl(duImpl);
				//map.put(duImpl.getName(), newDuImpl);
				map.put(duImpl.getName(), newDuImpl);
			} catch (Exception e) {
				e.printStackTrace();
			}
			}else if(node.getUserObject() instanceof HostResourceImpl){
				HostResourceImpl hostImpl=(HostResourceImpl) node.getUserObject();
				try {
					HostResourceImpl newHostImpl=siteFactory.newHostResourceImpl(hostImpl);
					map.put(hostImpl.getId(), newHostImpl);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(node.getUserObject() instanceof ProcessingUnitConfigImpl){
				ProcessingUnitConfigImpl proUnitImpl=(ProcessingUnitConfigImpl) node.getUserObject();
				try {
					ProcessingUnitConfigImpl newProUnitImpl=siteFactory.newProcessingUnitConfigImpl(proUnitImpl, proUnitImpl.getDeploymentUnitImpl()) ;
					map.put(proUnitImpl.getId(), newProUnitImpl);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		/*//For Selected Edges
		for(TSEEdge edge: selectedEdges) {
			StateTransition transition = (StateTransition)edge.getUserObject();
			try {
				StateTransition newTransition = (StateTransition)EcoreUtil.copy(transition);
				map.put(transition.getName(), newTransition);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
	}
	
	/**
	 * @param ip
	 * @return
	 */
	public static boolean isValidIP(String ip) {
		return pattern.matcher(ip).matches();
	}

	
}
