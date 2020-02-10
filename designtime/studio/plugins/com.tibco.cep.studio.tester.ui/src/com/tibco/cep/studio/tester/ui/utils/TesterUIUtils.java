package com.tibco.cep.studio.tester.ui.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.xml.sax.InputSource;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.designtime.core.model.domain.Single;
import com.tibco.cep.designtime.core.model.domain.impl.RangeImpl;
import com.tibco.cep.designtime.core.model.domain.util.DomainUtils;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.tester.core.provider.TesterEngine;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.actions.RuleSessionDynamicContributionItem;
import com.tibco.cep.studio.tester.ui.actions.ShowResultAction;
import com.tibco.cep.studio.tester.ui.actions.ShowWMObjectsAction;
import com.tibco.cep.studio.tester.utilities.PropertyTableConstants;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;

public class TesterUIUtils extends DomainUtils {

	public static String ENTITY_NS = "www.tibco.com/be/ontology";
	
	public static String ENTITY_CONCEPT_EXT = ".concept";
	public static String ENTITY_EVENT_EXT = ".event";
	public static String ENTITY_SCORECARD_EXT = ".scorecard";

	/**
	 * @param menu
	 * @param list
	 * 
	 *            Populate menu like this DebugTargetName -> RuleSessionName.
	 *            Both these parameters should be available in action.
	 */
	public static void populateWMSessions(Menu menu,
			List<IContributionItem> list) throws Exception {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		Set<IRuleRunTarget> runTargets = TesterEngine.INSTANCE.getAllTargets();
		ShowWMObjectsAction act = null;
		ActionContributionItem item = null;
		if (runTargets.size() == 0) {
			Separator separator = new Separator();
			if (menu != null) {
				separator.fill(menu, -1);
			}
			if (list != null) {
				list.add(separator);
			}
			act = new ShowWMObjectsAction(window, null, Messages
					.getString("tester.no.sessions"), "", "");
			item = new ActionContributionItem(act);
			act.setEnabled(false);
			if (menu != null) {
				item.fill(menu, -1);
			}
			if (list != null) {
				list.add(item);
			}
			return;
		}
		for (IRuleRunTarget runTarget : runTargets) {
			String[] ruleSessions = TesterEngine.INSTANCE
					.getRuleSessions(runTarget);
			List<ActionContributionItem> items = new ArrayList<ActionContributionItem>();
			for (String session : ruleSessions) {
				StudioTesterUIPlugin.debug("Current running sessions:{0}",
						session);
				String name = runTarget.getName();
				StringBuilder stringBuilder = new StringBuilder(name);
				stringBuilder.append("/");
				stringBuilder.append(session);
				ShowWMObjectsAction act2 = new ShowWMObjectsAction(window,
						runTarget, name, session, stringBuilder.toString());
				ActionContributionItem item2 = new ActionContributionItem(act2);
				items.add(item2);

			}
			MenuManager runTargetSubMenu = new MenuManager(runTarget.getName(),
					"");
			RuleSessionDynamicContributionItem ruleSessionContribItem = new RuleSessionDynamicContributionItem(
					items);
			runTargetSubMenu.add(ruleSessionContribItem);
			if (menu != null) {
				runTargetSubMenu.fill(menu, -1);
			}
			if (list != null) {
				list.add(runTargetSubMenu);
			}
		}
	}

