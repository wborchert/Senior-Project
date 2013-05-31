package com.wb.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageButton;
//ImageButton that can retrieve its character ID and Bitmap
public class UsefulImageButton extends ImageButton {
	
	private String id;
	private Bitmap drawing;

	public UsefulImageButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public UsefulImageButton(Context context, String newid, Bitmap map) {
		super(context);
		id = newid;
		drawing = map;
	}

	public UsefulImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public UsefulImageButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public UsefulImageButton(Context context, AttributeSet attrs, int defStyle, String newid) {
		super(context, attrs, defStyle);
		id = newid;
	}
	
	public String getCharId() {
		return id;
	}
	
	public Bitmap getMap() {
		return drawing;
	}
	
	public void setCharMap(Bitmap newDrawing) {
		drawing = newDrawing;
	}
	
	public void setCharId(String newId) {
		id = newId;
	}

}
