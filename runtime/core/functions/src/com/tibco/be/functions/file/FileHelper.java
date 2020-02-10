package com.tibco.be.functions.file;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 4, 2007
 * Time: 11:04:49 PM
 * To change this template use File | Settings | File Templates.
 */

@com.tibco.be.model.functions.BEPackage(
        catalog = "Standard",
        category = "File",
        synopsis = "Functions to support general file IO operations")
public class FileHelper {
    public static final String BRK = System.getProperty("line.separator", "\n");
    public static final byte[] BRK_BYTES;
    static {
        byte[] tmp = new byte[BRK.length()];
        for(int ii = 0; ii < BRK.length(); ii++) tmp[ii] = (byte)BRK.charAt(ii);
        BRK_BYTES = tmp;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "fileClose",
        synopsis = "Closes the file that was previously opened with File.openFile().",
        signature = "void fileClose (Object writerReader)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "writerReader", type = "Object", desc = "The file reader to be closed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Closes the file that was previously opened with File.openFile().",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    static public void fileClose(Object writerReader) {
        try {
            ((FileRec)writerReader).close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "fileExists",
        synopsis = "Tests whether the file or directory denoted by the specified path exists.",
        signature = "boolean fileExists (String path)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "path", type = "String", desc = "The target file or directory.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns true if the specified file/directory exists; otherwise, returns false."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Tests whether the file or directory denoted by the specified path exists.",
        cautions = "none",
        fndomain = {ACTION,CONDITION,QUERY},
        example = ""
    )
    static public boolean fileExists(String path) {
        try {
            File file = new File(path);
            return file.exists();
        } catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "fileLength",
        synopsis = "Returns the length of the specified file (in bytes).",
        signature = "long fileLength (Object writerReader)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "writerReader", type = "Object", desc = "The file whose current length is of interest.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "Returns the length of the specified file (in bytes)."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the length of the specified file (in bytes).",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    static public long fileLength(Object writerReader) {
        try {
            return ((FileRec)writerReader).size();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "fileReadLine",
        synopsis = "Reads from the file pointer until the first instance of \r\n, \r, \n or the end of the file.  Each byte of the input is converted in sequence into the low bytes of one character in the result.  The line ending is not included in the result.",
        signature = "String fileReadLine (Object fileReader)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fileReader", type = "Object", desc = "The file whose text line is to be read.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Returns the next line of text from the file; or null if EOF is immediately encountered."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Reads the next line of text from the specified file.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    static public String fileReadLine(Object fileReader) {
        try {
            return ((FileRec)fileReader).readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "fileRemove",
        synopsis = "Deletes the specified file or directory.",
        signature = "boolean fileRemove (String path)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "path", type = "String", desc = "The file or directory to be deleted.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns true if the target file/directory is deleted; false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Deletes the specified file or directory.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    static public boolean fileRemove(String path) {
        try {
            File file = new File(path);
            return file.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "fileRename",
        synopsis = "Renames the specified file or directory.",
        signature = "boolean fileRename (String srcPath, String destPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "srcPath", type = "String", desc = "The original (/current) file or directory name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "destPath", type = "String", desc = "The target file or directory name.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns true if the rename is successful; false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Renames the specified file or directory.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    static public boolean fileRename(String srcPath, String destPath) {
        try {
            File sourceFile = new File(srcPath);
            File destinationFile = new File(destPath);
            return sourceFile.renameTo(destinationFile);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "fileSeek",
        synopsis = "Set the file-pointer offset (in bytes) at which the next IO will occur.",
        signature = "void fileSeek (Object writerReader, long pos)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "writerReader", type = "Object", desc = "The target file."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pos", type = "long", desc = "The offset from the beginning of the file.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the file-pointer offset at which the next read or write will occur.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    static public void fileSeek(Object writerReader, long pos) {
        try {
            ((FileRec)writerReader).seek(pos);
        } catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "fileWrite",
        synopsis = "Writes the low byte of each character in the String in sequence to the file.",
        signature = "void fileWrite (Object fileWriter, String str)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fileWriter", type = "Object", desc = "The target file."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "str", type = "String", desc = "String to be written to the targeted file.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Writes the string to the file as a sequence of bytes starting at the current file-pointer position.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    static public void fileWrite(Object fileWriter, String str) {
        try {
            ((FileRec)fileWriter).writeBytes(str);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

     @com.tibco.be.model.functions.BEFunction(
        name = "fileWriteBytes",
        synopsis = "Writes a <code>byte[]</code> to the file.",
        signature = "void fileWrite (Object fileWriter, Object bytes)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fileWriter", type = "Object", desc = "The target file."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bytes", type = "Object", desc = "<code>byte[]</code> to be written to the targeted file.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Writes the <code>byte[]</code> to the file.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void fileWriteBytes(Object fileWriter, Object bytes) {
         if (!(bytes instanceof byte[])) {
             throw new IllegalArgumentException("Input is not a byte[]");
         }
         byte[] byteArray = (byte[])bytes;
         try {
             ((FileRec)fileWriter).write(byteArray);
         } catch (IOException ex) {
             ex.printStackTrace();
             throw new RuntimeException(ex);
         }
     }

    @com.tibco.be.model.functions.BEFunction(
        name = "fileWriteLine",
        synopsis = "Writes the low byte of each character in the String to the file in sequence, followed by the system-specific line separator.",
        signature = "void fileWriteLine (Object fileWriter, String str)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fileWriter", type = "Object", desc = "The target file."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "str", type = "String", desc = "String to be written to the targeted file.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Writes the new line to the file as a sequence of bytes starting at the current file-pointer position.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    static public void fileWriteLine(Object fileWriter, String str) {
        try {
            ((FileRec)fileWriter).writeBytes(str);
            ((FileRec)fileWriter).write(BRK_BYTES);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "listFiles",
        synopsis = "Returns an array of files from the target directory that match the specified filter/pattern.",
        signature = "String[] listFiles (String dirPath, String filter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "dirPath", type = "String", desc = "The target directory path."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "String", desc = "An expression used to select the files - i.e., file names - of interest.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "Returns an array of files from the target directory that match the specified filter/pattern."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of files from the target directory that match the specified filter/pattern.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
   static public String[] listFiles(String dirPath, String filter) {
       try {
           File parent = new File(dirPath);
           String[] files = parent.list();
           String filename;
           ArrayList arr = new ArrayList();

           if (filter == null) {
               return files;
           }

           boolean wild = ("*".equals(filter));
           boolean partWild = (0 <= filter.indexOf('?') || 0 <= filter.indexOf('*'));
           if (null == files) {
               return new String[0];
           }
           for (int i = files.length - 1; 0 <= i; i--) {
               filename = files[i];
               if (wild || (partWild && wildMatch(filter, filename)) || filename.equals(filter)) {
                   arr.add(new File(parent, filename));
               }
           }

           String[] ret = new String[arr.size()];
           for(int j = 0; j < arr.size(); j++) {
               ret[j] = ((File)arr.get(j)).getAbsolutePath();
           }
           return ret;
       } catch(Exception ex) {
           ex.printStackTrace();
           throw new RuntimeException(ex);
       }
    }

   @com.tibco.be.model.functions.BEFunction(
        name = "openFile",
        synopsis = "Open the specified file for reading, and optionally, writing.",
        signature = "Object openFile (String path, String mode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "path", type = "String", desc = "The target directory path."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mode", type = "String", desc = "The access mode; i.e., $1r$1, $1rw$1, $1rws$1, or $1rwd$1")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Returns a connection to the specified name."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Open the specified file for reading, and optionally, writing.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    static public Object openFile(String path, String mode) {
        try {
            return new FileRec(path, mode);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "readFileAsString",
        synopsis = "Read the contents of the specified file and return a string",
        signature = "String readFileAsString(String filePath, [String encoding = $1UTF-8$1])",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filePath", type = "String", desc = "The file to read"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "encodingArguments", type = "String", desc = "Encoding used. Optional Parameter. Default value = $1UTF-8$1")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Contents of the file"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Read the contents of the specified file and return a string.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static String readFileAsString(String filePath, Object... encodingArguments) {
        String encoding = "UTF-8";
        Object encodings[] = encodingArguments;
        if (encodings != null && encodings.length > 0) {
            encoding = (String) encodings[0];
        }
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(filePath); FileChannel channel = fis.getChannel()) {
            byte[] bytes = new byte[(int) channel.size()];
            ByteBuffer buf = ByteBuffer.allocateDirect((int) channel.size());
            int numRead = 0;
            int counter = 0;
            while (numRead >= 0) {
                ((Buffer)buf).rewind();
                //Read bytes from the channel
                numRead = channel.read(buf);
                ((Buffer)buf).rewind();
                for (int i = 0; i < numRead; i++) {
                    byte b = buf.get();
                    bytes[counter++] = b;
                }
                if (numRead > 0) {
                    String temp = new String(bytes, encoding);
                    sb.append(temp);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "readFileAsBytes",
        synopsis = "Read the contents of the specified file and return a <b><i>byte[]</i></b>",
        signature = "Object readFileAsBytes (String filePath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filePath", type = "String", desc = "The file to read")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Contents of the file as bytes"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Read the contents of the specified file and return a <tt>byte[]</tt>",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Object readFileAsBytes(String filePath) {
        byte[] bytes = null;
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r"); FileChannel channel = raf.getChannel()) {
            int size = (int) channel.size();
            bytes = new byte[size];
            ByteBuffer buf = ByteBuffer.wrap(bytes);
            int numRead;
            int totalRead = 0;
            do {
                numRead = channel.read(buf);
                totalRead += numRead;
                buf = buf.slice();
            } while(numRead >= 0 && totalRead < size);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    static final char QUANTIFIER_STAR = '*';
    static boolean wildMatch(String pat, String filename) {
        if (pat == null || pat.length() == 0 || filename == null || filename.length() == 0)
            return false;

        // using java regular expression API
        pat = escapedMetaCharacters(pat);

        // match BW metachar quantifier '*' to Java's of '.*'
        pat = pat.replaceAll("\\*", ".*");
        // match BW metachar quantifier '?' to Java's of '.'
        pat = pat.replace('?', '.');

        // regular expressions cannot start with '*'
        char firstChar = pat.charAt(0);
        if (firstChar == QUANTIFIER_STAR) {
            pat = '.' + pat;
        }

        boolean match = filename.matches(pat);
        return match;
    }

    //static final String METACHARS = "([{\\^$|)?*+.";
    static final String METACHARS = "$+.";
    static String escapedMetaCharacters(String pattern) {
        StringBuffer postPattern = new StringBuffer();
        int pcnt = pattern.length();
        int pi = 0;
        char c;
        while (pi < pcnt) {
            c = pattern.charAt(pi);
            if (METACHARS.indexOf(c) < 0) {
                postPattern.append(c);
            }
            else {
                // escape the metachar then
                postPattern.append('\\');
                postPattern.append(c);
            }
            pi++;
        }
        return postPattern.toString();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createFolder",
        synopsis = "Creates a folder on the file system",
        signature = "boolean createFolder(String directoryPath, String folderName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "directoryPath", type = "String", desc = "The directory to create folder in"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "folderName", type = "String", desc = "The name of the folder to create")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Whether folder was created or not"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a folder on the file system",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static boolean createFolder(String directoryPath, String folderName) {
        if (directoryPath == null || folderName == null) {
            throw new IllegalArgumentException("Directory Path or Folder Name cannot be null");
        }
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException("Directory Path " + directoryPath + " is not a valid directory path");
        }
        if (!dir.canWrite()) {
            throw new IllegalArgumentException("Specified Directory Path " + directoryPath + " is not writable");
        }
        File folder = new File(directoryPath, folderName);
        return folder.mkdir();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createFolders",
        synopsis = "Creates a folder structure on the file system under the path specified by directory path",
        signature = "boolean createFolders(String directoryPath, String folderPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "directoryPath", type = "String", desc = "The directory to create folder in"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filePath", type = "String", desc = "The filePath whose folder structure has to be created")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Whether folder structure was created or not"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a folder structure on the file system for this filepath",
        cautions = "The filePath should not point to a directory",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static boolean createFolders(String directoryPath, String filePath) {
        if (directoryPath == null || filePath == null) {
            throw new IllegalArgumentException("Directory Path or File Path cannot be null");
        }
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException("Directory Path " + directoryPath + " is not a valid directory path");
        }
        if (!dir.canWrite()) {
            throw new IllegalArgumentException("Specified Directory Path " + directoryPath + " is not writable");
        }
        File file = new File(directoryPath, filePath);
        File parentFile = file.getParentFile();
        //Check if it exists
        if (parentFile.exists()) {
            return true;
        }
        return parentFile.mkdirs();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "fileTruncateAndWrite",
        synopsis = "Truncate existing file and write any new contents.",
        signature = "void fileTruncateAndWrite(Object fileWriter, Object byteContents)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fileWriter", type = "Object", desc = "The filewriter object."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "contents", type = "Object", desc = "byte[] representing new contents to write.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Truncate existing file and write any new contents.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void fileTruncateAndWrite(Object fileWriter, Object byteContents) {
        try {
            ((FileRec) fileWriter).truncate();
            ((FileRec) fileWriter).write((byte[])byteContents);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public static class FileRec {
        private FileChannel channel;
        private long actualPos;
        private Scanner scanner = null;
        //javadocs for RandomAccessFile.readLine() say it always looks for \r, \n, \r\n, or EOF
        //regardless of the line.terminator system property.
        private static final Pattern linePattern = Pattern.compile("(.*?)(?:\\r?\\n|\\r|(\\z))");

        public FileRec(String path, String mode) throws IOException {
            channel = new RandomAccessFile(path, mode).getChannel();
        }

        public void close() throws IOException {
            if (channel != null) channel.close();
            channel = null;
            if (scanner != null) scanner.close();
            scanner = null;
        }

        public long size() throws IOException {
            checkClosed();
            return channel.size();
        }

        public void seek(long pos) throws IOException {
            checkClosed();
            if (pos != actualPos) {
                channel.position(pos);
                actualPos = pos;
                scanner = null;
            }
        }

        public void truncate() throws IOException {
            checkClosed();
            channel.truncate(0);
            actualPos = 0;
            scanner = null;
        }

        public void write(byte[] bytes) throws IOException {
            checkClosed();
            if(scanner != null) {
                channel.position(actualPos);
                scanner = null;
            }
            actualPos += channel.write(ByteBuffer.wrap(bytes));
        }

        public void writeBytes(String str) throws IOException {
            checkClosed();
            if (scanner == null) actualPos = channel.position();
            else scanner = null;

            //LowerByteChannel discards the upper byte of the characters like RandomAccessFile.writeBytes.
            actualPos += channel.transferFrom(new LowByteChannel(str), actualPos, str.length());
            channel.position(actualPos);
        }

        public String readLine() throws IOException {
            checkClosed();
            if (scanner == null) {
                scanner = new Scanner(channel, LowByteCharset.name);
                scanner.useDelimiter("");
            }

            String line = scanner.findWithinHorizon(linePattern, 0);

            MatchResult result = scanner.match();
            //group 2 is \\z for EOF
            if (line.length() == 0 && result.group(2) != null) {
                return null;
            }
            actualPos += line.length();
            return result.group(1);
        }

        private void checkClosed() throws IOException {
            if (channel == null) throw new IOException("File already closed");
        }
    }

    public static class LowByteChannel implements ReadableByteChannel {
        private String str;
        private int pos = 0;

        public LowByteChannel(String str) {
            this.str = str;
        }

        @Override
        public int read(ByteBuffer dst) throws ClosedChannelException {
            int limit = pos + Math.min(str.length() - pos, dst.remaining());
            int start = pos;
            for (; pos < limit; pos++) {
                //remove high bits like RandomAccessFile would
                dst.put((byte)str.charAt(pos));
            }
            return pos - start;
        }

        //implementing the close method exactly per the interface docs
        //requires synchronization. If it's necessary, better not to use the interface
        @Override
        public void close() {}

        @Override
        public boolean isOpen() {
            return true;
        }
    }

    /*
    public static void main(String[] args) throws IOException {
        //Writer fw = new BufferedWriter(new FileWriter("c:/temp/asdf.txt"));
        //for(int ii = 0; ii < 1000000; ii++) fw.write(ii+"\n");
        //fw.flush();fw.close();

        testnew();
        //testold();

        //long start = System.currentTimeMillis();
        //long end;
        //
        //testnew();
        //end = System.currentTimeMillis();
        //System.out.println("new : " + (end - start));
        //start = end;

        //testold();
        //end = System.currentTimeMillis();
        //System.out.println("old : " + (end - start));
    }

    private static void testnew() {
        Object file = openFile("c:/temp/asdf.txt", "rw");
        String line;
        while(true) {
            line = fileReadLine(file);
            if(line == null) break;
        }
    }

    private static void testold() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("c:/temp/asdf.txt", "rw");
        String line;
        while(true) {
            line = raf.readLine();
            if(line == null) break;
        }
    }
    */
}
