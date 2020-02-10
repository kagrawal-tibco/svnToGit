package com.tibco.cep.vcs;

import java.util.List;

/**
 * User: nprade
 * Date: 6/24/11
 * Time: 6:26 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public interface ChangedSourceFilesProvider {

    List<String> getAddedFilePaths(String path, long revisionStart, long revisionEnd) throws Exception;

    List<String> getAllChangedFilePaths(String path, long revisionStart, long revisionEnd) throws Exception;

    List<String> getDeletedFilePaths(String path, long revisionStart, long revisionEnd) throws Exception;

    List<String> getModifiedFilePaths(String path, long revisionStart, long revisionEnd) throws Exception;

    List<String> getRemainingChangedFilePaths(String branchPath, long revisionStart, long revisionEnd) throws Exception;

}
