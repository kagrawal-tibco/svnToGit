package com.tibco.be.oracle;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 17, 2006
 * Time: 1:37:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEOracleSource {
    private String url;
    private String userid;
    private String password;

    /**
     *
     * @param url
     * @param userid
     * @param password
     */
    public BEOracleSource(String url, String userid, String password) {
        this.setUrl(url);
        this.setUserid(userid);
        this.setPassword(password);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
