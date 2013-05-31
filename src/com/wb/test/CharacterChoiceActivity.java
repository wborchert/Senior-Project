package com.wb.test;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

public class CharacterChoiceActivity extends Activity {
	
	private ImageButton[] imgs;
	private GridView grid;
	private CharacterChoiceActivity activity;
	private String[] chars;
	private int[] counts;
	public static final String myChar = "com.wb.test.CHAR";
	public static final String myLang = "com.wb.test.LANG";
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
		
		activity = this;
		Intent intent = getIntent();
		String message = intent.getStringExtra(TypeMenuActivity.type);
		//Sets up a grid of hiragana characters to be chosen from
		if(message.equals("hiragana")) {
			chars = new String[]{"A", "I", "U", "E", "O", "KA", "KI", "KU", "KE", "KO", "SA", "SHI", "SU", "SE", "SO", "TA", "CHI", "TSU", "TE", "TO", "NA", "NI", "NU", "NE", "NO", "HA", "HI", "FU", "HE", "HO", "MA", "MI", "MU", "ME", "MO", "YA", "YU", "YO", "RA", "RI", "RU", "RE", "RO", "WA", "WO", "N"};
			counts = new int[]{3, 2, 2, 2, 3, 3, 4, 1, 3, 2, 3, 1, 2, 3, 1, 3, 2, 1, 1, 2, 4, 3, 2, 2, 1, 3, 1, 4, 1, 4, 3, 2, 3, 2, 3, 3, 2, 2, 2, 2, 1, 2, 1, 2, 3, 1};
			imgs = new ImageButton[chars.length];
			final String language = message;
			for(int i = 0; i < chars.length; i++) {
				int ID = getResources().getIdentifier(chars[i].toLowerCase(Locale.getDefault()), "drawable", getPackageName());
				Bitmap map = BitmapFactory.decodeResource(getResources(), ID);
				map = Bitmap.createScaledBitmap(map, 75, 75, false);
				imgs[i] = new UsefulImageButton(this, chars[i], map);
				imgs[i].setImageBitmap(map);
				imgs[i].setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						UsefulImageButton button = (UsefulImageButton)v;
                    	Intent intent = new Intent(activity, DrawScreenActivity.class);
                    	intent.putExtra(myChar, new Character(button.getCharId(), counts[getIndexOf(button.getCharId())], button.getMap()));
                    	intent.putExtra(myLang, language);
                    	startActivity(intent);
                    }
				});
			}
		}
		//Sets up a grid of katakana characters to be chosen from
		else if(message.equals("katakana")) {
			chars = new String[]{"A_KATA", "I_KATA", "U_KATA", "E_KATA", "O_KATA", "KA_KATA", "KI_KATA", "KU_KATA", "KE_KATA", "KO_KATA", "SA_KATA", "SHI_KATA", "SU_KATA", "SE_KATA", "SO_KATA", "TA_KATA", "CHI_KATA", "TSU_KATA", "TE_KATA", "TO_KATA", "NA_KATA", "NI_KATA", "NU_KATA", "NE_KATA", "NO_KATA", "HA_KATA", "HI_KATA", "FU_KATA", "HE_KATA", "HO_KATA", "MA_KATA", "MI_KATA", "MU_KATA", "ME_KATA", "MO_KATA", "YA_KATA", "YU_KATA", "YO_KATA", "RA_KATA", "RI_KATA", "RU_KATA", "RE_KATA", "RO_KATA", "WA_KATA", "WO_KATA", "N_KATA"};
			counts = new int[]{2, 2, 3, 3, 3, 2, 3, 2, 3, 2, 3, 3, 2, 2, 2, 3, 3, 3, 3, 2, 2, 2, 2, 4, 1, 2, 2, 1, 1, 4, 2, 3, 2, 2, 3, 2, 2, 3, 2, 2, 2, 1, 3, 2, 3, 2};
			imgs = new ImageButton[chars.length];
			final String language = message;
			for(int i = 0; i < chars.length; i++) {
				int ID = getResources().getIdentifier(chars[i].toLowerCase(Locale.getDefault()), "drawable", getPackageName());
				Bitmap map = BitmapFactory.decodeResource(getResources(), ID);
				map = Bitmap.createScaledBitmap(map, 75, 75, false);
				imgs[i] = new UsefulImageButton(this, chars[i], map);
				imgs[i].setImageBitmap(map);
				imgs[i].setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						UsefulImageButton button = (UsefulImageButton)v;
                    	Intent intent = new Intent(activity, DrawScreenActivity.class);
                    	intent.putExtra(myChar, new Character(button.getCharId(), counts[getIndexOf(button.getCharId())], button.getMap()));
                    	intent.putExtra(myLang, language);
                    	startActivity(intent);
                    }
				});
			}
		}
		//Sets up a grid of kanji characters to be chosen from
		else if(message.equals("kanji")) {
			chars = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "hundred", "thousand", "up_top_above", "down_bottom_below", "left", "right", "inside_middle", "large", "small", "month_moon", "day_sun", "year", "early", "tree", "woods", "mountain", "river", "soil", "sky", "ricepaddy", "heaven", "life", "flower", "grass", "insect", "dog", "person", "name", "female", "male", "child", "eye", "ear", "mouth", "hand", "foot", "see", "sound"};
			counts = new int[]{1, 2, 3, 4, 5, 4, 2, 2, 2, 2, 6, 3, 3, 3, 5, 5, 4, 3, 3, 4, 4, 6, 6, 4, 8, 3, 3, 3, 8, 5, 4, 5, 7, 9, 6, 4, 2, 6, 3, 7, 3, 5, 6, 3, 4, 7, 7, 9};
			imgs = new ImageButton[chars.length];
			final String language = message;
			for(int i = 0; i < chars.length; i++) {
				int ID = getResources().getIdentifier(chars[i], "drawable", getPackageName());
				Bitmap map = BitmapFactory.decodeResource(getResources(), ID);
				map = Bitmap.createScaledBitmap(map, 75, 75, false);
				imgs[i] = new UsefulImageButton(this, chars[i], map);
				imgs[i].setImageBitmap(map);
				imgs[i].setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						UsefulImageButton button = (UsefulImageButton)v;
                    	Intent intent = new Intent(activity, DrawScreenActivity.class);
                    	intent.putExtra(myChar, new Character(button.getCharId(), counts[getIndexOf(button.getCharId())], button.getMap()));
                    	intent.putExtra(myLang, language);
                    	startActivity(intent);
                    }
				});
			}
		}
		setContentView(R.layout.activity_character_choice);
		grid = (GridView) findViewById(R.id.charView);
	    ImageButtonAdapter adapter = new ImageButtonAdapter(this, imgs);
	    grid.setAdapter(adapter);
		setContentView(grid);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		} else if (item.getItemId() == R.id.practice) {
			enterDrawingMode();
			return true;
		} else if (item.getItemId() == R.id.quiz) {
			enterQuizMode();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    
    public void enterQuizMode() {
    	Intent intent = new Intent(this, QuizTypeMenuActivity.class);
    	startActivity(intent);
    }
    
    public void enterDrawingMode() {
    	Intent intent = new Intent(this, TypeMenuActivity.class);
    	startActivity(intent);
    }
	
	public int getIndexOf(String s) {
		for(int i = 0; i < chars.length; i++) {
			if(chars[i].equals(s)) {
				return i;
			}
		}
		return -1;
	}
}

