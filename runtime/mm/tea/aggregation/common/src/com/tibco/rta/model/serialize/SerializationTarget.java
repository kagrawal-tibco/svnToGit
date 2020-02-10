package com.tibco.rta.model.serialize;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/10/12
 * Time: 2:46 PM
 * Intermediate stage of serialization where serialization is done to a target.
 */
public interface SerializationTarget<T> {

    /**
     * Persist the target to file based on type of serialization.
     *
     * @param file
     * @throws Exception
     */
    void persist(File file, T persistee) throws Exception;

    /**
     * Persist the target to writer based on type of serialization.
     *
     * @param writer
     * @throws Exception
     */
    void persist(Writer writer, T persistee) throws Exception;

    /**
     * Persist the target to outputstream based on type of serialization.
     *
     * @param outputStream
     * @throws Exception
     */
    void persist(OutputStream outputStream, T persistee) throws Exception;
}
