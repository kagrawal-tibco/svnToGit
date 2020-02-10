package com.tibco.cep.mapper.util;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JavaClassfileManipulator
{
    // The following bits are used to traverse a class file to find the class name.
//    private static final int CONSTANT_EMPTY                 =   0;
    private static final int CONSTANT_Utf8                  =   -1;
    private static final int CONSTANT_UNDEFINED             =   -2;
    private static final int CONSTANT_Class                 =   -7;
    private static final int CONSTANT_LongOrDouble          =   -5;
    private static final int PUBLIC_CLASS                   =   33;

    private static final int[] SKIP_BYTES;

    static
    {
        SKIP_BYTES = new int[]
        {
            CONSTANT_UNDEFINED,         // empty tag
            CONSTANT_Utf8,              // Utf8
            CONSTANT_UNDEFINED,         // undefined
            4,                          // Integer
            4,                          // Float
            CONSTANT_LongOrDouble,      // Long
            CONSTANT_LongOrDouble,      // Double
            CONSTANT_Class,             // Class
            2,                          // String
            4,                          // Fieldref
            4,                          // Methodref
            4,                          // InterfaceMethodref
            4,                          // NameAndType
        };
    }

    /**
     * Extracts out the classname from the bytecode, throws an IOException if not a class.
     */
    public static String getClassNameFromBytecode(byte[] code)
        throws IOException
    {
        int value;
        Map strings = new HashMap();
        Map classes = new HashMap();
        DataInputStream classfile = new DataInputStream(new ByteArrayInputStream(code));

        readHeadersAndConstants(classfile, strings, classes);

        // access flags
        value=classfile.readUnsignedShort();
        // Do not read value yet; allow it to continue: if (value != PUBLIC_CLASS)
        // this class
        value=classfile.readUnsignedShort();
        Integer pos = (Integer) classes.get(new Integer(value));
        String className = (String) strings.get(pos);
        return className.replace('/','.');
    }

    /**
     * Indicates if it's not a public class, throws an IOException if not a class.
     */
    public static boolean isPublicClass(byte[] code)
        throws IOException
    {
        int value;
        Map strings = new HashMap();
        Map classes = new HashMap();
        DataInputStream classfile = new DataInputStream(new ByteArrayInputStream(code));

        readHeadersAndConstants(classfile, strings, classes);

        // access flags
        value=classfile.readUnsignedShort();
        return value == PUBLIC_CLASS;
    }

    private static void readHeadersAndConstants(DataInputStream classfile, Map strings, Map classes) throws IOException
    {
        int value;
        // u4 magic
        value=classfile.readInt();
        if (value != 0xCAFEBABE)
        {
            throw new IOException("Not a class file!");
        }
        // u2 minor version
        value=classfile.readUnsignedShort();
        // u2 major version
        value=classfile.readUnsignedShort();
        // u2 constant pool count
        int constantPoolCount = classfile.readUnsignedShort();
        for (int i = 1; i < constantPoolCount; i++) // start at 3 because entry constant_pool[0] does not exist, but is of size 3.
        {
            if (readConstant(classfile, i, strings, classes))
            {
                i++; // Long and Double count for two entries in the constant table.
            }
        }
    }

    /**
     * Reads the constant from the constant pool
     */
    private static boolean readConstant(DataInput classfile, int index, Map utf8s, Map classes)
        throws IOException
    {
        int tag = classfile.readUnsignedByte();
        int skipBytes = SKIP_BYTES[tag];
        boolean isLongOrDouble =false;

        if (skipBytes < 0)
        {
            switch (skipBytes)
            {
                case CONSTANT_UNDEFINED:
                    throw new IOException("Invalid classfile!");

                case CONSTANT_Class:
                {
                    int classPos = classfile.readUnsignedShort();
                    //System.out.println(index + " " + tag + " " + classPos);
                    classes.put(new Integer(index), new Integer(classPos));
                    break;
                }

                case CONSTANT_Utf8:
                {
                    String utf8 = classfile.readUTF();
                    //System.out.println(index + " " + utf8);
                    utf8s.put(new Integer(index), utf8);
                    break;
                }

                case CONSTANT_LongOrDouble:
                {
                    classfile.skipBytes(8);
                    isLongOrDouble = true;      // count for two table entries!
                }
            }
        }
        else
        {
            //System.out.println(index + " " + tag);
            classfile.skipBytes(skipBytes);
        }
        return isLongOrDouble;
    }
}
