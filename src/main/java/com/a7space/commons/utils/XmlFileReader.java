package com.a7space.commons.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

/**
 * XML类型配置文件读取器
 * 
 * @author bingwangzi
 * @date 2013-02-25
 */
public class XmlFileReader {

	/**
	 * 解析XML类型文件
	 * 
	 * @param xmlPath
	 * 			XML文件路径
	 * @return
	 * 		Object
	 * @throws JAXBException 
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	public static <M> M parseXML(Class<M> className, String xmlPath) throws IOException, URISyntaxException, JAXBException {
		return (M) CfgFileReader.parseXML(className, xmlPath);
	}
	
	public static <M> M parseXML(Class<M> className, File xmlFile) throws IOException, URISyntaxException, JAXBException {
		return (M) CfgFileReader.parseXML(className, xmlFile);
	}
	
	public static <M> M parseXMLString(Class<M> className,String xmlString) throws IOException, URISyntaxException, JAXBException {
        return (M) CfgFileReader.parseXMLString(className, xmlString);
    }
	
	/**
	 * 验证并解析XML类型文件
	 * 
	 * @param xmlPath
	 * 			XML文件路径
	 * @param xsdPath
	 * 			XSD文件路径
	 * @return
	 * 		Object
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public static <M> M validateAndParseXML(Class<M> className, String xmlPath, String xsdPath) throws IOException, URISyntaxException, JAXBException, SAXException {
		return (M) CfgFileReader.validateAndParseXML(className, xmlPath, xsdPath);
	}
	
}