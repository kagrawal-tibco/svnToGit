package com.tibco.cep.studio.ui.validation.resolution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsd.XSDTerm;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.mapper.BEMapperCoreInputOutputAdapter;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.xml.mappermodel.emfapi.EMapperModelInputOutputProxy;
import com.tibco.xml.mappermodel.exports.MapperFactory;
import com.tibco.xml.mappermodel.primary.IMapperModelForUI;
import com.tibco.xml.mappermodel.primary.TargetNode;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.tns.TnsComponent;
import com.tibco.xml.tns.parse.TnsDocument;
import com.tibco.xml.tns.parse.TnsFragment;
import com.tibco.xml.xmodel.mmodel.IMapperModel;
import com.tibco.xml.xmodel.xpath.Coercion;

public class XslMapperModelWrapper extends BEMapperCoreInputOutputAdapter {

	private IMapperModel mMapperModel;
	private EMapperModelInputOutputProxy mMapperInputOutputProxy;
	private boolean isXPath;
	private boolean hasCoercions;
	private boolean manualChange;
	private boolean updateVersions = false;
	
	public XslMapperModelWrapper(MapperInvocationContext context,
			List<EObject> sourceElements, Entity targetElement, boolean xpath, boolean updateVersions) {
		super(context, sourceElements, targetElement);
		isXPath = xpath;
		this.updateVersions = updateVersions;
		if (isXPath) {
			mMapperModel = MapperFactory.createXPathBuilderModel();
		} else {
			mMapperModel = MapperFactory.createMapperModel();
		}
		manualChange = false;
		mMapperInputOutputProxy = new EMapperModelInputOutputProxy(this);
		stripSchemaLocationForProjlib(); // strip schema location prior to setting model
		updateVersions();
		mMapperModel.setModelInputInternal(mMapperInputOutputProxy);
		init();
	}

	private void updateVersions() {
		if (updateVersions  && !isXPath) {
			String xslt = context.getXslt();
			String regex = "\" version=\\\"1.0\\\"";
			int versionIdx = xslt.indexOf(regex);
			if (versionIdx != -1) {
				// set the version of the xslt to 2.0
				char[] charArray = xslt.toCharArray();
				if (charArray[versionIdx+regex.length()-5] == '1') {
					charArray[versionIdx+regex.length()-5] = '2';
					xslt = String.valueOf(charArray);
					context.setXslt(xslt);
					manualChange = true;
				}
			}
		}
	}

	private void init() {
		hasCoercions = false;
        ArrayList<String> parms = new ArrayList<String>();
        ArrayList<String> coercions = new ArrayList<String>();
        if (!this.isXPath) {
        	XSTemplateSerializer.deSerialize(context.getXslt(), parms, coercions);
        	if (coercions.size() > 0) {
        		// need to re-serialize with the new coercion format before validating and applying fixes
        		System.out.println("Coercions: "+coercions.size());
        		this.hasCoercions  = true;
        		processCoercions(coercions);
        	} else {
        		stripSchemaLocationForProjlib();
        	}
        }

	}

	private void stripSchemaLocationForProjlib() {
		String xslt = context.getXslt();
		int idx = xslt.indexOf("schema-location=");
		int idx2 = xslt.indexOf(".projlib");
		if (idx2 > idx && idx > 0) {
			// need to strip this schema-location, since BW6 can't handle a linked file inside an archive
			String regex = "schema-location=\\\\\"[^\\\"]+\\\"";
			String newxslt = xslt.replaceAll(regex, "");
			context.setXslt(newxslt);
			manualChange = true;
		}
	}

