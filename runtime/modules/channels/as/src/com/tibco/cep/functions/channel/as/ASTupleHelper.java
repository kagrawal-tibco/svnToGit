package com.tibco.cep.functions.channel.as;

/**
* Generated Code from String Template.
* Date : Sep 6, 2012
*/

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.tibco.as.space.ASException;
import com.tibco.as.space.DateTime;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.impl.data.ASDateTime;
import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionParamDescriptor;

public class ASTupleHelper {

    public static void clear (Object tuple) {
        if (tuple != null && tuple instanceof Tuple) {
            ((Tuple)tuple).clear();
        }
        return;
    }


    public static boolean containsKey (Object tuple, Object key) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).containsKey(key);
        }
        return false;
    }


    public static boolean containsValue (Object tuple, Object value) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).containsValue(value);
        }
        return false;
    }


    public static Object create () {
        return Tuple.create();
    }


    public static void deserialize (Object tuple, Object data) {
        if (tuple != null && tuple instanceof Tuple && data instanceof byte[]) {
            try {
                ((Tuple)tuple).deserialize((byte[])data);
            } catch (ASException e) {
                throw new RuntimeException(e);
            }
        }
        return;
    }


    public static Object entrySet (Object tuple) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).entrySet();
        }
        return null;
    }


    public static boolean exists (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).exists(key);
        }
        return false;
    }


    public static Object get (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).get(key);
        }
        return null;
    }


    public static Object getBlob (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).getBlob(key);
        }
        return null;
    }


    public static boolean getBoolean (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            Boolean b = ((Tuple)tuple).getBoolean(key);
            if (b != null) {
                return b;
            }
        }
        return false;
    }


    public static Object getChar (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            Character ch = ((Tuple)tuple).getChar(key);
            if (ch != null) {
                return ch;
            }
        }
        return '\0';
    }


    public static Object getDateTime (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            final DateTime dt = ((Tuple)tuple).getDateTime(key);
            if (dt != null) {
                final Calendar c = new GregorianCalendar();
                c.setTimeInMillis(dt.getTimeInMillis());
                return c;
            }
        }
        return null;
    }


    public static double getDouble (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            Double d = ((Tuple)tuple).getDouble(key);
            if (d != null) {
                return d;
            }
        }
        return -1;
    }


    public static Object getFieldNames (Object tuple) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).getFieldNames();
        }
        return null;
    }


    public static Object getFieldType (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).getFieldType(key);
        }
        return null;
    }


    public static double getFloat (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            Float f = ((Tuple)tuple).getFloat(key);
            if (f != null) {
                return f;
            }
        }
        return -1;
    }


    public static int getInt (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            Integer i = ((Tuple)tuple).getInt(key);
            if (i != null) {
                return i;
            }
        }
        return -1;
    }


    public static long getLong (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            Long l = ((Tuple)tuple).getLong(key);
            if (l != null) {
                return l;
            }
        }
        return -1;
    }


    public static int getShort (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            Short sh = ((Tuple)tuple).getShort(key);
            if (sh!= null) {
                return sh;
            }
        }
        return -1;
    }


    public static String getString (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).getString(key);
        }
        return null;
    }


    public static boolean isEmpty (Object tuple) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).isEmpty();
        }
        return false;
    }


    public static boolean isNull (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).isNull(key);
        }
        return false;
    }


    public static Object keySet (Object tuple) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).keySet();
        }
        return null;
    }


    public static Object put (Object tuple, String key, Object value) {
        if (tuple != null && tuple instanceof Tuple) {
            if (value instanceof Calendar) {
                return ((Tuple)tuple).put(key, new ASDateTime(((Calendar)value).getTimeInMillis()));
            }
            return ((Tuple)tuple).put(key, value);
        }
        return null;
    }


    public static void putAll (Object tuple, Object input) {
        if (tuple != null && input != null && tuple instanceof Tuple && input instanceof Tuple) {
            ((Tuple)tuple).putAll((Tuple)input);
        }
        return;
    }


    public static Object putBlob (Object tuple, String key, Object value) {
        if (tuple != null && tuple instanceof Tuple && value instanceof byte[]) {
            return ((Tuple)tuple).putBlob(key, (byte[])value);
        }
        return null;
    }


    public static Object putBoolean (Object tuple, String key, boolean value) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).putBoolean(key, value);
        }
        return null;
    }


    public static Object putChar (Object tuple, String key, Object value) {
        if (tuple != null && tuple instanceof Tuple && (value instanceof String || value instanceof Character)) {
            Character ch = ' ';
            if (value instanceof Character) {
                ch = (Character)value;
            }
            if (value instanceof String) {
                if (((String)value).length() > 0) {
                    ch = ((String)value).charAt(0);
                }
            }
            return ((Tuple)tuple).putChar(key, ch);
        }
        return null;
    }


    public static Object putDateTime (Object tuple, String key, Object value) {
        if (tuple != null && tuple instanceof Tuple && value instanceof Calendar) {
            return ((Tuple)tuple).putDateTime(key, new ASDateTime(((Calendar)value).getTimeInMillis()));
        }
        return null;
    }


    public static Object putDouble (Object tuple, String key, double value) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).putDouble(key, value);
        }
        return null;
    }


    public static Object putFloat (Object tuple, String key, Object value) {
        if (tuple != null && tuple instanceof Tuple && (value instanceof Float || value instanceof Double)) {
            Float f = null;
            if (value instanceof Float)
                f = (Float)value;
            else if (value instanceof Double)
                f = ((Double)value).floatValue();
            return ((Tuple)tuple).putFloat(key, f);
        }
        return null;
    }


    public static Object putInt (Object tuple, String key, int value) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).putInt(key, value);
        }
        return null;
    }


    public static Object putLong (Object tuple, String key, long value) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).putLong(key, value);
        }
        return null;
    }


    public static Object putShort (Object tuple, String key, Object value) {
        if (tuple != null && tuple instanceof Tuple && (value instanceof Short || value instanceof Integer)) {
            Short st = null;
            if (value instanceof Short)
                st = (Short) value;
            else if (value instanceof Integer)
                st = ((Integer) value).shortValue();
            return ((Tuple)tuple).putShort(key, st);
        }
        return null;
    }


    public static Object putString (Object tuple, String key, String value) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).putString(key, value);
        }
        return null;
    }


    public static void putObject (Object tuple, String key, Object value) {
        if (tuple != null && tuple instanceof Tuple) {
             ((Tuple)tuple).put(key, value);
        }
    }


    public static Object remove (Object tuple, String key) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).remove(key);
        }
        return null;
    }


    public static Object serialize (Object tuple) {
        if (tuple != null && tuple instanceof Tuple) {
            try {
                return ((Tuple)tuple).serialize();
            } catch (ASException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


    public static int size (Object tuple) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).size();
        }
        return 0;
    }


    public static Object values (Object tuple) {
        if (tuple != null && tuple instanceof Tuple) {
            return ((Tuple)tuple).values();
        }
        return null;
    }
}