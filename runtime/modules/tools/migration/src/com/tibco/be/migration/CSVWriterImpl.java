package com.tibco.be.migration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.HashMap;

/*
 * Author: Ashwin Jayaprakash Date: Feb 24, 2008 Time: 12:04:37 PM
 */

/**
 * <p>
 * Write the contents into a file with the following format:
 * </p>
 * <p>
 * Each comment row: {@link Constants#COMMENT_PREFIX}
 * {@link Constants#NON_NULL_COLUMN_START_END delimiter} ESCAPED-TEXT
 * {@link Constants#NON_NULL_COLUMN_START_END delimiter}
 * {@link Constants#LINE_SEPARATOR}
 * </p>
 * <p>
 * Each non-empty column: {@link Constants#NON_NULL_COLUMN_START_END delimiter}
 * ESCAPED-TEXT {@link Constants#NON_NULL_COLUMN_START_END delimiter}
 * </p>
 * <p>
 * Each empty column: Nothing, no delimters and no text.
 * </p>
 * <p>
 * Each non-comment row: column ({@link Constants#COLUMN_SEPARATOR} column)*
 * {@link Constants#LINE_SEPARATOR}
 * </p>
 * <p>
 * If the file larger than the specified size ({@link #maxFileSizeBytes}),
 * then the contents are split into multiple files. Each new file name has a
 * format: {@link #outputFileNamePrefix}
 * {@link Constants#ROLLED_FILE_NAME_SUBSTRING} file-number: 1 to ...
 * {@link Constants#MAIN_FILE_EXTENSION}. The main file does not have a
 * file-number substring.
 * </p>
 * <p>
 * If a column grows beyond {@link #maxInlineStrLenBytes}, then the contents
 * are written to an external file in the same directory as
 * {@link #outputDirectory}. The file names will be:
 * {@link #outputFileNamePrefix}
 * {@link Constants#OUT_OF_BAND_FILE_NAME_SUBSTRING} file-number: 1 to ...
 * {@link Constants#OUT_OF_BAND_FILE_EXTENSION}. The column in the main file
 * fragment will be a reference to the file in this format:
 * {@link Constants#OUT_OF_BAND_REF_PREFIX} + external-file-name.
 * </p>
 * <p>
 * Ensure {@link Constants#LINE_SEPARATOR} and
 * {@link Constants#LINE_SEPARATOR_OPTIONAL_PREFIX} are ISO-Control characters.
 * </p>
 */
public class CSVWriterImpl implements CSVWriter, Constants {
    protected final String fileEncoding;

    protected final String outputDirectory;

    protected final String outputFileNamePrefix;

    protected final long maxFileSizeBytes;

    protected final int maxInlineStrLenBytes;

    boolean previousOpWasColumnWrite;

    StringBuffer stringBuffer;

    int columnsWrittenInCurrentRow;

    long bytesWrittenInCurrentRow;

    long bytesWrittenInCurrentFile;

    long totalBytesWritten;

    int outOfBandRefNum;

    int rolledFileNum;

    int maxInlineStringLen;

    File mainFile;

    Helper helper;

    /**
     * @param outputDirectory
     * @param outputFileNamePrefix
     * @param fileEncoding
     * @param maxFileSizeBytes
     * @param maxInlineStrLenBytes
     * @throws IOException
     */
    public CSVWriterImpl(String outputDirectory, String outputFileNamePrefix, String fileEncoding,
            long maxFileSizeBytes, int maxInlineStrLenBytes) throws IOException {
        this.fileEncoding = fileEncoding;
        this.outputDirectory = outputDirectory;
        this.outputFileNamePrefix = outputFileNamePrefix;
        this.maxFileSizeBytes = maxFileSizeBytes;
        this.maxInlineStrLenBytes = maxInlineStrLenBytes;

        init();
    }

    /**
     * @param outputDirectory
     * @param outputFileNamePrefix
     * @throws IOException
     */
    public CSVWriterImpl(String outputDirectory, String outputFileNamePrefix) throws IOException {
        this(outputDirectory, outputFileNamePrefix, DEFAULT_FILE_ENCODING,
                DEFAULT_MAX_FILE_SIZE_BYTES, DEFAULT_MAX_INLINE_STR_LEN_BYTES);
    }

