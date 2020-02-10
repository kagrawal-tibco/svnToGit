package com.tibco.be.functions.Temporal.Statistic;

import com.tibco.be.functions.Temporal.TemporalUtilFunctions;
import com.tibco.be.functions.property.PropertyHelper;
import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomDoubleSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomIntSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomLongSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomSimple;

import java.util.Iterator;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Temporal.Statistic",
        synopsis = "Numeric Temporal Functions")

public class StatisticFunctions {

    final static long EPOCH_TIME=0;

     @com.tibco.be.model.functions.BEFunction(
        name = "avgOverTimeInt",
        synopsis = "This method calculates the average of samples of the property over a given time interval[stime, etime],\nall the times are in (milliseconds).",
        signature = "double avgOverTimeInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to average the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "average of all the samples taken."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method calculates the average of samples of the property over a given time interval[stime, etime].",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double avgOverTimeInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,
                                        boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pai.howOld();
        if (etime==EPOCH_TIME) etime=pai.howCurrent();
        if (bound_by_stime && stime<pai.howOld()) stime=pai.howOld();
        TemporalUtilFunctions.validateArgs("avgOverTimeInt", pai, stime, etime, sample_interval);
        long numSamples = numSumSamples(pai, stime, etime, sample_interval, bound_by_stime);
        if (numSamples < 1)
            throw new IllegalArgumentException("avgOverTimeInt : Not enough history available for sampling");
        else {
            return sumInt(pai, stime, etime, sample_interval, bound_by_stime) / (double) numSamples;
        }
    }

     @com.tibco.be.model.functions.BEFunction(
        name = "avgOverTimeLong",
        synopsis = "This method calculates the average of samples of the property over a given time interval[stime, etime],\nall the times are in (milliseconds).",
        signature = "double avgOverTimeLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to average the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "average of all the samples taken."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method calculates the average of samples of the property over a given time interval[stime, etime].",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
     public static double avgOverTimeLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,
                                          boolean bound_by_stime) {
         if (stime==EPOCH_TIME) stime=pal.howOld();
         if (etime==EPOCH_TIME) etime=pal.howCurrent();
         if (bound_by_stime && stime<pal.howOld()) stime=pal.howOld();
         TemporalUtilFunctions.validateArgs("avgOverTimeLong", pal, stime, etime, sample_interval);
         long numSamples = numSumSamples(pal, stime, etime, sample_interval, bound_by_stime);
         if (numSamples < 1)
             throw new IllegalArgumentException("avgOverTimeLong : Not enough history available for sampling");
         else {
             return sumLong(pal, stime, etime, sample_interval, bound_by_stime) / (double) numSamples;
         }
    }

