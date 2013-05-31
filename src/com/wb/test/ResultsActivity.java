package com.wb.test;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
//Displays the results of a quiz
public class ResultsActivity extends Activity {

	private ArrayList<Question> questions;
	private int index = 0;
	private String[] chars;
	private String[] onyomi;
	private String[] kunyomi;
	private boolean[] modes;
	private String type;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        questions = (ArrayList<Question>) bundle.get("Results");
    	chars = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "hundred", "thousand", "up_top_above", "down_bottom_below", "left", "right", "inside_middle", "large", "small", "month_moon", "day_sun", "year", "early", "tree", "woods", "mountain", "river", "soil", "sky", "ricepaddy", "heaven", "life", "flower", "grass", "insect", "dog", "person", "name", "female", "male", "child", "eye", "ear", "mouth", "hand", "foot", "see", "sound"};
    	onyomi = new String[]{"イチ_イツ", "ニ_ジ", "サン", "シ", "ゴ", "ロク", "シチ", "ハチ", "ク_キュー", "ジュー", "ヒャク", "セン", "ジョー", "カ_ゲ", "サ", "ウ_ユー", "チュー_ジュー", "ダイ_タイ", "ジョー", "ガツ_ケツ", "ニチ_ギツ", "ネン", "ソー_サツ", "モク_ボク", "リン", "サン_ザン", "セン", "ド_ト", "クー", "デン", "テン", "セイ_ジョー", "カ", "ソー", "チュー", "ケン", "ジン_ニン", "メイ_ミョー", "ジョ_ニョー", "ダン_ナン", "シ_ス", "モク", "ジ_ニ", "コー", "ジュ", "ソク", "ケン_ゲン", "オン"};
    	kunyomi = new String[]{"ひとつ", "ふたつ", "みっつ", "よん_よっつ", "いつつ", "むっつ", "ななつ", "やっつ", "ここのつ", "とう", "もも", "ち", "うえ", "した_しも_もと", "ひだり", "みぎ", "なか", "おう(きい)", "ちい(さい)_こ_お", "つき", "ひ_か", "とし", "はや(い)", "き", "はやし", "やま", "かわ", "つち", "そら_あ(く)_から", "だ_た", "あめ_あま", "い(きる)_う(む)_なま", "はな", "くさ", "むし", "いぬ", "ひと", "な", "おんな", "おとこ", "こ", "め", "みみ", "くち", "て", "あし", "みる", "ね_おと"};
    	modes = intent.getBooleanArrayExtra(QuizTypeMenuActivity.modes);
        type = intent.getStringExtra(QuizTypeMenuActivity.type);
    	setUpLayout(questions.get(index));
    }
	
	public void setUpLayout(Question question) {
		int type = question.getType();
		switch(type) {
		case 1: setContentView(R.layout.activity_results1);
			setUpModeOne(question);
			break;
		case 2: setContentView(R.layout.activity_results2);
			setUpModeTwo(question);
			break;
		case 3: setContentView(R.layout.activity_results3);
			setUpModeThree(question);
			break;
		}
	}
	
	public void setUpModeOne(Question question) {
		
	}
	
	public void setUpModeTwo(Question question) {
		Button one = (Button)findViewById(R.id.option1);
		Button two = (Button)findViewById(R.id.option2);
		Button three = (Button)findViewById(R.id.option3);
		Button four = (Button)findViewById(R.id.option4);
		ImageView q = (ImageView)findViewById(R.id.question);
		TextView question_counter = (TextView)findViewById(R.id.question_counter);
		question_counter.setText("Question: " + (index + 1) + " of " + questions.size());
		q.setImageBitmap(getBitmapOf(question.getQuestion()));
		String answer = question.getQuestion();
		String chosen = question.getChosen();
		String[] choices = question.getChoices();
		Button[] buttons = new Button[]{one, two, three, four};
		for(int i = 0; i < choices.length; i++) {
			buttons[i].setText(convertString(choices[i]));
			Log.v("Results", "choice " + choices[i]);
			Log.v("Results", "answer " + answer);
			Log.v("Results", "chosen " + chosen);
			if(compareAcrossLanguage(answer, choices[i])) {
				buttons[i].setBackgroundColor(Color.GREEN);
			}
			else if(choices[i].equals(chosen) && !(compareAcrossLanguage(answer, chosen))) {
				buttons[i].setBackgroundColor(Color.RED);
			}
		}
	}

	public void setUpModeThree(Question question) {
		UsefulImageButton one = (UsefulImageButton)findViewById(R.id.option1);
		UsefulImageButton two = (UsefulImageButton)findViewById(R.id.option2);
		UsefulImageButton three = (UsefulImageButton)findViewById(R.id.option3);
		UsefulImageButton four = (UsefulImageButton)findViewById(R.id.option4);
		TextView question_counter = (TextView)findViewById(R.id.question_counter);
		question_counter.setText("Question: " + (index + 1) + " of " + questions.size());
		TextView q = (TextView)findViewById(R.id.question);
		q.setText(convertString(question.getQuestion()));
		String answer = question.getQuestion();
		String chosen = question.getChosen();
		String[] choices = question.getChoices();
		UsefulImageButton[] buttons = new UsefulImageButton[]{one, two, three, four};
		for(int i = 0; i < choices.length; i++) {
			buttons[i].setImageBitmap(getBitmapOf(choices[i]));
			buttons[i].setCharId(choices[i]);
			buttons[i].setCharMap(getBitmapOf(choices[i]));
			if(compareAcrossLanguage(choices[i], answer)) {
				buttons[i].setBackgroundColor(Color.GREEN);
			}
			else if(choices[i].equals(chosen) && !(compareAcrossLanguage(chosen, answer))) {
				buttons[i].setBackgroundColor(Color.RED);
			}
		}
	}
	//Moves to the next question if there is one
	public void next(View view) {
		if(index < questions.size() - 1) {
			index++;
			setUpLayout(questions.get(index));
		}
	}
	//Moves to the previous question if there is one
	public void previous(View view) {
		if(index > 0) {
			index--;
			setUpLayout(questions.get(index));
		}
	}
	//Gets the bitmap of a String id
	public Bitmap getBitmapOf(String s) {
		int ID = getResources().getIdentifier(s.toLowerCase(Locale.getDefault()), "drawable", getPackageName());
		Bitmap map = BitmapFactory.decodeResource(getResources(), ID);
		map = Bitmap.createScaledBitmap(map, 150, 150, false);
		return map;
	}
	
	public String convertString(String s) {
		s = s.replace("_", ", ");
		return s;
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
		} else if(item.getItemId() == R.id.retry) {
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
    
    public boolean compareAcrossLanguage(String compare, String answer) {
    	int index = 0;
    	for(int i = 0; i < chars.length; i++) {
    		if(chars[i].equals(compare)) {
    			index = i;
    		}
    	}
    	if(answer.equals(onyomi[index].replace("_", ", ")) || answer.equals(kunyomi[index].replace("_", ", ")) || answer.equals(convertString(compare))) {
    		return true;
    	}
    	return false;
    }
    
    public void retry() {
    	Intent intent = new Intent(this, QuizActivity.class);
    	intent.putExtra(QuizTypeMenuActivity.modes, modes);
		intent.putExtra(QuizTypeMenuActivity.type, type);
		startActivity(intent);
    }
}
