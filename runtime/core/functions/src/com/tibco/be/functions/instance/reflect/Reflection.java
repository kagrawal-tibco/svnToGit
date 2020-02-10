package com.tibco.be.functions.instance.reflect;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

import com.tibco.cep.runtime.model.element.*;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Instance.Reflection",
        synopsis = "Reflection method for Concept")
public class Reflection {

    @com.tibco.be.model.functions.BEFunction(
        name = "toObject",
        synopsis = "This method explicitly casts the property to an object. It returns the property object, not the value in the property.",
        signature = "Object toObject(Property pa)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "Property", desc = "The target property.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The casted Property object."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method explicitly casts the property to an object. It returns the property object, not the value in the property.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object toObject(Property pa) {
    	return pa;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getProperty",
        synopsis = "This method returns a property of a Concept Instance given the property name.",
        signature = "Object getProperty(Concept instance, String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "the concept instance"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "the property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The property of the concept instance with the given property name"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns a property of a Concept Instance given the property name.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Property getProperty(Concept instance, String propertyName) {
        return instance.getProperty(propertyName);

    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getAllProperties",
        synopsis = "This method returns all properties of a Concept Instance.",
        signature = "Object[] getAllProperties(Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "the concept instance")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "The properties of the concept instance"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns all properties of a Concept Instance.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Property[] getAllProperties(Concept instance) {
        return instance.getProperties();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getAllPropertyNames",
        synopsis = "This method returns all property names of a Concept Instance.",
        signature = "String[] getAllPropertyNames(Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "the concept instance")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "The property names of the concept instance"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns all property names of a Concept Instance.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String[] getAllPropertyNames(Concept instance) {
        Property[] ps = instance.getProperties();
        String[] ret = new String[ps.length];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = ps[i].getName();
        }
        return ret;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setPropertyAtomValue",
        synopsis = "Given the concept instance and property name, set the value of a PropertyAtom to the value passed.",
        signature = "boolean setPropertyAtomValue(Concept instance, String name, Object value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "the concept instance"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "The name of the propertyAtom"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "The new value for the property. Its type has to be matched the property type.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if property is set to new value, false otherwise.  e.g. mismatched type, no such property exception."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Given the concept instance and property name, set the value of a PropertyAtom to the value passed.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean setPropertyAtomValue(Concept instance, String name, Object value) {
        try {
            instance.setPropertyValue(name, value);
            return true;
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getPropertyArrayLength",
        synopsis = "This method returns the length of a PropertyArray given the Concept instance and the property name.\nIt returns -1 if the length can't be determined. e.g. no such propertyArray exception",
        signature = "int getPropertyArrayLength(Concept instance, String name)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "the concept instance"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "the propertyArray name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The length of a PropertyArray."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the length of a PropertyArray given the Concept instance and the property name.\nIt returns -1 if the length can't be determined. e.g. no such propertyArray exception",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int getPropertyArrayLength(Concept instance, String name) {
        try {
            Property p = instance.getProperty(name);
            if(p instanceof PropertyArray) {
                return ((PropertyArray)p).length();
            }
            else {
                return -1;
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setPropertyArrayValue",
        synopsis = "Given the concept instance and property name, set the value of a PropertyArray at index to the value passed.",
        signature = "boolean setPropertyArrayValue(Concept instance, String name, int index, Object value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "the concept instance"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "The name of the propertyArray"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The position in the propertyArray"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "The new value, the type has to be matched the property type.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if property is set to new value, false otherwise.  e.g. mismatched type, no such property exception."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Given the concept instance and property name, set the value of a PropertyArray at index to the value passed.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean setPropertyArrayValue(Concept instance, String name, int index, Object value) {
        try {
            Property p = instance.getProperty(name);
            if(p instanceof PropertyArray) {
                PropertyArray pa = instance.getPropertyArray(name);
                int length = pa.length();
                if(index == length) {
                    pa.add(value);
                    return true;
                }
                else {
                    pa.get(index).setValue(value);
                    return true;
                }
            }
            else {
                return false;
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getPropertyArrayValue",
            synopsis = "Given the concept instance and property name, get the value of a PropertyArray at index to the value passed.",
            signature = "Object getPropertyArrayValue(Concept instance, String name, int index)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The concept instance"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "The name of the propertyArray"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The position in the propertyArray"),
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The value of the named property"),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Given the concept instance and property name, get the value of a PropertyArray at the index passed.",
            cautions = "none",
            fndomain = {ACTION, BUI},
            example = ""
        )
        public static Object getPropertyArrayValue(Concept instance, String name, int index) {
            try {
                Property p = instance.getProperty(name);
                if(p instanceof PropertyArray) {
                    PropertyAtom atom = ((PropertyArray)p).get(index);
                    if(atom != null) return atom.getValue();
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "isProperty",
        synopsis = "This method checks if an object is a Property object.",
        signature = "boolean isProperty(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object to be checked")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns if it is a Property object"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if an object is a Property object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean isProperty(Object obj) {
        return (obj instanceof Property);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isPropertyAtom",
        synopsis = "This method checks if an object is a PropertyAtom object.",
        signature = "boolean isPropertyAtom(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object to be checked")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns if it is a PropertyAtom object"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if an object is a PropertyAtom object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean isPropertyAtom(Object obj) {
        return (obj instanceof PropertyAtom);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isPropertyArray",
        synopsis = "This method checks if an object is a PropertyArray object.",
        signature = "boolean isPropertyArray(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object to be checked")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns if it is a PropertyArray object"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if an object is a PropertyArray object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean isPropertyArray(Object obj) {
        return (obj instanceof PropertyArray);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isPropertyBoolean",
        synopsis = "This method checks if an object is a PropertyBoolean object.",
        signature = "boolean isPropertyBoolean(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object to be checked")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns if it is a PropertyBoolean object"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if an object is a PropertyBoolean object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean isPropertyBoolean(Object obj) {
        return (obj instanceof Property.PropertyBoolean);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isPropertyConcept",
        synopsis = "This method checks if an object is a PropertyConcept object.",
        signature = "boolean isPropertyConcept(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object to be checked")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns if it is a PropertyConcept object"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if an object is a PropertyConcept object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static boolean isPropertyConcept(Object obj) {
        return (obj instanceof Property.PropertyConcept);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isPropertyConceptReference",
        synopsis = "This method checks if an object is a PropertyConceptReference object.",
        signature = "boolean isPropertyConceptReference(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object to be checked")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns if it is a PropertyConceptReference object"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if an object is a PropertyConceptReference object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean isPropertyConceptReference(Object obj) {
        return (obj instanceof Property.PropertyConceptReference);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isPropertyContainedConcept",
        synopsis = "This method checks if an object is a PropertyContainedConcept object.",
        signature = "boolean isPropertyContainedConcept(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object to be checked")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns if it is a PropertyContainedConcept object"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if an object is a PropertyContainedConcept object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean isPropertyContainedConcept(Object obj) {
        return (obj instanceof Property.PropertyContainedConcept);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isPropertyDateTime",
        synopsis = "This method checks if an object is a PropertyDateTime object.",
        signature = "boolean isPropertyDateTime(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object to be checked")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns if it is a PropertyDateTime object"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if an object is a PropertyDateTime object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean isPropertyDateTime(Object obj) {
        return (obj instanceof Property.PropertyDateTime);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isPropertyDouble",
        synopsis = "This method checks if an object is a PropertyDouble object.",
        signature = "boolean isPropertyDouble(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object to be checked")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns if it is a PropertyDouble object"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if an object is a PropertyDouble object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean isPropertyDouble(Object obj) {
        return (obj instanceof Property.PropertyDouble);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isPropertyInt",
        synopsis = "This method checks if an object is a PropertyInt object.",
        signature = "boolean isPropertyInt(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object to be checked")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns if it is a PropertyInt object"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if an object is a PropertyInt object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean isPropertyInt(Object obj) {
        return (obj instanceof Property.PropertyInt);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isPropertyLong",
        synopsis = "This method checks if an object is a PropertyLong object.",
        signature = "boolean isPropertyLong(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object to be checked")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns if it is a PropertyLong object"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if an object is a PropertyLong object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean isPropertyLong(Object obj) {
        return (obj instanceof Property.PropertyLong);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isPropertyString",
        synopsis = "This method checks if an object is a PropertyString object.",
        signature = "boolean isPropertyString(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object to be checked")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns if it is a PropertyString object"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if an object is a PropertyString object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean isPropertyString(Object obj) {
        return (obj instanceof Property.PropertyString);
    }

    /*
    public static double toDouble(Object obj) {

    }

    public static int toInt(Object obj) {

    }

    public static long toLong(Object obj) {

    }

    public static
     */

    @com.tibco.be.model.functions.BEFunction(
        name = "sum",
        synopsis = "Total sum of property (repeating or child's property) of a given instance or a property",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.instance.Reflection.sum", value=false),
        signature = "Object sum(Object instance_or_property, String propertyPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance_or_property", type = "Object", desc = "concept instance or property"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyPath", type = "String", desc = "the path to the property for sum")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "total sum"),
        version = "3.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Total sum of property (repeating or child's property) of a given instance or a property",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Object sum(Object instance_or_property, String propertyPath) {
        if(instance_or_property instanceof Concept)
            return getSum((Concept)instance_or_property, propertyPath) ;
        else //property
            return getSum((Property)instance_or_property, propertyPath) ;
    }

    static Object sumProperty(Property prop) {
        if(prop instanceof PropertyArray) {
            PropertyArray pa = (PropertyArray)prop;
            int retInt = 0;
            double retDouble = 0.0;
            long retLong = 0L;
            String retStr = "";
            for(int i = 0; i < pa.length(); i++ ) {
                if(pa instanceof PropertyArrayInt) {
                    retInt += (Integer)((PropertyArrayInt)pa).get(i).getValue();
                }
                else if(pa instanceof PropertyArrayDouble) {
                    retDouble += (Double)((PropertyArrayDouble)pa).get(i).getValue();
                }
                else if(pa instanceof PropertyArrayLong) {
                    retLong += (Long)((PropertyArrayLong)pa).get(i).getValue();
                }
                else if(pa instanceof PropertyArrayString) {
                    retStr += (String)((PropertyArrayString)pa).get(i).getValue();
                }
                else {
                    throw new RuntimeException("Sum for Property type <" + prop.getClass().getName() + "> is not supported");
                }
            }
            if(pa instanceof PropertyArrayInt) {
                return retInt;
            }
            else if(pa instanceof PropertyArrayDouble) {
                return retDouble;
            }
            else if(pa instanceof PropertyArrayLong) {
                return retLong;
            }
            else if(pa instanceof PropertyArrayString) {
                return retStr;
            }
            else {
                throw new RuntimeException("Sum for Property type <" + prop.getClass().getName() + "> is not supported");
            }
        }
        else { //propertyAtom
            if(prop instanceof PropertyAtomInt) {
                return ((PropertyAtomInt)prop).getInt();
            }
            else if(prop instanceof PropertyAtomDouble) {
                return ((PropertyAtomDouble)prop).getDouble();
            }
            else if(prop instanceof PropertyAtomLong) {
                return ((PropertyAtomLong)prop).getLong();
            }
            else if(prop instanceof PropertyAtomString) {
                return ((PropertyAtomString)prop).getString();
            }
            else {
                throw new RuntimeException("Sum for Property type <" + prop.getClass().getName() + "> is not supported");
            }
        }
    }


    static Object getSum(Property in, String path) {
        if(path == null || path.trim().equals("")) {
            //calculate this one
            return sumProperty(in);
        }
        //there is something
        if(in instanceof PropertyAtomConcept) {
            return getSum(((PropertyAtomConcept)in).getConcept(), path);
        }
        else if (in instanceof PropertyArrayConcept){
            Object ret = null;
            PropertyArrayConcept pa = (PropertyArrayConcept)in;
            for(int i = 0; i < pa.length(); i++) {
                if(i==0)
                    ret = getSum((Concept) pa.get(i).getValue(), path);
                else {
                    if(ret instanceof Integer)
                        ret = (((Integer)ret).intValue() + ((Integer)getSum((Concept) pa.get(i).getValue(), path)).intValue());
                    else if (ret instanceof Double)
                        ret = (((Double)ret).doubleValue() + ((Double)getSum((Concept) pa.get(i).getValue(), path)).doubleValue());
                    else if (ret instanceof Long)
                        ret = (((Long)ret).longValue() + ((Long)getSum((Concept) pa.get(i).getValue(), path)).longValue());
                    else if (ret instanceof String)
                        ret = (((String)ret) + ((String)getSum((Concept) pa.get(i).getValue(), path)));
                }
            }
            return ret;
        }
        else {
            throw new RuntimeException("PropertyPath is not correct");
        }
    }

    static Object getSum(Concept in, String path) {
        String[] splitted = path.split("\\.");
        if(splitted.length == 1) {
            //reached
            Property prop = in.getProperty(splitted[0]);
            return sumProperty(prop);
        }
        //more path
        String remainPath =  path.substring(splitted[0].length()+1);
        Property prop = in.getProperty(splitted[0]);
        if(prop instanceof PropertyArray) {
            if(prop instanceof PropertyArrayConcept) {
                PropertyArrayConcept pa = ((PropertyArrayConcept)prop);
                Object ret = null;
                for(int i = 0; i < pa.length(); i++) {
                    if(i==0)
                        ret = getSum((Concept) pa.get(i).getValue(), remainPath);
                    else {
                        if(ret instanceof Integer)
                            ret = (((Integer)ret).intValue() + ((Integer)getSum((Concept) pa.get(i).getValue(), remainPath)).intValue());
                        else if (ret instanceof Double)
                            ret = (((Double)ret).doubleValue() + ((Double)getSum((Concept) pa.get(i).getValue(), remainPath)).doubleValue());
                        else if (ret instanceof Long)
                            ret = (((Long)ret).longValue() + ((Long)getSum((Concept) pa.get(i).getValue(), remainPath)).longValue());
                        else if (ret instanceof String)
                            ret = (((String)ret) + ((String)getSum((Concept) pa.get(i).getValue(), remainPath)));
                    }
                }
                return ret;
            }
            else {
                throw new RuntimeException("PropertyPath is not correct or this type of property is not supported");
            }
        }
        else {
            if(prop instanceof PropertyAtomConcept) {
                return getSum(((PropertyAtomConcept)prop).getConcept(), remainPath);
            }
            else {
                throw new RuntimeException("PropertyPath is not correct");
            }
        }
    }
}