     @com.tibco.be.model.functions.BEFunction(
        name = "avgOverTimeDouble",
        synopsis = "This method calculates the average of samples of the property over a given time interval[stime, etime],\nall the times are in (milliseconds).",
        signature = "double avgOverTimeDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to average the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "average of all the samples taken."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method calculates the average of samples of the property over a given time interval[stime, etime].",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
     public static double avgOverTimeDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,
                                            boolean bound_by_stime) {
         if (stime==EPOCH_TIME) stime=pad.howOld();
         if (etime==EPOCH_TIME) etime=pad.howCurrent();
         if (bound_by_stime && stime<pad.howOld()) stime=pad.howOld();
         TemporalUtilFunctions.validateArgs("avgOverTimeDouble", pad, stime, etime, sample_interval);
         long numSamples = numSumSamples(pad, stime, etime, sample_interval, bound_by_stime);
         if (numSamples < 1)
             throw new IllegalArgumentException("avgOverTimeDouble : Not enough history available for sampling");
         else {
             return sumDouble(pad, stime, etime, sample_interval, bound_by_stime) / numSamples;
         }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "sumInt",
        synopsis = "This method returns the sum of samples of the history of a property atom over a given\ntime interval [stime, etime], all the times are in (milliseconds).",
        signature = "int sumInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "sum of all the samples taken."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the sum of samples of the history of a property atom over a given\ntime interval [stime, etime].",
        cautions = "If the values are too large the sum might overflow, also if value did not exist through entire\ninterval an exception is thrown. 'interval' precision is in milliseconds.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int sumInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,
                             boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pai.howOld();
        if (etime==EPOCH_TIME) etime=pai.howCurrent();
        if (bound_by_stime && stime<pai.howOld()) stime=pai.howOld();
        TemporalUtilFunctions.validateArgs("sumInt", pai, stime, etime, sample_interval);

        int i=0; long t=0;
        for (t=stime; t<=etime; t+=sample_interval) i+=PropertyHelper.getInt(pai, t);
        if(t<etime) i+=PropertyHelper.getInt(pai, etime);

        return i;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "sumLong",
        synopsis = "This method returns the sum of samples of the history of a property atom over a given\ntime interval [stime, etime] all the times are in (milliseconds).",
        signature = "long sumLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "sum of all the samples taken."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the sum of samples of the history of a property atom over a given\ntime interval [stime, etime].",
        cautions = "If the values are too large the sum might overflow, also if value did not exist through entire\ninterval an exception is thrown. 'interval' precision is in milliseconds.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long sumLong(PropertyAtomLong pal, long stime, long etime, int sample_interval, 
                               boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pal.howOld();
        if (etime==EPOCH_TIME) etime=pal.howCurrent();
        if (bound_by_stime && stime<pal.howOld()) stime=pal.howOld();
        TemporalUtilFunctions.validateArgs("sumLong", pal, stime, etime, sample_interval);

        long l=0; long t=0;
        for (t=stime; t<=etime; t+=sample_interval) l+=PropertyHelper.getLong(pal, t);
        if(t<etime) l+=PropertyHelper.getLong(pal, etime);

        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "sumDouble",
        synopsis = "This method returns the sum of samples of the history of a property atom over a given\ntime interval [stime, etime], all the times are in (milliseconds).",
        signature = "double sumDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "sum of all the samples taken."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the sum of samples of the history of a property atom over a given\ntime interval [stime, etime].",
        cautions = "If the values are too large the sum might overflow, also if value did not exist through entire\ninterval an exception is thrown. 'interval' precision is in milliseconds.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double sumDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,
                                   boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pad.howOld();
        if (etime==EPOCH_TIME) etime=pad.howCurrent();
        if (bound_by_stime && stime<pad.howOld()) stime=pad.howOld();
        TemporalUtilFunctions.validateArgs("sumDouble", pad, stime, etime, sample_interval);

        double d=0; long t=0;
        for (t=stime; t<=etime; t+=sample_interval) d+=PropertyHelper.getDouble(pad, t);
        if(t<etime) d+=PropertyHelper.getDouble(pad, etime);

        return d;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "maxOverTimeInt",
        synopsis = "This method returns the maximum value of samples of a PropertyAtomInt over a given time interval[stime, etime],\nall the times are in (milliseconds).",
        signature = "int maxOverTimeInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "returns the identified value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the maximum value of samples of a PropertyAtomInt over a given time interval[stime, etime].",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int maxOverTimeInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,
                                     boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pai.howOld();
        if (etime==EPOCH_TIME) etime=pai.howCurrent();
        if (bound_by_stime && stime<pai.howOld()) stime=pai.howOld();
        TemporalUtilFunctions.validateArgs("maxOverTimeInt", pai, stime, etime, sample_interval);

        int i1=PropertyHelper.getInt(pai, stime), i2=0;
        for (long t=stime; t<etime; t+=sample_interval) { i2=PropertyHelper.getInt(pai, t); i1=(i1>i2)?i1:i2; }
        i2=PropertyHelper.getInt(pai, etime);
        return (i1>i2)?i1:i2;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "maxOverTimeLong",
        synopsis = "This method returns the maximum value of samples of a PropertyAtomInt over a given time interval[stime, etime],\nall the times are in (milliseconds).",
        signature = "long maxOverTimeLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "returns the identified value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the maximum value of samples of a PropertyAtomLong over a given time interval[stime, etime].",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long maxOverTimeLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,
                                       boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pal.howOld();
        if (etime==EPOCH_TIME) etime=pal.howCurrent();
        if (bound_by_stime && stime<pal.howOld()) stime=pal.howOld();
        TemporalUtilFunctions.validateArgs("maxOverTimeLong", pal, stime, etime, sample_interval);

        long l1=PropertyHelper.getLong(pal, stime), l2=0;
        for (long t=stime; t<etime; t+=sample_interval) { l2=PropertyHelper.getLong(pal, t); l1=(l1>l2)?l1:l2; }
        l2=PropertyHelper.getLong(pal, etime);
        return (l1>l2)?l1:l2;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "maxOverTimeDouble",
        synopsis = "This method returns the maximum value of samples of a PropertyAtomInt over a given time interval[stime, etime],\nall the times are in (milliseconds).",
        signature = "double maxOverTimeDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "returns the identified value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the maximum value of samples of a PropertyAtomDouble over a given time interval[stime, etime].",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double maxOverTimeDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,
                                           boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pad.howOld();
        if (etime==EPOCH_TIME) etime=pad.howCurrent();
        if (bound_by_stime && stime<pad.howOld()) stime=pad.howOld();
        TemporalUtilFunctions.validateArgs("maxOverTimeDouble", pad, stime, etime, sample_interval);

        double d1=PropertyHelper.getDouble(pad, stime), d2=0.0;
        for (long t=stime; t<etime; t+=sample_interval) { d2=PropertyHelper.getDouble(pad, t); d1=(d1>d2)?d1:d2; }
        d2=PropertyHelper.getDouble(pad, etime);
        return (d1>d2)?d1:d2;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "minOverTimeInt",
        synopsis = "This method returns the minimum value of samples of a PropertyAtomInt over a given time interval[stime, etime],\nall the times are in (milliseconds).",
        signature = "int minOverTimeInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "returns the identified value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the minimum value of samples of a PropertyAtomInt over a given time interval[stime, etime].",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int minOverTimeInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,
                                     boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pai.howOld();
        if (etime==EPOCH_TIME) etime=pai.howCurrent();
        if (bound_by_stime && stime<pai.howOld()) stime=pai.howOld();
        TemporalUtilFunctions.validateArgs("minOverTimeInt", pai, stime, etime, sample_interval);

        int i1=PropertyHelper.getInt(pai, stime),i2=0;
        for (long t=stime; t<etime; t+=sample_interval) { i2=PropertyHelper.getInt(pai, t); i1=(i1<i2)?i1:i2; }
        i2=PropertyHelper.getInt(pai, etime);
        return (i1<i2)?i1:i2;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "minOverTimeLong",
        synopsis = "This method returns the minimum value of samples of a PropertyAtomLong over a given time interval[stime, etime],\nall the times are in (milliseconds).",
        signature = "long minOverTimeLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "returns the identified value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the minimum value of samples of a PropertyAtomLong over a given time interval[stime, etime].",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long minOverTimeLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,
                                       boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pal.howOld();
        if (etime==EPOCH_TIME) etime=pal.howCurrent();
        if (bound_by_stime && stime<pal.howOld()) stime=pal.howOld();
        TemporalUtilFunctions.validateArgs("minOverTimeLong", pal, stime, etime, sample_interval);

        long l1=PropertyHelper.getLong(pal, stime),l2=0;
        for (long t=stime; t<etime; t+=sample_interval) { l2=PropertyHelper.getLong(pal, t); l1=(l1<l2)?l1:l2; }
        l2=PropertyHelper.getLong(pal, etime);
        return (l1<l2)?l1:l2;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "minOverTimeDouble",
        synopsis = "This method returns the maximum value of samples of a PropertyAtomDouble over a given time interval[stime, etime],\nall the times are in (milliseconds).",
        signature = "double minOverTimeDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "returns the identified value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the minimum value of samples of a PropertyAtomDouble over a given time interval[stime, etime].",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double minOverTimeDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,
                                           boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pad.howOld();
        if (etime==EPOCH_TIME) etime=pad.howCurrent();
        if (bound_by_stime && stime<pad.howOld()) stime=pad.howOld();
        TemporalUtilFunctions.validateArgs("minOverTimeDouble", pad, stime, etime, sample_interval);

        double d1=PropertyHelper.getDouble(pad, stime), d2=0.0;
        for (long t=stime; t<etime; t+=sample_interval) { d2=PropertyHelper.getDouble(pad, t); d1=(d1<d2)?d1:d2; }
        d2=PropertyHelper.getDouble(pad, etime);
        return (d1<d2)?d1:d2;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "maxOverTime",
        synopsis = "This method returns the maximum value of samples of a PropertyAtom over time from last n number of msec,",
        enabled = @Enabled(value=false),
        signature = "Object maxOverTime(PropertyAtom pa, int from_no_of_msec_ago)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "from_no_of_msec_ago", type = "long", desc = "check from the last n number of msec till now")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "returns the identified value."),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the maximum value of samples of a PropertyAtom over time from last n number of msec.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object maxOverTime(PropertyAtom pa, long from_no_of_msec_ago) {
        if(pa instanceof PropertyAtomSimple) {
            if(pa instanceof PropertyAtomIntSimple)
                return((PropertyAtomIntSimple)pa).getInt();
            else if(pa instanceof PropertyAtomLongSimple)
                return (int)((PropertyAtomLongSimple)pa).getLong();
            else if(pa instanceof PropertyAtomDoubleSimple)
                return((PropertyAtomDoubleSimple)pa).getInt();
            else
                throw new RuntimeException("minOverTime doesn't support " + pa.getClass().getName());
        }
        else { //if(pa instanceof PropertyAtomImpl) {
            PropertyAtomImpl pai = (PropertyAtomImpl) pa;
            return pai.maxOverTime(from_no_of_msec_ago);
        }
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "minOverTime",
        synopsis = "This method returns the minimum value of samples of a PropertyAtom over time from last n number of msec,",
        enabled = @Enabled(value=false),
        signature = "Object minOverTime(PropertyAtom pa, int from_no_of_msec_ago)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "from_no_of_msec_ago", type = "long", desc = "check from the last n number of msec till now")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "returns the identified value."),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the minimum value of samples of a PropertyAtom over time from last n number of msec.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object minOverTime(PropertyAtom pa, long from_no_of_msec_ago) {
        if(pa instanceof PropertyAtomSimple) {
            if(pa instanceof PropertyAtomIntSimple)
                return((PropertyAtomIntSimple)pa).getInt();
            else if(pa instanceof PropertyAtomLongSimple)
                return (int)((PropertyAtomLongSimple)pa).getLong();
            else if(pa instanceof PropertyAtomDoubleSimple)
                return((PropertyAtomDoubleSimple)pa).getInt();
            else
                throw new RuntimeException("minOverTime doesn't support " + pa.getClass().getName());
        }
        else { //if(pa instanceof PropertyAtomImpl) {
            PropertyAtomImpl pai = (PropertyAtomImpl) pa;
            return pai.minOverTime(from_no_of_msec_ago);
        }
    }

