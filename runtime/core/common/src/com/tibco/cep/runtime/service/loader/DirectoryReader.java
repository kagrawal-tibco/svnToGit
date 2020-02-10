package com.tibco.cep.runtime.service.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarInputStream;
import java.util.zip.ZipInputStream;

import com.tibco.objectrepo.vfile.VFile;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Dec 19, 2005
 * Time: 4:59:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class DirectoryReader {

    String m_earPath;
    long   m_lastMod;
    long   m_length;

    public DirectoryReader(String earPath) throws Exception {
        m_earPath = earPath;
        File ear = new File(earPath);
        m_lastMod = ear.lastModified();
        m_length  = ear.length();
    }

    public Map read() throws Exception {
        File newEar = new File(m_earPath);
        long newLastMod = newEar.lastModified();
        long newLength  = newEar.length();

        if(newLastMod == m_lastMod && newLength == m_length) {
            return null;
        }
        else {
            //new ear detected
            while(true) {
                Thread.sleep(1000);
                long newAgainLastMod = newEar.lastModified();
                long newAgainLength  = newEar.length();

                if(newAgainLastMod == newLastMod && newAgainLength == newLength) {
                    //stable - assume no change in 1 sec is stable enough to take the file
                    break;
                }
                else {
                    newLastMod = newAgainLastMod;
                    newLength  = newAgainLength;
                }
            }
            m_lastMod = newLastMod;
            m_length  = newLength;
            return read(newEar);
        }
    }

    protected Map read(File earFile) throws Exception {
        FileInputStream fis = new FileInputStream(earFile);
        ZipInputStream zis  = new ZipInputStream(fis);
        VFileFactory vFileFactory = new ZipVFileFactory(zis);
        VFileDirectory vfd = vFileFactory.getRootDirectory();
        Map nameToByteCode = new HashMap ();
        handleVFile(vfd, nameToByteCode);
        zis.close();
        fis.close();
        return nameToByteCode;
    }

    protected void handleVFile(VFile vfile, Map nameToByteCode) throws Exception {
        if(vfile instanceof VFileDirectory) {
            for (Iterator children = ((VFileDirectory)vfile).getChildren(); children.hasNext(); ) {
                VFile child = (VFile)children.next();
                handleVFile(child, nameToByteCode);
            }
        }
        else if(vfile instanceof VFileStream) {
            String fileName = vfile.getName();
            String fileType = fileName.substring(fileName.length()-4);
            InputStream inputStream = ((VFileStream)vfile).getInputStream();
            if(fileType.equalsIgnoreCase(".bar")) {
                ZipInputStream zis  = new ZipInputStream(inputStream);
                ZipVFileFactory vff = new ZipVFileFactory(zis);
                VFileDirectory vfd  = vff.getRootDirectory();
                handleVFile(vfd, nameToByteCode);
                zis.close();
            }
            else if (fileType.equalsIgnoreCase(".jar")) {
                JarInputStream jis = new JarInputStream(inputStream);
                nameToByteCode.putAll(JarInputStreamReader.read(jis));
                jis.close();
            }
            inputStream.close();
        }
    }


//    private File moveTarget(File sourceEarFile) {
//        String fileName = sourceEarFile.getName().substring(0, sourceEarFile.getName().lastIndexOf(".ear"));
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
//        fileName = sdf.format(new Date()) + " " + fileName + ".ear";
//        File targetEarFile = new File(m_targetDir, fileName);
//        sourceEarFile.renameTo(targetEarFile);
//        return targetEarFile;
//    }

    private class FileComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            long ret = ((File)o1).lastModified() - ((File)o2).lastModified();
            return (int)ret;
        }
    }
}
