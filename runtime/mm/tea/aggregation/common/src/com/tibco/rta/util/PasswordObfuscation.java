package com.tibco.rta.util;

import java.lang.reflect.Method;

import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;


/**
 * PasswordObfuscation is a wrapper class to provide
 * encryption and decryption of passwords.
 * <p/>
 * Encryption/decryption is done by TIBCrypto.
 * <p/>
 * When encryption is done through TIBCrypto, the encryption
 * algorithm is DES3 in CBC mode. JSSE is used as the security provider.
 * When encrypted with TIBCrypto, the password is prefix with #! and is
 * base64 encoded. The key use for e/d is copied from TIBCrypto library.
 */
public class PasswordObfuscation {
    public static String CNAME = PasswordObfuscation.class.getName();

    //defines for pre-fixes
    private final static String TIBCRYPT_PREFIX = "#!";

    private static String defaultCryptoVendor = null;

    /* Return the default security vendor. */
    /* this is needed for AIX or AS/400, because the JVMs do not that the default j2se */
    //private static String getCryptoVendor() throws AXSecurityException {
    private static String getCryptoVendor() throws RTASecurityException {
        String cryptoVendor = System.getProperty("TIBCO_SECURITY_VENDOR");
        if (cryptoVendor == null)
            cryptoVendor = getDefaultVendor();
        return cryptoVendor;
    }

    private static String getDefaultVendor() {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("AIX"))
            return "ibm";
        if (osName.startsWith("OS/400"))
            return "ibm";
        return "j2se-default";
    }

    /**
     * Compute the encrypted form for  given chars
     *
     * @param inBuf chars to be obfuscated
     * @return the Base-64 encoded String of encrypted data. null if failed.
     */
    public static String encrypt(String plainText) throws RTASecurityException {
        if (plainText == null)
            return null;

        String result = null;

        if (!plainText.startsWith(TIBCRYPT_PREFIX)) {

            // install the crypto vendor now if have done so.
            if (defaultCryptoVendor == null) {
                defaultCryptoVendor = getCryptoVendor();
                initTibcrypt(defaultCryptoVendor);
            }

            // Use ObfuscationEngine in Tibcrypt from EMS or TRA?
            // From 4.7, Tibcrypt must be of 2.8 and above.
            // Tibcrypt from EMS 4.1 is 2.6 will not work as it does not has ObfuscationEngine
            try {
                result = ObfuscationEngine.encrypt(plainText.toCharArray());
            } catch (AXSecurityException e) {
                e.printStackTrace();
            } catch (Throwable cnfe) {
                cnfe.printStackTrace();
            }
        }

        // did not do any encryption, return the original
        if (result == null)
            result = plainText;

        return result;
    }

    /**
     * Compute the decrypted form for given string
     *
     * @param text Base-64 encoded String of encrypted data to be decrypted
     * @return the decrypted chars. null if failed.
     * @throws AXSecurityException if the obfuscated bytes is incorrect.
     */
    //public static String decrypt(String encrypted) throws AXSecurityException {
    public static String decrypt(String encrypted) throws RTASecurityException {
        if (encrypted == null)
            return null;

        String result = null;

        if (encrypted.startsWith(TIBCRYPT_PREFIX)) {
        	RTASecurityException axe = null;

            // install the crypto vendor now if have done so.
            if (defaultCryptoVendor == null) {
                defaultCryptoVendor = getCryptoVendor();
                initTibcrypt(defaultCryptoVendor);
            }

            // Use ObfuscationEngine in Tibcrypt from EMS or TRA?
            // From 4.7, Tibcrypt must be of 2.8 and above.
            // Tibcrypt from EMS 4.1 is 2.6 will not work as it does not has ObfuscationEngine
            try {
                char[] work = ObfuscationEngine.decrypt(encrypted);
                result = new String(work);
            } catch (Throwable e) {

                e.printStackTrace();
                if (e instanceof AXSecurityException) {
                	axe = new RTASecurityException(e.getMessage());
                }
                else {
                	axe = new RTASecurityException(e.getMessage());
                }
                throw axe;
            }

        }

        // did not do any decryption, return the original
        if (result == null)
            result = encrypted;

        return result;
    }

    // Initialize TIBCrypt.
    // Use reflection to avoid dependency on EMS client if not needed.   
    public static void initTibcrypt(String vendor) {
        Class params[] = {String.class};

        try {
            // get the Class
            Class<?> thisClass = Class.forName("com.tibco.security.TIBCOSecurity");

            // get the method
            Method thisMethod = thisClass.getDeclaredMethod("init", params);

            // call the method
            Object args[] = new Object[1];
            args[0] = vendor;
            thisMethod.invoke(null, args);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to initialize TIBCOSecurity. Exception: " + e);
        }
    }


    public static void main(String[] args) {
        if (args.length != 2 || (!args[0].equals("-encrypt") && !args[0].equals("-decrypt"))) {
            System.out.println("Usage: tibspmpassword [-encrypt|-decrypt] <password>");
            System.out.println("Note:  Encrypted string from the output can be used as password to the -ems_transport.");
            return;
        }

        if (args[0].equals("-encrypt")) {
            try {
                String encrypted = encrypt(args[1]);
                System.out.println("Encrypted password: " + encrypted);
            } catch (Exception e) {
                System.out.println("Failed to encrypt: " + args[1]);
            }
        } else if (args[0].equals("-decrypt")) {

            try {
                String decrypted = decrypt(args[1]);
                System.out.println("Decrypted password: " + decrypted);
            } catch (Exception e) {
                System.out.println("Failed to decrypt: " + args[1]);
            }

        }
    }
}
