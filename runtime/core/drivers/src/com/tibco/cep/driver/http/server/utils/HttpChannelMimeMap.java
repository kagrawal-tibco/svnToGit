/**
 *(c) Copyright 2011, TIBCO Software Inc.  All rights reserved.
 *
 * LEGAL NOTICE:  This source code is provided to specific authorized end
 * users pursuant to a separate license agreement.  You MAY NOT use this
 * source code if you do not have a separate license from TIBCO Software
 * Inc.  Except as expressly set forth in such license agreement, this
 * source code, or any portion thereof, may not be used, modified,
 * reproduced, transmitted, or distributed in any form or by any means,
 * electronic or mechanical, without written permission from
 * TIBCO Software Inc.
 */

package com.tibco.cep.driver.http.server.utils;

import java.util.Hashtable;

/**
 * @author abhijit
 *
 */
public class HttpChannelMimeMap {
	   
    private Hashtable<String,String> addOnMappings = new Hashtable<String,String>();

    public static final Hashtable<String,String> mimeMap =
        new Hashtable<String,String>();
    static {
        mimeMap.put("txt", "text/plain");
        mimeMap.put("html","text/html");
        mimeMap.put("htm", "text/html");
        mimeMap.put("gif", "image/gif");
        mimeMap.put("jpg", "image/jpeg");
        mimeMap.put("jpe", "image/jpeg");
        mimeMap.put("jpeg", "image/jpeg");
        mimeMap.put("png", "image/png");
        mimeMap.put("java", "text/plain");
        mimeMap.put("body", "text/html");
        mimeMap.put("rtx", "text/richtext");
        mimeMap.put("tsv", "text/tab-separated-values");
        mimeMap.put("etx", "text/x-setext");
        mimeMap.put("ps", "application/x-postscript");
        mimeMap.put("class", "application/java");
        mimeMap.put("csh", "application/x-csh");
        mimeMap.put("sh", "application/x-sh");
        mimeMap.put("tcl", "application/x-tcl");
        mimeMap.put("tex", "application/x-tex");
        mimeMap.put("texinfo", "application/x-texinfo");
        mimeMap.put("texi", "application/x-texinfo");
        mimeMap.put("t", "application/x-troff");
        mimeMap.put("tr", "application/x-troff");
        mimeMap.put("roff", "application/x-troff");
        mimeMap.put("man", "application/x-troff-man");
        mimeMap.put("me", "application/x-troff-me");
        mimeMap.put("ms", "application/x-wais-source");
        mimeMap.put("src", "application/x-wais-source");
        mimeMap.put("zip", "application/zip");
        mimeMap.put("bcpio", "application/x-bcpio");
        mimeMap.put("cpio", "application/x-cpio");
        mimeMap.put("gtar", "application/x-gtar");
        mimeMap.put("shar", "application/x-shar");
        mimeMap.put("sv4cpio", "application/x-sv4cpio");
        mimeMap.put("sv4crc", "application/x-sv4crc");
        mimeMap.put("tar", "application/x-tar");
        mimeMap.put("ustar", "application/x-ustar");
        mimeMap.put("dvi", "application/x-dvi");
        mimeMap.put("hdf", "application/x-hdf");
        mimeMap.put("latex", "application/x-latex");
        mimeMap.put("bin", "application/octet-stream");
        mimeMap.put("oda", "application/oda");
        mimeMap.put("pdf", "application/pdf");
        mimeMap.put("ps", "application/postscript");
        mimeMap.put("eps", "application/postscript");
        mimeMap.put("ai", "application/postscript");
        mimeMap.put("rtf", "application/rtf");
        mimeMap.put("nc", "application/x-netcdf");
        mimeMap.put("cdf", "application/x-netcdf");
        mimeMap.put("cer", "application/x-x509-ca-cert");
        mimeMap.put("exe", "application/octet-stream");
        mimeMap.put("gz", "application/x-gzip");
        mimeMap.put("Z", "application/x-compress");
        mimeMap.put("z", "application/x-compress");
        mimeMap.put("hqx", "application/mac-binhex40");
        mimeMap.put("mif", "application/x-mif");
        mimeMap.put("ief", "image/ief");
        mimeMap.put("tiff", "image/tiff");
        mimeMap.put("tif", "image/tiff");
        mimeMap.put("ras", "image/x-cmu-raster");
        mimeMap.put("pnm", "image/x-portable-anymap");
        mimeMap.put("pbm", "image/x-portable-bitmap");
        mimeMap.put("pgm", "image/x-portable-graymap");
        mimeMap.put("ppm", "image/x-portable-pixmap");
        mimeMap.put("rgb", "image/x-rgb");
        mimeMap.put("xbm", "image/x-xbitmap");
        mimeMap.put("xpm", "image/x-xpixmap");
        mimeMap.put("xwd", "image/x-xwindowdump");
        mimeMap.put("au", "audio/basic");
        mimeMap.put("snd", "audio/basic");
        mimeMap.put("aif", "audio/x-aiff");
        mimeMap.put("aiff", "audio/x-aiff");
        mimeMap.put("aifc", "audio/x-aiff");
        mimeMap.put("wav", "audio/x-wav");
        mimeMap.put("mpeg", "video/mpeg");
        mimeMap.put("mpg", "video/mpeg");
        mimeMap.put("mpe", "video/mpeg");
        mimeMap.put("qt", "video/quicktime");
        mimeMap.put("mov", "video/quicktime");
        mimeMap.put("avi", "video/x-msvideo");
        mimeMap.put("movie", "video/x-sgi-movie");
        mimeMap.put("avx", "video/x-rad-screenplay");
        mimeMap.put("wrl", "x-world/x-vrml");
        mimeMap.put("mpv2", "video/mpeg2");
        mimeMap.put("xml", "text/xml");
        mimeMap.put("xsl", "text/xml");        
        mimeMap.put("svg", "image/svg+xml");
        mimeMap.put("svgz", "image/svg+xml");
        mimeMap.put("wbmp", "image/vnd.wap.wbmp");
        mimeMap.put("wml", "text/vnd.wap.wml");
        mimeMap.put("wmlc", "application/vnd.wap.wmlc");
        mimeMap.put("wmls", "text/vnd.wap.wmlscript");
        mimeMap.put("wmlscriptc", "application/vnd.wap.wmlscriptc");
    }
    
    public void addMimeType(final String extn, final String type) {
        addOnMappings.put(extn, type.toLowerCase());
    }
    

    public void removeMimeType(final String extn) {
    	addOnMappings.remove(extn.toLowerCase());
    }
   
    public String getMimeType(final String extn) {
        String type = addOnMappings.get(extn.toLowerCase());
        if( type == null ) {
        	type= mimeMap.get(extn);
        }
        return type;
    }

    public String getMimeTypeForFile(final String fileName) {
    	final String extn=getFileExtension( fileName );
        if (extn!=null) {
            return getMimeType(extn);
        } else {
            return null;
        }
    }
    
    private static String getFileExtension(final String fileName ) {
    	final int length=fileName.length();
    	int lastIndex = fileName.lastIndexOf('#');
        if( lastIndex== -1 ) lastIndex=length;
        int i = fileName.lastIndexOf('.', lastIndex );
        if (i != -1) {
             return  fileName.substring(i + 1, lastIndex );
        } else {
            return null;
        }
    }
}
