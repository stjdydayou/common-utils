package com.a7space.commons.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Url2PngUtils {

    private static Logger logger=LoggerFactory.getLogger(Url2PngUtils.class);
    /**
     *  Created on 2016年5月30日 
     * <p>Discription:[获取服务器路径]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return
     */
    public static String getAppPath(){
        String result="";
        try{
            String phantomjsPath=PropertyUtils.loadPropertyVaule("configurations.properties", "phantomjsPath");
            if(StringUtils.isNotBlank(phantomjsPath)){
                return phantomjsPath;
            }
            URL url=Url2PngUtils.class.getClassLoader().getResource("META-INF/runtime/phantomjs/");
            result=url.getPath();
            //result=result.substring(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    

    /**
     *  Created on 2016年5月30日 
     * <p>Discription:[方法功能中文描述]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param url  http://www.114mall.com
     * @param filePath 保存导出图片的文件完成路径xxxx.png
     * @return
     */
    public static boolean exportPng(String url,String filePath){
        boolean result=false;
        try{
            String osName=System.getProperty("os.name").toLowerCase();
            String dir=getAppPath();
            StringBuffer cmdBuffer=new StringBuffer();
            
            if(osName.contains("linux")){
                cmdBuffer.append(dir);
                cmdBuffer.append("phantomjs ");
            }
            if(osName.contains("windows")){
                dir=dir.substring(1);
                cmdBuffer.append(dir);
                cmdBuffer.append("phantomjs.exe ");
            }
            cmdBuffer.append(dir);
            cmdBuffer.append("url2png.js ");
            cmdBuffer.append(url);
            cmdBuffer.append(" ");
            cmdBuffer.append(filePath);
            //cmdBuffer.deleteCharAt(0);
            logger.info("runtime exe:{}",cmdBuffer.toString());
            Process pro = Runtime.getRuntime().exec(cmdBuffer.toString());
            pro.waitFor();
            
            //读取
            InputStream in = pro.getInputStream();  
            BufferedReader read = new BufferedReader(new InputStreamReader(in));  
            String line = null;  
            while((line = read.readLine())!=null){
                logger.info(line);
                if("export png file".equals(line)){
                    result=true;
                }
            }  
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
