/*******************************************************************************
 * Copyright 1996 by TIBCO, Inc.
 * ALL RIGHTS RESERVED
 */

package com.tibco.cep.mapper.share.util;



/**
 * The Path class implements an ordered set of names. It includes some methods
 * for building those sets and for manipulating the sets of names. The string
 * representation for the path consists of separating each name in the path by
 * a dot or some other separator and keeping the names in order from first to
 * last. The class exists to help manipulate an array of names. String.intern()
 * is called on each String in the array so that comparisons can be made more
 * efficiently.
 *
 * @version	$LastChangedRevision$, $LastChangedDate$
 * @author	Peter Ohler
 */
public final class Path implements Comparator {
    /**
     * These are the default separator characters for new paths.
     */
    public String	defaultSeparators = "./\\";

    public static final String	emptyStr = "".intern();

    /**
     * Creates an empty path with default separators.
     */
    public Path() {
        names = null;
        separators = defaultSeparators;
    }
    /**
     * This constructor parses a String into an array of interned Strings
     * and then stores them in the names variable.
     * @param str the String to be parsed
     */
    public Path(String str) {
        set(str, null);
    }
    /**
     * This constructor parses a String into an array of interned Strings
     * and then stores them in the names variable. The sepChars input
     * parameter is used as the separators for the instance.
     * @param str the String to be parsed
     * @param sepChars the separators to use for the Path
     */
    public Path(String str, String sepChars) {
        set(str, sepChars);
    }
    /**
     * Creates a Path from the provided String array. The array is not
     * copied but used directly. The strings in the array are NOT interned
     * by this constructor. They must be interned before being passed to
     * this constructor.
     * @param names the value for the names array of the Path
     */
    public Path(String[] names) {
        this(names, null, false);
    }
    /**
     * Creates a Path from the provided String array. The array is not
     * copied but used directly unless the names are to be interned. If the
     * strings are to be interned a new names array is created.
     * @param names the value for the names array of the Path
     * @param sepChars the separators to use for the Path
     * @param doIntern flag indicating if each names should be interned
     */
    public Path(String[] names, String sepChars, boolean doIntern) {
        if (doIntern &&
        null != names) {
            int	i;

            this.names = new String[names.length];
            for (i = names.length - 1; 0 <= i; i--) {
                //				this.names[i] = names[i].toLowerCase().intern();
                this.names[i] = names[i].intern();
            }
        } else {
            this.names = names;
        }
        separators = sepChars;
        if (null == separators) {
            separators = defaultSeparators;
        }
    }
    /**
     * Allocates the names array and sets the names in this Path to those
     * names in the path input parameter when this Path is created.
     * @param path the Path to copy.
     */
    public Path(Path path) {
        set(path);
    }
    /**
     * Creates a Path will an allocate names array where each name is null.
     * @param pathLength the length of the Path
     */
    public Path(int pathLength) {
        this(pathLength, null);
    }
    /**
     * Creates a Path will an allocate names array where each name is null.
     * @param pathLength the length of the Path
     * @param sepChars the separators to use for the Path
     */
    public Path(int pathLength, String sepChars) {
        if (0 >= pathLength) {
            names = null;
        } else {
            int	i;

            names = new String[pathLength];
            for (i = names.length - 1; 0 <= i; i--) {
                names[i] = null;
            }
        }
        separators = sepChars;
        if (null == separators) {
            separators = defaultSeparators;
        }
    }
    /**
     * This method parses a String into an array of interned Strings and
     * then stores them in the names variable.
     * @param str the String to be parsed
     */
    public void set(String str) {
        set(str, null);
    }
    /**
     * This method parses a String into an array of interned Strings and
     * then stores them in the names variable.
     * @param str the String to be parsed
     * @param sepChars the separators to use for the Path
     */
    public void set(String str, String sepChars) {
        separators = sepChars;
        if (null == separators) {
            separators = defaultSeparators;
        }
        int	len;

        if (null == str ||
        0 >= (len = str.length())) {
            names = null;

            return;
        }
        int		saCnt = 8;
        String[]	sa = new String[saCnt];
        int		i, j, k;
        int		start = 0, sepCnt = separators.length();
        int		pos, cnt = 0;

        do {
            i = len;
            for (j = 0; j < sepCnt; j++) {
                pos = str.indexOf(separators.charAt(j), start);
                if (0 <= pos &&
                pos < i) {
                    i = pos;
                }
            }
            if (saCnt <= cnt) {
                String[]	tmp = sa;

                saCnt = cnt + cnt / 2;
                sa = new String[saCnt];
                for (k = 0; k < cnt; k++) {
                    sa[k] = tmp[k];
                }
            }
            sa[cnt++] = str.substring(start, i).intern();
            start = i + 1;
            if (start == len) {
                if (saCnt <= cnt) {
                    String[]	tmp = sa;

                    saCnt = cnt + cnt / 2;
                    sa = new String[saCnt];
                    for (k = 0; k < cnt; k++) {
                        sa[k] = tmp[k];
                    }
                }
                sa[cnt++] = emptyStr;
            }
        } while (start < len);
        if (saCnt == cnt) {
            names = sa;
        } else {
            names = new String[cnt];
            for (k = 0; k < cnt; k++) {
                names[k] = sa[k];
            }
        }
    }
    /**
     * Allocates the names array and sets the names in this Path to those
     * names in the path input parameter.
     * @param path the Path to copy.
     */
    public void set(Path path) {
        String[]	pn;

        if (null == path ||
        null == (pn = path.names)) {
            names = null;
            separators = defaultSeparators;
        } else {
            int	cnt = pn.length;

            names = new String[cnt];
            for (cnt--; 0 <= cnt; cnt--) {
                names[cnt] = pn[cnt];
            }
            separators = path.separators;
        }
    }
    /**
     * The names of the path parameter are used to set replace the names in
     * this Path beginning with the start position of this Path and
     * continuing for the length specified.
     * @param path the path to use to replace this Path's names
     * @param start the starting position for the replacement
     * @param length the number of names to replace
     */
    public void replace(Path path, int start, int length) {
        String[]	pn;

        if (null != path &&
        null != (pn = path.names) &&
        null != names) {
            int	cnt = names.length;

            if (start < cnt) {
                if (start < 0) {
                    start = 0;
                }
                if (0 >= length ||
                length > cnt - start) {
                    length = cnt - start;
                }
                //				System.out.println("start: " + start +
                //						   "  length: " + length +
                //						   "  names.length: " + names.length +
                //						   "  path.size(): " + path.size());
                for (length--; 0 <= length; length--) {
                    names[start + length] = pn[length];
                }
            }
        }
    }
    /**
     * The names of the path parameter are used to set replace the names in
     * this Path beginning with the start position of this Path.
     * @param path the path to use to replace this Path's names
     * @param start the starting position for the replacement
     */
    public final void replace(Path path, int start) {
        // let an Exception be thrown if arguments are bad
        String[]	pn = path.names;
        int		i;

        for (i = pn.length - 1; 0 <= i; i--) {
            names[i + start] = pn[i];
        }
    }
    /**
     * Starting with the first name, this method replaces all the names
     * in the path with the ones in the pathNames array. The names are
     * interned if the doIntern flag is true. The doIntern flag should only
     * be set to false if all the Strings in the pathNames array have
     * already been interned.
     * @param pathNames the new name array
     * @param doIntern the flag to determine if String.intern() should be
     *		   called
     */
    public final void replace(String[] pathNames, boolean doIntern) {
        // let an Exception be thrown if arguments are bad
        int	i;

        if (doIntern) {
            for (i = pathNames.length - 1; 0 <= i; i--) {
                names[i] = pathNames[i].intern();
            }
        } else {
            for (i = pathNames.length - 1; 0 <= i; i--) {
                names[i] = pathNames[i];
            }
        }
    }
    /**
     * This methods sets the name of path at the designated position to the
     * str input parameter. The String is interned before it is added.
     * @param str the String to place in the designated position
     * @param pos the position for the str
     */
    public final void replace(String str, int pos) throws Exception {
        if (0 > pos ||
        null == names ||
        names.length <= pos) {
            throw new IndexOutOfBoundsException(String.valueOf(pos));
        }
        //		names[pos] = (null == str) ? null : str.toLowerCase().intern();
        names[pos] = (null == str) ? null : str.intern();
    }
    /**
     * This method adds a name to the end of the names array, modifying the
     * Path. The str String is interned before it is added.
     * @param str the String to add to the end of the Path
     */
    public void grow(String str) {
        if (null == str) {
            return;
        }
        str = str.intern();
        if (null == names) {
            names = new String[1];
            names[0] = str;
        } else {
            int		cnt = names.length;
            String[]	tmp = names;

            names = new String[cnt + 1];
            names[cnt] = str;
            for (cnt--; 0 <= cnt; cnt--) {
                names[cnt] = tmp[cnt];
            }
        }
    }

