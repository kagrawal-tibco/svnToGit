package com.tibco.be.bw.plugin;
/*
 * User: nprade
 * Date: Feb 3, 2010
 * Time: 8:03:05 PM
 */

public class RspConfigValues {

    private String repoUrl;
    private String cddUrl;
    private String puid;


    public RspConfigValues(String cddUrl, String puid, String repoUrl) {
        if (null != repoUrl) {
            repoUrl = repoUrl.trim();
        }
        if (null != cddUrl) {
            cddUrl = cddUrl.trim();
        }
        if (null != puid) {
            puid = puid.trim();
        }
        this.cddUrl = cddUrl;
        this.puid = puid;
        this.repoUrl = repoUrl;
    }


    public String getCddUrl() {
        return this.cddUrl;
    }


    public String getPuid() {
        return this.puid;
    }


    public String getRepoUrl() {
        return this.repoUrl;
    }

    
}
