package com.dotcms.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.DigestInputStream;
import java.security.MessageDigest;

/**
 * This class manage download files and checksum comparison
 * @author Oswaldo Gallango
 *
 */
public class FileUtil {

	/**
	 * Get file from url
	 * @param fileURL  File URL
	 * @param fileName file name for downloaded copy
	 * @return File
	 * @throws Exception
	 */
	public static File getFileFromURL(URL fileURL, String fileName) throws Exception{
		URLConnection connection = fileURL.openConnection();
		InputStream in = connection.getInputStream();
		File newFile = new File(System.getProperty("user.dir")+"/artifacts/testdata/downloads/"+fileName);
		FileOutputStream fos = new FileOutputStream(newFile);
		byte[] buf = new byte[512];
		while (true) {
			int len = in.read(buf);
			if (len == -1) {
				break;
			}
			fos.write(buf, 0, len);
		}
		in.close();
		fos.flush();
		fos.close();

		return newFile;
	}

	/**
	 * Compare if two file are the same by MD5Hash checksum
	 * @param file1 File 
	 * @param file2 File 
	 * @return true if the md5 checksum is the same, false if not
	 * @throws Exception
	 */
	public static boolean compareFilesChecksum(File file1, File file2) throws Exception{
		MessageDigest md_1 = MessageDigest.getInstance("MD5");
		MessageDigest md_2 = MessageDigest.getInstance("MD5");
		InputStream is_1 = new FileInputStream(file1);
		InputStream is_2 = new FileInputStream(file2);
		try {
			is_1 = new DigestInputStream(is_1, md_1);
			is_2 = new DigestInputStream(is_2, md_2);
		}
		finally {
			is_1.close();
			is_2.close();
		}
		byte[] digest_1 = md_1.digest();
		//convert byte to hex format
		String hashFile1 = "";
		for (int i = 0; i < digest_1.length; i++) {
			hashFile1+= Integer.toString((digest_1[i] & 0xff) + 0x100, 16).substring(1);
		}

		byte[] digest_2 = md_2.digest();
		//convert byte to hex format
		String hashFile2 = "";
		for (int i = 0; i < digest_2.length; i++) {
			hashFile2+= Integer.toString((digest_2[i] & 0xff) + 0x100, 16).substring(1);
		}
		return hashFile1.equals(hashFile2);
	}
	
	/**
	 * Delete a file or files under the specified path and name or extension
	 * @param filePath
	 * @param nameOrExtension
	 * @throws Exception
	 */
	public static void deleteFiles(String filePath, String nameOrExtension) throws Exception{
		String path = System.getProperty("user.dir");
		File folder = new File(path+filePath);
		final String fileName = nameOrExtension;
		File[] files =folder.listFiles(new FilenameFilter() {			
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(fileName);
			}
		});
		for(File file : files){
			if(file.isFile()){
				file.delete();
			}
		}
	}
}
