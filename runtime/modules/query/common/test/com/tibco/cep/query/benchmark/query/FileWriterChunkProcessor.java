package com.tibco.cep.query.benchmark.query;

import java.io.*;
import java.util.Collection;
import java.util.List;

/*
* Author: Ashwin Jayaprakash Date: Jun 19, 2008 Time: 2:03:23 PM
*/
public abstract class FileWriterChunkProcessor<R> implements Processor.CustomChunkProcessor<R> {
    /**
     * {@value}
     */
    public static final String FILE_EXTENSION = ".txt";

    /**
     * {@value}
     */
    public static final String FILE_COLUMN_SEPARATOR = ",";

    /**
     * {@value}
     */
    public static final String FILE_ROW_SEPARATOR = "\r\n";

    protected Writer[] writers;

    protected abstract String getOutputDir();

    protected abstract String getOutputFileName();

    protected abstract String[] getFileColumnHeaders();

    public void init(int numLanes) {
        String path = getOutputDir();
        if (path == null) {
            path = System.getProperty("user.dir");
        }

        File pathFile = new File(path);
        pathFile.mkdirs();
        if (pathFile.isDirectory()) {
            File[] contents = pathFile.listFiles();
            for (File content : contents) {
                content.delete();
            }
        }

        File file = new File(pathFile, getOutputFileName());
        path = file.getAbsolutePath();

        //------------

        writers = new Writer[numLanes];
        for (int i = 0; i < writers.length; i++) {
            String pathX = path + "." + i + FILE_EXTENSION;

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(pathX, false);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }

            writers[i] = new BufferedWriter(fileWriter);
        }

        //------------

        String[] columnNamesArr = getFileColumnHeaders();
        String columnNames = "";
        for (int j = 0; j < columnNamesArr.length; j++) {
            if (j > 0) {
                columnNames += FILE_COLUMN_SEPARATOR;
            }

            columnNames += columnNamesArr[j];
        }

        for (Writer writer : writers) {
            try {
                writer.write(columnNames);
                writer.write(FILE_ROW_SEPARATOR);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void process(int lane, List<? extends Chunk<R>> chunks) {
        Writer writer = writers[lane];

        for (Chunk<R> chunk : chunks) {
            Collection<R> rows = chunk.getElements();

            try {
                for (R row : rows) {
                    writeRow(lane, writer, row);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            chunk.discard();
        }
    }

    protected abstract void writeRow(int lane, Writer writer, R row) throws Exception;

    public void end() {
        for (Writer writer : writers) {
            try {
                writer.flush();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            try {
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}