package com.tibco.be.parser.codegen.stream;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

public class StreamCompilerException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * The fully qualified name of the class that was being compiled.
	 */
	private StreamFileManager fileManager;
	// Unfortunately, Diagnostic and Collector are not Serializable, so we
	// can't
	// serialize the collector.
	transient private DiagnosticCollector<? extends JavaFileObject> diagnostics;

	public StreamCompilerException(StreamFileManager fileManager,
			Throwable cause,
			DiagnosticCollector<? extends JavaFileObject> diagnostics) {
//		super(cause);
		setFileManager(fileManager);
		setDiagnostics(diagnostics);
	}

	public StreamCompilerException(String message,
			StreamFileManager fileManager,
			DiagnosticCollector<? extends JavaFileObject> diagnostics) {
//		super(message);
		setFileManager(fileManager);
		setDiagnostics(diagnostics);
	}

	public StreamCompilerException(String message,
			StreamFileManager fileManager, Throwable cause,
			DiagnosticCollector<? extends JavaFileObject> diagnostics) {
//		super(message, cause);
		setFileManager(fileManager);
		setDiagnostics(diagnostics);
	}

	/**
	 * @return The name of the classes whose compilation caused the compile
	 *         exception
	 */
	public Collection<String> getClassNames() {
		List<Map.Entry<URI, JavaFileObject>> fileMap = fileManager
				.getFileEntries(new Kind[] { Kind.SOURCE });
		Set<String> files = new HashSet<String>();
		for (Entry<URI, JavaFileObject> entry : fileMap) {
			String[] parts = entry.getKey().toString().split("/");
			final String kind = parts[0];
			final String packageName = parts[1];
			final String className = parts[2];
			final String qualifiedClassName = packageName + "." + className;
			files.add(qualifiedClassName);
		}
		return Collections.unmodifiableSet(files);
	}

	/**
	 * Gets the diagnostics collected by this exception.
	 * 
	 * @return this exception's diagnostics
	 */
	public DiagnosticCollector<? extends JavaFileObject> getDiagnostics() {
		return diagnostics;
	}

	@Override
	public String getMessage() {

        HashMap<JavaFileObject, LinkedList<Diagnostic>> map = new HashMap<JavaFileObject, LinkedList<Diagnostic>>();

		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            JavaFileObject source = diagnostic.getSource();

            LinkedList<Diagnostic> diagnosticsperSource = map.get(source);
            if (diagnosticsperSource == null) {
                diagnosticsperSource = new LinkedList<Diagnostic>();
                map.put(source, diagnosticsperSource);
            }
            diagnosticsperSource.add(diagnostic);
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<JavaFileObject, LinkedList<Diagnostic>> entry : map.entrySet())
        {

            JavaFileObject source = (JavaFileObject) entry.getKey();
            LinkedList<Diagnostic> diagnostics = entry.getValue();
            if (source.getKind().equals(Kind.SOURCE))
            {
            	CharSequence charContent = null;
				try {
					charContent = source.getCharContent(true);
				} catch (IOException e) {
//					e.printStackTrace();
				}
				if (charContent != null) {
					builder.append(charContent).append("\n");
				}
            }


            for (Diagnostic diagnostic : diagnostics) {
                builder.append(diagnostic.getMessage(Locale.getDefault()));
            }
            builder.append("\n------------------------------------------------------------------------------------------------\n");

        }

		return builder.toString();
	}

	private void setDiagnostics(
			DiagnosticCollector<? extends JavaFileObject> diagnostics) {
		this.diagnostics = diagnostics;
	}
	
	private void setFileManager(StreamFileManager fileManager) {
		// create a new HashSet because the set passed in may not
		// be Serializable. For example, Map.keySet() returns a
		// non-Serializable
		// set.
		this.fileManager = fileManager;
	}
}