package cn.com.text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReadFile {
	
	public static Set<String> readFile(String filename)throws Exception {

		Set<String> result = new HashSet<String>();
		String encoding="GBK";
		InputStreamReader read = new InputStreamReader(new FileInputStream(filename),encoding);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
		try {
			String str;
			while((str = bufferedReader.readLine()) != null) {
			if(!str.endsWith(".jpg"))
//				System.out.println(str);
				result.add(str);
			}
		} catch (Exception e) {

		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (read != null) {
				read.close();
			}
		}

		return result;
	}
	
	public static Map<String, Integer> listFile(String filename, Map<String, Integer> map)throws Exception {

		Set<String> result = new HashSet<String>();
		String encoding="GBK";
		InputStreamReader read = new InputStreamReader(new FileInputStream(filename),encoding);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
		try {
			String str;
			String key = null;
			while((str = bufferedReader.readLine()) != null) {
				if (str.endsWith(".jpg")) {
					key = str.substring(0, str.lastIndexOf("\\"));
					Integer value = map.get(key);
					if (value == null) {
						value = new Integer(0);
					} else {
						value = value + 1;
					}
					map.put(key, value);
				}
			}
		} catch (Exception e) {

		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (read != null) {
				read.close();
			}
		}

		return map;
	}
	
	private static Map<String, Integer> initMap(Set<String> sets){
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(String str : sets){
			map.put(str, new Integer(0));
		}
		return map;
	}
	
	private static void writeFile(Map<String, Integer>map, String file) throws IOException{
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		StringBuffer strs = new StringBuffer();
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, "UTF-8"); 
	       
			Set<String> set = map.keySet();
			for(String str : set){
				strs.append(str.substring(str.lastIndexOf("\\") + 1) + "\t" + map.get(str) + "\r\n");
			}
			System.out.println(strs.toString());
			osw.write(strs.toString());
			osw.flush();
			
		} catch (Exception e) {
			
		}finally{
			if(osw != null){
				osw.close();
			}
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		String str = "C:\\Users\\Administrator\\Desktop\\dxy\\1.txt";
		String str1 = "C:\\Users\\Administrator\\Desktop\\dxy\\result.txt";
		
		System.out.println(str.endsWith(".jpg"));
		
		Set<String> set = readFile(str);
		Map<String, Integer> map = initMap(set);
		
		Map<String, Integer> resMap = listFile(str, map);
		
		writeFile(resMap, str1);

	}
}
