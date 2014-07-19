package nc.ui.pub.excel;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;

import nc.vo.pub.BusinessException;

public class ImportExcelData {


	private String path = null;
	
	private int sheetnum = 0;
	private String sheetname = null;
	HashMap<String,String> map = new HashMap<String, String>();
	
	public ImportExcelData() {
		super();
	}
	public ImportExcelData(String sheetname,boolean sheetnameflag) {
		super();
		this.sheetname = sheetname;
	}
	public ImportExcelData(String path,String sheetname) {
		super();
		setPath(path);
		this.sheetname = sheetname;
	}
	public ImportExcelData(int sheet) {
		super();
		this.sheetnum = sheet;
	}
	public ImportExcelData(String nodecode) throws BusinessException {
		super();
		initMap();
		if(nodecode==null||!map.containsKey(nodecode)){
			throw new BusinessException("���뵥�����ʹ���");
		}
		if(nodecode!=null&&map.get(nodecode.trim())!=null){
			this.sheetname = map.get(nodecode.trim());
		}else{
		}
	}
	
	public void initMap(){
		if(map!=null&&map.size()>0){
			map.clear();
		}
		map = new HashMap<String, String>();
//		String[] array = IBillTypeCode.TYPETONAME;
//		for(int i=0;i<array.length;i++){
//			String[] split = array[i].split("->");
//			map.put(split[0], split[1]);
//		}
	}
	
	//����һ���ļ�ѡ��ؼ�
	public JFileChooser  getChooser(){
		JFileChooser  fc = new JFileChooser ();
		fc.setMultiSelectionEnabled(false);
		fc.setFileFilter(new ExcelFileFilter());
		return fc;
	}
	
	/**
	 * ʵ���ļ�ѡ���������Ѵ�ѡ��Excel�ļ��ж�ȡ����ݷ���
	 * @return ��Excel�ļ��ж�������ֵ�������Ķ�ά���鷵��
	 */
	public Object[][] executeImport(){
		if(getPath() == null || getPath().length() == 0){

			JFileChooser  tmp = getChooser();

			int res = tmp.showOpenDialog(null);

			if(res == JFileChooser.CANCEL_OPTION){

				return null;
			}

			setPath(tmp.getSelectedFile().getPath());
		}
		if(getPath() == null || getPath().length() == 0){
			return null;
		}

		Object[][] retArrayList = readFile(getPath());
		return retArrayList;
	}
	
	public HashMap<String, Object[][]> executeImport(String[] sheetname){
		JFileChooser tmp = getChooser();
		int res = tmp.showOpenDialog(null);
		if(res == JFileChooser.CANCEL_OPTION){
			//ȡ��
			return null;
		}
		setPath(tmp.getSelectedFile().getPath());
		if(getPath() == null || getPath().length() == 0){
			return null;
		}
		//�Ѷ�������ֵ����һ����Χ������
		HashMap<String, Object[][]> map = readFile(getPath(),sheetname);
		return map;
	}
	
	public ArrayList<Object[][]> executeImport(int[] sheets) {
		JFileChooser tmp = getChooser();
		int res = tmp.showOpenDialog(null);
		if(res == JFileChooser.CANCEL_OPTION){
			return null;
		}
		setPath(tmp.getSelectedFile().getPath());
		if(getPath() == null || getPath().length() == 0){
			return null;
		}
		
		ArrayList<Object[][]>  retArrayList = readFile(getPath(),sheets);
		return retArrayList;
	}
	
	
	/**
	 * ��ȡExcel�ļ���ȡ���  �����ά������
	 * @param path �ļ�·��
	 * @return ��ά����
	 */
	private Object[][] readFile(String path) {
		Object[][] retArray = null;
		ExcelFileReader reader = null;
		try {
			reader = new ExcelFileReader();
			reader.setSheetnum(sheetnum);
			if(this.sheetname!=null){
				reader.setSheetname(this.sheetname);
			}
			reader.openFile(path);
		
			retArray = reader.readExcel();

			reader.closeFile();

		} catch (Exception e) {
			try {
				reader.closeFile();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return retArray;
	}

	private ArrayList<Object[][]> readFile(String path,int[] sheets) {
		ArrayList<Object[][]>  retArrayList = new ArrayList<Object[][]>();
		for(int i=0;i<sheets.length;i++){
			Object[][] retArray = null;
			ExcelFileReader reader = null;
			try {
				reader = new ExcelFileReader();
				reader.setSheetnum(sheets[i]);
				reader.openFile(path);
				//����ExcelFileReader��readExcel()ȥ��Excel���к���
				retArray = reader.readExcel();
				retArrayList.add(retArray);
				//�ر�
				reader.closeFile();
//				//	����к��е�set�ķ���,�����ȡ���ֵ 
//				setRow(reader.getRow());
//				setCount(reader.getCount());
//				int x=getCount();
			} catch (Exception e) {
				try {
					reader.closeFile();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				String msg =e.getMessage();
				if(e.getMessage()==null||e.getMessage().trim().equals("")){
					msg = "��֧�ֵ�Excel�ļ���ʽ����ȡʧ��";
				}
			
			}
		}
		return retArrayList;
	}
	
	private HashMap<String, Object[][]> readFile(String path,String[] sheetname){
		HashMap<String, Object[][]> map = new HashMap<String, Object[][]>();
		ExcelFileReader reader = new ExcelFileReader();
		try {
			reader.openFile(path);
			for(int i=0;i<sheetname.length;i++){
				Object[][] retArray = null;
				reader.setSheetname(sheetname[i]);
				//����ExcelFileReader��readExcel()ȥ��Excel���к���
				retArray = reader.readExcel();
				map.put(sheetname[i], retArray);
			}
			//�ر��ļ�
			reader.closeFile();
		} catch (Exception e) {
			try {
				reader.closeFile();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			String msg =e.getMessage();
			if(e.getMessage()==null||e.getMessage().trim().equals("")){
				msg = "��֧�ֵ�Excel�ļ���ʽ����ȡʧ��";
			}

		}
		return map;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
}
