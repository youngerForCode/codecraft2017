package com.cacheserverdeploy.deploy.ga;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cacheserverdeploy.deploy.graph.LinkGraph;
import com.cacheserverdeploy.deploy.graph.MaxFlow;

public class Individual {

    static int defaultGeneLength = 64;
    private byte[] genes = new byte[defaultGeneLength];
    private int fitness = 0;
    
 
    
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            byte gene = 0;
            double random = Math.random();
            if(random < 0.75)
            	gene = 0;
            else
            	gene = 1;
            genes[i] = gene;
        }
    }

    // Use this if you want to create individuals with different gene lengths
    public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }

    public byte getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, byte value) {
        genes[index] = value;
        fitness = 0;
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
    
    //the methom relative to graph
    
    public static int netVertexN = 0;
    public static int netEdgeN = 0;
    public static int userN = 0;
    public static int serverCost = 0;
    public static int totalRequirement = 0;
    public static int [][] inputDatas;
    public  LinkGraph linkGraph ;
    public  void initGraphAndIndi(){
    	
		/*******create linkgraph*******/
		//add two vertex to place server node and user node
		//the netVertexN is user node ,the netVertexN+1 is servers node
    	linkGraph = new LinkGraph(netVertexN +2);
		for (int i = 0; i < netEdgeN; i++){ 
			linkGraph.addSingleEdge(inputDatas[i][0],inputDatas[i][1],inputDatas[i][2],inputDatas[i][3]);
			//net path is double direction
			linkGraph.addSingleEdge(inputDatas[i][1],inputDatas[i][0],inputDatas[i][2],inputDatas[i][3]);
		}
		
		for (int i = 0; i < userN; i++){
			//user edge`s cost is 0
			linkGraph.addSingleEdge(inputDatas[netEdgeN+i][1], netVertexN,inputDatas[netEdgeN+i][2] , 0);
		}
		
		generateIndividual();
		checkIndividual();
    }
    
    public void checkIndividual(){
        fitness = 0;
        deployServers();
        MaxFlow maxFlow = new MaxFlow(linkGraph);
        int []resuslt = maxFlow.fordFulkerson(Individual.netVertexN+1,Individual.netVertexN);
        if (resuslt[0] != Individual.totalRequirement) {
/*			generateIndividual();
			checkIndividual();
			return ;*/
        	resuslt[1] = Integer.MAX_VALUE/2;//never satisfying user requirement ,not a available solution
        }   

        fitness += resuslt[1];
        for (int i = 0; i < genes.length ; i++) {
        	if (genes[i] == 1) {
        		fitness += Individual.serverCost;
        	}
        }
			
    }
    //when genes changes ,this is needed
    public void deployServers(){
		for (int i = 0; i < genes.length; i++) {
			if(genes[i] == 1)
				linkGraph.addSingleEdge(netVertexN+1,i , Integer.MAX_VALUE, 0);
			else//delete server
				linkGraph.deleteSingleEdge(netVertexN+1,i );
				
		}
    }
    public String[] printNetPath(){
    	
    	if(fitness > Integer.MAX_VALUE/2)
    		return new String[]{"NA"};
    	
    	List<String> netPaths = linkGraph.netPaths;
    	String []ret = new String[netPaths.size()+2];
    	ret[0] = "" +netPaths.size();
    	ret[1] = "";
    	for (int i = 0; i < netPaths.size(); i++) {
    		String []tokens = netPaths.get(i).split(" ");
    		int fistNetNum = Integer.parseInt(tokens[2]);//refer to the rules of save path
    		int userNum = 0;
    		for(int j = 0;j<userN;j++){
    			if(inputDatas[netEdgeN+j][1] == fistNetNum){
    				userNum = inputDatas[netEdgeN+j][0];
    				break;
    			}
    		}
    		tokens[1] = ""+userNum;
    		String netPath = new String();
    		for (int j = 0; j < tokens.length; j++) {
    			netPath= tokens[j]+" " + netPath;
    		}
    		ret[2+i] = netPath;
    	}
    	return ret;
/*    	
    	List<String> netPaths = linkGraph.netPaths;
    	try {
    		PrintWriter out  = new PrintWriter(new File("result.txt"));
    		if(fitness > Integer.MAX_VALUE/2)                            
    			out.println("NA");
    		out.println(netPaths.size());
    		out.println();
    		for (int i = 0; i < netPaths.size(); i++) {
    			String []tokens = netPaths.get(i).split(" ");
    			int fistNetNum = Integer.parseInt(tokens[2]);
    			int userNum = 0;
    			for(int j = 0;j<userN;j++){
    				if(inputDatas[netEdgeN+j][1] == fistNetNum){
    					userNum = inputDatas[netEdgeN+j][0];
    					break;
    				}
    			}
    			tokens[1] = ""+userNum;
    			String netPath = new String();
    			for (int j = 0; j < tokens.length; j++) {
    				netPath= tokens[j]+" " + netPath;
    			}
    			out.println(netPath);
    		}
    		out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
/*    
    	List<String> netPaths = linkGraph.netPaths;
    	System.out.println(netPaths.size()+"\n");
    	for (int i = 0; i < netPaths.size(); i++) {
    		String []tokens = netPaths.get(i).split(" ");
    		int fistNetNum = Integer.parseInt(tokens[2]);
    		int userNum = 0;
    		for(int j = 0;j<userN;j++){
    			if(inputDatas[netEdgeN+j][1] == fistNetNum){
    				userNum = inputDatas[netEdgeN+j][0];
    				break;
    			}
    		}
    		tokens[1] = ""+userNum;
    		String netPath = new String();
    		for (int j = 0; j < tokens.length; j++) {
				netPath= tokens[j]+" " + netPath;
			}
    		System.out.println(netPath);
		}*/
    	
    }
    

	public static int[][] initByFile(String []graphContent) {
	    String[] lines = graphContent;
        
  		if (graphContent[0].contains(" ") && graphContent[0].split(" ").length == 3)
		{
			String[] array = graphContent[0].split(" ");
			netVertexN = Integer.parseInt(array[0]);
			netEdgeN = Integer.parseInt(array[1]);
			userN = Integer.parseInt(array[2]);
//			System.out.println(netVertexN+" "+netEdgeN+" "+userN);/************************************************/
			
		}
		
		
  		String []tokens = null;
		tokens = lines[2].split(" ");
		serverCost = Integer.parseInt(tokens[0]);
//		System.out.println(serverCost);/****************************************************************************/
		
		int [][]datas = new int[netEdgeN+userN][4]; 
		for(int i = 0;i<netEdgeN;i++){
			tokens = lines[4+i].split(" ");
			datas[i] = new int[]{Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
					Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3])};
		}
		for(int i = 0;i<userN;i++){
			tokens = lines[4+netEdgeN+1+i].split(" ");
			datas[netEdgeN+i] = new int[]{Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
					Integer.parseInt(tokens[2]),0}; 
			totalRequirement += Integer.parseInt(tokens[2]);
		}
		
