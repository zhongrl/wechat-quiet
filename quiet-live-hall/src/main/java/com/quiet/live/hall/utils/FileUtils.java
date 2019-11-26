package com.quiet.live.hall.utils;

import static sun.font.CreatedFontTracker.MAX_FILE_SIZE;

import java.io.File;
import java.math.BigDecimal;

import com.quiet.live.hall.utils.string.ValidateUtils;

/**
 * 文件工具类
 * @author Peter
 *
 */
public class FileUtils {
	/**
	 * 传入文件夹路径，该方法能够实现创建整个路径
	 * @param path 文件夹路径，不包含文件名称及后缀名
	 */
	public static void isDir(String path){
		String[] paths = path.split("/");
			String filePath = "";
			for(int i = 0 ; i < paths.length ; i++){
				if(i == 0){
					filePath = paths[0];
				}else{
					filePath += "/" + paths[i];
				}
				creatDir(filePath);
			}
	}
	
	/**
	 * 该方法用来判断文件夹是否存在，如果不存在则创建，存在则什么都不做
	 * @param filePath
	 */
	public static void creatDir(String filePath){
		File file = new File(filePath);
		if(!file.exists()){
				file.mkdir();
		}
	}
	/**
	 * 文件格式校验
	 */
	public static boolean validateUploadFile(File file, String fileFileName) {
		String lastName = fileFileName.substring(fileFileName.lastIndexOf(".") + 1);

		if (ValidateUtils.isEmpty(file)) {
			// 没有上传文件
			return false;
		}
		if (file.length() > MAX_FILE_SIZE) {
			// 上传的文件文件不能大于10M
			return false;
		}
		if (!"jpg".equals(lastName) && !"gif".equals(lastName) && !"jpeg".equals(lastName) && !"png".equals(lastName)) {
			// 上单的文件文件必须是jpg、gif、jpeg、png为后缀名的
			return false;
		}
		return true;
	}
	
	public static int getInt(double number){
	    BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
	    return Integer.parseInt(bd.toString()); 
	}
}
