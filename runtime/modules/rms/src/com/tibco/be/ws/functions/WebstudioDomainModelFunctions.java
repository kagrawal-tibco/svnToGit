package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.cep.studio.core.index.utils.CommonIndexUtils.DOMAIN_EXTENSION;
import static com.tibco.cep.studio.core.index.utils.CommonIndexUtils.deserializeEObjectFromString;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLHelperImpl;

import com.tibco.be.ws.functions.util.WebstudioFunctionUtils;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.designtime.core.model.domain.Single;
import com.tibco.cep.designtime.core.model.domain.util.DomainUtils;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Domain",
        synopsis = "Functions to work with Domain model artifacts in Webstudio.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Domain", value=true))

public class WebstudioDomainModelFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "resolveDomainForPath",
        synopsis = "",
        signature = "Domain resolveDomainForPath(String scsIntegrationType, String repoRootURL, String projectName, String artifactPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The SCS integration type to be used. Default is file for null value."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "The SCS root url."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The name of the project"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "The FQN of the domain model")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "string for the binding type"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getRuleTemplateBindings",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Fetch the domain model instance matching this FQN.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object resolveDomainForPath(String scsIntegrationType,
                                              String repoRootURL,
                                              String projectName,
                                              String artifactPath) {
        String domainModelContents =
            WebstudioServerSCSFunctions.showArtifactContents(scsIntegrationType,
                    repoRootURL,
                    projectName,
                    artifactPath,
                    DOMAIN_EXTENSION,
                    null, null);
        //Parse contents
        try {
            return deserializeEObjectFromString(domainModelContents);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDomainFullPath",
        synopsis = "",
        signature = "Object getDomainFullPath(Object domainObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainObject", type = "Object", desc = "The domain model instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "the", desc = "FQN of the Domain."),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getDomainsForBinding",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Fetch the FQN of the Domain.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getDomainFullPath(Object domainObject) {
        if (!(domainObject instanceof Domain)) {
            throw new IllegalArgumentException("Passed argument should be a Domain");
        }
        return ((Domain)(domainObject)).getFullPath();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDomainDataType",
        synopsis = "",
        signature = "String getDomainDataType(Object domainObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainObject", type = "Object", desc = "The domain model instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "the", desc = "datatype of the Domain."),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getDomainsForBinding",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Fetch the datatype of the Domain.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getDomainDataType(Object domainObject) {
        if (!(domainObject instanceof Domain)) {
            throw new IllegalArgumentException("Passed argument should be a Domain");
        }
        return ((Domain)(domainObject)).getDataType().getLiteral();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDomainForPath",
        synopsis = "",
        signature = "Object[] getAllDomainEntries(String scsIntegrationType, String repoRootURL, String projectName, Object domain)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The SCS integration type to be used. Default is file for null value."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "The SCS root url."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The name of the project"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domain", type = "Object", desc = "The Domain model instance")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "string for the binding type"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getDomainsForBinding",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Fetch all domain model entries including those of super domains as well.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getAllDomainEntries(String scsIntegrationType,
                                               String repoRootURL,
                                               String projectName,
                                               Object domain) {
        if (!(domain instanceof Domain)) {
            throw new IllegalArgumentException("Parameter should be an instance of Domain");
        }
        try {
            Object[] domainEntries =
                WebstudioFunctionUtils.
                getDomainEntriesAndDescriptions(scsIntegrationType, repoRootURL, projectName, (Domain) domain);
            return domainEntries;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "createDomainModelEMFObject",
            synopsis = "",
            signature = "Object createDomainModelEMFObject(String domainModelXML)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainModelXML", type = "String", desc = "Domain Model XML.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Domain Model EObject."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates Domain (DomainImpl) EObject from the XML model.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	public static Object createDomainModelEMFObject(String domainModelXML) {
        if (domainModelXML == null) {
            throw new IllegalArgumentException("Argument domain model XML cannot be null");
        }		
		EObject eObject;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(domainModelXML.getBytes("UTF-8"));
			eObject = CommonIndexUtils.deserializeEObject(bais);
			if (eObject instanceof Domain) {
				return eObject;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "getNamespace",
            synopsis = "",
            signature = "String getNamespace(Object domainEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEMFModelObject", type = "Object", desc = "Domain EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "namespace of the Domain."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the namespace of the Domain.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getNamespace(Object domainEMFModelObject) {
		if (!(domainEMFModelObject instanceof Domain)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Domain.class.getName()));
		}
		Domain domain = (Domain) domainEMFModelObject;
		return domain.getNamespace();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getName",
            synopsis = "",
            signature = "String getName(Object domainEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEMFModelObject", type = "Object", desc = "Domain EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Name of the Domain."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the name of the Domain.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getName(Object domainEMFModelObject) {
		if (!(domainEMFModelObject instanceof Domain)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Domain.class.getName()));
		}
		Domain domain = (Domain) domainEMFModelObject;
		return domain.getName();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getFolder",
            synopsis = "",
            signature = "String getFolder(Object domainEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEMFModelObject", type = "Object", desc = "Domain EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Folder of the Domain."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the folder of the Domain.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getFolder(Object domainEMFModelObject) {
		if (!(domainEMFModelObject instanceof Domain)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Domain.class.getName()));
		}
		Domain domain = (Domain) domainEMFModelObject;
		return domain.getFolder();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getDescription",
            synopsis = "",
            signature = "String getDescription(Object domainEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEMFModelObject", type = "Object", desc = "Domain EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Description of the Domain."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the description of the Domain.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getDescription(Object domainEMFModelObject) {
		if (!(domainEMFModelObject instanceof Domain)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Domain.class.getName()));
		}
		Domain domain = (Domain) domainEMFModelObject;
		return domain.getDescription();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getSuperDomainPath",
            synopsis = "",
            signature = "String getSuperDomainPath(Object domainEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEMFModelObject", type = "Object", desc = "Domain EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Path of the parent Domain."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the path of the parent Domain.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getSuperDomainPath(Object domainEMFModelObject) {
		if (!(domainEMFModelObject instanceof Domain)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Domain.class.getName()));
		}
		Domain domain = (Domain) domainEMFModelObject;
		return domain.getSuperDomainPath();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getOwnerProjectName",
            synopsis = "",
            signature = "String getOwnerProjectName(Object domainEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEMFModelObject", type = "Object", desc = "Domain EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Project name of the Domain."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Project name of the Domain.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getOwnerProjectName(Object domainEMFModelObject) {
		if (!(domainEMFModelObject instanceof Domain)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Domain.class.getName()));
		}
		Domain domain = (Domain) domainEMFModelObject;
		return domain.getOwnerProjectName();
    }    

    @com.tibco.be.model.functions.BEFunction(
            name = "getDomainEntries",
            synopsis = "",
            signature = "Object[] getDomainEntries(Object domainEMFModelObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEMFModelObject", type = "Object", desc = "Domain EMF Model object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Array of domain entries of the Domain."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns an array of Domain entries of the Domain.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static Object[] getDomainEntries(Object domainEMFModelObject) {
		if (!(domainEMFModelObject instanceof Domain)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Domain.class.getName()));
		}
		Domain domain = (Domain) domainEMFModelObject;
		List<DomainEntry> entries = domain.getEntries();
		if (entries != null) {
			return entries.toArray();
		}
		
		return new Object[0];		
    }    
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getDomainEntryType",
            synopsis = "",
            signature = "String getDomainEntryType(Object domainEntryEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEntryEMFObject", type = "Object", desc = "DomainEntry EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Type of Domain entry."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the type of Domain entry.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getDomainEntryType(Object domainEntryEMFObject) {
		if (!(domainEntryEMFObject instanceof DomainEntry)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", DomainEntry.class.getName()));
		}
		String type = null;
		DomainEntry domainEntry = (DomainEntry) domainEntryEMFObject;
		if (domainEntry instanceof Single) {
			type = "Single";
		} else if (domainEntry instanceof Range) {
			type = "Range";
		}
		return type;
    }    

    @com.tibco.be.model.functions.BEFunction(
            name = "getDomainEntryDescription",
            synopsis = "",
            signature = "String getDomainEntryDescription(Object domainEntryEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEntryEMFObject", type = "Object", desc = "DomainEntry EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Domain entry description."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Domain entry description.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getDomainEntryDescription(Object domainEntryEMFObject) {
		if (!(domainEntryEMFObject instanceof DomainEntry)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", DomainEntry.class.getName()));
		}
		DomainEntry domainEntry = (DomainEntry) domainEntryEMFObject;
		return domainEntry.getDescription();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getDomainEntryValue",
            synopsis = "",
            signature = "String getDomainEntryValue(Object domainEntryEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEntryEMFObject", type = "Object", desc = "DomainEntry EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Domain entry value."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Domain entry value.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getDomainEntryValue(Object domainEntryEMFObject) {
		if (!(domainEntryEMFObject instanceof DomainEntry)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", DomainEntry.class.getName()));
		}
		DomainEntry domainEntry = (DomainEntry) domainEntryEMFObject;
		return domainEntry.getValue();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getDomainRangeLowerValue",
            synopsis = "",
            signature = "String getDomainRangeLowerValue(Object domainEntryEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEntryEMFObject", type = "Object", desc = "DomainEntry EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Lower value of Range entry."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Lower value of the Range entry.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getDomainRangeLowerValue(Object domainEntryEMFObject) {
		if (!(domainEntryEMFObject instanceof Range)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Range.class.getName()));
		}
		Range range = (Range) domainEntryEMFObject;		
		return range.getLower();
    } 
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getDomainRangeUpperValue",
            synopsis = "",
            signature = "String getDomainRangeUpperValue(Object domainEntryEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEntryEMFObject", type = "Object", desc = "DomainEntry EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Upper value of Range entry."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Upper value of the Range entry.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getDomainRangeUpperValue(Object domainEntryEMFObject) {
		if (!(domainEntryEMFObject instanceof Range)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Range.class.getName()));
		}
		Range range = (Range) domainEntryEMFObject;		
		return range.getUpper();
    } 
    
    @com.tibco.be.model.functions.BEFunction(
            name = "isDomainRangeLowerInclusive",
            synopsis = "",
            signature = "boolean isDomainRangeLowerInclusive(Object domainEntryEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEntryEMFObject", type = "Object", desc = "DomainEntry EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns true if the Lower value is inclusive in the Range entry, false other-wise.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static boolean isDomainRangeLowerInclusive(Object domainEntryEMFObject) {
		if (!(domainEntryEMFObject instanceof Range)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Range.class.getName()));
		}
		Range range = (Range) domainEntryEMFObject;		
		return range.isLowerInclusive();
    } 
    
    @com.tibco.be.model.functions.BEFunction(
            name = "isDomainRangeUpperInclusive",
            synopsis = "",
            signature = "boolean isDomainRangeUpperInclusive(Object domainEntryEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEntryEMFObject", type = "Object", desc = "DomainEntry EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns true if the Upper value is inclusive in the Range entry, false other-wise.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static boolean isDomainRangeUpperInclusive(Object domainEntryEMFObject) {
		if (!(domainEntryEMFObject instanceof Range)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Range.class.getName()));
		}
		Range range = (Range) domainEntryEMFObject;		
		return range.isUpperInclusive();
    }    

    @com.tibco.be.model.functions.BEFunction(
            name = "getFormattedSingleEntryValue",
            synopsis = "",
            signature = "String getFormattedSingleEntryValue(String dataType, String value)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "dataType", type = "String", desc = "DomainEntry EMF object."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "value to be formatted.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Formatted value"),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the formatted value.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getFormattedSingleEntryValue(String dataType, String value) {
		if (DTDomainUtil.containsWhiteSpace(value)) {
			value = "\""+ value + "\"";
		}
		if (DOMAIN_DATA_TYPES.STRING.getLiteral().equalsIgnoreCase(dataType)) {
			if (DomainUtils.isNumeric(value) || DomainUtils.isLong(value) || DomainUtils.isDouble(value)) {
				value = "\"" + value + "\"";
			}
		}
		return value;
    }    

    @com.tibco.be.model.functions.BEFunction(
            name = "getFormattedRangeEntryValue",
            synopsis = "",
            signature = "String getFormattedRangeEntryValue(boolean includeLower, boolean includeUpper, String lower, String upper)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "includeLower", type = "boolean", desc = "is Lower Inclusive"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "includeUpper", type = "boolean", desc = "is Upper Inclusive"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "lower", type = "String", desc = "Lower value"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "upper", type = "String", desc = "Upper value")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Formatted value"),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the formatted value.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String getFormattedRangeEntryValue(boolean includeLower, boolean includeUpper, String lower, String upper) {
		return DTDomainUtil.getStringValueFromRangeInfo(includeLower, includeUpper, lower, upper);
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "createDomainEMFObject",
            synopsis = "",
            signature = "Object createDomainEMFObject(String name, String project, String folder, String dataType, String superDomainPath)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "Domain name."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "project", type = "String", desc = "Owner project."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "folder", type = "String", desc = "Domain containing folder."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "dataType", type = "String", desc = "Domain data type.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Domain EMF Object."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates a new Domain EMF Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static Object createDomainEMFObject(String name, String project, String folder, String dataType) {
		Domain domainModel = DomainFactory.eINSTANCE.createDomain();
		domainModel.setName(name);
		domainModel.setOwnerProjectName(project);
		domainModel.setFolder(folder);
		domainModel.setDataType(DOMAIN_DATA_TYPES.get(dataType));	
		return domainModel;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setDescription",
            synopsis = "",
            signature = "void setDescription(Object domainEMFObject, String description)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEMFObject", type = "Object", desc = "Domain EMF model."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "description", type = "String", desc = "Description.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Set the Description for domain.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void setDescription(Object domainEMFObject, String description) {
		if (!(domainEMFObject instanceof Domain)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Domain.class.getName()));
		}
		Domain domain = (Domain) domainEMFObject;
		domain.setDescription(description);
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "setSuperDomain",
            synopsis = "",
            signature = "void setSuperDomain(Object domainEMFObject, String superDomainPath)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEMFObject", type = "Object", desc = "Domain EMF model."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "superDomainPath", type = "String", desc = "Super Domain.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Set the path of the super domain.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void setSuperDomain(Object domainEMFObject, String superDomainPath) {
		if (!(domainEMFObject instanceof Domain)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Domain.class.getName()));
		}
		Domain domain = (Domain) domainEMFObject;
		domain.setSuperDomainPath(superDomainPath);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setNamespace",
            synopsis = "",
            signature = "void setNamespace(Object domainEMFObject, String namespace)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEMFObject", type = "Object", desc = "Domain EMF model."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "namespace", type = "String", desc = "Namespace.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Set the namespace for domain.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void setNamespace(Object domainEMFObject, String namespace) {
		if (!(domainEMFObject instanceof Domain)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Domain.class.getName()));
		}
		Domain domain = (Domain) domainEMFObject;
		domain.setNamespace(namespace);
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "addSingleEntryToDomain",
            synopsis = "",
            signature = "void addSingleEntryToDomain(Object domainEMFObject, String value, String description)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEMFObject", type = "Object", desc = "Domain EMF model."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "Entry value."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "description", type = "String", desc = "Entry description.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates a new Single DomainEntry and adds to Domain EMF Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void addSingleEntryToDomain(Object domainEMFObject, String value, String description) {
		if (!(domainEMFObject instanceof Domain)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Domain.class.getName()));
		}
		Domain domain = (Domain) domainEMFObject; 
    	DomainEntry domainEntry = DomainFactory.eINSTANCE.createSingle();
		domainEntry.setValue(value);
		domainEntry.setDescription(description);
		domain.getEntries().add(domainEntry);
    }    
    
    @com.tibco.be.model.functions.BEFunction(
            name = "addRangeEntryToDomain",
            synopsis = "",
            signature = "void addRangeEntryToDomain(Object domainEMFObject, String value, String description)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domainEMFObject", type = "String", desc = "Domain EMF model."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "lowerInclusive", type = "boolean", desc = "Is lower inclusive."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "lower", type = "String", desc = "lower value of range."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "upperInclusive", type = "boolean", desc = "Entry value."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "upper", type = "String", desc = "upper value of range."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "description", type = "String", desc = "Entry description.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates a new Single DomainEntry and adds to Domain EMF Object.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void addRangeEntryToDomain(Object domainEMFObject, boolean lowerInclusive, String lower, boolean upperInclusive, String upper, String description) {
		if (!(domainEMFObject instanceof Domain)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Domain.class.getName()));
		}
		Domain domain = (Domain) domainEMFObject; 
    	Range domainEntry = DomainFactory.eINSTANCE.createRange();
		domainEntry.setDescription(description);
		domainEntry.setLower(lower);
		domainEntry.setUpper(upper);
		domainEntry.setLowerInclusive(lowerInclusive);
		domainEntry.setUpperInclusive(upperInclusive);
		domain.getEntries().add(domainEntry);
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "serializeDomainEMFObject",
            synopsis = "",
            signature = "Object serializeDomainEMFObject(Object domainEObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableEObject", type = "Object", desc = "Domain EMF Object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Serialized XML byte stream of Domain."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Serializes Domain EObject to XML byte stream.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    @SuppressWarnings("unchecked")
	public static Object serializeDomainEMFObject(Object domainEObject) {
		Object obj;
		if (!(domainEObject instanceof Domain)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Domain.class.getName()));
		}

		Map<String, Boolean> options = new HashMap<String, Boolean>();
		options.put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);
		options.put(XMLResource.OPTION_FORMATTED, Boolean.TRUE);
		
		Domain domainModel = (Domain) domainEObject;
		XMLHelperImpl xmlHelper = new XMLHelperImpl();
		ArrayList<Domain> arrayList = new ArrayList<Domain>();
		arrayList.add(domainModel);
		try {
			String xml = XMLHelperImpl.saveString(options, arrayList, "UTF-8", xmlHelper);
			obj = xml.getBytes("UTF-8");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	
		return obj;
	}    
}
