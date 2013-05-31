package com.wb.test;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Character implements Parcelable{
	
	private final String name;
	private final int stroke_count;
	private final Bitmap drawing;
	private final String on;
	private final String kun;
	private final boolean isKanji;
	
	//Creates a new non-kanji Character
	public Character(String n, int s, Bitmap d) {
		name = n;
		stroke_count = s;
		drawing = d;
		on = "";
		kun = "";
		isKanji = false;
	}
	
	//Creates a new kanji Character
	public Character(String n, int s, Bitmap d, String o, String k) {
		name = n;
		stroke_count = s;
		drawing = d;
		on = o;
		kun = k;
		isKanji = true;
	}
	
	public String getName() {
		return name;
	}
	
	public int getStrokeCount() {
		return stroke_count;
	}
	
	public Bitmap getDrawing() {
		return drawing;
	}
	
	public String getOnyomi() {
		return on;
	}
	
	public String getKunyomi() {
		return kun;
	}
	
	public boolean isKanji() {
		return isKanji;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//Creates a parcel with the contents of the Character to be transferred between activities
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeInt(stroke_count);
		dest.writeValue(drawing);
		dest.writeString(on);
		dest.writeString(kun);
		dest.writeByte((byte) (isKanji ? 1 : 0));
	}
	
	public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>() {
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    //Creates a new Character from a parcel 
    public Character(Parcel in) {
        name = in.readString();
        stroke_count = in.readInt();
        drawing = (Bitmap) in.readValue(Character.class.getClassLoader());
        on = in.readString();
        kun = in.readString();
        isKanji = in.readByte() == 1; 
    }
}
