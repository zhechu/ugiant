package com.ugiant.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jfinal.kit.StrKit;

public final class FormatUtil {
	
	public static String formatHtml(String html){
		if(StrKit.notBlank(html)){
			html = html.replaceAll("&", "&amp;");
			html = html.replaceAll("<", "&lt;");
			html = html.replaceAll(">", "&gt;");
		}
		return html;
	}
	
	public static String deFormatHtml(String html){
		if(StrKit.notBlank(html)){
			
			html = html.replaceAll("&lt;","<");
			html = html.replaceAll("&gt;",">");
			html = html.replaceAll("&amp;","&");
		}
		return html;
	}
	
	public static String formatDate(Date date , String format){
		if(date != null && format != null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else{
			return null;
		}

	}
	
	public static Date formatStringToDate(String dateStr, String format) throws ParseException{
		if(dateStr != null && format != null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateStr);
		}else{
			return null;
		}
		
	}
	
	public static Date formatDate2(Date date,String format) throws ParseException{
		if(date != null && format != null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(sdf.format(date));
		}else{
			return null;
		}
	}
}
