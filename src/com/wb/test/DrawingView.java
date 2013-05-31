package com.wb.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import mpi.cbg.fly.Feature;
import mpi.cbg.fly.PointMatch;
import mpi.cbg.fly.SIFT;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable.Callback;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawingView extends SurfaceView implements Callback, Runnable {
	
	private boolean running = false;
	private Thread t;
	private SurfaceHolder h;
	private Paint p;
	private Bitmap mBitmap;
	private Bitmap mReadBitmap;
	private Canvas bitcanvas;
	private ArrayList<ArrayList<DrawPoint>> points;
	private ArrayList<DrawPoint> tempPoints;
	private int height;
	private int width;
	private Character[] charimgs;
	private String[] chars;
	private int[] counts;
	private Character current;
	private boolean isCorrect = false;
	private boolean readyToCheck = false;
	private Bitmap correct;
	private Bitmap incorrect;
	private String language;
	
	//Creates a new DrawingView
	public DrawingView(Context context) {
		super(context);
		h = getHolder();
		p = new Paint();
		points = new ArrayList<ArrayList<DrawPoint>>();
		tempPoints = new ArrayList<DrawPoint>();
		mBitmap = Bitmap.createBitmap(350, 350, Bitmap.Config.ARGB_8888);
		bitcanvas = new Canvas(mBitmap);
		chars = new String[]{"A", "I", "U", "E", "O", "KA", "KI", "KU", "KE", "KO", "SA", "SHI", "SU", "SE", "SO", "TA", "CHI", "TSU", "TE", "TO", "NA", "NI", "NU", "NE", "NO", "HA", "HI", "FU", "HE", "HO", "MA", "MI", "MU", "ME", "MO", "YA", "YU", "YO", "RA", "RI", "RU", "RE", "RO", "WA", "WO", "N"};
		counts = new int[]{3, 2, 2, 2, 3, 3, 4, 1, 3, 2, 3, 1, 2, 3, 1, 3, 2, 1, 1, 2, 4, 3, 2, 2, 1, 3, 1, 4, 1, 4, 3, 2, 3, 2, 3, 3, 2, 2, 2, 2, 1, 2, 1, 2, 3, 1};
		correct = BitmapFactory.decodeResource(getResources(), R.drawable.correct);
		incorrect = BitmapFactory.decodeResource(getResources(), R.drawable.incorrect);
		resume();
	}
	//Creates a new DrawingView
	public DrawingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		h = getHolder();
		p = new Paint();
		points = new ArrayList<ArrayList<DrawPoint>>();
		tempPoints = new ArrayList<DrawPoint>();
		mBitmap = Bitmap.createBitmap(350, 350, Bitmap.Config.ARGB_8888);
		bitcanvas = new Canvas(mBitmap);
		chars = new String[]{"A", "I", "U", "E", "O", "KA", "KI", "KU", "KE", "KO", "SA", "SHI", "SU", "SE", "SO", "TA", "CHI", "TSU", "TE", "TO", "NA", "NI", "NU", "NE", "NO", "HA", "HI", "FU", "HE", "HO", "MA", "MI", "MU", "ME", "MO", "YA", "YU", "YO", "RA", "RI", "RU", "RE", "RO", "WA", "WO", "N"};
		counts = new int[]{3, 2, 2, 2, 3, 3, 4, 1, 3, 2, 3, 1, 2, 3, 1, 3, 2, 1, 1, 2, 4, 3, 2, 2, 1, 3, 1, 4, 1, 4, 3, 2, 3, 2, 3, 3, 2, 2, 2, 2, 1, 2, 1, 2, 3, 1};
		correct = BitmapFactory.decodeResource(getResources(), R.drawable.correct);
		incorrect = BitmapFactory.decodeResource(getResources(), R.drawable.incorrect);
		resume();
	}
	//Creates a new DrawingView as called from the XML
	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		h = getHolder();
		p = new Paint();
		points = new ArrayList<ArrayList<DrawPoint>>();
		tempPoints = new ArrayList<DrawPoint>();
		mBitmap = Bitmap.createBitmap(350, 350, Bitmap.Config.ARGB_8888);
		bitcanvas = new Canvas(mBitmap);
		chars = new String[]{"A", "I", "U", "E", "O", "KA", "KI", "KU", "KE", "KO", "SA", "SHI", "SU", "SE", "SO", "TA", "CHI", "TSU", "TE", "TO", "NA", "NI", "NU", "NE", "NO", "HA", "HI", "FU", "HE", "HO", "MA", "MI", "MU", "ME", "MO", "YA", "YU", "YO", "RA", "RI", "RU", "RE", "RO", "WA", "WO", "N"};
		counts = new int[]{3, 2, 2, 2, 3, 3, 4, 1, 3, 2, 3, 1, 2, 3, 1, 3, 2, 1, 1, 2, 4, 3, 2, 2, 1, 3, 1, 4, 1, 4, 3, 2, 3, 2, 3, 3, 2, 2, 2, 2, 1, 2, 1, 2, 3, 1};
		correct = BitmapFactory.decodeResource(getResources(), R.drawable.correct);
		incorrect = BitmapFactory.decodeResource(getResources(), R.drawable.incorrect);
		resume();
	}
	
	public void surfaceCreated(SurfaceHolder h) {
		
	}
	
	public void surfaceDestroyed(SurfaceHolder h) {
		
	}
	
	public void surfaceChanged(SurfaceHolder h, int format, int width, int height) {
	
	}

	//Instructs the view what to draw
	@Override
	public void run() {
		while(running == true) {
			if(h.getSurface().isValid()) {
				Canvas c = h.lockCanvas();
				p.setColor(Color.BLACK);
				if((points.size() > 0 || tempPoints.size() > 0) && t.getState() == Thread.State.RUNNABLE) {
					p.setStrokeWidth(20);
					//Draws the current line
					for(int k = 0; k < tempPoints.size() - 2; k++) {
						if((tempPoints.size() > 0) && (points.size() <= current.getStrokeCount())) {
						c.drawCircle(tempPoints.get(k).getX(), tempPoints.get(k).getY(), 10, p);
						c.drawLine(tempPoints.get(k).getX(), tempPoints.get(k).getY(), 
						tempPoints.get(k + 1).getX(), tempPoints.get(k + 1).getY(), p);
						c.drawCircle(tempPoints.get(k + 1).getX(), tempPoints.get(k + 1).getY(), 10, p);
						bitcanvas.drawCircle(tempPoints.get(k).getX() - 75, tempPoints.get(k).getY() - 180, 10, p);
						bitcanvas.drawLine(tempPoints.get(k).getX() - 75, tempPoints.get(k).getY() - 180, 
						tempPoints.get(k + 1).getX() - 75, tempPoints.get(k + 1).getY() - 180, p);
						bitcanvas.drawCircle(tempPoints.get(k + 1).getX() - 75, tempPoints.get(k + 1).getY() - 180, 10, p);
						}
					}
					if(points.size() > 0  && tempPoints.size() != 0 && (points.get(points.size() - 1).get(points.get(points.size() - 1).size() - 1) != tempPoints.get(tempPoints.size() - 1))) {
						points.set(points.size() - 1, tempPoints);
					}
					//Draws all recorded lines
					for(int i = 0; i < points.size() - 1; i++) {
						for(int j = 0; j < points.get(i).size() - 2; j++) {
							if(points.get(i).size() <= 1) {
								c.drawCircle(points.get(0).get(j).getX(), points.get(0).get(j).getY(), 10, p);
							}
							else {
								c.drawCircle(points.get(i).get(j).getX(), points.get(i).get(j).getY(), 10, p);
								c.drawLine(points.get(i).get(j).getX(), points.get(i).get(j).getY(), 
								points.get(i).get(j + 1).getX(), points.get(i).get(j + 1).getY(), 
								p);
								c.drawCircle(points.get(i).get(j + 1).getX(), points.get(i).get(j + 1).getY(), 10, p);
								bitcanvas.drawCircle(points.get(i).get(j).getX() - 75, points.get(i).get(j).getY() - 180, 10, p);
								bitcanvas.drawLine(points.get(i).get(j).getX() - 75, points.get(i).get(j).getY() - 180, 
								points.get(i).get(j + 1).getX() - 75, points.get(i).get(j + 1).getY() - 180, 
								p);
								bitcanvas.drawCircle(points.get(i).get(j + 1).getX() - 75, points.get(i).get(j + 1).getY() - 180, 10, p);
							}
						}
					}
				}
				//Draws the canvas' standard state
				else {
					int counter = 0;
					while(true) {
						if(t.getState() == Thread.State.RUNNABLE || counter > 1000) {
							c.drawARGB(255, 255, 255, 255);
							p.setAlpha(90);
							c.drawBitmap(Bitmap.createScaledBitmap(getCurrentChar(), 350, 350, false), 75, 180, p);
							p.setAlpha(256);
							break;
						}
						counter++;
					}
				}
				h.unlockCanvasAndPost(c);
			}
		}
	}
	//Stops the thread
	public void pause() {
		setRunning(false);
		while(t != null) {
			try {
				t.join();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		t = null;
	}
	//Restarts the thread
	public void resume() {
		running = true;
		t = new Thread(this);
		t.start();
	}
	//Adds a new point to the current line
	public void pushPoint(float x, float y) {
		if(y > 180 && y < 530 && x < 425 && x > 75 && points.size() < current.getStrokeCount()) {
			tempPoints.add(new DrawPoint((int)x, (int)y));
		}
	}
	//Adds the current line to the archive of lines
	public void pushLine() {
		ArrayList<DrawPoint> temp = new ArrayList<DrawPoint>();
		for(int i = 0; i < tempPoints.size(); i++) {
			temp.add(tempPoints.get(i));
		}
		if(temp.size() > 0) {
			points.add(temp);
		}
	}
	//Starts a new line
	public void newLine() {
		int counter = 0;
		while(true) {
			if(t.getState() == Thread.State.RUNNABLE || counter > 10000) {
				pause();
				tempPoints.clear();
				break;
			}
			counter++;
		}
	}
	//Clears the canvas
	public void clear() {
		int counter = 0;
		while(true) {
			if(t.getState() == Thread.State.RUNNABLE || counter > 10000) {
				pause();
				points.clear();
				tempPoints.clear();
				Canvas c = h.lockCanvas();
				c.drawARGB(255, 255, 255, 255);
				h.unlockCanvasAndPost(c);
				mBitmap = Bitmap.createBitmap(350, 350, Bitmap.Config.ARGB_8888);
				bitcanvas.setBitmap(mBitmap);
				readyToCheck = false;
				break;
			}
			counter++;
		}
	}
	//Saves a bitmap of the current drawing
	public void save() {
		pause();
		try {
			String file_path = Environment.getExternalStorageDirectory().getAbsolutePath();
			File dir = new File(file_path);
			if(!dir.exists()) {
				dir.mkdirs();
			}
				File file = new File(dir, "storedimage.png");
				FileOutputStream fOut = new FileOutputStream(file);
				
				Bitmap bitmap = Bitmap.createScaledBitmap(mBitmap, getCurrentChar().getWidth(), getCurrentChar().getHeight(), false);
				bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
				fOut.flush();
				fOut.close();
			
		} catch (IOException e) {
		    Log.w("DrawingView", e);
		}
	}
	//Loads the saved bitmap
	public Bitmap load() {
		Bitmap loaded = Bitmap.createBitmap(mBitmap);
		try {
			String file_path = Environment.getExternalStorageDirectory().getAbsolutePath();
			File dir = new File(file_path);
			if(!dir.exists()) {
				dir.mkdirs();
			}
				File file = new File(dir, "storedimage.png");
				FileInputStream fIn = new FileInputStream(file);
				loaded = BitmapFactory.decodeStream(fIn);
			
		} catch (IOException e) {
		    Log.w("DrawingView", e);
		}
		return loaded;
	}
	//Returns the template bitmap of the character being drawn
	public Bitmap getCurrentChar() {
		Bitmap currentMap = current.getDrawing();
		return currentMap;
	}
	//Returns the character being drawn
	public Character getCurrent() {
		return current;
	}
	
	public void setCurrentChar(Character c) {
		current = c;
	}
	//Checks using hash conversion to see if the drawn and recorded images are similar
	public boolean check() {
		save();
		mReadBitmap = load();
		mReadBitmap = Bitmap.createScaledBitmap(mReadBitmap, 8, 8, false);
		Bitmap mDrawnBitmap = getCurrentChar();
		mDrawnBitmap = Bitmap.createScaledBitmap(mDrawnBitmap, 8, 8, false);
		String answerHash = convertToHash(mReadBitmap);
		String drawnHash = convertToHash(mDrawnBitmap);
		Log.v("DrawingView", answerHash);
		Log.v("DrawingView", drawnHash);
		int counter = 0;
		for(int i = 0; i < answerHash.length(); i++) {
			if(answerHash.substring(i, i+1).equals(drawnHash.substring(i, i+1))) {
				counter++;
			}
		}
		int threshold = answerHash.length() - 8;
		Log.v("DrawingView", "" + threshold);
		Log.v("DrawingView", "" + counter);
		if(counter >= threshold) {
			Log.v("DrawingView", "Greater");
			return true;
		}
		return false;
	}
	//Checks with SIFT to see if the drawn and recorded images are similar - Not Working Currently
	public boolean siftCheck() {
		save();
		mReadBitmap = load();
		mReadBitmap = Bitmap.createScaledBitmap(mReadBitmap, getCurrentChar().getWidth(), getCurrentChar().getHeight(), false);
		Bitmap mDrawnBitmap = getCurrentChar();
		int[] pixels = new int[mReadBitmap.getWidth() * mReadBitmap.getHeight()];
		mReadBitmap.getPixels(pixels, 0, mReadBitmap.getWidth(), 0, 0, mReadBitmap.getWidth(), mReadBitmap.getHeight());
		for(int i = 0; i < 5; i++) {
			pixels = mixPixels(pixels, mReadBitmap.getWidth());
		}
		Vector<Feature> features = SIFT.getFeatures(mReadBitmap.getWidth(), mReadBitmap.getHeight(), pixels);
		int[] pixels2 = new int[mDrawnBitmap.getWidth() * mDrawnBitmap.getHeight()];
		mDrawnBitmap.getPixels(pixels2, 0, mDrawnBitmap.getWidth(), 0, 0, mDrawnBitmap.getWidth(), mDrawnBitmap.getHeight());
		Vector<Feature> features2 = SIFT.getFeatures(mDrawnBitmap.getWidth(), mDrawnBitmap.getHeight(), pixels2);
		Vector<PointMatch> matches = SIFT.createMatches(features, features2, (float)0, null, (float)0);
		Log.v("DrawingView", "" + features.size());
		Log.v("DrawingView", "" + features2.size());
		Log.v("DrawingView", "" + matches.size());
		for(int i = 0; i < matches.size(); i++) {
			Log.v("DrawingView", "" + matches.get(i));
		}
		return false;
	}
	//Compares individual pixels to see if the drawn and recorded images are similar - Not Working Currently
	public boolean pixelCheck() {
		readyToCheck = false;
		int counter = 0;
		int matches = 0;
		int missed = 0;
				save();
				mReadBitmap = load();
				int[] answerPixels = new int[getCurrentChar().getWidth() * getCurrentChar().getHeight()];
				getCurrentChar().getPixels(answerPixels, 0, getCurrentChar().getWidth(), 0, 0, getCurrentChar().getWidth(), getCurrentChar().getHeight());
				int[] drawnPixels = new int[mReadBitmap.getWidth() * mReadBitmap.getHeight()];
				mReadBitmap.getPixels(drawnPixels, 0, mReadBitmap.getWidth(), 0, 0, mReadBitmap.getWidth(), mReadBitmap.getHeight());
					
					Log.v("DrawingView", "Drawn: " + drawnPixels.length);
					Log.v("DrawingView", "Answer: " + answerPixels.length);
					for(int i = 0; i < answerPixels.length; i++) {
						if((drawnPixels[i] != 0 && (answerPixels[i] != -1 && answerPixels[i] != 0)) || (drawnPixels[i] == 0 && (answerPixels[i] == -1 || answerPixels[i] == 0))) {
							matches++;
						}
						else if((drawnPixels[i] == 0) && (answerPixels[i] < -16500000)) {
							missed++;
						}
					} 
					Log.v("DrawingView", "Counter: " + matches);
					Log.v("DrawingView", "Threshold: " + getDefault(answerPixels));
					Log.v("DrawingView", "Missed Threshold: " + getMissed(answerPixels));
					Log.v("DrawingView", "Missed: " + missed);
					if((matches >= getDefault(answerPixels)) && (missed < getMissed(answerPixels)) && (points.size() == current.getStrokeCount())) {
						Log.v("DrawingView", "SUCCESS");
						isCorrect = true;
						readyToCheck = true;
						return true;
					}
					else {
						isCorrect = false;
					}
		readyToCheck = true;
		return false;	
		}
	//Gets threshold needed for matching pixels
	public int getDefault(int[] pixels) {
		int min = 0;
		for(int i = 0; i < pixels.length; i++) {
			if(pixels[i] == -1 || pixels[i] == 0) {
				min++;
			}
		}
		if(language.equals("kanji")) {
			min = min + (int)(0.5 * (pixels.length - min));
		}
		else {
			min = min + (int)(0.4 * (pixels.length - min));
		}
		return min;
	}
	//Gets threshold needed for required important matching pixels
	public int getMissed(int[] pixels) {
		int missedThreshold = 0;
		for(int i = 0; i < pixels.length; i++) {
			if(pixels[i] <= -16500000) {
				missedThreshold++;
			}
		}
		missedThreshold = (int)(0.3 * missedThreshold);
		return missedThreshold;
	}
	//Finds the longest line drawn
	public int findLongest(ArrayList<ArrayList<DrawPoint>> array) {
		int longest = 0;
		for(int i = 0; i < array.size(); i++) {
			if(array.get(i).size() > longest) {
				longest = array.get(i).size();
			}
		}
		return longest;
	}
	//Returns the index of the specified string in the character string array
	public int getIndexOf(String s) {
		for(int i = 0; i < chars.length; i++) {
			if(chars[i].equals(s)) {
				return i;
			}
		}
		return -1;
	}
	
	public Paint getPaint() {
		return p;
	}
	
	public SurfaceHolder getCurrentHolder() {
		return h;
	}
	
	public Thread getThread() {
		return t;
	}
	
	public ArrayList<ArrayList<DrawPoint>> getPoints() {
		return points;
	}
	
	public ArrayList<DrawPoint> getTempPoints() {
		return tempPoints;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public boolean isReadyToCheck() {
		return readyToCheck;
	}
	
	public void setIsReadyToCheck(boolean b) {
		readyToCheck = b;
	}
	
	public boolean isCorrect() {
		return isCorrect;
	}
	
	public Bitmap getCorrect() {
		return correct;
	}
	
	public Bitmap getIncorrect() {
		return incorrect;
	}
	
	public Bitmap getMBitmap() {
		return mBitmap;
	}
	
	public Canvas getBitCanvas() {
		return bitcanvas;
	}
	
	public void setRunning(boolean run) {
		running = run;
	}
	
	public void setLanguage(String s) {
		language = s;
	}
	//Converts a bitmap to an integer hash
	public String convertToHash(Bitmap b) {
		String hash;
		String bits = "";
		int[] pixels = new int[b.getWidth() * b.getHeight()];
		b.getPixels(pixels, 0, b.getWidth(), 0, 0, b.getWidth(), b.getHeight());
		int avg = computeAverage(pixels);
		for(int i = 0; i < pixels.length; i++) {
			if(pixels[i] < avg) {
				bits += "0";
			}
			else {
				bits += "1";
			}
		}
		Log.v("DrawingView", bits);
		hash = computeHash(bits);
		return hash;
	}
	//Computes the average of an array of integers
	public int computeAverage(int[] p) {
		int avg = 0;
		for(int i = 0; i < p.length; i++) {
			avg += p[i];
		}
		avg /= p.length;
		return avg;
	}
	//Transforms a String integer from binary to hexadecimal
	public String computeHash(String s) {
		int smallsum = 0;
		String hash = "";
		for(int i = 0; i < s.length(); i+=8) {
			for(int j = i; j < i + 8; j++) {
				String temp = s.substring(j, j + 1);
				switch(j%8) {
				case 0:	smallsum += Integer.parseInt(temp) * Math.pow(2, 7);
					break;
				case 1: smallsum += Integer.parseInt(temp) * Math.pow(2, 6);
					break;
				case 2: smallsum += Integer.parseInt(temp) * Math.pow(2, 5);
					break;
				case 3: smallsum += Integer.parseInt(temp) * Math.pow(2, 4);
					break;
				case 4: smallsum += Integer.parseInt(temp) * Math.pow(2, 3);
					break;
				case 5: smallsum += Integer.parseInt(temp) * Math.pow(2, 2);
					break;
				case 6: smallsum += Integer.parseInt(temp) * Math.pow(2, 1);
					break;
				case 7: smallsum += Integer.parseInt(temp) * Math.pow(2, 0);
					break;
				}
			}
			hash += hexadecimal(smallsum);
			smallsum = 0;
		}
		return hash;
	}
	//Transforms a decimal number into hexadecimal
	public String hexadecimal(int i) {
		String hex = "";
		int first = i/16;
		int second = i%16;
		if(first > 9) {
			switch(first) {
			case 10: hex += "a";
				break;
			case 11: hex += "b";
				break;
			case 12: hex += "c";
				break;
			case 13: hex += "d";
				break;	
			case 14: hex += "e";
				break;
			case 15: hex += "f";
				break;
			}
		}
		else {
			hex += first + "";
		}
		if(second > 9) {
			switch(second) {
			case 10: hex += "a";
				break;
			case 11: hex += "b";
				break;
			case 12: hex += "c";
				break;
			case 13: hex += "d";
				break;
			case 14: hex += "e";
				break;
			case 15: hex += "f";
				break;
			}
		}
		else {
			hex += second + "";
		}
		Log.v("DrawingView", "Hex: " + hex);
		return hex;
	}
	//Mixes pixels with neighbors to create shading -- Not Used
	public int[] mixPixels(int[] pixels, int width) {
		int[] mixed = new int[pixels.length];
		int[] temp = new int[5];
		for(int i = 0; i < pixels.length; i++) {
			mixed[i] = 0;
		}
		for(int i = width; i < pixels.length - width; i++) {
			temp[0] = pixels[i];
			temp[1] = pixels[i + 1];
			temp[2] = pixels[i - 1];
			temp[3] = pixels[i - width];
			temp[4] = pixels[i + width];
			mixed[i] = computeAverage(temp);
		}
		return mixed;
	}
}
