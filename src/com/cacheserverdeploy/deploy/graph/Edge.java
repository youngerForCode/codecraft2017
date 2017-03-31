package com.cacheserverdeploy.deploy.graph;

public class Edge{
	
	public int start;
	public int end;
	public int weight;
	public Edge next;
	public int type; 
	public int f ;
	public int capacity;
	public Edge(int start,int end, int weight) {
		this(start, end, Integer.MAX_VALUE, weight);

	}
	public Edge(int start,int end, int capacity,int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
		this.next = null;
		this.f = capacity;// save the init capacity
		this.capacity = capacity;
	}
	@Override
	public String toString() {
		return start +"_"+capacity+"("+weight+")_"+end;
	}
}
