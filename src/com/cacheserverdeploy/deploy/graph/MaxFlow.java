package com.cacheserverdeploy.deploy.graph;

public class MaxFlow {

	public LinkGraph lGraph ;
	public MaxFlow(LinkGraph linkGraph) {
		this.lGraph = linkGraph;
	}
	public MaxFlow(int m) {
		lGraph = new LinkGraph(m);
	}
	

	public int[] fordFulkerson(int s,int t){
		int maxf = 0;
		int totalCost = 0;
		lGraph.netPaths.clear();//clear old path
		while(true ){
			MinPath minPath = new MinPath(lGraph);
			minPath.dijkstra(s);
			if(lGraph.V[t].pie  == -1)//no augment path
				break;
			totalCost += lGraph.V[t].d;
//			lGraph.printPath(s, t);             /*******************************************/
			
			int cf = Integer.MAX_VALUE;
			int v = t;
			for(int u = lGraph.V[v].pie; u!=-1 ; v = u,u=lGraph.V[v].pie){//search remaining capacity
				Edge edge = lGraph.V[u].head;
				while(edge != null && edge.end != v)
					edge = edge.next;
				if(edge.end == v){
					if(cf > edge.capacity)
						cf = edge.capacity;
				}
				else
					System.out.println("error can't find parent node,when search remaining capacity");
			}
//			System.out.println("capacity:"+cf); /*******************************************************/
			maxf += cf;
			v = t;
			String netPath = ""+cf;//save the current flow
			for(int u = lGraph.V[v].pie; u!=-1; v = u,u=lGraph.V[v].pie){//augment flow
				Edge edge = lGraph.V[u].head;
				while(edge != null && edge.end != v)
					edge = edge.next;
				if(edge != null && edge.end == v){
					edge.capacity -= cf;
					netPath =netPath +" "+ v;
/*					if(edge.capacity == 0)//checked the capacity when search path ,so no necessary
						lGraph.deleteSingleEdge(u, v);*/
				}else
					System.out.println("the minpath cannt find edge");
				
				edge = lGraph.V[v].head;//change (v,u) capacity
				while(edge != null && edge.end != u)
					edge = edge.next;
				if(edge!= null && edge.end == u){
					edge.capacity += cf;
/*					if(edge.capacity == 0)//checked the capacity when search path ,so no necessary
						lGraph.deleteSingleEdge(v, u);*/
				}/*else if(edge == null)// should add edge,but we have done before
					lGraph.addSingleEdge(v, u,cf,1);*/
     		}
			lGraph.netPaths.add(netPath);
		}
		//reset capacity is needed,when execute max flow in more once
		for (int i = 0; i < lGraph.m; i++) {
			for(Edge edge = lGraph.V[i].head;edge != null;edge = edge.next)
				edge.capacity = edge.f;//f had save the init capacity
		}
//		System.out.println("the maxflow : " +maxf);/*******************************************************/
		return new int[]{maxf, totalCost} ;
	}	
	
	
	
}

class TestMaxFlow{
	public static void test(){
/*		
		MaxFlow maxFlow = new MaxFlow(6);
		maxFlow.mGraph.addSingleEdge(0, 1,16);
		maxFlow.mGraph.addSingleEdge(1, 2,12);
		maxFlow.mGraph.addSingleEdge(2, 3,20);
		maxFlow.mGraph.addSingleEdge(4, 3,4);
		maxFlow.mGraph.addSingleEdge(5, 4,14);
		maxFlow.mGraph.addSingleEdge(0, 5,13);
		maxFlow.mGraph.addSingleEdge(5, 1,4);
		maxFlow.mGraph.addSingleEdge(4, 2,7);
		maxFlow.mGraph.addSingleEdge(2, 5,9);
		maxFlow.fordFulkerson(0, 3);*/
		
/*		MaxFlow maxFlow = new MaxFlow(6);
		maxFlow.lGraph.addSingleEdge(0, 1,16,1);
		maxFlow.lGraph.addSingleEdge(0, 5,13,1);
		maxFlow.lGraph.addSingleEdge(1, 2,12,1);
		maxFlow.lGraph.addSingleEdge(2, 3,20,1);
		maxFlow.lGraph.addSingleEdge(2, 5,9,1);
		maxFlow.lGraph.addSingleEdge(4, 2,7,1);
		maxFlow.lGraph.addSingleEdge(4, 3,4,1);
		maxFlow.lGraph.addSingleEdge(5, 1,4,1);
		maxFlow.lGraph.addSingleEdge(5, 4,14,1);
		maxFlow.fordFulkerson(0, 3);*/
		MaxFlow maxFlow = new MaxFlow(4);
		maxFlow.lGraph.addSingleEdge(0, 1,5,2);
		maxFlow.lGraph.addSingleEdge(0, 3,2,5);
		maxFlow.lGraph.addSingleEdge(1, 2,2,7);
		maxFlow.lGraph.addSingleEdge(1, 3,1,3);
		maxFlow.lGraph.addSingleEdge(3, 2,4,1);
		maxFlow.fordFulkerson(0, 2);
		
		
	}
}
