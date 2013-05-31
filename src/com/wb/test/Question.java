package com.wb.test;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
//A class that holds all the information pertaining to a question on the QuizActivity
public class Question implements Parcelable {

	private Bitmap drawnBitmap;
	private Bitmap answerBitmap;
	private String[] choices;
	private String question;
	private int type;
	private String chosen;
	//Creates a new question for a drawing
	public Question(String q, Bitmap d, Bitmap a, int t) {
		question = q;
		drawnBitmap = d;
		answerBitmap = a;
		type = t;
	}
	//Creates a new question for multiple choice
	public Question(String[] c, String q, int t) {
		choices = c;
		question = q;
		type = t;
	}
	
	public Bitmap getAnswerBitmap() {
		return answerBitmap;
	}
	
	public Bitmap getDrawnBitmap() {
		return drawnBitmap;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String[] getChoices() {
		return choices;
	}
	
	public int getType() {
		return type;
	}

	public String getChosen() {
		return chosen;
	}

	public void setChosen(String s) {
		chosen = s;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(type);
		switch(type) {
			case 1:	Bitmap.createScaledBitmap(drawnBitmap, 50, 50, false);
				Bitmap.createScaledBitmap(answerBitmap, 50, 50, false);
				dest.writeValue(drawnBitmap);
				dest.writeValue(answerBitmap); 
				dest.writeString(question);
				break;
			default: dest.writeStringArray(choices);
				dest.writeString(question);
				dest.writeString(chosen);
				break;
		}	
	}
	
	public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    private Question(Parcel in) {
    	type = in.readInt();
    	switch(type) {
	    	case 1:	drawnBitmap = (Bitmap) in.readValue(Question.class.getClassLoader());
		        Bitmap.createScaledBitmap(drawnBitmap, 350, 350, false);
		        answerBitmap = (Bitmap) in.readValue(Question.class.getClassLoader());
		        Bitmap.createScaledBitmap(answerBitmap, 350, 350, false);
		        question = in.readString();
	    		break;
	    	default: String[] temp = new String[4];
		        in.readStringArray(temp);
		        choices = temp;
		        question = in.readString();
		        chosen = in.readString();
	    		break;
    	}
    }
}
