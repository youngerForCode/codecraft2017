package com.cacheserverdeploy.deploy.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cacheserverdeploy.deploy.ga.Individual;

public class CodeCraft {

	public void craftMain(){
//		MaxFlow maxFlow = new MaxFlow();
//		maxFlow.initByFile("case0.txt");
//		TestMinPath.test();
//		TestMaxFlow.test();
		
/*		CodeCraft codeCraft = new CodeCraft();
		codeCraft.craftMain();*/
		
/*		long initTime = System.currentTimeMillis(); 
		Individual.initByFile("case0.txt");
		Individual individual = new Individual();
		individual.initGraphAndIndi();
		long estimatedTime = System.currentTimeMillis() - initTime;
		System.out.println("the totalCost:"+individual.getFitness()+" ,runningtime: "+estimatedTime);
		individual.printNetPath();*/
		
		List<Edge> netEdges = new ArrayList<Edge>();
		List<Edge> userEdges = new ArrayList<Edge>();
		
		int [] datas = initByFile("case0.txt",  netEdges, userEdges);
		int netVertexN = datas[0];int netEdgeN = datas[1];int userN = datas[2];
		int serverCost = datas[3];
		int requirement = 0;
		for (int i = 0; i < userEdges.size(); i++)
			requirement += userEdges.get(i).capacity;
		System.out.println(requirement);
/*		System.out.println(netVertexN+" "+ netEdgeN+" "+ userN+" "+ serverCost);
		System.out.println(netEdges.size() +" : "+ netEdges.get(netEdges.size() -1));
		System.out.println(userEdges.size() +" : "+ userEdges.get(userEdges.size() -1));*/
		
		/*******�����ڽ�����*******/
		//����һ����������÷������������ӣ���һ���������е����ѽ��
		//��netVertexN����������ѽ�㣬��netVertexN+1������Ƿ��������� 
		LinkGraph lGraph = new LinkGraph(netVertexN +2);
		for (int i = 0; i < netEdges.size(); i++){ 
			Edge edge = netEdges.get(i);
			lGraph.addSingleEdge(edge);
			//������·��˫���
			lGraph.addSingleEdge(edge.end,edge.start,edge.capacity,edge.weight);
		}
		
		for (int i = 0; i < userEdges.size(); i++){
			Edge edge = userEdges.get(i);//�����ѻ��㶼�ϲ��� ��netVertexN����
			lGraph.addSingleEdge(edge.end, netVertexN, edge.capacity, 0);
		}
		
		
		
		/*******���÷�����*******/
		Individual individual = new Individual();
		individual.setDefaultGeneLength(netVertexN);
		individual.generateIndividual();
		for (int i = 0; i < netVertexN; i++) {
			if(individual.getGene(i) == 1)//������ i ��������Ϸ��÷�����
				lGraph.addSingleEdge(netVertexN+1,i , Integer.MAX_VALUE, 0);
		}
		System.out.println(individual);
		/*//������0��������Ϸ��÷�����
		lGraph.addSingleEdge(netVertexN+1,0 , Integer.MAX_VALUE, 0);
		//������1��������Ϸ��÷�����
		lGraph.addSingleEdge(netVertexN+1,1 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,2 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,3 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,4 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,5 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,6 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,7 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,8 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,9 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,10 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,11 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,12 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,13 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,14 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,15 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,16 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,17 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,18 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,19 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,10 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,21 , Integer.MAX_VALUE, 0);
		lGraph.addSingleEdge(netVertexN+1,38 , Integer.MAX_VALUE, 0);*/
		
		
		MaxFlow maxFlow = new MaxFlow(lGraph);
		int []ret =  maxFlow.fordFulkerson(netVertexN+1, netVertexN);
		int maxf = ret[0];
		int totalCost = ret[1];
		if(maxf == requirement)
			System.out.println("OK!"+" the total cost:"+totalCost);

	}
	
	public int[] initByFile(String path,List<Edge> netEdges , List<Edge> userEdges ) {
		int netVertexN,  netEdgeN,  userN, serverCost;
        List<String[]> lines = readData(path);
        
		String []tokens = lines.get(0);
		netVertexN = Integer.parseInt(tokens[0]);
		netEdgeN = Integer.parseInt(tokens[1]);
		userN = Integer.parseInt(tokens[2]);
		//System.out.println(netVertexN+" "+netEdgeN+" "+userN);/***********/
		
		tokens = lines.get(2);
		serverCost = Integer.parseInt(tokens[0]);
		//System.out.println(serverCost);/***********/
		
		for(int i = 0;i<netEdgeN;i++){
			tokens = lines.get(4+i);
			netEdges.add(new Edge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
					Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3])));
		}
		for(int i = 0;i<userN;i++){
			tokens = lines.get(4+netEdgeN+1+i);
			userEdges.add(new Edge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
					Integer.parseInt(tokens[2]),0)); //���ѽ�������ӵıߵĻ������� 0
		}
		return new int[]{netVertexN,  netEdgeN,  userN, serverCost};
	}
	
	private List<String[]> readData(String path){
		Scanner in = null ;
		try {
			in = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		List<String[]> lines = new ArrayList<String[]>();
		while(in.hasNextLine()){
			line = in.nextLine();
			lines.add(line.split(" "));
		}
		return lines;
	}
}
