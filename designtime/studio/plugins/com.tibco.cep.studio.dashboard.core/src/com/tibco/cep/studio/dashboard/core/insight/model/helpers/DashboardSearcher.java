package com.tibco.cep.studio.dashboard.core.insight.model.helpers;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.dashboard.core.DashboardCorePlugIn;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.SearchPath.PathElement;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

/**
 * The <code>DashboardSearcher</code> searches for references to a
 * <code>LocalElement</code>. The <code>DashboardSearcher</code> parses and
 * maintains an index of search-ables and search paths. Any element to be
 * searched in checked against the index for search paths. Once search paths are
 * found, the appropriate local elements in the project are traversed down the
 * search path to see if the target element is in use.
 * 
 * @author anpatil
 * 
 */
public class DashboardSearcher {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
	private static DashboardSearcher instance;

	public static final synchronized DashboardSearcher getInstance() {
		if (instance == null) {
			instance = new DashboardSearcher();
		}
		return instance;
	}

	// the config reader which gives us the dashboard element definitions
	private IConfigReader configReader;
	// the list of dashboard types which are searchable
	private Set<String> searchableDashboardTypes;
	// the index of searchables-searchpaths
	private Map<String, List<SearchPath>> searchPathsIndex;

	private DashboardSearcher() {
		searchPathsIndex = new HashMap<String, List<SearchPath>>();
		configReader = ViewsConfigReader.getInstance();
		searchableDashboardTypes = getSearchableDashboardTypes();
		// since we can only create/view the top level elements, we will
		// restrict the reference search to them
		List<String> topLevelElementNames = new LinkedList<String>(BEViewsElementNames.getTopLevelElementNames());
		//PATCH adding text and chart component to the list 
//		topLevelElementNames.add(BEViewsElementNames.TEXT_COMPONENT);
//		topLevelElementNames.add(BEViewsElementNames.CHART_COMPONENT);
		//topLevelElementNames.remove(BEViewsElementNames.TEXT_CHART_COMPONENT);
		for (String topLevelElement : topLevelElementNames) {
			// create a new search path for each top level element
			SearchPath searchPath = new SearchPath();
			// add it the top level element as a path element to the path
			searchPath.addPathElement(topLevelElement, topLevelElement);
			// locate all searchables in the element
			locateSearchables(searchPath);
		}
	}

	/**
	 * Returns the set of search-able dashboard element types. If a element type
	 * is a sub class, then root type of the element type is added to the set,
	 * else the element type itself is added to the set. We also add
	 * <code>BEViewsElementNames.METRIC</code> and
	 * <code>PropertyDefinition</code> to the set.
	 * 
	 * @return A unique, non-repeating set of search-able dashboard element
	 *         types
	 * @see BEViewsElementNames#getTopLevelElementNames()
	 * @see Metric
	 * @see PropertyDefinition
	 */
	private Set<String> getSearchableDashboardTypes() {
		Set<String> set = new HashSet<String>();
		for (String topLevelElement : BEViewsElementNames.getTopLevelElementNames()) {
			String baseType = getBaseType(topLevelElement);
			if (baseType != null) {
				set.add(baseType);
			} else {
				set.add(topLevelElement);
			}
		}
		// add metric & property definition
		set.add(BEViewsElementNames.METRIC);
		set.add(PropertyDefinition.class.getSimpleName());
		return set;
	}

	/**
	 * Returns the base type of a type. Returns <code>null</code> if there is no
	 * base type
	 * 
	 * @param type
	 *            The type whose base type is needed
	 * @return the base type if found else <code>null</code>
	 */
	private String getBaseType(String type) {
		String superType = configReader.getSuperParticleName(type);
		if (superType == null) {
			return null;
		}
		while (superType != null) {
			type = superType;
			superType = configReader.getSuperParticleName(type);
		}
		return type;
	}