	/**
	 * @param menu
	 * @param list
	 */
	@SuppressWarnings("unused")
	public static void populateTesterRuns(Menu menu,
			List<IContributionItem> list) {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		Collection<String> sessions = TesterEngine.INSTANCE.getRunningSessions();
		List<String> testerSessions = new ArrayList<String>();
		// for (String RSPName : sessions) {
		// for (TesterSession session :
		// TesterEngine.INSTANCE.getRSP(RSPName).getAllConnectedSessions()) {
		// Iterator<TesterRun> currentRuns = session.getCurrentRuns();
		// while (currentRuns.hasNext()) {
		// testerSessions.add(new
		// StringBuilder(RSPName).append("/").append(session.getSessionName()).append("/").append(currentRuns.next().getRunName()).toString());
		// }
		// }
		// }
		ShowResultAction act = null;
		ActionContributionItem item = null;
		if (testerSessions.size() == 0) {
			Separator separator = new Separator();
			if (menu != null) {
				separator.fill(menu, -1);
			}
			if (list != null) {
				list.add(separator);
			}
			act = new ShowResultAction(window, "No Tester Runs", "");
			item = new ActionContributionItem(act);
			act.setEnabled(false);
			if (menu != null) {
				item.fill(menu, -1);
			}
			if (list != null) {
				list.add(item);
			}
			return;
		}
		for (String session : testerSessions) {
			StudioTesterUIPlugin.debug("Current running sessions:{0}", session);

			act = new ShowResultAction(window, session, session);
			item = new ActionContributionItem(act);
			if (menu != null) {
				item.fill(menu, -1);
			}
			if (list != null) {
				list.add(item);
			}
		}
		// Separator separator = new Separator();
		//		
		// if (menu != null) {
		// separator.fill(menu, -1);
		// }
		// if (list != null) {
		// list.add(separator);
		// }
		//		
		// act = new ShowResultAction(window,
		// Messages.getString("tester.others"),
		// Messages.getString("tester.others.tooltip"));
		// item = new ActionContributionItem(act);
		// if (menu != null) {
		// item.fill(menu, -1);
		// }
		// if (list != null) {
		// list.add(item);
		// }
	}
	
	/**
	 * @param file
	 * @return
	 * @throws Exception
	 */
//	public static void removeEncodeUTF_8(File file) throws Exception {
//		// Removing "<?xml version="1.0" encoding="UTF-8"?>"
//		BufferedReader reader = new BufferedReader(new FileReader(file));
//		String line = "", oldtext = "";
//		int i = 0;
//		while ((line = reader.readLine()) != null) {
//			if (i > 0) {
//				oldtext += line + "\r\n";
//			}
//			i++;
//		}
//		reader.close();
//		FileWriter writer = new FileWriter(file);
//		StudioTesterUIPlugin.debug(oldtext);
//		writer.write(oldtext);
//		writer.flush();
//		writer.close();
//	}

	/**
	 * @param domainInstances
	 * @param projectName
	 * @return
	 */
	public static String[][] getDomainEntryDescriptions(
			List<DomainInstance> domainInstances, String projectName) {
		List<String> valList = new ArrayList<String>();
		List<String> descList = new ArrayList<String>();
		List<String[]> values = new ArrayList<String[]>();

		if (domainInstances != null && domainInstances.size() > 0) {
			for (DomainInstance domainInstance : domainInstances) {
				// Get the domain corresponding to this instance
				String domainPath = domainInstance.getResourcePath();
				Domain domain = IndexUtils.getDomain(projectName, domainPath);
				// Do for all super domains
				Domain superDomain = domain;
				while (superDomain != null) {
					List<String> vals = getValArray(superDomain, superDomain
							.getEntries(), true);
					valList.addAll(vals);
					List<String> descs = getDescArray(superDomain, superDomain
							.getEntries());
					descList.addAll(descs);
					String superDomainPath = superDomain.getSuperDomainPath();
					/**
					 * This is done to get updated value from index, and not the
					 * cached in-memory one
					 */
					superDomain = IndexUtils.getDomain(projectName,
							superDomainPath);
				}
			}
		}
		for (int index = 0; index < valList.size(); index++) {
			String val = valList.get(index);
			String desc = descList.get(index);
			values.add(new String[] { val, desc });
		}

		String[][] valArray = new String[values.size()][2];
		if (valArray.length > 0) {
			return values.toArray(valArray);
		}
		return new String[0][0];
	}

