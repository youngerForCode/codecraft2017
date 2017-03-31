package com.cacheserverdeploy.deploy.ga;

public class Population {
    Individual[] individuals;
    
    public Population(int populationSize, String[] graphContent) {
        individuals = new Individual[populationSize];
        Individual.initByFile(graphContent);
        for (int i = 0; i < size(); i++) {
        	Individual individual = new Individual();
    		individual.initGraphAndIndi();
        	saveIndividual(i, individual);
        }
    }
    public Population(int populationSize, boolean initialise) {
        individuals = new Individual[populationSize];
        if (initialise) {
            for (int i = 0; i < size(); i++) {
                Individual newIndividual = new Individual();
                newIndividual.generateIndividual();
                saveIndividual(i, newIndividual);
            }
        }
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual getElite() {
        Individual fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() > getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }
}
