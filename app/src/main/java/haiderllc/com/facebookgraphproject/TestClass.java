package haiderllc.com.facebookgraphproject;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import basegeneticalgorithm.Chromosome;
import geneticalgorithm.ColorChromosome;
import geneticalgorithm.ColorGene;
import geneticalgorithm.ColorGeneticAlgorithm;
import graph.Graph;

/**
 * Created by Haider on 10/15/2014.
 */
public class TestClass {

    /*
    Setup ArrayList of ColorGenes the size of the graph.getAdjMatrixEdges().length
     */
    private ArrayList<Integer[]> colorGenesPool = new ArrayList<Integer[]>();

    /*
    Setup population size of pool
     */
    public static final int POPULATION_SIZE = 300;

    /*
    Set the mutation percent
     */
    public static final double MUTATION_PERCENT = 0.25;

    /*
    Mating population size, population eligible for mating
     */
    protected int matingPopulationSize = POPULATION_SIZE / 2;

    /*
    Part of population favored for mating
     */
    protected int favoredPopulatingSize = this.matingPopulationSize / 2;

    /*
    Set the cut length of the Chromosome when mating
     */
    protected int cutLength = this.SIZE / 5;

    /*
    The generation number for the iteration of the algorithm
     */
    protected int generation;

    /*
    The background worker thread
     */
    protected Thread worker = null;

    /*
    Is the thread started
     */
    protected boolean started = false;

    /*
    Size of ColorGene Array
     */
    public static final int SIZE = 4039;

    /*
    Class to test the ColorChromosome
     */
    private ColorGeneticAlgorithm colorGeneticAlgorithm;
    private ColorChromosome[] colorChromosomes;
    private Graph facebookGraph;

    public TestClass() {

        /*
        Setup Random object
         */
        Random random = new Random();

        // Initialize Graph
        facebookGraph = new Graph(4039);

        // Setup edges for the boolean[][] array using the facebook.txt file
        try {
            @SuppressWarnings("resource")
            Scanner inFile = new Scanner(new
                    File("C:\\Heart Disease Machine Learning\\facebook.txt"));

            while (inFile.hasNextLine()) {

                // Store the next line from the text file
                String line = inFile.nextLine();

                // Split the lines via space
                String[] numbers = line.split(" ");

                // Add the edges for the graph
                facebookGraph.addEdge(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*
        For each index i in the parameter of the population size, create a ColorGene array of random
        int values and then add that to the ArrayList of ColorGene arrays
         */
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Integer[] genes = new Integer[SIZE];
            for (int j = 0; j < SIZE; j++) {
                genes[j] = ColorChromosome.randInt(0, 3);
            }
            colorGenesPool.add(i, genes);
        }

        /*
        We want to set the cut length less than 50% of the gene length
        so that cutpoint2 does not randomly get cut out of bounds
         */
        colorGeneticAlgorithm = new ColorGeneticAlgorithm(colorGenesPool, POPULATION_SIZE,
                MUTATION_PERCENT, 0.25, 0.25, cutLength, facebookGraph);


        int thisCost;
        int oldcost = 4000;
        int countSame = 0;

        while (countSame < 100) {

            this.generation++;

            this.colorGeneticAlgorithm.iteration();

            thisCost = (int) this.colorGeneticAlgorithm.getChromosome(0).getCost();

            System.out.println("Generation " + generation + " Cost " +
                    thisCost + " Mutated" + this.colorGeneticAlgorithm.getMutationPercent());

            if (thisCost == oldcost) {
                countSame++;
            } else {
                // do nothing keep going
            }

        }

        System.out.println("Solution found after " + this.generation + " generations");
        System.out.println("            The cost for the top Chromosome is: " + getTop().getCost());


    }

    public ColorChromosome getTop() {
        return colorGeneticAlgorithm.getChromosome(0);
    }

    public static void main(String[] args) {

        TestClass testClass = new TestClass();

    }

}
