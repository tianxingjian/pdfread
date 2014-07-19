package test;

import com.pub.StringUtils;

public class Test1 {
	public static void main(String args[]) {
		String tesStr = "特别约。定 1.\"以人zheng民币结算,本保单不提供外汇的共同海损和/或救助担保.\" 2.\"船龄超过20年(含20年)的船舶,保险人按老龄船加费,超过25年船龄(含25年) 保险人不予承保.\" 3.本保单启运时间以投保单传真时间为准，即2013年01月05日  12时";
		String testStr = "ever body ,here, PICC人保财险信息技术部";
//		System.out.println(formatString4Auto1(tesStr, 10, " "));
		System.out.println(formatString4Auto1(testStr, 10, " "));
//		System.out.println("，".getBytes().length);
	}
	
	public static String autoCut(String values, final int lineSize, String formatWord){
		String enter = "\r\n";
		int strLen = 0;
		final int formatLen = formatWord.length();
		StringBuilder resultBuilder = new StringBuilder();
		for(int i = 0; i < values.length(); i++){
			char value = values.charAt(i);
			int formatIndex = values.indexOf(formatWord, i);
			//i==formatIndex表示当前正好游到了分离符那里，如果能打印下就打印，打印不下就去掉，因为行首不打印分离符
			if(i == formatIndex ){
				if(strLen == 0){
					i += formatLen;
					continue;
				}else if(strLen + formatLen > lineSize){
					resultBuilder.append(enter);
					i += (formatLen - 1 > 0) ? (formatIndex -1) : 0;
					strLen = 0;
					continue;
				}else{
					resultBuilder.append(formatWord);
					i += (formatLen - 1 > 0) ? (formatIndex -1) : 0;
					strLen += formatLen;
					continue;
				}
			}
			//当字符为中文时
			else if(isChinese(value)){  
				if(strLen + 2 <= lineSize){
					resultBuilder.append(value);  //当字符长度加上当前长度为小于行长时
					strLen += 2;
				}else{
					resultBuilder.append(enter);  
					resultBuilder.append(value);
					strLen = 2;
				}
			}else{
				int nextIndex = getNextBlankIndex(values, i, formatWord);
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
	 * 供自动换行方法formatString4Auto1()内部调用，通过传入英文字符当前遍历位置begin，查找此英文
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
	 * @param formatWord 英文单词隔离符
	 * @return  
	 */
	public static String formatString4Auto1(String value, final int lineSize,
			String formatWord) {
		value = formatString(value);
		StringBuilder sb = new StringBuilder();
		String entr = "\r\n";
		String[] valueArray = value.split(entr);
		
		for (String valueTmp : valueArray) {
			if (StringUtils.isNotBlank(sb.toString())) {
				sb.append(entr);
			}
			sb.append(autoCut(valueTmp, lineSize, formatWord));
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
