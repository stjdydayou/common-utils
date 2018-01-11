package com.a7space.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;


/**
 * @author huan.li
 * @since 2014年5月4日
 */
public class CfgFileReader {

    final static Logger logger = LoggerFactory.getLogger(CfgFileReader.class);


    public CfgFileReader() {
    };
    
    /**
     * 获取ClassPath目录下的资源文件
     * @param path 目录（format-file）
     * @param filter 文件后缀（.xml,.txt），可为空
     * @return path/filename 后续使用ClassLoader.getResourceAsSteam读取
     */
    public static List<String> getResourceFile(String path , final String extensions){
    	ClassLoader classLoader = CfgFileReader.class.getClassLoader();
        List<String> fileNameLists = new ArrayList<String>();

        try{
	        Enumeration<URL> urls = classLoader.getResources(path);
	        while (urls.hasMoreElements()) {
	            URL temp = urls.nextElement();
	            String protocol = temp.getProtocol();
	            if ("jar".equals(protocol)) {
	                JarFile jarFile = null;
	                JarURLConnection connection = (JarURLConnection) temp.openConnection();
	                jarFile = connection.getJarFile();
	
	                for (Enumeration<JarEntry> en = jarFile.entries(); en.hasMoreElements();) {
		                JarEntry entry = en.nextElement();
		                final String entryName = entry.getName();
		                logger.debug("Current entry name is {}", entryName);
		                if (entryName.startsWith(path)) {
		                	if((extensions == null || extensions.trim().equals("")) || entryName.toUpperCase().endsWith(extensions.toUpperCase())){
		                		fileNameLists.add(entryName);
		                	}
		                }
	                }
	            } else if ("file".equals(protocol)) {
	                File file = new File(temp.getFile());
	                logger.debug(file.getAbsolutePath());
	                String[] files = file.list(new FilenameFilter() {
						public boolean accept(File dir, String name) {
							if((extensions == null || extensions.trim().equals(""))
								|| name.toUpperCase().endsWith(extensions.toUpperCase()))
								return true;
							return false;
						}
					});
	                for(String f:files){
	                	fileNameLists.add(new StringBuilder(path).append(File.separatorChar).append(f).toString());
	                }
	            } else {
	            	throw new RuntimeException("Unknow Protocol [" + protocol + "]");
	            }
	        }
	        return fileNameLists;
        }catch(Exception e){
        	throw new RuntimeException("获取资源文件出错",e);
        }
    }


    /**
     * 读取配置文件
     * 
     * @param className
     *            类名
     * @param path
     *            文件路径(文件必须存在)
     * @return
     * @throws Exception
     */
    public static <M> File getCommCfg(Class<M> className, final String path) throws IOException,
            URISyntaxException {

        URL url = className.getClassLoader().getResource(path);
        if (url == null) {
            throw new IOException("文件" + path + " -- 未找到");
        }
        logger.debug(url.toString());
        URLConnection conn = url.openConnection();
        File file = new File(((URLConnection) conn).getURL().toURI());
        return file;
    }


    /**
     * 相对路径获取方法
     * 
     * @param className
     *            类名(作为坐标参考点)
     * @param partOfpath
     *            (存在的路径名,一般是文件夹路径,或者可以为空)
     * @return String(相对路径)
     * @throws Exception
     */
    public static <M> String getCommCfgPath(Class<M> className, String partOfpath) throws Exception {
        URL url = null;
        try {
            logger.debug(partOfpath);
            url = className.getClassLoader().getResource(partOfpath);
            logger.debug(url.toString());
            URLConnection conn = url.openConnection();
            return ((URLConnection) conn).getURL().toURI().toString().replace("file:/", "");
        } catch (Exception e) {
            logger.error("配置文件路径获取错误--{}, Path: {}", className, partOfpath);
            throw e;
        }
    }


    /**
     * 加载指定类加载路径下的properties文件
     * 
     * @param className
     *            类名
     * @param propsPath
     *            文件路径
     * @return properties
     * @throws IOException
     * @throws URISyntaxException
     */
    public static <M> Properties loadProps(Class<M> className, final String propsPath) throws IOException,
            URISyntaxException {
        Properties props = new Properties();

        File propsFile = CfgFileReader.getCommCfg(className, propsPath);
        FileInputStream fis = new FileInputStream(propsFile);
        props.load(fis);
        fis.close();

        return props;
    }


    /**
     * 解析指定类路径下的XML文件成对象
     * 
     * @param <M>
     * @param className
     * @param path
     * @return M
     * @throws URISyntaxException
     * @throws IOException
     * @throws JAXBException
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <M> M parseXML(Class<M> className, final String xmlPath) throws IOException,
            URISyntaxException, JAXBException {
        // File xmlFile = CfgFileReader.getCommCfg(className, xmlPath);

        JAXBContext context = JAXBContext.newInstance(className);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        // M m = (M) unmarshaller.unmarshal(xmlFile);

        M m = (M) unmarshaller.unmarshal(CfgFileReader.class.getClassLoader().getResourceAsStream(xmlPath));

        return m;
    }


    @SuppressWarnings("unchecked")
    public static <M> M parseXML(Class<M> className, final File xmlFile) throws IOException, URISyntaxException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(className);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        M m = (M) unmarshaller.unmarshal(xmlFile);
        return m;
    }

    @SuppressWarnings("unchecked")
    public static <M> M parseXMLString(Class<M> className,String xmlString) throws IOException, URISyntaxException, JAXBException{
        StringReader reader = new StringReader(xmlString);
        JAXBContext context = JAXBContext.newInstance(className);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        M m = (M) unmarshaller.unmarshal(reader);
        return m;
    }
    

    /**
     * 验证并解析XML
     * 
     * @param className
     *            类路径
     * @param xmlPath
     *            XML文件路径
     * @param xsdPath
     *            XSD文件路径
     * @return Object
     * @throws IOException
     * @throws URISyntaxException
     * @throws JAXBException
     * @throws SAXException
     */
    @SuppressWarnings("unchecked")
    public static <M> M validateAndParseXML(Class<M> className, final String xmlPath, final String xsdPath)
            throws IOException, URISyntaxException, JAXBException, SAXException {
        File xmlFile = CfgFileReader.getCommCfg(className, xmlPath);
        File xsdFile = CfgFileReader.getCommCfg(className, xsdPath);

        JAXBContext context = JAXBContext.newInstance(className);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
            xsdFile));
        M m = (M) unmarshaller.unmarshal(xmlFile);

        return m;
    }

}