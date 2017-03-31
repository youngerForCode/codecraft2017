package com.cacheserverdeploy.deploy.ga;

import com.cacheserverdeploy.deploy.graph.MaxFlow;

public class FitnessCalc {

    static byte[] solution = new byte[64];

    /* Public methods */
    public static void setSolution(byte[] newSolution) {
        solution = newSolution;
    }

    public static void setSolution(String newSolution) {
        solution = new byte[newSolution.length()];
        // Loop through each character of our string and save it in our byte 
        for (int i = 0; i < newSolution.length(); i++) {
            String character = newSolution.substring(i, i + 1);
            if (character.contains("0") || character.contains("1")) {
                solution[i] = Byte.parseByte(character);
            } else {
                solution[i] = 0;
            }
        }
    }


    public static int getFitness(Individual individual) {
        int fitness = 0;
        MaxFlow maxFlow = new MaxFlow(individual.linkGraph);
        fitness += maxFlow.fordFulkerson(Individual.netVertexN+1,Individual.netVertexN)[1];//user cost
        
        for (int i = 0; i < individual.size(); i++) {
            if (individual.getGene(i) == 1) {
                fitness += Individual.serverCost; //servers cost
            }
        }
        
        return fitness;
    }

   public static int getMaxFitness() {/****************************************/
        int maxFitness = solution.length;
        return maxFitness;
    }
}
