package com.tibco.cep.query.utils;


public class UnicodeConvert {
    /**
       * Given the input string with escaped unicode characters convert them
       * to their native unicode characters and return the result. This is quite
       * similar to the functionality found in property file handling. White space
       * escapes are not processed (as they are consumed by the template library).
       * Any bogus escape codes will remain in place.
       * <p>
       * When files are provided in another encoding, they can be converted to ascii using
       * the native2ascii tool (a java sdk binary). This tool will escape all the
       * non Latin1 ASCII characters and convert the file into Latin1 with unicode escapes.
       *
       * @param source
       *      string with unicode escapes
       * @return
       *      string with all unicode characters, all unicode escapes expanded.
       *
       */
    public static String unescapeUnicode(String source) {
         /* could use regular expression, but not this time... */
         final int srcLen = source.length();
         char c;

         StringBuffer buffer = new StringBuffer(srcLen);

         // Must have format \\uXXXX where XXXX is a hexadecimal number
         int i=0;
         while (i <srcLen-5) {

                c = source.charAt(i++);

                if (c=='\\') {
                    char nc = source.charAt(i);
                    if (nc == 'u') {

                        // Now we found the u we need to find another 4 hex digits
                        // Note: shifting left by 4 is the same as multiplying by 16
                        int v = 0; // Accumulator
                        for (int j=1; j < 5; j++) {
                            nc = source.charAt(i+j);
                            switch(nc)
                            {
                                case 48: // '0'
                                case 49: // '1'
                                case 50: // '2'
                                case 51: // '3'
                                case 52: // '4'
                                case 53: // '5'
                                case 54: // '6'
                                case 55: // '7'
                                case 56: // '8'
                                case 57: // '9'
                                    v = ((v << 4) + nc) - 48;
                                    break;

                                case 97: // 'a'
                                case 98: // 'b'
                                case 99: // 'c'
                                case 100: // 'd'
                                case 101: // 'e'
                                case 102: // 'f'
                                    v = ((v << 4)+10+nc)-97;
                                    break;

                                case 65: // 'A'
                                case 66: // 'B'
                                case 67: // 'C'
                                case 68: // 'D'
                                case 69: // 'E'
                                case 70: // 'F'
                                    v = ((v << 4)+10+nc)-65;
                                    break;
                                default:
                                    // almost but no go
                                    j = 6;  // terminate the loop
                                    v = 0;  // clear the accumulator
                                    break;
                            }
                        } // for each of the 4 digits

                        if (v > 0) {      // We got a full conversion
                            c = (char)v;  // Use the converted char
                            i += 5;       // skip the numeric values
                        }
                    }
                }
                buffer.append(c);
            }

        // Fill in the remaining characters from the buffer
        while (i <srcLen) {
            buffer.append(source.charAt(i++));
        }
        return buffer.toString();
    }

}