     @com.tibco.be.model.functions.BEFunction(
        name = "numMatchOverLast",
        synopsis = "This method counts the number of object which match a specified value over the last n history entries.",
        signature = "int numMatchOverLast(PropertyAtom pa, Object matchValue, int last_n_times)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The target property."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "matchValue", type = "Object", desc = "The object whose value is to be matched."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "last_n_times", type = "int", desc = "The number of last history entry to match.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Number of history value which match the specified value."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method counts the number of object which matches a specified value over the last n history entries.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int numMatchOverLast(PropertyAtom pa, Object matchValue, int last_n_times) {
    	//parameters checking
    	if (pa == null || matchValue == null || last_n_times < 0) {
    		return -1;
    	}

    	Iterator iter = pa.backwardHistoryIterator();
    	int counter = 0;
    	int matchCount = 0;

    	while (iter.hasNext()) {
			if (counter == last_n_times)
				break;

			PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
    		if (matchValue.equals(curItem.value)) {
    			matchCount++;
    		}
    		counter++;
    	}

    	return matchCount;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "numMatchSince",
        synopsis = "This method counts the number of object which match a specified value since the specified time.",
        signature = "int numMatchSince(PropertyAtom pa, Object matchValue, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The target property."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "matchValue", type = "Object", desc = "The object whose value is to be matched."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The specified time in millisecond.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Number of history value which match the specified value."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method counts the number of object which match a specified value since the specified time.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
   public static int numMatchSince(PropertyAtom pa, Object matchValue, long time) {
       //parameters checking
       if (pa == null || matchValue == null || time < 0) {
           return -1;
       }

       Iterator iter = pa.backwardHistoryIterator();
       int matchCount = 0;

       while (iter.hasNext()) {
            PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
            if (time > curItem.time ) {
                break;
            }
            if (matchValue.equals(curItem.value)) {
                matchCount++;
            }
       }
       return matchCount;
   }
    
    private static long numSumSamples(PropertyAtom pa, long stime, long etime, int sample_interval, boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pa.howOld();
        if (etime==EPOCH_TIME) etime=pa.howCurrent();
        if (bound_by_stime && stime<pa.howOld()) stime=pa.howOld();
        return ((etime - stime) / sample_interval) + 1;
    }

}
