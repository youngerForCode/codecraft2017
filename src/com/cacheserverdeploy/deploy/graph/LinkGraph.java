package com.cacheserverdeploy.deploy.graph;

import java.util.ArrayList;
import java.util.List;

public class LinkGraph {

	public int m;
	public Vertex[] V;
	public List<String> netPaths = new ArrayList<String>();
	public LinkGraph() {
	}
	public LinkGraph(int m) {
		this.m = m;
		V = new Vertex[m];
		for(int i = 0;i < m; i++)
			V[i] = new Vertex();
	}

	public void addSingleEdge(Edge newEdge){
		int start = newEdge.start; 
		int end = newEdge.end;
		if(V[start].head == null || V[start].head.end > end ){
			newEdge.next = V[start].head;
			V[start].head = newEdge;
		}else {
			Edge edge = V[start].head;
			Edge pre = edge;
			while(edge !=  null && edge.end < end){//sort by end
				pre = edge;
				edge = edge.next;
			}
			if(edge !=  null && edge.end ==end)//the edge exits
				return ;
			newEdge.next = edge;
			pre.next = newEdge;
		}
	}

	
	public void addSingleEdge(int start,int end, int weight){
		addSingleEdge(start, end, Integer.MAX_VALUE, weight);
		
	}
	
	public void addSingleEdge(int start,int end, int capacity, int weight){
		Edge newEdge = new Edge(start, end, capacity, weight);
		if(V[start].head == null || V[start].head.end > end ){
			newEdge.next = V[start].head;
			V[start].head = newEdge;
		}else {
			Edge edge = V[start].head;
			Edge pre = edge;
			while(edge !=  null && edge.end < end){//sort by end
				pre = edge;
				edge = edge.next;
			}
			if(edge !=  null && edge.end ==end)//the edge exits
				return ;
			newEdge.next = edge;
			pre.next = newEdge;
		}
		
	}
	public void addSingleEdge(int start,int end){
		addSingleEdge(start, end, 1);
	}
	
    public void deleteSingleEdge(int start,int end){
    	Edge edge = V[start].head;
    	Edge pre = null;
    	while(edge != null && edge.end < end){
    		pre = edge;
    		edge = edge.next;
    	}
    	if(edge == null || edge.end > end)
    		return ;
    	else if(edge == V[start].head)
    		V[start].head = edge.next;
    	else 
    		pre.next = edge.next;
    }
	
    
    //print path by Vertex.pie 
    public void printPath(int u,int v){
    	if(u== v)  System.out.println(u);
        //    	DFS();
    	if(V[v].pie == -1) System.out.println("no path from u to v exists") ;
    	else{
    		System.out.printf("distance of between (%d,%d) :%3d  ",u,v,V[v].d);
    		int []path = new int[1000];
    		int i = 0;
    		for(int cur = v;cur != -1;cur = V[cur].pie,i++)
    			path[i] = cur;
    		//saved path is end to start ,need to reverse
    		System.out.print("the  path: ");
    		for(int j=i-1;j>=0;j--)
    			System.out.print(" "+path[j]);
    		System.out.println();
    		
    		/*int pre = V[v].pie;
    		while(pre != -1){
    			System.out.print(" "+pre);
    			pre = V[pre].pie;
    		}
    	    System.out.println();*/
    	}
    }

	
}

class TestLinkGraph_DFS{
	public static void test(){

	}
}