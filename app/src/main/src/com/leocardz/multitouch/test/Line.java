package com.leocardz.multitouch.test;

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
			y = 0;
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
		this.index = index;
	}
	
	public void setIndexToMax() {
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
		yList.add(point.y);
		index = xList.size() - 1;
	}

}
