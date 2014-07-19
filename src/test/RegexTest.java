package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
	public static void main(String []args){
		String tesStr = "特别 约。定 1.\"以人zheng民币结算,本保单不提供外汇的共同海损和/或救助担保.\" 2.\"船龄超过20年(含20年)的船舶,保险人按老龄船加费,超过25年船龄(含25年) 保险人不予承保.\" 3.本保单启运时间以投保单传真时间为准，即2013年01月05日  12时";
		String []strs = tesStr.split("\\p{Punct}");
		String testStr = "aabb";
//		String []strs = tesStr.split("\\p{Punct}");
//		String []strs = tesStr.split("\\pP|\\s", 3);
		for(String str : strs){
			System.out.println(str);
		}
		System.out.println(Character.isWhitespace('\t'));
//		Pattern pattern = Pattern.compile("\\pP|\\s");
//		Matcher matcher = pattern.matcher(",");
//		System.out.println(matcher.matches());

	}
}
