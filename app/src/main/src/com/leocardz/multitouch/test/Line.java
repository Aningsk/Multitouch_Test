package com.leocardz.multitouch.test;
/*
 * Java Class Line
 * created by Aningsk. I use this class to describe line.
 * 
 * @xList : saves all X coordinates
 * @yList : saves all Y coordinates
 * @index : the point's index on one line
 * @point : used as include two coordinates (X and Y)
 * 
 * Actually, I can use a Class Point to structure the Class Line, instead of two coordinates' ArrayLists.
 * The concept of POINT is only used here, so I wouldn't create a Class Point.
 * And the inner-class Point is just used for that I can return two coordinates.^_^.
 * 
 * But there may be a question:
 * Make sure that the data of xList and yList are one-to-one.
 * I never write some interface which can operate ONE ArrayList.
 * Even so, I had written a private function named as "checkLists". 
 * In fact, Class Line can work normally without the function "checkLists".
 */
import java.util.ArrayList;

public class Line {
	private ArrayList<Float> xList = new ArrayList<Float>();
	private ArrayList<Float> yList = new ArrayList<Float>();
	private int index;
	private Point point;
	
	public class Point {
		private float x;
		private float y;
		public Point() {
			setX(0);
			setY(0);
		}
		public float getX() {
			return x;
		}
		public void setX(float x) {
			this.x = x;
		}
		public float getY() {
			return y;
		}
		public void setY(float y) {
			this.y = y;
		}
	}
	
	public Line() {
		setIndex(-1);
		point = new Point();
	}
	
	public void clearLine() {
		xList.clear();
		yList.clear();
		setIndex(-1);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		checkLists();
		if (index < 0 || index >= xList.size()) 
			return;
		this.index = index;
	}
	
	public void setIndexToMax() {
		checkLists();
		this.index = xList.size() - 1;
	}

	public Point getPoint(int i) {
		point.setX(xList.get(i));
		point.setY(yList.get(i));
		return point;
	}

	public void setPoint(float x, float y) {
		point.setX(x);
		point.setY(y);
		xList.add(point.getX());
		yList.add(point.getY());
		checkLists();
		index = xList.size() - 1;
	}
	
	private void checkLists() {
		int longer = yList.size() - xList.size();
		
		if (longer == 0)
			return;
		else if (longer > 0)
			for (int i = yList.size() - 1; longer > 0; longer--)
				yList.remove(i--);
		else if (longer < 0)
			for (int i = xList.size() - 1; longer < 0; longer++)
				xList.remove(i--);
	}
	
}
