package nc.ui.pub.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelFileReader {

	private String m_sFilePath = null;

	private int titleRow = 0;

	/** �������� */
	private FileInputStream fileIn = null;

	private POIFSFileSystem fs = null;

	private HSSFWorkbook workBook = null;
	private int row;
	private int count;
	private int sheetnum = 0;
	private String sheetname = null;
	public String getSheetname() {
		return sheetname;
	}

	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}

	public int getSheetnum() {
		return sheetnum;
	}

	public void setSheetnum(int sheetnum) {
		this.sheetnum = sheetnum;
	}

	public void closeFile() throws Exception {
		fileIn.close();
	}

	public int getFieldCount(int nSheet) {
		try {
			HSSFRow row = getFieldNameRow(getSheet(nSheet));
			return row.getPhysicalNumberOfCells();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String[] getFieldNames(int nSheet) {
		return null;
	}

	public boolean hasNextRecord(int nSheet) {
		return false;
	}

	public Object[] nextRecord(int nSheet) throws Exception {
		return null;
	}

	public void openFile(String sPath) throws Exception {
		m_sFilePath = sPath;
		File file = new File(m_sFilePath);
		if (!file.exists()) {
			throw new Exception("Ŀ���ļ�����ʧ�ܣ�" + m_sFilePath);
		}

		try {
			/** ��XLS�ļ� */
			fileIn = new FileInputStream(m_sFilePath);
			fs = new POIFSFileSystem(fileIn);
			workBook = new HSSFWorkbook(fs);
		} catch (Exception ne) {
			ne.printStackTrace();
			throw new Exception("��֧�ֵ�Excel�ļ���ʽ����ȡʧ��");
		}
	}

	public Object[][] readAllRecords(int nSheet) throws Exception {
		return null;
	}

	public Object[] readAllTable() throws Exception {
		int num = workBook.getNumberOfSheets();
		Vector v = new Vector();
		Object[] obj = null;
		for (int i = 0; i < num; i++) {
			if (workBook.getSheetName(i) != null) {
				v.addElement(workBook.getSheetName(i));
			}
		}
		if (v != null && v.size() > 0) {
			obj = new Object[v.size()];
			v.copyInto(obj);
		}
		return obj;
	}

	// ����������ֹ�д��һ�������� ������Դ�ڽӿ�
	public Object[][] readExcel() throws Exception {
		Object[][] obj = null;
		HSSFSheet sheet = getSheet(0);		
		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		int firstColumn = sheet.getRow(firstRow).getFirstCellNum();
		int lastColunm = sheet.getRow(firstRow).getLastCellNum();	
		for (int j = 0; j < lastRow -firstRow+1; j++) {
			HSSFRow row = sheet.getRow(firstRow +j);
			if (row != null) { // �����
				lastColunm = Math.max(row.getLastCellNum(), lastColunm);
			}
		}
		obj = new Object[lastRow - firstRow+1][lastColunm];
		setRow(lastRow - firstRow+1);
		setCount(lastColunm - firstColumn);
		// lastRow-firstRow:��������
		for (int j = 0; j < lastRow -firstRow+1; j++) {
			HSSFRow row = sheet.getRow(firstRow +j);
			if (row != null) {// �����
				/** ��ȡ�в��� */
				int iFirstCellNum = row.getFirstCellNum(); // ��ʼ�����
				int iLastCellNum = row.getLastCellNum(); // ���������  
				for (int m = iFirstCellNum; m <iLastCellNum; m++){
					HSSFCell cell = row.getCell((short) m);
					if (cell != null){
						obj[j][m] = new Object();
						obj[j][m] = getCellValue(cell);
					}
				}
			}
		}

		return obj;
	}

	private HSSFSheet getSheet(int sheetIndex) throws Exception {
		if(sheetname!=null){
			if(workBook.getSheet(sheetname)==null){
				throw new Exception("ҳǩ:"+sheetname+" ��������Ҫ��ҳǩ��Χ֮��,ѡ��ģ�����");
			}
			return workBook.getSheet(sheetname);
		}
		if (sheetnum < 0 || sheetnum > getSheetNum()){
			throw new Exception("ҳǩ��������Ҫ��ҳǩ��Χ֮��");
		}
		return workBook.getSheetAt(sheetnum);
	}

	private int getSheetNum() {
		return workBook.getNumberOfSheets();
	}

	private int getRecorderRows(HSSFSheet sheet) throws Exception {
		int iNumOfRows = sheet.getPhysicalNumberOfRows();
		return iNumOfRows;
		// if (iNumOfRows > getTitleRow())
		// return iNumOfRows - getTitleRow();
		// else
		// throw new Exception("��֧�ֵĸ�ʽ�����ҳû�����");
	}

	private HSSFRow getFieldNameRow(HSSFSheet sheet) throws Exception {
		if (getRecorderRows(sheet) >= 0){
			return sheet.getRow(getTitleRow());
		}else{
			throw new Exception("��֧�ֵĸ�ʽ�����ҳû�����");
		}
	}

	private Object getCellValue(org.apache.poi.hssf.usermodel.HSSFCell cell) throws Exception {
		int type = cell.getCellType();
		try {
			switch (type) {
			case HSSFCell.CELL_TYPE_NUMERIC: {
				Object value = null;
				value = new UFDouble(cell.getNumericCellValue());
				if (value.toString().indexOf(".") > 0) {
					String s = value.toString().substring(
							value.toString().indexOf("."),
							value.toString().length());
					char[] c = s.toCharArray();
					int iPower = 0;
					for(int i = c.length - 1; i >= 1; i--){
						if (c[i] != '0'){
							iPower = i + 1;
							break;
						}
					}
					value = (new UFDouble(value.toString())).setScale(-iPower,
							UFDouble.ROUND_DOWN);
				}
				return value;
			}
			case HSSFCell.CELL_TYPE_STRING: {
				return cell.getStringCellValue() == null ? null : cell
						.getStringCellValue().toString();
			}
			case HSSFCell.CELL_TYPE_FORMULA: {
				Double obj = cell.getNumericCellValue();
				if (!obj.equals(Double.NaN)){
					return new UFDouble(cell.getNumericCellValue()).toString();
				}else{
					return cell.getStringCellValue() == null ? null : cell
							.getStringCellValue().toString();
				}
			}
			case HSSFCell.CELL_TYPE_BLANK: {
				return null;
			}
			case HSSFCell.CELL_TYPE_BOOLEAN: {
				boolean flag = cell.getBooleanCellValue();
				if(flag){
					return new UFBoolean(true);
				}else{
					return new UFBoolean(false);
				}
//				System.out.println("���������δ���?û�ж���");
//				return null;
			}
			case HSSFCell.CELL_TYPE_ERROR: {
				
			}
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	/**
	 * @return ���� titleRow��
	 */
	public int getTitleRow() {
		return titleRow;
	}

	/**
	 * @param titleRow
	 *            Ҫ���õ� titleRow��
	 */
	public void setTitleRow(int titleRow) {
		this.titleRow = titleRow;
	}


	/**
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row Ҫ���õ� row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count Ҫ���õ� count
	 */
	public void setCount(int count) {
		this.count = count;
	}
}
