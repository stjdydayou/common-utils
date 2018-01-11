package com.a7space.commons.fastdfs;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FileManager {

    private static Logger logger = LoggerFactory.getLogger(FileManager.class);

    private static TrackerClient trackerClient;

    private static TrackerServer trackerServer;

    private static StorageServer storageServer;

//    private static StorageClient storageClient;

    static { // Initialize Fast DFS Client configurations

        try {
            ClientGlobal.initByProperties(UploadConfig.FDFS_CONF_FILE_PATH);

            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();

//            storageClient = new StorageClient(trackerServer, storageServer);

        } catch (Exception e) {
            logger.error("fastdfs fail", e);

        }
    }

    public static FDFSUploadResult upload(FastDFSFile file) {
        logger.debug("File Name: " + file.getName() + "     File Length: " + file.getContent().length);

        NameValuePair[] meta_list = new NameValuePair[3];
        meta_list[0] = new NameValuePair("width", file.getWidth());
        meta_list[1] = new NameValuePair("heigth", file.getHeight());
        meta_list[2] = new NameValuePair("author", "Wedding");

        long startTime = System.currentTimeMillis();
        String[] uploadResults = null;
        try {
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);

            if (uploadResults == null) {
                logger.error("upload file fail, error code: " + storageClient.getErrorCode());
            }

        } catch (IOException e) {
            logger.error("IO Exception when uploadind the file: " + file.getName(), e);
        } catch (Exception e) {
            logger.error("Non IO Exception when uploadind the file: " + file.getName(), e);
        }
        logger.info("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");


        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];

        // String fileAbsolutePath = PROTOCOL +
        // trackerServer.getInetSocketAddress().getHostName() + SEPARATOR +
        // groupName + SEPARATOR + remoteFileName;

        logger.debug("upload file successfully!!!  " + "group_name: " + groupName + ", remoteFileName:" + " " + remoteFileName);
        FDFSUploadResult fdfsResult = new FDFSUploadResult(groupName, remoteFileName);

        return fdfsResult;

    }

    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    public static void deleteFile(String groupName, String remoteFileName) throws Exception {
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        storageClient.delete_file(groupName, remoteFileName);
    }

    public static StorageServer[] getStoreStorages(String groupName) throws IOException {
        return trackerClient.getStoreStorages(trackerServer, groupName);
    }

    public static ServerInfo[] getFetchStorages(String groupName, String remoteFileName) throws IOException {
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

}
