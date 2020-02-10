package com.tibco.cep.driver.soap;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Mar 31, 2009
 * Time: 6:52:13 AM
 * <!--
 * Add Description of the class here
 * -->
 */
public class SoapAttachment
{

    private String contentID;

    private String contentType;

    private String contentEncoding;

    private byte[] content;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public String getContentID() {
        return contentID;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
