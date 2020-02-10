package com.tibco.be.rms.functions;


import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.functions.string.StringHelper;
import com.tibco.be.rms.util.ChannelMigrator;
import com.tibco.cep.security.authz.utils.IOUtils;
import com.tibco.net.mime.Base64Codec;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Jun 6, 2008
 * Time: 10:30:10 AM
 * To change this template use File | Settings | File Templates.
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "RMS.Util",
        synopsis = "Utility Functions For RMS",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.RMS.Util", value=true))

public class RMSFunctionUtils {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat(DATETIME_FORMAT);

    private static final int COMPRESS_SIZE_MIN = 100000;//More than 1 MB


    public static Object now() {
        Date date = new Date();
        try {
            return FORMAT.parse(date.toString());
        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }
    }

    public static String serializeEObject(final EObject eObject) throws IOException {
        Resource resource = new XMLResourceImpl();
        resource.getContents().add(eObject);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resource.save(bos, null);
        byte[] bytes = bos.toByteArray();
        return new String(bytes, "UTF-8");
    }

    public static String readFile(String path) {
        try {
            byte[] bytes = IOUtils.readBytes(path);
            return new String(bytes, "UTF-8");
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "compressString",
        synopsis = "Compress large string contents and return the result",
        signature = "String compressString (String largeString)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "largeString", type = "String", desc = "The large string to compress")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Compressed contents as string"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Compress large string contents and return the result",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static String compressString(String largeString) {
        BufferedWriter writer = null;
        FileChannel fch = null;
        try {
            final File tmpDir = new File(System.getProperty("java.io.tmpdir"));
            int random = new Random(System.nanoTime()).nextInt();
            String tmpFile = new StringBuilder(tmpDir.getAbsolutePath()).
                    append(File.separatorChar).append("large").append(random).append(".tmp")
                    .toString();
            File f = new File(tmpFile);
            FileOutputStream fos = new FileOutputStream(f);
            GZIPOutputStream zos = new GZIPOutputStream(fos);
            writer = new BufferedWriter(new OutputStreamWriter(zos));
            writer.write(largeString);

            zos.finish();
            RandomAccessFile raf = new RandomAccessFile(tmpFile, "r");
            fch = raf.getChannel();
            ByteBuffer buffer =
                    fch.map(FileChannel.MapMode.READ_ONLY, 0, fch.size());
            //Delete tmp file
            f.delete();

            byte[] bytes = new byte[buffer.capacity()];
            buffer.get(bytes);
            return encodeBase64(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (fch != null) {
                    fch.close();
                }
            } catch (IOException e) {

            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "encodeBase64",
            synopsis = "",
            signature = "String encodeBase64(Object bytes)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bytes", type = "Object", desc = "Object byte array to be encoded.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Base64 encoded String."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Base64 encoding of object byte array.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static String encodeBase64(Object bytes) {
        if (!(bytes instanceof byte[])) {
            throw new IllegalArgumentException("Input should be byte[]");
        }
        byte[] byteContents = (byte[])bytes;
        return Base64Codec.encodeBase64(byteContents);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "decodeBase64",
            synopsis = "",
            signature = "Object decodeBase64(String encodedString)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "encodedString", type = "String", desc = "Base64 encoded String.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Decoded byte array."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Decode the base64 encoded string into a byte array.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static Object decodeBase64(String encodedString) {
        return Base64Codec.decodeBase64(encodedString);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "decompressString",
        synopsis = "UnCompress compressed large string contents and return the result",
        signature = "String decompressString (String largeString)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "compressedString", type = "String", desc = "The compressedString string to decompress"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "encoding", type = "String", desc = "The encoding to be used.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "UnCompressed contents as string"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "UnCompress compressed large string contents and return the result",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static String decompressString(String compressedString, String encoding) {
        try {
            byte[] compressedBytes = compressedString.getBytes(encoding);
            byte[] inflatedBytes = (byte[])decompressBytes(compressedBytes);
		    String inflatedContents =
                    StringHelper.convertByteArrayToString(inflatedBytes, encoding);
            return inflatedContents;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

     @com.tibco.be.model.functions.BEFunction(
        name = "decompressBytes",
        synopsis = "UnCompress compressed large byte contents and return the result bytes.",
        signature = "Object decompressBytes (Object compressedContent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "compressedContent", type = "Object", desc = "The compressed content to decompress")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "UnCompressed contents as bytes"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "UnCompress compressed large byte contents and return the result bytes.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object decompressBytes(Object compressedContent) {
        try {
            byte[] compressedByteContent;
            if (compressedContent instanceof byte[]) {
                compressedByteContent = (byte[])compressedContent;
            } else {
                throw new IllegalArgumentException("Input can be in byte form or string only");
            }
            Inflater decompressor = new Inflater();
		    decompressor.setInput(compressedByteContent);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // Compress the data
		    byte[] buf = new byte[1024];
		    while (!decompressor.finished()) {
                int count;
                try {
                    count = decompressor.inflate(buf);
                } catch (DataFormatException e) {
                    throw new RuntimeException(e);
                }
                bos.write(buf, 0, count);
		    }
		    try {
			    bos.close();
		    } catch (IOException e) {
                throw new RuntimeException(e);
            }
		    byte[] inflatedBytes = bos.toByteArray();
		    return inflatedBytes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "compressByteArray",
        synopsis = "Compress contents in the form of bytes, and return the string representation",
        signature = "String compressByteArray (Object contents)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "contents", type = "Object", desc = "The contents to compress"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "needsCompression", type = "boolean", desc = "A boolean indicating whether any compression is needed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Compressed contents as string"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Compress contents in the form of bytes, and return the string representation",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static String compressByteArray(Object contents, boolean needsCompression) {
        BufferedOutputStream bufOs = null;
        try {
            byte[] input = (byte[])contents;
            if (!needsCompression) {
                return new String(input, "UTF-8");
            }
            Deflater compressor = new Deflater();
            compressor.setLevel(Deflater.BEST_COMPRESSION);

            // Give the compressor the data to compress
            compressor.setInput(input);
            //We are done with setting input.
            compressor.finish();

            // Create an expandable byte array to hold the compressed data.
            // You cannot use an array that's the same size as the original because
            // there is no guarantee that the compressed data will be smaller than
            // the uncompressed data.
            ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
            bufOs = new BufferedOutputStream(bos);
            // Compress the data
            byte[] buf = new byte[input.length];
            while (!compressor.finished()) {
                int count = compressor.deflate(buf);
                bufOs.write(buf, 0, count);
            }
            // Get the compressed data
            byte[] compressedData = bos.toByteArray();
            return encodeBase64(compressedData);
        } catch (Exception une) {
            throw new RuntimeException(une);
        } finally {
            try {
                if (bufOs != null) {
                    bufOs.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "needsCompression",
        synopsis = "Returns a boolean on whether the contents needs compression",
        signature = "boolean needsCompression(Object contents)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "contents", type = "Object", desc = "The contents to compress")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "whether compression is needed or not"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a boolean on whether the contents needs compression",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean needsCompression(Object contents) {
        byte[] input = (byte[])contents;
        return input.length >= COMPRESS_SIZE_MIN;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getMatchingDirectoryForRequestId",
        synopsis = "Search for a directory which contains the requestid string inside the\nworkspace area of a project.",
        signature = "getMatchingDirectoryForRequestId(String baseDirectoryPath,",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "baseDirectoryPath", type = "String", desc = "The base directory under which to search"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestId", type = "String", desc = "The requestid string")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Name of the directory matchi ng this id. <b>Should be only one</b>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Search for a directory which contains the requestid string inside the\nworkspace area of a project.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static String getMatchingDirectoryForRequestId(final String baseDirectoryPath,
                                                          final String requestId) {
        File baseDirectory = new File(baseDirectoryPath);
        if (!baseDirectory.exists()) {
            throw new IllegalArgumentException("Base directory to search is absent");
        }
        String[] files = baseDirectory.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(requestId);
                }
        });
        if (files.length != 1) {
            return null;
        }
        return files[0];
    }

    public static boolean getBooleanValue(String boolValue) {
        if (boolValue == null) {
            throw new IllegalArgumentException("Input value cannot be null");
        }
        return Boolean.parseBoolean(boolValue);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "writeToFile",
        synopsis = "Writes byte contents to a file specified by the absolute path",
        signature = "void writeToFile(String filePath, Object contents)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filePath", type = "String", desc = "Absolute path of the file to write to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "contents", type = "Object", desc = "byte[] form of contents.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Writes byte contents to a file specified by the absolute path.\n<p>\nAny previous contents are overwritten.\n</p>",
        cautions = "Make sure the file is writable.",
        fndomain = {ACTION},
        example = ""
    )
    public static void writeToFile(String filePath, Object contents) {
        if (filePath == null) {
            throw new IllegalArgumentException("File Path cannot be null");
        }
        if (!(contents instanceof byte[])) {
            throw new IllegalArgumentException("Contents should be a byte[]");
        }
        File file = new File(filePath);
        try {
            byte[] bytes = (byte[])contents;
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            FileOutputStream fos = new FileOutputStream(file);
            IOUtils.writeBytes(bis, fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @com.tibco.be.model.functions.BEFunction(
            name = "migrateChannels",
            synopsis = "Migrate the channels to new format",
            signature = "void migrateChannels(String projectDirectoryPath)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectDirectoryPath", type = "String", desc = "the directory of project")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "3.0.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Migrate the channels to new format",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    public static void migrateChannels(String projectDirectoryPath) {
        if (projectDirectoryPath == null) {
            throw new IllegalArgumentException("Project path for migration utility cannot be null");
        }
        File projectFile = new File(projectDirectoryPath);
        if (!projectFile.exists()) {
            throw new IllegalArgumentException("Project directory path is invalid");
        }
        try {
            ChannelMigrator.migrateChannels(projectFile, "channel", true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
