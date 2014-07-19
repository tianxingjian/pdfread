package com.pdf;

import java.io.File;
import java.util.LinkedList;

public class ListFileDir {
	
	public static void listDir(String dirStr)throws Exception {
		long a = System.currentTimeMillis();

		LinkedList<File> list = new LinkedList<File>();
		File dir = new File(dirStr);
		File file[] = dir.listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isDirectory()) {
				list.add(file[i]);
			} else {
				String fileName = file[i].getName();
				if (fileName.endsWith(".pdf")) {
//					System.out.println(fileName);
					String text = PdfReadBase.getText(file[i]);
					String  subString = TextCut.cutText(text, "(22)");
					String  subString1 = TextCut.cutText(text, "(71)");
					if("".equals(subString1)){
						subString1 = TextCut.cutText(text, "(73)");
					}
					System.out.println(fileName + "\t" + subString + "\t" + subString1);
				}
			}
		}
		
		File tmp;
		while (!list.isEmpty()) {
			tmp = list.removeFirst();
			if (tmp.isDirectory()) {
				file = tmp.listFiles();
				if (file == null)
					continue;
				for (int i = 0; i < file.length; i++) {
					if (file[i].isDirectory()){
						list.add(file[i]);
					}
					else{
						String fileName = file[i].getName();
						if (fileName.endsWith(".pdf")) {
//							System.out.println(fileName);
							String text = PdfReadBase.getText(file[i]);
							String  subString = TextCut.cutText(text, "(22)");
							String  subString1 = TextCut.cutText(text, "(71)");
							if("".equals(subString1)){
								subString1 = TextCut.cutText(text, "(73)");
							}
							System.out.println(fileName + "\t" + subString + "\t" + subString1);
						}
					}	
				}
			} else {
				System.out.println(tmp.getAbsolutePath());
			}
		}
		System.out.println(System.currentTimeMillis() - a);
	}

	
	public static void main(String args[])throws Exception{
		listDir("d:/pdf1");
	}
}
