package com.file;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FileTrans {
	final static String soStr = "demo";
	final static String destStr = "model";
	public static void main(String args[]) throws IOException {

		String url1 = "E:/security";
//		String url2 = "\\\\11.205.32.36\\c$\\IndigoDocGenerator\\Server\\forms";
		String url2 = "E:/forms";
		if (!new File(url2).exists()) {
			(new File(url2)).mkdirs();
		}
		String subStr = "00001";
		String edit = "-00001-00001.mdf";
		File[] file = (new File(url1)).listFiles();
		for (int i = 0; i < file.length; i++) {
			String fileName = file[i].getName();
			String endStr = fileName.substring(fileName.lastIndexOf(".") + 1);
			String startStr = fileName;
			if(fileName.indexOf(".") > 0){
				startStr = fileName.substring(0, fileName.lastIndexOf("."));
			}
			if (file[i].isFile() && "mdf".equals(endStr)) {
				String returnPath = createDir(url2 + File.separator + startStr, subStr);
				//System.out.println("file{I} : " + file[i].getName());
				boolean result = renameFile(file[i], returnPath, edit, 1);
				if(result){
					System.out.println(file[i].getName() + "传输成功！");
				}else{
					System.out.println(file[i].getName() + "传输失败！");
				}
			}
		}
	}
	
	public static String createDir(String mainDirStr, String subDirStr){
		mainDirStr = mainDirStr.replace(soStr, destStr);
		File dir = new File(mainDirStr + File.separator + subDirStr);
		if(!dir.exists()){
			dir.mkdirs();
		}
		return dir.getAbsolutePath();
	}
	
	public static String getFormatTime(){
		return String.valueOf(System.currentTimeMillis());
	}
	
	public static boolean renameFile(File file, String destDir, String edit, int conflictType){
		String tmpName = file.getName();
		tmpName = tmpName.replaceAll(soStr, destStr);
		if(tmpName.lastIndexOf(".") > 0){
			tmpName = tmpName.substring(0, tmpName.lastIndexOf("."));
		}
		String destFileStr = destDir + File.separator + tmpName + edit;
		File destFile = new File(destFileStr);
		if(destFile.exists()){
			try {
				if(conflictType == 1){
					System.out.println("准备删除冲突文件： " + destFile.getName());
					boolean delRs = destFile.delete();
					if(delRs){
						System.out.println(destFile.getName() + "删除成功！");
					}else{
						System.out.println(destFile.getName() + "删除失败！");
					}
				}else{
					boolean rs = destFile.renameTo(new File(destFile.getAbsolutePath() + getFormatTime() + ".bak"));
					if (rs) {
						System.out.println(destFile.getName() + "备份成功！");
					} else {
						System.out.println(destFile.getName() + "备份失败！");
					}
				}
				
			} catch (Exception e) {
				System.out.println(file.getName() + "\r文件冲突处理失败！");
			}
		}
		return file.renameTo(destFile);
	}
}
