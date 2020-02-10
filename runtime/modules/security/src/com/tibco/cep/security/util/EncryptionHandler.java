/**
 * 
 */
package com.tibco.cep.security.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.tibco.cep.security.authz.utils.IOUtils;

/**
 * A utility class to handle encryption, and decryption.
 * @author aathalye
 *
 */
public class EncryptionHandler {

    private KeyPair keyPair;
    private int keySize;
    /**
     * Use available store if present
     */
    private boolean useExistingStore;

    public EncryptionHandler(int keySize, boolean useExistingStore) {
        if (keySize > 1024) {
            throw new IllegalArgumentException("Key size parameter cannot exceed 1024");
        }
        if (keySize < 512) {
            throw new IllegalArgumentException("Key size parameter cannot be less than 512");
        }
        this.keySize = keySize;
        this.useExistingStore = useExistingStore;
    }
    private static final int KEY_BITS_SIZE = 1024;

    public EncryptionHandler() {
        keySize = KEY_BITS_SIZE;
    }
    private static final int PADDING_BITS_SIZE = 11;
    private static File PRIV_FILE = new File(System.getProperty("java.io.tmpdir") + File.separatorChar + "Priv.ks");
    private static File PUB_FILE = new File(System.getProperty("java.io.tmpdir") + File.separatorChar + "Pub.ks");

    private boolean generateKeyPair(String privateKeyFilePath) throws Exception {
        if (useExistingStore) {
            //Check if store exists
            if (privateKeyFilePath != null) {
                PRIV_FILE = new File(privateKeyFilePath);
            }
            if (!PRIV_FILE.exists()) {
                PRIV_FILE.createNewFile();
            }
            if (PRIV_FILE.exists() && PUB_FILE.exists()) {
                //Get keys from it
                byte[] privKeyEncoded = getEncodedKey(PRIV_FILE);
                PrivateKey privKey = getPrivateKey(privKeyEncoded);

                byte[] pubKeyEncoded = getEncodedKey(PUB_FILE);
                PublicKey pubKey = getPublicKey(pubKeyEncoded);
                keyPair = new KeyPair(pubKey, privKey);
                return false;
            }
        }
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(keySize, random);
        keyPair = keyGen.generateKeyPair();
        return true;
    }

    private void storePrivateKey(String privateKeyFilePath) throws IOException {
        PRIV_FILE = new File(privateKeyFilePath);
        storeKey(PRIV_FILE, keyPair.getPrivate());
    }

    private void storePrivateKey() throws IOException {
        storeKey(PRIV_FILE, keyPair.getPrivate());
    }

    private void storePublicKey() throws IOException {
        storeKey(PUB_FILE, keyPair.getPublic());
    }

    private <K extends Key> void storeKey(File storeFile, K key) throws IOException {
        //Get its encoded format
        byte[] keyBytes = key.getEncoded();
        IOUtils.writeBytesToFile(keyBytes, storeFile);
    }

    private byte[] getEncodedKey(final File file) {
        try {
            return IOUtils.readBytes(file.getAbsolutePath());
        } catch (IOException ioe) {
            return new byte[0];
        }
    }

    /**
     * This methods encrypts raw bytes of clear text, and writes out 
     * encrypted bytes to the <tt>OutputStream</tt>.
     * <p>
     * Uses RSA:1024 keysize with <b>PKCS1Padding</b>
     * </p>
     * <p>
     * This uses <b>ECB</b> mode of block encryption. In one batch
     * number of bytes=keysize can be encrypted.
     * </p>
     * @param out: The <tt>OutputStream</tt> to write encrypted content
     * @param bytes: A <tt>byte[]</tt> representation of the clear text
     * @throws Exception
     */
    public void encryptFile(final OutputStream out,
            final byte[] bytes, String privateKeyFilePath) throws Exception {

        if (bytes.length > 0) {
            int length = bytes.length;
            //StudioUtilPlugin.debug(getClass().getName(),MessageFormat.format("Size of incoming raw clear text data {0}",
            //           length));
            //Start encryption
            Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            boolean keyPairGenerated = generateKeyPair(privateKeyFilePath);
            Key pubKey = keyPair.getPublic();
            rsa.init(Cipher.ENCRYPT_MODE, pubKey);
            //For RSA the max data size will be equal to keySizeBits - paddingBits.
            //So in above case it will be 128 - 11 = 117
            int keysize = (keySize >>> 3) - PADDING_BITS_SIZE;
            //StudioUtilPlugin.debug(getClass().getName(),MessageFormat.format("Keysize used for encryption {0}",keysize));
            int mod = (length % keysize);
            //StudioUtilPlugin.debug(getClass().getName(),MessageFormat.format("Modulo parameter value {0}",mod));
            int bytesWritten = 0;
            int off = 0;
            //Chunk the stream to write at a max only number of bytes = keysize
            while (bytesWritten < length) {
                //StudioUtilPlugin.debug(getClass().getName(),MessageFormat.format("Number of bytes encrypted {0}",bytesWritten));
                int bytesToBeWritten = length - (bytesWritten);
                if (bytesToBeWritten < keysize) {
                    keysize = mod;
                }
                byte[] enc = rsa.doFinal(bytes, off, keysize);
                out.write(enc);
                bytesWritten = off + keysize;
                off += keysize;
            }
            out.close();
            //Store the private key if it was generated
            if (keyPairGenerated) {
                if (privateKeyFilePath != null) {
                    storePrivateKey(privateKeyFilePath);
                } else {
                    storePrivateKey();
                }
                storePublicKey();
            }
        }
    }

