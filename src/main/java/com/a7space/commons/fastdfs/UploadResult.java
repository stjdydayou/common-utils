package com.a7space.commons.fastdfs;

import java.io.Serializable;

public class UploadResult implements Serializable {
    private static final long serialVersionUID = -9105589442902748697L;

    private Long id;

    private boolean success;

    private String message;

    private String url;

    private String subfix;

    private String mimeType;

    private long size;

    private String imgFileHost;

    private String fileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getImgFileHost() {
        return imgFileHost;
    }

    public void setImgFileHost(String imgFileHost) {
        this.imgFileHost = imgFileHost;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSubfix() {
        return subfix;
    }

    public void setSubfix(String subfix) {
        this.subfix = subfix;
    }
}