	/**
	 * Locates and indexes search-ables found in a search path. Search-ables are
	 * all particles whose type is search-able and all properties which use
	 * search-able(s)
	 * 
	 * @param searchPath
	 *            The path to search in and eventually index
	 */
	private void locateSearchables(SearchPath searchPath) {
		try {
			// get the type of the last element in the search path (as in the
			// latest)
			// note that the search path is built backwards (towards the top
			// level search-able)
			String type = searchPath.getLastPathElement().getType();
			// get all the particles for the type
			for (LocalParticle childParticle : configReader.getParticleList(type)) {
				// are we dealing with a repeating path element?
				// if the last path element's name & type is matching the
				// current child particle name & type
				// then we are dealing with a repetitive path element
				if (searchPath.getLastPathElement().getType().equals(childParticle.getTypeName()) == true && searchPath.getLastPathElement().getName().equals(childParticle.getName()) == true) {
					searchPath.getLastPathElement().setRepetitive();
				}
				// we clone the search path for each child , since we want
				// unique search path to be indexed
				// using same reference will cause the search paths to get
				// messed up
				SearchPath perChildPath = (SearchPath) searchPath.clone();
				if (isSearchable(childParticle) == true) {
					// this child particle is searchable, update the search path
					// and index it
					perChildPath.setReferencingParticle(childParticle);
					indexSearchPath(perChildPath);
				} else if (perChildPath.getLastPathElement().isRepetitive() == false) {
					// this child particle is not searchable and is not
					// repetitive,
					// search further down the child particle for search-able(s)
					perChildPath.addPathElement(childParticle.getName(), childParticle.getTypeName());
					locateSearchables(perChildPath);
				}
			}
			// index properties which use search-ables as type [source fields]
			// or as referencables [tooltips/displayformats]
			for (SynProperty synProperty : configReader.getPropertyList(type)) {
				if ((synProperty.getTypeDefinition() instanceof SynStringType) == true) {
					LocalPropertyConfig propertyHelper = ViewsConfigReader.getInstance().getPropertyHelper(type, synProperty.getName());
					if (propertyHelper.getReferences().isEmpty() == false) {
						for (String reference : propertyHelper.getReferences()) {
							SearchPath perPropertyPath = (SearchPath) searchPath.clone();
							perPropertyPath.setReferencingProperty(synProperty, reference);
							indexSearchPath(perPropertyPath);
						}
					} else if (propertyHelper.isElementRef == true) {
						SearchPath perPropertyPath = (SearchPath) searchPath.clone();
						perPropertyPath.setReferencingProperty(synProperty, propertyHelper.getType());
						indexSearchPath(perPropertyPath);
					}
				}
			}
		} catch (Exception e) {
			DashboardCorePlugIn.getDefault().getLog().log(new Status(IStatus.WARNING, DashboardCorePlugIn.PLUGIN_ID, "could not traverse " + searchPath, e));
		}
	}

	/**
	 * Determines if the <code>LocalParticle</code> is searchable. A
	 * <code>LocalParticle</code> is searchable if it's type is either in the
	 * <code>searchableDashboardTypes</code> or base type of the type is in the
	 * <code>searchableDashboardTypes</code> or the type is external reference
	 * 
	 * @param localParticle
	 *            The <code>LocalParticle</code> which is to be checked
	 * @return <code>true</code> is the <code>LocalParticle</code> is searchable
	 *         else <code>false</code>
	 */
	private boolean isSearchable(LocalParticle localParticle) {
		String typeName = localParticle.getTypeName();
		if (typeName.startsWith("MD") == true) {
			return false;
		}
		if (localParticle.isMDConfigType() == false) {
			return true;
		}
		if (searchableDashboardTypes.contains(typeName) == true) {
			return true;
		}
		String baseType = getBaseType(typeName);
		if (baseType != null && searchableDashboardTypes.contains(baseType) == true) {
			return true;
		}
		return false;
	}

	/**
	 * Indexes a search path against the target type of the search path
	 * 
	 * @param searchPath
	 *            The search path to be indexed
	 * @see SearchPath#getTargetType()
	 */
	private void indexSearchPath(SearchPath searchPath) {
		List<SearchPath> possiblePaths = searchPathsIndex.get(searchPath.getTargetType());
		if (possiblePaths == null) {
			possiblePaths = new LinkedList<SearchPath>();
			searchPathsIndex.put(searchPath.getTargetType(), possiblePaths);
		}
		possiblePaths.add(searchPath);
	}

	/**
	 * Searches for references to local element
	 * 
	 * @param localECoreFactory
	 *            The <code>LocalECoreFactory</code> to use for getting top
	 *            level elements
	 * @param localElement
	 *            The <code>LocalElement</code> to search for
	 * @return A list of <code>SearchPathResult</code>
	 * @throws Exception
	 *             if search fails
	 */
	public List<SearchPathResult> search(LocalECoreFactory localECoreFactory, LocalElement localElement) throws Exception {
		return internalSearch(localECoreFactory, localElement, "");
	}

