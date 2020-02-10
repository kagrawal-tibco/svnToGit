package com.tibco.be.functions.Temporal.Calculus;

import com.tibco.be.functions.Temporal.TemporalUtilFunctions;
import com.tibco.be.functions.property.PropertyHelper;
import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Temporal.Calculus",
        synopsis = "Numeric Temporal Functions")

public class CalculusFunctions {

    final static long EPOCH_TIME=0;

    @com.tibco.be.model.functions.BEFunction(
        name = "integralInt",
        synopsis = "This method returns the integral of values in the history of a property atom over a given\ntime interval [stime, etime], all the times are in (milliseconds).",
        signature = "int integralInt(PropertyAtomInt pai, long stime, long etime, int sample_rate,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_rate", type = "int", desc = "sample rate for the interval should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "returns the calculated value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the integral of values in the history of a property atom over a given\ntime interval [stime, etime]; we are dealing with discrete steps so just a summation of all the terms\neach multiplied with the interval(milliseconds) is sufficient.",
        cautions = "If the values are too large the sum might overflow, also if value did not exist through entire\ninterval an exception is thrown. 'interval' precision is in milliseconds.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int integralInt(PropertyAtomInt pai,long stime, long etime, int sample_rate,
                                  boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pai.howOld();
        if (etime==EPOCH_TIME) etime=pai.howCurrent();
        if (bound_by_stime==true && stime<pai.howOld()) stime=pai.howOld();
        TemporalUtilFunctions.validateArgs("integralInt", pai, stime, etime, sample_rate);

        int i=0; long t=stime+sample_rate;
        for (; t<=etime; t+=sample_rate) i+=PropertyHelper.getInt(pai, t)*sample_rate;
        if (t>etime) i+=PropertyHelper.getInt(pai, t)*(etime-(t-sample_rate)); // zero not a prob

        return i;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "integralLong",
        synopsis = "This method returns the integral of values in the history of a property atom over a given\ntime interval [stime, etime], all the times are in (milliseconds).",
        signature = "long integralLong(PropertyAtomLong pal, long stime, long etime, int sample_rate,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_rate", type = "int", desc = "sample rate for the interval should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "returns the calculated value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the integral of values in the history of a property atom over a given\ntime interval [stime, etime]. we are dealing with discrete steps so just a summation of all the terms each\nmultiplied with the interval(milliseconds) is sufficient.",
        cautions = "If the values are too large the sum might overflow, also if value did not exist thourgh entire\ninterval an exception is thrown. 'interval' precision is in milliseconds.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long integralLong(PropertyAtomLong pal, long stime, long etime, int sample_rate,
                                    boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pal.howOld();
        if (etime==EPOCH_TIME) etime=pal.howCurrent();
        if (bound_by_stime==true && stime<pal.howOld()) stime=pal.howOld();
        TemporalUtilFunctions.validateArgs("integralLong", pal, stime, etime, sample_rate);

        long l=0, t=stime+sample_rate;
        for (; t<=etime; t+=sample_rate) l+=PropertyHelper.getLong(pal, t)*sample_rate;
        if (t>etime) l+=PropertyHelper.getLong(pal, t)*(etime-(t-sample_rate)); // zero not a prob

        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "integralDouble",
        synopsis = "This method returns the integral of values in the history of a property atom over a given\ntime interval [stime, etime], all the times are in (milliseconds).",
        signature = "double integralDouble(PropertyAtomDouble pad, long stime, long etime, int sample_rate,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_rate", type = "int", desc = "sample rate for the interval should be more than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "returns the calculated value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the integral of values in the history of a property atom over a given\ntime interval [stime, etime]; we are dealing with discrete steps so just a summation of all the terms each\nmultiplied with the interval(milliseconds) is sufficient.",
        cautions = "If the values are too large the sum might overflow, also if value did not exist thourgh entire\ninterval an exception is thrown. 'interval' precision is in milliseconds.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double integralDouble(PropertyAtomDouble pad, long stime, long etime, int sample_rate,
                                        boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pad.howOld();
        if (etime==EPOCH_TIME) etime=pad.howCurrent();
        if (bound_by_stime==true && stime<pad.howOld()) stime=pad.howOld();
        TemporalUtilFunctions.validateArgs("integralDouble", pad, stime, etime, sample_rate);

        double d=0.0; long t=stime+sample_rate;
        for (; t<=etime; t+=sample_rate) d+=PropertyHelper.getDouble(pad, t)*sample_rate;
        if (t>etime) d+=PropertyHelper.getDouble(pad, t)*(etime-(t-sample_rate)); // zero not a prob

        return d;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "rateOfChangeInt",
        synopsis = "This method performs a least-squares fit of a straight line (Y = mX +b) and returns the\nvalue of the slope of the line (m) as the rate of change of the given PorpertyAtomInt over the time\ninterval[stime, etime].  All the times are in (milliseconds).",
        signature = "double rateOfChangeInt(PropertyAtomInt pai, long stime, long etime, int sample_rate,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to calculate the rate of change for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_rate", type = "int", desc = "sample rate for the interval should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "returns the identified value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method performs a least-squares fit of a straight line (Y = mX +b) and returns the\nvalue of the slope of the line (m) as the rate of change of the given PorpertyAtomInt over the time\ninterval[stime, etime].  All the times are in (milliseconds).",
        cautions = "If value did not exist thourgh the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double rateOfChangeInt(PropertyAtomInt pai, long stime, long etime, int sample_rate,
                                          boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pai.howOld();
        if (etime==EPOCH_TIME) etime=pai.howCurrent();
        if (bound_by_stime==true && stime<pai.howOld()) stime=pai.howOld();

        double[] values=new double[2];

        int num_values=(((int)(etime-stime))/sample_rate)+1; // little more than the interval actually

        if (num_values==2) {
            values[0]=PropertyHelper.getInt(pai, stime); values[1]=PropertyHelper.getInt(pai, etime);
            return (values[1]-values[0])/(etime-stime);
        } else {
            double a, b=0.0, certainity, sigb, t, sxoss, sx=0.0, sy=0.0, st2=0.0, ss, sigdat, chi2;
            // accumulate sums
            long idoub = stime;
            sy=0.0;
            for (int i=0; i < num_values; i++) {
              sy = sy + PropertyHelper.getInt(pai, idoub);
              sx = sx + idoub;
              idoub = idoub + sample_rate;
            }

            ss=num_values;
            sxoss=sx/ss;

            idoub = stime;
            for (int i = 0; i < num_values; i++) {
              t = idoub - sxoss;
              st2 = st2 + t * t;
              double val = PropertyHelper.getInt(pai, idoub);
              b = b + t * val;
              idoub = idoub + sample_rate;
            }
            b = b / st2;
            a = (sy - sx * b) / ss;
            sigb = Math.sqrt(1.0/st2);

            chi2 = 0.0;
            // evaluate sig using chi2 and adjust the standard deviation
            idoub = stime;
            for (int i=0; i<num_values; i++) {
              chi2 = chi2 + Math.pow((PropertyHelper.getInt(pai, idoub) - a - b*idoub), 2);
              idoub = idoub + sample_rate;
            }
            sigdat=Math.sqrt(chi2/(num_values-2));
            sigb = sigb * sigdat;

            if (sigb > 1) { sigb = 1; } // sigb part is not of any use right now
            certainity=1-sigb;          // certainity of the value calculated

            return b;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "rateOfChangeLong",
        synopsis = "This method performs a least-squares fit of a straight line (Y = mX +b) and returns the\nvalue of the slope of the line (m) as the rate of change of the given PorpertyAtomLong over the time\ninterval[stime, etime].  All the times are in (milliseconds).",
        signature = "double rateOfChangeLong(PropertyAtomLong pal, long stime, long etime, int sample_rate,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to calculate the rate of change for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_rate", type = "int", desc = "sample rate for the interval should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "returns the identified value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method performs a least-squares fit of a straight line (Y = mX +b) and returns the\nvalue of the slope of the line (m) as the rate of change of the given PorpertyAtomLong over the time\ninterval[stime, etime].  All the times are in (milliseconds).",
        cautions = "If value did not exist thourgh the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double rateOfChangeLong(PropertyAtomLong pal, long stime, long etime, int sample_rate,
                                          boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pal.howOld();
        if (etime==EPOCH_TIME) etime=pal.howCurrent();
        if (bound_by_stime==true && stime<pal.howOld()) stime=pal.howOld();

        double[] values=new double[2];

        int num_values=(((int)(etime-stime))/sample_rate)+1; // little more than the interval actually

        if (num_values==2) {
            values[0]=PropertyHelper.getLong(pal, stime); values[1]=PropertyHelper.getLong(pal, etime);
            return (values[1]-values[0])/(etime-stime);
        } else {
            double a, b=0.0, certainity, sigb, t, sxoss, sx=0.0, sy=0.0, st2=0.0, ss, sigdat, chi2;
            // accumulate sums
            long idoub = stime;
            sy=0.0;
            for (int i=0; i < num_values; i++) {
              sy = sy + PropertyHelper.getLong(pal, idoub);
              sx = sx + idoub;
              idoub = idoub + sample_rate;
            }

            ss=num_values;
            sxoss=sx/ss;

            idoub = stime;
            for (int i = 0; i < num_values; i++) {
              t = idoub - sxoss;
              st2 = st2 + t * t;
              double val = PropertyHelper.getLong(pal, idoub);
              b = b + t * val;
              idoub = idoub + sample_rate;
            }
            b = b / st2;
            a = (sy - sx * b) / ss;
            sigb = Math.sqrt(1.0/st2);

            chi2 = 0.0;
            // evaluate sig using chi2 and adjust the standard deviation
            idoub = stime;
            for (int i=0; i<num_values; i++) {
              chi2 = chi2 + Math.pow((PropertyHelper.getLong(pal, idoub) - a - b*idoub), 2);
              idoub = idoub + sample_rate;
            }
            sigdat=Math.sqrt(chi2/(num_values-2));
            sigb = sigb * sigdat;

            if (sigb > 1) { sigb = 1; } // sigb part is not of any use right now
            certainity=1-sigb;          // certainity of the value calculated

            return b;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "rateOfChangeDouble",
        synopsis = "This method performs a least-squares fit of a straight line (Y = mX +b) and returns the\nvalue of the slope of the line (m) as the rate of change of the given PorpertyAtomInt over the time\ninterval[stime, etime].  All the times are in (milliseconds).",
        signature = "double rateOfChangeDouble(PropertyAtomDouble pad, long stime, long etime, int sample_rate,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to calculate the rate of change for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_rate", type = "int", desc = "sample rate for the interval should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "returns the identified value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method performs a least-squares fit of a straight line (Y = mX +b) and returns the\nvalue of the slope of the line (m) as the rate of change of the given PorpertyAtomInt over the time\ninterval[stime, etime].  All the times are in (milliseconds).",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double  rateOfChangeDouble(PropertyAtomDouble pad, long stime, long etime, int sample_rate,
                                          boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pad.howOld();
        if (etime==EPOCH_TIME) etime=pad.howCurrent();
        if (bound_by_stime==true && stime<pad.howOld()) stime=pad.howOld();

        double[] values=new double[2];

        int num_values=(((int)(etime-stime))/sample_rate)+1; // little more than the interval actually

        if (num_values==2) {
            values[0]=PropertyHelper.getDouble(pad, stime); values[1]=PropertyHelper.getDouble(pad, etime);
            return (values[1]-values[0])/(etime-stime);
        } else {
            double a, b=0.0, certainity, sigb, t, sxoss, sx=0.0, sy=0.0, st2=0.0, ss, sigdat, chi2;
            // accumulate sums
            long idoub = stime;
            sy=0.0;
            for (int i=0; i < num_values; i++) {
              sy = sy + PropertyHelper.getDouble(pad, idoub);
              sx = sx + idoub;
              idoub = idoub + sample_rate;
            }

            ss=num_values;
            sxoss=sx/ss;

            idoub = stime;
            for (int i = 0; i < num_values; i++) {
              t = idoub - sxoss;
              st2 = st2 + t * t;
              double val = PropertyHelper.getDouble(pad, idoub);
              b = b + t * val;
              idoub = idoub + sample_rate;
            }
            b = b / st2;
            a = (sy - sx * b) / ss;
            sigb = Math.sqrt(1.0/st2);

            chi2 = 0.0;
            // evaluate sig using chi2 and adjust the standard deviation
            idoub = stime;
            for (int i=0; i<num_values; i++) {
              chi2 = chi2 + Math.pow((PropertyHelper.getDouble(pad, idoub) - a - b*idoub), 2);
              idoub = idoub + sample_rate;
            }
            sigdat=Math.sqrt(chi2/(num_values-2));
            sigb = sigb * sigdat;

            if (sigb > 1) { sigb = 1; } // sigb part is not of any use right now
            certainity=1-sigb;          // certainity of the value calculated

            return b;
        }
    }

    /* TODO:
        public static double StdDevOverTime(PropertyAtom pa, long stime, long etime, int interval) {}
    */
}