//		System.out.println(totalRequirement);/*************************************************************************/
		defaultGeneLength = netVertexN ;
		inputDatas = datas;
		return datas;
	}
	
	public static int[][] initByFile(String path) {
	    List<String[]> lines = readData(path);
	    String []tokens = null;
			
	    tokens = lines.get(0);
	    netVertexN = Integer.parseInt(tokens[0]);
	    netEdgeN = Integer.parseInt(tokens[1]);
	    userN = Integer.parseInt(tokens[2]);
	    System.out.println(netVertexN+" "+netEdgeN+" "+userN);/************************************************/
		
		
		tokens = lines.get(2);
		serverCost = Integer.parseInt(tokens[0]);
		System.out.println(serverCost);/****************************************************************************/
		
		int [][]datas = new int[netEdgeN+userN][4]; 
		for(int i = 0;i<netEdgeN;i++){
			tokens = lines.get(4+i);
			datas[i] = new int[]{Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
					Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3])};
		}
		for(int i = 0;i<userN;i++){
			tokens = lines.get(4+netEdgeN+1+i);
			datas[netEdgeN+i] = new int[]{Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
					Integer.parseInt(tokens[2]),0}; 
			totalRequirement += Integer.parseInt(tokens[2]);
		}
		
		System.out.println(totalRequirement);/*************************************************************************/
		defaultGeneLength = netVertexN ;
		inputDatas = datas;
		return datas;
	}
	private static List<String[]> readData(String path){
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