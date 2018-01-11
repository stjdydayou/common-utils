package com.a7space.commons.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2015年11月09
 * <p>Title:       配置读取</p>
 * <p>Description: 配置读取</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class ConfigHelper {

	private static Logger logger=LoggerFactory.getLogger(PropertyUtils.class);
    private static final String CONFIG_PATH = "/META-INF/config/configurations.properties";

    Properties prop;

    private static ConfigHelper con = new ConfigHelper();

    private ConfigHelper() {
        String configPaths[] = new String[]{CONFIG_PATH};
        prop = new Properties();
        for (String configPath : configPaths) {
            String path = null;
            URL url = this.getClass().getClassLoader().getResource(configPath);
            if (url == null) {
                logger.error(configPath + "not found");
            } else {
                path = url.getPath();
            }
            try {
                if (path != null) {
                    prop.load(new InputStreamReader(new FileInputStream(path), "UTF-8"));
                }
            } catch (IOException e) {
                throw new RuntimeException(CONFIG_PATH + "not found");
            }
        }
    }

    public static String getValue(String key) {
        if (con == null) {
            con = new ConfigHelper();
        }
        return con.prop.getProperty(key);
    }

    public static Integer getInteger(String key) {
        return Integer.parseInt(getValue(key));
    }

    public static long getLong(String key) {
        return Long.parseLong(getValue(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(getValue(key));
    }
}