	/**
	 * @param domainInstances
	 * @param resourcePath
	 * @param projectName
	 * @return
	 */
	public static List<String> getDomainEntryStrings(
			List<DomainInstance> domainInstances,String projectName) {
		List<String> valList = new ArrayList<String>();

		if (domainInstances != null && domainInstances.size() > 0) {
			valList.add("*");
			for (DomainInstance domainInstance : domainInstances) {
				// Get the domain corresponding to this instance
				String domainPath = domainInstance.getResourcePath();
				Domain domain = IndexUtils.getDomain(projectName, domainPath);

				// Do for all super domains
				Domain superDomain = domain;
				while (superDomain != null) {
					List<String> vals = getValArray(superDomain, superDomain
							.getEntries(), true);
					valList.addAll(vals);
					String superDomainPath = superDomain.getSuperDomainPath();
					/**
					 * This is done to get updated value from index, and not the
					 * cached in-memory one
					 */
					superDomain = IndexUtils.getDomain(projectName,
							superDomainPath);
				}
				/*
				 * if (valList != null && valList.size() > 0) { String dbRef =
				 * domain.getDbRef(); if (dbRef != null &&
				 * !dbRef.trim().equals("")) { // get domain from dbref Domain
				 * dm = getDomain(dbRef, domainModel); String[] valArr =
				 * getValArray(dm, domainEntries, valList); // merge both the
				 * arrays String[] compArray = new String[vals.length +
				 * valArr.length]; System .arraycopy(vals, 0, compArray, 0,
				 * vals.length); System.arraycopy(valArr, 0, compArray,
				 * vals.length, valArr.length); return compArray; } else {
				 * return vals; } //TODO Domain from DB concept
				 * 
				 * 
				 * } else { String dbRef = domain.getDbRef(); if (dbRef != null
				 * && !dbRef.trim().equals("")) { // get domain from dbref
				 * Domain dm = getDomain(dbRef, domainModel); String[] valArr =
				 * getValArray(dm, domainEntries, null); return valArr; } else {
				 * return vals; } }
				 */
			}
		}
		return valList;

	}
	
	public static final Map<String, DomainEntry> getDomainEntriesMap(List<DomainInstance> domainInstances, String projectName) {
		Map<String, DomainEntry> domainEntries = new HashMap<String, DomainEntry>();
		//Dont care entry
		Single dontCare = DomainFactory.eINSTANCE.createSingle();
		dontCare.setValue("*");
		domainEntries.put("*",dontCare);
		if (domainInstances != null && domainInstances.size() > 0) {
			for (DomainInstance domainInstance : domainInstances) {
				String domainPath = domainInstance.getResourcePath();
				Domain domain = CommonIndexUtils.getDomain(projectName, domainPath);
				Domain superDomain = domain;
				
				while (superDomain != null) {
					List<DomainEntry> entries = superDomain.getEntries();
					for (DomainEntry entry : entries) {
						if (entry instanceof RangeImpl) {
							String strRange = getStringValueFromRangeInfo((Range)entry, domain);
							domainEntries.put(strRange, entry);
						} else {
							domainEntries.put(entry.getValue().toString(), entry);
						}
					}
					String superDomainPath = superDomain.getSuperDomainPath();
					superDomain = CommonIndexUtils.getDomain(projectName, superDomainPath);
				}
			}
		}
		return domainEntries;
	}
	
	
	/**
	 * Get the string representation of the {@link Range} object passed
	 * @param range
	 * @return
	 */
	public static String getStringValueFromRangeInfo(Range range, Domain domain){
		String lower = range.getLower();
		String upper = range.getUpper();
		
		boolean includeLower = range.isLowerInclusive();
		boolean includeUpper = range.isUpperInclusive();
		return getStringValueFromRangeInfo(includeLower, includeUpper, lower, upper);
	}
	
