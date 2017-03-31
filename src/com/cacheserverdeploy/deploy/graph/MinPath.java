package com.cacheserverdeploy.deploy.graph;

import java.util.Comparator;

public class MinPath {

	public LinkGraph linkGraph ;
	public MinPath(int m) {
		linkGraph = new LinkGraph(m);
	}
	public MinPath(LinkGraph linkGraph) {
		this.linkGraph = linkGraph;
	}
	private void initializeSingleSource(int s){
		for (int i = 0; i < linkGraph.m; i++) {
//			linkGraph.V[i].d = Integer.MAX_VALUE;
			linkGraph.V[i].d = 100000000;
			linkGraph.V[i].pie = -1;
		}
		linkGraph.V[s].d = 0;
	}
	private void relax(int u,int v,int weight){
		if(linkGraph.V[v].d > linkGraph.V[u].d + weight){
			linkGraph.V[v].d = linkGraph.V[u].d + weight;
			linkGraph.V[v].pie = u;
			
			System.out.println("relax ("+u+","+v+") d = " +linkGraph.V[v].d);
		}
	}
	//@param s :source node
	//@return the boolean value,exist there minimum distance path
	public boolean bellManFord(int s){
		initializeSingleSource(s);
		for (int i = 0; i < linkGraph.m -1; i++) {//loop |V|-1 times
			for(int j = 0;j< linkGraph.m;j++ ){//scanner the every edges
				Edge edge = linkGraph.V[j].head;
				while(edge != null){
					relax(edge.start, edge.end, edge.weight);
					edge = edge.next;
				}
			}
		}
		for(int j = 0;j< linkGraph.m;j++ ){//scanner the every edges
			Edge edge = linkGraph.V[j].head;
			while(edge != null){
				if(linkGraph.V[edge.end].d > linkGraph.V[edge.start].d + edge.weight)
					return false;
				edge = edge.next;
			}
		}
		return true;
	}
	//@param s :source node
	//add checked the capacity weather more than 0
	public void dijkstra(int s){
		initializeSingleSource(s);
		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>(linkGraph.m, new Comparator<Vertex>() {

			@Override
			public int compare(Vertex o1, Vertex o2) {
				if(o1.d > o2.d) 
					return 1;
				else if(o1.d == o2.d) 
					return 0;
				else
					return -1;
			}
		});
		for (int i = 0; i < linkGraph.m; i++) 
			queue.add(linkGraph.V[i]);
		while(queue.size() > 0){
			Vertex u = queue.poll();
			for(Edge edge = u.head;edge != null ;edge = edge.next)
				if(edge.capacity > 0 && linkGraph.V[edge.end].d > linkGraph.V[edge.start].d + edge.weight){
					linkGraph.V[edge.end].d = linkGraph.V[edge.start].d + edge.weight;
					linkGraph.V[edge.end].pie = edge.start;
					queue.decrease(linkGraph.V[edge.end]);//note ,queue never auto adjust 
				}
		}
		
		
	}

	
	
}

class TestMinPath{
	public static void test(){
/*		System.out.println("bellmanFord algorithm test");
		MinPath minDistance = new MinPath(5);
		minDistance.linkGraph.addSingleEdge(0, 1, 6);
		minDistance.linkGraph.addSingleEdge(0, 4, 7);
		minDistance.linkGraph.addSingleEdge(1, 2, 5);
		minDistance.linkGraph.addSingleEdge(1, 3, -4);
		minDistance.linkGraph.addSingleEdge(1, 4, 8);
		minDistance.linkGraph.addSingleEdge(2, 1, -2);
		minDistance.linkGraph.addSingleEdge(3, 0, 2);
		minDistance.linkGraph.addSingleEdge(3, 2, 7);
		minDistance.linkGraph.addSingleEdge(4, 2, -3);
		minDistance.linkGraph.addSingleEdge(4, 3, 9);
		System.out.println(minDistance.bellManFord(0));
		minDistance.linkGraph.printPath(0, 2);
		minDistance.linkGraph.printPath(0, 3);*/
		
/*		System.out.println("dag_shortest_path test");
		minDistance = new MinPath(6);
		minDistance.linkGraph.addSingleEdge(0, 1, 5);
		minDistance.linkGraph.addSingleEdge(0, 2, 3);
		minDistance.linkGraph.addSingleEdge(1, 2, 2);
		minDistance.linkGraph.addSingleEdge(1, 3, 6);
		minDistance.linkGraph.addSingleEdge(2, 3, 7);
		minDistance.linkGraph.addSingleEdge(2, 4, 4);
		minDistance.linkGraph.addSingleEdge(2, 5, 2);
		minDistance.linkGraph.addSingleEdge(3, 4, -1);
		minDistance.linkGraph.addSingleEdge(3, 5, 1);
		minDistance.linkGraph.addSingleEdge(4, 5, -2);
		minDistance.dagShortestPath(1);
		minDistance.linkGraph.printPath(1, 5);*/
		System.out.println("dijkstra algorithm test");
		MinPath minDistance = new MinPath(5);
		minDistance.linkGraph.addSingleEdge(0, 1, 10);
		minDistance.linkGraph.addSingleEdge(0, 4, 5);
		minDistance.linkGraph.addSingleEdge(1, 2, 1);
		minDistance.linkGraph.addSingleEdge(1, 4, 2);
		minDistance.linkGraph.addSingleEdge(2, 3, 4);
		minDistance.linkGraph.addSingleEdge(3, 0, 7);
		minDistance.linkGraph.addSingleEdge(3, 2, 6);
		minDistance.linkGraph.addSingleEdge(4, 1, 3);
		minDistance.linkGraph.addSingleEdge(4, 2, 9);
		minDistance.linkGraph.addSingleEdge(4, 3, 2);
		minDistance.dijkstra(0);
		minDistance.linkGraph.printPath(0, 2);
		minDistance.linkGraph.printPath(0, 3);
	}
}
