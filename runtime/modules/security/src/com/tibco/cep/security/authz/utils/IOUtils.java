/**
 * 
 */
package com.tibco.cep.security.authz.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.Buffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author aathalye
 *
 */
public class IOUtils {
	
	/**
	 * Write bytes present in the <tt>byte[]</tt> to the specified {@link File}.
	 * <p>
	 * Useful for writing large byte arrays.
	 * </p>
	 * @param bytes: The <tt>byte[]</tt> to write
	 * @param filePath: The absolute path of the file on disk to write to
	 * @throws IOException
	 */
	public static void writeBytesToFile(byte[] bytes, File filePath)
			throws IOException {
		
		FileChannel channel = new RandomAccessFile(filePath, "rw").getChannel();
        MappedByteBuffer buf = 
        	channel.map(FileChannel.MapMode.READ_WRITE, 0, bytes.length);
    
        buf.put(bytes, 0, bytes.length);
        // Force the change to the file system
        buf.force();
        // Close the file
        channel.close();
	}
	
	/**
	 * Write bytes present in the <tt>InputStream</tt> to the specified <tt>OutputStream</tt>.
	 * @param inStream
	 * @param outStream
	 * @throws Exception
	 */
	public static void writeBytes(InputStream inStream, OutputStream outStream)
			throws IOException {
		ByteBuffer buf = ByteBuffer.allocate(1024);
		byte[] bufferArray = buf.array();
		WritableByteChannel wbc = Channels.newChannel(outStream);
		int count = 0;
		int index = 0;
		while (count >= 0) {
			if (index == count) {
				count = inStream.read(bufferArray);
				index = 0;
			}
			while (index < count && buf.hasRemaining()) {
				buf.put(bufferArray[index++]);
			}
			((Buffer)buf).flip();
			// Write the bytes to the channel
			wbc.write(buf);
			if (buf.hasRemaining()) {
				buf.compact();
			} else {
				((Buffer)buf).clear();
			}
		}
		wbc.close();
		inStream.close();
	}
	
	/**
	 * Read contents of a file specified by absolute path,
	 * and return <tt>byte[]</tt>
	 * @param fileName: The absolute path of the file
	 * @return <tt>byte[]</tt> of the contents
	 * @throws IOException
	 */
	public static byte[] readBytes(final String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(fileName);
		FileChannel channel = fis.getChannel();
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
        }
        channel.close();
        return bytes;
	}
	
	/**
	 * Match the digests of the 2 streams to compare their equality
	 * @param stream1
	 * @param stream2
	 * @return true if the 2 streams are exactly identical, false otherwise
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static boolean matchStreamsForEquality(final InputStream stream1,
			                                      final InputStream stream2) throws NoSuchAlgorithmException,
			                                                                         IOException {
		//Compute digests on both stream, and compare for equality

		MessageDigest md1 = MessageDigest.getInstance("SHA");
		MessageDigest md2 = MessageDigest.getInstance("SHA");
		//Calc Dig1
		readInto(stream1, md1);
		readInto(stream2, md2);
		byte[] dig1 = md1.digest();
		byte[] dig2 = md2.digest();
		if (MessageDigest.isEqual(dig1, dig2)) {
			return true;
		}
		return false;
	}

	private static void readInto(InputStream stream,
			                     final MessageDigest md) throws IOException {
		if (stream == null) {
			throw new IllegalArgumentException("Input parameter cannot be null");
		}
		/**
		 * The streams could have been read before in which case
		 * The "pos" value would be somewhere in the middle
		 * or even end of the stream. Need to reset it to last
		 * marked position which in this case is 0.
		 * If input stream is not a mark supporting stream wrap it into one.
		 *
		 */
		if (!stream.markSupported()) {
			//BufferedInputStream wrapped = new BufferedInputStream(stream);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			IOUtils.writeBytes(stream, bos);
			byte[] bytes = bos.toByteArray();
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			stream = bis;
		}
		stream.reset();
		ByteBuffer buf = ByteBuffer.allocate(1024);
		byte[] bufferArray = buf.array();
		int count = 0;
		int index = 0;
		while (count >= 0) {
			if (index == count) {
				count = stream.read(bufferArray);
				index = 0;
			}
			while (index < count && buf.hasRemaining()) {
				buf.put(bufferArray[index++]);
			}
			((Buffer)buf).flip();
			md.update(buf);
			if (buf.hasRemaining()) {
				buf.compact();
			} else {
				((Buffer)buf).clear();
			}
		}
	    //stream.close();
	}
}
