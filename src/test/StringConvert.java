package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串自动换行算法实现
 * @author tianxingjian
 *
 */
public class StringConvert {
	public static void main(String args[]) {
		String testStr = "特别约。定 1.\"以人zheng民币结算,本保单不提供外汇的共同海损和/或救助担保.\" 2.\"船龄超过20年(含20年)的船舶,保险人按老龄船加费,超过25年船龄(含25年) 保险人不予承保.\" 3.本保单启运时间以投保单传真时间为准，即2013年01月05日  12时";
		String testStr1 = "every body here, PICC人保财险信息技术部! 白菜KKK，神马都是浮云9999？";
		String testStr2 = "白菜KKK，神马都";
		System.out.println(formatString4Auto(testStr, 20));
		System.out.println();
		System.out.println(formatString4Auto(testStr1, 20));
		System.out.println();
		System.out.println(formatString4Auto(testStr2, 6));
	}
	
	/**
	 * 单行字符串自动换行，并且将自动换行的字符串作为目标字符串返回
	 * @param values 输入单行字符串确定没有回车（有回车可能导致出现空行的问题）
	 * @param lineSize 指定目标字符串行宽（以英文字符个数计算）
	 * @return
	 */
	public static String autoCut(String values, final int lineSize){
		String enter = "\r\n";
		int strLen = 0;
		
		StringBuilder resultBuilder = new StringBuilder();
		for(int i = 0; i < values.length(); i++){
			char value = values.charAt(i);
			/**
			 * 当遍历到的字符为空白字符时，如果在行首则不打印，如果在行为且装不下就直接
			 * 添加一个换行符
			 */
			if(Character.isWhitespace(value)){
				if(strLen == 0){
					
				}else if(strLen + 1 > lineSize){
					resultBuilder.append(enter);
					strLen = 0;
				}else{
					resultBuilder.append(value);
					strLen += 1;
				}
			}
			/**当字符为中文时,如果中文后面紧接着是符号和空白字符,则将中文字符和空白字符合并
			 *为不可分割的字符串（即要么都换行，要么都不换行）
			 */
			else if(isChinese(value)){
				boolean joinSybol = false;
				int width = 1;
				String isSymbol = "";
				if(i + 1 < values.length()){
					isSymbol = values.charAt(i+1) + "";
					joinSybol = symbolMatcher(isSymbol);
				}
				if(joinSybol){
					width = 2;
					i++;
				}else{
					isSymbol = "";
				}
				if(strLen + 2 * width <= lineSize){
					resultBuilder.append(value + isSymbol);  //当字符长度加上当前长度为小于行长时
					strLen += 2 * width;
				}else{
					resultBuilder.append(enter);  
					resultBuilder.append(value + isSymbol);
					strLen = 2 * width;
				}
			}else{
//				int nextIndex = getNextBlankIndex(values, i, formatWord);
				int nextIndex = getNextSeparate(values, i);
				if(nextIndex == -1){
					String tempStr = values.substring(i);
					if(strLen + tempStr.length() > lineSize){
						resultBuilder.append(enter);
						resultBuilder.append(tempStr);
						break;
					}
				}else{
					String tempStr = values.substring(i, nextIndex);
					if(strLen + tempStr.length() > lineSize){
						resultBuilder.append(enter);
						resultBuilder.append(tempStr);
						strLen = nextIndex - i;
						i = nextIndex -1;
					}else{
						resultBuilder.append(tempStr);
						strLen += nextIndex - i;
						i = nextIndex -1;
					}
				}
			}
		}
		return resultBuilder.toString();
	}
	
	/**
	 * 供自动换行方法formatString4Auto()内部调用，通过传入英文字符当前遍历位置begin，查找此英文
	 * 单词的结束位置，结束位置可以是单词隔离符，也可以是某个中文字符的开始
	 * @param values  传入的字符串
	 * @param begin   字符串当前遍历位置
	 * @param formatWord  单词隔离符
	 * @return
	 */
	private static int getNextBlankIndex(String values, final int begin, String formatWord){
		int index = values.indexOf(formatWord, begin);
		for(int i = begin + 1; i < values.length(); i++){
			char value = values.charAt(i);
			if(isChinese(value) || (index != -1 && index < i)){
				if(index > 0){
					return i < index ? i : index;
				}else{
					return i;
				}	
			}
		}
		return -1;
	}
	
	/**
	 * 供自动换行方法formatString4Auto()内部调用，传入字符串和字符串当前遍历位置，
	 * 找出最早的一个标点符号或者空白字符的位置
	 * @param values
	 * @param begin
	 * @return
	 */
	private static int getNextSeparate(String values, final int begin){
		int index = -1;
		String regex = "\\pP|\\s";
		String subString = values.substring(begin);
		String []sepStrings = subString.split(regex, 2);
		String destStr = sepStrings[0];

		if(begin + destStr.length() + 1 < values.length()){
			index = begin + destStr.length() + 1;
		}
		for(int i = begin + 1; i < values.length(); i++){
			char value = values.charAt(i);
			if(isChinese(value) || (index != -1 && index < i)){
				if(index > 0){
					return i < index ? i : index;
				}else{
					return i;
				}	
			}
		}
		return index;
	}

	/**
	 * 判断一个字符是否为标点符号
	 * @param value
	 * @return
	 */
	private static boolean symbolMatcher(String value){
		Pattern pattern = Pattern.compile("\\pP");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	/**
	 * 判断字符是否为中文字符
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
			return true;
		return false;
	}
	
	/**
	 * 字符串自动换行的方法，支持中英文混合字符串换行（中文字符宽度设为英文字符的两倍），
	 * 通过传入要处理的字符串，每行长度（可打印英文字符个数），英文单词间隔离符惊醒处理
	 * @param value  要格式化的字符串
	 * @param lineSize  每行可打英文字符个数
	 * @return  
	 */
	public static String formatString4Auto(String value, final int lineSize) {
		value = formatString(value);
		StringBuilder sb = new StringBuilder();
		String entr = "\r\n";
		String[] valueArray = value.split(entr);
		
		for (String valueTmp : valueArray) {
			if (StringUtils.isNotBlank(sb.toString())) {
				sb.append(entr);
			}
			sb.append(autoCut(valueTmp, lineSize));
		}
		
		return sb.toString();
	}
	
	public static String formatString(String value) {

		if (StringUtils.isNotBlank(value)) {
			value = value.replaceAll("<", "@@lt;");
			value = value.replaceAll(">", "@@gt;");
			value = value.replaceAll("&#039;", "\'");
			value = value.replaceAll("'", "@@apos;");
			// strEndorseText = strEndorseText.replaceAll("\"", "@@quot;");
			/**
			 * 错误输入书名号的解决办法 错误输入<< 书名>>,正确输入《书名》
			 */

			value = value.replaceAll("@@apos;", "\'");
			value = value.replaceAll("@quot;", "\"");
			value = value.replaceAll("&quot;", "\"");
			value = value.replaceAll("@@lt;@@lt;", "《");
			value = value.replaceAll("@@gt;@@gt;", "》");
			// 过滤tab键
			value = value.replaceAll("\t", "  ");
			value = value.replaceAll("@@lt;", "<");
			value = value.replaceAll("@@gt;", ">");
			return value;
		} else {
			return "";
		}
	}
}

/**
 * 进行字符串空值判断的方法
 * @author tianxingjian
 *
 */
class StringUtils {
	public static boolean isNotBlank(String str){
		if(null != str && !"".equals(str.trim())){
			return true;
		}
		return false;
	}
}