    public void shrink() {
        if (null == names) {
            return;
        }
        int	cnt = names.length - 1;

        if (0 >= cnt) {
            names = null;
        } else {
            String[]	tmp = names;
            int		i;

            names = new String[cnt];
            for (i = cnt - 1; 0 <= i; i--) {
                names[i] = tmp[i];
            }
        }
    }
    /**
     * This method creates a new Path with the same names as this one and
     * then appends the str input parameter to the end of the names array.
     * This Path is not changed. The str String is interned before it is
     * added.
     * @param str the String to add to the end of the Path
     * @return The new path is returned.
     */
    public Path append(String str) {
        if (null == str) {
            return new Path(this);
        }
        if (null == names) {
            String[]	sa = { str };

            return new Path(sa, separators, true);
        }
        int		len = names.length;
        String[]	strs = new String[len + 1];

        //		strs[len] = str.toLowerCase().intern();
        strs[len] = str.intern();
        for (len--; 0 <= len; len--) {
            strs[len] = names[len];
        }
        return new Path(strs, separators, false);
    }
    /**
     * This method appends a Path to the end of this Path and returns the
     * new Path. Neither this Path or the path input parameter are changed.
     * @param path the Path to add to the end of this Path
     * @return The new path is returned.
     */
    public Path append(Path path) {
        String[]	pn;

        if (null == path ||
        null == (pn = path.names)) {
            return new Path(this);
        }
        if (null == names) {
            return new Path(path);
        }
        int		i, len = names.length;
        int		pathLen = pn.length;
        String[]	strs = new String[pathLen + len];

        for (i = 0; i < len; i++) {
            strs[i] = names[i];
        }
        for (i = 0; i < pathLen; i++) {
            strs[i + len] = pn[i];
        }
        return new Path(strs, separators, false);
    }
    /**
     * This method creates a new Path with the name inserted before the
     * position specified and returns the new Path. This Path is not
     * changed. The String is interned before it is added.
     * @param str the String to insert
     * @return The new path is returned.
     */
    public Path insert(String str, int index) {
        if (null != str) {
            str = str.intern();
        }
        if (null == names) {
            String[]	newNames = { str };

            return new Path(newNames, separators, false);
        }
        int		i, len = names.length;
        String[]	strs = new String[len + 1];

        if (0 > index) {
            index = 0;
        } else if (len < index) {
            index = len;
        }
        for (i = 0; i < index; i++) {
            strs[i] = names[i];
        }
        strs[i] = str;
        for (; i < len; i++) {
            strs[i + 1] = names[i];
        }
        return new Path(strs, separators, false);
    }
    /**
     * This method reverse the order of the names in the path. This changes
     * the Path. It does not make a copy of the Path.
     */
    public final void reverse() {
        if (null == names) {
            return;
        }
        int	i, j;
        String	temp;

        for (i = 0, j = names.length - 1; i < j; i++, j--) {
            temp = names[i];
            names[i] = names[j];
            names[j] = temp;
        }
    }
    /**
     * If the path input parameter is the same as the first names in this
     * Path true is returned. If the path parameter is longer than this Path
     * or if any of the names differ then false is returned.
     * @param path the Path to compare this one to
     * @return True is returned if this Path begins with the names in the
     *	   input path, false otherwise.
     */
    public final boolean beginsWith(Path path) {
        if (null == path) {
            return false;
        }
        if (null == names ||
        null == path.names) {
            return (names == path.names);
        }
        int	i;
        int	thisLen = names.length;
        int	pathLen = path.names.length;

        if (pathLen > thisLen) {
            return false;
        }

        for (i = 0; i < pathLen; i++) {
            if (names[i] != path.names[i]) {
                return false;
            }
        }
        return true;
    }
    /**
     * Returns true is all the names in this Path are the same as those in
     * the path input parameter.
     * @param path the Path to compare this one to
     * @return True is returned if the two paths are the same, false is
     *	   returned if they are not the same.
     */
    public final boolean equals(Path path) {
        if (null == path) {
            return false;
        }
        String[]	pNames = path.names;

        if (null == names ||
        null == pNames) {
            return (names == pNames);
        }
        int	i;
        int	thisLen = names.length;
        int	pathLen = pNames.length;

        if (pathLen != thisLen) {
            return false;
        }
        for (i = pathLen - 1; 0 <= i; i--) {
            if (names[i] != pNames[i]) {
                return false;
            }
        }
        return true;
    }
    /**
     * Returns the hash code value for this object, which is the
     * hash code for the string representing the path.
     * @return Returns a hash code value for the object
     */
    public int hashCode() {
        return toString().hashCode();
    }

