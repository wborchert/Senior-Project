package com.wb.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
//The menu for choosing a language for practice
public class TypeMenuActivity extends Activity {
	
	public final static String type = "com.wb.test.TYPE";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_menu);
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
    
    public void hiragana(View view) {
    	Intent intent = new Intent(this, CharacterChoiceActivity.class);
    	String message = "hiragana";
    	intent.putExtra(type, message);
    	startActivity(intent);
    }
    
    public void katakana(View view) {
    	Intent intent = new Intent(this, CharacterChoiceActivity.class);
    	String message = "katakana";
    	intent.putExtra(type, message);
    	startActivity(intent);
    }
    
    public void kanji(View view) {
    	Intent intent = new Intent(this, CharacterChoiceActivity.class);
    	String message = "kanji";
    	intent.putExtra(type, message);
    	startActivity(intent);
    }
    
    
}
