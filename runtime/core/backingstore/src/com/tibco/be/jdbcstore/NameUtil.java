package com.tibco.be.jdbcstore;

import java.security.MessageDigest;

public class NameUtil {

    public static String normalizeName(String name) {
        if (name == null)
            throw new IllegalArgumentException("name");

        int len = name.length();
        if (len == 0)
            return name;

        StringBuffer newName = new StringBuffer(len);

        if (Character.isDigit(name.charAt(0))) {
            newName.append("_");
        }

        for (int i = 0; i < len; i++) {
            char ch = name.charAt(i);
            if (Character.isLetterOrDigit(ch) || ch == '_') /*|| ch == '$' || ch == '#')*/ {
                newName.append(ch);
            }
            else {
                newName.append("_");
            }
        }
        return newName.toString();
    }

    public static StringBuffer mangleName(String originalName, int maxLen) {
        return mangleName(originalName, maxLen, null);
    }

    public static StringBuffer mangleName(String originalName, int maxLen, StringBuffer newName) {
        return mangleName(originalName, maxLen, newName, true);
    }

    public static StringBuffer mangleName(String originalName, int maxLen, StringBuffer newName, boolean addUnderScore) {
        if (originalName == null || maxLen < 0)
            throw new IllegalArgumentException("originalName or maxLen");

        if (newName == null)
            newName = new StringBuffer();

        originalName = normalizeName(originalName);
        if (originalName.length() < maxLen || maxLen < 5)
            return newName.append(originalName);

        int newLen = (addUnderScore ? maxLen - 3 : maxLen - 2);
        newName.append(originalName.substring(0, newLen));
        try {
            MessageDigest md = DigestUtil.allocateDigest("MD5");

            md.update(DigestUtil.string2Bytes("" + originalName.length() + ":" + originalName));
            StringBuffer digest = DigestUtil.foldBytesTo2Digits(md.digest());
            if (addUnderScore)
                newName.append("_").append(digest);
            else
                newName.append(digest);
            DigestUtil.freeDigest(md);
            return newName;
        } catch (Exception e) {
            System.err.println("Failed to mangle name:" + e);
            e.printStackTrace();
        }
        return newName;
    }

    private static StringBuffer mangle2Names(String name1, int maxLen1, String name2, int maxLen2) {
        StringBuffer newName = new StringBuffer();

        name1 = normalizeName(name1);
        name2 = normalizeName(name2);

        mangleName(name1, maxLen1, newName, false);
        newName.append("_");
        mangleName(name2, maxLen2, newName, false);
        return newName;
    }

    private static StringBuffer mangleColumnName(String tableName, int maxTable, String columnName, int maxColumn) {
        return mangle2Names(tableName, maxTable, columnName, maxColumn);
    }
}
