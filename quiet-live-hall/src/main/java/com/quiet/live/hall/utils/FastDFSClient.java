package com.quiet.live.hall.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FastDFS分布式文件系统操作客户端.
 * 
 *
 * 
 */
public class FastDFSClient {

	// private static final String CONF_FILENAME =
	// Thread.currentThread().getContextClassLoader().getResource("fdfs/fdfs_client.conf");

	private static StorageClient1 client = null;

	private static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);

	private static String httpPort = "";

	public static StorageClient1 getClient() {
		if (client == null) {
			try {
				httpPort = PropUtil.get("fastdfs.http.access.url");
				ClientGlobal.setG_anti_steal_token(Boolean.parseBoolean(PropUtil.get("fastdfs.http.anti_steal_token")));
				ClientGlobal.setG_charset(PropUtil.get("fastdfs.charset"));
				ClientGlobal.setG_connect_timeout(Integer.parseInt(PropUtil.get("fastdfs.connect_timeout")));
				ClientGlobal.setG_network_timeout(Integer.parseInt(PropUtil.get("fastdfs.network_timeout")));
				ClientGlobal.setG_secret_key(PropUtil.get("fastdfs.http.secret_key"));

				String trackers = PropUtil.get("fastdfs.tracker_server");
				if (trackers == null) {
					throw new MyException("item \"tracker_server\" in not found");
				}
				String[] szTrackerServers = trackers.split(",");
				if (szTrackerServers == null) {
					throw new MyException("item \"tracker_server\" in not found");
				}
				InetSocketAddress[] tracker_servers = new InetSocketAddress[szTrackerServers.length];
				for (int i = 0; i < szTrackerServers.length; ++i) {
					String[] parts = szTrackerServers[i].split("\\:", 2);
					if (parts.length != 2) {
						throw new MyException(
								"the value of item \"tracker_server\" is invalid, the correct format is host:port");
					}
					tracker_servers[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
				}
				ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));
				ClientGlobal.setG_tracker_http_port(Integer.parseInt(PropUtil.get("fastdfs.http.tracker_http_port")));

				// logger.info("=== CONF_FILENAME:" + CONF_FILENAME);
				// ClientGlobal.init(CONF_FILENAME.getFile());

				TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
				TrackerServer trackerServer = trackerClient.getConnection();
				if (trackerServer == null) {
					logger.error("getConnection return null");
				}
				StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
				if (storageServer == null) {
					logger.error("getStoreStorage return null");
				}
				client = new StorageClient1(trackerServer, storageServer);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		}
		return client;
	}

	/**
	 * 只加载一次.
	 */
	static {

	}

	public static String convertToUrl(String fileId) {

		return httpPort + fileId;
	}

	/**
	 *
	 * @param file
	 *            文件
	 * @param fileName
	 *            文件名
	 * @return 返回Null则为失败
	 */
	public static String uploadFile(File file, String fileName) {
		FileInputStream fis = null;
		try {
			NameValuePair[] meta_list = null; // new NameValuePair[0];
			fis = new FileInputStream(file);
			byte[] file_buff = null;
			if (fis != null) {
				int len = fis.available();
				file_buff = new byte[len];
				fis.read(file_buff);
			}

			String fileid = getClient().upload_file1(file_buff, getFileExt(fileName), meta_list);
			return fileid;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	/**
	 *
	 * @param fis
	 *            文件流
	 * @param fileName
	 *            文件名
	 * @return 返回Null则为失败
	 */
	public static String uploadFile(FileInputStream fis, String fileName) {
		try {
			NameValuePair[] meta_list = null; // new NameValuePair[0];
			byte[] file_buff = null;
			if (fis != null) {
				int len = fis.available();
				file_buff = new byte[len];
				fis.read(file_buff);
			}

			String fileid = getClient().upload_file1(file_buff, getFileExt(fileName), meta_list);
			return fileid;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	/**
	 * 直接通过byte[]进行文件读取
	 * 
	 * @param fileBytes
	 *            文件字节
	 * @param fileName
	 *            文件名
	 * @return 返回Null则为失败
	 */
	public static String uploadFile(byte[] fileBytes, String fileName) {
		try {
			NameValuePair[] meta_list = null; // new NameValuePair[0];
			String fileid = getClient().upload_file1(fileBytes, getFileExt(fileName), meta_list);
			return fileid;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 根据组名和远程文件名来删除一个文件
	 *
	 * @param groupName
	 *            例如 "group1" 如果不指定该值，默认为group1
	 * @param fileName
	 *            例如"M00/00/00/wKgxgk5HbLvfP86RAAAAChd9X1Y736.jpg"
	 * @return 0为成功，非0为失败，具体为错误代码
	 */
	public static int deleteFile(String groupName, String fileName) {
		try {
			int result = getClient().delete_file(groupName == null ? "group1" : groupName, fileName);
			return result;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return 0;
		}
	}

	/**
	 * 根据fileId来删除一个文件（我们现在用的就是这样的方式，上传文件时直接将fileId保存在了数据库中）
	 *
	 * @param fileId
	 *            file_id源码中的解释file_id the file id(including group name and
	 *            filename);例如
	 *            group1/M00/00/00/ooYBAFM6MpmAHM91AAAEgdpiRC0012.xml
	 * @return 0为成功，非0为失败，具体为错误代码
	 */
	public static int deleteFile(String fileId) {
		try {
			int result = getClient().delete_file1(fileId);
			return result;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return 0;
		}
	}

	/**
	 * 修改一个已经存在的文件
	 *
	 * @param oldFileId
	 *            原来旧文件的fileId, file_id源码中的解释file_id the file id(including group
	 *            name and filename);例如
	 *            group1/M00/00/00/ooYBAFM6MpmAHM91AAAEgdpiRC0012.xml
	 * @param file
	 *            新文件
	 * @param filePath
	 *            新文件路径
	 * @return 返回空则为失败
	 */
	public static String modifyFile(String oldFileId, File file, String filePath) {
		String fileid = null;
		try {
			// 先上传
			fileid = uploadFile(file, filePath);
			if (fileid == null) {
				return null;
			}
			// 再删除
			int delResult = deleteFile(oldFileId);
			if (delResult != 0) {
				return null;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;
		}
		return fileid;
	}

	/**
	 * 文件下载
	 *
	 * @param fileId
	 * @return 返回一个流
	 */
	public static InputStream downloadFile(String fileId) {
		try {
			byte[] bytes = getClient().download_file1(fileId);
			InputStream inputStream = new ByteArrayInputStream(bytes);
			return inputStream;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;
		}
	}

	/**
	 * 获取文件后缀名（不带点）.
	 *
	 * @return 如："jpg" or "".
	 */
	private static String getFileExt(String fileName) {
		if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
			return "";
		} else {
			return fileName.substring(fileName.lastIndexOf(".") + 1); // 不带最后的点
		}
	}
	
	public static ByteArrayInputStream parse(OutputStream out) throws Exception
    {
        ByteArrayOutputStream   baos=new   ByteArrayOutputStream();
        baos=(ByteArrayOutputStream) out;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }
	
	 public static boolean deleteLoaclHostFile(String fileName) {
	        File file = new File(fileName);
	        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
	        if (file.exists() && file.isFile()) {
	            if (file.delete()) {
	                return true;
	            } else {
	                return false;
	            }
	        } else {
	            return false;
	        }
	    }
}