    public final boolean contains(String name) {
        return contains(name, true);
    }
    public final boolean contains(String name, boolean doIntern) {
        if (null == names) {
            return false;
        }
        if (null == name) {
            name = emptyStr;
        } else if (doIntern) {
            name = name.intern();
        }
        int	i;

        for (i = names.length - 1; 0 <= i; i--) {
            if (name == names[i]) {
                return true;
            }
        }
        return false;
    }
    /**
     * Compares the obj input parameter to this instance. if the obj input
     * parameter is not a Path then 1 is returned. If the obj is a Path with
     * the same names then 0 is returned. If this instance is shorter or
     * alphabetically lower than the obj instance then a negative number is
     * returned. If the obj is greater than this instance then a positive
     * number is returned.
      
     */
    public final int compareTo(Object obj) {
        if (!(obj instanceof Path)) {
            return 1;
        }
        Path	path = (Path)obj;

        if (null == names ||
        null == path.names) {
            return (names == path.names) ? 0 :
                (null == names) ? -1 : 1;
        }
        int	thisLen = names.length;
        int	pathLen = path.names.length;
        int	i, cnt = (thisLen > pathLen) ? pathLen : thisLen;

        for (i = 0; i < cnt; i++) {
            if (names[i] != path.names[i]) {
                return names[i].compareTo(path.names[i]);
            }
        }
        return thisLen - pathLen;
    }
    /**
     * Returns true is all the names in this Path are the same as those in
     * the path input parameter. Wildcard patterns patterns in the input
     * pattern will match any name in this Path. The wildOne and wildRest
     * Strings must be interned first.
     * @param pattern the Path to compare this one to
     * @param wildOne match for a single name
     * @param wildRest match for all the rest after and including this one
     * @return True is returned if the two paths are the same, false is
     *	   returned if they are not the same.
     */
    public final boolean match(Path		pattern,
    String	wildOne,
    String	wildRest) {
        return match(pattern, wildOne, wildRest, true);
    }
    /**
     * Returns true is all the names in this Path are the same as those in
     * the path input parameter. Wildcard patterns patterns in the input
     * pattern will match any name in this Path. The wildOne and wildRest
     * Strings must be interned first.
     * @param pattern the Path to compare this one to
     * @param wildOne match for a single name
     * @param wildRest match for all the rest after and including this one
     * @param doIntern if true the wildOne and wildRest will be interned
     * @return True is returned if the two paths are the same, false is
     *	   returned if they are not the same.
     */
    public final boolean match(Path		pattern,
    String	wildOne,
    String	wildRest,
    boolean	doIntern) {
        if (null == pattern) {
            return false;
        }
        String[]	pNames = pattern.names;

        if (null == names ||
        null == pNames) {
            return (names == pNames);
        }
        if (doIntern) {
            wildOne = wildOne.intern();
            wildRest = wildRest.intern();
        }
        int	i;
        int	thisLen = names.length;
        int	patternLen = pNames.length;
        String	n, pn;

        if (patternLen > thisLen) {
            return false;
        }
        for (i = 0; i < patternLen; i++) {
            n = names[i];
            pn = pNames[i];
            if (wildRest == pn) {
                return true;
            }
            if (n != pn &&
            wildOne != pn) {
                return false;
            }
        }
        return (patternLen == thisLen);
    }
    /**
     * Creates a new Path that is a subset of this Path. The first position
     * is 0. All the names after the start position will be in the new Path.
     * @param start the first name of this path to use in the new Path
     * @return The new path is returned.
     */
    public final Path subPath(int start) {
        return subPath(start, names.length - start);
    }
    /**
     * Creates a new Path that is a subset of this Path. The first position
     * is 0.
     * @param start the first name of this path to use in the new Path
     * @param length the number of names in the new Path
     * @return The new path is returned.
     */
    public final Path subPath(int start, int length) {
        if (0 >= length) {
            return null;
        }
        Path		path = new Path(length, separators);
        String[]	pn = path.names;
        int		i;

        for (i = 0; i < length; i++) {
            pn[i] = names[start + i];
        }
        return path;
    }
    /**
     * Returns the number of names in the path.
     */
    public final int size() {
        return (null == names) ? 0 : names.length;
    }
    /**
     * Returns the name at the specified location in the path. The first
     * position is position 0.
     * @param pos the position in the path
     * @return The name at the position specified, or null if the position
     *	   is out of bounds.
     */
    public final String nameAt(int pos) {
        return (null == names) ? null : names[pos];
    }

