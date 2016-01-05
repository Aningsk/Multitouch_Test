package com.leocardz.multitouch.test;

import java.util.ArrayList;

public class Line {
	private ArrayList<Float> xList = new ArrayList<Float>();
	private ArrayList<Float> yList = new ArrayList<Float>();
	private int index;
	private int length;
	
	public Line() {
		index = -1;
		length = 0;
	}
	
	public int setIndexToMax() {
		index = length -1;
		return index;
	}
	
	public float getX() {
		if (index < 0)
			return -1f;
		else 
			return xList.get(--index);
	}
	public float getX(int i) {
		if (i >= length || i < 0)
			return -1f;
		return xList.get(i);
	}
	
	public float getY() {
		if (index < 0)
			return -1f;
		else
			return yList.get(--index);
	}
	public float getY(int i) {
		if (i >= length || i < 0)
			return -1f;
		return yList.get(i);
	}
	
	public int addPoint(float x, float y) {
		if (x > 0 && y > 0) {
			xList.add(x);
			yList.add(y);
			length++;
			//index = length - 1;
		}
		return length;
	}
	
	public void clearLine() {
		index = -1;
		length = 0;
		xList.clear();
		yList.clear();
	}
}