	private List<SearchPathResult> internalSearch(LocalECoreFactory localECoreFactory, LocalElement localElement, String debugIndent) throws Exception {
		List<SearchPathResult> results = new LinkedList<SearchPathResult>();
		if (localElement != null) {
	//		String elementType = localElement.getElementType();
			// we will use the persisted objects type (which is akin to top level
			// element names
			// also lets us work with external references better
			String elementType = localElement.getEObject().eClass().getName();
			// get basic search paths
			List<SearchPath> searchPaths = searchPathsIndex.get(elementType);
			if (searchPaths == null) {
				searchPaths = new LinkedList<SearchPath>();
			}
			// get search types for base type if any
			String baseType = getBaseType(elementType);
			if (baseType != null) {
				searchPaths.addAll(searchPathsIndex.get(baseType));
			}
			// handle the uniqueness for text chart component
	//		if (localElement instanceof LocalTextChartComponent) {
	//			searchPaths.addAll(searchPathsIndex.get(((LocalTextChartComponent) localElement).getInsightType()));
	//		}
			if (searchPaths != null) {
				HashMap<String, List<LocalElement>> startingPointsCache = new HashMap<String, List<LocalElement>>();
				// perform a search for each search path using the SearchWorker
				for (SearchPath searchPath : searchPaths) {
					List<LocalElement> startingPoints = startingPointsCache.get(searchPath.getFirstPathElement().getType());
					if (startingPoints == null) {
						startingPoints = getStartingPoints(localECoreFactory, localElement, searchPath, debugIndent);
						startingPointsCache.put(searchPath.getFirstPathElement().getType(), startingPoints);
					}
					SearchWorker worker = new SearchWorker(localElement, searchPath, startingPoints);
					worker.indent = debugIndent;
					worker.run();
					// merge the results
					List<SearchPathResult> workerResults = worker.getResults();
					if (workerResults.isEmpty() == false) {
						results.addAll(workerResults);
					}
				}
			}
		}
		return results;
	}

	private List<LocalElement> getStartingPoints(LocalECoreFactory localECoreFactory, LocalElement targetElement, SearchPath searchPath, String indent) throws Exception {
		List<LocalElement> startingPoints = Collections.emptyList();
		if (targetElement instanceof LocalAttribute) {
			// if we dealing with a attribute target element, we should find
			// all the search-ables which reference it's parent and then perform
			// a search for the attribute. This is done to handle the case of
			// repeating field names across metrics
			startingPoints = deriveStartingPoints(localECoreFactory, searchPath, targetElement.getParent(), new LinkedList<LocalElement>(), indent);
		} else {
			// we will get the top level elements from root element using the
			// first path element in search path
			String particleName = searchPath.getFirstPathElement().getName();
			//PATCH to handle transition from TextChartComponent to TextComponent/ChartComponent 
//			if (BEViewsElementNames.getChartComponentTypes().contains(particleName) == true) {
//				particleName = BEViewsElementNames.TEXT_CHART_COMPONENT;
//			}
			startingPoints = new LinkedList<LocalElement>(localECoreFactory.getChildren(particleName));
		}
		return startingPoints;
	}

	/**
	 * Derive the starting point list for a local element. A search for local
	 * element is done in the system till elements matching the reference search
	 * path are found or the search types repeat (to prevent recursion). E.g. to
	 * search for metric field, we need to find all charts using the metric. The
	 * path is metric -> Data Source -> Component.
	 * 
	 * @param refSearchPath
	 *            The reference search path to check against
	 * @param localElement
	 *            The element to search for
	 * @param alreadySearchedElements
	 *            A recursion preventing list
	 * @return A list of LocalElement which can be used as starting points
	 * @throws Exception
	 *             if search fails
	 */
	private List<LocalElement> deriveStartingPoints(LocalECoreFactory root, SearchPath refSearchPath, LocalElement localElement, List<LocalElement> alreadySearchedElements, String indent) throws Exception {
		// System.out.println(indent+"Hunting for starting points for "+localElement.getName()+" with "+referenceSearchPath+" as reference");
		if (alreadySearchedElements.contains(localElement) == true) {
			return Collections.emptyList();
		}
		List<SearchPathResult> parentSearchResult = internalSearch(root, localElement, indent + "\t");
		alreadySearchedElements.add(localElement);
		LinkedList<LocalElement> startingPoints = new LinkedList<LocalElement>();
		for (SearchPathResult searchPathResult : parentSearchResult) {
			LocalElement startingPoint = searchPathResult.getActualResult();
			String type = startingPoint.getElementType();
			if (refSearchPath.getFirstPathElement().getType().equals(type) == true) {
				startingPoints.add(startingPoint);
			} else {
				startingPoints.addAll(deriveStartingPoints(root, refSearchPath, startingPoint, alreadySearchedElements, indent + "\t"));
			}
		}
		return startingPoints;
	}

	/**
	 * Does the actual search
	 * 
	 * @author anpatil
	 * 
	 */
	class SearchWorker {

		String indent = "";

		// the root element which is used for getting top level elements
		// LocalECoreFactory rootElement;

		// the starting elements
		List<LocalElement> startingElements;

		// the element being searched
		LocalElement targetElement;

