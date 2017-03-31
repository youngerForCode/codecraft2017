package com.cacheserverdeploy.deploy;

import com.cacheserverdeploy.deploy.ga.Algorithm;
import com.cacheserverdeploy.deploy.ga.Individual;
import com.cacheserverdeploy.deploy.ga.Population;

public class Deploy {
    /**
     * todo
     * 
     * @param graphContent caseinfo
     * @return  caseouput info
     * @see [huawei]
     * 
     * the record of improving
     * change the probability of generating 0 and 1
     */
    public static String[] deployServer(String[] graphContent)
    {
        /**do your work here**/
        long initTime = System.currentTimeMillis();
		int generationCount = 0;
		int maxGeneration = 200;
		Population myPop = new Population(40, graphContent);

//		while (generationCount < maxGeneration && (System.currentTimeMillis()-initTime) < 80000) {
		while ( (System.currentTimeMillis()-initTime) < 80000) {
/*			System.out.println("Generation: " + generationCount + " Fittest: "
					+ myPop.getElite().getFitness());*/
			myPop = Algorithm.evolvePopulation(myPop);
/*			if(myPop.getElite().getFitness() >= FitnessCalc.getMaxFitness())
				break;*/
			generationCount++;
		}
		
		Individual elite = myPop.getElite();
		System.out.println("Generation: " + generationCount+" the totalCost: "+elite.getFitness() );
		System.out.println("Final Fittest Genes:");
		System.out.println(elite);
		String []netPaths =  elite.printNetPath();
/*		for (int i = 0; i < netPaths.length; i++) {
			System.out.println(netPaths[i]);
		}*/
		
		return netPaths;
//		return new String[]{"17","\r\n","0 8 0 20"};
    }
}
