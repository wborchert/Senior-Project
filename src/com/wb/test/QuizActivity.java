package com.wb.test;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

//The activity that manages the quiz
public class QuizActivity extends Activity implements OnTouchListener {
	
	private String type;
	private ArrayList<Integer> modes;
	private String[] chars;
	private int[] counts;
	private String[] onyomi;
	private String[] kunyomi;
	private QuizDrawingView drawingView;
	private Activity activity = this;
	private int currentmode;
	private Character answer;
	private ArrayList<Question> questions;
	private int numQuestions = 10;
	private int questionCounter;
	private ArrayList<Boolean> selectedModes;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modes = new ArrayList<Integer>();
        questions = new ArrayList<Question>();
        Intent intent = getIntent();
        type = intent.getStringExtra(QuizTypeMenuActivity.type);
        boolean[] m = intent.getBooleanArrayExtra(QuizTypeMenuActivity.modes);
        selectedModes = new ArrayList<Boolean>();
        if(type.equals("hiragana")) {
        	chars = new String[]{"A", "I", "U", "E", "O", "KA", "KI", "KU", "KE", "KO", "SA", "SHI", "SU", "SE", "SO", "TA", "CHI", "TSU", "TE", "TO", "NA", "NI", "NU", "NE", "NO", "HA", "HI", "FU", "HE", "HO", "MA", "MI", "MU", "ME", "MO", "YA", "YU", "YO", "RA", "RI", "RU", "RE", "RO", "WA", "WO", "N"};
     		counts = new int[]{3, 2, 2, 2, 3, 3, 4, 1, 3, 2, 3, 1, 2, 3, 1, 3, 2, 1, 1, 2, 4, 3, 2, 2, 1, 3, 1, 4, 1, 4, 3, 2, 3, 2, 3, 3, 2, 2, 2, 2, 1, 2, 1, 2, 3, 1};
        }
        else if(type.equals("katakana")) {
        	chars = new String[]{"A", "I", "U", "E", "O", "KA", "KI", "KU", "KE", "KO", "SA", "SHI", "SU", "SE", "SO", "TA", "CHI", "TSU", "TE", "TO", "NA", "NI", "NU", "NE", "NO", "HA", "HI", "FU", "HE", "HO", "MA", "MI", "MU", "ME", "MO", "YA", "YU", "YO", "RA", "RI", "RU", "RE", "RO", "WA", "WO", "N"};
			counts = new int[]{2, 2, 3, 3, 3, 2, 3, 2, 3, 2, 3, 3, 2, 2, 2, 3, 3, 3, 3, 2, 2, 2, 2, 4, 1, 2, 2, 1, 1, 4, 2, 3, 2, 2, 3, 2, 2, 3, 2, 2, 2, 1, 3, 2, 3, 2};
        }
        else if(type.equals("kanji")) {
        	chars = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "hundred", "thousand", "up_top_above", "down_bottom_below", "left", "right", "inside_middle", "large", "small", "month_moon", "day_sun", "year", "early", "tree", "woods", "mountain", "river", "soil", "sky", "ricepaddy", "heaven", "life", "flower", "grass", "insect", "dog", "person", "name", "female", "male", "child", "eye", "ear", "mouth", "hand", "foot", "see", "sound"};
			counts = new int[]{1, 2, 3, 4, 5, 4, 2, 2, 2, 2, 6, 3, 3, 3, 5, 5, 4, 3, 3, 4, 4, 6, 6, 4, 8, 3, 3, 3, 8, 5, 4, 5, 7, 9, 6, 4, 2, 6, 3, 7, 3, 5, 6, 3, 4, 7, 7, 9};
	    	onyomi = new String[]{"イチ_イツ", "ニ_ジ", "サン", "シ", "ゴ", "ロク", "シチ", "ハチ", "ク_キュー", "ジュー", "ヒャク", "セン", "ジョー", "カ_ゲ", "サ", "ウ_ユー", "チュー_ジュー", "ダイ_タイ", "ジョー", "ガツ_ケツ", "ニチ_ギツ", "ネン", "ソー_サツ", "モク_ボク", "リン", "サン_ザン", "セン", "ド_ト", "クー", "デン", "テン", "セイ_ジョー", "カ", "ソー", "チュー", "ケン", "ジン_ニン", "メイ_ミョー", "ジョ_ニョー", "ダン_ナン", "シ_ス", "モク", "ジ_ニ", "コー", "ジュ", "ソク", "ケン_ゲン", "オン"};
        	kunyomi = new String[]{"ひとつ", "ふたつ", "みっつ", "よん_よっつ", "いつつ", "むっつ", "ななつ", "やっつ", "ここのつ", "とう", "もも", "ち", "うえ", "した_しも_もと", "ひだり", "みぎ", "なか", "おう(きい)", "ちい(さい)_こ_お", "つき", "ひ_か", "とし", "はや(い)", "き", "はやし", "やま", "かわ", "つち", "そら_あ(く)_から", "だ_た", "あめ_あま", "い(きる)_う(む)_なま", "はな", "くさ", "むし", "いぬ", "ひと", "な", "おんな", "おとこ", "こ", "め", "みみ", "くち", "て", "あし", "みる", "ね_おと"};
        }
        
        for(int i = 0; i < m.length; i++) {
        	selectedModes.add(Boolean.valueOf(m[i]));
        }
        for(int j = 0; j < selectedModes.size(); j++) {
        	if(selectedModes.get(j).equals(Boolean.valueOf(false))) {
        		modes.add(Integer.valueOf(-1));
        	}
        	else {
        		modes.add(Integer.valueOf(j));
        	}
        }
        setUpMode();
    }
	//Chooses a random mode from the ones selected by the user on the QuizTypeMenuActivity
	public int selectMode() {
		if(questions.size() < numQuestions) {	
			int i = (int) (Math.random() * modes.size());
			if(modes.get(i) == Integer.valueOf(-1)) {
				i = selectMode();
			}
		return i;
		}
		else {
			moveToResults();
		}
		return -1;
	}
	//Sets up mode one: Drawing
	public void setUpModeOne() {
        drawingView = (QuizDrawingView)findViewById(R.id.drawing_view_quiz);
		drawingView.setCurrentChar(pickRandomChar());
		drawingView.setOnTouchListener(this);
		TextView textView = (TextView)findViewById(R.id.char_to_draw);
		textView.setText(drawingView.getCurrent().getName());
		currentmode = 1;
		onResume();
	}
	//Sets up mode two: Multiple choice with text answers and an image question
	public void setUpModeTwo() {
		String type = "";
		String[] choices = new String[4];
		TextView question_counter = (TextView)findViewById(R.id.question_counter);
		question_counter.setText("Question: " + (questionCounter + 1) + " of " + numQuestions);
		Button one = (Button)findViewById(R.id.option1);
		Button two = (Button)findViewById(R.id.option2);
		Button three = (Button)findViewById(R.id.option3);
		Button four = (Button)findViewById(R.id.option4);
		ImageView question = (ImageView)findViewById(R.id.question);
		answer = pickRandomChar();
		answer = removeDupesAndReplace(answer);
		question.setImageBitmap(answer.getDrawing());
		Button[] answers = new Button[]{one, two, three, four};
		if(type.equals("kanji")) {
			int j = (int) (Math.random() * 3);
			switch(j) {
			case 0: type = "name";
				break;
			case 1: type = "onyomi";
				break;
			case 2: type = "kunyomi";
				break;
			}
		}
		for(int i = 0; i < answers.length; i++) {
			Character c = pickRandomChar();
			if(type.equals("kanji")) {
				if(type.equals("name")) {
					answers[i].setText(convertString(c.getName()));
				}
				else if(type.equals("onyomi")) {
					answers[i].setText(convertString(c.getOnyomi()));
				}
				else if(type.equals("kunyomi")) {
					answers[i].setText(convertString(c.getKunyomi()));
				}
			}
			else {
				answers[i].setText(convertString(c.getName()));
			}
			choices[i] = c.getName();
			answers[i].setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Button button = (Button)v;
					checkAnswer((String)button.getText());
                }
			});
		}
		answers = ensureNoDuplicates(answers);
		int r = (int)(Math.random() * answers.length);
		answers[r].setText(convertString(answer.getName()));
		if(type.equals("name")) {
			answers[r].setText(convertString(answer.getName()));
		}
		else if(type.equals("onyomi")) {
			answers[r].setText(convertString(answer.getOnyomi()));
		}
		else if(type.equals("kunyomi")) {
			answers[r].setText(convertString(answer.getKunyomi()));
		}
		for(int k = 0; k < choices.length; k++) {
			choices[k] = (String)answers[k].getText();
		}
		currentmode = 2;
		questions.add(new Question(choices, answer.getName(), currentmode));
	}
	//Sets up mode three: Multiple choice with image answers and a text question
	public void setUpModeThree() {
		String[] choices = new String[4];
		TextView question_counter = (TextView)findViewById(R.id.question_counter);
		question_counter.setText("Question: " + (questionCounter + 1) + " of " + numQuestions);
		UsefulImageButton one = (UsefulImageButton)findViewById(R.id.option1);
		UsefulImageButton two = (UsefulImageButton)findViewById(R.id.option2);
		UsefulImageButton three = (UsefulImageButton)findViewById(R.id.option3);
		UsefulImageButton four = (UsefulImageButton)findViewById(R.id.option4);
		TextView question = (TextView)findViewById(R.id.question);
		answer = pickRandomChar();
		answer = removeDupesAndReplace(answer);
		if(type.equals("kanji")) {
			int i = (int) (Math.random() * 3);
			switch(i) {
			case 0: question.setText(convertString(answer.getName()));
				break;
			case 1: question.setText(convertString(answer.getOnyomi()));
				break;
			case 2: question.setText(convertString(answer.getKunyomi()));
				break;
			}
		}
		else {
			question.setText(convertString(answer.getName()));
		}
		UsefulImageButton[] answers = new UsefulImageButton[]{one, two, three, four};
		for(int i = 0; i < answers.length; i++) {
			Character c = pickRandomChar();
			choices[i] = c.getName();
			answers[i].setCharMap(c.getDrawing());
			answers[i].setImageBitmap(Bitmap.createScaledBitmap(c.getDrawing(), 150, 150,false));
			answers[i].setCharId(c.getName());
			answers[i].setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					UsefulImageButton button = (UsefulImageButton)v;
					checkAnswer(button.getCharId());
                }
			});
		}
		answers = ensureNoDuplicates(answers);
		for(int i = 0; i < answers.length; i++) {
			choices[i] = answers[i].getCharId();
		}
		int r = (int)(Math.random() * answers.length);
		answers[r].setImageBitmap(Bitmap.createScaledBitmap(answer.getDrawing(), 150, 150,false));
		answers[r].setCharId((String)answer.getName());
		choices[r] = (String)answer.getName();
		currentmode = 3;
		questions.add(new Question(choices, (String)question.getText(), currentmode));
	}
	//Handles touch events for the first mode
	public boolean onTouch(View v, MotionEvent e) {
		if(currentmode == 1) {
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
				if(drawingView.check()) {
					drawingView.save();
					questions.add(new Question(drawingView.getCurrent().getName(), drawingView.load(), drawingView.getCurrentChar(), currentmode));
				}
				onResume();
				break;
			}
		}
		return true;
	}
	
	public void onPause() {
		super.onPause();
		if(currentmode == 1) {
			drawingView.pause();
		}
	}
	
	public void onResume() {
		super.onResume();
		if(currentmode == 1) {
			drawingView.resume();
		}
	}
	
	public void clear(View view) {
		drawingView.clear();
		onResume();
	}
	//Sets up the selected mode
	public void setUpMode() {
		int selectedMode = selectMode();
        switch(selectedMode) {
        	case 0:  
        		setContentView(R.layout.activity_quiz3);
        		setUpModeThree();
        		break;
        	case 1:	       
        		setContentView(R.layout.activity_quiz2);
        		setUpModeTwo();
        		break;
        	case 2:	
        		setContentView(R.layout.activity_quiz1);
        		setUpModeOne();
        		break;
        	case 3: //setContentView(R.layout.activity_quiz4);
        		break;
        }
	}
	//Forfeits the current question and moves on to the next
	public void giveUp(View view) {
		if(currentmode == 1) {
			drawingView.save();
			questions.add(new Question(drawingView.getCurrent().getName(), drawingView.load(), drawingView.getCurrentChar(), currentmode));
			drawingView.setRunning(false);
			drawingView = null;
		}
		if(questions.size() > numQuestions) {
			moveToResults();
		}
		else {
			setUpMode();
		}
	}
	//Picks a random character
	public Character pickRandomChar() {
		int ID;
		Character c;
		int index = (int)(Math.random() * chars.length);
		if(type.equals("katakana")) {
			ID = getResources().getIdentifier(chars[index].toLowerCase(Locale.getDefault()) + "_kata", "drawable", getPackageName());
		}
		else {
			ID = getResources().getIdentifier(chars[index].toLowerCase(Locale.getDefault()), "drawable", getPackageName());
		}
		Bitmap map = BitmapFactory.decodeResource(getResources(), ID);
		if(type.equals("kanji")) {
			c = new Character(chars[index], counts[index], map, onyomi[index], kunyomi[index]);
		}
		else {
			c = new Character(chars[index], counts[index], map);
		}
		return c;
	}
	//Checks the answer for a multiple choice question and immediately begins another one or moves to the results
	public boolean checkAnswer(String s) {
		questionCounter++;
		questions.get(questions.size() - 1).setChosen(s);
		if(s.equals(convertString(answer.getName()))) {
				if(questions.size() > numQuestions) {
					moveToResults();
				}
				else {
					setUpMode();
				}
			return true;
		}
		if(questions.size() > numQuestions) {
			moveToResults();
		}
		else {
			setUpMode();
		}
		return false;
	}
	//Removes all duplicates among the choices in the multiple choice questions for mode 2
	public Button[] ensureNoDuplicates(Button[] buttons) {
		boolean duplicatesPresent = false;
		while(true) {
			duplicatesPresent = false;
			for(int i = 0; i < buttons.length - 1; i++) {
				for(int j = i + 1; j < buttons.length; j++) {
					if(buttons[i].getText().equals(convertString(answer.getName()))) {
						Log.v("Quiz", "[i] before: " + buttons[i].getText());
						buttons[i].setText(convertString(pickRandomChar().getName()));
						Log.v("Quiz", "[i] after: " + buttons[i].getText());
						duplicatesPresent = true;
					}
					else if(buttons[i].getText().equals(buttons[j].getText()) || buttons[j].getText().equals(convertString(answer.getName()))) {
						Log.v("Quiz", "[j] before: " + buttons[j].getText());
						buttons[j].setText(convertString(pickRandomChar().getName()));
						Log.v("Quiz", "[j] after: " + buttons[j].getText());
						duplicatesPresent = true;
					}
				}
			}
			if(!duplicatesPresent) {
				Log.v("Quiz", "NO DUPES");
				return buttons;
			}
			Log.v("Quiz", "DUPES");
		}
	}
	//Removes all duplicates among the choices in the multiple choice questions for mode 3
	public UsefulImageButton[] ensureNoDuplicates(UsefulImageButton[] buttons) {
		boolean duplicatesPresent = false;
		while(true) {
			duplicatesPresent = false;
			for(int i = 0; i < buttons.length - 1; i++) {
				for(int j = i + 1; j < buttons.length; j++) {
					if(buttons[i].getCharId().equals(answer.getName())) {
						Character c = pickRandomChar();
						Log.v("Quiz", "[i] before: " + buttons[i].getCharId());
						buttons[i].setCharId(c.getName());
						buttons[i].setCharMap(c.getDrawing());
						buttons[i].setImageBitmap(Bitmap.createScaledBitmap(c.getDrawing(), 150, 150, false));
						Log.v("Quiz", "[i] after: " + buttons[i].getCharId());
						duplicatesPresent = true;
					}
					else if(buttons[i].getCharId().equals(buttons[j].getCharId()) || buttons[j].getCharId().equals(answer.getName())) {
						Character c = pickRandomChar();
						Log.v("Quiz", "[j] before: " + buttons[j].getCharId());
						buttons[j].setCharId(c.getName());
						buttons[j].setCharMap(c.getDrawing());
						buttons[j].setImageBitmap(Bitmap.createScaledBitmap(c.getDrawing(), 150, 150, false));
						Log.v("Quiz", "[j] after: " + buttons[j].getCharId());
						duplicatesPresent = true;
					}
				}
			}
			if(!duplicatesPresent) {
				Log.v("Quiz", "NO DUPES");
				return buttons;
			}
			Log.v("Quiz", "DUPES");
		}
	}
	//Tranfers the questions and answers to the results screen for display
	public void moveToResults() {
		Intent intent = new Intent(this, ResultsActivity.class);
		intent.putExtra("Results", questions);
		Boolean[] b = new Boolean[selectedModes.size()];
		b = selectedModes.toArray(b);
		boolean[] c = new boolean[b.length];
		for(int i = 0; i < b.length; i++) {
			c[i] = b[i].booleanValue();
		}
		intent.putExtra(QuizTypeMenuActivity.modes, c);
		intent.putExtra(QuizTypeMenuActivity.type, type);
		startActivity(intent);
	}
	//Ensures the same character won't be used as the question multiple times
	public Character removeDupesAndReplace(Character c) {
		boolean dupesPresent;
		while(true) {
			dupesPresent = false;
			for(Question q: questions) {
				if(type.equals("kanji")) {
					if(compareAcrossLanguage(c.getName(), q.getQuestion())) {
						c = pickRandomChar();
						dupesPresent = true;
					}
				}
				else {
					if(c.getName().equals(q.getQuestion())) {
						c = pickRandomChar();
						dupesPresent = true;
					}
				}
			}
			if(!dupesPresent) {
				break;
			}
		}
		return c;
	}
	//Compares strings for kanji
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
	//Converts a string for display
	public String convertString(String s) {
		s = s.replace("_", ", ");
		return s;
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
