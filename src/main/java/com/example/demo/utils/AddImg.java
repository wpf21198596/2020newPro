package com.example.demo.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class AddImg {
    public static void main(String[] args) {

    }

    public static void getxy(String filePath,String outPath) throws IOException {

// 获得pdf页数
        int pdfPage = 3; //指定将和 图片拼接的 PDF

// 获取第一页宽和高
        PdfReader pdfreader = new PdfReader(filePath);
        Document document = new Document(pdfreader.getPageSize(1));
// 获取页面宽度
        float width = document.getPageSize().getWidth();
// 获取页面高度
        float height = document.getPageSize().getHeight();
        if ( pdfreader != null)
            pdfreader.close();
        if ( document != null)
            document.close();
        System.out.println("width = "+width+", height = "+height);
        String picturePath; //图片路径


        PdfReader pdf = new PdfReader(filePath);
        PdfStamper stamper  = null;

        try {

            stamper  =  new PdfStamper(pdf, new FileOutputStream(outPath));//生成的PDF 路径 outPath
            for (int i = 1 ;i <= pdfPage; i++){
                PdfContentByte overContent = stamper.getOverContent(i);
                picturePath = "D:/" + "/wordTemplate/"+"cutContract"+i+".png";
                // 剪切图片
                File directory = new File("");// 参数为空
                String courseFile = directory.getCanonicalPath();
                cutPicture("jpg",courseFile+"\\src\\main\\webapp\\WEB-INF\\docPicture\\contract.png",113/pdfPage*(i-1),0,113/pdfPage,113,picturePath);
                //添加图片
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(picturePath);//图片名称
                image.setAbsolutePosition((int)width-113/pdfPage,(int)height/2);//左边距、底边距
                overContent.addImage(image);
                overContent.stroke();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (null != stamper ){
                    stamper.close();
                }
                if (pdf != null){
                    pdf.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    public static void cutPicture(String lastDir, String srcPath, int x, int y, int width,
                                  int height, String subPath) throws IOException {

        FileInputStream is = null;
        ImageInputStream iis = null;

        try {
            // 读取图片文件
            is = new FileInputStream(srcPath);
            /*
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
             * 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
             */
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(lastDir);
            ImageReader reader = it.next();
            // 获取图片流

            iis = ImageIO.createImageInputStream(is);
            /*
             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
             */
            reader.setInput(iis, true);

            /*
             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
             * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
             */
            ImageReadParam param = reader.getDefaultReadParam();
            /*
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
             */
            Rectangle rect = new Rectangle(x, y, width, height);
            // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);
            /*
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
             * BufferedImage 返回。
             */
            BufferedImage bi = reader.read(0, param);

            // 保存新图片
            ImageIO.write(bi, lastDir, new File(subPath));
        } finally {
            if (is != null)
                is.close();
            if (iis != null)
                iis.close();
        }
    }
}
