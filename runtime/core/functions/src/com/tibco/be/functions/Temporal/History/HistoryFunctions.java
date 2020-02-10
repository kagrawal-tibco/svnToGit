package com.tibco.be.functions.Temporal.History;

import com.tibco.be.functions.Temporal.TemporalUtilFunctions;
import com.tibco.be.functions.property.PropertyHelper;
import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.runtime.model.element.*;

import java.util.Iterator;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Temporal.History",
        synopsis = "Temporal History Functions")
public class HistoryFunctions {

    final static long EPOCH_TIME=0;

    @com.tibco.be.model.functions.BEFunction(
        name = "howMany",
        synopsis = "This method returns how many values exist in interval [stime, etime].",
        signature = "int howMany(PropertyAtom pa, long stime, long etime, boolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "no of values that exist in interval [stime, etime]."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns how many values exist in interval [stime, etime].",
        cautions = "If PropertyAtom does not have enough timeline, the oldest value starts it.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int howMany(PropertyAtom pa, long stime, long etime, boolean bound_by_stime)
            throws IllegalArgumentException {
        long oldestTime = pa.howOld();
        long latestTime = pa.howCurrent();
        if (stime>latestTime) return 0;
        if (stime==EPOCH_TIME) stime=oldestTime;
        if (etime==EPOCH_TIME) etime=latestTime;
        if (bound_by_stime && stime<oldestTime) { 
            stime=oldestTime;
            if (etime>latestTime) {
                etime=latestTime;
            }
        }
        TemporalUtilFunctions.validateArgs("howMany", pa, stime, etime, 1);

        try {
            return ((PropertyAtom)pa).howMany(stime, etime);
        }
        catch (PropertyException pe) {
            throw new IllegalArgumentException(pe.getMessage());
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "howManySince",
        synopsis = "This method returns how many history values exist for given property since the specified time.",
        signature = "int howManySince(PropertyAtom pa, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The target property."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The specified time in millisecond.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Number of history values that exist since the specified time."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns how many history values exist for given property since\nthe specified time.\nThe value is bounded by history size.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int howManySince(PropertyAtom pa, long time) {
        //parameters checking
        if (pa == null || time < 0) {
            return -1;
        }

        int counter = 0;
        Iterator iter = pa.backwardHistoryIterator();
        while (iter.hasNext()) {
            PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
            if (time > curItem.time ) {
                break;
            }
            counter++;
        }

        return counter;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "howManyInLast",
        synopsis = "This method returns how many history values exist for a property in last number of msec ago .",
        signature = "int howManyInLast(PropertyAtom pa, long num_msec_ago)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The target property."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "num_msec_ago", type = "long", desc = "Number of msec ago.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Number of history values that exist within the last specified msec timeframe."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns how many history values exist for given property in last number of msec ago.\nThe value is bounded by history size.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int howManyInLast(PropertyAtom pa, long num_msec_ago) {
        //parameters checking
        if (pa == null || num_msec_ago < 0) {
            return -1;
        }

        long time = System.currentTimeMillis() - num_msec_ago;

        int counter = 0;
        Iterator iter = pa.backwardHistoryIterator();
        while (iter.hasNext()) {
            PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
            if (time > curItem.time ) {
                break;
            }
            counter++;
        }

        return counter;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "howOld",
        synopsis = "This method returns how old is the oldest value in the PropertyAtom history.",
        signature = "long howOld(PropertyAtom pa)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The property to check for the value of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "time stamp of the oldest value in the history(milliseconds)."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns how old is the oldest value in the PropertyAtom history.",
        cautions = "none.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long howOld(PropertyAtom pa) {
        TemporalUtilFunctions.validateArg("howOld", pa);
        return pa.howOld();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "howCurrent",
        synopsis = "This method returns how old is the newest value in the PropertyAtom history.",
        signature = "long howCurrent(PropertyAtom pa)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The property to check for the value of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "time stamp of the newest value in the history(milliseconds)."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns how old is the newest value in the PropertyAtom history.",
        cautions = "none.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long howCurrent(PropertyAtom pa) {
        TemporalUtilFunctions.validateArg("howCurrent", pa);
        return pa.howCurrent();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "previousInt",
        synopsis = "This method returns the previous value of PropertyAtomInt.",
        signature = "int previousInt(PropertyAtomInt pai)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to check for the value of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "returns the previous value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the previous value of PropertyAtomInt",
        cautions = "If this is the first value; PropertyAtomInt default value is returned.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int previousInt(PropertyAtomInt pai) {
        TemporalUtilFunctions.validateArg("previousInt", pai);
        return ((Integer)pai.getPreviousValue()).intValue();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "previousLong",
        synopsis = "This method returns the previous value of PropertyAtomInt.",
        signature = "long previousLong(PropertyAtomLong pal)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to check for the value of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "returns the previous value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the previous value of PropertyAtomLong",
        cautions = "If this is the first value; PropertyAtomInt default value is returned.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long previousLong(PropertyAtomLong pal) {
        TemporalUtilFunctions.validateArg("previousLong", pal);
        return ((Long)pal.getPreviousValue()).longValue();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "previousDouble",
        synopsis = "This method returns the previous value of PropertyAtomInt.",
        signature = "double previousDouble(PropertyAtomDouble pad)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to check for the value of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "returns the previous value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the previous value of PropertyAtomDouble",
        cautions = "If this is the first value; PropertyAtomInt default value is returned.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double previousDouble(PropertyAtomDouble pad) {
        TemporalUtilFunctions.validateArg("previousDouble", pad);
        return ((Double)pad.getPreviousValue()).doubleValue();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "previous",
        synopsis = "This method returns the previous value of a PropertyAtom.",
        signature = "Object previous(PropertyAtom pad)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtom", desc = "The property to check for the value of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "returns the previous value."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the previous value of a PropertyAtom.",
        cautions = "If this is the first value; PropertyAtomInt default value is returned.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object previous(PropertyAtom pad) {
        TemporalUtilFunctions.validateArg("previous", pad);
        return pad.getPreviousValue();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "alwaysDecreasingInt",
        synopsis = "This method checks if samples of the property always decrease over a given time interval [stime, etime].\nReturns true only if each sample value is less than the immediately prior sample value in the given time interval.\nAll the times are in milliseconds.",
        signature = "boolean alwaysDecreasingInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if samples of the property always decrease, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property always decrease over a given time interval [stime, etime].\nReturns true only if each sample value is less than the immediately prior sample value in the given time interval.\nAll the times are in milliseconds.",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean alwaysDecreasingInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,
                                              boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pai.howOld();
        if (etime==EPOCH_TIME) etime=pai.howCurrent();
        if (bound_by_stime && stime<pai.howOld()) stime=pai.howOld();
        TemporalUtilFunctions.validateArgs("alwaysDecreasingInt", pai, stime, etime, sample_interval);

        int i1,i2; i1=i2=PropertyHelper.getInt(pai, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) {
            i2=PropertyHelper.getInt(pai, t);
            if (i2<i1) i1=i2; else return false;
        }
        if (PropertyHelper.getInt(pai, etime)<i2) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "alwaysDecreasingLong",
        synopsis = "This method checks if samples of the property always decrease over a given time interval [stime, etime].\nReturns true only if each sample value is less than the immediately prior sample value in the given time interval.\nAll the times are in milliseconds.",
        signature = "boolean alwaysDecreasingLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if samples of the property always decrease, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property always decrease over a given time interval [stime, etime].\nReturns true only if each sample value is less than the immediately prior sample value in the given time interval.\nAll the times are in milliseconds.",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean alwaysDecreasingLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,
                                               boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pal.howOld();
        if (etime==EPOCH_TIME) etime=pal.howCurrent();
        if (bound_by_stime && stime<pal.howOld()) stime=pal.howOld();
        TemporalUtilFunctions.validateArgs("alwaysDecreasingLong", pal, stime, etime, sample_interval);

        long l1,l2; l1=l2=PropertyHelper.getLong(pal, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) {
            l2=PropertyHelper.getLong(pal, t);
            if (l2<l1) l1=l2; else return false;
        }
        if (PropertyHelper.getLong(pal, etime)<l2) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "alwaysDecreasingDouble",
        synopsis = "This method checks if samples of the property always decrease over a given time interval [stime, etime].\nReturns true only if each sample value is less than the immediately prior sample value in the given time interval.\nAll the times are in milliseconds.",
        signature = "boolean alwaysDecreasingDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if samples of the property always decrease, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property always decrease over a given time interval [stime, etime].\nReturns true only if each sample value is less than the immediately prior sample value in the given time interval.\nAll the times are in milliseconds.",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean alwaysDecreasingDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,
                                                 boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pad.howOld();
        if (etime==EPOCH_TIME) etime=pad.howCurrent();
        if (bound_by_stime && stime<pad.howOld()) stime=pad.howOld();
        TemporalUtilFunctions.validateArgs("alwaysDecreasingDouble", pad, stime, etime, sample_interval);

        double d1,d2; d1=d2=PropertyHelper.getDouble(pad, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) {
            d2=PropertyHelper.getDouble(pad, t);
            if (d2<d1) d1=d2; else return false;
        }
        if (PropertyHelper.getDouble(pad, etime)<d2) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "alwaysIncreasingInt",
        synopsis = "This method checks if samples of the property always increase over a given time interval [stime, etime].\nReturns true only if each sample value is greater than the immediately prior sample value in the given time interval.\nAll the times are in milliseconds.",
        signature = "boolean alwaysIncreasingInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if samples of the property always increase, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property always increase over a given time interval [stime, etime].\nReturns true only if each sample value is greater than the immediately prior sample value in the given time interval.\nAll the times are in milliseconds.",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean alwaysIncreasingInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,
                                              boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pai.howOld();
        if (etime==EPOCH_TIME) etime=pai.howCurrent();
        if (bound_by_stime && stime<pai.howOld()) stime=pai.howOld();
        TemporalUtilFunctions.validateArgs("alwaysIncreasingInt", pai, stime, etime, sample_interval);

        int i1,i2; i1=i2=PropertyHelper.getInt(pai, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) {
            i2=PropertyHelper.getInt(pai, t);
            if (i2>i1) i1=i2; else return false;
        }
        if (PropertyHelper.getInt(pai, etime)>i2) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "alwaysIncreasingLong",
        synopsis = "This method checks if samples of the property always increase over a given time interval [stime, etime].\nReturns true only if each sample value is greater than the immediately prior sample value in the given time interval.\nAll the times are in milliseconds.",
        signature = "boolean alwaysIncreasingLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if samples of the property always increase, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property always increase over a given time interval [stime, etime].\nReturns true only if each sample value is greater than the immediately prior sample value in the given time interval.\nAll the times are in milliseconds.",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean alwaysIncreasingLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,
                                               boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pal.howOld();
        if (etime==EPOCH_TIME) etime=pal.howCurrent();
        if (bound_by_stime && stime<pal.howOld()) stime=pal.howOld();
        TemporalUtilFunctions.validateArgs("alwaysIncreasingLong", pal, stime, etime, sample_interval);

        long l1,l2; l1=l2=PropertyHelper.getLong(pal, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) {
            l2=PropertyHelper.getLong(pal, t);
            if (l2>l1) l1=l2; else return false;
        }
        if (PropertyHelper.getLong(pal, etime)>l2) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "alwaysIncreasingDouble",
        synopsis = "This method checks if samples of the property always increase over a given time interval [stime, etime].\nReturns true only if each sample value is greater than the immediately prior sample value in the given time interval.\nAll the times are in milliseconds.",
        signature = "boolean alwaysIncreasingDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if samples of the property always increase, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property always increase over a given time interval [stime, etime].\nReturns true only if each sample value is greater than the immediately prior sample value in the given time interval.\nAll the times are in milliseconds.",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean alwaysIncreasingDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,
                                                 boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pad.howOld();
        if (etime==EPOCH_TIME) etime=pad.howCurrent();
        if (bound_by_stime && stime<pad.howOld()) stime=pad.howOld();
        TemporalUtilFunctions.validateArgs("alwaysIncreasingDouble", pad, stime, etime, sample_interval);

        double d1,d2; d1=d2=PropertyHelper.getDouble(pad, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) {
            d2=PropertyHelper.getDouble(pad, t);
            if (d2>d1) d1=d2; else return false;
        }
        if (PropertyHelper.getDouble(pad, etime)>d2) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "everDecreasingInt",
        synopsis = "This method checks if samples of the property ever decrease over a given time interval [stime, etime].\nReturns true only if any sample value is less than any prior sample value in the given time interval.\nAll the times are in milliseconds.",
        signature = "boolean everDecreasingInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if samples of the property ever decrease, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property ever decrease over a given time interval [stime, etime].\nReturns true only if any sample value is less than any prior sample value in the given time interval.\nAll the times are in milliseconds.",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean everDecreasingInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,
                                            boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pai.howOld();
        if (etime==EPOCH_TIME) etime=pai.howCurrent();
        if (bound_by_stime && stime<pai.howOld()) stime=pai.howOld();
        TemporalUtilFunctions.validateArgs("everDecreasingInt", pai, stime, etime, sample_interval);

        int i1,i2; i1=i2=PropertyHelper.getInt(pai, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) {
            i2=PropertyHelper.getInt(pai, t);
            if (i2<i1) return true;
        }
        if (PropertyHelper.getInt(pai, etime)<i2) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "everDecreasingLong",
        synopsis = "This method checks if samples of the property ever decrease over a given time interval [stime, etime].\nReturns true only if any sample value is less than any prior sample value in the given time interval.\nAll the times are in milliseconds.",
        signature = "boolean everDecreasingLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if samples of the property ever decrease, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property ever decrease over a given time interval [stime, etime].\nReturns true only if any sample value is less than any prior sample value in the given time interval.\nAll the times are in milliseconds.",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean everDecreasingLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,
                                             boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pal.howOld();
        if (etime==EPOCH_TIME) etime=pal.howCurrent();
        if (bound_by_stime && stime<pal.howOld()) stime=pal.howOld();
        TemporalUtilFunctions.validateArgs("everDecreasingLong", pal, stime, etime, sample_interval);

        long l1,l2; l1=l2=PropertyHelper.getLong(pal, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) {
            l2=PropertyHelper.getLong(pal, t);
            if (l2<l1) return true;
        }
        if (PropertyHelper.getLong(pal, etime)<l2) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "everDecreasingDouble",
        synopsis = "This method checks if samples of the property ever decrease over a given time interval [stime, etime].\nReturns true only if any sample value is less than any prior sample value in the given time interval.\nAll the times are in milliseconds.",
        signature = "boolean everDecreasingDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if samples of the property ever decrease, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property ever decrease over a given time interval [stime, etime].\nReturns true only if any sample value is less than any prior sample value in the given time interval.\nAll the times are in milliseconds.",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean everDecreasingDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,
                                               boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pad.howOld();
        if (etime==EPOCH_TIME) etime=pad.howCurrent();
        if (bound_by_stime && stime<pad.howOld()) stime=pad.howOld();
        TemporalUtilFunctions.validateArgs("everDecreasingDouble", pad, stime, etime, sample_interval);

        double d1,d2; d1=d2=PropertyHelper.getDouble(pad, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) {
            d2=PropertyHelper.getDouble(pad, t);
            if (d2<d1) return true;
        }
        if (PropertyHelper.getDouble(pad, etime)<d2) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "everIncreasingInt",
        synopsis = "This method checks if samples of the property ever increase over a given time interval [stime, etime].\nReturns true only if any sample value is greater than any prior sample value in the given time interval.\nAll the times are in milliseconds.",
        signature = "boolean everIncreasingInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if samples of the property ever increase, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property ever increase over a given time interval [stime, etime].\nReturns true only if any sample value is greater than any prior sample value in the given time interval.\nAll the times are in milliseconds.",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean everIncreasingInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,
                                            boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pai.howOld();
        if (etime==EPOCH_TIME) etime=pai.howCurrent();
        if (bound_by_stime && stime<pai.howOld()) stime=pai.howOld();
        TemporalUtilFunctions.validateArgs("everIncreasingInt", pai, stime, etime, sample_interval);

        int i1,i2; i1=i2=PropertyHelper.getInt(pai, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) {
            i2=PropertyHelper.getInt(pai, t);
            if (i2>i1) return true;
        }
        if (PropertyHelper.getInt(pai, etime)>i2) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "everIncreasingLong",
        synopsis = "This method checks if samples of the property ever increase over a given time interval [stime, etime].\nReturns true only if any sample value is greater than any prior sample value in the given time interval.\nAll the times are in milliseconds.",
        signature = "boolean everIncreasingLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if samples of the property ever increase, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property ever increase over a given time interval [stime, etime].\nReturns true only if any sample value is greater than any prior sample value in the given time interval.\nAll the times are in milliseconds.",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean everIncreasingLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,
                                             boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pal.howOld();
        if (etime==EPOCH_TIME) etime=pal.howCurrent();
        if (bound_by_stime && stime<pal.howOld()) stime=pal.howOld();
        TemporalUtilFunctions.validateArgs("everIncreasingLong", pal, stime, etime, sample_interval);

        long l1,l2; l1=l2=PropertyHelper.getLong(pal, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) {
            l2=PropertyHelper.getLong(pal, t);
            if (l2>l1) return true;
        }
        if (PropertyHelper.getLong(pal, etime)>l2) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "everIncreasingDouble",
        synopsis = "This method checks if samples of the property ever increase over a given time interval [stime, etime].\nReturns true only if any sample value is greater than any prior sample value in the given time interval.\nAll the times are in milliseconds.",
        signature = "boolean everIncreasingDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if samples of the property ever increase, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property ever increase over a given time interval [stime, etime].\nReturns true only if any sample value is greater than any prior sample value in the given time interval.\nAll the times are in milliseconds.",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean everIncreasingDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,
                                               boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pad.howOld();
        if (etime==EPOCH_TIME) etime=pad.howCurrent();
        if (bound_by_stime && stime<pad.howOld()) stime=pad.howOld();
        TemporalUtilFunctions.validateArgs("everIncreasingDouble", pad, stime, etime, sample_interval);

        double d1,d2; d1=d2=PropertyHelper.getDouble(pad, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) {
            d2=PropertyHelper.getDouble(pad, t);
            if (d2>d1) return true;
        }
        if (PropertyHelper.getDouble(pad, etime)>d2) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "steadyInt",
        synopsis = "This method checks if samples of the property value remain steady over a given time interval[stime, etime],\nall the times are in (milliseconds).",
        signature = "boolean steadyInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the value was steady false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property value remain steady over a given time interval[stime, etime].",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean steadyInt(PropertyAtomInt pai, long stime, long etime, int sample_interval,
                                    boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pai.howOld();
        if (etime==EPOCH_TIME) etime=pai.howCurrent();
        if (bound_by_stime && stime<pai.howOld()) stime=pai.howOld();
        TemporalUtilFunctions.validateArgs("steadyInt", pai, stime, etime, sample_interval);

        int i=PropertyHelper.getInt(pai, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) { if(i!=PropertyHelper.getInt(pai, t)) return false; }

        if (PropertyHelper.getInt(pai, etime)==i) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "steadyLong",
        synopsis = "This method checks if samples of the property value remain steady over a given time interval[stime, etime],\nall the times are in (milliseconds).",
        signature = "boolean steadyLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the value was steady false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property value remain steady over a given time interval[stime, etime].",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean steadyLong(PropertyAtomLong pal, long stime, long etime, int sample_interval,
                                     boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pal.howOld();
        if (etime==EPOCH_TIME) etime=pal.howCurrent();
        if (bound_by_stime && stime<pal.howOld()) stime=pal.howOld();
        TemporalUtilFunctions.validateArgs("steadyLong", pal, stime, etime, sample_interval);

        long l=PropertyHelper.getLong(pal, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) { if(l!=PropertyHelper.getLong(pal, t)) return false; }

        if (PropertyHelper.getLong(pal, etime)==l) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "steadyDouble",
        synopsis = "This method checks if samples of the property value remain steady over a given time interval[stime, etime],\nall the times are in (milliseconds).",
        signature = "boolean steadyDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,\nboolean bound_by_stime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stime", type = "long", desc = "start time of the interval, 0 means use oldest value's timestamp as start time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "etime", type = "long", desc = "end time of the interval, 0 means use newest value's timestamp as end time."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sample_interval", type = "int", desc = "interval between samples in milliseconds, should be greater than 0."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bound_by_stime", type = "boolean", desc = "true implies for stime less than oldest value's time stamp[t0] use t0.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the value was steady false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if samples of the property value remain steady over a given time interval[stime, etime].",
        cautions = "If value did not exist through the entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean steadyDouble(PropertyAtomDouble pad, long stime, long etime, int sample_interval,
                                       boolean bound_by_stime) {
        if (stime==EPOCH_TIME) stime=pad.howOld();
        if (etime==EPOCH_TIME) etime=pad.howCurrent();
        if (bound_by_stime && stime<pad.howOld()) stime=pad.howOld();
        TemporalUtilFunctions.validateArgs("steadyDouble", pad, stime, etime, sample_interval);

        double d=PropertyHelper.getDouble(pad, stime);
        for (long t=stime+sample_interval; t<etime; t+=sample_interval) { if(d!=PropertyHelper.getDouble(pad, t)) return false; }

        if (PropertyHelper.getDouble(pad, etime)==d) return true;
        return false;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "historyInt",
        synopsis = "This method returns PropertyAtomInt value a given number of milli seconds ago.",
        signature = "double historyInt(PropertyAtomInt pai, long no_of_milli_seconds_ago)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "no_of_milli_seconds_ago", type = "long", desc = "Time at which the interpolated value is required.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "returns the identified value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns PropertyAtomInt value a given number of milli seconds ago",
        cautions = "If value did not exist through entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int historyInt(PropertyAtomInt pai, int no_of_milli_seconds_ago) {
        TemporalUtilFunctions.validateArg("historyInt", pai);
        return PropertyHelper.getInt(pai, System.currentTimeMillis()-no_of_milli_seconds_ago);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "historyLong",
        synopsis = "This method returns PropertyAtomLong value a given number of milli seconds ago.",
        signature = "double historyLong(PropertyAtomLong pal, long no_of_milli_seconds_ago)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "no_of_milli_seconds_ago", type = "long", desc = "Time at which the interpolated value is required.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "returns the identified value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns PropertyAtomLong value a given number of milli seconds ago",
        cautions = "If value did not exist through entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long historyLong(PropertyAtomLong pal, int no_of_milli_seconds_ago) {
        TemporalUtilFunctions.validateArg("historyLong", pal);
        return PropertyHelper.getLong(pal, System.currentTimeMillis()-no_of_milli_seconds_ago);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "historyDouble",
        synopsis = "This method returns PropertyAtomDouble value a given number of milli seconds ago.",
        signature = "double historyDouble(PropertyAtomDouble pad, long no_of_milli_seconds_ago)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "no_of_milli_seconds_ago", type = "long", desc = "Time at which the interpolated value is required.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "returns the identified value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns PropertyAtomDouble value a given number of milli seconds ago",
        cautions = "If value did not exist through entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double historyDouble(PropertyAtomDouble pad, int no_of_milli_seconds_ago) {
        TemporalUtilFunctions.validateArg("historyDouble", pad);
        return PropertyHelper.getDouble(pad, System.currentTimeMillis()-no_of_milli_seconds_ago);
   }

    @com.tibco.be.model.functions.BEFunction(
        name = "history",
        synopsis = "This method returns PropertyAtom value a given number of milli seconds ago.",
        signature = "Object history(PropertyAtom pad, long no_of_milli_seconds_ago)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtom", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "no_of_milli_seconds_ago", type = "long", desc = "Time at which the interpolated value is required.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "returns the identified value."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns PropertyAtom value a given number of milli seconds ago",
        cautions = "If value did not exist through entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object history(PropertyAtom pad, int no_of_milli_seconds_ago) {
        TemporalUtilFunctions.validateArg("history", pad);
        return PropertyHelper.getValueAt(pad, System.currentTimeMillis()-no_of_milli_seconds_ago);
   }
}