	/**
	 * @param includeLower
	 * @param includeUpper
	 * @param lower
	 * @param upper
	 * @return
	 */
	public static String getStringValueFromRangeInfo(boolean includeLower, boolean includeUpper, String lower, String upper) {
		String lowerExp = "Undefined"
				.equalsIgnoreCase(lower) ? ""
						: (includeLower ? ">= " : "> ")
					+ lower;
		String upperExp = "Undefined"
				.equalsIgnoreCase(upper) ? ""
						: (includeUpper ? "<= " : "< ")
						+ upper;		
		String str = upperExp.length() != 0
		&& lowerExp.length() != 0 ? lowerExp
				+ " && " + upperExp
				: (lowerExp.length() != 0 ? lowerExp
						: (upperExp.length() != 0 ? upperExp
								: ""));
		
		return str;
	}

	/**
	 * @param domain
	 * @param domainEntries
	 * @return
	 */
	public static List<String> getDescArray(Domain domain,
			List<DomainEntry> domainEntries) {
		if (domain == null)
			return new ArrayList<String>(0);
		List<DomainEntry> entries = domainEntries;
		if (entries.size() != 0) {
			List<String> descs = new ArrayList<String>();
			for (int j = 0; j < entries.size(); j++) {
				DomainEntry entry = entries.get(j);
				String value = entry.getDescription();
				descs.add(value == null ? "" : value);
				if (domainEntries != null)
					domainEntries.add(entry);
			}
			return descs;
		}
		return new ArrayList<String>(0);
	}

	public static List<DomainInstance> getDomains(String propertyPath,
			String projectName) {
		if (null == propertyPath) {
			return null;
		}
		// Tokenize property path
		int lastIndex = propertyPath.lastIndexOf('/');
		if (lastIndex == -1) {
			// This may be a primitive
			return null;
		}
		String entityPath = propertyPath.substring(0, lastIndex);
		String propertyName = propertyPath.substring(lastIndex + 1,
				propertyPath.length());

		// Check if the entity exists
		Entity entity = IndexUtils.getEntity(projectName, entityPath);
		// Get property def for this
		if (entity instanceof com.tibco.cep.designtime.core.model.element.Concept) {
			com.tibco.cep.designtime.core.model.element.Concept concept = (com.tibco.cep.designtime.core.model.element.Concept) entity;
			List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> allProps = concept
					.getAllProperties();
			for (com.tibco.cep.designtime.core.model.element.PropertyDefinition propertyDefinition : allProps) {
				if (propertyDefinition.getName().equals(propertyName)) {
					// Found
					return propertyDefinition.getDomainInstances();
				}
			}
		}
		if (entity instanceof com.tibco.cep.designtime.core.model.event.Event) {
			com.tibco.cep.designtime.core.model.event.Event event = (com.tibco.cep.designtime.core.model.event.Event) entity;
			List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> allProps = event
					.getAllUserProperties();
			for (com.tibco.cep.designtime.core.model.element.PropertyDefinition propertyDefinition : allProps) {
				if (propertyDefinition.getName().equals(propertyName)) {
					// Found
					return propertyDefinition.getDomainInstances();
				}
			}
		}
		return null;
	}


