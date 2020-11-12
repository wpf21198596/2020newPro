//package com.example.demo.utils;
//
//import com.itextpdf.text.BadElementException;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfSignatureAppearance;
//import com.itextpdf.text.pdf.PdfStamper;
//import com.itextpdf.text.pdf.parser.ImageRenderInfo;
//import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
//import com.itextpdf.text.pdf.parser.RenderListener;
//import com.itextpdf.text.pdf.parser.TextRenderInfo;
//import com.itextpdf.text.pdf.security.*;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.security.GeneralSecurityException;
//import java.security.KeyStore;
//import java.security.PrivateKey;
//import java.security.cert.Certificate;
//import java.util.LinkedList;
//import java.util.List;
//
//public class SignUtil {
//    /**
//     *
//     * @param inputStream
//     *            需要签章的pdf 输入流
//     * @param keyStoreStream
//     *            证书输入流
//     * @param keyStorePassword
//     *            证书密码
//     * @param signImage
//     *            签章图片（可以是文字生成的图片）
//     * @param signKeyWord
//     *            签章关键字
//     * @param reason
//     *            签订原因
//     * @param location
//     *            签订地址
//     * @throws GeneralSecurityException
//     * @throws IOException
//     * @throws DocumentException
//     */
//    public static ByteArrayOutputStream sign(InputStream inputStream, //
//                                             InputStream keyStoreStream, //
//                                             String keyStorePassword, //
//                                             Image signImage, //
//                                             String signKeyWord, //
//                                             String reason, //
//                                             String location) //
//            throws GeneralSecurityException, IOException, DocumentException {
//
//        // 读取pdf内容
//        PdfReader pdfReader = new PdfReader(inputStream);
//        int pageNum = pdfReader.getNumberOfPages();
//        // 签章位置对象
//        List<RectangleCentre> rectangleCentreList = new LinkedList<>();
//
//        // 下标从1开始
//        for (int page = 1; page <= pageNum; page++) {
//            RectangleCentre rectangleCentreBase = new RectangleCentre();
//            rectangleCentreBase.setPage(page);
//            PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);
//            pdfReaderContentParser.processContent(page, new RenderListener() {
//
//                StringBuilder sb = new StringBuilder("");
//                int maxLength = signKeyWord.length();
//
//                @Override
//                public void renderText(TextRenderInfo textRenderInfo) {
//                    // 只适用 单字块文档 以及 关键字整个为一个块的情况
//                    // 设置 关键字文本为单独的块，不然会错位
//                    boolean isKeywordChunk = textRenderInfo.getText().length() == maxLength;
//                    if (isKeywordChunk) {
//                        // 文档按照 块 读取
//                        sb.delete(0, sb.length());
//                        sb.append(textRenderInfo.getText());
//                    } else {
//                        // 有些文档 单字一个块的情况
//                        // 拼接字符串
//                        sb.append(textRenderInfo.getText());
//                        // 去除首部字符串，使长度等于关键字长度
//                        if (sb.length() > maxLength) {
//                            sb.delete(0, sb.length() - maxLength);
//                        }
//                    }
//                    // 判断是否匹配上
//                    if (signKeyWord.equals(sb.toString())) {
//                        RectangleCentre rectangleCentre = rectangleCentreBase.copy();
//
//                        // 计算中心点坐标
//
//                        com.itextpdf.awt.geom.Rectangle2D.Float baseFloat = textRenderInfo.getBaseline()
//                                .getBoundingRectange();
//                        com.itextpdf.awt.geom.Rectangle2D.Float ascentFloat = textRenderInfo.getAscentLine()
//                                .getBoundingRectange();
//
//                        float centreX;
//                        float centreY;
//                        if (isKeywordChunk) {
//                            centreX = baseFloat.x + ascentFloat.width / 2;
//                            centreY = baseFloat.y + ((ascentFloat.y - baseFloat.y) / 2);
//                        } else {
//                            centreX = baseFloat.x + ascentFloat.width - (maxLength * ascentFloat.width / 2);
//                            centreY = baseFloat.y + ((ascentFloat.y - baseFloat.y) / 2);
//                        }
//
//                        rectangleCentre.setCentreX(centreX);
//                        rectangleCentre.setCentreY(centreY);
//                        rectangleCentreList.add(rectangleCentre);
//                        // 匹配完后 清除
//                        sb.delete(0, sb.length());
//                    }
//                }
//
//
//                @Override
//                public void renderImage(ImageRenderInfo arg0) {
//                    // nothing
//                }
//
//                @Override
//                public void endTextBlock() {
//                    // nothing
//                }
//
//                @Override
//                public void beginTextBlock() {
//                    // nothing
//                }
//            });
//        }
//        if (rectangleCentreList.isEmpty()) {
//            return null;
//        }
//
//        // 获取证书相关信息
//        // 读取keystore
//        KeyStore keyStore = KeyStore.getInstance("PKCS12");
//        char[] password = keyStorePassword.toCharArray();
//        keyStore.load(keyStoreStream, password);
//        //
//        String alias = keyStore.aliases().nextElement();
//        // 获得私钥
//        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password);
//        // 获得证书链
//        Certificate[] chain = keyStore.getCertificateChain(alias);
//
//        ByteArrayOutputStream result = null;
//
//        RectangleCentre rectangleCentre;
//        for (int i = 0; i < rectangleCentreList.size(); i++) {
//            rectangleCentre = rectangleCentreList.get(i);
//            if (i > 0) {
//                // 多次签名，得重新读取
//                pdfReader = new PdfReader(result.toByteArray());
//            }
//            // 创建临时字节流 重复使用
//            result = new ByteArrayOutputStream();
//
//            // 创建签章工具
//            PdfStamper pdfStamper = PdfStamper.createSignature(pdfReader, result, '\0', null, true);
//            // 获取数字签章属性对象，设定数字签章的属性
//            PdfSignatureAppearance appearance = pdfStamper.getSignatureAppearance();
//            // 设置签章原因
//            appearance.setReason(reason);
//            // 设置签章地点
//            appearance.setLocation(location);
//            // 设置签章图片
//            appearance.setSignatureGraphic(signImage);
//            // 设置签章级别
//            appearance.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
//            // 设置签章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
//            appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
//            // 设置签章位置 图章左下角x，原点为pdf页面左下角，图章左下角y，图章右上角x，图章右上角y
//            appearance.setVisibleSignature(rectangleCentre.getRectangle(signImage), rectangleCentre.getPage(), null);
//
//            // 签章算法 可以自己实现
//            // 摘要算法
//            ExternalDigest digest = new BouncyCastleDigest();
//            // 签章算法
//            ExternalSignature signature = new PrivateKeySignature(privateKey, DigestAlgorithms.SHA1, null);
//            // 进行盖章操作 CMS高级电子签名(CAdES)的长效签名规范
//            MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);
//
//        }
//        // 写入输出流中
//        return result;
//
//    }
//
//    private static ByteArrayOutputStream getImage(String content) throws IOException {
//        /*
//         * Because font metrics is based on a graphics context, we need to create a
//         * small, temporary image so we can ascertain the width and height of the final
//         * image
//         */
//        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2d = img.createGraphics();
//        Font font = new Font("宋体", Font.PLAIN, 50);
//        g2d.setFont(font);
//        FontMetrics fm = g2d.getFontMetrics();
//        int width = fm.stringWidth(content);
//        int height = fm.getHeight();
//        g2d.dispose();
//
//        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//        g2d = img.createGraphics();
//        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
//        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
//        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
//        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
//        g2d.setFont(font);
//        fm = g2d.getFontMetrics();
//        g2d.setColor(Color.RED);
//        g2d.drawString(content, 0, fm.getAscent());
//        g2d.dispose();
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(img, "png", baos);
//        return baos;
//    }
//
//    public static void main(String[] args) throws IOException, DocumentException, GeneralSecurityException {
//        InputStream in = new FileInputStream("D://main.pdf");
//        String fileName  = "";
//        InputStream keyStoreStream = new FileInputStream(new File("D:/a.jpg"));
//        //InputStream keyStoreStreamNew = PdfSignUtil.class.getResourceAsStream("/template/client.p12");
//        String keyStorePassword = "654321";
//        String reason = "签订原因";
//        String location = "签订地址";
//        String chapterPath = "D:/a.jpg";
//        String chapterContent = "名称";
//        // 图片签章                                                                                                           
//        Image signImage = Image.getInstance(chapterPath);
//        // 文本签章                                                                                                           
//        Image signImageText = Image.getInstance(getImage(chapterContent).toByteArray());
//        // 首次签订                                                                                                           
//        ByteArrayOutputStream byteOut = sign(in, in, keyStorePassword, signImage, "DELL服务", reason, location);
//        in.close();
//        if (byteOut == null) {
//            return;
//        }
//        // 多次签订                                                                                                           
//        byteOut = sign(new ByteArrayInputStream(byteOut.toByteArray()), keyStoreStream, keyStorePassword,
//                signImageText, "乙方", reason, location);
//        if (byteOut == null) {
//            return;
//        }
//        try (OutputStream out = new FileOutputStream("ff.pdf")) {
//            out.write(byteOut.toByteArray());
//        }
//    }
//}
