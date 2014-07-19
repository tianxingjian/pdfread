package org.miner;

import java.math.BigInteger;

public class Miner {

	private static void Miner(String[] strKeys,String[] strValues){
		for(int i=1;i<=Math.pow(2, strKeys.length)-1;i++){
			int temp = i;
			int pos=0;
			String strTemp="";
			String keyStr="";
			int[] keepValues=new int[strValues[0].length()];
			int freCount=0;//统计频繁数
			while(temp!=0){
				//System.out.println(pos);
				if(temp%2==1){//找到1的位置
					strTemp=strValues[pos];//将
					keyStr+=strKeys[pos]+"&";
					//对应位置出现1的加1操作
					for(int ii=0;ii<strTemp.length();ii++){
						char ch = strTemp.charAt(ii);
						if('1'==ch){
							keepValues[ii]++;
						}
					}
				}pos++;
				temp/=2;
			}
			for(int jj=0;jj<keepValues.length;jj++){
				if(keepValues[jj]==keyStr.split("&").length)
					freCount++;
			}
			if(freCount>=9){//输出满足条件的频繁项以及频繁数
				System.out.println(keyStr.substring(0,keyStr.length()-1)+" "+freCount);
			}
		}
			
	}
	public static void main(String[] args){
		String[] str=new String[7];
		str[0]="10101011 01011001 11100111 01100001";
		str[1]="10011001 01110000 00101011 00001011";
		str[2]="00101011 10100101 01000111 00100110";
		str[3]="00010111 01111001 11010110 00000111";
		str[4]="10101100 00000000 00001100 11011110";
		str[5]="01001010 00001011 00000000 00000000";
		str[6]="01010000 00101101 10110010 00011111";
		String[] key=new String[7];
		key[0]="a";
		key[1]="b";
		key[2]="c";
		key[3]="d";
		key[4]="e";
		key[5]="f";
		key[6]="g";
		Miner(key,str);
		BigInteger a;
	}
}
