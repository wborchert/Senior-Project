package com.wb.test;

import android.graphics.Point;
//Custom Point class that can retrieve its location
public class DrawPoint extends Point {
	
	private int myX;
	private int myY;

	public DrawPoint(int x, int y) {
		myX = x;
		myY = y;
	}
	
	public int getX() {
		return myX;
	}
	
	public int getY() {
		return myY;
	}
	
	public String toString() {
    	return "(" + myX + ", " + myY + ")";
    }

}
