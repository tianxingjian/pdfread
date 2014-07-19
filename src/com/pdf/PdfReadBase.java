package com.pdf;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PdfReadBase {
	
    public static String getText(File file)throws Exception{
        boolean sort=false;
        int startPage=1;
        int endPage=10;
        PDDocument document=null;
        try{
            try{
                document=PDDocument.load(file);
            }catch(MalformedURLException e){
                
            }
            PDFTextStripper stripper=new PDFTextStripper();
            stripper.setSortByPosition(sort);
            stripper.setStartPage(startPage);
            stripper.setEndPage(endPage);
            String result = stripper.getText(document);
//            System.out.println(result);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }finally{
            if(document!=null){
                document.close();
            }
        }
    }
    
    public static void main(String[] args){
        File file=new File("D:/200910142250-ÕºœÒ‘Î…˘∆¿º€∑Ω∑®°¢ÕºœÒ‘Î…˘∆¿º€◊∞÷√.pdf");
        try{
            String cont=getText(file);
            System.out.println(cont);
        }catch(Exception e){
            System.out.println("Strip failed.");
            e.printStackTrace();
        }
    }
}

