package com.cacheserverdeploy.deploy.graph;

public class Vertex {

	public int color;
	public int d;
	public int f;
	public int pie;
	public int key;
	public Edge head;
	
	public int h;
	public int e;
	public Vertex() {
		color = 0;
		d = -1;
		f = -1;
		pie = -1;
		key =-1;
		head = null;
		
		h = 0;
		e = 0;
	}
	@Override
	public String toString() {
		return d+","+pie;
	}

}
