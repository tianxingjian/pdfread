package com.pinfanshu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class model {
	final static float t = 0.01f;
	final static long n = 100000;
	
	/*
	 * 32位整数1个数
	 */
	private final static long MASK_1 = 0x5555555555555555L;
    private final static long MASK_2 = 0x3333333333333333L;
    private final static long MASK_4 = 0x0F0F0F0F0F0F0F0FL;
    private final static long MASK_8 = 0x00FF00FF00FF00FFL;
    private final static long MASK_16 = 0x0000FFFF0000FFFFL;
    private final static long MASK_32 = 0x00000000FFFFFFFFL;
   
    public static int bitCount_Parallel(long n){
        n = (n & MASK_1) + ((n >>> 1) & MASK_1); 
        n = (n & MASK_2) + ((n >>> 2) & MASK_2); 
        n = (n & MASK_4) + ((n >>> 4) & MASK_4); 
        n = (n & MASK_8) + ((n >>> 8) & MASK_8); 
        n = (n & MASK_16) + ((n >>> 16) & MASK_16); 
        n = (n & MASK_32) + ((n >>> 32) & MASK_32); 
        return (int)n;
    }
    /*
	 * 计算单项中1的位数 ,返回1的个数
	 */
	public static int BitCount(String str){
		String[] valueStr = str.split(" ");//首先进行拆分
		int bitCount=0;

		for(int i=0;i<valueStr.length;i++)
		{
			String[] values=valueStr[i].split(",");//按“,”拆分
			for(int j=0;j<values.length;j++){
				
				if(values[j]=="0")
					bitCount+=0;
				else
					bitCount+=bitCount_Parallel(Long.parseLong(values[j]));
			}
		}

		return bitCount;
	}
	/*
	 * 计算两个组合中1的位数 ,返回支持数以及组合后的数
	 */
	public static String[] BitCount(String str1,String str2){
		String[] resultData = new String[2];
		
		
		String[] fonStr = str1.split(" ");
		String[] backStr = str2.split(" ");
		String str="";
		int bitCount=0;
		if(str1.length()==0){
			str=str2;
			for(int i=0;i<backStr.length;i++){
				String[] values=backStr[i].split(",");
				for(String val:values){
					if(val=="0")
						bitCount+=0;
					else
						bitCount+=bitCount_Parallel(Long.parseLong(val));
				}
			}
		}
		else{
			for(int i=0;i<fonStr.length;i++){
				String[] valuesFon=fonStr[i].split(",");
				String[] valuesBack=backStr[i].split(",");
				for(int j=0;j<((valuesFon.length<valuesBack.length)?valuesFon.length:valuesBack.length);j++){
					if(valuesFon[j]=="0"||valuesBack[j]=="0"){
						str+="0"+",";
						bitCount+=0;
					}
					else{
						long temp = Long.parseLong(valuesFon[j])&Long.parseLong(valuesBack[j]);//求&运算
						bitCount+=bitCount_Parallel(temp);
						str+=Long.toString(temp)+",";
					}
				}
				str=str.substring(0, str.length()-1);//去掉“,”
				str+=" ";
			}
			
		}
		resultData[0]=str;
		resultData[1]=Integer.toString(bitCount);
		return resultData;
	}
	
	public static boolean Output(int cur_totalVar,String[] keyResult,String[] valueResult){
		String str = "";//中间变量
		String strKeycom = "";//记录组合
		int resCount = 0;//组合支持数
		int frequentNumber = (int) (t*n); //将map中支持数小于指定值的模式直接过滤
		//int k;//记录最大组合数，超过的直接返回false
		//从两个组合起
		//if(cur_totalVar>=k)return false;
		if(cur_totalVar>=2){//考虑到每项都是频繁项
			str = valueResult[0];//这样可以每次调用Output时减少一次BitCount
			for (int i=0;i<cur_totalVar;i++)
			{
				String[] resultStr = BitCount(str, valueResult[i]);//保存组合和对应支持数
				resCount = Integer.parseInt(resultStr[1]);//获取支持数
				
				if (resCount > frequentNumber) {
					strKeycom += keyResult[i] + "&";
					str = resultStr[0];
				}
				else
					return false;//过滤掉不符合要求的组合
			}
		}
		if(resCount != 0){
			System.out.println("keyCom="+strKeycom.subSequence(0, strKeycom.length()-1)+"	 resCount="+ +resCount);
			//dataStore.writeData(fileName,strKeycom.subSequence(0, strKeycom.length()-1)+ "  " +resCount+"\r\n");
		}
		return true;
	}
	/**
	 * @param cur_totalVar
	 * 			     当前位置
	 * @param nextVar
	 * 			     下一位置
	 * @param keyResult
	 * 			     保存需进行组合的key
	 * @param n
	 *            组合数大小
	 * @param keyMat
	 * 			     存放所有从文件中读取的key      
	 * @param valueResult
	 *            保存需进行组合的key所对应的value
	 * @param valueMat
	 *            存放所有从文件中读取的value  
	 */
	public static void Com(int cur_totalVar,int nextVar,String[] keyResult,int n,String[] keyMat,String[] valueResult,String[] valueMat) {
		
		if(Output(cur_totalVar,keyResult,valueResult)){
			for (int i=nextVar;i<n;i++){
				keyResult[cur_totalVar]=keyMat[i];
				valueResult[cur_totalVar]=valueMat[i];
				Com(cur_totalVar+1,i+1,keyResult,n,keyMat,valueResult,valueMat);//易错的地方，递归的时候使用i+1，相同的位置分别存放各个位置
			}
		}
		else
		{
			for (int i=nextVar;i<n;i++)
			{
				keyResult[cur_totalVar]=keyMat[i];//重新定位
				valueResult[cur_totalVar]=valueMat[i];
			}
		}
	}
	/*
	 * 用于找出频繁模式
	 */
	
	
	@SuppressWarnings("deprecation")
	public static void Miner() throws IOException{
		long startime=System.currentTimeMillis();
		List<String[]> valuesList=new ArrayList<String[]>();//用于保存从文件中读取的数据
	    final String fileName = "d:\\result\\part-r-00000";
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				String[] strSpilt = tempString.split("\t", 2);
				valuesList.add(strSpilt);// 将频繁项添加到valuesList中
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		
        //将List中的数据读入到String数组中进行操作
        final String[] keyStr=new String[valuesList.size()];//保存key数据，用于启动Com
        final String[] valuesStr=new String[valuesList.size()];//保存values数据，用于启动Com
        int frequentNumber = (int) (t*n); //将map中支持数小于指定值的模式直接删掉
        int tempPos=0;//临时变量
        for (int i = 0; i < valuesList.size(); i++) {    
        	
        	String[] lineStr = (String[]) valuesList.get(i);
        	
        	//设置函数检测此数值串中1的位数是否符合要求 
			int resCount = BitCount(lineStr[1]);
			//数据划分
			if(resCount>frequentNumber){
				keyStr[tempPos]=lineStr[0];//保存Key
				valuesStr[tempPos]=lineStr[1];//保存values
				System.out.println("KeyCom="+keyStr[tempPos]+"	resCount="+resCount+"\r\n");
				tempPos++;
			}
    	}
        
        String[] keyMat = keyStr;
        String[] valueMat = valuesStr;
        String[] keyResult = new String[keyStr.length];
        String[] valueResult = new String[valuesStr.length];
        
        /*
        /*
         * 调用Com进行组合，并将满足条件组合即频繁项写入文件
         */
        //进行Com调用
        Com(0,0,keyResult,keyStr.length,keyMat,valueResult,valueMat);//进入组合调用
        System.out.println("Miner执行时间为："+ (System.currentTimeMillis()-startime)/1000 + "秒!");
	}
	public static void main(String[] arg) throws IOException{
		Miner();
	}
}
