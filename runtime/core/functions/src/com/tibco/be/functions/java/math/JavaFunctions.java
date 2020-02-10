package com.tibco.be.functions.java.math;

import java.math.BigDecimal;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

/**
 * User: apuneet
 * Date: Aug 28, 2004
 * Time: 12:26:28 AM
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Math",
        synopsis = "Utility Math Functions")

public class JavaFunctions {
    @com.tibco.be.model.functions.BEConstant(
        name = "E",
        synopsis = "The <code>double</code> value that is closer than any other\nto <i>e</i>, the base of the natural logarithms.",
        signature = "",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "The <code>double</code> value that is closer than any other to\n<i>e</i>, the base of the natural logarithms.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static final double E = 2.7182818284590452354;

    @com.tibco.be.model.functions.BEConstant(
        name = "PI",
        synopsis = "The <code>double</code> value that is closer than any other to\n<i>pi</i>, the ratio of the circumference of a circle to its diameter.",
        signature = "",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "The <code>double</code> value that is closer than any other to\n<i>pi</i>, the ratio of the circumference of a circle to its diameter.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static final double PI = 3.14159265358979323846;


    @com.tibco.be.model.functions.BEFunction(
        name = "sin",
        synopsis = "Returns the trigonometric sine of an angle.",
        signature = "double sin (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "An angle, in radians.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The sine of the argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the trigonometric sine of an angle.  Special cases:\n<br/>If the argument is NaN or an infinity, then the result is NaN.\n<br/>If the argument is zero, then the result is a zero with the\nsame sign as the argument.\n<br/>\nA result must be within 1 ulp of the correctly rounded result.  Results\nmust be semi-monotonic.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static double sin(double a) {
        return Math.sin(a);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "cos",
        synopsis = "Returns the trigonometric cosine of an angle.",
        signature = "double cos (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "An angle, in radians.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The cosine of the argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the trigonometric cosine of an angle. Special cases:\n<br/>\nIf the argument is NaN or an infinity, then the result is NaN.\n<br/>\nA result must be within 1 ulp of the correctly rounded result.  Results\nmust be semi-monotonic.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static double cos(double a) {
        return Math.cos(a); // default impl. delegates to StrictMath
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "tan",
        synopsis = "Returns the trigonometric tangent of an angle.",
        signature = "double tan (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "An angle, in radians.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The tangent of the argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the trigonometric tangent of an angle.  Special cases:\n<br/>If the argument is NaN or an infinity, then the result is NaN.\n<br/>If the argument is zero, then the result is a zero with the\nsame sign as the argument.\n<br/>\nA result must be within 1 ulp of the correctly rounded result.  Results\nmust be semi-monotonic.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static double tan(double a) {
      return Math.tan(a); // default impl. delegates to Math
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "asin",
        synopsis = "Returns the arc sine of an angle.",
        signature = "double asin (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "The value whose arc sine is to be returned.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The arc sine of the argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the arc sine of an angle, in the range of -pi/2 through\npi/2. Special cases:\n<br/>\nIf the argument is NaN or its absolute value is greater\nthan 1, then the result is NaN.\n<br/>\nIf the argument is zero, then the result is a zero with the\nsame sign as the argument.\n<br/>\nA result must be within 1 ulp of the correctly rounded result.  Results\nmust be semi-monotonic.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static double asin(double a) {
      return Math.asin(a); // default impl. delegates to Math
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "acos",
        synopsis = "Returns the arc cosine of an angle.",
        signature = "double acos (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "The value whose arc cosine is to be returned.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The arc cosine of the argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the arc cosine of an angle, in the range of 0.0 through\npi.  Special case:\n<br/>\nIf the argument is NaN or its absolute value is greater\nthan 1, then the result is NaN.\n<br/>\nA result must be within 1 ulp of the correctly rounded result.  Results\nmust be semi-monotonic.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static double acos(double a) {
      return Math.acos(a); // default impl. delegates to Math
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "atan",
        synopsis = "Returns the arc tangent of an angle.",
        signature = "double atan (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "The value whose arc tangent is to be returned.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The arc tangent of the argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the arc tangent of an angle, in the range of -pi/2\nthrough pi/ 2.  Special cases:\n<br/>\nIf the argument is NaN, then the result is NaN.\n<br/>\nIf the argument is zero, then the result is a zero with the\nsame sign as the argument.\n<br/>\nA result must be within 1 ulp of the correctly rounded result.  Results\nmust be semi-monotonic.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static double atan(double a) {
      return Math.atan(a); // default impl. delegates to Math
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "toRadians",
        synopsis = "Converts an angle measured in degrees to an angle measured in radians.",
        signature = "double toRadians (double angdeg)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "angdeg", type = "double", desc = "An angle, in degrees.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The measurement of the angle <code>angdeg</code> in radians."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Converts an angle measured in degrees to an approximately\nequivalent angle measured in radians.  The conversion from\ndegrees to radians is generally inexact.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double toRadians(double angdeg) {
      return angdeg / 180.0 * PI;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "toDegrees",
        synopsis = "Converts an angle measured in radians to an angle measured in degrees.",
        signature = "double toDegrees (double angrad)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "angrad", type = "double", desc = "An angle, in radians.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The measurement of the angle <code>angrad</code> in degrees."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Converts an angle measured in radians to an approximately\nequivalent angle measured in degrees.  The conversion from\nradians to degrees is generally inexact; users should\n<i>not</i> expect <code>cos(toRadians(90.0))</code> to exactly\nequal <code>0.0</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double toDegrees(double angrad) {
      return angrad * 180.0 / PI;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "exp",
        synopsis = "Returns Euler's number <i>e</i> raised to the power of a <code>double</code> value.",
        signature = "double exp (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "The exponent to raise <i>e</i> to.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The value <i>e</i><sup><code>a</code></sup>,\nwhere <i>e</i> is the base of the natural logarithms."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns Euler's number <i>e</i> raised to the power of a\n<code>double</code> value.  Special cases:\n<br/>\nIf the argument is NaN, the result is NaN.\n<br/>\nIf the argument is positive infinity, then the result is\npositive infinity.\n<br/>\nIf the argument is negative infinity, then the result is\npositive zero.\n<br/>\nA result must be within 1 ulp of the correctly rounded result.  Results\nmust be semi-monotonic.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double exp(double a) {
      return Math.exp(a); // default impl. delegates to Math
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "log",
        synopsis = "Returns the natural logarithm (base <i>e</i>) of a <code>double</code> value.",
        signature = "double log (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "A number greater than <code>0.0</code>.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The value ln&nbsp;<code>a</code>, the natural logarithm of <code>a</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the natural logarithm (base <i>e</i>) of a <code>double</code>\nvalue.  Special cases:\n<br/>\nIf the argument is NaN or less than zero, then the result is NaN.\n<br/>\nIf the argument is positive infinity, then the result is\npositive infinity.\n<br/>\nIf the argument is positive zero or negative zero, then the\nresult is negative infinity.\n<br/>\nA result must be within 1 ulp of the correctly rounded result.  Results\nmust be semi-monotonic.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double log(double a) {
      return Math.log(a); // default impl. delegates to Math
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "sqrt",
        synopsis = "Returns the correctly rounded positive square root of a <code>double</code> value.",
        signature = "double sqrt (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "A value.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The positive square root of <code>a</code>.\nIf the argument is NaN or less than zero, the result is NaN."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the correctly rounded positive square root of a\n<code>double</code> value.\nSpecial cases:\n<br/>If the argument is NaN or less than zero, then the result is NaN.\n<br/>If the argument is positive infinity, then the result is positive infinity.\n<br/>If the argument is positive zero or negative zero, then the\nresult is the same as the argument.\n<br/>\nOtherwise, the result is the <code>double</code> value closest to\nthe true mathematical square root of the argument value.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double sqrt(double a) {
      return Math.sqrt(a);
      // default impl. delegates to Math
      // Note that hardware sqrt instructions frequently can be directly used by JITs
      // and should be much faster than doing Math.sqrt in software.
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "IEEEremainder",
        synopsis = "Computes the remainder operation on two arguments as prescribed\nby the IEEE 754 standard.",
        signature = "double IEEEremainder (double f1, double f2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "f1", type = "double", desc = "The dividend."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "f2", type = "double", desc = "The divisor.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The remainder when <code>f1</code> is divided by <code>f2</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Computes the remainder operation on two arguments as prescribed\nby the IEEE 754 standard.\nThe remainder value is mathematically equal to\nf1 - f2 X n, \nwhere n is the mathematical integer closest to the exact\nmathematical value of the quotient f1/f2, and if two\nmathematical integers are equally close to f1/f2,\nthen n is the integer that is even. If the remainder is\nzero, its sign is the same as the sign of the first argument.<br/>\nSpecial cases:\n<br/>\nIf either argument is NaN, or the first argument is infinite,\nor the second argument is positive zero or negative zero, then the\nresult is NaN.\n<br/>\nIf the first argument is finite and the second argument is\ninfinite, then the result is the same as the first argument.</ul>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static double IEEEremainder(double f1, double f2) {
        return Math.IEEEremainder(f1, f2); // delegate to Math
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "ceil",
        synopsis = "Returns the smallest <code>double</code> value that is greater\nthan or equal to the argument and is equal to an integer.",
        signature = "double ceil (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "A value.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The smallest (closest to negative infinity)\nfloating-point value that is not less than the argument\nand is equal to a mathematical integer."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the smallest (closest to negative infinity)\n<code>double</code> value that is not less than the argument and is\nequal to a mathematical integer. Special cases:\n<br/>\nIf the argument value is already equal to a mathematical\ninteger, then the result is the same as the argument.\n<br/>\nIf the argument is NaN or an infinity or positive zero or negative\nzero, then the result is the same as the argument.\n<br/>\nIf the argument value is less than zero but greater than -1.0,\nthen the result is negative zero.\n<br/>\nNote that the value of <code>Math.ceil(x)</code> is exactly the\nvalue of <code>-Math.floor(-x)</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double ceil(double a) {
      return Math.ceil(a); // default impl. delegates to Math
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "floor",
        synopsis = "Returns the largest <code>double</code> value that is less\nthan or equal to the argument and is equal to an integer.",
        signature = "double floor (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "A value.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The largest (closest to positive infinity)\nfloating-point value that is not greater than the argument\nand is equal to a mathematical integer."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the largest (closest to positive infinity)\n<code>double</code> value that is not greater than the argument and\nis equal to a mathematical integer. Special cases:\n<br/>\nIf the argument value is already equal to a mathematical\ninteger, then the result is the same as the argument.\n<br/>\nIf the argument is NaN or an infinity or positive zero or\nnegative zero, then the result is the same as the argument.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double floor(double a) {
      return Math.floor(a); // default impl. delegates to Math
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "rint",
        synopsis = "Returns the <code>double</code> value that is closest in value\nto the argument and is equal to an integer.",
        signature = "double rint (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "A <code>double</code> value.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The closest floating-point value to <code>a</code> that is\nequal to a mathematical integer."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <code>double</code> value that is closest in value\nto the argument and is equal to a mathematical integer. If two\n<code>double</code> values that are mathematical integers are\nequally close, the result is the integer value that is\neven. Special cases:\n<br/>\nIf the argument value is already equal to a mathematical\ninteger, then the result is the same as the argument.\n<br/>If the argument is NaN or an infinity or positive zero or negative\nzero, then the result is the same as the argument.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static double rint(double a) {
      return Math.rint(a); // default impl. delegates to Math
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "atanRectangularToPolar",
        synopsis = "Converts rectangular coordinates (x, y) to polar (r, theta).",
        signature = "double atanRectangularToPolar (double y, double x)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "y", type = "double", desc = "The ordinate coordinate."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "x", type = "double", desc = "The abscissa coordinate.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The <i>theta</i> component of the point (r, theta)\nin polar coordinates that corresponds to the point (x,y) in\nCartesian coordinates."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Converts rectangular coordinates (x, y)\nto polar(r, theta).\nThis method computes the phase <i>theta</i> by computing an arc tangent\nof <code>y/x</code> in the range of -<i>pi</i> to <i>pi</i>. Special\ncases:\n<br/>\nIf either argument is NaN, then the result is NaN.\n<br/>\nIf the first argument is positive zero and the second argument\nis positive, or the first argument is positive and finite and the\nsecond argument is positive infinity, then the result is positive zero.\n<br/>\nIf the first argument is negative zero and the second argument\nis positive, or the first argument is negative and finite and the\nsecond argument is positive infinity, then the result is negative zero.\n<br/>\nIf the first argument is positive zero and the second argument\nis negative, or the first argument is positive and finite and the\nsecond argument is negative infinity, then the result is the\n<code>double</code> value closest to <i>pi</i>.\n<br/>\nIf the first argument is negative zero and the second argument\nis negative, or the first argument is negative and finite and the\nsecond argument is negative infinity, then the result is the\n<code>double</code> value closest to -<i>pi</i>.\n<br/>\nIf the first argument is positive and the second argument is\npositive zero or negative zero, or the first argument is positive\ninfinity and the second argument is finite, then the result is the\n<code>double</code> value closest to <i>pi</i>/2.\n<br/>\nIf the first argument is negative and the second argument is\npositive zero or negative zero, or the first argument is negative\ninfinity and the second argument is finite, then the result is the\n<code>double</code> value closest to -<i>pi</i>/2.\n<br/>\nIf both arguments are positive infinity, then the result is the\n<code>double</code> value closest to <i>pi</i>/4.\n<br/>\nIf the first argument is positive infinity and the second argument\nis negative infinity, then the result is the <code>double</code>\nvalue closest to 3*<i>pi</i>/4.\n<br/>\nIf the first argument is negative infinity and the second argument\nis positive infinity, then the result is the <code>double</code> value\nclosest to -<i>pi</i>/4.\n<br/>\nIf both arguments are negative infinity, then the result is the\n<code>double</code> value closest to -3*<i>pi</i>/4.</ul>\n<br/>\nA result must be within 2 ulps of the correctly rounded result.  Results\nmust be semi-monotonic.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static double atanRectangularToPolar(double y, double x) {
    return Math.atan2(y, x); // default impl. delegates to Math
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "pow",
        synopsis = "Returns the value of the first argument raised to the power of the second argument.",
        signature = "double pow (double a, double b)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "The base."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "b", type = "double", desc = "The exponent.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The value <code>a<sup>b</sup></code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the value of the first argument raised to the power of the\nsecond argument. Special cases:\n<br/>If the second argument is positive or negative zero, then the\nresult is 1.0.\n<br/>If the second argument is 1.0, then the result is the same as the\nfirst argument.\n<br/>If the second argument is NaN, then the result is NaN.\n<br/>If the first argument is NaN and the second argument is nonzero,\nthen the result is NaN.\n<br/>If\n<br/> - the absolute value of the first argument is greater than 1\nand the second argument is positive infinity, or\n<br/> - the absolute value of the first argument is less than 1 and\nthe second argument is negative infinity,\n<br/>\nthen the result is positive infinity.\n<br/>If\n<br/> - the absolute value of the first argument is greater than 1 and\nthe second argument is negative infinity, or\n<br/> - the absolute value of the\nfirst argument is less than 1 and the second argument is positive\ninfinity,\n<br/>\nthen the result is positive zero.\n<br/>If the absolute value of the first argument equals 1 and the\nsecond argument is infinite, then the result is NaN.\n<br/>If\n<br/> - the first argument is positive zero and the second argument\nis greater than zero, or\n<br/> - the first argument is positive infinity and the second\nargument is less than zero,\n<br/>\nthen the result is positive zero.\n<br/>If\n<br/> - the first argument is positive zero and the second argument\nis less than zero, or\n<br/> - the first argument is positive infinity and the second\nargument is greater than zero,\n<br/>\nthen the result is positive infinity.\n<br/>If\n<br/> - the first argument is negative zero and the second argument\nis greater than zero but not a finite odd integer, or\n<br/> - the first argument is negative infinity and the second\nargument is less than zero but not a finite odd integer,\n<br/>\nthen the result is positive zero.\n<br/>If\n<br/> - the first argument is negative zero and the second argument\nis a positive finite odd integer, or\n<br/> - the first argument is negative infinity and the second\nargument is a negative finite odd integer,\n<br/>\nthen the result is negative zero.\n<br/>If\n<br/> - the first argument is negative zero and the second argument\nis less than zero but not a finite odd integer, or\n<br/> - the first argument is negative infinity and the second\nargument is greater than zero but not a finite odd integer,\n<br/>\nthen the result is positive infinity.\n<br/>If\n<br/> - the first argument is negative zero and the second argument\nis a negative finite odd integer, or\n<br/> - the first argument is negative infinity and the second\nargument is a positive finite odd integer,\n<br/>\nthen the result is negative infinity.\n<br/>If the first argument is finite and less than zero\n<br/> if the second argument is a finite even integer, the\nresult is equal to the result of raising the absolute value of\nthe first argument to the power of the second argument\n<br/>if the second argument is a finite odd integer, the result\nis equal to the negative of the result of raising the absolute\nvalue of the first argument to the power of the second\nargument\n<br/>if the second argument is finite and not an integer, then\nthe result is NaN.\n<br/>If both arguments are integers, then the result is exactly equal\nto the mathematical result of raising the first argument to the power\nof the second argument if that result can in fact be represented\nexactly as a <code>double</code> value.\n<br/>\n(In the foregoing descriptions, a floating-point value is\nconsidered to be an integer if and only if it is finite and a\nfixed point of the method <tt>ceil</tt> or,\nequivalently, a fixed point of the method <tt>floor</tt>. A value is a fixed point of a one-argument\nmethod if and only if the result of applying the method to the\nvalue is equal to the value.)\n<br/>\nA result must be within 1 ulp of the correctly rounded\nresult.  Results must be semi-monotonic.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double pow(double a, double b) {
      return Math.pow(a, b); // default impl. delegates to Math
    }


//    /** @.name round
//     * @.synopsis Returns the closest <code>int</code> to the argument.
//     * @.signature int round (float a)
//     * @param a double A floating-point value to be rounded to an integer.
//     * @return int The value of the argument rounded to the nearest <code>int</code> value.
//     * @.version 1.0
//     * @.see java.lang.Integer#MAX_VALUE
//     * @.see java.lang.Integer#MIN_VALUE
//     * @.mapper false
//     * @.description
//     * Returns the closest <code>int</code> to the argument. The
//     * result is rounded to an integer by adding 1/2, taking the
//     * floor of the result, and casting the result to type <code>int</code>.
//     * In other words, the result is equal to the value of the expression:
//     * <p><pre>(int)Math.floor(a + 0.5f)</pre>
//     * Special cases:
//     * <ul><li>If the argument is NaN, the result is 0.
//     * <li>If the argument is negative infinity or any value less than or
//     * equal to the value of <code>Integer.MIN_VALUE</code>, the result is
//     * equal to the value of <code>Integer.MIN_VALUE</code>.
//     * <li>If the argument is positive infinity or any value greater than or
//     * equal to the value of <code>Integer.MAX_VALUE</code>, the result is
//     * equal to the value of <code>Integer.MAX_VALUE</code>.</ul>
//     * @.cautions none
//     * @.domain action, condition, query
//     * @.example
//     * <code>String  result = String.valueOfInteger (Math.round (2.4);</code><br />
//     * Result is: result contains: "2".
//     */
//    public static int round(float a) {
//      return (int)floor(a + 0.5f);
//    }


    @com.tibco.be.model.functions.BEFunction(
        name = "round",
        synopsis = "Returns the closest <code>long</code> to the argument.",
        signature = "long round (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "A floating-point value to be rounded to a <code>long</code>.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The value of the argument rounded to the nearest <code>long</code> value."),
        version = "1.0",
        see = "java.lang.Long#MIN_VALUE",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the closest <code>long</code> to the argument. The result\nis rounded to an integer by adding 1/2, taking the floor of the\nresult, and casting the result to type <code>long</code>. In other\nwords, the result is equal to the value of the expression:\n<p><pre>(long)Math.floor(a + 0.5d)</pre>\nSpecial cases:\n<br/>If the argument is NaN, the result is 0.\n<br/>If the argument is negative infinity or any value less than or\nequal to the value of <code>Long.MIN_VALUE</code>, the result is\nequal to the value of <code>Long.MIN_VALUE</code>.\n<br/>If the argument is positive infinity or any value greater than or\nequal to the value of <code>Long.MAX_VALUE</code>, the result is\nequal to the value of <code>Long.MAX_VALUE</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long round(double a) {
      return (long)floor(a + 0.5d);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "random",
        synopsis = "Returns a random <code>double</code> value between <code>0.0</code> and <code>1.0</code>.",
        signature = "double random ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "A pseudorandom <code>double</code> greater than or equal\nto <code>0.0</code> and less than <code>1.0</code>."),
        version = "1.0",
        see = "java.util.Random#nextDouble()",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a <code>double</code> value with a positive sign, greater\nthan or equal to <code>0.0</code> and less than <code>1.0</code>.\nReturned values are chosen pseudorandomly with (approximately)\nuniform distribution from that range.\nWhen this method is first called, it creates a single new\npseudorandom-number generator, exactly as if by the expression\n<blockquote><pre>new java.util.Random</pre></blockquote>\nThis new pseudorandom-number generator is used thereafter for all\ncalls to this method and is used nowhere else.\nThis method is properly synchronized to allow correct use by more\nthan one thread. However, if many threads need to generate\npseudorandom numbers at a great rate, it may reduce contention for\neach thread to have its own pseudorandom-number generator.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double random() {
        return Math.random();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "absInt",
        synopsis = "Returns the absolute value of an <code>int</code> value.",
        signature = "int absInt (int a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "int", desc = "The argument whose absolute value is to be determined.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The absolute value of the argument."),
        version = "1.0",
        see = "java.lang.Integer#MIN_VALUE",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the absolute value of an int value.\nIf the argument is not negative, the argument is returned.\nIf the argument is negative, the negation of the argument is returned.\nNote that if the argument is equal to the value of\nInteger.MIN_VALUE, the most negative representable\nint value, the result is that same value, which is\nnegative.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int absInt(int a) {
      return (a < 0) ? -a : a;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "absLong",
        synopsis = "Returns the absolute value of a <code>long</code> value.",
        signature = "long absLong (long a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "long", desc = "The argument whose absolute value is to be determined.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The absolute value of the argument."),
        version = "1.0",
        see = "java.lang.Long#MIN_VALUE",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the absolute value of a long value.\nIf the argument is not negative, the argument is returned.\nIf the argument is negative, the negation of the argument is returned.\nNote that if the argument is equal to the value of\nLong.MIN_VALUE, the most negative representable\nlong value, the result is that same value, which is\nnegative.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long absLong(long a) {
      return (a < 0) ? -a : a;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "absFloat",
        synopsis = "Returns the absolute value of a <code>float</code> value.",
        enabled = @Enabled(value=false),
        signature = "float absFloat (float a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "float", desc = "The argument whose absolute value is to be determined.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "float", desc = "The absolute value of the argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the absolute value of a <code>float</code> value.\nIf the argument is not negative, the argument is returned.\nIf the argument is negative, the negation of the argument is returned.\nSpecial cases:\n<ul><li>If the argument is positive zero or negative zero, the\nresult is positive zero.\n<li>If the argument is infinite, the result is positive infinity.\n<li>If the argument is NaN, the result is NaN.</ul>\nIn other words, the result is the same as the value of the expression:\n<p><pre>Float.intBitsToFloat(0x7fffffff & Float.floatToIntBits(a))</pre>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static float absFloat(float a) {
        return (a <= 0.0F) ? 0.0F - a : a;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "absDouble",
        synopsis = "Returns the absolute value of a <code>double</code> value.",
        signature = "double absDouble (double a)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "The argument whose absolute value is to be determined.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The absolute value of the argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the absolute value of a double value.\nIf the argument is not negative, the argument is returned.\nIf the argument is negative, the negation of the argument is returned. <br/>\nSpecial cases:\n<br/>\nIf the argument is positive zero or negative zero, the result\nis positive zero.\n<br/>\nIf the argument is infinite, the result is positive infinity.\n<br/>\nIf the argument is NaN, the result is NaN.\n<br/>\nIn other words, the result is the same as the value of the expression:\nDouble.longBitsToDouble((Double.doubleToLongBits(a)&lt;&lt;1)&gt;&gt;&gt;1)",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double absDouble(double a) {
        return (a <= 0.0D) ? 0.0D - a : a;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "maxInt",
        synopsis = "Returns the greater of two <code>int</code> values.",
        signature = "int maxInt (int a, int b)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "int", desc = "An argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "b", type = "int", desc = "Another argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The larger of <code>a</code> and <code>b</code>."),
        version = "1.0",
        see = "java.lang.Long#MAX_VALUE",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the greater of two <code>int</code> values. That is, the\nresult is the argument closer to the value of\n<code>Integer.MAX_VALUE</code>. If the arguments have the same value,\nthe result is that same value.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int maxInt(int a, int b) {
      return (a >= b) ? a : b;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "maxLong",
        synopsis = "Returns the greater of two <code>long</code> values.",
        signature = "long maxLong (long a, long b)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "long", desc = "An argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "b", type = "long", desc = "Another argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The larger of <code>a</code> and <code>b</code>."),
        version = "1.0",
        see = "java.lang.Long#MAX_VALUE",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the greater of two <code>long</code> values. That is, the\nresult is the argument closer to the value of\n<code>Long.MAX_VALUE</code>. If the arguments have the same value,\nthe result is that same value.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long maxLong(long a, long b) {
      return (a >= b) ? a : b;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "maxFloat",
        synopsis = "Returns the greater of two <code>float</code> values.",
        enabled = @Enabled(value=false),
        signature = "float maxFloat (float a, float b)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "float", desc = "An argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "b", type = "float", desc = "Another argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "float", desc = "The larger of <code>a</code> and <code>b</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the greater of two <code>float</code> values.  That is,\nthe result is the argument closer to positive infinity. If the\narguments have the same value, the result is that same\nvalue. If either value is NaN, then the result is NaN.  Unlike\nthe the numerical comparison operators, this method considers\nnegative zero to be strictly smaller than positive zero. If one\nargument is positive zero and the other negative zero, the\nresult is positive zero.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static float maxFloat(float a, float b) {
        return Math.max(a,b);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "maxDouble",
        synopsis = "Returns the greater of two <code>double</code> values.",
        signature = "double maxDouble (double a, double b)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "An argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "b", type = "double", desc = "Another argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The larger of <code>a</code> and <code>b</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the greater of two <code>double</code> values.  That\nis, the result is the argument closer to positive infinity. If\nthe arguments have the same value, the result is that same\nvalue. If either value is NaN, then the result is NaN.  Unlike\nthe the numerical comparison operators, this method considers\nnegative zero to be strictly smaller than positive zero. If one\nargument is positive zero and the other negative zero, the\nresult is positive zero.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double maxDouble(double a, double b) {
        return Math.max(a,b);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "minInt",
        synopsis = "Returns the smaller of two <code>int</code> values.",
        signature = "int minInt (int a, int b)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "int", desc = "An argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "b", type = "int", desc = "Another argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The smaller of <code>a</code> and <code>b</code>."),
        version = "1.0",
        see = "java.lang.Long#MIN_VALUE",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the smaller of two <code>int</code> values. That is,\nthe result the argument closer to the value of\n<code>Integer.MIN_VALUE</code>.  If the arguments have the same\nvalue, the result is that same value.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int minInt(int a, int b) {
      return (a <= b) ? a : b;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "minLong",
        synopsis = "Returns the smaller of two <code>long</code> values.",
        signature = "long minLong (long a, long b)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "long", desc = "An argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "b", type = "long", desc = "Another argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The smaller of <code>a</code> and <code>b</code>."),
        version = "1.0",
        see = "java.lang.Long#MIN_VALUE",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the smaller of two <code>long</code> values. That is,\nthe result is the argument closer to the value of\n<code>Long.MIN_VALUE</code>. If the arguments have the same\nvalue, the result is that same value.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long minLong(long a, long b) {
      return (a <= b) ? a : b;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "minFloat",
        synopsis = "Returns the smaller of two <code>float</code> values.",
        enabled = @Enabled(value=false),
        signature = "float minFloat (float a, float b)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "float", desc = "An argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "b", type = "float", desc = "Another argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "float", desc = "The smaller of <code>a</code> and <code>b.</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the smaller of two <code>float</code> values.  That is,\nthe result is the value closer to negative infinity. If the\narguments have the same value, the result is that same\nvalue. If either value is NaN, then the result is NaN.  Unlike\nthe the numerical comparison operators, this method considers\nnegative zero to be strictly smaller than positive zero.  If\none argument is positive zero and the other is negative zero,\nthe result is negative zero.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static float minFloat(float a, float b) {
        return Math.min(a,b);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "minDouble",
        synopsis = "Returns the smaller of two <code>double</code> values.",
        signature = "double minDouble (double a, double b)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "a", type = "double", desc = "An argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "b", type = "double", desc = "Another argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The smaller of <code>a</code> and <code>b</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the smaller of two <code>double</code> values.  That\nis, the result is the value closer to negative infinity. If the\narguments have the same value, the result is that same\nvalue. If either value is NaN, then the result is NaN.  Unlike\nthe the numerical comparison operators, this method considers\nnegative zero to be strictly smaller than positive zero. If one\nargument is positive zero and the other is negative zero, the\nresult is negative zero.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double minDouble(double a, double b) {
        return Math.min(a,b);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "roundFraction",
        synopsis = "Returns a number rounded to integer places right of the decimal point.",
        signature = "double roundFraction (double d1, int precision)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d1", type = "double", desc = "The number to round."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "precision", type = "int", desc = "Length of precision.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Rounding the number <code>d1</code> to the precision of <code>precision</code> places.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
     public static double roundFraction(double d1, int precision) {
        BigDecimal roundfinalPrice = new BigDecimal(d1).setScale(precision,BigDecimal.ROUND_HALF_UP);
        return roundfinalPrice.doubleValue();
     }


}
