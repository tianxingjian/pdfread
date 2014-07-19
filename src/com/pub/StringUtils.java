package com.pub;

public class StringUtils {
	public static boolean isNotBlank(String str){
		if(null != str && !"".equals(str.trim())){
			return true;
		}
		return false;
	}
}
