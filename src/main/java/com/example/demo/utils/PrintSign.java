//package com.example.demo.utils;
//
//import com.itextpdf.text.BadElementException;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//import com.itextpdf.text.pdf.parser.ImageRenderInfo;
//import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
//import com.itextpdf.text.pdf.parser.RenderListener;
//import com.itextpdf.text.pdf.parser.TextRenderInfo;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PrintSign {
//
//    public static void main(String[] args) throws Exception  {
//        // TODO Auto-generated method stub
//        String s = "D:/main.pdf";
//        File file = new File("D:/main.pdf");
//        int len = 0;
//        FileInputStream stream = new FileInputStream(file);
//        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//        byte[] buffer = new byte[5];
//        //先读后写,循环读写
//        while ((len = stream.read(buffer)) != -1) {
//            stream2.write(buffer, 0, len);
//        }
//        printSign(stream2);
//    }
//
//    public static void printSign(OutputStream outputStream){
//        List<String> keyWords = new ArrayList<String>();
//        keyWords.add("紫光数码");//支持多关键字，默认选择第一个找到的关键字
//        PdfReader pdfReader;
//        PdfStamper pdfStamper = null;
//        try {
//            ByteArrayOutputStream outputStream1 = (ByteArrayOutputStream) outputStream;
//            pdfReader = new PdfReader(outputStream1.toByteArray());
//            pdfStamper = new PdfStamper(pdfReader, outputStream);
//            List<List<float[]>> arrayLists = findKeywords(keyWords, pdfReader);//查找关键字所在坐标
//            //一个坐标也没找到，就返回
//            if (arrayLists==null) {
//                return;
//            }
//            if (arrayLists.get(0)!=null) {
//                for (int i=0; i<arrayLists.get(0).size(); i++) {
//                    int i1 = (int) arrayLists.get(0).get(i)[2];
//                    PdfContentByte overContent = pdfStamper.getOverContent(1);
//                    String imgPath = "D:/a.jpg";
//                    float sealWidth = 150f;
//                    float sealHeight = 95f;
//                    InputStream pageHeaderInputstream = new FileInputStream(imgPath);
//                    Image pageHeaderImg = getImgByInputstream(pageHeaderInputstream);
//                    pageHeaderImg.setAlignment(Element.ALIGN_LEFT);
//                    pageHeaderImg.scaleAbsolute(sealWidth, sealHeight);// 控制签章大小
//                    pageHeaderImg.setAbsolutePosition(arrayLists.get(0).get(i)[0], arrayLists.get(0).get(i)[1] - sealHeight/2);// 控制图片位置
//                    overContent.addImage(pageHeaderImg);//将图片加入pdf的内容中
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            //此处一定要关闭流，否则可能会出现乱码
//            if(pdfStamper != null){
//                try {
//                    pdfStamper.close();
//                } catch (DocumentException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    private static List<List<float[]>> findKeywords(final List<String> keyWords, PdfReader pdfReader) throws IOException {
//        File pdfFile = new File("D:\\main.pdf");
//        byte[] pdfData = new byte[(int) pdfFile.length()];
//        FileInputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(pdfFile);
//            inputStream.read(pdfData);
//        } catch (IOException e) {
//            try {
//                throw e;
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                }
//            }
//        }
//        String keyword = "紫光数码";
//        List<float[]> positions = PdfKeywordFinder.findKeywordPostions(pdfData, keyword);
//        System.out.println("total:" + positions.size());
//        List<List<float[]>> list=new ArrayList<>();
//        list.add(positions);
//        return list;
//    }
//
//    public static List<String> parseList(String source, String regex) {
//        if (source == null ||  "".equals(source)) {
//            return null;
//        }
//        List<String> strList = new ArrayList<String>();
//        if (regex== null ||  "".equals(regex)) {
//            strList.add(source);
//        } else {
//            String[] strArr = source.split(regex);
//            for (String str : strArr) {
//                if (str!= null ||  !"".equals(str)) {
//                    strList.add(str);
//                }
//            }
//        }
//        return strList;
//    }
//    private static Image getImgByInputstream(InputStream is) {
//        if (is == null) {
//            return null;
//        }
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        Image img = null;
//        try {
//            readInputStream(is, output);
//            try {
//                img = Image.getInstance(output.toByteArray());
//            } catch (BadElementException e) {
//                e.printStackTrace();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return img;
//    }
//    public static void readInputStream(InputStream inputStream, OutputStream outputStream) throws IOException{
//        byte[] buffer = new byte[2048];
//        int n = 0;
//        while (-1 != (n = inputStream.read(buffer))) {
//            outputStream.write(buffer, 0, n);
//        }
//        inputStream.close();
//    }
//
//
//}
