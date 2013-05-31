package com.wb.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
//Activity holding the drawingview for practice
public class DrawScreenActivity extends Activity implements OnTouchListener{

	private DrawingView drawingView;
	public final static String ANSWER = "com.wb.test.ANSWER";
	public final static String CORRECT = "com.wb.test.CORRECT";
	public final static String LANG = "com.wb.test.LANG";
	private int stroke_count;
	private String language;
	private TextView strokeCounter;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
		 setContentView(R.layout.activity_draw_screen);
		 drawingView = (DrawingView)findViewById(R.id.drawing_view);
		 drawingView.setOnTouchListener(this);
		 strokeCounter = (TextView)findViewById(R.id.stroke_counter);
		 Intent intent = getIntent();
		 Character currentChar = intent.getParcelableExtra("com.wb.test.CHAR");
		 drawingView.setCurrentChar(currentChar);
		 language = intent.getStringExtra("com.wb.test.LANG");
		 drawingView.setLanguage(language);
		 stroke_count = drawingView.getCurrent().getStrokeCount();
		 strokeCounter.setText("Strokes Left: " + stroke_count);
	}
	//Determines what to do when the screen is touched
	public boolean onTouch(View v, MotionEvent e) {
		
		switch(e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			drawingView.pushPoint(e.getX(), e.getY());
			break;
		case MotionEvent.ACTION_DOWN:
			drawingView.newLine();
			onResume();
			break;
		case MotionEvent.ACTION_UP:
			drawingView.pushLine();
			drawingView.check();
			if(stroke_count > 0) {
				stroke_count--;
				strokeCounter.setText("Strokes Left: " + stroke_count);
			}
			onResume();
			break;
		}
		return true;
	}
	//Pauses the drawingview
	public void onPause() {
		super.onPause();
		drawingView.pause();
	}
	//Restarts the drawingview
	public void onResume() {
		super.onResume();
		drawingView.resume();
	}
	//Checks the drawn image with the template and starts a LoadedImageActivity to display the results
	public void check() {
		drawingView.save();
		Intent intent = new Intent(this, LoadedImageActivity.class);
		Character answer = drawingView.getCurrent();
		intent.putExtra(ANSWER, answer);
		intent.putExtra(CORRECT, (drawingView.check() && drawingView.pixelCheck()));
		intent.putExtra(LANG, language);
    	startActivity(intent);
	}
	//Clears the drawingview and restarts the stroke count
	public void clear() {
		drawingView.clear();
		stroke_count = drawingView.getCurrent().getStrokeCount();
		strokeCounter.setText("Strokes Left: " + stroke_count);
		onResume();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.draw_screen, menu);
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
		} else if (item.getItemId() == R.id.clear) {
			clear();
			onResume();
			return true;
		} else if (item.getItemId() == R.id.check) {
			check();
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
