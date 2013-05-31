package com.wb.test;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
//An adapter to manage the imagebuttons in the CharacterChoiceAcivity
public class ImageButtonAdapter extends BaseAdapter {
	
	private Context context;
	private ImageButton[] imgs;
	//Creates a new adapter with the specified ImageButton array
	public ImageButtonAdapter(Context c, ImageButton[] buttons) {
		context = c;
		imgs = buttons;
	}
	
	@Override
	public int getCount() {
		return imgs.length;
	}

	@Override
	public Object getItem(int index) {
		return imgs[index];
	}

	@Override
	public long getItemId(int index) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parentView) {    
		ImageButton button;
        if (convertView == null) {           
            button = imgs[position];  
        } else {        
        	button = imgs[position];  
        }       
      return button; 
	}

}
