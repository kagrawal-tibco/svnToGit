package com.tibco.cep.bemm.security.authen.mm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.net.mime.Base64Codec;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 2/10/11
 * Time: 8:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class RolesCredentials {

    private Logger logger = LogManagerFactory.getLogManager().getLogger(this.getClass());
    private String path;
    private File file;

    //credentials are stored [username,password]
    private HashMap<String,LinkedList<String[]>> roleToCredentials = new HashMap<String, LinkedList<String[]>>();

    public RolesCredentials(File passwordFile) {
        this.file = passwordFile;
        this.path = file.getAbsolutePath();
        putRoleToCredtlsFromFile();
    }

    public RolesCredentials(String pwdFileFullPath) {
        this.path = pwdFileFullPath;
        this.file = new File(path);
        putRoleToCredtlsFromFile();
    }

    private void putRoleToCredtlsFromFile() {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            LinkedList<String[]> credsList;
            String row;
            MMRoles[] mmRoles = MMRoles.values();

            //each line in the file has the format USER_NAME:PASSWORD:ROLE_NAME1,ROLE_NAME2,...;
            while ((row = br.readLine())!=null) {
                row = row.substring(0,row.length()-1);  //remove ';' from the end of each row
                String[] rowSplit = row.split(":");
                String[] roles = rowSplit[2].toLowerCase().split(",");  //One username can be associated with multiple roles

                for (String role:roles) {
                    boolean isValidRole = false;

                    for (MMRoles mmRole : mmRoles) {
                        isValidRole |= role.equalsIgnoreCase(mmRole.getName());
                    }

                    //if not a valid role, do not put it in the map. Check next role
                    if(!isValidRole)
                        continue;

                    //role already exist in map
                    if((credsList = roleToCredentials.get(role))!=null) {
                        credsList.addLast(new String[]{rowSplit[0],rowSplit[1]});
                    } else {    //role not in map so put it
                        credsList = new LinkedList<String[]>();
                        credsList.addLast((new String[]{rowSplit[0],rowSplit[1]}));
                        roleToCredentials.put(role, credsList);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.ERROR,e,"Could not find file: %S",file.getAbsolutePath());
        } catch (IOException ioe ) {
            logger.log(Level.ERROR,ioe,"Could not find file: %S",file.getAbsolutePath());
        }
    }

    private String decodePwd(String encodedPwd) {            //TODO delete
        try {
            return Base64Codec.decodeBase64(encodedPwd, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.ERROR,"Password encoded with unsupported coding scheme. Proceeding with encoded password.");
        }
        return encodedPwd;
    }


    /** Returns ONE set of credentials associated with the specified role, or null if no credentials are specified
     *  for that role */
    public String[] getCredentials(String roleName) {
        if (roleToCredentials != null && roleToCredentials.get(roleName)!=null)
            roleToCredentials.get(roleName).getFirst();

        return null;
    }

    /** Iterates over the allowed roles until it finds one role with credentials specified in the passwords file.
     *  If no role with credentials is found [null,null] is returned.
     *  The search starts in the role with higher level of access.
     * */
    public String[] getCredentials() {
        for (MMRoles mmRole : MMRoles.values()) {
            if(roleToCredentials!=null && roleToCredentials.containsKey(mmRole.getName()))
                return roleToCredentials.get(mmRole.getName()).getFirst();
        }
        return null;
    }

//    public static void main(String[] args) {
//        System.out.println("Starting");
//        System.setProperty(JMXConnUtil.BE_PROPERTIES.AUTH_FILE, "C:\\Software\\Tibco\\Dev\\Debug\\5.0\\Test\\users.pwd");
//        RolesCredentials rc = new RolesCredentials(System.getProperty(JMXConnUtil.BE_PROPERTIES.AUTH_FILE));
//        String[] creds = rc.getCredentials();
//        System.err.println(creds);
//        System.out.println("End");
//    }

}
