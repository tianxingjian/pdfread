package test;

import nc.ui.pub.excel.ImportExcelData;

public class ExcelImportTest {

	public static void main(String[] args) {
		ImportExcelData data = new nc.ui.pub.excel.ImportExcelData();
		Object[][] importData = data.executeImport();
		
		for(int i = 0; i < importData.length; i++){
			for(int j = 0; j < importData[i].length; j++){
				System.out.print(importData[i][j] + "\t");
			}
			System.out.println();
		}
	}

}
