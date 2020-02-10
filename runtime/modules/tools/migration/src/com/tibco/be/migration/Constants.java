package com.tibco.be.migration;

/*
 * Author: Ashwin Jayaprakash Date: Feb 25, 2008 Time: 1:34:19 PM
 */

public interface Constants {
    /**
     * {@value}
     */
    public String MAIN_FILE_EXTENSION = ".bexm";

    /**
     * {@value}
     */
    public String OUT_OF_BAND_FILE_EXTENSION = ".bexo";

    /**
     * {@value}
     */
    public String DEFAULT_FILE_ENCODING = "UTF-8";

    /**
     * {@value}
     */
    public char LINE_SEPARATOR = '\n';

    /**
     * {@value}
     */
    public char LINE_SEPARATOR_OPTIONAL_PREFIX = '\r';

    /**
     * {@value}
     */
    public char COLUMN_SEPARATOR = ',';

    /**
     * {@value}
     */
    public char COMMENT_PREFIX = '#';

    /**
     * {@value}
     */
    public char NON_NULL_COLUMN_START_END = '\"';

    /**
     * {@value}
     */
    public String OUT_OF_BAND_REF_PREFIX = "file://";

    /**
     * {@value}
     */
    public String OUT_OF_BAND_FILE_NAME_SUBSTRING = ".";

    /**
     * {@value}
     */
    public String ROLLED_FILE_NAME_SUBSTRING = ".";

    /**
     * {@value}
     */
    // 64MB.
    public int DEFAULT_MAX_INLINE_STR_LEN_BYTES = 1 << 26;

    /**
     * {@value}
     */
    // 2GB - 1024.
    public long DEFAULT_MAX_FILE_SIZE_BYTES = ((1 << 30) * 2) - 1024;
}
