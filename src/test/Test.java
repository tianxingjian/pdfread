package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test{

	public static void main(String[] args){
		String srcStr = "#,2014-04-16 00:00:00@@2014-04-16 23:59:59";
		
		String srcStr1 = "2014-04-06 00:00:00@@2014-04-06 23:59:59,2014-04-16 00:00:00@@2014-04-16 23:59:59";
		
		String srcStr2 = "2014-04-16 00:00:00@@2014-04-16 23:59:59,#";
		
		String src = "pk_org@org_orgs@pk_org@name";
		
//		testStart(srcStr2);
//		testEnd(srcStr2);
//		System.out.println("close");
		testRef(src);
		splitRefKey(src);
	}
	
	
	private static void testAll(String str){
		Pattern pattern = Pattern.compile("(.+@@.+)");
		Matcher matcher = pattern.matcher(str);
        
        while (matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }
	}
	
	private static void testEnd(String str){
		Pattern pattern = Pattern.compile("^\\d.+@@.+,.+@@(.+\\d)$");
		Matcher matcher = pattern.matcher(str);
        
        while (matcher.find()) {
            System.out.println("结束日期：" + matcher.group(1));
        }
	}
	
	private static void testStart(String str){
		Pattern pattern = Pattern.compile("^(\\d.+)@@.+,.+@@.+\\d$");
		Matcher matcher = pattern.matcher(str);
        
        while (matcher.find()) {
            System.out.println("初始日期：" + matcher.group(1));
        }
	}
	
	private static void testRef(String str){
		
		Pattern pattern = Pattern.compile("^(.+)@(.+)@(.+)@(.+)?");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
            System.out.println("初始日期1：" + matcher.group(1));
            System.out.println("初始日期2：" + matcher.group(2));
            System.out.println("初始日期3：" + matcher.group(3));
            System.out.println("初始日期4：" + matcher.group(4));
        }
	}
	
	public static void splitRefKey(String srcStr) {
		String[] result = new String[2];
		Pattern pattern = Pattern.compile("^(.+)@(.+@.+@.+)?");
		Matcher matcher = pattern.matcher(srcStr);
		if(matcher.find()){
			   System.out.println("初始日期1：" + matcher.group(1));
	            System.out.println("初始日期2：" + matcher.group(2));
		}
	}
}