    /**
     * Returns the parent path of this path.
     */
    public final Path getParent() {
        return subPath(0,size()-1);
    }

    /**
     * Returns the first name in the path.
     */
    public final String firstName() {
        return (null == names) ? null : names[0];
    }
    /**
     * Returns the last name in the path.
     */
    public final String lastName() {
        return (null == names) ? null : names[names.length - 1];
    }
    /**
     * Check if the Path is empty.
     * @return Returns true if the path is empty, false otherwise.
     */
    public final boolean isEmpty() {
        return (null == names);
    }
    /**
     * This method creates a String representation of the path with the
     * first character in the separators String as the separater character.
     * @return The string representation of the path is returned, or null is
     *	   returned if there are no names in the path.
     */
    public final String pathStr() {
        return pathStr(separators.charAt(0));
    }
    /**
     * This method creates a String representation of the path with the
     * sep parameter character as the separater character.
     * @return The string representation of the path is returned, or null is
     *	   returned if there are no names in the path.
     */
    public final String pathStr(char sep) {
        if (null == names) {
            return emptyStr;
        }
        StringBuffer	buf = new StringBuffer();
        int		i, end = names.length - 1;

        for (i = 0; i <= end; i++) {
            buf.append(names[i]);
            if (i != end) {
                buf.append(sep);
            }
        }
        return new String(buf);
    }
    /**
     * Returns a string representation of the Object.
     */
    public String toString() {
        return pathStr();
    }
    /**
     * Returns the separators for the path.
     */
    public String getSeparators() {
        return separators;
    }
    /**
     * Set the separators for the path. The first character is used when
     * creating a String representation of the Object.
     */
    public void setSeparators(String separators) {
        this.separators = separators;
    }

