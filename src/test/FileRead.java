package test;



import java.io.IOException;
import java.util.BitSet;



public class FileRead {
	
	/*
	 * 从n个数据中挑选m个进行排列组合
	 */
	public static void Com(String[] strData,int n,int m){
		int[] number=new int[n];
		long count=0;
		boolean findfirst,end,swap;
		int i,j,k,l;
		end=false;
		System.out.println("n=" + n + ",m=" + m);
		//初始化number
		for(int ii=0;ii<m;ii++){
			number[ii]=1;
		}
		//屏蔽掉结果输出以节约时间 ,第一组数据
		for(int ii=0;ii<n;ii++) {
			if(number[ii]!=0){
				//System.out.print((ii+1)+" ");
				System.out.print(strData[ii]+" ");
			}
		}
		System.out.println();
		/*
		 * 给定变量初始值
		 */
		count=1; //计数
		j=n; 
		k=0;
		if(n==m)return;
		while(!end) 
		{ 
			findfirst=false; 
			swap=false; //标志复位 
			for(i=0;i<j;i++) { 
				if(!findfirst && number[i]!=0) { 
					k=i; //k记录下扫描到的第一个数 
					findfirst=true; //设置标志	
				} 
				if(number[i]==1 && number[i+1]==0) //从左到右扫描第一个“10”组合 
				{ 	
					number[i]=0; 
					number[i+1]=1; 
					swap=true; //设置交换标志 
					for(l=0;l<i-k;l++) 
						number[l]=number[k+l]; 
					for(l=i-k;l<i;l++) 
						number[l]=0; //交换后将之前的“1”全部移动到最左端 
					if(k==i && i+1==n-m) //如果第一个“1”已经移动到了m-n的位置，说明这是最后一个组合了。 
						end=true;
				} 
				if(swap) //交换一次后就不用继续找“10”组合了 
					break; 
			} 
			//屏蔽掉结果输出以节约时间 
			for(int ii=0;ii<n;ii++) {
				if(number[ii]!=0){
					//System.out.print((ii+1)+" ");
					System.out.print(strData[ii]+" ");
				}
			}
			System.out.println();
			count++; //组合数计数器递增1  
		}
		System.out.println("count="+count);

	}
	
	
	public static void main(String[] args) throws IOException {
		
		String str="a b c d e f";
		String[]strSpilt=str.split(" ");
		int n=6;
		int m=3;
		Com(strSpilt,n,m);
		
//		String tempStr = "01111000";
//		BitSet bs = new BitSet(tempStr.length()); 
//		for(int i = 0; i < tempStr.length(); i++){
//			char temp = tempStr.charAt(i);
//			if('1' == temp){
//				System.out.println(i);
//				bs.set(i);
//			}
//		}
//		System.out.print(bs.toString());
//		System.out.println("wuqu");
	}
	

}
