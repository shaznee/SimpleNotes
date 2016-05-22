package com.shaznee.simplenotes.models;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Note {
	
	public static String KEY = "key" ,TEXT = "text";	
	private String key,text;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static Note getNew() {
		Locale locale = new Locale("en_US");
		Locale.setDefault(locale);
		
		String dateFormat = "yyyy-MM-dd HH:mm:ss Z";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		String key = formatter.format(new Date());
		
		Note note = new Note();
		note.setKey(key);
		note.setText("");
		return note;		
	}
	
	@Override
	public String toString() {
		return this.getText();
	}

}