    /**
     * Returns true if the Path is a relative and not an absolute path name.
     * @return Returns true if the Path is a relative and not an absolute
     * path name.
     */
    public final boolean isRelative() {
        return (null != names &&
        emptyStr == names[0]);
    }
    /**
     * Creates a new Path with the same names as this Path but with all the
     * names converted to lowercase.
     * @return The new Path is returned.
     */
    public Path toLowerCase() {
        if (null == names) {
            return this;
        }
        int	i;
        String	s, ls;

        for (i = names.length - 1; 0 <= i; i--) {
            s = names[i];
            ls = s.toLowerCase();
            if (s != ls) {
                break;
            }
        }
        if (0 > i) {
            return this;
        }
        int		cnt = names.length;
        String[]	newNames = new String[cnt];

        for (i = cnt - 1; 0 <= i; i--) {
            newNames[i] = names[i].toLowerCase().intern();
        }
        return new Path(newNames);
    }
    /**
     * Creates a new Path with the same names as this Path but with all the
     * names converted to uppercase.
     * @return The new Path is returned.
     */
    public Path toUpperCase() {
        if (null == names) {
            return this;
        }
        int	i;
        String	s, ls;

        for (i = names.length - 1; 0 <= i; i--) {
            s = names[i];
            ls = s.toUpperCase();
            if (s != ls) {
                break;
            }
        }
        if (0 > i) {
            return this;
        }
        int		cnt = names.length;
        String[]	newNames = new String[cnt];

        for (i = cnt - 1; 0 <= i; i--) {
            newNames[i] = names[i].toUpperCase().intern();
        }
        return new Path(newNames);
    }
    String[]	names;
    String		separators;
}

