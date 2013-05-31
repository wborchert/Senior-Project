package com.wb.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

//Specialized DrawingView for the Quiz portion that does not display the template
public class QuizDrawingView extends DrawingView {
	
	public QuizDrawingView(Context context) {
		super(context);
	}
	
	public QuizDrawingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public QuizDrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void run() {
		while(isRunning()) {
			if(getCurrentHolder().getSurface().isValid()) {
				Canvas c = getCurrentHolder().lockCanvas();
				getPaint().setColor(Color.BLACK);
				if((getPoints().size() > 0 || getTempPoints().size() > 0) && getThread().getState() == Thread.State.RUNNABLE) {
					getPaint().setStrokeWidth(20);
					for(int k = 0; k < getTempPoints().size() - 2; k++) {
						if((getTempPoints().size() > 0) && (getPoints().size() <= getCurrent().getStrokeCount())) {
						c.drawCircle(getTempPoints().get(k).getX(), getTempPoints().get(k).getY(), 10, getPaint());
						c.drawLine(getTempPoints().get(k).getX(), getTempPoints().get(k).getY(), 
						getTempPoints().get(k + 1).getX(), getTempPoints().get(k + 1).getY(), getPaint());
						c.drawCircle(getTempPoints().get(k + 1).getX(), getTempPoints().get(k + 1).getY(), 10, getPaint());
						getBitCanvas().drawCircle(getTempPoints().get(k).getX() - 75, getTempPoints().get(k).getY() - 180, 10, getPaint());
						getBitCanvas().drawLine(getTempPoints().get(k).getX() - 75, getTempPoints().get(k).getY() - 180, 
						getTempPoints().get(k + 1).getX() - 75, getTempPoints().get(k + 1).getY() - 180, getPaint());
						getBitCanvas().drawCircle(getTempPoints().get(k + 1).getX() - 75, getTempPoints().get(k + 1).getY() - 180, 10, getPaint());
						}
					}
					if(getPoints().size() > 0  && getTempPoints().size() != 0 && (getPoints().get(getPoints().size() - 1).get(getPoints().get(getPoints().size() - 1).size() - 1) != getTempPoints().get(getTempPoints().size() - 1))) {
						getPoints().set(getPoints().size() - 1, getTempPoints());
					}
					for(int i = 0; i < getPoints().size() - 1; i++) {
						for(int j = 0; j < getPoints().get(i).size() - 2; j++) {
							if(getPoints().get(i).size() <= 1) {
								c.drawCircle(getPoints().get(0).get(j).getX(), getPoints().get(0).get(j).getY(), 10, getPaint());
							}
							else {
								c.drawCircle(getPoints().get(i).get(j).getX(), getPoints().get(i).get(j).getY(), 10, getPaint());
								c.drawLine(getPoints().get(i).get(j).getX(), getPoints().get(i).get(j).getY(), 
								getPoints().get(i).get(j + 1).getX(), getPoints().get(i).get(j + 1).getY(), 
								getPaint());
								c.drawCircle(getPoints().get(i).get(j + 1).getX(), getPoints().get(i).get(j + 1).getY(), 10, getPaint());
								getBitCanvas().drawCircle(getPoints().get(i).get(j).getX() - 75, getPoints().get(i).get(j).getY() - 180, 10, getPaint());
								getBitCanvas().drawLine(getPoints().get(i).get(j).getX() - 75, getPoints().get(i).get(j).getY() - 180, 
								getPoints().get(i).get(j + 1).getX() - 75, getPoints().get(i).get(j + 1).getY() - 180, 
								getPaint());
								getBitCanvas().drawCircle(getPoints().get(i).get(j + 1).getX() - 75, getPoints().get(i).get(j + 1).getY() - 180, 10, getPaint());
							}
						}
					}
				}
				else {
					int counter = 0;
					while(true) {
						if(getThread().getState() == Thread.State.RUNNABLE || counter > 1000) {
							c.drawARGB(255, 255, 255, 255);
							break;
						}
						counter++;
					}
				}
				if(getPoints().size() == getCurrent().getStrokeCount() && isReadyToCheck()) {
					Log.v("QuizDrawingView", "" + getPoints().size());
					Log.v("QuizDrawingView", "" + getCurrent().getStrokeCount());
					Log.v("QuizDrawingView", "" + isReadyToCheck());
					getPaint().setColor(Color.WHITE);
					c.drawRect(330, 0, 480, 150, getPaint());
					if(isCorrect()) {
						c.drawBitmap(getCorrect(), 330, 0, null);
					}
					else {
						Log.v("QuizDrawingView", "poo");
						c.drawBitmap(getIncorrect(), 330, 0, null);
					}
					setIsReadyToCheck(false);
				}
				getCurrentHolder().unlockCanvasAndPost(c);
			}
		}
	}
}
