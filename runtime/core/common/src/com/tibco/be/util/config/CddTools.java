package com.tibco.be.util.config;

import com.tibco.be.util.config.cdd.CddRoot;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.DomainObjectConfig;
import com.tibco.be.util.config.cdd.impl.CddPackageImpl;
import com.tibco.be.util.config.cdd.util.CddResourceImpl;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CddTools {
	
	public static Map<String,String> replaceInKeys(
			Map<String,String> input,
            String keyword,
            String replacement) {

        final Map<String,String> output = new HashMap<String,String>();
        final String quotedKeyword = Pattern.quote(keyword);

        for (final Map.Entry<String, String> entry : input.entrySet()) {
            final String k = entry.getKey();
            if (k instanceof String) {
                output.put(((String) k).replaceAll(quotedKeyword, replacement), entry.getValue());
            } else {
                output.put(k, entry.getValue());
            }
        }

        return output;
    }


    public static Map<String,String> addSuffixToKeys(
            Map<String,String> input,
            String suffix) {

        final Map<String,String> output = new HashMap<String,String>();

        for (final Map.Entry<String, String> entry : input.entrySet()) {
            final String k = entry.getKey();
            if (k instanceof String) {
                output.put(k + suffix, entry.getValue());
            } else {
                output.put(k, entry.getValue());
            }
        }

        return output;
    }

    
	public static String addEntryFromMixed(Map<Object, Object> map, String key,
			EObject o, boolean trim) {

		final String value = getValueFromMixed(o, trim);

		if ((null != value) && !value.isEmpty()) {
			map.put(key, value);
			return value;
		} else {
			return null;
		}
	}

	public static String addEntryFromMixed(Map<Object, Object> map, String key,
			EObject o, boolean trim, String defaultValue) {

		final String value = addEntryFromMixed(map, key, o, trim);

		if ((null == value) && (null != defaultValue)) {
			map.put(key, defaultValue);
			return defaultValue;
		} else {
			return null;
		}
	}

	public static String addEntryFromJoinOfMixed(Map<Object, Object> map, String key,
			EList<? extends EObject> list, String separator, boolean trim) {

		final String value = getValueFromJoinOfMixed(list, trim, separator);

		if ((null != value) && !value.isEmpty()) {
			map.put(key, value);
			return value;
		} else {
			return null;
		}
	}
	

	public static String addEntryFromJoinOfMixed(Map<Object, Object> map, String key,
			EList<? extends EObject> list, String separator, boolean trim, String defaultValue) {

		final String value = addEntryFromJoinOfMixed(map, key, list, separator, trim);

		if ((null == value) && (null != defaultValue)) {
			map.put(key, defaultValue);
			return defaultValue;
		} else {
			return null;
		}
	}
	
	
	public static String getValueFromJoinOfMixed(EList<? extends EObject> list, boolean trim, String separator) {

		if (null != list) {
			final StringBuffer sb = new StringBuffer();
			boolean addSeparator = false;
			for (final EObject o : list) {
				final String value = CddTools.getValueFromMixed(o,  true);
				if (null != value) {
					if (addSeparator) {
						sb.append(separator);
					} else {
						addSeparator = true;
					}
					sb.append(value);
				}				
			}
			return sb.toString();
		}
		
		return null;
	}

	public static String getValueFromMixed(EObject o, String defaultValue) {
		String value = getValueFromMixed(o, true);
		if (null == value) {
			return defaultValue;
		}
		return value;
	}
		
	public static String getValueFromMixed(EObject o) {
		return getValueFromMixed(o, true);
	}
		
	public static String getValueFromMixed(EObject o, boolean trim) {

		if (null != o) {
			try {
				FeatureMap mixed = (FeatureMap) o.getClass()
						.getMethod("getMixed").invoke(o);
				if ((null != mixed) && (mixed.size() > 0)) {
					String value = (String) mixed.get(0).getValue();
					if (null != value) {
						if (trim) {
							value = value.trim();
						}
						if (!value.isEmpty()) {
							return value;
						}
					}
				}
			} catch (Exception ignored) {
			}
		}

		return null;
	}
    
	
	public static com.tibco.be.util.config.cdd.ArtifactConfig findById(
			EList<? extends com.tibco.be.util.config.cdd.ArtifactConfig> list, String id) {
		if ((null != list) && (null != id)) {
			for (final com.tibco.be.util.config.cdd.ArtifactConfig a : list) {
				if (id.equals(a.getId())) {
					return a;
				}
			}
		}
		return null;
	}
	
	public static DomainObjectConfig findByUri(
			EList<DomainObjectConfig> list, String id) {
		if ((null != list) && (null != id)) {
			for (final DomainObjectConfig d : list) {
				if (id.equals(d.getUri())) {
					return d;
				}
			}
		}
		return null;
	}
	
	
	public static byte[] serialize(ClusterConfig clusterConfig)
			throws IOException {

        IOException lastException = null;
        for (int i = 0; i < 10; i++) {
            try {
                synchronized (CddTools.class) {
                    ClassLoader classLoader = (ClassLoader)
                            RuleServiceProviderManager.getInstance().getDefaultProvider().getTypeManager();
                    Thread.currentThread().setContextClassLoader(classLoader);

                    CddPackageImpl.eINSTANCE.eClass();
                    ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
                            "cdd", new GenericXMLResourceFactoryImpl());

                    final CddResourceImpl resource = new CddResourceImpl(URI.createURI("serialized.cdd"));
                    final Map<Object, Object> options = new HashMap<Object, Object>();
                    options.put(XMLResource.OPTION_ENCODING, "UTF-8");
                    options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
                    options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
                    options.put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
                    options.put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);

                    final ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    resource.getContents().add(clusterConfig.eContainer());
                    resource.save(baos, options);

                    return baos.toByteArray();
                }
            }
            catch (IOException e) {
                lastException = e;
            }
        }
        throw lastException;
    }

	public static ClusterConfig deserialize(byte[] serialized)
			throws IOException {

        synchronized (CddTools.class) {
            ClassLoader classLoader = (ClassLoader)
                    RuleServiceProviderManager.getInstance().getDefaultProvider().getTypeManager();
            Thread.currentThread().setContextClassLoader(classLoader);

            CddPackageImpl.eINSTANCE.eClass();
            ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
                    "cdd", new GenericXMLResourceFactoryImpl());

            final CddResourceImpl resource = new CddResourceImpl(URI.createURI("serialized.cdd"));
            final Map<Object, Object> options = new HashMap<Object, Object>();
            options.put(XMLResource.OPTION_ENCODING, "UTF-8");
            options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
            options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
            options.put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
            options.put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);

            resource.load(new ByteArrayInputStream(serialized), options);

            return ((CddRoot) resource.getContents().get(0)).getCluster();
        }
    }
	
}
