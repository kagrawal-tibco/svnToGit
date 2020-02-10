package com.tibco.be.functions.java.integer;

import static com.tibco.be.model.functions.FunctionDomain.*;

/**
 * User: apuneet
 * Date: Aug 24, 2004
 * Time: 3:13:02 PM
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Number",
        synopsis = "Utility Functions to Operate on Integer, Long Properties")

public class JavaFunctions {



    @com.tibco.be.model.functions.BEFunction(
        name = "longValue",
        synopsis = "Returns the value of long in the String passed based on the passed radix (number base).",
        signature = "long longValue (String s, int radix)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s", type = "String", desc = "A String containing a long (in the radix specified) to be converted to an long."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "radix", type = "int", desc = "The radix to use when converting the integer in the String <code>s</code> to an long.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The value of the long in the String passed based on the passed radix (number base)."),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an long holding the value extracted from the specified String when\nparsed with the radix given by the second argument. The first argument is\ninterpreted as representing a signed long in the radix specified by the\nsecond argument. The result is a long object that represents the long\nvalue specified by the string.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long longValue (String s, int radix) {
        return Long.valueOf(s, radix).longValue();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "doubleValue",
        synopsis = "Returns the value of double in the String passed .",
        signature = "double doubleValue (String s)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s", type = "String", desc = "A String with a double format.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The value of the double in the String passed."),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an double holding the value extracted from the specified String.\nThe first argument is interpreted as representing a signed double.\nThe result is a double that represents the double value specified by the string.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double doubleValue(String s) {
        return Double.parseDouble(s);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "intValue",
        synopsis = "Returns the value of the integer in the String passed based on the passed radix (number base).",
        signature = "int intValue (String s, int radix)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s", type = "String", desc = "A String containing an integer (in the radix specified) to be converted to an int."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "radix", type = "int", desc = "The radix to use when converting the integer in the String <code>s</code> to an int.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The value of the integer in the String passed based on the passed radix (number base)."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an int holding the value extracted from the specified String when\nparsed with the radix given by the second argument. The first argument is\ninterpreted as representing a signed integer in the radix specified by the\nsecond argument. The result is an Integer object that represents the integer\nvalue specified by the string.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int intValue (String s, int radix) {
        return Integer.valueOf(s, radix).intValue();
    }





    @com.tibco.be.model.functions.BEFunction(
        name = "valueOfString",
        synopsis = "Returns the value of the integer in the String passed based on the passed radix (number base).",
        signature = "int valueOfString (String s, int radix)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s", type = "String", desc = "A String containing an integer (in the radix specified) to be converted to an int."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "radix", type = "int", desc = "The radix to use when converting the integer in the String <code>s</code> to an int.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The value of the integer in the String passed based on the passed radix (number base)."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an int holding the value extracted from the specified String when\nparsed with the radix given by the second argument. The first argument is\ninterpreted as representing a signed integer in the radix specified by the\nsecond argument. The result is an Integer object that represents the integer\nvalue specified by the string.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int valueOfString (String s, int radix) {
        return Integer.valueOf(s, radix).intValue();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "valueOfFloat",
        synopsis = "Returns the value of the float passed in (<code>f</code>) as an int.",
        signature = "int valueOfFloat (float f)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "f", type = "float", desc = "A float to convert to an int.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The value of the float passed in as an int."),
        version = "1.0",
        see = "java.lang.Float",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the value of the float passed in (<code>f</code>) as an int.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static int valueOfFloat (float f) {
        return (int) f;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "valueOfLong",
        synopsis = "Returns the value of the long passed in (<code>l</code>) as an int.",
        signature = "int valueOfLong (long l)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "l", type = "long", desc = "A long that will be returned as an int.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The value of the long passed in (<code>l</code>) as an int."),
        version = "1.0",
        see = "java.lang.Long",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the value of the long passed in (</code>l</code>) as an int.\nThe high order bits are simply truncated which can result in sign reversal.",
        cautions = "The high order bits are simply truncated which can result in sign reversal.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int valueOfLong (long l) {
        return (int) l;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "MIN_VALUE",
        synopsis = "Returns the minimum value an int can have, -2^31.",
        signature = "int MIN_VALUE ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The minimum value an int can have, -2^31."),
        version = "1.0",
        see = "java.lang.Integer#MIN_VALUE",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the minimum value an int can have, -2^31.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int MIN_VALUE () {
        return Integer.MIN_VALUE;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "MAX_VALUE",
        synopsis = "Returns the maximum value an int can have, 2^31-1.",
        signature = "int MAX_VALUE ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The maximum value an int can have, 2^31-1."),
        version = "1.0",
        see = "java.lang.Integer#MAX_VALUE",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the maximum value an int can have, 2^31-1.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int MAX_VALUE () {
        return Integer.MAX_VALUE;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "toHexString",
        synopsis = "Returns a String containing the hexadecimal representation of the int passed.",
        signature = "String toHexString (int i)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "i", type = "int", desc = "An int to convert to a hexadecimal String.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A String containing the hexadecimal representation of the int passed."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return a String containing the hexadecimal representation of the int passed.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String toHexString (int i) {
        return Integer.toHexString(i);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "toBinaryString",
        synopsis = "Returns a String containing the binary representation of the int passed.",
        signature = "String toBinaryString (int i)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "i", type = "int", desc = "An int to convert to a binary String.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A String containing the binary representation of the int passed."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return a String containing the binary representation of the int passed.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String toBinaryString (int i) {
        return Integer.toBinaryString(i);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "toOctalString",
        synopsis = "Returns a String containing the octal representation of the int passed.",
        signature = "String toOctalString (int i)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "i", type = "int", desc = "An int to convert to an octal String.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A String containing the octal representation of the int passed."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return a String containing the octal representation of the int passed.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String toOctalString (int i) {
        return Integer.toOctalString(i);
    }
}
