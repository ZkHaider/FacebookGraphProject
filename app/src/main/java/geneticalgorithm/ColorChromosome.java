package geneticalgorithm;

import java.util.Random;

import basegeneticalgorithm.Chromosome;
import basegeneticalgorithm.GeneticAlgorithmError;
import graph.Graph;

/*
Created by Haider on 10/12/2014.
*/

/*
Description: Will generate a chromosome or a bitstream of ints between 0-3 which represents the
colors of the graph structure. The numbers will be random but the Genetic Algorithm with the
cost (fitness) function will determine the best fit for the graph.

In the cost function we want to to determine the best fit for a 4-color graph.
 */

public class ColorChromosome extends Chromosome<Integer, ColorGeneticAlgorithm> {

    /*
    Graph input that will be taken into the cost function
     */
    private Graph graph;

    /*
    Cost variable
     */
    private int cost;


    public ColorChromosome(final ColorGeneticAlgorithm master, final Integer[] colorGenes,
                           Graph graph) {
        this.graph = graph;
        // Initialize the Genetic Algorithm, and GeneArray
        this.setGeneticAlgorithm(master);
        // This will grab index i ColorGene[] from the ArrayList passed into ColorGeneticAlgorithm
        // class.

        // Define the size, make a copy of the genes
        final Integer[] tempGenes = new Integer[colorGenes.length];

        // Set the chromosome to the int values in colorGenes
        for (int i = 0; i < tempGenes.length; i++) {
            tempGenes[i] = colorGenes[i];
        }

        setGenes(tempGenes);
        calculateCost();

        if(tempGenes == null || tempGenes[0] == null) {
            throw new RuntimeException("BLAH");
        }
    }

    /*
    Fitness function that will take in a parameters of graph and colorchromosome
      Description: Will assign colors given by the chromosome to the vertices of the graph
     */
    @Override
    public void calculateCost() throws GeneticAlgorithmError {
        // Implement code to determine the fitness of each gene which will implement determining
        // the actual color number from the adjacency matrix
        int size = graph.getAdjMatrixEdges().length;
        Integer[] genes = getGenes();
        int[] colorArray = new int[size];

        // Assign colors from genes to colorArray
        for (int i = 0; i < size; i++) {
            colorArray[i] = genes[i];
        }

        // Initial Vertex Color
        int color;

        // Cost variable
        cost = 0;

        // Get adjacency matrix
        boolean[][] adjMatrixEdges = graph.getAdjMatrixEdges();

        // Initialize for loop to loop through the vertices of the graph and then find the neighbors
        // adjacent to each vertex
        for (int i = 0; i < adjMatrixEdges.length; i++) { // Loop through the initial vertex
            color = colorArray[i]; // Set the color to initial vertex
            for (int j = 0; j < adjMatrixEdges[i].length; j++) { // Loop through the incidence vertices
                if (adjMatrixEdges[i][j]) {

                    // Add +1 to the cost if the color of the neighbor is the same as the initial
                    // vertex
                    if (colorArray[j] == color) {
                        cost++;
                    }

                }
            }
        }

        setCost(cost);
    }

    @Override
    public void mutate() {
        // Mutate the genes
        final int length = this.getGenes().length;
        final int swap1 = (int) (Math.random() * length);
        final int swap2 = (int) (Math.random() * length);
        final Integer temporary = getGene(swap1);
        setGene(swap1, getGene(swap2));
        setGene(swap2, temporary);
    }


    public static int randInt(int min, int max) {
        Random random = new Random();
        int randNumber = random.nextInt((max - min) + 1) + min;
        return randNumber;
    }
}
