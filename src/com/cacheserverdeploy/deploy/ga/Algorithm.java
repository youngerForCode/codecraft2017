package com.cacheserverdeploy.deploy.ga;

public class Algorithm {

    /* GA param */
    private static final double mateRate = 0.7; 
//    private static final double mutationRate = 0.015; 
    private static final double mutationRate = 0.35;
    private static final int tournamentSize = 5; 
    private static final boolean elitism = true; 

    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false);

        if (elitism) {
            newPopulation.saveIndividual(0, pop.getElite());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            // Mutate population  
            mutate(newIndiv);
            newIndiv.initGraphAndIndi();//when crose create a new individual,but never init linkgraph
            newPopulation.saveIndividual(i, newIndiv);
        }

/*        // Mutate population  
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }*/

        return newPopulation;
    }

    private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual(); 
        // random select
        for (int i = 0; i < indiv1.size(); i++) {
            if (Math.random() <= mateRate) {
                newSol.setGene(i, indiv1.getGene(i));
            } else {
                newSol.setGene(i, indiv2.getGene(i));
            }
        }
        return newSol;
    }

    private static void mutate(Individual indiv) {
        for (int i = 0; i < indiv.size(); i++) {
            if (Math.random() <= mutationRate) {
                byte gene = (byte) Math.round(Math.random());
                indiv.setGene(i, gene);
            }
        }
    }

    private static Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournamentPop = new Population(tournamentSize, false);
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournamentPop.saveIndividual(i, pop.getIndividual(randomId));
        }
        Individual fittest = tournamentPop.getElite();
        return fittest;
    }
}