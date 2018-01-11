package com.a7space.commons.utils;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PropertyUtils {
    private Logger logger=LoggerFactory.getLogger(PropertyUtils.class);
    
    private Properties properties;

    
    /**
     *  Created on 2016年7月7日 
     * <p>Discription:[方法功能中文描述]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param path /META-INF/config/configurations.properties
     */
    public void load(String path){
        try{
            if(this.properties==null){
                this.properties = new Properties();
            }
            InputStream is=PropertyUtils.class.getClass().getResourceAsStream(path);
            if(null !=is){
                properties.load(is);
            }
        }catch(Exception e){
            logger.error("load error:{}",e);
        }
    }
    
    public String getKey(String key){
        return properties.getProperty(key,"");
    }
    
    public static String loadPropertyVaule(String path,String key) {
        return loadPropertyVaule(path,key,"");
    }
    
    public static String loadPropertyVaule(String path,String key,String defaultvalue) {
        String result=defaultvalue;
        try{
            if(StringUtils.isBlank(path)){
                path="/META-INF/config/configurations.properties";
            }
            Properties properties = new Properties();            
            //String propertiesName="configurations.properties";
            InputStream is=PropertyUtils.class.getClass().getResourceAsStream(path);
            if(null==is){
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            }
            if(null==is){
                is = ClassLoader.getSystemResourceAsStream(path);
            }            
            if(null !=is){
                properties.load(is);
                result=properties.getProperty(key,defaultvalue);
                is.close();
            }
            return result;
        }catch(Exception e){
            return defaultvalue;
        }
    }
}
