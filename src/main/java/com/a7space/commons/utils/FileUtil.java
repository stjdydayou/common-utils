package com.a7space.commons.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public final static String CHAR_SET_UTF8 = "UTF-8";
    public final static String CHAR_SET_GBK = "GBK";
    public final static int NEW_LINE_CHAR_LENGTH = System.getProperty("line.separator").getBytes().length;


    /**
     * 删除文件，可以是单个文件或文件夹
     * 
     * @param fileName
     *            待删除的文件名
     * @return 文件删除成功返回true,否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            logger.info("删除文件失败：" + fileName + "文件不存在");
            return false;
        } else {
            if (file.isFile()) {

                return deleteFile(fileName);
            } else {
                return deleteDirectory(fileName);
            }
        }
    }


    /**
     * 删除单个文件
     * 
     * @param fileName
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true,否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            logger.info("删除单个文件" + fileName + "成功！");
            return true;
        } else {
            logger.info("删除单个文件" + fileName + "失败！");
            return false;
        }
    }


    /**
     * 删除目录（文件夹）以及目录下的文件
     * 
     * @param dir
     *            被删除目录的文件路径
     * @return 目录删除成功返回true,否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        logger.debug("文件目录dirFile："+dirFile );
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            logger.info("删除目录失败" + dir + "目录不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        if(files!=null){
            for (int i = 0; i < files.length; i++) {
                // 删除子文件
                if (files[i].isFile()) {
                    flag = deleteFile(files[i].getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                }
                // 删除子目录
                else {
                    flag = deleteDirectory(files[i].getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                }
            } 
        }

        if (!flag) {
            logger.info("删除目录失败");
            return false;
        }

        // 删除当前目录
        if (dirFile.delete()) {
            logger.info("删除目录" + dir + "成功！");
            return true;
        } else {
            logger.info("删除目录" + dir + "失败！");
            return false;
        }
    }


    public static boolean deleteDirFiles(String dir) {
        logger.debug("正在调用方法：deleteDirFiles ," + "目录：" + dir); 
        boolean flag = true;
        File dirFile = new File(dir);
        // 删除文件夹下的所有文件(包括子目录)
        logger.debug("查看文件目录：" + dirFile); 
        File[] files = dirFile.listFiles();
        if(files!=null){
            logger.debug("查看所有子文件：" + files + "子文件个数：" + files.length); 
            for (int i = 0; i < files.length; i++) {
                // 删除子文件
                if (files[i].isFile()) {
                    flag = deleteFile(files[i].getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                }
                // 删除子目录
                else {
                    flag = deleteDirectory(files[i].getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                }
            }
        }
        return flag;
    }
    
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    /**
     * 
     * @param fileName
     *            文件路径+文件名
     * @param content
     *            文件内容
     * @throws Exception
     */
    public static void write(String fileName, String charSet, String content) {
        write(fileName, content, charSet, false);
    }


    /**
     * 追写文件
     * 
     * @param fileName
     * @param content
     * @param append
     * @throws Exception
     */
    public static void write(String fileName, String content, String charSet, boolean append) {
        try {
            File file = new File(fileName);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                boolean exist = fileParent.mkdirs();
                if (!exist) {
                    throw new RuntimeException("生成文件路径" + fileParent.getAbsolutePath() + "失败!");
                }
            }
            BufferedWriter bw =
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), charSet));
            try {
                bw.write(content);
            } finally {
                bw.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("写入文件失败", e);
        }

    }


    /**
     * 获取文件的总行数,文件需要每一行均含有固定长度
     * 
     * @param fileName
     * @param lineByteSize
     *            单行的固定字节数 ，小于1表示无固定长度
     * @return
     * @throws Exception
     */
    public static long getFileRowCount(String fileName, String charSet, long lineByteSize,
            int newLineCharLength) {
        try {
            long rowCount = 0;
            if (lineByteSize > 0) {
                long len = new File(fileName).length();
                rowCount = (len + newLineCharLength) / (lineByteSize + newLineCharLength);// 加上换行符字节数
            } else {
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charSet));
                try {
                    while (br.readLine() != null) {
                        rowCount++;
                    }
                } finally {
                    br.close();
                }
            }
            return rowCount;

        } catch (Exception e) {
            throw new RuntimeException("读取文件失败", e);
        }

    }


    /**
     * 读取固定行长度的文件，每行记录存放到List中返回
     * 
     * @param fileName
     *            文件名
     * @param startLineIndex
     *            起始行,从0开始
     * @param lineByteSize
     *            每行固定的字节数 ,小于等于0表示无固定长度
     * @param maxLine
     *            读取的最大行数,List.size的最大值
     * @return
     * @throws Exception
     */
    public static List<String> readFileToList(String fileName, String charSet, long startLineIndex,
            long lineByteSize, long maxLine, int newLineCharLength) {
        try {
            List<String> list = new ArrayList<String>();
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charSet));
            String buf = null;
            long lineNum = 0;
            int lineIndex = 0;
            try {
                if (lineByteSize > 0) {
                    br.skip(startLineIndex * (lineByteSize + newLineCharLength));
                } else {
                    while (lineIndex < startLineIndex) {
                        br.readLine();
                        lineIndex++;
                    }
                }
                while (++lineNum <= maxLine && (buf = br.readLine()) != null) {
                    list.add(buf);
                }
            } finally {
                br.close();
            }
            return list;

        } catch (Exception e) {
            throw new RuntimeException("读取文件失败", e);
        }

    }



    /**
     * 解压ZIP，返回解压后的所有文件名（绝对路径）列表
     */
    public static List<String> unZip(String path) {
        int count = -1;
        int index = -1;
        int buffer = 256;

        File src = new File(path);
        String savepath = src.getAbsolutePath();
        savepath = savepath.substring(0, savepath.lastIndexOf(".")) + File.separatorChar;
        new File(savepath).mkdir();

        List<String> unZipFiles = new ArrayList<String>();
        ZipInputStream zis = null;
        try {
            zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(path)));

            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null) {
                byte data[] = new byte[buffer];

                String temp = entry.getName();

                index = temp.lastIndexOf("/");
                if (index != -1)
                    temp = temp.substring(index + 1);
                temp = savepath + temp;

                File f = new File(temp);
                f.createNewFile();

                BufferedOutputStream bos = null;
                try {
                    bos = new BufferedOutputStream(new FileOutputStream(f), buffer);
                    while ((count = zis.read(data, 0, buffer)) != -1) {
                        bos.write(data, 0, count);
                    }
                    bos.flush();
                } finally {
                    if (bos != null)
                        bos.close();
                }
                unZipFiles.add(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException("解压文件失败", e);
        } finally {
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }

        return unZipFiles;

    }


    /**
     * 读取固定行长度的文件，每行记录存放到List中返回
     * 
     * @param fileName
     *            文件名
     * @param startLineIndex
     *            起始行,从0开始
     * @param lineByteSize
     *            每行固定的字节数 ,小于等于0表示无固定长度
     * @param maxLine
     *            读取的最大行数,List.size的最大值
     * @return
     * @throws Exception
     */
    public static List<String> readFileToList(String fileName, String charSet) {
        try {
            List<String> list = new ArrayList<String>();
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charSet));
            String buf = null;
            try {
                while ((buf = br.readLine()) != null) {
                    list.add(buf);
                }
            } finally {
                br.close();
            }
            return list;

        } catch (Exception e) {
            throw new RuntimeException("读取文件失败", e);
        }

    }

    /**
     *  Created on 2017年2月8日 
     * <p>Discription:[将字节流写入到文件]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param fileFullName
     * @param bytes
     */
    public static void writeBytesToFile(byte[] bytes,String filePath,String fileName){
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath+File.separatorChar+fileName);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bytes);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
    }
    
    /**
     *  Created on 2017年2月8日 
     * <p>Discription:[从文件中获取字节流]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param filePath
     * @return
     */
    public static byte[] readBytesFromFile(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath); 
            if(file.exists()==false){
                return null;
            }
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }
}

