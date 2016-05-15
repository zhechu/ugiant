package com.ugiant.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jfinal.kit.StrKit;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {
	public static boolean compressImage(String srcImage,String desImage,boolean proportion,int outputWidth, int outputHeight){
		 try { 
			 //获得源文件 
			 File file = new File(srcImage); 
			 if (!file.exists()) { 
				 return false; 
			 } 
			 Image img = ImageIO.read(file); 
			 // 判断图片格式是否正确 
			 if (img.getWidth(null) == -1) {
				 System.out.println(" can't read,retry!" + "<BR>"); 
				 return false; 
			 } else { 
				 int newWidth; int newHeight; 
				 // 判断是否是等比缩放 
				 if (proportion) { 
					 // 为等比缩放计算输出的图片宽度及高度 
					 double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1; 
					 double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1; 
					 // 根据缩放比率大的进行缩放控制 
					 double rate = rate1 > rate2 ? rate1 : rate2; 
					 newWidth = (int) (((double) img.getWidth(null)) / rate); 
					 newHeight = (int) (((double) img.getHeight(null)) / rate); 
				 } else { 
					 newWidth = outputWidth; // 输出的图片宽度 
					 newHeight = outputHeight; // 输出的图片高度 
				 } 
			 	BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB); 
			 	
			 	/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
				 * 优先级比速度高 生成的图片质量比较好 但速度慢
				 */ 
			 	tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
			 	FileOutputStream out = new FileOutputStream(desImage);
			 	// JPEGImageEncoder可适用于其他图片类型的转换 
			 	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out); 
			 	encoder.encode(tag); 
			 	out.close(); 
			 } 
		 } catch (IOException ex) { 
			 ex.printStackTrace(); 
		 } 
		 return true; 
	}
	
	public static String getUrlPath(String baseUrl,String absUrl){
		if(StrKit.notBlank(baseUrl,absUrl)){
			return baseUrl.concat(absUrl);
		}
		return "";
	}
}