		// the search path to take to reach the target element
		SearchPath searchPath;

		// the starting path element
		PathElement startingPathElement;

		// who is last element in the search path?
		PathElement referenceParticleName;

		// is the target element an attribute?
		private boolean isAttribute;

		// search results
		LinkedList<SearchPathResult> results;

		SearchWorker(LocalElement targetElement, SearchPath searchPath, List<LocalElement> startingElements) {
			super();
			this.targetElement = targetElement;
			this.searchPath = searchPath;
			this.startingElements = startingElements;
			if (this.startingElements == null) {
				this.startingElements = Collections.emptyList();
			}
			this.startingPathElement = searchPath.getNextPathElement(searchPath.getFirstPathElement());
			this.referenceParticleName = searchPath.getLastPathElement();
			isAttribute = searchPath.isAttribute();
			results = new LinkedList<SearchPathResult>();
		}

		void run() throws Exception {
			// System.out.println(indent+"Going to search for "+targetElement.getName()+" using "+searchPath);
			// get the starting points and starting path element
			for (LocalElement startingElement : startingElements) {
				// System.out.println(indent+"\tUsing "+startingPoint.getName()+" as starting point to find "+targetElement+" using "+searchPath);
				boolean foundmatch = false;
				// get the real reference holders
				List<LocalElement> referenceHolders = getReferenceHolders(startingElement, startingPathElement);
				for (LocalElement referenceHolder : referenceHolders) {
					if (isAttribute == true) {
						// if target element is attribute, check if property is
						// of type 'String'
						SynProperty property = (SynProperty) referenceHolder.getProperty(searchPath.getTargetLocation());
						if (property.getTypeDefinition() instanceof SynStringType) {
							// we simply search for target element's name in the
							// value
							if (property.getValue().contains(targetElement.getName()) == true) {
								// we have a match
								results.add(new SearchPathResult(searchPath, startingElement));
								foundmatch = true;
							}
						}
					} else {
						// load the target location particle and search its
						// element for target element
						LocalParticle particle = referenceHolder.getParticle(searchPath.getTargetLocation());
						if (particle.isInitialized() == false) {
							referenceHolder.loadChildren(searchPath.getTargetLocation());
							particle.setInitialized(true);
						}
						if (particle.getElementByID(targetElement.getID()) != null) {
							// we have a match
							results.add(new SearchPathResult(searchPath, startingElement));
							foundmatch = true;
							break;
						}
					}
				}
				if (foundmatch == true) {
					// System.out.println(indent+"\tFound Match!!!");
				} else {
					// System.out.println(indent+"\tNo Match!!!");
				}
			}
		}

		/**
		 * Traverses a local element till the reference particle element has
		 * been reached. Handles repetitive traversal.
		 * 
		 * @param localElement
		 *            The local element to traverse
		 * @param pathElement
		 *            The path element to look for in the local element
		 * @return A list of local element(s) matching the path element
		 * @throws Exception
		 *             if traversal fails
		 */
		// TODO isRepetitive is tied to last path element and not generic enough
		// for any repetitive path element in the search path
		private List<LocalElement> getReferenceHolders(LocalElement localElement, PathElement pathElement) throws Exception {
			List<LocalElement> referenceHolders = new LinkedList<LocalElement>();
			if (pathElement == null) {
				// if we have no path element, then return the local element
				// itself
				referenceHolders.add(localElement);
			} else {
				// get all the children in local element using the path element
				List<LocalElement> children = localElement.getChildren(pathElement.getName());
				if (pathElement.equals(referenceParticleName) == true) {
					// path element matches the last element in the search path
					// add all children to reference holder
					referenceHolders.addAll(children);
					if (pathElement.isRepetitive() == true) {
						// path element is repetitive, get reference holders in
						// the child
						// matching the same path element
						for (LocalElement child : children) {
							referenceHolders.addAll(getReferenceHolders(child, pathElement));
						}
					}
				} else {
					// no match, go further down the tree
					for (LocalElement child : children) {
						referenceHolders.addAll(getReferenceHolders(child, searchPath.getNextPathElement(pathElement)));
					}
				}
			}
			return referenceHolders;
		}

		/**
		 * Returns the results
		 * 
		 * @return The list of <code>SearchPathResult</code>
		 */
		List<SearchPathResult> getResults() {
			return results;
		}

	}

	public static void main(String[] args) {
		DashboardSearcher searcher = DashboardSearcher.getInstance();
		for (String type : searcher.searchPathsIndex.keySet()) {
			System.out.println("[" + type + "]");
			List<SearchPath> paths = searcher.searchPathsIndex.get(type);
			for (SearchPath searchPath : paths) {
				System.out.println("\t" + searchPath);
			}
		}
	}
}
