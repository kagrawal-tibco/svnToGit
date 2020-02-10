package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringEscapeUtils;

import com.tibco.be.functions.file.FileHelper;
import com.tibco.be.functions.object.ObjectHelper;
import com.tibco.be.rms.cep_rmsVersion;
import com.tibco.be.ws.functions.util.KEYWORD_REPLACEMENT_MAP;
import com.tibco.be.ws.functions.util.WebstudioFunctionUtils;
import com.tibco.be.ws.process.WSDLHelper;
import com.tibco.cep.designtime.model.AddOnRegistry;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyArrayConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.util.StringUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/3/12
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Common",
        synopsis = "Common and utility functions.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Common", value=true))

public class WebstudioServerCommonFunctions {
	private static final String SERVER_VERSION = "Version";
	private static final String SERVER_BUILD = "Build";
	private static final String SERVER_BUILDDATE = "BuildDate";
	private static final String SERVER_COPYRIGHT = "Copyright";
	
	private static final String REQUEST_DATA_PREFIX = "data=";
	private static final String REQUEST_DATA_COMPLETION = "%3C%2Frequest%3E";
		
	private static ByteArrayOutputStream bout = null;
	private static ZipOutputStream zos = null;

	@com.tibco.be.model.functions.BEFunction(
            name = "unzipProject",
            signature = "void unzipProject(String scsURL, Object zipProject, String subscriptionId)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsURL", type = "String", desc = "The path, where project has to be unzipped."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "zipProject", type = "Object", desc = "Project zip file."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "subscriptionId", type = "String", desc = "")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.5.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Unzips the project file at given location.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
        public static void unzipProject(String scsURL, Object zipProject, String subscriptionId) {
			if (!(zipProject instanceof byte[])) {
				throw new IllegalArgumentException(String.format("Argument should be of type [%s]", byte[].class.getName()));
			}	    	
	    	if	(subscriptionId != null) {
	    		scsURL = scsURL + File.separator + subscriptionId;
	    	}
	    	
	    	File project = new File(scsURL);
	    	if	(!project.exists()) {
	    		project.mkdirs();
	    	}
	    
			byte[] buff = (byte[]) zipProject;
			ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(buff));
			try {
				ZipEntry zipEntry = zipStream.getNextEntry();
				while (zipEntry != null) {
					String filePath =  scsURL + File.separator + zipEntry.getName();
					if (zipEntry.isDirectory()) {
						File directory =  new File(filePath);
						directory.mkdirs();	
					} else {
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
						byte[] bytesIn = new byte[2048];
						int read = 0;
					    while ((read = zipStream.read(bytesIn)) != -1) {
					            bos.write(bytesIn, 0, read);
					    }
					    bos.close(); 
					}
					zipStream.closeEntry();
					zipEntry = zipStream.getNextEntry();
				}
				zipStream.close();
			} catch (IOException e) {
				 throw new RuntimeException(e);
			}
        }
	
	
	@com.tibco.be.model.functions.BEFunction(
			name = "initStreams()",
			signature = "initStreams()",
			params = {
			},
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
			version = "5.6",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Initialize the output streams",
			cautions = "",
			fndomain = {ACTION},
			example = ""
	)
	public static void initStreams() {
		bout = new ByteArrayOutputStream();
		zos = new ZipOutputStream(bout);
	}
	
	@com.tibco.be.model.functions.BEFunction(
			name = "getExportedContentZip()",
			signature = "Object getExportedContentZip()",
			params = {
			},
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
			version = "5.6",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "",
			cautions = "",
			fndomain = {ACTION},
			example = ""
	)
	public static Object getExportedContentZip() {
		try {
			zos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bout.toByteArray();
	}
	
	@com.tibco.be.model.functions.BEFunction(
			name = "getProjectSize(String projectPath)",
			signature = "String getProjectSize(String projectPath)",
			params = {
					@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectPath", type = "String", desc = "project path"),
			},
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
			version = "5.6",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "",
			cautions = "",
			fndomain = {ACTION},
			example = ""
	)
	public static String getProjectSize(String projectPath) {
		File file = new File(projectPath);
		long size = getFileFolderSize(file);
		double sizeMB = (double) size / 1024 / 1024;
		String s = " MB";
		if (sizeMB < 1) {
			sizeMB = (double) size / 1024;
			s = " KB";
		}
		return sizeMB + s;
	}
	
	public static long getFileFolderSize(File dir) {
		long size = 0;
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if (file.isFile()) {
					size += file.length();
				} else
					size += getFileFolderSize(file);
			}
		} else if (dir.isFile()) {
			size += dir.length();
		}
		return size;
	}
	
	@com.tibco.be.model.functions.BEFunction(
			name = "updateExportedContent(String artifact, Object byteArray)",
			signature = "void updateExportedContent(String artifact, Object byteArray)",
			params = {
					@com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifact", type = "String", desc = "Artifact name"),
					@com.tibco.be.model.functions.FunctionParamDescriptor(name = "byteArray", type = "Object", desc = "Artifact exported bytes")
			},
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
			version = "5.6",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "",
			cautions = "",
			fndomain = {ACTION},
			example = ""
	)
	public static void updateExportedContent(String artifact, Object byteArray) {
	    try{
		   zos.putNextEntry(new ZipEntry(artifact));
		   byte[] array = (byte[]) byteArray;
		   zos.write(array);
		   zos.closeEntry();
	    }catch (Exception e) {
		// TODO: handle exception
	    }
		
	}
   
	@com.tibco.be.model.functions.BEFunction(
		name = "setExtId",
		signature = "void setExtId(Concept concept, String extId)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "concept", type = "Concept", desc = "The unasserted concept instance on which to set the extid."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "The extId to set.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Set extId on an un-asserted concept.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
    public static void setExtId(Concept concept, String extId) {
		if (concept == null) {
            throw new IllegalArgumentException("Concept param cannot be null");
        }
        concept.setExtId(extId);
    }

    @com.tibco.be.model.functions.BEFunction(
         name = "assertInstance",
         signature = "void assertInstance(Concept concept, boolean fireRules)",
         params = {
             @com.tibco.be.model.functions.FunctionParamDescriptor(name = "concept", type = "Concept", desc = "The unasserted concept."),
             @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fireRules", type = "boolean", desc = "Whether to fire rules or not as a reult of this assert.")
         },
         freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
         version = "5.1",
         see = "",
         mapper = @com.tibco.be.model.functions.BEMapper(),
         description = "Assert a concept into WM.",
         cautions = "",
         fndomain = {ACTION},
         example = ""
     )
    public static void assertInstance(Concept concept, boolean fireRules) {
        if (concept == null) {
            throw new IllegalArgumentException("Concept param cannot be null");
        }
        RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();
        if (ruleSession == null) {
            throw new RuntimeException("Cannot perform assert operation when no rulesession is present");
        }
        try {
            ruleSession.assertObject(concept, fireRules);
        } catch (DuplicateExtIdException e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getContainedConcept",
        signature = "Concept getContainedConcept (PropertyAtomContainedConcept pacc, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pacc", type = "PropertyAtomContainedConcept", desc = "The property atom to get the value for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The result."),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomContainedConcept from a non asserted concept.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Concept getContainedConcept(PropertyAtomContainedConcept pacc) {
        try {
            Object value = pacc.getValue();
            //Not required to go to Coherence store if concept is not asserted.
            return (Concept)value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getConceptReference",
        signature = "Concept getConceptReference(PropertyAtomConceptReference pacr)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pacr", type = "PropertyAtomConceptReference", desc = "The property atom to get the value for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The result."),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomConceptReference from a non asserted concept.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Concept getConceptReference(PropertyAtomConceptReference pacr) {
        try {
            Object value = pacr.getValue();
            //Not required to go to Coherence store if concept is not asserted.
            return (Concept)value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "toArrayConcept",
        signature = "Concept[] toArrayConcept(PropertyArrayConcept arr)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayConcept", desc = "A PropertyArray of type ConceptReference or ContainedConcept.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept[]", desc = "A Concept array containing all of the instances in PropertyArrayConcept in the correct order."),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a Concept[] containing all of the instances in a PropertyArrayConcept in the correct order",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Concept[] toArrayConcept(PropertyArrayConcept propertyArrayConcept) {
        int len = propertyArrayConcept.length();
        if (len == 0) {
            return (Concept[])Array.newInstance(propertyArrayConcept.getType(), len);
        }
        else {
            Concept[] ret = (Concept[])Array.newInstance(propertyArrayConcept.getType(), len);
            for (int i = 0; i < len; i++) {
                PropertyAtom propertyAtom = propertyArrayConcept.get(i);
                if (propertyAtom instanceof PropertyAtomConceptReference) {
                    PropertyAtomConceptReference propertyAtomConceptReference = (PropertyAtomConceptReference)propertyAtom;
                    long refId = propertyAtomConceptReference.getConceptId();
                    //Resolve this without loading into Rete
                    ret[i] = ObjectHelper.getById(refId);
                }
            }
            return ret;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "generateUUID",
        signature = "String generateUUID",
        params = {

        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A unique identifier."),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Generate random UID.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDestinationConceptArray",
        signature = "Concept[] getDestinationConceptArray(String conceptURI, int length)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "conceptURI", type = "String", desc = "The URI of concept to create an instance of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "length", type = "int", desc = "Desired length of the array.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept[]", desc = "The result."),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates an array of concept type specified by the uri. All elements are null.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Concept[] getDestinationConceptArray(String conceptURI, int length) {
        RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();
        TypeManager typeManager = ruleSession.getRuleServiceProvider().getTypeManager();
        if (typeManager != null) {
            TypeManager.TypeDescriptor typeDescriptor = typeManager.getTypeDescriptor(conceptURI);
            if (typeDescriptor != null) {
                return (Concept[]) Array.newInstance(typeDescriptor.getImplClass(), length);
            }
        }
        return null;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "joinStringArray",
        signature = "String joinStringArray(String[] stringArray, String delimiter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stringArray", type = "String[]", desc = "The array of strings to join."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "delimiter", type = "String", desc = "Desired delimiter.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The result."),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Join a string[] and create a string out of it with specified delimiter.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
	public static String joinStringArray(String[] stringArray, String delimiter) {
		if (delimiter == null || delimiter.isEmpty()) {
			return StringUtilities.join(stringArray);
		} else {
			return StringUtilities.join(stringArray, delimiter);
		}
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "splitToStringArray",
            signature = "String[] splitToStringArray(String originalString, String delimiter)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "originalString", type = "String", desc = "String to be split."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "delimiter", type = "String", desc = "Desired delimiter.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "String array as result"),
            version = "5.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Split a string to an array conditionally based on an delimiter.",
            cautions = "",
            fndomain = {ACTION, CONDITION, QUERY},
            example = ""
        )    
	public static String[] splitToStringArray(String originalString, String delimiter) {
		if (delimiter == null || delimiter.isEmpty()) {
			return StringUtilities.split(originalString);
		} else {
			return StringUtilities.split(originalString, delimiter);
		}
	}
		
    @com.tibco.be.model.functions.BEFunction(
        name = "unEscapeXML",
        signature = "String unEscapeXML(String xml)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xml", type = "String", desc = "XML String.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Unescaped XML String."),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Unescape characters in XML String.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String unEscapeXML(String xml) {
        if (xml == null) {
            throw new IllegalArgumentException("Argument XML String cannot be null");
        }

		return StringEscapeUtils.unescapeXml(xml);		
	}
    
    @com.tibco.be.model.functions.BEFunction(
        name = "byteArrayLength",
        signature = "int byteArrayLength(Object array)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "array", type = "Object", desc = "Getting the length of the array")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Byte array length"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the length of the byte array.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static int byteArrayLength(Object array) {
    	if (array == null) {
    		throw new IllegalArgumentException("Argument array cannot be null");
    	}
    	
    	if (array instanceof byte[]) {
    		byte[] byteArray = (byte[]) array;
    		return byteArray.length;
    	} else {
    		throw new IllegalArgumentException("Argument array is not of byte");
    	}
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "escapeXML",
        signature = "String escapeXML(String xml)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xml", type = "String", desc = "XML String.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Escaped XML String."),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Escape characters in XML String.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String escapeXML(String xml) {
        if (xml == null) {
            throw new IllegalArgumentException("Argument XML String cannot be null");
        }

		return StringEscapeUtils.escapeXml(xml);		
	}
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getServerBuildDetails",
        signature = "Object getServerVersion()",
        params = { },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Server version, build & copyright details."),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the server version, build & copyright details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getServerBuildDetails() {
    	Map<String, String> versionDetails = new HashMap<String, String>();
    	
    	versionDetails.put(SERVER_VERSION, cep_rmsVersion.version);
    	versionDetails.put(SERVER_BUILD, cep_rmsVersion.build);
    	versionDetails.put(SERVER_BUILDDATE, cep_rmsVersion.buildDate);
    	versionDetails.put(SERVER_COPYRIGHT, cep_rmsVersion.copyright);
    	
    	return versionDetails;
    }
    
    @com.tibco.be.model.functions.BEFunction(
		name = "isDecisionManagerInstalled",
		signature = "boolean isDecisionManagerInstalled()",
		params = { },
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
		version = "5.1.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Checks whether Decision Manager is installed.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
    public static boolean isDecisionManagerInstalled() {
    	try {
    		return AddOnRegistry.getInstance().getAddOnMap().containsKey(AddOnType.DECISIONMANAGER);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    @com.tibco.be.model.functions.BEFunction(
    	name = "getUniqueId",
    	signature = "String getUniqueId()",
    	params = { },
    	freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Unique"),
    	version = "5.1.0",
    	see = "",
    	mapper = @com.tibco.be.model.functions.BEMapper(),
    	description = "Creates a unique Id for an Element.",
    	cautions = "",
    	fndomain = {ACTION},
    	example = ""
    )
    public static String getUniqueId() {
    	return Long.toString(System.nanoTime()) + (new Random().nextInt(1000)) + (new Random().nextInt(1000));
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "escapeJavaScript",
        signature = "String escapeJavaScript(String content)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "content", type = "String", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Escaped String."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Escape characters in String as per JS String rules.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String escapeJavaScript(String content) {
        if (content == null) {
            throw new IllegalArgumentException("Argument String cannot be null");
        }
        //return StringEscapeUtils.escapeJavaScript(content);
        return StringEscapeUtils.escapeEcmaScript(content);
	}
    
    @com.tibco.be.model.functions.BEFunction(
		name = "getClassPathSeparator",
		signature = "String getClassPathSeparator()",
		params = {},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Classpath Separator value"),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the class path separator based on the underlying OS.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
    public static String getClassPathSeparator() {
    	return File.pathSeparator;
    }
    
    @com.tibco.be.model.functions.BEFunction(
		name = "cleanupRequestData",
		signature = "String cleanupRequestData(String rawData)",
		params = {
			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "rawData", type = "String", desc = "Raw request data")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Returns cleanup request data"),
		version = "5.2",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns cleanup request data",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
    public static String cleanupRequestData(String rawData) {
    	// remove the prefix
    	String cleanupData = null;
    	if (rawData.contains(REQUEST_DATA_PREFIX)) {
    		cleanupData = rawData.substring(REQUEST_DATA_PREFIX.length(), rawData.length());
    		int requestEndIndex = cleanupData.indexOf(REQUEST_DATA_COMPLETION) + REQUEST_DATA_COMPLETION.length();
    		cleanupData = cleanupData.substring(0, requestEndIndex);
    		try {
				cleanupData = URLDecoder.decode(cleanupData, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
    	} else {
    		cleanupData = rawData;
    	}
    	
    	return cleanupData;
    }
    
    @com.tibco.be.model.functions.BEFunction(
		name = "isBPMNInstalled",
		signature = "boolean isBPMNInstalled()",
		params = { },
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
		version = "5.1.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Checks whether BPMN is installed.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
    public static boolean isBPMNInstalled() {
    	try {
    		return AddOnRegistry.getInstance().getAddOnMap().containsKey(AddOnType.PROCESS);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    @com.tibco.be.model.functions.BEFunction(
		name = "loadAndParseWSDL",
		signature = "String loadAndParseWSDL(String wsdlPath)",
		params = { 
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "wsdlPath", type = "String", desc = "Path of the WSDL file.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Parsed WSDL"),
		version = "5.2.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Loads and Parses the given WSDL.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
    public static String loadAndParseWSDL(String wsdlPath) {
    	try {
    		return WSDLHelper.loadAndParseWSDL(wsdlPath);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    @com.tibco.be.model.functions.BEFunction(
		name = "getProjectLibraries",
		signature = "String[] getProjectLibraries(String rootURL, String projectName, String archivePath)",
		params = { 
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "rootURL", type = "String", desc = "Current rootURL of the project."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Project Name."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "archivePath", type = "String", desc = "Project archive Path.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "List of Project libraries"),
		version = "5.3.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Retrives the list of project libraries associated to the given project.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
    public static String[] getProjectLibraries(String rootURL, String projectName, String archivePath) {
    	try {
    		return WebstudioFunctionUtils.getProjectLibraries(rootURL, projectName, archivePath);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    @com.tibco.be.model.functions.BEFunction(
		name = "getContentFromProjectLibrary",
		signature = "String getContentFromProjectLibrary(String projectLibPath, String artifactPath, String artifactExtn)",
		params = { 
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectLibPath", type = "String", desc = "Path of the project Library."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "Artifact Path."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactExtn", type = "String", desc = "Artifact Extension")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Artifact Content"),
		version = "5.3.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Retrives the the artifact content from the given project library.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
    public static String getContentFromProjectLibrary(String projectLibPath, String artifactPath, String artifactExtn) {
    	try {
    		return WebstudioFunctionUtils.getContentFromProjectLibrary(projectLibPath, artifactPath, artifactExtn);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    @com.tibco.be.model.functions.BEFunction(
		name = "checkAndReplaceKeywords",
		signature = "String checkAndReplaceKeywords(String content, boolean incoming, boolean isXML)",
		params = { 
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "content", type = "String", desc = "Content needing keyword replacement"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "incoming", type = "boolean", desc = "Incoming or Outgoing."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "isXML", type = "boolean", desc = "Is XML/JSON.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Content minus reserved keywords"),
		version = "5.3.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Checks and replaces reserved keywords in the given content",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
    public static String checkAndReplaceKeywords(String content, boolean incoming, boolean isXML) {
    	try {
    		if (content != null && !content.isEmpty()) {
    			List<String> keywordsToCheck = (incoming) ? KEYWORD_REPLACEMENT_MAP.getRestrictedKeywordList() : KEYWORD_REPLACEMENT_MAP.getReplacementKeywordList();
    			
    			String replacementKeyword = null;
    			for (String keyword : keywordsToCheck) {
    				replacementKeyword = (incoming) ? KEYWORD_REPLACEMENT_MAP.getReplacementIfExists(keyword) : KEYWORD_REPLACEMENT_MAP.getRestrictedIfExists(keyword);
    				if (replacementKeyword != null && !replacementKeyword.isEmpty()) {
    					if (isXML) {
    						content = content.replace("<" + keyword + " ", "<" + replacementKeyword + " ");
    						content = content.replace("<" + keyword + ">", "<" + replacementKeyword + ">"); // for manual incoming cases
    						content = content.replace("</" + keyword + ">", "</" + replacementKeyword + ">");
    					} else {
    						content = content.replace("\"" + keyword + "\":", "\"" + replacementKeyword + "\":");
    					}
    				}
    			}
    		}
    		return content;
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    
    public static void main(String[] args) {
    	boolean isXML = false;
    	boolean inComing = true;
    	String basePath = "/Users/vpatil/Downloads/%s.json";
    	
    	String originalFileContents = FileHelper.readFileAsString(String.format(basePath, "processContent"), "UTF-8");
    	String replacedContent = checkAndReplaceKeywords(originalFileContents, inComing, isXML);
    	
    	Object fileWriter = FileHelper.openFile(String.format(basePath, "processContentReplaced"), "rw");
    	FileHelper.fileWrite(fileWriter, replacedContent);
    }
}
