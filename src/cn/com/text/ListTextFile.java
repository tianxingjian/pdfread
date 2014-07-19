package cn.com.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ListTextFile {
	public static String listDir(String dirStr)throws Exception {

		File dir = new File(dirStr);
		String[] as;
		File file[] = dir.listFiles();
		StringBuffer sb = new StringBuffer(); 
		for (int i = 0; i < file.length; i++) {
			if (file[i].isDirectory()) {
				continue;
			} else {
				String fileName = file[i].getName();
				FileReader fr = null;
				BufferedReader br = null;
				if (fileName.endsWith(".txt")) {
					try{
						fr = new FileReader(file[i]); 
						br = new BufferedReader(fr); 
						String str;
						if((str = br.readLine()) != null){
							String[] macStr = splitString(str);
							if(macStr != null && macStr.length >= 4){
								for(int j = 0; j < 4; j++){
									System.out.print(macStr[j] + "\t");
									sb.append(macStr[j] + "\t");
								}
								sb.append("\r\n");
								System.out.println();
							}
							continue;
						}
					}catch(Exception e){
						
					}finally{
						if(br != null){
							br.close();
						}
						if(fr != null){
							fr.close();
						}
					}
					
				}
			}
		}
		
		return sb.toString();
	}
	
	static String[] splitString(String resource){
		 Matcher m = Pattern.compile("\\£¨(.*?)\\£©").matcher(resource);
		 ArrayList<String> macStr = new ArrayList<String>();
		 while (m.find()) {
			 macStr.add(m.group(1));
		 }
	     return macStr.toArray(new String[0]); 
	}
	
	public static void writeFile(String source, File file){
		FileOutputStream fos = null;
		PrintWriter  pw = null;
		try {
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(source);
			pw.flush();
		} catch (Exception e) {
			
		}finally{
			if(pw != null){
				pw.close();
			}
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
		
	}
	
	public static void main(String args[])throws Exception{
		String result = listDir("d:/txtFile/a");
		writeFile(result, new File("d:/txtFile/½á¹û.txt"));
	}
}
