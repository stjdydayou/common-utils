package com.a7space.commons.fastdfs;

import java.io.Serializable;

public class FastDFSFile implements Serializable {

    private static final long serialVersionUID = -996760121932438618L;

    private String name;

    private byte[] content;

    private String ext;

    private String height = "120";

    private String width = "120";

    public FastDFSFile(String name, byte[] content, String ext, String height, String width) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.height = height;
        this.width = width;
    }

    public FastDFSFile(String name, byte[] content, String ext) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
