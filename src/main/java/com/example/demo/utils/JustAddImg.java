package com.example.demo.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JustAddImg {
    private static int blank = 5;

    public static void main(String[] args) throws IOException {
        try {
            // 获取所有图片
            List<String> list = new ArrayList<>();
            list.add("D:\\zz.jpg");

            addPdfMark("D:\\main.pdf", "D:\\out.pdf", list,"代表签字");
        } catch (Exception e) {
            System.out.println("失败");
            e.printStackTrace();
        }
        System.out.println("成功");
    }

    public static void addPdfMark(String InPdfFile, String outPdfFile, List<String> imgList,String keywords) throws Exception {
        try {
            float[] position = PdfKeywordFinder.findPosition(keywords, InPdfFile);
            float x = position[1];
            float y = position[2];
            System.out.println(x+","+y);
            // 获取PDF文档信息
            Map<String, Object> pdfMsg = getPdfMsg(InPdfFile);
            // 开始计算图片起始位置 * PDF文档宽度 - （所有图片的宽度 + 每张图片右侧加5 个单位的空白 ）
            float pdfWidth = Float.valueOf(pdfMsg.get("width").toString());
            float pdfHeight = Float.valueOf(pdfMsg.get("height").toString());
            int   pageSize = (int) pdfMsg.get("pageSize");
            PdfReader reader = new PdfReader(InPdfFile, "PDF".getBytes());
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outPdfFile));

            for (String imgPath : imgList) {
                Map<String, Object> imgMsg = getImgMsg(imgPath);
                float imgWidth = Float.valueOf(imgMsg.get("width").toString());
                float imgHeight = Float.valueOf(imgMsg.get("height").toString());
                Image img = Image.getInstance(imgPath);// 插入水印   
                // 设置图片水印的位置。
                img.setAbsolutePosition((float) (pdfWidth*x), (float) (pdfHeight-pdfHeight*y-imgHeight/2));
                // 开始水印 如果需要每一页都加图片，这里添加循环即可
                PdfContentByte under = stamp.getUnderContent(pageSize);
                under.addImage(img);
            }

            stamp.close();// 关闭          
            File tempfile = new File(InPdfFile);

            if (tempfile.exists()) {
                tempfile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> getPdfMsg(String filePath) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            // 获取PDF共有几页
            PdfReader pdfReader = new PdfReader(new FileInputStream(filePath));
            int pages = pdfReader.getNumberOfPages();
            // System.err.println(pages);
            map.put("pageSize", pages);
            // 获取PDF 的宽高
            PdfReader pdfreader = new PdfReader(filePath);
            Document document = new Document(pdfreader.getPageSize(pages));
            float widths = document.getPageSize().getWidth();
            // 获取页面高度
            float heights = document.getPageSize().getHeight();
            // System.out.println("widths = " + widths + ", heights = " + heights);
            map.put("width", widths);
            map.put("height", heights);
            System.out.println(widths+","+heights);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Object> getImgMsg(String imgPath) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            File picture = new File(imgPath);
            BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
            // System.out.println("=源图宽度===>"+sourceImg.getWidth()); // 源图宽度
            // System.out.println("=源图高度===>"+sourceImg.getHeight()); // 源图高度
            map.put("width", sourceImg.getWidth());
            map.put("height", sourceImg.getHeight());
            System.out.println("图片：宽"+sourceImg.getWidth()+",高"+sourceImg.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
