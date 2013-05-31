package com.wb.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
//Activity containing the menu with which the user determines the types of questions on the quiz
public class QuizTypeMenuActivity extends TypeMenuActivity {

	public final static String type = "com.wb.test.TYPE";
	public final static String modes = "com.wb.test.MODES";
	private boolean japToEng = false;
	private boolean engToJap = false;
	private boolean drawChars = false;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_type_menu);
    }
	
	//Starts a quiz using hiragana
	@Override
	public void hiragana(View view) {
		if(japToEng != false || engToJap != false || drawChars != false) {
	    	Intent intent = new Intent(this, QuizActivity.class);
	    	String message = "hiragana";
	    	intent.putExtra(type, message);
	    	boolean[] vals = new boolean[]{engToJap, japToEng, drawChars};
	    	intent.putExtra(modes, vals);
	    	startActivity(intent);
		}
    }
    
	//Starts a quiz using katakana
	@Override
    public void katakana(View view) {
		if(japToEng != false || engToJap != false || drawChars != false) {
	    	Intent intent = new Intent(this, QuizActivity.class);
	    	String message = "katakana";
	    	intent.putExtra(type, message);
	    	boolean[] vals = new boolean[]{engToJap, japToEng, drawChars};
	    	intent.putExtra(modes, vals);
	    	startActivity(intent);
		}
    }
    //Starts a quiz using kanji
	@Override
    public void kanji(View view) {
		if(japToEng != false || engToJap != false || drawChars != false) {
	    	Intent intent = new Intent(this, QuizActivity.class);
	    	String message = "kanji";
	    	intent.putExtra(type, message);
	    	boolean[] vals = new boolean[]{engToJap, japToEng, drawChars};
	    	intent.putExtra(modes, vals);
	    	startActivity(intent);
		}
    }
	//Handles the checkboxes for extra settings
	public void onCheckboxClicked(View view) {
		CheckBox box = (CheckBox) view;
		String s = (String)box.getText();
		if(s.equals("Enter Japanese Characters For English Names")) {
			engToJap = !engToJap;
		}
		else if(s.equals("Enter English Names For Japanese Characters")) {
			japToEng = !japToEng;
		}
		else {
			drawChars = !drawChars;
		}
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
}
