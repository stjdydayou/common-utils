package com.a7space.commons.fastdfs;

public enum MimeType {
    jpg("FFD8FF", "jpg", "image/jpeg"),
    png("89504E47", "png", "image/jpeg"),
    gif("47494638", "gif", "image/gif"),
    tif("49492A00", "tif", "image/tiff"),
    bmp("424D", "bmp", "image/x-windows-bmp"), //Windows Bitmap (bmp)
    //    dwg("41433130", "dwg", "application/acad"),//CAD (dwg)
//    html("68746D6C3E", "html", "text/html"),
//    rtf("7B5C727466", "rtf", "text/richtext"),//Rich Text Format (rtf)
//    xml("3C3F786D6C", "xml", "text/xml"),
//    zip("504B0304", "zip", "application/zip"),
//    rar("52617221", "rar", ""),
//    psd("38425053", "psd", "application/octet-stream"),
//    eml("44656C69766572792D646174653A", "eml", ""),//Email [thorough only] (eml)
//    dbx("CFAD12FEC5FD746F", "dbx", ""),//Outlook Express (dbx)
//    pst("2142444E", "pst", ""),//Outlook (pst)
    xls("D0CF11E0", "xls", ""),
//    doc("D0CF11E0", "doc", ""),
//    mdb("5374616E64617264204A", "mdb", ""),
//    wpd("FF575043", "wpd", ""),
//    eps("252150532D41646F6265", "eps", ""),
//    ps("252150532D41646F6265", "ps", ""),
    pdf("255044462D312E", "pdf", ""),
//    qdf("AC9EBD8F", "qdf", ""),
//    pwl("E3828596", "pwl", ""),
//    wav("57415645", "wav", ""),
//    avi("41564920", "avi", ""),
//    ram("2E7261FD", "ram", ""),
//    rm("2E524D46", "rm", ""),
//    mpg("000001BA", "mpg", ""),
//    mov("6D6F6F76", "mov", ""),
//    asf("3026B2758E66CF11", "asf", ""),
//    mid("4D546864", "mid", "");
    ;
    private String hex;

    private String subfix;

    private String mimeType;

    MimeType(String hex, String subfix, String mimeType) {
        this.hex = hex;
        this.subfix = subfix;
        this.mimeType = mimeType;
    }

    public String getHex() {
        return hex;
    }

    public String getSubfix() {
        return subfix;
    }

    public String getMimeType() {
        return mimeType;
    }
}
