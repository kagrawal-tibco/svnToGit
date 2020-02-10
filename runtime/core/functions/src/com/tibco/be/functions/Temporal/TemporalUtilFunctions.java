package com.tibco.be.functions.Temporal;

import com.tibco.cep.runtime.model.element.PropertyAtom;
@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Temporal",
        synopsis = "Category place holder")

public class TemporalUtilFunctions {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    private static void dummy() {}


    public static void validateArg(String fname, PropertyAtom pa) throws IllegalArgumentException {
        String s = null;
        if (pa==null) s="PropertyAtom is null";
        if (s!=null ) throw new IllegalArgumentException(fname + ":" + s);
    }

    public static void validateArgs(String fname, PropertyAtom pa, long stime, long etime, int sample_rate)
            throws IllegalArgumentException
    {
        String s = null;
        if (pa==null      ) s="PropertyAtom is null";
        if (etime<stime   ) s="End time of the interval is before the start time";
        if (sample_rate<=0) s="Sample rate can not be < or = 0";
        if (s!=null    ) throw new IllegalArgumentException(fname + ":" + s);
    }

    /*todo : calendar feature will enable most of them
    public static void    TimerDestroy(name) {}
    public static void    TimerDump( ) {}
    public static double  TimerGetDeltaTime(name) {}
    public static double  TimerGetExpireTime(name) {}
    public static void    TimerSet(name, slot, value, delta_time) {}
    public static void    TimerSetUnknown(name, slot, delta_time) {}
    public static void    TimerUpdateExpireTime(name, new_expire_time) {}
    */

    public static void main(String[] args) {
        TemporalUtilFunctions j=new TemporalUtilFunctions();
    }
}
