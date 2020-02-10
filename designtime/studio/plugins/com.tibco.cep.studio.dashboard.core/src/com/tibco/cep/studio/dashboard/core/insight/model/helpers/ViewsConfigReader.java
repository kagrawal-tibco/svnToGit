package com.tibco.cep.studio.dashboard.core.insight.model.helpers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfigParticle;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynOptionalProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynRequiredProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynBooleanType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynColorType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynDoubleType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynFloatType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynImageURLType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynIntType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynLongType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynPrimitiveType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynShortType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.util.EnumHelper;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.core.util.XMLUtil;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ViewsConfigReader implements IConfigReader {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }


	private static Logger logger = Logger.getLogger(ViewsConfigReader.class.getName());

	protected static final String ELEMENTREF = "@";

    protected static final String NAMEREF = "#";

    protected static final String SEPARATOR = ".";

    protected static final String SUB_SEPARATOR = ":";

    protected static final String SUBSCRIPT_START = "[";

    protected static final String SUBSCRIPT_END = "]";

    protected static final String TYPE_START = "{";

    protected static final String TYPE_END = "}";

    protected static final String VIEWS_CONFIG_XML = "viewsconfig.xml";

	private static ViewsConfigReader configReader;

    protected Map<String, Map<String, LocalPropertyConfig>> propertyMap = new HashMap<String, Map<String, LocalPropertyConfig>>();

    protected Map<String, Map<String, String>> enumsMap = new HashMap<String, Map<String, String>>();

    protected Map<String, Map<String, LocalParticleConfig>> particleMap = new HashMap<String, Map<String, LocalParticleConfig>>();

    Map<String, String> ParticleInheritanceMap = new HashMap<String, String>();

    public static final ViewsConfigReader getInstance() {
    	if (configReader == null) {
    		synchronized (ViewsConfigReader.class) {
				if (configReader == null) {
					configReader = new ViewsConfigReader();
				}
			}
    	}
    	return configReader;
    }

    private ViewsConfigReader() {
        try {
            initHelper();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not process insight configuration specification file", e);
        }
	}

    private void initHelper() throws Exception {

        try {
            InputStream is = ViewsConfigReader.class.getResourceAsStream(VIEWS_CONFIG_XML);
            Node root = XMLUtil.parse(is);
            Iterator<Node> enumIter = XMLUtil.getAllNodes(root, "enum");
            while (enumIter.hasNext()) {
                Element enumNode = (Element) enumIter.next();
                String enumName = enumNode.getAttribute("name");
                //INFO we will not use TreeMap for enums, but LinkedHashMap (the viewsconfig.xml has enums defined in the right order)
                Map<String, String> enumPair = new LinkedHashMap<String, String>();
                Iterator<Node> mapIter = XMLUtil.getAllNodes(enumNode, "map");
                while (mapIter.hasNext()) {
                    Element mapNode = (Element) mapIter.next();
                    enumPair.put(mapNode.getAttribute("localvalue"), mapNode.getAttribute("mdvalue"));
                }
                enumsMap.put(enumName, enumPair);
            }
            Iterator<Node> particleIter = XMLUtil.getAllNodes(root, "particle");
            while (particleIter.hasNext()) {

                Element particleNode = (Element) particleIter.next();
                String particleName = particleNode.getAttribute("name");
                Map<String, LocalPropertyConfig> particleProps = new TreeMap<String, LocalPropertyConfig>();
                Map<String, LocalParticleConfig> particleChildren = new HashMap<String, LocalParticleConfig>();
                // Get the super particle and its properties and particles.
                // Do this before we do this particle, so that this particle
                // can override the properties of the super particle and not the other way around
                // :-)
                // Ah, the fun of reinventing Java.
                String superParticleName = particleNode.getAttribute("extends");
                if (superParticleName != null && superParticleName.length() > 0) {
                	ParticleInheritanceMap.put(particleName, superParticleName);
                    Map<String, LocalPropertyConfig> superPropertyMaps = propertyMap.get(superParticleName);
                    if (superPropertyMaps == null) {
                        throw new Exception("Super particle " + superParticleName + " of particle " + particleName + "not defined");
                    }
                    particleProps.putAll(superPropertyMaps);

                    Map<String, LocalParticleConfig> superParticleMaps = particleMap.get(superParticleName);
                    if (superParticleMaps != null) {
                        particleChildren.putAll(superParticleMaps);
                    }
                }

                Iterator<Node> propIter = XMLUtil.getAllNodes(particleNode, "property");
                while (propIter.hasNext()) {
                    Element propNode = (Element) propIter.next();
                    String pseudo = propNode.getAttribute("pseudo");
                    String propName = propNode.getAttribute("name");
                    String propPath = propNode.getAttribute("path");
                    String propType = propNode.getAttribute("type");
                    if (StringUtil.isEmpty(propType)) {
                        propType = "String";
                    }
                    String referencers = propNode.getAttribute("references");
                    String propEnum = propNode.getAttribute("enum");
                    String propDefaultValue = propNode.getAttribute("default");
                    String propOptional = propNode.getAttribute("optional");
                    String propArray = propNode.getAttribute("array");
                    String propMin = propNode.getAttribute("min");
                    String propMax = propNode.getAttribute("max");
                    boolean allowNegative = Boolean.valueOf(propNode.getAttribute("allownegative"));

                    boolean optional = (propOptional != null) && propOptional.equalsIgnoreCase("true");
                    boolean array = (propArray != null) && propArray.equalsIgnoreCase("true");
                    boolean isSystem = pseudo.equalsIgnoreCase("true");

                    SynPrimitiveType t;
                    Map<String, String> enumPair = null;

                    if (propEnum != null && propEnum.length() > 0) {
                        enumPair = enumsMap.get(propEnum);
                        if (enumPair == null) {
                            throw new Exception("Unknown enumeration " + propEnum + " referenced by property " + particleName + "." + propName);
                        }
                        t = makeType(propType, enumPair);
                        // If this an enum property and no default
                        // value is specified, use the first enum value
                        // as the default.
                        if (!optional && StringUtil.isEmpty(propDefaultValue)) {
                            String[] localNames = EnumHelper.getLocalNames(enumPair);
                            if (localNames.length > 0)
                                propDefaultValue = localNames[0];
                        }
                    } else {
                        if (!optional && StringUtil.isEmpty(propDefaultValue)) {
                            if (!StringUtil.isEmpty(propMin))
                                propDefaultValue = propMin;
                        }
                        t = makeType(propType,allowNegative);
                    }

                    if (!StringUtil.isEmpty(propMin)) {
                        t.setMinInclusive(propMin);
                    }
                    if (!StringUtil.isEmpty(propMax)) {
                        t.setMaxInclusive(propMax);
                    }

                    SynProperty p;
                    if (optional) {
                        p = new SynOptionalProperty(propName, t, propDefaultValue, isSystem);
                    } else {
                        p = new SynRequiredProperty(propName, t, propDefaultValue, isSystem);
                    }
                    p.setArray(array);
                    particleProps.put(propName, new LocalPropertyConfig(p, propPath, enumPair, propType, referencers));
                }
                propertyMap.put(particleName, particleProps);

                // Now get all the child particles of this particle.

                Iterator<Node> childIter = XMLUtil.getAllNodes(particleNode, "particle");
                while (childIter.hasNext()) {
                    Element childNode = (Element) childIter.next();
                    String childName = childNode.getAttribute("name");
                    String childPath = childNode.getAttribute("path");
                    String childType = childNode.getAttribute("type");
                    int min = 1;
                    int max = 1;
                    String s = childNode.getAttribute("min");
                    if (s != null && s.length() > 0) {
                        try {
                            min = Integer.parseInt(s);
                        } catch (NumberFormatException nfe) {
                        }
                    }
                    s = childNode.getAttribute("max");
                    if (s != null && s.length() > 0) {
                        try {
                            max = Integer.parseInt(s);
                        } catch (NumberFormatException nfe) {
                        }
                    }
                    LocalParticle lp = new LocalConfigParticle(childName, min, max);
                    if (childPath.startsWith("@")) {
                        lp.setReference(true);
                        lp.setPath(childPath.substring(1, childPath.length()));
                    } else {
                        lp.setReference(false);
                        lp.setPath(childPath);
                    }
                    // setTypeName takes out the square brackets.
                    if (childType.startsWith(TYPE_START) && childType.endsWith(TYPE_END)) {
                        lp.setTypeName(childType.substring(1, childType.length() - 1));
                        lp.setMDConfigType(false);
                    } else {
                        lp.setTypeName(childType);
                        lp.setMDConfigType(true);
                    }
                    particleChildren.put(childName, new LocalParticleConfig(lp, childPath, childType));
                }
                particleMap.put(particleName, particleChildren);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static SynPrimitiveType makeType(String name, boolean allowNegative) {
        SynPrimitiveType type;
        if (name.equalsIgnoreCase("Int") || name.equalsIgnoreCase("Integer")) {
            type = new SynIntType(allowNegative);
        } else if (name.equalsIgnoreCase("Long")) {
            type = new SynLongType(allowNegative);
        } else if (name.equalsIgnoreCase("Short")) {
            type = new SynShortType(allowNegative);
        } else if (name.equalsIgnoreCase("Float")) {
            type = new SynFloatType(allowNegative);
        } else if (name.equalsIgnoreCase("Double")) {
            type = new SynDoubleType(allowNegative);
        } else if (name.equalsIgnoreCase("Boolean")) {
            type = new SynBooleanType();
        } else if (name.equalsIgnoreCase("Color")) {
            type = new SynColorType();
        } else if (name.equalsIgnoreCase("ImageURL")) {
            type = new SynImageURLType();
        } else {
            type = new SynStringType();
        }
        return type;
    }

    private static SynPrimitiveType makeType(String name, Map<String, String> enumPair) {
        SynPrimitiveType type;
        name = name.toLowerCase();
        String[] values = EnumHelper.getLocalNames(enumPair);
        if (name.equalsIgnoreCase("int") || name.equalsIgnoreCase("integer")) {
            type = new SynIntType(values);
        } else if (name.equalsIgnoreCase("long")) {
            type = new SynLongType(values);
        } else if (name.equalsIgnoreCase("short")) {
            type = new SynShortType(values);
        } else if (name.equalsIgnoreCase("float")) {
            type = new SynFloatType(values);
        } else if (name.equalsIgnoreCase("double")) {
            type = new SynDoubleType(values);
        } else if (name.equalsIgnoreCase("boolean")) {
            type = new SynBooleanType();
        } else if (name.equalsIgnoreCase("color")) {
            type = new SynColorType();
        } else if (name.equalsIgnoreCase("imageurl")) {
            type = new SynImageURLType();
        } else {
            type = new SynStringType(values);
        }
        return type;
    }

    public LocalPropertyConfig getPropertyHelper(String type, String property) {
		Map<String, LocalPropertyConfig> m = propertyMap.get(type);
		if (m == null){
			return null;
		}
		return m.get(property);
    }

    public LocalParticleConfig getParticleHelper(String typeName, String particleName) {
        Map<String, LocalParticleConfig> m = particleMap.get(typeName);
        if (m == null){
        	return null;
        }
        return m.get(particleName);
    }

    public List<SynProperty> getPropertyList(String typeName) {
        Map<String, LocalPropertyConfig> m = propertyMap.get(typeName);
        if (m != null) {
            List<SynProperty> l = new ArrayList<SynProperty>();
            for (Iterator<LocalPropertyConfig> iter = m.values().iterator(); iter.hasNext();) {
                LocalPropertyConfig lp = iter.next();
                l.add(lp.property);
            }
            return l;
        } else {
            return new ArrayList<SynProperty>(1);
        }
    }

	public SynProperty getProperty(String typeName, String propertyName) {
		return getPropertyHelper(typeName, propertyName).property;
	}

    public List<LocalParticle> getParticleList(String typeName) {

        Map<String, LocalParticleConfig> m = particleMap.get(typeName);
        if (m != null) {
            List<LocalParticle> l = new ArrayList<LocalParticle>();
            for (Iterator<LocalParticleConfig> iter = m.values().iterator(); iter.hasNext();) {
                LocalParticleConfig lp = iter.next();
                l.add(lp.particle);
            }
            return l;
        } else {
            return new ArrayList<LocalParticle>(0);
        }
    }

    public String getSuperParticleName(String particleName) {
    	return ParticleInheritanceMap.get(particleName);
    }

    public Map<String, Map<String, LocalParticleConfig>> getParticleMaps() {
		return particleMap;
	}

    public static void main(String[] args) throws Exception {
		List<String> names = BEViewsElementNames.getTopLevelElementNames();
		for (String name : names) {
			System.out.println("["+name+"]");
			printProperties(" ",name);
			printTree(" ",name);
		}
	}

    static void printTree(String indent,String type) throws Exception{
    	List<LocalParticle> particles = ViewsConfigReader.getInstance().getParticleList(type);
    	for (LocalParticle localParticle : particles) {
    		System.out.println(indent+"Particle<name="+localParticle.getName()+",type="+localParticle.getTypeName()+">");
    		printProperties(indent+" ",localParticle.getTypeName());
			printTree(indent+" ",localParticle.getName());
		}
    }

    static void printProperties(String indent,String type) throws Exception{
    	List<SynProperty> properties = ViewsConfigReader.getInstance().getPropertyList(type);
    	for (SynProperty synProperty : properties) {
    		LocalPropertyConfig propertyHelper = ViewsConfigReader.getInstance().getPropertyHelper(type, synProperty.getName());
    		System.out.println("Property<name="+synProperty.getName()+",leaf="+propertyHelper.leaf+">");
    		if ((synProperty.getTypeDefinition() instanceof SynStringType) == true) {

    			if (propertyHelper.getReferences().isEmpty() == false) {
    				System.out.println(indent+"Property<name="+synProperty.getName()+",type="+synProperty.getTypeDefinition()+",references="+propertyHelper.getReferences()+">");
    			}
    			else if (propertyHelper.isElementRef == true){
    				System.out.println(indent+"Property<name="+synProperty.getName()+",type="+propertyHelper.getType()+">");
    			}
    		}
		}
    }
}
