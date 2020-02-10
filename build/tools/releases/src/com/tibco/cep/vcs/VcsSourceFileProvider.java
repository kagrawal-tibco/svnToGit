package com.tibco.cep.vcs;

/**
 * User: nprade
 * Date: 7/14/11
 * Time: 12:13 PM
 */
public interface VcsSourceFileProvider {


    byte[] getSourceFile(
            String path)
            throws Exception;

    byte[] getSourceFile(
            String path,
            long revision)
            throws Exception;

}
