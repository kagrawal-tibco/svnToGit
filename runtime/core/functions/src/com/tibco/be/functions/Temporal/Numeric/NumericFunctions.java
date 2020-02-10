package com.tibco.be.functions.Temporal.Numeric;

import com.tibco.be.functions.Temporal.TemporalUtilFunctions;
import com.tibco.be.functions.property.PropertyHelper;
import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.runtime.model.element.*;

import java.util.Iterator;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Temporal.Numeric",
        synopsis = "Numeric Temporal Functions")

public class NumericFunctions {

    final static long EPOCH_TIME=0;

    @com.tibco.be.model.functions.BEFunction(
        name = "addAllHistoryInt",
        synopsis = "This method returns the sum of all the values in the history of a property atom.",
        signature = "int addAllHistoryInt(PropertyAtomInt pai, long t)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "t", type = "time", desc = "from which values are added (in milliseconds); 0 uses all values recorded.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "sum of all values in the history."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the sum of all the values in the history of a property atom.",
        cautions = "If the values are too large the sum might overflow.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int addAllHistoryInt(PropertyAtomInt pai, long t) {
        TemporalUtilFunctions.validateArg("addAllHistoryInt", pai);
        int i=0; Iterator iter = pai.historyIterator();
        while (iter.hasNext()) {
            PropertyAtom.History h = (PropertyAtom.History)iter.next();
            if (h.time>=t) i+=((Integer)(h).value).intValue();
            else           break;
        }
        return i;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "addAllHistoryLong",
        synopsis = "This method returns the sum of all the values in the history of a property atom.",
        signature = "long addAllHistoryLong(PropertyAtomLong pal, long t)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "t", type = "time", desc = "from which values are added (in milliseconds); 0 uses all values recorded.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "sum of all values in the history."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the sum of all the values in the history of a property atom.",
        cautions = "If the values are too large the sum might overflow.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long addAllHistoryLong(PropertyAtomLong pal, long t) {
        TemporalUtilFunctions.validateArg("addAllHistoryLong", pal);
        long l=0; Iterator iter = pal.historyIterator();
        while (iter.hasNext()) {
            PropertyAtom.History h = (PropertyAtom.History)iter.next();
            if (h.time>=t) l+=((Long)(h).value).longValue();
            else           break;
        }
        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "addAllHistoryDouble",
        synopsis = "This method returns the sum of all the values in the history of a property atom.",
        signature = "double addAllHistoryDouble(PropertyAtomDouble pad, long t)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "t", type = "time", desc = "from which values are added (in milliseconds); 0 uses all values recorded.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "sum of all values in the history."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the sum of all the values in the history of a property atom.",
        cautions = "If the values are too large the sum might overflow.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double addAllHistoryDouble(PropertyAtomDouble pad, long t) {
        TemporalUtilFunctions.validateArg("addAllHistoryDouble", pad);
        double d=0; Iterator iter = pad.historyIterator();
        while (iter.hasNext()) {
            PropertyAtom.History h = (PropertyAtom.History)iter.next();
            if (h.time>=t) d+=((Double)(h).value).doubleValue();
            else           break;
        }
        return d;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "interpolateInt",
        synopsis = "This method returns interpolated value over the history a given number of msec ago.",
        signature = "double interpolateInt(PropertyAtomInt pai, long no_of_milli_seconds_ago)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "no_of_milli_seconds_ago", type = "long", desc = "Time at which the interpolated value is required.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "returns the calculated value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns interpolated value over the history a given number of msec ago",
        cautions = "If value did not exist through entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double interpolateInt(PropertyAtomInt pai, long no_of_milli_seconds_ago) {
        long[] interval=new long[2];  int[] values=new int[2];
        long itime=System.currentTimeMillis()-no_of_milli_seconds_ago;
        TemporalUtilFunctions.validateArg("interpolateInt", pai);
        if (itime<pai.howOld()) throw new IllegalArgumentException("Error: No history available at time requested");


        try {
            pai.getHistoryTimeInterval(itime, interval);
        } catch (PropertyException e) {
            throw new RuntimeException(e);
        }


        if (interval[0]==interval[1]) return PropertyHelper.getInt(pai, itime);
        else {
            values[0]=PropertyHelper.getInt(pai, interval[0]);
            values[1]=PropertyHelper.getInt(pai, interval[1]);
            double slope = ((itime - interval[0])*1.0)/((interval[1] - interval[0])*1.0);
            return values[0] + slope * (values[1]-values[0]);
       }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "interpolateLong",
        synopsis = "This method returns interpolated value over the history a given number of msec ago.",
        signature = "double interpolateLong(PropertyAtomLong pal, long no_of_milli_seconds_ago)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "no_of_milli_seconds_ago", type = "long", desc = "Time at which the interpolated value is required.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "returns the calculated value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns interpolated value over the history a given number of msec ago",
        cautions = "If value did not exist through entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double interpolateLong(PropertyAtomLong pal, long no_of_milli_seconds_ago) {
        long[] interval=new long[2];  long[] values=new long[2];
        long itime=System.currentTimeMillis()-no_of_milli_seconds_ago;
        TemporalUtilFunctions.validateArg("interpolateLong", pal);
        if (itime<pal.howOld()) throw new IllegalArgumentException("Error: No history available at time requested");

        try {
            pal.getHistoryTimeInterval(itime, interval);
        } catch (PropertyException e) {
            throw new RuntimeException(e);
        }


        if (interval[0]==interval[1]) return PropertyHelper.getLong(pal, itime);
        else {
            values[0]=PropertyHelper.getLong(pal, interval[0]);
            values[1]=PropertyHelper.getLong(pal, interval[1]);
            double slope = ((itime - interval[0])*1.0)/((interval[1] - interval[0])*1.0);
            return values[0] + slope * (values[1]-values[0]);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "interpolateDouble",
        synopsis = "This method returns interpolated value over the history a given number of msec ago.",
        signature = "double interpolateDouble(PropertyAtomDouble pad, long no_of_milli_seconds_ago)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property to check for the value of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "no_of_milli_seconds_ago", type = "long", desc = "Time at which the interpolated value is required.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "returns the calculated value."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns interpolated value over the history a given number of msec ago",
        cautions = "If value did not exist through entire interval an exception is thrown.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double interpolateDouble(PropertyAtomDouble pad, long no_of_milli_seconds_ago) {
        long[] interval=new long[2];  double[] values=new double[2];
        long itime=System.currentTimeMillis()-no_of_milli_seconds_ago;
        TemporalUtilFunctions.validateArg("interpolateDouble", pad);
        if (itime<pad.howOld()) throw new IllegalArgumentException("Error: No history available at time requested");

        try {
            pad.getHistoryTimeInterval(itime, interval);
        } catch (PropertyException e) {
            throw new RuntimeException(e);
        }


        if (interval[0]==interval[1]) return PropertyHelper.getDouble(pad, itime);
        else {
            values[0]=PropertyHelper.getDouble(pad, interval[0]);
            values[1]=PropertyHelper.getDouble(pad, interval[1]);
            double slope = ((itime - interval[0])*1.0)/((interval[1] - interval[0])*1.0);
            return values[0] + slope * (values[1]-values[0]);
        }
    }

     @com.tibco.be.model.functions.BEFunction(
        name = "totalOfLast",
        synopsis = "This method calculates the sum of the value of the last n history entries.",
        signature = "Object totalOfLast(PropertyAtom pa, int last_n_times)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The target property for calculation."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "last_n_times", type = "int", desc = "The number of last history entries to calculate.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The sum of the value of the last n history entries. The return object type is the same as the type of the value of 'pa'."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method calculates the sum of the value of the last n history entries.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
	public static Object totalOfLast(PropertyAtom pa, int last_n_times) {
    	//parameters checking
    	if (pa == null || last_n_times <= 0) {
    		return null;
    	}

		Iterator iter = pa.backwardHistoryIterator();

		Object mostCurrentValue = pa.getValue();

		int counter = 0;
		if (mostCurrentValue instanceof Integer) {
			int result = 0;
			while (iter.hasNext()) {
				if (counter == last_n_times) {
					break;
				}

				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
				Integer curIntObj = (Integer) curItem.value;
				result += curIntObj.intValue();
				counter++;
			}

			return new Integer(result);
		} else if (mostCurrentValue instanceof Long) {
			long result = 0;
			while (iter.hasNext()) {
				if (counter == last_n_times) {
					break;
				}

				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
				Long curLongObj = (Long) curItem.value;
				result += curLongObj.longValue();
				counter++;
			}

			return new Long(result);

		} else if (mostCurrentValue instanceof Double) {
			double result = 0;
			while (iter.hasNext()) {
				if (counter == last_n_times) {
					break;
				}

				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
				Double curDoubleObj = (Double) curItem.value;
				result += curDoubleObj.doubleValue();
				counter++;
			}

			return new Double(result);
		}

		// if the pa value is NOT of double, long or int,
		// just return the most currentValue.
		return mostCurrentValue;

	}


    @com.tibco.be.model.functions.BEFunction(
        name = "totalSince",
        synopsis = "This method calculates the sum of the history value since the specified time.",
        signature = "Object totalSince(PropertyAtom pa, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The target property for calculation."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The specified time in millisecond.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The sum of the value since the specified time. The return object type is the same as the type of the value of 'pa'."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method calculates the sum of the history value since the specified time.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
   public static Object totalSince(PropertyAtom pa, long time) {
       //parameters checking
       if (pa == null || time <= 0) {
           return null;
       }

       Iterator iter = pa.backwardHistoryIterator();
       Object mostCurrentValue = pa.getValue();

       if (mostCurrentValue instanceof Integer) {
           int result = 0;
           while (iter.hasNext()) {
               PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
               if (time > curItem.time ) {
                   break;
               }
               Integer curIntObj = (Integer) curItem.value;
               result += curIntObj.intValue();
           }
           return new Integer(result);
       } else if (mostCurrentValue instanceof Long) {
           long result = 0;
           while (iter.hasNext()) {
               PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
               if (time > curItem.time ) {
                   break;
               }
               Long curLongObj = (Long) curItem.value;
               result += curLongObj.longValue();
           }
           return new Long(result);
       } else if (mostCurrentValue instanceof Double) {
           double result = 0;
           while (iter.hasNext()) {
               PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
               if (time > curItem.time ) {
                   break;
               }
               Double curDoubleObj = (Double) curItem.value;
               result += curDoubleObj.doubleValue();
           }
           return new Double(result);
       }
       // if the pa value is NOT of double, long or int,
       // just return the most currentValue.
       return mostCurrentValue;
   }


    @com.tibco.be.model.functions.BEFunction(
        name = "avgOfLast",
        synopsis = "This method calculates the average of the value of the last n history entries.",
        signature = "Object avgOfLast(PropertyAtom pa, int last_n_times)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The target property for calculation."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "last_n_times", type = "int", desc = "The number of last history entries to calculate.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The average of the value of the last n history entries. The return object type is always Double if any."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method calculates the average of the value of the last n history entries.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object avgOfLast(PropertyAtom pa, int last_n_times) {
    	//parameters checking
    	if (pa == null || last_n_times <= 0) {
    		return null;
    	}

		Iterator iter = pa.backwardHistoryIterator();

		Object mostCurrentValue = pa.getValue();

		int counter = 0;
		if (mostCurrentValue instanceof Integer) {
			int summation = 0;
			while (iter.hasNext()) {
				if (counter == last_n_times) {
					break;
				}

				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
				Integer curIntObj = (Integer) curItem.value;
				summation += curIntObj.intValue();
				counter++;
			}

			double average = 0;
			if (counter != 0)
				average = (new Double(summation)).doubleValue() / counter;

			return new Double(average);
		} else if (mostCurrentValue instanceof Long) {
			long summation = 0;
			while (iter.hasNext()) {
				if (counter == last_n_times) {
					break;
				}

				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
				Long curLongObj = (Long) curItem.value;
				summation += curLongObj.longValue();
				counter++;
			}

			double average = 0;
			if (counter != 0)
				average = summation / counter;

			return new Double(average);

		} else if (mostCurrentValue instanceof Double) {
			double summation = 0;
			while (iter.hasNext()) {
				if (counter == last_n_times) {
					break;
				}

				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
				Double curDoubleObj = (Double) curItem.value;
				summation += curDoubleObj.doubleValue();
				counter++;
			}

			double average = 0;
			if (counter != 0)
				average = summation / counter;

			return new Double(average);
		}

		// if the pa value is NOT of double, long or int,
		// just 0
		return new Double(0);

    }


    @com.tibco.be.model.functions.BEFunction(
        name = "avgSince",
        synopsis = "This method calculates the average history value since the specified time.",
        signature = "Object avgSince(PropertyAtom pa, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The target property for calculation."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The specified time in millisecond.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The average of the value since the specified time. The return object type is always Double if any."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method calculates the average history value since the specified time.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object avgSince(PropertyAtom pa, long time) {
    	//parameters checking
    	if (pa == null || time <= 0) {
    		return null;
    	}

		Iterator iter = pa.backwardHistoryIterator();

		Object mostCurrentValue = pa.getValue();

		int counter = 0;
		if (mostCurrentValue instanceof Integer) {
			int summation = 0;
			while (iter.hasNext()) {
				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
                if (time > curItem.time ) {
                    break;
                }
				Integer curIntObj = (Integer) curItem.value;
				summation += curIntObj.intValue();
				counter++;
			}
			double average = 0;
			if (counter != 0)
				average = (new Double(summation)).doubleValue() / counter;

			return new Double(average);
		} else if (mostCurrentValue instanceof Long) {
			long summation = 0;
			while (iter.hasNext()) {
				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
                if (time > curItem.time ) {
                    break;
                }
				Long curLongObj = (Long) curItem.value;
				summation += curLongObj.longValue();
				counter++;
			}
			double average = 0;
			if (counter != 0)
				average = summation / counter;

			return new Double(average);

		} else if (mostCurrentValue instanceof Double) {
			double summation = 0;
			while (iter.hasNext()) {
				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
                if (time > curItem.time ) {
                    break;
                }
				Double curDoubleObj = (Double) curItem.value;
				summation += curDoubleObj.doubleValue();
				counter++;
			}
			double average = 0;
			if (counter != 0)
				average = summation / counter;

			return new Double(average);
		}
		// if the pa value is NOT of double, long or int,
		// just 0
		return new Double(0);

    }


    @com.tibco.be.model.functions.BEFunction(
        name = "maxOfLast",
        synopsis = "This method returns the maximum value of the last n history entries.",
        signature = "Object maxOfLast(PropertyAtom pa, int last_n_times)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The target property for calculation."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "last_n_times", type = "int", desc = "The number of last history entries to be compared.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The maximum value of the last n history entries. The return object type is the same as the type of the value of 'pa'."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the maximum value of the last n history entries.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object maxOfLast(PropertyAtom pa, int last_n_times) {
    	//parameters checking
    	if (pa == null || last_n_times <= 0) {
    		return null;
    	}

    	Iterator iter = pa.backwardHistoryIterator();

		Object mostCurrentValue = pa.getValue();

		int counter = 0;
		if (mostCurrentValue instanceof Integer) {
			Integer maxValue = (Integer) mostCurrentValue;
			while (iter.hasNext()) {
				if (counter == last_n_times) {
					break;
				}

				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();

				Integer curIntObj = (Integer) curItem.value;
				if (curIntObj.compareTo(maxValue) > 0) {
					maxValue = curIntObj;
				}
				counter++;
			}

			return maxValue;
		} else if (mostCurrentValue instanceof Long) {
			Long maxValue = (Long) mostCurrentValue;
			while (iter.hasNext()) {
				if (counter == last_n_times) {
					break;
				}

				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();

				Long curLongObj = (Long) curItem.value;
				if (curLongObj.compareTo(maxValue) > 0) {
					maxValue = curLongObj;
				}
				counter++;
			}

			return maxValue;
		} else if (mostCurrentValue instanceof Double) {
			Double maxValue = (Double) mostCurrentValue;
			while (iter.hasNext()) {
				if (counter == last_n_times) {
					break;
				}

				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();

				Double curDoubleObj = (Double) curItem.value;
				if (curDoubleObj.compareTo(maxValue) > 0) {
					maxValue = curDoubleObj;
				}
				counter++;
			}

			return maxValue;
		}

		// if the pa value is NOT of double, long or int,
		// just return the most currentValue.
		return mostCurrentValue;
    }



    @com.tibco.be.model.functions.BEFunction(
        name = "maxSince",
        synopsis = "This method returns the maximum history value since the specified time.",
        signature = "Object maxSince(PropertyAtom pa, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The target property for calculation."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The specified time in millisecond.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The maximum value since the specified time. The return object type is the same as the type of the value of 'pa'."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the maximum history value since the specified time.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )

    public static Object maxSince(PropertyAtom pa, long time) {
    	//parameters checking
    	if (pa == null || time <= 0) {
    		return null;
    	}

    	Iterator iter = pa.backwardHistoryIterator();

		Object mostCurrentValue = pa.getValue();

		if (mostCurrentValue instanceof Integer) {
			Integer maxValue = (Integer) mostCurrentValue;
			while (iter.hasNext()) {
				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
                if (time > curItem.time ) {
                    break;
                }
				Integer curIntObj = (Integer) curItem.value;
                if (curIntObj.compareTo(maxValue) > 0) {
					maxValue = curIntObj;
				}
			}

			return maxValue;
		} else if (mostCurrentValue instanceof Long) {
			Long maxValue = (Long) mostCurrentValue;
			while (iter.hasNext()) {
				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
                if (time > curItem.time ) {
                    break;
                }
				Long curLongObj = (Long) curItem.value;
				if (curLongObj.compareTo(maxValue) > 0) {
					maxValue = curLongObj;
				}
			}

			return maxValue;
		} else if (mostCurrentValue instanceof Double) {
			Double maxValue = (Double) mostCurrentValue;
			while (iter.hasNext()) {
				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
                if (time > curItem.time ) {
                    break;
                }
				Double curDoubleObj = (Double) curItem.value;
				if (curDoubleObj.compareTo(maxValue) > 0) {
					maxValue = curDoubleObj;
				}
			}
			return maxValue;
		}

		// if the pa value is NOT of double, long or int,
		// just return the most currentValue.
		return mostCurrentValue;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "minOfLast",
        synopsis = "This method returns the minimum value of the last n history entries.",
        signature = "Object minOfLast(PropertyAtom pa, int last_n_times)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The target property for calculation."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "last_n_times", type = "int", desc = "The number of last history entries to be compared.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The minimum value of the last n history entries. The return object type is the same as the type of the value of 'pa'."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the minimum value of the last n history entries.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object minOfLast(PropertyAtom pa, int last_n_times) {
    	//parameters checking
    	if (pa == null || last_n_times <= 0) {
    		return null;
    	}

    	Iterator iter = pa.backwardHistoryIterator();

		Object mostCurrentValue = pa.getValue();

		int counter = 0;
		if (mostCurrentValue instanceof Integer) {
			Integer minValue = (Integer) mostCurrentValue;
			while (iter.hasNext()) {
				if (counter == last_n_times) {
					break;
				}

				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();

				Integer curIntObj = (Integer) curItem.value;
				if (curIntObj.compareTo(minValue) < 0) {
					minValue = curIntObj;
				}
				counter++;
			}

			return minValue;
		} else if (mostCurrentValue instanceof Long) {
			Long minValue = (Long) mostCurrentValue;
			while (iter.hasNext()) {
				if (counter == last_n_times) {
					break;
				}

				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();

				Long curLongObj = (Long) curItem.value;
				if (curLongObj.compareTo(minValue) < 0) {
					minValue = curLongObj;
				}
				counter++;
			}

			return minValue;
		} else if (mostCurrentValue instanceof Double) {
			Double minValue = (Double) mostCurrentValue;
			while (iter.hasNext()) {
				if (counter == last_n_times) {
					break;
				}

				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();

				Double curDoubleObj = (Double) curItem.value;
				if (curDoubleObj.compareTo(minValue) < 0) {
					minValue = curDoubleObj;
				}
				counter++;
			}

			return minValue;
		}

		// if the pa value is NOT of double, long or int,
		// just return the most currentValue.
		return mostCurrentValue;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "minSince",
        synopsis = "This method returns the minimum history value since the specified time.",
        signature = "Object minSince(PropertyAtom pa, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The target property for calculation."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The specified time in millisecond.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The minimum value since the specified time. The return object type is the same as the type of the value of 'pa'."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the minimum history value since the specified time.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )

    public static Object minSince(PropertyAtom pa, long time) {
    	//parameters checking
    	if (pa == null || time <= 0) {
    		return null;
    	}

    	Iterator iter = pa.backwardHistoryIterator();

		Object mostCurrentValue = pa.getValue();

		if (mostCurrentValue instanceof Integer) {
			Integer minValue = (Integer) mostCurrentValue;
			while (iter.hasNext()) {
				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
                if (time > curItem.time ) {
                    break;
                }
				Integer curIntObj = (Integer) curItem.value;
                if (curIntObj.compareTo(minValue) < 0) {
					minValue = curIntObj;
				}
			}

			return minValue;
		} else if (mostCurrentValue instanceof Long) {
			Long minValue = (Long) mostCurrentValue;
			while (iter.hasNext()) {
				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
                if (time > curItem.time ) {
                    break;
                }
				Long curLongObj = (Long) curItem.value;
				if (curLongObj.compareTo(minValue) < 0) {
					minValue = curLongObj;
				}
			}

			return minValue;
		} else if (mostCurrentValue instanceof Double) {
			Double minValue = (Double) mostCurrentValue;
			while (iter.hasNext()) {
				PropertyAtom.History curItem = (PropertyAtom.History) iter.next();
                if (time > curItem.time ) {
                    break;
                }
				Double curDoubleObj = (Double) curItem.value;
				if (curDoubleObj.compareTo(minValue) < 0) {
					minValue = curDoubleObj;
				}
			}
			return minValue;
		}

		// if the pa value is NOT of double, long or int,
		// just return the most currentValue.
		return mostCurrentValue;
    }
}