	/**
	 * @param refCon
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Vector<String> getRefId(String refCon, String filename)
			throws Exception {
		Vector<String> refIds = new Vector<String>();
		File file = new File(filename);
		if (file.exists() && file.length() > 0) {

			FileInputStream fis = new FileInputStream(file);
			XiNode rootNode = XiParserFactory.newInstance().parse(
					new InputSource(fis));
			XiNode mainNode = rootNode.getFirstChild();
			Iterator<XiNode> subNodeIterator = mainNode.getChildren();
			int ct = 0;
			while (subNodeIterator.hasNext()) {
				subNodeIterator.next();
				refIds.add(new Integer(ct).toString());
				ct++;
			}
			fis.close();
		}
		return refIds;
	}

//	public static String convertEntityToTestDataTaskInputString(
//			EntityType entityType, String projectName, TableModel model) {
//		String namespace = entityType.getNamespace();
//		String entityName = namespace.substring(ENTITY_NS.length());
//		Entity entity  = CommonIndexUtils.getEntity(projectName, entityName);
//		
//	
//		XiFactory factory = XiSupport.getXiFactory();
//		XiNode root = factory.createDocument();
//		//XiNode testdataRoot = factory.createElement(ExpandedName
//			//	.makeName("testdata"));
//	//	root.appendChild(testdataRoot);
//		FileOutputStream fos = null;
//		BufferedWriter bufWriter = null;
//		try {
//		
//			Vector<Vector> vectors = ((DefaultTableModel) model).getDataVector();
//			String[] rows = new String[vectors.size()];
//			for (int i = 0; i < vectors.size(); i++) {
//				int rowlength = vectors.elementAt(i).size();
//				EList<PropertyDefinition> list = null;
//				int col = 1;
//				XiNode entityElement = factory.createElement(ExpandedName
//						.makeName(ENTITY_NS + entity.getFullPath(), entity
//								.getName()));
//
//		
//
//				if (entity instanceof Concept) {
//					Concept concept = (Concept) entity;
//					list = concept.getAllProperties();
//					Object extIdOb = model.getValueAt(i, col);
//					String extId = extIdOb == null ? "" : extIdOb.toString();
//				/*	if (!extId.trim().equals("")) {
//						XiNode extIdAttribElement = factory.createAttribute(
//								ExpandedName.makeName("extId"), extId);// Adding
//																		// extId
//																		// attribute
//						entityElement.appendChild(extIdAttribElement);
//					}*/
//					col++;
//					for (int j = col; j < rowlength; j++) {
//						rows[i] = (String) (vectors.elementAt(i).elementAt(j));
//						String columnName = model.getColumnName(j);
//						PropertyDefinition propertyDefinition = getPropertyDefinition(
//								columnName, list);
//						saveXMLEntityTestData(factory, entity,
//								propertyDefinition, bufWriter, entityElement,
//								rows[i], columnName);
//					}
//				}
//				if (entity instanceof Event) {
//					Event event = (Event) entity;
//					list = event.getAllUserProperties();
//
//					Object extIdOb = model.getValueAt(i, 2);
//					String extId = extIdOb == null ? "" : extIdOb.toString();
//
//					/*if (!extId.trim().equals("")) {
//						XiNode extIdAttribElement = factory.createAttribute(
//								ExpandedName.makeName("extId"), extId);// Adding
//																		// extId
//																		// attribute
//						entityElement.appendChild(extIdAttribElement);
//					}*/
//
//					Object payloadObj = model.getValueAt(i, 1);
//					String val = payloadObj == null ? "" : payloadObj.toString();
//
//					if (event.getPayloadString() != null && !val.trim().equals("")) {
//						XiNode payloadElement = factory.createElement(ExpandedName.makeName("payload"));
////						InputStream inputstream = new ByteArrayInputStream(val.getBytes("UTF-8"));
//						XiNode payloadNode = XiParserFactory.newInstance().parse(new InputSource(new StringReader(val)));
//						payloadElement.appendChild(payloadNode.getFirstChild());
//						entityElement.appendChild(payloadElement);
//					}
//
//					for (int j = 3; j < rowlength; j++) {
//						rows[i] = (String) (vectors.elementAt(i).elementAt(j));
//						String columnName = model.getColumnName(j);
//						PropertyDefinition propertyDefinition = getPropertyDefinition(
//								columnName, list);
//						saveXMLEntityTestData(factory, entity,
//								propertyDefinition, bufWriter, entityElement,
//								rows[i], columnName);
//					}
//				}
//				
//				root.appendChild(entityElement);
//				return XiSerializer.serialize(root);
//			}
//			return null;
//		}catch(Exception e) {
//			return null;
//		}
//	}
	
