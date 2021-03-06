//package com.example.demo.utils;
//
//import com.itextpdf.text.Image;
//import com.itextpdf.text.Rectangle;
//
///**                                                                                                                                       
//  * 匹配关键字的中心                                                                                                                               
//  *                                                                                                                                        
//  * @author caijunjun                                                                                                                      
//  * @date 2018年7月10日                                                                                                                       
//  */
//public class RectangleCentre {
//    private int page;
//    private float centreX;
//    private float centreY;
//    public RectangleCentre() {
//        super();
//    }
//    public RectangleCentre(int page) {
//        super();
//        this.page = page;
//    }
//    public RectangleCentre copy() {
//        return new RectangleCentre(this.page);
//    }
//    public int getPage() {
//        return page;
//    }
//    public void setPage(int page) {
//        this.page = page;
//    }
//    public float getCentreX() {
//        return centreX;
//    }
//    public void setCentreX(float centreX) {
//        this.centreX = centreX;
//    }
//    public float getCentreY() {
//        return centreY;
//    }
//    public void setCentreY(float centreY) {
//        this.centreY = centreY;
//    }
//    @Override
//    public String toString() {
//        return "RectangleText [page=" + page + ", centreX=" + centreX + ", centreY=" + centreY + "]";
//    }
//    public Rectangle getRectangle(Image signImage) {
//        //自己可以调整图片的大小                                                                                                                     
//        float halfWith = signImage.getWidth() / 3;
//    float halfHeight = signImage.getHeight() / 3;
//        return new Rectangle(this.centreX - halfWith, this.centreY - halfHeight, this.centreX + halfWith,
//        this.centreY + halfHeight);
//    }
//}
