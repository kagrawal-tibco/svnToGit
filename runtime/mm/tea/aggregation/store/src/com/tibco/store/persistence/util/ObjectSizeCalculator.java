package com.tibco.store.persistence.util;

public class ObjectSizeCalculator {

    public static long getSizeInBytes(Object obj) {
        if (obj == null) {
            return 0;
        }
        // PORT: Need to be careful, haven't taken into account encoding (UTF-8, UTF-16 etc)
        else if (obj instanceof String) {
            return ((String) obj).getBytes().length;
        } else if (obj instanceof Double || obj instanceof Long) {
            return 8;
        } else if (obj instanceof Float || obj instanceof Integer) {
            return 4;
        } else if (obj instanceof Short || obj instanceof Character) {
            return 2;
        } else if (obj instanceof Byte) {
            return 1;
        }

        return obj.toString().length();
    }

}
