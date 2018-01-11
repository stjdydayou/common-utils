package com.a7space.commons.fastdfs;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UploadConfig {
    private static final Logger logger = LoggerFactory.getLogger(UploadConfig.class);
    private final static String CONFIG_PATH = "upload.properties";
    /**
     * 号百支付统一收银台商户号
     */
    public static String FDFS_CONF_FILE_PATH;
    public static long UPLOAD_MAX_FILE_SIZE;
    public static String[] UPLOAD_FILE_TYPES;
    public static String UPLOAD_FILE_HOST;
    public static String UPLOAD_USE_IMAGE_COMPRESS;
    public static int UPLOAD_IMAGE_COMPRESS_HEIGHT;
    public static int UPLOAD_IMAGE_COMPRESS_WIDTH;
    public static float UPLOAD_IMAGE_COMPRESS_QUALITY;
    private static AbstractConfiguration config;

    static {
        try {
            config = new PropertiesConfiguration(CONFIG_PATH);
        } catch (ConfigurationException e) {
            logger.error("classpath upload.properties load error", e);
        }
        UPLOAD_MAX_FILE_SIZE = config.getLong("upload.max.file.size");
        UPLOAD_FILE_TYPES = config.getStringArray("upload.file.types");
        UPLOAD_FILE_HOST = config.getString("upload.file.host");
        UPLOAD_USE_IMAGE_COMPRESS = config.getString("upload.use.image.compress");
        UPLOAD_IMAGE_COMPRESS_HEIGHT = config.getInt("upload.image.compress.height");
        UPLOAD_IMAGE_COMPRESS_WIDTH = config.getInt("upload.image.compress.width");
        UPLOAD_IMAGE_COMPRESS_QUALITY = config.getFloat("upload.image.compress.quality");
        FDFS_CONF_FILE_PATH = CONFIG_PATH;
    }

}
