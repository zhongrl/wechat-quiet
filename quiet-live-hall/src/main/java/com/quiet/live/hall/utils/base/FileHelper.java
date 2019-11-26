package com.quiet.live.hall.utils.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHelper {
	public static void genDir(String dirPath) {
		File f = new File(dirPath);
		f.mkdirs();
	}

	public static String readFile(String path, boolean needUTF8) {
		String content = "";
		DataInputStream din = null;
		try {
			din = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(path))));

			byte[] bs = new byte[din.available()];

			din.read(bs);
			if (needUTF8)
				content = new String(bs, "utf-8");
			else
				content = new String(bs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				din.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	public static void writeFile(String pathName, String content, boolean needUTF8) {
		File f = new File(pathName);
		if (f.exists())
			f.delete();
		else if (!(f.getParentFile().exists())) {
			f.getParentFile().mkdirs();
		}

		DataOutputStream dout = null;
		try {
			f.createNewFile();

			dout = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));

			if (needUTF8)
				dout.write(content.getBytes("utf-8"));
			else
				dout.write(content.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}