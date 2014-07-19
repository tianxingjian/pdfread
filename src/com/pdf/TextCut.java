package com.pdf;

public class TextCut {
	public static String cutText(String text, String indexText){
		
		int startIndex = 0;
		int endIndex = 0;
		
		startIndex = text.indexOf(indexText);
		if(startIndex >= 0){
			endIndex = text.indexOf("\n", startIndex);
			return text.substring(startIndex + indexText.length(), endIndex -1);
		}
		return "";
	}
}