	public static void setColumnImages(Table table, Map<String, String> tableColumnsWithType, ArrayList<String> tableColumnNames){
		Image image = null;
		for (int i = 0; i < tableColumnsWithType.size(); i++) {
			TableColumn tc = new TableColumn(table, SWT.NONE);
			
			if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("string")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_STRING);
				tc.setText(tableColumnNames.get(i));
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("string-Multiple")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_STRING);
				tc.setText(tableColumnNames.get(i)+"[M]");
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("string-Domain")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_STRING);
				tc.setText(tableColumnNames.get(i)+"[D]");
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("long")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_LONG);
				tc.setText(tableColumnNames.get(i));
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("long-Multiple")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_LONG);
				tc.setText(tableColumnNames.get(i)+"[M]");
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("long-Domain")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_LONG);
				tc.setText(tableColumnNames.get(i)+"[D]");
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("int")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_INTEGER);
				tc.setText(tableColumnNames.get(i));
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("int-Multiple")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_INTEGER);
				tc.setText(tableColumnNames.get(i)+"[M]");
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("int-Domain")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_INTEGER);
				tc.setText(tableColumnNames.get(i)+"[D]");
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("boolean")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_BOOLEAN);
				tc.setText(tableColumnNames.get(i));
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("boolean-Multiple")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_BOOLEAN);
				tc.setText(tableColumnNames.get(i)+"[M]");
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("boolean-Domain")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_BOOLEAN);
				tc.setText(tableColumnNames.get(i)+"[D]");
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("double")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_DOUBLE);
				tc.setText(tableColumnNames.get(i));
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("double-Multiple")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_DOUBLE);
				tc.setText(tableColumnNames.get(i)+"[M]");
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("double-Domain")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_DOUBLE);
				tc.setText(tableColumnNames.get(i)+"[D]");
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("datetime")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_DATE);
				tc.setText(tableColumnNames.get(i));
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("datetime-Multiple")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_DATE);
				tc.setText(tableColumnNames.get(i)+"[M]");
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("datetime-Domain")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_DATE);
				tc.setText(tableColumnNames.get(i)+"[D]");
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("CONTAINEDCONCEPT")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_CONCEPT);
				tc.setText(tableColumnNames.get(i));
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("CONTAINEDCONCEPT-Multiple")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_CONCEPT);
				tc.setText(tableColumnNames.get(i)+"[M]");
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("Payload")) {
		/*		image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_PAYLOAD);*/
				tc.setText(tableColumnNames.get(i));
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("ExtId")) {
				/*image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_EXTID);*/
				tc.setText(tableColumnNames.get(i));
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("use")) {
			/*	image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_USE);*/
				tc.setText(tableColumnNames.get(i));
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("CONCEPTREFERENCE")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_CONCEPTREFERENCE);
				tc.setText(tableColumnNames.get(i));
			} else if (tableColumnsWithType.get(tableColumnNames.get(i))
					.equalsIgnoreCase("CONCEPTREFERENCE-Multiple")) {
				image = StudioUIPlugin.getDefault().getImage(
						PropertyTableConstants.ICON_CONCEPTREFERENCE);
				tc.setText(tableColumnNames.get(i)+"[M]");
			}
			if (image != null)
				tc.setImage(image);
		}
		for (int i = 0; i < table.getColumnCount(); i++) { // /////
			table.getColumn(i).pack();
		}

	}
	
	public static String getResourcePath(Entity entity, String fullPath) {
		String resourcePath="";
		if(entity instanceof Scorecard){
			resourcePath=fullPath+ENTITY_SCORECARD_EXT;
		}
		else if(entity instanceof Event){
			resourcePath=fullPath+ENTITY_EVENT_EXT;
		}
		else if(entity instanceof Concept){
			resourcePath=fullPath+ENTITY_CONCEPT_EXT;
		}
		return resourcePath;
	}
}