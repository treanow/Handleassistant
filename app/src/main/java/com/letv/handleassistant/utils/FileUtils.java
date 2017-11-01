package com.letv.handleassistant.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作的工具类
 * @author wuyu
 * 2015-8-14 上午11:38:47
 */
public class FileUtils {

	public static String SDPATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/wiseparking/picture/";

	
	/**
	 * 保存图片
	 * @param bm
	 * @param picName	图片名字
	 */
	public static void saveBitmap(Bitmap bm, String picName) {
		Log.e("", "保存图片");
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".JPEG");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			Log.e("", "已经保存");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建目录
	 * @param dirName
	 * @return
	 * @throws IOException
	 */
	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	/**
	 * 文件是否存在
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	/**
	 * 删除文件
	 * @param fileName
	 */
	public static void delFile(String fileName){
		File file = new File(SDPATH + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	/**
	 * 根据路径来删除整个目录
	 * @param path
	 */
	public static void deleteDir(String path) {
		File dir = new File(path);
		File to = new File(dir.getAbsolutePath() + System.currentTimeMillis());
		dir.renameTo(to);
		if (to == null || !to.exists() || !to.isDirectory())
			return;
		
		for (File file : to.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDir(file.getAbsolutePath()); // 递规的方式删除文件夹
		}
		to.delete();// 删除目录本身
	}

	/**
	 * 根据路径来判断该文件是否存在
	 * @param path
	 * @return
	 */
	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

	/**
	 * 获得文件类型
	 * @param name
	 * @return
	 */
	public static String fileType(String name) {
		String type = "";
		String end = name.substring(name.lastIndexOf(".") + 1, name.length())
				.toLowerCase();
		if (end.equals("jpg")) {
			type = "image";
		} else if (end.equals("3gp")) {
			type = "video";
		} else {
			type = "*";
		}
		type += "/*";
		return type;
	}
	
}
