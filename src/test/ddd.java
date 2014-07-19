package test;

import java.util.Properties;

public class ddd {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		   Properties props=System.getProperties(); //获得系统属性集  
		   String osName = props.getProperty("os.name"); //操作系统名称  
		   String osArch = props.getProperty("os.arch"); //操作系统构架  
		   String osVersion = props.getProperty("os.version"); //操作系统版本  
		   System.out.println(osName);
		   System.out.println(osArch);
		   System.out.println(osVersion);
	}
	
	public static  void map(){
		   String line = "ad bb cc";
			
			String[] strData=line.split(" ");
			for(int i = 0; i < 3; i++){
				Com(strData,1,2);
				System.out.println(strData.length);
			}
		
		}
		
		//从n个数据中挑选m个进行排列组合
		public static void Com(String[]str, int n, int m){
			
			System.out.println(str[1] + ":" + n + m);
		}

}