    /**
     * This methods encrypts raw bytes of clear text, and returns 
     * encrypted bytes.
     * <p>
     * Uses RSA:1024 keysize with <b>PKCS1Padding</b>
     * </p>
     * <p>
     * This uses <b>ECB</b> mode of block encryption. In one batch
     * number of bytes=keysize can be encrypted.
     * </p>
     * @param bytes: A <tt>byte[]</tt> representation of the clear text
     * @throws Exception
     */
    public byte[] encrypt(byte[] bytes, String filePath) throws Exception {
        //Create a temp stream
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        encryptFile(bos, bytes, filePath);
        return bos.toByteArray();
    }

    /**
     * This method decrypts an encrypted byte stream to return the
     * <tt>byte[]</tt> representing decrypted form
     * <p>
     * Uses RSA:1024 keysize with <b>PKCS1Padding</b>
     * </p>
     * @param encBytes: The encrypted bytes to decrypt
     * @return the <tt>byte[]</tt> representing clear text
     * @throws Exception
     */
    public byte[] decrypt(final byte[] encBytes, String privateKeyFilePath) throws Exception {

        if (privateKeyFilePath != null) {
            PRIV_FILE = new File(privateKeyFilePath);
        }

        byte[] decryptedBytes = null;
        Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        //Get private key to decrypt from storage
        byte[] privKeyEncoded = getEncodedKey(PRIV_FILE);
        if (privKeyEncoded.length == 0) {
            return new byte[0];
        }
        PrivateKey privKey = getPrivateKey(privKeyEncoded);
        rsa.init(Cipher.DECRYPT_MODE, privKey);
        int length = encBytes.length;

        if (length > 0) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int keysize = (keySize >>> 3);
            int mod = (length % keysize);
            int bytesWritten = 0;
            int off = 0;
            //Chunk the stream to write at a max only number of bytes = keysize
            while (bytesWritten < length) {
                int bytesToBeWritten = length - (bytesWritten);
                if (bytesToBeWritten < keysize) {
                    keysize = mod;
                }
                byte[] dec = rsa.doFinal(encBytes, off, keysize);
                bos.write(dec);
                bytesWritten = off + keysize;
                off += keysize;
            }
            bos.close();
            decryptedBytes = bos.toByteArray();
        }
        return decryptedBytes;
    }

    /**
     * Return the private key from the byte stream.
     * <p>
     * The format of the key should comply with <b>PKCS#8</b>
     * specification.
     * </p>
     * @param encoded
     * @return
     * @throws Exception
     */
    private PrivateKey getPrivateKey(final byte[] encoded) throws Exception {
        PrivateKey pKey = null;
        if (encoded != null && encoded.length > 0) {
            final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encoded);
            final KeyFactory factory = KeyFactory.getInstance("RSA");
            pKey = factory.generatePrivate(spec);
        }
        return pKey;
    }

    /**
     * Return the public key from the byte stream.
     * <p>
     * The format of the key should comply with <b>PKCS#8</b>
     * specification.
     * </p>
     * @param encoded
     * @return
     * @throws Exception
     */
    private PublicKey getPublicKey(final byte[] encoded) throws Exception {
        PublicKey pKey = null;
        if (encoded != null && encoded.length > 0) {
            final X509EncodedKeySpec spec = new X509EncodedKeySpec(encoded);
            final KeyFactory factory = KeyFactory.getInstance("RSA");
            pKey = factory.generatePublic(spec);
        }
        return pKey;
    }
}
