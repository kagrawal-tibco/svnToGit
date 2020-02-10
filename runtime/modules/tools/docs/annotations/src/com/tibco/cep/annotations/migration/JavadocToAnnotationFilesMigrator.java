package com.tibco.cep.annotations.migration;

import java.io.*;


/**
 * User: nprade
 * Date: 4/17/12
 * Time: 4:31 PM
 */
public class JavadocToAnnotationFilesMigrator {


    public static void main(String[] args) {
        final JavadocToAnnotationFilesMigrator migrator = new JavadocToAnnotationFilesMigrator();
        for (String arg : args) {
            migrator.process(new File(arg));
        }
    }


    public JavadocToAnnotationFilesMigrator() {
    }


    public void process(
            File file) {

        if (file.isDirectory()) {
            //noinspection ConstantConditions
            for (final File child : file.listFiles()) {
                process(child);
            }
        }
        else if (file.getName().endsWith(".java")) {
            processSourceFile(file);
        }
    }


    private void processSourceFile(
            File file) {

        if (!file.canRead()) {
            logError("Cannot read: " + file.getPath());
        }
        else {
            final JavadocToAnnotationFileMigrator fcm = new JavadocToAnnotationFileMigrator();
            try {
                final BufferedReader reader = new BufferedReader(new FileReader(file));
                try {
                    for (String line = reader.readLine(); null != line; line = reader.readLine()) {
                        fcm.processLine(line);
                    }
                }
                finally {
                    reader.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (fcm.isChanged()) {
                try {
                    final FileOutputStream fos = new FileOutputStream(file.getPath());
                    try {
                        fos.write(fcm.getContent().getBytes("UTF-8"));
                    }
                    finally {
                        fos.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private static void logError(
            String message) {

        System.err.print(message);
    }


}

