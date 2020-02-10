package com.tibco.cep.query.stream.monitor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/*
 * Author: Ashwin Jayaprakash Date: Nov 27, 2007 Time: 7:30:28 PM
 */

public class CustomMultiSourceException extends CustomException implements KnownResource {
    private static final long serialVersionUID = 1L;

    protected final Collection<Source> sources;

    private final ProxyThrowable proxyCause;

    public CustomMultiSourceException(ResourceId resourceId, String message, Source[] sources) {
        super(resourceId, message);

        this.sources = (sources == null) ? new HashSet<Source>() : Arrays.asList(sources);

        this.proxyCause = new ProxyThrowable();
        if (sources != null) {
            String s = convertToString();
            this.proxyCause.setCustomMessage(s);
        }
    }

    public CustomMultiSourceException(ResourceId resourceId, String message) {
        this(resourceId, message, null);
    }

    public CustomMultiSourceException(ResourceId resourceId) {
        this(resourceId, null, null);
    }

    public boolean hasSources() {
        return !sources.isEmpty();
    }

    /**
     * Do not modify the collection.
     *
     * @return
     */
    public Collection<Source> getSources() {
        return sources;
    }

    @Override
    public Throwable getCause() {
        return proxyCause;
    }

    public void addSource(Source source) {
        sources.add(source);

        // Clear first.
        String s = convertToString();
        proxyCause.setCustomMessage(s);
    }

    private String convertToString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);

        if (sources.isEmpty() == false) {
            synchronized (printStream) {
                int i = 1;
                for (Source source : sources) {
                    String str = source.getMessage();
                    str = (str == null) ? "" : str;
                    printStream.println();
                    printStream.println("**Source " + i + ": " + str);

                    Throwable t = source.getThrowable();
                    t.printStackTrace(printStream);

                    i++;
                }
            }
        }
        else {
            synchronized (printStream) {
                printStream.println("No sources.");
            }
        }

        printStream.flush();
        printStream.close();

        return baos.toString();
    }

    // ----------

    protected static class ProxyThrowable extends Throwable {
        protected String customMessage;

        public ProxyThrowable() {
            super("");
        }

        public String getCustomMessage() {
            return customMessage;
        }

        public void setCustomMessage(String customMessage) {
            this.customMessage = customMessage;
        }

        @Override
        public String toString() {
            return customMessage;
        }
    }

    public static class Source implements Serializable {
        private static final long serialVersionUID = 1L;

        protected final String message;

        protected final Throwable throwable;

        public Source(String message, Throwable throwable) {
            this.message = message;
            this.throwable = throwable;
        }

        public Source(Throwable cause) {
            this(null, cause);
        }

        public Source(String message) {
            this(message, null);
        }

        public Throwable getThrowable() {
            return throwable;
        }

        public String getMessage() {
            return message;
        }
    }
}