	private void processCoercions(ArrayList<String> coercions) {
		ArrayList<Coercion> coercionList = new ArrayList<Coercion>();
		for (String c : coercions) {
			String[] split = c.split(",");
			String xpath = split[2];
			// check whether the variable used in the coercion is defined.  This eliminates orphaned coercion issues
			if (!variableDefined(xpath)) {
				continue;
			}
			String typeOrElementName = split[1];
			int nsPrefix = typeOrElementName.indexOf(":");
			Coercion coercion = null;
			if (nsPrefix > 0) {
				String[] segments = typeOrElementName.split(":");
				String prefix = segments[0];
				String ns = this.mMapperModel.getNamespaceResolver().getNamespaceURIForPrefix(prefix);
				if (ns != null) {
					EMFTnsCache cache = StudioCorePlugin.getCache(this.context.getProjectName());
					Iterator locations = cache.getLocations();
					while (locations.hasNext()) {
						String loc = (String) locations.next();
						TnsDocument document = cache.getDocument(loc);
						if (document != null && document.getSingleFragment() != null) {
							if (ns.equals(document.getSingleFragment().getNamespaceURI())) {
						        Iterator<?> frags = document.getFragments();
						        while (frags.hasNext()) {
						            TnsFragment frag = (TnsFragment) frags.next();
						            int coercionType = Coercion.COERCION_ELEMENT;
						            TnsComponent component = frag.getComponent(segments[1], SmComponent.ELEMENT_TYPE);
						            if (component == null) {
						            	component = frag.getComponent(segments[1], SmComponent.TYPE_TYPE);
						            	coercionType = Coercion.COERCION_TYPE;
						            }
						            if (component != null) {
						            	coercion = new Coercion(xpath, typeOrElementName, coercionType);
						            	coercion.setNamespace(ns);
										String root = cache.getRootURI();
										String relLoc = null;
										Path path = new Path(loc).setDevice(null);
										int segCount = 0;
										if (loc.startsWith("jar:") && loc.contains(".projlib!")) {
											String[] segs = path.segments();
											String projLibName = null;
											for (String seg : segs) {
												if (seg.contains("projlib!")) {
													projLibName = seg.substring(0, seg.length()-1);
													segCount++;
													break;
												}
												segCount++;
											}
											Path p = new Path(root);
											relLoc = path.removeFirstSegments(segCount).toString();
											relLoc = p.removeFirstSegments(p.segmentCount()-1).append(projLibName).append(relLoc).toString();
											
										} else {
											segCount = new Path(root).segmentCount();
											relLoc = path.removeFirstSegments(segCount).toString();
										}
										if (relLoc.charAt(0) != '/') {
											relLoc = '/' + relLoc;
										}
										((IMapperModelForUI)this.mMapperModel).addToQNameResolver(ns, segments[1], coercionType, relLoc);
										// need to clear the imported namespaces or the serialization will write the schema-location, which is absolute for project library locations
										((IMapperModelForUI)this.mMapperModel).getQNameResolver().clearImportedNamespaces();
										break;
						            } else {
						            	System.out.println("Couldn't find component for coercion, skipped migration");
						            }
						        }
							}
						}
					}
				}
			}
			if (coercion != null) {
				coercionList.add(coercion);
			}
		}
		
		((IMapperModelForUI)this.mMapperModel).processCoercionList(coercionList);
	}

	private boolean variableDefined(String xpath) {
		if (xpath == null || xpath.length() == 0) {
			return false;
		}
		if (xpath.charAt(0) == '$') {
			int idx = xpath.indexOf('/');
			int endIdx = idx == -1 ? xpath.length() - 1 : idx; 
			String var = xpath.substring(1, endIdx);
			List<VariableDefinition> definitions = context.getDefinitions();
			for (VariableDefinition variableDefinition : definitions) {
				if (var.equals(variableDefinition.getName())) {
					return true;
				}
			}
			StudioUIPlugin.log(new Status(IStatus.WARNING, StudioUIPlugin.PLUGIN_ID, "Coercion variable '"+var+"' is not defined in the scope of the mapping.  The coercion '"+xpath+"' will not be migrated"));
		}

		return false;
	}

	/**
	 * returns null if nothing fixed.
	 */
	public String fixTypeCastErrors(boolean indent) {
		return mMapperModel.fixTypeCastErrors(indent);
	}

	/**
	 * returns null if nothing fixed.
	 */
	public String fixErrors(boolean indent, boolean onlyCopyModeOutOfSync, boolean onlyTypeCastErrors) {
		if (mMapperModel.autoFix(onlyCopyModeOutOfSync, onlyTypeCastErrors)) {
			if (this.isXPath) {
				TargetNode root = ((IMapperModelForUI)mMapperModel).getTargetRoot();
				if (root == null || root.getChildCount() == 0) {
					return null;
				}
				TargetNode targetNode = root.getChild(0);
				return targetNode.getBinding().getFormula();
			}
			return mMapperModel.getCurrentXslt(indent);
		} else if (manualChange || (hasCoercions && !this.isXPath)) {
			// need to call getCurrentXslt with 'true' to force the mapper model to recalculate the xslt string with the coercions
			return mMapperModel.getCurrentXslt(true);
		}
		return null;
	}

	public EMapperModelInputOutputProxy getMapperModelInputOutputProxy() {
		return this.mMapperInputOutputProxy;
	}

	public void setIsXPath(boolean xpath) {
		this.isXPath = xpath;
	}

	@Override
	public XSDTerm getTargetXSDTerm() {
		if (this.isXPath) {
			return null;
		}
		return super.getTargetXSDTerm();
	}
	
	@Override
	public String getXPathResultNativeTypeName() {
		if (!this.isXPath) {
			return null;
		}
		return super.getXPathResultNativeTypeName();
	}
}

