package com.wb.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//Activity displaying the results of a practice
public class LoadedImageActivity extends Activity {
	
	private Bitmap bitmap;
	private Bitmap answer;
	private Bitmap correct;
	private Bitmap incorrect;
	private Paint p;
	private boolean isCorrect;
	private Character c;
	private String language;
	public final static String CHAR = "com.wb.test.CHAR";
	public final static String LANG = "com.wb.test.LANG";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
		bitmap = loadBitmap();
		Intent intent = getIntent();
		language = intent.getStringExtra("com.wb.test.LANG");
		correct = BitmapFactory.decodeResource(getResources(), R.drawable.correct);
		incorrect = BitmapFactory.decodeResource(getResources(), R.drawable.incorrect);
		isCorrect = intent.getBooleanExtra(DrawScreenActivity.CORRECT, false);
		c = intent.getParcelableExtra(DrawScreenActivity.ANSWER);
		answer = c.getDrawing();
		answer = Bitmap.createScaledBitmap(answer, 250, 250, false);
		bitmap = Bitmap.createScaledBitmap(bitmap, 250, 250, false);
		p = new Paint();
		p.setAlpha(127);
		int[] drawnPixels = new int[bitmap.getWidth() * bitmap.getHeight()];
		bitmap.getPixels(drawnPixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
		int[] answerPixels = new int[answer.getWidth() * answer.getHeight()];
		answer.getPixels(answerPixels, 0, answer.getWidth(), 0, 0, answer.getWidth(), answer.getHeight());
		setContentView(new myView(this));
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.loaded_image, menu);
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
		} else if (item.getItemId() == R.id.retry) {
			retry();
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
	
	public Bitmap loadBitmap() {
		String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/storedimage.png";
		Bitmap temp = BitmapFactory.decodeFile(file_path);

		return temp;
	}
	
	public void retry() {
		Intent intent = new Intent(this, DrawScreenActivity.class);
		intent.putExtra(CHAR, c);
		intent.putExtra(LANG, language);
		startActivity(intent);
	}
	
	//Handles the graphics for the display
	private class myView extends View{

		 public myView(Context context) {
		  super(context);
		  // TODO Auto-generated constructor stub
		 }

		 @Override
		 protected void onDraw(Canvas canvas) {
		  // TODO Auto-generated method stub
			 canvas.drawBitmap(answer, getWidth()/2 - answer.getWidth()/2, 2*(getHeight()/5) - answer.getHeight()/2, p);
			 canvas.drawBitmap(bitmap, getWidth()/2 - answer.getWidth()/2, 4*(getHeight()/5) - answer.getHeight()/2, null);
			 if(isCorrect) {
				 canvas.drawBitmap(correct, 330, 0, null);
			 }
			 else {
				 canvas.drawBitmap(incorrect, 330, 0, null);
			 }
		}
	}
}
