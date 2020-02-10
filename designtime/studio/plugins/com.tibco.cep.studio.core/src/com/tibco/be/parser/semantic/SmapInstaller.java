package com.tibco.be.parser.semantic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.tibco.cep.studio.core.StudioCorePlugin;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jan 9, 2009
 * Time: 5:34:47 PM
 * To change this template use File | Settings | File Templates.
 */

//*********************************************************************
// Installation logic (from Robert Field, JSR-045 spec lead)
public class SmapInstaller {


    static final String nameSDE = "SourceDebugExtension";

    byte[] orig;
    byte[] sdeAttr;
    byte[] gen;

    int origPos = 0;
    int genPos = 0;

    int sdeIndex;

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            install(new File(args[0]), new File(args[1]));
        } else if (args.length == 3) {
            install(
                    new File(args[0]),
                    new File(args[1]),
                    new File(args[2]));
        } else {
            System.err.println(
                    "Usage: <command> <input class file> "
                            + "<attribute file> <output class file name>\n"
                            + "<command> <input/output class file> <attribute file>");
        }
    }

    static void install(File inClassFile, File attrFile, File outClassFile)
            throws IOException {
        new SmapInstaller(inClassFile, attrFile, outClassFile);
    }

    static void install(File inOutClassFile, File attrFile)
            throws IOException {
        File tmpFile = new File(inOutClassFile.getPath() + "tmp");
        new SmapInstaller(inOutClassFile, attrFile, tmpFile);
        if (!inOutClassFile.delete()) {
            throw new IOException("inOutClassFile.delete() failed");
        }
        if (!tmpFile.renameTo(inOutClassFile)) {
            throw new IOException("tmpFile.renameTo(inOutClassFile) failed");
        }
    }

    static void install(File classFile, byte[] smap) throws IOException {
        File tmpFile = new File(classFile.getPath() + "tmp");
        new SmapInstaller(classFile, smap, tmpFile);
        if (!classFile.delete()) {
            throw new IOException("classFile.delete() failed");
        }
        if (!tmpFile.renameTo(classFile)) {
            throw new IOException("tmpFile.renameTo(classFile) failed");
        }
    }

    SmapInstaller(File inClassFile, byte[] sdeAttr, File outClassFile)
            throws IOException {
        if (!inClassFile.exists()) {
            throw new FileNotFoundException("no such file: " + inClassFile);
        }

        this.sdeAttr = sdeAttr;
        // get the bytes
        orig = readWhole(inClassFile);
        gen = new byte[orig.length + sdeAttr.length + 100];

        // do it
        addSDE();

        // write result
        FileOutputStream outStream = new FileOutputStream(outClassFile);
        outStream.write(gen, 0, genPos);
        outStream.close();
    }

    SmapInstaller(File inClassFile, File attrFile, File outClassFile)
            throws IOException {
        this(inClassFile, readWhole(attrFile), outClassFile);
    }

    static byte[] readWhole(File input) throws IOException {
        FileInputStream inStream = new FileInputStream(input);
        int len = (int) input.length();
        byte[] bytes = new byte[len];
        if (inStream.read(bytes, 0, len) != len) {
            throw new IOException("expected size: " + len);
        }
        inStream.close();
        return bytes;
    }

    void addSDE() throws UnsupportedEncodingException, IOException {
        int i;
        copy(4 + 2 + 2); // magic min/maj version
        int constantPoolCountPos = genPos;
        int constantPoolCount = readU2();
            StudioCorePlugin.debug("constant pool count: " + constantPoolCount);
        writeU2(constantPoolCount);

        // copy old constant pool return index of SDE symbol, if found
        sdeIndex = copyConstantPool(constantPoolCount);
        if (sdeIndex < 0) {
            // if "SourceDebugExtension" symbol not there add it
            writeUtf8ForSDE();

            // increment the countantPoolCount
            sdeIndex = constantPoolCount;
            ++constantPoolCount;
            randomAccessWriteU2(constantPoolCountPos, constantPoolCount);

                StudioCorePlugin.debug("SourceDebugExtension not found, installed at: " + sdeIndex);
        } else {
                StudioCorePlugin.debug("SourceDebugExtension found at: " + sdeIndex);
        }
        copy(2 + 2 + 2); // access, this, super
        int interfaceCount = readU2();
        writeU2(interfaceCount);
            StudioCorePlugin.debug("interfaceCount: " + interfaceCount);
        copy(interfaceCount * 2);
        copyMembers(); // fields
        copyMembers(); // methods
        int attrCountPos = genPos;
        int attrCount = readU2();
        writeU2(attrCount);
            StudioCorePlugin.debug("class attrCount: " + attrCount);
        // copy the class attributes, return true if SDE attr found (not copied)
        if (!copyAttrs(attrCount)) {
            // we will be adding SDE and it isn't already counted
            ++attrCount;
            randomAccessWriteU2(attrCountPos, attrCount);
                StudioCorePlugin.debug("class attrCount incremented");
        }
        writeAttrForSDE(sdeIndex);
    }

    void copyMembers() {
        int count = readU2();
        writeU2(count);
            StudioCorePlugin.debug("members count: " + count);
        for (int i = 0; i < count; ++i) {
            copy(6); // access, name, descriptor
            int attrCount = readU2();
            writeU2(attrCount);
                StudioCorePlugin.debug("member attr count: " + attrCount);
            copyAttrs(attrCount);
        }
    }

    boolean copyAttrs(int attrCount) {
        boolean sdeFound = false;
        for (int i = 0; i < attrCount; ++i) {
            int nameIndex = readU2();
            // don't write old SDE
            if (nameIndex == sdeIndex) {
                sdeFound = true;
                    StudioCorePlugin.debug("SDE attr found");
            } else {
                writeU2(nameIndex); // name
                int len = readU4();
                writeU4(len);
                copy(len);
                    StudioCorePlugin.debug("attr len: " + len);
            }
        }
        return sdeFound;
    }

    void writeAttrForSDE(int index) {
        writeU2(index);
        writeU4(sdeAttr.length);
        for (int i = 0; i < sdeAttr.length; ++i) {
            writeU1(sdeAttr[i]);
        }
    }

    void randomAccessWriteU2(int pos, int val) {
        int savePos = genPos;
        genPos = pos;
        writeU2(val);
        genPos = savePos;
    }

    int readU1() {
        return ((int) orig[origPos++]) & 0xFF;
    }

    int readU2() {
        int res = readU1();
        return (res << 8) + readU1();
    }

    int readU4() {
        int res = readU2();
        return (res << 16) + readU2();
    }

    void writeU1(int val) {
        gen[genPos++] = (byte) val;
    }

    void writeU2(int val) {
        writeU1(val >> 8);
        writeU1(val & 0xFF);
    }

    void writeU4(int val) {
        writeU2(val >> 16);
        writeU2(val & 0xFFFF);
    }

    void copy(int count) {
        for (int i = 0; i < count; ++i) {
            gen[genPos++] = orig[origPos++];
        }
    }

    byte[] readBytes(int count) {
        byte[] bytes = new byte[count];
        for (int i = 0; i < count; ++i) {
            bytes[i] = orig[origPos++];
        }
        return bytes;
    }

    void writeBytes(byte[] bytes) {
        for (int i = 0; i < bytes.length; ++i) {
            gen[genPos++] = bytes[i];
        }
    }

    int copyConstantPool(int constantPoolCount)
            throws UnsupportedEncodingException, IOException {
        int sdeIndex = -1;
        // copy const pool index zero not in class file
        for (int i = 1; i < constantPoolCount; ++i) {
            int tag = readU1();
            writeU1(tag);
            switch (tag) {
                case 7: // Class
                case 8: // String
                        StudioCorePlugin.debug(i + " copying 2 bytes");
                    copy(2);
                    break;
                case 9: // Field
                case 10: // Method
                case 11: // InterfaceMethod
                case 3: // Integer
                case 4: // Float
                case 12: // NameAndType
                    StudioCorePlugin.debug(i + " copying 4 bytes");
                    copy(4);
                    break;
                case 5: // Long
                case 6: // Double
                        StudioCorePlugin.debug(i + " copying 8 bytes");
                    copy(8);
                    i++;
                    break;
                case 1: // Utf8
                    int len = readU2();
                    writeU2(len);
                    byte[] utf8 = readBytes(len);
                    String str = new String(utf8, "UTF-8");
                    StudioCorePlugin.debug(i + " read class attr -- '" + str + "'");
                    if (str.equals(nameSDE)) {
                        sdeIndex = i;
                    }
                    writeBytes(utf8);
                    break;
                default:
                    throw new IOException("unexpected tag: " + tag);
            }
        }
        return sdeIndex;
    }

    void writeUtf8ForSDE() {
        int len = nameSDE.length();
        writeU1(1); // Utf8 tag
        writeU2(len);
        for (int i = 0; i < len; ++i) {
            writeU1(nameSDE.charAt(i));
        }
    }
}