    private void init() throws IOException {
        previousOpWasColumnWrite = false;
        stringBuffer = new StringBuffer();
        columnsWrittenInCurrentRow = 0;
        bytesWrittenInCurrentRow = 0;
        bytesWrittenInCurrentFile = 0;
        totalBytesWritten = 0;
        outOfBandRefNum = 1;
        rolledFileNum = 1;

        mainFile = createFile(outputDirectory, outputFileNamePrefix + MAIN_FILE_EXTENSION, true);

        helper = new Helper();
        helper.init(mainFile, fileEncoding);

        float f = maxInlineStrLenBytes / (float) helper.averageBytesPerChar;
        maxInlineStringLen = Math.round(f);
    }

    public String getFileEncoding() {
        return fileEncoding;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public String getOutputFileNamePrefix() {
        return outputFileNamePrefix;
    }

    // ----------

    /**
     * Write one csv field to the file, followed by a separator unless it is the
     * last field on the line. Lead and trailing blanks will be removed.
     *
     * @param s
     *            The string to write. Any additional quotes or embedded quotes
     *            will be provided by write.
     * @throws IOException
     */
    private void writeColumn(String s) throws IOException {
        if (previousOpWasColumnWrite) {
            directWrite(COLUMN_SEPARATOR);
        }

        if (s == null) {
            previousOpWasColumnWrite = true;
            columnsWrittenInCurrentRow++;

            return;
        }

        String str = escapeString(s);

        if (str.length() > maxInlineStringLen) {
            String ref = writeOutOfBand(str);
            directWrite(ref);
        }
        else {
            directWrite(NON_NULL_COLUMN_START_END);
            directWrite(str);
            directWrite(NON_NULL_COLUMN_START_END);
        }

        // Make a note to print trailing comma later.
        previousOpWasColumnWrite = true;
        columnsWrittenInCurrentRow++;

        return;
    }

    private void directWrite(char c) throws IOException {
        long actualInFile = bytesWrittenInCurrentFile;

        /*
         * Estimate the num of bytes this string might take when decoded. If it
         * can overflow, then start a new file.
         */
        if ((actualInFile + helper.averageBytesPerChar) > maxFileSizeBytes) {
            actualInFile = rollMainFile();
        }

        long bytes = helper.encodeAndWriteToFile(c);

        bytesWrittenInCurrentRow += bytes;
        bytesWrittenInCurrentFile = actualInFile + bytes;
        totalBytesWritten += bytes;
    }

    private void directWrite(String s) throws IOException {
        long actualInFile = bytesWrittenInCurrentFile;

        long x = actualInFile + (helper.averageBytesPerChar * s.length());
        // Can overflow.
        if (x > maxFileSizeBytes) {
            actualInFile = rollMainFile();
        }

        long bytes = helper.encodeAndWriteToFile(s);

        bytesWrittenInCurrentRow += bytes;
        bytesWrittenInCurrentFile = actualInFile + bytes;
        totalBytesWritten += bytes;
    }

    private File createFile(String outDirectory, String outFileName, boolean newForWriting)
            throws IOException {
        File file = new File(outDirectory, outFileName);

        if (newForWriting) {
            if (file.exists()) {
                throw new IOException("File: " + file.getAbsolutePath()
                        + " already exists. Please specify another file name.");
            }

            if (file.createNewFile() == false) {
                throw new IOException("Unable to create file: " + file.getAbsolutePath()
                        + " for writing.");
            }
        }

        return file;
    }

    /**
     * @return Number of bytes written to the new file - that were transferred
     *         from the old one.
     * @throws IOException
     */
    private long rollMainFile() throws IOException {
        // Close the old one.
        helper.discard();

        String destFileNamePrefix = outputFileNamePrefix + ROLLED_FILE_NAME_SUBSTRING
                + rolledFileNum + MAIN_FILE_EXTENSION;
        File destFile = createFile(outputDirectory, destFileNamePrefix, true);
        // This is the new one.
        helper.init(destFile, fileEncoding);

        // Incomplete write in previous file. Move them to new file.
        if (bytesWrittenInCurrentRow > 0) {
            long seekTo = bytesWrittenInCurrentFile - bytesWrittenInCurrentRow;

            RandomAccessFile srcFile = new RandomAccessFile(mainFile, "rw");
            srcFile.seek(seekTo);

            // Copy the last half-done line.
            byte[] array = new byte[2048];
            int c = 0;
            while ((c = srcFile.read(array)) != -1) {
                helper.writeToFile(array, 0, c);
            }

            // Truncate old file.
            srcFile.setLength(seekTo);
            srcFile.close();
        }

        mainFile = destFile;
        rolledFileNum++;

        return bytesWrittenInCurrentRow;
    }

    private String escapeString(String str) {
        final int length = str.length();
        StringBuffer buffer = null;
        buffer = new StringBuffer(length);

        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);

            if (Character.isISOControl(c) || (c == '\\') || (c == COLUMN_SEPARATOR)
                    || (c == NON_NULL_COLUMN_START_END)) {
                if (buffer == null) {
                    /*
                     * Lazy init. If there was nothing to be escaped, then we
                     * save the trouble of copying the string to the buffer.
                     */
                    buffer = new StringBuffer(length);

                    if (i > 0) {
                        // Copy contents that were scanned so far.
                        String subStr = str.substring(0, i);
                        buffer.append(subStr);
                    }
                }

                buffer.append("\\u");
                String paddedCode = Integer.toHexString(c);
                for (int q = paddedCode.length(); q < 4; q++) {
                    buffer.append('0');
                }
                buffer.append(paddedCode);
            }
            else if (buffer != null) {
                buffer.append(c);
            }
        }

