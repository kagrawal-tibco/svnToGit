package com.tibco.be.jdbcstore;

public class HexUtil
{
    public static String bytesToHex(byte[] data)
    {
        return bytesToHex(data, 0, data.length);
    }

    public static String bytesToHex(byte[] data, int offset, int len)
    {
        StringBuffer    hexStrBuf = new StringBuffer();
        bytesToHex(data, offset, len, hexStrBuf);
        return hexStrBuf.toString();
    }

    public static void bytesToHex(byte[] data, int offset, int len, StringBuffer hexStrBuf)
    {
        int             end = offset + len;

        for (int i = offset; i < end; i++)
        {
            hexStrBuf.append(sHexChar[(data[i] >> 4) & 0xF]);
            hexStrBuf.append(sHexChar[data[i] & 0xF]);
        }
    }

    public static byte[] hexToBytes(String hexStr)
            throws Exception
    {
        byte[]  bytes = new byte[hexStr.length() / 2];
        hexToBytes(hexStr, bytes, 0);
        return bytes;
    }

    public static int hexToBytes(String hexStr, byte[] bytebuf, int offset)
            throws Exception
    {
        int     len = hexStr.length();

        for (int i = 0; i < len; i += 2)
        {
            char    highChar = hexStr.charAt(i);
            char    lowChar = hexStr.charAt(i+1);
            byte    highNibble = 0;
            byte    lowNibble = 0;

            if (highChar >= '0' && highChar <= '9')
            {
                highNibble = (byte)(highChar - '0');
            }
            else if (highChar >= 'A' && highChar <= 'F')
            {
                highNibble = (byte)(10 + highChar - 'A');
            }
            else if (highChar >= 'a' && highChar <= 'f')
            {
                highNibble = (byte)(10 + highChar - 'a');
            }
            else
            {
                throw new Exception("Invalid hexadecimal string " + highChar + " of '" + hexStr + "'");
            }

            if (lowChar >= '0' && lowChar <= '9')
            {
                lowNibble = (byte)(lowChar - '0');
            }
            else if (lowChar >= 'A' && lowChar <= 'F')
            {
                lowNibble = (byte)(10 + lowChar - 'A');
            }
            else if (lowChar >= 'a' && lowChar <= 'f')
            {
                lowNibble = (byte)(10 + lowChar - 'a');
            }
            else
            {
                throw new Exception("Invalid hexadecimal string " + lowChar + " of '" + hexStr + "'");
            }

            bytebuf[offset++] = (byte)(highNibble << 4 | lowNibble);
        }

        return offset;
    }

    private static char[]   sHexChar;

    static
    {
        sHexChar = new char[16];
        sHexChar[0] = '0';
        sHexChar[1] = '1';
        sHexChar[2] = '2';
        sHexChar[3] = '3';
        sHexChar[4] = '4';
        sHexChar[5] = '5';
        sHexChar[6] = '6';
        sHexChar[7] = '7';
        sHexChar[8] = '8';
        sHexChar[9] = '9';
        sHexChar[10] = 'A';
        sHexChar[11] = 'B';
        sHexChar[12] = 'C';
        sHexChar[13] = 'D';
        sHexChar[14] = 'E';
        sHexChar[15] = 'F';
    }
}
