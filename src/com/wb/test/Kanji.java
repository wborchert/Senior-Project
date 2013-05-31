package com.wb.test;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
//Obsolete
public class Kanji extends Character {

	private String onyomi;
	private String kunyomi;
	
	public Kanji(String n, int s, Bitmap d, String on, String kun) {
		super(n, s, d);
		onyomi = on;
		kunyomi = kun;
	}
	
	public String getOnyomi() {
		return onyomi;
	}
	
	public String getKunyomi() {
		return kunyomi;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getName());
		dest.writeInt(getStrokeCount());
		dest.writeValue(getDrawing());
		dest.writeString(onyomi);
		dest.writeString(kunyomi);
	}
	
	public static final Parcelable.Creator<Kanji> CREATOR = new Parcelable.Creator<Kanji>() {
        public Kanji createFromParcel(Parcel in) {
            return new Kanji(in);
        }

        public Kanji[] newArray(int size) {
            return new Kanji[size];
        }
    };

    private Kanji(Parcel in) {
        super(in);
        onyomi = in.readString();
        kunyomi = in.readString();
    }
}