        return (buffer == null) ? str : buffer.toString();
    }

    /**
     * @param str
     * @return The "URL" of the file stored in the same folder.
     * @throws IOException
     */
    private String writeOutOfBand(String str) throws IOException {
        String childFileName = outputFileNamePrefix + OUT_OF_BAND_FILE_NAME_SUBSTRING
                + outOfBandRefNum + OUT_OF_BAND_FILE_EXTENSION;

        File childFile = createFile(outputDirectory, childFileName, true);

        Helper oobHelper = new Helper();
        oobHelper.init(childFile, fileEncoding);
        try {
            totalBytesWritten += oobHelper.encodeAndWriteToFile(str);
        }
        finally {
            oobHelper.discard();
        }

        outOfBandRefNum++;

        return OUT_OF_BAND_REF_PREFIX + childFileName;
    }

    /**
     * Writes a new line in the CVS output file to mark the end of record.
     *
     * @throws IOException
     */
    public CSVWriter writeln() throws IOException {
        directWrite(LINE_SEPARATOR);

        previousOpWasColumnWrite = false;
        columnsWrittenInCurrentRow = 0;
        bytesWrittenInCurrentRow = 0;

        return this;
    }

    public CSVWriter writeCommentln(String text) throws IOException {
        if (previousOpWasColumnWrite) {
            writeln();
        }

        directWrite(COMMENT_PREFIX);
        writeColumn(text);
        writeln();

        return this;
    }

    /**
     * @throws IOException
     */
    public Report close() throws IOException {
        helper.discard();

        return new Report(totalBytesWritten, rolledFileNum);
    }

    // ----------

    public CSVWriter write(String s) throws IOException {
        writeColumn(s);

        return this;
    }

    /**
     * Writes a single value in a line suited by a newline to the file given by
     * the <code>token</code>.
     *
     * @param token
     *            contains the value.
     * @throws IOException
     */
    public CSVWriter writeln(String token) throws IOException {
        writeColumn(token);
        writeln();

        return this;
    }

    /**
     * Writes a single line of comma separated values from the array given by
     * <code>line</code>.
     *
     * @param line
     *            containig an array of tokens.
     * @throws IOException
     */
    public CSVWriter writeln(String[] line) throws IOException {
        for (int ii = 0; ii < line.length; ii++) {
            writeColumn(line[ii]);
        }

        writeln();

        return this;
    }

    public CSVWriter write(char c) throws IOException {
        stringBuffer.append(c);

        writeColumn(stringBuffer.toString());

        stringBuffer.delete(0, stringBuffer.length());

        return this;
    }

    public CSVWriter write(int i) throws IOException {
        stringBuffer.append(i);

        writeColumn(stringBuffer.toString());

        stringBuffer.delete(0, stringBuffer.length());

        return this;
    }

    public CSVWriter write(short s) throws IOException {
        stringBuffer.append(s);

        writeColumn(stringBuffer.toString());

        stringBuffer.delete(0, stringBuffer.length());

        return this;
    }

    public CSVWriter write(boolean b) throws IOException {
        stringBuffer.append(b);

        writeColumn(stringBuffer.toString());

        stringBuffer.delete(0, stringBuffer.length());

        return this;
    }

    public CSVWriter write(long l) throws IOException {
        stringBuffer.append(l);

        writeColumn(stringBuffer.toString());

        stringBuffer.delete(0, stringBuffer.length());

        return this;
    }

    public CSVWriter write(float f) throws IOException {
        stringBuffer.append(f);

        writeColumn(stringBuffer.toString());

        stringBuffer.delete(0, stringBuffer.length());

        return this;
    }

    public CSVWriter write(double d) throws IOException {
        stringBuffer.append(d);

        writeColumn(stringBuffer.toString());

        stringBuffer.delete(0, stringBuffer.length());

        return this;
    }

    public CSVWriter write(Object j) throws IOException {
        stringBuffer.append(j);

        writeColumn(stringBuffer.toString());

        int length = stringBuffer.length();
        stringBuffer.delete(0, length);
        if (length > maxInlineStringLen) {
            stringBuffer.trimToSize();
        }

        return this;
    }

    // ----------

    protected static class Helper {
        FileChannel fileChannel;

        ByteBuffer byteBuffer;

        Charset charset;

        CharsetEncoder charsetEncoder;

        int averageBytesPerChar;

        StringBuffer charSeqBridge;

        void init(File file, String encoding) throws IOException {
            fileChannel = new FileOutputStream(file).getChannel();

            byteBuffer = ByteBuffer.allocate(2048);

            charset = Charset.forName(encoding);
            charsetEncoder = charset.newEncoder();

            averageBytesPerChar = Math.round(charsetEncoder.averageBytesPerChar());

            charSeqBridge = new StringBuffer(1);
        }

        long encodeAndWriteToFile(CharSequence charSequence) throws IOException {
            CharBuffer charBuffer = CharBuffer.wrap(charSequence);

            return encodeAndWriteCharBufferToFile(charBuffer);
        }

        long encodeAndWriteToFile(char c) throws IOException {
            charSeqBridge.delete(0, charSeqBridge.length());
            charSeqBridge.append(c);
            CharBuffer charBuffer = CharBuffer.wrap(charSeqBridge);

            return encodeAndWriteCharBufferToFile(charBuffer);
        }

        private long encodeAndWriteCharBufferToFile(CharBuffer charBuffer) throws IOException {
            long lengthInBytes = 0;

            while (true) {
                CoderResult coderResult = charsetEncoder.encode(charBuffer, byteBuffer, true);
                ((Buffer)byteBuffer).flip();

                while (byteBuffer.hasRemaining()) {
                    lengthInBytes += fileChannel.write(byteBuffer);
                }
                ((Buffer)byteBuffer).clear();

                if (coderResult.isUnderflow()) {
                    break;
                }
            }

            return lengthInBytes;
        }

        void writeToFile(byte[] bytes, int offset, int length) throws IOException {
            ByteBuffer tempByteBuffer = ByteBuffer.wrap(bytes, offset, length);

            /*
             * Don't flip, because we are not writing to the buffer, only
             * reading.
             */

            while (tempByteBuffer.hasRemaining()) {
                fileChannel.write(tempByteBuffer);
            }
            ((Buffer)tempByteBuffer).clear();
        }

        void discard() throws IOException {
            byteBuffer = null;
            charset = null;
            charsetEncoder = null;
            averageBytesPerChar = 0;
            charSeqBridge = null;

            if (fileChannel != null) {
                fileChannel.close();
            }
            fileChannel = null;
        }
    }

    /**
     * Test driver
     *
     * @param args
     *            [0]: The directory. [1]: The file name prefix.
     * @throws Exception
     */
    static public void main(String[] args) throws Exception {
        CSVWriterImpl csv = new CSVWriterImpl(args[0], args[1], "UTF-8", (1 << 16), (1 << 16));

        csv.writeCommentln("This is a test csv-file: '" + args[0] + "'");
        csv.writeColumn("aaaa");
        csv.writeColumn("");
        csv.writeColumn("bbbb");
        csv.writeColumn("g h i");
        csv.writeColumn("jk,l");
        csv.writeColumn(null);
        csv.writeColumn("m\"n\'o ");
        csv.writeColumn(null);
        csv.writeln();

        csv.writeColumn("abcd\n\'efg ");
        csv.writeColumn("    ");
        csv.writeColumn("a");
        csv.writeColumn("x,y,z");
        csv.writeColumn("x;y;z");
        csv.writeColumn("");
        csv.writeln();

        csv.writeCommentln("file://c:/hello.txtx;y;z");
        csv.writeln();

        csv.writeColumn("file://m:/testblah.\t##\r\n\\e\\a\\b,,,!~%^&%$%@#$%^&ooklklkla");
        csv.writeln();

        csv.writeCommentln("Comment line 1");
        csv.writeCommentln("Comment ##line 2");
        csv.writeColumn("\r\nbl###ah\\u\\u\\u002f\t  \\u iiijjjjhhhhh");
        csv.writeln();
        csv.writeCommentln("#Comment line 3");
        csv.writeCommentln("#Comment line 4");

        for (int m = 0; m < 50; m++) {
            csv.write(false);
            csv.write('\t');
            csv.write(200.9876543219d);
            csv.write(3000.123454321f);
            csv.write(900);
            StringBuffer buffer = new StringBuffer();
            buffer.append('\r');
            buffer.append('-');
            buffer.append('\t');
            buffer.append('-');
            buffer.append('\"');
            buffer.append(',');
            buffer.append('\n');
            buffer.append('-');
            for (int i = 0; i < csv.maxInlineStringLen; i++) {
                buffer.append('z');
            }
            for (int i = 65; i < 91; i++) {
                char c = (char) i;
                buffer.append(c);
            }
            csv.write(buffer.toString());
            csv.write(Long.MIN_VALUE);
            csv.write(new HashMap());
            csv.write((short) 9);
            csv.write(buffer.toString());
            csv.writeln();
        }

        csv.writeln(new String[] { "This", "is", "an", "array." });

        String s1 = "aaaaaaaaabbbbbbbbcccccccccdddd";
        String s2 = "\"\"\"\"\n\n\n\n\t\t\t\t\t,,,";
        String s3 = "111111111222222223333333334444";
        int bytes = 0;
        while (bytes < (1 << 16) /* Constants.DEFAULT_MAX_INLINE_STR_LEN_BYTES */) {
            csv.write(s1);
            csv.write(s2);
            csv.write(s3);
            csv.writeln();

            bytes = bytes + ((s1.length() + s2.length() + s3.length()) * 4) + 4 /* Newline. */;
        }
        bytes = 0;
        for (int i = 0; bytes < (2 * (1 << 16)) /*
                                                 * 2 *
                                                 * Constants.DEFAULT_MAX_FILE_SIZE_BYTES
                                                 */; i++) {
            String s = "[" + i + "]#################";

            bytes += s.length();
            csv.write(s);

            bytes += s2.length();
            csv.write(s2);

            bytes += s3.length();
            csv.write(s3);

            s = "eeeeeeeeeeeeee:END";
            bytes += s.length();
            csv.write(s);

            bytes++;
            csv.writeln();
        }

        Report report = csv.close();
        System.err.println("Report:: Num of main files: " + report.getTotalMainFileFragments()
                + ", Total bytes: " + report.getTotalBytesWritten());
    }
}
